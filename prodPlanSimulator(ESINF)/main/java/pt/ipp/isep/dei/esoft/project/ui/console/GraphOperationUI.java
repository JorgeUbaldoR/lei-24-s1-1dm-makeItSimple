package pt.ipp.isep.dei.esoft.project.ui.console;

import pt.ipp.isep.dei.esoft.project.application.controller.GraphOperationController;
import pt.ipp.isep.dei.esoft.project.application.controller.PETRGraphController;
import pt.ipp.isep.dei.esoft.project.application.controller.TopologicalController;
import pt.ipp.isep.dei.esoft.project.domain.Activity;
import pt.ipp.isep.dei.esoft.project.domain.Graph.CriticalPath;
import pt.ipp.isep.dei.esoft.project.domain.Graph.map.MapGraph;
import pt.ipp.isep.dei.esoft.project.domain.ID;
import pt.ipp.isep.dei.esoft.project.domain.enumclasses.TypeID;

import java.util.*;

import static pt.ipp.isep.dei.esoft.project.domain.more.ColorfulOutput.*;
import static pt.ipp.isep.dei.esoft.project.domain.more.ColorfulOutput.ANSI_RESET;

public class GraphOperationUI implements Runnable {
    private ID graphID;
    private String fileName;
    private final GraphOperationController graphOperationController;
    private final PETRGraphController controller;
    private final Scanner in = new Scanner(System.in);

    public GraphOperationUI() {
        graphOperationController = new GraphOperationController();
        controller = new PETRGraphController();
    }

    private PETRGraphController getController() {
        return this.controller;
    }
    private GraphOperationController getGraphOperationController(){
        return this.graphOperationController;
    }

    @Override
    public void run() {
        System.out.println("\n\n══════════════════════════════════════════");
        System.out.println(ANSI_BRIGHT_WHITE + "             Delay Simulation                 " + ANSI_RESET + "\n");
        confirmFileSubmission();
    }

    private void confirmFileSubmission() {

        boolean confirmation = confirmSubmission();

        MapGraph<Activity, Double> createdMap = getGraphOperationController().getGraph(graphID);

        simulateDelay(createdMap);

        if (confirmation) {
            System.out.println(ANSI_BRIGHT_GREEN + "File successfully created!" + ANSI_RESET);
        } else {
            System.out.println(ANSI_BRIGHT_RED + "File not created - cancelled!" + ANSI_RESET);
        }
    }

    private boolean confirmSubmission() {
        boolean fileSubmission = fileNameSubmission();
        if (fileSubmission) {
            //topologicalSort.write(list, fileName);
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
        return inputID.length() > 2 && reference == 'G' && inputID.charAt(1) == '-' && Character.isDigit(inputID.charAt(2));
    }

    public void simulateDelay(MapGraph<Activity, Double> createdMap) {

        int option = -1;

        if (createdMap.numVertices() == 0) {
            System.out.println("No activities available to delay.");
            return;
        }

        do {
            System.out.println("\nChoose the activity to delay (or type 0 to exit):");

            for (int i = 0; i < createdMap.vertices().size(); i++) {
                System.out.println((i + 1) + "-> " + createdMap.vertices().get(i).getId());
            }

            System.out.print("Your choice: ");
            option = in.nextInt();

            if (option < 0 || option > createdMap.vertices().size()) {
                System.out.println("Invalid choice. Please try again.");
            } else if (option > 0) {
                Activity selectedActivity = createdMap.vertices().get(option - 1);

                System.out.println("You selected: " + selectedActivity);
                System.out.print("Enter the delay duration: ");
                double delay = in.nextDouble();

                selectedActivity.setDuration(selectedActivity.getDuration() + delay);
            }
        } while (option != 0);

        ShowGraphCriticalPathUI showGraphCriticalPathUI = new ShowGraphCriticalPathUI();

        CriticalPath criticalPath = new CriticalPath();

        Map<String, Object> critPath = criticalPath.calculateCriticalPath(createdMap);

        showGraphCriticalPathUI.printCriticalPath(critPath);

    }

}
