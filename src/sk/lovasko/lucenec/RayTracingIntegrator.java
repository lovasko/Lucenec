package sk.lovasko.lucenec;

public final class RayTracingIntegrator extends Integrator
{
	public final RGB get_radiance (final Ray ray)
	{
		final Intersection intersection = 
			get_world().
			get_scene().
			intersect(ray, Double.MAX_VALUE);

		if (intersection.hit())
		{
			RGB color = new RGB(0.0);
			CoordinateMapper mapper = 
				intersection.
				get_solid().
				get_coordinate_mapper();

			Point texture_coordinates = mapper.get_coordinates(intersection);
			
			for (final Light light : get_world().get_lights())
			{
				final LightHit light_hit = light.get_light_hit(intersection.get_hit_point());

				final double sgn1 = Vector.dot_product(
					light_hit.get_direction().normalize().negate(), 
					intersection.get_normal());
				final double sgn2 = Vector.dot_product(
					ray.get_direction().negate(),
					intersection.get_normal());
				if (sgn1 < 0.0 && sgn2 > 0.0 || sgn1 > 0.0 && sgn2 < 0.0) 
				{
					continue;
				}

				final Ray shade_ray = new Ray(intersection.get_hit_point(), light_hit.get_direction().normalize().negate());
				Intersection intersection2 = get_world().get_scene().intersect(shade_ray, light_hit.get_distance() - 0.000001);
				if (intersection2.hit())
				{
					continue;
				}

				final double alpha = 
					Math.abs(
						Vector.dot_product(
							light_hit.get_direction().normalize(), 
							intersection.get_normal()));

				RGB irradiance = 
					light.
					get_intensity(light_hit).
					multiply_scalar(alpha).
					multiply(
						intersection.
						get_solid().
						get_material().
						get_reflectance(
							texture_coordinates, 
							intersection.get_normal(), 
							light_hit.get_direction().normalize().negate(), 
							ray.get_direction().normalize())
					);

				color = color.add(irradiance);
			}

			return color;
		}
		else
		{
			if (get_world().get_environment_texture() == null)
				return get_world().get_background_color();
			else
			{
				EnvironmentalCoordinateMapper ecm = new EnvironmentalCoordinateMapper(new Vector(0.0, 1.0, 0.0), new Vector(1.0, 0.0, 1.0));
				Point point = ecm.get_coordinates(new Intersection(ray, null, Double.MAX_VALUE, new Vector(0.0), new Point(0.0), new Point(0.0)));
				return get_world().get_environment_texture().get_color(point);
			}
		}
	}
}

