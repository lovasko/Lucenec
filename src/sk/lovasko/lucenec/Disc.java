package sk.lovasko.lucenec;

import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;

public final class Disc extends Solid
{
	private Point center;
	private Vector normal;
	private double radius;
	private final double radius2;

	Disc (Point _center, Vector _normal, double _radius)
	{
		center = _center;
		normal = _normal;
		radius = _radius;

		bounding_box = new BoundingBox(center.move(new Vector(-radius, -radius, -radius)),
		                               center.move(new Vector( radius,  radius,  radius)));

		radius2 = radius * radius;
	}

	public BoundingBox get_bounds ()
	{
		return bounding_box;
	}

	public Intersection intersect (Ray ray, double best)
	{
		double d = Vector.dot_product(normal, ray.get_direction());

		if (Double.compare(d, 0.0) == 0)
			return Intersection.failure();

		double t = - Vector.dot_product(normal, ray.get_origin().subtract(center)) / d;	

		if (t <= 0 || t >= best)
			return Intersection.failure();

		Point hit_point = ray.get_point(t);
		Vector difference = hit_point.subtract(center);

		if (Vector.dot_product(difference, difference) > radius2)
			return Intersection.failure();	

		double distance = hit_point.subtract(ray.get_origin()).length();
		return new Intersection(ray, this, t, normal, hit_point, null);     		
	}

	public double get_area ()
	{
		return 0.0;	
	}

	public String toString ()
	{
		return "disc " + center + " " + normal + " " + radius;
	}

	public static Disc read_from_xml_element (Element element)
	{
		Point center = XmlHelpers.get_point(element, "center");
		Vector normal = XmlHelpers.get_vector(element, "normal");
		Double radius = XmlHelpers.get_double(element, "radius");

		if (center == null || normal == null || radius == null)
		{
			return null;
		}
		else
		{
			return new Disc(center, normal, radius);
		}
	}
}

