package sk.lovasko.lucenec;

public final class RecursiveRayTracingIntegrator extends Integrator
{
	public RecursiveRayTracingIntegrator ()
	{
		depth = 0;
	}

	@Override
	public final RGB get_radiance (final Ray ray)
	{
		Intersection intersection = 
			world
				.get_scene()
				.intersect(ray, Double.MAX_VALUE);

		if (intersection.hit())
		{
			final Vector normal = intersection.get_normal().normalize();
			final Vector in_dir = ray.get_direction().negate().normalize();
			
			final Sampling sampling = 
				intersection
					.get_solid()
					.get_material()
					.get_sampling();

			RGB color = new RGB(0.0);

			if (sampling != Sampling.NOT_NEEDED)
			{
				final SampleReflectance sr = 
					intersection
					.get_solid()
					.get_material()
					.get_sample_reflectance(
						intersection.get_hit_point(), 
						intersection.get_normal(), 
						intersection.get_ray().get_direction().negate().normalize());

				Ray ray2 = new Ray(intersection.get_hit_point(), sr.get_direction());

				if (depth > 6)
					return color;

				depth++;

				color = color.add(get_radiance(ray2).multiply(sr.get_reflectance()));

				if (sampling == Sampling.ALL) 
				{
					return color;
				}
			}

			for (final Light light : world.get_lights())
			{
				final LightHit light_hit = 
					light.get_light_hit(intersection.get_hit_point());
				final Vector out_dir = light_hit.get_direction().negate().normalize();
				final Ray shade_ray = new Ray(intersection.get_hit_point(), out_dir);

				final double sgn1 = 
					Vector.dot_product(
						light_hit.get_direction().negate().normalize(), 
						intersection.get_normal().normalize());

				final double sgn2 = 
					Vector.dot_product(
						ray.get_direction().negate(), 
						intersection.get_normal().normalize());

				if (sgn1 < 0.0 && sgn2 > 0.0 || sgn1 > 0.0 && sgn2 < 0.0) 
				{
					continue;
				}

				final Intersection intersection2 = world
					.get_scene()
					.intersect(shade_ray, light_hit.get_distance() - 0.001);

				if (intersection2.hit())
				{
					continue;
				}

				final double cos_theta = 
					Math.abs(
						Vector.dot_product(
							light_hit.get_direction().normalize(), 
							intersection.get_normal().normalize()));

				final RGB irradiance = 
					light.get_intensity(light_hit).multiply_scalar(cos_theta)
						.multiply( 
							intersection
								.get_solid()
								.get_material()
								.get_reflectance(
									intersection.get_local(), 
									intersection.get_normal(), 
									out_dir, 
									in_dir))

						.add(intersection
							.get_solid()
							.get_material()
							.get_emission(
								intersection.get_local(), 
								intersection.get_normal(), 
								out_dir));

				color = color.add(irradiance);
			}

			return color;
		}
		else
		{
			return world.get_background_color();
		}
	}
}

