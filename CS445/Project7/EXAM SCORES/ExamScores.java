// my Pitt ID: 3665767
// my name: John Abraham
// CS 445 - Project 7
// COLLECTIONS PROBLEM: EXAM SCORES

import java.util.*;
import java.io.*;
import java.text.*;;


public class ExamScores
{
	static TreeMap< String, String > averageScores = new TreeMap< String, String >();
	static DecimalFormat format = new DecimalFormat("00.00");

	public static void main( String args[] ) throws Exception
	{
		// close/reuse this file handle on the next file
		BufferedReader infile = new BufferedReader( new FileReader( "ExamScores.txt" ) );
		BufferedReader infile2 = new BufferedReader( new FileReader( "query.txt" ) );

		// you declare all needed ArrayLists and other variables from here on.
		List< String > ExamScores = new ArrayList< String >();

		System.out.println("\nSTEP #1: 50%");  // 50%

		while( infile.ready() )
			ExamScores.add( infile.readLine() );

		for( int i=0; i<ExamScores.size(); i++ )
			System.out.println( ExamScores.get(i) );

		//_______________________________________________________________________________

		System.out.println("\nSTEP #2: 25%");  // 75 %

		Collections.sort( ExamScores );

		for( int i=0; i<ExamScores.size(); i++ )
			System.out.println( ExamScores.get(i) );

		//_______________________________________________________________________________

		System.out.println("\nSTEP #3: 10%");  // 85%

		String query = infile2.readLine();

		for( int i=0; i<ExamScores.size(); i++ )
			computeAverages( ExamScores.get(i), query );

		for ( Map.Entry < String, String > entry : averageScores.entrySet() )
			System.out.println( entry.getValue() +" "+ entry.getKey() );

		//_______________________________________________________________________________

		System.out.println("\nSTEP #4: 15%"); // 100%

		TreeMap< String, ArrayList<Double> > lowestScores = new TreeMap< String, ArrayList<Double> >();
		int numberScores = 0;

		for( int i=0; i<ExamScores.size(); i++ )
		{
			ArrayList< Double > list = new ArrayList< Double >();

			String[] grades = ExamScores.get(i).split("\\s+");
			numberScores = grades.length-1;

			for( int x=1; x<grades.length; x++ )
				list.add( Double.parseDouble( grades[x] ) );

			lowestScores.put( grades[0], list );
		}

		computeLowestAverage( lowestScores, numberScores );

	} // END MAIN

	// - - - - - - H E L P E R   M E T H O D S   H E R E - - - - -
	public static void computeAverages( String line, String query )
	{
		String[] grades = line.split("\\s+");
		String[] queries = query.split(" ");
		Double sum = 0.00;

		for( int i=0; i<queries.length; i++ )
			sum += Double.parseDouble( grades[ Integer.parseInt( queries[i] ) ] );

		averageScores.put( format.format (sum/queries.length).toString(), grades[0] );
	}

	public static void computeLowestAverage( TreeMap< String, ArrayList<Double> > data, int numberScores )
	{
		ArrayList<Double> averages = new ArrayList<Double>();	

		for( int i=0; i<numberScores; i++ )
		{
			double d = 0.00;
			for ( Map.Entry < String, ArrayList<Double> > entry : data.entrySet() )
				d += entry.getValue().get(i);

			averages.add( d/averageScores.size() );
		}

		// averages is an ArrayList that contains all of the averages
		// find which index of the ArrayList contains the lowest average score
		Object minValue = Collections.min( averages );
		int examNum = averages.indexOf(minValue)+1;
		System.out.println( "exam" + examNum +" had the lowest average" );
	}
} // END EXAMSCORES CLASS
