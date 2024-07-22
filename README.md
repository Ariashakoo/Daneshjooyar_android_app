Android Daneshjooyar Project
Welcome to the Android Daneshjooyar Project! This project utilizes a Command Line Interface (CLI) for the admin account to manage students, teachers, and more. The system architecture ensures smooth data handling from back-end to front-end for a seamless user experience.

Project Architecture
CLI (Command Line Interface)
Admin Account: Manage Students, Teachers, and other entities.
Storage: The CLI sends data directly to the database which is stored as either text files or SQL databases.
Database
File-Based Storage: Utilizes text files for basic storage.
SQL Database: Optimal for efficient data storage and quick access with minimal code complexity.
Server
Data Handling: Retrieves data from the database and sends it to the front-end.
Frontend
Flutter Integration: Receives data through sockets in each required page.
Data Processing: The incoming data is usually in string format which is split and displayed part by part.
UI Enhancement: Each page is designed with a visually appealing background.
Key Features
Efficient data management with SQL for faster performance.
Simplified code and data structures for easier understanding.
Real-time data fetching and display using Flutter and socket integration.
Attractive UI with page-specific backgrounds.

