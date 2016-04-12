/* John Abraham
 * CS 1501: Assignment 3
 * ApplyDijkstraAllPairs.java
 */

import java.util.Scanner;

public class ApplyDijkstraAllPairs {
    private ApplyDijkstra[] all;

    /**
     * Computes a shortest paths tree from each vertex to to every other vertex in
     * the edge-weighted digraph <tt>G</tt>.
     * @param G the edge-weighted digraph
     * @throws IllegalArgumentException if an edge weight is negative
     * @throws IllegalArgumentException unless 0 &le; <tt>s</tt> &le; <tt>V</tt> - 1
     */
    public ApplyDijkstraAllPairs(EdgeWeightedDigraph G) {
        all  = new ApplyDijkstra[G.V()];
        for (int v = 0; v < G.V(); v++)
            all[v] = new ApplyDijkstra(G, v);
    }

    /**
     * Returns a shortest path from vertex <tt>s</tt> to vertex <tt>t</tt>.
     * @param s the source vertex
     * @param t the destination vertex
     * @return a shortest path from vertex <tt>s</tt> to vertex <tt>t</tt>
     *    as an iterable of edges, and <tt>null</tt> if no such path
     */
    public Iterable<DirectedEdge> path(int s, int t) {
        return all[s].pathTo(t);
    }

    /**
     * Is there a path from the vertex <tt>s</tt> to vertex <tt>t</tt>?
     * @param s the source vertex
     * @param t the destination vertex
     * @return <tt>true</tt> if there is a path from vertex <tt>s</tt> 
     *    to vertex <tt>t</tt>, and <tt>false</tt> otherwise
     */
    public boolean hasPath(int s, int t) {
        return dist(s, t) < Double.POSITIVE_INFINITY;
    }

    /**
     * Returns the length of a shortest path from vertex <tt>s</tt> to vertex <tt>t</tt>.
     * @param s the source vertex
     * @param t the destination vertex
     * @return the length of a shortest path from vertex <tt>s</tt> to vertex <tt>t</tt>;
     *    <tt>Double.POSITIVE_INFINITY</tt> if no such path
     */
    public double dist(int s, int t) {
        return all[s].distTo(t);
    }

    /************************************************************************************/
    public static void main(String[] args) {

        /***read in and store the vertex weights to be used to recalibrate***/
        int numVertices = StdIn.readInt();
        double [] vertexWeights = new double[numVertices];
        for (int i=0; i<numVertices; i++)
            vertexWeights[i] = StdIn.readDouble();


        /**create graph with given data and find shortest path with Dijkstra**/
        String inputData = "";
        while (StdIn.hasNextLine())
            inputData += StdIn.readLine() +"\n";

        for (int startingVertex=0 ; startingVertex<numVertices ; startingVertex++) {
            Scanner scan = new Scanner(inputData);
            In in = new In(scan);
            EdgeWeightedDigraph G = new EdgeWeightedDigraph(in);
            int s = startingVertex;

            ApplyDijkstra sp = new ApplyDijkstra(G, s);

            String shortestPath = "";

            for (int t = 0; t < G.V(); t++) {
                if (sp.hasPathTo(t)) {
                    shortestPath += s +" "+ t +" "+ sp.distTo(t) +" ";
                    if (sp.hasPathTo(t)) {
                        for (DirectedEdge e : sp.pathTo(t)) {
                            shortestPath += e + " ";
                        }
                    }
                    shortestPath += "\n";
                }
                else {
                    shortestPath += s +" "+ t +" no path\n";
                }
            }


            /*******recalibrate all the edges in G** to update shortest paths******/
            /**a helper method "edgeWeightLookup" has been added to be used next"**/
            Scanner scan2 = new Scanner(inputData);
            scan2.nextLine(); scan2.nextLine(); scan2.nextLine();
            while (scan2.hasNextLine()) {
                String line = scan2.nextLine();
                String[] tokens = line.split("\\s+");
                int source       = Integer.parseInt(tokens[0]);
                int dest         = Integer.parseInt(tokens[1]);
                double oldWeight = Double.parseDouble(tokens[2]);
                double edgeWeight =  oldWeight + vertexWeights[dest] - vertexWeights[source];
                recalibratedGraph += source +" "+ dest +" "+ edgeWeight +"\n";
            }

            /****update edge values of shortest path using the previous part****/
            shortestPath = shortestPath.replace("->", " ");
            Scanner scan3 = new Scanner(shortestPath);
            while (scan3.hasNextLine()) {
                String line = scan3.nextLine();
                String[] tokens = line.split("\\s+");
                double[][] lineData = new double[tokens.length/3][3];
                int j=0;
                for (int i=0; i<tokens.length/3; i++) {
                    lineData[i][0] = Double.parseDouble(tokens[j]); j += 1;
                    lineData[i][1] = Double.parseDouble(tokens[j]); j += 2;
                }

                double totalDistance = 0;
                if(lineData[0][0] != lineData[0][1]) {
                    for (int i=1; i<tokens.length/3; i++) {
                        lineData[i][2] = edgeWeightLookup((int)lineData[i][0], (int)lineData[i][1]);
                        totalDistance += edgeWeightLookup((int)lineData[i][0], (int)lineData[i][1]);
                    }
                    lineData[0][2] = totalDistance;
                }
                for (int i=0; i<tokens.length/3; i++) {
                    if (tokens[2].equals("no"))
                        StdOut.print((int)lineData[i][0] +" to "+ (int)lineData[i][1] +" "+ "\t\tno path  ");
                    else {
                        if(i == 0)
                            StdOut.printf("%d to %d (%.2f)  ", (int)lineData[i][0], (int)lineData[i][1], lineData[i][2]);
                        else
                            StdOut.printf("%d->%d %.2f  ", (int)lineData[i][0], (int)lineData[i][1], lineData[i][2]);
                    }
                } StdOut.println();
            } StdOut.println();
        }
    }

    static String recalibratedGraph = "";

    private static double edgeWeightLookup (int s, int t) {
        Scanner scanner = new Scanner(recalibratedGraph);
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            String[] tokens = line.split("\\s+");
            int source = Integer.parseInt(tokens[0]);
            int dest   = Integer.parseInt(tokens[1]);
            if (source == s && dest == t)
                return Double.parseDouble(tokens[2]);
        }
        return 0;
    }
    /************************************************************************************/
}