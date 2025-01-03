
---

# USEI09 - Efficient Search

## 4. Tests

### **Test 1: ConstructProductionTree**

```java
@Test
void constructProductionTree() {
    System.out.println("Test Constructor");
    ProductionTree productionTree = new ProductionTree();
    List<Node> list = productionTree.getNodesOfTree();
    Map<ID, Node> map = productionTree.getRawMaterials();
    assertTrue(map.isEmpty());
    assertTrue(list.isEmpty());

    productionTree.setPdtTreeName("Production Tree");
    assertTrue(productionTree.getNodesOfTree().isEmpty(), "Nodes list should be empty after initialization.");
    assertTrue(productionTree.getRawMaterials().isEmpty(), "Raw materials map should be empty after initialization.");
    assertTrue(productionTree.getMaterials().isEmpty(), "Materials map should be empty after initialization.");
    assertTrue(productionTree.getOperationNodeID().isEmpty(), "Operation map should be empty after initialization.");
}
```

**Objective:**  
Test the initial construction of `ProductionTree`, verifying if the tree's internal structures (lists and maps) are correctly initialized as empty.

**Validations:**
1. The node list (`Nodes`) and the maps for raw materials, materials, and operations are empty after initialization.
2. Assigning a name to the tree does not alter the internal state of the lists and maps.

**Expected Result:**  
All internal elements should be correctly initialized as empty.

---

### **Test 2: GetInformation**

```java
@Test
void getInformation() {
    System.out.println("Test GetInformation");
    ProductionTree productionTree = new ProductionTree();
    boolean result = productionTree.getInformations("prodPlanSimulator(ESINF)/main/java/pt/ipp/isep/dei/esoft/project/files/input/boo.csv");

    List<Node> list = productionTree.getNodesOfTree();
    Map<ID, Node> map = productionTree.getRawMaterials();
    Map<ID, Node> map2 = productionTree.getMaterials();
    Map<ID, Node> map1 = productionTree.getRawMaterials();
    Map<Integer, List<Node>> mapHeight = productionTree.getHeightMap();

    assertFalse(map.isEmpty());
    assertFalse(mapHeight.isEmpty());
    assertFalse(list.isEmpty()); 
    assertFalse(map2.isEmpty());
    assertFalse(map1.isEmpty());
    assertTrue(result);

    assertThrows(IllegalArgumentException.class,
    () -> productionTree.getInformations(""));
    try {
        result = productionTree.getInformations("");
        assertFalse(result);
    } catch (IllegalArgumentException e) {}
}
```

**Objective:**  
Verify the functionality of the `getInformations` method when processing a valid file. Also validates if the method throws appropriate exceptions for invalid inputs.

**Validations:**
1. The node list (`Nodes`) and the maps for raw materials, materials, and tree height (`HeightMap`) must not be empty after reading a valid file.
2. The method must throw an `IllegalArgumentException` for invalid files or empty paths.

**Expected Result:**
- Valid files should be processed correctly, filling the internal structures.
- Invalid files should result in an exception and not alter the tree's internal state.

---

### **Test 3: GetInformationInvalidFile**

```java
@Test
void testGetInformationInvalidFile() {
    System.out.println("Test GetInformation with Invalid File");
    String invalidFilePath = "invalidFile.csv";

    assertThrows(IllegalArgumentException.class,
            () -> productionTree.getInformations(invalidFilePath),
            "An exception should be thrown for an invalid file.");

    // Verify the tree's state after an error
    assertTrue(productionTree.getNodesOfTree().isEmpty(), "The node list should be empty after a processing failure.");
    assertTrue(productionTree.getRawMaterials().isEmpty(), "The raw materials map should be empty after a processing failure.");
}
```

**Objective:**  
Validate the behavior of the `getInformations` method when attempting to process a nonexistent or invalid file.

**Validations:**
1. The method must throw an `IllegalArgumentException` for an invalid file path.
2. The tree's internal state (lists and maps) must remain empty after the error.

