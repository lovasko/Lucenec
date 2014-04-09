package sk.lovasko.lucenec;

public final class PhongMaterial implements Material
{
	private final Texture texture;
	private final double exponent; 

	public PhongMaterial (final Texture _texture, final double _exponent) 
	{
		texture = _texture;
		exponent = _exponent;
	}

	public RGB get_reflectance (final Point tex_point, final Vector normal, final Vector out_dir, final Vector in_dir)
	{
		final Vector ideal_reflection = normal.multiply_scalar(2.0).multiply_scalar(Vector.dot_product(normal, in_dir)).subtract(in_dir);
		final double cos = Vector.dot_product(ideal_reflection.normalize(), out_dir.normalize());
		
		if (cos < 0.0) 
		{
			return new RGB(0.0);
		}

		final double power = Math.pow(cos, exponent);

		return new RGB(power * (exponent + 2.0) / (2.0 * Math.PI));
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

