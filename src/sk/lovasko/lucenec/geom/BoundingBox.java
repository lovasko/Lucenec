package sk.lovasko.lucenec;

import java.io.Serializable;

public final class BoundingBox implements Serializable
{
	private final Point min;
	private final Point max;
	private SizeType size_type;

	BoundingBox (final Point p1, final Point p2)
	{
		if (p1 != null && p2 != null)
		{
			min = Point.min(p1, p2);
			max = Point.max(p1, p2);
		}
		else
		{
			min = null;
			max = null;
		}

		size_type = SizeType.NORMAL;
	}

	BoundingBox (BoundingBox bounding_box)
	{
		min = new Point(bounding_box.get_min());
		max = new Point(bounding_box.get_max());
		size_type = bounding_box.get_size_type();
	}

	public static BoundingBox empty ()
	{
		BoundingBox bounding_box = new BoundingBox(null, null);
		bounding_box.size_type = SizeType.EMPTY;

		return bounding_box;
	}

	public static BoundingBox full ()
	{
		BoundingBox bounding_box = new BoundingBox(new Point(0.0), new Point(0.0));
		bounding_box.size_type = SizeType.FULL;

		return bounding_box;
	}

	public final BoundingBox extend (Point point)
	{
		switch (size_type)
		{
			case EMPTY:
				return new BoundingBox(point, point);

			case FULL:
				return BoundingBox.full();

			case NORMAL:
				return new BoundingBox(Point.min(min, point), Point.max(max, point));

			default:
				return null;
		}
	}

	public final BoundingBox extend (BoundingBox bounding_box)
	{
		switch (size_type)
		{
			case FULL:
				return BoundingBox.full();

			case EMPTY:
				switch (bounding_box.get_size_type())
				{
					case FULL:
						return BoundingBox.full();

					case EMPTY:
						return BoundingBox.empty();

					case NORMAL:
						return new BoundingBox(bounding_box);
				}

			case NORMAL:
				switch (bounding_box.get_size_type())
				{
					case FULL:
						return BoundingBox.full();

					case EMPTY:
						return new BoundingBox(this);

					case NORMAL:
						return new BoundingBox(Point.min(min, bounding_box.get_min()), 
						                       Point.max(max, bounding_box.get_max()));
				}

			default:
				return null;
		}
	}

	public final SizeType get_size_type ()
	{
		return size_type;
	}

	public final Point get_min ()
	{
		return min;
	}

	public final Point get_max ()
	{
		return max;
	}

	public String toString ()
	{
		return "boundingBox " + min + " " + max;
	}

	public final Vector get_diagonal ()
	{
		return Point.subtract(min, max);
	}

	public final boolean is_unbounded ()
	{
		if (size_type == SizeType.FULL)
		{
			return true;
		}
		else if (size_type == SizeType.EMPTY)
		{
			return false;
		}
		else if (Double.compare(min.get_x(), Double.MAX_VALUE) == 0 || 
		         Double.compare(min.get_y(), Double.MAX_VALUE) == 0 ||
		         Double.compare(min.get_z(), Double.MAX_VALUE) == 0 || 
		         Double.compare(max.get_x(), Double.MAX_VALUE) == 0 || 
		         Double.compare(max.get_y(), Double.MAX_VALUE) == 0 || 
		         Double.compare(max.get_z(), Double.MAX_VALUE) == 0 ||   
		         Double.compare(min.get_x(), Double.MIN_VALUE) == 0 ||
		         Double.compare(min.get_y(), Double.MIN_VALUE) == 0 ||
		         Double.compare(min.get_z(), Double.MIN_VALUE) == 0 ||
		         Double.compare(max.get_x(), Double.MIN_VALUE) == 0 ||
		         Double.compare(max.get_y(), Double.MIN_VALUE) == 0 ||
		         Double.compare(max.get_z(), Double.MIN_VALUE) == 0)  
		{
			return true;
		}
		else
		{
			return false;
		}
	}

