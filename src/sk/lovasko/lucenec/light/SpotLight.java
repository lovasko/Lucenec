package sk.lovasko.lucenec.light;

import sk.lovasko.lucenec.color.RGB;
import sk.lovasko.lucenec.geom.Point;
import sk.lovasko.lucenec.geom.Vector;

public final class SpotLight implements Light
{
	private final Point position;
	private final Vector direction;
	private final double angle;
	private final double exp;
	private final RGB color;
	private final double cut_off;

	public SpotLight (final Point _position,
	                  final Vector _direction,
	                  final double _angle,
	                  final double _exp,
	                  final RGB _color)
	{
		position = _position;
		direction = _direction.normalize();
		angle = _angle;
		exp = _exp;
		color = _color;
		cut_off = Math.cos(angle);
	}

	public LightHit get_light_hit (final Point point)
	{
		final Vector difference = point.subtract(position);
		return new LightHit(difference, difference.length());
	}

	public RGB get_intensity (final LightHit light_hit)
	{
		final double between_angle = Vector.dot_product(light_hit.get_direction().normalize(), direction);

		if (between_angle < 0.0 || between_angle < cut_off)
		{
			return new RGB(0.0);
		}
		else
		{
			final double angular_strength = Math.pow(between_angle, exp);
			final double distance_weakness = light_hit.get_distance() * light_hit.get_distance();

			return color.multiply_scalar(angular_strength).divide_scalar(distance_weakness);
		}
	}

	public final void dump ()
	{
		System.out.println("Spot " + position + " " + color);
	}
}

