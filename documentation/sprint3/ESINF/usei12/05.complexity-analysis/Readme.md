# USEI12 - Update material quantities in the production tree


##

Here is the complexity analysis for the `UpdateMaterial` class following the requested format:

### **UpdateMaterial Complexity Analysis**

| Method                                  | Complexity |
|-----------------------------------------|------------|
| Constructor(UpdateMaterial())           | O(1)       |
| updateMaterial(ID, float)               | O(n + m)   |
| updateQTD(Node, float)                  | O(n + k)   |
| updateRawMaterialsQuantity(Node, float) | O(k)       |

#### 1. **Constructor `(UpdateMaterial())`**

**Complexity:**
The constructor initializes the UpdateMaterial object by setting references to the production tree and its materials. These assignments are direct and involve no iterations, resulting in a time complexity of: O(1)

#### 2. **Method `updateMaterial(ID materialID, float newQuantity)`**

**Complexity:**

**Search Operation:**
The method retrieves the material node using its ID from a Map. The lookup in a HashMap is O(1).

Quantity Update Propagation:

The method calculates the scaling factor and calls updateQTD() to update the material and its dependencies. This involves traversing dependent materials O(m) and operations O(n) where:

m: Number of raw materials associated with the material.

n: Number of dependent operations.

**Overall Complexity:**

The combined operations lead to a total complexity of: O(m + n)

#### 3. **Method `updateQTD(Node node, float quantity)`**

**Complexity:**

**Update:**

The method updates the quantity of the given node, which is a constant-time operation O(1).

**Raw Materials Update:**

The method calls updateRawMaterialsQuantity() to update raw materials, which takes O(k), where k is the number of raw materials associated with the node.

**Recursive Updates:**

For each dependent operation, the method makes a recursive call to itself. In the worst case, it visits every node in the dependency tree, leading to a time complexity of O(n), where n is the total number of operations in the tree.

**Overall Complexity:**

For a node with k raw materials and n dependent operations, the complexity is: O(n + k)

#### 4. **Method `updateRawMaterialsQuantity(Node node, float quantity)`**

**Complexity:**

This method iterates over all raw materials of a node and updates their quantities proportionally. If there are k raw materials, the complexity is: O(k).

**Overall Complexity Analysis:**

The most computationally intensive operation in the UpdateMaterial class is updating dependent materials and operations via recursion. The overall complexity is dominated by the number of dependent nodes in the tree (n) and the number of raw materials (m).

**Worst-case Complexity:**

If every node in the production tree depends on all other nodes and raw materials, the overall complexity is: O(n + m)

**Where:**

n: Total number of nodes (operations) in the dependency tree.

m: Total number of raw materials across all nodes.

This complexity reflects the traversal of all dependent operations and the updates to raw materials for each node.