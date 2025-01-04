# USEI17 - Build a PERT-CPM graph

##

### **ActivityReader Complexity Analysis**

| Method                               | Complexity |
|--------------------------------------|------------|
| `readCSV()`                          | O(n + m)   |
| `addFinishPredecessors()`            | O(n)       |
| `getFinalID()`                       | O(k)       |
| `validateString()`                   | O(1)       |
| `validateParametersUnits()`          | O(1)       |
| `checkString()`                      | O(1)       |
| `checkConversion()`                  | O(1)       |
| `checkConversionAndNegativeInt()`    | O(1)       |
| `checkConversionAndNegativeDouble()` | O(1)       |
| `sendNegativeError()`                | O(1)       |

#### 1. **Method `readCSV()`**

- **Explanation:** The `readCSV` method processes a CSV file, creates a graph, and validates dependencies:
    - **Reading and parsing CSV:** This operation iterates over each record in the file. For each record, several
      actions are performed, such as:
        - Validating activity ID, duration, and cost (constant time for each validation).
        - Parsing and adding predecessors.
        - Creating the activity object and adding it to a graph.
    - **Adding vertices:** For each activity, a vertex is added to the graph, which takes constant time (O(1) per
      vertex).
    - **Adding edges:** Dependencies are processed and edges are added to the graph, which requires iterating through
      the vertices (O(m), where m is the number of edges).
    - **Circular dependency check:** The circular dependency check is executed at the end using an algorithm that might
      scan through all vertices and edges, making it O(n + m) in the worst case.

- **Complexity:** O(n + m), where n is the number of activities (vertices) and m is the number of dependencies (edges).

#### 2. **Method `addFinishPredecessors()`**

- **Explanation:** This method adds predecessors to the finish activity:
    - Iterates through the activities and checks if they are not present in the predecessors set, which takes O(n) where
      n is the number of activities.

- **Complexity:** O(n), where n is the number of activities in the map.

#### 3. **Method `getFinalID()`**

- **Explanation:** This method extracts the numeric part of an ID string by iterating over each character in the ID. The
  length of the ID string determines the complexity.

- **Complexity:** O(k), where k is the length of the string ID being processed.

#### 4. **Method `validateString()`**

- **Explanation:** This is a simple validation that checks if the string is not null or empty, which is a constant time
  operation.

- **Complexity:** O(1)

#### 5. **Method `validateParametersUnits()`**

- **Explanation:** This method validates the ID, duration, and cost parameters:
    - It checks the string values and converts them to integers or doubles, which are all constant time operations (O(
      1)).

- **Complexity:** O(1)

#### 6. **Method `checkString()`**

- **Explanation:** This method checks if a given string is not null or empty, which is constant time.

- **Complexity:** O(1)

#### 7. **Method `checkConversion()`**

- **Explanation:** This method converts the parameters (ID, duration, and cost) to integers and doubles and checks their
  validity. Each conversion and check is O(1).

- **Complexity:** O(1)

#### 8. **Method `checkConversionAndNegativeInt()`**

- **Explanation:** This method checks if a string is a valid integer and ensures it's non-negative. The operations
  involved are constant time.

- **Complexity:** O(1)

#### 9. **Method `checkConversionAndNegativeDouble()`**

- **Explanation:** Similar to the integer check but for doubles. It checks if the value is non-negative, which is O(1).

- **Complexity:** O(1)

#### 10. **Method `sendNegativeError()`**

- **Explanation:** This method throws an exception if a parameter is negative. It’s a constant-time operation.

- **Complexity:** O(1)

### **Overall Complexity**

- **Overall Complexity of `readCSV()`:** O(n + m), where:
    - n is the number of records (activities) in the CSV file.
    - m is the number of edges (dependencies) between activities.
- All other methods have a constant-time complexity (O(1)) or are dependent on iterating through the activities, making
  them negligible in the overall complexity when compared to `readCSV()`. Therefore, the dominant complexity for this
  class is O(n + m).

