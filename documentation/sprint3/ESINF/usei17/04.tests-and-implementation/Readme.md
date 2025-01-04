# USEI17 - Build a PERT-CPM graph

## 4. Tests

## ActivityReaderTest Class

### **Test : testValidCSVFile**

```java
@Test
void testValidCSVFile() throws IOException {
    // Create a temporary CSV file
    File tempFile = File.createTempFile("validActivities", ".csv");
    try (FileWriter writer = new FileWriter(tempFile)) {
        writer.write("ActivKey,descr,duration,duration-unit,tot-cost,predecessors\n");
        writer.write("1,Activity 1,10,hours,1000,\n");
        writer.write("2,Activity 2,5,hours,500,1\n");
    }

    // Read the CSV file and validate the graph
    MapGraph<Activity, Double> graph = ActivityReader.readCSV(tempFile.getPath(), true);
    assertNotNull(graph, "Graph should not be null.");
    assertEquals(5, graph.numVertices(), "Graph should contain 5 vertices (including Start and Finish).");
    assertEquals(3, graph.numEdges(), "Graph should contain 3 edges.");

    // Validate the structure
    List<Activity> vertices = graph.vertices();
    assertTrue(vertices.stream().anyMatch(a -> a.getDescription().equals("Start")), "Graph should include a Start node.");
    assertTrue(vertices.stream().anyMatch(a -> a.getDescription().equals("Finish")), "Graph should include a Finish node.");
    assertTrue(vertices.stream().anyMatch(a -> a.getDescription().equals("Activity 1")), "Graph should include Activity 1.");
    assertTrue(vertices.stream().anyMatch(a -> a.getDescription().equals("Activity 2")), "Graph should include Activity 2.");
}
```

**Objective:** To verify that a valid CSV file is correctly parsed into a graph structure with the expected nodes and edges.

**Validations:**

1. Ensure the graph is not null.
2. Confirm the number of vertices and edges match the expected values.
3. Verify that specific nodes (e.g., Start, Finish, Activity 1, Activity 2) exist in the graph.

**Expected Result:** The graph is correctly populated, with the expected structure and attributes.

---

### **Test : testInvalidCSVFileDuplicateID**

```java
@Test
void testInvalidCSVFileDuplicateID() throws IOException {
    // Create a temporary CSV file with duplicate IDs
    File tempFile = File.createTempFile("duplicateIDActivities", ".csv");
    try (FileWriter writer = new FileWriter(tempFile)) {
        writer.write("ActivKey,descr,duration,duration-unit,tot-cost,predecessors\n");
        writer.write("1,Activity 1,10,hours,1000,\n");
        writer.write("1,Activity 2,5,hours,500,\n");
    }

    // Expect an exception when processing the file
    Exception exception = assertThrows(IllegalArgumentException.class, () -> {
        ActivityReader.readCSV(tempFile.getPath(), true);
    });
    assertTrue(exception.getMessage().contains("Duplicate activity ID"), "Exception should mention duplicate IDs.");
}
```

**Objective:** To ensure the parser detects and throws an error when duplicate IDs are present in the CSV file.

**Validations:**

1. Verify that an exception is thrown for duplicate activity IDs.
2. Confirm the exception message indicates the error.

**Expected Result:** The method throws an `IllegalArgumentException` with a message mentioning duplicate IDs.

---

### **Test : testInvalidCSVFileCircularDependency**

```java
@Test
void testInvalidCSVFileCircularDependency() throws IOException {
    // Create a temporary CSV file with a circular dependency
    File tempFile = File.createTempFile("circularDependency", ".csv");
    try (FileWriter writer = new FileWriter(tempFile)) {
        writer.write("ActivKey,descr,duration,duration-unit,tot-cost,predecessors\n");
        writer.write("1,Activity 1,10,hours,1000,2\n");
        writer.write("2,Activity 2,5,hours,500,1\n");
    }

    // Expect an exception when processing the file
    Exception exception = assertThrows(IllegalArgumentException.class, () -> {
        ActivityReader.readCSV(tempFile.getPath(), true);
    });
    assertTrue(exception.getMessage().contains("Circular Dependencies"), "Exception should mention circular dependencies.");
}
```

**Objective:** To verify that the parser detects and throws an error when circular dependencies are present in the CSV file.

**Validations:**

1. Ensure an exception is thrown for circular dependencies.
2. Confirm the exception message indicates the error.

**Expected Result:** The method throws an `IllegalArgumentException` with a message mentioning circular dependencies.

---

### **Test : testInvalidCSVFileMissingPredecessor**

```java
@Test
void testInvalidCSVFileMissingPredecessor() throws IOException {
    // Create a temporary CSV file with a missing predecessor
    File tempFile = File.createTempFile("missingPredecessor", ".csv");
    try (FileWriter writer = new FileWriter(tempFile)) {
        writer.write("ActivKey,descr,duration,duration-unit,tot-cost,predecessors\n");
        writer.write("1,Activity 1,10,hours,1000,2\n");
    }

    // Expect an exception when processing the file
    Exception exception = assertThrows(IllegalArgumentException.class, () -> {
        ActivityReader.readCSV(tempFile.getPath(), true);
    });
    assertTrue(exception.getMessage().contains("predecessor is"), "Exception should mention missing predecessors.");
}
```

**Objective:** To verify that the parser detects and throws an error when a predecessor is missing in the CSV file.

**Validations:**

1. Ensure an exception is thrown for missing predecessors.
2. Confirm the exception message indicates the error.

**Expected Result:** The method throws an `IllegalArgumentException` with a message mentioning missing predecessors.

---

### **Test : testValidationFunctions**

