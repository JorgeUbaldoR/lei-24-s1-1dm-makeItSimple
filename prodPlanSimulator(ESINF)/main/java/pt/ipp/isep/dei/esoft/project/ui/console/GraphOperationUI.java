package pt.ipp.isep.dei.esoft.project.ui.console;

import pt.ipp.isep.dei.esoft.project.application.controller.GraphOperationController;
import pt.ipp.isep.dei.esoft.project.application.controller.PETRGraphController;
import pt.ipp.isep.dei.esoft.project.application.controller.TopologicalController;
import pt.ipp.isep.dei.esoft.project.domain.Activity;
import pt.ipp.isep.dei.esoft.project.domain.Graph.CriticalPath;
import pt.ipp.isep.dei.esoft.project.domain.Graph.map.MapGraph;
import pt.ipp.isep.dei.esoft.project.domain.ID;
import pt.ipp.isep.dei.esoft.project.domain.ProjectSchedule;
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

        boolean confirm = fileNameSubmission();

        MapGraph<Activity, Double> createdMap = getGraphOperationController().getGraph(graphID);

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
            System.out.println(ANSI_BRIGHT_RED + "No activities available to delay." + ANSI_RESET);
            return;
        }

        ShowGraphCriticalPathUI showGraphCriticalPathUI = new ShowGraphCriticalPathUI();

        CriticalPath criticalPath = new CriticalPath();

        ProjectSchedule projectSchedule = new ProjectSchedule(createdMap, graphID);

        createdMap = removeActivities(createdMap);

        Map<String, Object> criticalPathOriginal = criticalPath.calculateCriticalPath(createdMap);
        System.out.println("\n\n══════════════════════════════════════════");
        System.out.println(ANSI_BRIGHT_WHITE + "             Original Critical Path                 " + ANSI_RESET + "\n");
        showGraphCriticalPathUI.printCriticalPath(criticalPathOriginal);

        do {
            System.out.println("\nChoose the activity to delay:");

            for (int i = 0; i < createdMap.vertices().size() - 1; i++) {
                System.out.println((i + 1) + "-> " + createdMap.vertices().get(i).getId());
            }
            System.out.println("0-> Exit");
            System.out.print("Your choice: ");
            option = in.nextInt();

            if (option < 0 || option > createdMap.vertices().size() - 1) {
                System.out.println(ANSI_BRIGHT_RED + "Invalid choice. Please try again." + ANSI_RESET);
            } else if (option > 0) {
                Activity selectedActivity = createdMap.vertices().get(option - 1);

                System.out.println("You selected: " + selectedActivity);
                System.out.print("Enter the delay duration: ");
                double delay = in.nextDouble();
                double duration = selectedActivity.getDuration() + delay;
                if (duration >= 0) {
                    selectedActivity.setDuration(selectedActivity.getDuration() + delay);

                    Map<String, Object> criticalPathDelayed = criticalPath.calculateCriticalPath(createdMap);
                    System.out.println("\n\n══════════════════════════════════════════");
                    System.out.println(ANSI_BRIGHT_WHITE + "             Delayed Critical Path                 " + ANSI_RESET + "\n");
                    showGraphCriticalPathUI.printCriticalPath(criticalPathDelayed);
                    projectSchedule.calculateScheduleAnalysis(false, duration);
                } else {
                    System.out.println(ANSI_BRIGHT_RED + "Duration cannot be negative" + ANSI_RESET);
                }
            }
        } while (option != 0);

    }

    public MapGraph<Activity, Double> removeActivities(MapGraph<Activity, Double> createdMap) {
        Iterator<Activity> iterator = createdMap.vertices().iterator();
        ID start = new ID(7777, TypeID.ACTIVITY);
        ID finish = new ID(7778, TypeID.ACTIVITY);
        while (iterator.hasNext()) {
            Activity activity = iterator.next();
            ID activityId = activity.getId();

            if (start.equals(activityId) || finish.equals(activityId)) {
                createdMap.removeVertex(activity);
            }
        }
        return createdMap;
    }
}
