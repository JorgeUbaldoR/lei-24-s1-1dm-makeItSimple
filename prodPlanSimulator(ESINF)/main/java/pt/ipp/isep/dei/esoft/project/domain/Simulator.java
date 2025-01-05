package pt.ipp.isep.dei.esoft.project.domain;


import java.util.*;

import static pt.ipp.isep.dei.esoft.project.domain.more.ColorfulOutput.*;

public class Simulator {
    /**
     * A Simulator class simulates a manufacturing process where items are processed
     * by machines according to specified operations.
     *
     * <p>This class handles the operations of machines, the queuing of items for
     * processing, and tracks execution times and waiting times during the
     * simulation. It allows for simulation of various manufacturing scenarios
     * with a specified set of operations and available machines.</p>
     *
     * @param <Operation> The type representing an operation in the manufacturing process.
     * @param <Item> The type representing an item that undergoes processing.
     */
    private final Map<Operation, Queue<Machine>> machineListMap;
    private final Map<Operation, OperationQueue> operationQueueMap;
    private final ArrayList<Machine> machineList;
    private TreeMap<Integer, Queue<Map<Item, Float>>> itemsByHeight;
    private boolean wasActivated = false;


    private final Map<ID, Float> timesPerOperation = new HashMap<>();
    private final Map<Operation, Float> operationTime;
    private final Map<Item, Float> waitingTime;
    private final Map<Operation, Float> avgExecutionTime;
    private final LinkedList<Item> itemLinkedList;
    public final Map<Item, LinkedList<ID>> itemLinkedListMap;
    private final Map<Machine, Float> machineUsage;
    private final Map<Operation, Integer> executionPerOperation;

    /**
     * Constructor for the Simulator class, which initializes various fields and sets up the simulation
     * environment. This includes the machine list, operation queue map, and item queues based on the provided
     * parameters. The constructor also calls the checkInformation method to ensure that all provided data is
     * valid before proceeding with the setup.
     *
     * @param machines      A map of operations to queues of machines, representing which machines are
     *                      available for each operation.
     * @param items         A TreeMap containing items categorized by height. Each item is mapped to its
     *                      associated float values in a queue.
     * @param operations    A list of operations to be simulated.
     * @param machList      A list of machines that will be used in the simulation.
     * @param priorityFlag  A boolean flag indicating whether priority-based processing should be used in
     *                      the simulation.
     */
    public Simulator(Map<Operation, Queue<Machine>> machines, TreeMap<Integer, Queue<Map<Item, Float>>> items, List<Operation> operations, ArrayList<Machine> machList, boolean priorityFlag) {
        checkInformation(machines, operations, items);
        this.machineList = new ArrayList<>(machList);
        this.operationQueueMap = new HashMap<>();
        this.machineListMap = machines;
        this.operationTime = new HashMap<>();
        this.waitingTime = new HashMap<>();
        this.itemLinkedList = new LinkedList<>();
        this.itemLinkedListMap = new HashMap<>();
        this.machineUsage = new HashMap<>();
        this.avgExecutionTime = new HashMap<>();
        this.executionPerOperation = new HashMap<>();
        this.itemsByHeight = items;
        addOperationToQueue(operations, priorityFlag);

    }

    /**
     * Constructs a Simulator instance with default settings.
     * Initializes an empty map for machine operations and an empty list for operation queues.
     */
    public Simulator() {
        this.machineList = new ArrayList<>();
        this.operationQueueMap = new HashMap<>();
        this.machineListMap = new HashMap<>();
        this.operationTime = new HashMap<>();
        this.waitingTime = new HashMap<>();
        this.itemLinkedList = new LinkedList<>();
        this.itemLinkedListMap = new HashMap<>();
        this.machineUsage = new HashMap<>();
        this.avgExecutionTime = new HashMap<>();
        this.executionPerOperation = new HashMap<>();
    }


    /**
     * Adds a list of operations to the operation queue list, creating
     * an OperationQueue for each operation.
     *
     * @param operations   The list of operations to be added to the operation queue.
     * @param priorityFlag A flag indicating whether priority should be applied to the queues.
     */
    private void addOperationToQueue(List<Operation> operations, boolean priorityFlag) {
        for (Operation operation : operations) {
            operationQueueMap.put(operation, new OperationQueue(operation, priorityFlag));
        }
    }


