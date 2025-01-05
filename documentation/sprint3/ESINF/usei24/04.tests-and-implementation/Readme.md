# USEI24- Simulate Project Delays and their Impact

## 4. Tests

## Topological Sort Class

### **Test : testAddEdge**

```java
@Test
    void testAddEdge() {
            TopologicalSort topologicalSort = new TopologicalSort();

            topologicalSort.addEdge("A1", "A2");
            topologicalSort.addEdge("A2", "A3");
            topologicalSort.addEdge("A1", "A3");

            Map<String, List<String>> expectedGraph = new HashMap<>();
        expectedGraph.put("A1", Arrays.asList("A2", "A3"));
        expectedGraph.put("A2", Arrays.asList("A3"));
        expectedGraph.put("A3", new ArrayList<>());

        Map<String, Integer> expectedInDegree = new HashMap<>();
        expectedInDegree.put("A1", 0); // No incoming edges
        expectedInDegree.put("A2", 1); // One incoming edge (A1 -> A2)
        expectedInDegree.put("A3", 2); // Two incoming edges (A1 -> A3, A2 -> A3)

        assertEquals(expectedGraph, topologicalSort.graph);

        assertEquals(expectedInDegree, topologicalSort.inDegree);
        }
```

**Objective:** To verify that edges are properly added

**Expected Result:** The created graph does indeed have the added edges

### **Test : testPerformTopologicalSortWithIDs**

```java
@Test
    void testPerformTopologicalSortWithIDs() {
            TopologicalSort topologicalSort = new TopologicalSort();
            MapGraph<Activity, Double> mapGraph = new MapGraph<>(true);

        Activity activityA1 = new Activity(new ID(1, TypeID.ACTIVITY), "Task A1");
        Activity activityA2 = new Activity(new ID(2, TypeID.ACTIVITY), "Task A2");
        Activity activityA3 = new Activity(new ID(3, TypeID.ACTIVITY), "Task A3");
        Activity activityA4 = new Activity(new ID(4, TypeID.ACTIVITY), "Task A4");

        mapGraph.addVertex(activityA1);
        mapGraph.addVertex(activityA2);
        mapGraph.addVertex(activityA3);
        mapGraph.addVertex(activityA4);

        mapGraph.addEdge(activityA1, activityA2, 1.0); // A1 -> A2
        mapGraph.addEdge(activityA1, activityA3, 1.0); // A1 -> A3
        mapGraph.addEdge(activityA2, activityA4, 1.0); // A2 -> A4
        mapGraph.addEdge(activityA3, activityA4, 1.0); // A3 -> A4

        List<String> sortedOrder = topologicalSort.performTopologicalSort(mapGraph);

        List<String> expectedOrder = Arrays.asList("A-1", "A-2", "A-3", "A-4");

        assertEquals(expectedOrder, sortedOrder);
        }
```

**Objective:** To verify that the list is properly sorted

**Expected Result:** The created list does indeed have the right order

### **Test : testConvertMapGraphToAdjacencyListWithIDs**

```java
@Test
    void testConvertMapGraphToAdjacencyListWithIDs() {
            TopologicalSort topologicalSort = new TopologicalSort();
            MapGraph<Activity, Double> mapGraph = new MapGraph<>(true);

        Activity activityA1 = new Activity(new ID(1, TypeID.ACTIVITY), "Task A1");
        Activity activityA2 = new Activity(new ID(2, TypeID.ACTIVITY), "Task A2");
        Activity activityA3 = new Activity(new ID(3, TypeID.ACTIVITY), "Task A3");

        mapGraph.addVertex(activityA1);
        mapGraph.addVertex(activityA2);
        mapGraph.addVertex(activityA3);

        mapGraph.addEdge(activityA1, activityA2, 1.0); // A1 -> A2
        mapGraph.addEdge(activityA2, activityA3, 1.0); // A2 -> A3

        Map<String, List<String>> adjacencyList = topologicalSort.convertMapGraphToAdjacencyList(mapGraph);

        Map<String, List<String>> expected = new HashMap<>();
        expected.put("A-1", Arrays.asList("A-2"));
        expected.put("A-2", Arrays.asList("A-3"));
        expected.put("A-3", new ArrayList<>());

        assertEquals(expected, adjacencyList);
        }
```

**Objective:** To verify that the adjacency list is created

**Expected Result:** The created adjacency list does indeed have the right elements

---
