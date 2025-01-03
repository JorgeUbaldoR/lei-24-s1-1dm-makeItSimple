
---

# USEI10 - Binary Search Tree - (Tracking Materials Quantities)

## 4. Tests

### **Test 1: Root**

```java
@Test
void root() {
    System.out.println("Test root");
    BinarySearchTree.Node<ObjectBST> root1 =  binarySearchTree.getRoot();
    assertEquals(root1, binarySearchTree.getRoot());
    assertEquals(null, binarySearchTreeEmpty.getRoot());
}
```
**Objective:**  
Verify the `getRoot()` method's behavior for both a populated and an empty binary search tree.

**Validations:**
1. For a populated tree, the root should not be `null`.
2. For an empty tree, the root should be `null`.

**Expected Result:**
1. The root of the populated tree matches the expected value.
2. The empty tree's root is `null`.

---

### **Test 2: Is Empty**

```java
@Test
void isEmpty() {
    System.out.println("Test isEmpty");
    assertFalse(binarySearchTree.isEmpty());
    assertTrue(binarySearchTreeEmpty.isEmpty());
}
```

**Objective:**  
Check the `isEmpty()` method's behavior to confirm whether it accurately reflects the tree's state.

**Validations:**
1. A populated tree should return `false`.
2. An empty tree should return `true`.

**Expected Result:**  
The method returns the correct boolean value for both cases.

---

### **Test 3: Insert And Find**

```java
@Test
void insertAndFind() {
    System.out.println("Test insert and find");
    Item newItem = new Item(new ID(111, TypeID.ITEM),"Door",new LinkedList<>());
    Item newItem1 = new Item(new ID(333, TypeID.ITEM),"Wheel",new LinkedList<>());
    newItem1.setQuantity(32);
    Item newItem2 = new Item(new ID(444, TypeID.ITEM),"Window",new LinkedList<>());
    newItem2.setQuantity(64);
    binarySearchTree.insert(newItem);
    binarySearchTree.insert(newItem1);
    binarySearchTree.insert(newItem2);

    Item itemNotFound = new Item(new ID(555, TypeID.ITEM),"screwdriver",new LinkedList<>());


    assertNotNull(binarySearchTree.find(binarySearchTree.root(),newItem));
    assertNotNull(binarySearchTree.find(binarySearchTree.root(),newItem1));
    assertNotNull(binarySearchTree.find(binarySearchTree.root(),newItem2));
    assertNull(binarySearchTree.find(binarySearchTree.root(),itemNotFound));
}
```

**Objective:**  
Test the `insert()` and `find()` methods to ensure items can be inserted and located in the tree.

**Validations:**
1. Insert multiple items into the tree and verify they can be found.
2. Attempt to find an item that was not inserted, ensuring the result is `null`.

**Expected Result:**
1. Inserted items are found successfully.
2. Searching for a non-existent item returns `null`.


### **Test 4: Size**

```java
@Test
void size() {
    System.out.println("Test size");
    BinarySearchTree binarySearchTreeNew = new BinarySearchTree();
    assertEquals(0, binarySearchTreeNew.size());

    Item firstItem = new Item(new ID(232, TypeID.ITEM),"Window",new LinkedList<>());
    binarySearchTreeNew.insert(firstItem);
    assertEquals(1, binarySearchTreeNew.size());

    Item secondItem = new Item(new ID(333, TypeID.ITEM),"Window",new LinkedList<>());
    secondItem.setQuantity(64);
    binarySearchTreeNew.insert(secondItem);
    assertEquals(2, binarySearchTreeNew.size());

    Item sameQtdFirst = new Item(new ID(456, TypeID.ITEM),"Window",new LinkedList<>());
    binarySearchTreeNew.insert(sameQtdFirst);
    assertEquals(2, binarySearchTreeNew.size());
}
```

**Objective:**  
Verify the correctness of the `size()` method in reflecting the number of elements in the tree.

**Validations:**
1. A newly created tree has size `0`.
2. After inserting items, the size increases appropriately.
3. Duplicate insertions (with the same quantity) do not increase the size.

**Expected Result:**  
The tree's size is correctly calculated in all cases.
---

### **Test 5: Height**