**Expected Result:**  
The tree should not be altered, and the exception must be correctly thrown.

---

### **Test 4: GetInformationsValidFile**

```java
@Test
void testGetInformationsValidFile() throws IOException {
    System.out.println("Test GetInformations with valid file");
    // Create a temporary file to simulate input
    String filePath = "prodPlanSimulator(ESINF)/main/java/pt/ipp/isep/dei/esoft/project/files/input/test.csv";
    try (FileWriter writer = new FileWriter(filePath)) {
        writer.write("""
            op_id;item_id;item_qtd;(;op1;op_qtd1;op2;op_qtd2;opN;op_qtdN;);(;item_id1;item_qtd1;item_id1;item_qtd1;item_id1;item_qtd1;)
            20;1006;1;(;11;1;;;;;);(;1014;0,125;;;;;)
            ...
        """);
    }

    boolean result = productionTree.getInformations(filePath);
    assertTrue(result, "Information should be successfully read.");

    // Validate if nodes were correctly loaded
    assertFalse(productionTree.getNodesOfTree().isEmpty(), "Node list should not be empty.");
    assertFalse(productionTree.getRawMaterials().isEmpty(), "Raw materials map should not be empty.");
    assertFalse(productionTree.getMaterials().isEmpty(), "Materials map should not be empty.");
    assertEquals(10, productionTree.getNodesOfTree().size(), "There should be 10 nodes in the tree.");

    // Validate tree heights
    assertNotNull(productionTree.getHeightMap().get(0), "Height 0 should contain nodes.");
    assertNotNull(productionTree.getHeightMap().get(1), "Height 1 should contain nodes.");
}
```

**Objective:**  
Test the complete processing of a valid input file, verifying if all nodes, materials, and heights are correctly loaded.

**Validations:**
1. After reading the file, the node list (`Nodes`) and the maps for raw materials and materials must not be empty.
2. The tree height must be correctly populated.
3. The number of nodes loaded must match the data in the file.

**Expected Result:**  
The tree structure is correctly populated with the data from the file, including nodes, raw materials, and heights.

---

### Class Production Tree

```java
/**
 * Constructs an empty production tree with default values.
 *
 * Complexity: O(1) - Basic initialization of fields.
 */
public ProductionTree() {
    this.pdtTreeName = "No Name";
    nodesOfTree = new ArrayList<>();
    heightMap = new HashMap<>();
    materials = new HashMap<>();
    rawMaterials = new HashMap<>();
    operationNodeID = new HashMap<>();
    getItemRepository();
}
```
```java
/**
 * Retrieves the ItemRepository instance.
 *
 * @return the ItemRepository instance
 */
private ItemRepository getItemRepository() {
    if(itemRepository == null) {
        Repositories repositories = Repositories.getInstance();
        itemRepository = repositories.getItemRepository();
    }
    return itemRepository;

}
```
```java
    /**
 * Reads tree information from a file and constructs the nodes.
 *
 * @param path the file path to read data from.
 * @return true if information is successfully read and processed, false otherwise.
 *
 * Complexity: O(n * m) - n is the number of records in the file, m is the average
 * number of operations/materials per record.
 */
public boolean getInformations(String path) {
    try {
        List<String[]> readedInformation = ReadTreeInfo.readBoo(path);
        if (readedInformation.isEmpty()) {
            return false;
        }

        for (int i = 0; i < readedInformation.size(); i += 3) {
            String[] firstThreeValues = readedInformation.get(i);
            String[] arrayOperations = readedInformation.get(i + 1);
            String[] arrayMaterials = readedInformation.get(i + 2);

            ID operationID = new ID(Integer.parseInt(firstThreeValues[0]), TypeID.OPERATION);
            ID itemID = new ID(Integer.parseInt(firstThreeValues[1]), TypeID.ITEM);
            float qtd = Float.parseFloat(firstThreeValues[2]);

            associatedQtdToItem(itemID, qtd);

            // Process operation map
            Map<ID, Float> operationMap = new HashMap<>();
            for (int j = 1; j < arrayOperations.length; j += 2) {
                operationMap.put(new ID(Integer.parseInt(arrayOperations[j]), TypeID.OPERATION),
                        Float.parseFloat(arrayOperations[j + 1].replace(",", ".")));
            }

            Node node = new Node(operationID, itemID, qtd, operationMap, null);

            // Process material map
            Map<ID, Float> materialMap = new HashMap<>();
            for (int j = 1; j < arrayMaterials.length; j += 2) {
                ID newID = new ID(Integer.parseInt(arrayMaterials[j]), TypeID.ITEM);
                float newQtd = Float.parseFloat(arrayMaterials[j + 1].replace(",", "."));
                materialMap.put(newID, newQtd);
                rawMaterials.put(newID, node);
                associatedQtdToItem(newID, newQtd);
            }
            node.setMaterialMap(materialMap);
            nodesOfTree.add(node);
            operationNodeID.put(operationID, node);
            materials.put(itemID, node);
        }
    } catch (IOException e) {
        throw new IllegalArgumentException("File not found...");
    }
    fillTreeHeight(nodesOfTree.get(0), 0);
    return true;
}
```

