package sk.lovasko.lucenec;

public final class ImageTexture implements Texture 
{
	private final Image image;
	private final BorderHandling border_handling;
	private final Interpolation interpolation;

	public ImageTexture (
		final String filename, 
		final BorderHandling border_handling, 
		final Interpolation interpolation)
	{
		this.border_handling = border_handling;
		this.interpolation = interpolation;
		image = Image.read(filename);
	}

	public final RGB get_color (final Point point)
	{
		//point.dump();

		final double x = point.get_x() * image.get_size().get_width();
		final double y = point.get_y() * image.get_size().get_height();
		double dx;
		double dy;
		int ix;
		int iy;

		switch (interpolation)
		{
			case NEAREST:
				ix = (int)Math.floor(x);
				dx = Common.fractional(x);
				if (dx >= 0.5) 
					ix++;

				iy = (int)Math.floor(y);
				dy = Common.fractional(y);
				if (dy >= 0.5) 
					iy++;

				final Point texture_point = get_texture_point(ix, iy);
				return image.get_color(texture_point.get_x(), texture_point.get_y());

			case BILINEAR:
				ix = (int)Math.floor(x);
				iy = (int)Math.floor(y);

				dx = Common.abs_fractional(x);
				dy = Common.abs_fractional(y);

				final Point p00 = get_texture_point(ix,     iy);
				final Point p01 = get_texture_point(ix,     iy + 1);
				final Point p10 = get_texture_point(ix + 1, iy);
				final Point p11 = get_texture_point(ix + 1, iy + 1);

				final RGB c00 = image.get_color(p00.get_x(), p00.get_y());
				final RGB c01 = image.get_color(p01.get_x(), p01.get_y());
				final RGB c10 = image.get_color(p10.get_x(), p10.get_y());
				final RGB c11 = image.get_color(p11.get_x(), p11.get_y());
			
				return RGB.lerp2d(c00, c10, c01, c11, dx, dy);

			default:
				return new RGB(0.0);
		}
	}

	private final Point get_texture_point (final int x, final int y)
	{
		switch (border_handling)
		{
			case CLAMP:
				return BorderHandling.handle_clamp(x, y, image.get_size());

			case MIRROR:
				return BorderHandling.handle_mirror(x, y, image.get_size());

			case REPEAT:
				return BorderHandling.handle_repeat(x, y, image.get_size()); 

			default:
				return new Point(0.0);
		}
	}
}

