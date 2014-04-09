package sk.lovasko.lucenec;

import java.io.File;

class Main
{
	private static final long get_file_size (final String filename)
	{
		File file = new File(filename);
		return file.length(); 
	}

	public static void main(final String[] args)
	{
		System.setProperty("java.awt.headless", "true");
		if (args.length > 0 && args[0].equals("serialize"))
		{
			// TODO do this with the Scanner class and 
			// nextLine method.
			final String filename = args[1];
			long start;
			long end;
			double normal_time;
			double serialized_time;

			ObjModel obj = new ObjModel(filename);
			start = System.currentTimeMillis();
			obj.load();
			end = System.currentTimeMillis();
			normal_time = ((double)(end-start)/1000.0);

			SerializedModel ser = obj.to_serialized(filename + ".slo");
			ser.save();

			SerializedModel ser2 = new SerializedModel(filename + ".slo");
			start = System.currentTimeMillis();
			ser2.load();
			end = System.currentTimeMillis();
			serialized_time = ((double)(end-start)/1000.0);

			System.out.println("Statistics:");
			System.out.println("Original size:           " + get_file_size(filename));
			System.out.println("Serialized size:         " + get_file_size(filename + ".slo"));
			System.out.println("Original loading time:   " + normal_time + "s");
			System.out.println("Serialized loading time: " + serialized_time + "s");

			return;
		}

		RenderingCompetition.run();
	}
}

