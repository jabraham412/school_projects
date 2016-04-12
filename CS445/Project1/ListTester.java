import java.io.*;
import java.util.*;

public class ListTester
{
	public static void main( String args[] ) throws Exception
	{

		// ### TEST CONSTRUCTOR THAT INSERTS AT HEAD ###

		System.out.println( "### TEST CONSTRUCTOR THAT INSERTS AT HEAD ###\n");
		LL myList = new LL( args[0], "head" ); // C'Tor calls loadList, uses insertAtFront to load elements

		System.out.println("myList:\n" +  myList ); // invokes toString
		System.out.println("myList length: " +  myList.length() ); // invokes toString

		System.out.println("myList contains hoffmant? " + myList.search( "hoffmant" )); // returns true or false
		System.out.println("myList contains cmanson? " + myList.search( "cmanson" )); // returns true or false
		System.out.println("myList contains zorro? " + myList.search( "zorro" )); // returns true or false
		System.out.println("myList contains bobcat? " + myList.search( "bobcat" )); // returns true or false
		System.out.println();
		myList.removeFirst("hoffmant");
		System.out.println("myList after removeFirst hoffmant:\n" + myList );
		myList.removeAll("cmanson");
		System.out.println("myList after removeAll cmanson:\n" + myList );
		myList.removeFirst("zorro");
		System.out.println("myList after removeFirst zorro:\n" + myList );
		myList.removeFirst("bobcat");
		System.out.println("myList after removeFirst bobcat:\n" + myList );
		myList.makeEmpty();
		System.out.println("myList after makeEmpty:\n" + myList );


		// ### TEST CONSTRUCTOR THAT INSERTS AT TAIL ###

		System.out.println( "### TEST CONSTRUCTOR THAT INSERTS AT TAIL ###\n");
		myList = new LL( args[0], "tail" ); // C'Tor calls loadList, uses insertAtTail to load elements
		System.out.println("myList:\n" +  myList ); // invokes toString
		System.out.println("myList length: " +  myList.length() ); // invokes toString

		System.out.println("myList contains hoffmant? " + myList.search( "hoffmant" )); // returns true or false
		System.out.println("myList contains cmanson? " + myList.search( "cmanson" )); // returns true or false
		System.out.println("myList contains zorro? " + myList.search( "zorro" )); // returns true or false
		System.out.println("myList contains bobcat? " + myList.search( "bobcat" )); // returns true or false
		System.out.println();
		myList.removeFirst("hoffmant");
		System.out.println("myList after removeFirst hoffmant:\n" + myList );
		myList.removeAll("cmanson");
		System.out.println("myList after removeAll cmanson:\n" + myList );
		myList.removeFirst("zorro");
		System.out.println("myList after removeFirst zorro:\n" + myList );
		myList.removeFirst("bobcat");
		System.out.println("myList after removeFirst bobcat:\n" + myList );
		myList.makeEmpty();
		System.out.println("myList after makeEmpty:\n" + myList );


		// ### TEST CONSTRUCTOR THAT INSERTS IN ORDER TAIL ###

		System.out.println( "### TEST CONSTRUCTOR THAT INSERTS IN ORDER ###\n\n");
		myList = new LL( args[0], "ordered" ); // C'Tor calls loadList, uses insertInOrder to load elements
		System.out.println("myList:\n" +  myList ); // invokes toString
		System.out.println("myList length: " +  myList.length() ); // invokes toString

		System.out.println("myList contains hoffmant? " + myList.search( "hoffmant" )); // returns true or false
		System.out.println("myList contains cmanson? " + myList.search( "cmanson" )); // returns true or false
		System.out.println("myList contains zorro? " + myList.search( "zorro" )); // returns true or false
		System.out.println("myList contains bobcat? " + myList.search( "bobcat" )); // returns true or false
		System.out.println();
		myList.removeFirst("hoffmant");
		System.out.println("myList after removeFirst hoffmant:\n" + myList );
		myList.removeAll("cmanson");
		System.out.println("myList after removeAll cmanson:\n" + myList );
		myList.removeFirst("zorro");
		System.out.println("myList after removeFirst zorro:\n" + myList );
		myList.removeFirst("bobcat");
		System.out.println("myList after removeFirst bobcat:\n" + myList );


		// ### TEST COPY CONSTRUCTOR AND LIST EQUALS METHOD ###

		System.out.println( "\n### TEST COPY CONSTRUCTOR AND LIST EQUALS METHOD ###\n");
		LL yourList = new LL( myList );  // Copy C'Tor make yourList a DEEP COPY of myList
		System.out.println("yourList: (should be identical to last print of mine)\n" +  yourList ); // it better look identical to last print of  mine
		System.out.println( "myList.equals(yourList)? " + myList.equals( yourList ) ) ; // better be true

		myList.removeFirst("hoffmant");
		System.out.println("myList after removeFirst hoffmant:\n" + myList );
		myList.removeAll("dbock");
		System.out.println("myList after removeAll dbock:\n" + myList );

		System.out.println("yourList: (should look same as its last print)\n" +  yourList ); // your List should not be NOT be affected by changes to myList
		System.out.println( "myList.equals(yourList)? " + myList.equals( yourList ) ) ; // better be false
		myList.makeEmpty();
		yourList = new LL( myList );

		System.out.println("\nmyList after makeEmpty" +  myList); // should be empty
		System.out.println("yourList after makeEmpty " +  yourList); // should be empty


	} // END MAIN
} // EOF
