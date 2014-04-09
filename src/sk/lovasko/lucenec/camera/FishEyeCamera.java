package sk.lovasko.lucenec;

public final class FishEyeCamera implements Camera
{
	private final Point center;
	private final double psi_max;
	private static double PI_180 = 0.017453292519943295;
	private final Vector z_axis;
	private final Vector x_axis;
	private final Vector y_axis;

	public FishEyeCamera (
		final Point center, 
		final Vector forward, 
		final Vector up, 
		final double psi_max)
	{
		this.center = center;
		this.psi_max = psi_max;
		z_axis = forward.normalize();
		x_axis = Vector.cross_product(z_axis, up.normalize()).normalize();
		y_axis = Vector.cross_product(x_axis, z_axis).normalize();
		
	}

	public final Ray get_primary_ray (final double x, final double y)
	{
		final double r = Math.sqrt(x*x + y*y);
		final double psi = r * psi_max * PI_180;
		final double sin_psi = Math.sin(psi);
		final double cos_psi = Math.cos(psi);
		final double sin_alpha = y / r;
		final double cos_alpha = x / r;

		final Vector d = x_axis.multiply_scalar(sin_psi * cos_alpha)
		            .add(y_axis.multiply_scalar(sin_psi * sin_alpha))
		       .subtract(z_axis.multiply_scalar(cos_psi));

		return new Ray(center, d);
	}
}

