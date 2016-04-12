//John Abraham
// Project5.java

import java.io.*;
import java.util.*;
import java.util.regex.*;

public class Project5
{
	static int fileCount = 0;
	static int dirCount = 0;
	static int size = 0;
	static Pattern p = null;
	static Pattern p2 = null;
	static String dir = null;
	static int matches = 0;

	public static void main( String args[] ) throws Exception
	{
		    File f = new File ( args[0] );
   		    dir = args[0];
		    p = Pattern.compile( args[1] );
		    p2 = Pattern.compile( args[2] );

			if ( ! f.exists() )
					System.out.println("File: " + f.getAbsolutePath() + " does not exist") ;
			else
			{
			    	listFilesInDirectory( f );
			    	System.out.println( "\n" + size + " bytes in " + fileCount + " files in " + dirCount + " directories");
			    	System.out.println( "Total line matches: " +matches );
		    }
	}

	public static void listFilesInDirectory( File dir ) throws Exception
	{
			File[] files = dir.listFiles();

			if (files != null)
			{
				for (File f : files)
				{
						Matcher m = p.matcher( f.getName() );

						if ( f.isDirectory() )
						{
								String [] entries = f.list();
								for( int i=0; i<entries.length; i++ )
								{
										Matcher mmm = p.matcher( entries[i] );
										if( mmm.find() )
										{
											if( isMatch( dir+"/"+f.getName()+"/"+entries[i] ) )
											{
													dirCount++;
													i = entries.length;
											}
										}
								}

								listFilesInDirectory( f );
						}
						else
						{
							if( m.find() )
							{
									List<String> text = new ArrayList<String>();
									Scanner sc = new Scanner ( new File( dir +"/"+ f.getName() ) );

									while( sc.hasNext() )
											text.add( sc.nextLine() );

									for (int i=0; i<text.size(); i++)	// print out file name and location
									{
										Matcher m2 = p2.matcher( text.get( i ) );

										if( m2.find() )
										{
											System.out.println( f );
										    size += f.length();
											fileCount++;
											i = text.size();
										}
									}

									for (int i=0; i<text.size(); i++)	// print out matched lines of text
									{
										Matcher m2 = p2.matcher( text.get( i ) );

										if( m2.find() )
										{
											System.out.println( text.get( i ) );
											matches++;
										}
									}
									text.clear();
							}
						}							
				}
			}
	}

	public static boolean isMatch ( String s ) throws Exception
	{
			Scanner sc = new Scanner ( new File (s) );
			List<String> text = new ArrayList<String>();
			while( sc.hasNext() )
				text.add( sc.nextLine() );

			for (int i=0; i<text.size(); i++)
			{
				Matcher m = p2.matcher( text.get( i ) );
				if( m.find() )
				{
					return true;
				}
			}
			return false;
	}
}