    /**
     * This method processes the items in the given TreeMap and organizes them into operation queues.
     * It checks if each item has a current operation, and if so, it adds the item to the appropriate
     * operation queue in the operationQueueMap.
     *
     * @param items A TreeMap where each key represents the height of an item, and the value is a queue
     *              of maps containing items and their associated float values.
     * @return A boolean indicating whether any items were added to the operation queues.
     */
    private boolean createQueues(TreeMap<Integer, Queue<Map<Item, Float>>> items) {
        boolean haveItems = false;

        for (Integer height : items.keySet()) {
            Queue<Map<Item, Float>> itemsAtHeight = items.get(height);
            if (itemsAtHeight != null && !itemsAtHeight.isEmpty()) {
                for (Map<Item, Float> itemMap : itemsAtHeight) {
                    for (Map.Entry<Item, Float> entry : itemMap.entrySet()) {
                        Item item = entry.getKey();
                        if (item.getCurrentOperation() != null) {
                            haveItems = true;
                            Operation currentOperation = item.getCurrentOperation();
                            OperationQueue operationQueue = operationQueueMap.get(currentOperation);
                            operationQueue.addItemToQueue(item);
                        }
                    }
                }
            }
        }

        return haveItems;
    }


    /**
     * This method starts the simulation process. It processes each height in the items list and runs a loop
     * that updates machine statuses, processes items, assigns new items to machines, and prints simulation
     * progress and statistics.
     */
    public void startSimulation() {
        int time = 0;

        for (Integer height : itemsByHeight.keySet()) {
            System.out.printf("Processing height: %d%n", height);

            Queue<Map<Item, Float>> itemsAtHeight = itemsByHeight.get(height);
            if (itemsAtHeight == null || itemsAtHeight.isEmpty()) {
                continue;
            }

            if (!createQueues(new TreeMap<>(Map.of(height, itemsAtHeight)))) {
                System.out.printf("No items at height %d to queue.%n", height);
                continue;
            }


            while (checkItemsLeftProcesses() || checkTimeOperations()) {
                printInitialSimulationStatus(time);

                System.out.printf("%n• Updates:%n");
                updateMachines();

                System.out.printf("%n• Queue:%n");
                printQueue();

                System.out.printf("%n• Status:%n");
                printMachineStatus();

                System.out.printf("%n• New Processing:%n");
                assignItemToMachine();

                if (time > 0) {
                    fillWaitingTime(operationQueueMap);
                }

                System.out.printf("%n%s===========================================================%s%n%n%n", ANSI_BRIGHT_BLACK, ANSI_RESET);
                time++;
            }
            wasActivated = true;
        }

        System.out.printf("%s✅ All operations completed! %s%n", ANSI_GREEN, ANSI_RESET);
        printStatistics();
    }

    /**
     * Prints the initial status of the simulation at the current time.
     *
     * @param time The current time of the simulation.
     */
    private void printInitialSimulationStatus(int time) {
        System.out.printf("%s===========================================================%s%n", ANSI_BRIGHT_BLACK, ANSI_RESET);
        System.out.printf("%s||%s              %sSIMULATION - TIME: %d%s                     %s||%s%n", ANSI_BRIGHT_BLACK, ANSI_RESET, ANSI_BRIGHT_WHITE, time, ANSI_RESET, ANSI_BRIGHT_BLACK, ANSI_RESET);
        System.out.printf("%s===========================================================%s%n", ANSI_BRIGHT_BLACK, ANSI_RESET);
    }

    /**
     * Prints statistical information about the simulation.
     */
    private void printStatistics() {
        printExecutionTimesMachine();
        printExecutionTimesOperation();
        printAverageExecutionTime();
        printWaitingTime();
        printItemRoute();
        printMachineRoute();
    }

    /**
     * Prints the current state of all operation queues to the console.
     *
     * <p>This method iterates through the list of operation queues and
     * prints the string representation of each queue using the
     * {@link OperationQueue#toString()} method. Each queue will be
     * displayed on a new line, providing a clear overview of the
     * items waiting to be processed in each operation queue.</p>
     */
    private void printQueue() {
        for (OperationQueue operationQueue : operationQueueMap.values()) {
            System.out.printf("%s%n", operationQueue.toString());
        }
    }


