package sk.lovasko.lucenec.solid;

import sk.lovasko.lucenec.xml.XmlHelpers;
import sk.lovasko.lucenec.geom.Point;
import sk.lovasko.lucenec.geom.Vector;
import sk.lovasko.lucenec.geom.BoundingBox;
import sk.lovasko.lucenec.geom.Ray;
import sk.lovasko.lucenec.geom.Intersection;
import sk.lovasko.lucenec.material.Material;
import sk.lovasko.lucenec.mapper.CoordinateMapper;

import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;
import java.io.Serializable;

public class Triangle extends Solid implements Serializable
{
	protected final Point p1;
	protected final Point p2;
	protected final Point p3;
	private final Vector normal;

	public Triangle (final Point p1, final Point p2, final Point p3)
	{
		this.p1 = p1;
		this.p2 = p2;
		this.p3 = p3;

		final Vector edge1 = p2.subtract(p1).normalize();
		final Vector edge2 = p3.subtract(p1).normalize();
		normal = Vector.cross_product(edge1, edge2).normalize();

		bounding_box = BoundingBox.empty().extend(p1).extend(p2).extend(p3);
	}

	public Triangle (
		final Point p1, 
		final Point p2, 
		final Point p3, 
		final CoordinateMapper coordinate_mapper, 
		final Material material)
	{
		this(p1, p2, p3);
		set_coordinate_mapper(coordinate_mapper);
		set_material(material);
	}

	public BoundingBox get_bounds ()
	{
		return bounding_box;
	}

	public final Intersection intersect (final Ray ray, final double best)
	{
		final double denominator = Vector.dot_product(ray.get_direction(), normal);
		if (Double.compare(denominator, 0.0) == 0)
		{
			return Intersection.failure();
		}

		final double t = - Vector.dot_product(ray.get_origin().subtract(p1), normal) / denominator;
		if (t < 0.0001 || t > best)
		{
			return Intersection.failure();
		}

		Point surface_point = ray.get_point(t);

		final Vector e1 = p2.subtract(p1);
		final Vector e2 = p3.subtract(p1);
		final Vector u = Vector.cross_product(e1, e2);
		final double doubleArea = u.length();

		final Vector e11 = surface_point.subtract(p1);
		final Vector e12 = p3.subtract(p1);
		final Vector u1 = Vector.cross_product(e11, e12);
		final double doubleArea1 = u1.length();

		final Vector e21 = p2.subtract(surface_point);
		final Vector e22 = p3.subtract(surface_point);
		final Vector u2 = Vector.cross_product(e21, e22);
		final double doubleArea2 = u2.length();

		final Vector e31 = p2.subtract(p1);
		final Vector e32 = surface_point.subtract(p1);
		final Vector u3 = Vector.cross_product(e31, e32);
		final double doubleArea3 = u3.length();

		final double lambda1 = doubleArea1 / doubleArea;
		final double lambda2 = doubleArea2 / doubleArea;
		final double lambda3 = doubleArea3 / doubleArea;

		final double dotuu1 = Vector.dot_product(u, u1);
		final double dotuu2 = Vector.dot_product(u, u2);
		final double dotuu3 = Vector.dot_product(u, u3);

		if (dotuu1 < 0.0 || dotuu2 < 0.0 || dotuu3 < 0.0) 
			return Intersection.failure();

		final Point barycentric_point = new Point(lambda1, lambda2, lambda3);
		return new Intersection(
			ray, 
			this, 
			t, 
			get_normal(normal, barycentric_point), 
			surface_point, 
			barycentric_point);
	}

	public final double get_area ()
	{
		final Vector norm = Vector.cross_product(p2.subtract(p1), p3.subtract(p1));
		return norm.length() / 2.0;
	}

	protected Vector get_normal (final Vector normal, final Point barycentric)
	{
		return normal;
	}

	public final Point get_sample ()
	{
		final double b1 = Math.random();
		final double b2 = Math.random();
		final double b3 = 1.0 - b2 - b1;

		return
			p1.multiply(b1)
			.add(p2.multiply(b2))
			.add(p3.multiply(b3));
	}

	public String toString ()
	{
		return "triangle " + p1 + " " + p2 + " " + p3;
	}

	public static Triangle read_from_xml_element(Element element)
	{
		Point p1 = XmlHelpers.get_point(element, "p1");	
		Point p2 = XmlHelpers.get_point(element, "p2");	
		Point p3 = XmlHelpers.get_point(element, "p3");	

		if (p1 == null || p2 == null || p3 == null)
		{
			return null;
		}
		else
		{
			return new Triangle(p1, p2, p3);
		}
	}
}