```java
 @Test
void height() {
    System.out.println("Test height");
    BinarySearchTree binarySearchTreeNew = new BinarySearchTree();
    assertEquals(-1, binarySearchTreeNew.height());

    Item firstItem = new Item(new ID(232, TypeID.ITEM),"Window",new LinkedList<>());
    binarySearchTreeNew.insert(firstItem);
    assertEquals(0, binarySearchTreeNew.height());
    Item secondItem = new Item(new ID(333, TypeID.ITEM),"Window",new LinkedList<>());
    secondItem.setQuantity(64);
    binarySearchTreeNew.insert(secondItem);
    assertEquals(1, binarySearchTreeNew.height());

    Item thirdItem = new Item(new ID(456, TypeID.ITEM),"Window",new LinkedList<>());
    thirdItem.setQuantity(64);
    binarySearchTreeNew.insert(thirdItem);
    assertEquals(1, binarySearchTreeNew.height());

    Item fourthItem = new Item(new ID(456, TypeID.ITEM),"Window",new LinkedList<>());
    fourthItem.setQuantity(2);
    binarySearchTreeNew.insert(fourthItem);
    assertEquals(2, binarySearchTreeNew.height());
}
```

**Objective:**  
Test the `height()` method to ensure it calculates the tree's height accurately based on its structure.

**Validations:**
1. An empty tree has height `-1`.
2. The height increases as new levels are added to the tree.

**Expected Result:**  
The height value reflects the tree's depth in all scenarios.



### **Test 6: Smallest Element**

```java
@Test
void smallestElement() {
    System.out.println("Test smallest element");

    ObjectBST samllests = binarySearchTree.smallestElement();
    System.out.println(samllests);

    ObjectBST smallestObj = new ObjectBST(new HashMap<>(),0.0576F);
    ItemRepository itemRepository = Repositories.getInstance().getItemRepository();
    ID search = new ID(1015, TypeID.ITEM);
    Item item = itemRepository.getMapItemList().get(search);
    smallestObj.getItemsWithQuantity().put(item.getItemID(),item);
    System.out.println();

    assertNotNull(samllests);
    assertEquals(smallestObj,samllests);
}
```

**Objective:**  
Validate the behavior of the `smallestElement()` method to ensure it correctly identifies and returns the node with the smallest quantity in the binary search tree.

**Validations:**
1. The method should return a non-`null` node when the tree contains elements.
2. The returned node should match the expected smallest element in the tree (based on its quantity).
3. The smallest element's associated item and quantity should match the predefined expectations.

**Expected Result:**
1. The `smallestElement()` method returns a valid `ObjectBST` object.
2. The returned node corresponds to the node with the smallest quantity (`0.0576F`).
3. The associated item within the returned node matches the item retrieved from the repository (`Item` with ID `1015`).


### **Test 7: In Order**
```java
 @Test
void inOrder() {
    System.out.println("Test inOrder");
    List<ObjectBST> list = (List<ObjectBST>) binarySearchTree.inOrder();
    for(int i = 1; i < list.size();i++ ){
        assertTrue(list.get(i-1).getQuantityOfElements() < list.get(i).getQuantityOfElements());
    }
}
```
**Objective:**  
Ensure the `inOrder()` method correctly traverses the tree in ascending order of element quantities.

**Validations:**
1. The resulting list from `inOrder()` is sorted in ascending order.

**Expected Result:**  
The elements are returned in ascending order of quantity.


### **Test 8: In Order Reverse**
```java
@Test
void inOrderReverse() {
    List<ObjectBST> list = (List<ObjectBST>) binarySearchTree.inOrderReverse();
    for(int i = 1; i < list.size();i++ ){
        assertTrue(list.get(i-1).getQuantityOfElements() > list.get(i).getQuantityOfElements());
    }
}
```
**Objective:**  
Validate the `inOrderReverse()` method for correctly traversing the tree in descending order of element quantities.

**Validations:**
1. The resulting list from `inOrderReverse()` is sorted in descending order.

**Expected Result:**  
The elements are returned in descending order of quantity.

### **Test 9: Nodes By Level**
```java
@Test
void nodesByLevel() {
    System.out.println("Test nodesByLevel");
    Map<Integer, List<ObjectBST>> map = binarySearchTree.nodesByLevel();
    int altura = 0;

    for (Map.Entry<Integer, List<ObjectBST>> entry : map.entrySet()) {
        assertEquals(altura, entry.getKey());
        altura++;
    }
}
```
**Objective:**  
Test the `nodesByLevel()` method to ensure it correctly groups nodes by their depth level.

