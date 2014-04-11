package sk.lovasko.lucenec.camera;

import sk.lovasko.lucenec.geom.Point;
import sk.lovasko.lucenec.geom.Vector;
import sk.lovasko.lucenec.geom.Ray;

public final class OrthographicCamera implements Camera
{
	private final Point center;
	private final double scale_x;
	private final double scale_y;
	private final Vector z_axis;
	private final Vector x_axis;
	private final Vector y_axis;

	public OrthographicCamera (
		final Point center, 
		final Vector forward, 
		final Vector up, 
		double scale_x, 
		double scale_y)
	{
		this.center = center;
		this.scale_x = scale_x;
		this.scale_y = scale_y;
		z_axis = forward.normalize();
		x_axis = Vector.cross_product(z_axis, up.normalize()).normalize();
		y_axis = Vector.cross_product(x_axis, z_axis).normalize();
	}

	public final Ray get_primary_ray (final double x, final double y)
	{
		final Vector v = x_axis.multiply_scalar(x * scale_x / 2.0)
		            .add(y_axis.multiply_scalar(y * scale_y / 2.0));
		
		return new Ray(center.move(v), z_axis);
	}
}

