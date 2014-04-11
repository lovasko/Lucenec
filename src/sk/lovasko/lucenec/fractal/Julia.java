package sk.lovasko.lucenec;

import sk.lovasko.lucenec.geom.Point;
import sk.lovasko.lucenec.geom.Vector;

public class Julia
{
	public static int MAX_ITER = 512;

	public static int julia (Point v, Point c)
	{
		Vector p = v.to_vector();
		int iter;

		for (iter = 0; iter < MAX_ITER; iter++)
		{
			if (p.lensqr() > 1.0e+8)
				break;
	
			Vector q = p.duplicate();
			q.set_y(-p.get_y());

			Vector new_p = new Vector(0.0);
			new_p.set_x(p.dot_product(q));
			new_p.set_y(Vector.cross_product(q, p).get_z());
			new_p.set_z(0.0);

			p = new_p.add(c.to_vector());
		}

		return iter;
	}
}