```java
@Test
void testValidationFunctions() {
    // Test valid string
    assertTrue(ActivityReader.validateString("Test"), "Valid string should return true.");
    assertFalse(ActivityReader.validateString(""), "Empty string should return false.");
    assertFalse(ActivityReader.validateString(null), "Null string should return false.");

    // Test valid ID, duration, and cost
    assertDoesNotThrow(() -> {
        ActivityReader.validateParametersUnits("1", "10", "100", 1);
    }, "Valid parameters should not throw exceptions.");

    // Test invalid ID
    Exception idException = assertThrows(IllegalArgumentException.class, () -> {
        ActivityReader.validateParametersUnits("A", "10", "100", 1);
    });
    assertTrue(idException.getMessage().contains("Invalid ID"), "Exception should mention invalid ID.");

    // Test negative duration
    Exception durationException = assertThrows(IllegalArgumentException.class, () -> {
        ActivityReader.validateParametersUnits("1", "-10", "100", 1);
    });
    assertTrue(durationException.getMessage().contains("DURATION"), "Exception should mention invalid duration.");

    // Test negative cost
    Exception costException = assertThrows(IllegalArgumentException.class, () -> {
        ActivityReader.validateParametersUnits("1", "10", "-100", 1);
    });
    assertTrue(costException.getMessage().contains("COST"), "Exception should mention invalid cost.");
}
```

**Objective:** To test the validation functions for string, ID, duration, and cost parameters, ensuring proper behavior for both valid and invalid inputs.

**Validations:**

1. Check behavior for valid and invalid strings.
2. Ensure valid parameters do not throw exceptions.
3. Verify exceptions are thrown for invalid IDs, negative durations, and costs.

**Expected Result:** Validation functions behave correctly, throwing exceptions with appropriate error messages for invalid inputs.

---

## MapGraphTest Class

### **Test: TestCopyConstructor**

```java
@Test
public void testCopyConstructor() {
    System.out.println("Test copy constructor");

    for (int i = 0; i < co.size(); i++)
        instance.addEdge(co.get(i), cd.get(i), cw.get(i));

    Graph<String, Integer> g = new MapGraph<>(instance);
    assertEquals(instance.getClass(), g.getClass(), "The graphs should be from the same class");
    assertEquals(instance, g, "The graphs should have equal contents");
}
```

**Objective:**
To validate the behavior of the copy constructor in the `MapGraph` class. This test ensures that the newly created graph (`g`) after copying from the original instance (`instance`) maintains the same class type and identical content.

**Validations:**

1. **Class Type Matching:** Ensure the copied graph (`g`) is of the same class as the original graph (`instance`).
2. **Content Equality:** Verify that the contents of both the original and copied graphs are the same.

**Expected Result:**
The test should confirm that the copied graph is of the same class type as the original and that both graphs contain identical data.

---

### **Test: TestIsDirected**

```java
@Test
public void testIsDirected() {
    System.out.println("Test isDirected");

    // Test for a directed graph
    assertTrue(instance.isDirected(), "result should be true");

    // Test for an undirected graph
    instance = new MapGraph<>(false);
    assertFalse(instance.isDirected(), "result should be false");
}
```

**Objective:**
To validate the behavior of the `isDirected()` method in the `MapGraph` class. This test ensures that the method correctly identifies whether the graph is directed or not based on the constructor's argument.

**Validations:**

1. **Directed Graph Check:** Ensure the graph is correctly identified as directed when initialized with `true`.
2. **Undirected Graph Check:** Ensure the graph is correctly identified as undirected when initialized with `false`.

**Expected Result:**
The test should confirm that the `isDirected()` method returns `true` for a directed graph and `false` for an undirected graph.

---


### **Test: TestNumVertices**

```java
@Test
public void testNumVertices() {
    System.out.println("Test numVertices");

    // Test for initial number of vertices (should be 0)
    assertEquals(0, instance.numVertices(), "result should be zero");

    // Add a vertex "A" and check the number of vertices
    instance.addVertex("A");
    assertEquals(1, instance.numVertices(), "result should be one");

    // Add another vertex "B" and check the number of vertices
    instance.addVertex("B");
    assertEquals(2, instance.numVertices(), "result should be two");

    // Remove vertex "A" and check the number of vertices
    instance.removeVertex("A");
    assertEquals(1, instance.numVertices(), "result should be one");

    // Remove vertex "B" and check the number of vertices
    instance.removeVertex("B");
    assertEquals(0, instance.numVertices(), "result should be zero");
}
```

**Objective:**
To validate the behavior of the `numVertices()` method in the `MapGraph` class. This test ensures that the method correctly tracks the number of vertices after various operations, including adding and removing vertices.

**Validations:**

1. **Initial Vertex Count:** Ensure the number of vertices is `0` when the graph is empty.
2. **Adding Vertices:** Verify that adding vertices increases the vertex count as expected.
3. **Removing Vertices:** Ensure that removing vertices decreases the vertex count correctly.

**Expected Result:**
The test should confirm that the `numVertices()` method correctly reflects the number of vertices in the graph at each stage, from empty to populated and back to empty after removals.

---

### **Test: TestVertices**

```java
@Test
public void testVertices() {
    System.out.println("Test vertices");

    // Test for initial vertices (should be empty)
    assertEquals(0, instance.vertices().size(), "vertices should be empty");

    // Add vertices "A" and "B"
    instance.addVertex("A");
    instance.addVertex("B");

    // Check the number of vertices and remove vertices from the collection
    Collection<String> cs = instance.vertices();
    assertEquals(2, cs.size(), "Must have 2 vertices");
    cs.removeAll(Arrays.asList("A", "B"));
    assertEquals(0, cs.size(), "Vertices should be A and B");

    // Remove vertex "A" and check the remaining vertices
    instance.removeVertex("A");
    cs = instance.vertices();
    assertEquals(1, cs.size(), "Must have 1 vertex");
    cs.removeAll(Arrays.asList("B"));
    assertEquals(0, cs.size(), "Vertex should be B");

    // Remove vertex "B" and check if the collection is empty
    instance.removeVertex("B");
    cs = instance.vertices();
    assertEquals(0, cs.size(), "Must not have any vertex");
}
```

**Objective:**
To validate the behavior of the `vertices()` method in the `MapGraph` class. This test ensures that the method correctly reflects the vertices in the graph after various operations, including adding and removing vertices.

