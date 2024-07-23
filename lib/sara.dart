import 'package:flutter/material.dart';
import 'dart:io';
import 'kara.dart';
import 'khabara.dart';
import 'classa.dart';
import 'tasks_page.dart';

class SaraPage extends StatefulWidget {
  @override
  _SaraPageState createState() => _SaraPageState();
}

class _SaraPageState extends State<SaraPage> {
  int _currentIndex = 0;
  String highestGrade = "";
  String lowestGrade = "";

  final List<Widget> _children = [
    HomePage(highestGrade: '', lowestGrade: ''), // Initialize with empty strings
    NewsPage(),
    ClassesPage(),
    Kara(), // Navigate to Kara page
    AssignmentsPage(),
  ];

  void onTabTapped(int index) {
    setState(() {
      _currentIndex = index;
    });
  }

  @override
  void initState() {
    super.initState();
    fetchGrades();
  }

  void fetchGrades() async {
    try {
      Socket socket = await Socket.connect('', 12345);
      socket.write('sara\n');

      // Listen for the server's response
      socket.listen((List<int> event) {
        String serverResponse = String.fromCharCodes(event);
        List<String> grades = serverResponse.split(',');
        setState(() {
          highestGrade = grades[0].trim();
          lowestGrade = grades[1].trim();
          // Update HomePage with new values
          _children[0] = HomePage(highestGrade: highestGrade, lowestGrade: lowestGrade);
        });
        socket.destroy();
      });
    } catch (e) {
      print('Error: $e');
    }
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: Text('Sara Page'),
      ),
      body: _children[_currentIndex],
      bottomNavigationBar: BottomNavigationBar(
        onTap: onTabTapped,
        currentIndex: _currentIndex,
        selectedItemColor: Colors.black, // Set selected item color to black
        unselectedItemColor: Colors.black, // Set unselected item color to black
        items: [
          BottomNavigationBarItem(
            icon: Icon(Icons.home),
            label: 'Sara Page',
          ),
          BottomNavigationBarItem(
            icon: Icon(Icons.newspaper),
            label: 'Khabara Page',
          ),
          BottomNavigationBarItem(
            icon: Icon(Icons.class_),
            label: 'Classa Page',
          ),
          BottomNavigationBarItem(
            icon: Icon(Icons.file_copy),
            label: 'Kara Page',
          ),
          BottomNavigationBarItem(
            icon: Icon(Icons.file_copy),
            label: 'Tamrina',
          ),
        ],
      ),
    );
  }
}

class HomePage extends StatefulWidget {
  final String highestGrade;
  final String lowestGrade;

  const HomePage({
    Key? key,
    required this.highestGrade,
    required this.lowestGrade,
  }) : super(key: key);

  @override
  State<HomePage> createState() => _HomePageState();
}

class _HomePageState extends State<HomePage> {
  List<TaskModel> tasks = [];

  @override
  void initState() {
    super.initState();
    tasks = Kara.getTasks();
  }

  @override
  Widget build(BuildContext context) {
    List<TaskModel> currentTasks = tasks.where((task) => !task.isDone).toList();
    List<TaskModel> completedTasks = tasks.where((task) => task.isDone).toList();

    return Container(
      decoration: BoxDecoration(
        image: DecorationImage(
          image: AssetImage('assets/images/ae.jpg'), // replace with your background image
          fit: BoxFit.cover,
        ),
      ),
      child: Scaffold(
        backgroundColor: Colors.transparent,
        body: SingleChildScrollView(
          child: Padding(
            padding: const EdgeInsets.all(16.0),
            child: Column(
              crossAxisAlignment: CrossAxisAlignment.start,
              children: [
                const SizedBox(height: 60),
                const Text(
                  'Summary',
                  style: TextStyle(fontSize: 24, fontWeight: FontWeight.bold),
                ),
                const SizedBox(height: 20),
                GridView.count(
                  crossAxisCount: 3,
                  shrinkWrap: true,
                  mainAxisSpacing: 10,
                  crossAxisSpacing: 10,
                  children: [
                    summaryCard(Icons.star, 'Highest grade: ${widget.highestGrade}'),
                    summaryCard(Icons.sentiment_dissatisfied, 'Lowest grade: ${widget.lowestGrade}'),
                    summaryCard(Icons.favorite, 'Upcoming Exams: 2'),
                    summaryCard(Icons.access_alarm, 'Tasks remaining: ${currentTasks.length}'),
                    summaryCard(Icons.access_time, 'Missed deadlines: 1'),
                  ],
                ),
                const SizedBox(height: 20),
                const Text(
                  'Current Tasks',
                  style: TextStyle(fontSize: 20, fontWeight: FontWeight.bold),
                ),
                const SizedBox(height: 10),
                ...currentTasks.map((task) => taskCard(task.title, false)).toList(),
                const SizedBox(height: 20),
                const Text(
                  'Completed Tasks',
                  style: TextStyle(fontSize: 20, fontWeight: FontWeight.bold),
                ),
                const SizedBox(height: 10),
                ...completedTasks.map((task) => taskCard(task.title, true)).toList(),
              ],
            ),
          ),
        ),
      ),
    );
  }

  Widget summaryCard(IconData icon, String text) {
    return Container(
      padding: const EdgeInsets.all(8),
      decoration: BoxDecoration(
        color: Colors.white,
        borderRadius: BorderRadius.circular(10),
        boxShadow: const [
          BoxShadow(
            color: Colors.black12,
            blurRadius: 10,
          ),
        ],
      ),
      child: Column(
        mainAxisAlignment: MainAxisAlignment.center,
        children: [
          Icon(icon, size: 40),
          const SizedBox(height: 10),
          Text(
            text,
            textAlign: TextAlign.center,
          ),
        ],
      ),
    );
  }

  Widget taskCard(String text, bool isDone) {
    return Container(
      padding: const EdgeInsets.symmetric(horizontal: 10, vertical: 5),
      decoration: BoxDecoration(
        color: isDone ? Colors.green[100] : Colors.red[100],
        borderRadius: BorderRadius.circular(10),
      ),
      child: Row(
        mainAxisAlignment: MainAxisAlignment.spaceBetween,
        children: [
          Text(text),
          Icon(
            isDone ? Icons.check_circle : Icons.cancel,
            color: isDone ? Colors.green : Colors.red,
          ),
        ],
      ),
    );
  }
}
