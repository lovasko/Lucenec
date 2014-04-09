package sk.lovasko.lucenec;

public final class SquareTimeDistribution implements TimeDistribution
{
	public final double modify (final double time)
	{
		return time*time;
	}
}

