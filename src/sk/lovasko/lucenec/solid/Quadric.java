package sk.lovasko.lucenec;

import sk.lovasko.lucenec.xml;

import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;

public final class Quadric extends Solid
{
	private double a;
	private double b;
	private double c;
	private double d;
	private double e;
	private double f;
	private double g;
	private double h;
	private double i;
	private double j;

	Quadric (double _a, double _b, double _c, double _d, double _e, 
	         double _f, double _g, double _h, double _i, double _j)
	{
		a = _a;
		b = _b;
		c = _c;
		d = _d;
		e = _e;
		f = _f;
		g = _g;
		i = _i;
		j = _j;

		bounding_box = BoundingBox.full();
	}

	public BoundingBox get_bounds ()
	{
		return bounding_box;
	}

	public Intersection intersect (Ray ray, double best)
	{
		// ray direction x, y z
		double dx = ray.get_direction().get_x();
		double dy = ray.get_direction().get_y();
		double dz = ray.get_direction().get_z();

		// ray origin x, y, z
		double ox = ray.get_origin().get_x();
		double oy = ray.get_origin().get_y();
		double oz = ray.get_origin().get_z();

		double A = (a * dx * dx) + 
		           (b * dy * dy) + 
		           (c * dz * dz) + 
		           (d * dx * dy) +
		           (e * dx * dz) + 
		           (f * dy * dz);

		double B = (2.0 * a * ox * dx) + 
		           (2.0 * b * oy * dy) + 
		           (2.0 * c * oz * dz) + 
		           (d * (ox * dy + oy * dx)) + 
		           (e * (ox * dz + oz * dx)) + 
		           (f * (oy * dz + dy * oz)) + 
		           (g * dx) + 
		           (h * dy) + 
		           (i * dz);   

		double C = (a * ox * ox) + 
		           (b * oy * oy) + 
		           (c * oz * oz) + 
		           (d * ox * oy) + 
		           (e * ox * oz) + 
		           (f * oy * oz) + 
		           (g * ox) + 
		           (h * oy) + 
		           (i * oz) + 
		           (j);

		double determinant = B * B - 4.0 * A * C;
		if (determinant < 0.0)
		{
			return Intersection.failure();
		}

		double t;

		if (Double.compare(A, 0.0) == 0)
		{
			t = -C/B;
		}
		else
		{
			// TODO optimize the sqrt
			t = (-B - Math.sqrt(determinant)) / (2.0 * A);
			if (t < 0.0)
			{
				t = (-B + Math.sqrt(determinant)) / (2.0 * A);
			}
		}

		if (t > best || t < 0.0)
		{
			return Intersection.failure();
		}

		Point hit_point = ray.get_point(t);
		double hpx = hit_point.get_x();
		double hpy = hit_point.get_y();
		double hpz = hit_point.get_z();

		Vector normal = new Vector(0.0);
		normal.set_x(2.0 * a * hpx + d * hpy + e * hpz + g);
		normal.set_y(2.0 * b * hpy + d * hpx + f * hpz + h);
		normal.set_z(2.0 * c * hpz + e * hpx + f * hpy + i);

		return new Intersection(ray, this, t, normal, hit_point, null);
	}

	public double get_area ()
	{
		return 0.0;
	}

	public String toString ()
	{
		return "quadric " + a + " " 
		                  + b + " " 
		                  + c + " " 
		                  + d + " " 
		                  + e + " " 
		                  + f + " " 
		                  + g + " " 
		                  + h + " " 
		                  + i + " " 
		                  + j + " ";
	}

	public static Quadric read_from_xml_element (Element element)
	{
		Double a = XmlHelpers.get_double(element, "a");
		Double b = XmlHelpers.get_double(element, "b");
		Double c = XmlHelpers.get_double(element, "c");
		Double d = XmlHelpers.get_double(element, "d");
		Double e = XmlHelpers.get_double(element, "e");
		Double f = XmlHelpers.get_double(element, "f");
		Double g = XmlHelpers.get_double(element, "g");
		Double h = XmlHelpers.get_double(element, "h");
		Double i = XmlHelpers.get_double(element, "i");
		Double j = XmlHelpers.get_double(element, "j");

		if (a == null || b == null || c == null || d == null || e == null ||
		    f == null || g == null || h == null || i == null || j == null)
		{
			return null;
		}

		return new Quadric(a, b, c, d, e, f, g, h, i, j);
	}
}
