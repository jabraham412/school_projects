/*
	Solution1.java

	This program fills an array of random size with random ints.
	Then finds and reports the smallest value and the largest value in the array
	along with the index where the min or max was located

*/


import java.io.*;
import java.util.Random;

public class Solution1
{
	public static void main( String args[] )
	{
		final int DIM_MIN = 10, DIM_MAX=25;  // I want dimension in 10..25 inclusinve
		final int MAX_VAL = 100; // all numbers put into array must be in 0..100 inclusive

		Random r = new Random(); // random number generator
		int myArr[];  // just a ref - no data storage yet

		// initialize the this array to have a random dimension between 10 and 25 inclusive

		// nextInt( DIM_MAX-DIM_MIN ) gives me random in range (0..15) inclusive
		// I add 10 to that to shift range to 15..25 inclusive

		int dimension = r.nextInt(DIM_MAX-DIM_MIN) + 10;  // 10..25 range

		myArr = new int[ dimension ];

		// loop: fill the array with random values between 1 and 100 inclusive

		for (int i=0 ; i < myArr.length ; ++i)
			myArr[i] = r.nextInt( MAX_VAL+1 );  // 0..100 range

		// Echo contents of array

		System.out.print("\nmyArr: ");
		for (int i=1 ; i < myArr.length ; ++i)
			System.out.print( myArr[i] + " ");
		System.out.println();

		// loop: find and report the actual min

		int minVal = myArr[0]; // Do it this way rather than init to some huge value
		int indOfMin= 0;

		for (int i=1 ; i < myArr.length ; ++i)
			if (myArr[i] < minVal)
			{
				indOfMin=i;
				minVal = myArr[indOfMin];
			}
		System.out.println("Min value: " + minVal + " found at index: " + indOfMin );

		// loop: find and report the actual max

		int maxVal = myArr[0]; // Do it this way rather than init to some small value
		int indOfMax = 0;
		for (int i=1 ; i < myArr.length ; ++i)
			if (myArr[i] > maxVal)
			{
				indOfMax=i;
				maxVal = myArr[indOfMax];
			}
		System.out.println("Max value: " + maxVal + " found at index: " + indOfMax );

	} // END main

} // EOF
