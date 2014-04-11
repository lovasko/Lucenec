package sk.lovasko.lucenec.light;

import sk.lovasko.lucenec.color.RGB;
import sk.lovasko.lucenec.geom.Point;
import sk.lovasko.lucenec.geom.Vector;

public final class PointLight implements Light
{
	private final Point position;
	private final RGB color;

	public PointLight (final Point position, final RGB color)
	{
		this.position = position;
		this.color = color;
	}

	public final LightHit get_light_hit (final Point point)
	{
		final Vector difference = point.subtract(position);
		return new LightHit(difference, difference.length());
	}

	public final RGB get_intensity (final LightHit light_hit)
	{
		return color.divide_scalar(light_hit.get_distance() * light_hit.get_distance());
	}

	public final void dump ()
	{
		System.out.println("Point " + position + " " + color);
	}
}

