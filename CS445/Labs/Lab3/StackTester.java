import java.io.*;

public class StackTester
{
	public static void main( String args[] ) throws Exception
	{
		Stack myStack = new Stack();

		BufferedReader infile = new BufferedReader( new FileReader(args[0]) );
		while (infile.ready())
			myStack.push( infile.readLine() );
		infile.close();

		System.out.println("\nmyStack after loading from " + args[0] + "\n" + myStack); // invokes toString
		System.out.println("\npopping mystack one by one printing each top element's data:");
		while (! myStack.empty() )
		{
			Comparable data = myStack.pop();
			System.out.println( "top element's data: " + data );
		}
		System.out.println( "myStack now empty? " +  myStack.empty() ); // should print "true"

		// RELOAD STACK AND TEST makeEmpty

		System.out.println( "\nReloading myStack from " + args[0] + "\n");
		infile = new BufferedReader( new FileReader(args[0]) );
		while (infile.ready())
				myStack.push( infile.readLine() );
		infile.close();
		System.out.println("\nmyStack after RE-loading from " + args[0] + "\n" + myStack);  // invokes toString
		myStack.makeEmpty();
		System.out.println ("\nmyStack after calling makeEmpty() " + myStack ); // should not print any values

	} // END MAIN
} // END STACK TESTER CLASS

