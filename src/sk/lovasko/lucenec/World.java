package sk.lovasko.lucenec;

import sk.lovasko.lucenec.xml;

import java.util.List;
import java.util.ArrayList;
import java.util.HashSet;
import java.io.File;
import java.io.IOException;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.ParserConfigurationException;
import org.xml.sax.SAXException;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;

class World
{
	private final Primitive scene;
	private final List<Light> lights; 
	private final RGB background_color;
	private final Camera camera;

	private static HashSet<String> camera_set;
	private static HashSet<String> primitive_set;
	private static HashSet<String> light_set;

	private Texture environment_texture;

	static
	{
		camera_set = new HashSet<String>();
		camera_set.add("perspectiveCamera");
		camera_set.add("orthographicCamera");
		camera_set.add("fishEyeCamera");

		primitive_set = new HashSet<String>();
		primitive_set.add("simpleGroup");
		primitive_set.add("kdTree");
		primitive_set.add("sphere");
		primitive_set.add("quad");
		primitive_set.add("triangle");
		primitive_set.add("infinitePlane");
		primitive_set.add("disc");
		primitive_set.add("axisAlignedBox");
		primitive_set.add("quadric");

		light_set = new HashSet<String>();
		light_set.add("spotLight");
		light_set.add("pointLight");
		light_set.add("directionalLight");
	}
	
	public World (final Primitive _scene, final RGB _background_color, final Camera _camera)
	{
		scene = _scene;
		lights = new ArrayList<Light>();
		background_color = _background_color;
		camera = _camera;
		environment_texture = null;
	}

	public final void set_environment_texture (final Texture texture)
	{
		environment_texture = texture; 
	}

	public final Texture get_environment_texture ()
	{
		return environment_texture;	
	}

	public final void add_light (final Light light)
	{
		lights.add(light);
	}

	public final Primitive get_scene ()
	{
		return scene;
	}

	public final RGB get_background_color ()
	{
		return background_color;
	}

	public final Camera get_camera ()
	{
		return camera;
	}

	public final List<Light> get_lights ()
	{
		return lights;
	}

	// move this to each respective camera class
	private static Camera load_camera_from_element (final Element element)
	{
		String camera_type = element.getTagName();

		if (camera_type.equals("perspectiveCamera"))
		{
			Point center = XmlHelpers.get_point(element, "center");
			Vector up = XmlHelpers.get_vector(element, "up");
			Vector forward = XmlHelpers.get_vector(element, "forward");
			Double angle = XmlHelpers.get_double(element, "angle");
			Double ratio = XmlHelpers.get_double(element, "ratio");

			if (center == null || up == null || forward == null ||
			    angle == null || ratio == null)
			{
				return null;
			}
			else
			{
				return new PerspectiveCamera(center, forward, up, angle, ratio);
			}
		}
		else if (camera_type.equals("orthographicCamera"))
		{
			Point center = XmlHelpers.get_point(element, "center");
			Vector forward = XmlHelpers.get_vector(element, "forward");
			Vector up = XmlHelpers.get_vector(element, "up");
			Double scale_x = XmlHelpers.get_double(element, "scaleX");
			Double scale_y = XmlHelpers.get_double(element, "scaleY");

			if (center == null || up == null || forward == null ||
			    scale_x == null || scale_y == null)
			{
				return null;
			}
			else
			{
				return new OrthographicCamera(center, forward, up, scale_x, scale_y); 
			}
		}
		else if (camera_type.equals("fishEyeCamera"))
		{
			Point center = XmlHelpers.get_point(element, "center"); 
			Vector forward = XmlHelpers.get_vector(element, "forward"); 
			Vector up = XmlHelpers.get_vector(element, "up"); 
			Double psi_max = XmlHelpers.get_double(element, "psiMax");

			if (center == null || forward == null ||
			    up == null || psi_max == null)
			{
				return null;
			}
			else
			{
				return new FishEyeCamera(center, forward, up, psi_max);
			}
		}
		else
		{
			// should never get here, just for consistency
			return null;	
		}
	}

	private static Primitive load_primitive_from_element (final Element element)
	{
		String primitive_type = element.getTagName();
		if (primitive_type.equals("simpleGroup"))
		{
			SimpleGroup sg = new SimpleGroup();
			NodeList node_list = element.getChildNodes();
			for (int i = 0; i < node_list.getLength(); i++)
			{
				Node node = node_list.item(i);
				if (node.getNodeType() == Node.ELEMENT_NODE)
				{
					Element e = (Element) node;
					if (e.getTagName().equals("objModel"))
					{
						ObjModel obj_model = ObjModel.read_from_xml_element(e);
						if (obj_model != null)
						{
							obj_model.add_to_group(sg);	
							System.out.println(sg);
						}
						else
						{
							return null;
						}
					}
					else
					{
						Primitive p = load_primitive_from_element(e);
						if (p == null)
						{
							return null;
						}
						else
						{
							sg.add(p);
						}
					}
				}
			}
			return sg;
		}
		else if (primitive_type.equals("kdTree"))
		{
			KDTree kdtree = new KDTree();		
			NodeList node_list = element.getChildNodes();
			for (int i = 0; i < node_list.getLength(); i++)
			{
				Node node = node_list.item(i);
				if (node.getNodeType() == Node.ELEMENT_NODE)
				{
					Element e = (Element) node;
					if (e.getTagName().equals("objModel"))
					{
						ObjModel obj_model = ObjModel.read_from_xml_element(e);
						if (obj_model != null)
						{
							obj_model.add_to_group(kdtree);	
						}
						else
						{
							return null;
						}
					}
					else
					{
						Primitive p = load_primitive_from_element(e);
						if (p == null)
						{
							return null;
						}
						else
						{
							kdtree.add(p);
						}
					}
				}
			}

			kdtree.rebuild_index();
			return kdtree;
		}
		else if (primitive_type.equals("sphere"))
		{
			return Sphere.read_from_xml_element(element);
		}
		else if (primitive_type.equals("quadric"))
		{
			return Quadric.read_from_xml_element(element); 
		}
		else if (primitive_type.equals("triangle"))
		{
			return Triangle.read_from_xml_element(element);
		}
		else if (primitive_type.equals("quad"))
		{
			return Quad.read_from_xml_element(element);
		}
		else if (primitive_type.equals("disc"))
		{
			return Disc.read_from_xml_element(element);
		}
		else if (primitive_type.equals("infinitePlane"))
		{
			return InfinitePlane.read_from_xml_element(element);
		}
		else if (primitive_type.equals("axisAlignedBox"))
		{
			return AxisAlignedBox.read_from_xml_element(element);
		}
		else
		{
			return null;
		}
	}

