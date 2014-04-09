package sk.lovasko.lucenec;
/*
public final class A5
{
	private static final void add_tree (final Group group, 
	                                    final int trunk_tessel, 
	                                    final double trunk_height, 
	                                    final double trunk_radius, 
	                                    final double crown_height, 
	                                    final double crown_radius, 
	                                    final int crown_tessel, 
	                                    final int crown_steps)
	{
		// TODO why are these here?
		Vector ex = new Vector(1.0, 0.0, 0.0);
		Vector ey = new Vector(0.0, 0.0, 1.0);
		Vector ez = new Vector(0.0, 1.0, 0.0);

		double trunk_step = (2.0 * Math.PI) / trunk_tessel;
		double crown_step = (2.0 * Math.PI) / crown_tessel;

		for (int i = 0; i < trunk_tessel; ++i) 
		{
			double angle = i * trunk_step;
			double angleNext = (i + 1) * trunk_step;

			Point start = new Point(trunk_radius * Math.sin(angle), 
			                        0.0, 
			                        trunk_radius * Math.cos(angle));

			Point end = new Point(trunk_radius * Math.sin(angleNext), 
			                      0.0, 
			                      trunk_radius * Math.cos(angleNext));

			group.add(new Quad(start, end.subtract(start), new Vector(0.0, trunk_height, 0.0)));
		}

		for (int i = 0; i < crown_steps; ++i) 
		{
			double height = trunk_height + i * crown_height / crown_steps;
			double x = (double)(crown_steps - i - 1) / crown_steps;
			double step_radius = crown_radius * Math.sin(x * Math.PI);

			if (step_radius > 0.0) 
			{
				double sphere_radius = 1.5 * Math.PI * step_radius / crown_tessel;

				for (int j = 0; j < crown_tessel; ++j) 
				{
					double angle = j * crown_step;
					group.add(new Sphere(new Point(step_radius * Math.sin(angle), height, step_radius * Math.cos(angle)), 
					                     sphere_radius));
				}
			}

			group.add(new Sphere(new Point(0.0, trunk_height, 0.0), trunk_radius));
			group.add(new Sphere(new Point(0.0, trunk_height + crown_height - crown_step * 1.5, 0.0), trunk_radius));
		}
	}

	public static final void run ()
	{
		World world = new World();
		PerspectiveCamera camera = new PerspectiveCamera(new Point(-3.75, 20.0, 40.0), 
		                                                 new Vector(0.1, -0.5, -1.0), 
		                                                 new Vector(0.0, 1.0, 0.0), 
		                                                 Math.PI / 4.0, 
		                                                 4.0 / 3.0);
		RGB bgcolor = new RGB(0.0);
		CosineIntegrator integrator = new CosineIntegrator();
		integrator.set_world(world);

		SimpleGroup scene = new SimpleGroup();

		SimpleGroup tree = new SimpleGroup();
		add_tree(tree, 16, 3.0, 0.5, 5.0, 2.0, 8, 8);

		Instance normal = new Instance(tree);
		scene.add(normal);

		double circle_radius = 18.0;
		for (int i = 0; i < 11; ++i) 
		{
			double angle = 2.0 * i * Math.PI / 11.0;
			Instance itree = new Instance(tree);
			itree.scale(new Vector(1.0, 1.0 + Math.sin(i * 1.0) * 0.3, 1.0));
			itree.translate(new Vector(Math.sin(angle) * circle_radius, 
			                           0.0, 
			                           Math.cos(angle) * circle_radius));
			scene.add(itree);
		}

		Instance broken = new Instance(tree);
		broken.rotate(new Vector(0.3, 0.0, 0.7), -1.0);
		broken.translate(new Vector(5.0, -0.5, 0.0));
		scene.add(broken);

		Instance broken2 = new Instance(tree);
		broken2.rotate(new Vector(0.7, 0.0, -0.3), -1.0);
		broken2.translate(new Vector(5.0, -0.5, 0.0));
		scene.add(broken2);

		Instance flat = new Instance(tree);
		flat.scale(new Vector(0.3, 1.5, 1.5));
		flat.translate(new Vector(-11.0, 0.0, 0.0));
		scene.add(flat);

		scene.add(new InfinitePlane(new Point(0.0, 0.0, 0.0), new Vector(0.0, 1.0, 0.0)));

		world.set_scene(scene);
		world.set_camera(camera);
		world.set_integrator(integrator);
		world.set_background_color(bgcolor);

		world.render(800, 600, "a5-1.png", "png");
	}
}
*/