    /**
     * Assigns the next item from the operation queue to an available machine.
     */
    private void assignItemToMachine() {
        for (OperationQueue operationQueue : operationQueueMap.values()) {
            //System.out.println(operationQueue + "--------------------------------------");
            if (!operationQueue.isEmpty()) {
                Queue<Machine> listMachineOp = machineListMap.get(operationQueue.getOperation());
//                System.out.println(operationQueue.getOperation() + ": " + listMachineOp);
                if (listMachineOp != null && !listMachineOp.isEmpty()) {
                    for (Machine currentMachine : listMachineOp) {
                        if (currentMachine.isAvailable() && !operationQueue.isEmpty()) {
                            currentMachine.processItem(operationQueue.getNextItem());
                            fillExecutionPerOperation(currentMachine.getOperation());
                        }
                    }
                } else {
                    //Case doesn't exist machine for item to be process
                    operationQueue.getNextItem();
                }
            }
        }
    }


    /**
     * Updates the status of the machines and assigns the next operation
     * for any items that have finished processing.
     */
    private void updateMachines() {
        for (Machine machine : machineList) {
            boolean finished = machine.updateMachine();
            if (finished) {
                Item currentItem = machine.getCurrentProcessingItem();
                Operation nextOperation = currentItem.getNextOperation();
                extraMethods(currentItem, machine);
                if (nextOperation != null) {
                    OperationQueue operationQueue = operationQueueMap.get(nextOperation);
                    operationQueue.addItemToQueue(currentItem);
                } else {
                    itemLinkedList.add(currentItem);
                }
            }
        }
    }

    /**
     * Processes additional methods related to the current item and machine.
     *
     * @param currentItem The current item being processed by the machine.
     * @param machine     The machine that is processing the current item.
     */
    private void extraMethods(Item currentItem, Machine machine) {
        addExecutionTimesOperation(machine.getOperation(), machine.getProcessingSpeed());
        addExecutionTimesMachine(machine, machine.getProcessingSpeed());
        itemLinkedListMap.putIfAbsent(currentItem, new LinkedList<>());
        itemLinkedListMap.get(currentItem).add(machine.getId_machine());
    }

    /**
     * Prints the status of all machines in the simulator.
     */
    private void printMachineStatus() {
        for (Operation operation : machineListMap.keySet()) {
            for (Machine machine : machineListMap.get(operation)) {
                machine.printStatus();
            }
        }
    }

    /**
     * Checks if there are any items left in the operation queues.
     *
     * @return true if there are items in any of the operation queues; false otherwise.
     */
    private boolean checkItemsLeftProcesses() {
        for (OperationQueue operationQueue : operationQueueMap.values()) {
            if (!operationQueue.isEmpty()) {
                return true;
            }
        }
        return false;
    }

    /**
     * Checks if any machines are still processing items.
     *
     * @return true if any machine has time left to finish processing; false otherwise.
     */
    private boolean checkTimeOperations() {
        for (Machine machine : machineList) {
            if (machine.getTimeLeftToFinish() != 0)
                return true;
        }

        return false;
    }

    /**
     * Puts the current thread to sleep for the specified duration.
     *
     * @param milliseconds the duration in milliseconds for which the thread should sleep.
     */
    private void sleep(int milliseconds) {
        try {
            Thread.sleep(milliseconds);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException("Thread interrupted", e);
        }
    }

    /**
     * Checks the provided lists for null or empty values and throws an exception if any are found.
     *
     * @param machines   the map of machines to check.
     * @param operations the list of operations to check.
     * @param items      the list of items to check.
     * @throws IllegalArgumentException if any of the lists are null or empty.
     */
    private void checkInformation(Map<Operation, Queue<Machine>> machines, List<Operation> operations, TreeMap<Integer, Queue<Map<Item, Float>>> items) {
        if (machines == null || machines.isEmpty()) {
            throw new IllegalArgumentException("Machine list is null or empty");
        }
        if (operations == null || operations.isEmpty()) {
            throw new IllegalArgumentException("Operations list is null or empty");
        }
        if (items == null || items.isEmpty()) {
            throw new IllegalArgumentException("Items list is null or empty");
        }
    }


