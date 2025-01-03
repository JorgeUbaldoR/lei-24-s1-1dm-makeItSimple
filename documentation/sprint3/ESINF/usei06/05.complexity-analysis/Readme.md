# USEI06 - Average Execution Times and Waiting Times

### **Class Simulator**

| Method                                                             | Complexity |
|--------------------------------------------------------------------|------------|
| fillExecutionPerOperation(Operation operation)                     | O(1)       |
| calculateAverageExecutionTimes()                                   | O(N)       |
| printAverageExecutionTime()                                        | O(N⋅logN)  | 
| List<Map.Entry<Operation, Float>> ascendingOrderAvgExecutionTime() | O(N⋅logN)  |
| List<Map.Entry<Item, Float>> ascendingOrderWaitingTime()           | O(N⋅logN)  |
| Map<Operation, Float> getExecutionTimesOperation()                 | O(1)       |
| Map<Operation, Float> Map<Item, Float> getExecutionWaitingTime()   | O(1)       |
| Queue<Item> getItemList()                                          | O(1)       |
| fillWaitingTime(List<OperationQueue> operationQueueList)           | O(N^2)     |
| printWaitingTime()                                                 | O(N⋅logN)  |

**- USEI06 complexity** O(N^2)

