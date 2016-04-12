/*************************************************************************
 * John Abraham
 * CS 1501: Assignment 4
 * 3/19/2014

 * Compilation: javac BurrowsWheeler.java
 * Execution: java BurrowsWheeler - < abra.txt | java HexDump 16 (encode)
 * Execution: java BurrowsWheeler + < abra.txt | java HexDump 16 (decode)
 *************************************************************************/
public class BurrowsWheeler {
	/*apply Burrows-Wheeler encoding*/
	/*reading from standard input and writing to standard output*/
	public static void encode () {
        int first;
        String s = BinaryStdIn.readString();
        CircularSuffixArray suffixArray = new CircularSuffixArray(s);

        /*print the row position of the original string first*/
        for (first = 0; first < suffixArray.length(); first++) {
        	if (suffixArray.index(first) == 0)		break;
        }
        BinaryStdOut.write(first);

		/*print the encoded string BWT next*/
        int N = s.length();
        char[] t = new char[N];

		for (int i=0; i<N; i++) {
			int pos = suffixArray.index(i) - 1;
			if (pos < 0) pos = s.length() - 1;
			t[i] = s.charAt(pos);
		}

		for (int i=0; i<N; i++)
			BinaryStdOut.write(t[i]);

		BinaryStdOut.close();
	}

	/*apply Burrows-Wheeler decoding*/
	/*reading from standard input and writing to standard output*/
	public static void decode () {
 		int first 	= BinaryStdIn.readInt();
 		String t 	= BinaryStdIn.readString();
 		int N 		= t.length();
 		int[] next  = new int[N];
 		int[] asciiSet = new int[257];

		/*we use index counting technique (256 ASCII) to find the 1st column*/
		/*the first column is a sorted array of string t*/
		/*this technique is less time and space consuming than-*/
		/*creating a new array of length N and sorting it*/
		for (int i = 0; i < N; i++)
			asciiSet[t.charAt(i) + 1] = asciiSet[t.charAt(i) + 1] + 1;

		/*create the first column sorted array and call it tSorted*/
		int aux = 0;
		char[] tSorted = new char[N];
		for (int i=0; i<257; i++) {
			if (asciiSet[i] != 0) {
				for (int j=0; j<asciiSet[i]; j++)
					tSorted[aux++] = (char)(i-1);
			}
		}

		/*Now we create the next[] array to be able to*/
		/*recover the original string using next[], first, t*/
		for (int i=0; i<N; i++) {
			int var = 0;
			if (i > 0 && tSorted[i] == tSorted[i-1]) {
				var = next[i-1] + 1;
			}

			for (int j=var; j<N; j++) {
				if (tSorted[i] == t.charAt(j)) {
					var = j;
					break;
				}
			}
			next[i] = var;
		}

		/*Now that we have next[], first, and String t*/
		/*we need to recover the original string*/
		for (int i=0; i<N; i++) {
			first = next[first];
			BinaryStdOut.write(t.charAt(first));
		}
 		BinaryStdOut.close();
	}

	public static void main (String [] args) {
		if    	     (args[0].equals("-")) encode();
		else if      (args[0].equals("+")) decode();
        else throw new IllegalArgumentException("Illegal command line argument");
	}
}