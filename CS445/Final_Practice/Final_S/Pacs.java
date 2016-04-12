import java.util.*;
import java.io.*;

public class Pacs
{
	public static void main( String args[] ) throws Exception
	{
		BufferedReader pacs = new BufferedReader (new FileReader( "pacs.txt" ));
		BufferedReader members = new BufferedReader( new FileReader( "member2pacs.txt" ) );

		TreeMap< String, ArrayList<String> > map = new TreeMap< String, ArrayList<String> >();

		while( members.ready() )
		{
			ArrayList< String > list = new ArrayList< String > ( Arrays.asList( members.readLine().split(" ") ) );
			map.put	( list.remove(0), list );
		}

		for( Map.Entry< String, ArrayList<String> >entry : map.entrySet() )
		{
			System.out.println( entry.getKey() +" "+ entry.getValue().toString().replace(",", " ").replace("[", "").replace("]","") );
		}

		TreeMap< String, ArrayList<String> > inverseMapping = new TreeMap<String, ArrayList<String>>();
		System.out.println();

		while( pacs.ready() )
		{
			String p = pacs.readLine();

			ArrayList<String> list = new ArrayList<String>();

			for( Map.Entry< String, ArrayList<String> >entry : map.entrySet() )
			{
				for( int i=0; i<entry.getValue().size(); i++ )
				{
					if( entry.getValue().get(i).equals( p ) )
						list.add( entry.getKey() );
				}
			}

			inverseMapping.put( p, list );
		}

		for( Map.Entry< String, ArrayList<String> >entry : inverseMapping.entrySet() )
		{
			System.out.println( entry.getKey() +" "+ entry.getValue().toString().replace(",", " ").replace("[", "").replace("]","") );
		}
	}
}
