import java.io.*;

public class Stack
{
	LE stack;
	LE next;

	// 25% push : add an element to the front/top of the stack
	public void push( Comparable data )
	{
		stack = new LE(data, stack);
		
	}
    // 35% pop : remove the first element and return that element's data value
	public Comparable pop()
	{
		// YOUR CODE HERE
		Comparable var;

		if(stack != null)
            	{
			var = stack.getData();                	
			stack = stack.getNext();
            	}
		else
			return null;

		return var;

	}

    // 20% front : return (but not remove) front element's data value
	public Comparable front()
	{
		// YOUR CODE HERE
		
		if(stack != null)
		{
			return stack.getData();
		}
		else
		{
			return null;
		}

	}

    // 10% empty : boolean tells if stack is empty
	public boolean empty()
	{
		// YOUR CODE HERE
		if(stack == null)
			return true;
		else
			return false;

	}

    // 10% makeEmpty : remove all the elements from the list making stack ptr == NULL
	public void  makeEmpty()
	{
		// YOUR CODE HERE
		stack = null;

	}

	public String toString()
	{
		String s= "";
		if (empty()) return s;

		s= "[stack:] --> ";
		LE iter = stack;
		while (iter!=null)
		{
			s += iter.getData();
			if (iter.getNext() != null) s+= " --> ";
			iter = iter.getNext();
		}
		return s;
	}

}
