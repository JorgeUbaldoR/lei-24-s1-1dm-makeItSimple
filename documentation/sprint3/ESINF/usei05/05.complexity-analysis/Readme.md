# USEI05 - Workstation Statistics

### **Class Simulator**

| Method                                                               | Complexity |
|----------------------------------------------------------------------|------------|
| addExecutionTimesMachine(Machine, float)                             | O(1)       |
| List<Map.Entry<Machine, Float>> ascendingOrderMachineTimes()         | O(N⋅logN)  |
| printExecutionTimesMachine()                                         | O(N⋅logN)  |
| Map<Operation, Float> getExecutionTimesOperation()                   | O(1)       |
| Map<Operation, Float> Map<Machine, Float> getExecutionTimesMachine() | O(1)       |


**`addExecutionTimesMachine(Machine, float)`** : this method has an O(1) complexity, meaning that it performs in constant time. Here’s a breakdown of why this is the case:

1. **Checking Key Existence (containsKey)**: The method first checks if the machineUsage map already contains the key m. In a HashMap, this operation has an O(1) complexity on average because the lookup is performed based on a hashed key

2. **Insertion or Update (put and get)**:
- If m is not present in the map, machineUsage.put(m, time) adds it directly, which is also O(1)
- If m is already in the map, machineUsage.get(m) retrieves the current time value associated with the key m, and O(1) time is spent updating the total execution time. The final update to the map (put) is also O(1)

> Since each of these operations—checking for a key, retrieving a value, and adding or updating a key-value pair—executes in constant time, the overall complexity of the addExecutionTimesMachine method is O(1). This means that it executes in the same amount of time regardless of the size of machineUsage

**`List<Map.Entry<Machine, Float>> ascendingOrderMachineTimes `** : this method has a O(n log n) complexity, where n is the number of entries in the machineUsage map. Here’s a breakdown of why this is the case:

1. **getExecutionTimesMachine**: This method retrieves the Map<Machine, Float> containing the execution times. Assuming it’s a simple getter, this operation takes O(1) time. However, if it involves computation or iteration, it could take O(n) time, but we assume O(1) for simplicity

2. **The entrySet() method returns a set of key-value pairs (i.e., Map.Entry<Machine, Float>) from the machineUsage map.**:
- The time complexity of calling entrySet() on a map is O(1), but it creates a set containing n entries, where n is the number of entries in the map

3. new ArrayList<>(...): This creates a new ArrayList and initializes it with the elements from entrySet()
- The time complexity of this step is O(n), as it involves copying all n entries from the entrySet into the new list

4. list.sort(...): The list is sorted using Map.Entry.comparingByValue(Comparator.reverseOrder())
- Sorting the list involves a comparison-based sorting algorithm (typically Timsort in Java). The time complexity of the sorting operation is O(n log n)

> Thus, the overall time complexity of the ascendingOrderMachineTimes() method is dominated by the sorting step, which is O(n log n)

**`printExecutionTimesMachine`** : this method has a O(n log n) time complexity, where n is the number of entries in the machineUsage map. Here’s a breakdown of why this is the case

1. **ascendingOrderMachineTimes**: as we analyzed previously, ascendingOrderMachineTimes() has a O(n log n) time complexity because it sorts the list of entries based on the execution times. This sorting operation dominates the time complexity of this method

2. **sumTotalTime**: iterates over all entries in the map to sum the values, the time complexity is O(n), where n is the number of machines

3. **Printing the Statistics**: The method proceeds to print the formatted statistics to the console
- The System.out.println() and System.out.printf() calls are used to output the formatted text. These operations are typically O(1) for each print statement.
- The loop that iterates through the list of entries (with n entries) performs O(n) iterations. In each iteration, it computes a percentage and prints the details of each machine. These operations are O(1) for each entry

> The overall time complexity of the printExecutionTimesMachine() method is dominated by the sorting step in ascendingOrderMachineTimes(), which is O(n log n)

> **`Map<Operation, Float> getExecutionTimesOperation`** : The space complexity is O(1) for the method itself, as it does not create any new data structures. The space required is only for returning the reference to the existing operationTime map.


> **`Map<Operation, Float> Map<Machine, Float> getExecutionTimesMachine`** : The space complexity is O(1) for the method itself, as it does not create any new data structures. It only returns a reference to the existing machineUsage map.


**- USEI05 complexity**: The overall time complexity of the USEI05 class is O(n log n), as this complexity dominates the other complexities of O(1), and the space complexity is O(n), due to the creation of new lists for storing map entries.