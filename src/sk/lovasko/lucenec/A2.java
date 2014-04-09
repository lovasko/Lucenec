package sk.lovasko.lucenec;
/*
class A2
{
	public static double weight (double fx, double fy, Point c, double div)
	{
		Point v = new Point(fx, fy, 0.0);
		int num_iter = Julia.julia(v, c);

		return ((double)num_iter / (double)(num_iter + div));
	}

	public static RGB compute_color (Ray r)
	{
    double theta = Math.asin(r.get_direction().get_z()) / Math.PI * 2.0;
    double phi = Math.atan2(r.get_direction().get_y(), 
		                        r.get_direction().get_x()) / Math.PI;

    double ofx = Common.abs_fractional((r.get_origin().get_x()+1.0)/2.0)*2.0-1.0;
    double ofy = Common.abs_fractional((r.get_origin().get_y()+1.0)/2.0)*2.0-1.0;

		RGB color = new RGB(0.0);

		RGB c1 = new RGB(0.8, 0.8,  1.0); 
		RGB c2 = new RGB(0.5, 0.5, -0.2);
		RGB c3 = new RGB(0.4, 0.5,  0.6);

		Point p1 = new Point(-0.8,   0.156, 0.0);	
		Point p2 = new Point(-0.6,   0.2,   0.0);
		Point p3 = new Point( 0.285, 0.0,   0.0);

		c1.multiply_scalar(A2.weight(phi, theta, p1, 64.0));
		c2.multiply_scalar(A2.weight(phi, theta, p2, 64.0) * 0.2);
		c3.multiply_scalar(A2.weight(ofy, ofx, p3, 64.0));

		color.add(c1);
		color.add(c2);
		color.add(c3);

		color.negate();

    if (Common.abs_fractional(theta / (2.0 * Math.PI) * 90.0) < 0.03) 
		{
			RGB c4 = new RGB(0.9, 0.5, 0.5);
			c4.multiply_scalar(0.7);

			color = c4; 
		}

    if (Common.abs_fractional(phi / (2.0 * Math.PI) * 90.0) < 0.03) 
		{
			RGB c4 = new RGB(0.9, 0.9, 0.5);
			c4.multiply_scalar(0.7);

			color = c4;
		}

    return color;
	}

	public static void run ()
	{
    Image img = new Image(800, 800);
    Image low = new Image(128, 128);

    PerspectiveCamera pcam = new PerspectiveCamera(
			new Point (0.0, 0.0, 0.0), 
			new Vector(1.0, 0.0, 0.1), 
			new Vector(0.0, 0.0, 1.0), 
			Math.PI / 3.0, 
			1.0);

    Renderer r1 = new Renderer(pcam, null);
    r1.render(img, 4);
    r1.render(low, 4);
    img.write("a2-1.png", "png");
    low.write("a2-1-low.png", "png");

    PerspectiveCamera pcam2 = new PerspectiveCamera(
			new Point (0.0, 0.0, 0.0), 
			new Vector(0.5, 0.5, 0.3), 
			new Vector(0.0, 0.0, 1.0), 
			Math.PI * 0.9, 
			1.0);

    Renderer r12 = new Renderer(pcam2, null);
    r12.render(img, 4);
    img.write("a2-2.png", "png");
		

    OrthographicCamera ocam = new OrthographicCamera(
			new Point (0.0, 0.0, 0.0), 
			new Vector(0.1, 0.1, 1.0), 
			new Vector(0.2, 1.0, 0.2), 
			10.0, 
			10.0);

    Renderer r2 = new Renderer(ocam, null);
    r2.render(img, 4);
    img.write("a2-3.png", "png");

    FishEyeCamera fecam = new FishEyeCamera(
			new Point (0.0, 0.0, 0.0), 
			new Vector(1.0, 0.0, 0.0), 
			new Vector(0.0, 0.0, 1.0), 
			180.0);

    Renderer r3 = new Renderer(fecam, null);
    r3.render(img, 4);
    img.write("a2-fisheye.png", "png");
	}
}
*/
