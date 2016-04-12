/*************************************************************************
 * John Abraham
 * CS 1501: Assignment 6
 * 4/21/2014

 * Part A. Small Integer RSA Cryptography
 *************************************************************************/

public class KeyGen {
	public static void main (String [] args) {
		long p, q, n, phi, e, d;

		StdOut.println("Enter two primes (p and q) with p < q");
		StdOut.print("Enter a prime value for p: ");
		p = StdIn.readLong();


		StdOut.print("Enter a prime value for q: ");
		q = StdIn.readLong();

		while(p >= q || !isPrime(p) || !isPrime(q)) {

			StdOut.println("p & q must be prime " +
				"numbers and p must be less than q");

			StdOut.print("Enter a prime value for p: ");
			p = StdIn.readLong();

			StdOut.print("Enter a prime value for q: ");
			q = StdIn.readLong();
		}

		StdOut.println();
		StdOut.println("The value of p = " +p);
		StdOut.println("The valye of q = " +q);

		/* Choose n as the product of p and q
		 * no known algorithm can recompute p
		 * and q from n within a reasonable
		 * period of time for large n.
		*/
		n = p * q;
		StdOut.println("The value of n = " + n);

		/* Compute phi = (p-1)*(q-1) */
		phi = (p - 1) * ( q - 1);
		StdOut.println("The value of PHI = " + phi);

		/* choose a random prime e between 1 and phi, exclusive, */ 

		/* so that e has no common factors with phi. 			 */
		e = findfirstnocommon(phi);
		StdOut.println("The public exponent (e) = " + e);

		/* Compute d as the multiplicative inverse of e modulo phi(n). */
		d = findinverse(e,phi); 
		StdOut.println("The private key (d) = " + d);
	}

	static boolean isPrime(long n) {
		/*check if n is a multiple of 2*/
		if (n%2 == 0) return false;

		/*if not, then check only the numbers*/
		for(long i=3; i*i<=n; i+=2) {
		if(n%i == 0)
			return false;
		}
		return true;
	}

	static long findfirstnocommon(long n) {
		long j;
		for(j = 2; j < n; j++)
			if(euclid(n,j) == 1) return j;
		return 0;
	}

	static long euclid(long m, long n) {
		/* pre: m and n are two positive integers (not both 0) */
		/* post: returns the largest integer that divides both */
		/* m and n exactly 									   */
		while(m > 0) {
			long t = m;
			m = n % m;
			n = t;
		}
		return n;
	}

	static long findinverse(long n, long phi) {
		long i = 2;
		while( ((i * n) % phi) != 1) i++;
			return i;
	}

}