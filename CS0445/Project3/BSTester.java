import java.io.*;

public class BSTester
{
	public static void main( String args[] ) throws Exception
	{
		BST bst1 = new BST();

		BufferedReader infile = new BufferedReader( new FileReader(args[0]) );
		while (infile.ready())
			bst1.insert( infile.readLine() );
		infile.close();

/*      THE TREE IS BUILT. FIRST PHASE IS TO TEST THE PASSIVE OPERATIONS

                        H
               D                 L
          B        F         J        N
        A   C    E   G     I   K    M   O
*/
		System.out.print("\ninOrderPrint: ");
		bst1.inOrderPrint();    // A B C D E F G H I J K L M N O
		System.out.println("Number of nodes: " + bst1.countNodes() );
		System.out.println("Number of leaves: " + bst1.countLeaves() );
		System.out.println("Number of levels: " + bst1.depth() );   // 4 i.e. depth
		System.out.println("Level counts:");
		bst1.printLevelCounts();
		/*
			0: 1
			1: 2
			2: 4
			3: 8
			etc.
		*/
		System.out.println("leftmost element: " + bst1.leftMost() );      // "A" i.e. smallest value in BST
		System.out.println("rightmost element: " + bst1.rightMost() );     // "O" i.e. largest value in BST
		System.out.println("BST is full? " + bst1.isFull() );             // true   see lecture note for definition
		System.out.println("BST is complete? " + bst1.isComplete() );     // true   see lecture note for definition

		// TEST THE OPERATIONS THAT MODIFY THE TREE BY REMOVING EVERY NODE IN THE TREE

		bst1.remove( "I" );
		System.out.print("BST after removal of 'I': ");
		bst1.inOrderPrint();  // A B C D E F G H J K L M N O

		bst1.remove( "J" );
		System.out.print("BST after removal of 'J': ");
		bst1.inOrderPrint();  // A B C D E F G H K L M N O

		bst1.remove( "D" );
		System.out.print("BST after removal of 'D': ");
		bst1.inOrderPrint();

		bst1.remove( "L" );
		System.out.print("BST after removal of 'L': ");
		bst1.inOrderPrint();

		bst1.remove( "B" );
		System.out.print("BST after removal of 'B': " );
		bst1.inOrderPrint();

		bst1.remove( "F" );
		System.out.print("BST after removal of 'F': ");
		bst1.inOrderPrint();

		bst1.remove( "N" );
		System.out.print("BST after removal of 'N': ");
		bst1.inOrderPrint();

		bst1.remove( "A" );
		System.out.print("BST after removal of 'A': ");
		bst1.inOrderPrint();

		bst1.remove( "C" );
		System.out.print("BST after removal of 'C': ");
		bst1.inOrderPrint();

		bst1.remove( "E" );
		System.out.print("BST after removal of 'E': ");
		bst1.inOrderPrint();

		bst1.remove( "G" );
		System.out.print("BST after removal of 'G': ");
		bst1.inOrderPrint();

		bst1.remove( "K" );
		System.out.print("BST after removal of 'K': ");
		bst1.inOrderPrint();

		bst1.remove( "M" );
		System.out.print("BST after removal of 'M': ");
		bst1.inOrderPrint();

		bst1.remove( "O" );
		System.out.print("BST after removal of 'O': ");
		bst1.inOrderPrint();

		bst1.remove( "H" );
		System.out.print("BST after removal of 'H': ");
		bst1.inOrderPrint();
	} // END MAIN
} // END STACK TESTER CLASS