**Validations:**

1. **Initial Vertices:** Ensure the graph has no vertices initially (empty collection).
2. **Adding Vertices:** Verify that adding vertices increases the collection size.
3. **Removing Vertices:** Ensure that removing vertices decreases the collection size and that only the remaining vertices are present.

**Expected Result:**
The test should confirm that the `vertices()` method accurately returns the set of vertices at any point in time, after adding and removing vertices.

--- 


### **Test: TestValidVertex**

```java
@Test
public void testValidVertex() {
    System.out.println("Test validVertex");

    // Add edges based on predefined data
    for (int i = 0; i < co.size(); i++)
        instance.addEdge(co.get(i), cd.get(i), cw.get(i));

    // Check if each vertex in 'co' exists in the graph
    for (String v : co)
        assertTrue(instance.validVertex(v), "Vertex should exist");

    // Check for a vertex that doesn't exist
    assertFalse(instance.validVertex("Z"), "Vertex should not exist");
}
```

**Objective:**
To validate the behavior of the `validVertex()` method in the `MapGraph` class. This test ensures that the method correctly identifies whether a vertex exists in the graph.

**Validations:**

1. **Valid Vertices:** Ensure that the `validVertex()` method returns `true` for vertices that have been added to the graph.
2. **Invalid Vertex:** Ensure that the `validVertex()` method returns `false` for vertices that do not exist in the graph.

**Expected Result:**
The test should confirm that the `validVertex()` method accurately identifies whether a vertex is present in the graph.

---


### **Test: TestKey**

```java
@Test
public void testKey() {
    System.out.println("Test key");

    // Add edges based on predefined data
    for (int i = 0; i < co.size(); i++)
        instance.addEdge(co.get(i), cd.get(i), cw.get(i));

    // Check the key for each vertex in 'ov'
    for (int i = 0; i < ov.size(); i++)
        assertEquals(i, instance.key(ov.get(i)), "Vertex key should match");

    // Check for a vertex that doesn't exist
    assertEquals(-1, instance.key("Z"), "Vertex key should be -1 for non-existing vertex");
}
```

**Objective:**
To validate the behavior of the `key()` method in the `MapGraph` class. This test ensures that the method correctly returns the key of a vertex, or `-1` if the vertex does not exist.

**Validations:**

1. **Valid Vertex Keys:** Ensure that the `key()` method returns the correct key for vertices that exist in the graph.
2. **Invalid Vertex Key:** Ensure that the `key()` method returns `-1` for vertices that do not exist in the graph.

**Expected Result:**
The test should confirm that the `key()` method accurately returns the expected key for valid vertices and `-1` for non-existing vertices.

---


### **Test: TestAdjVertices**

```java
@Test
public void testAdjVertices() {
    System.out.println("Test adjVertices");

    // Add edges based on predefined data
    for (int i = 0; i < co.size(); i++)
        instance.addEdge(co.get(i), cd.get(i), cw.get(i));

    // Test adjacent vertices for vertex "A"
    Collection<String> cs = instance.adjVertices("A");
    assertEquals(2, cs.size(), "Number of adjacent vertices should be 2");
    cs.removeIf(s -> s.equals("B") || s.equals("C"));
    assertEquals(0, cs.size(), "Adjacent vertices should be B and C");

    // Test adjacent vertices for vertex "B"
    cs = instance.adjVertices("B");
    assertEquals(1, cs.size(), "Number of adjacent vertices should be 1");
    cs.removeIf(s -> s.equals("D"));
    assertEquals(0, cs.size(), "Adjacent vertex should be D");

    // Test adjacent vertices for vertex "E"
    cs = instance.adjVertices("E");
    assertEquals(2, cs.size(), "Number of adjacent vertices should be 2");
    cs.removeIf(s -> s.equals("D") || s.equals("E"));
    assertEquals(0, cs.size(), "Adjacent vertices should be D and E");
}
```

**Objective:**
To validate the behavior of the `adjVertices()` method in the `MapGraph` class. This test ensures that the method correctly returns the adjacent vertices for a given vertex.

**Validations:**

1. **Correct Number of Adjacent Vertices:** Ensure the method returns the correct number of adjacent vertices for a given vertex.
2. **Correct Adjacent Vertices:** Ensure that the method returns the correct adjacent vertices for a given vertex.

**Expected Result:**
The test should confirm that the `adjVertices()` method accurately identifies and returns the correct adjacent vertices for each input vertex.

---

### **Test: TestNumEdges**

```java
@Test
public void testNumEdges() {
    System.out.println("Test numEdges");

    // Test the initial number of edges
    assertEquals(0, instance.numEdges(), "Number of edges should be zero");

    // Add an edge and test the number of edges
    instance.addEdge("A", "B", Integer.valueOf(1));
    assertEquals(1, instance.numEdges(), "Number of edges should be one");

    // Add another edge and test again
    instance.addEdge("A", "C", Integer.valueOf(2));
    assertEquals(2, instance.numEdges(), "Number of edges should be two");

    // Remove an edge and test the number of edges
    instance.removeEdge("A", "B");
    assertEquals(1, instance.numEdges(), "Number of edges should be one");

    // Remove another edge and test again
    instance.removeEdge("A", "C");
    assertEquals(0, instance.numEdges(), "Number of edges should be zero");
}
```

**Objective:**
To validate the behavior of the `numEdges()` method in the `MapGraph` class. This test ensures that the method correctly returns the number of edges in the graph after adding and removing edges.

**Validations:**

1. **Correct Edge Count:** Ensure that the method returns the correct number of edges after adding and removing edges.

**Expected Result:**
The test should confirm that the `numEdges()` method correctly tracks the number of edges, incrementing and decrementing as edges are added and removed.

---

### **Test: TestEdges**

