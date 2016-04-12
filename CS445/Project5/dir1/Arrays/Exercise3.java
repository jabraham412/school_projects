/*
	Exercise3.java

	You are given an array with dimension of 10 but only 5 values in it. The last five
	cells still contain zeros;

	You are to write a loop that executes 5 times. Each time thru you ask for and parse
	a number from the user. you are to always add that new number to the FRONT of the array.
	This means that you must first shuffle all the existing elements to the right, then copy
	the new element into the [0] slot.
*/


import java.io.*;
import java.util.Random;

public class Exercise3
{
	public static void main( String args[] )
	{
		// declare our BufferedReader kbd variable for reading from kbd

		// declate and intialize array

		int myArr[] = new int[ 10 ];
		int count=0; // actual number of cells being used

		for (int i=0 ; i<5 ; ++i )
			myArr[count++] = i;

		// now, myArr:  [0][1][2][3][4][0][0][0][0][0]   (last 5 are zeros)

		// loop: print the array - all on one line separated by a space
		// use count NOT .length

		// loop:  must run until array is full and no more
		//	prompt user for a number
		// 	shuffle all the elements one place toward end of array ( NESTED LOOP )
		//    copy new number into [0] slot
		// 	increment count

		// DEBUGGING HINT: print the array & count inside the loop after every insertion

		// Assume user entered 9,8,7,6 and 5
		// then myArr would look like: [5][6][7][8][9][1][2][3][4][5]
		// *Note the 5 new values get "reversed" from their inputed dorder


		// loop: print the array - all on one line separated by a space
		// use count NOT .length

	} //

}
