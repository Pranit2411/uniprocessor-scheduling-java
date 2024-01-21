### Steps to build and run the project:
1. Ensure that you have JDK version 20.0.2 or later installed (https://www.oracle.com/java/technologies/downloads/#jdk21-windows)

   **Note**: On Windows, download the right .exe file, launch the installer and follow the steps on screen.
2. Clone or download the repository on your computer.
3. From the project's root directory, navigate into `src/`
4. When you are in `uniprocessor-scheduling-java/src`, start your terminal from within this directory.
5. Compile the package using the command:
   `javac Main.java`
6. Run the program using the command (command line arguments specified below in the "Schedule selection section):
   `java Main <schedule_selection>`

Alternatively, if you have the right version of Java and IntelliJ installed, you can run the project using the IDE.

##### Schedule selection
When running the program, you can specify which scheduling algorithm to run using command line arguments. If none are supplied, all the implemented algorithms will be simulated.
The following arguments can be provided

All -
`java Main`

SPN Scheduling -
`java Main 1`

Feedback Scheduling -
`java Main 2`

HRRN -
`java Main 3`

FCFS -
`java Main 4`

RR Scheduling -
'java Main 5'