```java
@Test
public void testEdges() {
    System.out.println("Test Edges");

    // Test when no edges are added
    assertEquals(0, instance.edges().size(), "Edges should be empty");

    // Add edges based on predefined data
    for (int i = 0; i < co.size(); i++)
        instance.addEdge(co.get(i), cd.get(i), cw.get(i));

    // Verify the number of edges and ensure they match the added edges
    Collection<Edge<String, Integer>> ced = instance.edges();
    assertEquals(8, ced.size(), "There should be 8 edges");
    for (int i = 0; i < co.size(); i++) {
        int finalI = i;
        ced.removeIf(e -> e.getVOrig().equals(co.get(finalI)) && e.getVDest().equals(cd.get(finalI)) && e.getWeight().equals(cw.get(finalI)));
    }
    assertEquals(0, ced.size(), "Edges should match the inserted ones");

    // Remove an edge and verify
    instance.removeEdge("A", "B");
    ced = instance.edges();
    assertEquals(7, ced.size(), "There should be 7 edges");
    for (int i = 1; i < co.size(); i++) {
        int finalI = i;
        ced.removeIf(e -> e.getVOrig().equals(co.get(finalI)) && e.getVDest().equals(cd.get(finalI)) && e.getWeight().equals(cw.get(finalI)));
    }
    assertEquals(0, ced.size(), "Edges should match the inserted ones after removal");

    // Remove another edge and verify
    instance.removeEdge("E", "E");
    ced = instance.edges();
    assertEquals(6, ced.size(), "There should be 6 edges");
    for (int i = 1; i < co.size() - 1; i++) {
        int finalI = i;
        ced.removeIf(e -> e.getVOrig().equals(co.get(finalI)) && e.getVDest().equals(cd.get(finalI)) && e.getWeight().equals(cw.get(finalI)));
    }
    assertEquals(0, ced.size(), "Edges should match the inserted ones after second removal");

    // Remove all edges and verify the graph is empty
    instance.removeEdge("A", "C");
    instance.removeEdge("B", "D");
    instance.removeEdge("C", "D");
    instance.removeEdge("C", "E");
    instance.removeEdge("D", "A");
    instance.removeEdge("E", "D");

    assertEquals(0, instance.edges().size(), "Edges should be empty");
}
```

**Objective:**
To validate the behavior of the `edges()` method in the `MapGraph` class. This test ensures that the method correctly returns the edges in the graph and reflects the changes when edges are added or removed.

**Validations:**

1. **Correct Number of Edges:** Ensure that the `edges()` method returns the correct number of edges.
2. **Correct Edge Contents:** Ensure that the edges returned by the `edges()` method match the edges added to the graph.
3. **Correct Behavior After Edge Removal:** Verify that the `edges()` method correctly reflects the graph state after edges are removed.

**Expected Result:**
The test should confirm that the `edges()` method accurately returns the correct edges, and that the number and contents of edges change as expected when edges are added or removed.

---

---

### **Test: TestGetEdge**

```java
@Test
public void testGetEdge() {
    System.out.println("Test getEdge");

    // Add edges to the graph
    for (int i = 0; i < co.size(); i++)
        instance.addEdge(co.get(i), cd.get(i), cw.get(i));

    // Verify each edge's weight
    for (int i = 0; i < co.size(); i++)
        assertEquals(cw.get(i), instance.edge(co.get(i), cd.get(i)).getWeight(), 
                     "Edge between " + co.get(i) + " and " + cd.get(i) + " should be " + cw.get(i));

    // Verify that edges between non-existing vertices return null
    assertNull(instance.edge("A", "E"), "Edge should be null");
    assertNull(instance.edge("D", "B"), "Edge should be null");

    // Remove an edge and verify it's removed
    instance.removeEdge("D", "A");
    assertNull(instance.edge("D", "A"), "Edge should be null");
}
```

**Objective:**
To validate the behavior of the `edge()` method in the `MapGraph` class. This test ensures that the method correctly returns an edge by its vertices and reflects the removal of edges.

**Validations:**

1. **Edge Weight:** Ensure that the `edge()` method correctly retrieves the edge weight between two vertices.
2. **Null for Non-Existing Edges:** Verify that the `edge()` method returns `null` for edges between non-existent vertices.
3. **Edge Removal:** Ensure that the edge is correctly removed and that the `edge()` method returns `null` for a removed edge.

**Expected Result:**
The test should confirm that the `edge()` method behaves as expected when retrieving edges and handling removed edges.

---

### **Test: TestGetEdgeByKey**

```java
@Test
public void testGetEdgeByKey() {
    System.out.println("Test getEdge");

    // Add edges to the graph
    for (int i = 0; i < co.size(); i++)
        instance.addEdge(co.get(i), cd.get(i), cw.get(i));

    // Verify each edge's weight using keys
    for (int i = 0; i < co.size(); i++)
        assertEquals(cw.get(i), instance.edge(instance.key(co.get(i)), instance.key(cd.get(i))).getWeight(), 
                     "Edge between " + co.get(i) + " and " + cd.get(i) + " should be " + cw.get(i));

    // Verify that edges between non-existing vertices return null
    assertNull(instance.edge(instance.key("A"), instance.key("E")), "Edge should be null");
    assertNull(instance.edge(instance.key("D"), instance.key("B")), "Edge should be null");

    // Remove an edge and verify it's removed
    instance.removeEdge("D", "A");
    assertNull(instance.edge(instance.key("D"), instance.key("A")), "Edge should be null");
}
```

**Objective:**
To validate the behavior of the `edge()` method when using vertex keys instead of vertex names in the `MapGraph` class. This test ensures that the method works correctly with keys for retrieving edges and reflects edge removal.

**Validations:**

1. **Edge Weight Using Keys:** Ensure that the `edge()` method retrieves the correct edge weight using vertex keys.
2. **Null for Non-Existing Edges Using Keys:** Verify that the `edge()` method returns `null` for edges between non-existent vertices when using keys.
3. **Edge Removal Using Keys:** Ensure that the edge is correctly removed and that the `edge()` method returns `null` for a removed edge when using keys.

**Expected Result:**
The test should confirm that the `edge()` method behaves correctly with vertex keys for retrieving edges and handling removed edges.

---

### **Test: TestOutDegree**

