package sk.lovasko.lucenec;

class Ray
{
	private Point origin;
	private Vector direction;

	Ray (Point o, Vector d)
	{
		origin = o;
		direction = d;
		direction.normalize();
	}

	public Point get_origin()
	{
		return origin;
	}

	public Vector get_direction ()
	{
		return direction;
	}

	public Point get_point (double distance)
	{
		Vector movement = new Vector(direction).multiply_scalar(distance);

		return new Point(origin.get_x() + movement.get_x(),
		                 origin.get_y() + movement.get_y(),
		                 origin.get_z() + movement.get_z());
	}
}
