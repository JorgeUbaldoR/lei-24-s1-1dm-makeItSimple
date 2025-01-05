package pt.ipp.isep.dei.esoft.project.ui.console;

import pt.ipp.isep.dei.esoft.project.application.controller.OrdersController;
import pt.ipp.isep.dei.esoft.project.domain.Machine;
import pt.ipp.isep.dei.esoft.project.domain.Order;

import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Scanner;

import static pt.ipp.isep.dei.esoft.project.domain.more.ColorfulOutput.*;

public class ProcessOrdersUI implements Runnable {
    private OrdersController controller;

    public ProcessOrdersUI() {
        controller = new OrdersController();
    }

    @Override
    public void run() {
        System.out.println("\n\n══════════════════════════════════════════");
        System.out.println(ANSI_BRIGHT_WHITE + "             Process Orders                 " + ANSI_RESET + "\n");

        System.out.print("Enter the file path to your orders.csv: ");
        Scanner scanner = new Scanner(System.in);
        String path = scanner.nextLine();

        try {
            PriorityQueue<Order> list = controller.readOrderCSV(path);
            int size = list.size();
            System.out.printf("%n%sREGISTERING ORDERS...%s%n", ANSI_BRIGHT_GREEN, ANSI_RESET);

            for (int i = 0; i < size; i++) {
                System.out.println(list.poll());
            }

            Queue<Machine> machineList = controller.readMachinhesCSV();
            System.out.printf("%n%sREGISTERING NEW TIMES IN MACHINES...%s%n", ANSI_BRIGHT_GREEN, ANSI_RESET);

            for (Machine machine : machineList) {
                System.out.printf("[%sMachine = %s%s] : Operation = %s%s%s (%-28s | Time = %s%-10s%s%n",
                        ANSI_BRIGHT_BLACK, ANSI_RESET, machine.getId_machine(),
                        ANSI_BRIGHT_WHITE,machine.getOperation().getOperationId(),ANSI_RESET,machine.getOperation().getOperationName()+")",
                        ANSI_BRIGHT_WHITE,machine.getProcessingSpeed(),ANSI_RESET
                        );
            }

            System.out.printf("%n%sREGISTERING PRODUCTION TREES AND NEW QUANTITIES...%s%n", ANSI_BRIGHT_GREEN, ANSI_RESET);
            controller.createProductionTree();



        } catch (Exception e) {
            System.out.println(ANSI_BRIGHT_RED + "Error reading orders.csv: " + e.getMessage() + ANSI_RESET);
        }

    }






}
