package sk.lovasko.lucenec;

public final class SphericalCoordinateMapper implements CoordinateMapper
{
	private final Vector local_zenith;
	private final Vector local_azimuth;
	private final Point origin;
	private final Matrix local;
	private double x_scale;
	private double y_scale;

	public SphericalCoordinateMapper (
		final Point origin,
		final Vector zenith,
		final Vector azimuth)
	{
		local = Matrix.local(zenith);

		local_azimuth = local.multiply(azimuth);
		local_zenith = local.multiply(zenith);

		this.origin = origin;
		x_scale = azimuth.length();
		y_scale = zenith.length();
	}

	public final Point get_coordinates (final Intersection intersection)
	{
		final Vector difference = intersection.get_local().subtract(origin);
		final Vector local_difference = local.multiply(difference);

		final double beta = Vector.dot_product(
			local_difference.normalize(), 
			local_zenith.normalize());

		final double theta = Math.acos(beta) / Math.PI;
		final double phi = Math.atan2(
			local_difference.get_y(), 
			local_difference.get_x()) 
			/ (2.0 * Math.PI);

		return new Point(phi/x_scale, theta/y_scale, 0.0);
	}
}

