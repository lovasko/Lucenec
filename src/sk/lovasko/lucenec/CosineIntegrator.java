package sk.lovasko.lucenec;

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

/*			System.out.println(intersection.get_solid());
			return intersection.
			 get_solid().
			 get_material().
			 get_emission(
				intersection.get_hit_point(), 
				intersection.get_normal(), 
				ray.get_direction())
			.multiply_scalar(Math.abs(cosine)); 
			*/return new RGB(Math.abs(cosine));
		}
		else
		{
			return background_color;
		}
	}
}

