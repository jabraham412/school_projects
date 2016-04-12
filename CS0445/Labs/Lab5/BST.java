import java.util.*;
import java.io.*;

public class BST
{
	private BSTNode root;

	public void insert( Comparable data )
	{
		root = insertHelper( root, data );
	}
	private BSTNode insertHelper( BSTNode root, Comparable data )
	{
		if (root == null)
			return new BSTNode(data,null,null);
		int comp = data.compareTo( root.getData() );
		if (comp < 0)
			root.setLeft( insertHelper(root.getLeft(), data ) );
		else
			root.setRight( insertHelper(root.getRight(), data ) );

		return root;
         } // END insertHelper

	public void levelOrderPrint()
	{
		// YOU CODe HERE. YOU MAY DECLARE AN ARRAYLIST OR ANY OF JAVA'S BUILTIN ADTS
		// TO IMPLEMENT YOUR LEVEL PRINT. YOU ARE ALSO ALLOWED TO JUST USE RECURSION
		// TO DO THE LEVEL ORDER PRINT. USING A QUEUE IS HOWEVER THE SIMPLEST.
                if(root != null)
                {
                        Queue<BSTNode> q  = new LinkedList<BSTNode>();
                        q.offer( root );  //insert root into the queue

                        while( q.size( ) != 0 )
                        {
                                BSTNode currNode = q.poll();
                                System.out.print( currNode.getData() + " " );

                                if( currNode.getLeft() != null )
                                    q.offer( currNode.getLeft() );

                                if( currNode.getRight() != null )
                                    q.offer( currNode.getRight() );
                        }
                }
                    
        } // END LEVEL ORDER PRINT

} // END BST CLASS
