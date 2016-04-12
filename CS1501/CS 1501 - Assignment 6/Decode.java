/*************************************************************************
 * John Abraham
 * CS 1501: Assignment 6
 * 4/21/2014

 * Part A. Small Integer RSA Cryptography
 *************************************************************************/

public class Decode {
	public static void main (String [] args) {
		long d, n, c;
		char m;

		StdOut.println("Enter a private key pair (d,n)");
		StdOut.print("Enter a value for d: ");
		d = StdIn.readLong();

		StdOut.print("Enter a a value for n: ");
		n = StdIn.readLong();

		StdOut.print("Enter a single integer c to be decoded: ");
		c = StdIn.readLong();

		m = (char)expomod(c,d,n);
 
 		StdOut.println( "Decoding " + c + " to " + m);
	}

	static long expomod(long a, long n, long z) {
		long r = a % z;

		for(long i = 1; i < n; i++)
			r = (a * r) % z;

		return r;
	}

}