```java
@Test
public void testOutDegree() {
    System.out.println("Test outDegree");

    // Add edges to the graph
    for (int i = 0; i < co.size(); i++)
        instance.addEdge(co.get(i), cd.get(i), cw.get(i));

    // Test the out-degree of vertices
    assertEquals(-1, instance.outDegree("G"), "Out-degree should be -1 for non-existing vertex");
    assertEquals(2, instance.outDegree("A"), "Out-degree of vertex A should be 2");
    assertEquals(1, instance.outDegree("B"), "Out-degree of vertex B should be 1");
    assertEquals(2, instance.outDegree("E"), "Out-degree of vertex E should be 2");
}
```

**Objective:**
To validate the behavior of the `outDegree()` method in the `MapGraph` class. This test ensures that the method correctly returns the out-degree (number of outgoing edges) for a given vertex.

**Validations:**

1. **Non-Existing Vertex:** Ensure that the `outDegree()` method returns `-1` for a non-existing vertex.
2. **Correct Out-Degree:** Verify that the `outDegree()` method returns the correct out-degree for existing vertices.

**Expected Result:**
The test should confirm that the `outDegree()` method correctly computes the out-degree for vertices, including handling non-existing vertices.

---

### **Test: TestInDegree**

```java
@Test
public void testInDegree() {
    System.out.println("Test inDegree");

    // Add edges to the graph
    for (int i = 0; i < co.size(); i++)
        instance.addEdge(co.get(i), cd.get(i), cw.get(i));

    // Verify in-degree of vertices
    assertEquals(-1, instance.inDegree("G"), "Degree should be -1 for non-existing vertex");
    assertEquals(1, instance.inDegree("A"), "Degree of vertex A should be 1");
    assertEquals(3, instance.inDegree("D"), "Degree of vertex D should be 3");
    assertEquals(2, instance.inDegree("E"), "Degree of vertex E should be 2");
}
```

**Objective:**
To validate the behavior of the `inDegree()` method in the `MapGraph` class. This test ensures that the method correctly returns the in-degree (number of incoming edges) for a given vertex.

**Validations:**

1. **Non-Existing Vertex:** Ensure that the `inDegree()` method returns `-1` for a non-existing vertex.
2. **Correct In-Degree:** Verify that the `inDegree()` method returns the correct in-degree for existing vertices.

**Expected Result:**
The test should confirm that the `inDegree()` method correctly computes the in-degree for vertices, including handling non-existing vertices.

---

### **Test: TestOutgoingEdges**

```java
@Test
public void testOutgoingEdges() {
    System.out.println(" Test outgoingEdges");

    // Add edges to the graph
    for (int i = 0; i < co.size(); i++)
        instance.addEdge(co.get(i), cd.get(i), cw.get(i));

    // Verify outgoing edges for vertex C
    Collection<Edge<String, Integer>> coe = instance.outgoingEdges("C");
    assertEquals(2, coe.size(), "Outgoing edges of vertex C should be 2");
    coe.removeIf(e -> e.getWeight() == 4 || e.getWeight() == 5);
    assertEquals(0, coe.size(), "Outgoing edges of vertex C should be 4 and 5");

    // Verify outgoing edges for vertex E
    coe = instance.outgoingEdges("E");
    assertEquals(2, coe.size(), "Outgoing edges of vertex E should be 2");
    coe.removeIf(e -> e.getWeight() == 7 || e.getWeight() == 8);
    assertEquals(0, coe.size(), "Outgoing edges of vertex E should be 7 and 8");

    // Remove an edge and verify the updated outgoing edges
    instance.removeEdge("E", "E");
    coe = instance.outgoingEdges("E");
    assertEquals(1, coe.size(), "Outgoing edges of vertex E should be 1");

    instance.removeEdge("E", "D");
    coe = instance.outgoingEdges("E");
    assertEquals(0, coe.size(), "Outgoing edges of vertex E should be empty");
}
```

**Objective:**
To validate the behavior of the `outgoingEdges()` method in the `MapGraph` class. This test ensures that the method correctly returns the outgoing edges for a given vertex and reflects edge removal.

**Validations:**

1. **Outgoing Edges Count:** Ensure that the `outgoingEdges()` method returns the correct number of outgoing edges.
2. **Edge Removal:** Verify that the `outgoingEdges()` method reflects the removal of edges.

**Expected Result:**
The test should confirm that the `outgoingEdges()` method correctly computes the outgoing edges for vertices and handles edge removal.

---

### **Test: TestIncomingEdges**

```java
@Test
public void testIncomingEdges() {
    System.out.println(" Test incomingEdges");

    // Add edges to the graph
    for (int i = 0; i < co.size(); i++)
        instance.addEdge(co.get(i), cd.get(i), cw.get(i));

    // Verify incoming edges for vertex D
    Collection<Edge<String, Integer>> cie = instance.incomingEdges("D");
    assertEquals(3, cie.size(), "Incoming edges of vertex D should be 3");
    cie.removeIf(e -> e.getWeight() == 3 || e.getWeight() == 4 || e.getWeight() == 7);
    assertEquals(0, cie.size(), "Incoming edges of vertex D should be 3, 4, and 7");

    // Verify incoming edges for vertex E
    cie = instance.incomingEdges("E");
    assertEquals(2, cie.size(), "Incoming edges of vertex E should be 2");
    cie.removeIf(e -> e.getWeight() == 5 || e.getWeight() == 8);
    assertEquals(0, cie.size(), "Incoming edges of vertex E should be 5 and 8");

    // Remove an edge and verify the updated incoming edges
    instance.removeEdge("E", "E");
    cie = instance.incomingEdges("E");
    assertEquals(1, cie.size(), "Incoming edges of vertex E should be 1");

    instance.removeEdge("C", "E");
    cie = instance.incomingEdges("E");
    assertEquals(0, cie.size(), "Incoming edges of vertex E should be empty");
}
```

**Objective:**
To validate the behavior of the `incomingEdges()` method in the `MapGraph` class. This test ensures that the method correctly returns the incoming edges for a given vertex and reflects edge removal.

