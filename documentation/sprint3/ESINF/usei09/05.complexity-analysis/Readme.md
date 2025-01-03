# USEI09 - Efficient Search

##

### **ProductionTreeController Complexity Analysis**

| Method                                                                 | Complexity |
|------------------------------------------------------------------------|------------|
| ProductionTreeController()                                             | O(1)       |
| getItemRepository()                                                    | O(1)       |
| getOperationRepository()                                               | O(1)       |
| setName(String name)                                                   | O(1)       |
| getProductionTree()                                                    | O(1)       |
| getInformations(String path)                                           | O(P)       |
| getListToShow(int flag)                                                | O(N)       |
| isRawMaterial(ID selectedOperationID)                                  | O(1)       |
| getItemNode(ID selectedOperationID, boolean rawMaterial)               | O(1)       |
| getOperationNode(ID selectedOperationID)                               | O(1)       |
| findParentOperation(Node node)                                         | O(N)       |
| findNameOperation(ID operationID)                                      | O(1)       |
| findParentItem(Node node, boolean rawMaterial, ID selectedOperationID) | O(H + N)   |
| findNameItem(ID itemID)                                                | O(1)       |
| getNodeByOperationID(ID operationID)                                   | O(1)       |
| getItemNameByID(ID itemID)                                             | O(1)       |

#### 1. **Complexity of Repository Methods (`getItemRepository`, `getOperationRepository`)**

These methods check if the repository is already initialized and return it. The complexity is \(O(1)\) since these
methods only perform initialization checks or return existing references.

#### 2. **Method `getInformations(String path)`**

This method is responsible for loading data into the production tree from a file at the given `path`. Assuming loading
involves parsing the file and constructing the tree, the complexity would depend on the size of the file \(P\) (number
of lines or operations/items). The worst-case complexity would be \(O(P)\), where \(P\) is the size of the file.

#### 3. **Method `getListToShow(int flag)`**

This method iterates over the list of items or operations in the repository. If it iterates over \(N\) items or
operations, the complexity is \(O(N)\), where \(N\) is the number of items or operations.

#### 4. **Method `isRawMaterial(ID selectedOperationID)`**

This method checks if the `selectedOperationID` is a raw material by accessing the map of raw materials. The map lookup
is \(O(1)\) since it uses a hash map.

#### 5. **Method `getItemNode(ID selectedOperationID, boolean rawMaterial)`**

This method retrieves a node from either the raw materials or materials map. Both maps are likely hash maps, so the
lookup is \(O(1)\).

#### 6. **Method `getOperationNode(ID selectedOperationID)`**

This method retrieves the operation node from the map. As with other map lookups, this is \(O(1)\).

#### 7. **Method `findParentOperation(Node node)`**

This method finds the parent operation by traversing the tree structure. The height of the tree is denoted by \(H\), and
within each level, it looks through a list of nodes. Therefore, the worst-case time complexity is \(O(N)\), where \(N\)
is the total number of nodes at all levels.

#### 8. **Method `findNameOperation(ID operationID)`**

This method fetches the operation name by looking up the operation ID in a map. Since it uses a map, the lookup time
complexity is \(O(1)\).

#### 9. **Method `findParentItem(Node node, boolean rawMaterial, ID selectedOperationID)`**

This method traverses the tree's nodes based on the height of the tree. If the height is \(H\), it will check \(H\)
levels in the worst case. Within each level, it checks \(N\) nodes, so the complexity is \(O(H + N)\), where \(H\) is
the tree height and \(N\) is the number of nodes at each level.

#### 10. **Method `findNameItem(ID itemID)`**

This method fetches the item name by looking up the item ID in a map. Map lookups are \(O(1)\).

#### 11. **Method `getNodeByOperationID(ID operationID)`**

This method retrieves a node from the operation node map. Map lookups are \(O(1)\).

#### 12. **Method `getItemNameByID(ID itemID)`**

