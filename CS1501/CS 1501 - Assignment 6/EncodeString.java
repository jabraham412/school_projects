/*************************************************************************
 * John Abraham
 * CS 1501: Assignment 6
 * 4/21/2014

 * Part A. Small Integer RSA Cryptography
 *************************************************************************/
import java.util.Scanner;

public class EncodeString {
	public static void main (String [] args) {
		long e, n;
		String str = "";

		StdOut.println("Please enter a public key pair (e, n)");
		StdOut.print("Enter the encoding exponent e : ");
		e = StdIn.readLong();

		StdOut.print("Enter the modulus n : ");
		n = StdIn.readLong();

		StdOut.print("Enter the string to encode : ");
		Scanner scan = new Scanner(System.in);
		str = scan.nextLine();

		for (int i=0; i<str.length(); i++)
			StdOut.print( expomod(str.charAt(i),e,n) + " ");

		StdOut.println();
	}

	static long expomod(long a, long n, long z) {
		long r = a % z;

		for(long i = 1; i < n; i++)
			r = (a * r) % z;

		return r;
	}

}