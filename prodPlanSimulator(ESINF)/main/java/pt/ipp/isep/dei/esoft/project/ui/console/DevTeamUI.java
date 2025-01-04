package pt.ipp.isep.dei.esoft.project.ui.console;

import static pt.ipp.isep.dei.esoft.project.domain.more.ColorfulOutput.*;

/**
 * @author Paulo Maio pam@isep.ipp.pt
 */
public class DevTeamUI implements Runnable {

    public DevTeamUI() {

    }

    public void run() {
        System.out.println("\n\n══════════════════════════════════════════");
        System.out.println(ANSI_BRIGHT_WHITE+"            DEVELOPMENT TEAM                \n"+ANSI_RESET);
        System.out.println("  Emanuel Almeida   - 1230839@isep.ipp.pt");
        System.out.println("  Francisco Santos  - 1230564@isep.ipp.pt");
        System.out.println("  Jorge Ubaldo      - 1231274@isep.ipp.pt");
        System.out.println("  Paulo Mendes      - 1231498@isep.ipp.pt");
        System.out.println("  Romeu Xu     - 1230444@isep.ipp.pt");
        System.out.println("\n");
    }
}
