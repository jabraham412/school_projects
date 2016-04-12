/*
	Solution2.java  input validation

	This program prompts the user for a lower bound and an upper bound.  You must put these prompts in
	a loop that repeats until the following is true:
		- both numbers are greater than 0
		- lower is strictly less than the upper

	After the lower and upper are validated, echo out a line of output displaying the lower and the upper
*/

import java.io.*;
public class Solution2
{
	public static void main (String[] args) throws IOException
	{
		BufferedReader kbd = new BufferedReader(new InputStreamReader(System.in));
		int lower, upper;

		// prompt for and validate lower and upper bound
		do
		{
			System.out.print("\nEnter lower bound (positive number) ");
			lower = Integer.parseInt( kbd.readLine() );

			System.out.print("Enter upper bound (positive number) ");
			upper = Integer.parseInt( kbd.readLine() );
		} while ( upper <= lower || lower <= 0);

		System.out.println("\nlower: " + lower + "   upper: " + upper );

	} // END main
} //EOF