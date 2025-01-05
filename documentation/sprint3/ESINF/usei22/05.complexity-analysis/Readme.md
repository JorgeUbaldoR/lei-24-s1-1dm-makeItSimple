# USEI22 - Critical Path Calculation

### **calculateCriticalPath Method Complexity Analysis**

---

### **1. Method Overview**
The `calculateCriticalPath` method determines the critical path and total duration in a project graph. It processes vertices, filters valid activities, and identifies paths with zero slack to find the longest sequence.

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
- **Time Complexity**: Iterates through all vertices `O(V)`, with constant checks and additions.

#### **Find Critical Path**
```java
for (Activity activity : filteredActivities) {
    if (activity.getSlack() == 0 && !activity.getPredecessors().isEmpty()) {
```
- **Time Complexity**: Iterates through filtered activities `O(V)`.

##### **Path Construction Loop**
```java
List<Activity> path = new ArrayList<>();
double pathDuration = 0;

Activity current = activity;
while (current != null) {
    path.add(current);
    pathDuration += current.getDuration();
```
- **Time Complexity**: Inner loop traverses predecessors, potentially visiting each activity once. Worst case: `O(V)`.

##### **Next Activity Search**
```java
Activity next = null;
for (Activity a : filteredActivities) {
    if (a.getSlack() == 0 && a.getPredecessors().contains(current.getId())) {
        next = a;
        break;
    }
}
```
- **Time Complexity**: Nested loop checks predecessors for each activity, resulting in `O(V^2)` in the worst case.

##### **Longest Path Update**
```java
if (pathDuration > totalDuration) {
    criticalPath = path;
    totalDuration = pathDuration;
}
```
- **Time Complexity**: `O(1)` for comparison and assignment.

---

### **3. Error Handling**
```java
} catch (Exception e) {
    System.err.println("Error calculating critical path: " + e.getMessage());
```
- **Time Complexity**: `O(1)` for error handling.

---

### **4. Return Result**
```java
result.put("criticalPath", criticalPath);
result.put("totalDuration", totalDuration);
```
- **Time Complexity**: `O(1)` for inserting values into the map.

---

### **5. Final Complexity Summary**

| Section                            | Time Complexity |
|------------------------------------|-----------------|
| Input Validation                   | `O(V)`          |
| Filter Valid Activities            | `O(V)`          |
| Path Construction                  | `O(V^2)`        |
| Error Handling                     | `O(1)`          |
| Return Result                      | `O(1)`          |
| **Total Complexity**               | **`O(V^2)`**    |

---

### **6. Conclusion**
- **Efficiency**: The method efficiently handles filtering and path construction, but its nested loops lead to quadratic time complexity, making it less suitable for extremely large graphs.
- **Scalability**: Suitable for moderate-sized graphs but may require optimization for larger datasets.
- **Error Handling**: Provides basic error handling but could benefit from detailed logging for debugging purposes.