This method retrieves the item name by ID, again performing a map lookup. The complexity is \(O(1)\).

---

### **Overall Complexity Analysis**

The overall complexity of the `ProductionTreeController` depends on the operations being performed. For most methods,
the complexity is \(O(1)\), due to efficient data structures like hash maps used for lookups. However, methods like
`getInformations` and `findParentOperation` may involve traversing larger datasets, leading to higher complexity.

For methods that traverse the production tree or work with lists (such as `getListToShow` and `findParentOperation`),
the overall complexity can be \(O(N)\), where \(N\) is the number of items, operations, or nodes being processed.

Thus, the overall complexity is roughly dominated by methods that deal with the tree structure or large lists:

\[
O(N) \quad \text{(for most list-based operations and tree traversals)}
\]

##

### **ProductionTree Complexity Analysis**

| Method                                    | Complexity |
|-------------------------------------------|------------|
| ProductionTree()                          | O(1)       |
| getItemRepository()                       | O(1)       |
| getInformations(String path)              | O(n * m)   |
| associatedQtdToItem(ID itemID, float qtd) | O(1)       |
| fillTreeHeight(Node node, int height)     | O(n)       |
| findNodeByOperation(ID id)                | O(n)       |
| setPdtTreeName(String pdtTreeName)        | O(1)       |
| getNodesOfTree()                          | O(1)       |
| getHeightMap()                            | O(1)       |
| getOperationNodeID()                      | O(1)       |
| getMaterials()                            | O(1)       |
| getRawMaterials()                         | O(1)       |
| getTotalRequiredMaterials()               | O(n)       |

#### 1. **Constructor (`ProductionTree()`)**

The constructor initializes several data structures (e.g., `heightMap`, `nodesOfTree`, `materials`, `rawMaterials`,
`operationNodeID`) and calls `getItemRepository()`. This initialization is done in constant time, so the complexity is:

\[
O(1)
\]

#### 2. **Method `getItemRepository()`**

This method checks if the `ItemRepository` is initialized. If not, it initializes it by accessing the `Repositories`
singleton. This involves a single conditional check and map access, making the complexity:

\[
O(1)
\]

#### 3. **Method `getInformations(String path)`**

This method reads information from a file, processes it, and constructs nodes. The complexity depends on the number of
records (`n`) and the number of operations/materials per record (`m`). The method iterates over `n` records, and for
each record, it processes operations and materials, which adds a factor of `m`. Therefore, the time complexity of this
method is:

\[
O(n * m)
\]

where `n` is the number of records, and `m` is the average number of operations/materials per record.

#### 4. **Method `associatedQtdToItem(ID itemID, float qtd)`**

This method updates the quantity of an item in the `ItemRepository`. Since it accesses a map with a constant-time
lookup, the complexity is:

\[
O(1)
\]

#### 5. **Method `fillTreeHeight(Node node, int height)`**

This method recursively calculates the height of each node in the tree. The complexity depends on the number of nodes in
the tree (`n`), as it processes each node exactly once. The worst-case complexity is:

\[
O(n)
\]

where `n` is the number of nodes.

#### 6. **Method `findNodeByOperation(ID id)`**

This method searches through the `nodesOfTree` list to find a node corresponding to the given operation ID. In the worst
case, it checks each node, resulting in a linear search. Therefore, the complexity is:

\[
O(n)
\]

where `n` is the number of nodes in the tree.

#### 7. **Method `setPdtTreeName(String pdtTreeName)`**

This method sets the name of the production tree. Since it involves a simple field assignment, the complexity is:

\[
O(1)
\]

#### 8. **Getter
Methods (`getNodesOfTree()`, `getHeightMap()`, `getOperationNodeID()`, `getMaterials()`, `getRawMaterials()`)**

All these getter methods return simple references to fields. Since no iteration or complex operations are involved, the
complexity for each of these methods is:

\[
O(1)
\]

