# USEI10 - Binary Search Tree - (Tracking Materials Quantities)

##

Here is the complexity analysis for the `BinarySearchTree` class following the requested format:

### **BinarySearchTree Complexity Analysis**

| Method                | Complexity |
|-----------------------|------------|
| BinarySearchTree()    | O(1)       |
| insert(T value)       | O(log n)   |
| find(T value)         | O(log n)   |
| remove(T value)       | O(log n)   |
| inOrderTraversal()    | O(n)       |
| preOrderTraversal()   | O(n)       |
| postOrderTraversal()  | O(n)       |
| levelOrderTraversal() | O(n)       |
| nodesByLevel()        | O(n)       |

#### 1. **Constructor (`BinarySearchTree()`)**

The constructor initializes an empty tree with a `root` set to `null`. This operation does not involve any iteration or
complex operations, so its time complexity is:

\[
O(1)
\]

#### 2. **Method `insert(T value)`**

The `insert()` method inserts a value into the binary search tree by traversing the tree from the root to the
appropriate leaf node, which in the worst case is a depth of `log n` for a balanced tree. Thus, the time complexity is:

\[
O(log n)
\]

where `n` is the number of nodes in the tree.

#### 3. **Method `find(T value)`**

The `find()` method searches for a value in the tree by traversing from the root, following left or right subtrees based
on the comparison. The worst-case time complexity is also `O(log n)` for a balanced tree, since it involves traversing
down the height of the tree:

\[
O(log n)
\]

where `n` is the number of nodes in the tree.

#### 4. **Method `remove(T value)`**

The `remove()` method removes a node from the tree by first searching for the node (`O(log n)`) and then performing
additional work depending on whether the node has zero, one, or two children. The overall complexity for removal is
still dominated by the search operation, resulting in:

\[
O(log n)
\]

where `n` is the number of nodes in the tree.

#### 5. **Method `inOrderTraversal()`**

The `inOrderTraversal()` method visits every node in the tree in order. Since every node is visited once, the time
complexity is:

\[
O(n)
\]

where `n` is the number of nodes in the tree.

#### 6. **Method `preOrderTraversal()`**

Similar to `inOrderTraversal()`, the `preOrderTraversal()` method visits every node in the tree exactly once. Therefore,
the time complexity is:

\[
O(n)
\]

where `n` is the number of nodes in the tree.

#### 7. **Method `postOrderTraversal()`**

The `postOrderTraversal()` method also visits every node in the tree exactly once. Thus, the time complexity is:

\[
O(n)
\]

where `n` is the number of nodes in the tree.

#### 8. **Method `levelOrderTraversal()`**

The `levelOrderTraversal()` method performs a breadth-first traversal (BFS) of the tree, visiting all nodes. Each node
is enqueued and dequeued exactly once, so the time complexity is:

\[
O(n)
\]

where `n` is the number of nodes in the tree.

#### 9. **Method `nodesByLevel()`**

The `nodesByLevel()` method performs a level-order traversal and groups nodes by their level. Since each node is visited
exactly once, the time complexity is:

\[
O(n)
\]

where `n` is the number of nodes in the tree.

### **Overall Complexity**

The overall complexity of the `BinarySearchTree` class is primarily influenced by the `insert()`, `find()`, and
`remove()` methods, which have logarithmic time complexity for balanced trees. However, the tree traversal methods (
`inOrderTraversal()`, `preOrderTraversal()`, `postOrderTraversal()`, and `levelOrderTraversal()`) each have linear time
complexity.

Thus, the overall complexity for operations like insertion, removal, and search is \( O(\log n) \) for balanced trees,
while tree traversals have a complexity of \( O(n) \).

Therefore, the overall complexity of the `BinarySearchTree` class is:

\[
O(log n)
\]

for insertion, search, and removal operations, and \( O(n) \) for traversal operations, where `n` is the number of nodes
in the tree.

The provided code defines the `ObjectBST` class, which represents an object in a Binary Search Tree (BST) with
associated items and their quantities. Here's a breakdown of its time complexity for each method:

### **ObjectBST Complexity Analysis**

| Method                                                               | Complexity |
|----------------------------------------------------------------------|------------|
| ObjectBST(Map<ID, Item> itemsWithQuantity, float quantityOfElements) | O(1)       |
| getQuantityOfElements()                                              | O(1)       |
| setQuantityOfElements(float quantityOfElements)                      | O(1)       |
| getItemsWithQuantity()                                               | O(1)       |
| setItemsWithQuantity(Map<ID, Item> itemsWithQuantity)                | O(1)       |
| addItem(Item item)                                                   | O(1)       |
| compareTo(ObjectBST o)                                               | O(1)       |
| equals(Object o)                                                     | O(1)       |
| hashCode()                                                           | O(1)       |
| toString()                                                           | O(n)       |