### **MapGraph Complexity Analysis**

| Method                | Complexity                                   |
|-----------------------|----------------------------------------------|
| MapGraph(boolean)     | O(1)                                         |
| MapGraph(Graph<V, E>) | O(V + E)                                     |
| validVertex(V)        | O(1)                                         |
| adjVertices(V)        | O(1)                                         |
| edges()               | O(V + E)                                     |
| edge(V, V)            | O(1)                                         |
| edge(int, int)        | O(1)                                         |
| outDegree(V)          | O(1)                                         |
| inDegree(V)           | O(V)                                         |
| outgoingEdges(V)      | O(1)                                         |
| incomingEdges(V)      | O(V)                                         |
| addVertex(V)          | O(1)                                         |
| addEdge(V, V, E)      | O(1) (amortized)                             |
| removeVertex(V)       | O(V + E)                                     |
| removeEdge(V, V)      | O(1) / O(1) for directed / undirected graphs |
| clone()               | O(V + E)                                     |
| toString(ID)          | O(V + E)                                     |

#### 1. **Constructor `MapGraph(boolean directed)`**

- **Explanation:** This constructor initializes an empty graph with a `LinkedHashMap` to store vertices and a boolean
  flag indicating if the graph is directed. The complexity of this initialization is constant.

- **Complexity:** O(1)

#### 2. **Constructor `MapGraph(Graph<V, E> g)`**

- **Explanation:** This constructor creates a new graph based on an existing graph, copying its vertices and edges. The
  complexity of copying the graph depends on the number of vertices (V) and edges (E) in the original graph.

- **Complexity:** O(V + E)

#### 3. **Method `validVertex(V vert)`**

- **Explanation:** This method checks if the vertex exists in the graph by performing a lookup in the map, which is a
  constant time operation.

- **Complexity:** O(1)

#### 4. **Method `adjVertices(V vert)`**

- **Explanation:** This method returns the adjacent vertices of a given vertex. It looks up the vertex in the map and
  retrieves its adjacent vertices, which is a constant-time operation.

- **Complexity:** O(1)

#### 5. **Method `edges()`**

- **Explanation:** This method returns all the edges in the graph by iterating over all vertices and collecting their
  outgoing edges. Since it iterates over all vertices and edges, the time complexity is proportional to the number of
  vertices (V) and edges (E).

- **Complexity:** O(V + E)

#### 6. **Method `edge(V vOrig, V vDest)`**

- **Explanation:** This method retrieves the edge between two vertices by checking the adjacency list of the origin
  vertex. This is a constant-time operation once the vertices are found.

- **Complexity:** O(1)

#### 7. **Method `edge(int vOrigKey, int vDestKey)`**

- **Explanation:** This method retrieves the edge between two vertices identified by their keys. It first maps the keys
  to the corresponding vertices and then calls the `edge()` method, which is a constant-time operation.

- **Complexity:** O(1)

#### 8. **Method `outDegree(V vert)`**

- **Explanation:** This method returns the number of outgoing edges for a given vertex. It retrieves the vertex from the
  map and looks up its adjacency list, which is a constant-time operation.

- **Complexity:** O(1)

#### 9. **Method `inDegree(V vert)`**

- **Explanation:** This method calculates the number of incoming edges for a given vertex. It iterates over all vertices
  to check if there is an edge directed towards the given vertex. Therefore, its complexity depends on the number of
  vertices (V).

- **Complexity:** O(V)

#### 10. **Method `outgoingEdges(V vert)`**

- **Explanation:** This method retrieves the outgoing edges of a vertex. It retrieves the vertex from the map and
  returns its outgoing edges, which is a constant-time operation.

- **Complexity:** O(1)

#### 11. **Method `incomingEdges(V vert)`**

- **Explanation:** This method retrieves the incoming edges of a vertex by iterating over all other vertices and
  checking for edges directed towards the given vertex. Its complexity is proportional to the number of vertices (V).

- **Complexity:** O(V)

#### 12. **Method `addVertex(V vert)`**

