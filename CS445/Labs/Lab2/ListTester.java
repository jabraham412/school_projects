import java.io.*;
import java.util.*;

public class ListTester
{
	public static void main( String args[] ) throws Exception
	{
		LL myList = new LL();

		// ### LOAD LIST USING INSERT AT HEAD ###

		BufferedReader infile = new BufferedReader( new FileReader(args[0]) );
		while (infile.ready())
			myList.insertAtHead( infile.readLine() );
		infile.close();

		// test the length and search methods

		System.out.print("\nmyList after loading with insertAtHead\n" + myList);
		System.out.println("myList length: " +  myList.length() );
		System.out.println("myList contains cmanson? " + myList.search( "cmanson" ) );
		myList.removeAtHead();
		System.out.print("myList after removeAtHead\n" + myList);
		myList.removeAtTail();
		System.out.print("myList after removeAtTail\n" + myList);
		myList.remove("aardvaark");
		System.out.print("myList after remove aardvaark\n" + myList);
		myList.remove("zebra");
		System.out.print("myList after remove zebra\n" + myList);
		myList.remove("cmanson");
		System.out.print("myList after remove cmanson\n" + myList);
		System.out.println("myList length: " +  myList.length() );

		// ### LOAD LIST USING INSERT AT TAIL ###

		infile = new BufferedReader( new FileReader(args[0] ) );
		myList.makeEmpty(); // start over with an empty list

		while (infile.ready())
			myList.insertAtTail( infile.readLine() );
		infile.close();

		System.out.print("\nmyList after loading with insertAtTail\n" + myList);
		System.out.println("myList length: " +  myList.length() );
		System.out.println("myList contains dbock? " + myList.search( "dbock" ) );
		myList.removeAtHead();
		System.out.print("myList after removeAtHead\n" + myList);
		myList.removeAtTail();
		System.out.print("myList after removeAtTail\n" + myList);
		myList.remove("aardvaark");
		System.out.print("myList after remove aardvaark\n" + myList);
		myList.remove("zebra");
		System.out.print("myList after remove zebra\n" + myList);
		myList.remove("dbock");
		System.out.print("myList after remove dbock\n" + myList);
		System.out.println("myList length: " +  myList.length() );

	} // END MAIN
} // END LIST TESTER CLASS

