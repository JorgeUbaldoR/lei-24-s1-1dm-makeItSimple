package pt.ipp.isep.dei.esoft.project.ui.console.menu;

import pt.ipp.isep.dei.esoft.project.application.controller.TopologicalController;
import pt.ipp.isep.dei.esoft.project.domain.Activity;
import pt.ipp.isep.dei.esoft.project.domain.Graph.Pair;
import pt.ipp.isep.dei.esoft.project.domain.Graph.map.MapGraph;
import pt.ipp.isep.dei.esoft.project.domain.ID;
import pt.ipp.isep.dei.esoft.project.domain.TopologicalSort;
import pt.ipp.isep.dei.esoft.project.domain.enumclasses.TypeID;

import java.util.*;

import static pt.ipp.isep.dei.esoft.project.domain.more.ColorfulOutput.*;
import static pt.ipp.isep.dei.esoft.project.domain.more.ColorfulOutput.ANSI_RESET;

public class TopologicalSortUI implements Runnable {

    private String fileName;
    private ID graphID;
    private final TopologicalController controller;
    private final TopologicalSort topologicalSort = new TopologicalSort();

    public TopologicalSortUI() {
        controller = new TopologicalController();
    }

    private TopologicalController getTopologicalController() {
        return this.controller;
    }

    @Override
    public void run() {
        System.out.println("\n\n══════════════════════════════════════════");
        System.out.println(ANSI_BRIGHT_WHITE + "             Topological Sort                 " + ANSI_RESET + "\n");
        confirmFileSubmission();
    }

    private void confirmFileSubmission() {

        List<String> list = performTopologicalSort(graphID);

        boolean confirmation = confirmSubmission(list);

        if (confirmation) {
            System.out.println(ANSI_BRIGHT_GREEN + "File successfully created!" + ANSI_RESET);
        } else {
            System.out.println(ANSI_BRIGHT_RED + "File not created - cancelled!" + ANSI_RESET);
        }
    }

    private boolean confirmSubmission(List<String> list) {
        boolean fileSubmission = fileNameSubmission();
        if (fileSubmission) {
            topologicalSort.write(list, fileName);
            return true;
        }
        return false;
    }

    private boolean fileNameSubmission() {
        String confirmation;
        do {
            fileName = requestFileName();
            graphID = getInputID();
            displayTypedInfo(fileName, graphID);

            System.out.print("Do you wish to continue? (y/n): ");
            confirmation = yesNoConfirmation();
        } while (!confirmation.equalsIgnoreCase("y") && !confirmation.equalsIgnoreCase("n"));

        return confirmation.equalsIgnoreCase("y");
    }

    String requestFileName() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter file name: ");
        fileName = scanner.nextLine();
        return fileName;
    }

    private ID getInputID() {
        System.out.printf("%sExample of Input ->%s %sG-102%s %n", ANSI_BRIGHT_BLACK, ANSI_RESET, ANSI_BRIGHT_WHITE, ANSI_RESET);
        Scanner scanner = new Scanner(System.in);
        String inputID;
        System.out.print("Enter an ID: ");
        inputID = scanner.nextLine();

        if (!checkIDInput(inputID)) {
            do {
                System.out.print("Enter an ID (follow the example): ");
                inputID = scanner.nextLine();
            } while (!checkIDInput(inputID));
        }
        char type = inputID.charAt(0);
        int serial = Integer.parseInt(inputID.split("-")[1]);
        switch (Character.toUpperCase(type)) {
            case 'G':
                return new ID(serial, TypeID.GRAPH);
            default:
                return null;
        }
    }

    private void displayTypedInfo(String fileName, ID inputID) {
        System.out.printf("%nChosen file name -> [" + ANSI_GREEN + "%s" + ANSI_RESET + "]", fileName);
        System.out.printf("%nChosen ID -> [" + ANSI_GREEN + "%s" + ANSI_RESET + "]%n", inputID);
    }

    private String yesNoConfirmation() {
        Scanner scanner = new Scanner(System.in);
        String answer = scanner.nextLine().toLowerCase();

        while (!answer.equals("y") && !answer.equals("n")) {
            System.out.print("Please enter 'y' or 'n': ");
            answer = scanner.nextLine().toLowerCase();
        }

        return answer;
    }

    private boolean checkIDInput(String inputID) {
        char reference = inputID.charAt(0);
        reference = Character.toUpperCase(reference);
        return reference == 'G' && inputID.charAt(1) == '-' && Character.isDigit(inputID.charAt(2));
    }


    /**
     * Performs topological sort by creating a new Map based on the file read, converts it to an adjacency
     * list representation, and performs a topological sort to determine a valid linear ordering of its vertices.
     *
     * @param path of the file to be used
     */
    private List<String> performTopologicalSort(ID graphID) {
        List<String> sortedOrder = new ArrayList<>();
        try {

            MapGraph<Activity, Double> createdMap = getTopologicalController().getGraph(graphID);

            Map<String, List<String>> adjacencyList = convertMapGraphToAdjacencyList(createdMap);

            for (Map.Entry<String, List<String>> entry : adjacencyList.entrySet()) {
                String from = entry.getKey();
                List<String> toList = entry.getValue();

                for (String to : toList) {
                    topologicalSort.addEdge(from, to);
                }
            }


            sortedOrder = topologicalSort.topologicalSort();
            System.out.println("Topological Sorted Order: " + String.join(" -> ", sortedOrder));
            return sortedOrder;
        } catch (Exception e) {
            System.out.println("\n" + ANSI_BRIGHT_RED + "File not read" + ANSI_RESET);
            return sortedOrder;
        }
    }

    /**
     * Converts a MapGraph into an adjacencyList
     *
     * @param mapGraph that has all the information of the file read in a graph form
     *
     * @return an adjacencyList of the MapGraph
     */
    private Map<String, List<String>> convertMapGraphToAdjacencyList(MapGraph<Activity, Double> mapGraph) {

        Map<String, List<String>> adjacencyList = new HashMap<>();

        for (Activity activity : mapGraph.vertices()) {
            adjacencyList.putIfAbsent(activity.getId().toString(), new ArrayList<>());

            for (Activity neighbor : mapGraph.adjVertices(activity)) {
                adjacencyList.get(activity.getId().toString()).add(neighbor.getId().toString());
            }
        }

        return adjacencyList;
    }
}
