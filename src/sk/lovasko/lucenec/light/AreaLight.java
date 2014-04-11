package sk.lovasko.lucenec.light;

import sk.lovasko.lucenec.color.RGB;
import sk.lovasko.lucenec.solid.Solid;
import sk.lovasko.lucenec.geom.Point;
import sk.lovasko.lucenec.geom.Vector;

public final class AreaLight implements Light
{
	private final Solid solid;

	public AreaLight (final Solid solid)
	{
		this.solid = solid;
	}

	public final LightHit get_light_hit (final Point point)
	{
		final Point sample = solid.get_sample();
		final Vector difference = point.subtract(sample);

		return new LightHit(difference, difference.length());
	}

	public final RGB get_intensity (final LightHit light_hit)
	{
		return solid
			.get_material()
			.get_emission(new Point(0.0), new Vector(0.0), new Vector(0.0))
			.multiply_scalar(solid.get_area())
			.divide_scalar(light_hit.get_distance() * light_hit.get_distance());
	}

	public void dump ()
	{
		System.out.println("Area " + solid);
	}
}

