# USEI24- Simulate Project Delays and their Impact

### **Algorithms Class Complexity Analysis**

| Method                     | Complexity |
|----------------------------|------------|
| `calculateCriticalPath()`  | O(1)       |
| `updateActivityDuration()` | O(1)       |
| `removeActivities()`       | O(V+E)     |

#### 1. **Method `calculateCriticalPath()`**
- **Explanation:** This method delegates the critical path calculation to another method.
  - It calls CriticalPath.calculateCriticalPath(createdMap) directly, passing the MapGraph as input.
  - The method itself does not perform any additional computations or iterations.
  - The actual complexity of the operation depends on the implementation of CriticalPath.calculateCriticalPath.
- **Complexity:** **O(1)**(for the calculateCriticalPath method itself). The delegated method has complexity O(V+E), where `V` is the number of vertex and `E` is the number of edges.

#### 2. **Method `updateActivityDuration()`**
- **Explanation:** This method updates the duration of an activity, ensuring it remains non-negative.
  - Retrieves the current duration of the activity using a getter, O(1).
  - Adds the new duration to the current duration, O(1).
  - Checks if the result is non-negative, O(1).
  - Updates the activity’s duration using a setter, O(1).
  - All operations are constant-time and do not depend on the size of the input.
- **Complexity:** **O(1)**

#### 3. **Method `removeActivities()`**
- **Explanation:** This method removes specific vertices from a graph by iterating over its vertices and identifying those to be removed.
  - Iterates over all vertices of the graph to check if their IDs match the specified start or finish IDs. For each vertex, the ID is retrieved and compared, O(V).
  - Iterates over the list of vertices to be removed (toRemove) and removes them from the graph. The removal operation involves updating the graph's internal data structure, which may require adjusting adjacency lists or edges. O(E).
- **Complexity:** **O(V+E)**, where `V` is the number of vertex and `E` is the number of edges.

### Overall Complexity

-Graph Traversal Dominates Complexity: The primary contributors to the overall complexity are operations on the graph, including vertex and edge traversal. These operations involve iterating through all vertices and edges in the graph.

-Critical Path Calculation: The calculation of the critical path involves graph traversal and edge evaluation, which are O(V + E).

-Vertex Removal: Removing specific vertices involves iterating over all vertices and updating connections, which contributes O(V + E).

-Activity Duration Update is Constant: Updating the duration of a single activity is an O(1), operation and has negligible impact on the overall complexity.

-The entire system's complexity is dominated by graph operations, making it O(V + E), where V is the number of vertices and E is the number of edges in the graph.