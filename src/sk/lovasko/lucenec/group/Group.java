package sk.lovasko.lucenec.group;

import sk.lovasko.lucenec.Primitive;
import sk.lovasko.lucenec.material.Material;
import sk.lovasko.lucenec.mapper.CoordinateMapper;

import java.util.List;

public abstract class Group extends Primitive 
{
	protected List<Primitive> primitives;

	public abstract void add (Primitive primitive);
	public abstract void rebuild_index ();

	public final List<Primitive> get_primitives ()
	{
		return primitives;
	}

	public final void set_time (final double time)
	{
		this.time = time;

		for (final Primitive primitive : primitives)
		{
			primitive.set_time(time);
		}
	}

	public void set_material (final Material material)
	{
		for (final Primitive primitive : primitives)
		{
			primitive.set_material(material);
		}
	}

	public void set_coordinate_mapper (final CoordinateMapper mapper)
	{
		for (final Primitive primitive : primitives)
		{
			primitive.set_coordinate_mapper(mapper);
		}
	}
}

