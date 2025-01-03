# US006 - Average Execution Times and Waiting Times

## 4. Tests 

**Test 1:** Verify that the fillExecutionPerOperation method correctly adds a new operation with a count of 1 if the operation is not present, and increments the count if it is already present. 

	@Test
    void testFillExecutionPerOperation() {
        System.out.println("Testing Fill Execution Per Operation");
        simulator = new Simulator(machineListMap,itemList,operationList,false);
        Operation operation = new Operation("Op1");


        simulator.fillExecutionPerOperation(operation);
        assertEquals(1, simulator.getExecutionPerOperation().get(operation).intValue());

        simulator.fillExecutionPerOperation(operation);
        assertEquals(2, simulator.getExecutionPerOperation().get(operation).intValue());
    }
	

**Test 2:**  Verify that the calculateAverageExecutionTimes method calculates the average execution time correctly by dividing the total time by the number of executions.

	@Test
    void testCalculateAverageExecutionTimes() {
        System.out.println("Testing Calculate Average Execution Times");
        simulator = new Simulator(machineListMap,itemList,operationList,false);
        Operation operation = new Operation("Op1");

        simulator.getPpk().put(operation, 10f);
        simulator.getExecutionPerOperation().put(operation, 2);

        simulator.calculateAverageExecutionTimes();

        assertEquals(5f, simulator.getAvgExecutionTime().get(operation), 0.001);
    }

**Test 3:** Verify that the fillWaitingTime method correctly initializes waiting time for items in a queue and increments the time if the method is called again with the same items.

    @Test
    void testFillWaitingTime() {
        System.out.println("Testing Fill Waiting Time");
        simulator = new Simulator(machineListMap,itemList,operationList,false);

        operationQueue.addItem(itemList.get(1));
        List<OperationQueue> queues = Collections.singletonList(operationQueue);

        simulator.fillWaitingTime(queues);

        assertEquals(1f, simulator.getWaitingTime().get(itemList.get(0)), 0.001);
        assertEquals(1f, simulator.getWaitingTime().get(itemList.get(1)), 0.001);

        simulator.fillWaitingTime(queues);

        assertEquals(2f, simulator.getWaitingTime().get(itemList.get(0)), 0.001);
        assertEquals(2f, simulator.getWaitingTime().get(itemList.get(1)), 0.001);
    }

**Test 4:** Verify that the ascendingOrderAvgExecutionTime method returns a list of operations sorted by average execution time in descending order.

    @Test
    void testAscendingOrderAvgExecutionTime() {
        System.out.println("Testing Ascending Order Avg Execution Time");
        simulator = new Simulator(machineListMap,itemList,operationList,false);

        simulator.getAvgExecutionTime().put(operationList.get(0), 5f);
        simulator.getAvgExecutionTime().put(operationList.get(1), 10f);

        List<Map.Entry<Operation, Float>> result = simulator.ascendingOrderAvgExecutionTime();
        assertEquals(operationList.get(1), result.get(0).getKey());
        assertEquals(operationList.get(0), result.get(1).getKey());
    }

**Test 5:** Verify that the ascendingOrderWaitingTime method returns a list of items sorted by waiting time in descending order.

    @Test
    void testAscendingOrderWaitingTime() {
        System.out.println("Testing Ascending Order Waiting Time");
        simulator = new Simulator(machineListMap,itemList,operationList,false);

        simulator.getWaitingTime().put(itemList.get(0), 2f);
        simulator.getWaitingTime().put(itemList.get(1), 4f);

        List<Map.Entry<Item, Float>> result = simulator.ascendingOrderWaitingTime();
        assertEquals(itemList.get(1), result.get(0).getKey());
        assertEquals(itemList.get(0), result.get(1).getKey());
    }



## 5. Construction (Implementation)

### Class Simulator

```java
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
```

```java
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

```

```java

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
            "Average Time (min)"
            , ANSI_RESET,
            ANSI_BRIGHT_BLACK, "||", ANSI_RESET);
    System.out.printf("%s===============================================%s%n", ANSI_BRIGHT_BLACK, ANSI_RESET);

    for (Map.Entry<Operation, Float> entry : sortedListAvgExecutionTime) {
        System.out.printf("%s%s%s  %-14s %17.2f %n",
                ANSI_BRIGHT_BLACK, "||", ANSI_RESET,
                entry.getKey().getOperationName(),
                entry.getValue());
    }

    System.out.printf("%s===============================================%s%n", ANSI_BRIGHT_BLACK, ANSI_RESET);
}

```

```java

/**
     * Fills the waitingTime map with the waiting time of each item in the operation queue list.
     * If an item is already present, increments its waiting time by 1 minute.
     *
     * @param operationQueueList The list of operation queues to process for waiting time.
     */
    void fillWaitingTime(List<OperationQueue> operationQueueList) {
        for (OperationQueue operationQueue : operationQueueList) {
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

```

```java

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
                "Waiting Time (min)"
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

```

```java
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

```



## 6. Integration and Demo 

* For demo purposes some tasks are bootstrapped while system starts.


## 7. Observations

n/a