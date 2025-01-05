package pt.ipp.isep.dei.esoft.project.application.controller;

import pt.ipp.isep.dei.esoft.project.domain.*;
import pt.ipp.isep.dei.esoft.project.repository.ItemRepository;
import pt.ipp.isep.dei.esoft.project.repository.MachineRepository;
import pt.ipp.isep.dei.esoft.project.repository.OperationRepository;
import pt.ipp.isep.dei.esoft.project.repository.Repositories;

import java.util.*;

import static pt.ipp.isep.dei.esoft.project.domain.more.ColorfulOutput.ANSI_BRIGHT_RED;
import static pt.ipp.isep.dei.esoft.project.domain.more.ColorfulOutput.ANSI_RESET;


/**
 * Controller class that manages the simulation of operations involving machines and items.
 * This class initializes the simulator and provides functionality to start the simulation.
 */
public class SimulatorController {

    private ItemRepository itemRepository;
    private MachineRepository machineRepository;
    private OperationRepository operationRepository;
    private static Simulator simulator;
    private ItemLevelProcessor boo;

    /**
     * Constructs a SimulatorController instance.
     * Initializes the item repository, machine repository, operation repository, and the simulator.
     */
    public SimulatorController() {
        getItemRepository();
        getMachineRepository();
        getOperationRepository();
    }

    /**
     * Retrieves the ItemRepository instance.
     *
     * @return the ItemRepository instance
     */
    private ItemRepository getItemRepository() {
        if (itemRepository == null) {
            Repositories repositories = Repositories.getInstance();
            itemRepository = repositories.getItemRepository();
        }
        return itemRepository;

    }

    /**
     * Retrieves the MachineRepository instance.
     *
     * @return the MachineRepository instance
     */
    private MachineRepository getMachineRepository() {
        if (machineRepository == null) {
            Repositories repositories = Repositories.getInstance();
            machineRepository = repositories.getMachineRepository();
        }
        return machineRepository;
    }

    /**
     * Retrieves the OperationRepository instance.
     *
     * @return the OperationRepository instance
     */
    private OperationRepository getOperationRepository() {
        if (operationRepository == null) {
            Repositories repositories = Repositories.getInstance();
            operationRepository = repositories.getOperationRepository();
        }
        return operationRepository;
    }

    /**
     * Starts the simulation without considering priority in the operation queues.
     */
    public void startSimulationWithOutPriority() {
        long startTime = System.nanoTime(); // Ou System.currentTimeMillis()
        try {
            simulator = new Simulator(getMachinesMap(), getTree(), getOperationList(), (ArrayList<Machine>) getMachineList(), false);
            simulator.startSimulation();

            long endTime = System.nanoTime(); // Ou System.currentTimeMillis()
            double durationInSeconds = (endTime - startTime) / 1_000_000_000.0;
            System.out.printf("Execution time: %.3f seconds", durationInSeconds);
        } catch (IllegalArgumentException e) {
            System.out.printf("%n%s%s%s%s", ANSI_BRIGHT_RED, e.getMessage(), " End of simulation...", ANSI_RESET);
        }

    }

    public Map<ID, Float> startSimulationOrders() {
        long startTime = System.nanoTime(); // Ou System.currentTimeMillis()
        try {
            simulator = new Simulator(getMachinesMap(), getTreeOrders(), getOperationList(), (ArrayList<Machine>) getMachineList(), false);
            simulator.startSimulation();

            long endTime = System.nanoTime(); // Ou System.currentTimeMillis()
            double durationInSeconds = (endTime - startTime) / 1_000_000_000.0;
            System.out.printf("Execution time: %.3f seconds", durationInSeconds);
        } catch (IllegalArgumentException e) {
            System.out.printf("%n%s%s%s%s", ANSI_BRIGHT_RED, e.getMessage(), " End of simulation...", ANSI_RESET);
        }
        return simulator.getAvgTimesOp();
    }

    /**
     * Starts the simulation considering priority in the operation queues.
     */
    public void startSimulationWithPriority() {
        long startTime = System.nanoTime(); // Ou System.currentTimeMillis()
        try {
            simulator = new Simulator(getMachinesMap(), getTree(), getOperationList(), (ArrayList<Machine>) getMachineList(), true);
            simulator.startSimulation();

            long endTime = System.nanoTime(); // Ou System.currentTimeMillis()
            double durationInSeconds = (endTime - startTime) / 1_000_000_000.0;
            System.out.printf("Execution time: %.3f seconds", durationInSeconds);
        } catch (IllegalArgumentException e) {
            System.out.printf("%n%s%s%s%s", ANSI_BRIGHT_RED, e.getMessage(), " End of simulation...", ANSI_RESET);
        }
    }


    /**
     * Constructs a mapping of operations to their associated machines.
     *
     * @return a Map where each key is an Operation and its value is a Queue of Machines that can perform it
     */
    public Map<Operation, Queue<Machine>> getMachinesMap() {
        Map<Operation, Queue<Machine>> machinesMap = new HashMap<>();
        for (Machine machine : getMachineRepository().getMachineList()) {
            if (!machinesMap.containsKey(machine.getOperation())) {
                machinesMap.put(machine.getOperation(), new PriorityQueue<>());
                machinesMap.get(machine.getOperation()).add(machine);
            } else {
                machinesMap.get(machine.getOperation()).add(machine);
            }
        }
        return machinesMap;
    }

    /**
     * Method that returns the list of items.
     * It accesses the item repository and retrieves all stored items.
     *
     * @return List of items present in the repository.
     */
    public List<Item> getItemList() {
        return getItemRepository().getItemList();
    }

    public TreeMap<Integer, Queue<Map<Item, Float>>> getTree() {
        this.boo = new ItemLevelProcessor(false, "");
        return boo.getTree();
    }

    public TreeMap<Integer, Queue<Map<Item, Float>>> getTreeOrders() {
        this.boo = new ItemLevelProcessor(true, "prodPlanSimulator(ESINF)/main/java/pt/ipp/isep/dei/esoft/project/files/input/BooOrders.csv");
        return boo.getTree();
    }

    /**
     * Method that returns the list of operations.
     * It accesses the operation repository and retrieves all stored operations.
     *
     * @return List of operations present in the repository.
     */
    public List<Operation> getOperationList() {
        return getOperationRepository().getOperations();
    }

    /**
     * Method that returns the list of machines.
     * It accesses the machines repository and retrieves all stored machines.
     *
     * @return List of machines present in the repository.
     */
    public List<Machine> getMachineList() {
        return getMachineRepository().getMachineList();
    }

    /**
     * Provides access to the shared Simulator instance.
     *
     * @return The current Simulator instance.
     */
    public static Simulator getSharedSimulator() {
        return simulator;
    }
}
