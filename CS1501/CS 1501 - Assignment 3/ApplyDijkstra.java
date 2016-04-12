/* John Abraham
 * CS 1501: Assignment 3
 * ApplyDijkstra.java
 */

import java.util.Scanner;

public class ApplyDijkstra {
    private double[] distTo;          // distTo[v] = distance  of shortest s->v path
    private DirectedEdge[] edgeTo;    // edgeTo[v] = last edge on shortest s->v path
    private IndexMinPQ<Double> pq;    // priority queue of vertices

    /**
     * Computes a shortest paths tree from <tt>s</tt> to every other vertex in
     * the edge-weighted digraph <tt>G</tt>.
     * @param G the edge-weighted digraph
     * @param s the source vertex
     * @throws IllegalArgumentException if an edge weight is negative
     * @throws IllegalArgumentException unless 0 &le; <tt>s</tt> &le; <tt>V</tt> - 1
     */
    public ApplyDijkstra(EdgeWeightedDigraph G, int s) {
        for (DirectedEdge e : G.edges()) {
            if (e.weight() < 0)
                throw new IllegalArgumentException("edge " + e + " has negative weight");
        }

        distTo = new double[G.V()];
        edgeTo = new DirectedEdge[G.V()];
        for (int v = 0; v < G.V(); v++)
            distTo[v] = Double.POSITIVE_INFINITY;
        distTo[s] = 0.0;

        // relax vertices in order of distance from s
        pq = new IndexMinPQ<Double>(G.V());
        pq.insert(s, distTo[s]);
        while (!pq.isEmpty()) {
            int v = pq.delMin();
            for (DirectedEdge e : G.adj(v))
                relax(e);
        }

        // check optimality conditions
        assert check(G, s);
    }

    // relax edge e and update pq if changed
    private void relax(DirectedEdge e) {
        int v = e.from(), w = e.to();
        if (distTo[w] > distTo[v] + e.weight()) {
            distTo[w] = distTo[v] + e.weight();
            edgeTo[w] = e;
            if (pq.contains(w)) pq.decreaseKey(w, distTo[w]);
            else                pq.insert(w, distTo[w]);
        }
    }

    /**
     * Returns the length of a shortest path from the source vertex <tt>s</tt> to vertex <tt>v</tt>.
     * @param v the destination vertex
     * @return the length of a shortest path from the source vertex <tt>s</tt> to vertex <tt>v</tt>;
     *    <tt>Double.POSITIVE_INFINITY</tt> if no such path
     */
    public double distTo(int v) {
        return distTo[v];
    }

    /**
     * Is there a path from the source vertex <tt>s</tt> to vertex <tt>v</tt>?
     * @param v the destination vertex
     * @return <tt>true</tt> if there is a path from the source vertex
     *    <tt>s</tt> to vertex <tt>v</tt>, and <tt>false</tt> otherwise
     */
    public boolean hasPathTo(int v) {
        return distTo[v] < Double.POSITIVE_INFINITY;
    }

    /**
     * Returns a shortest path from the source vertex <tt>s</tt> to vertex <tt>v</tt>.
     * @param v the destination vertex
     * @return a shortest path from the source vertex <tt>s</tt> to vertex <tt>v</tt>
     *    as an iterable of edges, and <tt>null</tt> if no such path
     */
    public Iterable<DirectedEdge> pathTo(int v) {
        if (!hasPathTo(v)) return null;
        Stack<DirectedEdge> path = new Stack<DirectedEdge>();
        for (DirectedEdge e = edgeTo[v]; e != null; e = edgeTo[e.from()]) {
            path.push(e);
        }
        return path;
    }


    // check optimality conditions:
    // (i) for all edges e:            distTo[e.to()] <= distTo[e.from()] + e.weight()
    // (ii) for all edge e on the SPT: distTo[e.to()] == distTo[e.from()] + e.weight()
    private boolean check(EdgeWeightedDigraph G, int s) {

        // check that edge weights are nonnegative
        for (DirectedEdge e : G.edges()) {
            if (e.weight() < 0) {
                System.err.println("negative edge weight detected");
                return false;
            }
        }

        // check that distTo[v] and edgeTo[v] are consistent
        if (distTo[s] != 0.0 || edgeTo[s] != null) {
            System.err.println("distTo[s] and edgeTo[s] inconsistent");
            return false;
        }
        for (int v = 0; v < G.V(); v++) {
            if (v == s) continue;
            if (edgeTo[v] == null && distTo[v] != Double.POSITIVE_INFINITY) {
                System.err.println("distTo[] and edgeTo[] inconsistent");
                return false;
            }
        }

        // check that all edges e = v->w satisfy distTo[w] <= distTo[v] + e.weight()
        for (int v = 0; v < G.V(); v++) {
            for (DirectedEdge e : G.adj(v)) {
                int w = e.to();
                if (distTo[v] + e.weight() < distTo[w]) {
                    System.err.println("edge " + e + " not relaxed");
                    return false;
                }
            }
        }

        // check that all edges e = v->w on SPT satisfy distTo[w] == distTo[v] + e.weight()
        for (int w = 0; w < G.V(); w++) {
            if (edgeTo[w] == null) continue;
            DirectedEdge e = edgeTo[w];
            int v = e.from();
            if (w != e.to()) return false;
            if (distTo[v] + e.weight() != distTo[w]) {
                System.err.println("edge " + e + " on shortest path not tight");
                return false;
            }
        }
        return true;
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

        Scanner scan = new Scanner(inputData);
        In in = new In(scan);
        EdgeWeightedDigraph G = new EdgeWeightedDigraph(in);
        int s = Integer.parseInt(args[0]);

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
            }
            StdOut.println();
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