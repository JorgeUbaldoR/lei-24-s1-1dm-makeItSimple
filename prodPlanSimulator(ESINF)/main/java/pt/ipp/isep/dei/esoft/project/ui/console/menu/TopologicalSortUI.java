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

        List<String> list = topologicalSort.performTopologicalSort(getTopologicalController().getGraph(graphID));

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

}
