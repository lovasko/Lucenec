package sk.lovasko.lucenec;

import sk.lovasko.lucenec.group.Group;
import sk.lovasko.lucenec.group.SimpleGroup;
import sk.lovasko.lucenec.solid.InfinitePlane;
import sk.lovasko.lucenec.geom.Point;
import sk.lovasko.lucenec.geom.Vector;
import sk.lovasko.lucenec.geom.Size;
import sk.lovasko.lucenec.camera.Camera;
import sk.lovasko.lucenec.camera.PerspectiveCamera;
import sk.lovasko.lucenec.Primitive;
import sk.lovasko.lucenec.world.World;
import sk.lovasko.lucenec.integrator.Integrator;
import sk.lovasko.lucenec.integrator.CosineIntegrator;
import sk.lovasko.lucenec.render.RenderSettings;
import sk.lovasko.lucenec.render.Renderer;
import sk.lovasko.lucenec.color.RGB;

public final class MFF1
{
	private final static Group get_scene ()
	{
		SimpleGroup scene = new SimpleGroup();

		final InfinitePlane ground = new InfinitePlane(
			new Point(0.0, -1.27, 0.0), 
			new Vector(0.0, 1.0, 0.0), 
			null, null
			);

		scene.add(ground);

		return scene;
	}

	private final static Camera get_camera ()
	{
		return new PerspectiveCamera(
			new Point(0.0, 0.0, -30.0),
			new Vector(0.0, 0.0, 1.0),
			new Vector(0.0, 1.0, 0.0),
			Math.PI/2.0,
			1.0);
	}

	public static void run ()
	{
		final RenderSettings settings = new RenderSettings(
			new Size(600, 600),
			"mff-1.png",
			8,
			new Size(100, 100),
			30);

		final Primitive scene = get_scene();
		final World world = new World(scene, new RGB(0.0), get_camera());
		final Integrator integrator = new CosineIntegrator();
		integrator.set_world(world);
		Renderer.render(integrator, settings);
	}
}

