import java.util.*;
import java.io.*;

public class BST
{
	private BSTNode root;
	int counter = 0;

	// GIVEN
	public void insert( Comparable data )
	{
		root = insertHelper( root, data );
	}
	// GIVEN
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

	// GIVEN
	public void inOrderPrint()
	{
		inOrderPrintHelper( root );
		System.out.println();
	}
	// GIVEN
	public void inOrderPrintHelper( BSTNode root)
	{
		if (root==null) return;
		inOrderPrintHelper( root.getLeft() );
		System.out.print(root.getData() + " ");
		inOrderPrintHelper( root.getRight() );
	}

	// 	COUNT NODES  	counts all nodes leaves, root
	public int countNodes()
	{
		return countNodesHelper(root);
	}
	public int countNodesHelper(BSTNode root)
	{
		if(root == null)
			return 0;
		else
		{
			if(root.getLeft() == null && root.getRight() == null)
                return 1;	//return 1 when a leaf node is encountered.
			else
                //add 1 to each previous activation to get total number of nodes.
                return (1 + countNodesHelper( root.getLeft() ) + countNodesHelper( root.getRight() ) );
		}
	}

	// 	COUNT LEAVES  	counts only nodes with no children
	public int countLeaves()
	{
		return countLeavesHelper(root);
	}
	public int countLeavesHelper(BSTNode root)
	{
		if(root == null)	//if root is null return 0
			return 0;
		else
		{
			//a leaf is a node that points to null (has no children).
			//If left and right child nodes are null return 1.
			if(root.getLeft() == null && root.getRight() == null)
				return 1;
			else
				return (countLeavesHelper( root.getLeft() ) + countLeavesHelper( root.getRight() ));
		}
	}

	// DEPTH: number of levels. A tree of one node has depth of 1
	// the root is at level 0
	public int depth()
	{
		return depthHelper(root);
	}
	public int depthHelper(BSTNode root)
	{
		if(root == null)
			return 0;
		else
		{
			if(root.getLeft() == null && root.getRight() == null)
				return 1;
			else
				return( 1 + depthHelper( root.getLeft() ) );
		}
	}
	// PRINT LEVEL COUNTS
	public void printLevelCounts()
	{
		for(int i = 0; i < depth(); i++)
		{
			printLevelCountsHelper(root, i);
			System.out.print(i+ ":\t" +counter);
			System.out.println();
			counter = 0;
		}
	}
	public void printLevelCountsHelper(BSTNode root, int desire)
	{
		if(desire < 0)
			return;

       	Queue<BSTNode> q  = new LinkedList<BSTNode>();
		Queue<Integer> levels = new LinkedList<Integer>();

		q.add(root);
		levels.add(0);

		while(!q.isEmpty())
		{
			BSTNode temp = q.poll();
			int currLevel = levels.poll();
			if(temp == null)
				return;
			else if(currLevel == desire)
				counter++;
			else
			{
				q.add(temp.getLeft());
				levels.add(currLevel+1);
				q.add(temp.getRight());
				levels.add(currLevel+1);
			}
		}
	}
// LEFTMOST  return the data value in the very left most node in the tree (i.e. smallest value)
	public Comparable leftMost()
	{
		return leftMostHelper(root);
	}
	public Comparable leftMostHelper(BSTNode root)
	{
		if(root == null)
			return null;
		else
		{
			if(root.getLeft() == null && root.getRight() == null)
				return root.getData();
			else
				return( leftMostHelper( root.getLeft() ) );
		}
	}

	// RIGHTMOST  return the data value in the very right most node in the tree ( i.e. largest value)
	public Comparable rightMost()
	{
		return rightMostHelper(root);
	}
	public Comparable rightMostHelper(BSTNode root)
	{
		if(root == null)
			return null;
		else
		{
			if(root.getLeft() == null && root.getRight() == null)
				return root.getData();
			else
				return( rightMostHelper( root.getRight() ) );
		}
	}

	public boolean isFull()
	{
		return isFullHelper(root);
	}
	public boolean isFullHelper(BSTNode root)
	{
		//recursively traverse the tree checking if any node has
		//only one child node. If found return false
		if(root == null)
			return false;
		else
		{
	        if(root.getLeft() != null && root.getRight() == null)
	        	return false;
	        else if(root.getLeft() == null && root.getRight() != null)
	        	return false;
	        
	        isFullHelper( root.getLeft() );
	        isFullHelper( root.getRight() );

	        //return true of none of these conditions were met.
	        //In this case return true when all nodes either have
	        //2 child nodes or none.
	        return true;
    	}
	}

	public boolean isComplete()
	{
		return isCompleteHelper(root);
	}
	public boolean isCompleteHelper(BSTNode root)
	{
		//Complete binary tree: completely filled
		//with the possible exception of the bottom level
		//which is filled from left to right.
		//traverse recursively through every node with exception to
		//the leaves. Return false if any node has only one child
		if(root == null)
			return false;
		else
		{
			//make the exception for the bottom level
			if(root.getLeft() != null && root.getRight() != null)
			{	
				//traverse left to right
				isCompleteHelper( root.getLeft() );

				if(root.getLeft() != null && root.getRight() == null)
		        	return false;
		        if(root.getLeft() == null && root.getRight() != null)
		        	return false;

		        isCompleteHelper( root.getRight() );
			}
			//return true if none of these conditions have been met
	   		return true;
		}
	}

	// You may assume that any node to be deleted actually exists in the tree
	// This makes it a lot simpler
	public void remove( Comparable data )
	{
    	root = removeHelper(root, data);
	}
	public BSTNode removeHelper(BSTNode root, Comparable data)
	{
		//case 1: node has no children (leaf)
		//case 2: node only has one child node
		//case 3: node has two children
		if(root == null)
			return null;
		if(root.getData().compareTo(data) == 0)
		{
			if(root.getLeft() == null)
					return root.getRight();

			else if(root.getRight() == null)
					return root.getLeft();
	        else 
	        {
	               if (root.getRight().getLeft() == null) 
	               {
		                 root.setData(root.getRight().getData());
		                 root.setRight(root.getRight().getRight());
		                 return root;
	               } 
	               else
	               {
		                 root.setData(removeSmallest(root.getRight()));
		                 return root;
	               }
	        }
	    }
	    else  if (data.compareTo(root.getData()) < 0) 
	    {
	       root.setLeft(removeHelper(root.getLeft(), data));
	       return root;
	    } 
	    else if(data.compareTo(root.getData()) > 0)
	    {
	       root.setRight(removeHelper(root.getRight(), data));
	       return root;
	    }
           return root;
        }
        
        private Comparable removeSmallest(BSTNode var)
        {
                if (var.getLeft().getLeft() == null)
                {
                Comparable smallest = var.getLeft().getData();
                var.setLeft(var.getLeft().getRight());
                return smallest;
                }
                else 
                    return removeSmallest(var.getLeft());
        }
} // END BST CLASS
