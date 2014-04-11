package sk.lovasko.lucenec.model;

import sk.lovasko.lucenec.solid.Solid;
import sk.lovasko.lucenec.group.Group;

import java.io.Serializable;
import java.util.ArrayList;

public abstract class Model implements Serializable
{
	protected ArrayList<Solid> solids;	
	protected String filename;

	protected abstract boolean load ();
	
	public final void add_to_group (Group group)
	{
		for (final Solid solid : solids)
		{
			group.add(solid);
		}
	}
}

