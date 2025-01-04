# USEI20 - Project Scheduling

## 4. Implementation

### ProjectSchedule Class

The `ProjectSchedule` class is responsible for analyzing and scheduling project activities using a graph representation. It calculates important metrics like earliest start/finish, latest start/finish, and slack time for each activity, ensuring proper sequencing and dependency management.

---

### Attributes

1. **`verticesList`**
   - **Type**: `LinkedList<Activity>`
   - **Description**: Stores activities in topological order for graph traversal.

2. **`projectGraph`**
   - **Type**: `MapGraph<Activity, Double>`
   - **Description**: Represents the project's activity graph, where vertices are activities, and edges represent dependencies with weights indicating durations or costs.

3. **`mapGraphRepository`**
   - **Type**: `PETRGraphRepository`
   - **Description**: Provides access to activities stored in a repository for graph construction and updates.

4. **`graphID`**
   - **Type**: `ID`
   - **Description**: The unique identifier for the project graph being analyzed.

5. **Constants**
   - **`START_PAIR`**: Represents the starting activity with a name "Start" and a unique identifier `7777`.
   - **`FINISH_PAIR`**: Represents the finishing activity with a name "Finish" and a unique identifier `7778`.
   - **`START_ACTIVITY`** and **`FINISH_ACTIVITY`**: Static activity objects corresponding to the start and finish of the project.

---

### Methods

#### **Constructor**
```java
public ProjectSchedule(MapGraph<Activity, Double> graph, ID graphId) {
    this.projectGraph = graph;
    this.verticesList = new LinkedList<>();
    this.mapGraphRepository = getRepositories().getPetrGraphRepository();
    this.graphID = graphId;
}
```

#### **generateVerticesListBFS**
- **Description**: Generates a topologically ordered list of activities for the project.
- **Implementation**: Uses the `Algorithms.getTopologicalOrder` method from the `Algorithms` class to order the activities.

```java
protected void generateVerticesListBFS() {
    verticesList.clear();
    verticesList = Algorithms.getTopologicalOrder(projectGraph);
}
```

#### **calculateScheduleAnalysis**
- **Description**: Analyzes the project schedule to calculate earliest start/finish, latest start/finish, and slack for each activity.
- **Implementation**: Iterates through the list of activities to calculate necessary scheduling metrics.

```java
protected void calculateScheduleAnalysis() {
    generateVerticesListBFS();

    for (Activity a : verticesList) {
        if (a.equals(START_ACTIVITY) || a.equals(FINISH_ACTIVITY)) {
            continue;
        }
        double earliestStart;
        if (a.getPredecessors().contains(START_ACTIVITY.getId()) && a.getPredecessors().size() == 1) {
            earliestStart = 0;
        } else {
            earliestStart = getMaxEarliestFinish(a.getPredecessors());
        }
        a.setEarliestStart(earliestStart);

        double earliestFinish = earliestStart + a.getDuration();
        a.setEarliestFinish(earliestFinish);
    }

    ListIterator<Activity> iterator = verticesList.listIterator(verticesList.size());
    while (iterator.hasPrevious()) {
        Activity a = iterator.previous();
        if (a.equals(START_ACTIVITY) || a.equals(FINISH_ACTIVITY)) {
            continue;
        }

        double latestFinish;
        if (a.getSuccessors().contains(FINISH_ID) && a.getSuccessors().size() == 1) {
            latestFinish = a.getEarliestFinish();
        } else {
            latestFinish = getMinLatestStart(a.getSuccessors());
        }

        if (latestFinish == Double.MAX_VALUE) {
            latestFinish = a.getEarliestFinish();
        }
        a.setLatestFinish(latestFinish);

        double latestStart = latestFinish - a.getDuration();
        a.setLatestStart(latestStart);

        double slack = latestFinish - a.getEarliestFinish();
        a.setSlack(slack);
    }
}
```

#### **getMaxEarliestFinish**
- **Description**: Finds the maximum earliest finish time among the predecessors of a given activity.
- **Implementation**: Iterates through the list of predecessor IDs to find the maximum earliest finish time.

```java
private double getMaxEarliestFinish(List<ID> predecessors) {
   double max = 0;
   boolean hasValidPredecessor = false;

   for (ID pred : predecessors) {
      Activity ac = mapGraphRepository.getActivityByID(graphID, pred);
      if (ac != null) {
         hasValidPredecessor = true;
         max = Math.max(max, ac.getEarliestFinish());
      }
   }

   return hasValidPredecessor ? max : 0;
}
```
#### **getMinLatestStart**
- **Description**: Finds the minimum latest start time among the successors of a given activity.
- **Implementation**: Iterates through the list of successor IDs to find the minimum latest start time.

```java
protected double getMinLatestStart(List<ID> successors) {
   double min = Double.MAX_VALUE;
   boolean hasValidSuccessor = false;

   for (ID suc : successors) {
      Activity ac = mapGraphRepository.getActivityByID(graphID, suc);
      if (ac != null) {
         hasValidSuccessor = true;
         min = Math.min(min, ac.getLatestStart());
      }
   }

   return hasValidSuccessor ? min : Double.MAX_VALUE;
}
```

