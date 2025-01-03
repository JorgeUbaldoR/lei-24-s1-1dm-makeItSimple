# USEI15 - Put components into simulator

## 4. Tests

## ItemLevelProcessorTest Class

### **Test : testAssociateItemsWithTree**

```java

@Test
void testAssociateItemsWithTree() {
    TreeMap<Integer, Queue<Map<Item, Float>>> tree = itemLevelProcessor.getTree();


    assertNotNull(tree, "The tree should not be null.");
    assertFalse(tree.isEmpty(), "The tree should not be empty.");

    assertTrue(tree.containsKey(1), "Tree should contain height 1.");
    assertEquals(6, tree.size(), "Tree should have have height 6.");
    assertEquals(1, tree.get(1).size(), "Height 1 should have 1 queue.");

    Map<Item, Float> itemsAtHeight = tree.get(1).peek();
    assertNotNull(itemsAtHeight, "Items at height should not be null.");
    assertEquals(1.0f, itemsAtHeight.values().iterator().next(), "Item quantity should match the test data.");
}
```

**Objective:**  To verify that the associateItemsWithTree method correctly populates a TreeMap with items, ensuring
hierarchical structure, and proper association between heights and items.

**Validations:**

1. Ensure the tree is not null and is populated (!empty).
2. Verify that the tree contains the expected heights (e.g., height 1).
3. Confirm the size of the tree matches the expected number of heights (6 in this case).
4. Check the size and content of the queue at a specific height, ensuring proper mapping of items and their attributes.

**Expected Result:** The tree is correctly populated, contains expected heights, and the attributes of items (e.g.,
quantity) are valid and match the input data.


---

## SimulatorTest Class

### **Test 1: Test Start Simulation**

```java

@Test
void testStartSimulation() {
    System.out.println("Testing Start Simulation");
    simulator = new Simulator(machineListMap, itemList, operationList, (ArrayList<Machine>) machineList, false);
    simulator.startSimulation();
}
```

**Objective:** To verify that the startSimulation method initializes the simulation without exceptions and performs all
intended operations such as processing items and routes.

**Validations:**

1. Ensure the simulation completes without errors.
2. Verify that all key components (machines, items, operations) are processed.

**Expected Result:** Simulation starts successfully, and all necessary components are processed.

---

### **Test 2: Get Machine Route test**

```java

@Test
void getMachineRouteTest() {
    simulator = new Simulator(machineListMap, itemList, operationList, (ArrayList<Machine>) machineList, false);
    simulator.startSimulation();
    simulator.printMachineRoute();

    Map<ID, Map<ID, Integer>> transitionMap = simulator.getMachineRoute();

    assertTrue(transitionMap.containsKey(new ID(10, TypeID.MACHINE)));
    assertEquals(1, (int) transitionMap.get(new ID(10, TypeID.MACHINE)).get(new ID(11, TypeID.MACHINE)));

    assertTrue(transitionMap.containsKey(new ID(11, TypeID.MACHINE)));
    assertEquals(1, (int) transitionMap.get(new ID(11, TypeID.MACHINE)).get(new ID(12, TypeID.MACHINE)));

    assertTrue(transitionMap.containsKey(new ID(12, TypeID.MACHINE)));
    assertEquals(1, (int) transitionMap.get(new ID(12, TypeID.MACHINE)).get(new ID(11, TypeID.MACHINE)));
}
```

**Objective:** To verify the correctness of machine routing data after simulation.

**Validations:**

1. Confirm that the transition map contains expected machine IDs.
2. Check the correctness of transition counts between machines.

**Expected Result:** The transition map accurately reflects the expected routes and counts between machines after the simulation.

---

### **Test 3: Test fill execution per operation**

```java

@Test
void testFillExecutionPerOperation() {
    System.out.println("Testing Fill Execution Per Operation");
    simulator = new Simulator(machineListMap, itemList, operationList, (ArrayList<Machine>) machineList, false);
    Operation operation = new Operation("Op1");


    simulator.fillExecutionPerOperation(operation);
    assertEquals(1, simulator.getExecutionPerOperation().get(operation).intValue());

    simulator.fillExecutionPerOperation(operation);
    assertEquals(2, simulator.getExecutionPerOperation().get(operation).intValue());
}
```

**Objective:** To validate that fillExecutionPerOperation correctly increments execution counts for operations.

**Validations:**

1. Verify that the count for an operation is updated correctly after each call.
2. Check consistency in the updated counts.

**Expected Result:** The execution count for a given operation is incremented accurately after each call.

---

### **Test 4: Test calculate average exec times**

```java

@Test
void testCalculateAverageExecutionTimes() {
    System.out.println("Testing Calculate Average Execution Times");
    simulator = new Simulator(machineListMap, itemList, operationList, (ArrayList<Machine>) machineList, false);
    Operation operation = new Operation("Op1");

    simulator.getPpk().put(operation, 10f);
    simulator.getExecutionPerOperation().put(operation, 2);

    simulator.calculateAverageExecutionTimes();

    assertEquals(5f, simulator.getAvgExecutionTime().get(operation), 0.001);
}
```

