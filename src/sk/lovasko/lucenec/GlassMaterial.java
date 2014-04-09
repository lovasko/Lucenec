package sk.lovasko.lucenec;

public final class GlassMaterial implements Material
{
	private final double eta;

	public GlassMaterial (final double eta)
	{
		this.eta = eta;
	}

	public final RGB get_reflectance (
		final Point tex_point, 
		final Vector normal, 
		final Vector out_dir, 
		final Vector in_dir)
	{
		return new RGB(1.0);
	}

	public RGB get_emission (
		final Point tex_point, 
		final Vector normal, 
		final Vector out_dir)
	{
		return new RGB(0.0);
	}

	public SampleReflectance get_sample_reflectance (
		final Point tex_point, 
		final Vector normal, 
		final Vector out_dir)
	{
		double cos_beta = Vector.dot_product(out_dir.negate(), normal);

		// sin = 1 - cos (all squared)
		double sin_beta = Math.sqrt(1.0 - (cos_beta * cos_beta));

		double eta0 = eta;
		double eta1 = eta;
		double eta2 = 1.0;
		Vector new_normal = normal;

		if (cos_beta > 0.0)
		{
			new_normal = normal.negate();
		}
		else
		{
			cos_beta = -cos_beta;
			eta0 = 1.0/eta0;
			eta1 = 1.0;
			eta2 = eta;
		}

		if (sin_beta > (1.0 / eta0))
		{
			final Vector ideal_reflection = normal
				.multiply_scalar(2.0)
				.multiply_scalar(Vector.dot_product(normal, out_dir))
				.subtract(out_dir.normalize());

			return new SampleReflectance(ideal_reflection, new RGB(1.0));
		}

		double cos_alpha = Math.sqrt(1.0 - (eta0 * sin_beta * eta0 * sin_beta));
		double s = Math.abs((eta1 * cos_beta - eta2 * cos_alpha) / (eta1 * cos_beta + eta2 * cos_alpha));
		s = s * s;

		double p = Math.abs((eta1 * cos_alpha - eta2 * cos_beta) / (eta1 * cos_alpha + eta2 * cos_beta));
		p = p * p;

		final double fr = (s + p) / 2.0;
		final double tr = 1.0 - fr;

		if (Math.random() < 0.5)
		{
			final Vector ideal_reflection = normal
				.multiply_scalar(2.0)
				.multiply_scalar(Vector.dot_product(normal, out_dir))
				.subtract(out_dir.normalize());

			return new SampleReflectance(ideal_reflection.normalize(), new RGB(2.0 * fr));
		}
		else
		{
			final Vector trans = out_dir.negate().multiply_scalar(eta0)
				.add(new_normal.multiply_scalar(eta0 * cos_beta - cos_alpha));

			return new SampleReflectance(trans, new RGB(2.0 * tr));
		}
	}

	public Sampling get_sampling ()
	{
		return Sampling.ALL;
	}
}
