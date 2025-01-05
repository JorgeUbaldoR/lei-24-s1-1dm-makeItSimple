# USEI22 - Critical Path Calculation

### **calculateCriticalPath Method Complexity Analysis**

---

### **1. Method Overview**
The `calculateCriticalPath` method determines the critical path and total duration in a project graph by exploring paths starting from nodes connected to 'Start' and ending at nodes connected to 'Finish'. It uses stacks to simulate depth-first traversal and evaluates paths based on duration.

---

### **2. Line-by-Line Complexity Analysis**

#### **Input Validation**
```java
if (graph == null || graph.vertices().isEmpty()) {
    throw new IllegalArgumentException("Graph is empty or null.");
}
```
- **Time Complexity**: `O(1)` for null check and `O(V)` for checking if vertices are empty.

#### **Initialize Result Variables**
```java
List<Activity> criticalPath = new ArrayList<>();
double totalDuration = 0;
```
- **Time Complexity**: `O(1)` for initialization.

#### **Filter Valid Activities**
```java
List<Activity> filteredActivities = new ArrayList<>();
for (Activity activity : graph.vertices()) {
    if (!activity.getId().toString().equals("A-7777") && !activity.getId().toString().equals("A-7778")) {
        filteredActivities.add(activity);
    }
}
```
- **Time Complexity**: Iterates through all vertices, resulting in **`O(V)`**.

#### **Identify Start Nodes**
```java
List<Activity> startNodes = new ArrayList<>();
for (Activity activity : filteredActivities) {
    boolean isStartNode = false;
    for (ID id : activity.getPredecessors()) {
        if (id.toString().equals("A-7777")) {
            isStartNode = true;
            break;
        }
    }
    if (isStartNode) {
        startNodes.add(activity);
    }
}
```
- **Time Complexity**: Loops through filtered activities `O(V)` and checks predecessors for each activity, resulting in **`O(V * E)`**.

#### **Initialize Stacks for DFS**
```java
Deque<Activity> stack = new ArrayDeque<>();
Deque<Double> durationStack = new ArrayDeque<>();
Deque<List<Activity>> pathStack = new ArrayDeque<>();
```
- **Time Complexity**: `O(1)` for initialization.

#### **Explore Paths Using DFS**
```java
for (Activity start : startNodes) {
    stack.push(start);
    durationStack.push(0.0);
    pathStack.push(new ArrayList<>());

    while (!stack.isEmpty()) {
```
- **Outer Loop Complexity**: Each start node initiates a DFS, iterating through valid successors.
- Worst-case scenario processes all vertices and edges, leading to **`O(V + E)`**.

##### **Process Current Node**
```java
Activity current = stack.pop();
double pathDuration = durationStack.pop();
List<Activity> currentPath = new ArrayList<>(pathStack.pop());
```
- **Time Complexity**: Stack operations are **`O(1)`** each, repeated for all nodes, giving **`O(V)`** in total.

##### **Check End Nodes**
```java
boolean isEndNode = true;
for (ID id : current.getSuccessors()) {
    if (!id.toString().equals("A-7778")) {
        isEndNode = false;
        break;
    }
}
```
- **Time Complexity**: Checking successors for each node, contributing **`O(E)`** in total.

##### **Explore Valid Successors**
```java
for (Activity successor : filteredActivities) {
    if (successor.getSlack() == 0 && current.getSuccessors().contains(successor.getId())) {
        stack.push(successor);
        durationStack.push(pathDuration);
        pathStack.push(new ArrayList<>(currentPath));
    }
}
```
- **Time Complexity**: Nested loop through filtered activities and successor checks. Worst-case complexity is **`O(V^2)`**.

#### **Result Construction**
```java
result.put("criticalPath", criticalPath);
result.put("totalDuration", totalDuration);
```
- **Time Complexity**: `O(1)` for inserting values into the map.

---

### **3. Error Handling**
```java
} catch (Exception e) {
    System.err.println("Error calculating critical path: " + e.getMessage());
```
- **Time Complexity**: `O(1)` for error handling.

---

### **4. Final Complexity Summary**

| Section                            | Time Complexity |
|------------------------------------|-----------------|
| Input Validation                   | `O(V)`          |
| Filter Valid Activities            | `O(V)`          |
| Identify Start Nodes               | `O(V * E)`      |
| Initialize Stacks                  | `O(1)`          |
| DFS Path Exploration               | `O(V^2)`        |
| Error Handling                     | `O(1)`          |
| Result Construction                | `O(1)`          |
| **Total Complexity**               | **`O(V^2)`**    |

---

### **5. Conclusion**
- **Efficiency**: The updated method uses iterative DFS traversal with stacks, providing more clarity and control over path exploration. However, its nested loops and reliance on predecessor and successor checks result in **quadratic time complexity**.
- **Scalability**: Suitable for moderate-sized graphs but may struggle with very large datasets due to **`O(V^2)`** complexity.
- **Optimizations**: Possible improvements include using adjacency lists or hash maps for faster predecessor/successor lookups, reducing nested loop dependencies.
- **Error Handling**: Current error handling is basic and could benefit from structured logging or exception propagation.

