package sk.lovasko.lucenec;

public class Instance extends Solid 
{
	private Matrix matrix;
	private Matrix inverse;
	private final Primitive primitive;
	private BoundingBox bounding_box;

	Instance (final Primitive _primitive)
	{
		matrix = Matrix.identity();
		inverse = Matrix.identity();
		bounding_box = _primitive.get_bounds();

		primitive = _primitive;
	}

	@Override
	public BoundingBox get_bounds ()
	{
		return bounding_box;
	}

	@Override
	public double get_area ()
	{
		return 0.0;
	}

	private final void recompute_bounding_box ()
	{
		Point min = primitive.get_bounds().get_min();
		Point max = primitive.get_bounds().get_max();

		Point a = new Point(min.get_x(), min.get_y(), min.get_z());
		Point b = new Point(max.get_x(), min.get_y(), min.get_z());
		Point c = new Point(max.get_x(), min.get_y(), max.get_z());
		Point d = new Point(min.get_x(), min.get_y(), max.get_z());

		Point e = new Point(min.get_x(), max.get_y(), min.get_z());
		Point f = new Point(max.get_x(), max.get_y(), min.get_z());
		Point g = new Point(max.get_x(), max.get_y(), max.get_z());
		Point h = new Point(min.get_x(), max.get_y(), max.get_z());

		a = matrix.multiply(a);
		b = matrix.multiply(b);
		c = matrix.multiply(c);
		d = matrix.multiply(d);
		e = matrix.multiply(e);
		f = matrix.multiply(f);
		g = matrix.multiply(g);
		h = matrix.multiply(h);

		Point new_min = Point.min(a, b, Point.min(c, d, Point.min(e, f, Point.min(g, h)))); 	
		Point new_max = Point.max(a, b, Point.max(c, d, Point.max(e, f, Point.max(g, h)))); 	

		bounding_box = new BoundingBox(new_min, new_max);
	}

	@Override
	public Intersection intersect (Ray ray, double best)
	{
		Vector trans_direction = inverse.multiply(ray.get_direction()).normalize(); 
		Point trans_origin = inverse.multiply(ray.get_origin()); 
		Ray trans_ray = new Ray(trans_origin, trans_direction);

		Intersection intersection = primitive.intersect(trans_ray, best);
		if (intersection.hit())
		{
			Point hit_point = matrix.multiply(intersection.get_hit_point());
			return new Intersection(trans_ray, null, intersection.get_distance(), intersection.get_normal(),
				hit_point, null); 
		}
		else
		{
			return Intersection.failure();
		}
	}

	public final Primitive get_primitive ()
	{
		return primitive;
	}

	public final void reset ()
	{
		matrix = Matrix.identity();
	}

	private final void combine (final Matrix transformation)
	{
		matrix = transformation.product(matrix);
	}

	private final void recompute_inverse ()
	{
		inverse = matrix.inverse();	
	}

	public final void translate (final Vector vector)
	{
		final Matrix translation_matrix = new Matrix(
			new Double4(1.0, 0.0, 0.0, vector.get_x()),
			new Double4(0.0, 1.0, 0.0, vector.get_y()),
			new Double4(0.0, 0.0, 1.0, vector.get_z()),
			new Double4(0.0, 0.0, 0.0, 1.0));

		combine(translation_matrix);
		recompute_inverse();
		recompute_bounding_box();
	}

	public final void rotate (final Vector axis, final double angle)
	{
		final Vector unit = axis.normalize();		
		final double s = Math.sin(angle);
		final double c = Math.cos(angle);
		final double i = (1.0 - c);
		final double l = unit.get_x();
		final double m = unit.get_y();
		final double n = unit.get_z();
		final double ms = m*s;
		final double ns = n*s;
		final double ls = l*s;
		final double nni = n*n*i;
		final double lli = l*l*i;
		final double mmi = m*m*i;
		final double nmi = n*m*i;
		final double lmi = l*m*i;
		final double lni = l*n*i;

		final Matrix rotation_matrix = new Matrix(
			new Double4(lli + c,  lmi - ns, lni + ms, 0.0),
			new Double4(lmi + ns, mmi + c,  nmi - ls, 0.0),
			new Double4(lni - ms, nmi + ls, nni + c,  0.0),
			new Double4(0.0,      0.0,      0.0,      1.0));

		combine(rotation_matrix);
		recompute_inverse();
		recompute_bounding_box();
	}

	public final void scale (final double scale)
	{
		scale(new Vector(scale));
	}

	public final void scale (final Vector scale)
	{
		final Matrix scaling_matrix = new Matrix(
			new Double4(scale.get_x(), 0.0, 0.0, 0.0),
			new Double4(0.0, scale.get_y(), 0.0, 0.0),
			new Double4(0.0, 0.0, scale.get_z(), 0.0),
			new Double4(0.0, 0.0, 0.0,           1.0));

		combine(scaling_matrix);
		recompute_inverse();
		recompute_bounding_box();
	}

	public final void set_matrix (final Matrix matrix)
	{
		this.matrix = matrix;
		recompute_inverse();
		recompute_bounding_box();
	}
}

