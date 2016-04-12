//John Abraham
// Lab6.java

import java.io.*;
public class Lab6
{
  public static void main(String args[])
  {
	    String fname = new String( args[0] );
	    File f = new File (fname);

	if ( ! f.exists() )
	{
			// print the absolute path of the file and report that it does not exist
			System.out.println("File: " + f.getAbsolutePath() + " does not exist") ;
	}
    else if ( f.isDirectory() )
    {
			// print the absolute path of the file and then list its children (all the files in this dir)
	    	System.out.println( "FILE: " + f.getAbsolutePath() + " is a DIRECTORY. Its children are:");
	    	File [] listOfFiles = f.listFiles();

	    	for(int i=0; i<listOfFiles.length; i++)
	    	{
	    		if( listOfFiles[i].isFile() )
	    			System.out.println( listOfFiles[i].getName() );
			}
 	}
 	else
 	{
			// print the absolute path of the file and its size in bytes
	 		System.out.println("File: " + f.getAbsolutePath() + " contains " + f.length() + " bytes");
	}
  } // END MAIN
} // END LAB6 CLASS