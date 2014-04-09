package sk.lovasko.lucenec;

import java.util.List;
import java.util.ArrayList;

public final class KDTreeNode
{
	private KDTreeNode left;
	private KDTreeNode right;
	private List<Primitive> primitives;
	private BoundingBox bounding_box;
	private Axis split_axis; 
	private int depth;

	KDTreeNode ()
	{
		left = null;
		right = null;
		primitives = new ArrayList<Primitive>();
		bounding_box = BoundingBox.empty();
		split_axis = Axis.X;
	}

	public void set_split_axis (Axis _split_axis)
	{
		split_axis = _split_axis;
	}

	public Axis get_split_axis ()
	{
		return split_axis;	
	}

	public void set_depth (int _depth)
	{
		depth = _depth;
	}

	public int get_depth ()
	{
		return depth;
	}

	public void set_bounding_box (BoundingBox _bounding_box)
	{
		bounding_box = new BoundingBox(_bounding_box);	
	}

	public void set_bounding_box (Point p1, Point p2)
	{
		bounding_box = new BoundingBox(p1, p2);	
	}

	public BoundingBox get_bounding_box ()
	{
		return bounding_box;
	}

	public void set_primitives (final List<Primitive> _primitives)
	{
		primitives.clear();
		for (Primitive primitive : _primitives)
		{
			primitives.add(primitive);
		}
	}

	public List<Primitive> get_primitives ()
	{
		return primitives;
	}

	public final boolean is_leaf ()
	{
		if (left == null && right == null)
		{
			return true;
		}
		else
		{
			return false;
		}
	}

	public final double get_min_in_split_axis ()
	{
		switch (split_axis)
		{
			case X:
				return bounding_box.get_min().get_x();

			case Y:
				return bounding_box.get_min().get_y();

			case Z:
				return bounding_box.get_min().get_z();

			default:
				return 0.0;
		}
	}

	public final double get_max_in_split_axis ()
	{
		switch (split_axis)
		{
			case X:
				return bounding_box.get_max().get_x();

			case Y:
				return bounding_box.get_max().get_y();

			case Z:
				return bounding_box.get_max().get_z();

			default:
				return 0.0;
		}
	}

	public void set_left (KDTreeNode _left)
	{
		left = _left;
	}

	public void set_right (KDTreeNode _right)
	{
		right = _right;
	}

	public void add_primitive (Primitive primitive)
	{
		primitives.add(primitive);
	}

	public boolean has_left ()
	{
		if (left != null)
		{
			return true;
		}
		else
		{
			return false;
		}
	}

	public boolean has_right ()
	{
		if (right != null)
		{
			return true;
		}
		else
		{
			return false;
		}
	}

	public boolean has_any_primitives ()
	{
		if (primitives.isEmpty())
		{
			return false;	
		}
		else
		{
			return true;	
		}
	}

	public int get_primitive_count ()
	{
		return primitives.size();
	}

	public KDTreeNode get_left ()
	{
		return left;
	}

	public KDTreeNode get_right ()
	{
		return right;
	}

	public String toString ()
	{
		return "kdTreeNode " + (is_leaf() ? "leaf" : "node") + " " + get_primitive_count();
	}
}

