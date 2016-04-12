/* John Abraham
 * CS 1501: Assignment 3
 * CreateAuxiliaryGraph.java
 */

public class CreateAuxiliaryGraph {
	public static void main (String [] args) {

		String inputData = "";
		int V = StdIn.readInt();
		int E = StdIn.readInt()+V; V++;
		inputData += V + "\n";
		inputData += E;

		while (StdIn.hasNextLine()) {
			inputData += StdIn.readLine() + "\n";
		}

		for (int i=0; i<V-1; i++)
			inputData += V-1 + " " + i +" 0.0\n";

		StdOut.println(inputData);
	}
}