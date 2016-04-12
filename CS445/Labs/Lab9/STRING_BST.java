import java.util.*;
import java.io.*;


public class STRING_BST
{
	private STRING_BSTNode root;

	// GIVEN
	public void insert( String data )
	{
		root = insertHelper( root, data );
	}
	// GIVEN
	private STRING_BSTNode insertHelper( STRING_BSTNode root, String data )
	{
		if (root == null) return new STRING_BSTNode(data); // L,R default to null
		int comp = data.compareTo( root.data );
		if (comp==0) return root;
		if (comp < 0)
			root.left = insertHelper(root.left, data  );
		else
			root.right = insertHelper(root.right, data  );

		return root;
	} // END insertHelper

	// GIVEN
	public void inOrderPrint()
	{
		inOrderPrintHelper( root );
		System.out.println();
	}
	// GIVEN
	public void inOrderPrintHelper( STRING_BSTNode root)
	{
		if (root==null) return;
		inOrderPrintHelper( root.left );
		System.out.print(root.data + " ");
		inOrderPrintHelper( root.right );
	}

	class STRING_BSTNode
	{
		public String data;
		public STRING_BSTNode left,right;
		public STRING_BSTNode( String data )
		{
			this.data = data;
		}
	}


} // END STRING_BST CLASS


