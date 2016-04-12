import java.util.regex.*;

public class Lab7{
		public static void main (String args[]){
		      // String to be scanned to find the pattern.
		      String line = "";
		      String pattern =  "";

		      // Create a Pattern object
		      Pattern r = Pattern.compile(pattern);

		      // Now create matcher object.
		      Matcher m = r.matcher(line);

			  if ( m.find( ) ) {
			  		System.out.println( "found" );
			  		System.out.println( m.group(0) );
		      } else {
		         	System.out.println("NO MATCH");
		      }
		}
}