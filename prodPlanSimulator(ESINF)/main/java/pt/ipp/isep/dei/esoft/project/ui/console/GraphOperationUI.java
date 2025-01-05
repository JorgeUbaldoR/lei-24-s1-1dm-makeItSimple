package pt.ipp.isep.dei.esoft.project.ui.console;

import pt.ipp.isep.dei.esoft.project.application.controller.GraphOperationController;
import pt.ipp.isep.dei.esoft.project.application.controller.PETRGraphController;
import pt.ipp.isep.dei.esoft.project.domain.Activity;
import pt.ipp.isep.dei.esoft.project.domain.Delay;
import pt.ipp.isep.dei.esoft.project.domain.Graph.map.MapGraph;
import pt.ipp.isep.dei.esoft.project.domain.ID;
import pt.ipp.isep.dei.esoft.project.domain.enumclasses.TypeID;
import pt.ipp.isep.dei.esoft.project.ui.console.ShowGraphCriticalPathUI;

import java.util.Map;
import java.util.Scanner;

import static pt.ipp.isep.dei.esoft.project.domain.more.ColorfulOutput.*;

public class GraphOperationUI implements Runnable {

    private ID graphID;
    private final GraphOperationController graphOperationController;
    private final Scanner in = new Scanner(System.in);

    public GraphOperationUI() {
        graphOperationController = new GraphOperationController();
    }

    @Override
    public void run() {
        System.out.println("\n\n══════════════════════════════════════════");
        System.out.println(ANSI_BRIGHT_WHITE + "             Delay Simulation                 " + ANSI_RESET + "\n");
        confirmFileSubmission();
    }

    private void confirmFileSubmission() {
        boolean confirm = fileNameSubmission();
        if (!confirm) return;

        MapGraph<Activity, Double> createdMap = graphOperationController.getGraph(graphID);
        simulateDelay(createdMap);
    }

    private boolean fileNameSubmission() {
        String confirmation;
        do {
            graphID = getInputID();
            displayTypedInfo(graphID);

            System.out.print("Do you wish to continue? (y/n): ");
            confirmation = yesNoConfirmation();
        } while (!confirmation.equalsIgnoreCase("y") && !confirmation.equalsIgnoreCase("n"));

        return confirmation.equalsIgnoreCase("y");
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

    private void displayTypedInfo(ID inputID) {
        System.out.printf("%nChosen ID -> [" + ANSI_GREEN + "%s" + ANSI_RESET + "]%n", inputID);
    }

    private String yesNoConfirmation() {
        String answer = in.nextLine().toLowerCase();

        while (!answer.equals("y") && !answer.equals("n")) {
            System.out.print("Please enter 'y' or 'n': ");
            answer = in.nextLine().toLowerCase();
        }

        return answer;
    }

    private boolean checkIDInput(String inputID) {
        char reference = Character.toUpperCase(inputID.charAt(0));
        return inputID.length() > 2 && reference == 'G' && inputID.charAt(1) == '-' && Character.isDigit(inputID.charAt(2));
    }

    private void simulateDelay(MapGraph<Activity, Double> createdMap) {

        if (createdMap == null) {
            System.out.println(ANSI_BRIGHT_RED + "No activities available to delay." + ANSI_RESET);
            return;
        }

        Delay delay = new Delay();
        ShowGraphCriticalPathUI showGraphCriticalPathUI = new ShowGraphCriticalPathUI();

        createdMap = delay.removeActivities(createdMap);

        Map<String, Object> criticalPathOriginal = delay.calculateOriginalCriticalPath(createdMap);
        System.out.println("\n\n══════════════════════════════════════════");
        System.out.println(ANSI_BRIGHT_WHITE + "             Original Critical Path                 " + ANSI_RESET + "\n");
        showGraphCriticalPathUI.printCriticalPath(criticalPathOriginal);

        int option;
        do {
            System.out.println("\nChoose the activity to delay:");
            for (int i = 0; i < createdMap.vertices().size() - 1; i++) {
                System.out.println((i + 1) + "-> " + createdMap.vertices().get(i).getId());
            }
            System.out.println("0-> Exit");
            System.out.print("Your choice: ");
            option = in.nextInt();

            if (option > 0 && option <= createdMap.vertices().size() - 1) {
                Activity selectedActivity = createdMap.vertices().get(option - 1);

                System.out.println("You selected: " + selectedActivity);
                System.out.print("Enter the delay duration: ");
                double delayDuration = in.nextDouble();

                try {
                    delay.updateActivityDuration(selectedActivity, delayDuration);

                    Map<String, Object> criticalPathDelayed = delay.calculateDelayedCriticalPath(createdMap);
                    System.out.println("\n\n══════════════════════════════════════════");
                    System.out.println(ANSI_BRIGHT_WHITE + "             Delayed Critical Path                 " + ANSI_RESET + "\n");
                    showGraphCriticalPathUI.printCriticalPath(criticalPathDelayed);
                } catch (IllegalArgumentException e) {
                    System.out.println(ANSI_BRIGHT_RED + e.getMessage() + ANSI_RESET);
                }
            } else if (option != 0) {
                System.out.println(ANSI_BRIGHT_RED + "Invalid choice. Please try again." + ANSI_RESET);
            }
        } while (option != 0);
    }
}
