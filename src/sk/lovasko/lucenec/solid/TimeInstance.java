package sk.lovasko.lucenec.solid;

import sk.lovasko.lucenec.Primitive;
import sk.lovasko.lucenec.storyline.Transformation;
import sk.lovasko.lucenec.storyline.TranslateTransformation;
import sk.lovasko.lucenec.storyline.TimeDistribution;
import sk.lovasko.lucenec.math.Matrix;
import sk.lovasko.lucenec.geom.Ray;
import sk.lovasko.lucenec.geom.Intersection;
import sk.lovasko.lucenec.geom.Point;
import sk.lovasko.lucenec.geom.Vector;
import sk.lovasko.lucenec.geom.BoundingBox;
import sk.lovasko.lucenec.material.Material;
import sk.lovasko.lucenec.mapper.CoordinateMapper;

import java.util.List;
import java.util.ArrayList;

public final class TimeInstance extends Solid
{
	private final Primitive primitive;
	private List<Transformation> transformations;

	public TimeInstance (final Primitive primitive)
	{
		this.primitive = primitive;
		transformations = new ArrayList<Transformation>();
	}

	public final void add_translate_transformation (
		final double start_time,
		final double end_time,
		final Point start_point,
		final Point end_point,
		final TimeDistribution time_distribution)
	{
		transformations.add(
			new TranslateTransformation(
				start_time, 
				end_time, 
				start_point, 
				end_point,
				time_distribution));
	}

	private final Instance construct_instance ()
	{
		Matrix matrix = Matrix.identity();
		for (final Transformation transformation : transformations)
		{
			if (transformation.is_applied(time))
			{
				matrix = matrix.product(transformation.get_matrix(time));
			}
		}

		Instance instance = new Instance(primitive);
		instance.set_matrix(matrix);
		
		return instance;
	}

	@Override
	public final Intersection intersect (final Ray ray, final double best)
	{
		final Intersection intersection = 
			construct_instance().intersect(ray, best);

		intersection.set_solid(this);
		return intersection;
	}
	
	@Override
	public final double get_area()
	{
		return 1.0;
	}

	@Override
	public final BoundingBox get_bounds ()
	{
		return construct_instance().get_bounds();
	}

	@Override
	public final Material get_material ()
	{
		return primitive.get_material();
	}

	@Override
	public final CoordinateMapper get_coordinate_mapper ()
	{
		return primitive.get_coordinate_mapper();
	}

	@Override
	public final String toString ()
	{
		return "timeInstance\n\t" + primitive;
	}
}

