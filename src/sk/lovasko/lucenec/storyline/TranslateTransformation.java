package sk.lovasko.lucenec.storyline;

import sk.lovasko.lucenec.math.Matrix;
import sk.lovasko.lucenec.math.Double4;
import sk.lovasko.lucenec.geom.Point;
import sk.lovasko.lucenec.geom.Vector;

public final class TranslateTransformation extends Transformation
{
	private Point start_point;
	private Point end_point;

	public TranslateTransformation (
		final double start_time,
		final double end_time,
		final Point start_point,
		final Point end_point,
		final TimeDistribution time_distribution)
	{
		set_duration(start_time, end_time);
		set_time_distribution(time_distribution);
		this.start_point = start_point;
		this.end_point = end_point;
	}

	@Override
	public final Matrix get_matrix (final double time) 
	{
		final double modified_time = time_distribution.modify(time);
		final Vector difference = end_point.subtract(start_point);
		final Point translation = 
			start_point.move(difference.multiply(modified_time));

		return new Matrix(
			new Double4(1.0, 0.0, 0.0, translation.get_x()),
			new Double4(0.0, 1.0, 0.0, translation.get_y()),
			new Double4(0.0, 0.0, 1.0, translation.get_z()),
			new Double4(0.0, 0.0, 0.0, 1.0));
	}
}

