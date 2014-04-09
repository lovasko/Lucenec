package sk.lovasko.lucenec;

public interface Material
{
	public RGB get_reflectance (final Point tex_point, final Vector normal, final Vector out_dir, final Vector in_dir);
	public RGB get_emission (final Point tex_point, final Vector normal, final Vector out_dir);
	public SampleReflectance get_sample_reflectance (final Point tex_point, final Vector normal, final Vector out_dir);
	public Sampling get_sampling ();
}

