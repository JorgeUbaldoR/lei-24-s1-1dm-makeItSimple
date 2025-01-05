package pt.ipp.isep.dei.esoft.project.ui.console;

import pt.ipp.isep.dei.esoft.project.application.controller.ShowGraphCriticalPathController;
import pt.ipp.isep.dei.esoft.project.domain.Activity;
import pt.ipp.isep.dei.esoft.project.domain.ID;
import pt.ipp.isep.dei.esoft.project.domain.enumclasses.TypeID;

import java.util.List;
import java.util.Map;
import java.util.Scanner;

import static pt.ipp.isep.dei.esoft.project.domain.more.ColorfulOutput.*;

public class ShowBottlenecksUI implements Runnable {

    private ID graphID;
    private final ShowGraphCriticalPathController controller;

    public ShowBottlenecksUI() {
        controller = new ShowGraphCriticalPathController();
    }

    private ShowGraphCriticalPathController getController() {
        return controller;
    }


    @Override
    public void run() {
        System.out.println("\n\n══════════════════════════════════════════");
        System.out.println(ANSI_BRIGHT_WHITE + "               Bottlenecks Activities                 " + ANSI_RESET + "\n");

        printInfo();
    }

    private void printInfo() {
        graphID = getInputID();

        Map<String, Object> bottlenecks = getController().identifyBottlenecks(graphID);

        printBottlenecks(bottlenecks);
    }

    public static void printBottlenecks(Map<String, Object> result) {
        @SuppressWarnings("unchecked")
        Map<Activity, Integer> topBottlenecks = (Map<Activity, Integer>) result.get("topBottlenecks");

        System.out.println("Top 5 Bottleneck Activities:");
        for (Activity activity : topBottlenecks.keySet()) {
            System.out.println(activity.getId() + " - Dependencies: " + topBottlenecks.get(activity));
        }
    }

    private ID getInputID() {
        System.out.printf("%sExample of Input ->%s %sG-102%s %n", ANSI_BRIGHT_BLACK, ANSI_RESET, ANSI_BRIGHT_WHITE, ANSI_RESET);
        Scanner scanner = new Scanner(System.in);
        String inputID;
        System.out.print("Enter an ID: ");
        inputID = scanner.nextLine();

        if (!checkIDInput(inputID)) {
            do {
                System.out.print(" NOT FOUND! Enter an ID (follow the example): ");
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

    private boolean checkIDInput(String inputID) {
        char reference = inputID.charAt(0);
        reference = Character.toUpperCase(reference);
        return reference == 'G' && inputID.charAt(1) == '-' && Character.isDigit(inputID.charAt(2));
    }


}
