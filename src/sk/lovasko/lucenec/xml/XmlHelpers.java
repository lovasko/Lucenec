package sk.lovasko.lucenec.xml;

import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;

public final class XmlHelpers
{
	public static Double get_double (Element element, String name)
	{
		Double d = null;
		NodeList list = element.getElementsByTagName(name);
		if (list.getLength() > 0)
		{
			try
			{
				d = Double.valueOf(list.item(0).getTextContent());
			}
			catch (NumberFormatException e)
			{
				System.err.println(e);
				return null;
			}
		}
		else
		{
			System.err.println("No \"" + name + "\" defined");	
			return null;
		}

		return d;
	}

	public static Point get_point (Element element, String name)
	{
		NodeList list = element.getElementsByTagName(name);
		Point point = null;
		if (list.getLength() > 0)
		{
			point = Point.from_string(list.item(0).getTextContent());
			if (point == null)
			{
				System.err.println("Unable to parse the \"" + name + "\"");
				return null;
			}
		}
		else
		{
			System.err.println("No \"" + name + "\" defined");	
			return null;
		}

		return point;
	}

	public static Vector get_vector (Element element, String name)
	{
		NodeList list = element.getElementsByTagName(name);
		Vector vector = null;
		if (list.getLength() > 0)
		{
			vector = Vector.from_string(list.item(0).getTextContent());
			if (vector == null)
			{
				System.err.println("Unable to parse the \"" + name + "\"");
				return null;
			}
		}
		else
		{
			System.err.println("No \"" + name + "\" defined");	
			return null;
		}

		return vector;
	}

	public static RGB get_color (Element element, String name)
	{
		NodeList list = element.getElementsByTagName(name);
		RGB color = null;
		if (list.getLength() > 0)
		{
			color = RGB.from_string(list.item(0).getTextContent());
			if (color == null)
			{
				System.err.println("Unable to parse the \"" + name + "\"");
				return null;
			}
		}
		else
		{
			System.err.println("No \"" + name + "\" defined");	
			return null;
		}

		return color;
	}
}

