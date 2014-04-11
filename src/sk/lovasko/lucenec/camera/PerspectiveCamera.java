package sk.lovasko.lucenec.camera;

import sk.lovasko.lucenec.geom.Point;
import sk.lovasko.lucenec.geom.Vector;
import sk.lovasko.lucenec.geom.Ray;

public final class PerspectiveCamera implements Camera
{
	private final Point center;
	private final double ratio;
	private final Vector z_axis;
	private final Vector x_axis;
	private final Vector y_axis;
	private final double focus;

	public PerspectiveCamera (
		final Point center,
		final Vector forward,
		final Vector up,
		final double horizontal_opening_angle,
		final double ratio)
	{
		this.center = center;
		this.ratio = ratio;
		z_axis = forward.normalize();
		x_axis = Vector.cross_product(z_axis, up.normalize()).normalize();
		y_axis = Vector.cross_product(x_axis, z_axis).normalize();
		focus = 1.0 / Math.tan(horizontal_opening_angle / 2.0);
	}

	public final Ray get_primary_ray (final double x, final double y)
	{
		final Vector d = x_axis.multiply_scalar(x * ratio)
		            .add(y_axis.multiply_scalar(y))
		            .add(z_axis.multiply_scalar(focus));

		return new Ray(center, d.normalize());
	}
}

