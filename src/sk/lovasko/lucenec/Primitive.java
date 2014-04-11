package sk.lovasko.lucenec;

import java.io.Serializable;
import sk.lovasko.lucenec.geom.BoundingBox;
import sk.lovasko.lucenec.geom.Intersection;
import sk.lovasko.lucenec.geom.Ray;
import sk.lovasko.lucenec.mapper.CoordinateMapper;
import sk.lovasko.lucenec.material.Material;

public abstract class Primitive implements Serializable
{
	protected Material material;
	protected CoordinateMapper coordinate_mapper;
	protected BoundingBox bounding_box;

	protected double time;

	public abstract BoundingBox get_bounds ();
	public abstract Intersection intersect (Ray ray, double best);

	public void set_material (final Material _material)
	{
		material = _material;
	}

	public Material get_material ()
	{
		return material;
	}

	public void set_coordinate_mapper (final CoordinateMapper coordinate_mapper)
	{
		this.coordinate_mapper = coordinate_mapper;
	}

	public CoordinateMapper get_coordinate_mapper ()
	{
		return coordinate_mapper;
	}

	public void set_time (final double time)
	{
		this.time = time;
	}
}

