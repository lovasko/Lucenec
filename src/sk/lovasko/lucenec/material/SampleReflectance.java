package sk.lovasko.lucenec;

import java.io.Serializable;

public final class SampleReflectance implements Serializable
{
	private final Vector direction;
	private final RGB reflectance;

	public SampleReflectance(final Vector _direction, final RGB _reflectance)
	{
		direction = _direction;
		reflectance = _reflectance;
	}

	public SampleReflectance()
	{
		direction = new Vector(0.0, 0.0, 1.0);
		reflectance = new RGB(0.0);
	}

	public final Vector get_direction ()
	{
		return direction;
	}

	public final RGB get_reflectance ()
	{
		return reflectance;
	}

	public final SampleReflectance multiply_scalar_reflectance (final double scalar)
	{
		return new SampleReflectance(direction, reflectance.multiply_scalar(scalar));
	}
}

