package sk.lovasko.lucenec;

public final class WorldCoordinateMapper implements CoordinateMapper
{
	private final Matrix scale;

	public WorldCoordinateMapper ()
	{
		this.scale = Matrix.identity();
	}

	public WorldCoordinateMapper (final Double4 scale)
	{
		this.scale = Matrix.identity().multiply_double4(scale);
	}

	public final Point get_coordinates (final Intersection intersection)
	{
		return scale.multiply(intersection.get_hit_point());
	}
}