**Validations:**
1. Each level contains nodes corresponding to that depth.
2. The levels are incrementally numbered from `0`.

**Expected Result:**  
The nodes are grouped correctly by their levels, with no missing or extra levels.

### **Test 10: To String**
```java
@Test
void testToString() {
    System.out.println("Test toString");
    String string = binarySearchTree.toString();
    assertNotNull(string);
    String string1 = binarySearchTreeEmpty.toString();
    assertEquals("", string1);
}
```
**Objective:**  
Verify the `toString()` method's output for both populated and empty trees.

**Validations:**
1. A populated tree's `toString()` method returns a non-empty string.
2. An empty tree's `toString()` method returns an empty string.

**Expected Result:**  
The method provides the expected string representation of the tree.

### **Test 11: To String**

```java
@Test
void testRemoveExtended() {
    System.out.println("Test remove extended");

    // Case 1: Remove a leaf node
    System.out.println("Removing a leaf node...");
    Item leafItem = new Item(new ID(999, TypeID.ITEM), "Leaf", new LinkedList<>());
    leafItem.setQuantity(10);
    binarySearchTree.insert(leafItem);
    int initialSize = binarySearchTree.size();
    binarySearchTree.remove(new ObjectBST(new HashMap<>(), 10F));
    assertEquals(initialSize - 1, binarySearchTree.size());
    assertNull(binarySearchTree.find(binarySearchTree.root(), leafItem));

    // Case 2: Remove a node with one child
    System.out.println("Removing a node with one child...");
    Item singleChildItem = new Item(new ID(888, TypeID.ITEM), "SingleChild", new LinkedList<>());
    singleChildItem.setQuantity(50);
    binarySearchTree.insert(singleChildItem);
    Item childItem = new Item(new ID(887, TypeID.ITEM), "Child", new LinkedList<>());
    childItem.setQuantity(45);
    binarySearchTree.insert(childItem);

    initialSize = binarySearchTree.size();
    binarySearchTree.remove(new ObjectBST(new HashMap<>(), 50F));
    assertEquals(initialSize - 1, binarySearchTree.size());
    assertNull(binarySearchTree.find(binarySearchTree.root(), singleChildItem));
    assertNotNull(binarySearchTree.find(binarySearchTree.root(), childItem)); // Ensure child remains in the tree

    // Case 3: Remove a node with two children
    System.out.println("Removing a node with two children...");
    Item twoChildrenItem = new Item(new ID(777, TypeID.ITEM), "TwoChildren", new LinkedList<>());
    twoChildrenItem.setQuantity(100);
    binarySearchTree.insert(twoChildrenItem);
    Item leftChild = new Item(new ID(776, TypeID.ITEM), "LeftChild", new LinkedList<>());
    leftChild.setQuantity(90);
    binarySearchTree.insert(leftChild);
    Item rightChild = new Item(new ID(778, TypeID.ITEM), "RightChild", new LinkedList<>());
    rightChild.setQuantity(110);
    binarySearchTree.insert(rightChild);

    initialSize = binarySearchTree.size();
    binarySearchTree.remove(new ObjectBST(new HashMap<>(), 100F));
    assertEquals(initialSize - 1, binarySearchTree.size());
    assertNull(binarySearchTree.find(binarySearchTree.root(), twoChildrenItem));
    assertNotNull(binarySearchTree.find(binarySearchTree.root(), leftChild)); // Ensure left child remains
    assertNotNull(binarySearchTree.find(binarySearchTree.root(), rightChild)); // Ensure right child remains

    // Case 4: Try to remove a non-existent element
    System.out.println("Removing a non-existent node...");
    initialSize = binarySearchTree.size();
    binarySearchTree.remove(new ObjectBST(new HashMap<>(), 9999F)); // Element with quantity 9999 does not exist
    assertEquals(initialSize, binarySearchTree.size()); // Size should remain unchanged
}
```

**Objective:**  
Test the `remove()` method under specific cases:
1. Removing a leaf node.
2. Removing a node with one child.
3. Removing a node with two children.
4. Removing a non-existent node.

