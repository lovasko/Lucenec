package sk.lovasko.lucenec.integrator;

import sk.lovasko.lucenec.color.RGB;
import sk.lovasko.lucenec.geom.Vector;
import sk.lovasko.lucenec.geom.Ray;
import sk.lovasko.lucenec.world.World;
import sk.lovasko.lucenec.geom.Intersection;

public final class CosineIntegrator extends Integrator
{
	public final RGB get_radiance (final Ray ray)
	{
		final RGB background_color = world.get_background_color();
		final Intersection intersection = world.
			get_scene().
			intersect(ray, Double.MAX_VALUE);

		if (intersection.hit())
		{
			final double cosine = 
				Vector.dot_product(
					ray.get_direction().normalize(), 
					intersection.get_normal().normalize());

			return new RGB(Math.abs(cosine));
		}
		else
		{
			return background_color;
		}
	}
}

