
---

# USEI12 - Update material quantities in the production tree

## 4. Tests

### **Test 1: Update Material Valid**

```java
@Test
void testUpdateMaterialValid() {
        ID materialID = productionTree.getMaterials().keySet().iterator().next();
        Node materialNode = productionTree.getMaterials().get(materialID);
        float currentQuantity = materialNode.getItem_qtd();
        float factor = 3.0f;
        float newQuantity = currentQuantity * factor;

        updateMaterial.updateMaterial(materialID, newQuantity);

        assertEquals(newQuantity, materialNode.getItem_qtd(), "Material quantity should be updated.");
        }

```
**Objective:**  
Verify that the updateMaterial method updates the material's quantity correctly when provided with a valid material ID and new quantity.

**Validations:**
1. The material's quantity is updated to the new value.

**Expected Result:**
1. The material's quantity matches the newly updated value.

---

### **Test 2: Update Material Updates Dependent Materials**

```java
@Test
void testUpdateMaterialUpdatesDependentMaterials() {
        ID materialID = productionTree.getMaterials().keySet().iterator().next();
        Node materialNode = productionTree.getMaterials().get(materialID);

        Map<ID, Float> initialRawMaterials = materialNode.getMaterialMap();
        float factor = 1.5f;
        float newQuantity = materialNode.getItem_qtd() * factor;

        updateMaterial.updateMaterial(materialID, newQuantity);

        for (Map.Entry<ID, Float> entry : initialRawMaterials.entrySet()) {
        ID rawMaterialID = entry.getKey();
        float expectedQuantity = entry.getValue();
        float actualQuantity = materialNode.getMaterialMap().get(rawMaterialID);

        assertEquals(expectedQuantity, actualQuantity,
        "Raw material quantities should be updated proportionally.");
        }
        }
```
**Objective:**  
Ensure that when a material's quantity is updated, the dependent raw material quantities are updated proportionally.

**Validations:**
1. The dependent materials' quantities are adjusted based on the new quantity of the updated material.

**Expected Result:**
1. The dependent materials are updated proportionally to reflect the new material quantity.

---

### **Test 3: Update Material Throws Exception For Invalid Material**

```java
@Test
void testUpdateMaterialThrowsExceptionForInvalidMaterial() {
        ID invalidMaterialID = new ID(9999, TypeID.ITEM);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
        updateMaterial.updateMaterial(invalidMaterialID, 100),
        "Expected exception for non-existent material ID."
        );

        assertTrue(exception.getMessage().contains("Material not found"),
        "Exception message should indicate invalid material.");
        }
```
**Objective:**  
Verify that an exception is thrown when attempting to update a material that does not exist.

**Validations:**
1. The method throws an IllegalArgumentException for an invalid material ID.
2. The exception message includes information about the invalid material.

**Expected Result:**
1. An exception is thrown with an appropriate error message.

---

### **Test 4: Update Material Propagates To Dependent Operations**

```java
@Test
void testUpdateMaterialPropagatesToDependentOperations() {
        ID materialID = productionTree.getMaterials().keySet().iterator().next();
        Node materialNode = productionTree.getMaterials().get(materialID);

        Map<ID, Float> initialOperations = materialNode.getOperationMap();
        float factor = 2.0f;
        float newQuantity = materialNode.getItem_qtd() * factor;

        updateMaterial.updateMaterial(materialID, newQuantity);

        for (Map.Entry<ID, Float> entry : initialOperations.entrySet()) {
        Node operationNode = productionTree.findNodeByOperation(entry.getKey());
        assertEquals(factor, operationNode.getItem_qtd() / materialNode.getItem_qtd(),
        "Dependent operation quantities should be updated proportionally.");
        }
        }
```
**Objective:**  
Validate that updates to a material's quantity propagate to dependent operations and update their quantities proportionally.

**Validations:**
1. Dependent operations are adjusted to reflect the updated material quantity.

**Expected Result:**
1. The dependent operations are updated proportionally based on the new material quantity.

---

##  5. Implementation 


##  Class UpdateMaterial

### Method - UpdateMaterial Constructor
```java
/**
 * Constructor to initialize the UpdateMaterial with a given production tree.
 * <p>
 * The production tree is used to access and modify the material quantities.
 * </p>
 *
 * @param pdt The production tree containing the materials.
 */
public UpdateMaterial(ProductionTree pdt) {
        this.pdt = pdt;
        this.materials = pdt.getMaterials();
        }
```

### Method - updateMaterial
```java
/**
 * Updates the quantity of a specific material in the production tree.
 * <p>
 * The method retrieves the material by its ID, calculates the new quantity based on the provided
 * new quantity relative to the current quantity, and updates both the material and its associated raw materials.
 * </p>
 *
 * @param materialID The ID of the material to be updated.
 * @param newQuantity The new quantity to be set for the material.
 * @return The updated production tree with the new material quantities.
 * @throws IllegalArgumentException If the material is not found or is a raw material (not updatable).
 */
public ProductionTree updateMaterial(ID materialID, float newQuantity) {
        Node materialNode = materials.get(materialID);

        if (materialNode == null) {
        throw new IllegalArgumentException("Material not found / is raw material: " + materialID);
        }

        float currentQuantity = materialNode.getItem_qtd();
        float quantity = newQuantity / currentQuantity;
        System.out.println(ANSI_BRIGHT_GREEN + "\nCHANGES MADE..." + ANSI_RESET);
        updateQTD(materialNode, quantity);

        return pdt;
        }
```

### Method - updateQTD
```java
/**
 * Recursively updates the quantity of an item and its dependent materials and operations.
 * <p>
 * The method first updates the current item's quantity, and then propagates the update to its raw materials
 * and operations dependent on this item.
 * </p>
 *
 * @param node The node representing the item whose quantity needs to be updated.
 * @param quantity The factor by which the item's quantity is to be updated.
 */
private void updateQTD(Node node, float quantity) {
        node.setItem_qtd(quantity);

        System.out.println("• " + ANSI_BRIGHT_BLACK + "Update Item:" + ANSI_RESET + " " + node.getItemID() + " -> " + node.getItem_qtd());

        if (!node.getMaterialMap().isEmpty()) updateRawMaterialsQuantity(node, quantity);
        for (ID current_id : node.getOperationMap().keySet()) {
        Node childNode = pdt.findNodeByOperation(current_id);

        updateQTD(childNode, quantity);
        }
        }
```

### Method - updateRawMaterialsQuantity
```java
/**
 * Updates the quantities of raw materials associated with a given item.
 * <p>
 * The method multiplies the raw material's current quantity by the specified update factor.
 * </p>
 *
 * @param node The node representing the item whose raw materials are being updated.
 * @param quantity The factor by which the raw materials' quantities are to be updated.
 */
private void updateRawMaterialsQuantity(Node node, float quantity) {
        Map<ID, Float> rawMaterials = node.getMaterialMap();
        for (Map.Entry<ID, Float> raw : rawMaterials.entrySet()) {
        raw.setValue(raw.getValue() * quantity);
        System.out.println("• " + ANSI_BRIGHT_BLACK + "Update Raw Materials:" + ANSI_RESET + " " + raw.getKey() + " -> " + raw.getValue());
        }
        }
```



