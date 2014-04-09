package sk.lovasko.lucenec;

public final class LightHit
{
	private final Vector direction;
	private final double distance;

	public LightHit (final Vector direction, final double distance)
	{
		this.direction = direction;
		this.distance = distance;
	}

	public final Vector get_direction ()
	{
		return direction;
	}

	public final double get_distance ()
	{
		return distance;
	}
}

