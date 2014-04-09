package sk.lovasko.lucenec;

import sk.lovasko.lucenec.xml;

import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;

public final class InfinitePlane extends Solid
{
	private final Point center;
	private final Vector normal;

	public InfinitePlane (final Point _center, final Vector _normal)
	{
		center = _center;
		normal = _normal;
		bounding_box = BoundingBox.full();
	}

	public InfinitePlane (final Point center, final Vector normal, final Material material)
	{
		this(center, normal);
		set_material(material);
	}

	public InfinitePlane (final Point center, final Vector normal, final Material material, final CoordinateMapper mapper)
	{
		this(center, normal);
		set_coordinate_mapper(mapper);
		set_material(material);
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
		double distance = hit_point.subtract(ray.get_origin()).length();
		return new Intersection(ray, this, t, normal, hit_point, hit_point);
	}

	public double get_area ()
	{
		return Double.MAX_VALUE; 
	}

	public String toString ()
	{
		return "infinitePlane " + center + " " + normal;
	}

	public static InfinitePlane read_from_xml_element (Element element)
	{
		Point point = XmlHelpers.get_point(element, "point");
		Vector normal = XmlHelpers.get_vector(element, "normal");

		if (point == null || normal == null)
		{
			return null;
		}
		else
		{
			return new InfinitePlane(point, normal);
		}
	}
}

