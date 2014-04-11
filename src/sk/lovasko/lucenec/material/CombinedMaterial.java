package sk.lovasko.lucenec.material;

import sk.lovasko.lucenec.color.RGB;
import sk.lovasko.lucenec.geom.Vector;
import sk.lovasko.lucenec.geom.Point;

import java.util.ArrayList;

public final class CombinedMaterial implements Material
{
	private final ArrayList<Material> materials;
	private final ArrayList<Double> weights;
	private int count;

	public CombinedMaterial ()
	{
		materials = new ArrayList<Material>();
		weights = new ArrayList<Double>();
		count = 0;
	}

	public RGB get_reflectance (final Point tex_point, final Vector normal, final Vector out_dir, final Vector in_dir)
	{
		RGB reflectance = new RGB(0.0);

		for (int i = 0; i < count; i++)
		{
			if (materials.get(i).get_sampling() == Sampling.ALL) 
			{
				continue;
			}

			reflectance = reflectance.add(materials.get(i).get_reflectance(tex_point, normal, out_dir, in_dir).multiply_scalar(weights.get(i)));
		}

		return reflectance;
	}

	public RGB get_emission (final Point tex_point, final Vector normal, final Vector out_dir)
	{
		RGB emission = new RGB(0.0);

		for (int i = 0; i < count; i++)
		{
			emission = emission.add(materials.get(i).get_emission(tex_point, normal, out_dir).multiply_scalar(weights.get(i)));
		}

		return emission;
	}

	public SampleReflectance get_sample_reflectance (final Point tex_point, final Vector normal, final Vector out_dir)
	{
		for (int i = 0; i < count; i++)
		{
			Sampling sampling = materials.get(i).get_sampling();
			if (sampling == Sampling.ALL || sampling == Sampling.SECONDARY)
			{
				SampleReflectance sr = materials.get(i).get_sample_reflectance(tex_point, normal, out_dir);
				return sr.multiply_scalar_reflectance(weights.get(i));
			}
		}

		return null;
	}

	public Sampling get_sampling ()
	{
		boolean all = false;
		boolean not_needed = false;

		for (int i = 0; i < count; i++)
		{
			Sampling sampling = materials.get(i).get_sampling();

			switch (sampling)
			{
				case NOT_NEEDED:
					not_needed = true;
				break;

				case ALL:
					all = true;
				break;

				case SECONDARY:
					return Sampling.SECONDARY;

				default:
					return null;
			}
		}

		// TODO make this a small cool true/false table
		if (all && not_needed)
		{
			return Sampling.SECONDARY;
		}

		if (all)
		{
			return Sampling.ALL;
		}

		return Sampling.NOT_NEEDED;
	}

	public final void add (final Material material, final double weight)
	{
		materials.add(material);
		weights.add(weight);
		count += 1;
	}
}

