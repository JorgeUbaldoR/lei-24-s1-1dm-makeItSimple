# USEI14 - Show Critical Path(s)

## 4. Tests

## Production Tree Class

### **Test : testGetCriticalPathValidTree**

```java
@Test
void testGetCriticalPathValidTree() throws IOException {
    System.out.println("Test GetCriticalPath with Valid Tree");

    // Load tree data
    ProductionTree productionTree = new ProductionTree();

    // Call the method and verify the critical paths
    List<List<String>> criticalPaths = productionTree.getCriticalPath();
    assertNotNull(criticalPaths, "Critical paths should not be null.");
    assertEquals(2, criticalPaths.size(), "There should be two critical paths.");

    // Verify the specific paths
    List<String> expectedPath1 = List.of(
            "varnish bench",
            "assemble bench",
            "fix bolt M16 22",
            "drill bench leg",
            "polish bench leg",
            "cut bench leg"
    );

    List<String> expectedPath2 = List.of(
            "varnish bench",
            "assemble bench",
            "fix nut M16 21",
            "drill bench seat",
            "polish bench seat",
            "cut bench seat"
    );


    assertTrue(criticalPaths.contains(expectedPath1), "Expected critical path 1 is missing.");
    assertTrue(criticalPaths.contains(expectedPath2), "Expected critical path 2 is missing.");
}
```

## Objective
To verify that the `getCriticalPath` method accurately identifies and returns all critical paths in the production tree, ensuring correct traversal of dependencies and proper path calculation.

## Validations
1. Ensure that the `criticalPaths` list is not null and contains the correct number of paths (2 in this case).
2. Verify that each expected critical path is present in the result, matching the expected sequences of steps.
3. Confirm that the steps in each critical path are in the correct order, reflecting the hierarchy and dependencies in the production tree.

## Expected Result
The `criticalPaths` list is accurately populated, containing the expected number of paths, each with the correct steps in proper sequence, as derived from the production tree structure.

---

## 5. Construction (Implementation)


### Class ProductionTree

###### Get the Total of Required Materials

```java
/**
 * Computes and returns the critical paths of a production tree.
 * A critical path is defined as the sequence of operations from a root node to a leaf node in the production tree.
 *
 * The method processes the production tree by:
 * 1. Building a height map and an operation-to-node mapping using {@link ProductionTree}.
 * 2. Identifying root nodes (nodes at the minimum height).
 * 3. Traversing the tree using a priority queue, ensuring that paths are explored in increasing order of depth.
 * 4. Collecting paths that end at leaf nodes.
 *
 * @return A {@link List} of critical paths, where each critical path is represented as a {@link List} of {@link String} operation names.
 * @throws IllegalStateException If the tree does not contain any root nodes.
 */

public List<List<String>> getCriticalPath() {
    ProductionTree pdt = new ProductionTree();
    pdt.getInformations(BOO_PATH);

    Map<Integer, List<Node>> heightMap = pdt.getHeightMap();
    Map<ID, Node> operationNodeID = pdt.getOperationNodeID();

    List<List<String>> criticalPaths = new ArrayList<>();

    // Identify root nodes (those at the minimum height)
    int minHeight = heightMap.keySet().stream().min(Integer::compareTo).orElse(0);
    List<Node> rootNodes = heightMap.get(minHeight);

    if (rootNodes == null || rootNodes.isEmpty()) {
        throw new IllegalStateException("Tree does not have root nodes.");
    }

    PriorityQueue<QueueNode> pq = new PriorityQueue<>(Comparator.comparingInt(QueueNode::getDepth));

    for (Node rootNode : rootNodes) {
        pq.offer(new QueueNode(rootNode, new ArrayList<>(), 0));
    }

    while (!pq.isEmpty()) {
        QueueNode current = pq.poll();
        Node currentNode = current.node;
        List<String> currentPath = new ArrayList<>(current.path);

        currentPath.add(currentNode.getOperationNameByID());

        Map<ID, Float> operationMap = currentNode.getOperationMap();
        if (operationMap.isEmpty()) {
            // Leaf node: Add the critical path
            criticalPaths.add(currentPath);
        } else {
            // Add child nodes to the priority queue
            for (Map.Entry<ID, Float> entry : operationMap.entrySet()) {
                ID childId = entry.getKey();
                Node childNode = operationNodeID.get(childId);

                if (childNode != null) {
                    pq.offer(new QueueNode(childNode, currentPath, current.depth + 1));
                }
            }
        }
    }

    return criticalPaths;
}
````


