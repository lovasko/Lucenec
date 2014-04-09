package sk.lovasko.lucenec;
/*
class A1
{
	private static double weight (double fx, double fy, Point c, double div)  		
	{
		int num_iter;

		Point v = new Point((fx-0.5)*2.0, (fy-0.5)*2.0, 0.0);
		num_iter = Julia.julia(v, c);
		return ((double)num_iter / (double)(num_iter + div));
	}

	public static RGB compute_color (int x, int y, int width, int height)
	{
		double fx = (double)x / (double)width;
		double fy = (double)y / (double)height;

		RGB color = new RGB(0.0);

		RGB c1 = new RGB(0.8, 0.8,  1.0);
		RGB c2 = new RGB(0.5, 0.5, -0.2);
		RGB c3 = new RGB(0.2, 0.3,  0.4);

		Point p1 = new Point(-0.8,   0.156, 0.0);
		Point p2 = new Point(-0.6,   0.2,   0.0);
		Point p3 = new Point( 0.285, 0.0,   0.0);

		c1.multiply_scalar(A1.weight(fx, fy, p1, 64.0));
		c2.multiply_scalar(A1.weight(fx, fy, p2, 64.0) * 0.2);
		c3.multiply_scalar(A1.weight(fy, fx, p3, 64.0));

		color.add(c1);
		color.add(c2);
		color.add(c3);
		color.negate();

		return color;	
	}

	public static void run ()
	{
		Image image = new Image(800, 800);
		for (int x = 0; x < 800; x++)
		for (int y = 0; y < 800; y++)
		{
			image.get_color(x, y).set_all_components(A1.compute_color(x, y, 800, 800));
		}
		image.write("a1.png", "png");	
	}
}
*/
