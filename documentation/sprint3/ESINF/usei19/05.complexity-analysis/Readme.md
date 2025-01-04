# USEI19- Topological sort of project activities

### **Algorithms Class Complexity Analysis**

| Method                           | Complexity |
|----------------------------------|------------|
| `addEdge()`                      | O(1)       |
| `write()`                        | O(N)       |
| `performTopologicalSort`         | O(V+E)     |
| `convertMapGraphToAdjacencyList` | O(V+E)     |
| `topologicalSort `               | O(V+E)     |

#### 1. **Method `addEdge()`**
- **Explanation:** The addEdge method adds a directed edge from one node to another in a graph and updates the in-degree of the target node.
  - Uses "putIfAbsent" to initialize empty adjacency lists for "from" and "to" (constant time for each operation, O(1)).
  - Retrieves the adjacency list for "from" and adds "to" to it O(1).
  - Updates the in-degree of "to" and ensures "from" exists in the "inDegree" map O(1).
  - The method involves constant-time operations repeated a fixed number of times, resulting in overall constant complexity for each call.
- **Complexity:** **O(1)**

#### 2. **Method `write()`**
- **Explanation:** The write method writes a list of strings to a file line by line.
  - Opens a "PrintWriter" to write to the specified file (constant time to initialize, O(1).
  - Writes a fixed header line O(1).
  - Iterates over the list and writes each string to the file. Writing each string takes O(N).
  - Closes the file, which is a constant-time operation O(1).
- **Complexity:** **O(N)**, where `N` is the number of strings in the list.

#### 3. **Method `performTopologicalSort()`**
- **Explanation:** This method performs a topological sort by first converting a graph representation to an adjacency list, then constructing edges, and finally sorting.
  - Converts the graph to an adjacency list O(V+E).
  - Iterates over the adjacency list, adding edges for each connection O(E)
  - Performs a topological sort O(V+E).
- **Complexity:** **O(V+E)**, where `V` is the number of vertex and `E` is the number of edges.

#### 4. **Method `convertMapGraphToAdjacencyList()`**
- **Explanation:** This method converts a graph represented as a MapGraph to an adjacency list.
  - Iterates over all vertices O(V).
  - For each vertex, retrieves its neighbors and processes them O(E).
  - The adjacency list creation involves linear traversal of all vertices and edges, making the total complexity proportional to the graph size.
- **Complexity:** **O(V+E)**, where `V` is the number of vertex and `E` is the number of edges.

#### 5. **Method `topologicalSort()`**
- **Explanation:** The topologicalSort method performs a topological sort using Kahn’s Algorithm.
  - Iterates over all vertices to initialize a queue with nodes having zero in-degree O(V).
  - Processes each node in the queue, iterating over its neighbors and decrementing their in-degrees O(E).
  - Ensures no cycles exist by comparing the sorted order size with the graph size O(1).
  - The algorithm involves linear traversal of all vertices and edges, resulting in a total time complexity of O(V+E).
- **Complexity:** **O(V+E)**, where `V` is the number of vertex and `E` is the number of edges.

### Overall Complexity

-The dominant factors in the overall workflow are the graph operations and traversal steps, which involve visiting all vertices and edges.

-File writing depends on the size of the output but does not affect the graph traversal complexity.

-Therefore, the overall complexity for the entire system is O(V+E) with the file writing step adding O(N) based on the strings of the output.