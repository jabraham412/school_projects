import java.io.*;
import java.util.*;

public class STRING_Driver
{
	public static void main( String[] args ) throws Exception
	{
		BufferedReader infile = new BufferedReader( new FileReader( args[0] ) );
		STRING_BST myBST = new STRING_BST();
		while ( infile.ready() )
			myBST.insert( infile.readLine() );
		myBST.inOrderPrint(); // DUMPING TREE - dupes were rejected at insertion time
		myBST=null;
	}
}
