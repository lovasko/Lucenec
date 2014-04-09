package sk.lovasko.lucenec;
/*

public final class A8
{
	private static final Group get_scene_1 ()
	{
		SimpleGroup scene = new SimpleGroup();      

		ImageTexture clamp_tex = 
			new ImageTexture(
				"textures/stones_diffuse.png", 
				BorderHandling.CLAMP, 
				Interpolation.NEAREST);
		FlatMaterial clamp = new FlatMaterial(clamp_tex);

		ImageTexture mirror_tex = 
			new ImageTexture(
				"textures/stones_diffuse.png", 
				BorderHandling.MIRROR, 
				Interpolation.BILINEAR);
		FlatMaterial mirror = new FlatMaterial(mirror_tex);

		scene.add(
			new InfinitePlane(
				new Point(0.0, 0.0, -0.004), 
				new Vector(-0.02, -0.02, 1.0), 
				clamp));

		scene.add(
			new InfinitePlane(
				new Point(0.0, 0.0, -0.001), 
				new Vector(0.02, -0.02, 1.0), 
				mirror));

		return scene;
	}

	private static final Group get_scene_2 ()
	{
		SimpleGroup scene = new SimpleGroup();      

		ImageTexture near_tex = new ImageTexture(
			"textures/stones_diffuse.png", 
			BorderHandling.REPEAT, 
			Interpolation.NEAREST);
		FlatMaterial near = new FlatMaterial(near_tex);

		ImageTexture inter_tex = new ImageTexture(
			"textures/stones_diffuse.png", 
			BorderHandling.REPEAT, 
			Interpolation.BILINEAR);
		FlatMaterial inter = new FlatMaterial(inter_tex);

		scene.add(
			new InfinitePlane(
				new Point(0.1, -1.5, 0.15), 
				new Vector(0.1, 0.0, 1.0), 
				near));

		scene.add(
			new InfinitePlane(
				new Point(0.1, -1.5, 0.15), 
				new Vector(-0.1, 0.0, 1.0), 
				inter));

		return scene;
	}

	private static final Group get_scene_3 ()
	{
		SimpleGroup scene = new SimpleGroup();

		CheckerboardTexture checkerboard_tex = 
			new CheckerboardTexture(
				new RGB(1.0, 0.9, 0.7), 
				new RGB(0.2, 0.2, 0.0),
				0.5);
		FlatMaterial checkerboard = new FlatMaterial(checkerboard_tex);

		PerlinNoiseTexture perlin_tex = 
			new PerlinNoiseTexture(
				new RGB(1.0, 1.0, 0.9), 
				new RGB(0.5, 0.5, 1.0));
		perlin_tex.add_octave(0.5,    5.0);
		perlin_tex.add_octave(0.25,  10.0);
		perlin_tex.add_octave(0.125, 20.0);
		perlin_tex.add_octave(0.125, 40.0);
		FlatMaterial perlin = new FlatMaterial(perlin_tex);

		scene.add(
			new InfinitePlane(
				new Point(0.0, 0.0, -0.018), 
				new Vector(0.01, 0.0, 1.0), 
				checkerboard));

		scene.add(
			new InfinitePlane(
				new Point(0.0, 0.0, -0.02), 
				new Vector(-0.01, 0.0, 1.0), 
				perlin));
		
		return scene;
	}

	private static final Group get_scene_4 ()
	{
		final double scale = 0.001;
		ImageTexture whitetex = new ImageTexture(
			"textures/stones_diffuse.png",
			BorderHandling.REPEAT,
			Interpolation.BILINEAR);

		ConstantTexture redtex = new ConstantTexture(new RGB(0.7, 0.0, 0.0));
		ConstantTexture greentex = new ConstantTexture(new RGB(0.0, 0.7, 0.0));
		ConstantTexture blacktex = new ConstantTexture(new RGB(0.0));
		ConstantTexture lightsrctex = new ConstantTexture(new RGB(1.0));

		LambertianMaterial white = new LambertianMaterial(blacktex, whitetex);
		LambertianMaterial green = new LambertianMaterial(blacktex, greentex);
		LambertianMaterial red = new LambertianMaterial(blacktex, redtex);

		SimpleGroup scene = new SimpleGroup();
		scene.add(
			new Triangle(
				new Point(000.0,000.0,000.0).multiply_scalar(scale), 
				new Point(550.0,000.0,000.0).multiply_scalar(scale), 
				new Point(000.0,000.0,560.0).multiply_scalar(scale), 
				new WorldCoordinateMapper(), 
				white)); 

		scene.add(
			new Triangle(
				new Point(550.0,000.0,560.0).multiply_scalar(scale), 
				new Point(550.0,000.0,000.0).multiply_scalar(scale), 
				new Point(000.0,000.0,560.0).multiply_scalar(scale), 
				new WorldCoordinateMapper(), 
				white)); 

		scene.add(
			new Triangle(
				new Point(000.0,550.0,000.0).multiply_scalar(scale), 
				new Point(550.0,550.0,000.0).multiply_scalar(scale), 
				new Point(000.0,550.0,560.0).multiply_scalar(scale), 
				new WorldCoordinateMapper(),
				white)); 

		scene.add(
			new Triangle(
				new Point(550.0,550.0,560.0).multiply_scalar(scale), 
				new Point(550.0,550.0,000.0).multiply_scalar(scale), 
				new Point(000.0,550.0,560.0).multiply_scalar(scale), 
				new WorldCoordinateMapper(), 
				white)); 

		scene.add(
			new Triangle(
				new Point(000.0,000.0,560.0).multiply_scalar(scale), 
				new Point(550.0,000.0,560.0).multiply_scalar(scale), 
				new Point(000.0,550.0,560.0).multiply_scalar(scale), 
				new WorldCoordinateMapper(), 
				white)); 

		scene.add(
			new Triangle(
				new Point(550.0,550.0,560.0).multiply_scalar(scale), 
				new Point(550.0,000.0,560.0).multiply_scalar(scale), 
				new Point(000.0,550.0,560.0).multiply_scalar(scale), 
				new WorldCoordinateMapper(), 
				white)); 

		scene.add(
			new Triangle(
				new Point(000.0,000.0,000.0).multiply_scalar(scale), 
				new Point(000.0,000.0,560.0).multiply_scalar(scale), 
				new Point(000.0,550.0,000.0).multiply_scalar(scale), 
				new WorldCoordinateMapper(), 
				green));

		scene.add(
			new Triangle(
				new Point(000.0,550.0,560.0).multiply_scalar(scale), 
				new Point(000.0,000.0,560.0).multiply_scalar(scale), 
				new Point(000.0,550.0,000.0).multiply_scalar(scale), 
				new WorldCoordinateMapper(), 
				green));

		scene.add(
			new Triangle(
				new Point(550.0,000.0,000.0).multiply_scalar(scale), 
				new Point(550.0,000.0,560.0).multiply_scalar(scale), 
				new Point(550.0,550.0,000.0).multiply_scalar(scale), 
				new WorldCoordinateMapper(), 
				red)); 

		scene.add(
			new Triangle(
				new Point(550.0,550.0,560.0).multiply_scalar(scale), 
				new Point(550.0,000.0,560.0).multiply_scalar(scale), 
				new Point(550.0,550.0,000.0).multiply_scalar(scale), 
				new WorldCoordinateMapper(), 
				red)); 

		scene.add(
			new Sphere(
				new Point(200.0,100.0,300.0).multiply_scalar(scale), 
				150.0*scale, 
				new WorldCoordinateMapper(), 
				white));

		return scene;
	}

	private static final Group get_scene_5 (CoordinateMapper m1, CoordinateMapper m2)
	{
		final double scale = 0.001;

		SimpleGroup scene = new SimpleGroup();

		CheckerboardTexture redtex = new CheckerboardTexture(
			new RGB(0.7, 0.1, 0.1), 
			new RGB(0.3, 0.1, 0.1),
			0.5);

    PerlinNoiseTexture greentex = new PerlinNoiseTexture(
			new RGB(0.0, 0.7, 0.0), 
			new RGB(0.0, 0.2, 0.4));
		greentex.add_octave(1.0, 3.0);
		greentex.add_octave(0.5, 6.0);
		greentex.add_octave(0.25, 12.0);

		ConstantTexture blacktex = new ConstantTexture(new RGB(0.0));
		ConstantTexture lightsrctex = new ConstantTexture(new RGB(1.0));

		ImageTexture whitetex = new ImageTexture(
			"textures/stones_diffuse.png", 
			BorderHandling.REPEAT,
			Interpolation.BILINEAR);

		ImageTexture clamptex = new ImageTexture(
			"textures/stones_diffuse.png", 
			BorderHandling.CLAMP,
			Interpolation.BILINEAR);

		ImageTexture mirrtex = new ImageTexture(
			"textures/stones_diffuse.png", 
			BorderHandling.MIRROR,
			Interpolation.BILINEAR);

		LambertianMaterial white = new LambertianMaterial(blacktex, whitetex);
		LambertianMaterial clamp = new LambertianMaterial(blacktex, clamptex);
		LambertianMaterial mirror = new LambertianMaterial(blacktex, mirrtex);
		LambertianMaterial green = new LambertianMaterial(blacktex, greentex);
		LambertianMaterial red = new LambertianMaterial(blacktex, redtex);

		TriangleCoordinateMapper bottomleft = new TriangleCoordinateMapper(
			new Point(0.0, 0.0, 0.0), 
			new Point(3.0, 0.0, 0.0), 
			new Point(0.0, 3.0, 0.0));

		TriangleCoordinateMapper topright = new TriangleCoordinateMapper(
			new Point(3.0, 3.0, 0.0), 
			new Point(3.0, 0.0, 0.0), 
			new Point(0.0, 3.0, 0.0));

		scene.add(
			new Triangle(
				new Point(000.0,000.0,000.0).multiply_scalar(scale), 
				new Point(550.0,000.0,000.0).multiply_scalar(scale), 
				new Point(000.0,000.0,560.0).multiply_scalar(scale), 
				bottomleft, 
				clamp)); 

		scene.add(
			new Triangle(
				new Point(550.0,000.0,560.0).multiply_scalar(scale), 
				new Point(550.0,000.0,000.0).multiply_scalar(scale), 
				new Point(000.0,000.0,560.0).multiply_scalar(scale), 
				topright, 
				clamp)); 

		scene.add(
			new Triangle(
				new Point(000.0,550.0,000.0).multiply_scalar(scale), 
				new Point(550.0,550.0,000.0).multiply_scalar(scale), 
				new Point(000.0,550.0,560.0).multiply_scalar(scale), 
				bottomleft, 
				mirror)); 

		scene.add(
			new Triangle(
				new Point(550.0,550.0,560.0).multiply_scalar(scale), 
				new Point(550.0,550.0,000.0).multiply_scalar(scale), 
				new Point(000.0,550.0,560.0).multiply_scalar(scale), 
				topright, 
				mirror)); 

		scene.add(
			new Triangle(
				new Point(000.0,000.0,560.0).multiply_scalar(scale), 
				new Point(550.0,000.0,560.0).multiply_scalar(scale), 
				new Point(000.0,550.0,560.0).multiply_scalar(scale), 
				bottomleft, 
				white)); 

		scene.add(
			new Triangle(
				new Point(550.0,550.0,560.0).multiply_scalar(scale), 
				new Point(550.0,000.0,560.0).multiply_scalar(scale), 
				new Point(000.0,550.0,560.0).multiply_scalar(scale), 
				topright, 
				white)); 

		scene.add(
			new Triangle(
				new Point(000.0,000.0,000.0).multiply_scalar(scale), 
				new Point(000.0,000.0,560.0).multiply_scalar(scale), 
				new Point(000.0,550.0,000.0).multiply_scalar(scale), 
				bottomleft, 
				green));

		scene.add(
			new Triangle(
				new Point(000.0,550.0,560.0).multiply_scalar(scale), 
				new Point(000.0,000.0,560.0).multiply_scalar(scale), 
				new Point(000.0,550.0,000.0).multiply_scalar(scale), 
				topright, 
				green));

		scene.add(
			new Triangle(
				new Point(550.0,000.0,000.0).multiply_scalar(scale), 
				new Point(550.0,000.0,560.0).multiply_scalar(scale), 
				new Point(550.0,550.0,000.0).multiply_scalar(scale), 
				bottomleft, 
				red)); 

		scene.add(
			new Triangle(
				new Point(550.0,550.0,560.0).multiply_scalar(scale), 
				new Point(550.0,000.0,560.0).multiply_scalar(scale), 
				new Point(550.0,550.0,000.0).multiply_scalar(scale), 
				topright, 
				red)); 

		scene.add(
			new Sphere(
				new Point(400.0,450.0,300.0).multiply_scalar(scale), 
				150.0*scale, 
				m1, 
				white));

		scene.add(
			new Sphere(
				new Point(200.0,100.0,300.0).multiply_scalar(scale), 
				150.0*scale, 
				m2, 
				white));

		return scene;
	}

	private static final Group get_scene_6 ()
	{
		final double scale = 0.001;
		final double hsq2 = 0.5 / Math.sqrt(2.0);

		SimpleGroup scene = new SimpleGroup();
		ImageTexture whitetex = new ImageTexture(
			"textures/stones_diffuse.png",
			BorderHandling.REPEAT,
			Interpolation.BILINEAR);
		ConstantTexture blacktex = new ConstantTexture(new RGB(0.0));
		LambertianMaterial white = new LambertianMaterial(blacktex, whitetex);

		scene.add(
			new Sphere(
				new Point(200.0,100.0,300.0).multiply_scalar(scale), 
				150.0*scale, 
				new SphericalCoordinateMapper(
					new Point(.4f,.45f,.3f),
					new Vector(0.0, hsq2, hsq2),
					new Vector(0.5, 0.0, 0.0)),
				white));

		return scene;
	}

	private static final void add_lights1 (final World world)
	{
		final double scale = 0.001;
		world.add_light(
			new PointLight(
				new Point(278*scale, 429.99*scale, 279.5*scale),
				new RGB(100000.0*scale*scale)));

		world.add_light(
			new PointLight(
				new Point(478*scale, 229.99*scale, -59.5*scale),
				new RGB(150000.0*scale*scale)));

		world.add_light(
			new PointLight(
				new Point(490*scale, 159.99*scale, 279.5*scale),
				new RGB(40000.0*scale*scale, 0.0, 0.0)));

		world.add_light(
			new PointLight(
				new Point(40*scale, 159.99*scale, 249.5*scale),
				new RGB(
					5000.0*scale*scale, 
					30000.0*scale*scale, 
					5000.0*scale*scale)));
	}

	private static final void add_lights2 (final World world)
	{
		final double scale = 0.001;
		world.add_light(
			new PointLight(
				new Point(178*scale, 429.99*scale, 279.5*scale),
				new RGB(100000.0*scale*scale)));

		world.add_light(
			new PointLight(
				new Point(478*scale, 229.99*scale, -59.5*scale),
				new RGB(150000.0*scale*scale)));

		world.add_light(
			new PointLight(
				new Point(490*scale, 159.99*scale, 279.5*scale),
				new RGB(40000.0*scale*scale, 0.0, 0.0)));

		world.add_light(
			new PointLight(
				new Point(40*scale, 159.99*scale, 249.5*scale),
				new RGB(
					5000.0*scale*scale, 
					30000.0*scale*scale, 
					5000.0*scale*scale)));
	}

	public static final void run ()
	{
		final double scale = 0.001;

		RenderSettings settings1 = new RenderSettings(
			new Size(800, 800), 
			"a8-1.png", 
			4, 
			new Size(100, 100));

		RenderSettings settings2 = new RenderSettings(
			new Size(800, 800), 
			"a8-2.png", 
			4, 
			new Size(100, 100));

		RenderSettings settings3 = new RenderSettings(
			new Size(800, 800), 
			"a8-3.png", 
			4, 
			new Size(100, 100));

		RenderSettings settings4 = new RenderSettings(
			new Size(800, 800), 
			"a8-4.png", 
			4, 
			new Size(100, 100));

		RenderSettings settings5 = new RenderSettings(
			new Size(800, 800), 
			"a8-5.png", 
			4, 
			new Size(100, 100));

		RenderSettings settings6 = new RenderSettings(
			new Size(800, 800), 
			"a8-6.png", 
			4, 
			new Size(100, 100));

		RenderSettings settings7 = new RenderSettings(
			new Size(800, 800), 
			"a8-7.png", 
			4, 
			new Size(100, 100));

		RenderSettings settings8 = new RenderSettings(
			new Size(800, 800), 
			"a8-8.png", 
			4, 
			new Size(100, 100));

		PerspectiveCamera cam = new PerspectiveCamera(
			new Point(0.1, -1.5, 0.225), 
			new Vector(0.0, 1.0, -0.5), 
			new Vector(0.0, 0.0, 1.0), 
			1.0, 
			1.0);

		PerspectiveCamera cam2 = new PerspectiveCamera(
			new Point(278.0*scale, 273.0*scale, -800.0*scale), 
			new Vector(0.0, 0.0, 1.0), 
			new Vector(0.0, 1.0, 0.0), 
			0.686, 
			1.0);

		RGB background_color = new RGB(0.0);

		Integrator cosine_integrator = new CosineIntegrator();
		Integrator light_integrator = new RayTracingIntegrator();
		Integrator mirror_integrator = new RecursiveRayTracingIntegrator();
		final double hsq2 = 0.5 / Math.sqrt(2.0);

		World world1 = new World(get_scene_1(), background_color, cam);
		World world2 = new World(get_scene_2(), background_color, cam);
		World world3 = new World(get_scene_3(), background_color, cam);
		World world4 = new World(get_scene_4(), background_color, cam2);
		World world5 = new World(get_scene_5(
			new SphericalCoordinateMapper(
				new Point(.4f,.45f,.3f),
				new Vector(0.0, hsq2, hsq2),
				new Vector(0.5, 0.0, 0.0)),
			new SphericalCoordinateMapper(
				new Point(0.3, 0.1, 0.3),
				new Vector(0.0, hsq2, -hsq2),
				new Vector(0.5, 0.0, 0.0))
			), background_color, cam2);

		World world6 = new World(get_scene_5(
			new PlaneCoordinateMapper(
				new Vector(0.25, 0.0, 0.25),
				new Vector(-0.25, 0.0, 0.25)),
			new PlaneCoordinateMapper(
				new Vector(0.25, 0.35, -0.25),
				new Vector(-0.25, 0.35, -0.25))
				), background_color, cam2);

		World world7 = new World(get_scene_5(
			new CylindricalCoordinateMapper(
				new Point(0.4, 0.45, 0.3),
				new Vector(0.0, hsq2, hsq2),
				new Vector(0.5, 0.0, 0.0)),
			new CylindricalCoordinateMapper(
				new Point(0.3, 0.1, 0.3),
				new Vector(0.0, hsq2, -hsq2),
				new Vector(0.5, 0.0, 0.0))
				), background_color, cam2);
		World world8 = new World(get_scene_6(), background_color, cam2);
		world8.set_environment_texture(
			new ImageTexture(
				"textures/panorama.png", 
				BorderHandling.REPEAT, 
				Interpolation.BILINEAR));

		add_lights1(world4);
		add_lights2(world5);
		add_lights2(world6);
		add_lights2(world7);
		add_lights1(world8);

		cosine_integrator.set_world(world1);
		Renderer.render(cosine_integrator, settings1);

		cosine_integrator.set_world(world2);
		Renderer.render(cosine_integrator, settings2);

		cosine_integrator.set_world(world3);
		Renderer.render(cosine_integrator, settings3);

		light_integrator.set_world(world4);
		Renderer.render(light_integrator, settings4);

		light_integrator.set_world(world5);
		Renderer.render(light_integrator, settings5);

		light_integrator.set_world(world6);
		Renderer.render(light_integrator, settings6);

		light_integrator.set_world(world7);
		Renderer.render(light_integrator, settings7);

		light_integrator.set_world(world8);
		Renderer.render(light_integrator, settings8);
	}
}
*/
