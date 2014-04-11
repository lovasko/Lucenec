package sk.lovasko.lucenec.texture;

import sk.lovasko.lucenec.color.RGB;
import sk.lovasko.lucenec.geom.Point;

public interface Texture 
{
	public RGB get_color (final Point point);
}

