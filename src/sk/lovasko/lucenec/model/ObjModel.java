package sk.lovasko.lucenec.model;

import sk.lovasko.lucenec.geom.Point;
import sk.lovasko.lucenec.math.Double2;
import sk.lovasko.lucenec.math.Int3;
import sk.lovasko.lucenec.solid.Solid;
import sk.lovasko.lucenec.solid.Triangle;

import java.util.List;
import java.util.ArrayList;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;
import org.w3c.dom.Element;

public final class ObjModel extends Model
{
	private enum Instruction
	{
		NONE, 
		INVALID, 
		VERTEX, 
		TEXVERTEX, 
		NORMALVERTEX, 
		PARAMVERTEX, 
		CURVEVERTEX, 
		DEGREE, 
		BASISMATRIX, 
		STEP, 
		POINT, 
		LINE, 
		FACE,
		CURVE, 
		CURVE2, 
		SURFACE, 
		CURVEPARAMETER, 
		CURVETRIM, 
		CURVEHOLE, 
		CURVESPECIALCURVE, 
		CURVESPECIALPOINT, 
		CURVEEND, 
		CONNECT, 
		GROUP, 
		SMOOTH, 
		MERGINGGROUP, 
		OBJECT, 
		BEVEL, 
		COLORINTERPOLATION, 
		DISSOLVEINTERPOLATION, 
		LOD, 
		MATERIAL, 
		MATERIALLIBRARY, 
		SHADOW, 
		TRACE, 
		APPROXCURVE, 
		APPROXSURFACE 
	}

	private List<Point> vertices;
	private List<Point> normals;
	private List<Double2> tex_coords;
	private List<String> material_libraries;

	private ObjModel() { }

	public ObjModel (final String _filename)
	{
		vertices = new ArrayList<Point>();
		normals = new ArrayList<Point>();
		tex_coords = new ArrayList<Double2>();
		material_libraries = new ArrayList<String>();
		solids = new ArrayList<Solid>();

		filename = _filename;
	}

	private final String remove_comment (String line)
	{
		final int position = line.indexOf('#');
		if (position == -1)
		{
			return line;
		}
		else
		{
			return line.substring(0, position);
		}
	}

	private final Instruction get_instruction (String line)
	{
		if (line.isEmpty())             return Instruction.NONE;
		if (line.matches("v\\b.*"))      return Instruction.VERTEX;
		if (line.matches("vn\\b.*"))     return Instruction.NORMALVERTEX;
		if (line.matches("vt\\b.*"))     return Instruction.TEXVERTEX;
		if (line.matches("f\\b.*"))      return Instruction.FACE;
		if (line.matches("usemtl\\b.*")) return Instruction.MATERIAL;
		if (line.matches("mtllib\\b.*")) return Instruction.MATERIALLIBRARY;

		return Instruction.INVALID;
	}

	private final boolean parse_vertex (final String line)
	{
		final Scanner scanner = new Scanner(line.substring(2));
		double x;
		double y;
		double z;
		double w;

		if (scanner.hasNextDouble())
		{
			x = scanner.nextDouble();
		}
		else
		{
			System.err.println("Unable to read the first double");
			return false;
		}

		if (scanner.hasNextDouble())
		{
			y = scanner.nextDouble();
		}
		else
		{
			System.err.println("Unable to read the second double");
			return false;
		}

		if (scanner.hasNextDouble())
		{
			z = scanner.nextDouble();
		}
		else
		{
			System.err.println("Unable to read the third double");
			return false;
		}

		if (scanner.hasNextDouble())
		{
			w = scanner.nextDouble();
			x = x/w;
			y = y/w;
			z = z/w;
		}

		vertices.add(new Point(x, y, z));
		return true;
	}

	private final boolean parse_normal_vertex (final String line)
	{
		final Scanner scanner = new Scanner(line.substring(2));
		double x;
		double y;
		double z;
		double w;

		if (scanner.hasNextDouble())
		{
			x = scanner.nextDouble();
		}
		else
		{
			return false;
		}

		if (scanner.hasNextDouble())
		{
			y = scanner.nextDouble();
		}
		else
		{
			return false;
		}

		if (scanner.hasNextDouble())
		{
			z = scanner.nextDouble();
		}
		else
		{
			return false;
		}

		if (scanner.hasNextDouble())
		{
			w = scanner.nextDouble();
			x = x/w;
			y = y/w;
			z = z/w;
		}

		normals.add(new Point(x, y, z));
		return true;
	}

	private final boolean parse_tex_vertex (final String line)
	{
		final Scanner scanner = new Scanner(line.substring(2));
		double u;
		double v;

		if (scanner.hasNextDouble())
		{
			u = scanner.nextDouble();
		}
		else
		{
			return false;
		}

		if (scanner.hasNextDouble())
		{
			v = scanner.nextDouble();
		}
		else
		{
			return false;
		}

		tex_coords.add(new Double2(u, v));
		return true;
	}
	
