import 'package:flutter/material.dart';
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

  final List<Widget> _children = [
    HomePage(), // Replace SaraPageWidget with HomePage
    NewsPage(),
    ClassListScreen(),
    Kara(), // Navigate to Kara page
    AssignmentsPage(),
  ];

  void onTabTapped(int index) {
    setState(() {
      _currentIndex = index;
    });
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

class HomePage extends StatelessWidget {
  const HomePage({Key? key}) : super(key: key);

  @override
  Widget build(BuildContext context) {
    return Container(
      decoration: BoxDecoration(
        image: DecorationImage(
          image: AssetImage('assets/images/back.jpg'), // replace with your background image
          fit: BoxFit.cover,
        ),
      ),
      child: Scaffold(
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
                    summaryCard(Icons.star, 'Highest grade : 19.25'),
                    summaryCard(Icons.favorite, 'Upcoming Exams: 2'),
                    summaryCard(Icons.access_alarm, 'Tasks remaining: 3'),
                    summaryCard(Icons.access_time, 'Missed deadlines: 1'),
                    summaryCard(Icons.sentiment_dissatisfied, 'Lowest grade: 12'),
                  ],
                ),
                const SizedBox(height: 20),
                const Text(
                  'Current Tasks',
                  style: TextStyle(fontSize: 20, fontWeight: FontWeight.bold),
                ),
                const SizedBox(height: 10),
                taskCard('DLD - HW1', false),
                taskCard('AP - HW6', false),
                const SizedBox(height: 20),
                const Text(
                  'Completed Tasks',
                  style: TextStyle(fontSize: 20, fontWeight: FontWeight.bold),
                ),
                const SizedBox(height: 10),
                taskCard('DS - HW3', true),
                taskCard('AP - HW5', true),
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
        color: isDone? Colors.green[100] : Colors.red[100],
        borderRadius: BorderRadius.circular(10),
      ),
      child: Row(
        mainAxisAlignment: MainAxisAlignment.spaceBetween,
        children: [
          Text(text),
          Icon(
            isDone? Icons.check_circle : Icons.cancel,
            color: isDone? Colors.green : Colors.red,
          ),
        ],
      ),
    );
  }
}