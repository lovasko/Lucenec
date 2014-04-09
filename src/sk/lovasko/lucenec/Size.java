package sk.lovasko.lucenec;

public final class Size
{
	private final int width;
	private final int height;

	public Size (final int _width,
	             final int _height)
	{
		width = _width;
		height = _height;
	}

	public final int get_width ()
	{
		return width;
	}

	public final int get_height ()
	{
		return height;
	}

	public final int get_area ()
	{
		return width * height;
	}

	public String toString ()
	{
		return "size: " + 
			"\n\t" + width +
			"\n\t" + height;
	}
}

