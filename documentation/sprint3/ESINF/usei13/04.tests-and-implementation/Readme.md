# USEI13 - Show Total Quantity of Required Materials

## 4. Tests

## Production Tree Class

### **Test : testGetTotalRequiredMaterials**

```java

@Test
void testGetTotalRequiredMaterials() throws IOException {
    System.out.println("Test GetTotalRequiredMaterials");
    String filePath = "prodPlanSimulator(ESINF)/main/java/pt/ipp/isep/dei/esoft/project/files/input/testMaterials.csv";

    // Create a test file with material data
    try (FileWriter writer = new FileWriter(filePath)) {
        writer.write("""
                op_id;item_id;item_qtd;(;op1;op_qtd1;op2;op_qtd2;opN;op_qtdN;);(;item_id1;item_qtd1;item_id1;item_qtd1;item_id1;item_qtd1;)
                20;1006;1;(;11;1;;;;;);(;1014;0,125;;;;;)
                11;1010;1;(;17;1;16;4;;;);(;;;;;;;)
                17;1004;2;(;15;1;;;;;);(;1007;4;1005;12;;;)
                16;1002;1;(;14;1;;;;;);(;1013;1;;;;;)
                14;1001;1;(;18;1;;;;;);(;;;;;;;)
                18;1008;1;(;12;1;;;;;);(;;;;;;;)
                12;1011;1;(;;;;;;;);(;1016;0,28;;;;;)
                15;1003;1;(;19;1;;;;;);(;;;;;;;)
                19;1009;1;(;13;1;;;;;);(;;;;;;;)
                13;1012;1;(;;;;;;;);(;1015;0,0576;;;;;)
                """);
    }

    // Load data and calculate required materials
    ProductionTree productionTree = new ProductionTree();
    productionTree.getInformations(filePath);
    Map<ID, Float> totalMaterials = productionTree.getTotalRequiredMaterials();

    // Verify materials and quantities
    assertEquals(6, totalMaterials.size(), "There should be 6 raw materials in total.");
    assertTrue(totalMaterials.containsKey(new ID(1014, TypeID.ITEM)), "Material 1014 should be present.");
    assertEquals(0.125f, totalMaterials.get(new ID(1014, TypeID.ITEM)), 0.01, "Quantity for material 1014 is incorrect.");
}
```

## Objective
To verify that the `getTotalRequiredMaterials` method correctly calculates the total quantity of raw materials required based on the input data, ensuring proper aggregation and accurate calculations.

## Validations
1. Ensure the `totalMaterials` map is not null and contains the correct number of entries (6 in this case).
2. Verify that the `totalMaterials` map includes all expected material IDs, such as `1014`.
3. Confirm that the calculated quantities for each material match the expected values, considering the hierarchy and dependencies specified in the input file.

## Expected Result
The `totalMaterials` map is accurately populated, containing the expected number of materials, correct IDs, and valid quantities as per the input data.



---

## 5. Construction (Implementation)


### Class ProductionTree

###### Get the Total of Required Materials

```java
/**
 * Calculates the total quantity of required materials for production.
 *
 * @return a map where the key is the material ID and the value is the total quantity required
 * <p>
 * Time Complexity: O(n), where n is the number of raw materials. The loop iterates through all raw materials,
 * and the operations inside the loop are O(1) assuming constant-time map operations.
 */
public Map<ID, Float> getTotalRequiredMaterials() {
    getInformations(BOO_PATH);

    Map<ID, Float> totalRequiredMaterials = new HashMap<>();

    for (Map.Entry<ID, Node> rawMaterial : rawMaterials.entrySet()) {
        Node materialNode = rawMaterial.getValue();
        ID materialID = rawMaterial.getKey();

        float itemQtd = materialNode.getItem_qtd();
        float totalQtd = itemQtd * materialNode.getItemQtdByID(materialID);

        totalRequiredMaterials.put(materialID, totalQtd);
    }

    return totalRequiredMaterials;
}
````


