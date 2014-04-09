package sk.lovasko.lucenec;

public final class RenderSettings
{
	private final Size image_size;
	private final String filename;
	private final int thread_count;
	private final Size tile_size;
	private final int sample_count;

	public RenderSettings (
		final Size image_size,
	  final String filename,
	  final int thread_count,
	  final Size tile_size,
		final int sample_count)
	{
		this.image_size = image_size;
		this.filename = filename;
		this.thread_count = thread_count;
		this.tile_size = tile_size;
		this.sample_count = sample_count;
	}

	public final Size get_image_size ()
	{
		return image_size;
	}

	public final String get_filename ()
	{
		return filename;
	}

	public final int get_thread_count ()
	{
		return thread_count;
	}

	public final Size get_tile_size ()
	{
		return tile_size;
	}

	public final String get_image_format ()
	{
		int dot_index = filename.indexOf(".");
		if (dot_index > 0)
		{
			return filename.substring(dot_index + 1);
		}
		else
		{
			return "png";
		}
	}

	public final int get_sample_count ()
	{
		return sample_count;
	}

	public String toString ()
	{
		return "renderSettings: " + 
			"\n\t" + image_size + 
			"\n\tfilename " + filename + 
			"\n\tthread_count " + thread_count + 
			"\n\t" + tile_size; 
	}
}

