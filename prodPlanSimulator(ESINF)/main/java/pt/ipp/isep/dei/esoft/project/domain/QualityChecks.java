package pt.ipp.isep.dei.esoft.project.domain;


import pt.ipp.isep.dei.esoft.project.domain.TreeClasses.Node;
import pt.ipp.isep.dei.esoft.project.domain.TreeClasses.ProductionTree;

import static pt.ipp.isep.dei.esoft.project.domain.more.ColorfulOutput.*;

import java.util.*;

public class QualityChecks {

    private final PriorityQueue<Map<Integer, List<ID>>> priorityQueue;
    protected Map<Map<Integer, List<ID>>, Boolean> checkedOperations;

    /**
     * Constructor initializes the priority queue and the checked operations map.
     */
    public QualityChecks() {
        priorityQueue = new PriorityQueue<>(Comparator.comparingInt(map -> map.keySet().iterator().next()));
        checkedOperations = new HashMap<>();

    }

    /**
     * Fills the priority queue with data from the height map of the production tree.
     *
     * @return The populated priority queue.
     */
    public PriorityQueue<Map<Integer, List<ID>>> fillOperationsPriorityQueue() {
        ProductionTree pdt = new ProductionTree();
        pdt.getInformations("prodPlanSimulator(ESINF)/main/java/pt/ipp/isep/dei/esoft/project/files/input/boo.csv");
        Map<Integer, List<Node>> heightMap = pdt.getHeightMap();


        for (Map.Entry<Integer, List<Node>> entry : heightMap.entrySet()) {
            int height = entry.getKey();
            List<Node> nodesAtHeight = entry.getValue();

            Map<Integer, List<ID>> pqNode = new HashMap<>();
            List<ID> operations = new ArrayList<>();

            for (Node node : nodesAtHeight) {
                operations.add(node.getOperationID());
            }

            pqNode.put(height, operations);

            priorityQueue.add(pqNode);
        }

        return priorityQueue;
    }


    public PriorityQueue<Map<Integer, List<ID>>> fillOperationsPriorityQueueOrders(String path) {
        ProductionTree pdt = new ProductionTree();
        pdt.getInformations(path);
        Map<Integer, List<Node>> heightMap = pdt.getHeightMap();


        for (Map.Entry<Integer, List<Node>> entry : heightMap.entrySet()) {
            int height = entry.getKey();
            List<Node> nodesAtHeight = entry.getValue();

            Map<Integer, List<ID>> pqNode = new HashMap<>();
            List<ID> operations = new ArrayList<>();

            for (Node node : nodesAtHeight) {
                operations.add(node.getOperationID());
            }

            pqNode.put(height, operations);

            priorityQueue.add(pqNode);
        }

        return priorityQueue;
    }

    /**
     * Prepares a map for quality checks, initially marking all operations as unchecked.
     *
     * @return The populated quality check map.
     */
    public Map<Map<Integer, List<ID>>, Boolean> prepareQualityChecks() {
        Map<Map<Integer, List<ID>>, Boolean> checksMap = new LinkedHashMap<>();
        PriorityQueue<Map<Integer, List<ID>>> pq = fillOperationsPriorityQueue();

        while (!pq.isEmpty()) {
            Map<Integer, List<ID>> operations = pq.poll();
            checksMap.put(operations, false);
        }

        return checksMap;
    }


    /**
     * Performs quality checks on operations based on user confirmation and simulator activation.
     *
     * @param confirmation       User confirmation ("y" to perform checks).
     * @param simulatorActivated Indicates whether the simulator is activated.
     */
    public void performQualityChecks(String confirmation, boolean simulatorActivated) {
        Map<Map<Integer, List<ID>>, Boolean> checksMap = prepareQualityChecks();


        if (confirmation.equalsIgnoreCase("y")) {
            if (simulatorActivated) {

                for (Map.Entry<Map<Integer, List<ID>>, Boolean> entry : checksMap.entrySet()) {
                    Map<Integer, List<ID>> operationsMap = entry.getKey();
                    Boolean isChecked = entry.getValue();
                    int p = operationsMap.keySet().iterator().next();
                    List<ID> operations = operationsMap.get(p);
                    System.out.println("=======================================================");
                    System.out.println(ANSI_BRIGHT_WHITE + "Performing checks: " + ANSI_RESET);
                    if (!isChecked) {
                        entry.setValue(true);
                        System.out.printf("[%d] %s%s were checked%s%n", p, operations, ANSI_BRIGHT_GREEN, ANSI_RESET);
                        System.out.println("=======================================================");
                        System.out.println(ANSI_BRIGHT_WHITE + "Checks to be performed: " + ANSI_RESET);
                    }

                    for (Map.Entry<Map<Integer, List<ID>>, Boolean> entry2 : checksMap.entrySet()) {
                        operationsMap = entry2.getKey();
                        Boolean isChecked2 = entry2.getValue();
                        int p2 = operationsMap.keySet().iterator().next();
                        List<ID> operations2 = operationsMap.get(p2);

                        if (!isChecked2) {
                            System.out.printf("[%d] %s%s were not check yet%s%n", p2, operations2, ANSI_BRIGHT_YELLOW, ANSI_RESET);
                        }
                    }
                    System.out.println("=======================================================");

                    System.out.println("Operations that have already been checked:");
                    for (Map.Entry<Map<Integer, List<ID>>, Boolean> entry3 : checksMap.entrySet()) {
                        operationsMap = entry3.getKey();
                        Boolean isChecked3 = entry3.getValue();
                        int p3 = operationsMap.keySet().iterator().next();
                        List<ID> operations3 = operationsMap.get(p3);

                        if (isChecked3) {
                            System.out.printf("[%d] %s%s were checked%s%n", p3, operations3, ANSI_BRIGHT_GREEN, ANSI_RESET);
                        }
                    }
                    System.out.printf("%n%n");

                    sleep(1000);

                }
                checkedOperations = checksMap;
                System.out.printf("%n%sAll operations have been checked!%s%n", ANSI_BRIGHT_GREEN, ANSI_RESET);

            } else {
                System.out.println(ANSI_LIGHT_RED + "Activate simulator first!" + ANSI_RESET);

            }
        } else {
            System.out.println(ANSI_LIGHT_RED + "Quality check not performed!" + ANSI_RESET);

        }

    }

    /**
     * Utility method to pause execution for a given time.
     *
     * @param time Time in milliseconds to sleep.
     */
    private void sleep(int time) {
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException("Thread interrupted", e);
        }
    }
}