    /**
     * Adds the execution time of a given operation to the map. If the operation already has recorded time,
     * the new time is added to the existing time; otherwise, the operation is added with the provided time.
     *
     * @param op   the operation for which the execution time is being recorded.
     * @param time the time to add to the execution time of the operation.
     */
    private void addExecutionTimesOperation(Operation op, float time) {
        if (!this.operationTime.containsKey(op)) {
            operationTime.put(op, time);
        } else {
            float currentTime = operationTime.get(op) + time;
            operationTime.put(op, currentTime);
        }
    }

    /**
     * Adds or updates the execution time for a specific machine.
     *
     * @param m    The machine for which the execution time is being added or updated.
     * @param time The time (in seconds) to be added to the machine's total execution time.
     */
    private void addExecutionTimesMachine(Machine m, float time) {
        if (!this.machineUsage.containsKey(m)) {
            machineUsage.put(m, time);
        } else {
            float currentTime = machineUsage.get(m) + time;
            machineUsage.put(m, currentTime);
        }
    }


    /**
     * Prints the execution times for all operations and percentages relative to the total time to the console.
     * Each operation and its corresponding execution time is printed.
     */
    public void printExecutionTimesOperation() {
        List<Map.Entry<Operation, Float>> list = ascendingOrderOperationTimes();
        float totalTime = sumTotalTime();

        System.out.printf("%n%s====================================================%s%n", ANSI_BRIGHT_BLACK, ANSI_RESET);
        System.out.printf("%s%s%s%s %-20s %6s%15s%s%s%3s%s%n",
                ANSI_BRIGHT_BLACK, "||", ANSI_RESET,
                ANSI_BRIGHT_WHITE,
                "Operation",
                " Time(sec)",
                "Percentages", ANSI_RESET,
                ANSI_BRIGHT_BLACK, "||", ANSI_RESET);
        System.out.printf("%s====================================================%s%n", ANSI_BRIGHT_BLACK, ANSI_RESET);

        for (Map.Entry<Operation, Float> entry : list) {
            float percentage = (entry.getValue() / totalTime) * 100;
            System.out.printf("%s%s%s  %-22s %-11.2f %.2f %s%n",
                    ANSI_BRIGHT_BLACK, "||", ANSI_RESET,
                    entry.getKey().getOperationName(),
                    entry.getValue(),
                    percentage, "%");
        }

        System.out.printf("%s====================================================%s%n", ANSI_BRIGHT_BLACK, ANSI_RESET);
    }

    /**
     * Prints the execution times of each machine along with their percentages of total execution time.
     * It displays the data in a formatted table.
     */
    private void printExecutionTimesMachine() {
        List<Map.Entry<Machine, Float>> list = ascendingOrderMachineTimes();
        float totalTime = sumTotalTime();


        System.out.println("\n\n\n═══════════════════════════════════════════════");
        System.out.print(ANSI_BRIGHT_WHITE + "                  Statistics                 " + ANSI_RESET + "\n");
        System.out.println("═══════════════════════════════════════════════\n");


        System.out.printf("%n%s===============================================%s%n", ANSI_BRIGHT_BLACK, ANSI_RESET);
        System.out.printf("%s%s%s%s %-13s %6s %15s %s%s%3s%s%n",
                ANSI_BRIGHT_BLACK, "||", ANSI_RESET,
                ANSI_BRIGHT_WHITE,
                "Workstation",
                " Time(sec)",
                "Percentages", ANSI_RESET,
                ANSI_BRIGHT_BLACK, " ||", ANSI_RESET);
        System.out.printf("%s===============================================%s%n", ANSI_BRIGHT_BLACK, ANSI_RESET);

        for (Map.Entry<Machine, Float> entry : list) {
            float percentage = (entry.getValue() / totalTime) * 100;
            System.out.printf("%s%s%s  %-14s %-15.2f %.2f %s%n",
                    ANSI_BRIGHT_BLACK, "||", ANSI_RESET,
                    entry.getKey().getId_machine(),
                    entry.getValue(),
                    percentage, "%");
        }

        System.out.printf("%s===============================================%s%n", ANSI_BRIGHT_BLACK, ANSI_RESET);

    }

