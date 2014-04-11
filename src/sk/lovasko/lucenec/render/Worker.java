package sk.lovasko.lucenec.render;

import sk.lovasko.lucenec.color.RGB;
import sk.lovasko.lucenec.geom.Ray;
import sk.lovasko.lucenec.geom.Size;
import sk.lovasko.lucenec.image.Image;
import sk.lovasko.lucenec.integrator.Integrator;

import java.util.concurrent.Callable;

public final class Worker implements Callable<Result>
{
	private final int start_x;
	private final int start_y;
	private final int end_x;
	private final int end_y;
	private final int image_width;
	private final int image_height;
	private final int sample_count;
	private final Integrator integrator;

	Worker (final int start_x, 
					final int start_y, 
					final int end_x, 
					final int end_y, 
					final int image_width, 
					final int image_height,
					final int sample_count,
					final Integrator integrator)
	{
		this.start_x = start_x;
		this.start_y = start_y;
		this.end_x = end_x;
		this.end_y = end_y;
		this.image_width = image_width;
		this.image_height = image_height;
		this.sample_count = sample_count;
		this.integrator = integrator;
	}

	private final double convert_coordinates (
		final int value,
		final int max_value)
	{
		return ((((double)value + 0.5)/(double)max_value)*2.0)-1.0;
	}

	public final Result call ()
	{
		Image image = new Image(
			new Size(end_x - start_x + 1, end_y - start_y + 1));
		Result result = new Result(start_x, start_y, image);
		//RecursiveRayTracingIntegrator in = new RecursiveRayTracingIntegrator();
		//in.set_world(integrator.get_world());

		for (int x = start_x; x < end_x + 1; x++)
		for (int y = start_y; y < end_y + 1; y++)
		{
			RGB sum = new RGB(0.0);
			for (int i = 0; i < sample_count; i++)
			{
				final double rx = (Math.random() - 0.5) / image_width;
				final double ry = (Math.random() - 0.5) / image_height;
				final double nx = convert_coordinates(x, image_width) + rx;
				final double ny = convert_coordinates(y, image_height) + ry;

				final double time = Math.random();
				integrator.get_world().get_scene().set_time(time);

				Ray r = integrator.get_world().get_camera().get_primary_ray(nx, -ny);
				RGB c = integrator.get_radiance(r);
				//in.reset_depth();

				sum = sum.add(c);
			}

			image.set_color(x-start_x, y-start_y, sum.divide(sample_count));
		}
		System.out.println("Worker done.");

		return result;
	}
}