**Validations:**

1. **Incoming Edges Count:** Ensure that the `incomingEdges()` method returns the correct number of incoming edges.
2. **Edge Removal:** Verify that the `incomingEdges()` method reflects the removal of edges.

**Expected Result:**
The test should confirm that the `incomingEdges()` method correctly computes the incoming edges for vertices and handles edge removal.

---

### **Test: TestRemoveVertex**

```java
@Test
public void testRemoveVertex() {
    System.out.println("Test removeVertex");

    // Add edges to the graph
    for (int i = 0; i < co.size(); i++)
        instance.addEdge(co.get(i), cd.get(i), cw.get(i));

    // Verify the number of vertices and edges
    assertEquals(5, instance.numVertices(), "Number of vertices should be 5");
    assertEquals(8, instance.numEdges(), "Number of edges should be 8");

    // Remove vertices and verify the graph's state after each removal
    instance.removeVertex("A");
    assertEquals(4, instance.numVertices(), "Number of vertices should be 4");
    assertEquals(5, instance.numEdges(), "Number of edges should be 5");

    instance.removeVertex("B");
    assertEquals(3, instance.numVertices(), "Number of vertices should be 3");
    assertEquals(4, instance.numEdges(), "Number of edges should be 4");

    instance.removeVertex("C");
    assertEquals(2, instance.numVertices(), "Number of vertices should be 2");
    assertEquals(2, instance.numEdges(), "Number of edges should be 2");

    instance.removeVertex("D");
    assertEquals(1, instance.numVertices(), "Number of vertices should be 1");
    assertEquals(1, instance.numEdges(), "Number of edges should be 1");

    instance.removeVertex("E");
    assertEquals(0, instance.numVertices(), "Number of vertices should be 0");
    assertEquals(0, instance.numEdges(), "Number of edges should be 0");
}
```

**Objective:**
To validate the behavior of the `removeVertex()` method in the `MapGraph` class. This test ensures that the method correctly removes a vertex and adjusts the graph's structure accordingly.

**Validations:**

1. **Vertex and Edge Counts:** Ensure that the number of vertices and edges are updated correctly when a vertex is removed.
2. **Removal of All Vertices:** Verify that removing all vertices leaves the graph empty.

**Expected Result:**
The test should confirm that the `removeVertex()` method behaves as expected, correctly updating the graph's structure after each vertex removal.

---

### **Test: TestRemoveEdge**

```java
@Test
public void testRemoveEdge() {
    System.out.println("Test removeEdge");

    // Initial check for number of edges
    assertEquals(0, instance.numEdges(), "Number of edges should be 0");

    // Add edges to the graph
    for (int i = 0; i < co.size(); i++)
        instance.addEdge(co.get(i), cd.get(i), cw.get(i));

    // Verify the number of edges
    assertEquals(8, instance.numEdges(), "Number of edges should be 8");

    // Remove edges one by one and verify the updated number of edges
    for (int i = 0; i < co.size() - 1; i++) {
        instance.removeEdge(co.get(i), cd.get(i));
        Collection<Edge<String, Integer>> ced = instance.edges();
        int expected = co.size() - i - 1;
        assertEquals(expected, ced.size(), "Expected number of edges is " + expected);

        for (int j = i + 1; j < co.size(); j++) {
            ced.removeIf(e -> e.getVOrig().equals(co.get(j)) && e.getVDest().equals(cd.get(j)) && e.getWeight().equals(cw.get(j)));
        }
        assertEquals(0, ced.size(), "Expected remaining edges count is 0");
    }
}
```

**Objective:**
To validate the behavior of the `removeEdge()` method in the `MapGraph` class. This test ensures that the method correctly removes edges and updates the graph's edge count.

**Validations:**

1. **Edge Removal:** Ensure that the `removeEdge()` method removes edges correctly.
2. **Edge Count Update:** Verify that the number of edges decreases appropriately with each edge removal.

**Expected Result:**
The test should confirm that the `removeEdge()` method behaves as expected, removing edges and updating the graph’s structure accordingly.

---

## AlgorithmnsTest Class

### **Test : testBreadthFirstSearch_ValidVertex**

```java
@Test
    void testBreadthFirstSearch_ValidVertex() {
        LinkedList<String> bfsResult = Algorithms.BreadthFirstSearch(graph, "A");
        assertNotNull(bfsResult);
        assertEquals(4, bfsResult.size());
        assertEquals("A", bfsResult.getFirst());
    }
```
---

### **Test: testBreadthFirstSearch_ValidVertex**

```java
@Test
void testBreadthFirstSearch_ValidVertex() {
    LinkedList<String> bfsResult = Algorithms.BreadthFirstSearch(graph, "A");
    assertNotNull(bfsResult);
    assertEquals(4, bfsResult.size());
    assertEquals("A", bfsResult.getFirst());
}
```

### **Objective:**
To validate the behavior of the Breadth-First Search (BFS) algorithm when applied to a valid vertex in the graph. The test ensures that the algorithm correctly traverses the graph starting from a specified vertex and returns the expected results.

### **Validations:**

1. **Non-null Result:** Ensure that the result of the BFS is not null.
    - The test checks that the BFS algorithm returns a valid result (a non-null LinkedList).

2. **Correct Size of the Result:** Verify that the BFS traversal visits the correct number of vertices.
    - The test expects the BFS traversal to visit 4 vertices starting from vertex "A", so the resulting list should have 4 elements.

3. **Correct Starting Vertex:** Ensure that the BFS traversal starts from the specified vertex.
    - The test checks that the first vertex in the BFS result is "A", verifying that the traversal begins at the correct vertex.

### **Steps in the Test:**

1. **Initial Setup:**
    - The BFS algorithm is executed on the `graph` object, starting from the vertex "A".

