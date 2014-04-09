package sk.lovasko.lucenec;

public final class Matrix
{
	private final Double4 a; 
	private final Double4 b; 
	private final Double4 c; 
	private final Double4 d; 

	public Matrix (
		final Double4 a, 
		final Double4 b, 
		final Double4 c, 
		final Double4 d)
	{
		this.a = a;
		this.b = b;
		this.c = c;
		this.d = d;
	}

	public static final Matrix zero ()
	{
		return new Matrix(
			new Double4(0.0),
			new Double4(0.0),
			new Double4(0.0),
			new Double4(0.0));
	}

	public static final Matrix identity ()
	{
		return new Matrix(
			new Double4(1.0, 0.0, 0.0, 0.0),
			new Double4(0.0, 1.0, 0.0, 0.0),
			new Double4(0.0, 0.0, 1.0, 0.0),
			new Double4(0.0, 0.0, 0.0, 1.0));
	}

	public static final Matrix system (
		final Vector e1, 
		final Vector e2,
		final Vector e3)
	{
		return new Matrix(
			new Double4(e1.get_x(), e2.get_x(), e3.get_x(), 0.0),
			new Double4(e1.get_y(), e2.get_y(), e3.get_y(), 0.0),
			new Double4(e1.get_z(), e2.get_z(), e3.get_z(), 0.0),
			new Double4(0.0,        0.0,        0.0,        1.0));
	}

	public static final Matrix local (final Vector vector)
	{
		final Double4 c = new Double4(vector.normalize());

		final Vector v = Math.abs(c.get_x()) >= 1.0 ? 
			new Vector(0.0, 1.0, 0.0):
			new Vector(1.0, 0.0, 0.0);

		final Double4 b = new Double4(
			Vector.cross_product(
				c.to_vector(), 
				v)).normalize();	

		final Double4 a = new Double4(
			Vector.cross_product(
				b.to_vector(),
				c.to_vector())).normalize();

		final Double4 d = new Double4(0.0, 0.0, 0.0, 1.0);

		return new Matrix(a, b, c, d);
	}

	public final Double4 get (final int index)
	{
		switch (index)
		{
			case 0:
				return a;

			case 1:
				return b;

			case 2:
				return c;

			case 3:
				return d;

			default:
				System.err.println("Asked for non-existent index in Matrix: " + index);
				return new Double4(0.0);
		}
	}

	public final Double4 get_a ()
	{
		return a;
	}

	public final Double4 get_b ()
	{
		return b;
	}

	public final Double4 get_c ()
	{
		return c;
	}

	public final Double4 get_d ()
	{
		return d;
	}

	public final Matrix add (final Matrix matrix)
	{
		return new Matrix(
			a.add(matrix.get_a()),
			b.add(matrix.get_b()),
			c.add(matrix.get_c()),
			d.add(matrix.get_d()));
	}

	public final Matrix subtract (final Matrix matrix)
	{
		return new Matrix(
			a.subtract(matrix.get_a()),
			b.subtract(matrix.get_b()),
			c.subtract(matrix.get_c()),
			d.subtract(matrix.get_d()));
	}

	public final Matrix transpose ()
	{
		return new Matrix(
			new Double4(a.get_x(), b.get_x(), c.get_x(), d.get_x()),
			new Double4(a.get_y(), b.get_y(), c.get_y(), d.get_y()),
			new Double4(a.get_z(), b.get_z(), c.get_z(), d.get_z()),
			new Double4(a.get_w(), b.get_w(), c.get_w(), d.get_w()));
	}

