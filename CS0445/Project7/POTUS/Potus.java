// John Abraham
// CS 445 - Project 7
// COLLECTION PROBLEM: POTUS (Presidents of the United States)

import java.io.*;
import java.util.*;

public class Potus
{
	public static void main ( String args[] ) throws Exception
	{
		BufferedReader infile = new BufferedReader ( new FileReader( "state2presidents.txt" ) );
		BufferedReader allPres = new BufferedReader ( new FileReader( "allPresidents.txt" ) );
		BufferedReader allSt = new BufferedReader ( new FileReader( "allstates.txt" ) );

		TreeMap< String, List<String> > map = new TreeMap< String, List <String> > ();

		// STEP #1 worth 70%

		System.out.println( "The following states had these presidents born in them:\n" );

		while( infile.ready() )
		{
			List < String > list = new ArrayList<String>( Arrays.asList( infile.readLine().split(" ") ) );
			String stateKey = list.remove(0);	// remove first element and set it to a string
			Collections.sort( list );		// sort the ArrayList of the values (presidents)
			map.put( stateKey, list );		// insert the keys (states) and the values (presidents) to the TreeMap
		}

		for ( Map.Entry < String, List<String> > entry : map.entrySet() )
			System.out.println( entry.getKey() + " " +
				entry.getValue( ).toString().replace( ",", " " ).replace( "[", "" ).replace( "]", "" ) );


		//______________________________________________________________________________________________________
		// STEP #2 worth 15%

		System.out.println( "\nList of presidents and the state each was born in:\n" );

		List< String > presList = new ArrayList< String >();
		List< String > noStatePres = new ArrayList< String >();

		while( allPres.ready() )
			presList.add( allPres.readLine() );

		Collections.sort( presList );

		for( int i=0; i<presList.size(); i++ )
		{
			boolean flag = false;
			for ( Map.Entry < String, List<String> > entry : map.entrySet() )
			{
				if( entry.getValue().contains( presList.get(i) ) )
				{
					System.out.println( presList.get(i) +" "+ entry.getKey() );
					flag = true;
				}
			}

			if( flag == false )
				noStatePres.add( presList.get(i) );
		}

		//______________________________________________________________________________________________________
		// STEP #3 worth 10%

		System.out.println( "\nThese presidents were born before the states were formed:\n" );	
		for( int i=0; i<noStatePres.size(); i++ )
			System.out.println( noStatePres.get(i) ); 

		//______________________________________________________________________________________________________
		// STEP #4 worth 5%

		System.out.println( "\nThese states had no presidents were born in them:\n" );

		List< String > states = new ArrayList< String >();

		while( allSt.ready() )
			states.add( allSt.readLine() );

		Collections.sort( states );

		for( int i=0; i<states.size(); i++ )
		{
			if( !map.containsKey( states.get(i) ) )
				System.out.println( states.get(i) );
		}
	}// END MAIN
} // END CLASS Potus.java
