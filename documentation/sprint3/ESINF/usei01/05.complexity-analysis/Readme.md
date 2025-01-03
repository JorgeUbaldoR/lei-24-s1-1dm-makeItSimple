# USEI01 - File data importation

### **Class FileDateReader**

| Method               | Complexity |
|----------------------|------------|
| getMachinesDetails() | O(N)       |
| getItemsDetails()    | O(N)       |

**`getMachinesDetails`** : this method has O(N) complexity, where N is the number of lines in the input file. The complexity analysis follows the code flow and examines operations in relation to the number of lines:

1. **Reading File Lines**: The loop while (scanner.hasNextLine()) iterates over all lines in the file until there are no more lines to read. Since this loop performs one iteration per line, it iterates N times in total.

2. **Operations on Each Line**:
 - **Line Splitting**: Inside the loop, line.split(";") is called for each line to split the machine details. Splitting a line with split(";") takes time proportional to the length of the line, which is effectively constant concerning the number of lines in the file. Therefore, split(";") has a time complexity of O(1) per line.
 - **Length Check and Addition**: The condition if (parts.length == NUMBER_OF_DETAILS) checks if the line has the correct number of details before adding it to the machineDetails list. Both the check and the addition operation take constant time, O(1), for each line.
3. **Scanner Closing and Return**: After iterating through all lines, scanner.close() closes the file, and the method returns the list of machine details. Closing the scanner and returning the list are constant-time operations, O(1).

> Since the while loop runs N iterations, and each operation inside the loop is O(1), the entire method has an O(N) complexity. The execution time scales linearly with the number of lines in the file.
> 

**`getItemsDetails`** : The loop while (scanner.hasNextLine()) iterates over each line in the file, executing once for each line. Thus, if there are N lines, the loop will run N times.

1. **Reading File Lines**: The loop while (scanner.hasNextLine()) iterates over all lines in the file until there are no more lines to read. Since this loop performs one iteration per line, it iterates N times in total.

2. **Operations on Each Line**:
- **Line Splitting**: Inside the loop, each line is split using line.split(";"). This operation is done on each line, taking time proportional to the number of parts in a single line, which is generally considered constant relative to N. Therefore, this split operation has O(1) complexity per line.
- **Length Check and Addition**: The condition if (parts.length >= NUMBER_OF_DETAILS) checks if the line contains at least the required number of details. If true, it adds the parsed line to the itemsDetails list. Both the check and the addition are O(1) operations per line.
3. **Scanner Closing and Return**: After the loop, scanner.close() is called to close the file, and the method returns the list of item details. Both the closing and returning operations are O(1).

> Since each line is processed once, and each operation within the loop is constant-time, the total complexity of getItemsDetails is O(N), where N is the number of lines in the file. The time required grows linearly with the number of lines, making this an efficient file-reading method for line-by-line processing.
>

### **Class ItemRepository**

| Method             | Complexity |
|--------------------|------------|
| fillInventory()    | O(N^2)     |
| getItemList()      | O(N)       |

**`fillInventory`** : this method has a time complexity of  O(N x M) , or simply  O(N^2)  when both  N  and  M  scale similarly with input size. Here’s how this complexity is derived:

1. **Outer Loop Over Items**: The method starts by iterating over each element in importedItems, which has  N  items in total. This gives an  O(N)  complexity for the outer loop.

2. **Inner Loop Over Operations**: Inside the outer loop, there is another loop iterating over each element in the importedItem array starting from index 2. Since each item has an average of  M  operations, this inner loop has  O(M)  complexity per item.

3. **Combined Complexity**: 
 - With  N  items each having  M  operations, the total complexity of the fillInventory method is  O(N x M) 
 - If  M  grows in proportion to  N , we can generalize this to  O(N^2)

> Thus, in Big-O notation, we express the complexity as  O(N^2) , which accounts for both the number of items and their operations as input size grows.
> 

**`getItemList`** : this method has a time complexity of  O(N) , where  N  represents the number of items in itemList. Here’s a breakdown:

1. **Iteration Over itemList Values**: The method iterates over each Item in itemList.values(). Since itemList has  N  items, this loop has  O(N)  complexity.

2. **Cloning Each Item**: Inside the loop, each Item object is cloned with item.clone() and added to the items list. Cloning is assumed to be  O(1)  per item, so this operation also takes  O(N)  overall

3. **ArrayList Construction**: Constructing a new ArrayList to hold  N  items is also  O(N) .

> Summing these operations, the overall complexity of the method is  O(N) , as each step scales linearly with the number of items in itemList.


### **Class MachineRepository**

| Method          | Complexity |
|-----------------|------------|
| fillMachinery() | O(N)       |


**`fillMachinery`** : this method has a time complexity of  O(N) , where  N  represents the number of records (rows) in the list importedItems. Here is an analysis of its complexity:

1. **Reading Machine Details**: The method begins by calling FileDataReader.getMachinesDetails(), which, based on earlier analysis, is assumed to be  O(N)  because it reads each line in the file. This operation will be  O(N)  based on the number of lines in the file.

2. **Looping Over importedItems**: 
- The for loop iterates over each element in importedItems. For each element, the operations inside the loop have constant time complexity  O(1) , because:
  * reformatMachineId(importedItem[0]) is O(1) 
  * Creating an ID, Operation, and Machine object, as well as parsing values, are all  O(1)
  * Inserting the Machine object into machineList is also  O(1)

3. **Total Complexity**: 
- Since the loop runs  N  times and each iteration is  O(1) , the overall complexity of this loop is  O(N)
- Adding this to the  O(N)  complexity of getMachinesDetails, the total complexity of fillMachinery is therefore  O(N)

> Thus, the overall time complexity of fillMachinery is  O(N) , where  N  is the number of machine records processed from the file


### **Class OperationRepository**

| Method               | Complexity |
|----------------------|------------|
| fillOperations(List) | O(N^2)     |

**`fillOperations`** : this method has a time complexity of  O(N x M) , where  N  is the number of Item objects in the input list items, and  M  represents the average number of Operation objects within each Item‘s operationList. Here’s the breakdown:

1. **Outer Loop (Iterates Over items)**: The outer for loop iterates over each Item in the items list, so this part has  O(N)  complexity

2. **Inner Loop (Iterates Over Each Item’s operationList)**:
- For each Item, the inner for loop iterates over operationList, which contains  M  Operation objects on average
- Cloning each Operation object is  O(1) , and adding it to the operations list is also  O(1) , resulting in  O(M)  operations for each Item.

3. **Overall Complexity**: Since the outer loop runs  N  times and the inner loop runs  M  times for each item, the total complexity is  O(N x M)

> Thus, the time complexity of the fillOperations method is  O(N x M) , where  N  is the number of Item objects and  M  is the average number of Operation objects per Item.


**- USEI01 complexity** O(N^2)

**Explanation**: The overall complexity of the USEI01 feature is  O(N^2)  because the most time-consuming methods in this set of operations are those with  O(N^2) 