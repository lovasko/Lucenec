package sk.lovasko.lucenec;
/*

public final class A7
{
	private static final Texture red_tex = new ConstantTexture(new RGB(0.7, 0.0, 0.0)); 
	private static final Texture green_tex = new ConstantTexture(new RGB(0.0, 0.7, 0.0)); 
	private static final Texture blue_tex = new ConstantTexture(new RGB(0.0, 0.0, 0.7)); 
	private static final Texture black_tex = new ConstantTexture(new RGB(0.0)); 
	private static final Texture white_tex = new ConstantTexture(new RGB(1.0)); 

	private static Material[] materials;

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

	public static final void cornell_box (final double scale, final RenderSettings settings)
	{
		SimpleGroup scene = new SimpleGroup();
		PerspectiveCamera cam = new PerspectiveCamera(
			new Point(278*scale, 273*scale, -800*scale), 
			new Vector(0, 0, 1), 
			new Vector(0, 1, 0), 
			0.686, 
			1.0);

		final Material grey = materials[0];
		final Material left_wall_material = materials[1];
		final Material right_wall_material = materials[2];
		final Material sphere_material = materials[3];
		final Material floor_material = materials[4];

		scene.add(
			new Quad(
				new Point(000.f,000.f,000.f).multiply_scalar(scale), 
				new Vector(550.f,000.f,000.f).multiply_scalar(scale), 
				new Vector(000.f,000.f,560.f).multiply_scalar(scale), 
				floor_material));

		scene.add(
			new Quad(
				new Point(550.f,550.f,000.f).multiply_scalar(scale), 
				new Vector(-550.f,000.f,000.f).multiply_scalar(scale), 
				new Vector(000.f,000.f,560.f).multiply_scalar(scale), 
				grey));

		scene.add(
			new Quad(
			new Point(000.f,000.f,560.f).multiply_scalar(scale), 
			new Vector(550.f,000.f,000.f).multiply_scalar(scale), 
			new Vector(000.f,550.f,000.f).multiply_scalar(scale), 
			grey));

		scene.add(
			new Quad(
			new Point(000.f,000.f,000.f).multiply_scalar(scale), 
			new Vector(000.f,000.f,560.f).multiply_scalar(scale), 
			new Vector(000.f,550.f,000.f).multiply_scalar(scale), 
			right_wall_material));

		scene.add(
			new Quad(
				new Point(550.f,550.f,000.f).multiply_scalar(scale), 
				new Vector(000.f,000.f,560.f).multiply_scalar(scale), 
				new Vector(000.f,-550.f,000.f).multiply_scalar(scale), 
				left_wall_material));

		scene.add(
			new Sphere(
				new Point(150.0f, 100.0f, 150.0f).multiply_scalar(scale), 
				99.0f*scale, 
				sphere_material));

		make_box(
			scene, 
			new Point(265.f, 000.1f, 296.f).multiply_scalar(scale), 
			new Vector(158.f, 000.f, -049.f).multiply_scalar(scale),
			new Vector(049.f, 000.f, 160.f).multiply_scalar(scale), 
			new Vector(000.f, 330.f, 000.f).multiply_scalar(scale), 
			grey);

		World world = new World(scene, new RGB(0.0), cam);
		world.add_light(
			new PointLight(
				new Point((278)*scale, 529.99f*scale,(279.5f)*scale),
				new RGB(150000.0f*scale*scale)));

		world.add_light(
			new PointLight(
				new Point((278)*scale,229.99f*scale,(-359.5f)*scale),
				new RGB(50000.0f*scale*scale)));

		world.add_light(
			new PointLight(
				new Point(490*scale,159.99f*scale,279.5f*scale),
				new RGB(40000.0f*scale*scale,0,0)));

		world.add_light(
			new PointLight(
				new Point(40*scale,159.99f*scale,249.5f*scale),
				new RGB(5000.0f*scale*scale,30000.0f*scale*scale,5000.0f*scale*scale)));

		Integrator integrator = new RecursiveRayTracingIntegrator();
		integrator.set_world(world);
		Renderer.render(integrator, settings);
	}

	public static final void run ()
	{
		materials = new Material[5];

		RenderSettings settings_1 = new RenderSettings(new Size(800, 800), "a7-1.png", 4, new Size(100, 100));
		materials[0] = new LambertianMaterial(black_tex, white_tex);
		materials[1] = new LambertianMaterial(black_tex, red_tex);
		materials[2] = new LambertianMaterial(black_tex, green_tex);
		materials[3] = new LambertianMaterial(black_tex, white_tex);
		materials[4] = new LambertianMaterial(black_tex, white_tex);
		cornell_box (0.001, settings_1);

		RenderSettings settings_2 = new RenderSettings(new Size(800, 800), "a7-2.png", 4, new Size(100, 100));
		materials[0] = new LambertianMaterial(black_tex, white_tex);
		materials[1] = new LambertianMaterial(black_tex, red_tex);
		materials[2] = new LambertianMaterial(black_tex, green_tex);
		materials[3] = new PhongMaterial(white_tex, 10.0f);
		materials[4] = new MirrorMaterial(0.0f, 0.0f);
		cornell_box (0.001, settings_2);

		RenderSettings settings_3 = new RenderSettings(new Size(800, 800), "a7-3.png", 4, new Size(100, 100));
		materials[0] = new LambertianMaterial(black_tex, white_tex);
		materials[1] = new LambertianMaterial(black_tex, red_tex);

		CombinedMaterial green = new CombinedMaterial();
		green.add(new LambertianMaterial(black_tex, green_tex), 0.5f);
		green.add(new PhongMaterial(white_tex, 2.0f), 0.5f);
		materials[2] = green;
		
		materials[3] = new MirrorMaterial(2.485, 3.433);

		MirrorMaterial mirror = new MirrorMaterial(0.0f, 0.0f);
		PhongMaterial phong = new PhongMaterial(white_tex, 10.0f);
		CombinedMaterial combined = new CombinedMaterial();
		combined.add(materials[0],0.2f);
		combined.add(phong,0.62f);
		combined.add(mirror,0.18f);
		materials[4] = combined;
		cornell_box (0.001, settings_3);

		RenderSettings settings_4 = new RenderSettings(new Size(800, 800), "a7-4.png", 4, new Size(100, 100));
		materials[0] = new LambertianMaterial(black_tex, white_tex);
		materials[1] = new LambertianMaterial(black_tex, red_tex);
		materials[2] = new LambertianMaterial(black_tex, green_tex);
		CombinedMaterial experiment = new CombinedMaterial();
		experiment.add(new CookTorranceMaterial(white_tex, 5.0, 8.0, 11.0, 10.0), 0.9);
		experiment.add(new MirrorMaterial(0.0, 0.0), 0.1);
		materials[3] = experiment;
		materials[4] = new LambertianMaterial(black_tex, white_tex);
		cornell_box (0.001, settings_4);
	}
}
*/