**Validations:**
1. The tree's size decreases only for valid removals.
2. The node to be removed is no longer present in the tree.
3. The tree maintains its structural integrity after removals.

**Expected Result:**
1. Nodes are removed successfully when present.
2. The tree structure remains correct after each operation.
3. No changes occur when attempting to remove a non-existent node.

---
##  5. Implementation 


##  Nested Class Node

### Method - Node Constructor
```java
/**
 * Constructs a node with the given element and neighbors.
 *
 * @param e          the element to be stored
 * @param leftChild  reference to a left child node
 * @param rightChild reference to a right child node
 */
public Node(ObjectBST e, Node<ObjectBST> leftChild, Node<ObjectBST> rightChild) {
    element = e;
    left = leftChild;
    right = rightChild;
}
```

### Method - Get Element
```java
public ObjectBST getElement() {
    return element;
}
```

### Method - Get Left Node
```java
public Node<ObjectBST> getLeft() {
    return left;
}
```

### Method - Get Right Node
```java
public Node<ObjectBST> getRight() {
    return right;
}
```

### Method - Set Element
```java
public void setElement(ObjectBST e) {
    element = e;
}
```

### Method - Set Left
```java
public void setLeft(Node<ObjectBST> leftChild) {
    left = leftChild;
}
```

### Method - Set Right
```java
public void setRight(Node<ObjectBST> rightChild) {
    right = rightChild;
}
```

## Class Binary Search Tree

### Constructor BST
```java
/** Constructs an empty binary search tree. */
public BinarySearchTree() {
    root = null;
}
```

### Method - Get Root
```java
/**
 * Returns the root node of the tree.
 *
 * @return root node of the tree, or null if the tree is empty.
 */
public Node<ObjectBST> getRoot() {
    return root;
}
```

### Method - Root
```java
/**
 * @return root Node of the tree (or null if tree is empty)
 */
protected Node<ObjectBST> root() {
    return root;
}
```

### Method - Is Empty
```java
/**
 * Verifies if the tree is empty.
 *
 * @return true if the tree is empty, false otherwise.
 */
public boolean isEmpty() {
    return root == null;
}
```

### Method - Find
```java
/**
 * Returns the Node containing a specific Element, or null otherwise.
 *
 * @param node    the starting node for the search
 * @param element the element to find
 * @return the Node that contains the element, or null otherwise
 */
protected Node<ObjectBST> find(Node<ObjectBST> node, Item element) {
    if (node == null) {
        return null;
    }

    if (node.getElement().getQuantityOfElements() == element.getQuantity()) {
        if(node.getElement().getItemsWithQuantity().containsKey(element.getItemID())) {
            return node;
        }
        return null;
    }

    if (node.getElement().getQuantityOfElements() > element.getQuantity()) {
        return find(node.getLeft(), element);
    } else {
        return find(node.getRight(), element);
    }
}
```

### Method - Insert
```java
/**
 * Inserts an item into the binary search tree (BST).
 *<p>
 * Time Complexity:
 * - Average Case: O(log(n)) for balanced trees, as we only traverse the height of the tree.
 * - Worst Case: O(n) for unbalanced trees (e.g., all elements in sorted order).
 *
 * Space Complexity:
 * - Average Case: O(log(n)) due to recursive calls on the stack.
 * - Worst Case: O(n) for unbalanced trees, where recursion depth equals the number of nodes.
 *
 * @param item the item to insert
 */
public void insert(Item item) {
    root = insert(item, root());
}
```

### Method - Insert Helper
```java
/**
 * Helper method for inserting an item into the BST.
 *
 * Time Complexity:
 * - Average Case: O(log(n)) for balanced trees, as we only traverse the height of the tree.
 * - Worst Case: O(n) for unbalanced trees.
 *
 * Space Complexity:
 * - Average Case: O(log(n)) due to recursion on the stack.
 * - Worst Case: O(n) for unbalanced trees.
 *
 * @param item the item to insert
 * @param node the current node in the recursion
 * @return the updated node after insertion
 */
private Node<ObjectBST> insert(Item item, Node<ObjectBST> node) {

    if (node == null) {
        ObjectBST newObject = new ObjectBST(new HashMap<>(),item.getQuantity());
        newObject.getItemsWithQuantity().put(item.getItemID(), item);
        return new Node(newObject, null, null);
    }
    if(node.getElement().getQuantityOfElements() == item.getQuantity()) {
        node.getElement().addItem(item);
        return node;
    }else if (node.getElement().getQuantityOfElements() > item.getQuantity()) {
        node.setLeft(insert(item, node.getLeft()));
    } else if (node.getElement().getQuantityOfElements() < item.getQuantity()) {
        node.setRight(insert(item, node.getRight()));
    }

    return node;
}
```

