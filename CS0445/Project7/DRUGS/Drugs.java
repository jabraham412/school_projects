// John Abraham
// CS 445 - Project 7
// COLLECTION PROBLEM: DRUG INTERACTIONS

import java.util.*;
import java.io.*;

public class Drugs
{
	public static void main( String[] args ) throws Exception
	{
		BufferedReader infile = new BufferedReader ( new FileReader( "foodDrug2Category.txt" ) );
		BufferedReader infile2 = new BufferedReader ( new FileReader( "patient2FoodDrug.txt" ) );
		BufferedReader infile3 = new BufferedReader ( new FileReader( "dontMix.txt" ) );

		TreeMap< String, List<String> > foodDrug = new TreeMap< String, List <String> > ();
		TreeMap< String, List<String> > patient2FoodDrug = new TreeMap< String, List <String> > ();

		// STEP #1: 40 pts

		while( infile.ready() )
		{
			List < String > list = new ArrayList<String>( Arrays.asList( infile.readLine().split(",") ) );
			Collections.sort( list );
			foodDrug.put( list.remove(0), list );
		}

		for ( Map.Entry <String, List<String>> entry : foodDrug.entrySet() )
		{
			System.out.print( entry.getKey() +" "+ entry.getValue().toString().replace( ",", " " ).replace( "[", "" ).replace( "]", "" ) );
			System.out.println();
		}

		// STEP #2 40 pts

		while( infile2.ready() )
		{
			List < String > list = new ArrayList<String>( Arrays.asList( infile2.readLine().split(",") ) );
			Collections.sort( list );
			patient2FoodDrug.put( list.remove(0), list );
		}

		System.out.println();
		for ( Map.Entry <String, List<String>> entry : patient2FoodDrug.entrySet() )
		{
			System.out.print( entry.getKey() +" "+ entry.getValue().toString().replace( ",", " " ).replace( "[", "" ).replace( "]", "" ) );
			System.out.println();
		}

		// STEP #3: 20 pts

		System.out.println();
		while( infile3.ready() )
		{
			System.out.println( infile3.readLine().split(",")[1] );





		}
	} // END MAIN

} // END CLASS
