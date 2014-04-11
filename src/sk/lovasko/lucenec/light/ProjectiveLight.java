package sk.lovasko.lucenec.light;

import sk.lovasko.lucenec.color.RGB;
import sk.lovasko.lucenec.geom.Point;
import sk.lovasko.lucenec.geom.Vector;
import sk.lovasko.lucenec.texture.Texture;

public final class ProjectiveLight implements Light
{
	private final Point position;
	private final Texture texture;

	public ProjectiveLight (final Point position, final Texture texture)
	{
		this.position = position;
		this.texture = texture;
	}

	public final LightHit get_light_hit (final Point point)
	{
		final Vector difference = point.subtract(position);
		return new LightHit(difference, difference.length());
	}

	public RGB get_intensity (final LightHit light_hit)
	{
		final Point texture_point = position.move(light_hit.get_direction());
		final RGB color = texture.get_color(texture_point);
		return color.divide_scalar(light_hit.get_direction().lensqr());
	}

	public final void dump ()
	{
		System.out.println("Projective " + position);
	}
}