2. **Assertions:**
    - The first assertion checks that the BFS result is not null (`assertNotNull(bfsResult)`).
    - The second assertion checks that the BFS traversal includes 4 vertices (`assertEquals(4, bfsResult.size())`).
    - The third assertion ensures that the BFS traversal starts from the vertex "A" (`assertEquals("A", bfsResult.getFirst())`).

### **Expected Result:**
- The BFS algorithm should return a non-null result.
- The traversal should visit exactly 4 vertices in the graph.
- The first vertex in the BFS result should be "A", confirming the correct starting point for the traversal.

---


### **Test: testBreadthFirstSearch_InvalidVertex**

```java
@Test
void testBreadthFirstSearch_InvalidVertex() {
    assertNull(Algorithms.BreadthFirstSearch(graph, "Z"));
}
```

### **Objective:**
To validate the behavior of the Breadth-First Search (BFS) algorithm when an invalid vertex is provided. This test ensures that the algorithm handles the case where the starting vertex does not exist in the graph.

### **Validations:**

1. **Correct Handling of Invalid Vertex:** Ensure that when an invalid vertex ("Z") is provided, the BFS algorithm returns null.
    - The test checks that the algorithm returns null when it is asked to perform BFS starting from a non-existent vertex.

### **Steps in the Test:**

1. **Initial Setup:**
    - The BFS algorithm is executed on the `graph` object, starting from the vertex "Z", which is assumed to be invalid or non-existent in the graph.

2. **Assertion:**
    - The assertion checks that the result of the BFS algorithm is null (`assertNull()`).

### **Expected Result:**
- The BFS algorithm should return null when asked to start from an invalid vertex.

---

### **Test: testDepthFirstSearch_ValidVertex**

```java
@Test
void testDepthFirstSearch_ValidVertex() {
    LinkedList<String> dfsResult = Algorithms.DepthFirstSearch(graph, "A");
    assertNotNull(dfsResult);
    assertEquals(4, dfsResult.size());
    assertTrue(dfsResult.contains("A"));
}
```

### **Objective:**
To validate the behavior of the Depth-First Search (DFS) algorithm when applied to a valid vertex in the graph. This test ensures that the DFS algorithm correctly traverses the graph and includes the expected vertices.

### **Validations:**

1. **Non-null Result:** Ensure that the result of the DFS is not null.
    - The test checks that the DFS algorithm returns a valid result (a non-null LinkedList).

2. **Correct Size of the Result:** Verify that the DFS traversal visits the correct number of vertices.
    - The test expects the DFS traversal to visit 4 vertices starting from vertex "A", so the resulting list should have 4 elements.

3. **Vertex Presence in the Result:** Ensure that the starting vertex is included in the DFS result.
    - The test checks that the starting vertex ("A") is present in the DFS result (`assertTrue(dfsResult.contains("A"))`).

### **Steps in the Test:**

1. **Initial Setup:**
    - The DFS algorithm is executed on the `graph` object, starting from the vertex "A".

2. **Assertions:**
    - The first assertion checks that the DFS result is not null (`assertNotNull(dfsResult)`).
    - The second assertion checks that the DFS traversal includes 4 vertices (`assertEquals(4, dfsResult.size())`).
    - The third assertion checks that the starting vertex ("A") is included in the DFS result (`assertTrue(dfsResult.contains("A"))`).

### **Expected Result:**
- The DFS algorithm should return a non-null result.
- The traversal should visit exactly 4 vertices in the graph.
- The first vertex in the DFS result should be "A", confirming the correct starting point for the traversal.

---

### **Test: testDepthFirstSearch_InvalidVertex**

```java
@Test
void testDepthFirstSearch_InvalidVertex() {
    assertNull(Algorithms.DepthFirstSearch(graph, "Z"));
}
```

### **Objective:**
To validate the behavior of the Depth-First Search (DFS) algorithm when an invalid vertex is provided. This test ensures that the algorithm handles the case where the starting vertex does not exist in the graph.

### **Validations:**

1. **Correct Handling of Invalid Vertex:** Ensure that when an invalid vertex ("Z") is provided, the DFS algorithm returns null.
    - The test checks that the algorithm returns null when it is asked to perform DFS starting from a non-existent vertex.

### **Steps in the Test:**

1. **Initial Setup:**
    - The DFS algorithm is executed on the `graph` object, starting from the vertex "Z", which is assumed to be invalid or non-existent in the graph.

2. **Assertion:**
    - The assertion checks that the result of the DFS algorithm is null (`assertNull()`).

### **Expected Result:**
- The DFS algorithm should return null when asked to start from an invalid vertex.

---

### **Test: testGetTopologicalOrder_ValidGraph**

```java
@Test
void testGetTopologicalOrder_ValidGraph() {
    graph = new MapGraph<>(true); // Directed graph

    graph.addEdge("A", "B", 1);
    graph.addEdge("B", "C", 2);
    graph.addEdge("C", "D", 3);

    LinkedList<String> topologicalOrder = Algorithms.getTopologicalOrder(graph);

    assertNotNull(topologicalOrder);
    assertEquals(4, topologicalOrder.size());
    assertEquals("A", topologicalOrder.getFirst());
    assertEquals("D", topologicalOrder.getLast());
}
```

### **Objective:**
To validate the behavior of the `getTopologicalOrder()` algorithm on a directed acyclic graph (DAG). This test ensures that the algorithm computes the topological order correctly for a valid graph.

### **Validations:**

1. **Non-null Result:** Ensure that the result of the topological order is not null.
    - The test checks that the algorithm returns a non-null list (`assertNotNull(topologicalOrder)`).

2. **Correct Number of Elements:** Verify that the topological order includes all vertices.
    - The test expects 4 elements in the topological order list, corresponding to the vertices in the graph.

3. **Correct Topological Order:** Ensure that the topological order starts with the first vertex ("A") and ends with the last vertex ("D").
    - The test checks the first and last elements of the topological order (`assertEquals("A", topologicalOrder.getFirst())`, `assertEquals("D", topologicalOrder.getLast())`).

### **Steps in the Test:**

1. **Initial Setup:**
    - A directed graph (`MapGraph<>(true)`) is created.
    - The edges `A -> B`, `B -> C`, and `C -> D` are added to the graph.

