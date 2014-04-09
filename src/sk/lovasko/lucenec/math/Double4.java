package sk.lovasko.lucenec;

public final class Double4
{
	private final double x;
	private final double y;
	private final double z;
	private final double w;

	Double4 (final Point point)
	{
		x = point.get_x();
		y = point.get_y();
		z = point.get_z();
		w = 1.0;
	}

	Double4 (final Vector vector)
	{
		x = vector.get_x();
		y = vector.get_y();
		z = vector.get_z();
		w = 0.0;
	}

	Double4 (
		final double x,
		final double y,
		final double z,
		final double w)
	{
		this.x = x;
		this.y = y;
		this.z = z;
		this.w = w;
	}

	Double4 (final double xyzw)
	{
		x = xyzw;
		y = xyzw;
		z = xyzw;
		w = xyzw;
	}

	public final double get (final int index)
	{
		switch (index)
		{
			case 0:
				return x;

			case 1:
				return y;

			case 2:
				return z;

			case 3:
				return w;

			default:
				System.err.println("Asked for non-existent index in Double4: " + index);
				return 0.0;
		}
	}

	public final double get_x ()
	{
		return x;
	}

	public final double get_y ()
	{
		return y;
	}

	public final double get_z ()
	{
		return z;
	}

	public final double get_w ()
	{
		return w;
	}

	public final Double4 negate ()
	{
		return new Double4(-x, -y, -z, -w);
	}

	public final Double4 add (final Double4 double4)
	{
		return new Double4(x + double4.get_x(),
		                   y + double4.get_y(),
		                   z + double4.get_z(),
		                   w + double4.get_w());
	}

	public final Double4 subtract (final Double4 double4)
	{
		return new Double4(x - double4.get_x(),
		                   y - double4.get_y(),
		                   z - double4.get_z(),
		                   w - double4.get_w());
	}

	public final Double4 multiply (final Double4 double4)
	{
		return new Double4(
			x * double4.get_x(),
			y * double4.get_y(),
			z * double4.get_z(),
			w * double4.get_w());
	}

	public final Double4 multiply (final double scale)
	{
		return new Double4(
			x * scale,
			y * scale,
			z * scale,
			w * scale);
	}

	public final Double4 divide (final Double4 double4)
	{
		return new Double4(x / double4.get_x(),
		                   y / double4.get_y(),
		                   z / double4.get_z(),
		                   w / double4.get_w());
	}

	public boolean equals (Object obj)
	{
		if (obj == null)
		{
			return false;
		}

		if (obj == this)
		{
			return true;
		}

		if (!(obj instanceof Double4))
		{
			return false;
		}

		Double4 double4 = (Double4) obj;

		if (Double.compare(double4.get_x(), x) == 0 &&
		    Double.compare(double4.get_y(), y) == 0 &&
		    Double.compare(double4.get_z(), z) == 0 &&
		    Double.compare(double4.get_w(), w) == 0)
		{
			return true;
		}
		else
		{
			return false;
		}
	}

	public final Double4 multiply_scalar (double scalar)
	{
		return new Double4(x * scalar,
		                   y * scalar,
		                   z * scalar,
		                   w * scalar);
	}

	public final Double4 divide_scalar (double scalar)
	{
		return new Double4(x / scalar,
		                   y / scalar,
		                   z / scalar,
		                   w / scalar);
	}

	public final double dot_product (final Double4 double4)
	{
		return (x * double4.get_x()) + 
		       (y * double4.get_y()) + 
		       (z * double4.get_z()) + 
		       (w * double4.get_w());
	}

	public static final double dot_product (final Double4 d1, final Double4 d2)
	{
		return d1.dot_product(d2);
	}

	public static final Double4 min (final Double4 d1, final Double4 d2)
	{
		return new Double4(Math.min(d1.get_x(), d2.get_x()),
		                   Math.min(d1.get_y(), d2.get_y()),
		                   Math.min(d1.get_z(), d2.get_z()),
		                   Math.min(d1.get_w(), d2.get_w()));
	}

	public static final Double4 max (final Double4 d1, final Double4 d2)
	{
		return new Double4(Math.max(d1.get_x(), d2.get_x()),
		                   Math.max(d1.get_y(), d2.get_y()),
		                   Math.max(d1.get_z(), d2.get_z()),
		                   Math.max(d1.get_w(), d2.get_w()));
	}

	public final Vector to_vector ()
	{
		return new Vector(x, y, z);
	}

	public final Point to_point ()
	{
		return new Point(x, y, z);
	}

	public final Double4 normalize ()
	{
		return new Double4(to_vector().normalize());
	}

	public String toString ()
	{
		return "(" + x + ", " + y + ", " + z + ", " + w + ")";
	}
}

