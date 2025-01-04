# USEI20 - Project Scheduling

### **Algorithms Class Complexity Analysis**

| Method                           | Complexity          |
|----------------------------------|---------------------|
| `hasCircularDependencies()`      | O(V + E)            |
| `hasCycleUtil()`                 | O(V + E)            |

#### 1. **Method `hasCircularDependencies()`**
- **Explanation:** The method `hasCircularDependencies` performs a Depth First Search (DFS) over the graph to detect cycles. It:
  - Iterates over all vertices in the graph, calling `hasCycleUtil` on each unvisited vertex.
  - The outer loop runs `V` times (where `V` is the number of vertices).
  - For each vertex, the helper function `hasCycleUtil` is invoked, which performs a DFS traversal, visiting vertices and their adjacent vertices.
  - The DFS will visit each vertex and edge at most once, leading to a total time complexity of **O(V + E)** for the traversal.
- **Complexity:** **O(V + E)**, where `V` is the number of vertices and `E` is the number of edges in the graph.

#### 2. **Method `hasCycleUtil()`**
- **Explanation:** The helper function `hasCycleUtil` performs the actual DFS to detect cycles. It:
  - Marks the current vertex as visited and adds it to the recursion stack.
  - For each adjacent vertex, if it is already in the recursion stack, it indicates a cycle.
  - If the adjacent vertex is unvisited, it recursively explores that vertex.
  - Each vertex and edge is visited at most once during the DFS traversal.
  - The complexity of this method is tied to the total number of vertices and edges, making it **O(V + E)** for each call.
- **Complexity:** **O(V + E)**, where `V` is the number of vertices and `E` is the number of edges. This complexity is added across the DFS calls for all vertices, so the total complexity is still **O(V + E)**.

### Overall Complexity
The overall complexity for detecting circular dependencies (cycles) in a graph using DFS is **O(V + E)**, where:
- `V` is the number of vertices (nodes).
- `E` is the number of edges (connections between vertices).

This complexity is optimal for cycle detection in directed graphs.

