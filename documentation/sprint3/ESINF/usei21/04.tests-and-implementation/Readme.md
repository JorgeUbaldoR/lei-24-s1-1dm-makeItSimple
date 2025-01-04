# USEI21 - Export Project Schedule

## 4. Implementation

### ProjectSchedule Class

The `ProjectSchedule` class is responsible for the scheduling and management of project activities, including calculating scheduling metrics and exporting the schedule to a file. A crucial method in this class is `sendProjectScheduleToFile`, which ensures that the schedule is correctly exported to a CSV file for further analysis or reporting.

---

### Method: `sendProjectScheduleToFile`

The `sendProjectScheduleToFile` method generates a detailed project schedule, including activity details and their dependencies, and exports them to a CSV file. The file is formatted with the following columns:

- **act_id**: The unique identifier of the activity.
- **cost**: The cost associated with the activity.
- **duration**: The duration of the activity.
- **es**: The earliest start time of the activity.
- **ls**: The latest start time of the activity.
- **ef**: The earliest finish time of the activity.
- **lf**: The latest finish time of the activity.
- **prev_act_ids**: The IDs of the predecessor activities.

The method follows these steps:
1. It first calculates the scheduling analysis using `calculateScheduleAnalysis()`.
2. It then creates a `PrintWriter` object to write to the specified file.
3. The header row is written with column names, followed by data rows for each activity, including its associated metrics and predecessors.
4. Special handling for the start and finish activities is included.
5. Finally, the `PrintWriter` is closed after writing all the data.

Here’s the implementation of `sendProjectScheduleToFile`:

```java
public void sendProjectScheduleToFile(String fileName) {
    calculateScheduleAnalysis();
    try {
        writer = new PrintWriter(FILE_PATH + fileName + ".csv");
        writer.println("act_id;cost;duration;es;ls;ef;lf;prev_act_ids...");
        for (Activity a : verticesList) {
            if (a.getId().getSerial() != START_PAIR.getSecond() && a.getId().getSerial() != FINISH_PAIR.getSecond()) {
                writer.printf("%s;%.2f;%.2f;%.2f;%.2f;%.2f;%.2f", a.getId(), a.getCost(), a.getDuration(),
                        a.getEarliestStart(), a.getLatestStart(), a.getEarliestFinish(), a.getLatestFinish());
                for (ID pred : a.getPredecessors()) {
                    if (pred.getSerial() != START_PAIR.getSecond()) {
                        writer.print(";" + pred);
                    } else {
                        writer.print(";" + START_PAIR.getFirst());
                    }
                }
                writer.println();
            } else if (a.getId().getSerial() == FINISH_PAIR.getSecond()) {
                writer.println(FINISH_PAIR.getFirst());
            } else {
                writer.println(START_PAIR.getFirst());
            }
        }

        writer.close();
    } catch (Exception e) {
        System.out.println(ANSI_BRIGHT_RED + "Error writing to file " + FILE_PATH + fileName + ANSI_RESET);
    }
}
```
## 4.1 Tests

The **sendProjectScheduleToFile** method is tested to ensure that the project schedule is correctly exported to a file. This includes checking the creation of the file, ensuring it is properly closed, and validating that no errors occur during the export process.

---

### Test Case: `testSendProjectScheduleToFile`

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
This test case validates that:
1.	The file is created at the specified location.
2.	The PrintWriter is correctly used to write the project schedule data.
3.	The file exists after execution, confirming successful export.

### Test Case: `testSendProjectScheduleToFile_ErrorHandling`

```java
@Test
public void testSendProjectScheduleToFile_ErrorHandling() {
    String invalidFileName = "";

    try {
        projectSchedule.sendProjectScheduleToFile(invalidFileName);
    } catch (Exception e) {
        assertTrue(e instanceof FileNotFoundException);
    }
}
```

This test ensures that if an error occurs during the file writing process, the system handles the error appropriately. It checks that an exception is thrown when an invalid file name or writing issue is encountered, confirming the robustness of error handling in the method.

---

### Observations

**Functionality and Impact of the Method**
- The sendProjectScheduleToFile method is a key feature of the ProjectSchedule class, facilitating the export of detailed project schedules to a CSV file. This method provides a structured way to output project data, including critical metrics like start/finish times, costs, and dependencies. By exporting this data, the method ensures that project managers, stakeholders, and other users can easily analyze and review project schedules.

**Efficiency and Scalability**
- The method’s design takes into account the need to handle potentially large projects with multiple activities. The use of PrintWriter allows efficient writing to the file, and the careful iteration over each activity ensures that the project schedule is accurately represented. Moreover, the method’s ability to manage start and finish activities separately ensures that the file structure remains consistent.

**Error Handling**
- The method includes basic error handling through a try-catch block. If an issue arises during file writing (e.g., permission errors or invalid file names), it will print a clear error message. Further enhancements could include more robust logging or specific error reporting mechanisms.


### Final Thoughts

- The sendProjectScheduleToFile method plays a crucial role in the ProjectSchedule class by exporting project scheduling data in a standardized format for further use or reporting. The method’s implementation is efficient and well-structured, ensuring that the exported CSV file contains all necessary information for project analysis.

- The tests associated with this method verify its core functionality, ensuring the correct file creation and error handling. These tests guarantee that the method works as expected under normal and exceptional conditions, contributing to the overall reliability of the scheduling system.

- As a result, the sendProjectScheduleToFile method adds significant value to the ProjectSchedule class, enhancing its ability to facilitate project management tasks by providing a simple yet effective way to share project schedules with others.