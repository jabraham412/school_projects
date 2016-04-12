/*************************************************************************
 * John Abraham
 * CS 1501: Assignment 6
 * 4/21/2014

 * Part A. Small Integer RSA Cryptography
 *************************************************************************/
import java.util.Scanner;

public class DecodeInts {
	public static void main (String [] args) {
		long d, n;
		int numInts;
		String input = "";

		StdOut.println("Please enter a private key pair (d, n)");
		StdOut.print("Enter the decoding exponent d : ");
		d = StdIn.readLong();

		StdOut.print("Enter the modulus n : ");
		n = StdIn.readLong();

		StdOut.print("Enter the number of integers to decode : ");
		numInts = StdIn.readInt();

		Scanner scan = new Scanner(System.in);
		input = scan.nextLine();
		String [] items = input.split("\\s+");
		int [] allInts = new int[numInts];

		for (int i=0; i<numInts; i++)
			allInts [i] = Integer.parseInt(items[i]);

		StdOut.print("Decoded String : ");

		for (int i=0; i<allInts.length; i++)
			StdOut.print( (char)expomod(allInts[i],d,n) );

		StdOut.println();
	}

	static long expomod(long a, long n, long z) {
		long r = a % z;

		for(long i = 1; i < n; i++)
			r = (a * r) % z;

		return r;
	}

}