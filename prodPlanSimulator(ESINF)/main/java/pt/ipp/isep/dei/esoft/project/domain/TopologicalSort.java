package pt.ipp.isep.dei.esoft.project.domain;
import pt.ipp.isep.dei.esoft.project.domain.Graph.map.MapGraph;

import java.io.PrintWriter;
import java.util.*;

import static pt.ipp.isep.dei.esoft.project.domain.more.ColorfulOutput.ANSI_BRIGHT_RED;
import static pt.ipp.isep.dei.esoft.project.domain.more.ColorfulOutput.ANSI_RESET;

public class TopologicalSort {

    private static final String FILE_PATH = "prodPlanSimulator(ESINF)/main/java/pt/ipp/isep/dei/esoft/project/files/output/";
    protected Map<String, List<String>> graph = new HashMap<>();
    protected Map<String, Integer> inDegree = new HashMap<>();

    /**
     * Adds an edge to the graph
     *
     * @param from edge that links to another edge
     * @param to edge that is linked to another edge
     */
    public void addEdge(String from, String to) {
        graph.putIfAbsent(from, new ArrayList<>());
        graph.putIfAbsent(to, new ArrayList<>());
        graph.get(from).add(to);

        inDegree.put(to, inDegree.getOrDefault(to, 0) + 1);
        inDegree.putIfAbsent(from, 0);
    }

    /**
     * Writes the sorted list in a csv file
     *
     * @param list sorted list to be written
     * @param fileName of the csv to be written
     */
    public boolean write(List<String> list, String fileName) {

        if (list.isEmpty()) return false;

        try {
            PrintWriter writer = new PrintWriter(FILE_PATH + fileName + ".csv");

            writer.println("Topological Sort");

            for (String line : list) {
                writer.println(line);
            }

            writer.close();
            return true;
        } catch (Exception e) {
            System.out.println(ANSI_BRIGHT_RED + "Error writing to file " + FILE_PATH + fileName + ANSI_RESET);
            return false;
        }
    }

    /**
     * Performs topological sort using an adjacency list
     *
     * @param map of the activities
     * @return a sorted list
     */
    public List<String> performTopologicalSort(MapGraph<Activity, Double> map) {
        List<String> sortedOrder = new ArrayList<>();
        try {

            Map<String, List<String>> adjacencyList = convertMapGraphToAdjacencyList(map);

            for (Map.Entry<String, List<String>> entry : adjacencyList.entrySet()) {
                String from = entry.getKey();
                List<String> toList = entry.getValue();

                for (String to : toList) {
                    this.addEdge(from, to);
                }
            }

            sortedOrder = topologicalSort();
            System.out.println("Topological Sorted Order: " + String.join(" -> ", sortedOrder));
            return sortedOrder;
        } catch (Exception e) {
            System.out.println("\n" + ANSI_BRIGHT_RED + "File not read" + ANSI_RESET);
            return sortedOrder;
        }
    }

    /**
     * Converts an mapGraph into an adjacency list
     *
     * @param mapGraph of activities
     * @return adjacency list
     */
    public Map<String, List<String>> convertMapGraphToAdjacencyList(MapGraph<Activity, Double> mapGraph) {

        Map<String, List<String>> adjacencyList = new HashMap<>();

        for (Activity activity : mapGraph.vertices()) {
            adjacencyList.putIfAbsent(activity.getId().toString(), new ArrayList<>());

            for (Activity neighbor : mapGraph.adjVertices(activity)) {
                adjacencyList.get(activity.getId().toString()).add(neighbor.getId().toString());
            }
        }

        return adjacencyList;
    }

    /**
     * Performs the topological sort
     *
     * @return sorted list
     * @throws IllegalArgumentException if the graph has a cycle
     */
    public List<String> topologicalSort() throws IllegalArgumentException {
        Queue<String> queue = new LinkedList<>();
        List<String> sortedOrder = new ArrayList<>();

        for (Map.Entry<String, Integer> entry : inDegree.entrySet()) {
            if (entry.getValue() == 0) {
                queue.add(entry.getKey());
            }
        }

        while (!queue.isEmpty()) {
            String current = queue.poll();
            sortedOrder.add(current);

            for (String neighbor : graph.get(current)) {
                inDegree.put(neighbor, inDegree.get(neighbor) - 1);
                if (inDegree.get(neighbor) == 0) {
                    queue.add(neighbor);
                }
            }
        }

        if (sortedOrder.size() != graph.size()) {
            throw new IllegalArgumentException("Graph has a cycle!");
        }

        return sortedOrder;
    }
}
