# Maze-generation

## Overview

Maze-generation is a comprehensive application for generating and solving mazes using various algorithms. This project was developed as part of the final year study at the Faculty of Sciences in Rabat for obtaining a bachelor degree in Mathematical and Computer Science.

## Demo

![Maze-generation Demo](demo/maze-generation-demo.gif)

## Key Features

- Maze generation using multiple algorithms:
  - Kruskal's algorithm
  - Prim's algorithm
  - Recursive Backtracking algorithm
  - Aldous-Broder algorithm
  - Wilson's algorithm
- Maze solving using multiple algorithms:
  - Breadth-First Search (BFS)
  - Depth-First Search (DFS)
  - Dijkstra's algorithm
  - A* algorithm
- Interactive GUI for maze visualization
- Step-by-step visualization of generation and solving processes
- Adjustable speed for algorithm execution
- Ability to save and load mazes
- Export mazes as images

## Technologies Used

- Java
- JavaFX for GUI development
- SQLite for database management
- Maven for project management and dependencies
- Scene Builder for UI design
- MVC (Model-View-Controller) architecture

## Development Environment

- NetBeans IDE
- Scene Builder
- DB Browser for SQLite
- PowerDesigner (for UML diagrams)
- GanttProject (for project planning)
- GitHub (for version control)

## Getting Started

### Prerequisites

- Java Development Kit (JDK) 8 or higher
- Maven
- JavaFX SDK

### Installation

1. Clone the repository:
   ```
   git clone https://github.com/Hamdane-yassine/Maze-generation.git
   ```
2. Navigate to the project directory:
   ```
   cd Maze-generation
   ```
3. Build the project using Maven:
   ```
   mvn clean install
   ```
4. Run the application:
   ```
   java -jar target/Maze-generation.jar
   ```

## Usage

1. Start the application
2. Use the "Initialization" section to create a grid of desired size
3. In the "Generation" section, choose a maze generation algorithm and set the execution speed
4. Click "Execute" to generate the maze, or use "Step-by-step" for a detailed view of the process
5. Once generated, use the "Solving" section to choose a solving algorithm
6. Click "Execute" or "Step-by-step" to solve the maze
7. Use the "Save" section to export the maze as an image or save it to the database for later use

## Project Structure

The project follows the MVC architecture:
- `model`: Contains the core logic for maze generation and solving algorithms
- `view`: JavaFX FXML files for the user interface
- `controller`: Handles user interactions and updates the view
- `util`: Utility classes and helper functions

## Contributing

Contributions are welcome! Please feel free to submit a Pull Request.

## Authors

- AJAANI Mouna
- HAMDANE Yassine

## Acknowledgments

- Mr. AHIOD Belaid (Project Supervisor)
- Faculty of Sciences, Rabat

## License

This project is open source and available under the [MIT License](LICENSE).
