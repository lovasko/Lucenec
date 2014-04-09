package sk.lovasko.lucenec;

import java.util.ArrayList;

public final class SimpleGroup extends Group
{
	SimpleGroup ()
	{
		primitives = new ArrayList<Primitive>(); 
		bounding_box = BoundingBox.empty();
	}

	public BoundingBox get_bounds ()
	{
		return bounding_box;
	}

	public Intersection intersect (Ray ray, double _best)
	{
		double best = _best;
		Intersection result = Intersection.failure();
		for (Primitive primitive : primitives)
		{
			Intersection intersection = primitive.intersect(ray, best);
			if (intersection.hit())
			{
				result = intersection;
				best = intersection.get_distance();
			}
		}

		return result;
	}

	public void rebuild_index ()
	{
		BoundingBox bb = new BoundingBox(null, null);
		for (final Primitive primitive : primitives)
		{
			bb = bb.extend(primitive.get_bounds());
		}

		bounding_box = bb;
	}

	public void add (Primitive primitive)
	{
		primitives.add(primitive);
		bounding_box = bounding_box.extend(primitive.get_bounds());
	}

	public String toString()
	{
		String representation = "simpleGroup: (" + primitives.size() + ")"; 
		for (final Primitive primitive : primitives)
		{
			representation += "\n\t" + primitive.toString();
		}

		return representation;
	}	
}

