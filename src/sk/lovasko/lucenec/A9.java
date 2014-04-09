package sk.lovasko.lucenec;

public final class A9
{
	private static final void make_box (
		final Group scene, 
		final Point aaa, 
		final Vector forward, 
		final Vector left, 
		final Vector up, 
		final CoordinateMapper mapper, 
		final Material material) 
	{
		scene.add(new Quad(aaa, forward, left, mapper, material));
		scene.add(new Quad(aaa, forward, up, mapper, material));
		scene.add(new Quad(aaa, left, up, mapper, material));

		final Point bbb = aaa.move(forward).move(left).move(up);
		scene.add(new Quad(bbb, forward.negate(), left.negate(), mapper, material));
		scene.add(new Quad(bbb, forward.negate(), up.negate(), mapper, material));
		scene.add(new Quad(bbb, left.negate(), up.negate(), mapper, material));
	}


	private static final void render_cornell_box (
		final double scale, 
		final String filename, 
		final Camera cam, 
		final Material sphere_material, 
		final Material floor_material, 
		final int sample_count) 
	{
		final RenderSettings settings = new RenderSettings(
			new Size(800, 800),
			filename,
			8,
			new Size(100, 100),
			sample_count);

		final SimpleGroup scene = new SimpleGroup();
		final World world = new World(scene, new RGB(0.0), cam);

		Texture redtex = new ConstantTexture(new RGB(0.7,0.0,0.0));
		Texture greentex = new ConstantTexture(new RGB(0.0, 0.7, 0.0));
		Texture yellowtex = new ConstantTexture(new RGB(0.7, 0.7, 0.0));
		Texture bluetex = new ConstantTexture(new RGB(0.0, 0.0, 0.7));
		Texture blacktex = new ConstantTexture(new RGB(0.0));
		Texture whitetex = new ConstantTexture(new RGB(1.0));

		Material grey = new LambertianMaterial(blacktex, whitetex);
		Material yellowmat = new LambertianMaterial(blacktex, yellowtex);
		Material leftwallmaterial = new LambertianMaterial(blacktex, redtex);
		Material rightwallmaterial = new LambertianMaterial(blacktex, greentex);

		scene.add(
			new Quad(
				new Point(000.0, 000.0, 000.0).multiply(scale), 
				new Vector(550.0,000.0,000.0).multiply(scale), 
				new Vector(000.0,000.0,560.0).multiply(scale), 
				new WorldCoordinateMapper(), 
				floor_material));

		scene.add(
			new Quad(
				new Point(550.0,550.0,000.0).multiply(scale), 
				new Vector(-550.0,000.0,000.0).multiply(scale), 
				new Vector(000.0,000.0,560.0).multiply(scale), 
				new WorldCoordinateMapper(), 
				grey));

		scene.add(
			new Quad(
				new Point(000.0,000.0,560.0).multiply(scale), 
				new Vector(550.0,000.0,000.0).multiply(scale), 
				new Vector(000.0,550.0,000.0).multiply(scale), 
				new WorldCoordinateMapper(), 
				grey));

		scene.add(
			new Quad(
				new Point(000.0,000.0,000.0).multiply(scale), 
				new Vector(000.0,000.0,560.0).multiply(scale), 
				new Vector(000.0,550.0,000.0).multiply(scale), 
				new WorldCoordinateMapper(), 
				rightwallmaterial));

		scene.add(
			new Quad(
				new Point(550.0,550.0,000.0).multiply(scale), 
				new Vector(000.0,000.0,560.0).multiply(scale), 
				new Vector(000.0,-550.0,000.0).multiply(scale), 
				new WorldCoordinateMapper(), 
				leftwallmaterial));

		scene.add(
			new Sphere(
				new Point(150.0, 100.0, 240.0).multiply(scale), 
				99.0 * scale, 
				new WorldCoordinateMapper(), 
				sphere_material));

		scene.add(
			new Sphere(
				new Point(450.0, 50.0, 50.0).multiply(scale), 
				49.0 * scale, 
				new WorldCoordinateMapper(), 
				yellowmat));

		make_box(
			scene, 
			new Point(265.0, 000.1, 296.0).multiply(scale), 
			new Vector(158.0, 000.0, -049.0).multiply(scale), 
			new Vector(049.0, 000.0, 160.0).multiply(scale), 
			new Vector(000.0, 330.0, 000.0).multiply(scale), 
			new WorldCoordinateMapper(), 
			grey);

		ConstantTexture lightsrctex = new ConstantTexture(new RGB(25.0));
		Material lightsource = new LambertianMaterial(lightsrctex, blacktex);

		Quad light = new Quad(
			new Point(213*scale,549.99*scale,227*scale), 
			new Vector(130*scale,0,0), 
			new Vector(0,0,105*scale), 
			new WorldCoordinateMapper(), 
			lightsource);

		world.add_light(new AreaLight(light));
		scene.add(light);

		world.add_light(
			new PointLight(
				new Point(490*scale,159.99*scale,279.5*scale),
				new RGB(40000.0*scale*scale,0,0)));

		world.add_light(
			new PointLight(
				new Point(40*scale,159.99*scale,249.5*scale),
				new RGB(5000.0*scale*scale,30000.0*scale*scale,5000.0*scale*scale)));

		Integrator integrator = new RecursiveRayTracingIntegrator();
		integrator.set_world(world);
		Renderer.render(integrator, settings);

		System.out.println(filename + " done.");
	}

	public static final void run () 
	{
		final PerspectiveCamera cam = new PerspectiveCamera(
			new Point(0.278, 0.273, -0.800), 
			new Vector(0.0, 0.0, 1.0), 
			new Vector(0.0, 1.0, 0.0), 
			0.686, 
			1.0);

		final DOFPerspectiveCamera dofcam = new DOFPerspectiveCamera(
			new Point(0.278, 0.273, -0.800), 
			new Vector(0.0, 0.0, 1.0), 
			new Vector(0.0, 1.0, 0.0), 
			0.686, 
			1.0,
			1.025,
			0.045);

		Texture blacktex = new ConstantTexture(new RGB(0.0));
		Texture whitetex = new ConstantTexture(new RGB(1.0));
		Material floor_material1 = new LambertianMaterial(blacktex, whitetex);
		Material floor_material2 = new FuzzyMirrorMaterial(2.485, 3.433, 0.05);
		Material sphere_material1 = floor_material1;
		Material sphere_material2 = new GlassMaterial(2.0);

		render_cornell_box(0.001, "a9-1.png", cam, sphere_material1, floor_material1, 30);
		render_cornell_box(0.001, "a9-2.png", cam, sphere_material2, floor_material2, 30);
		render_cornell_box(0.001, "a9-3.png", dofcam, sphere_material2, floor_material2, 30);
		render_cornell_box(0.001, "a9-4.png", dofcam, sphere_material2, floor_material2, 1000);
	}
}

