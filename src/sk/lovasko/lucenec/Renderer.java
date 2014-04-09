package sk.lovasko.lucenec;

import java.util.List;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ExecutionException;

public final class Renderer
{
	public static final void render (
		final Integrator integrator, 
		final RenderSettings settings)
	{
		Image image = new Image(settings.get_image_size());

		if (settings.get_thread_count() < 1)
		{
			System.err.println("ERROR: thread count is less than one: " 
				+ settings.get_thread_count());
			return;
		}

		ExecutorService pool = Executors.newFixedThreadPool(settings.get_thread_count());
		List<Future<Result>> results = new ArrayList<Future<Result>>();
		List<Worker> tasks = new ArrayList<Worker>();

		final int tile_w = settings.get_tile_size().get_width();
		final int tile_h = settings.get_tile_size().get_height();

		for (int x = 0; x < image.get_width(); x += tile_w)
		for (int y = 0; y < image.get_height(); y += tile_h)
		{
			int end_x;
			int end_y;

			if (x + tile_w >= image.get_width())
			{
				end_x = image.get_width() - 1;
			}
			else
			{
				end_x = x + tile_w - 1;
			}

			if (y + tile_h >= image.get_height())
			{
				end_y = image.get_height() - 1;
			}
			else
			{
				end_y = y + tile_h - 1;
			}

			tasks.add(
				new Worker(
					x, 
					y, 
					end_x, 
					end_y, 
					image.get_width(), 
					image.get_height(), 
					settings.get_sample_count(), 
					integrator));
		}

		try
		{
			results = pool.invokeAll(tasks);
			for (Future<Result> future : results)
			{
				Result result = future.get();
				result.put_to_image(image);
			}
		}
		catch (InterruptedException|ExecutionException e)
		{
			System.err.println(e);
			e.printStackTrace();
		}

		pool.shutdown();

		image.write(settings.get_filename(), settings.get_image_format());
	}
}

