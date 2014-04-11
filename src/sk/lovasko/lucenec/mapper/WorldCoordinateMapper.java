package sk.lovasko.lucenec.mapper;

import sk.lovasko.lucenec.geom.Intersection;
import sk.lovasko.lucenec.geom.Point;
import sk.lovasko.lucenec.geom.Vector;
import sk.lovasko.lucenec.math.Double4;
import sk.lovasko.lucenec.math.Matrix;

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