```java
    /**
 * Recursively calculates and assigns heights to nodes in the tree.
 *
 * @param node   the current node being processed.
 * @param height the height of the current node.
 *               <p>
 *               Complexity: O(n) - Processes each node exactly once.
 */
private void fillTreeHeight(Node node, int height) {
    if (node == null) {
        return;
    }

    heightMap.putIfAbsent(height, new ArrayList<>());
    heightMap.get(height).add(node);
    node.setHeigthInTree(height);

    treeHeight = Math.max(treeHeight, height + 1);

    for (ID id : node.getOperationMap().keySet()) {
        Node childNode = findNodeByOperation(id);
        if (childNode != null) {
            fillTreeHeight(childNode, height + 1);
        }
    }
}   
```
```java

/**
 * Finds a node in the tree by its operation ID.
 *
 * @param id the operation ID to search for.
 * @return the node corresponding to the operation ID, or null if not found.
 * <p>
 * Complexity: O(n) - Searches through the list of nodes.
 */
private Node findNodeByOperation(ID id) {
    for (Node node : nodesOfTree) {
        if (node.getOperationID().equals(id)) {
            return node;
        }
    }
    return null;
}
```

### Class Search Production Tree UI

#### Constructor
```java
    public SearchProductionTreeUI() {
    controller = new ProductionTreeController();
}
```
#### Get Controller
```java
    private ProductionTreeController getProductionTreeController() {
    return controller;
}
```

#### Get Controller
```java
@Override
public void run() {
    System.out.println("\n\n══════════════════════════════════════════");
    System.out.println(ANSI_BRIGHT_WHITE + "        SEARCH OPERATION/MATERIAL                 " + ANSI_RESET + "\n");

    System.out.printf("Select type of search:%n");
    System.out.printf(" %s(1)%s - Search Material%n", ANSI_BRIGHT_BLACK, ANSI_RESET);
    System.out.printf(" %s(2)%s - Search Operation%n", ANSI_BRIGHT_BLACK, ANSI_RESET);
    System.out.printf(" %s(3)%s - Search by ID%n", ANSI_BRIGHT_BLACK, ANSI_RESET);
    System.out.printf(" %s(0)%s - Cancel%n", ANSI_BRIGHT_BLACK, ANSI_RESET);
    int searchChoice = getChoice(3);

    if(searchChoice != 0) {
        System.out.printf("%n%s!IMPORTANT!%s Select your preference:%n",ANSI_BRIGHT_YELLOW,ANSI_RESET);
        System.out.printf(" %s(1)%s - Use Default File%n", ANSI_BRIGHT_BLACK, ANSI_RESET);
        System.out.printf(" %s(2)%s - Insert Path File%n", ANSI_BRIGHT_BLACK, ANSI_RESET);
        int preferenceChoice = getChoice(2);
        if(preferenceChoice == 1) {
            doChoice(searchChoice, "prodPlanSimulator(ESINF)/main/java/pt/ipp/isep/dei/esoft/project/files/input/boo.csv");
        }else {
            Scanner scanner = new Scanner(System.in);
            System.out.print("Enter path: ");
            String path = scanner.nextLine();
            doChoice(searchChoice,path );
        }
    }
}
```

