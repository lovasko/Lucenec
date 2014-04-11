package sk.lovasko.lucenec.material;

import sk.lovasko.lucenec.color.RGB;
import sk.lovasko.lucenec.geom.Point;
import sk.lovasko.lucenec.geom.Vector;
import sk.lovasko.lucenec.math.Matrix;

public final class FuzzyMirrorMaterial implements Material
{
	private final double eta;
	private final double kappa;
	private final double max_angle;
	private final double e2k2;

	public FuzzyMirrorMaterial (
		final double eta, 
		final double kappa,
		final double max_angle)
	{
		this.eta = eta;
		this.kappa = kappa;
		this.max_angle = max_angle;

		e2k2 = eta * eta + kappa * kappa;
	}

	public RGB get_reflectance (
		final Point tex_point, 
		final Vector normal, 
		final Vector out_dir, 
		final Vector in_dir)
	{
		final double cos_theta = Vector.dot_product(
			normal.normalize(), 
			in_dir.normalize());

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
		final Vector ideal_reflection = normal
			.multiply_scalar(2.0)
			.multiply_scalar(Vector.dot_product(normal, out_dir))
			.subtract(out_dir);

		final Matrix rotation = Matrix.local(ideal_reflection);
		final double radius = Math.random() * Math.tan(max_angle);
		final double beta = Math.sqrt(Math.random()) * 2.0 * Math.PI;
		final double x = radius * Math.cos(beta);
		final double y = radius * Math.sin(beta);

		final Vector fuzzy_reflection = 
			rotation.get_a().multiply_scalar(x)
			.add(rotation.get_b().multiply_scalar(y)
			.add(rotation.get_c())).to_vector();

		return new SampleReflectance(
			fuzzy_reflection.normalize(), 
			get_reflectance(
				tex_point,
				normal, 
				out_dir, 
				ideal_reflection));
	}

	public Sampling get_sampling ()
	{
		return Sampling.ALL;
	}
}

