package sk.lovasko.lucenec.texture;

import sk.lovasko.lucenec.geom.Point;
import sk.lovasko.lucenec.geom.Size;

// TODO introduce Point2D
public enum BorderHandling
{
	CLAMP,
	MIRROR,
	REPEAT;

	public static final Point handle_clamp (
		final int _x, 
		final int _y,
		final Size size)
	{
		int x = _x;
		int y = _y;

		if (x < 0) 
			x = 0;
		if (y < 0) 
			y = 0;

		if (x >= size.get_width()) 
			x = size.get_width() - 1;
		if (y >= size.get_height()) 
			y = size.get_height() - 1;

		return new Point(x, y, 0.0);
	}
		
	public static final Point handle_mirror (
		final int _x,
		final int _y,
		final Size size)
	{
		int x = Math.abs(_x);
		int y = Math.abs(_y);

		int x_parity = x / size.get_width();
		int y_parity = y / size.get_height();

		x = x % size.get_width();
		if (x_parity % 2 != 0) x = size.get_width() - x;
		x = x % size.get_width();

		y = y % size.get_height();
		if (y_parity % 2 != 0) y = size.get_height() - y;
		y = y % size.get_height();

		return new Point(x, y, 0.0);
	}

	public static final Point handle_repeat (
		final int _x, 
		final int _y, 
		final Size size)
	{
		final int x = (int)Math.abs(_x % size.get_width());
		final int y = (int)Math.abs(_y % size.get_height());

		return new Point(x, y, 0.0);
	}
}

