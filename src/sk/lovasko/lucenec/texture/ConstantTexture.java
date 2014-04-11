package sk.lovasko.lucenec.texture;

import sk.lovasko.lucenec.color.RGB;
import sk.lovasko.lucenec.geom.Point;

public final class ConstantTexture implements Texture
{
	private final RGB color;

	public ConstantTexture (final RGB color)
	{
		this.color = color;
	}

	public final RGB get_color (final Point point)
	{
		return color;
	}
}

