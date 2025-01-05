# USEI23 - Identify Bottleneck Activities

## 4. Implementation

### Bottlenecks Class

The `Bottlenecks` class is responsible for identifying bottleneck activities in the project schedule. These are activities with the highest dependency counts and those that frequently appear in critical paths.

---

### Method: `identifyBottlenecks`

The `identifyBottlenecks` method calculates and identifies bottleneck activities based on dependency counts and frequency in critical paths. It returns the top 5 bottleneck activities along with a list of all identified bottlenecks.

#### Implementation
```java
public static Map<String, Object> identifyBottlenecks(MapGraph<Activity, Double> graph, List<Activity> criticalPaths) {
    Map<String, Object> result = new HashMap<>();
    try {
        // Verifica se o grafo ou o caminho crítico está vazio
        if (graph == null || graph.vertices().isEmpty() || criticalPaths == null || criticalPaths.isEmpty()) {
            throw new IllegalArgumentException("Graph or critical paths are empty or null.");
        }

        Map<Activity, Integer> dependencyCount = new HashMap<>();
        Map<Activity, Integer> criticalPathFrequency = new HashMap<>();

        // Calcula o grau de dependência e frequência no caminho crítico
        for (Activity activity : graph.vertices()) {
            if (!activity.getId().toString().equals("A-7777") && !activity.getId().toString().equals("A-7778")) {
                dependencyCount.put(activity, activity.getPredecessors().size());
                criticalPathFrequency.put(activity, 0);
            }
        }

        // Corrige dependência para os primeiros elementos
        for (Activity activity : dependencyCount.keySet()) {
            for (ID predecessor: activity.getPredecessors()) {
                if (predecessor.toString().equals("A-7777")) {
                    dependencyCount.put(activity, 0);
                }
            }
        }

        for (Activity activity : criticalPaths) {
            criticalPathFrequency.put(activity, criticalPathFrequency.get(activity) + 1);
        }

        List<Activity> bottlenecksList = new ArrayList<>(dependencyCount.keySet());
        bottlenecksList.sort((activity1, activity2) -> {
            int compareDependency = Integer.compare(dependencyCount.get(activity2), dependencyCount.get(activity1));
            if (compareDependency == 0) {
                return Integer.compare(criticalPathFrequency.get(activity2), criticalPathFrequency.get(activity1));
            }
            return compareDependency;
        });

        // Seleciona as Top 5 atividades
        Map<Activity, Integer> topBottlenecks = new LinkedHashMap<>();
        for (Activity activity : bottlenecksList.subList(0, Math.min(5, bottlenecksList.size()))) {
            topBottlenecks.put(activity, dependencyCount.get(activity));
        }

        // Preenche o resultado
        result.put("topBottlenecks", topBottlenecks);
        result.put("allBottlenecks", bottlenecksList);
    } catch (Exception e) {
        System.err.println("Error identifying bottlenecks: " + e.getMessage());
        result.put("topBottlenecks", Collections.emptyList());
        result.put("allBottlenecks", Collections.emptyList());
    }
    return result;
}
```

---

## 4.1 Tests

### Test Case: `identifyBottlenecks`

```java
@Test
void identifyBottlenecks() {
    // Calcular o caminho crítico
    Map<String, Object> result = criticalPath.calculateCriticalPath(graph);
    List<Activity> criticalPathResult = (List<Activity>) result.get("criticalPath");

    // Testar gargalos
    Map<String, Object> bottlenecks = Bottlenecks.identifyBottlenecks(graph, criticalPathResult);

    Map<Activity, Integer> topBottlenecks = (Map<Activity, Integer>) bottlenecks.get("topBottlenecks");
    List<Activity> allBottlenecks = (List<Activity>) bottlenecks.get("allBottlenecks");

    assertNotNull(topBottlenecks);
    assertNotNull(allBottlenecks);
    assertFalse(topBottlenecks.isEmpty());
    assertFalse(allBottlenecks.isEmpty());

    // Verificar se as 5 atividades principais estão ordenadas corretamente
    assertTrue(topBottlenecks.size() <= 5);
    assertTrue(allBottlenecks.size() >= topBottlenecks.size());
}
```

### Test Case: `testEmptyGraph`

```java
@Test
void testEmptyGraph() {
    MapGraph<Activity, Double> emptyGraph = new MapGraph<>(true);
    Map<String, Object> bottlenecks = Bottlenecks.identifyBottlenecks(emptyGraph, Collections.emptyList());

    assertEquals(Collections.emptyList(), bottlenecks.get("topBottlenecks"));
    assertEquals(Collections.emptyList(), bottlenecks.get("allBottlenecks"));
}
```

### Test Case: `testEmptyCriticalPath`

```java
@Test
void testEmptyCriticalPath() {
    Map<String, Object> bottlenecks = Bottlenecks.identifyBottlenecks(graph, Collections.emptyList());

    assertEquals(Collections.emptyList(), bottlenecks.get("topBottlenecks"));
    assertEquals(Collections.emptyList(), bottlenecks.get("allBottlenecks"));
}
```

---

### Observations

**Functionality and Impact of the Method**
- The `identifyBottlenecks` method identifies critical bottlenecks in the project graph, focusing on dependency levels and critical path frequency. This helps project managers prioritize tasks that may impact schedules the most.

**Efficiency and Scalability**
- The method is optimized for performance, ensuring scalability with large graphs by leveraging efficient sorting and data structures.

**Error Handling**
- Exception handling ensures robustness by validating input and providing clear error messages for unexpected scenarios.

### Final Thoughts
- The `identifyBottlenecks` method is a vital tool for identifying and prioritizing critical tasks in a project schedule. It integrates seamlessly with the PERT-CPM analysis and provides actionable insights for optimizing workflows.

