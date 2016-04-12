public class LL
{
    private LE head;
    private LE next;

	public LL() throws Exception
	{
		head = null;
	}

	public void insertAtHead(String data)
	{
		// YOUR CODE HERE
                head = new LE(data, head);
	}

	// make head point to whatever is AFTER the first element
	// by doing so we leap frog over the first element and remove it from the list
	public void removeAtHead()
	{
		// YOUR CODE HERE
            if(head != null)
            {
                head = head.getNext();
            }
	}

	// search the list for an LE with this data in it. Remove this node
	// remove by making the LE before it point to the one after it.
	public void remove( String data )
	{
		// YOUR CODE HERE
        boolean b = false;
        LE var = head;

        if(var != null)
        {
            while(var.getNext() != null )
            {
                if(data.equals(var.getData())) {
                    b = true;
                    break;
                }
                
                var = var.getNext();
            }

            if(b == true)
            {
                LE H, H2;
                H = var;
                H2 = var.getNext();

                //set out variable equal to the next variable data and next variable pointer.
                //eliminating the variable from the list.
                H.setData(H2.getData());
                H.setNext(H2.getNext());
            }
        }
	}


	public void insertAtTail( String data)
	{
		// YOUR CODE HERE
                if(head == null)
                {
                     this.insertAtHead(data);
                }
                else
                {
                    
                    LE var = head;
                    while(var.getNext() != null)
                    {
                            var = var.getNext();
                    }
                    
                    LE var2 = new LE(data, null);
                    var.setNext(var2);
                }
	}
        
	public void removeAtTail()
	{
		// YOUR CODE HERE
                LE var = head;
                while(var.getNext().getNext() != null)
                {
                        var = var.getNext();
                }
                var.setNext(null);
                
	}

	public int length()
	{
                return lengthRecursive( head );
	}
        
        private int lengthRecursive( LE head )
	{
		if (head == null)
                {
                    return 0;
                }
                else
                {
                    return 1 + lengthRecursive( head.getNext() );
                }
        }


	public boolean search( String data )
	{
                    //YOUR CODE HERE                
                    LE var = head;
                    while(var != null )
                    {
                        if(data.equals(var.getData()))
                        {
                            return true;
                        }
                        else
                            var = var.getNext();
                    }
                    return false;
	}
        

	public void makeEmpty()
	{
		// YOUR CODE HERE
                head = null;
	}

	// GIVEN AS IS DO NOT MODIFY
	public String toString()
	{
		String s = new String();

		for (LE curr = head; curr != null; curr = curr.getNext())
		{
			s += curr.getData().toString();
			if (curr.getNext() != null)
				s += "->";
		}
		s += "\n";

		return s;
	}
} // END LINKED LIST CLASS
