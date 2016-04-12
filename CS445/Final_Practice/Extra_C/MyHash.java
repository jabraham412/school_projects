import java.util.*;
import java.io.*;

public class MyHash
{

	public static void main( String[] args) throws Exception
	{
		MyHashSet hs = new MyHashSet( 256 );

		BufferedReader infile  = new BufferedReader ( new FileReader( args[0])  );
		String s;
		while ( (s = infile.readLine()) != null )
		{
			hs.add( s );
		}

		hs.printStats();

	}
}

class MyHashSet
{
	Random generator;
	int[] table;

	public MyHashSet( int numBuckets )
	{
		generator= new Random(17);
		table = new int[ numBuckets ];
	}
	public void add( String s ) // a real add would return boolean but you assume I feed you no dupes in the dictionary
	{
		int h = hash(hashCode( s )); // step 1: see comments below in function
		int i = h & (table.length-1);             // efficient bit operation to map int into a bucket number in table
		table[i]++;   // We are NOT really adding the String to the table - we are merely incrementing that bucket's count

	}						//  that is all this program is doing - examinig the distribution of keys into buckets - no bneed to actullt store them

	private int hashCode( String s )
	{
		// RELACE RANDOM NUMBERS WITH YOUR CODE HERE - GOOD LUCK :)
		// THIS IS a CHEAT. IT IS JUST A PLACEHOLDER FOR YOUR HASHCODE FUNCTION
		return generator.nextInt( table.length );  // REPLACE ME!!!!
	}

	private static int hash(int h)
	{
    	// This function ensures that hashCodes that differ only by
    	// constant multiples at each bit position have a bounded
    	// number of collisions (approximately 8 at default load factor).
    	h ^= (h >>> 20) ^ (h >>> 12);
    	return h ^ (h >>> 7) ^ (h >>> 4);
	}

	public void printStats()
	{
		//int largestBucketLen=0;
		//int aveBucketLen=0;
		for (int i = 0 ; i < table.length ; ++i)
			System.out.printf("[%-3d]:%d\n",i,table[i]);
	}
}
