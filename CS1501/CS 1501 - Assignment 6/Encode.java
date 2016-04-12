/*************************************************************************
 * John Abraham
 * CS 1501: Assignment 6
 * 4/21/2014

 * Part A. Small Integer RSA Cryptography
 *************************************************************************/

public class Encode {
	public static void main (String [] args) {
		long e, n, c;
		char m;

		StdOut.println("Enter a public key pair (e,n)");
		StdOut.print("Enter a value for e: ");
		e = StdIn.readLong();

		StdOut.print("Enter a value for n: ");
		n = StdIn.readLong();

		StdOut.print("Enter a character value for m: ");
		/* If user enters an integer instead of char convert to ASCII */
		String str = StdIn.readString();
		if ( isNumeric(str) ) {
			m = (char) Integer.parseInt(str);
		} else {
			m = str.charAt(0);
		}

		c = expomod(m,e,n);
		StdOut.println("\nTransmitting encoded '"+ m +"' as " +c);
	}

	static long expomod(long a, long n, long z) {
		long r = a % z;

		for(long i = 1; i < n; i++)
			r = (a * r) % z;

		return r;
	}

	static boolean isNumeric (String str) {
		return str.matches("-?\\d+(\\.\\d+)?");
	}

}