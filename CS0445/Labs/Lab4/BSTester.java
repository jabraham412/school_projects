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

		System.out.println("\nmyBST after loading from " + args[0]); // invokes toString
		myBST.inOrderPrint();

		BSTNode location;

		location = myBST.search("ali");
		System.out.println("myBST contains 'ali': " +  (location!=null) );

		location = myBST.search("mike");
		System.out.println("myBST contains 'mike': " +  (location!=null) );

		location = myBST.search("aaron");
		System.out.println("myBST contains 'aaron': " +  (location!=null) );

		location = myBST.search("zorba");
		System.out.println("myBST contains 'zorba': " +  (location!=null) );


	} // END MAIN
} // END STACK TESTER CLASS

