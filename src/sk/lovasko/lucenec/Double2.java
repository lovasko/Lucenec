package sk.lovasko.lucenec;

public final class Double2
{
	private final double a;
	private final double b;

	Double2 (final double _a, final double _b)
	{
		a = _a;
		b = _b;
	}

	public final Point to_point ()
	{
		return new Point(a, b, 0.0);
	}

	public final Vector to_vector ()
	{
		return new Vector(a, b, 0.0);
	}
}

