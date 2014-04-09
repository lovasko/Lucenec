package sk.lovasko.lucenec;

import java.util.regex.Pattern;
import java.util.regex.Matcher;
import java.io.Serializable;

public final class Point implements Serializable
{
	private double x;
	private double y;
	private double z;

	Point (final double x, final double y, final double z)
	{
		set_all_components(x, y, z);
	}

	Point (final double xyz)
	{
		set_all_components(xyz);
	}

	Point (final Point point)
	{
		set_all_components(point.get_x(), 
		                   point.get_y(), 
		                   point.get_z());
	}

	Point (final Double4 double4)
	{
		set_all_components(double4.get_x(), 
		                   double4.get_y(), 
		                   double4.get_z());
	}

	public double get_x ()
	{
		return x;
	}

	public double get_y ()
	{
		return y;
	}

	public double get_z ()
	{
		return z;
	}

	public void set_x (double new_x)
	{
		x = new_x;
	}

	public void set_y (double new_y)
	{
		y = new_y;
	}

	public final Point set_z (final double new_z)
	{
		return new Point(x, y, new_z);
	}

	public void set_all_components (double new_x,
	                                double new_y,
	                                double new_z)
	{
		x = new_x;
		y = new_y;
		z = new_z;
	}

	public void set_all_components (double new_xyz)
	{
		x = new_xyz;
		y = new_xyz;
		z = new_xyz;
	}

	public boolean equals (Object obj)
	{
		if (obj == null)
			return false;

		if (obj == this)
			return true;

		if (!(obj instanceof Point))
			return false;

		Point point = (Point) obj;

		if (Double.compare(point.get_x(), x) == 0 &&
		    Double.compare(point.get_y(), y) == 0 &&
		    Double.compare(point.get_z(), z) == 0)
			return true;
		else
			return false;
	}

	public static final Point min (final Point a, final Point b)
	{
		return new Point(Math.min(a.get_x(), b.get_x()), 
		                 Math.min(a.get_y(), b.get_y()), 
		                 Math.min(a.get_z(), b.get_z()));
	}

	public static final Point min (final Point a, final Point b, final Point c)
	{
		return new Point(Math.min(Math.min(a.get_x(), b.get_x()), c.get_x()), 
		                 Math.min(Math.min(a.get_y(), b.get_y()), c.get_y()), 
		                 Math.min(Math.min(a.get_z(), b.get_z()), c.get_z()));
	}

	public static final Point max (final Point a, final Point b)
	{
		return new Point(Math.max(a.get_x(), b.get_x()), 
		                 Math.max(a.get_y(), b.get_y()), 
		                 Math.max(a.get_z(), b.get_z()));
	}

	public static final Point max (final Point a, final Point b, final Point c)
	{
		return new Point(Math.max(Math.max(a.get_x(), b.get_x()), c.get_x()), 
		                 Math.max(Math.max(a.get_y(), b.get_y()), c.get_y()), 
		                 Math.max(Math.max(a.get_z(), b.get_z()), c.get_z()));
	}

	public final Point add (final Point point)
	{
		return new Point(x + point.get_x(),
		                 y + point.get_y(),
		                 z + point.get_z());
	}

	public final Point move (final Vector vector)
	{
		return new Point(x + vector.get_x(),
		                 y + vector.get_y(),
		                 z + vector.get_z());
	}

	public final Vector subtract (final Point point)
	{
		return new Vector(x - point.get_x(),
		                  y - point.get_y(), 
		                  z - point.get_z());
	}

	public static final Vector subtract (final Point point1, final Point point2)
	{
		return point1.subtract(point2);
	}

	public final Vector to_vector ()
	{
		return new Vector(x, y, z);
	}

	public final Point multiply_scalar (final double scalar)
	{
		return new Point(x * scalar, 
		                 y * scalar, 
		                 z * scalar);
	}

	public final Point multiply (final double scalar)
	{
		return new Point(
			x * scalar, 
			y * scalar, 
			z * scalar);
	}

	public final Point divide_scalar (final double scalar)
	{
		return new Point(x / scalar, 
		                 y / scalar, 
		                 z / scalar);
	}

	public static final Point from_string (final String string)
	{
		Pattern pattern = Pattern.compile("^\\[(-?\\d+\\.\\d+),\\s*(-?\\d+\\.\\d+),\\s*(-?\\d+\\.\\d+)\\]$");
		Matcher matcher = pattern.matcher(string);

		if (matcher.find())
		{
			String x_string = matcher.group(1);
			double x = 0.0;
			if (x_string != null)
			{
				try
				{
					x = Double.parseDouble(x_string);
				}
				catch (NumberFormatException nfe)
				{
					return null;
				}
			}

			String y_string = matcher.group(2);
			double y = 0.0;
			if (y_string != null)
			{
				try
				{
					y = Double.parseDouble(y_string);
				}
				catch (NumberFormatException nfe)
				{
					return null;
				}
			}

			String z_string = matcher.group(3);
			double z = 0.0;
			if (z_string != null)
			{
				try
				{
					z = Double.parseDouble(z_string);
				}
				catch (NumberFormatException nfe)
				{
					return null;
				}
			}

			return new Point(x, y, z);
		}
		else
		{
			return null;
		}
	}

	public String toString()
	{
		return "[" + x + ", " + y + ", " + z + "]";
	}

	public final void dump ()
	{
		System.out.println(this);
	}
}

