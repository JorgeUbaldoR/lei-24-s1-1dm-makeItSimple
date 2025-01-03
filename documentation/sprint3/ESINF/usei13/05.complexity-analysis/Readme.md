# USEI13 - Show Total Quantity of Required Materials

##

### **TotalRequiredMaterials Complexity Analysis**

| Method                      | Complexity |
|-----------------------------|------------|
| getTotalRequiredMaterials() | O(n)       |
| getInformations()           | O(n * m)   |
| getItem_qtd()               | O(1)       |
| getItemQtdByID(materialID)  | O(1)       |

#### 1. **Method `getTotalRequiredMaterials()`**

- **Explanation:**
  - This method iterates over the `rawMaterials` map to calculate the total quantity of materials required.
  - For each material in the map:
    - Accessing the `item_qtd` and `getItemQtdByID(materialID)` involves constant-time operations (`O(1)`).
    - The loop runs once for each entry in `rawMaterials`, where `n` is the total number of raw materials.
  - The overall complexity is determined by the size of the `rawMaterials` map.

- **Complexity:** O(n), where `n` is the number of raw materials.

#### 2. **Method `getInformations()`**

- **Explanation:**
  - The method processes the input file to populate the necessary data structures:
    - Iterates over the `readedInformation` list (size `n`).
    - For each iteration, processes the materials (`m` elements) and updates data structures such as `operationMap`, `materialMap`, and `rawMaterials`.
    - Parsing and updating maps involve constant-time operations per entry.
    - The loop is nested: for each entry in `n`, it processes `m` materials.
  - The dominant factor is the nested iteration over `n` entries and their respective `m` materials.

- **Complexity:** O(n × m), where:
  - `n` is the number of entries in `readedInformation`.
  - `m` is the number of materials processed per entry.

#### 3. **Method `getItem_qtd()`**

- **Explanation:**
  - This is a simple getter method that retrieves the `item_qtd` attribute of a `Node`.
  - Accessing an attribute has constant time complexity.

- **Complexity:** O(1)

#### 4. **Method `getItemQtdByID(materialID)`**

- **Explanation:**
  - This method retrieves the quantity of a specific material by accessing the `materialMap` using the provided key.
  - Accessing a `HashMap` by key has constant time complexity.

- **Complexity:** O(1)

---

### **Overall Complexity Analysis of TotalRequiredMaterials**

The overall complexity of calculating total required materials is primarily influenced by the preprocessing step in `getInformations()` and the iteration in `getTotalRequiredMaterials()`.

- `getInformations()`: O(n × m), which dominates the workload due to nested iterations over entries (`n`) and materials (`m`).
- `getTotalRequiredMaterials()`: O(n), which is linear to the number of raw materials but less significant compared to `getInformations()`.

### **Final Overall Complexity:**

- The overall complexity simplifies to **O(n * m)**, where:
  - `n` is the number of entries in `readedInformation`.
  - `m` is the number of materials processed per entry.

---

