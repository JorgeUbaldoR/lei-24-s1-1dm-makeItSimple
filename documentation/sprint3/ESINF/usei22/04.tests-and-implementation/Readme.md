# USEI22 - Generate Critical Path

## 4. Implementation

### CriticalPathAnalyzer Class

The `CriticalPathAnalyzer` class is responsible for analyzing the project graph and calculating the critical path, including the total duration. It evaluates scheduling metrics, identifies critical activities, and organizes them in sequence for further analysis or reporting.

---

### Method: `calculateCriticalPath`

The `calculateCriticalPath` method calculates the longest path through the project graph, identifying activities with zero slack and their dependencies. It outputs the critical path and its total duration. The method uses the following steps:

1. Checks if the graph is null or empty and throws an exception if invalid.
2. Filters valid activities, excluding start and finish nodes.
3. Iterates through activities to identify zero-slack paths.
4. Calculates the total duration of each valid path.
5. Returns the longest critical path and its duration.

Here’s the implementation of `calculateCriticalPath`:

```java
public Map<String, Object> calculateCriticalPath(MapGraph<Activity, Double> graph) {
    Map<String, Object> result = new HashMap<>();
    try {
        // Verifica se o grafo está vazio
        if (graph == null || graph.vertices().isEmpty()) {
            throw new IllegalArgumentException("Graph is empty or null.");
        }

        List<Activity> criticalPath = new ArrayList<>();
        double totalDuration = 0;

        // Filtrar atividades válidas (excluindo Start e Finish)
        List<Activity> filteredActivities = new ArrayList<>();
        for (Activity activity : graph.vertices()) {
            if (!activity.getId().toString().equals("A-7777") && !activity.getId().toString().equals("A-7778")) {
                filteredActivities.add(activity);
            }
        }

        // Identificar caminho crítico
        for (Activity activity : filteredActivities) {
            if (activity.getSlack() == 0 && !activity.getPredecessors().isEmpty()) { // Atividades válidas
                List<Activity> path = new ArrayList<>();
                double pathDuration = 0;

                Activity current = activity;
                while (current != null) {
                    path.add(current);
                    pathDuration += current.getDuration(); // Soma a duração

                    // Encontra o próximo na sequência simplificado sem stream
                    Activity next = null;
                    for (Activity a : filteredActivities) {
                        if (a.getSlack() == 0 && a.getPredecessors().contains(current.getId())) {
                            next = a;
                            break;
                        }
                    }
                    current = next;
                }

                // Se a duração do caminho for a mais longa até agora
                if (pathDuration > totalDuration) {
                    criticalPath = path; // Mantém apenas o caminho mais longo
                    totalDuration = pathDuration;
                }
            }
        }

        // Preenche o resultado
        result.put("criticalPath", criticalPath);
        result.put("totalDuration", totalDuration);
    } catch (Exception e) {
        System.err.println("Error calculating critical path: " + e.getMessage());
        result.put("criticalPath", Collections.emptyList());
        result.put("totalDuration", 0.0);
    }

    return result;
}
```

## 4.1 Tests

The **calculateCriticalPath** method is tested to ensure that it accurately calculates the critical path and total duration. Tests include scenarios for valid inputs, empty graphs, and error handling.

---

### Test Case: `calculateCriticalPath`

```java
@Test
void calculateCriticalPath() {
    Map<String, Object> result = criticalPath.calculateCriticalPath(graph);
    List<Activity> criticalPathResult = (List<Activity>) result.get("criticalPath");
    double totalDuration = (double) result.get("totalDuration");

    assertNotNull(criticalPathResult);
    assertFalse(criticalPathResult.isEmpty());
    assertEquals(16.0, totalDuration, 0.01); // Total esperado: 16 semanas

    // Verificar atividades específicas no caminho crítico
    List<String> expectedPath = List.of("A-2", "A-3", "A-6", "A-7", "A-9", "A-12");
    List<String> actualPath = new ArrayList<>();
    for (Activity activity : criticalPathResult) {
        actualPath.add(activity.getId().toString());
    }
    assertEquals(expectedPath, actualPath);
}
```
This test case validates that:
1. The critical path is correctly identified and matches expected activities.
2. The total duration matches the expected value.
3. Activities in the critical path follow the expected sequence.

### Test Case: `testEmptyGraph`

```java
@Test
void testEmptyGraph() {
    MapGraph<Activity, Double> emptyGraph = new MapGraph<>(true);
    Map<String, Object> result = criticalPath.calculateCriticalPath(emptyGraph);

    assertEquals(Collections.emptyList(), result.get("criticalPath"));
    assertEquals(0.0, result.get("totalDuration"));
}
```
This test ensures that when an empty graph is provided, the method returns an empty path and zero duration, confirming robustness against invalid inputs.

---

### Observations

**Functionality and Impact of the Method**
- The calculateCriticalPath method is a key feature for project analysis, providing insights into the sequence of activities that determine project completion time. It identifies activities with zero slack, enabling focused resource management.

**Efficiency and Scalability**
- The method efficiently filters and evaluates activities, ensuring scalability for larger graphs. The iterative process handles dependencies without excessive complexity, making it suitable for real-world scenarios.

**Error Handling**
- Error handling covers invalid graph inputs, such as null or empty graphs, providing informative error messages and default outputs. Additional enhancements could include logging frameworks for detailed debugging.

### Final Thoughts

- The calculateCriticalPath method is a fundamental component for project schedule analysis, enabling users to identify and address critical activities effectively.
- The associated tests validate its functionality under normal and edge-case conditions, ensuring reliability and robustness.
- As a result, this method enhances the project management toolkit, supporting better decision-making and planning through precise scheduling analysis.