2. **Assertions:**
    - The first assertion checks that the topological order is not null.
    - The second assertion verifies that the topological order contains 4 vertices.
    - The third and fourth assertions check that the topological order starts with "A" and ends with "D".

### **Expected Result:**
- The topological order should not be null.
- The order should contain exactly 4 vertices, starting with "A" and ending with "D".

---

### **Test: testHasCircularDependencies_WithCycle**

```java
@Test
void testHasCircularDependencies_WithCycle() {
    assertTrue(Algorithms.hasCircularDependencies(graph)); // The undirected graph contains a cycle
}
```

### **Objective:**
To validate the behavior of the `hasCircularDependencies()` algorithm when the graph contains a cycle. This test ensures that the algorithm correctly detects circular dependencies in a graph.

### **Validations:**

1. **Cycle Detection:** Ensure that the algorithm correctly identifies the presence of a cycle in the graph.
    - The test expects the algorithm to return `true` when a cycle is present in the graph.

### **Steps in the Test:**

1. **Initial Setup:**
    - The graph is assumed to have a cycle (undirected graph in this case).
    - The `hasCircularDependencies()` algorithm is executed on the graph.

2. **Assertion:**
    - The assertion checks that the algorithm returns `true`, indicating the presence of a cycle.

### **Expected Result:**
- The algorithm should return `true`, confirming the existence of circular dependencies (a cycle) in the graph.

---

### **Test: testHasCircularDependencies_WithoutCycle**

```java
@Test
void testHasCircularDependencies_WithoutCycle() {
    graph = new MapGraph<>(true); // Directed graph

    graph.addEdge("A", "B", 1);
    graph.addEdge("B", "C", 2);
    graph.addEdge("C", "D", 3);

    assertFalse(Algorithms.hasCircularDependencies(graph)); // This graph has no cycle
}
```

### **Objective:**
To validate the behavior of the `hasCircularDependencies()` algorithm when the graph does not contain a cycle. This test ensures that the algorithm correctly identifies the absence of circular dependencies.

### **Validations:**

1. **Cycle Absence Detection:** Ensure that the algorithm correctly identifies the absence of a cycle in the graph.
    - The test expects the algorithm to return `false` when no cycle is present in the graph.

### **Steps in the Test:**

1. **Initial Setup:**
    - A directed graph (`MapGraph<>(true)`) is created.
    - The edges `A -> B`, `B -> C`, and `C -> D` are added to the graph, which does not contain any cycles.

2. **Assertion:**
    - The assertion checks that the algorithm returns `false`, indicating the absence of a cycle.

### **Expected Result:**
- The algorithm should return `false`, confirming that there are no circular dependencies (no cycle) in the graph.

---

### **Test: testMinDistGraph_ValidGraph**

```java
@Test
void testMinDistGraph_ValidGraph() {
    Comparator<Integer> comparator = Integer::compareTo;
    MatrixGraph<String, Integer> minGraph = Algorithms.minDistGraph(
            graph,
            comparator,
            Integer::sum
    );

    assertNotNull(minGraph);
    assertEquals(graph.numVertices(), minGraph.numVertices());
}
```

### **Objective:**
To validate the behavior of the `minDistGraph()` algorithm on a valid graph. This test ensures that the algorithm computes the minimum distance graph correctly, and that the result has the same number of vertices as the original graph.

### **Validations:**

1. **Non-null Result:** Ensure that the resulting graph is not null.
    - The test checks that the `minDistGraph()` method returns a non-null graph (`assertNotNull(minGraph)`).

2. **Same Number of Vertices:** Verify that the resulting minimum distance graph has the same number of vertices as the original graph.
    - The test ensures that the number of vertices in both the original graph and the result are equal (`assertEquals(graph.numVertices(), minGraph.numVertices())`).

### **Steps in the Test:**

1. **Initial Setup:**
    - A `Comparator<Integer>` is defined for comparing integer values.
    - The `minDistGraph()` algorithm is executed on the graph with the provided comparator and a sum function for edge weights.

2. **Assertions:**
    - The first assertion checks that the minimum distance graph is not null.
    - The second assertion verifies that the number of vertices in the original graph and the minimum distance graph are equal.

### **Expected Result:**
- The algorithm should return a non-null minimum distance graph that has the same number of vertices as the original graph.

---

### **Test: testMinDistGraph_EmptyGraph**

```java
@Test
void testMinDistGraph_EmptyGraph() {
    Graph<String, Integer> emptyGraph = new MapGraph<>(true);

    Comparator<Integer> comparator = Integer::compareTo;
    MatrixGraph<String, Integer> minGraph = Algorithms.minDistGraph(
            emptyGraph,
            comparator,
            Integer::sum
    );

    assertNotNull(minGraph);
    assertEquals(0, minGraph.numVertices());
}
```

### **Objective:**
To validate the behavior of the `minDistGraph()` algorithm when the input graph is empty. This test ensures that the algorithm handles an empty graph correctly, returning a graph with zero vertices.

### **Validations:**

1. **Non-null Result:** Ensure that the resulting graph is not null.
    - The test checks that the `minDistGraph()` method returns a non-null graph (`assertNotNull(minGraph)`).

2. **Zero Vertices:** Verify that the resulting graph has no vertices, as the input graph is empty.
    - The test ensures that the number of vertices in the result is zero (`assertEquals(0, minGraph.numVertices())`).

### **Steps in the Test:**

1. **Initial Setup:**
    - An empty graph (`MapGraph<>(true)`) is created.
    - A `Comparator<Integer>` is defined for comparing integer values.
    - The `minDistGraph()` algorithm is executed on the empty graph with the provided comparator and a sum function for edge weights.

2. **Assertions:**
    - The first assertion checks that the minimum distance graph is not null.
    - The second assertion verifies that the number of vertices in the result is zero.

### **Expected Result:**
- The algorithm should return a non-null minimum distance graph with zero vertices, as the input graph is empty.

---

