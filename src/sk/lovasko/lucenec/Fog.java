package sk.lovasko.lucenec;

public final class Fog
{
	private final Texture texture;
	private final double density; 

	public Fog (final Texture texture, final double density)
	{
		this.texture = texture;
		this.density = density;
	}

	public final double get_intensity (final double distance)
	{
		return Math.exp(distance * density);
	}

	public final RGB get_color (final Point point)
	{
		return texture.get_color(point);
	}
}

