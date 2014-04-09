package sk.lovasko.lucenec;

import sk.lovasko.lucenec.xml;

public final class EnvironmentMap extends Solid
{
	public EnvironmentMap (final String image)
	{
		set_material(
			new FlatMaterial(
				new ImageTexture(
					image,
					BorderHandling.REPEAT,
					Interpolation.BILINEAR)));

		set_coordinate_mapper(new EnvironmentalCoordinateMapper(new Vector(0, 1, 0), new Vector(1, 0, 1)));
	}

	public final BoundingBox get_bounds ()
	{
		return BoundingBox.full();
	}

	public final Intersection intersect (final Ray ray, final double best)
	{
		if (best < Double.MAX_VALUE) 
			return Intersection.failure();

		return new Intersection(
			ray, 
			this, 
			Double.MAX_VALUE, 
			new Vector(0.0), 
			new Point(0.0), 
			new Point(0.0));    
	}

	public final double get_area()
	{
		return 0.0;
	}
}

