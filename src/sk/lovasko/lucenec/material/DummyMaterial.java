package sk.lovasko.lucenec.material;

import sk.lovasko.lucenec.color.RGB;
import sk.lovasko.lucenec.geom.Vector;
import sk.lovasko.lucenec.geom.Point;

public final class DummyMaterial implements Material
{
	public final RGB get_reflectance (final Point tex_point, final Vector normal, final Vector out_dir, final Vector in_dir)
	{
		return new RGB(1.0);	
	}

	public final RGB get_emission (final Point tex_point, final Vector normal, final Vector out_dir)
	{
		return new RGB(0.0);	
	}
	
	public SampleReflectance get_sample_reflectance (final Point tex_point, final Vector normal, final Vector out_dir)
	{
		return null;
	}

	public Sampling get_sampling ()
	{
		return null;
	}
}

