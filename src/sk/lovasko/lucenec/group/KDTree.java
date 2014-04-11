package sk.lovasko.lucenec.group;

import java.util.ArrayList;
import java.util.Stack;
import sk.lovasko.lucenec.Primitive;
import sk.lovasko.lucenec.geom.BoundingBox;
import sk.lovasko.lucenec.geom.Point;
import sk.lovasko.lucenec.geom.Ray;
import sk.lovasko.lucenec.geom.Intersection;
import sk.lovasko.lucenec.geom.PointPair;

public final class KDTree extends Group
{
	private KDTreeNode root;
	private int min_primitives;
	private int max_depth;

	private Axis next_axis (Axis current)
	{
		switch (current)
		{
			case X:
				return Axis.Y;

			case Y:
				return Axis.Z;

			case Z:
				return Axis.X;

			default:
				return Axis.X;
		}
	}

	private PointPair get_border_points (KDTreeNode node, double coordinate)
	{
		Point first = new Point(node.get_bounding_box().get_max());
		Point second = new Point(node.get_bounding_box().get_min());
		
		switch (node.get_split_axis())
		{
			case X:
				first.set_x(coordinate);
				second.set_x(coordinate);
			break;

			case Y:
				first.set_y(coordinate);
				second.set_y(coordinate);
			break;

			case Z:
				first.set_z(coordinate);
				second.set_z(coordinate);
			break;
		}
		
		return new PointPair(first, second);
	}
	
	public KDTree ()
	{
		primitives = new ArrayList<Primitive>(); 
		bounding_box = BoundingBox.empty();	
		max_depth = 20;
		min_primitives = 3;
		root = null;
	}

	@Override
	public BoundingBox get_bounds ()
	{
		return bounding_box;
	}

	@Override
	public final Intersection intersect (final Ray ray, final double _best)
	{
		if (root.get_bounding_box().intersect(ray).fail())
		{
			return Intersection.failure();	
		}

		double best = _best;
		Intersection best_intersection = null;

		Stack<KDTreeNode> stack = new Stack<KDTreeNode>();				
		stack.push(root);

		while(!stack.empty())
		{
			KDTreeNode node = stack.pop();
			if (node.is_leaf())
			{
				for (final Primitive primitive : node.get_primitives())
				{
					if (primitive.get_bounds().intersect(ray).fail())
					{
						continue;
					}

					Intersection intersection = primitive.intersect(ray, best);
					if (intersection.hit())
					{
						if (primitive.get_bounds().contains_point(intersection.get_hit_point()))
						{
							best = intersection.get_distance();
							best_intersection = intersection; 
						}
					}
				}
			}
			else
			{
				if (node.has_left() && node.get_left().get_bounding_box().intersect(ray).success())
				{
					stack.push(node.get_left());		
				}

				if (node.has_right() && node.get_right().get_bounding_box().intersect(ray).success())
				{
					stack.push(node.get_right());		
				}
			}
		}

		if (best_intersection == null)
		{
			return Intersection.failure();	
		}
		else
		{	
			return best_intersection;
		}
	}

	@Override
	public final void rebuild_index ()
	{
		Stack<KDTreeNode> stack = new Stack<KDTreeNode>();	
		root = new KDTreeNode();	
		root.set_depth(0);
		root.set_split_axis(Axis.X);
		root.set_bounding_box(get_bounds());
		root.set_primitives(primitives);

		stack.push(root);
		while (!stack.empty())
		{
			KDTreeNode node = stack.pop();			

			// check for terminating due to depth
			if (node.get_depth() >= max_depth)
			{
				continue;
			}

			// check for terminating due to number of primitives
			if (node.get_primitive_count() <= min_primitives)
			{
				continue;
			}

			double split_coordinate = 0.0;
			switch (node.get_split_axis())
			{
				case X:
					split_coordinate = (node.get_bounding_box().get_min().get_x() + 
					                    node.get_bounding_box().get_max().get_x()) / 2;
				break;

				case Y:
					split_coordinate = (node.get_bounding_box().get_min().get_y() + 
					                    node.get_bounding_box().get_max().get_y()) / 2;
				break;

				case Z:
					split_coordinate = (node.get_bounding_box().get_min().get_z() + 
					                    node.get_bounding_box().get_max().get_z()) / 2;
				break;
			}
			
			PointPair pair = get_border_points(node, split_coordinate);

			Point first_min = node.get_bounding_box().get_min();
			Point first_max = pair.get_first();
			Point second_min = pair.get_second();
			Point second_max = node.get_bounding_box().get_max();

			KDTreeNode left = new KDTreeNode();  
			KDTreeNode right = new KDTreeNode();  

			left.set_split_axis(next_axis(node.get_split_axis()));
			right.set_split_axis(next_axis(node.get_split_axis()));

			left.set_depth(node.get_depth() + 1);
			right.set_depth(node.get_depth() + 1);

			left.set_bounding_box(first_min, first_max);
			right.set_bounding_box(second_min, second_max);

			for (final Primitive primitive : node.get_primitives())
			{
				double min_coord = 0.0;
				double max_coord = 0.0;

				switch (node.get_split_axis())
				{
					case X:
						min_coord = primitive.get_bounds().get_min().get_x();
						max_coord = primitive.get_bounds().get_max().get_x();
					break;

					case Y:
						min_coord = primitive.get_bounds().get_min().get_y();
						max_coord = primitive.get_bounds().get_max().get_y();
					break;

					case Z:
						min_coord = primitive.get_bounds().get_min().get_z();
						max_coord = primitive.get_bounds().get_max().get_z();
					break;
				}

				if (min_coord < split_coordinate)
				{
					left.add_primitive(primitive);	
				}

				if (max_coord > split_coordinate)
				{
					right.add_primitive(primitive);	
				}
			}

			if (left.has_any_primitives())
			{
				stack.push(left);	
				node.set_left(left);
			}

			if (right.has_any_primitives())
			{
				stack.push(right);	
				node.set_right(right);
			}
		}
	}

	@Override
	public final void add (Primitive primitive)
	{
		primitives.add(primitive);
		bounding_box = bounding_box.extend(primitive.get_bounds());	
	}

	@Override
	public final String toString()
	{
		String representation = "kdTree: (" + primitives.size() + ")"; 
/*		for (final Primitive primitive : primitives)
		{
			representation += "\n\t" + primitive.toString();
		}
		*/

		return representation;
	}
}

