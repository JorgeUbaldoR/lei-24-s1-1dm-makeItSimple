package pt.ipp.isep.dei.esoft.project.ui.console;

import pt.ipp.isep.dei.esoft.project.application.controller.ProjectScheduleController;
import pt.ipp.isep.dei.esoft.project.application.controller.ShowGraphCriticalPathController;
import pt.ipp.isep.dei.esoft.project.domain.Activity;
import pt.ipp.isep.dei.esoft.project.domain.ID;
import pt.ipp.isep.dei.esoft.project.domain.enumclasses.TypeID;

import java.util.List;
import java.util.Map;
import java.util.Scanner;

import static pt.ipp.isep.dei.esoft.project.domain.more.ColorfulOutput.*;

public class ShowGraphCriticalPathUI implements Runnable {

    private ID graphID;
    private final ShowGraphCriticalPathController controller;

    public ShowGraphCriticalPathUI() {
        controller = new ShowGraphCriticalPathController();
    }

    private ShowGraphCriticalPathController getController() {
        return controller;
    }

    @Override
    public void run() {

        System.out.println("\n\n══════════════════════════════════════════");
        System.out.println(ANSI_BRIGHT_WHITE + "               Critical Path(s)                 " + ANSI_RESET + "\n");

        printInfo();
    }

    private void printInfo() {
        graphID = getInputID();
        Map<String, Object> criticalPath = getController().calculateCriticalPath(graphID);

        printCriticalPath(criticalPath);

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
        return inputID.length() > 2 && reference == 'G' && inputID.charAt(1) == '-' && Character.isDigit(inputID.charAt(2));
    }

    public static void printCriticalPath(Map<String, Object> result) {
        @SuppressWarnings("unchecked")
        List<Activity> criticalPath = (List<Activity>) result.get("criticalPath");
        double totalDuration = (double) result.get("totalDuration");

        System.out.print("Critical Path: ");
        for (int i = 0; i < criticalPath.size(); i++) {
            if (i > 0) {
                System.out.print(" → ");
            }
            System.out.print(criticalPath.get(i).getId());
        }
        System.out.println("\nTotal Duration: " + totalDuration + " units");
    }


//        // Print critical path activities
//        System.out.println("\nCritical Path Activities:");
//        System.out.println("ID\t|\tES\t|\tEF\t|\tLS\t|\tLF\t|\tSL\t|\tDuration");
//        for (Activity activity : criticalPath) {
//            System.out.printf("%s\t|\t%2.0f\t|\t%2.0f\t|\t%2.0f\t|\t%2.0f\t|\t%2.0f\t|\t%.0f%n",
//                    activity.getId(),
//                    activity.getEarliestStart(),
//                    activity.getEarliestFinish(),
//                    activity.getLatestStart(),
//                    activity.getLatestFinish(),
//                    activity.getSlack(),
//                    activity.getDuration());
//
//        }

}

