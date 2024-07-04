import 'package:flutter/material.dart';
import 'kara.dart';
import 'khabara.dart';
import 'classa.dart';

class SaraPage extends StatefulWidget {
  @override
  _SaraPageState createState() => _SaraPageState();
}

class _SaraPageState extends State<SaraPage> {
  int _currentIndex = 0;

  final List<Widget> _children = [
    SaraPageWidget(),
    KhabaraPage(),
    ClassListScreen(),
    Kara(), // Navigate to Kara page
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
            label: 'kara Page',
          ),
        ],
      ),
    );
  }
}

class SaraPageWidget extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return Center(
      child: Text(
        'Welcome to Sara Page!',
        style: TextStyle(fontSize: 24),
      ),
    );
  }
}