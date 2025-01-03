package pt.ipp.isep.dei.esoft.project.domain.Graph;

import pt.ipp.isep.dei.esoft.project.domain.Graph.matrix.MatrixGraph;

import java.util.*;
import java.util.function.BinaryOperator;


public class Algorithms {

    /**
     * Performs a breadth-first search (BFS) on a graph starting from a specified vertex.
     *
     * @param <V> the type of vertices in the graph
     * @param <E> the type of edges in the graph
     * @param g   the graph instance
     * @param vert the starting vertex for the BFS
     * @return a LinkedList containing the vertices visited in BFS order, or null if the vertex is invalid
     *
     * Big O Complexity: O(V + E), where V is the number of vertices and E is the number of edges.
     * BFS visits each vertex and edge exactly once.
     */
    public static <V, E> LinkedList<V> BreadthFirstSearch(Graph<V, E> g, V vert) {
        if (!g.validVertex(vert)) {
            return null;
        }

        Queue<V> queue = new LinkedList<>();
        LinkedList<V> result = new LinkedList<>();
        Set<V> visited = new HashSet<>();

        queue.add(vert);
        visited.add(vert);

        while (!queue.isEmpty()) {
            V currentVertex = queue.poll();
            result.add(currentVertex);
            for (V adjVert : g.adjVertices(currentVertex)) {
                if (!visited.contains(adjVert)) {
                    queue.add(adjVert);
                    visited.add(adjVert);
                }
            }
        }
        return result;
    }

    public static <V, E> LinkedList<V> getTopologicalOrder(Graph<V, E> g) {
        LinkedList<V> topologicalOrder = new LinkedList<>();
        Map<V, Integer> inDegree = new HashMap<>();
        Queue<V> queue = new LinkedList<>();

        for (V vertex : g.vertices()) {
            inDegree.put(vertex, g.inDegree(vertex));
            if (inDegree.get(vertex) == 0) {
                queue.add(vertex);
            }
        }

        while (!queue.isEmpty()) {
            V currentVertex = queue.poll();
            topologicalOrder.add(currentVertex);

            for (V adjVertex : g.adjVertices(currentVertex)) {
                inDegree.put(adjVertex, inDegree.get(adjVertex) - 1);
                if (inDegree.get(adjVertex) == 0) {
                    queue.add(adjVertex);
                }
            }
        }

        return topologicalOrder;
    }

    /**
     * Performs depth-first search starting in a vertex
     *
     * @param g       Graph instance
     * @param vOrig   vertex of graph g that will be the source of the search
     * @param visited set of previously visited vertices
     * @param qdfs    return LinkedList with vertices of depth-first search
     */
    private static <V, E> void DepthFirstSearch(Graph<V, E> g, V vOrig, boolean[] visited, LinkedList<V> qdfs) {
        qdfs.add(vOrig);
        visited[g.key(vOrig)] = true;

        for (V adjVert : g.adjVertices(vOrig)) {
            if (!visited[g.key(adjVert)]) {
                DepthFirstSearch(g, adjVert, visited, qdfs);
            }
        }
    }

    /**
     * Performs a depth-first search (DFS) on a graph starting from a specified vertex.
     *
     * @param <V> the type of vertices in the graph
     * @param <E> the type of edges in the graph
     * @param g   the graph instance
     * @param vert the starting vertex for the DFS
     * @return a LinkedList containing the vertices visited in DFS order, or null if the vertex is invalid
     *
     * Big O Complexity: O(V + E), where V is the number of vertices and E is the number of edges.
     * DFS visits each vertex and edge exactly once.
     */
    public static <V, E> LinkedList<V> DepthFirstSearch(Graph<V, E> g, V vert) {

        if (!g.validVertex(vert)) {
            return null;
        }

        boolean[] visited = new boolean[g.numVertices()];
        LinkedList<V> qdfs = new LinkedList<>();

        DepthFirstSearch(g, vert, visited, qdfs);

        return qdfs;
    }


