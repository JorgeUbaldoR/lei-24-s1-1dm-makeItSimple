package pt.ipp.isep.dei.esoft.project.domain;
import java.io.PrintWriter;
import java.util.*;

import static pt.ipp.isep.dei.esoft.project.domain.more.ColorfulOutput.ANSI_BRIGHT_RED;
import static pt.ipp.isep.dei.esoft.project.domain.more.ColorfulOutput.ANSI_RESET;

public class TopologicalSort {

    private static final String FILE_PATH = "prodPlanSimulator(ESINF)/main/java/pt/ipp/isep/dei/esoft/project/files/output/";

    private Map<String, List<String>> graph = new HashMap<>();
    private Map<String, Integer> inDegree = new HashMap<>();

    public void addEdge(String from, String to) {
        graph.putIfAbsent(from, new ArrayList<>());
        graph.putIfAbsent(to, new ArrayList<>());
        graph.get(from).add(to);

        inDegree.put(to, inDegree.getOrDefault(to, 0) + 1);
        inDegree.putIfAbsent(from, 0);
    }

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

    public void write(List<String> list, String fileName) {
        try {
            PrintWriter writer = new PrintWriter(FILE_PATH + fileName + ".csv");

            writer.println("Topological Sort");

            for (String line : list) {
                writer.println(line);
            }

            writer.close();
        } catch (Exception e) {
            System.out.println(ANSI_BRIGHT_RED + "Error writing to file " + FILE_PATH + fileName + ANSI_RESET);
        }
    }


}
