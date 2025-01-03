# USEI11 - QualityChecks

##

### **QualityChecksController Complexity Analysis**

| Method                    | Complexity |
|---------------------------|------------|
| QualityChecksController() | O(1)       |
| getOperationRepository()  | O(1)       |
| getQualityChecks()        | O(1)       |
| getNameByID(ID id)        | O(n)       |
| askQualityChecksView      | O(n)       |

#### 1. **Constructor `QualityChecksController()`**

- **Explanation:** This is the constructor method, and it initializes the operationRepository by calling the
  getOperationRepository() method. No loops or complex logic are involved, so the complexity remains constant.
- **Complexity:** O(1)

#### 2. **Method `getOperationRepository()`**

- **Explanation:** This method checks if operationRepository is null. If so, it initializes it using a singleton
  instance of Repositories. The operations are straightforward, and there are no iterations, making the complexity
  constant.
- **Complexity:** O(1)

#### 3. **Method `getQualityChecks()`**

- **Explanation:** This method creates a new instance of the QualityChecks class. Object instantiation in Java is a
  constant-time operation as it does not involve loops or recursion.
- **Complexity:** O(1)

#### 4. **Method `getNameByID(ID id)`**

- **Explanation:** The complexity depends on how getNameByID is implemented in OperationRepository. The repository uses
  a set , the method performs a linear search, resulting in a complexity of O(n), where n is the number of operations.
- **Complexity:** O(n)

#### 5. **Method `askQualityChecksView `**

- **Explanation:** This method delegates to performQualityChecks in QualityChecks, which likely iterates over all
  operations for quality checks. This method processes a set of operations, the complexity will be proportional to the
  number of operations, O(n).
- **Complexity:** O(n)

### **Overall Complexity Analysis of QualityChecksController**

- The overall complexity of QualityChecksController is O(n), as the most computationally expensive operations (
  getNameByID and askQualityChecksView) are linear in relation to the number of operations or IDs being processed.

---

### **QualityChecks Complexity Analysis**

| Method                                                                | Complexity    |
|-----------------------------------------------------------------------|---------------|
| QualityChecks()                                                       | O(1)          |
| fillOperationsPriorityQueue()                                         | O(n^2)        |
| prepareQualityChecks()                                                | O(n^2)        |
| performQualityChecks(String confirmation, boolean simulatorActivated) | O(n^2) / O(1) |
| sleep(int time)                                                       | O(1)          |

#### 1. **Constructor (`QualityChecks()`)**

- **Explanation:** he constructor initializes a PriorityQueue and a HashMap without any complex operations. The data
  structures are empty upon initialization, so the complexity is constant.
- **Complexity:** O(1)

#### 2. **Method `fillOperationsPriorityQueue()`**

- **Explanation:** This method creates a priority queue based on data extracted from a ProductionTree. It:
    - Calls getInformations and getHeightMap (whose complexities depend on the implementation of ProductionTree).
    - Iterates over the entries in the heightMap. For each entry, it:
        - Iterates over a list of Node objects, accessing the operationID from each node and adding it to a List<ID>.
        - Constructs a Map<Integer, List<ID>> and adds it to the priority queue.
    - heightMap has n entries and each height contains m nodes on average, the nested iteration contributes O(n^2).
- **Complexity:** O(n^2)

#### 3. **Method ` prepareQualityChecks()`**

- **Explanation:** 
    - This method uses fillOperationsPriorityQueue() to populate a priority queue, contributing O(n^2) complexity.
    - It then iterates over the priority queue, transferring its contents into a LinkedHashMap while marking all entries as false (unchecked). This iteration is linear with respect to the number of elements in the queue, contributing O(n) additional complexity.
    - Combined, the complexity is determined by the dominating operation, fillOperationsPriorityQueue.
- **Complexity:** O(n^2)

#### 4. **Method `performQualityChecks(String confirmation, boolean simulatorActivated)`**

- **Explanation:** 
    - If the simulator is not activated, the method terminates with constant complexity, O(1).
    - If activated:
        - Calls prepareQualityChecks(), contributing O(n^2).
        - If confirmation is "y", iterates over the entries in the checksMap. For each entry:
            - Checks whether it has been processed (isChecked), updates the status, and performs nested iterations over the checksMap to print the status of all operations. These nested iterations are O(n²), where k is the number of height levels (from the priority queue).
        - Sleeps for a specified time after each iteration, contributing O(1) per iteration.
- **Complexity:** O(n^2)

#### 5. **Method `sleep(int time)`**

- **Explanation:** This method introduces a fixed delay (Thread.sleep) and does not involve any computation. Its
  complexity is constant.
- **Complexity:** O(1)

### **Overall Complexity**
- The overall complexity of the QualityChecks class is O(n^2), dominated by processing the production tree and performing nested checks on operations.
---

### **Final Overall Complexity**

- Final Overall Complexity

The final overall complexity of the USEI11 - QualityChecks module is O(n^2), primarily due to the nested iterations over operations and height levels in the QualityChecks class, specifically in methods like fillOperationsPriorityQueue, prepareQualityChecks, and performQualityChecks.

