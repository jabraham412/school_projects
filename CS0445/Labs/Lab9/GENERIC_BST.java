// John Abraham
// CS 445 - Lab 9
// Templating a class to be GENERIC

public class GENERIC_BST < T extends Comparable <T> >
{
            private STRING_BSTNode<T> root;

            // GIVEN
            public void insert( T data )
            {
                    root = insertHelper( root, data );
            }
            // GIVEN
            private STRING_BSTNode<T> insertHelper( STRING_BSTNode<T> root, T data )
            {
                    if (root == null) return new STRING_BSTNode<T>( data ); // L,R default to null
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

            class STRING_BSTNode < T extends Comparable <T> >
            {
                    public T data;
                    public STRING_BSTNode<T> left,right;
                    public STRING_BSTNode ( T data )
                    {
                            this.data = data;
                    }
            }

}// end of GENERIC_BST class
