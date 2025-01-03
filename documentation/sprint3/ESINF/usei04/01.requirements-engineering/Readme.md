# USEI04 - Calculate execution times by each operation


## 1. Requirements Engineering

### 1.1. User Story Description

As a user of the system, I want to calculate the execution time of each operation.

### 1.2. Customer Specifications and Clarifications

**From the specifications document:**

>	Each operation should have an ID, a name and time of the operation.

**From the client clarifications:**

> **Question:** In which format should the time be presented?
>
> **Answer:** Time should be presented in minutes.

> **Question:** Regarding USEI04 - Calculate execution times by each operation, is for each operation and item or for the total operation time(each operation) for all the items of the csv file?
> 
> **Answer:** It's for each operation type (like CUT).

> **Question:** The USEI04 states that we should calculate execution times by each operation. Does it mean that we should calculate the total time needed for each operation to be concluded? So if 2 operations of the same type like "cutting" was concluded it should be both of the times added to each other?
> 
> **Answer:** It means labour time for an operation type.

### 1.3. Acceptance Criteria

* **AC1:** There should be operations.
* **AC2:** The time in the operations should be in the right format.

### 1.4. Found out Dependencies

* There is a dependency on "USEI02 - Implement a simulator that processes all the items according" as there must be a simulator

### 1.5 Input and Output Data

**Input Data:**

* Typed data:

* Selected data:

**Output Data:**

* List of time used in each operation
* (In)Success of the operation

### 1.6. System Sequence Diagram (SSD)

**_Other alternatives might exist._**

#### Alternative One

![System Sequence Diagram - Alternative One](svg/usei04-system-sequence-diagram-alternative-one-System_Sequence_Diagram__SSD____Alternative_One.svg)

### 1.7 Other Relevant Remarks

