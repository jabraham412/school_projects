import java.io.*;

public class BSTester
{
	public static void main( String args[] ) throws Exception
	{
		BST myBST = new BST();

		BufferedReader infile = new BufferedReader( new FileReader(args[0]) );
		while (infile.ready())
			myBST.insert( infile.readLine() );
		infile.close();

		// DO NOT EDIT MAIN

		System.out.println("\nlevelOrder print of BST: ");
		myBST.levelOrderPrint();
		System.out.println();

	} // END MAIN
} // END STACK TESTER CLASS

