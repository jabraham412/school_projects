/*************************************************************************
 * John Abraham
 * CS 1501: Assignment 5
 * 34/7/2014
 *************************************************************************/
import java.io.*;

public class Compress {
	public static void main(String[] args) throws IOException {

		AdaptiveHuffmanTree aft = new AdaptiveHuffmanTree();
		String inputString = "";
		String outputStream = "";

		while ( !BinaryStdIn.isEmpty() ) {
			char c = BinaryStdIn.readChar();
			inputString += c;

			if (!aft.characterInTree(c)) {
				String s = aft.getCodeWordForNYT().toString();
				String asciiValue = Integer.toBinaryString( (int)c );
				String output = String.format("%8s", asciiValue).replace(' ', '0');
				outputStream += s + output;
			} else {
				String s = aft.getCodeWordFor(c).toString();
				outputStream += s;
			}

			aft.update(c);
		}

		int x = outputStream.length() % 8;
		int y = 8 - x;
		for (int i=0; i<y; i++)
			outputStream += "0";

		StringBuffer result = new StringBuffer();
		for (int i = 0; i < outputStream.length(); i += 8) {
			result.append((char) Integer.parseInt(outputStream.substring(i, i + 8), 2));
		}

		BinaryStdOut.write(result.toString());
		BinaryStdOut.close();

		/*
		* save compression results in "statistics.txt"
		 */
		int bitsRead = inputString.length() * 8;
		int bitsTransmitted = (outputStream.length() - y);

		Out stat = new Out("statistics.txt");
		stat.print(inputString);
		stat.print("\nbits read = " + bitsRead);
		stat.print("\nbits transmitted = " +  bitsTransmitted);
		stat.printf("\ncompression ratio = %.1f",  100 * (1 - ((float)bitsTransmitted/(float)bitsRead)));
   }
}