#### 9. **Method `getTotalRequiredMaterials()`**

This method calculates the total quantity of required materials for production. It first calls `getInformations()`,
which has a complexity of \(O(n * m)\), and then iterates through the `rawMaterials` map (which has a size of `n`). The
complexity of iterating through the raw materials is \(O(n)\), and each operation within the loop is \(O(1)\), making
the overall complexity of `getTotalRequiredMaterials()`:

\[
O(n * m + n)
\]

Since \(O(n * m)\) dominates \(O(n)\), the final complexity is:

\[
O(n * m)
\]

### **Overall Complexity**

The overall complexity of the `ProductionTree` class is primarily driven by the methods that process the information
file (`getInformations()`) and the method that calculates the required materials (`getTotalRequiredMaterials()`). The
overall complexity of these operations is \(O(n * m)\), where `n` is the number of records and `m` is the average number
of operations/materials per record.

Therefore, the overall complexity of the `ProductionTree` class is:

\[
O(n * m)
\]

where `n` is the number of records in the file and `m` is the average number of operations/materials per record.

### **Node Class: Complexity Analysis**

| Method                                                                                                       | Complexity |
|--------------------------------------------------------------------------------------------------------------|------------|
| Node(ID operationID, ID itemID, float itemQuantity, Map<ID, Float> operationMap, Map<ID, Float> materialMap) | O(1)       |
| getOperationID()                                                                                             | O(1)       |
| getItemID()                                                                                                  | O(1)       |
| getItemQuantity()                                                                                            | O(1)       |
| getOperationMap()                                                                                            | O(1)       |
| getMaterialMap()                                                                                             | O(1)       |
| getItemQuantityByID(ID id)                                                                                   | O(1)       |
| setOperationMap(Map<ID, Float> operationMap)                                                                 | O(1)       |
| setMaterialMap(Map<ID, Float> materialMap)                                                                   | O(1)       |
| setHeightInTree(int heightInTree)                                                                            | O(1)       |
| getHeightInTree()                                                                                            | O(1)       |
| toString()                                                                                                   | O(n + m)   |

### 1. Method `Node`

- The constructor of the `Node` class initializes various attributes of the object, such as `operationID`, `itemID`,
  `itemQuantity`, `operationMap`, `materialMap`, and `heightInTree`. All these operations are simple assignments or
  initializations, so the complexity is \(O(1)\).

### 2. Method `getOperationID`

- This method returns the `operationID` field, which is a simple field access operation. The complexity is \(O(1)\).

### 3. Method `getItemID`

- This method returns the `itemID` field, which is another simple field access operation, resulting in \(O(1)\)
  complexity.

### 4. Method `getItemQuantity`

- This method returns the `itemQuantity` field, which is a constant-time operation. The complexity is \(O(1)\).

### 5. Method `getOperationMap`

- This method returns the `operationMap` field, which is a simple field access operation. The complexity is \(O(1)\).

### 6. Method `getMaterialMap`

- This method returns the `materialMap` field, which is also a simple field access operation. The complexity is \(O(
  1)\).

### 7. Method `getItemQuantityByID`

- This method performs a lookup on the `materialMap` to retrieve the quantity associated with the provided ID. Since
  lookups in a hash map are constant time, the complexity is \(O(1)\).

### 8. Method `setOperationMap`

- This method sets the `operationMap` field to a new value. It is a simple assignment operation, so the complexity is
  \(O(1)\).

### 9. Method `setMaterialMap`

- This method sets the `materialMap` field to a new value. This is a simple assignment operation, so the complexity is
  \(O(1)\).

### 10. Method `setHeightInTree`

- This method sets the `heightInTree` field to a new value. It is a simple assignment, resulting in \(O(1)\) complexity.

### 11. Method `getHeightInTree`

- This method returns the `heightInTree` field, which is a simple field access operation. The complexity is \(O(1)\).

### 12. Method `toString`

