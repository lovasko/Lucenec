package sk.lovasko.lucenec;

public final class TriangleCoordinateMapper implements CoordinateMapper
{
	private final Point p1;
	private final Point p2;
	private final Point p3;

	public TriangleCoordinateMapper (
		final Point p1, 
		final Point p2, 
		final Point p3)
	{
		this.p1 = p1;
		this.p2 = p2;
		this.p3 = p3;
	}

	public final Point get_coordinates (final Intersection intersection)
	{
		final Point local = intersection.get_local();

		final Point result = 
			     p1.multiply(local.get_y())
			.add(p2.multiply(local.get_z()))
			.add(p3.multiply(local.get_x()));

		return new Point(result.get_y(), result.get_x(), result.get_z());
	}
}