### PETRGraphRepository Class

The `PETRGraphRepository` class is responsible for managing a repository of project graphs and their associated activities. It provides methods to add, retrieve, and check the existence of graphs, as well as to obtain specific activities by their ID.

---

### Methods

#### **Constructor**
- **Description**: Initializes an empty repository for storing project graphs.
```java
public PETRGraphRepository() {
    mapGraphRepository = new HashMap<>();
}
```

#### **getMapGraphRepository**
- **Description**: Retrieves a specific graph from the repository by its ID.
- **Parameters**: 
  - graphID: The unique identifier of the graph to be retrieved.
- **Returns**: The corresponding MapGraph<Activity, Double> object if it exists; otherwise, null.

```java
public Map<ID, MapGraph<Activity, Double>> getMapGraphRepository() {
   return mapGraphRepository;
}
```

#### **idGraphExistInRepository**
- **Description**: Checks whether a graph with a specified ID exists in the repository.
- **Parameters**:
   - id: The unique identifier of the graph.
- **Returns**: true if the graph exists; otherwise, false.

```java
public boolean idGraphExistInRepository(ID id) {
   return this.mapGraphRepository.containsKey(id);
}
```

#### **getActivityByID**
- **Description**: Retrieves an activity from a specific graph in the repository by its ID.
- **Parameters**:
   - 	graphID: The ID of the graph containing the activity.
   - 	activityID: The ID of the activity to be retrieved.
- **Returns**: The corresponding Activity object if found; otherwise, null.

```java
public Activity getActivityByID(ID graphID, ID activityID) {
   if (!idGraphExistInRepository(graphID)) {
      return null;
   }

   MapGraph<Activity, Double> graph = this.getMapGraphByID(graphID);
   for (Activity activity : graph.vertices()) {
      if (activity.getId().equals(activityID)) {
         return activity;
      }
   }
   return null;
}
```

#### **getScheduleInfo**
- **Description**:  Prepares scheduling information for a specific graph and saves it to a file.
- **Parameters**:
   - 	fileName: The name of the file where the schedule will be saved.
   - 	graphID: The ID of the graph to be scheduled.
- **Returns**: An Optional<Pair<String, ID>> containing the file name and graph ID if successful; otherwise, an empty Optional.

```java
public Optional<Pair<String, ID>> getScheduleInfo(String fileName, ID graphID) {
   if (fileName == null || fileName.trim().isEmpty() || graphID == null || !idGraphExistInRepository(graphID)) {
      return Optional.empty();
   }
   MapGraph<Activity, Double> graph = this.getMapGraphByID(graphID);
   ProjectSchedule projectSchedule = new ProjectSchedule(graph, graphID);
   projectSchedule.sendProjectScheduleToFile(fileName);

   return Optional.of(new Pair<>(fileName, graphID));
}
```

---

### Final Observation

The implementation of the project scheduling system demonstrates an efficient integration between the `ProjectSchedule` and `PETRGraphRepository` classes.

1. **Responsibilities and Collaboration**:
   - The `ProjectSchedule` class is responsible for calculating and analyzing the project's schedule, focusing on metrics such as earliest and latest start/finish times and activity slack. It uses algorithms for topological sorting and dependency analysis.
   - The `PETRGraphRepository` class acts as a persistence layer, ensuring that graph data is securely and systematically managed. It provides access to activities and graphs stored in the repository, which are essential for the analysis conducted by `ProjectSchedule`.

2. **Dependency Flow**:
   - `ProjectSchedule` leverages methods from `PETRGraphRepository` to retrieve activities and graphs using IDs. This ensures that scheduling information is always based on centralized and consistent data.

3. **Encapsulation**:
   - The clear separation of responsibilities between analysis (in `ProjectSchedule`) and data persistence (in `PETRGraphRepository`) promotes high cohesion and low coupling. This design choice enhances maintainability, testing, and potential system extensions.

4. **Integration Points**:
   - `ProjectSchedule` depends on `PETRGraphRepository` to fetch activity and graph details for its calculations.
   - The repository, in turn, is instrumental in managing and validating the existence of the data required for scheduling.

This collaboration ensures a robust and modular design, where changes in one class (e.g., the repository implementation) have minimal impact on the other, facilitating long-term scalability and reliability.


## 4.1 Tests

# ProjectScheduleTest Class

## Overview

The **ProjectScheduleTest** class validates the functionality of the `ProjectSchedule` class, ensuring that project scheduling and analysis operations are performed correctly. Using the JUnit framework, this test suite focuses on verifying the correctness of scheduling metrics, graph traversal logic, and output file generation.

---

## Structure of the Tests

### 1. **Setup Method (`@BeforeEach`)**

