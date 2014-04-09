package sk.lovasko.lucenec;

import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;

public final class Sphere extends Solid
{
	private final Point center;
	private final double radius;

	public Sphere (final Point center, final double radius)
	{
		this.center = center;
		this.radius = radius;

		bounding_box = new BoundingBox(
			center.move(new Vector(-radius, -radius, -radius)),
			center.move(new Vector( radius,  radius,  radius)));
	}

	public Sphere (
		final Point center, 
		final double radius, 
		final Material material)
	{
		this(center, radius);
		set_material(material);
	}

	public Sphere (
		final Point center, 
		final double radius, 
		final CoordinateMapper coordinate_mapper,
		final Material material)
	{
		this(center, radius, material);
		set_coordinate_mapper(coordinate_mapper);
	}

	public BoundingBox get_bounds ()
	{
		return bounding_box;
	}

	public Intersection intersect (Ray ray, double best)
	{
		final Vector ray_origin_vector = ray.get_origin().subtract(new Point(0,0,0));
		final Vector sphere_center_vector = center.subtract(new Point(0,0,0));

		final double A = Vector.dot_product(ray.get_direction(), ray.get_direction());
		final double B = 2 * Vector.dot_product(ray.get_direction(), ray_origin_vector) - 2 * Vector.dot_product(sphere_center_vector, ray.get_direction());
		final double C =  ray_origin_vector.lensqr() - 2 * Vector.dot_product(sphere_center_vector, ray_origin_vector) + sphere_center_vector.lensqr() - radius * radius;

		double t = 1.0;

		if (A == 0)
		{
			t = - C / B;

			if (t >= 0 && t <= best)
			{
				Point surface_point = ray.get_point(t);
				return new Intersection(ray, this, t, get_normal(surface_point), surface_point, surface_point);
			}
			else return Intersection.failure();
		}
		else
		{
			double discriminant = B * B - 4 * A * C;

			if (discriminant < 0.0)
			{
				return Intersection.failure();
			}

			double t1 = ( - B + Math.sqrt(discriminant)) / (2 * A);
			double t2 = ( - B - Math.sqrt(discriminant)) / (2 * A);

			boolean t1Valid = t1 <= best && t1 >= 0.0001;
			boolean t2Valid = t2 <= best && t2 >= 0.0001;

			if (!t1Valid && !t2Valid) return Intersection.failure();

			if (t1Valid && t2Valid) t = Math.min(t1, t2);
			if (!t1Valid && t2Valid) t = t2;
			if (t1Valid && !t2Valid) t = t1;

			Point surface_point = ray.get_point(t);
			return new Intersection(ray, this, t, get_normal(surface_point), surface_point, surface_point);
		}
	}

	public double get_area ()
	{
		return 4.0 * Math.PI * radius * radius;
	}

	public String toString()
	{
		return "sphere " + center + " " + radius + "\n";
	}

	private Vector get_normal (Point point)
	{
		return center.subtract(point).negate().normalize();
	}

	public static Sphere read_from_xml_element (Element element)
	{
		Point center = XmlHelpers.get_point(element, "center");
		Double radius = XmlHelpers.get_double(element, "radius");

		if (center == null || radius == null)
		{
			return null;
		}

		return new Sphere(center, radius);
	}
}

