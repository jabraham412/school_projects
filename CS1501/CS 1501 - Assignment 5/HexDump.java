/*************************************************************************
 * John Abraham
 * CS 1501: Assignment 4
 * 3/19/2014

 *  Compilation:  javac HexDump.java
 *  Execution:    java HexDump < file
 *  Dependencies: BinaryStdIn.java
 *  Data file:    http://introcs.cs.princeton.edu/stdlib/abra.txt
 *  
 *  Reads in a binary file and writes out the bytes in hex, 16 per line.
 *************************************************************************/

public class HexDump {

    public static void main(String[] args) {
        int BYTES_PER_LINE = 16;
        if (args.length == 1) {
            BYTES_PER_LINE = Integer.parseInt(args[0]);
        }

        int i;
        for (i = 0; !BinaryStdIn.isEmpty(); i++) {
            if (BYTES_PER_LINE == 0) { BinaryStdIn.readChar(); continue; }
            if (i == 0) StdOut.printf("");
            else if (i % BYTES_PER_LINE == 0) StdOut.printf("\n", i);
            else StdOut.print(" ");
            char c = BinaryStdIn.readChar();
            StdOut.printf("%02x", c & 0xff);
        }
        if (BYTES_PER_LINE != 0) StdOut.println();
        StdOut.println((i*8) + " bits");
    }
}