# USEI08 - Processing order based on priority


## 1. Requirements Engineering

### 1.1. User Story Description

Consider an improvement to the simulator developed in USEI02 that takes into account a processing order based on priority.

### 1.2. Customer Specifications and Clarifications 

**From the specifications document:**

>	None. 

**From the client clarifications:**

> **Question:** None.
>
> **Answer:** None.



### 1.3. Acceptance Criteria

* **AC1:** The items in the queue should be assigned, according to their priority (high, normal, low) to the available machine that can perform the required operation the fastest.
* **AC2:** Statistical measures should be produced similarly for this variant of the simulator.

### 1.4. Found out Dependencies

* There is a dependency on "USEI01 - Store the information imported from the files" as there must be items and machines in the system to do a simulation.
* There is a dependency on "US002 -  Implement a simulator" as there must be a simulator to be imporve.
### 1.5 Input and Output Data

**Input Data:**

* Typed data:
    * None
	
* Selected data:
    * Option "Simulator With Priority"

**Output Data:**
* List of updates, queues, status of machines and new processing.
* (In)Success of the operation.
* Simulator execution

### 1.6. System Sequence Diagram (SSD)

**_Other alternatives might exist._**

#### Alternative One

![System Sequence Diagram - Alternative One](svg/usei08-system-sequence-diagram-USEI02___Simulator_Without_Priority.svg)


### 1.7 Other Relevant Remarks

* The processing time of a machine is in minutes.
* 1 second is equals to a while loop in code.
