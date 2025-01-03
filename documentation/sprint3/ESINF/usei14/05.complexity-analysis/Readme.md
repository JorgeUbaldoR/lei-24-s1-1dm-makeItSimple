# USEI14 - Show Critical Path(s)

## CriticalPath Complexity Analysis

| **Method**                 | **Complexity**          |
|----------------------------|-------------------------|
| **getCriticalPath()**      | O(n + e \* log e)       |
| **getHeightMap()**         | O(1)                   |
| **getOperationNodeID()**   | O(1)                   |
| **getOperationNameByID()** | O(1)                   |
| **getOperationMap()**      | O(k)                   |

---

### **1. Method `getCriticalPath()`**

- **Explanation:**
  - **Initial Setup:**
    - `getInformations()` is called to populate the production tree. As previously analyzed, it has a complexity of **O(n \* m)**.
    - `getHeightMap()` and `getOperationNodeID()` are both constant-time operations **O(1)** as they simply return precomputed maps.
  - **Root Node Identification:**
    - `heightMap.keySet().stream().min()` iterates over the keys of `heightMap`. Assuming `n` heights, this is **O(n)**.
  - **Priority Queue Processing:**
    - For every node in the tree, its children are added to the priority queue. Assuming the tree has `n` nodes and `e` edges:
      - Each insertion and removal from the priority queue takes **O(log e)**.
      - The total number of operations is proportional to the number of edges (`e`), making the complexity of this section **O(e \* log e)**.
  - **Path Construction:**
    - Constructing and adding paths to `criticalPaths` depends on the number of leaf nodes. In the worst case, this involves processing all nodes once, which is bounded by **O(n)**.

- **Complexity:** Dominated by the priority queue operations and edge traversals: **O(e \* log e)**.

---

### **2. Method `getHeightMap()`**

- **Explanation:**
  - This method simply returns the precomputed `heightMap`. No iteration or computation is performed.

- **Complexity:** **O(1)**.

---

### **3. Method `getOperationNodeID()`**

- **Explanation:**
  - Similarly, this method returns the precomputed `operationNodeID`. No iteration or computation is performed.

- **Complexity:** **O(1)**.

---

### **4. Method `getOperationNameByID()`**

- **Explanation:**
  - This method retrieves the name of an operation by calling `getNameByID()` on the `operationRepository`. Assuming the repository is implemented as a hash map, key lookup is constant time.

- **Complexity:** **O(1)**.

---

### **5. Method `getOperationMap()`**

- **Explanation:**
  - This method returns the `operationMap` for the current node. Accessing a map is constant time, but iterating over its entries depends on the number of child operations (`k`).

- **Complexity:** **O(k)**, where `k` is the number of child operations.

---

### **Overall Complexity of `getCriticalPath`**

- Combining all factors:
  - **Tree Initialization (`getInformations()`):** O(n \* m)
  - **Priority Queue Processing:** O(e \* log e)
  - **Other Operations (constant time methods):** Negligible.

- **Final Complexity:** **O(n \* m + e \* log e)**, where:
  - `n` is the number of nodes.
  - `m` is the average number of materials per node.
  - `e` is the number of edges in the tree.

---

