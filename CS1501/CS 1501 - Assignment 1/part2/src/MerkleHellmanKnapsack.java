/*************************************************************************
 * John Abraham
 * CS 1501: Assignment 1
 * 6/1/2014
 *************************************************************************/
import java.math.BigInteger;
import java.util.Scanner;
import java.util.Random;

/**
* Implementation of Merkle-Hellman Knaosack Encryption and Decryption
* algorithm using up to 80 bytes of data.
*
* @author John
*
*/
public class MerkleHellmanKnapsack {
    /**
    * w holds the superincreasing sequence of integers that make
	* up part of the private key and used for decryption.
	* b holds the public key material used for encryption.
	*
	*/
	private BigIntegerLinkedList w; //used for private key
	private BigIntegerLinkedList b; //used for public key
	private BigInteger q; //modulus
	private BigInteger r; //multiplier
	private int numInputBits = 80 * 8;
 
	/**
	* constructor with no parameters initializes w and b
	*
	*/
	public MerkleHellmanKnapsack() {
		w = new BigIntegerLinkedList();
		b = new BigIntegerLinkedList();
	}

	/**
	* Takes an array of bytes as input and encryptes it using Merkle-Hellman Knapsack algorithm <br><br>
	* Best Case: Big Theta (n^2) <br>
	* Worst Case: Big Theta (n^2) <br>
	* Pre-condition: Input cannot be greater than 80 bytes <br>
	* Post-condition: Final encrypted number must be a single BigInteger <br>
	*
	* @param input An array of bytes that we want to encrypt
	* @return BigInteger value representing the encrypted message
	*
	*/
	public BigInteger encrypt(byte[] input) {
		if(input.length > 80) {
			return null;
		}

		numInputBits = input.length * 8;
		KeyGen(numInputBits);

		BigInteger encryptedMsg = BigInteger.ZERO;

		// convert input byte array to boolean array representing bits
        boolean[] bits = new boolean[numInputBits];
        for (int i = 0; i < input.length * 8; i++) {
            if ( (input[i / 8] & (1 << (7 - (i % 8)))) > 0 )
                bits[i] = true;
        }

        // multiply each respective bit in boolean array by the corresponding BigInteger in b
        // then find the sum
        // or return the sum of everything in b corresponding to a 1/true in boolean array
        for (int i = 0; (i < bits.length) && (i < b.countNodes()); i++) {
            if ( bits[i] ) {
                encryptedMsg = encryptedMsg.add( b.getNodeAt(i).getData() );
            }
        }

        return encryptedMsg;
	}

	/**
	* Takes an encrypted message as a BigInteger value as input and decrypts it according to
	* Merkle-Hellman Knapsack algoritm  <br><br>
	* Best Case: Big Theta (n^2) <br>
	* Worst Case: Big Theta (n^2) <br>
	* Pre-condition: The keys used for encryption must be used for decryption as well <br>
	* Post-condition: The decrypted output bytes must be printable as readable characters
	* and not a bit output stream<br>
	*
	* @param intput BigInteger message to decrypt
	* @return Byte array of decreypted message
	*
	**/
	public byte[] decrypt(BigInteger output) {
        BigInteger b1 = output.mod(q);
        BigInteger b2 = r.modInverse(q);
        BigInteger b3 = b1.multiply(b2);
        BigInteger value = b3.mod(q);


		// Decompose "value" by selecting the largest element in w which is less than or equal to "value"
		// Then select the next largest element less than or equal to the difference, until the
		// difference is 0
        byte       [] decryptedMsgBytes  = new byte[numInputBits];
        String decryptedMsgBits = "";
        BigIntegerLinkedList nextLargestElement = new BigIntegerLinkedList();

		BigInteger difference = value;
		do {
			for (int i = numInputBits-1; i >= 0; i--) {
				if ( w.getNodeAt(i).getData().compareTo(difference) <= 0 ) {
					nextLargestElement.addNodeAtEnd(w.getNodeAt(i).getData());
					difference = difference.subtract(w.getNodeAt(i).getData());
					i = -1;
				}
			}
		} while ( difference.compareTo(BigInteger.ZERO) != 0 );

		nextLargestElement.reverse();

		// Now from "w" and "nextLargestElement" Linked Lists we can fill out decryptedMsgBits
		// with 0/1's to represent the output stream in single bits
		for (int i = 0; i < numInputBits; i++) {
			if (nextLargestElement.countNodes() > 0) {
				if ( w.getNodeAt(i).getData().compareTo(nextLargestElement.getNodeAt(0).getData()) == 0 ) {
					decryptedMsgBits += "1";
					nextLargestElement.removeFirstNode();
				} else {
					decryptedMsgBits += "0";
				}
			}
		}

		// Add zeros to the output stream to make the total divisible by 8
		int x = decryptedMsgBits.length() % 8;
		int y = 8 - x;
		for (int i=0; i<y; i++)
			decryptedMsgBits += "0";

		// Convert decryptedMsgBits (string) to decryptedMsgBytes (byte array) and return the bytes
		StringBuffer outputStream = new StringBuffer();
		for (int i = 0; i < decryptedMsgBits.length(); i += 8) {
			outputStream.append((char) Integer.parseInt(decryptedMsgBits.substring(i, i + 8), 2));
		}

		return outputStream.toString().getBytes();
	}

