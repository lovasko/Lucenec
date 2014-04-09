package sk.lovasko.lucenec;

public final class RenderingCompetition
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

	private final static KDTree load_part (
		final String filename,
		final Material material)
	{
		KDTree tree = new KDTree();
		ObjModel model = new ObjModel(filename);

		model.load();
		model.add_to_group(tree);

		tree.set_coordinate_mapper(new WorldCoordinateMapper());
		tree.set_material(material);
		tree.rebuild_index();
		return tree;
	}

	private final static Primitive get_tatra (final Vector movement)
	{
		final SimpleGroup tatra_group = new SimpleGroup();
		final Material glass_material = new GlassMaterial(2.0);
		final Material mirror_material = new MirrorMaterial(2.0, 2.0);
		final Material chassis_material = new PhongMaterial(new ConstantTexture(new RGB(0.0, 0.0, 0.4)), 1.0);
		final Material rubber_material = new LambertianMaterial(
			new ConstantTexture(new RGB(1.0)), 
			new ConstantTexture(new RGB(0.7))); 

		tatra_group.add(load_part("worlds/models/tatra/chassis.obj", chassis_material));
		//tatra_group.add(load_part("worlds/models/tatra/chrome.obj", mirror_material));
		//tatra_group.add(load_part("worlds/models/tatra/glass.obj", glass_material));
		//tatra_group.add(load_part("worlds/models/tatra/interior.obj"));
		//tatra_group.add(load_part("worlds/models/tatra/interior_2.obj"));
		//tatra_group.add(load_part("worlds/models/tatra/mirrors.obj", mirror_material));
		tatra_group.add(load_part("worlds/models/tatra/rubber.obj", rubber_material));
		//tatra_group.add(load_part("worlds/models/tatra/seats.obj"));
		//tatra_group.add(load_part("worlds/models/tatra/sign.obj"));
		//tatra_group.add(load_part("worlds/models/tatra/signal_lights.obj"));
		//tatra_group.add(load_part("worlds/models/tatra/stop_lights.obj"));

		Instance tatra_instance = new Instance(tatra_group);
		tatra_instance.translate(movement);

		TimeInstance tatra = new TimeInstance(tatra_instance);
		tatra.add_translate_transformation(
			0.0, 
			1.0, 
			new Point(0.0, 0.0, 3.0),
			new Point(0.0, 0.0, -3.0),
			new SquareTimeDistribution());

		return tatra;
	}

	private final static Group get_scene ()
	{
		SimpleGroup scene = new SimpleGroup();

		final InfinitePlane ground = new InfinitePlane(
			new Point(0.0, -9.27, 0.0), 
			new Vector(0.0, 1.0, 0.0), 
			new LambertianMaterial(
				new ConstantTexture(new RGB(1.0)), 
				new ConstantTexture(new RGB(0.7))),
			new WorldCoordinateMapper()
			);

		scene.add(get_tatra(new Vector(0.0)));
		scene.add(get_tatra(new Vector(28.0, 0.0, -38.0)));
		scene.add(get_tatra(new Vector(21.0, 0.0, 88.0)));
		scene.add(ground);

		make_box(scene, 
			new Point(80.0, -9.27, -38.0), 
			new Vector(0.0, 0.0, 1000.0),
			new Vector(10.0, 0.0, 0.0),
			new Vector(0.0, 30.0, 0.0),
			new WorldCoordinateMapper(),
			new LambertianMaterial(
				new ConstantTexture(new RGB(1.0)), 
				new ConstantTexture(new RGB(0.7))));

		scene.add(new Quad(
			new Point(80.0, -9.27 + 25.0, -38.0),
			new Vector(0.0, 0.0, 1000.0),
			new Vector(150.0, 85.0, 0.0),
			new WorldCoordinateMapper(),
			new LambertianMaterial(
				new ConstantTexture(new RGB(1.0)), 
				new ConstantTexture(new RGB(0.7)))));

		//Instance human = new Instance(load_part("human_1.obj"));
		//human.scale(25.0);

		//scene.add(human);

		return scene;
	}

	private final static Camera get_camera ()
	{
		return new PerspectiveCamera(
			new Point(-20.0, 20.0, -30.0),
			new Vector(1.0, 0.1, 1.0),
			new Vector(0.0, 1.0, 0.0),
			Math.PI/2.0,
			1.0);
	}

	private static final void add_lights (final World world)
	{
		world.add_light(new PointLight(
			new Point(0.0, 4.0, -4.0),
			new RGB(80.6)));
	}

	public final static void run ()
	{
		final RenderSettings settings = new RenderSettings(
			new Size(800, 800),
			"daniel-lovasko.png",
			8,
			new Size(100, 100),
			300);

		final Primitive scene = get_scene();
		System.out.println(scene);

		final World world = new World(scene, new RGB(0.0), get_camera());
		add_lights(world);

		//final Integrator integrator = new CosineIntegrator();
		final Integrator integrator = new CosineIntegrator();
		integrator.set_world(world);
		Renderer.render(integrator, settings);
	}
}

