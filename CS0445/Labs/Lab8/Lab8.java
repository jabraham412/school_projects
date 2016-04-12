// John Abraham
// CS 445 - Lab 8
// The Bloodbank

import java.util.*;
import java.io.*;

public class Lab8
{
	public static void main ( String args[] ) throws Exception
	{
		BufferedReader data = new BufferedReader (new FileReader( args[0] ));
		TreeMap< String, ArrayList<String> > map = new TreeMap< String, ArrayList<String> >();
		// generate TreeMap
		while( data.ready() )
		{
			ArrayList <String> list = new ArrayList<String>(Arrays.asList(data.readLine().split(",")));
			map.put( list.remove(0), list );
		}

		// print TreeMap
		for ( Map.Entry <String, ArrayList<String>> entry : map.entrySet() )
		{
			String values = entry.getValue().toString();
			int indexOfOpenBracket = values.indexOf("[");
			int indexOfLastBracket = values.lastIndexOf("]");
			// remove brackets
			String valuesWOB = values.substring(indexOfOpenBracket+1, indexOfLastBracket);
			// remove commas
			String [] temp = valuesWOB.split(",");
			// print keys
			System.out.print( entry.getKey() + "\t" );
			// print values
			for( int i=0; i<temp.length; i++)
				System.out.print( temp[i] );
			System.out.println();
		}

		// generate inverse mapping
		TreeMap< String, String > inverseMapping = new TreeMap< String, String >();
		for( Map.Entry <String, ArrayList<String>> entry : map.entrySet() )
		{
			for( int i=0; i<entry.getValue().size(); i++ )
				inverseMapping.put(entry.getValue().get(i), entry.getKey());
		}

		// print inverse mapping
		for ( Map.Entry <String, String> entry : inverseMapping.entrySet() )
		{
			System.out.println( entry.getKey() + "\t" + entry.getValue());
		}
	}
}
