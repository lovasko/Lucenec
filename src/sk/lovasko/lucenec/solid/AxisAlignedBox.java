package sk.lovasko.lucenec;

import sk.lovasko.lucenec.xml;

import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;

public final class AxisAlignedBox extends Solid
{
	private final Point near;
	private final Point far;

	AxisAlignedBox (Point _corner1, Point _corner2)
	{
		near = Point.min(_corner1, _corner2);
		far = Point.max(_corner1, _corner2);
		bounding_box = new BoundingBox(near, far);
	}

	public BoundingBox get_bounds ()
	{
		return bounding_box;
	}

	public Intersection intersect (Ray ray, double best)
	{
		double t_near = Double.MIN_VALUE;
		double t_far = Double.MAX_VALUE;

		if (Double.compare(ray.get_direction().get_x(), 0.0) == 0) 
		{
			if (ray.get_origin().get_x() < near.get_x() ||
			    ray.get_origin().get_x() > far.get_x())
				return Intersection.failure();
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
				t_near = t1;
			if (t2 < t_far)
				t_far = t2;
			if (t_near > t_far)
				return Intersection.failure();
			if (t_far < 0.0)
				return Intersection.failure();
		}

		if (Double.compare(ray.get_direction().get_y(), 0.0) == 0 &&
		    ray.get_origin().get_y() < near.get_y() &&
		    ray.get_origin().get_y() > far.get_y())
			return Intersection.failure();
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
				return Intersection.failure();
		}

		if (Double.compare(ray.get_direction().get_z(), 0.0) == 0 &&
		    ray.get_origin().get_z() < near.get_z() &&
		    ray.get_origin().get_z() > far.get_z())
			return Intersection.failure();
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
				return Intersection.failure();
		}

		double t = t_near;
		if (t > best)
			return Intersection.failure();

		Point hit_point = ray.get_point(t);
		Vector normal;
		if (Math.abs(hit_point.get_x() - near.get_x()) < 0.01)
			normal = new Vector(-1.0, 0.0, 0.0);
		else if (Math.abs(hit_point.get_x() - far.get_x()) < 0.01)
			normal = new Vector(1.0, 0.0, 0.0);
		else if (Math.abs(hit_point.get_y()- near.get_y()) < 0.01)
			normal = new Vector(0.0, -1.0, 0.0);
		else if (Math.abs(hit_point.get_y()- far.get_y()) < 0.01)
			normal = new Vector(0.0, 1.0, 0.0);
		else if (Math.abs(hit_point.get_z()- near.get_z()) < 0.01)
			normal = new Vector(0.0, 0.0, -1.0);
		else if (Math.abs(hit_point.get_z()- far.get_z()) < 0.01)
			normal = new Vector(0.0, 0.0, 1.0);
		else
		{
			System.out.println("hm");
			normal = new Vector(0.0, 0.0, 0.0);
		}
		return new Intersection(ray, this, t, normal, hit_point, null);
	}

	public double get_area ()
	{
		return 0.0;	
	}

	public String toString ()
	{
		return "axisAlignedBox " + near + " " + far;
	}

	public static AxisAlignedBox read_from_xml_element(Element element)
	{
		Point p1 = XmlHelpers.get_point(element, "p1");
		Point p2 = XmlHelpers.get_point(element, "p2");

		if (p1 == null || p2 == null)
		{
			return null;
		}
		else
		{
			return new AxisAlignedBox(p1, p2);
		}
	}
}

