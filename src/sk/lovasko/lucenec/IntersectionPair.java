package sk.lovasko.lucenec;

/* TODO
 * idiotic API that considers t0 > t1 as a failure.
 * soooo wrong, fix this.
 */

public final class IntersectionPair
{
	private final double t0;
	private final double t1;

	IntersectionPair (final double _t0, final double _t1)
	{
		t0 = _t0;
		t1 = _t1;
	}

	public final double get_t0 ()
	{
		return t0;
	}

	public final double get_t1 ()
	{
		return t1;
	}

	public final boolean fail ()
	{
		if (t0 > t1)
		{
			return true;
		}
		else
		{
			return false;
		}
	}

	public final boolean success ()
	{
		return !fail();
	}
}

