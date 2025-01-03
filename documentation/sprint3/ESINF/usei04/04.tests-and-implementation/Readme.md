# USEI04 - Calculate execution times by each operation 

## 4. Tests 

**Test 1:** Tests to check if the print contains the operations inserted and their respective duration. 

	@Test
    public void testPrintExecutionTimesOperation() {
        simulator = new Simulator(machineListMap, itemList, operationList, false);
        simulator.startSimulation();
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));
        simulator.printExecutionTimesOperation();
        String output = outputStream.toString();

        assertTrue(output.contains("Cutting"));
        assertTrue(output.contains("welding"));
        assertTrue(output.contains("Painting"));

        assertTrue(output.contains("55,56 %"));
        assertTrue(output.contains("33,33 %"));
        assertTrue(output.contains("11,11 %"));

        System.setOut(System.out);
    }


_It is also recommended to organize this content by subsections._ 


## 5. Construction (Implementation)

### Class Simulator

```java
public Map<Operation, Float> getExecutionTimesOperation() {
        return this.operationTime;
        }
```

```
private List<Map.Entry<Operation, Float>> ascendingOrderOperationTimes() {
        List<Map.Entry<Operation, Float>> list = new ArrayList<>(getExecutionTimesOperation().entrySet());
        list.sort(Map.Entry.comparingByValue(Comparator.reverseOrder()));
        return list;
    }
```

```
private float sumTotalTime() {
        float sum = 0;
        List<Map.Entry<Operation, Float>> list = ascendingOrderOperationTimes();
        for (Map.Entry<Operation, Float> entry : list) {
            sum += entry.getValue();
        }
        return sum;
    }
```

```
public void printExecutionTimesOperation() {
        List<Map.Entry<Operation, Float>> list = ascendingOrderOperationTimes();
        float totalTime = sumTotalTime();

        System.out.printf("%n%s===============================================%s%n", ANSI_BRIGHT_BLACK, ANSI_RESET);
        System.out.printf("%s%s%s%s %-13s %6s %15s %s%s%3s%s%n",
                ANSI_BRIGHT_BLACK, "||", ANSI_RESET,
                ANSI_BRIGHT_WHITE,
                "Operation",
                " Time(min)",
                "Percentages", ANSI_RESET,
                ANSI_BRIGHT_BLACK, "||", ANSI_RESET);
        System.out.printf("%s===============================================%s%n", ANSI_BRIGHT_BLACK, ANSI_RESET);

        for (Map.Entry<Operation, Float> entry : list) {
            float percentage = (entry.getValue() / totalTime) * 100;
            System.out.printf("%s%s%s  %-14s %-13.2f %.2f %s%n",
                    ANSI_BRIGHT_BLACK, "||", ANSI_RESET,
                    entry.getKey().getOperationName(),
                    entry.getValue(),
                    percentage, "%");
        }

        System.out.printf("%s===============================================%s%n", ANSI_BRIGHT_BLACK, ANSI_RESET);
    }
```


## 6. Integration and Demo 

* It will print the operation and their time as well as a percentage of the time used in each operation when a simulation finishes.

## 7. Observations

n/a