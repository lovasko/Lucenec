package sk.lovasko.lucenec;

import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;

public final class Quad extends Solid
{
	private final Point point;
	private final Vector span1;
	private final Vector span2;

	private final Point p1, p2, p3, p4;
	private final Point center;
	private final Vector normal;
	private final double area;

	public Quad (
		final Point point, 
		final Vector span1, 
		final Vector span2)
	{
		this.point = point;
		this.span1 = span1;
		this.span2 = span2;

		p1 = point;
		p2 = point.move(span1);
		p3 = point.move(span1).move(span2);
		p4 = point.move(span2);
		center = p1
			.move(span1.multiply_scalar(0.5))
			.move(span2.multiply_scalar(0.5));

		bounding_box = BoundingBox.empty().extend(p1).extend(p3);

		normal = Vector.cross_product(span1, span2).normalize();
		area = Vector.cross_product(span1, span2).length();
	}

	public Quad (
		final Point point, 
		final Vector span1, 
		final Vector span2, 
		final Material material)
	{
		this(point, span1, span2);
		set_material(material);
	}

	public Quad (
		final Point point, 
		final Vector span1, 
		final Vector span2, 
		final CoordinateMapper mapper,
		final Material material)
	{
		this(point, span1, span2, material);
		set_coordinate_mapper(mapper);
	}

	public final BoundingBox get_bounds ()
	{
		return bounding_box;
	}

	public final Intersection intersect (Ray ray, double best)
	{
		final double denominator = Vector.dot_product(ray.get_direction(), normal);
		if (denominator == 0.0)
		{
			return Intersection.failure(); 
		}
    
		final double t = Vector.dot_product(center.subtract(ray.get_origin()), normal) / denominator;

		if (t > best || t < 0.0001)
		{
			return Intersection.failure();
		}

		Point surface_point = ray.get_point(t);

    final boolean test1 = 
			Vector.dot_product(
				Vector.cross_product(
					p2.subtract(p1), 
					surface_point.subtract(p1)), 
				normal) >= 0;

		final boolean test2 = 
			Vector.dot_product(
				Vector.cross_product(
					p3.subtract(p2), 
					surface_point.subtract(p2)), 
				normal) >= 0;

		final boolean test3 = 
			Vector.dot_product(
				Vector.cross_product(
					p4.subtract(p3), 
					surface_point.subtract(p3)), 
				normal) >= 0;

		final boolean test4 = 
			Vector.dot_product(
				Vector.cross_product(
					p1.subtract(p4), 
					surface_point.subtract(p4)), 
				normal) >= 0;

		if (test1 && test2 && test3 && test4)   
		{
			return new Intersection(ray, this, t, normal, surface_point, surface_point);
		}
		else
		{
			return Intersection.failure();
		}
	}

	public double get_area ()
	{
		return area;
	}

	public final Point get_sample()
	{
		final double r1 = Math.random();
		final double r2 = Math.random();

		return point
			.move(span1.multiply(r1))
			.move(span2.multiply(r2));
	}

	public final Vector get_normal_at_point (final Point point)
	{
		return normal;
	}

	public String toString ()
	{
		return "quad " + point + " " + span1 + " " + span2;
	}

	public static Quad read_from_xml_element (Element element)
	{
		Point point = XmlHelpers.get_point(element, "point");
		Vector span1 = XmlHelpers.get_vector(element, "span1");
		Vector span2 = XmlHelpers.get_vector(element, "span2");

		if (point == null || span1 == null || span2 == null)
		{
			return null;
		}
		else
		{
			return new Quad(point, span1, span2);
		}
	}
}