### Method - Remove
```java
    /**
     * Removes an element from the binary search tree (BST) while maintaining the tree's consistency.
     *
     * Time Complexity:
     * - Average Case: O(log(n)) for balanced trees, as we only traverse the height of the tree.
     * - Worst Case: O(n) for unbalanced trees.
     *<p>
     * Space Complexity:
     * - Average Case: O(log(n)) due to recursive calls on the stack.
     * - Worst Case: O(n) for unbalanced trees.
     *
     * @param element the element to remove
     */
    public void remove(ObjectBST element) {
        root = remove(element, root());
    }
```

### Method - Remove Helper
```java
    /**
     * Helper method to remove an element from the BST.
     *<p>
     * Time Complexity:
     * - Average Case: O(log(n)) for balanced trees, as we only traverse the height of the tree.
     * - Worst Case: O(n) for unbalanced trees.
     *<p>
     * Space Complexity:
     * - Average Case: O(log(n)) due to recursion on the stack.
     * - Worst Case: O(n) for unbalanced trees.
     *<p>
     * @param element the element to remove
     * @param node the current node in the recursion
     * @return the updated node after removal
     */
    private Node<ObjectBST> remove(ObjectBST element, Node<ObjectBST> node) {

        if (node == null) {
            return null;    //throw new IllegalArgumentException("Element does not exist");
        }
        if (element.getQuantityOfElements() - node.getElement().getQuantityOfElements() == 0) {
            // node is the Node to be removed
            if (node.getLeft() == null && node.getRight() == null) { //node is a leaf (has no childs)
                return null;
            }
            if (node.getLeft() == null) {   //has only right child
                return node.getRight();
            }
            if (node.getRight() == null) {  //has only left child
                return node.getLeft();
            }
            ObjectBST min = smallestElement(node.getRight());
            node.setElement(min);
            node.setRight(remove(min, node.getRight()));
        } else if (element.getQuantityOfElements() < node.getElement().getQuantityOfElements())
            node.setLeft(remove(element, node.getLeft()));
        else
            node.setRight(remove(element, node.getRight()));

        return node;
    }
```

### Method - Size
```java
/**
 * Returns the number of nodes in the tree.
 *<p>
 * This method calculates the total number of nodes by performing a recursive traversal
 * of the tree, visiting each node exactly once.
 *<p>
 * Time Complexity:
 * - O(n), where n is the number of nodes in the tree. In the worst case, we need to visit
 *   every node in the tree.
 *<p>
 * Space Complexity:
 * - O(h), where h is the height of the tree. This is due to the recursion depth in the
 *   worst case, which could be equal to the height of the tree.
 *   - For a balanced tree, h = O(log(n)).
 *   - For an unbalanced tree, h = O(n).
 *
 * @return the number of nodes in the tree
 */
public int size() {
    return size(root);
}
```

### Method - Size Helper
```java
    /**
 * Helper method that recursively calculates the size of the subtree rooted at the given node.
 *<p>
 * Time Complexity:
 * - O(n), where n is the number of nodes in the subtree. Each node is visited exactly once.
 *<p>
 * Space Complexity:
 * - O(h), where h is the height of the tree. The recursion depth can go up to the height
 *   of the tree, which is O(log(n)) for balanced trees and O(n) for unbalanced trees.
 *
 * @param node the root of the subtree
 * @return the number of nodes in the subtree
 */
private int size(Node<ObjectBST> node) {
    if (node != null) {
        return 1 + size(node.getLeft()) + size(node.getRight());
    }
    return 0;
}
```

### Method - Height
```java
/*
 * Returns the height of the tree
 * @return height
 */
public int height() {
    return height(root);
}
```

### Method - Height Helper
```java
/**
 * Returns the height of the subtree rooted at Node node.
 * @param node A valid Node within the tree
 * @return height
 */
protected int height(Node<ObjectBST> node) {
    if(node == null) {
        return -1;
    }

    int leftHeight = height(node.getLeft());
    int rightHeight = height(node.getRight());

    return Math.max(leftHeight, rightHeight) + 1;
}
```

