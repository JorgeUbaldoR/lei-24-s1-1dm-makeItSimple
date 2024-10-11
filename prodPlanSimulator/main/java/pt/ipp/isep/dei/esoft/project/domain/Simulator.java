package pt.ipp.isep.dei.esoft.project.domain;

import pt.ipp.isep.dei.esoft.project.domain.Operation;

import java.util.*;

public class Simulator {
    /**
     * A Simulator class simulates a manufacturing process where items are processed
     * by machines according to specified operations.
     */
    private final Map<Operation, Queue<Machine>> machineList;
    private final List<OperationQueue> operationQueueList;


    /**
     * Constructs a Simulator instance with the provided machines, items, and operations.
     *
     * @param machines a map of operations to queues of machines available for processing.
     * @param items a list of items to be processed in the simulation.
     * @param operations a list of operations to be executed during the simulation.
     * @throws IllegalArgumentException if any of the provided lists are null or empty.
     */
    public Simulator(Map<Operation, Queue<Machine>> machines, List<Item> items, List<Operation> operations) {
        checkInformation(machines, operations, items);
        this.machineList = machines;
        this.operationQueueList = new ArrayList<>();
        addOperationToQueue(operations);
        createQueues(items);
    }


    /**
     * Adds a list of operations to the operation queue list, creating
     * an OperationQueue for each operation.
     *
     * @param operations the list of operations to be added to the operation queue.
     */
    private void addOperationToQueue(List<Operation> operations) {
        for (Operation operation : operations) {
            operationQueueList.add(new OperationQueue(operation));
        }
    }


    /**
     * Creates operation queues for the given items based on their current operation.
     *
     * @param items a list of items to be added to the operation queues.
     * @return a list of operation queues populated with items.
     */
    public List<OperationQueue> createQueues(List<Item> items) {
        for (OperationQueue operationQueue : operationQueueList) {
            Operation currentOperation = operationQueue.getOperation();
            for (Item item : items) {
                if (currentOperation.equals(item.getCurrentOperation())) {
                    operationQueue.addItemToQueue(item);
                }
            }
        }
        return operationQueueList;
    }


    /**
     * Starts the simulation process, incrementing time and processing items
     * until all operations are completed.
     */
    public void startSimulation() {
        int time = 0;
        while (checkOperationQueue() || checkTimeOperations()) {
            System.out.println("---------------------------------");
            System.out.println("| INICIO SIMULAÇÃO - Tempo: " + time + "   |");
            System.out.println("---------------------------------");
            for (OperationQueue operationQueue : operationQueueList) {
                if (!operationQueue.isEmpty())
                    assignItemToMachine(operationQueue, machineList.get(operationQueue.getOperation()));
            }
            updateMachines();
            printMachineStatus();
            System.out.println("---------------------------------");
            time++;
            sleep(1000);
        }
        System.out.println("✅ All operations completed!");
    }


    /**
     * Assigns the next item from the operation queue to an available machine.
     *
     * @param operationQueue the operation queue from which the item is retrieved.
     * @param machineList the queue of machines available for processing the item.
     */
    private void assignItemToMachine(OperationQueue operationQueue, Queue<Machine> machineList) {
        if (!machineList.isEmpty()) {
            for (Machine currentMachine : machineList) {
                if (currentMachine.isAvailable() && !operationQueue.isEmpty()) {
                    currentMachine.processItem(operationQueue.getNextItem());
                }
            }
        }
    }


    /**
     * Updates the status of the machines and assigns the next operation
     * for any items that have finished processing.
     */
    private void updateMachines() {
        for (Operation operation : machineList.keySet()) {
            for (Machine machine : machineList.get(operation)) {
                boolean finished = machine.updateMachine();
                if (finished) {
                    Item currentItem = machine.getCurrentProcessingItem();
                    Operation newOperation = currentItem.getNextOperation();
                    if (newOperation != null) {
                        OperationQueue operationQueue = findOperationInQueue(newOperation);
                        if (operationQueue != null) {
                            operationQueue.addItemToQueue(currentItem);
                        }
                    }
                }
            }
        }
    }

    /**
     * Finds the operation queue corresponding to the given operation.
     *
     * @param newOperation the operation to search for.
     * @return the operation queue associated with the operation, or null if not found.
     */
    private OperationQueue findOperationInQueue(Operation newOperation) {
        for (OperationQueue operationQueue : operationQueueList) {
            if (operationQueue.getOperation().equals(newOperation)) {
                return operationQueue;
            }
        }
        return null;
    }

    /**
     * Prints the status of all machines in the simulator.
     */
    private void printMachineStatus() {
        for (Operation operation : machineList.keySet()) {
            for (Machine machine : machineList.get(operation)) {
                machine.printStatus();
            }
        }
    }

    /**
     * Checks if there are any items left in the operation queues.
     *
     * @return true if there are items in any of the operation queues; false otherwise.
     */
    private boolean checkOperationQueue() {
        for (OperationQueue operationQueue : operationQueueList) {
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
        List<Machine> machines = new ArrayList<>();
        for (Queue<Machine> machine : machineList.values()) {
            machines.addAll(machine);
        }
        for (Machine machine : machines) {
            if(machine.getTimeLeftToFinish() != 0)
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
     * @param machines the map of machines to check.
     * @param operations the list of operations to check.
     * @param items the list of items to check.
     * @throws IllegalArgumentException if any of the lists are null or empty.
     */
    private void checkInformation(Map<Operation, Queue<Machine>> machines, List<Operation> operations, List<Item> items) {
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

}
