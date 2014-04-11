package sk.lovasko.lucenec.material;

import sk.lovasko.lucenec.color.RGB;
import sk.lovasko.lucenec.geom.Vector;
import sk.lovasko.lucenec.geom.Point;
import sk.lovasko.lucenec.texture.Texture;

public final class FlatMaterial implements Material
{
	private final Texture texture;

	public FlatMaterial (final Texture _texture)
	{
		texture = _texture;
	}

	public RGB get_reflectance (
		final Point tex_point, 
		final Vector normal, 
		final Vector out_dir, 
		final Vector in_dir)
	{
		return new RGB(0.0);
	}

	public RGB get_emission (
		final Point tex_point, 
		final Vector normal, 
		final Vector out_dir)
	{
		return texture.get_color(tex_point);
	}

	public SampleReflectance get_sample_reflectance (
		final Point tex_point, 
		final Vector normal, 
		final Vector out_dir)
	{
		return null;
	}

	public Sampling get_sampling ()
	{
		return Sampling.NOT_NEEDED;
	}
}

