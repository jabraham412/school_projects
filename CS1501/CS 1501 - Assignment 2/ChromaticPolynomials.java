/* John Abraham
 * CS 1501: Algorithm Implementation 
 * Assignment 2: Polynomials & Graphs
 * Task 3: Create a client of the Graph class to compute the chromatic polynomial of a list of simple graphs. (50 points)
*/

import java.math.BigInteger;

public class ChromaticPolynomials
{
	static Polynomial [] P;
	static int polyTerm;
	static double maxNumEdges;

	public static void main (String args[])
	{
		if (args.length == 0) {
			StdOut.println("There are no graphs to process!");
		}else {
			for (int i=0; i<args.length; i++) {
				In in = new In(args[i]);
	            Graph G = new Graph(in);
	            P = new Polynomial[G.V()+1];
	            polyTerm = 0;
	            maxNumEdges = ( (double)G.V()*((double)G.V()-(double)1) ) / (double)2;

	            /*Print the name of graph, # V's & E's, and the adjacency list*/
	            StdOut.println("\nGraph: " + args[i].replace(".txt", ""));
	            StdOut.println(G);

	            /*Print whether the graph is connected or not connected.
	             *Print the number of components if it is not connected*/
				CC cc = new CC(G);
	            if ( cc.count() == 1) {
		            StdOut.println(args[i].replace(".txt", "") + " is connected!");
		        }else {
		        	StdOut.println(args[i].replace(".txt", "") + " is NOT connected!");
		        	StdOut.println(args[i].replace(".txt", "") + " has " + cc.count() + " components");
		        }

			    /*
				 * When computing the chromatic polynomial of a graph G with |V| vertices, when the number
				 * of edges is less than |V|(|V|-1)/4, use the reduction where edge {u,v} is removed  from
				 * G to form two related graphs G – uv (the graph that results after the edge {u,v}     is
				 * removed from G) and Guv (the graph formed from G by identifying the vertices u and v)
				 * P(G, x) = P(G − uv, x)  −  P(Guv, x), otherwise,
				 * use the alternate reduction formula: P(G, x) = P(G + uv, x)  +  P(Guv, x)
				 *
				 * 1) Reducing to Null Graphs.     P(G, x) = P(G - uv, x)  -  P(Guv, x)
				 * 2) Reducing to Complete Graphs. P(G, x) = P(G + uv, x)  +  P(Guv, x)
				*/

			    double GraphDensity = (double)2*(double)G.E() / ((double)G.V()*(double)G.V()-(double)G.V());

				if ( GraphDensity <= 0.5 ) {
					flag1 = true;
			    	computeChromaticPolynomialToNull( G, G.E() );
			    } else if (GraphDensity > 0.5) {
			    	computeChromaticPolynomialToComplete( G, G.E() );
				}

        		/* Print the chromatic polynomial computed from the Polynomial class */
        		StdOut.print("P(x) = ");
        		for (int t = 0; t < P.length; t++) {
	        		StdOut.print( P[t] );
	        		if (t < P.length-1) StdOut.print(" + ");
	        	}
	        	StdOut.println();
			}
		}
	}

	static Graph G2 = null;
	static boolean flag1 = false;
	static boolean flag2 = true;

    public static void computeChromaticPolynomialToNull ( Graph g, int E )
    {
		/********************Base case: Graph is null********************/
        if (E == 0) {
        	//add term to polynomial class
        	P[polyTerm] = new Polynomial( BigInteger.valueOf(1), g.V() );
        	polyTerm++;
        	flag2 = true;
		/********************Base case: Graph is null********************/
        }else{
    		Graph G1 = null;
        	//find an edge in the graph and remove it store new in G1 then merge vertices and store in G2
        	for   (int i=0; i<g.V(); i++)
        	{
        		for (int j=0; j<(int)maxNumEdges; j++)
        		{
					if ( g.isEdge(i,j) )
					{
						g.removeEdge(i,j);
						G1 = new Graph(g);
						if( flag1 && flag2 )
						{
							g.mergeVertices(i,j);
							G2 = new Graph(g);
							flag2 = false;
						}
						i=g.V();
					}
				}
 			}
        	computeChromaticPolynomialToNull( G1 , E-1);
        }
     	computeChromaticPolynomialToNull( G2 , E-1);
    }

    public static void computeChromaticPolynomialToComplete (Graph G, int E)
    {
		/******************Base case: Graph is Complete******************/
		if ( E == (int)maxNumEdges ) {
			//add to plynomial class
        	P[polyTerm] = new Polynomial( BigInteger.valueOf(1), G.V() );
        	polyTerm++;
		/******************Base case: Graph is Complete******************/
		} else {

		}
    }
}