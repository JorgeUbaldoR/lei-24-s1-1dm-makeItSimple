# USEI11 - QualityChecks

##

### **ItemLevelProcessor Complexity Analysis**

| Method                   | Complexity          |
|--------------------------|---------------------|
| ItemLevelProcessor()     | O(1) / O(t * n * k) |
| initializeTreeMap()      | O(n log n)          |
| associateItemsWithTree() | O(t * n * k)        |
| getTree()                | O(1)                |

#### 1. **Constructor `ItemLevelProcessor() `**

- **Explanation:** The constructor initializes the tree structure using a TreeMap with a custom comparator (
  Collections.reverseOrder()) and then calls the associateItemsWithTree() method.
    - Initializing the TreeMap is constant time as it only involves setting up an empty map.
    - The complexity of associateItemsWithTree() will dominate the overall complexity of the constructor.

- **Complexity:** Depends on associateItemsWithTree(). For the initialization itself: O(1).

#### 2. **Method `initializeTreeMap()`**

- **Explanation:** This method creates a TreeMap and populates it using data from a priority queue filled by
  fillOperationsPriorityQueue() in the QualityChecks class.
- The priority queue is iterated until empty, and for each entry, IDs are added to the TreeMap using computeIfAbsent,
  which is efficient. Assuming n is the number of operations in the priority queue and m is the number of IDs in total:
    - Polling from the priority queue has a complexity of O(log n) per operation.
    - Adding to the TreeMap takes O(log h), where h is the height of the tree.
- Overall, this is dominated by the iteration over pq and inserting IDs.

- **Complexity:** O(n log n), where n is the number of priority queue entries, m is the total number of IDs, and h is
  the number of priority levels.

#### 3. **Method `associateItemsWithTree()`**

- **Explanation:** This method associates items to the tree structure:
    - Initializes a TreeMap using initializeTreeMap().
    - Iterates over the treeMap entries, and for each height:
        - Iterates over the IDs and the items in the itemList to find matches (item.hasOperationWithID()).
        - For matched items, it adds their quantities to a LinkedHashMap.
    - Adds this LinkedHashMap to a queue, which is added to the tree.
    - Adds this LinkedHashMap to a queue, which is added to the tree.
    - Let t be the number of heights in the treeMap, n be the total number of IDs, and k be the number of items in
      itemList:
        - Iterating over the treeMap has complexity O(t).
        - For each height, iterating over the IDs and items involves checking if an item has a specific operation, which
          is O(nk) in the worst case.
    - Overall, this is dominated by iterating through all IDs and items for each height.

- **Complexity:** O(t * n * k), where t is the number of heights, n is the number of IDs, and k is the number of items.

#### 4. **Method `getTree()`**

- **Explanation:** This is a simple getter that returns the tree structure. Accessing a variable has constant time
  complexity.

- **Complexity:** O(1)

### **Overall Complexity Analysis of ItemLevelProcessor**

- The overall complexity of the ItemLevelProcessor class depends primarily on the associateItemsWithTree() method, as it
  is called during the constructor and dominates the processing.
- Overall Complexity: O(t * n * k), where t is the number of heights in the tree, n is the number of IDs, and k is the
  number of items.

---

### **SimulatorController Complexity Analysis**

| Method                           | Complexity   |
|----------------------------------|--------------|
| SimulatorController()            | O(1)         |
| getTree()                        | O(t × n × k) |
| startSimulationWithOutPriority() | O(n^2)       |

#### 1. **Constructor (`SimulatorController()`)**

- **Explanation:** The constructor calls three methods: getItemRepository(), getMachineRepository(), and
  getOperationRepository(). These methods initialize them.
- Each method executes in constant time (accessing a singleton), the overall complexity will remain constant.
-

- **Complexity:** O(1)

#### 2. **Method `getTree() `**

- **Explanation:** The method initializes an instance of ItemLevelProcessor and calls its getTree() method:
- Creating the ItemLevelProcessor calls its constructor, which in turn invokes associateItemsWithTree(). The complexity
  of this constructor is dominated by the complexity of associateItemsWithTree() in the ItemLevelProcessor class.
- Returning the tree via getTree() is O(1).

- **Complexity:** O(t × n × k), where t is the number of heights in the tree, n is the number of IDs, and k is the
  number of items in the item list (from associateItemsWithTree()).

#### 3. **Method `startSimulationWithOutPriority() `**

- **Explanation:** This method performs the following:
- Records the current time.
- Initializes a Simulator instance:
  - Constructor complexity depends on setting up the machine map, the tree structure, the operation list, and the machine list.
  - Assuming these are direct assignments or involve straightforward data processing, the complexity depends on the size of these data structures.
- Calls simulator.startSimulation():
  - This method is O(n^3)


- **Complexity:** O(t × n × k) or simply O(n^3)

### **Overall Complexity:**

- The overall complexity of SimulatorController is dominated by:
  - getTree()
  - startSimulationWithOutPriority()
- Both O(n^3), so overall complexity is O(n^3) 

---

### **Final Overall Complexity**

- startSimulationWithOutPriority() and getTree() dominate the computational workload due to their iterative and nested processing. If n³ (from simulation) outweighs t × n × k (from tree initialization), the overall complexity simplifies to O(n³).