#### 1. **Constructor (`ObjectBST(Map<ID, Item> itemsWithQuantity, float quantityOfElements)`)**

The constructor initializes the `ObjectBST` instance with the provided map and quantity of elements. Both assignments
are done in constant time:

\[
O(1)
\]

#### 2. **Method `getQuantityOfElements()`**

This method simply returns the `quantityOfElements` field. Accessing the field directly takes constant time:

\[
O(1)
\]

#### 3. **Method `setQuantityOfElements(float quantityOfElements)`**

This method sets the `quantityOfElements` field to the provided value. Assigning a value to a field takes constant time:

\[
O(1)
\]

#### 4. **Method `getItemsWithQuantity()`**

This method returns the `itemsWithQuantity` map. Accessing the map directly takes constant time:

\[
O(1)
\]

#### 5. **Method `setItemsWithQuantity(Map<ID, Item> itemsWithQuantity)`**

This method sets the `itemsWithQuantity` map to the provided value. Assigning a value to a field takes constant time:

\[
O(1)
\]

#### 6. **Method `addItem(Item item)`**

This method adds an item to the `itemsWithQuantity` map. The `put` operation for a map is generally constant time on
average:

\[
O(1)
\]

#### 7. **Method `compareTo(ObjectBST o)`**

This method compares the `quantityOfElements` field with that of another `ObjectBST`. The comparison involves a single
arithmetic operation, which takes constant time:

\[
O(1)
\]

#### 8. **Method `equals(Object o)`**

This method checks if two `ObjectBST` objects are equal by comparing the `quantityOfElements` field. This comparison is
based on a simple float comparison, which also takes constant time:

\[
O(1)
\]

#### 9. **Method `hashCode()`**

This method calculates the hash code based on the `quantityOfElements` field. The `Objects.hashCode()` method runs in
constant time:

\[
O(1)
\]

#### 10. **Method `toString()`**

This method constructs a string representation of the `ObjectBST` object. It iterates over the `itemsWithQuantity` map,
which contains `n` items, and appends each item's ID to a `StringBuilder`. Therefore, the time complexity is
proportional to the number of items:

\[
O(n)
\]

where `n` is the number of items in the `itemsWithQuantity` map.

### **Overall Complexity**

The overall complexity of the `ObjectBST` class is primarily driven by the `toString()` method, which has a linear time
complexity \( O(n) \), where `n` is the number of items in the `itemsWithQuantity` map. All other methods have constant
time complexity \( O(1) \). Therefore, the complexity of the `ObjectBST` class is:

\[
O(n)
\]

where `n` is the number of items in the map for methods like `toString()`, and \( O(1) \) for the other methods.


Certainly! Here is the complexity analysis for the `BSTController` class in the same format as the `ObjectBST` analysis you provided:

### **BSTController Complexity Analysis**

| Method                                                           | Complexity   |
|------------------------------------------------------------------|--------------|
| **Constructor (`BSTController()`)**                               | O(n log n)   |
| `fillTreeByQuantity()`                                            | O(n log n)   |
| `insertBalanced(List<Item>, int, int)`                            | O(n)         |
| `getItemRepository()`                                             | O(1)         |
| `generateTreeIncrescingOrder()`                                   | O(n)         |
| `generateTreeDecrescingOrder()`                                   | O(n)         |
| `getBinarySearchTree()`                                           | O(1)         |

#### 1. **Constructor (`BSTController()`)**

The constructor initializes the item repository and binary search tree (BST). It then calls `fillTreeByQuantity()` to populate the BST. This method involves sorting the items and inserting them into the tree in a balanced manner.

- **Sorting** takes \( O(n \log n) \), where \( n \) is the number of items.
- **Balanced insertion** of items takes \( O(n) \).

Thus, the overall complexity for the constructor is:

\[
O(n \log n)
\]

#### 2. **Method `fillTreeByQuantity()`**

This method sorts the items by quantity and then inserts them into the BST in a balanced manner.

- **Sorting** the list by quantity takes \( O(n \log n) \).
- **Balanced insertion** of items into the BST takes \( O(n) \), as each item is inserted in a balanced manner.

Thus, the overall complexity of this method is:

\[
O(n \log n)
\]

#### 3. **Method `insertBalanced(List<Item>, int, int)`**

