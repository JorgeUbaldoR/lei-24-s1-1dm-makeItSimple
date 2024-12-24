package pt.ipp.isep.dei.esoft.project.ui.console;

import pt.ipp.isep.dei.esoft.project.application.controller.ProjectScheduleController;

import static pt.ipp.isep.dei.esoft.project.domain.more.ColorfulOutput.*;

import java.util.Optional;
import java.util.Scanner;

public class ProjectScheduleUI implements Runnable {

    private String fileName;
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

    private void displayTypedFileName(String fileName) {
        System.out.printf("Selected file name: %s[%s]%s\n", ANSI_BRIGHT_YELLOW, fileName, ANSI_RESET);
    }


    private boolean fileNameSubmission() {
        String confirmation;
        do {
            fileName = requestFileName();
            displayTypedFileName(fileName);
            System.out.print("Do you wish to continue? (y/n): ");
            confirmation = yesNoConfirmation();
        } while (!confirmation.equalsIgnoreCase("y") && !confirmation.equalsIgnoreCase("n"));

        return confirmation.equalsIgnoreCase("y");
    }

    Optional<String> confirmSubmission() {
        boolean fileSubmission = fileNameSubmission();
        if (fileSubmission) {
           return getController().getFileName(fileName);
        }
        return Optional.empty();
    }

    private void confirmFileSubmission() {
        Optional<String> confirmation = confirmSubmission();

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

    }