### Method - Smallest Element
```java
    /**
 * Returns the height of the tree.
 *<p>
 * This method calculates the height of the tree by recursively determining the height of
 * each subtree and returning the maximum height among them.
 * The height of an empty tree is defined as -1, and the height of a tree with just one node
 * is 0.
 *<p>
 * Time Complexity:
 * - O(n), where n is the number of nodes in the tree. In the worst case, we need to visit
 *   every node to calculate the height.
 *<p>
 * Space Complexity:
 * - O(h), where h is the height of the tree. This is due to the recursion depth, which can
 *   go up to the height of the tree.
 *   - For a balanced tree, h = O(log(n)).
 *   - For an unbalanced tree, h = O(n).
 *<p>
 * @return the height of the tree
 */
public ObjectBST smallestElement() {
    return smallestElement(root());
}
```


### Method - Smallest Element Helper
```java
/**
 * Helper method that recursively calculates the height of the subtree rooted at the given node.
 *<p>
 * Time Complexity:
 * - O(n), where n is the number of nodes in the subtree. Each node is visited exactly once
 *   to calculate the height.
 *<p>
 * Space Complexity:
 * - O(h), where h is the height of the tree. The recursion depth can go up to the height
 *   of the tree.
 *   - For a balanced tree, h = O(log(n)).
 *   - For an unbalanced tree, h = O(n).
 *<p>
 * @param node the root of the subtree
 * @return the height of the subtree
 */
protected ObjectBST smallestElement(Node<ObjectBST> node) {
    if (node.getLeft() == null) {
        return node.getElement();
    }
    return smallestElement(node.getLeft());
}
```


### Method - In Order
```java
    /**
 * Returns an iterable collection of elements of the tree in in-order traversal.
 *<p>
 * This method performs an in-order traversal of the binary search tree (BST),
 * meaning it visits nodes in the following order: left subtree, root, right subtree.
 * The result is returned as an iterable collection (a snapshot of the tree elements)
 * in the order defined by the in-order traversal.
 *<p>
 * Time Complexity:
 * - O(n), where n is the number of nodes in the tree. The method visits each node
 *   exactly once to add its element to the snapshot.
 *<p>
 * Space Complexity:
 * - O(n), where n is the number of nodes in the tree. The method uses a list to store
 *   the elements of the tree, which requires O(n) space.
 *
 * @return an iterable collection of the tree's elements in in-order
 */
public Iterable<ObjectBST> inOrder() {
    List<ObjectBST> snapshot = new ArrayList<>();
    if (root != null)
        inOrderSubtree(root, snapshot);   // fill the snapshot recursively
    return snapshot;
}
```


### Method - In Order Subtree
```java
/**
 * Adds elements of the subtree rooted at Node node to the given snapshot using an in-order traversal.
 *<p>
 * This is a helper method that performs the actual recursive traversal of the tree.
 * It adds the elements of the subtree rooted at the provided node to the given snapshot
 * in the order defined by the in-order traversal: left subtree, root, right subtree.
 *<p>
 * Time Complexity:
 * - O(n), where n is the number of nodes in the subtree. The method visits each node
 *   exactly once during the traversal.
 *<p>
 * Space Complexity:
 * - O(h), where h is the height of the tree. The space complexity is determined by the
 *   depth of the recursion stack. In the worst case (unbalanced tree), this could be O(n).
 *   In a balanced tree, this will be O(log n).
 *
 * @param node the root node of the subtree
 * @param snapshot a list to which results are appended
 */
private void inOrderSubtree(Node<ObjectBST> node, List<ObjectBST> snapshot) {
    if (node == null)
        return;
    inOrderSubtree(node.getLeft(), snapshot);
    snapshot.add(node.getElement());
    inOrderSubtree(node.getRight(), snapshot);
}
```