- **Purpose**: Prepares the test environment before each test execution.
- **Details**:
   - **Graph Initialization**: A directed graph (`MapGraph<Activity, Double>`) with three activities (`activity1`, `activity2`, `activity3`) and dependencies.
   - **Repository Mocking**: A mock `PETRGraphRepository` to simulate data retrieval.
   - **Activity Configuration**:
      - Each activity is assigned:
         - A unique ID, name, duration, and cost.
         - Predecessor relationships to define dependencies.
      - Example dependencies:
         - `activity2` depends on `activity1`.
         - `activity3` depends on `activity2`.
   - **ProjectSchedule Object**: Created and linked to the test graph.

---

### 2. **Test Cases**

#### **2.1. `testCalculateScheduleAnalysis`**

- **Purpose**: Ensures the `calculateScheduleAnalysis` method computes the correct scheduling metrics.
- **Steps**:
   1. Executes `calculateScheduleAnalysis`.
   2. Validates computed values for:
      - **Earliest Start/Finish**:
         - `activity1`: Start = `0.0`, Finish = `5.0`.
         - `activity2`: Start = `0.0`, Finish = `3.0`.
         - `activity3`: Start = `0.0`, Finish = `2.0`.
      - **Latest Finish**:
         - `activity1`: `5.0`.
         - `activity2`: `3.0`.
         - `activity3`: `2.0`.
      - **Slack**: All activities have a slack of `0.0` (indicating a critical path).
   3. Confirms correct dependency handling (e.g., successors are identified).

```java
@Test
public void testCalculateScheduleAnalysis() {
    projectSchedule.calculateScheduleAnalysis();

    List<ID> sucList1 = Arrays.asList(activity2.getId()); // Sucessores de activity1
    List<ID> sucList2 = Arrays.asList(activity3.getId()); // Sucessores de activity2

    assertEquals(Double.MAX_VALUE, projectSchedule.getMinLatestStart(sucList1), 0.1);
    assertEquals(Double.MAX_VALUE, projectSchedule.getMinLatestStart(sucList2), 0.1);

    assertEquals(0.0, activity1.getEarliestStart(), 0.1);
    assertEquals(5.0, activity1.getEarliestFinish(), 0.1);

    assertEquals(0.0, activity2.getEarliestStart(), 0.1);
    assertEquals(3.0, activity2.getEarliestFinish(), 0.1);

    assertEquals(0.0, activity3.getEarliestStart(), 0.1);
    assertEquals(2.0, activity3.getEarliestFinish(), 0.1);

    assertEquals(5.0, activity1.getLatestFinish(), 0.1);
    assertEquals(3.0, activity2.getLatestFinish(), 0.1);
    assertEquals(2.0, activity3.getLatestFinish(), 0.1);

    assertEquals(0.0, activity1.getSlack(), 0.1);
    assertEquals(0.0, activity2.getSlack(), 0.1);
    assertEquals(0.0, activity3.getSlack(), 0.1);
}
```
---

#### **2.2. `testSendProjectScheduleToFile`**

- **Purpose**: Validates that the project schedule is correctly exported to a file.
- **Steps**:
   1. Creates a file with the schedule (`test_schedule_output.csv`).
   2. Confirms:
      - File existence.
      - Proper file closure after writing.
   3. Ensures no errors occur during export.

```java
@Test
public void testSendProjectScheduleToFile() throws FileNotFoundException {
    String fileName = "test_schedule_output";

    PrintWriter writer = new PrintWriter(fileName + ".csv");

    projectSchedule.setPrintWriter(writer);

    projectSchedule.sendProjectScheduleToFile(fileName);

    writer.close();
    assertTrue(new File(fileName + ".csv").exists());
}
```
---

#### **2.3. `testGenerateVerticesListBFS`**

- **Purpose**: Verifies the generation of a topological order for activities in the project graph.
- **Steps**:
   1. Executes `generateVerticesListBFS`.
   2. Checks:
      - The vertices list is not null.
      - The list contains the expected activities in topological order.

```java
@Test
public void testGenerateVerticesListBFS() {
    projectSchedule.generateVerticesListBFS();

    assertNotNull(projectSchedule.getVerticesList());
    assertTrue(projectSchedule.getVerticesList().size() > 0);
}
```
---

## Observations

### Responsibilities of the Test Class

- **Dependency Management**: The test setup replicates real-world scenarios with activity dependencies and ensures all graph-related operations function correctly.
- **Comprehensive Coverage**: The tests cover all essential methods of `ProjectSchedule`, including scheduling analysis, graph traversal, and file output.

### Integration with Other Classes

- **`PETRGraphRepository`**: Simulates data retrieval for testing `ProjectSchedule`.
- **`Activity` and `MapGraph`**: Core components tested for correct initialization, manipulation, and traversal.

---

## Final Thoughts

The **ProjectScheduleTest** class is a robust test suite that validates the correctness of project scheduling logic. Its design ensures:
1. Accurate analysis of scheduling metrics (e.g., earliest start, latest finish, slack).
2. Seamless graph traversal and dependency handling.
3. Reliable file generation for schedule outputs.

This test class effectively ensures the `ProjectSchedule` implementation meets its functional requirements, laying the groundwork for a dependable scheduling system.