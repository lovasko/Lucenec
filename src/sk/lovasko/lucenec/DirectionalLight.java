package sk.lovasko.lucenec;

public final class DirectionalLight implements Light
{
	private final Vector direction;
	private final RGB color;

	public DirectionalLight(final Vector _direction, final RGB _color)
	{
		direction = _direction.normalize();
		color = _color;
	}

	public final LightHit get_light_hit (final Point point)
	{
		return new LightHit(direction, Double.MAX_VALUE);
	}

	public final RGB get_intensity (final LightHit light_hit)
	{
		return color;
	}

	public final void dump ()
	{
		System.out.println("Directional " + direction + " " + color);
	}
}