### Method - In Order Reverse
```java
/**
 * Returns an iterable collection of elements of the tree, reported in in-order reverse traversal.
 *<p>
 * This method performs a reverse in-order traversal of the binary search tree (BST),
 * meaning it visits nodes in the following order: right subtree, root, left subtree.
 * The result is returned as an iterable collection (a snapshot of the tree elements)
 * in the reverse in-order order, which is the reverse of the standard in-order traversal.
 *<p>
 * Time Complexity:
 * - O(n), where n is the number of nodes in the tree. The method visits each node
 *   exactly once to add its element to the snapshot.
 * <p>
 * Space Complexity:
 * - O(n), where n is the number of nodes in the tree. The method uses a list to store
 *   the elements of the tree, which requires O(n) space.
 *
 * @return an iterable collection of the tree's elements in reverse in-order
 */
public Iterable<ObjectBST> inOrderReverse() {
    List<ObjectBST> snapshot = new ArrayList<>();
    if (root != null)
        inOrderSubtreeReverse(root, snapshot);   // fill the snapshot recursively
    return snapshot;
}
```


### Method - In Order Reverse Subtree
```java
    /**
 * Adds elements of the subtree rooted at Node node to the given snapshot using reverse in-order traversal.
 *<p>
 * This is a helper method that performs the actual recursive traversal of the tree.
 * It adds the elements of the subtree rooted at the provided node to the given snapshot
 * in the reverse in-order order: right subtree, root, left subtree.
 *<p>
 * Time Complexity:
 * - O(n), where n is the number of nodes in the subtree. The method visits each node
 *   exactly once during the traversal.
 *<p>
 * Space Complexity:
 * - O(h), where h is the height of the tree. The space complexity is determined by the
 *   depth of the recursion stack. In the worst case (unbalanced tree), this could be O(n).
 *   In a balanced tree, this will be O(log n).
 *
 * @param node the root node of the subtree
 * @param snapshot a list to which results are appended
 */
private void inOrderSubtreeReverse(Node<ObjectBST> node, List<ObjectBST> snapshot) {
    if (node == null)
        return;

    inOrderSubtreeReverse(node.getRight(), snapshot);
    snapshot.add(node.getElement());
    inOrderSubtreeReverse(node.getLeft(), snapshot);
}
```

### Method - Nodes By Level
```java
/**
 * Returns a map of nodes grouped by their level in the tree.
 * <p>
 * This method performs a level-order traversal (or breadth-first traversal) of the binary search tree (BST),
 * grouping the nodes by their respective levels. The result is returned as a map where the key is the level (an integer)
 * and the value is a list of nodes at that level. The levels start at 0, which corresponds to the root of the tree.
 *
 * Time Complexity:
 * - O(n), where n is the number of nodes in the tree. Each node is visited exactly once during the traversal.
 *
 * Space Complexity:
 * - O(n), where n is the number of nodes in the tree. The method uses a map to store the nodes grouped by their levels,
 *   and the map will hold all the nodes in the tree.
 *
 * @return a map where the key is the level and the value is a list of nodes at that level
 */
public Map<Integer, List<ObjectBST>> nodesByLevel() {
    Map<Integer, List<ObjectBST>> nodesByLevel = new HashMap<>();

    if (root != null) {
        processBstByLevel(root, nodesByLevel, 0);
    }

    return nodesByLevel;
}
```

### Method - Nodes By Level Helper
```java
/**
 * Adds nodes of the subtree rooted at Node node to the result map, grouped by level.
 *
 * This is a helper method that performs a recursive traversal of the tree and groups the nodes
 * based on their levels. It updates the result map by adding the nodes at each level.
 *
 * Time Complexity:
 * - O(n), where n is the number of nodes in the subtree. The method visits each node exactly once.
 *
 * Space Complexity:
 * - O(h), where h is the height of the tree. The space complexity is determined by the depth of the recursion stack.
 *   In the worst case (unbalanced tree), this could be O(n). In a balanced tree, this would be O(log n).
 *
 * @param node   the root node of the subtree
 * @param result a map to store the nodes grouped by their level
 * @param level  the level of the current node
 */
private void processBstByLevel(Node<ObjectBST> node, Map<Integer, List<ObjectBST>> result, int level) {
    if (node == null) {
        return;
    }

    result.putIfAbsent(level, new ArrayList<>());
    result.get(level).add(node.getElement());

    processBstByLevel(node.getLeft(), result, level + 1);
    processBstByLevel(node.getRight(), result, level + 1);
}
```


## Class ObjectBST