    /**
     * Returns a list where all operations are sorted by their time, in descending order.
     *
     * @return A list of entries (key-value pairs) where the key is an operation (Operation)
     * and the value is the operation time (Float), sorted in descending order of time.
     */
    private List<Map.Entry<Operation, Float>> ascendingOrderOperationTimes() {
        List<Map.Entry<Operation, Float>> list = new ArrayList<>(getExecutionTimesOperation().entrySet());
        list.sort(Map.Entry.comparingByValue(Comparator.reverseOrder()));
        return list;
    }

    /**
     * Returns a list where all machines are sorted by their execution time, in descending order.
     *
     * @return A list of entries (key-value pairs), where the key is the machine (Machine)
     * and the value is the execution time (Float), sorted in descending order of time.
     */
    private List<Map.Entry<Machine, Float>> ascendingOrderMachineTimes() {
        List<Map.Entry<Machine, Float>> list = new ArrayList<>(getExecutionTimesMachine().entrySet());
        list.sort(Map.Entry.comparingByValue(Comparator.reverseOrder()));
        return list;
    }

    /**
     * Sorts the average execution time entries in descending order.
     *
     * @return A list of entries sorted by their average execution time in descending order.
     */
    List<Map.Entry<Operation, Float>> ascendingOrderAvgExecutionTime() {
        List<Map.Entry<Operation, Float>> list = new ArrayList<>(getAvgExecutionTime().entrySet());
        list.sort(Map.Entry.comparingByValue(Comparator.reverseOrder()));
        return list;
    }

    /**
     * Sorts the waiting time entries in descending order.
     *
     * @return A list of entries sorted by their waiting time in descending order.
     */
    List<Map.Entry<Item, Float>> ascendingOrderWaitingTime() {
        List<Map.Entry<Item, Float>> list = new ArrayList<>(getWaitingTime().entrySet());
        list.sort(Map.Entry.comparingByValue(Comparator.reverseOrder()));
        return list;
    }


    /**
     * @return the sum of all operation's times.
     */
    private float sumTotalTime() {
        float sum = 0;
        List<Map.Entry<Operation, Float>> list = ascendingOrderOperationTimes();
        for (Map.Entry<Operation, Float> entry : list) {
            sum += entry.getValue();
        }
        return sum;
    }


    /**
     * Retrieves the execution times for all operations.
     *
     * @return a map containing operations and their corresponding execution times.
     */
    public Map<Operation, Float> getExecutionTimesOperation() {
        return this.operationTime;
    }

    /**
     * Retrieves the map that contains the execution times for each machine.
     *
     * @return A map where the keys are Machine objects and the values are the execution times (in seconds) for each machine.
     */
    public Map<Machine, Float> getExecutionTimesMachine() {
        return this.machineUsage;
    }

    /**
     * Retrieves the map containing the average execution times for each operation.
     *
     * @return A map where the keys are Operations and the values are their average execution times in seconds.
     */
    public Map<Operation, Float> getAvgExecutionTime() {
        return this.avgExecutionTime;
    }

    /**
     * Retrieves the map containing the waiting times for each item.
     *
     * @return A map where the keys are Items and the values are their waiting times in seconds.
     */
    public Map<Item, Float> getWaitingTime() {
        return this.waitingTime;
    }

    /**
     * Retrieves the map containing the number of executions for each operation.
     *
     * @return A map where the keys are Operations and the values are their execution counts.
     */
    public Map<Operation, Integer> getExecutionPerOperation() {
        return executionPerOperation;
    }

    /**
     * Retrieves the map containing the total execution time for each operation.
     *
     * @return A map where the keys are Operations and the values are their total execution times in seconds.
     */
    public Map<Operation, Float> getPpk() {
        return operationTime;
    }

