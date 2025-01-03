# USEI07 - Produce a listing representing the flow dependency between machines 

## 3. Design - User Story Realization 

### 3.1. Rationale

_**Note that SSD - Alternative One is adopted.**_

| Interaction ID | Question: Which class is responsible for...                 | Answer              | Justification (with patterns)                                                                              |
|:-------------  |:------------------------------------------------------------|:--------------------|:-----------------------------------------------------------------------------------------------------------|
| Step 1  		 | 	... interacting with the actor?                            | SimulatorUI         | Pure Fabrication: Responsible for interacting with the user, displaying data, and receiving inputs         |
| 			  		 | 	... coordinating the simulation?                           | SimulatorController | Controller: Responsible for coordinating the simulation logic and interacting with repositories.           |
| 			  		 | ... retrieving the flow dependency between machines?        | Simulator           | IE: Simulator has a map of machines and their associated machines.                                         |
| Step 2  		 | 	...starting the simulation?		                              |   SimulatorController                  | Controller: The controller starts the simulation and passes the necessary configurations to the simulator. |
| Step 3  		 | 	...showing the machines and their respective dependencies? | Simulator                | IE: Simulator has a map of machines and their associated machines.                                                   |

### Systematization ##

According to the taken rationale, the conceptual classes promoted to software classes are: 

* OperationQueue
* Operation
* Machine
* Simulator
* ID

Other software classes (i.e. Pure Fabrication) identified: 

* SimulatorUI


## 3.2. Sequence Diagram (SD)

_**Note that SSD - Alternative Two is adopted.**_

### Full Diagram

This diagram shows the full sequence of interactions between the classes involved in the realization of this user story.

![Sequence Diagram - Full](svg/usei07-sequence-diagram-full.svg)

## 3.3. Class Diagram (CD)

![Class Diagram](svg/usei07-class-diagram.svg)