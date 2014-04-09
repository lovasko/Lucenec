package sk.lovasko.lucenec;

// TODO make this final immutable class
public final class PointPair
{
	private Point first;
	private Point second;

	PointPair (Point _first, Point _second)
	{
		first = _first;
		second = _second;
	}

	public Point get_first ()
	{
		return first;
	}

	public Point get_second ()
	{
		return second;
	}
}

