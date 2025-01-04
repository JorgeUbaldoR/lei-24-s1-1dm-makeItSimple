# USEI20 - Project Scheduling

### **ProjectSchedule Class Complexity Analysis**

---

### **1. `generateVerticesListBFS`**

- **Objective**: This method generates a topologically ordered list of vertices (activities).
- **Operations**:
    1. **Clear the list**: `verticesList.clear()`. This operation is `O(n)`, where `n` is the number of activities,
       because it removes all elements from the list.
    2. **Generate topological order**: `Algorithms.getTopologicalOrder(projectGraph)`. The complexity of this operation
       depends on the implementation of the topological sorting algorithm. If it uses depth-first search (DFS) or
       breadth-first search (BFS), the time complexity will be `O(V + E)`, where `V` is the number of vertices (
       activities) and `E` is the number of edges (dependencies between activities).

**Complexity**:

- **Time Complexity**: `O(V + E)`
- **Space Complexity**: `O(V)` (to store the ordered list of activities).

---

### **2. `calculateScheduleAnalysis`**

- **Objective**: This method calculates scheduling metrics such
  as `earliestStart`, `earliestFinish`, `latestStart`, `latestFinish`, and `slack` for each activity.
- **Operations**:
    1. **Generate topologically ordered list of vertices**: This has already been analyzed in the complexity
       of `generateVerticesListBFS`, being `O(V + E)`.
    2. **Calculate `earliestStart` and `earliestFinish` for each activity**:

    - Iterating over all vertices is `O(V)`.
    - For each activity, the method checks its predecessors to calculate the `earliestStart`. This involves iterating
      through the list of predecessors for each activity, leading to a time complexity of `O(p)` for each activity,
      where `p` is the number of predecessors. In the worst case, `p = V`, so this step has a complexity of `O(V)` for
      each activity.
    - Calculating `earliestFinish` is a constant-time operation for each activity, i.e., `O(1)`.
    - Overall, calculating the `earliestStart` and `earliestFinish` for all activities has a complexity of `O(V * p)` in
      the worst case.

    3. **Calculate `latestFinish`, `latestStart`, and `slack` for each activity**:

    - This step iterates through the list of activities in reverse order (topologically sorted list). This iteration
      takes `O(V)`.
    - For each activity, the method checks its successors to calculate the `latestStart`. This involves iterating
      through the list of successors, which in the worst case could be `O(V)` for each activity.
    - Calculating `slack` is a constant-time operation for each activity, i.e., `O(1)`.
    - Overall, calculating `latestFinish`, `latestStart`, and `slack` has a complexity of `O(V * s)` in the worst case,
      where `s` is the number of successors.

**Complexity**:

- **Time Complexity**: `O(V + E)`
    - The time complexity is dominated by the graph traversal and the analysis for each activity.
- **Space Complexity**: `O(V)` (for storing the list of activities and their scheduling information).

---

### **3. `getMaxEarliestFinish`**

- **Objective**: Finds the maximum `earliestFinish` time among the predecessors of a given activity.
- **Operations**:
    1. **Iterating through predecessors**: This involves iterating through the list of predecessors of the activity. In
       the worst case, there are `V` predecessors, so this operation has a time complexity of `O(V)`.
    2. **Finding the maximum `earliestFinish`**: This is done in constant time for each predecessor, i.e., `O(1)`.

**Complexity**:

- **Time Complexity**: `O(p)` where `p` is the number of predecessors (in the worst case, `p = V`).
- **Space Complexity**: `O(1)`.

---

### **4. `getMinLatestStart`**

- **Objective**: Finds the minimum `latestStart` time among the successors of a given activity.
- **Operations**:
    1. **Iterating through successors**: This involves iterating through the list of successors of the activity. In the
       worst case, there are `V` successors, so this operation has a time complexity of `O(V)`.
    2. **Finding the minimum `latestStart`**: This is done in constant time for each successor, i.e., `O(1)`.

**Complexity**:

- **Time Complexity**: `O(s)` where `s` is the number of successors (in the worst case, `s = V`).
- **Space Complexity**: `O(1)`.

---

### **Final Complexity Table**

| Method                      | Time Complexity      | Space Complexity |
|-----------------------------|----------------------|------------------|
| `generateVerticesListBFS`   | `O(V + E)`           | `O(V)`           |
| `calculateScheduleAnalysis` | `O(V + E)`           | `O(V)`           |
| `getMaxEarliestFinish`      | `O(p)` (where p = V) | `O(1)`           |
| `getMinLatestStart`         | `O(s)` (where s = V) | `O(1)`           |

---

### **Conclusions**

- The overall complexity of the `ProjectSchedule` class is heavily influenced by the graph traversal and dependency
  analysis. The time complexity of the most significant methods is `O(V + E)`, which is typical for graph-related
  algorithms that need to process each node and edge.
- The space complexity for each method is generally `O(V)`, which is reasonable considering that the scheduling data (
  such as `earliestStart`, `earliestFinish`, etc.) needs to be stored for each activity.
- The auxiliary methods like `getMaxEarliestFinish` and `getMinLatestStart` have linear complexity in terms of the
  number of predecessors or successors but don't introduce additional significant complexity beyond the main scheduling
  analysis.

In conclusion, the `ProjectSchedule` class is efficient in terms of both time and space, with its most time-consuming
operations being linear with respect to the number of activities and dependencies.