	private static Light load_light_from_element (final Element element)
	{
		String primitive_type = element.getTagName();
		if (primitive_type.equals("pointLight"))
		{
			final Point position = XmlHelpers.get_point(element, "position");
			final RGB color = XmlHelpers.get_color(element, "color");

			if (position == null || color == null)
			{
				return null;
			}
			else
			{
				return new PointLight(position, color);
			}
		}
		else if (primitive_type.equals("spotLight"))
		{
			final Point position = XmlHelpers.get_point(element, "position");
			final Vector direction = XmlHelpers.get_vector(element, "direction");
			final Double angle = XmlHelpers.get_double(element, "angle");
			final Double exp = XmlHelpers.get_double(element, "exp");
			final RGB color = XmlHelpers.get_color(element, "color");

			if (position == null || direction == null || angle == null ||
			    exp == null || color == null)
			{
				return null;
			}
			else
			{
				return new SpotLight(position, direction, angle, exp, color);
			}
		}
		else if (primitive_type.equals("directionalLight"))
		{
			final Vector direction = XmlHelpers.get_vector(element, "direction");
			final RGB color = XmlHelpers.get_color(element, "color");

			if (direction == null || color == null)
			{
				return null;
			}
			else
			{
				return new DirectionalLight(direction, color);
			}
		
		}
		else
		{
			return null;
		}
	}

	public static Integrator load_from_file (final String filename)
	{
		File file = new File(filename);
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		List<Light> lights = new ArrayList<Light>();

		DocumentBuilder builder = null;
		try
		{
			builder = factory.newDocumentBuilder();
		}
		catch (ParserConfigurationException e)
		{
			System.err.println(e);
			return null;
		}

		Document document = null;
		try
		{
			document = builder.parse(file);
		}
		catch (SAXException|IOException e)
		{
			System.err.println(e);
			return null;
		}

		document.getDocumentElement().normalize();

		Element root = document.getDocumentElement();
		if (!root.getTagName().equals("world"))
		{
			System.err.println("The root element of the document is not \"world\".");
			return null;
		}

		RGB bgcolor = RGB.from_string(root.getAttribute("bgcolor"));		
		if (bgcolor == null)
		{
			System.out.println("The background color of the world not set, defaulting to black");
			bgcolor = new RGB(0.0);
		}

		String integrator_name = root.getAttribute("integrator");
		Integrator integrator;
		if (integrator_name.isEmpty())
		{
			System.err.println("The world has no integrator set");
			return null;
		}
		else if (integrator_name.equals("cosine"))
		{
			integrator = new CosineIntegrator();
		}
		else if (integrator_name.equals("raytracing"))
		{
			integrator = new RayTracingIntegrator();
		}
		else
		{
			System.err.println("Unknown integrator \"" + integrator_name + "\"");	
			return null;
		}

		boolean have_camera = false;
		Camera camera = null;

		boolean have_primitive = false;
		Primitive primitive = null;

		NodeList node_list = root.getChildNodes(); 
		for (int i = 0; i < node_list.getLength(); i++)
		{
			Node node = node_list.item(i);
			if (node.getNodeType() == Node.ELEMENT_NODE)
			{
				Element element = (Element)node;
				String element_name = element.getTagName();

				if (camera_set.contains(element_name))
				{
					if (have_camera)
					{
						System.err.println("Only one camera can be defined");	
						return null;
					}
					else
					{
						camera = load_camera_from_element(element);
						if (camera == null)
						{
							System.err.println("Unable to parse the camera");	
							return null;
						}

						have_camera = true;
					}
				}
				else if (light_set.contains(light_set))
				{
					final Light light = load_light_from_element(element);
					if (light != null)
					{
						lights.add(light);
					}
				}
				else if (primitive_set.contains(element_name))
				{
					if (have_primitive)
					{
						System.err.println("The scene can be defined by only one primitive");	
						return null;
					}
					else
					{
						primitive = load_primitive_from_element(element);
					}
				}
				else
				{
					System.out.println("Unknown element: \"" + element_name + "\"");
					return null;
				}
			}
		}

		World world = new World(primitive, bgcolor, camera);
		for (final Light light : lights)
		{
			world.add_light(light);
		}

		integrator.set_world(world);

		return integrator;
	}
}

