package sk.lovasko.lucenec.material;

import sk.lovasko.lucenec.color.RGB;
import sk.lovasko.lucenec.geom.Vector;
import sk.lovasko.lucenec.geom.Point;

public final class MirrorMaterial implements Material
{
	private final double eta;
	private final double kappa;
	private final double e2k2;

	public MirrorMaterial (final double _eta, final double _kappa)
	{
		eta = _eta;
		kappa = _kappa;

		e2k2 = eta * eta + kappa * kappa;
	}

	public RGB get_reflectance (final Point tex_point, final Vector normal, final Vector out_dir, final Vector in_dir)
	{
		final double cos_theta = Vector.dot_product(normal.normalize(), in_dir.normalize());
		final double cos_theta_2 = cos_theta * cos_theta;

		final double parallel = (e2k2 * cos_theta_2 - 2.0 * eta * cos_theta + 1.0) / (e2k2 * cos_theta_2 + 2.0 * eta * cos_theta + 1.0);
		final double perpendicular = (e2k2 - 2.0 * eta * cos_theta + cos_theta_2) / (e2k2 + 2.0 * eta * cos_theta + cos_theta_2);

		final double fr = (parallel + perpendicular) * 0.5;
		return new RGB(RGB.clamp_double(fr));
	}

	public RGB get_emission (final Point tex_point, final Vector normal, final Vector out_dir)
	{
		return new RGB(0.0);
	}

	public SampleReflectance get_sample_reflectance (final Point tex_point, final Vector normal, final Vector out_dir)
	{
		final Vector ideal_reflection = normal.multiply_scalar(2.0).multiply_scalar(Vector.dot_product(normal, out_dir)).subtract(out_dir);
		return new SampleReflectance(ideal_reflection, get_reflectance(tex_point,	normal, out_dir, ideal_reflection));
	}

	public Sampling get_sampling ()
	{
		return Sampling.ALL;
	}
}

