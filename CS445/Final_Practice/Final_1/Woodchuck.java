// John Abraham - jpa18
// CS 445 - Final Exam
// 4/23/13

import java.io.*;
import java.util.*;

public class Woodchuck
{
	public static void main( String args[] ) throws Exception
	{
		BufferedReader infile = new BufferedReader(new FileReader( "woodchuck.txt" ) );  // leave this  as given

		TreeMap< String, Integer > map = new TreeMap< String, Integer >();

		ArrayList< String > words = new ArrayList< String >();

		while( infile.ready() )
			words.add( infile.readLine() );

		for( int i=0; i<words.size(); i++ )
		{
			int counter = 0;

			for( int x=0; x<words.size(); x++ )
			{
				if( words.get(i).equals( words.get(x) ) )
					counter ++;
			}
			map.put( words.get(i), counter );
		}

		for( Map.Entry< String, Integer > entry: map.entrySet() )
		{
			System.out.println( entry.getKey() +" "+ entry.getValue() );
		}

	} // END MAIN
} // END WOODCHUCK CLASS
