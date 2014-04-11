package sk.lovasko.lucenec.storyline;

public final class SquareTimeDistribution implements TimeDistribution
{
	public final double modify (final double time)
	{
		return time*time;
	}
}

