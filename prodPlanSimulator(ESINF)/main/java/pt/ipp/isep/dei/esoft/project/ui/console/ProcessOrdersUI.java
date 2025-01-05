package pt.ipp.isep.dei.esoft.project.ui.console;

import pt.ipp.isep.dei.esoft.project.application.controller.OrdersController;
import pt.ipp.isep.dei.esoft.project.domain.Order;

import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Scanner;

import static pt.ipp.isep.dei.esoft.project.domain.more.ColorfulOutput.ANSI_BRIGHT_WHITE;
import static pt.ipp.isep.dei.esoft.project.domain.more.ColorfulOutput.ANSI_RESET;

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
        }catch (Exception e) {
            e.printStackTrace();
        }

    }
}