    /**
     * Prints the route of a item
     */
    public void printItemRoute() {
        System.out.printf("%n%s===============================================%s%n", ANSI_BRIGHT_BLACK, ANSI_RESET);
        System.out.printf("%s%s%s%s %-13s %17s %s%s%3s%n",
                ANSI_BRIGHT_BLACK, "||", ANSI_RESET,
                ANSI_BRIGHT_WHITE,
                "Item",
                "Item Route"
                , ANSI_RESET,
                ANSI_BRIGHT_BLACK, ANSI_RESET);
        System.out.printf("%s===============================================%s%n", ANSI_BRIGHT_BLACK, ANSI_RESET);
        for (Map.Entry<Item, LinkedList<ID>> entry : itemLinkedListMap.entrySet()) {
            for (Item it : itemLinkedList) {
                if (entry.getKey().equals(it)) {
                    System.out.printf("%s%s%s  %-20s %s %n", ANSI_BRIGHT_BLACK, "||", ANSI_RESET, entry.getKey().getItemID(), entry.getValue());
                }
            }
        }
        System.out.printf("%n%s===============================================%s%n", ANSI_BRIGHT_BLACK, ANSI_RESET);
    }

    /**
     * Populates the executionPerOperation map with the number of executions for each operation.
     * If the operation is already present, increments its count by 1.
     *
     * @param operation The operation whose execution count is being tracked.
     */
    void fillExecutionPerOperation(Operation operation) {
        if (!executionPerOperation.containsKey(operation)) {
            executionPerOperation.put(operation, 1);
        } else {
            executionPerOperation.put(operation, executionPerOperation.get(operation) + 1);
        }
    }

    /**
     * Calculates the average execution time for each operation and stores it in avgExecutionTime.
     * Divides the total execution time of each operation by its number of executions.
     */
    void calculateAverageExecutionTimes() {
        float totalTime;
        int qtdOperations;

        for (Map.Entry<Operation, Float> entry : operationTime.entrySet()) {
            Operation operation = entry.getKey();
            totalTime = entry.getValue();
            qtdOperations = executionPerOperation.get(operation);

            avgExecutionTime.put(operation, (totalTime / qtdOperations));
        }
    }


    /**
     * Prints the average execution time for each operation in a formatted output.
     * Sorts the list of operations by their average execution time before printing.
     */
    private void printAverageExecutionTime() {
        calculateAverageExecutionTimes();
        List<Map.Entry<Operation, Float>> sortedListAvgExecutionTime = ascendingOrderAvgExecutionTime();

        System.out.printf("%n%s===============================================%s%n", ANSI_BRIGHT_BLACK, ANSI_RESET);
        System.out.printf("%s%s%s%s %-13s %26s %s%s%3s%s%n",
                ANSI_BRIGHT_BLACK, "||", ANSI_RESET,
                ANSI_BRIGHT_WHITE,
                "Operation",
                "Average Time (sec)"
                , ANSI_RESET,
                ANSI_BRIGHT_BLACK, "||", ANSI_RESET);
        System.out.printf("%s===============================================%s%n", ANSI_BRIGHT_BLACK, ANSI_RESET);

        for (Map.Entry<Operation, Float> entry : sortedListAvgExecutionTime) {
            System.out.printf("%s%s%s  %-25s %.2f %n",
                    ANSI_BRIGHT_BLACK, "||", ANSI_RESET,
                    entry.getKey().getOperationName(),
                    entry.getValue());
            timesPerOperation.put(entry.getKey().getOperationId(), entry.getValue());
        }

        System.out.printf("%s===============================================%s%n", ANSI_BRIGHT_BLACK, ANSI_RESET);
    }

    public Map<ID,Float> getAvgTimesOp(){
        return timesPerOperation;
    }
    /**
     * Fills the waitingTime map with the waiting time of each item in the operation queue list.
     * If an item is already present, increments its waiting time by 1 second.
     *
     * <p>
     * This method iterates through each operation queue provided in the operationQueueMap.
     * For each item in the queue, it checks if the item is already recorded in the waitingTime map.
     * If the item is not present, it adds it with a waiting time of 1 second. If the item is already present,
     * its waiting time is incremented by 1 second to reflect the additional wait.
     * </p>
     *
     * @param operationQueueMap a map of operations to their corresponding operation queues.
     *                          Each queue contains items that are currently waiting to be processed.
     */
    void fillWaitingTime(Map<Operation, OperationQueue> operationQueueMap) {
        for (OperationQueue operationQueue : operationQueueMap.values()) {
            if (!operationQueue.isEmpty()) {
                Queue<Item> items = operationQueue.getItemList();

                for (Item item : items) {
                    if (!waitingTime.containsKey(item)) {
                        waitingTime.put(item, 1f);
                    } else {
                        waitingTime.put(item, waitingTime.get(item) + 1f);
                    }
                }
            }
        }
    }

