# US006 - Average Execution Times and Waiting Times


## 1. Requirements Engineering

### 1.1. User Story Description

As a Plant Floor Manager, I want to see the average execution time per operation and the corresponding waiting times.

### 1.2. Customer Specifications and Clarifications 

**From the specifications document:**

>	None.

**From the client clarifications:**

> **Question:** Regarding USEI06, what's the waiting time supposed to be? Is it the waiting time of a workstation waiting for an item to start it's operation? For example, press station is waiting for a cut operation to be done first in another station.
>
> **Answer:** No, average waiting time for items awaiting a specific operation to be carried out on them.


### 1.3. Acceptance Criteria

* **AC1:** The average execution time and waiting time must be calculated and displayed in minutes.

### 1.4. Found out Dependencies

* There is a dependency on "US002 - "  " as a simulator must have been implemented.

### 1.5 Input and Output Data

**Output Data:**

* List of average execution time per operation 
* List of waiting time per item
* (In)Success of the operation

### 1.6. System Sequence Diagram (SSD)

**_Other alternatives might exist._**

#### Alternative One

![System Sequence Diagram - Alternative One](svg/us006-system-sequence-diagram-alternative-one.svg)

### 1.7 Other Relevant Remarks

* None.