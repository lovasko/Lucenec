package sk.lovasko.lucenec.mapper;

import sk.lovasko.lucenec.geom.Intersection;
import sk.lovasko.lucenec.geom.Point;

public interface CoordinateMapper
{
	public Point get_coordinates (final Intersection intersection); 
}

