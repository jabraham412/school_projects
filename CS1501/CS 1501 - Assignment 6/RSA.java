/*************************************************************************
 * John Abraham
 * CS 1501: Assignment 6
 * 4/21/2014

 * Part B. BigInteger RSA Cryptography
 *************************************************************************/
import java.math.BigInteger;
import java.util.Random;
 
public class RSA {
	public static void main(String [] args) {
		if (args.length == 0) {
			StdOut.println("Enter java RSA <nBits> to generate keys");
			StdOut.println("Enter java RSA -encrypt file.txt to encrypt a file");
			StdOut.println("Enter java RSA -decrypt file.txt.enc to decrypt and encrypted file");
			return;
		}

		else if (isNumeric(args[0])) {
			KeyGen(Integer.parseInt(args[0]));
		}

		else if (args[0].equals("-encrypt")) {
			encrypt(args[1]);
		}

		else if (args[0].equals("-decrypt")) {
			BDecode(args[1]);
		}

		else {
			StdOut.println("Unrecognized input");
			StdOut.println("Enter java RSA <nBits> to generate keys");
			StdOut.println("Enter java RSA -encrypt file.txt to encrypt a file");
			StdOut.println("Enter java RSA -decrypt file.txt.enc to decrypt and encrypted file");
		}
	}

	static void KeyGen (int primeBits) {
		BigInteger p, q, pMinus1, qMinus1, n, m, e, d;

		do {
			/* get a random number */
			Random rnd = new Random();

			/* get two distinct primes of size primeBits */
			p = new BigInteger(primeBits,128,rnd);
			do q = new BigInteger(primeBits,128,rnd);
			while(p.compareTo(q) == 0 || p.compareTo(q) == 1);

			/* compute the modulus n */
			n = p.multiply(q);

			/* compute m = phi(n) */
			pMinus1 = p.subtract(BigInteger.valueOf(1));
			qMinus1 = q.subtract(BigInteger.valueOf(1));
			m = pMinus1.multiply(qMinus1);

			/* get e relatively prime to m */
			e = BigInteger.valueOf(3);
			while(e.gcd(m).compareTo(BigInteger.valueOf(1)) > 0)
			e = e.add(BigInteger.valueOf(2));

			BigInteger [] vals = XGCD(e, m);
			/* compute d the decryption exponent */
			/* random d using XGCD() where (1 < d < PHI) */
			/* XGCD() replaces d = e.modInver(m)*/
			d = vals[1];
		} while ( d.compareTo(m) == 1 || d.compareTo(m) == 0 || d.compareTo(BigInteger.ONE) == -1 || d.compareTo(BigInteger.ONE) == 0 );

		/* the values for e and n are stored in a file public.txt */
		Out publicOutput = new Out("public.txt");
		publicOutput.print(e.toString() +"\n"+ n.toString());
		publicOutput.close();

		/* the values of d and n are stored in a file private.txt */
		Out privateOutput = new Out("private.txt");
		privateOutput.print(d.toString() +"\n"+ n.toString());
		privateOutput.close();

		System.out.println("Success ! public.txt & private.txt keys are generated");
	}

	static void encrypt (String file) {
		In publicKeys = new In("public.txt");
		In input = new In(file);
		Out output = new Out(file+".enc");

		/* Read in the public key pair (e,n) from public.txt */
		BigInteger e = new BigInteger(publicKeys.readLine());
		BigInteger n = new BigInteger(publicKeys.readLine());

		/* m is the character to encode */
		while ( !input.isEmpty() ) {
			BigInteger m = new BigInteger( String.valueOf( (int) input.readChar() ) );
			output.print( expomod(m, e, n) + " ");
		}

		publicKeys.close();
		input.close();
		output.close();
		StdOut.println("Encoding Successful !");
	}

	static void BDecode (String file) {
		In privateKeys = new In("private.txt");
		In input = new In(file);
		Out output = new Out(file.replace(".enc", "")+".cop");

		/* Read in the private key pair (d,n) from private.txt*/
		BigInteger d = new BigInteger(privateKeys.readLine());
		BigInteger n = new BigInteger(privateKeys.readLine());

		/* m is the BigInteger to deocde */
		while ( !input.isEmpty() ) {
			BigInteger m = new BigInteger( input.readString() );
			output.print( (char) expomod(m, d, n).intValue() );

		}

		privateKeys.close();
		input.close();
		output.close();
		StdOut.println("Deccoding Successful !");
	}

	static boolean isNumeric (String str) {
		return str.matches("-?\\d+(\\.\\d+)?");
	}

	static BigInteger[] XGCD (BigInteger e, BigInteger m) {
		/* Extended Greatest Common Divisor algorithm discussed in class */
		/* Replaces calls to modInverse()*/
		if (m.equals(BigInteger.ZERO))
			return new BigInteger[] { e, BigInteger.ONE, BigInteger.ZERO };

		BigInteger[] vals = XGCD(m, e.mod(m));
		BigInteger d = vals[0];
		BigInteger a = vals[2];
		BigInteger b = vals[1].subtract( e.divide(m).multiply(vals[2]) );
		return new BigInteger[] { d, a, b };
	}

	static BigInteger expomod (BigInteger a, BigInteger n, BigInteger z) {
		BigInteger r = a.mod(z);

		for(BigInteger i = BigInteger.ONE; i.compareTo(n) < 0 ; i = i.add(BigInteger.ONE))
			r = (a.multiply(r)).mod(z);

		return r;
	}

}