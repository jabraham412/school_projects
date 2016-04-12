/**
* John Abraham
* CS 445 - Project6
* Shortest Path
*
* convert adjacency matrix to adjacency list
* create HashMap with keys set to sources and values set to ArrayList of objects
* these objects represent destinations (edges) and distances (weights)
* create a separate class for Vertices
* create a separate class for Edges
* compute shortest path using Dijkstra's algorithm (method: computeDijkstraPaths)
*/

import java.io.*;
import java.util.*;

public class Project6
{
	// dataMap holds the adjacency list of the data that will be passed into vertices and edges
	static HashMap< String, ArrayList<Project6> > dataMap = new HashMap< String, ArrayList<Project6> >();

	// Destination and Distance object setters and getters (Edges and Weights).
	private String dest = null;
	private int weight = 0;

	Project6 ( String dest, int weight )
	{
		this.dest = dest;
		this.weight = weight;
	}

	String getDest()
	{
		return dest;
	}

	int getWeight()
	{
		return weight;
	}

	void setDest( String dest )
	{
		this.dest = dest;
	}

	void setWeight( int weight )
	{
		this.weight = weight;
	}
//##############################################################################################

	public static void main ( String args[] ) throws Exception
	{
		// read in each line from the file
		// and set the keys of the HashMap equal to cities and values to ArrayList of objects
		BufferedReader graphData = new BufferedReader (new FileReader( args[0] ));
		String[] splitLine = graphData.readLine().split("\\s+");
		int sourceIndex = 0;
		while( graphData.ready() )
		{
			String[] split = graphData.readLine().split("\\s+");
			ArrayList<Project6> destAndDist = new ArrayList<Project6>();
			for( int i=0; i<split.length; i++ )
			{
				if( !split[i].equals( "#" ) && !split[i].equals( "*" ) )
				{
					// pass in the name of the city and the distance from source (row city)
					Project6 obj = new Project6( splitLine[i], Integer.parseInt( split[i] ) );
					destAndDist.add( obj );
				}
			}
			dataMap.put( splitLine[sourceIndex], destAndDist );
			sourceIndex++;
		}
		
		// store the vertex objects in an ArrayList to be accessed later
		ArrayList< Vertex > vertices = new ArrayList< Vertex >();
		for(  Map.Entry < String, ArrayList<Project6> > entry : dataMap.entrySet() )
		{
			Vertex v = new Vertex( entry.getKey() );
			vertices.add( v );
		}

		// set pointers to adjacent cities from each city in vertices ArrayList
		for( int i=0; i<vertices.size(); i++ )
		{
			vertices.get(i).adjacent = verticesAndEdgesArray( vertices, vertices.get(i) );
		}
		
		// find which vertex user asked to compute as source
		int thisVertexIndex = 0;
		for ( int i=0; i<vertices.size(); i++ )
			if( vertices.get(i).toString().equals( args[1] ) )
				thisVertexIndex = i;

		System.out.println( "Finding shorest path from "+ args[1] +" to "+ args[2] );
		computeDijkstraPaths( vertices.get( thisVertexIndex ) );

		// this for loop can print every destination from the source but
		// the if statements make it only print results to the screen
		// associated with the destination user enters from command line
		for (Vertex v : vertices)
		{
			if( v.toString().equals( args[2] ) )
			{
				if( v.minDistance == 2147483647 ) // Integer.MAX_VALUE
				{
					System.out.println( "NO PATH\n" );
					System.exit(0);
				}
				else
				{
					List< Vertex > path = getShortestPathTo( v );
					System.out.print( path.toString().replace(",", "--->").replace("[", "").replace("]", "") );
					System.out.println( "\tDIST="+ v.minDistance +"\n" );
					System.exit(0);
				}
			}
		}
		System.out.println( "There is no such city on the map. Please enter correct abbreviations.\n" );
		System.exit(0);
	}
//##############################################################################################

	public static Edge[] verticesAndEdgesArray ( ArrayList< Vertex > vertices, Vertex entry )
	{
		// return an array that contains the destinations as vertex objects and distances in each index
		Edge[] e = new Edge[ dataMap.get( entry.toString() ).size() ];
		for( int i=0; i<e.length; i++ )
		{
			int counter = 0;
			for( int x=0; x<vertices.size(); x++ )
				if( vertices.get(x).toString().equals( dataMap.get(entry.toString()).get(i).getDest() ) )
					counter = x;
			e[i] = new Edge( vertices.get(counter), dataMap.get(entry.toString()).get(i).getWeight() );
		}
		return e;
	}
//##############################################################################################

	public static List<Vertex> getShortestPathTo( Vertex destination )
	{
		List< Vertex > path = new ArrayList< Vertex >();
		for ( Vertex vertex = destination; vertex != null; vertex = vertex.previous )
			path.add( vertex );
		Collections.reverse( path );
		return path;
	}
//##############################################################################################

	public static void computeDijkstraPaths( Vertex source )
	{
		source.minDistance = 0;
		PriorityQueue<Vertex> vertexPQueue = new PriorityQueue<Vertex>();
		vertexPQueue.add( source );

		while ( !vertexPQueue.isEmpty() )
		{
			Vertex pq = vertexPQueue.poll();

			// Visit each edge exiting pq
			for ( Edge e : pq.adjacent )
			{
				Vertex v = e.destination;
				int weight = e.weight;
				int distanceThroughU = pq.minDistance + weight;
				if ( distanceThroughU < v.minDistance )
				{
					vertexPQueue.remove(v);
					v.minDistance = distanceThroughU ;
					v.previous = pq;
					vertexPQueue.add( v );
				}
			}
		}
	}
}
// end of Project6 class-----------------------------------------------------------------------------------

class Vertex implements Comparable< Vertex >
{
	public final String label;
	public Edge[] adjacent;
	public int minDistance = Integer.MAX_VALUE;
	public Vertex previous;

	public Vertex( String argLabel ) {
		label = argLabel;
	}

	public String toString() {
		return label;
	}

	public int compareTo( Vertex other ) {
		return Integer.compare( minDistance, other.minDistance );
	}
}
// end of Vertex class--------------------------------------------------------------------------------------

class Edge
{
	public final Vertex destination;
	public final int weight;
	public Edge(Vertex argDest, int argWeight) {
		destination = argDest;
		weight = argWeight;
	}
}
// end of Edge class--------------------------------------------------------------------------------------


