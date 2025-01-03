# USEI02 - Implement a simulator that processes all the items.

##

### **Class SimulatorController Complexity Analysis**

| Method                           | Complexity        |
|----------------------------------|-------------------|
| SimulatorController()            | O(1)              |
| getItemRepository()              | O(1)              |
| getMachineRepository()           | O(1)              |
| getOperationRepository()         | O(1)              |
| startSimulationWithOutPriority() | O (M ⋅ log M + S) |
| getMachinesMap()                 | O(M⋅log M)        |
| getItemList()                    | O(N)              |
| getOperationList()               | O(N)              |
| getMachineList()                 | O(N)              |

#### 1. Complexity of Repository Methods (`getItemRepository`, `getMachineRepository`, `getOperationRepository`)

Each of these methods checks if a repository instance exists and, if not, initializes it. These methods have \(O(1)\)
complexity, as they only perform existence checks and assignments.

#### 2. Method `getMachinesMap()`

This method iterates over the list of machines and organizes each machine into a priority queue within a `Map` of
operations to machines. The complexity of this method can be broken down as follows:

- **Iteration over the list of machines**: \(O(M)\), where \(M\) is the number of machines.
- **Insertion into `PriorityQueue`**: Each insertion into a `PriorityQueue` has an average complexity of \(O(\log N)\),
  where \(N\) is the number of elements in the queue.

In the worst case, the complexity of `getMachinesMap()` is:
\[
O(M * log M)
\]

#### 3. Method `getItemList()`, `getOperationList()`, and `getMachineList()`

These methods retrieve lists directly from the repositories. Assuming that the repositories return the lists directly,
these methods will have \(O(1)\) complexity. However, if the lists are dynamically built, the complexity would be
proportional to the list sizes, i.e., \(O(I)\), \(O(O)\), and \(O(M)\), where \(I\) is the number of items, \(O\) is the
number of operations, and \(M\) is the number of machines.

#### 4. Method `startSimulationWithOutPriority()` and `startSimulationWithPriority()`

These methods start the simulation, passing necessary parameters to the `Simulator` class. The complexity depends on two
factors:

- **Parameter Construction**: The complexity of building parameters was discussed earlier (e.g., `getMachinesMap` with
  \(O(M * log M)\)).
- **Simulation Execution**: The complexity of the simulation itself depends on the implementation within the `Simulator`
  class. Assuming `startSimulation()` in `Simulator` has a complexity of \(S\), the complexity of the methods
  `startSimulationWithOutPriority` and `startSimulationWithPriority` will be limited by that complexity.

In the worst case, the complexity of the simulation methods can be estimated as:
\[
O(M * log M + S)
\]

#### Overall Complexity

For the code as a whole, the complexity depends on the amount of data processed and the `Simulator` implementation. The
methods with the highest complexity are `getMachinesMap()` and the execution of `Simulator.startSimulation()`.
Therefore, the overall complexity of the controller is approximately:

\[
O(M * log M + S)
\]

where \(M\) is the number of machines and \(S\) is the complexity of the simulation.

##

### **Class ItemRepository Complexity Analysis**

| Method        | Complexity |
|---------------|------------|
| getItemList() | O(N)       |

#### 1. Method `getItemList`:

- This method returns a new list containing all items stored in `itemList`, which appears to be a `Map`.
- `itemList.values()` provides a collection of all values in the map. Creating an `ArrayList` from this collection has a
  complexity of \(O(N)\), where \(N\) is the number of items in the `itemList` map.

##

### **Class MachineRepository Complexity Analysis**

| Method           | Complexity |
|------------------|------------|
| getMachineList() | O(N)       |

#### 1. Method `getMachineList`:

- This method returns a new list containing all machines stored in `machineList`, which also appears to be a `Map`.
- Similar to `getItemList`, `machineList.values()` returns a collection with all machines, and creating an `ArrayList`
  from it has a complexity of \(O(N)\), where \(N\) is the number of machines in the `machineList` map.

##

### **Class OperationRepository Complexity Analysis**

| Method          | Complexity |
|-----------------|------------|
| getOperations() | O(N)       |

#### 1. Method `getOperations`:

- This method returns a new list containing all elements in `operations`, which appears to be a `List`.
- Creating a new `ArrayList` from a `List` has a complexity of \(O(N)\), where \(N\) is the number of operations in the
  `operations` list.

##

### **Class Simulator Complexity Analysis**

| Method                                                                                                                                              | Complexity |
|-----------------------------------------------------------------------------------------------------------------------------------------------------|------------|
| Simulator(Map<Operation, Queue<Machine>> machines, List<Item> items, List<Operation> operations, ArrayList<Machine> machList, boolean priorityFlag) | O(O + I)   |
| checkInformation(Map<Operation, Queue<Machine>> machines, List<Operation> operations, List<Item> items)                                             | O(1)       |
| addOperationToQueue(List<Operation> operations, boolean priorityFlag)                                                                               | O(O)       |
| createQueues(List<Item> items)                                                                                                                      | O(I)       |
| checkOperationQueue()                                                                                                                               | O(N)       |
| checkTimeOperations()                                                                                                                               | O(M)       |
| updateMachines()                                                                                                                                    | O(M)       |
| printQueue()                                                                                                                                        | O(N)       |       
| printMachineStatus()                                                                                                                                | O(M)       |       
| assignItemToMachine(OperationQueue operationQueue, Queue<Machine> machineList)                                                                      | O(N)       |       