This method recursively inserts items into the BST in a balanced manner.

- **Recursion**: The method recursively splits the list in half for balanced insertion, which takes \( O(n) \) recursive calls.
- **Insertion** into the tree takes \( O(\log n) \) per item because the tree remains balanced.

Thus, the total complexity of this method is:

\[
O(n)
\]

#### 4. **Method `getItemRepository()`**

This method retrieves the item repository. It performs a simple check and assignment, making it a constant-time operation:

\[
O(1)
\]

#### 5. **Method `generateTreeIncrescingOrder()`**

This method generates a representation of the BST in increasing order using in-order traversal.

- **In-order traversal** visits each node once, resulting in a time complexity of \( O(n) \), where \( n \) is the number of nodes in the BST.

Thus, the complexity of this method is:

\[
O(n)
\]

#### 6. **Method `generateTreeDecrescingOrder()`**

This method generates a representation of the BST in decreasing order using reverse in-order traversal.

- **Reverse in-order traversal** also visits each node once, so the complexity is \( O(n) \).

Thus, the complexity of this method is:

\[
O(n)
\]

#### 7. **Method `getBinarySearchTree()`**

This method simply returns the `binarySearchTree` instance. It is a constant-time operation:

\[
O(1)
\]

### **Overall Complexity**

- The overall complexity of the `BSTController` class is dominated by the methods that sort the items and insert them into the tree. These operations have a time complexity of \( O(n \log n) \).
- Methods like `generateTreeIncrescingOrder()` and `generateTreeDecrescingOrder()` have a complexity of \( O(n) \), and the remaining methods have constant-time complexities \( O(1) \).

Thus, the overall complexity of the `BSTController` class is:

\[
O(n \log n)
\]

Where \( n \) is the number of items in the repository.


### **Overall Complexity Analysis**

Considering the complexities of the methods in the `BinarySearchTree`, `ObjectBST`, and `BSTController` classes, the overall time complexity can be analyzed as follows:

1. **`BinarySearchTree`**:
    - The insertion, search, and removal operations in `BinarySearchTree` each have a complexity of \( O(\log n) \) for balanced trees.
    - The traversal operations (in-order, pre-order, post-order, level-order) each have a complexity of \( O(n) \), where \( n \) is the number of nodes in the tree.
    - **Overall Complexity**: For operations like insertion, search, and removal, the time complexity is \( O(\log n) \) for balanced trees, while tree traversals have a complexity of \( O(n) \). Thus, the overall complexity is dominated by the traversal operations, making it:

   \[
   O(n)
   \]

2. **`ObjectBST`**:
    - The majority of methods in `ObjectBST`, such as `getQuantityOfElements()`, `setQuantityOfElements()`, `getItemsWithQuantity()`, etc., have constant time complexity \( O(1) \).
    - The `toString()` method has a linear complexity \( O(n) \), where \( n \) is the number of items in the `itemsWithQuantity` map.
    - **Overall Complexity**: The complexity is dominated by the `toString()` method, which has \( O(n) \) complexity. Therefore, the overall complexity is:

   \[
   O(n)
   \]

3. **`BSTController`**:
    - The `fillTreeByQuantity()` and constructor (`BSTController()`) both involve sorting the items and inserting them in a balanced manner, which has a complexity of \( O(n \log n) \).
    - The method `insertBalanced()` has \( O(n) \) complexity, as it inserts the items into the tree in a balanced manner.
    - Methods like `generateTreeIncrescingOrder()` and `generateTreeDecrescingOrder()` involve in-order and reverse in-order traversal, each having a complexity of \( O(n) \).
    - **Overall Complexity**: The overall complexity is dominated by the sorting and insertion operations in `fillTreeByQuantity()` and the constructor, which have a time complexity of \( O(n \log n) \). Thus, the overall complexity is:

   \[
   O(n \log n)
   \]

### **Combined Overall Complexity**

When combining the complexities of all the classes (`BinarySearchTree`, `ObjectBST`, and `BSTController`), the overall complexity is determined by the most complex operations across the classes:

- The **`BSTController`** class has the highest complexity of \( O(n \log n) \) due to sorting and insertion operations.
- The **`BinarySearchTree`** and **`ObjectBST`** classes have complexities of \( O(n) \), primarily due to traversal and string conversion operations.

Therefore, the overall complexity of the entire system is:

\[
O(n \log n)
\]

This is because \( O(n \log n) \) dominates \( O(n) \), and thus the overall time complexity for operations like insertion and tree construction in the `BSTController` class will be \( O(n \log n) \).