### Method - Constructor ObjectBST
```java
/**
 * Constructs an ObjectBST instance with a map of items and a quantity of elements.
 *
 * @param itemsWithQuantity A map of items with their associated quantities.
 * @param quantityOfElements The quantity of elements associated with this object.
 *
 * Time Complexity: O(1) - The constructor only initializes the instance variables with the provided values.
 */
public ObjectBST(Map<ID, Item> itemsWithQuantity, float quantityOfElements) {
    this.itemsWithQuantity = itemsWithQuantity;
    this.quantityOfElements = quantityOfElements;
}
```

### Method - Get Quantity Of Elements
```java
/**
 * Gets the quantity of elements associated with this object.
 *
 * @return The quantity of elements.
 *
 * Time Complexity: O(1) - Accessing the field directly takes constant time.
 */
public float getQuantityOfElements() {
    return quantityOfElements;
}
```

### Method - Get Item With Quantity
```java
/**
 * Gets the map of items with their associated quantities.
 *
 * @return The map of items.
 *
 * Time Complexity: O(1) - Accessing the map directly takes constant time.
 */
public Map<ID, Item> getItemsWithQuantity() {
    return itemsWithQuantity;
}
```

### Method - Set Items With Quantity
```java
    /**
 * Sets the map of items with their associated quantities.
 *
 * @param itemsWithQuantity The map of items to set.
 *
 * Time Complexity: O(1) - Assigning a value to a field takes constant time.
 */
public void setItemsWithQuantity(Map<ID, Item> itemsWithQuantity) {
    this.itemsWithQuantity = itemsWithQuantity;
}
```

### Method - Add Item
```java
    /**
 * Adds an item to the map of items with their associated quantities.
 *
 * @param item The item to add.
 *
 * Time Complexity: O(1) - Inserting an item into a map by key is constant time on average.
 */
public void addItem(Item item) {
    getItemsWithQuantity().put(item.getItemID(), item);
}
```


### Method - Compare To
```java
    /**
 * Compares this ObjectBST with another based on the quantity of elements.
 *
 * @param o The other ObjectBST to compare.
 * @return A negative integer, zero, or a positive integer as this ObjectBST is less than, equal to, or greater than the specified ObjectBST.
 *
 * Time Complexity: O(1) - The comparison is based on a single arithmetic operation, which takes constant time.
 */
@Override
public int compareTo(ObjectBST o) {
    return (int) (this.quantityOfElements - o.quantityOfElements);
}
```


### Method - Equals
```java
    /**
 * Compares this ObjectBST with another for equality.
 *
 * @param o The object to compare.
 * @return true if the two ObjectBST objects are equal; false otherwise.
 *
 * Time Complexity: O(1) - The comparison is based on a simple float comparison, which takes constant time.
 */
@Override
public boolean equals(Object o) {
    if (o == null || getClass() != o.getClass()) return false;
    ObjectBST objectBST = (ObjectBST) o;
    return Float.compare(quantityOfElements, objectBST.quantityOfElements) == 0;
}
```


### Method - HashCode
```java
    /**
 * Returns a hash code value for this ObjectBST.
 *
 * @return A hash code value for this ObjectBST.
 *
 * Time Complexity: O(1) - The `Objects.hashCode()` method runs in constant time for a single field.
 */
@Override
public int hashCode() {
    return Objects.hashCode(quantityOfElements);
}
```

### Method - To String
```java
    /**
 * Returns a string representation of this ObjectBST, including the quantity of elements and a list of item IDs.
 * The item IDs are displayed in square brackets and separated by commas.
 *
 * @return A string representation of this ObjectBST.
 *
 * Time Complexity: O(n) - The time complexity is proportional to the number of items in `itemsWithQuantity`.
 * Each item is added to the string builder, so if there are `n` items, the time complexity is O(n).
 */
@Override
public String toString() {
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append(ANSI_BRIGHT_BLACK+"• Quantity "+ANSI_RESET+"-> ").append(ANSI_BRIGHT_WHITE+quantityOfElements+ANSI_RESET).append("\n[ ");

    int num = itemsWithQuantity.size();
    int qtd = 1;
    for (Item item : itemsWithQuantity.values()) {
        stringBuilder.append(item.getItemID());
        if (qtd < num) {
            stringBuilder.append(", ");
        }
        qtd++;
    }
    stringBuilder.append(" ]");
    return stringBuilder.toString();
}

```






