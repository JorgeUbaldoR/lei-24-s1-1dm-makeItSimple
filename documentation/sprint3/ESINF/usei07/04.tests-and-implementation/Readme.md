# USEI07 - Produce a listing representing the flow dependency between machines

## 4. Tests 

**Test 1:** Check if the transitionMap has the machines came from and where they will go as well as how many times they made that route.

	@Test
    void getMachineRouteTest() {
        simulator = new Simulator(machineListMap, itemList, operationList, false);
        simulator.startSimulation();
        simulator.printMachineRoute();

        Map<ID, Map<ID, Integer>> transitionMap = simulator.getMachineRoute();

        assertTrue(transitionMap.containsKey(new ID(10, TypeID.MACHINE)));
        assertEquals(1, (int) transitionMap.get(new ID(10, TypeID.MACHINE)).get(new ID(11, TypeID.MACHINE)));

        assertTrue(transitionMap.containsKey(new ID(11, TypeID.MACHINE)));
        assertEquals(1, (int) transitionMap.get(new ID(11, TypeID.MACHINE)).get(new ID(12, TypeID.MACHINE)));

        assertTrue(transitionMap.containsKey(new ID(12, TypeID.MACHINE)));
        assertEquals(1, (int) transitionMap.get(new ID(12, TypeID.MACHINE)).get(new ID(11, TypeID.MACHINE)));
    }
	

**Test 2:** Check if the print contains the machines and their transitions (movements between machines)

	@Test
    public void testPrintMachineRoute() {
        simulator = new Simulator(machineListMap, itemList, operationList, false);
        simulator.startSimulation();
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));
        simulator.printMachineRoute();
        String printedOutput = outputStream.toString();

        assertTrue(printedOutput.contains("Machine ID"));
        assertTrue(printedOutput.contains("Machine Route"));

        assertTrue(printedOutput.contains("W-10"));
        assertTrue(printedOutput.contains("(W-11,1)"));
        assertTrue(printedOutput.contains("W-11"));
        assertTrue(printedOutput.contains("(W-12,1)"));

        System.setOut(System.out);
    }

**Test 3:** Check if the item route in the correct order

    @Test
    public void testPrintItemRoute() {
    simulator = new Simulator(machineListMap, itemList, operationList, false);
    simulator.startSimulation();
    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    System.setOut(new PrintStream(outputStream));
    simulator.printItemRoute();
    String printedOutput = outputStream.toString();

        assertTrue(printedOutput.contains("Item"));
        assertTrue(printedOutput.contains("Item Route"));

        assertTrue(printedOutput.contains("I-10"));
        assertTrue(printedOutput.contains("[W-11, W-12]"));

        System.setOut(System.out);
    }

_It is also recommended to organize this content by subsections._ 


## 5. Construction (Implementation)

### Simulator

```java
private void updateMachines() {
        for (Operation operation : machineList.keySet()) {
            Queue<Machine> machines = machineList.get(operation);
            for (Machine machine : machines) {
                boolean finished = machine.updateMachine();
                if (finished) {
                    addExecutionTimesOperation(operation, machine.getProcessingSpeed());
                    addExecutionTimesMachine(machine, machine.getProcessingSpeed());
                    Item currentItem = machine.getCurrentProcessingItem();
                    Operation newOperation = currentItem.getNextOperation();
                    itemLinkedListMap.putIfAbsent(currentItem, new LinkedList<>());
                    itemLinkedListMap.get(currentItem).add(machine.getId_machine());
                    if (newOperation != null) {
                        OperationQueue operationQueue = findOperationInQueue(newOperation);
                        if (operationQueue != null) {
                            operationQueue.addItemToQueue(currentItem);
                        }
                    } else {
                        itemLinkedList.add(currentItem);
                    }
                }
            }
        }
    }
```

```
public void printItemRoute() {
        System.out.printf("%n%s===============================================%s%n", ANSI_BRIGHT_BLACK, ANSI_RESET);
        System.out.printf("%s%s%s%s %-13s %17s %s%s%3s%n",
                ANSI_BRIGHT_BLACK, "||", ANSI_RESET,
                ANSI_BRIGHT_WHITE,
                "Item",
                "Item Route"
                , ANSI_RESET,
                ANSI_BRIGHT_BLACK, ANSI_RESET);
        System.out.printf("%s===============================================%s%n", ANSI_BRIGHT_BLACK, ANSI_RESET);
        for (Map.Entry<Item, LinkedList<ID>> entry : itemLinkedListMap.entrySet()) {
            for (Item it : itemLinkedList) {
                if (entry.getKey().equals(it)) {
                    System.out.printf("%s%s%s  %-17s %s %n", ANSI_BRIGHT_BLACK, "||", ANSI_RESET, entry.getKey().getItemID(), entry.getValue());
                }
            }
        }
        System.out.printf("%n%s===============================================%s%n", ANSI_BRIGHT_BLACK, ANSI_RESET);
    }
```

```
public Map<ID, Map<ID, Integer>> getMachineRoute() {
        Map<ID, Map<ID, Integer>> transitionMap = new HashMap<>();

        for (Map.Entry<Item, LinkedList<ID>> entry : itemLinkedListMap.entrySet()) {
            LinkedList<ID> machineIds = entry.getValue();

            for (int i = 0; i < machineIds.size() - 1; i++) {
                ID fromMachine = machineIds.get(i);
                ID toMachine = machineIds.get(i + 1);

                transitionMap.putIfAbsent(fromMachine, new HashMap<>());

                Map<ID, Integer> toMachineCount = transitionMap.get(fromMachine);

                toMachineCount.put(toMachine, toMachineCount.getOrDefault(toMachine, 0) + 1);
            }
        }
        return transitionMap;
    }
```

```
public void printMachineRoute() {

        Map<ID, Map<ID, Integer>> transitionMap = getMachineRoute();

        System.out.printf("%n%s===============================================%s%n", ANSI_BRIGHT_BLACK, ANSI_RESET);
        System.out.printf("%s%s%s%s %-13s %17s %s%s%3s%n",
                ANSI_BRIGHT_BLACK, "||", ANSI_RESET,
                ANSI_BRIGHT_WHITE,
                "Machine ID",
                "Machine Route"
                , ANSI_RESET,
                ANSI_BRIGHT_BLACK, ANSI_RESET);
        System.out.printf("%s===============================================%s%n", ANSI_BRIGHT_BLACK, ANSI_RESET);
        for (Map.Entry<ID, Map<ID, Integer>> transitionEntry : transitionMap.entrySet()) {
            ID fromMachine = transitionEntry.getKey();
            Map<ID, Integer> toMachines = transitionEntry.getValue();

            List<String> transitionStrings = new ArrayList<>();
            for (Map.Entry<ID, Integer> toMachineEntry : toMachines.entrySet()) {
                ID toMachine = toMachineEntry.getKey();
                int count = toMachineEntry.getValue();
                transitionStrings.add("(" + toMachine.getKeyID()+ "," + count + ")");
            }
            System.out.printf("%s%s%s  %-17s %s %n", ANSI_BRIGHT_BLACK, "||", ANSI_RESET, fromMachine.getKeyID(), String.join(", ", transitionStrings));
        }
        System.out.printf("%s===============================================%s%n", ANSI_BRIGHT_BLACK, ANSI_RESET);
    }
```

## 6. Integration and Demo 

* It will print the machine transitions when a simulation finishes.


## 7. Observations

n/a