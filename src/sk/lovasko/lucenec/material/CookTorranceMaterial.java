package sk.lovasko.lucenec.material;

import sk.lovasko.lucenec.color.RGB;
import sk.lovasko.lucenec.geom.Vector;
import sk.lovasko.lucenec.geom.Point;
import sk.lovasko.lucenec.texture.Texture;

public final class CookTorranceMaterial implements Material
{
	private final Texture texture;
	private final double exp_x;
	private final double exp_y;
	private final double eta;
	private final double kappa;
	private final double e2k2;

	public CookTorranceMaterial (final Texture _texture, 
		final double _exp_x,
		final double _exp_y,
		final double _eta,
		final double _kappa)
	{
		texture = _texture;
		exp_x = _exp_x;
		exp_y = _exp_y;
		eta = _eta;
		kappa = _kappa;
		e2k2 = eta * eta + kappa * kappa;
	}

	private final class CoordinateConverter
	{
		private Vector a, b, c;

		CoordinateConverter (Vector setter)
		{
			c = setter.normalize();
			b = Vector.cross_product(c, new Vector(1, 0, 0));
			a = Vector.cross_product(b, c);
		}

		public Vector convert (Vector vector)
		{
			return new Vector(
				Vector.dot_product(vector, a), 
				Vector.dot_product(vector, b), 
				Vector.dot_product(vector, c));
		}
	}

	// i dont claim any deep knowledge of this code
	// most of it is written to stick with various formulas on the wikipedia
	public RGB get_reflectance (final Point tex_point, final Vector normal, final Vector out_dir, final Vector in_dir)
	{
		CoordinateConverter cf = new CoordinateConverter(normal);

		final Vector lin = cf.convert(in_dir);
		final Vector lout = cf.convert(out_dir);
		final Vector lh = lin.add(lout).normalize();
		final double cos_2 = lh.get_z() * lh.get_z();
		final double sin = Math.sqrt(1.0 - cos_2);
		final double a = lh.get_x() / sin;
		final double b = lh.get_y() / sin;
		final double e = exp_x * a * a + exp_y * b * b;
		final double D = Math.sqrt(exp_x + 2) * Math.sqrt(exp_y + 2) * Math.pow(Math.abs(lh.get_z()), e) * Math.PI / 2.0;

		final Vector gh = in_dir.add(out_dir).normalize();
		final double cos_theta = Vector.dot_product(out_dir.normalize(), gh);
		final double cos_theta_2 = cos_theta * cos_theta;
		final double parallel = (e2k2 * cos_theta_2 - 2.0 * eta * cos_theta + 1.0) / (e2k2 * cos_theta_2 + 2.0 * eta * cos_theta + 1.0);
		final double perpendicular = (e2k2 - 2.0 * eta * cos_theta + cos_theta_2) / (e2k2 + 2.0 * eta * cos_theta + cos_theta_2);
		final double fresnel = (parallel + perpendicular) * 0.5;
		final double F = RGB.clamp_double(fresnel); 

		final double ga = lh.get_z() * lin.get_z() * 2.0 * Vector.dot_product(lh, lin); 
		final double gb = lh.get_z() * lout.get_z() * 2.0 * Vector.dot_product(lh, lout); 
		double G = Math.min(ga, gb); 

		if (G > 1.0) G = 1.0;

		final double kspec = D * F * G / (4.0 * Vector.dot_product(normal, in_dir) * Vector.dot_product(normal, out_dir));
		return texture.get_color(tex_point).multiply_scalar(kspec);
	}

	public RGB get_emission (final Point tex_point, final Vector normal, final Vector out_dir)
	{
		return new RGB(0.0);
	}

	public SampleReflectance get_sample_reflectance (final Point tex_point, final Vector normal, final Vector out_dir)
	{
		return null;
	}

	public Sampling get_sampling ()
	{
		return Sampling.NOT_NEEDED;
	}
}
