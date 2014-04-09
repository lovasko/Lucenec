package sk.lovasko.lucenec;

public final class A10
{
	private static final void make_sphere(
		final Group scene, 
		final  Point center, 
		final double radius, 
		final int v_tessel_count, 
		final int h_tessel_count, 
		final CoordinateMapper tex_mapper, 
		final Material material) 
	{
		final double v_angle_step = Math.PI / v_tessel_count;
		final double h_angle_step = (2.0 * Math.PI) / h_tessel_count;

		final Point top = center.move(new Vector(0.0, 0.0, radius));
		final Point bottom = center.move(new Vector(0.0, 0.0, radius).negate());

		final double z = Math.cos(v_angle_step) * radius;
		final double r = Math.sin(v_angle_step) * radius;

		for (int i = 0; i < h_tessel_count; ++i) 
		{
			final Point t_left = center.move(new Vector(
				Math.sin(i * h_angle_step) * r,
				Math.cos(i * h_angle_step) * r,
				z));
			final Point t_right = center.move(new Vector(
				Math.sin((i+1) * h_angle_step) * r,
				Math.cos((i+1) * h_angle_step) * r,
				z));
			scene.add(
				new Triangle(
					top,
					t_left,
					t_right,
					tex_mapper,
					material));

			final Point b_left = center.move(new Vector(
				Math.sin(i*h_angle_step)*r,
				Math.cos(i*h_angle_step)*r,
				-z));
			final Point b_right = center.move(new Vector(
				Math.sin((i+1)*h_angle_step)*r,
				Math.cos((i+1)*h_angle_step)*r,
				-z));
			scene.add(
				new Triangle(
					bottom,
					b_left,
					b_right,
					tex_mapper,
					material));
		}

		for (int y = 1; y < v_tessel_count-1; ++y) 
		{
			final double topz = Math.cos(v_angle_step*y)*radius;
			final double bottomz = Math.cos(v_angle_step*(y+1))*radius;
			final double topr = Math.sin(v_angle_step*y)*radius;
			final double bottomr = Math.sin(v_angle_step*(y+1))*radius;

			for (int x = 0; x<h_tessel_count; ++x) 
			{
				final Point tleft = center.move(new Vector(
					Math.sin(x*h_angle_step)*topr,
					Math.cos(x*h_angle_step)*topr,
					topz));
				final Point tright = center.move(new Vector(
					Math.sin((x+1)*h_angle_step)*topr,
					Math.cos((x+1)*h_angle_step)*topr,
					topz));
				final Point bleft = center.move(new Vector(
					Math.sin(x*h_angle_step)*bottomr,
					Math.cos(x*h_angle_step)*bottomr,
					bottomz));
				final Point bright = center.move(new Vector(
					Math.sin((x+1)*h_angle_step)*bottomr,
					Math.cos((x+1)*h_angle_step)*bottomr,
					bottomz));

				scene.add(new Triangle(tleft, tright, bleft, tex_mapper, material));
				scene.add(new Triangle(bleft, bright, tright, tex_mapper, material));
			}
		}
	}

