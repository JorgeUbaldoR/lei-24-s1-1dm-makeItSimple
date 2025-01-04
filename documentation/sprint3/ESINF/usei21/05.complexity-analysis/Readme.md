# USEI21 - Export Project Schedule

### **ProjectSchedule Class Complexity Analysis**

---

### **1. `sendProjectScheduleToFile`**

The `sendProjectScheduleToFile` method is responsible for exporting the project schedule to a CSV file, including
activity details and their dependencies. Let’s break down its complexity step by step.

#### **Operations:**

1. **Calculate Schedule Analysis**:

- Calls `calculateScheduleAnalysis()`, which has been previously analyzed with a complexity of `O(V + E)`.

2. **File Writing with PrintWriter**:

- **File Creation**: The method creates a `PrintWriter` to write data to a CSV file. The creation of a `PrintWriter` is
  a constant-time operation, `O(1)`.
- **Writing Header**: The header line is written once, which takes constant time: `O(1)`.

3. **Writing Activity Data**:

- The loop iterates over `verticesList` (activities), writing data for each activity. The size of `verticesList`
  is `V` (the number of activities), so this part will iterate `V` times. For each activity, several operations are
  performed:
    - Writing activity details (ID, cost, duration, timings) is a constant-time operation for each activity: `O(1)`.
    - Iterating through the activity's predecessors (`getPredecessors`) is done for each activity. In the worst case,
      each activity can have up to `V` predecessors, leading to `O(V)` complexity for the predecessor iteration.

Therefore, the time complexity for writing activity data for all activities becomes `O(V * p)`, where `p` is the number
of predecessors per activity. In the worst case, this becomes `O(V^2)`.

4. **Writing Special Activities (Start and Finish)**:

- Writing the start and finish activities is a constant-time operation (`O(1)`), as they are handled separately from the
  main loop.

5. **Closing the PrintWriter**:

- Closing the `PrintWriter` is a constant-time operation: `O(1)`.

#### **Complexity Analysis**:

**Time Complexity**:

- **File writing operations**: The loop over `verticesList` involves iterating over `V` activities and writing their
  details. The worst case for writing each activity involves iterating over all its predecessors, so the time complexity
  is:
    - `O(V + V * p)` or `O(V^2)` in the worst case.
    - The calculation of schedule analysis is `O(V + E)`, but this has already been handled separately.

Thus, the overall time complexity of the method is:

- **Time Complexity**: `O(V^2)` (due to iterating over predecessors for each activity).

**Space Complexity**:

- The space complexity is determined by the storage of the activity data and the temporary storage used by
  the `PrintWriter`.
- Since the method only writes to a file and does not store additional large data structures beyond the existing project
  schedule, the space complexity is:
    - **Space Complexity**: `O(1)` (for file writing, as no additional large data structures are created during the
      export).

---

### **Final Complexity Table**

| Method                      | Time Complexity | Space Complexity |
|-----------------------------|-----------------|------------------|
| `sendProjectScheduleToFile` | `O(V^2)`        | `O(1)`           |

---

### **Conclusions**

- **Efficiency**: The method is efficient in terms of space, but its time complexity can become quadratic (`O(V^2)`) in
  the worst case due to the iteration over each activity's predecessors. However, for typical project schedules with
  fewer dependencies, this should perform well.

- **Scalability**: As the number of activities (`V`) increases, the method's time complexity grows quadratically, which
  may impact performance for very large projects with numerous dependencies. Optimizing the handling of predecessors
  could reduce this complexity in future improvements.

- **Error Handling**: The method includes basic error handling for file writing issues, which improves its robustness.
  However, further improvements in error reporting and logging could enhance its reliability in larger-scale or
  production environments.

In conclusion, while the `sendProjectScheduleToFile` method provides valuable functionality for exporting project
schedules, attention should be paid to its scalability with large numbers of activities and dependencies.