	public final Matrix inverse ()
	{
		final double det = determinant();
		if (det == 0.0)
		{
			return Matrix.zero();
		}

		double m[] = new double[16];
		m[0] = a.get_x();
		m[1] = a.get_y();
		m[2] = a.get_z();
		m[3] = a.get_w();

		m[4] = b.get_x();
		m[5] = b.get_y();
		m[6] = b.get_z();
		m[7] = b.get_w();

		m[8] =  c.get_x();
		m[9] =  c.get_y();
		m[10] = c.get_z();
		m[11] = c.get_w();

		m[12] = d.get_x();
		m[13] = d.get_y();
		m[14] = d.get_z();
		m[15] = d.get_w();

		double result[] = new double[16];
		result[0] = m[5]  * m[10] * m[15] -
		            m[5]  * m[11] * m[14] -
		            m[9]  * m[6]  * m[15] +
		            m[9]  * m[7]  * m[14] +
		            m[13] * m[6]  * m[11] -
		            m[13] * m[7]  * m[10];

		result[4] = -m[4]  * m[10] * m[15] +
		             m[4]  * m[11] * m[14] +
		             m[8]  * m[6]  * m[15] -
		             m[8]  * m[7]  * m[14] -
		             m[12] * m[6]  * m[11] +
		             m[12] * m[7]  * m[10];

		result[8] = m[4]  * m[9]  * m[15] -
		            m[4]  * m[11] * m[13] -
		            m[8]  * m[5]  * m[15] +
		            m[8]  * m[7]  * m[13] +
		            m[12] * m[5]  * m[11] -
		            m[12] * m[7]  * m[9];

		result[12] = -m[4]  * m[9]  * m[14] +
		              m[4]  * m[10] * m[13] +
		              m[8]  * m[5]  * m[14] -
		              m[8]  * m[6]  * m[13] -
		              m[12] * m[5]  * m[10] +
		              m[12] * m[6]  * m[9];

		result[1] = -m[1]  * m[10] * m[15] +
		             m[1]  * m[11] * m[14] +
		             m[9]  * m[2]  * m[15] -
		             m[9]  * m[3]  * m[14] -
		             m[13] * m[2]  * m[11] +
		             m[13] * m[3]  * m[10];

		result[5] = m[0]  * m[10] * m[15] -
		            m[0]  * m[11] * m[14] -
		            m[8]  * m[2]  * m[15] +
		            m[8]  * m[3]  * m[14] +
		            m[12] * m[2]  * m[11] -
		            m[12] * m[3]  * m[10];

		result[9] = -m[0]  * m[9]  * m[15] +
		             m[0]  * m[11] * m[13] +
		             m[8]  * m[1]  * m[15] -
		             m[8]  * m[3]  * m[13] -
		             m[12] * m[1]  * m[11] +
		             m[12] * m[3]  * m[9];

		result[13] = m[0]  * m[9]  * m[14] -
		             m[0]  * m[10] * m[13] -
		             m[8]  * m[1]  * m[14] +
		             m[8]  * m[2]  * m[13] +
		             m[12] * m[1]  * m[10] -
		             m[12] * m[2]  * m[9];

		result[2] = m[1] *  m[6] * m[15] -
		            m[1] *  m[7] * m[14] -
		            m[5] *  m[2] * m[15] +
		            m[5] *  m[3] * m[14] +
		            m[13] * m[2] * m[7] -
		            m[13] * m[3] * m[6];

		result[6] = -m[0] *  m[6] * m[15] +
		             m[0] *  m[7] * m[14] +
		             m[4] *  m[2] * m[15] -
		             m[4] *  m[3] * m[14] -
		             m[12] * m[2] * m[7] +
		             m[12] * m[3] * m[6];

		result[10] = m[0] *  m[5] * m[15] -
		             m[0] *  m[7] * m[13] -
		             m[4] *  m[1] * m[15] +
		             m[4] *  m[3] * m[13] +
		             m[12] * m[1] * m[7] -
		             m[12] * m[3] * m[5];

		result[14] = -m[0] *  m[5] * m[14] +
		              m[0] *  m[6] * m[13] +
		              m[4] *  m[1] * m[14] -
		              m[4] *  m[2] * m[13] -
		              m[12] * m[1] * m[6] +
		              m[12] * m[2] * m[5];

		result[3] = -m[1] * m[6] * m[11] +
		             m[1] * m[7] * m[10] +
		             m[5] * m[2] * m[11] -
		             m[5] * m[3] * m[10] -
		             m[9] * m[2] * m[7] +
		             m[9] * m[3] * m[6];

		result[7] = m[0] * m[6] * m[11] -
		            m[0] * m[7] * m[10] -
		            m[4] * m[2] * m[11] +
		            m[4] * m[3] * m[10] +
		            m[8] * m[2] * m[7] -
		            m[8] * m[3] * m[6];

		result[11] = -m[0] * m[5] * m[11] +
		              m[0] * m[7] * m[9] +
		              m[4] * m[1] * m[11] -
		              m[4] * m[3] * m[9] -
		              m[8] * m[1] * m[7] +
		              m[8] * m[3] * m[5];

		result[15] = m[0] * m[5] * m[10] -
		             m[0] * m[6] * m[9] -
		             m[4] * m[1] * m[10] +
		             m[4] * m[2] * m[9] +
		             m[8] * m[1] * m[6] -
		             m[8] * m[2] * m[5];

		Matrix helper = new Matrix(
			new Double4(result[0],  result[1],  result[2],  result[3]),
			new Double4(result[4],  result[5],  result[6],  result[7]),
			new Double4(result[8],  result[9],  result[10], result[11]),
			new Double4(result[12], result[13], result[14], result[15]));

		return helper.multiply_scalar(1.0/det);
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

		if (!(obj instanceof Matrix))
		{
			return false;
		}

		Matrix matrix = (Matrix) obj;

		if (a.equals(matrix.get_a()) &&
		    b.equals(matrix.get_b()) &&
		    c.equals(matrix.get_c()) &&
		    d.equals(matrix.get_d()))
		{
			return true;
		}
		else
		{
			return false;
		}
	}

