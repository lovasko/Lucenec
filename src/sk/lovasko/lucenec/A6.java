package sk.lovasko.lucenec;
/*

public final class A6
{
	private static final void make_box (
		final Group scene, 
		final Point aaa, 
		final Vector forward, 
		final Vector left, 
		final Vector up, 
		final Material material) 
	{
			scene.add(new Quad(aaa, forward, left, material));
			scene.add(new Quad(aaa, forward, up, material));
			scene.add(new Quad(aaa, left, up, material));

			Point bbb = aaa.move(forward).move(left).move(up);
			scene.add(new Quad(bbb, forward.negate(), left.negate(), material));
			scene.add(new Quad(bbb, forward.negate(), up.negate(), material));
			scene.add(new Quad(bbb, left.negate(), up.negate(), material));
	}

	private static final void cornell_box_1 (final double scale, final RenderSettings settings) 
	{
		SimpleGroup scene = new SimpleGroup();

		PerspectiveCamera cam = new PerspectiveCamera(
			new Point(278.0*scale, 273.0*scale, -800.0*scale), 
			new Vector(0.0, 0.0, 1.0), 
			new Vector(0.0, 1.0, 0.0), 
			0.686, 
			1.0);

		DummyMaterial mat = new DummyMaterial();
		Integrator integrator = new RayTracingIntegrator(); 

		scene.add(
			new Quad(
				new Point(0.0, 0.0, 0.0).multiply_scalar(scale), 
				new Vector(550.0, 0.0, 0.0).multiply_scalar(scale), 
				new Vector(0.0, 0.0, 560.0).multiply_scalar(scale), 
				mat)); 

		scene.add(
			new Quad(
				new Point(550.0, 550.0, 0.0).multiply_scalar(scale), 
				new Vector(-550.0, 0.0, 0.0).multiply_scalar(scale), 
				new Vector(0.0, 0.0, 560.0).multiply_scalar(scale), 
				mat)); 

		scene.add(
			new Quad(
				new Point(0.0, 0.0, 560.0).multiply_scalar(scale),
				new Vector(550.0, 0.0, 0.0).multiply_scalar(scale), 
				new Vector(0.0, 550.0, 0.0).multiply_scalar(scale),
				mat)); 

		scene.add(
				new Quad(
				new Point(0.0, 0.0, 0.0).multiply_scalar(scale),
				new Vector(0.0, 0.0, 560.0).multiply_scalar(scale),
				new Vector(0.0, 550.0, 0.0).multiply_scalar(scale),
				mat)); 

		scene.add(
			new Quad(
				new Point(550.0, 550.0, 0.0).multiply_scalar(scale),
				new Vector(0.0, 0.0, 560.0).multiply_scalar(scale),
				new Vector(0.0, -550.0, 0.0).multiply_scalar(scale),
				mat)); 

		make_box(
			scene, 
			new Point(82.0, 0.1, 225.0).multiply_scalar(scale), 
			new Vector(158.0, 0.0, 47.0).multiply_scalar(scale), 
			new Vector(48.0, 0.0, -160.0).multiply_scalar(scale), 
			new Vector(0.0, 165.0, 0.0).multiply_scalar(scale), 
			mat);

		make_box(
			scene, 
			new Point(265.0, 0.1, 296.0).multiply_scalar(scale), 
			new Vector(158.0, 0.0, -49.0).multiply_scalar(scale), 
			new Vector(49.0, 0.0, 160.0).multiply_scalar(scale), 
			new Vector(0.0, 330.0, 0.0).multiply_scalar(scale), 
			mat);

		World world = new World(scene, new RGB(0.0), cam); 
		world.add_light(new PointLight(new Point(288.0*scale,529.99*scale,279.5*scale), new RGB(40000.0*scale*scale)));
		world.add_light(new PointLight(new Point(490.0*scale,329.99*scale,279.5*scale), new RGB(60000.0*scale*scale,0,0)));
		world.add_light(new PointLight(new Point(40.0*scale,329.99*scale,279.5*scale), new RGB(0,60000.0*scale*scale,0)));

		integrator.set_world(world);
		Renderer.render(integrator, settings);
	}

	private static final void cornell_box_2 (final double scale, final RenderSettings settings) 
	{
		SimpleGroup scene = new SimpleGroup();

		PerspectiveCamera cam = new PerspectiveCamera(
			new Point(278.0*scale, 273.0*scale, -800.0*scale), 
			new Vector(0.0, 0.0, 1.0), 
			new Vector(0.0, 1.0, 0.0), 
			0.686, 
			1.0);

		DummyMaterial mat = new DummyMaterial();

		scene.add(
			new Quad(
				new Point(0.0, 0.0, 0.0).multiply_scalar(scale), 
				new Vector(550.0, 0.0, 0.0).multiply_scalar(scale), 
				new Vector(0.0, 0.0, 560.0).multiply_scalar(scale), 
				mat));

		scene.add(
			new Quad(
				new Point(0.0, 550.0, 0.0).multiply_scalar(scale), 
				new Vector(550.0, 0.0, 0.0).multiply_scalar(scale), 
				new Vector(0.0, 0.0, 560.0).multiply_scalar(scale), 
				mat)); 

		scene.add(
			new Quad(
				new Point(0.0, 0.0, 560.0).multiply_scalar(scale), 
				new Vector(550.0, 0.0, 0.0).multiply_scalar(scale), 
				new Vector(0.0, 550.0, 0.0).multiply_scalar(scale), 
				mat)); 

		scene.add(
			new Quad(
				new Point(0.0, 0.0, 0.0).multiply_scalar(scale), 
				new Vector(0.0, 0.0, 560.0).multiply_scalar(scale), 
				new Vector(0.0, 550.0, 0.0).multiply_scalar(scale), 
				mat));

		scene.add(
			new Quad(
				new Point(550.0, 0.0, 0.0).multiply_scalar(scale), 
				new Vector(0.0, 0.0, 560.0).multiply_scalar(scale), 
				new Vector(0.0, 550.0, 0.0).multiply_scalar(scale), 
				mat));

		make_box(
			scene, 
			new Point(82.0, 0.1, 225.0).multiply_scalar(scale), 
			new Vector(158.0, 0.0, 47.0).multiply_scalar(scale), 
			new Vector(48.0, 0.0, -160.0).multiply_scalar(scale), 
			new Vector(0.0, 165.0, 0.0).multiply_scalar(scale), 
			mat);

		make_box(
			scene, 
			new Point(265.0, 0.1, 296.0).multiply_scalar(scale), 
			new Vector(158.0, 0.0, -049.0).multiply_scalar(scale), 
			new Vector(49.0, 0.0, 160.0).multiply_scalar(scale), 
			new Vector(0.0, 330.0, 0.0).multiply_scalar(scale), 
			mat);

		World world = new World(scene, new RGB(0.0), cam);
		world.add_light(
			new PointLight(
				new Point(288*scale,529.99*scale,279.5*scale), 
				new RGB(20000.0*scale*scale)));

		world.add_light(
			new SpotLight(
				new Point(70.0, 400.0, 230.0).multiply_scalar(scale), 
				new Vector(0.0, -1.0, 0.0),  
				Math.PI/4.0, 
				8.0, 
				new RGB(0,60000.0*scale*scale,0)));

		world.add_light(
			new SpotLight(
				new Point(520.0, 300.0, 230.0).multiply_scalar(scale), 
				new Vector(-1.0, -1.0, 0.0),
				Math.PI/3.0, 
				3.0, 
				new RGB(60000.0*scale*scale,0,0)));

		world.add_light(
			new DirectionalLight(
				new Vector(0.2,-0.5,0.5).normalize(), 
				new RGB(0.25, 0.25, 0.5)));

		RayTracingIntegrator integrator = new RayTracingIntegrator();
		integrator.set_world(world);
		Renderer.render(integrator, settings);
}

	public static final void run ()
	{
		RenderSettings settings_1 = new RenderSettings(new Size(400, 400), "a6-1.png", 4, new Size(100, 100));
		RenderSettings settings_2 = new RenderSettings(new Size(400, 400), "a6-2.png", 4, new Size(100, 100));
		RenderSettings settings_3 = new RenderSettings(new Size(400, 400), "a6-3.png", 4, new Size(100, 100));
		RenderSettings settings_4 = new RenderSettings(new Size(400, 400), "a6-4.png", 4, new Size(100, 100));

		cornell_box_1(0.001, settings_1);
		cornell_box_1(0.01, settings_2);
		cornell_box_2(0.001, settings_3);
		cornell_box_2(0.01, settings_4);
	}
}
*/
