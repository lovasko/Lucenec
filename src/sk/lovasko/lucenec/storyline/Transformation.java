package sk.lovasko.lucenec.storyline;

import sk.lovasko.lucenec.math.Matrix;

public abstract class Transformation
{
	protected double start_time;
	protected double end_time;
	protected TimeDistribution time_distribution;

	protected final double normalize_time (final double time)
	{
		return (end_time - start_time) * time;
	}

	protected final void set_duration (
		double start_time,
		double end_time)
	{
		this.start_time = start_time;
		this.end_time = end_time;
	}

	protected final void set_time_distribution (
		final TimeDistribution time_distribution)
	{
		this.time_distribution = time_distribution;
	}


	public final boolean is_applied (final double time)
	{
		return ((time >= start_time) && (time <= end_time));
	}

	public abstract Matrix get_matrix (final double time);
}

