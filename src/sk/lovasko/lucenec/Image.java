package sk.lovasko.lucenec;

import javax.imageio.ImageIO;
import java.awt.Color;
import java.io.File;
import java.io.IOException;
import java.awt.image.BufferedImage;

public final class Image
{
	private final Size size;
	private final RGB[] data;

	public Image (final Size _size)
	{
		size = _size;
		data = new RGB[size.get_area()];

		for (int i = 0; i < size.get_area(); i++)
		{
			data[i] = new RGB(0.0);
		}
	}

	public final int get_width ()
	{
		return size.get_width();
	}

	public final int get_height ()
	{
		return size.get_height();
	}

	public final Size get_size ()
	{
		return size;
	}

	public static final String[] read_formats ()
	{
		return ImageIO.getReaderFormatNames();
	}

	public static final String[] write_formats ()
	{
		return ImageIO.getWriterFormatNames();
	}

	public final RGB get_color (final int x, final int y)
	{
		return data[x*size.get_height() + y];
	}

	public final RGB get_color (final double x, final double y)
	{
		return get_color((int)x, (int)y);
	}

	public final void set_color (final int x, final int y, final RGB rgb)
	{
		data[x*size.get_height() + y] = rgb;
	}

	private final Color convert_to_awt (final int x, final int y)
	{
		RGB rgb = get_color(x, y).clamp();
		return new Color(rgb.get_red_255(), rgb.get_green_255(), rgb.get_blue_255());
	}

	private static final RGB convert_from_awt (final Color color)
	{
		return new RGB(
			(double)(color.getRed()/255.0),
			(double)(color.getGreen()/255.0),
			(double)(color.getBlue()/255.0));
	}

	public final void write (final String filename, final String format)
	{
		final BufferedImage bi = new BufferedImage(
			size.get_width(), 
			size.get_height(), 
			BufferedImage.TYPE_INT_RGB);

		for (int x = 0; x < size.get_width(); x++)
		for (int y = 0; y < size.get_height(); y++)
		{
			Color color = convert_to_awt(x, y);
			bi.setRGB(x, y, color.getRGB()); 
		}

		try
		{
			ImageIO.write(bi, format, new File(filename));
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}
	
	public static final Image read (final String filename)
	{
		BufferedImage bi;

		try
		{
			bi = ImageIO.read(new File(filename));
		}
		catch (IOException e)
		{
			e.printStackTrace();
			return null;
		}

		Image image = new Image(new Size(bi.getWidth(), bi.getHeight()));
		for (int x = 0; x < image.get_width(); x++)
		for (int y = 0; y < image.get_height(); y++)
		{
			Color read_color = new Color(bi.getRGB(x, y));
			image.set_color(x, y, convert_from_awt(read_color));
		}

		return image;
	}

	public String toString ()
	{
		return "image: " + "\n\t" + size; 
	}
}

