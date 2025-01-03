
---

# USEI11 - Quality Checks

## 4. Tests

## QualityChecksTest class
### **Test 1: testFillOperationsPriorityQueue**

```java
 @Test
void testFillOperationsPriorityQueue() {
    PriorityQueue<Map<Integer, List<ID>>> priorityQueue = qualityChecks.fillOperationsPriorityQueue();

    assertNotNull(priorityQueue);
    assertFalse(priorityQueue.isEmpty());

    List<Integer> heights = new ArrayList<>();
    for (Map<Integer, List<ID>> map : priorityQueue) {
        heights.add(map.keySet().iterator().next());
    }
    List<Integer> sortedHeights = new ArrayList<>(heights);
    Collections.sort(sortedHeights);
    assertEquals(sortedHeights, heights);
}
```

**Objective:**  Test the method fillOperationsPriorityQueue() to ensure it correctly fills a priority queue with operations sorted by height.


**Validations:**
1. The priority queue must not be null.
2. The priority queue must not be empty.
3. The heights of the operations must be sorted in ascending order.

**Expected Result:** The priorityQueue is populated and sorted by the heights of operations in ascending order.


---

### **Test 2: testPrepareQualityChecks**

```java
@Test
void testPrepareQualityChecks() {
    Map<Map<Integer, List<ID>>, Boolean> checksMap = qualityChecks.prepareQualityChecks();
    assertNotNull(checksMap);
    assertFalse(checksMap.isEmpty());

    for (Boolean value : checksMap.values()) {
        assertFalse(value);
    }
}
```

**Objective:** Test the prepareQualityChecks() method to ensure it correctly prepares the quality checks map and initializes checks with a false value.



**Validations:**
1. The checksMap should not be null.
2. The checksMap should not be empty.
3. All values in the map should be false initially.

**Expected Result:** A non-empty map is returned with all values set to false, indicating no checks have been performed yet.


---

### **Test 3: testPerformQualityChecks_Successful**

```java
  @Test
void testPerformQualityChecks_Successful() {
    boolean simulatorActivated = true;
    String confirmation = "y";

    qualityChecks.performQualityChecks(confirmation, simulatorActivated);

    Map<Map<Integer, List<ID>>, Boolean> checkedOperations = qualityChecks.checkedOperations;
    for (Boolean value : checkedOperations.values()) {
        assertTrue(value);
    }
}
```

**Objective:**  Test the performQualityChecks() method to ensure that when the simulator is activated and confirmation is given, all operations are checked successfully.


**Validations:**
1. Ensure that checkedOperations is populated.
2. Ensure that all operations in checkedOperations have a true value, indicating they were successfully checked.

**Expected Result:** The checkedOperations map contains all operations with true values, confirming successful quality checks.


---

### **Test 4: testPerformQualityChecks_NotActivated**

```java
  @Test
void testPerformQualityChecks_NotActivated() {
    boolean simulatorActivated = false;
    String confirmation = "y";

    qualityChecks.performQualityChecks(confirmation, simulatorActivated);

    assertTrue(qualityChecks.checkedOperations.isEmpty());
}
```

**Objective:** Test the performQualityChecks() method when the simulator is not activated. It should not perform any quality checks.


**Validations:**
1. The simulator must be deactivated (simulatorActivated = false).
2. When confirmation is provided, no quality checks should be performed.
3. checkedOperations should remain empty.

**Expected Result:** No operations are checked, and checkedOperations is empty because the simulator is not activated.



### **Test 5: testPerformQualityChecks_NoConfirmation**

```java
@Test
void testPerformQualityChecks_NoConfirmation() {
    boolean simulatorActivated = true;
    String confirmation = "n";

    qualityChecks.performQualityChecks(confirmation, simulatorActivated);

    assertTrue(qualityChecks.checkedOperations.isEmpty());
}
```

**Objective:** Test the performQualityChecks() method to ensure that if the confirmation is not provided ("n"), no quality checks are performed, even if the simulator is activated.


**Validations:**
1. The simulator should be activated (simulatorActivated = true).
2. The user does not provide confirmation (confirmation = "n").
3. The checkedOperations should be empty because the quality check process was canceled.

**Expected Result:** No operations are checked, and checkedOperations is empty due to the lack of user confirmation.


---


## 5. Construction (Implementation)

### Class QualityChecksController

###### 1. Constructor
```java
  /**
 * Constructor initializes the operation repository.
 */
public QualityChecksController() {
    operationRepository = getOperationRepository();
}
```
###### 2. Get Operation Repository

```java
  /**
 * Retrieves the OperationRepository instance.
 *
 * @return The OperationRepository instance.
 */
private OperationRepository getOperationRepository() {
    if (operationRepository == null) {
        Repositories repository = Repositories.getInstance();
        operationRepository = repository.getOperationRepository();
    }
    return operationRepository;
}
```

###### 3. Get quality checks

```java
       /**
 * Retrieves or initializes the QualityChecks instance.
 *
 * @return A new or existing instance of QualityChecks.
 */
public QualityChecks getQualityChecks() {
    qualityChecks = new QualityChecks();
    return qualityChecks;
}
```
###### 4. Get Name by ID

```java
      /**
 * Retrieves the name of an operation using its ID.
 *
 * @param id The ID of the operation.
 * @return The name of the operation corresponding to the provided ID.
 */
public String getNameByID(ID id) {
    return operationRepository.getNameByID(id);
}
```
###### 5. Get Item List

```java
    public List<Item> getItemList(){
        return getItemRepository().getItemList();
    }
```
###### 6. Ask Quality Checks

