package pt.ipp.isep.dei.esoft.project.domain.Graph;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pt.ipp.isep.dei.esoft.project.domain.Graph.map.MapGraph;
import pt.ipp.isep.dei.esoft.project.domain.Graph.matrix.MatrixGraph;

import java.util.Comparator;
import java.util.LinkedList;

import static org.junit.jupiter.api.Assertions.*;

class AlgorithmsTest {

    private Graph<String, Integer> graph;

    @BeforeEach
    void setUp() {
        graph = new MapGraph<>(false); // Undirected graph

        graph.addVertex("A");
        graph.addVertex("B");
        graph.addVertex("C");
        graph.addVertex("D");

        graph.addEdge("A", "B", 1);
        graph.addEdge("B", "C", 2);
        graph.addEdge("C", "D", 3);
        graph.addEdge("D", "A", 4);
    }

    @Test
    void testBreadthFirstSearch_ValidVertex() {
        LinkedList<String> bfsResult = Algorithms.BreadthFirstSearch(graph, "A");
        assertNotNull(bfsResult);
        assertEquals(4, bfsResult.size());
        assertEquals("A", bfsResult.getFirst());
    }

    @Test
    void testBreadthFirstSearch_InvalidVertex() {
        assertNull(Algorithms.BreadthFirstSearch(graph, "Z"));
    }

    @Test
    void testDepthFirstSearch_ValidVertex() {
        LinkedList<String> dfsResult = Algorithms.DepthFirstSearch(graph, "A");
        assertNotNull(dfsResult);
        assertEquals(4, dfsResult.size());
        assertTrue(dfsResult.contains("A"));
    }

    @Test
    void testDepthFirstSearch_InvalidVertex() {
        assertNull(Algorithms.DepthFirstSearch(graph, "Z"));
    }

    @Test
    void testGetTopologicalOrder_ValidGraph() {
        graph = new MapGraph<>(true); // Directed graph

        graph.addEdge("A", "B", 1);
        graph.addEdge("B", "C", 2);
        graph.addEdge("C", "D", 3);

        LinkedList<String> topologicalOrder = Algorithms.getTopologicalOrder(graph);

        assertNotNull(topologicalOrder);
        assertEquals(4, topologicalOrder.size());
        assertEquals("A", topologicalOrder.getFirst());
        assertEquals("D", topologicalOrder.getLast());
    }

    @Test
    void testHasCircularDependencies_WithCycle() {
        assertTrue(Algorithms.hasCircularDependencies(graph)); // The undirected graph contains a cycle
    }

    @Test
    void testHasCircularDependencies_WithoutCycle() {
        graph = new MapGraph<>(true); // Directed graph

        graph.addEdge("A", "B", 1);
        graph.addEdge("B", "C", 2);
        graph.addEdge("C", "D", 3);

        assertFalse(Algorithms.hasCircularDependencies(graph)); // This graph has no cycle
    }

    @Test
    void testMinDistGraph_ValidGraph() {
        Comparator<Integer> comparator = Integer::compareTo;
        MatrixGraph<String, Integer> minGraph = Algorithms.minDistGraph(
                graph,
                comparator,
                Integer::sum
        );

        assertNotNull(minGraph);
        assertEquals(graph.numVertices(), minGraph.numVertices());
    }

    @Test
    void testMinDistGraph_EmptyGraph() {
        Graph<String, Integer> emptyGraph = new MapGraph<>(true);

        Comparator<Integer> comparator = Integer::compareTo;
        MatrixGraph<String, Integer> minGraph = Algorithms.minDistGraph(
                emptyGraph,
                comparator,
                Integer::sum
        );

        assertNotNull(minGraph);
        assertEquals(0, minGraph.numVertices());
    }
}
