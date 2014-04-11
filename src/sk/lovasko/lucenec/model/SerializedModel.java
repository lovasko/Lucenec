package sk.lovasko.lucenec.model;

import sk.lovasko.lucenec.solid.Solid;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.ObjectInputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.Serializable;
import java.util.ArrayList;

public final class SerializedModel extends Model implements Serializable
{
	public SerializedModel (final String _filename)
	{
		filename = _filename;
	}

	public SerializedModel (final String _filename, 
		ArrayList<Solid> _solids)
	{
		this(_filename);
		solids = _solids;	
	}

	public final boolean load ()
	{
		try
		{
			FileInputStream file_in = new FileInputStream(filename);
			ObjectInputStream in = new ObjectInputStream(file_in);
			SerializedModel sm = (SerializedModel) in.readObject();
			in.close();
			file_in.close();

			solids = sm.solids;

			return true;
		}
		catch (IOException|ClassNotFoundException e)
		{
			System.out.println(e);
			return false;
		}
	}

	public final boolean save ()
	{
		try
		{
			FileOutputStream file_out = new FileOutputStream(filename);
			ObjectOutputStream out = new ObjectOutputStream(file_out);
			out.writeObject(this);
			out.close();
			file_out.close();

			return true;
		}
		catch (IOException e)
		{
			System.out.println(e);
			return false;
		}
	}
}