    /**
     * Prints the waiting time for each item in a formatted output.
     * Sorts the list of items by their waiting time before printing.
     */
    private void printWaitingTime() {
        List<Map.Entry<Item, Float>> sortedListWaitingTime = ascendingOrderWaitingTime();

        System.out.printf("%n%s===============================================%s%n", ANSI_BRIGHT_BLACK, ANSI_RESET);
        System.out.printf("%s%s%s%s %-13s %26s %s%s%3s%s%n",
                ANSI_BRIGHT_BLACK, "||", ANSI_RESET,
                ANSI_BRIGHT_WHITE,
                "Item",
                "Waiting Time (sec)"
                , ANSI_RESET,
                ANSI_BRIGHT_BLACK, "||", ANSI_RESET);
        System.out.printf("%s===============================================%s%n", ANSI_BRIGHT_BLACK, ANSI_RESET);

        for (Map.Entry<Item, Float> entry : sortedListWaitingTime) {
            System.out.printf("%s%s%s  %-14s %17.2f %n",
                    ANSI_BRIGHT_BLACK, "||", ANSI_RESET,
                    entry.getKey().getItemID(),
                    entry.getValue());
        }

        System.out.printf("%s===============================================%s%n", ANSI_BRIGHT_BLACK, ANSI_RESET);
    }

    /**
     * Prints the transitions of a machine to another.
     */
    public void printMachineRoute() {

        Map<ID, Map<ID, Integer>> transitionMap = getMachineRoute();

        System.out.printf("%n%s===============================================%s%n", ANSI_BRIGHT_BLACK, ANSI_RESET);
        System.out.printf("%s%s%s%s %-13s %17s %s%s%3s%n",
                ANSI_BRIGHT_BLACK, "||", ANSI_RESET,
                ANSI_BRIGHT_WHITE,
                "Machine ID",
                "Machine Route"
                , ANSI_RESET,
                ANSI_BRIGHT_BLACK, ANSI_RESET);
        System.out.printf("%s===============================================%s%n", ANSI_BRIGHT_BLACK, ANSI_RESET);
        for (Map.Entry<ID, Map<ID, Integer>> transitionEntry : transitionMap.entrySet()) {
            ID fromMachine = transitionEntry.getKey();
            Map<ID, Integer> toMachines = transitionEntry.getValue();

            List<String> transitionStrings = new ArrayList<>();
            for (Map.Entry<ID, Integer> toMachineEntry : toMachines.entrySet()) {
                ID toMachine = toMachineEntry.getKey();
                int count = toMachineEntry.getValue();
                transitionStrings.add("(" + toMachine.getKeyID() + "," + count + ")");
            }
            System.out.printf("%s%s%s  %-17s %s %n", ANSI_BRIGHT_BLACK, "||", ANSI_RESET, fromMachine.getKeyID(), String.join(", ", transitionStrings));
        }
        System.out.printf("%s===============================================%s%n", ANSI_BRIGHT_BLACK, ANSI_RESET);
    }

    /**
     * @return a Map that stores all the transitions of the machines
     */
    public Map<ID, Map<ID, Integer>> getMachineRoute() {
        Map<ID, Map<ID, Integer>> transitionMap = new HashMap<>();

        for (Map.Entry<Item, LinkedList<ID>> entry : itemLinkedListMap.entrySet()) {
            LinkedList<ID> machineIds = entry.getValue();

            for (int i = 0; i < machineIds.size() - 1; i++) {
                ID fromMachine = machineIds.get(i);
                ID toMachine = machineIds.get(i + 1);

                transitionMap.putIfAbsent(fromMachine, new HashMap<>());

                Map<ID, Integer> toMachineCount = transitionMap.get(fromMachine);

                toMachineCount.put(toMachine, toMachineCount.getOrDefault(toMachine, 0) + 1);
            }
        }
        return transitionMap;
    }

    /**
     *
     * @return a boolean indicating if the simulator was activated
     */
    public boolean wasActivated() {
        return wasActivated;
    }
}
