package sk.lovasko.lucenec.integrator;

import sk.lovasko.lucenec.color.RGB;
import sk.lovasko.lucenec.world.World;
import sk.lovasko.lucenec.geom.Ray;

public abstract class Integrator
{
	protected World world;
	protected int depth;

	public abstract RGB get_radiance (final Ray ray);	

	public final void set_world (final World _world)
	{
		world = _world;
	}

	public final World get_world ()
	{
		return world;
	}

	public final void reset_depth ()
	{
		depth = 0;
	}
}

