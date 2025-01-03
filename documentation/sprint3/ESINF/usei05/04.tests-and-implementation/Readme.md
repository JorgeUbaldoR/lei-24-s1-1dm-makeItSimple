# USEI05 - Workstation Statistics

## 4. Construction (Implementation)

### Class Simulator 

```java
 /**
 * Adds or updates the execution time for a specific machine.
 *
 * @param m    The machine for which the execution time is being added or updated.
 * @param time The time (in minutes) to be added to the machine's total execution time.
 */
private void addExecutionTimesMachine(Machine m, float time) {
    if (!this.machineUsage.containsKey(m)) {
        machineUsage.put(m, time);
    } else {
        float currentTime = machineUsage.get(m) + time;
        machineUsage.put(m, currentTime);
    }
}
```

```java
 /**
 * Prints the execution times of each machine along with their percentages of total execution time.
 * It displays the data in a formatted table.
 */
private void printExecutionTimesMachine() {
    List<Map.Entry<Machine, Float>> list = ascendingOrderMachineTimes();
    float totalTime = sumTotalTime();


    System.out.println("\n\n═══════════════════════════════════════════════");
    System.out.print(ANSI_BRIGHT_WHITE + "                  Statistics                 " + ANSI_RESET + "\n");

    System.out.printf("%n%s===============================================%s%n", ANSI_BRIGHT_BLACK, ANSI_RESET);
    System.out.printf("%s%s%s%s %-13s %6s %15s %s%s%3s%s%n",
            ANSI_BRIGHT_BLACK, "||", ANSI_RESET,
            ANSI_BRIGHT_WHITE,
            "Workstation",
            " Time(min)",
            "Percentages", ANSI_RESET,
            ANSI_BRIGHT_BLACK, " ||", ANSI_RESET);
    System.out.printf("%s===============================================%s%n", ANSI_BRIGHT_BLACK, ANSI_RESET);

    for (Map.Entry<Machine, Float> entry : list) {
        float percentage = (entry.getValue() / totalTime) * 100;
        System.out.printf("%s%s%s  %-14s %-15.2f %.2f %s%n",
                ANSI_BRIGHT_BLACK, "||", ANSI_RESET,
                entry.getKey().getId_machine(),
                entry.getValue(),
                percentage, "%");
    }

    System.out.printf("%s===============================================%s%n", ANSI_BRIGHT_BLACK, ANSI_RESET);

}
```

```java
/**
 * Returns a list where all machines are sorted by their execution time, in descending order.
 *
 * @return A list of entries (key-value pairs), where the key is the machine (Machine)
 * and the value is the execution time (Float), sorted in descending order of time.
 */
private List<Map.Entry<Machine, Float>> ascendingOrderMachineTimes() {
    List<Map.Entry<Machine, Float>> list = new ArrayList<>(getExecutionTimesMachine().entrySet());
    list.sort(Map.Entry.comparingByValue(Comparator.reverseOrder()));
    return list;
}
```


```java
/**
 * Retrieves the execution times for all operations.
 *
 * @return a map containing operations and their corresponding execution times.
 */
public Map<Operation, Float> getExecutionTimesOperation() {
    return this.operationTime;
}
```

```java
 /**
 * Retrieves the map that contains the execution times for each machine.
 *
 * @return A map where the keys are Machine objects and the values are the execution times (in minutes) for each machine.
 */
public Map<Machine, Float> getExecutionTimesMachine() {
    return this.machineUsage;
}
```
