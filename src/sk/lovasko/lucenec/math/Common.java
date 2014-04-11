package sk.lovasko.lucenec.math;

public final class Common
{
	public static double fractional (double number)
	{
		int decimal = (int)number;
		return (number - decimal);
	}

	public static double lerp (final double t, final double a, final double b)
	{
		return a + (b-a)*t;
	}

	public static double abs_fractional (double number)
	{
    double fr = fractional(number);

		if (fr >= 0.0) 
			return fr;

		fr += 1.0;
		if (fr >= 1.0) //can happen due to epsilon errors
			return 0.0;

		return fr;
	}
}

