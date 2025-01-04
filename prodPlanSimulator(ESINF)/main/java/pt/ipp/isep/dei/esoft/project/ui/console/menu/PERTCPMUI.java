package pt.ipp.isep.dei.esoft.project.ui.console.menu;

import pt.ipp.isep.dei.esoft.project.domain.TopologicalSort;
import pt.ipp.isep.dei.esoft.project.ui.console.CreatePETRGraphUI;
import pt.ipp.isep.dei.esoft.project.ui.console.ProjectScheduleUI;
import pt.ipp.isep.dei.esoft.project.ui.console.utils.Utils;

import java.util.ArrayList;
import java.util.List;

import static pt.ipp.isep.dei.esoft.project.domain.more.ColorfulOutput.ANSI_BRIGHT_WHITE;
import static pt.ipp.isep.dei.esoft.project.domain.more.ColorfulOutput.ANSI_RESET;

public class PERTCPMUI implements Runnable {

    public PERTCPMUI() {
    }


    public void run() {
        List<MenuItem> options = new ArrayList<MenuItem>();
        options.add(new MenuItem("Generate PETR-CPM Graph", new CreatePETRGraphUI()));
        options.add(new MenuItem("Generate Project Schedule", new ProjectScheduleUI()));
        options.add(new MenuItem("Topological Sort Activities", new TopologicalSortUI()));
        options.add(new MenuItem("Simulate Delay", new GraphOperationUI()));


        int option = 0;
        do {
            System.out.println("\n\n╔════════════════════════════════════════╗");
            option = Utils.showAndSelectIndex(options, "║" + ANSI_BRIGHT_WHITE + "             PERT/CPM MENU   " + ANSI_RESET + "           ║");

            if ((option >= 0) && (option < options.size())) {
                options.get(option).run();
            }
        } while (option != -1);
    }
}
