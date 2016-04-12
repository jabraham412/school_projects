/*************************************************************************
 * John Abraham
 * CS 1501: Assignment 4
 * 3/19/2014

 * Compilation: javac MoveToFront.java
 * Execution: java MoveToFront - < abra.txt | java HexDump 16 (encode)
 * Execution: java MoveToFront + < abra.txt | java HexDump 16 (decode)
 *************************************************************************/
public class MoveToFront {
	/*apply move-to-front encoding*/
	/*reading from standard input and writing to standard output*/
	public static void encode () {
		char [] orderedSequence = new char[256];
		for (char i=0; i<256; i++)
			orderedSequence[i] = i;

		while (!BinaryStdIn.isEmpty()) {
			char inputChar = BinaryStdIn.readChar();
			char count = 0;
			char in;
			char out = orderedSequence[0];
			while (inputChar != orderedSequence[count]) {
				in = orderedSequence[count];
				orderedSequence[count] = out;
				out = in;
				count++;
			}
			orderedSequence[count] = out;
			orderedSequence[0] = inputChar;
			BinaryStdOut.write(count);
		}
		BinaryStdOut.close();
	}

	/*apply move-to-front decoding*/
	/*reading from standard input and writing to standard output*/
	public static void decode () {
		char [] orderedSequence = new char[256];
		for (char i=0; i<256; i++)
			orderedSequence[i] = i;

		while (!BinaryStdIn.isEmpty()) {
			char inputChar = BinaryStdIn.readChar();
			char count = orderedSequence[inputChar];
			while (inputChar > 0) {
				orderedSequence[inputChar] = orderedSequence[--inputChar];
			}
			orderedSequence[inputChar] = count;
			BinaryStdOut.write(count);
		}
		BinaryStdOut.close();
	}

	public static void main (String [] args) {
		if           (args[0].equals("-")) encode();
		else if      (args[0].equals("+")) decode();
        else throw new IllegalArgumentException("Illegal command line argument");
	}
}