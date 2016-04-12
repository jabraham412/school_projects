import java.io.*;
import java.util.*;

public class GENERIC_Driver
{
	public static void main( String[] args ) throws Exception
	{
		BufferedReader infile = new BufferedReader( new FileReader( args[0] ) );

		GENERIC_BST<String> bst1 = new GENERIC_BST<String>();

		System.out.println( "inserting: ");
		while (infile.ready())
		{
			String s=infile.readLine();
			System.out.print( s + " " );
			bst1.insert( s );
		}
		System.out.println();

		GENERIC_BST<Integer> bst2 = new GENERIC_BST<Integer>();

		Random generator = new Random(17);
		System.out.println( "inserting: ");
		for (int i= 0; i<30 ; ++i)
		{
			int r = generator.nextInt( 31 );
			System.out.print( r + " ");
			bst2.insert( r );  // primitive r gets autobox converted to a capitol I Integer Object
		}
		System.out.println();

		System.out.println("\nDUMPING BOTH BSTs (**notice that dupes were rejected at insertion time)\n");
		bst1.inOrderPrint();
		bst2.inOrderPrint();
	}
}
