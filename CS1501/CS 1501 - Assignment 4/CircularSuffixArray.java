/*************************************************************************
 * John Abraham
 * CS 1501: Assignment 4
 * 3/19/2014

 * Compilation: javac CircularSuffixArray.java
 
 *************************************************************************/
import java.util.Arrays;

public class CircularSuffixArray {
    private String s;
    private int[] suffixArrayIndex;

	public CircularSuffixArray(String s) {
        this.s = s;
        suffixArrayIndex = new int[s.length()];

        Node[] nodes = new Node[s.length()];

        for (int i = 0; i < s.length(); i++)
            nodes[i] = new Node(s, i);

        Arrays.sort(nodes);

        for (int i = 0; i < nodes.length; i++)
            suffixArrayIndex[i] = nodes[i].suffix;
	}

	public int length() {
        return suffixArrayIndex.length;
	}

	public int index(int i) {
        return suffixArrayIndex[i];
	}



    private class Node implements Comparable<Node> {
	    private String s;
	    private int suffix;

    	public Node(String s, int suffix) {
        	this.suffix = suffix;
        	this.s = s;
	    }

	    public int compareTo(Node node) {
	        return less(suffix, node.suffix);
	    }

	    private int less(int A, int B) {
	        for (int i = 0; i < s.length(); i++) {
	            if (getChar(B, i) > getChar(A, i))
	                return -1;
	            else if (getChar(B, i) == getChar(A, i))
	                continue;
	            else
	                return 1;
	        }
	        return 1;
	    }
	}

   private char getChar(int startSuffixForOrigin, int index) {
        if ( index < s.length() - startSuffixForOrigin)
            return s.charAt(startSuffixForOrigin + index);
        else
            return s.charAt(index - (s.length() - startSuffixForOrigin));
    }
}