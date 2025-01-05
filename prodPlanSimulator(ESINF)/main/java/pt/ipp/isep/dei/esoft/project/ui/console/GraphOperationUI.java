package pt.ipp.isep.dei.esoft.project.ui.console;

import pt.ipp.isep.dei.esoft.project.application.controller.PETRGraphController;
import pt.ipp.isep.dei.esoft.project.domain.Activity;
import pt.ipp.isep.dei.esoft.project.domain.Graph.map.MapGraph;
import pt.ipp.isep.dei.esoft.project.domain.ID;
import pt.ipp.isep.dei.esoft.project.domain.enumclasses.TypeID;

import java.util.*;

import static pt.ipp.isep.dei.esoft.project.domain.more.ColorfulOutput.*;
import static pt.ipp.isep.dei.esoft.project.domain.more.ColorfulOutput.ANSI_RESET;

public class GraphOperationUI implements Runnable {
    private final PETRGraphController controller;
    private final String DEFAULT_PATH = "prodPlanSimulator(ESINF)/main/java/pt/ipp/isep/dei/esoft/project/files/input/activities.csv";
    private final Scanner in = new Scanner(System.in);

    public GraphOperationUI() {
        controller = new PETRGraphController();
    }

    private PETRGraphController getController() {
        return this.controller;
    }

    @Override
    public void run() {
        System.out.println("\n\n══════════════════════════════════════════");
        System.out.println(ANSI_BRIGHT_WHITE + "             PETR-CPM GRAPH                 " + ANSI_RESET + "\n");

        System.out.printf("Select a option:%n");
        System.out.printf("     %s(1)%s - Use Default File%n", ANSI_BRIGHT_BLACK, ANSI_RESET);
        System.out.printf("     %s(2)%s - Insert Path File%n", ANSI_BRIGHT_BLACK, ANSI_RESET);
        System.out.printf("     %s(0)%s - Cancel%n", ANSI_BRIGHT_BLACK, ANSI_RESET);
        doChoice(getChoice());
    }


    /**
     * Executes actions based on the user's choice.
     *
     * @param choice User's choice from the menu.
     */
    private void doChoice(int choice) {
        switch (choice) {
            case 1:
                System.out.println(ANSI_BRIGHT_GREEN + "\n\n       USING DEFAULT FILE" + ANSI_RESET + "\n");
                simulateDelay(DEFAULT_PATH);
                break;

            case 2:
                System.out.println(ANSI_BRIGHT_GREEN + "\n\n       USING SPECIFIC FILE" + ANSI_RESET + "\n");
                System.out.print("Enter the path to the file: ");
                String path = in.nextLine();
                simulateDelay(path);
                break;

            default:
                System.out.println(ANSI_BRIGHT_RED + "\nLEAVING..." + ANSI_RESET);
                break;
        }
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

        if (!checkInputID(inputID)) {
            do {
                System.out.print("Enter an ID (follow the example): ");
                inputID = scanner.nextLine();
            } while (!checkInputID(inputID));
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

    /**
     * Checks if the provided input ID follows the expected format (e.g., "I-123" or "O-456").
     *
     * @param inputID the input string to validate.
     * @return true if the input is valid; false otherwise.
     * <p>
     * Complexity: O(1) as it performs a constant number of character checks.
     */
    private boolean checkInputID(String inputID) {
        char reference = inputID.charAt(0);
        reference = Character.toUpperCase(reference);
        return reference == 'G' && inputID.charAt(1) == '-' && Character.isDigit(inputID.charAt(2));
    }

    /**
     * Prompts the user to input their choice and validates it.
     *
     * @return A valid choice between 0 and 2.
     */
    private int getChoice() {
        int choice = 0;
        boolean valid = false;
        do {
            System.out.print("Type your choice: ");
            try {
                choice = in.nextInt();

                if (choice < 0 || choice > 2) {
                    System.out.println(ANSI_LIGHT_RED + "Select a valid number: " + ANSI_RESET);
                } else {
                    valid = true;
                }
            } catch (InputMismatchException e) {
                System.out.println(ANSI_LIGHT_RED + "Invalid choice. Please try again: " + ANSI_RESET);
                in.next();

            }
        } while (!valid);
        return choice;
    }


    private void displayOption(String name, int flag) {
        if (flag == 0) {
            System.out.printf("%nChosen ID -> [" + ANSI_GREEN + "%s" + ANSI_RESET + "]", name);
        } else {
            System.out.printf("%nChosen Path -> [" + ANSI_GREEN + "%s" + ANSI_RESET + "]", name);
        }
    }

    private void confirmationData(ID idGraph, String path) {
        System.out.print("Type (y) for a DGraph or (n) for NotDGraph: ");

        displayOption(idGraph.toString(), 0);
        displayOption(path, 1);

        System.out.print("\nDo you wish to save the operation? (y/n): ");
        String answer = yesNoConfirmation();

        if (answer.equalsIgnoreCase("y")) {

            try {
                if (getController().idGraphExist(idGraph)) {
                    MapGraph<Activity, Double> createdMap = getController().createMapGraph(path, true);
                    System.out.println(createdMap.toString(idGraph));
                    getController().writeGraph(createdMap, idGraph);
                    if (getController().saveGraph(createdMap, idGraph)) {
                        System.out.println("\n" + ANSI_BRIGHT_GREEN + "Graph successfully generated!" + ANSI_RESET);
                    } else {
                        System.out.println("\n" + ANSI_BRIGHT_YELLOW + "Graph successfully generated! - But ERROR saving MapGraph" + ANSI_RESET);
                    }
                } else {
                    System.out.println("\n" + ANSI_BRIGHT_RED + "Operation canceled - The ID chosen already exist" + ANSI_RESET);
                }

            } catch (Exception e) {
                System.out.println("\n" + ANSI_BRIGHT_RED + e.getMessage() + ANSI_RESET);
            }

        } else {
            System.out.println("\n" + ANSI_BRIGHT_RED + "Operation canceled." + ANSI_RESET);
        }

    }


    /**
     * Prompts the user for a yes or no confirmation.
     *
     * @return The user's answer as a lowercase string, either "y" or "n".
     */
    private String yesNoConfirmation() {
        Scanner sc = new Scanner(System.in);
        String answer = sc.nextLine().toUpperCase();

        while (!answer.equals("Y") && !answer.equals("N")) {
            System.out.print("Please enter 'y' or 'n': ");
            answer = sc.nextLine().toUpperCase();
        }

        return answer;
    }

    public void simulateDelay(String path) {
        MapGraph<Activity, Double> createdMap = getController().createMapGraph(path, true);

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


    }

}
