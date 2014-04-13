package sk.lovasko.lucenec;

import sk.lovasko.lucenec.group.Group;
import sk.lovasko.lucenec.group.SimpleGroup;
import sk.lovasko.lucenec.solid.InfinitePlane;
import sk.lovasko.lucenec.solid.Revolution;
import sk.lovasko.lucenec.solid.Instance;
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

import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;

public final class MFF1
{
	private final static Group get_scene ()
	{
		SimpleGroup scene = new SimpleGroup();

		final InfinitePlane ground = new InfinitePlane(
			new Point(0.0, -1.0, 0.0), 
			new Vector(0.0, 1.0, 0.0), 
			null, null
			);

		double[] d2 = {0,0.033, 0.102,0.034, 0.183,0, 0.374,0,
		0.399,0.03, 0.399,0.053, 0.387,0.066, 0.344,0.066, 0.268,0.083, 0.18,0.116,
		0.117,0.169, 0.087,0.223, 0.061,0.376, 0.056,0.585, 0.077,0.626,
		0.119,0.66, 0.174,0.663, 0.289,0.693, 0.39,0.772, 0.464,0.903, 0.529,1.133,
		0.565,1.463, 0.565,1.964, 0.561,1.993, 0.549,2.006, 0.532,2.007,
		0.519,1.993, 0.515,1.964, 0.513,1.48, 0.475,1.258, 0.384,1.056,
		0.177,0.965, 0,0.94};

		double[] d3 ={0, 0.2167 , 0.1259, 0.1927, 0.2254, 0.1065, 0.257, 0.0051,
		0.277, -0.0137, 0.4255, -0.0137, 0.44, 0.0122, 0.4458, 0.0792, 0.4451,
		0.1555, 0.4139, 0.2811, 0.4152, 0.3288, 0.4399, 2.3939, 0.4338, 2.5103,
		0.4104, 2.6311, 0.3762, 2.74, 0.3278, 2.8301, 0.2472, 2.9013, 0.2022,
		2.934, 0.177, 2.9697, 0.1657, 3.018, 0.15, 3.6058, 0.1569, 3.6176, 0.1663,
		3.6176, 0.1745, 3.6195, 0.1783, 3.6282, 0.1771, 3.8075, 0.1744, 3.8189,
		0.1675, 3.8234, 0.158, 3.8241, 0.1555, 3.829, 0.1557, 3.8591, 0.1544,
		3.8698, 0.1447, 3.8734, 0.1271, 3.8734, 0.1224, 3.8693, 0.1219, 3.8238,
		0.1195, 3.6169, 0.1196, 3.0112, 0.1346, 2.947, 0.1629, 2.8952, 0.2131,
		2.8588, 0.2845, 2.7957, 0.3258, 2.7187, 0.3575, 2.6177, 0.3796, 2.5036,
		0.3855, 2.3928, 0.3573, 0.2813, 0.3893, 0.1306, 0.3745, 0.0781, 0.3474,
		0.0617, 0.3218, 0.072, 0.2727, 0.1376, 0.1655, 0.2666, 0, 0.3095};

		double[] momo = {
			0.0, 0.1, 
			0.5, 0.5, 
			0.0, 0.3};

		double[] d={0,4, 1,3.3, 1,1, 0,0};

		List<Double> glass_curve_list = new ArrayList<Double>();
		for (int i = 0; i < d.length; i++)
		{
			glass_curve_list.add(new Double(d[i]));
		}

		final Revolution glass = new Revolution(glass_curve_list);
		final Instance instance = new Instance(glass);
		instance.translate(new Vector(0.0, 1.0, -0.0));
		instance.rotate(new Vector(1.0, 0.0, 0.0), -Math.PI/2.0);

		//scene.add(ground);
		scene.add(instance);
		return scene;
	}

	private final static Camera get_camera ()
	{
		return new PerspectiveCamera(
			new Point(0.7, 3.0, -10.0),
			new Vector(0.0, -0.2, 1.0),
			new Vector(0.0, 1.0, 0.0),
			Math.PI/2.0,
			1.0);
		/*
		return new PerspectiveCamera(
			new Point(0.0, 0.0, -3.0),
			new Vector(0.0, 0.0, 1.0),
			new Vector(0.0, 1.0, 0.0),
			Math.PI/2.0,
			1.0);
		*/
	}

	public static void run ()
	{
		final RenderSettings settings = new RenderSettings(
			new Size(600, 600),
			"mff-1.png",
			8,
			new Size(100, 100),
			1);

		final Primitive scene = get_scene();
		final World world = new World(scene, new RGB(0.0), get_camera());
		final Integrator integrator = new CosineIntegrator();
		integrator.set_world(world);
		Renderer.render(integrator, settings);
	}
}