- **Explanation:** This method adds a new vertex to the graph. It checks if the vertex already exists and adds it to the
  map, which is a constant-time operation.

- **Complexity:** O(1)

#### 13. **Method `addEdge(V vOrig, V vDest, E weight)`**

- **Explanation:** This method adds a new edge between two vertices. It checks if the edge already exists and, if
  necessary, adds the vertices and the edge. The operation is typically constant time, amortized by vertex additions.

- **Complexity:** O(1) (amortized)

#### 14. **Method `removeVertex(V vert)`**

- **Explanation:** This method removes a vertex and its associated edges from the graph. It removes all edges pointing
  to the vertex and deletes the vertex itself. The complexity is proportional to the number of vertices (V) and edges (
  E) associated with the vertex.

- **Complexity:** O(V + E)

#### 15. **Method `removeEdge(V vOrig, V vDest)`**

- **Explanation:** This method removes an edge between two vertices. The operation is constant-time for directed graphs
  and requires two operations for undirected graphs (removal of the edge in both directions).

- **Complexity:** O(1) (for directed graphs), O(1) + O(1) (for undirected graphs)

#### 16. **Method `clone()`**

- **Explanation:** This method creates a deep copy of the graph. It copies all vertices and edges, so its complexity
  depends on the number of vertices (V) and edges (E).

- **Complexity:** O(V + E)

#### 17. **Method `toString(ID id)`**

- **Explanation:** This method generates a string representation of the graph, including its vertices, edges, and
  connections. It iterates over all vertices and edges, so the complexity is proportional to the number of vertices (V)
  and edges (E).

- **Complexity:** O(V + E)

#### **Overall Complexity**

The overall time complexity for the methods in this class depends on the number of vertices (V) and edges (E) in the
graph. For most methods, the complexity is either constant (O(1)) or linear with respect to the number of vertices and
edges (O(V + E)). For methods involving iterating over vertices or edges, the complexity is dominated by the size of the
graph. Therefore, for large graphs with many vertices and edges, the complexity can approach O(V + E).



### **MapVertex Complexity Analysis**

| Method                               | Complexity |
|--------------------------------------|------------|
| `MapVertex(V vert)`                  | O(1)       |
| `getElement()`                       | O(1)       |
| `addAdjVert(V vAdj, Edge<V, E> edge)` | O(1)       |
| `remAdjVert(V vAdj)`                 | O(1)       |
| `getEdge(V vAdj)`                    | O(1)       |
| `numAdjVerts()`                      | O(1)       |
| `getAllAdjVerts()`                   | O(k)       |
| `getAllOutEdges()`                   | O(k)       |
| `toString()`                         | O(k)       |

#### 1. **Method `MapVertex(V vert)`**
- **Explanation:** The constructor initializes the vertex element and the adjacency map. These operations are constant time operations, as no iteration or additional data structures are involved.
- **Complexity:** **O(1)**

#### 2. **Method `getElement()`**
- **Explanation:** This method returns the stored vertex element. The operation is simply accessing a field, which takes constant time.
- **Complexity:** **O(1)**

#### 3. **Method `addAdjVert(V vAdj, Edge<V, E> edge)`**
- **Explanation:** This method adds an adjacent vertex and its associated edge to the `outVerts` map. The map's `put` operation is generally **O(1)** in average cases, as `LinkedHashMap` ensures that insertion is efficient.
- **Complexity:** **O(1)**

#### 4. **Method `remAdjVert(V vAdj)`**
- **Explanation:** This method removes an adjacent vertex from the `outVerts` map. The map's `remove` operation is generally **O(1)** on average.
- **Complexity:** **O(1)**

#### 5. **Method `getEdge(V vAdj)`**
- **Explanation:** This method retrieves the edge associated with a given adjacent vertex by performing a map lookup. Lookup in a `LinkedHashMap` is generally **O(1)** on average.
- **Complexity:** **O(1)**

