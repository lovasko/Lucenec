package sk.lovasko.lucenec.solid;

import sk.lovasko.lucenec.xml.XmlHelpers;
import sk.lovasko.lucenec.geom.Point;
import sk.lovasko.lucenec.geom.Vector;
import sk.lovasko.lucenec.geom.BoundingBox;
import sk.lovasko.lucenec.geom.Intersection;
import sk.lovasko.lucenec.geom.Ray;
import sk.lovasko.lucenec.material.FlatMaterial;
import sk.lovasko.lucenec.texture.ImageTexture;
import sk.lovasko.lucenec.math.Interpolation;
import sk.lovasko.lucenec.texture.BorderHandling;
import sk.lovasko.lucenec.mapper.EnvironmentalCoordinateMapper;

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

