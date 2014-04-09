package sk.lovasko.lucenec;

import java.util.Collections;
import java.util.ArrayList;
import java.util.List;

public final class PerlinNoiseTexture implements Texture
{
	private final RGB black;
	private final RGB white;
	private final List<Double> frequencies;
	private final List<Double> amplitudes;
	private int octave_count;

	public PerlinNoiseTexture (final RGB white, final RGB black)
	{
		this.black = black;
		this.white = white;

		frequencies = new ArrayList<Double>();
		amplitudes = new ArrayList<Double>();
		octave_count = 0;
	}

	private final double noise (final int x, final int y, final int z) 
	{
		int n = x + y * 57 + z * 997;
		n = (n << 13) ^ n;

		return 
			(1.0 - 
				(
					(n * (n * n * 15731 + 789221) + 1376312589) 
					& 0x7fffffff
				) 
			/ 1073741824.0);
	}

	private final double noise_3d (final Point point)
	{
		final int ix = (int)Math.floor(point.get_x());
		final int iy = (int)Math.floor(point.get_y());
		final int iz = (int)Math.floor(point.get_z());

		final double dx = Common.abs_fractional(point.get_x());
		final double dy = Common.abs_fractional(point.get_y());
		final double dz = Common.abs_fractional(point.get_z());

		final double w000 = noise(ix,   iy,   iz);
		final double w100 = noise(ix+1, iy,   iz);
		final double w010 = noise(ix,   iy+1, iz);
		final double w110 = noise(ix+1, iy+1, iz);
		final double w001 = noise(ix,   iy,   iz+1); 
		final double w101 = noise(ix+1, iy,   iz+1); 
		final double w011 = noise(ix,   iy+1, iz+1); 
		final double w111 = noise(ix+1, iy+1, iz+1);

		final double x00 = Common.lerp(dx, w000, w100);
		final double x10 = Common.lerp(dx, w010, w110);
		final double x01 = Common.lerp(dx, w001, w101);
		final double x11 = Common.lerp(dx, w011, w111);
		final double y0 = Common.lerp(dy, x00, x10);
		final double y1 = Common.lerp(dy, x01, x11);

		// because we need to use the lerp3D function
		return lerp3d(w000, w100, w010, w110, w001, w101, w011, w111, dx, dy, dz);
//		return Common.lerp(dz, y0, y1);
		
	}

	private final double lerp3d (
		final double px0y0z0,
		final double px1y0z0, 
		final double px0y1z0, 
		final double px1y1z0,
		final double px0y0z1, 
		final double px1y0z1, 
		final double px0y1z1, 
		final double px1y1z1,
		final double x_point, 
		final double y_point, 
		final double z_point) 
	{
		return 
		(1.0 - x_point) * (1.0 - y_point) * (1.0 - z_point) * px0y0z0 +  
		(1.0 - x_point) * (1.0 - y_point) * (z_point)       * px0y0z1 +  
		(1.0 - x_point) * (y_point)       * (1.0 - z_point) * px0y1z0 +  
		(1.0 - x_point) * (y_point)       * (z_point)       * px0y1z1 +  
		(x_point)       * (1.0 - y_point) * (1.0 - z_point) * px1y0z0 +  
		(x_point)       * (1.0 - y_point) * (z_point)       * px1y0z1 +  
		(x_point)       * (y_point)       * (1.0 - z_point) * px1y1z0 +  
		(x_point)       * (y_point)       * (z_point)       * px1y1z1; 
	}

	public final void add_octave (
		final double amplitude, 
		final double frequency)
	{
		amplitudes.add(amplitude);
		frequencies.add(frequency);
		octave_count += 1;
	}

	public final RGB get_color (final Point point)
	{
		double sum = 0.0;
		for (int i = 0; i < octave_count; i++)
		{
			sum += noise_3d(point.multiply_scalar(frequencies.get(i))) * amplitudes.get(i);
		}
		sum = (sum + 1.0) / 2.0;

		return RGB.lerp(black, white, sum);
	}
}