	private static final void make_smooth_sphere(
		final Group scene, 
		final  Point center, 
		final double radius, 
		final int vtesselCount, 
		final int htesselCount, 
		final CoordinateMapper texMapper, 
		final Material material) 
	{
		final double vangleStep = Math.PI / vtesselCount;
		final double hangleStep = (2*Math.PI) / htesselCount;

		final Point top = center.move(new Vector(0,0,radius));
		final Vector topN = new Vector(0,0,1);
		final Point bottom = center.move(new Vector(0,0,radius).negate());
		final Vector bottomN = new Vector(0,0,-1);

		final double z = Math.cos(vangleStep)*radius;
		final double r = Math.sin(vangleStep)*radius;

		for (int i=0; i<htesselCount; ++i) 
		{
			Vector tleftN = new Vector(Math.sin(i*hangleStep)*r, Math.cos(i*hangleStep)*r, z);
			Point tleft = center.move(tleftN);
			tleftN = tleftN.normalize();

			Vector trightN = new Vector(Math.sin((i+1)*hangleStep)*r, Math.cos((i+1)*hangleStep)*r, z);
			Point tright = center.move(trightN);
			trightN = trightN.normalize();

			scene.add(new SmoothTriangle(top,tleft,tright,topN,tleftN,trightN,texMapper,material));
			Vector bleftN = new Vector(Math.sin(i*hangleStep)*r, Math.cos(i*hangleStep)*r, -z);
			Point bleft = center.move(bleftN);
			bleftN = bleftN.normalize();

			Vector brightN = new Vector(Math.sin((i+1)*hangleStep)*r, Math.cos((i+1)*hangleStep)*r, -z);
			Point bright = center.move(brightN);
			brightN = brightN.normalize();
			scene.add(new SmoothTriangle(bottom,bleft,bright,bottomN,bleftN,brightN,texMapper,material));
		}

		for (int y=1; y<vtesselCount-1; ++y) 
		{
			final double topz = Math.cos(vangleStep*y)*radius;
			final double bottomz = Math.cos(vangleStep*(y+1))*radius;
			final double topr = Math.sin(vangleStep*y)*radius;
			final double bottomr = Math.sin(vangleStep*(y+1))*radius;

			for (int x=0; x<htesselCount; ++x) 
			{
				Vector tleftN = new Vector(Math.sin(x*hangleStep)*topr, Math.cos(x*hangleStep)*topr, topz);
				Point tleft = center.move(tleftN);
				tleftN = tleftN.normalize();

				Vector trightN = new Vector(Math.sin((x+1)*hangleStep)*topr, Math.cos((x+1)*hangleStep)*topr, topz);
				Point tright = center.move(trightN);
				trightN = trightN.normalize();

				Vector bleftN = new Vector(Math.sin(x*hangleStep)*bottomr, Math.cos(x*hangleStep)*bottomr, bottomz);
				Point bleft = center.move(bleftN);
				bleftN = bleftN.normalize();

				Vector brightN = new Vector(Math.sin((x+1)*hangleStep)*bottomr, Math.cos((x+1)*hangleStep)*bottomr, bottomz);
				Point bright = center.move(brightN);
				brightN = brightN.normalize();
				scene.add(new SmoothTriangle(tleft,tright,bleft,tleftN,trightN,bleftN,texMapper,material));
				scene.add(new SmoothTriangle(bleft,bright,tright,bleftN,brightN,trightN,texMapper,material));
			}
		}
	}

	private static final void render_sphere (
		final double scale,
		final String filename,
		final boolean smooth)
	{
		final RenderSettings settings = new RenderSettings(
			new Size(800, 800),
			filename,
			8,
			new Size(100, 100),
			20);

    final PerspectiveCamera cam = new PerspectiveCamera(
			new Point(278*scale, 273*scale, -800*scale), 
			new Vector(0, 0.5f, 1), 
			new Vector(0.1f, 1, 0), 
			0.686f, 
			1.0);

		final SimpleGroup scene = new SimpleGroup();
		final World world = new World(scene, new RGB(0.0), cam);

		if (smooth)
			make_smooth_sphere(
				scene, 
				new Point(300.f,720.f,25.f).multiply_scalar(scale), 
				200.0*scale, 
				8, 
				16, 
				new WorldCoordinateMapper(), 
				null);
		else
			make_sphere(
				scene, 
				new Point(300.f,720.f,25.f).multiply_scalar(scale), 
				200.0*scale, 
				8, 
				16, 
				new WorldCoordinateMapper(), 
				null);

		Integrator integrator = new CosineIntegrator();
		integrator.set_world(world);
		Renderer.render(integrator, settings);
	}

	public static final void run ()
	{
    render_sphere(0.01, "a10-smooth-1.png", false);
    render_sphere(0.01, "a10-smooth-2.png", true);
	}
}

