# USEI01 - File data importation

## 4. Tests 

**Test 1:** Tests getting machine details from a valid file and verifies that the correct number of details is returned and checks specific entries.

	@Test public void testGetMachinesDetails_ValidFile() throws IOException {
        List<String[]> details = FileDataReader.getMachinesDetails();
        assertEquals(18, details.size());
        assertArrayEquals(new String[]{"1", "Drill", "1.5"}, details.get(0));
        assertArrayEquals(new String[]{"18", "Polish", "0.75"}, details.get(17)); 
    }
	

**Test 2:** Tests getting item details from a valid file and verifies that the correct number of details is returned and checks specific entries.


	@Test public void testGetItemsDetails_ValidFile() throws IOException {
        List<String[]> details = FileDataReader.getItemsDetails();
        assertEquals(20, details.size());
        assertArrayEquals(new String[]{"101", "HIGH", "Drill", "Cut", "Polish", "Assemble"}, details.get(0));
        assertArrayEquals(new String[]{"120", "HIGH", "Drill", "Test", "Polish", "Cut", "Grind"}, details.get(19)); 
    }

**Test 3:** Tests the behavior when trying to read machine details from a non-existent file and verifies that an IOException is thrown.

          @Test public void testGetMachinesDetails_FileNotFound() {
        new File(TEST_MACHINES_FILE).delete();
        assertThrows(IOException.class, () -> FileDataReader.getMachinesDetails());
    }

**Test 4:** Tests the behavior when trying to read item details from a non-existent file and verifies that an IOException is thrown.

    @Test public void testGetMachinesDetails_FileNotFound() {
    new File(TEST_MACHINES_FILE).delete();
    assertThrows(IOException.class, () -> FileDataReader.getMachinesDetails());
}


## 5. Construction (Implementation)

### Class FileDataReader 

```java
/**
 * Reads item details from a CSV file and returns them as a list of string arrays
 *
 * @return List of string arrays containing item details
 * @throws IOException if the file is not found or another I/O error occurs
 */
public static List<String[]> getItemsDetails() throws IOException {
    File file = new File("prodPlanSimulator/main/java/pt/ipp/isep/dei/esoft/project/files/articlesFinal.csv");
    if (!file.exists()) {
        throw new IOException("File not found: " + file.getAbsolutePath());
    }

    Scanner scanner = new Scanner(file);
    List<String[]> itemsDetails = new ArrayList<>();

    if (scanner.hasNextLine()) {
        scanner.nextLine();
    }

    while (scanner.hasNextLine()) {
        String line = scanner.nextLine();
        String[] parts = line.split(";");

        if (parts.length >= NUMBER_OF_DETAILS) {
            itemsDetails.add(parts);
        }
    }

    scanner.close();
    return itemsDetails;
}

```

```java
   /**
 * Reads machine details from a CSV file and returns them as a list of string arrays
 *
 * @return List of string arrays containing machine details
 * @throws IOException if an error occurs while reading the file
 */
public static List<String[]> getMachinesDetails() throws IOException {
    Scanner scanner = new Scanner(new File("prodPlanSimulator/main/java/pt/ipp/isep/dei/esoft/project/files/workstations.csv"));
    List<String[]> machineDetails = new ArrayList<>();

    if (scanner.hasNextLine()) {
        scanner.nextLine();
    }

    while (scanner.hasNextLine()) {
        String line = scanner.nextLine();
        String[] parts = line.split(";");

        if (parts.length == NUMBER_OF_DETAILS) {
            machineDetails.add(parts);
        }
    }
    scanner.close();
    return machineDetails;
}

```

### Class ItemRepository

```java
  /**
 * Fills the inventory with items from a data source.
 * This method reads item details from a file and populates the item list.
 * It handles IOExceptions that may occur during file reading.
 */
private void fillInventory() {
    try {

        List<String[]> importedItems = FileDataReader.getItemsDetails();
        for (String[] importedItem : importedItems) {
            ID newId = new ID(Integer.parseInt(importedItem[0]), TypeID.ITEM);
            Priority priority = Priority.fromString(importedItem[1]);
            Queue<Operation> operationQueue = new LinkedList<>();

            for (int i = 2; i < importedItem.length; i++) {
                Operation operation = new Operation(importedItem[i]);
                operationQueue.add(operation);

            }

            itemList.put(newId, new Item(newId, priority, operationQueue));
        }

    } catch (IOException e) {
        System.out.println("Error reading items details");
    }
}

```


### Class MachineRepository

```java
      /**
 * Fills the machinery list with machines from a data source.
 * This method reads machine details from a file and populates the machine list.
 * It handles IOExceptions that may occur during file reading.
 */
private void fillMachinery() {
    try {

        List<String[]> importedItems = FileDataReader.getMachinesDetails();
        for (String[] importedItem : importedItems) {
            ID machineID = new ID(Integer.parseInt(reformatMachineId(importedItem[0])), TypeID.MACHINE);
            Operation operation = new Operation(importedItem[1]);
            float duration = Float.parseFloat(importedItem[2]);

            Machine machine = new Machine(machineID, operation, duration);

            machineList.put(machineID, machine);

        }

    } catch (IOException e) {
        System.out.println("Error reading machines from file");
    }

}

```

```java
public List<Machine> getMachineList() {
return new ArrayList<>(machineList.values());
}
```

### Class OperationController

```java
   /**
 * Retrieves all items from the item repository.
 *
 * @return a list containing all items managed by the item repository.
 */
public List<Item> getAllItems() {
    return itemRepository.getItemList();
}
```

```java
    /**
 * Populates the operation repository with operations extracted from the items.

 * This method first retrieves all items by calling getAllItems(), then uses those items
 * to fill the operation repository. Each item may be associated with one or more operations,
 * and this method ensures that the operation repository is populated based on the available items.
 */
public void fillOperationsFromItems() {
    List<Item> items = getAllItems();
    operationRepository.fillOperations(items);
}
```

### Class OperationRepository

```java
/**
 * Populates the operations list with operations extracted from the provided items.

 * This method iterates over each item in the provided list and retrieves the list of operations
 * associated with each item. Each operation is cloned to ensure that the original operation objects
 * are not modified when added to the operations list. The cloned operations are then added to the
 * internal list of operations.
 *
 * @param items a list of Item objects from which operations are extracted and added to the operations list.
 */
public void fillOperations(List<Item> items) {
    for (Item item : items) {
        for (Operation operation : item.getOperationList()) {
            operations.add(operation.clone());
        }
    }
}

```

```java
/**
 * Constructs an OperationRepository instance.
 * Initializes the set to hold operations.
 *
 * @param items a list of items to fill the repository with operations
 */
public OperationRepository(List<Item> items) {
    this.operations = new HashSet<>();
    fillOperations(items);
}

```



