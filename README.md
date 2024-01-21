# uniprocessor-scheduling-java

This project aims to develop and analyze a suite of essential uniprocessor scheduling algorithms, a cornerstone in the field of operating systems and process management. The focus is on implementing six fundamental algorithms: 
First Come First Serve (FCFS), Round Robin (RR), Shortest Process Next (SPN), Shortest Remaining Time (SRT), Highest Response Ratio Next (HRRN), and Feedback.

Each algorithm offers a unique approach to handling process scheduling, balancing factors such as efficiency, fairness, and response time. FCFS, characterized by its simplicity and queue-based structure, serves as a baseline for comparison. RR introduces time quantums to enhance fairness in process handling. SPN and SRT are both efficiency-oriented, prioritizing processes based on their execution time, with SRT providing a dynamic, preemptive approach. HRRN combines waiting time and execution time to optimize overall response ratio, while Feedback, with its multiple queues and dynamic prioritization, addresses the needs of diverse process types.

The project's implementation will be in a controlled simulation environment, allowing for the detailed analysis and comparison of each algorithm under various workloads. This comparison will include metrics such as average turnaround time, waiting time, and CPU utilization, providing insights into the strengths and weaknesses of each algorithm in different scenarios.

The outcome of this project is expected to serve as an educational tool for students and a reference for researchers in the field of operating systems, highlighting the practical implications and performance characteristics of these fundamental scheduling algorithms in uniprocessor systems.

References:

William Stallings. Operating Systems: Internals and Design Principals, 1992


**Steps to build and run the project:**

1. Ensure that you have JDK version 20.0.2 or later installed (https://www.oracle.com/java/technologies/downloads/#jdk21-windows)
    Note: On Windows, download the right .exe file, launch the installer and follow the steps on screen.
2. Clone or download the repository on your computer.
3. From the project's root directory, navigate into `src/`
4. When you are in `uniprocessor-scheduling-java/src`, start your terminal from within this directory.
5. Compile the package using the command:
    `javac Main.java`
6. Run the program using the command (command line arguments specified below in the "Schedule selection" section):
    `java Main <schedule_selection>`

Alternatively, if you have the right version of Java and IntelliJ installed, you can run the project using the IDE.


Schedule selection
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

RR -
`java Main 5`
