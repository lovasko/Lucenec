package sk.lovasko.lucenec;

import java.io.Serializable;

abstract class Solid extends Primitive implements Serializable
{
	public abstract double get_area();

	public Point get_sample()
	{
		return null;
	}

	public Vector get_normal_at_point (final Point point)
	{
		return null;
	}
}

