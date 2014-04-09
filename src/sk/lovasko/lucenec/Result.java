package sk.lovasko.lucenec;

public final class Result
{
	private final int start_x;
	private final int start_y;
	private final Image image;

	Result (final int _start_x, final int _start_y, final Image _image)
	{
		start_x = _start_x;
		start_y = _start_y;
		image = _image;
	}

	public final void put_to_image (final Image destination)
	{
		for (int x = 0; x < image.get_width(); x++)
		{
			for (int y = 0; y < image.get_height(); y++)
			{
				destination.set_color(x + start_x, y + start_y, image.get_color(x, y));
			}
		}
	}

	public String toString ()
	{
		return "result " + start_x + " " + start_y + " " + image;
	}
}