```java
      /**
 * Handles the execution of quality checks, interacting with the simulator if available.
 *
 * @param confirmation A string input ("y" for yes, otherwise no) indicating user confirmation.
 */
public void askQualityChecksView(String confirmation) {
    qualityChecks = new QualityChecks();
    try {
        Simulator simulator = SimulatorController.getSharedSimulator();
        qualityChecks.performQualityChecks(confirmation, simulator.wasActivated());
    } catch (NullPointerException e) {
        qualityChecks.performQualityChecks(confirmation, false);
    }
}
```

### Class QualityChecks

###### 1. Constructor
```java
/**
 * Constructor initializes the priority queue and the checked operations map.
 */
public QualityChecks() {
    priorityQueue = new PriorityQueue<>(Comparator.comparingInt(map -> map.keySet().iterator().next()));
    checkedOperations = new HashMap<>();

}
```
###### 2. Fill Operations (PriorityQueue)

```java
 /**
 * Fills the priority queue with data from the height map of the production tree.
 *
 * @return The populated priority queue.
 */
public PriorityQueue<Map<Integer, List<ID>>> fillOperationsPriorityQueue() {
    ProductionTree pdt = new ProductionTree();
    pdt.getInformations("prodPlanSimulator(ESINF)/main/java/pt/ipp/isep/dei/esoft/project/files/input/boo.csv");
    Map<Integer, List<Node>> heightMap = pdt.getHeightMap();


    for (Map.Entry<Integer, List<Node>> entry : heightMap.entrySet()) {
        int height = entry.getKey();
        List<Node> nodesAtHeight = entry.getValue();

        Map<Integer, List<ID>> pqNode = new HashMap<>();
        List<ID> operations = new ArrayList<>();

        for (Node node : nodesAtHeight) {
            operations.add(node.getOperationID());
        }

        pqNode.put(height, operations);

        priorityQueue.add(pqNode);
    }

    return priorityQueue;
}
```

###### 3. Prepare QualityChecks

```java
/**
 * Prepares a map for quality checks, initially marking all operations as unchecked.
 *
 * @return The populated quality check map.
 */
public Map<Map<Integer, List<ID>>, Boolean> prepareQualityChecks() {
    Map<Map<Integer, List<ID>>, Boolean> checksMap = new LinkedHashMap<>();
    PriorityQueue<Map<Integer, List<ID>>> pq = fillOperationsPriorityQueue();

    while (!pq.isEmpty()) {
        Map<Integer, List<ID>> operations = pq.poll();
        checksMap.put(operations, false);
    }

    return checksMap;
}
```
###### 4. Perform QualityChecks

```java
 /**
 * Performs quality checks on operations based on user confirmation and simulator activation.
 *
 * @param confirmation User confirmation ("y" to perform checks).
 * @param simulatorActivated Indicates whether the simulator is activated.
 */
public void performQualityChecks(String confirmation, boolean simulatorActivated) {
    Map<Map<Integer, List<ID>>, Boolean> checksMap = prepareQualityChecks();

    if (simulatorActivated) {


        if (confirmation.equalsIgnoreCase("y")) {

            for (Map.Entry<Map<Integer, List<ID>>, Boolean> entry : checksMap.entrySet()) {
                Map<Integer, List<ID>> operationsMap = entry.getKey();
                Boolean isChecked = entry.getValue();
                int p = operationsMap.keySet().iterator().next();
                List<ID> operations = operationsMap.get(p);
                System.out.println("=======================================================");
                System.out.println(ANSI_BRIGHT_WHITE + "Performing checks: " + ANSI_RESET);
                if (!isChecked) {
                    entry.setValue(true);
                    System.out.printf("[%d] %s%s were checked%s%n", p, operations, ANSI_BRIGHT_GREEN, ANSI_RESET);
                    System.out.println("=======================================================");
                    System.out.println(ANSI_BRIGHT_WHITE + "Checks to be performed: " + ANSI_RESET);
                }

                for (Map.Entry<Map<Integer, List<ID>>, Boolean> entry2 : checksMap.entrySet()) {
                    operationsMap = entry2.getKey();
                    Boolean isChecked2 = entry2.getValue();
                    int p2 = operationsMap.keySet().iterator().next();
                    List<ID> operations2 = operationsMap.get(p2);

                    if (!isChecked2) {
                        System.out.printf("[%d] %s%s were not check yet%s%n", p2, operations2, ANSI_BRIGHT_YELLOW, ANSI_RESET);
                    }
                }
                System.out.println("=======================================================");

                System.out.println("Operations that have already been checked:");
                for (Map.Entry<Map<Integer, List<ID>>, Boolean> entry3 : checksMap.entrySet()) {
                    operationsMap = entry3.getKey();
                    Boolean isChecked3 = entry3.getValue();
                    int p3 = operationsMap.keySet().iterator().next();
                    List<ID> operations3 = operationsMap.get(p3);

                    if (isChecked3) {
                        System.out.printf("[%d] %s%s were checked%s%n", p3, operations3, ANSI_BRIGHT_GREEN, ANSI_RESET);
                    }
                }
                System.out.printf("%n%n");

                sleep(1000);

            }
            checkedOperations = checksMap;
            System.out.printf("%n%sAll operations have been checked!%s%n", ANSI_BRIGHT_GREEN, ANSI_RESET);

        } else {
            System.out.println(ANSI_LIGHT_RED + "Quality check not performed!" + ANSI_RESET);
        }
    } else {
        System.out.println(ANSI_LIGHT_RED + "Activate simulator first!" + ANSI_RESET);
    }

}
```