- The `toString` method constructs a string representation of the `Node` object. It iterates over the `operationMap` and
  `materialMap` to append the values to the string. The complexity of this operation depends on the sizes of the maps,
  resulting in \(O(n + m)\), where `n` is the size of the `operationMap` and `m` is the size of the `materialMap`.

### Overall Complexity

Overall, the complexity of most methods in the `Node` class is \(O(1)\), meaning they operate in constant time. The only
exception is the `toString` method, which has a complexity of \(O(n + m)\) due to the iteration over the maps. This
indicates that the `Node` class is efficient in its operations for most methods, with the exception of string
representation, which depends on the size of the data stored in the maps.

### **Search Tree UI Class : Complexity Analysis**

| Method                                                                          | Complexity   |
|---------------------------------------------------------------------------------|--------------|
| `SearchProductionTreeUI()`                                                      | O(1)         |
| `getProductionTreeController()`                                                 | O(1)         |
| `run()`                                                                         | O(n + k)     |
| `getChoice(int max)`                                                            | O(1)         |
| `doChoice(int choice, String path)`                                             | O(max(m, o)) |
| `searchInfItem(ID selectedItemID)`                                              | O(p)         |
| `printNodeItemInf(Node node, String parentName, ID selectedItemID, String qtd)` | O(1)         |
| `searchInfOperation(ID selectedOperationID)`                                    | O(p)         |
| `getInputID()`                                                                  | O(n)         |
| `checkInputID(String inputID)`                                                  | O(1)         |
| `printNodeOperationInf(Node node, String parentName, ID selectedOperationID)`   | O(1)         |
| `showAndSelectOperation()`                                                      | O(o)         |
| `showOptions(Map<ID, String> list)`                                             | O(l)         |

---

### Explanation of Complexity for Each Method:

1. **`SearchProductionTreeUI()`**:  
   The constructor initializes the controller and performs simple assignments. Complexity: **O(1)**.

2. **`getProductionTreeController()`**:  
   Simple field retrieval. Complexity: **O(1)**.

3. **`run()`**:  
   Displays the menu and handles user interaction. The complexity depends on the `doChoice` method, which may involve
   linear operations on data lists. Complexity: **O(n + k)**, where `n` is for the input list traversal in `doChoice`,
   and `k` is the input validation in `getChoice`.

4. **`getChoice(int max)`**:  
   Simple input validation, assuming bounded iterations. Complexity: **O(1)**.

5. **`doChoice(int choice, String path)`**:  
   Depending on the case, this method interacts with materials (`O(m)`) or operations (`O(o)`) lists, where `m` and `o`
   are their respective sizes. Complexity: **O(max(m, o))**.

6. **`searchInfItem(ID selectedItemID)`**:  
   Retrieves item information using the controller. Complexity depends on the controller’s internal operations. Assuming
   it involves searching within a list, complexity: **O(p)**, where `p` is the size of the parent data list.

7. **`printNodeItemInf(...)`**:  
   Prints item information. This is straightforward string formatting. Complexity: **O(1)**.

8. **`searchInfOperation(ID selectedOperationID)`**:  
   Similar to `searchInfItem`, retrieves data using the controller. Complexity: **O(p)**.

9. **`getInputID()`**:  
   Reads user input and validates it in a loop. Assuming `n` iterations for input validation, complexity: **O(n)**.

10. **`checkInputID(String inputID)`**:  
    Validates input using character checks and comparisons. Complexity: **O(1)**.

11. **`printNodeOperationInf(...)`**:  
    Prints operation information. Similar to `printNodeItemInf`, complexity: **O(1)**.

12. **`showAndSelectOperation()`**:  
    Iterates through the list of operations to display options. Complexity: **O(o)**, where `o` is the size of the
    operations list.

13. **`showOptions(Map<ID, String> list)`**:  
    Iterates through the map entries. Complexity: **O(l)**, where `l` is the number of entries in the map.

---
##