#### 6. **Method `numAdjVerts()`**
- **Explanation:** This method returns the number of adjacent vertices, which is simply the size of the `outVerts` map. The `size()` method on a map is **O(1)**.
- **Complexity:** **O(1)**

#### 7. **Method `getAllAdjVerts()`**
- **Explanation:** This method returns a collection of all adjacent vertices. It creates a new `ArrayList` from the key set of the map, which requires iterating over the `outVerts` map. The time complexity is proportional to the number of adjacent vertices (`k`).
- **Complexity:** **O(k)**, where `k` is the number of adjacent vertices.

#### 8. **Method `getAllOutEdges()`**
- **Explanation:** This method returns a collection of all outgoing edges. Similar to `getAllAdjVerts()`, it creates a new `ArrayList` from the values of the map, which requires iterating over the map’s entries. The time complexity is proportional to the number of outgoing edges (`k`).
- **Complexity:** **O(k)**, where `k` is the number of outgoing edges.

#### 9. **Method `toString()`**
- **Explanation:** This method generates a string representation of the vertex, including details about its adjacent vertices and edges. It iterates over all adjacent vertices and appends their details to the `StringBuilder`. The time complexity is proportional to the number of adjacent vertices (`k`).
- **Complexity:** **O(k)**, where `k` is the number of adjacent vertices.

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


### **Edge Class Complexity Analysis**

| Method                         | Complexity          |
|--------------------------------|---------------------|
| `Edge()` (Constructor)         | O(1)                |
| `getVOrig()`                   | O(1)                |
| `getVDest()`                   | O(1)                |
| `getWeight()`                  | O(1)                |
| `setWeight()`                  | O(1)                |
| `toString()`                   | O(1)                |
| `equals()`                     | O(1)                |
| `hashCode()`                   | O(1)                |

#### 1. **Method `Edge()` (Constructor)**
- **Explanation:** The constructor initializes the edge with the given origin vertex, destination vertex, and optional weight. It throws an exception if either vertex is null.
- **Complexity:** **O(1)**, as the constructor only performs a constant-time check and assigns values to fields.

#### 2. **Method `getVOrig()`**
- **Explanation:** This method returns the origin vertex of the edge.
- **Complexity:** **O(1)**, as it only involves returning a field value.

#### 3. **Method `getVDest()`**
- **Explanation:** This method returns the destination vertex of the edge.
- **Complexity:** **O(1)**, as it only involves returning a field value.

#### 4. **Method `getWeight()`**
- **Explanation:** This method returns the weight of the edge.
- **Complexity:** **O(1)**, as it only involves returning a field value.

#### 5. **Method `setWeight()`**
- **Explanation:** This method updates the weight of the edge.
- **Complexity:** **O(1)**, as it only involves setting a field value.

#### 6. **Method `toString()`**
- **Explanation:** This method returns a string representation of the edge, including the origin vertex, destination vertex, and weight.
- **Complexity:** **O(1)**, as string formatting involves only a fixed amount of data and does not depend on any collection or iteration.

#### 7. **Method `equals()`**
- **Explanation:** This method checks if two edges are equal by comparing their origin and destination vertices (ignores the weight).
- **Complexity:** **O(1)**, as it involves a fixed number of comparisons (just the origin and destination).

#### 8. **Method `hashCode()`**
- **Explanation:** This method generates a hash code based on the origin and destination vertices, excluding the weight.
- **Complexity:** **O(1)**, as it involves a fixed number of operations (calculating the hash of two objects).

### Overall Complexity
All methods in the `Edge` class are constant-time operations, meaning the overall complexity for any operation in this class is **O(1)**.


### Summary of Overall Class Complexities

- **ActivityReader:** O(n + m) (dominant complexity due to `readCSV()`)
- **MapGraph:** O(V + E)
- **MapVertex:** O(1) to O(k) (depending on the operation)
- **Algorithms:** O(V + E) for cycle detection methods
- **Edge:** O(1) for all methods

These complexity analyses provide insight into the performance of various operations in the PERT-CPM graph construction and validation process.
