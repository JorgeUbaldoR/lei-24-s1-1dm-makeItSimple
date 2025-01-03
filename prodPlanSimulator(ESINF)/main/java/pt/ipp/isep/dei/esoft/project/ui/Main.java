package pt.ipp.isep.dei.esoft.project.ui;

import pt.ipp.isep.dei.esoft.project.files.ArrangeData;
import pt.ipp.isep.dei.esoft.project.ui.console.menu.MainMenuUI;

import java.io.IOException;

import static pt.ipp.isep.dei.esoft.project.domain.more.ColorfulOutput.*;

public class Main {
    private static final String BOO_PATH = "prodPlanSimulator(ESINF)/main/java/pt/ipp/isep/dei/esoft/project/files/SQL Developer/files/BOO.csv";
    private static final String ITEMS_PATH = "prodPlanSimulator(ESINF)/main/java/pt/ipp/isep/dei/esoft/project/files/SQL Developer/files/ITEMS.csv";
    private static final String NEW_BOO_PATH = "prodPlanSimulator(ESINF)/main/java/pt/ipp/isep/dei/esoft/project/files/SQL Developer/files/BOO_LAPR.csv";
    private static final String NEW_ITEMS_PATH = "prodPlanSimulator(ESINF)/main/java/pt/ipp/isep/dei/esoft/project/files/SQL Developer/files/ITEMS_LAPR.csv";

    public static void main(String[] args) throws IOException {
        ArrangeData.createChangedItem(ITEMS_PATH, NEW_ITEMS_PATH);
        ArrangeData.createChangedBoo(BOO_PATH, ITEMS_PATH, NEW_BOO_PATH);

        Bootstrap bootstrap = new Bootstrap();
        bootstrap.run();
        System.out.printf("%n%sDo login with:%n • %-9s -> %s%n • %-9s -> %s%s", ANSI_BRIGHT_BLACK, "User", "prodm@this.app", "Password", "manager", ANSI_RESET);


        try {
            MainMenuUI menu = new MainMenuUI();
            menu.run();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}