	private final boolean parse_face (final String line)
	{
		String[] line_parts = line.substring(2).split(" ");
		if (line_parts.length < 3)
		{
			return false;
		}
		
		Int3[] vertex_data = new Int3[line_parts.length];
		for (int i = 0; i < line_parts.length; i++)
		{
			vertex_data[i] = new Int3();
			try
			{
				String[] vertex_parts = line_parts[i].split("/");
				switch (vertex_parts.length)
				{
					case 2:
						vertex_data[i].set_data(0, Integer.parseInt(vertex_parts[0]));
						vertex_data[i].set_data(1, Integer.parseInt(vertex_parts[1]));
						vertex_data[i].set_presence(2, false);
					break;

					case 3:
						if (vertex_parts[1].isEmpty())
						{
							vertex_data[i].set_data(0, Integer.parseInt(vertex_parts[0]));			
							vertex_data[i].set_presence(1, false);
							vertex_data[i].set_data(2, Integer.parseInt(vertex_parts[2]));		
						}
						else
						{
							vertex_data[i].set_data(0, Integer.parseInt(vertex_parts[0]));			
							vertex_data[i].set_data(1, Integer.parseInt(vertex_parts[1]));			
							vertex_data[i].set_data(2, Integer.parseInt(vertex_parts[2]));		
						}
					break;

					default:
						return false;
				}
			}
			catch (NumberFormatException e)
			{
				System.err.println(e);
			}
		
			vertex_data[i].turn_around(0, vertices.size());
			vertex_data[i].turn_around(1, tex_coords.size());
			vertex_data[i].turn_around(2, normals.size());
			
			vertex_data[i].decrement_all(1);
		}
			
		for (int i = 1; i < line_parts.length - 1; i++)
		{
			Point p1 = vertices.get(vertex_data[0].get_data(0));
			Point p2 = vertices.get(vertex_data[i].get_data(0));
			Point p3 = vertices.get(vertex_data[i + 1].get_data(0));

			solids.add(new Triangle(p1, p2, p3));
		}

		return true;
	}

	private final boolean parse_material_library (final String line)
	{
		material_libraries.add(line.substring(7)); // after 'mtllib '	
		return true;
	}

	public final boolean load ()
	{
		BufferedReader br = null;
		try
		{
			br = new BufferedReader(new FileReader(filename));
		}
		catch (FileNotFoundException e)
		{
			System.err.println(e);
			return false;
		}

		String line = null;
		int line_counter = 0;

		while (true)
		{
			try
			{
				line = br.readLine();
			}
			catch (IOException e)
			{
				System.err.println(e);	
				return false;
			}

			if (line == null)
			{
				break;
			}

			line = line.trim();
			line = remove_comment(line);
			line_counter++;

			Instruction instruction = get_instruction(line);
			switch (instruction)
			{
				case NONE:
				break;

				case VERTEX:
					if (!parse_vertex(line))
					{
						System.err.println("Error parsing vertex at line " + line_counter + " at file " + filename);
						System.err.println("The line is: \"" + line + "\"");
						return false;
					}
				break;

				case NORMALVERTEX:
					if (!parse_normal_vertex(line))
					{
						System.err.println("Error parsing normal vertex at line " + line_counter + " at file " + filename);
						return false;
					}
				break;

				case TEXVERTEX:
					if (!parse_tex_vertex(line))
					{
						System.err.println("Error parsing texture vertex at line " + line_counter + " at file " + filename);
						return false;
					}
				break;

				case FACE:
					if (!parse_face(line))
					{
						System.err.println("Error parsing face at line " + line_counter + " at file " + filename);
						return false;
					}
				break;

				case MATERIAL:
					// look for the material?
				break;

				case MATERIALLIBRARY:
					if (!parse_material_library(line))
					{
						System.err.println("Error parsing material library at line " + line_counter + " at file " + filename);
						return false;
					}
				break;

				// fall
				default:
				case INVALID:
					System.err.println("Invalid/unimplemented instruction at line " + line_counter + " at file " + filename);
					System.err.println(line);
				break;
			}
		}
	
		return true;
	}

	public static final ObjModel read_from_xml_element (Element element)
	{
		if (element.hasAttribute("src"))
		{
			String filename = element.getAttribute("src");	
			ObjModel obj_model = new ObjModel(filename);

			if (obj_model.load())
			{
				return obj_model;	
			}
			else
			{
				return null;
			}
		}
		else
		{
			return null;		
		}
	}

	public final SerializedModel to_serialized (final String filename)
	{
		return new SerializedModel(filename, solids);
	}
}

