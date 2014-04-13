package sk.lovasko.lucenec.geom;

import sk.lovasko.lucenec.solid.Solid;

public class Intersection
{
	private Ray ray;
	private Solid solid;
	private double distance;
	private Vector normal;
	private Point hit_point;  
	private Point local;

	private boolean fail;

	private Intersection () { }

	public Intersection (
		final Ray _ray,
		final Solid _solid,
		final double _distance,
		final Vector _normal,
		final Point _hit_point,
		final Point _local)
	{
		ray = _ray;
		solid = _solid;
		distance = _distance;
		normal = _normal;
		hit_point = _hit_point;
		local = _local;

		fail = false;
	}

	public static Intersection failure ()
	{
		Intersection intersection = new Intersection();
		intersection.fail = true;

		return intersection;
	}

	public boolean hit ()
	{
		return !fail;
	}

	public void set_solid (Solid _solid)
	{
		solid = _solid;
	}

	public Solid get_solid ()
	{
		return solid;
	}

	public double get_distance ()
	{
		return distance;
	}

	public Ray get_ray ()
	{
		return ray;
	}

	public Vector get_normal ()
	{
		return normal;
	}

	public Point get_hit_point ()
	{
		return hit_point;
	}

	public Point get_local ()
	{
		return local;
	}

	public String toString()
	{
		return (fail ? "Intersection: fail" : "Intersection: " + distance);
	}
}

