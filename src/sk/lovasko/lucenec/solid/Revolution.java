package sk.lovasko.lucenec.solid;

import sk.lovasko.lucenec.geom.Point;
import sk.lovasko.lucenec.geom.Vector;
import sk.lovasko.lucenec.geom.BoundingBox;
import sk.lovasko.lucenec.geom.Intersection;
import sk.lovasko.lucenec.geom.Ray;
import sk.lovasko.lucenec.math.Matrix;

import java.util.List;
import java.util.ArrayList;

public final class Revolution extends Solid
{
	private List<Double> values;
	private BoundingBox bounding_box;

	public Revolution (List<Double> values)
	{
		List<Double> values_copy = new ArrayList<Double>(values);
		this.values = new ArrayList<Double>();

		if (values.get(1) < values.get(values.size()-1))
		{
			for (int i = values.size()-1; i >= 0 ; i-=2)
			{
				values_copy.set(values.size()-i+0, values.get(i));
				values_copy.set(values.size()-i-1, values.get(i-1));
			}
		}

		if (values_copy.get(0) != 0.0)
		{
			this.values.add(0.0);
			this.values.add(values_copy.get(1));
		}

		for (int i = 0; i < values_copy.size(); i++)
		{
			this.values.add(values_copy.get(i));
		}

		if (this.values.get(this.values.size()-2) != 0.0)
		{
			this.values.add(0.0);
			this.values.add(values_copy.get(values_copy.size()-1));
		}

		bounding_box = BoundingBox.full();
	}

	public BoundingBox get_bounds ()
	{
		return bounding_box;
	}

	public double get_area ()
	{
		return Double.MAX_VALUE; 
	}

	public final Intersection intersect (final Ray ray, final double best)
	{
		double best_distance = best;
		Intersection result = Intersection.failure();

		final Point origin = ray.get_origin();
		final Vector direction = ray.get_direction();
	
		final double tmpa = direction.get_x() * direction.get_x() +
		                    direction.get_y() * direction.get_y();
		final double tmpb = (origin.get_x() * direction.get_x() +
		                     origin.get_y() * direction.get_y()) * 2.0;
		final double tmpc = origin.get_x() * origin.get_x() +
		                    origin.get_y() * origin.get_y();

		for (int k = 0; k < values.size() - 2; k += 2)
		{
			final double maxz = Math.max(values.get(k+1), values.get(k+3));
			final double minz = Math.min(values.get(k+1), values.get(k+3));

			if (maxz == minz)
			{
				final double minr = Math.min(values.get(k), values.get(k+2));
				final double maxr = Math.max(values.get(k), values.get(k+2));

				if (direction.get_z() == 0.0)
				{
					continue;
				}

				final double t3 = (maxz - origin.get_z()) / direction.get_z();
				final double x1 = origin.get_x() + direction.get_x() * t3;
				final double y1 = origin.get_y() + direction.get_y() * t3;
				final double x2 = x1 * x1;
				final double y2 = y1 * y1;

				if (x2 + y2 > minr * minr && 
				    x2 + y2 < maxr * maxr)
				{
					if (t3 < best_distance)
					{
						result = new Intersection(ray, this, t3, get_normal(k,
						    ray.get_point(t3)), ray.get_point(t3), null);
						best_distance = t3;
					}
				}
				
			}
			else
			{
				double s = values.get(k) - values.get(k+2);
				if (values.get(k+1) - values.get(k+3) == 0.0)
					s = 0.0;
				else
					s = s / (values.get(k+1) - values.get(k+3));

				double q = values.get(k) - s * values.get(k+1);
				double a = tmpa - direction.get_z() * direction.get_z() * s * s;
				double b = tmpb - 2.0 * s * s * origin.get_z() * direction.get_z() -
				                  2.0 * s * q * direction.get_z();
				double c = tmpc - s * s * origin.get_z() * origin.get_z() - q * q - 2 *
				                  s * q * origin.get_z();

				double D = b * b - 4.0 * a * c;
				if (D < 0.0)
				{
					continue;
				}
				D = Math.sqrt(D);
				double t0 = (-b - D) / (2.0 * a);
				double t1 = (-b + D) / (2.0 * a);

				double tz = origin.get_z() + t0 * direction.get_z();
				if ( tz > minz && tz < maxz)
				{
					if (t0 < best_distance)
					{
						result = new Intersection(ray, this, t0, get_normal(k,
						    ray.get_point(t0)), ray.get_point(t0), null);
						best_distance = t0;
					}
				}

				tz = origin.get_z() + t1 * direction.get_z();
				if (tz > minz && tz < maxz)
				{
					if (t1 < best_distance)
					{
						result = new Intersection(ray, this, t1, get_normal(k,
						    ray.get_point(t1)), ray.get_point(t1), null);
						best_distance = t1;
					}
				}
			}
		}

		return result;
	}

	private final Vector get_normal (int k, Point p)
	{
		Vector normal;
		final double phi = Math.atan2(p.get_y(), p.get_x());

		if (values.get(k+0).doubleValue() == values.get(k+2).doubleValue() && 
		    values.get(k+1).doubleValue()  < values.get(k+3).doubleValue())
		{
			normal = new Vector(-1.0, 0.0, 0.0);
		}
		else if (values.get(k+0).doubleValue() == values.get(k+2).doubleValue())
		{ 
			normal = new Vector(1.0, 0.0, 0.0);
		}
		else if (values.get(k+1).doubleValue() == values.get(k+3).doubleValue() && 
		         values.get(k+0).doubleValue()  < values.get(k+2).doubleValue())
		{
			normal = new Vector(0.0, 0.0, 1.0);
		}
		else if (values.get(k+1).doubleValue() == values.get(k+3).doubleValue())
		{
			normal = new Vector(0.0, 0.0, -1.0);
		}
		else
		{
			double h = values.get(k+3).doubleValue() - values.get(k+1).doubleValue();
			double r = values.get(k+2).doubleValue() - values.get(k+0).doubleValue();
			normal = new Vector(-h, 0.0, r);
		}

		normal = normal.normalize();
		normal = normal.rotate(new Vector(0.0, 0.0, 1.0), phi);

		Matrix local = Matrix.local(normal);
		normal = local.to_world(normal);

		return normal;
	}
}

