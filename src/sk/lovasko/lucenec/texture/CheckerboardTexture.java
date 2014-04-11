package sk.lovasko.lucenec.texture;

import sk.lovasko.lucenec.color.RGB;
import sk.lovasko.lucenec.geom.Point;

public final class CheckerboardTexture implements Texture
{
	private final RGB black;
	private final RGB white;
	private final double size;

	public CheckerboardTexture (final RGB black, final RGB white, final double size)
	{
		this.black = black;
		this.white = white;
		this.size = size;
	}

	public RGB get_color (final Point point)
	{
		final boolean x = (int)Math.floor(point.get_x() / size) % 2 == 0;
		final boolean y = (int)Math.floor(point.get_y() / size) % 2 == 0;
		final boolean z = (int)Math.floor(point.get_z() / size) % 2 == 0;

		return (x == y == z ? black : white);
	}
}

