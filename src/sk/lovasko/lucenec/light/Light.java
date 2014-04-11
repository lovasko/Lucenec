package sk.lovasko.lucenec.light;

import sk.lovasko.lucenec.color.RGB;
import sk.lovasko.lucenec.geom.Point;
import sk.lovasko.lucenec.geom.Vector;

public interface Light
{
	public LightHit get_light_hit (final Point point);
	public RGB get_intensity (final LightHit light_hit);
	public void dump ();
}

