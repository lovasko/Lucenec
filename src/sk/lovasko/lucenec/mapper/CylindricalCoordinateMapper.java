package sk.lovasko.lucenec.mapper;

import sk.lovasko.lucenec.geom.Intersection;
import sk.lovasko.lucenec.geom.Point;
import sk.lovasko.lucenec.geom.Vector;
import sk.lovasko.lucenec.math.Matrix;

public final class CylindricalCoordinateMapper implements CoordinateMapper
{
	private final Point origin;
	private final Matrix local;
	private final double x_scale;
	private final double y_scale;

	public CylindricalCoordinateMapper (
		final Point origin, 
		final Vector longitudinal,
		final Vector polar)
	{
		this.origin = origin;	
		local = Matrix.local(longitudinal);
		x_scale = polar.length();	
		y_scale = longitudinal.length();	
	}

	public final Point get_coordinates (final Intersection intersection)
	{
		final Vector difference = intersection.get_local().subtract(origin);
		final Vector local_difference = local.multiply(difference);

		final double beta = -Math.atan2(
			local_difference.get_y(), 
			local_difference.get_x()) 
			/ (2.0 * Math.PI);

		return new Point(beta / x_scale, local_difference.get_z() / y_scale, 0.0);
	}
}

