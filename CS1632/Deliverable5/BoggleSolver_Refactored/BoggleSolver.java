/**
* BoggleSolver.java
*
* @author John Abraham
* @author Yibo Cui
*/

import java.io.*;
import java.util.*;

public class BoggleSolver
{
	static String[][] grid;
	static int[] rdelta = {-1,-1,-1, 0, 0, 1, 1, 1};
	static int[] cdelta = {-1, 0, 1,-1, 1,-1, 0, 1};
	static ArrayList<String> wordsFound = new ArrayList<String>();
	static PrefixTree dictionary = new PrefixTree();
	static BoggleSolver boggleSolver = new BoggleSolver();
	
	public static void main(String args []) throws Exception
	{
		double start = System.currentTimeMillis();

		grid = loadLetters( args[0] );
		BufferedReader dictionaryFile = new BufferedReader( new FileReader("dictionary.txt") );

		/**
		* only insert words from the dictionary that start with a letter from the grid
		* and the second letter of the word be adjacent to the letter in the grid to reduce the overall execution time.
		*
		*/
		int g = grid.length * grid.length;
		while( dictionaryFile.ready() )
		{
			String previousLine = dictionaryFile.readLine();
			if( previousLine.length() >= 3 && previousLine.length() <= g)
			{
				for (int row = 0; row < grid.length; row++)
				{
					for (int col = 0; col < grid.length; col++)
					{
						if( previousLine.startsWith(grid[row][col]) )
						{
							dictionary.insert( previousLine );
							row = grid.length;
							col = grid.length;
						}
					}
				}
			}
		}
		dictionaryFile.close();

		boggleSolver.findAllWords();

		double end = System.currentTimeMillis();
		System.out.println("Execution Time: " +((end-start)/1000) + " s");
	}
	
	private static String[][] loadLetters( String infileName ) throws Exception
	{
		Scanner infile = new Scanner( new File(infileName) );
		int row = infile.nextInt();
		int col = row;
		String[][] grid = new String[row][col];

		for(int r = 0; r < row ; r++)
			for(int c = 0; c < col; c++)
				grid[r][c] = infile.next();

		infile.close();
		return grid;
	}

	private void findAllWords() throws Exception
	{
		for (int row = 0; row < grid.length; row++)
			for (int col = 0; col < grid.length; col++)
				findAllWordsHelper("", row, col);
	}
	
	private void findAllWordsHelper(String words, int row, int col) throws Exception
	{
		if (row >= 0 && row < grid.length && col >= 0 && col < grid.length && !grid[row][col].equals("*") )
		{
			words += grid[row][col];
			if(words.length() >= 3)
			{
				if(dictionary.search(words))
				{
					if(!wordsFound.contains(words)){
						System.out.println(words);
						wordsFound.add(words);
					}
				}
			}

			if(dictionary.isPrefix(words))
			{
				String temp = grid[row][col];
				grid[row][col] = "*";
				for(int k=0; k < rdelta.length; k++)
					  findAllWordsHelper(words, row+rdelta[k], col+cdelta[k]);
				grid[row][col] = temp;
			}
		}
	}

	/*
	* used to access the words found to verify that the program 
	* found all the correct words
	*/
	public ArrayList<String> wordsFound()
	{
		return wordsFound;
	}
}