	/**
	* method that generates keys based on the input size
	*
	*/
	private void KeyGen(int inputSize) {
		// create the superincreasing sequence of BigIntegers and add them to the Linked List w
		w.addNodeAtEnd( BigInteger.ONE );

		for(int i = 1; i < inputSize; i++) {
			w.addNodeAtEnd( nextSuperIncreasingNumber(w) );
		}

		// choose a number for q that is greater than the sum of w = {w1, w2, w3, ..., wi}
		// the next superincreasing number is greater than the sum and can be used for q
		q = nextSuperIncreasingNumber(w);

		// generate a random value for the multiplier r such that:
		// r is in the range of [1, q] and is coprime to q (i.e. gcd(r,q) = 1) 
		Random rand = new Random();
		do {
			r = new BigInteger( q.bitLength(), rand);
		} while( !isCoprime(r, q) || (r.compareTo(BigInteger.ZERO) <= 0) || (r.compareTo(q) == 1) );

		// generate the sequence b by multiplying each element in w by r mod q
		for (int i = 0; i < inputSize; i++) {
			b.addNodeAtEnd( w.getNodeAt(i).getData().multiply(r).mod(q) );
		}
	}

	/**
	* Method that generates the next superincreasing number based on randomness
	*
	*/
	private BigInteger nextSuperIncreasingNumber(BigIntegerLinkedList list) {
		BigInteger sum = BigInteger.ZERO;

		// find the sum of BigIntegers in the list
		for (int i = 0; i < list.countNodes(); i++) {
			sum = sum.add( list.getNodeAt(i).getData() );
		}

		// generate a random BigInteger greater than the sum of the nodes
		Random rand = new Random();
		return sum.add( new BigInteger(rand.nextInt(10)+1 + "") );
	}

	/**
	* Method that check if the two number r and q are coprime (gcd(r, q) = 1)
	* returns true if the greatest common divisor of x and y is 1
	* return false otherwise
	*
	*/
	private boolean isCoprime(BigInteger x, BigInteger y) {
		return (BigInteger.ONE).compareTo( x.gcd(y) ) == 0;
	}

	/*************************************************************************
	* Test driver for MerkleHellmanKnapsack.java
	* 
	* @param args
	*
	*/

	public static void main(String[] args) {
		MerkleHellmanKnapsack mhk = new MerkleHellmanKnapsack();

		Scanner scan = new Scanner(System.in);
		String input = "";

		do {
			System.out.println("\nEnter a string and I will encrypt it in a single integer:");
			input = scan.nextLine();
			System.out.println("\nClear text:\n" + input);
			System.out.println("\nNumber of clear text bytes = " + input.length());
			if (input.length() > 80) {
			    System.out.println("Maximum character length allowed is 80. Please enter again.");
			}
		} while (input.length() > 80);

		BigInteger encryptedMsg = mhk.encrypt( input.getBytes() );
		System.out.println("\n\"" + input + "\" " + "is encryped as " + encryptedMsg);

		System.out.println("\nResult of decryption: " + new String( mhk.decrypt(encryptedMsg) ));
	}
}