**Objective:** To confirm the accuracy of average execution time calculations.


**Validations:**

1. Verify that the average execution time is calculated correctly based on total execution time and the number of executions.
2. Ensure the values are stored in the correct data structure.

**Expected Result:**

---

### **Test 5: Test fill waiting time**

```java

@Test
void testFillWaitingTime() {
    System.out.println("Testing Fill Waiting Time");
    simulator = new Simulator(machineListMap, itemList, operationList, (ArrayList<Machine>) machineList, false);

    Map.Entry<Integer, Queue<Map<Item, Float>>> firstEntry = itemList.firstEntry();
    Map.Entry<Integer, Queue<Map<Item, Float>>> secondEntry = itemList.higherEntry(firstEntry.getKey());

    Item firstItem = firstEntry.getValue().peek().keySet().iterator().next();
    Item secondItem = secondEntry.getValue().peek().keySet().iterator().next();

    operationQueue.addItem(secondItem);

    Map<Operation, OperationQueue> operationQueueMap = new HashMap<>();
    operationQueueMap.put(operationQueue.getOperation(), operationQueue);

    simulator.fillWaitingTime(operationQueueMap);

    assertEquals(1f, simulator.getWaitingTime().get(firstItem), 0.001);
    assertEquals(1f, simulator.getWaitingTime().get(secondItem), 0.001);

    simulator.fillWaitingTime(operationQueueMap);

    assertEquals(2f, simulator.getWaitingTime().get(firstItem), 0.001);
    assertEquals(2f, simulator.getWaitingTime().get(secondItem), 0.001);
}
```

**Objective:** To validate the proper accumulation of waiting times for items.


**Validations:**

1. Verify that the waiting time for items is updated correctly after each call.
2. Check that the waiting time values are consistent.

**Expected Result:** The waiting time for items is accumulated accurately after each invocation.

---

### **Test 6: Test fill waiting time**

```java

@Test
void testAscendingOrderAvgExecutionTime() {
    System.out.println("Testing Ascending Order Avg Execution Time");
    simulator = new Simulator(machineListMap, itemList, operationList, (ArrayList<Machine>) machineList, false);

    simulator.getAvgExecutionTime().put(operationList.get(0), 5f);
    simulator.getAvgExecutionTime().put(operationList.get(1), 10f);

    List<Map.Entry<Operation, Float>> result = simulator.ascendingOrderAvgExecutionTime();
    assertEquals(operationList.get(1), result.get(0).getKey());
    assertEquals(operationList.get(0), result.get(1).getKey());
}
```

**Objective:** To ensure that operations are sorted correctly based on average execution times in ascending order.

**Validations:**

1. Verify that the returned list is sorted correctly.
2. Ensure the order corresponds to the average execution time values.

**Expected Result:**

---

### **Test 7: Test ascending order waiting time**

```java

@Test
void testAscendingOrderWaitingTime() {
    System.out.println("Testing Ascending Order Waiting Time");
    simulator = new Simulator(machineListMap, itemList, operationList, (ArrayList<Machine>) machineList, false);

    Map.Entry<Integer, Queue<Map<Item, Float>>> firstEntry = itemList.firstEntry();
    Map.Entry<Integer, Queue<Map<Item, Float>>> secondEntry = itemList.higherEntry(firstEntry.getKey());

    Item firstItem = firstEntry.getValue().peek().keySet().iterator().next();
    Item secondItem = secondEntry.getValue().peek().keySet().iterator().next();

    simulator.getWaitingTime().put(firstItem, 2f);
    simulator.getWaitingTime().put(secondItem, 4f);

    List<Map.Entry<Item, Float>> result = simulator.ascendingOrderWaitingTime();

    assertEquals(secondItem, result.get(0).getKey());
    assertEquals(firstItem, result.get(1).getKey());
}
```

**Objective:**

**Validations:**

1. To validate that items are sorted correctly based on waiting times in ascending order.
2. Ensure the order corresponds to the waiting time values.

**Expected Result:** The list of items is sorted in ascending order of waiting times.

---

### **Test 8: Test Print Execution Times Operation**

```java

@Test
public void testPrintExecutionTimesOperation() {
    simulator = new Simulator(machineListMap, itemList, operationList, (ArrayList<Machine>) machineList, false);
    simulator.startSimulation();
    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    System.setOut(new PrintStream(outputStream));
    simulator.printExecutionTimesOperation();
    String output = outputStream.toString();

    assertTrue(output.contains("Cutting"));
    assertTrue(output.contains("welding"));
    assertTrue(output.contains("Painting"));

    assertTrue(output.contains("55,56 %"));
    assertTrue(output.contains("33,33 %"));
    assertTrue(output.contains("11,11 %"));

    System.setOut(System.out);
}
```

**Objective:** To verify that execution times per operation are printed correctly, including their distribution as percentages.

**Validations:**

1. Ensure the output contains operation names (e.g., “Cutting”).
2. Check that the percentages match the expected distribution.

**Expected Result:** The execution times and percentages are printed correctly and match the test data.

---

### **Test 9: Test Print Machine Route**

