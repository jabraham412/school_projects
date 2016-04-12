import java.util.*;
import java.io.*;

public class LL
{
	private LE head = null;  // pointer to the front (first) element of the list.  None yet
        
	public LL( String fileName, String insertionMode ) throws Exception
	{
		head = loadList( fileName ,  insertionMode); // compiler does this anyway. just for emphasis
	}

	public LL( LL other )
	{
            	// YOUR CODE HERE
                LE otherHead = other.head;
                while(otherHead != null)
                {
                          head = insertInOrder(otherHead.getData());
                          otherHead = otherHead.getNext();
                }

	}

	//---------------------------------------------------------

	public LE loadList( String fileName , String insertionMode ) throws Exception
	{
			Scanner inFile = new Scanner( new File( fileName) );
			String data;

			while ( inFile.hasNext() )
			{
				data =  inFile.next();

				if (insertionMode.equals( "head" ))
					head = insertAtHead( data );
				else if (insertionMode.equals( "tail" ))
					head =  insertAtTail(  data );
				else if (insertionMode.equals( "ordered" ))
					head = insertInOrder( data  );
				else
					throw new RuntimeException("Unrecognized insertion mode: <" + insertionMode + ">");
			}
			inFile.close();
			return head;
	}


	public LE insertAtHead(String data)
	{
		return insertAtHeadRecursive(head, data); // need recursive version since main can't pass head in (it's private)
	}

	private LE insertAtHeadRecursive(LE head, String data)
	{
		return new LE(data,head); // need recursive version since main can't pass head in (it's private)
	}

	public LE insertAtTail( String data)
	{
		return insertAtTailRecursive(head, data); // need recursive version since main can't pass head in (it's private)
	}

 	private  LE insertAtTailRecursive( LE head, String data)
 	{
 	   if (head == null)
 	     head = new LE(data,null);
 	   else
 	     head.setNext( insertAtTailRecursive(head.getNext(), data) );

 	   return head;
 	 }

	public  LE insertInOrder (String data)
	{
		return insertInOrderRecursive( head, data); // need recursive version since main can't pass head in (it's private)
	}

 	private  LE insertInOrderRecursive(LE head, String data)
 	{
     		// YOUR CODE HERE
                LE newData;
                if(head == null)
                {
                    head = new LE(data, head);
                    
                }
                else
                {
                    if(head.getData().compareTo(data) < 0)
                    {
                        head.setNext( insertInOrderRecursive(head.getNext(), data) );

                    }
                    else
                    {
                        newData = new LE (data, head);
                        head = newData;


                    }
                }
                return head;                
  	}

	public LE removeFirst(String data)
	{
		return removeFirstRecursive( head,  data); // need recursive version since main can't pass head in (it's private)
	}

  	private LE removeFirstRecursive(LE head, String data)
	{
		// YOUR CODE HERE
                LE var = head;
                
                if(var == null)
                {
                    return null;
                }
                else
                {
                     if(data.equals(var.getData()))
                     {
                         return var.getNext();
                     
                     }
                     else
                     {
                         var.setNext( removeFirstRecursive ( var.getNext(), data ) );
                         return var;
                     
                     }
                }
  	}

	public LE removeAll(String data)
	{ 
		return removeAllRecursive( head,  data); // need recursive version since main can't pass head in (it's private)
	}

	private  LE removeAllRecursive(LE head, String data)
	{
		// YOUR CODE HERE
                LE var = head;
                
                if(var == null)
                {
                    return null;
                }
                else
                {
                     if(data.equals(var.getData()))
                     {
                         if(var.getData().equals(var.getNext().getData()))
                         {
                             var = var.getNext().getNext();
                         
                         }
                         else
                         {
                             var = var.getNext();
                             var.setNext( removeAllRecursive ( var.getNext(), data ) );
                         }
                     }
                     else
                     {
                         var.setNext( removeAllRecursive ( var.getNext(), data ) );
                         return var;
                     
                     }
                }
                 
                return var;

                
        }

	public boolean search(String data)
	{
		return searchRecursive( head,  data); // need recursive version since main can't pass head in (it's private)
	}

	private boolean searchRecursive(LE head, String data)
	{
		// YOUR CODE HERE   
                LE var = head;
                if(var != null)
                {
                    if(data.equals(var.getData()))
                    {
                        return true;
                    }
                    else
                        return searchRecursive(var.getNext(), data);
                }
                else
                    return false;
                    
        }
        
	public boolean equals(LL other)
	{
		return equalsRecursive( head, other.head); // need recursive version since main can't pass head in (it's private)
	}

	private boolean equalsRecursive(LE head, LE otherHead)
	{
		// YOUR CODE HERE
		if(head != null && otherHead != null)
		{
			if(head.getData().equals(otherHead.getData()))
                            {
                                    head = head.getNext();
                                    otherHead = otherHead.getNext();
                                    return equalsRecursive(head, otherHead);
                                    //if the else body is never reached true is returned
                                    //by reaching the end.
                            }
			else
                            return false; 
                        
                }else
                    
                    return true;
            
	}

	public int length(  )
	{
			return lengthRecursive( head ); // need recursive version since main can't pass head in (it's private)
	}

	private int lengthRecursive( LE head )
	{
		if (head==null) return 0;
		return 1 + lengthRecursive( head.getNext() );
	}

	public boolean isEmpty()
	{
		// YOUR CODE HERE
                if (head == null)
                        return true;
                else
                        return false;
	}

	public void makeEmpty()
	{
		// YOUR CODE HERE
                head = null;
        
        }

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
