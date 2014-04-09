package sk.lovasko.lucenec;

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

