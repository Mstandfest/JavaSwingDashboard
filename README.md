# Java Swing Dashboard for Data Visualization
**A simple yet powerful desktop dashboard for capturing, visualizing, and storing numerical data. This application is built with Java Swing for the graphical user interface and JFreeChart for dynamic chart creation.**

## Description
* This application provides a **user-friendly interface** for entering numerical values.
* Each entered value is immediately added to a line chart that **visualizes the data's history**.
* Additionally, the **average of all captured values** is calculated and displayed in real-time.
* The recorded data list can be **saved locally and loaded again** at a later time.

## Features
* **Data Entry:** Easily add numerical values (floating-point numbers).
* **Real-time Visualization:** The line chart updates instantly with each new value.
* **Live Statistics:** The current average of all entered values is continuously displayed.
* **Data Persistence:** Save and load the entire data list to a local file (werte.txt) using Java Serialization.
* **Data Management:** The current session can be completely reset with a single click.
* **User-Friendly UI:** A modern "Nimbus Look and Feel" and a clean, intuitive layout of controls.

## Tech Stack
* **Programming Language:** Java
* **GUI Framework:** Java Swing
* **Charting Library:** JFreeChart
* **Build Tool:** Maven
* **Development Environment:** IntelliJ IDEA

## Getting Started
**To run the project locally, you only need a Java Development Kit (JDK).**
**The required JFreeChart library will be downloaded automatically by Maven.**

### Prerequisites
* Java Development Kit (JDK), version 8 or newer.

### Installation & Setup
1. **Clone the repository:**
```
git clone https://github.com/Mstandfest/JavaSwingDashboard.git
```
2. **Open the project in your IDE** (e.g., IntelliJ IDEA or Eclipse).
* Your IDE should automatically detect the pom.xml file and set up the project. It will download the JFreeChart dependency for you.
3. **If dependencies not downloaded automatically:**
* In IntelliJ IDEA: Right-click the pom.xml file and select Maven -> Reload Project.
* In Eclipse: Right-click the project, select Maven -> Update Project....
4. **Running the Application**
* Run the main method in the Main.java class. The application window should now start.

## Code Structure
**The project is divided into three classes, each with a clear responsibility:**

### Main.java
* The entry point of the application (main method).
* Responsible for setting the global Look and Feel (Nimbus).
* Initializes the Dashboard window on the Swing Event Dispatch Thread (EDT) to ensure thread safety.

### Dashboard.java
* The main class representing the application window (inherits from JFrame).
* Builds the entire graphical user interface, including input fields, buttons, and the chart panel.
* Contains all ActionListeners and the logic for processing user input.
* Includes the updateDashboard() method to refresh the display (average and chart).

### Speicher.java
* A helper class with static methods for file handling.
* listeSpeichern() (saveList): Serializes the ArrayList<Double> and saves it to a file.
* listeLaden() (loadList): Deserializes the data from the file and returns it as a new ArrayList<Double>.
