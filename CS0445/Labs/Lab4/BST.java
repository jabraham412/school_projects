public class BST
{
	private BSTNode root;

	// all of the following methods require recursive helper methods
	// because they require the root be passed in and recursed on.
	// the code in main can't pass in the root

	public void insert( Comparable data )
	{
		root = insertHelper( root, data ); 
	}
	private BSTNode insertHelper( BSTNode root, Comparable data )
	{
		// YOUR CODE HERE
                if(root == null)
                {
                    root = new BSTNode (data, null, null);

                }
                else
                {
                      if(root.getData().compareTo(data) < 0)
                      {
                          root.setRight( insertHelper (root.getRight(), data) );
                      }
                      else
                      {
                          root.setLeft( insertHelper (root.getLeft(), data) );
                      }
                }
                
            
                return root;
    } // END insertHelper


	public BSTNode search( Comparable data )
	{
		return searchHelper( root, data );
	}
	private BSTNode searchHelper( BSTNode root, Comparable key )
	{
		// YOUR CODE HERE
		if ( root == null )
			return root;

		else if ( key.compareTo(root.getData()) < 0 ) 
			return searchHelper ( root.getLeft(), key );

		else if ( key.compareTo(root.getData()) > 0 ) 
			return searchHelper ( root.right, key );

		else                                     
			return root;  
		
	}


	// DO NOT MODIFY:  prints node out in sorted order
	public void inOrderPrint()
	{
		inOrderPrintHelper( root );
		System.out.println();
	}
	private void inOrderPrintHelper( BSTNode root )
	{
		if (root==null) return;
		inOrderPrintHelper( root.getLeft() );
		System.out.print( root.getData() + " " );
		inOrderPrintHelper( root.getRight() );
	}


} //EOF
