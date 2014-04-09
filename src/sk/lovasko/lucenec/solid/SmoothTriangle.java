package sk.lovasko.lucenec;

import sk.lovasko.lucenec.xml;

public final class SmoothTriangle extends Triangle
{
	private final Vector normal1;
	private final Vector normal2;
	private final Vector normal3;

	public SmoothTriangle (
		final Point point1,
		final Point point2,
		final Point point3,
		final Vector normal1,
		final Vector normal2,
		final Vector normal3,
		final CoordinateMapper coordinate_mapper,
		final Material material)
	{
		super(point1, point2, point3, coordinate_mapper, material);

		this.normal1 = normal1;
		this.normal2 = normal2;
		this.normal3 = normal3;
	}

	public String toString ()
	{
		return "smoothTriangle";
	}

	protected final Vector get_normal (
		final Vector normal, 
		final Point barycentric)
	{
		return 
			normal1.multiply_scalar(barycentric.get_y()).add(	
			normal2.multiply_scalar(barycentric.get_x())).add(
			normal3.multiply_scalar(barycentric.get_z())).normalize();
	}
}