#### 1. Constructor `Simulator`

The constructor initializes various data structures and sets up parameters for the simulation. The overall complexity is
dominated by the number of operations \(O\) and items \(I\):

- **Parameter Copying**: Constant time operations \(O(1)\).
- **`checkInformation` Method Call**: \(O(1)\).
- **`addOperationToQueue` Method Call**: \(O(O)\).
- **`createQueues` Method Call**: \(O(I)\).

Thus, the overall complexity of the constructor is:

\[
O(O + I)
\]

where \(O\) is the number of operations and \(I\) is the number of items.

#### 2. Method `checkInformation`

This method verifies that the provided collections are not null or empty. The checks are constant time operations,
yielding a total complexity of:

\[
O(1)
\]

#### 3. Method `addOperationToQueue`

This method iterates through each operation and adds it to `operationQueueMap`, resulting in:

\[
O(O)
\]

#### 4. Method `createQueues`

This method assigns items to their corresponding operation queues. The complexity is:

\[
O(I)
\]

#### 5. Method `checkOperationQueue`

Assuming this method checks if any operation queues are non-empty, it will iterate through the `operationQueueMap`,
which has complexity:

\[
O(N)
\]

where \(N\) is the number of operation queues.

#### 6. Method `checkTimeOperations`

This method checks if any machine has remaining time left to finish processing. If \(M\) is the number of machines, the
complexity is:

\[
O(M)
\]

#### 7. Method `updateMachines`

This method iterates over all machines to update their status. Its complexity is:

\[
O(M)
\]

#### 8. Method `printQueue`

This method prints the current state of all operation queues, resulting in a complexity of:

\[
O(N)
\]

#### 9. Method `printMachineStatus`

This method prints the status of each machine. Thus, it has a complexity of:

\[
O(M)
\]

#### 10. Method `assignItemToMachine`

This method assigns the next item from the operation queue to an available machine. Assuming \(N\) is the total number
of items being processed, the complexity is:

\[
O(N)
\]

#### Overall Complexity Summary

The overall complexity for the `Simulator` class can be summarized as follows:

1. **Initialization**: \(O(O + I)\)
2. **Method Calls**:
    - `checkInformation`: \(O(1)\)
    - `addOperationToQueue`: \(O(O)\)
    - `createQueues`: \(O(I)\)
    - `checkOperationQueue`: \(O(N)\)
    - `checkTimeOperations`: \(O(M)\)
    - `updateMachines`: \(O(M)\)
    - `printQueue`: \(O(N)\)
    - `printMachineStatus`: \(O(M)\)
    - `assignItemToMachine`: \(O(N)\)

The final complexity reflects the class's efficiency in managing operations and items while ensuring that each method
performs optimally without unnecessary iterations. This comprehensive analysis provides clarity on how the complexity
scales with varying numbers of operations, items, and machines, thus facilitating potential optimizations in the future.

##

### **Machine Class: Complexity Analysis**

| Method                                                             | Complexity |
|--------------------------------------------------------------------|------------|
| Machine(ID id_machine, Operation operation, float processingSpeed) | O(1)       |
| processItem(Item item)                                             | O(1)       |
| updateMachine()                                                    | O(1)       |
| resetMachine()                                                     | O(1)       |
| printStatus()                                                      | O(1)       |
| setCurrentProcessingItem(Item item)                                | O(1)       |
| setNotAvailable()                                                  | O(1)       |
| setTimeToFinish()                                                  | O(1)       |
| getOperation()                                                     | O(1)       |
| getId_machine()                                                    | O(1)       |
| getProcessingSpeed()                                               | O(1)       |
| isAvailable()                                                      | O(1)       |
| getTimeLeftToFinish()                                              | O(1)       |
| getCurrentProcessingItem()                                         | O(1)       |

### 1. Method `Machine`

- The constructor of the `Machine` class initializes various attributes of the object, such as `id_machine`,
  `operation`, `processingSpeed`, `available`, `timeLeftToFinish`, and `currentProcessingItem`. All these operations are
  simple assignments, so the complexity is \(O(1)\).

### 2. Method `processItem`

- This method checks if the `operation` of the item matches the machine's `operation`. Both the equality check (
  `equals`) and setting the current processing item involve constant-time operations, resulting in a complexity of \(O(
  1)\).

### 3. Method `updateMachine`

- The method updates the machine's status by decrementing `timeLeftToFinish` and checking if the processing is complete.
  All these operations are straightforward and thus have a complexity of \(O(1)\).

### 4. Method `resetMachine`

- This method resets the machine's status and time left to finish to default values. The operations performed are simple
  assignments, leading to \(O(1)\) complexity.

### 5. Method `printStatus`

- The method prints the current status of the machine. It checks if the machine is available or currently processing an
  item and performs printing based on this condition. The checks and print operations are \(O(1)\).

### 6. Setters and Getters

- All setter and getter methods (e.g., `setCurrentProcessingItem`, `setNotAvailable`, `getOperation`, etc.) perform
  assignments or return values, resulting in a complexity of \(O(1)\).


### Overall Complexity

Overall, the complexity of all methods in the `Machine` class is \(O(1)\), meaning they all operate in constant time,
independent of the size of any input data. This indicates that the `Machine` class is efficient in its operations.