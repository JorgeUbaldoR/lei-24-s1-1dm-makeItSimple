# USEI02 - Implement a simulator that processes all the items.

## 4. Tests 

**Test 1:** Check that it is not possible to create an instance of the Task class with null values. 

	@Test(expected = IllegalArgumentException.class)
		public void ensureNullIsNotAllowed() {
		Task instance = new Task(null, null, null, null, null, null, null);
	}
	

**Test 2:** Check that it is not possible to create an instance of the Task class with a reference containing less than five chars - AC2. 

	@Test(expected = IllegalArgumentException.class)
		public void ensureReferenceMeetsAC2() {
		Category cat = new Category(10, "Category 10");
		
		Task instance = new Task("Ab1", "Task Description", "Informal Data", "Technical Data", 3, 3780, cat);
	}

_It is also recommended to organize this content by subsections._ 


## 5. Construction (Implementation)

### Class SimulatorController

###### 1. Constructor
```java
 public SimulatorController() {
    getItemRepository();
    getMachineRepository();
    getOperationRepository();
}
```
###### 2.1. Get Item Repository

```java
private ItemRepository getItemRepository() {
    if(itemRepository == null) {
        Repositories repositories = Repositories.getInstance();
        itemRepository = repositories.getItemRepository();
    }
    return itemRepository;

}
```
###### 2.2. Get Machine Repository

```java
    private MachineRepository getMachineRepository() {
        if(machineRepository == null) {
            Repositories repositories = Repositories.getInstance();
            machineRepository = repositories.getMachineRepository();
        }
        return machineRepository;
}
```
###### 2.3. Get Operation Repository

```java
    private OperationRepository getOperationRepository() {
        if(operationRepository == null) {
            Repositories repositories = Repositories.getInstance();
            operationRepository = repositories.getOperationRepository();
        }
        return operationRepository;
}
```
###### 3. Start Simulation WithOut Priority

```java
    public void startSimulationWithOutPriority(){
        this.simulator = new Simulator(getMachinesMap(), getItemList(),getOperationList(),false);
        this.simulator.startSimulation();
}
```
###### 4. Get Machines Map

```java
    public Map<Operation, Queue<Machine>> getMachinesMap() {
        Map<Operation, Queue<Machine>> machinesMap = new HashMap<>();
        for (Machine machine : getMachineRepository().getMachineList()) {
            if(!machinesMap.containsKey(machine.getOperation())) {
                machinesMap.put(machine.getOperation(), new PriorityQueue<>());
                machinesMap.get(machine.getOperation()).add(machine);
            }else {
                machinesMap.get(machine.getOperation()).add(machine);
            }
        }
        return machinesMap;
}
```
###### 5. Get Item List

```java
    public List<Item> getItemList(){
        return getItemRepository().getItemList();
    }
```
###### 6. Get Operation List

```java
    public List<Operation> getOperationList(){
        return getOperationRepository().getOperations();
}
```

### Class Simulator

```java
    public Simulator(Map<Operation, Queue<Machine>> machines, List<Item> items, List<Operation> operations, boolean priorityFlag) {
        checkInformation(machines, operations, items);
        this.machineList = machines;
        this.operationQueueList = new ArrayList<>();
        this.operationTime = new HashMap<>();
        this.waitingTime = new HashMap<>();
        this.itemLinkedList = new LinkedList<>();
        this.itemLinkedListMap = new HashMap<>();
        this.machineUsage = new HashMap<>();
        this.avgExecutionTime = new HashMap<>();
        this.executionPerOperation = new HashMap<>();
        addOperationToQueue(operations, priorityFlag);
        createQueues(items);
    }
```
```java
    private void addOperationToQueue(List<Operation> operations, boolean priorityFlag) {
        for (Operation operation : operations) {
            operationQueueList.add(new OperationQueue(operation, priorityFlag));
        }
    }
```
```java
    private void createQueues(List<Item> items) {
        for (OperationQueue operationQueue : operationQueueList) {
            Operation currentOperation = operationQueue.getOperation();
            for (Item item : items) {
                if (currentOperation.equals(item.getCurrentOperation())) {
                    operationQueue.addItemToQueue(item);
                }
            }
        }
    }
```

```java
 public void startSimulation() {
        int time = 0;
        while (checkOperationQueue() || checkTimeOperations()) {
            System.out.printf("%s===========================================================%s%n", ANSI_BRIGHT_BLACK, ANSI_RESET);
            System.out.printf("%s||%s              %sSIMULATION - TIME: %d%s                     %s||%s%n", ANSI_BRIGHT_BLACK, ANSI_RESET, ANSI_BRIGHT_WHITE, time, ANSI_RESET, ANSI_BRIGHT_BLACK, ANSI_RESET);
            System.out.printf("%s===========================================================%s%n", ANSI_BRIGHT_BLACK, ANSI_RESET);
            System.out.printf("%n• Updates:%n");
            updateMachines();
            System.out.printf("%n• Queue:%n");
            printQueue();
            System.out.printf("%n• Status:%n");
            printMachineStatus();

            System.out.printf("%n• New Processing:%n");
            for (OperationQueue operationQueue : operationQueueList) {
                if (!operationQueue.isEmpty()) {
                    assignItemToMachine(operationQueue, machineList.get(operationQueue.getOperation()));
                }
            }

            if (time > 0) {
                fillWaitingTime(operationQueueList);
            }

            System.out.printf("%n%s===========================================================%s%n%n%n", ANSI_BRIGHT_BLACK, ANSI_RESET);
            time++;
        }
        System.out.printf("%s✅ All operations completed! %s%n", ANSI_GREEN, ANSI_RESET);
        printStatistics();

    }
```
```java
    private void assignItemToMachine(OperationQueue operationQueue, Queue<Machine> machineList) {
        if (machineList != null && !machineList.isEmpty()) {
            for (Machine currentMachine : machineList) {
                if (currentMachine.isAvailable() && !operationQueue.isEmpty()) {
                    currentMachine.processItem(operationQueue.getNextItem());
                }
            }
        } else {
            operationQueue.getNextItem();
        }
    }
```

```java
  private void updateMachines() {
        for (Operation operation : machineList.keySet()) {
            Queue<Machine> machines = machineList.get(operation);
            for (Machine machine : machines) {
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
```
```java
    private OperationQueue findOperationInQueue(Operation newOperation) {
        for (OperationQueue operationQueue : operationQueueList) {
            if (operationQueue.getOperation().equals(newOperation)) {
                return operationQueue;
            }
        }
        return null;
    }
```
```java
    private void printMachineStatus() {
        for (Operation operation : machineList.keySet()) {
            for (Machine machine : machineList.get(operation)) {
                machine.printStatus();
            }
        }
    }
```

```java
    private boolean checkOperationQueue() {
        for (OperationQueue operationQueue : operationQueueList) {
            if (!operationQueue.isEmpty()) {
                return true;
            }
        }
        return false;
    }
```

```java 
    private boolean checkTimeOperations() {
        for (Queue<Machine> machines : machineList.values()) {
            for (Machine machine : machines) {
                if (machine.getTimeLeftToFinish() != 0)
                    return true;
            }
        }
        return false;
    }
``` 