```java

@Test
public void testPrintMachineRoute() {
    simulator = new Simulator(machineListMap, itemList, operationList, (ArrayList<Machine>) machineList, false);
    simulator.startSimulation();
    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    System.setOut(new PrintStream(outputStream));
    simulator.printMachineRoute();
    String printedOutput = outputStream.toString();

    assertTrue(printedOutput.contains("Machine ID"));
    assertTrue(printedOutput.contains("Machine Route"));

    assertTrue(printedOutput.contains("W-10"));
    assertTrue(printedOutput.contains("(W-11,1)"));
    assertTrue(printedOutput.contains("W-11"));
    assertTrue(printedOutput.contains("(W-12,1)"));

    System.setOut(System.out);
}
```

**Objective:** To confirm that machine routes are printed accurately after the simulation.

**Validations:**

1. Verify that the output contains machine IDs and their respective routes.
2. Check the correctness of route formatting and transitions.

**Expected Result:** The machine routes are printed correctly and match the expected routes.

---

### **Test 10: Test Print Item Route**

```java

@Test
public void testPrintItemRoute() {
    simulator = new Simulator(machineListMap, itemList, operationList, (ArrayList<Machine>) machineList, false);
    simulator.startSimulation();
    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    System.setOut(new PrintStream(outputStream));
    simulator.printItemRoute();
    String printedOutput = outputStream.toString();

    assertTrue(printedOutput.contains("Item"));
    assertTrue(printedOutput.contains("Item Route"));

    assertTrue(printedOutput.contains("I-10"));
    assertTrue(printedOutput.contains("[W-11, W-12]"));

    System.setOut(System.out);
}
```

**Objective:** To ensure that item routes are printed correctly after the simulation.

**Validations:**

1. Verify that the output contains item IDs and their respective routes.
2. Check the correctness of route formatting for each item.

**Expected Result:** The item routes are printed accurately and match the simulation data.

---

## 5. Construction (Implementation)


### Class ItemLevelProcessor

###### Constructor

```java
  /**
 * Constructor initializes the structure and associates items with the tree.
 */
public ItemLevelProcessor() {
    tree = new TreeMap<>(Collections.reverseOrder());
    associateItemsWithTree();

}
```

###### Initialize TreeMap

```java
    /**
 * Initializes a TreeMap containing IDs organized by priority levels (heights).
 *
 * @return A TreeMap with IDs associated with their priority levels.
 */
private TreeMap<Integer, List<ID>> initializeTreeMap() {
    TreeMap<Integer, List<ID>> treeMap = new TreeMap<>(Collections.reverseOrder());
    QualityChecks qc = new QualityChecks();
    PriorityQueue<Map<Integer, List<ID>>> pq = qc.fillOperationsPriorityQueue();

    while (!pq.isEmpty()) {
        Map<Integer, List<ID>> entry = pq.poll();
        for (Map.Entry<Integer, List<ID>> e : entry.entrySet()) {
            Integer priority = e.getKey();
            List<ID> ids = e.getValue();
            treeMap.computeIfAbsent(priority, k -> new ArrayList<>()).addAll(ids);
        }
    }
    return treeMap;
}
```

###### Associate Items with Tree

```java
/**
 * Associates items with the tree structure based on heights and IDs.
 */
public void associateItemsWithTree() {
    ItemController itemController = new ItemController();
    TreeMap<Integer, List<ID>> treeMap = initializeTreeMap();
    Map<ID, Item> itemList = itemController.getItemList();

    for (Map.Entry<Integer, List<ID>> entry : treeMap.entrySet()) {
        Map<Item, Float> associatedItems = new LinkedHashMap<>();
        Queue<Map<Item, Float>> itemQueue = new LinkedList<>();
        Integer height = entry.getKey();
        List<ID> idList = entry.getValue();

        for (ID id : idList) {
            for (Map.Entry<ID, Item> itemEntry : itemList.entrySet()) {
                Item item = itemEntry.getValue();
                if (item.hasOperationWithID(id)) {
                    float quantity = item.getQuantity();

                    if (!associatedItems.containsKey(item)) {
                        associatedItems.put(item, quantity);
                    }
                }
            }
        }

        itemQueue.add(new LinkedHashMap<>(associatedItems));
        tree.put(height, itemQueue);
    }
}
```

###### Get tree

```java
  /**
 * Getter for the tree structure.
 *
 * @return The TreeMap with items organized by height.
 */
public TreeMap<Integer, Queue<Map<Item, Float>>> getTree() {
    return tree;
}
```



### Class SimulatorController

###### Constructor

```java
 public SimulatorController() {
    getItemRepository();
    getMachineRepository();
    getOperationRepository();
}
```

###### Get Tree

```java
 public TreeMap<Integer, Queue<Map<Item, Float>>> getTree() {
    this.boo = new ItemLevelProcessor();
    return boo.getTree();
}
```


###### Start simulation
```java
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

```

### Class Simulator (only changes made)

###### Constructor
```java
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
```

###### Create queues
```java
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
```

###### Start simulation
```java
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
```

###### Was activated method
```java
   /**
 *
 * @return a boolean indicating if the simulator was activated
 */
public boolean wasActivated() {
    return wasActivated;
}
```



