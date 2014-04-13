package sk.lovasko.lucenec.geom;

import sk.lovasko.lucenec.math.Double4;

import java.util.regex.Pattern;
import java.util.regex.Matcher;
import java.io.Serializable;

public final class Vector implements Serializable
{
	private double x;
	private double y;
	private double z;

	public Vector (double x, double y, double z)
	{
		set_all_components(x, y, z); 	
	}

	public Vector (double xyz)
	{
		set_all_components(xyz);
	}

	public Vector (Vector vector)
	{
		x = vector.get_x();
		y = vector.get_y();
		z = vector.get_z();
	}

	public Vector (final Double4 double4)
	{
		x = double4.get_x();
		y = double4.get_y();
		z = double4.get_z();

		if (double4.get_w() != 0.0)
		{
			System.err.println("WARNING: the Double4 has last component non-zero.");
		}
	}

	public Vector duplicate ()
	{
		return new Vector(x, y, z);
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

	public final Vector set_x (final double new_x)
	{
		return new Vector(new_x, y, z);
	}

	public final Vector set_y (final double new_y)
	{
		return new Vector(x, new_y, z);
	}

	public final Vector set_z (final double new_z)
	{
		return new Vector(x, y, new_z);
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

		if (!(obj instanceof Vector))
			return false;

		Vector vector = (Vector) obj;

		if (Double.compare(vector.get_x(), x) == 0 &&
		    Double.compare(vector.get_y(), y) == 0 &&
		    Double.compare(vector.get_z(), z) == 0)
			return true;
		else
			return false;
	}

	public static Vector min (Vector a, Vector b)
	{
		return new Vector(Math.min(a.x, b.x), 
		                  Math.min(a.y, b.y), 
		                  Math.min(a.z, b.z));
	}

	public static Vector max (Vector a, Vector b)
	{
		return new Vector(Math.max(a.x, b.x), 
		                  Math.max(a.y, b.y), 
		                  Math.max(a.z, b.z));
	}

	public Vector normalize ()
	{
		if (x == 0.0 && y == 0.0 && z == 0.0)
		{
			System.out.println("zero vector!");
			return new Vector(0.0);
		}
		return divide_scalar(length());			
	}

	public Vector negate ()
	{
		return new Vector(-x, -y, -z); 
	}

	public double length()
	{
		return Math.sqrt(dot_product(this));	
	}

	public double lensqr()
	{
		return dot_product(this);
	}

	public double dot_product (Vector vector)
	{
		return x * vector.x + y * vector.y + z * vector.z;
	}

	public static double dot_product (Vector v1, Vector v2)
	{
		return v1.dot_product(v2);
	}

	public static Vector cross_product (Vector v1, Vector v2)
	{
		double new_x;
		double new_y;
		double new_z;

		new_x = v1.get_y() * v2.get_z() - v1.get_z() * v2.get_y();  
		new_y = v1.get_z() * v2.get_x() - v1.get_x() * v2.get_z(); 
		new_z = v1.get_x() * v2.get_y() - v1.get_y() * v2.get_x(); 

		return new Vector(new_x, new_y, new_z);
	}

	public Vector add (Vector vector)
	{
		return new Vector(x + vector.get_x(), 
		                  y + vector.get_y(),
		                  z + vector.get_z());
	}

	public Vector subtract (Vector vector)
	{
		return new Vector(x - vector.get_x(), 
		                  y - vector.get_y(),
		                  z - vector.get_z());
	}

	public Vector multiply_scalar (double scalar)
	{
		return new Vector(x * scalar,
		                  y * scalar, 
		                  z * scalar); 
	}

	public Vector multiply (double scalar)
	{
		return new Vector(x * scalar,
		                  y * scalar, 
		                  z * scalar); 
	}

	public Vector divide_scalar (double scalar) 
	{
		return new Vector(x / scalar,
		                  y / scalar, 
		                  z / scalar); 
	}

	public final Vector rotate (final Vector axis, final double angle)
	{
      final Vector an = axis.normalize();
			final double s = Math.sin(angle);
			final double c = Math.cos(angle);
			final double cn = 1.0 - c;
			final double ax = axis.get_x();
			final double ay = axis.get_y();
			final double az = axis.get_z();

      final Vector r1 = new Vector(
				ax * ax * cn + c,
				ax * ay * cn - az * s,
				ax * az * cn + ay * s);

      final Vector r2 = new Vector(
				ay * ax * cn + az * s,
				ay * ay * cn + c,
				ay * az * cn - ax * s);

      final Vector r3 = new Vector(
				az * ax * cn - ay * s,
				az * ay * cn + ax * s,
				az * az * cn + c);

      final double rx = Vector.dot_product(this, r1);
      final double ry = Vector.dot_product(this, r2);
      final double rz = Vector.dot_product(this, r3);

      final Vector result = new Vector(rx, ry, rz);
      return result.normalize();
	}

	public Point to_point ()
	{
		return new Point(x, y, z);
	}

	public static Vector from_string (String string)
	{	
		Pattern pattern = Pattern.compile("^\\((-?\\d+\\.\\d+),\\s*(-?\\d+\\.\\d+),\\s*(-?\\d+\\.\\d+)\\)$");
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

			return new Vector(x, y, z);
		}
		else
		{
			return null;
		}
	}

	public String toString ()
	{
		return "(" + x + ", " + y + ", " + z + ")";
	}

	public final void dump ()
	{
		System.out.println(this);
	}
}

