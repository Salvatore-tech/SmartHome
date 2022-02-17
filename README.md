# SmartHome
The Smarthome project aims to implement a system capable of managing smart devices inside your home. 
It offers a set of Rest API, to add devices of different types, to which different information including name, type and status have been attached.

To allow the user a better experience, he was offered the possibility to create rooms where to place the devices and search them by their room.
Another of the features developed is the creation of scenarios self-activated through a temporal event or when a value exceeds a threshold. 
Ex. "Turn off all the lights and set the AC to 20 degree celsius every day at 12:00 PM"
 
Each action is saved into a history and it is also possible to revert the last action performed.  

## Architecture  
The architecture can be illustrated by initially analyzing the subsystems and packages present. We can see a layered architecture, where it has tried to maintain high cohesion between the classes of a package and reduce coupling between packages of different subsystems.
<img src="https://github.com/Salvatore-tech/SmartHome/blob/master/resources/architecture.png" width="60%" height="50%">

- Application: The first subsystem contains mainly Controller classes, capable of receiving a request from the user, delegate the execution and return an output. 

- Business logic: in the second we find those classes that are determined by the implementation choices.
    - Service: these classes are the core of the elaboration, accepting the input from the upper layer and validating it before running the execution.
    - Memento: contains the classes where it is meaningful to record a previous state of the object by using the memento pattern.
    - Strategy: collects different strategies to parse the input json body.
    - Command: stand-alone objects to reduce the bolerplate for operation in common like CRUD ones.
    - Beans: other Spring Boot utilities beans
    
- Persistence: the last subsystem handles the access to the datasource
    - Repository: these classes offer entry points to read and write the persistent data.
    - Model: entity classes mapped using Hibernate.
