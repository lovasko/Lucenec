package sk.lovasko.lucenec.material;

import sk.lovasko.lucenec.color.RGB;
import sk.lovasko.lucenec.geom.Vector;
import sk.lovasko.lucenec.geom.Point;
import sk.lovasko.lucenec.texture.Texture;

public final class LambertianMaterial implements Material
{
	private final Texture emission;
	private final Texture diffuse;

	public LambertianMaterial (final Texture _emission, final Texture _diffuse)
	{
		emission = _emission;
		diffuse = _diffuse;
	}

	public RGB get_reflectance (final Point tex_point, final Vector normal, final Vector out_dir, final Vector in_dir)
	{
		final double reflectance = 1.0 / Math.PI;
		return diffuse.get_color(tex_point).multiply_scalar(reflectance);
	}

	public RGB get_emission (final Point tex_point, final Vector normal, final Vector out_dir)
	{
		return emission.get_color(tex_point);
	}

	public SampleReflectance get_sample_reflectance (final Point tex_point, final Vector normal, final Vector out_dir)
	{
		return null;
	}

	public Sampling get_sampling ()
	{
		return Sampling.NOT_NEEDED;
	}
}