	public final IntersectionPair intersect (Ray ray)
	{
		double t_near = Double.MIN_VALUE;
		double t_far = Double.MAX_VALUE;
		Point near = min;
		Point far = max;

		if (Double.compare(ray.get_direction().get_x(), 0.0) == 0) 
		{
			if (ray.get_origin().get_x() < near.get_x() ||
			    ray.get_origin().get_x() > far.get_x())
			{
				// failure
				return new IntersectionPair(1.0, 0.0); 
			}
		}
		else
		{
			double t1 = (near.get_x() - ray.get_origin().get_x()) / ray.get_direction().get_x();
			double t2 = (far.get_x() - ray.get_origin().get_x()) / ray.get_direction().get_x();
			if (t1 > t2)
			{
				double temp = t1;
				t1 = t2;
				t2 = temp;
			}

			if (t1 > t_near)
			{
				t_near = t1;
			}

			if (t2 < t_far)
			{
				t_far = t2;
			}

			if (t_near > t_far)
			{
				// failure
				return new IntersectionPair(1.0, 0.0); 
			}

			if (t_far < 0.0)
			{
				// failure
				return new IntersectionPair(1.0, 0.0); 
			}
		}

		if (Double.compare(ray.get_direction().get_y(), 0.0) == 0 &&
		    ray.get_origin().get_y() < near.get_y() &&
		    ray.get_origin().get_y() > far.get_y())
		{
				// failure
				return new IntersectionPair(1.0, 0.0); 
		}
		else
		{
			double t1 = (near.get_y() - ray.get_origin().get_y()) / ray.get_direction().get_y();
			double t2 = (far.get_y() - ray.get_origin().get_y()) / ray.get_direction().get_y();
			if (t1 > t2)
			{
				double temp = t1;
				t1 = t2;
				t2 = temp;
			}

			if (t1 > t_near)
				t_near = t1;

			if (t2 < t_far)
				t_far = t2;

			if ((t_near > t_far) || (t_far < 0.0))
			{
				// failure
				return new IntersectionPair(1.0, 0.0);
			}
		}

		if (Double.compare(ray.get_direction().get_z(), 0.0) == 0 &&
		    ray.get_origin().get_z() < near.get_z() &&
		    ray.get_origin().get_z() > far.get_z())
		{
			// failure
			return new IntersectionPair(1.0, 0.0);
		}
		else
		{
			double t1 = (near.get_z() - ray.get_origin().get_z()) / ray.get_direction().get_z();
			double t2 = (far.get_z() - ray.get_origin().get_z()) / ray.get_direction().get_z();
			if (t1 > t2)
			{
				double temp = t1;
				t1 = t2;
				t2 = temp;
			}

			if (t1 > t_near)
				t_near = t1;

			if (t2 < t_far)
				t_far = t2;

			if ((t_near > t_far) || (t_far < 0.0))
			{
				// failure
				return new IntersectionPair(1.0, 0.0);
			}
		}

		return new IntersectionPair(t_near, t_far);
	/*	
			if (size_type == SizeType.EMPTY)
			{
				return new IntersectionPair(1.0, 0.0);
			}
			else if (size_type == SizeType.FULL)
			{
				return new IntersectionPair(0.0, 0.0);			
			}

			if (ray.get_direction().get_x() == 0.0)
			{
				if (ray.get_origin().get_x() < min.get_x() || 
				    ray.get_origin().get_x() > max.get_x())
				{
					return new IntersectionPair(1.0, 0.0);
				}
			}

			if (ray.get_direction().get_y() == 0.0)
			{
				if (ray.get_origin().get_y() < min.get_y() || 
				    ray.get_origin().get_y() > max.get_y())
				{
					return new IntersectionPair(1.0, 0.0);
				}
			}

			if (ray.get_direction().get_z() == 0.0)
			{
				if (ray.get_origin().get_z() < min.get_z() || 
				    ray.get_origin().get_z() > max.get_z())
				{
					return new IntersectionPair(1.0, 0.0);
				}
			}

			double t0x = (min.get_x() - ray.get_origin().get_x()) / ray.get_direction().get_x();
			double t1x = (max.get_x() - ray.get_origin().get_x()) / ray.get_direction().get_x();
			if (t0x > t1x) 
			{
				double temp = t1x;
				t1x = t0x;
				t0x = t1x;
			}

			double t0y = (min.get_y() - ray.get_origin().get_y()) / ray.get_direction().get_y();
			double t1y = (max.get_y() - ray.get_origin().get_y()) / ray.get_direction().get_y();
			if (t0y > t1y) 
			{
				double temp = t1y;
				t1y = t0y;
				t0y = t1y;
			}

			double t0z = (min.get_z() - ray.get_origin().get_z()) / ray.get_direction().get_z();
			double t1z = (max.get_z() - ray.get_origin().get_z()) / ray.get_direction().get_z();
			if (t0z > t1z) 
			{
				double temp = t1z;
				t1z = t0z;
				t0z = t1z;
			}

			double maxt0 = Math.max(Math.max(t0x, t0y), t0z);
			double mint1 = Math.min(Math.min(t1x, t1y), t1z);
			return new IntersectionPair(maxt0, mint1); 
			*/
	}

	public final boolean contains_point (final Point point)
	{
		if (point.get_x() > min.get_x() && point.get_x() < max.get_x() &&
		    point.get_y() > min.get_y() && point.get_y() < max.get_y() &&
				point.get_z() > min.get_z() && point.get_z() < max.get_z())
		{
			return true;
		}
		else
		{
			return false;
		}
	}
}

