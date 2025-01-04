package pt.ipp.isep.dei.esoft.project.ui.console;

import pt.ipp.isep.dei.esoft.project.application.controller.ProjectScheduleController;
import pt.ipp.isep.dei.esoft.project.domain.Graph.Pair;
import pt.ipp.isep.dei.esoft.project.domain.ID;
import pt.ipp.isep.dei.esoft.project.domain.enumclasses.TypeID;

import static pt.ipp.isep.dei.esoft.project.domain.more.ColorfulOutput.*;

import java.util.Optional;
import java.util.Scanner;

public class ProjectScheduleUI implements Runnable {

    private String fileName;
    private ID graphID;
    private final ProjectScheduleController controller;


    public ProjectScheduleUI() {
        controller = new ProjectScheduleController();
    }

    @Override
    public void run() {
        System.out.println("\n\n══════════════════════════════════════════");
        System.out.println(ANSI_BRIGHT_WHITE + "               Project Schedule                 " + ANSI_RESET + "\n");
        confirmFileSubmission();
    }

    private ProjectScheduleController getController() {
        return controller;
    }

    String requestFileName() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter file name: ");
        fileName = scanner.nextLine();
        return fileName;
    }
    /**
     * Prompts the user to enter an ID (either item or operation) and validates the input.
     *
     * @return the ID entered by the user, or null if invalid input is provided.
     * <p>
     * Complexity:
     * - Validation loop: O(n), where n is the number of attempts until valid input is provided.
     * - String parsing and ID creation: O(1).
     * Overall: O(n), dominated by the input validation loop.
     */
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

    Optional<Pair<String, ID>> confirmSubmission() {
        boolean fileSubmission = fileNameSubmission();
        if (fileSubmission) {
           return getController().getScheduleInfo(fileName, graphID);
        }
        return Optional.empty();
    }

    private void confirmFileSubmission() {
        Optional<Pair<String, ID>> confirmation = confirmSubmission();

        if (confirmation.isPresent()) {
            System.out.println(ANSI_BRIGHT_GREEN + "File successfully created!" + ANSI_RESET);
        } else {
            System.out.println(ANSI_BRIGHT_RED + "File not created - cancelled!" + ANSI_RESET);
        }
    }

        /**
         * Prompts the user for a yes or no confirmation.
         *
         * @return The user's answer as a lowercase string, either "y" or "n".
         */
        private String yesNoConfirmation() {
            Scanner scanner = new Scanner(System.in);
            String answer = scanner.nextLine().toLowerCase();

            while (!answer.equals("y") && !answer.equals("n")) {
                System.out.print("Please enter 'y' or 'n': ");
                answer = scanner.nextLine().toLowerCase();
            }

            return answer;
        }

    /**
     * Checks if the provided input ID follows the expected format (e.g., "I-123" or "O-456").
     *
     * @param inputID the input string to validate.
     * @return true if the input is valid; false otherwise.
     * <p>
     * Complexity: O(1) as it performs a constant number of character checks.
     */
    private boolean checkIDInput(String inputID) {
        char reference = inputID.charAt(0);
        reference = Character.toUpperCase(reference);
        return inputID.length() > 1 && reference == 'G' && inputID.charAt(1) == '-' && Character.isDigit(inputID.charAt(2));
    }

    }