#### Get Choice
```java
    private int getChoice(int max) {
        int choice = 0;
        boolean valid = false;
        do {
            System.out.printf(" %sType your choice:%s ", ANSI_BRIGHT_BLACK, ANSI_RESET);
            try {
                choice = in.nextInt();

                if (choice < 0 || choice > max) {
                    System.out.println(ANSI_LIGHT_RED + "Select a valid number: " + ANSI_RESET);
                } else {
                    valid = true;
                }
            } catch (InputMismatchException e) {
                System.out.println(ANSI_LIGHT_RED + "Invalid choice. Please try again: " + ANSI_RESET);
                in.next();

            }
        } while (!valid);
        return choice;
    }
```

#### Do Choice
```java
    private void doChoice(int choice, String path) {
        try {
            if (getProductionTreeController().getInformations(path)) {
                switch (choice) {
                    case 1:
                        ID selectedItemID = showAndSelectMaterial();
                        searchInfItem(selectedItemID);
                        break;
                    case 2:
                        ID selectedOperationID = showAndSelectOperation();
                        searchInfOperation(selectedOperationID);
                        break;
                    case 3:
                        System.out.printf("%n%s• SEARCH BY ID:%s%n", ANSI_BRIGHT_WHITE, ANSI_RESET);
                        System.out.printf("%s-----------------------------------%s%n", ANSI_BRIGHT_BLACK, ANSI_RESET);
                        System.out.printf("%sExample of Input ->%s %sI-102%s %s|%s %sO-232%s%n%n", ANSI_BRIGHT_BLACK, ANSI_RESET, ANSI_BRIGHT_WHITE, ANSI_RESET, ANSI_BRIGHT_BLACK, ANSI_RESET, ANSI_BRIGHT_WHITE, ANSI_RESET);
                        ID id = getInputID();
                        if (id != null) {
                            if(id.getTypeID() == TypeID.ITEM){
                                searchInfItem(id);
                            }else{
                                searchInfOperation(id);
                            }
                        }
                        break;

                    default:
                        System.out.println(ANSI_BRIGHT_RED + "\nLEAVING..." + ANSI_RESET);
                        break;
                }
                System.out.println("\n" + ANSI_BRIGHT_GREEN + "Information Successfully Shown!" + ANSI_RESET);
            } else {
                System.out.println("\n" + ANSI_BRIGHT_RED + "Operation canceled - File doesn't have information to be read" + ANSI_RESET);
            }
        } catch (Exception e) {
            System.out.println("\n" + ANSI_BRIGHT_RED + e.getMessage() + ANSI_RESET);
        }
    }
```

#### Search Information Item
```java
private void searchInfItem(ID selectedItemID) {
    boolean rawMaterial = getProductionTreeController().isRawMaterial(selectedItemID);
    Node node = getProductionTreeController().getItemNode(selectedItemID,rawMaterial);
    String[] parentAndQtd = getProductionTreeController().findParentItem(node,rawMaterial,selectedItemID);
    printNodeItemInf(node,parentAndQtd[0],selectedItemID,parentAndQtd[1]);
}
```