    /**
     * Detects whether a graph contains circular dependencies (cycles).
     *
     * @param <V> the type of vertices in the graph
     * @param <E> the type of edges in the graph
     * @param g   the graph instance
     * @return true if the graph contains cycles, false otherwise
     *
     * Big O Complexity: O(V + E), where V is the number of vertices and E is the number of edges.
     * The method performs DFS to check for back edges, indicating cycles.
     */
    public static <V, E> boolean hasCircularDependencies(Graph<V, E> g) {

        boolean[] visited = new boolean[g.numVertices()];
        boolean[] recStack = new boolean[g.numVertices()];

        for (V vertex : g.vertices()) {
            if (!visited[g.key(vertex)]) {
                if (hasCycleUtil(g, vertex, visited, recStack)) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Helper function for detecting cycles in a graph.
     *
     * @param <V>    the type of vertices in the graph
     * @param <E>    the type of edges in the graph
     * @param g      the graph instance
     * @param v      the current vertex being explored
     * @param visited a boolean array tracking visited vertices
     * @param recStack a boolean array tracking the recursion stack
     * @return true if a cycle is detected, false otherwise
     */
    private static <V, E> boolean hasCycleUtil(Graph<V, E> g, V v, boolean[] visited, boolean[] recStack) {
        int vKey = g.key(v);

        visited[vKey] = true;
        recStack[vKey] = true;

        for (V adjVert : g.adjVertices(v)) {
            int adjKey = g.key(adjVert);

            if (recStack[adjKey]) {
                return true;
            }

            if (!visited[adjKey] && hasCycleUtil(g, adjVert, visited, recStack)) {
                return true;
            }
        }

        recStack[vKey] = false;
        return false;
    }


    /**
     * Helper function for finding all paths between two vertices in a graph.
     *
     * @param <V>    the type of vertices in the graph
     * @param <E>    the type of edges in the graph
     * @param g      the graph instance
     * @param vOrig  the current vertex being explored
     * @param vDest  the destination vertex
     * @param visited a boolean array tracking visited vertices
     * @param path   a LinkedList containing the current path (in reverse order)
     * @param paths  an ArrayList containing all discovered paths
     */
    private static <V, E> void allPaths(Graph<V, E> g, V vOrig, V vDest, boolean[] visited,
                                        LinkedList<V> path, ArrayList<LinkedList<V>> paths) {

        path.add(vOrig);
        visited[g.key(vOrig)] = true;

        if (vOrig.equals(vDest)) {
            paths.add(new LinkedList<>(path));
        } else {
            for (V adjVert : g.adjVertices(vOrig)) {
                if (!visited[g.key(adjVert)]) {
                    allPaths(g, adjVert, vDest, visited, path, paths);
                }
            }
        }

        path.removeLast();
        visited[g.key(vDest)] = false;
    }

    /**
     * Finds all paths between two vertices in a graph.
     *
     * @param <V> the type of vertices in the graph
     * @param <E> the type of edges in the graph
     * @param g   the graph instance
     * @param vOrig the starting vertex
     * @param vDest the ending vertex
     * @return an ArrayList containing all paths from vOrig to vDest, or null if vertices are invalid
     *
     * Big O Complexity: O(V!), where V is the number of vertices.
     * This is due to the combinatorial nature of generating all possible paths.
     */
    public static <V, E> ArrayList<LinkedList<V>> allPaths(Graph<V, E> g, V vOrig, V vDest) {
        if (!g.validVertex(vOrig) || !g.validVertex(vDest)) {
            return null;
        }

        boolean[] visited = new boolean[g.numVertices()];
        ArrayList<LinkedList<V>> paths = new ArrayList<>();
        LinkedList<V> path = new LinkedList<>();

        allPaths(g, vOrig, vDest, visited, path, paths);
        return paths;
    }


    /**
     * Calculates the minimum distance graph using Floyd-Warshall
     *
     * @param g   initial graph
     * @param ce  comparator between elements of type E
     * @param sum sum two elements of type E
     * @return the minimum distance graph
     */
    @SuppressWarnings("unchecked")
    public static <V, E> MatrixGraph<V, E> minDistGraph(Graph<V, E> g, Comparator<E> ce, BinaryOperator<E> sum) {

        MatrixGraph<V, E> minGraph = new MatrixGraph<>(g.isDirected(), g.numVertices());
        List<V> vertices = g.vertices();

        for (V vOrig : vertices) {
            minGraph.addVertex(vOrig);
        }

        E[][] dist = (E[][]) new Object[g.numVertices()][g.numVertices()];
        for (int i = 0; i < g.numVertices(); i++) {
            for (int j = 0; j < g.numVertices(); j++) {
                if (i == j) {
                    dist[i][j] = null;
                } else {
                    Edge<V, E> edge = g.edge(vertices.get(i), vertices.get(j));
                    dist[i][j] = edge != null ? edge.getWeight() : null;
                }
            }
        }


        // T[i,j]^k = T[i,j]^(k-1) E ( T[i,k]^(K-1) and T[k,j]^(k-1) )

        for (int k = 0; k < g.numVertices(); k++) {
            for (int i = 0; i < g.numVertices(); i++) {
                if (k != i && dist[i][k] != null) {
                    for (int j = 0; j < g.numVertices(); j++) {
                        if (i != j && k != j && dist[k][j] != null) {
                            E newDist = sum.apply(dist[i][k], dist[k][j]);
                            if (dist[i][j] == null || ce.compare(newDist, dist[i][j]) < 0) {
                                dist[i][j] = newDist;
                            }
                        }
                    }
                }
            }
        }

        for (int i = 0; i < g.numVertices(); i++) {
            for (int j = 0; j < g.numVertices(); j++) {
                if (dist[i][j] != null) {
                    minGraph.addEdge(vertices.get(i), vertices.get(j), dist[i][j]);
                }
            }
        }

        return minGraph;
    }

}