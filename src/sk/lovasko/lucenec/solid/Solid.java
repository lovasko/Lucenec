package sk.lovasko.lucenec.solid;

import java.io.Serializable;
import sk.lovasko.lucenec.Primitive;
import sk.lovasko.lucenec.geom.Point;
import sk.lovasko.lucenec.geom.Vector;
import sk.lovasko.lucenec.geom.BoundingBox;
import sk.lovasko.lucenec.geom.Intersection;
import sk.lovasko.lucenec.geom.Ray;

public abstract class Solid extends Primitive implements Serializable
{
	public abstract double get_area();

	public Point get_sample()
	{
		return null;
	}

	public Vector get_normal_at_point (final Point point)
	{
		return null;
	}
}

