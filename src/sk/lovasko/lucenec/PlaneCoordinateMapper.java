package sk.lovasko.lucenec;

public final class PlaneCoordinateMapper implements CoordinateMapper
{
	private final Matrix transformation;

	public PlaneCoordinateMapper (final Vector v1, final Vector v2)
	{
		final Vector normal = Vector.cross_product(v2, v1);	

		transformation = new Matrix(
			new Double4(v1.get_x(), v2.get_x(), normal.get_x(), 0.0),
			new Double4(v1.get_y(), v2.get_y(), normal.get_y(), 0.0),
			new Double4(v1.get_z(), v2.get_z(), normal.get_z(), 0.0),
			new Double4(0.0,        0.0,        0.0,            1.0)).inverse();
	}

	public final Point get_coordinates (final Intersection intersection)
	{
		return transformation.multiply(intersection.get_local()).set_z(0.0);	
	}
}

