/*
	Lab1.java

	requires you to write several recursive methods

	    * recursive palindrome checker method
	    * recursive factorial method
	    * recursive reverse print of the digits of a number
    	* same as above but print numbers in reverse
*/


import java.io.*;

public class Lab1
{
	public static void main( String args[] ) throws Exception
	{
		String[] words = { "stanleyyelnats", "radar", "madamimadam", "toyota", "recursion", };
		int[] numbers = { 1, 123456,  2468 };
		int[] smallNums = { 1, 5, 9 };

		for (int i=0 ; i< words.length ; ++i)
			System.out.println( words[i] + " is a palindome? " + isPalindrome(words[i], 0, words[i].length()-1)	);

		System.out.println();

		for (int i=0 ; i< numbers.length ; ++i)
		{
			System.out.print( numbers[i] + "  printed forward: " );
			printDigitsForward( numbers[i]  );
			System.out.println();
		}

		System.out.println();

		for (int i=0 ; i< numbers.length ; ++i)
		{
			System.out.print( numbers[i] + "  printed backward: ");
			printDigitsBackward( numbers[i] );
			System.out.println();
		}

		System.out.println();

		for (int i=0 ; i< smallNums.length ; ++i)
			System.out.println( smallNums[i] + "! = "  + factorial(smallNums[i]) );

	} // END MAIN


	// YOU FILL IN THE METHODS BELOW

	// isPalindrome
	// Must use recursion. You are allowed to change the parameters if you wish
	// but if you do you must change main.  Output must match my output.
	private static boolean isPalindrome( String s, int left, int right )
	{		
			//Base case:
			if (left >= right)
			{
				return false;
			}
			//Recursive case:
			else
			{
			

				return true;
			}
	}

	// factorial
	// Must use recursion. Must not use a loop or call a math library function.
	private static int factorial( int n )
	{
			//Base case for n <= 1
			if (n <= 1) {
				return 1;
			}
			// Recursive Case for n > 1
			//n! = n * (n-1)!
			else {
				return n * factorial(n-1);
			}
	}

	// printDigitsForward  takes a number like 23456 and prints out 65432
	// Must use recursion. You may not store the number in a string or convert to an array of any kind.

	private static void printDigitsForward( int n )
	{

	
	
	
	}

	// same as above buyt prints digits in their reverse order
	private static void printDigitsBackward( int n )
	{

	
	
	
	}

} //END Lab1