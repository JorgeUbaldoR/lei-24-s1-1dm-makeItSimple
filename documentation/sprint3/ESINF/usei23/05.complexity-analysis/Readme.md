# USEI23 - Identify Bottleneck Activities

## 4. Implementation

### Bottlenecks Class

The `Bottlenecks` class is responsible for identifying bottleneck activities in the project schedule. These are activities with the highest dependency counts and those that frequently appear in critical paths.

---

### Method: `identifyBottlenecks`

The `identifyBottlenecks` method calculates and identifies bottleneck activities based on dependency counts and frequency in critical paths. It returns the top 5 bottleneck activities along with a list of all identified bottlenecks.

---

## Complexity Analysis

### **Step-by-Step Complexity Analysis**

1. **Input Validation**
```java
if (graph == null || graph.vertices().isEmpty() || criticalPaths == null || criticalPaths.isEmpty()) {
    throw new IllegalArgumentException("Graph or critical paths are empty or null.");
}
```
- **Time Complexity**: `O(1)` - Constant time check for null or empty inputs.

2. **Initialization of HashMaps**
```java
Map<Activity, Integer> dependencyCount = new HashMap<>();
Map<Activity, Integer> criticalPathFrequency = new HashMap<>();
```
- **Time Complexity**: `O(1)` - Initialization of empty hash maps.

3. **Dependency Count and Frequency Calculation**
```java
for (Activity activity : graph.vertices()) {
    if (!activity.getId().toString().equals("A-7777") && !activity.getId().toString().equals("A-7778")) {
        dependencyCount.put(activity, activity.getPredecessors().size());
        criticalPathFrequency.put(activity, 0);
    }
}
```
- **Time Complexity**: `O(V)` - Iterates through all vertices in the graph.

4. **Correct Dependency for First Elements**
```java
for (Activity activity : dependencyCount.keySet()) {
    for (ID predecessor: activity.getPredecessors()) {
        if (predecessor.toString().equals("A-7777")) {
            dependencyCount.put(activity, 0);
        }
    }
}
```
- **Time Complexity**: `O(V * P)` - Iterates through all activities and their predecessors (`P` = max predecessors per activity).

5. **Update Critical Path Frequency**
```java
for (Activity activity : criticalPaths) {
    criticalPathFrequency.put(activity, criticalPathFrequency.get(activity) + 1);
}
```
- **Time Complexity**: `O(C)` - Iterates through all critical path activities.

6. **Sorting Activities by Dependency and Frequency**
```java
List<Activity> bottlenecksList = new ArrayList<>(dependencyCount.keySet());
bottlenecksList.sort((activity1, activity2) -> {
    int compareDependency = Integer.compare(dependencyCount.get(activity2), dependencyCount.get(activity1));
    if (compareDependency == 0) {
        return Integer.compare(criticalPathFrequency.get(activity2), criticalPathFrequency.get(activity1));
    }
    return compareDependency;
});
```
- **Time Complexity**: `O(V log V)` - Sorting based on dependency count and frequency.

7. **Select Top 5 Activities**
```java
Map<Activity, Integer> topBottlenecks = new LinkedHashMap<>();
for (Activity activity : bottlenecksList.subList(0, Math.min(5, bottlenecksList.size()))) {
    topBottlenecks.put(activity, dependencyCount.get(activity));
}
```
- **Time Complexity**: `O(1)` - Selection of top 5 activities.

8. **Return Results**
```java
result.put("topBottlenecks", topBottlenecks);
result.put("allBottlenecks", bottlenecksList);
```
- **Time Complexity**: `O(1)` - Constant time assignment to result.

---

## Final Complexity Summary

| Step                                  | Time Complexity      |
|---------------------------------------|----------------------|
| Input Validation                      | `O(1)`               |
| Initialization of HashMaps            | `O(1)`               |
| Dependency Count and Frequency        | `O(V)`               |
| Correct Dependency for First Elements | `O(V * P)`           |
| Update Critical Path Frequency        | `O(C)`               |
| Sorting Activities                    | `O(V log V)`         |
| Select Top 5 Activities               | `O(1)`               |
| Return Results                        | `O(1)`               |

### Overall Complexity
- **Time Complexity**: `O(V * P + V log V)`
- **Space Complexity**: `O(V)`

---

## Observations

**Functionality and Impact of the Method**
- The `identifyBottlenecks` method identifies critical bottlenecks in the project graph, focusing on dependency levels and critical path frequency. This helps project managers prioritize tasks that may impact schedules the most.

**Efficiency and Scalability**
- The method performs well for medium-sized graphs but can scale quadratically when the number of predecessors per activity is high. Future optimizations could target reducing dependency checks.

**Error Handling**
- Exception handling ensures robustness by validating input and providing clear error messages for unexpected scenarios.

### Final Thoughts
- The `identifyBottlenecks` method is a vital tool for identifying and prioritizing critical tasks in a project schedule. It integrates seamlessly with the PERT-CPM analysis and provides actionable insights for optimizing workflows.

