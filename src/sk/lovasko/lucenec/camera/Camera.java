package sk.lovasko.lucenec.camera;

import sk.lovasko.lucenec.geom.Ray;

public interface Camera
{
	public Ray get_primary_ray (final double x, final double y);
}