	public final Double4 multiply (final Double4 double4)
	{
		return new Double4(
			a.dot_product(double4),
			b.dot_product(double4),
			c.dot_product(double4),
			d.dot_product(double4));
	}

	public final Vector multiply (final Vector vector)
	{
		return new Vector(multiply(new Double4(vector)));
	}

	public final Point multiply (final Point point)
	{
		return new Point(multiply(new Double4(point)));
	}

	public final double determinant ()
	{
		double[] m = new double[16];

		m[0] = a.get_x();
		m[1] = a.get_y();
		m[2] = a.get_z();
		m[3] = a.get_w();

		m[4] = b.get_x();
		m[5] = b.get_y();
		m[6] = b.get_z();
		m[7] = b.get_w();

		m[8] =  c.get_x();
		m[9] =  c.get_y();
		m[10] = c.get_z();
		m[11] = c.get_w();

		m[12] = d.get_x();
		m[13] = d.get_y();
		m[14] = d.get_z();
		m[15] = d.get_w();

		return
			m[12] * m[9] * m[6]  * m[3]  - m[8] * m[13] * m[6]  * m[3]  -
			m[12] * m[5] * m[10] * m[3]  + m[4] * m[13] * m[10] * m[3]  +
			m[8]  * m[5] * m[14] * m[3]  - m[4] * m[9]  * m[14] * m[3]  -
			m[12] * m[9] * m[2]  * m[7]  + m[8] * m[13] * m[2]  * m[7]  +
			m[12] * m[1] * m[10] * m[7]  - m[0] * m[13] * m[10] * m[7]  -
			m[8]  * m[1] * m[14] * m[7]  + m[0] * m[9]  * m[14] * m[7]  +
			m[12] * m[5] * m[2]  * m[11] - m[4] * m[13] * m[2]  * m[11] -
			m[12] * m[1] * m[6]  * m[11] + m[0] * m[13] * m[6]  * m[11] +
			m[4]  * m[1] * m[14] * m[11] - m[0] * m[5]  * m[14] * m[11] -
			m[8]  * m[5] * m[2]  * m[15] + m[4] * m[9]  * m[2]  * m[15] +
			m[8]  * m[1] * m[6]  * m[15] - m[0] * m[9]  * m[6]  * m[15] -
			m[4]  * m[1] * m[10] * m[15] + m[0] * m[5]  * m[10] * m[15];
	}

	public final Matrix multiply_double4 (final Double4 double4)
	{
		return new Matrix(
			a.multiply_scalar(double4.get_x()),
			b.multiply_scalar(double4.get_y()),
			c.multiply_scalar(double4.get_z()),
			d.multiply_scalar(double4.get_w()));
	}

	public final Matrix multiply_scalar (final double scalar)
	{
		return multiply_double4(new Double4(scalar));
	}

	public final Matrix product (final Matrix matrix)
	{
		double m[] = new double[16];
		Matrix t = matrix.transpose();

		m[0]  = Double4.dot_product(a, t.get_a());
		m[1]  = Double4.dot_product(a, t.get_b());
		m[2]  = Double4.dot_product(a, t.get_c());
		m[3]  = Double4.dot_product(a, t.get_d());

		m[4]  = Double4.dot_product(b, t.get_a());
		m[5]  = Double4.dot_product(b, t.get_b());
		m[6]  = Double4.dot_product(b, t.get_c());
		m[7]  = Double4.dot_product(b, t.get_d());

		m[8]  = Double4.dot_product(c, t.get_a());
		m[9]  = Double4.dot_product(c, t.get_b());
		m[10] = Double4.dot_product(c, t.get_c());
		m[11] = Double4.dot_product(c, t.get_d());

		m[12] = Double4.dot_product(d, t.get_a());
		m[13] = Double4.dot_product(d, t.get_b());
		m[14] = Double4.dot_product(d, t.get_c());
		m[15] = Double4.dot_product(d, t.get_d());

		return new Matrix(
			new Double4(m[0],  m[1],  m[2],  m[3]),
			new Double4(m[4],  m[5],  m[6],  m[7]),
			new Double4(m[8],  m[9],  m[10], m[11]),
			new Double4(m[12], m[13], m[14], m[15]));
	}
}

