package sk.lovasko.lucenec;

public final class DOFPerspectiveCamera implements Camera
{
	private final Point center;
	private final double ratio;
	private final Vector z_axis;
	private final Vector x_axis;
	private final Vector y_axis;
	private final double focus;
	private final double focal_length;
	private final double aperture_radius;

	public DOFPerspectiveCamera (
		final Point center,
		final Vector forward,
		final Vector up,
		final double horizontal_opening_angle,
		final double ratio,
		final double focal_length,
		final double aperture_radius)
	{
		this.center = center;
		this.ratio = ratio;
		this.focal_length = focal_length;
		this.aperture_radius = aperture_radius;
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

		final double lu = Math.random();
		final double lv = Math.random();
		final double a = Math.sqrt(lu) * aperture_radius;
		final double theta = 2.0 * Math.PI * lv;
		final double xi = a * Math.cos(theta);
		final double yi = a * Math.sin(theta);

		final Ray ray = new Ray(center, d);

		final Point ro = center.move(x_axis.multiply(xi)).move(y_axis.multiply(yi));
		final double ft = focal_length / Vector.dot_product(z_axis, d);
		final Vector rd = (d.multiply(ft).subtract(ro.subtract(center))).normalize();

		return new Ray(ro, rd);
	}
}

