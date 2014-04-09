package sk.lovasko.lucenec;

public final class EnvironmentalCoordinateMapper implements CoordinateMapper
{
	private double shift;
	private final Matrix local;
	private final Vector local_zenith;

	public EnvironmentalCoordinateMapper (
		final Vector zenith, 
		final Vector azimuth)
	{
		local = Matrix.local(zenith);
		local_zenith = local.multiply(zenith);
		
		final Vector local_azimuth = local.multiply(azimuth).normalize();
		
		shift = Math.atan2(local_azimuth.get_y(), local_azimuth.get_x());
		if (shift < 0.0) 
			shift = shift + Math.PI * 2.0;
	}

	public final Point get_coordinates (final Intersection intersection)
	{
		final Vector local_difference = local.multiply(intersection.get_ray().get_direction()).normalize();

		double beta = Math.atan2(local_difference.get_y(), local_difference.get_x()) + shift;
		if (beta > Math.PI * 2.0)
			beta = beta - Math.PI * 2.0;
		
		double alpha = Vector.dot_product(local_difference.normalize(), local_zenith.normalize());
		double delta = Math.acos(alpha);

		return new Point(beta / (Math.PI * 2.0) / 2.0, delta / (Math.PI) / 2.0, 0);
	}
}

