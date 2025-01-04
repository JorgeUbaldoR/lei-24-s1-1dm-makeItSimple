# USEI18 - Detect Circular Dependencies

## 4. Tests

## ActivityReaderTest Class

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

## AlgorithmsTest Class
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


