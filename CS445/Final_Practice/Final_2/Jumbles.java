import java.io.*;
import java.util.*;

public class Jumbles
{
	public static void main(String args[]) throws Exception
	{

		BufferedReader dictFile = new BufferedReader(new FileReader( "dictionary.txt" ) );     // leave this  as given
		BufferedReader jumblesFile = new BufferedReader(new FileReader( "jumbles.txt" ) );  // leave this  as given

		HashMap< String, String > dictMap = new HashMap< String, String >();

		/*
		With each dWord from the dictionary make a sorted (canonical) copy sWord
		if map does not contain sWord then put <sWord,dWord>
		if already contains key sWord then put <sWord, value + " " + dWord>
		close dictFile


		With each jWord from jumbles file make a sorted (canonical) copy sWord
		if map contains key sWord then print jWord followed by value stored in map with sWord
		close jumblesFile

		*/

		while( dictFile.ready() )
		{
			String dWord = dictFile.readLine();
			String sWord = toCanonical( dWord );

			if( !dictMap.containsKey( sWord ) )
				dictMap.put( sWord, dWord );
			else
				dictMap.put( sWord, dictMap.get(sWord) +" "+ dWord );
		}
		dictFile.close();

		ArrayList<String> list = new ArrayList<String>();

		while( jumblesFile.ready() )
		{
			String jWord = jumblesFile.readLine();
			String sWord = toCanonical( jWord );

			if( dictMap.containsKey( sWord ) )
			{
				ArrayList<String> temp = new ArrayList<String>( Arrays.asList( dictMap.get( sWord ).split( " " ) ) );

				Collections.sort( temp );

				list.add( jWord +": "+ temp.toString().replace(","," ").replace("[", "").replace("]","") );
			}

			if( dictMap.get( sWord ) == null )
				list.add( jWord +":" );
		}
		jumblesFile.close();

		Collections.sort( list );
		for( int i=0; i<list.size(); i++ )
			System.out.println( list.get(i) );

	} // END MAIN

	// you should use this to make a sorted copy of a word
	static String toCanonical( String word )
	{
		char[] letters = word.toCharArray();
		Arrays.sort( letters );
		return new String( letters );
	}

} // END JUMBLES CLASS