#### Print Item Information
```java
    private void printNodeItemInf(Node node, String parentName,ID selectedItemID,String qtd) {
        System.out.printf("%n%n%s========================================%s%n", ANSI_BRIGHT_BLACK, ANSI_RESET);
        System.out.printf("     %sNODE INFORMATION | %s NODE%s%n", ANSI_BRIGHT_WHITE, "MATERIAL", ANSI_RESET);
        System.out.printf("%s========================================%s%n", ANSI_BRIGHT_BLACK, ANSI_RESET);
        System.out.printf("%s•%s Name Material: %s%s%s%n", ANSI_BRIGHT_BLACK, ANSI_RESET, ANSI_BRIGHT_WHITE,getProductionTreeController().findNameItem(selectedItemID), ANSI_RESET);
        System.out.printf("%s•%s ID Material: %s%s%s%n", ANSI_BRIGHT_BLACK, ANSI_RESET, ANSI_BRIGHT_WHITE, selectedItemID, ANSI_RESET);
        System.out.printf("%s•%s Quantity: %s%s%s%n", ANSI_BRIGHT_BLACK, ANSI_RESET, ANSI_BRIGHT_WHITE,qtd ,ANSI_RESET);
        if (parentName == null) {
            System.out.printf("%s•%s Parent Operation: %sNone%s%n", ANSI_BRIGHT_BLACK, ANSI_RESET, ANSI_BRIGHT_RED, ANSI_RESET);
        } else {
            System.out.printf("%s•%s Parent Operation: %s%s%s%n", ANSI_BRIGHT_BLACK, ANSI_RESET, ANSI_BRIGHT_WHITE, parentName, ANSI_RESET);
        }
    }
```

#### Search Information Operation
```java
    private void searchInfOperation(ID selectedOperationID) {
    Node node = getProductionTreeController().getOperationNode(selectedOperationID);
    String parentName = getProductionTreeController().findParentOperation(node);
    printNodeOperationInf(node, parentName,selectedOperationID);
}
```


#### Get Input ID
```java
    private ID getInputID() {
    Scanner scanner = new Scanner(System.in);
    String inputID;
    System.out.print("Enter an ID: ");
    inputID = scanner.nextLine();

    if (!checkInputID(inputID)) {
        do {
            System.out.print("Enter an ID (follow the example): ");
            inputID = scanner.nextLine();
        } while (!checkInputID(inputID));
    }
    char type = inputID.charAt(0);
    int serial = Integer.parseInt(inputID.split("-")[1]);
    switch (Character.toUpperCase(type)) {
        case 'I':
            return new ID(serial, TypeID.ITEM);
        case 'O':
            return new ID(serial, TypeID.OPERATION);
        default:
            return null;
    }
}
```

#### Check Input ID
```java
    private boolean checkInputID(String inputID) {
    char reference = inputID.charAt(0);
    reference = Character.toUpperCase(reference);
    return (reference == 'I' || reference == 'O') &&
            inputID.charAt(1) == '-' && Character.isDigit(inputID.charAt(2));
}
```

#### Print Node Operation Information
```java
    private void printNodeOperationInf(Node node, String parentName,ID selectedOperationID) {
        System.out.printf("%n%n%s========================================%s%n", ANSI_BRIGHT_BLACK, ANSI_RESET);
        System.out.printf("   %sNODE INFORMATION | %s NODE%s%n", ANSI_BRIGHT_WHITE, "OPERATION", ANSI_RESET);
        System.out.printf("%s========================================%s%n", ANSI_BRIGHT_BLACK, ANSI_RESET);
        System.out.printf("%s•%s Name Operation: %s%s%s%n", ANSI_BRIGHT_BLACK, ANSI_RESET, ANSI_BRIGHT_WHITE, getProductionTreeController().findNameOperation(node.getOperationID()), ANSI_RESET);
        System.out.printf("%s•%s ID Operation: %s%s%s%n", ANSI_BRIGHT_BLACK, ANSI_RESET, ANSI_BRIGHT_WHITE, selectedOperationID, ANSI_RESET);
        System.out.printf("%s•%s Quantity: %sN/A%s%n", ANSI_BRIGHT_BLACK, ANSI_RESET, ANSI_BRIGHT_WHITE, ANSI_RESET);
        if (parentName == null) {
            System.out.printf("%s•%s Parent Operation: %sNone%s%n", ANSI_BRIGHT_BLACK, ANSI_RESET, ANSI_BRIGHT_RED, ANSI_RESET);
        } else {
            System.out.printf("%s•%s Parent Operation: %s%s%s%n", ANSI_BRIGHT_BLACK, ANSI_RESET, ANSI_BRIGHT_WHITE, parentName, ANSI_RESET);
        }
}
```

