package sk.lovasko.lucenec;

public final class Int3
{
	private int[] data;
	private boolean[] present;

	Int3 () 
	{ 
		data = new int[3];
		present = new boolean[3];

		set_presence(0, false);
		set_presence(1, false);
		set_presence(2, false);
	}

	Int3 (final int a, final int b, final int c)
	{
		data = new int[3];
		present = new boolean[3];
		
		set_data(0, a);
		set_data(1, b);
		set_data(2, c);
	}

	public final int get_data (final int index)
	{
		return data[index];
	}

	public final void set_data (final int index, final int _data)
	{
		data[index] = _data;
		set_presence(index, true);
	}

	public final boolean get_presence (final int index)
	{
		return present[index];
	}

	public final void set_presence (final int index, final boolean presence)
	{
		present[index] = presence;
	}

	public final void decrement (final int index, final int how_much)
	{
		if (!present[index])
		{ 
			return;
		}

		data[index] -= how_much;
	}

	public final void decrement_all (final int how_much)
	{
		decrement(0, how_much);	
		decrement(1, how_much);	
		decrement(2, how_much);	
	}

	public final void turn_around (final int index, final int around_what)
	{
		if (!present[index])
		{ 
			return;
		}

		if (data[index] < 0)
		{
			data[index] = around_what + data[index];
		}
	}
}