### Class Production Tree Controller


#### Get Information
```java
    public boolean getInformations(String path) {
    productionTree = new ProductionTree();
    return productionTree.getInformations(path);
}
```

#### Get List To Show
```java
    public Map<ID, String> getListToShow(int flag) {
    Map<ID, String> map = new HashMap<>();
    if (flag == 1) {
        for (Item material : getItemRepository().getItemList()) {
            map.put(material.getItemID(), material.getName());
        }
        return map;
    }

    for (Operation operation : getOperationRepository().getOperations()) {
        map.put(operation.getOperationId(), operation.getOperationName());
    }
    return map;
}
```

#### Is Raw Material
```java
    public boolean isRawMaterial(ID selectedOperationID) {
        return getProductionTree().getRawMaterials().containsKey(selectedOperationID);
    }
```

#### Get Item Node
```java
    public Node getItemNode(ID selectedOperationID, boolean rawMaterial) {
    if (rawMaterial) {
        return getProductionTree().getRawMaterials().get(selectedOperationID);
    }
    return getProductionTree().getMaterials().get(selectedOperationID);
}
```

#### Get Operation Node
```java
    public Node getOperationNode(ID selectedOperationID) {
        return getProductionTree().getOperationNodeID().get(selectedOperationID);
}
```

#### Find Parent Operation
```java
    public String findParentOperation(Node node) {
    int height = node.getHeigthInTree();
    if (height != 0) {
        List<Node> nodesByHeight = getProductionTree().getHeightMap().get(node.getHeigthInTree() - 1);
        for (Node nodeInList : nodesByHeight) {
            if (nodeInList.getOperationMap().containsKey(node.getOperationID())) {
                return findNameOperation(nodeInList.getOperationID());
            }
        }
    }
    return null;
}
```

#### Find Name Operation
```java
    public String findNameOperation(ID operationID) {
        return getOperationRepository().getIdToOperation().get(operationID).getOperationName();
}
```

#### Find Parent Item
```java
    public String[] findParentItem(Node node, boolean rawMaterial, ID selectedOperationID) {
    String[] parentAndQtd = new String[2];
    if (rawMaterial) {
        parentAndQtd[0] = findNameItem(node.getItemID());
        parentAndQtd[1] = String.valueOf(node.getMaterialMap().get(selectedOperationID));
        return parentAndQtd;
    }
    int height = node.getHeigthInTree();
    if (height != 0) {
        List<Node> nodesByHeight = getProductionTree().getHeightMap().get(node.getHeigthInTree() - 1);
        for (Node nodeInList : nodesByHeight) {
            if (nodeInList.getOperationMap().containsKey(node.getOperationID())) {
                parentAndQtd[0] = findNameItem(nodeInList.getItemID());
                parentAndQtd[1] = String.valueOf(nodeInList.getItem_qtd());
                return parentAndQtd;
            }
        }
    }
    parentAndQtd[0] = null;
    parentAndQtd[1] = String.valueOf(node.getItem_qtd());
    return parentAndQtd;
}
```

#### Find Name Item
```java
public String findNameItem(ID itemID) {
    return getItemRepository().getMapItemList().get(itemID).getName();
}
```

#### Get Operation ID
```java
public Node getNodeByOperationID(ID operationID) {
    return getProductionTree().getOperationNodeID().get(operationID);
}
```

#### Get Item Name By ID
```java
public String getItemNameByID(ID itemID) {
    return getItemRepository().getItemNameByID(itemID);
}
```
