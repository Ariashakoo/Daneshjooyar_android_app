import 'package:flutter/material.dart';
import 'package:intl/intl.dart';

void main() {
  runApp(MyApp());
}

class MyApp extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      debugShowCheckedModeBanner: false,
      home: AssignmentsPage(),
    );
  }
}

class AssignmentsPage extends StatefulWidget {
  @override
  _AssignmentsPageState createState() => _AssignmentsPageState();
}

class _AssignmentsPageState extends State<AssignmentsPage> {
  int _currentIndex = 0;

  final List<Assignment> assignments = [
    Assignment('تمرین مینی‌پروژه AP', DateTime.now().add(Duration(days: 2)), '4:00 عصر'),
    Assignment('تمرین مدار منطقی 1', DateTime.now().add(Duration(days: 4)), '6:00 عصر'),
    Assignment('تمرین ریاضی 2', DateTime.now().add(Duration(days: 5)), '12:00 ظهر'),
    Assignment('تمرین معادلات دیفرانسیل 2', DateTime.now().add(Duration(days: 6)), '9:00 صبح'),
    Assignment('تمرین معماری کامپیوتر', DateTime.now().add(Duration(days: 7)), '9:00 صبح'),
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
        title: Text('تمرین‌ها'),
      ),
      body: ListView.builder(
        itemCount: assignments.length,
        itemBuilder: (context, index) {
          return AssignmentCard(
            assignment: assignments[index],
            onTap: () {
              Navigator.push(
                context,
                MaterialPageRoute(
                  builder: (context) =>
                      AssignmentDetailsPage(assignment: assignments[index]),
                ),
              );
            },
          );
        },
      ),
      bottomNavigationBar: BottomNavigationBar(
        onTap: onTabTapped,
        currentIndex: _currentIndex,
        selectedItemColor: Colors.black,
        unselectedItemColor: Colors.black,
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
            label: 'tamrina',
          ),
        ],
      ),
    );
  }
}

class Assignment {
  final String title;
  final DateTime dueDate;
  final String time;
  bool isCompleted;

  Assignment(this.title, this.dueDate, this.time, {this.isCompleted = false});
}

class AssignmentCard extends StatelessWidget {
  final Assignment assignment;
  final VoidCallback onTap;

  const AssignmentCard({required this.assignment, required this.onTap});

  @override
  Widget build(BuildContext context) {
    return Opacity(
      opacity: assignment.isCompleted ? 0.5 : 1.0,
      child: Card(
        margin: EdgeInsets.symmetric(vertical: 8, horizontal: 16),
        child: ListTile(
          leading: Checkbox(
            value: assignment.isCompleted,
            onChanged: (bool? value) {
              // Handle checkbox value change if needed
            },
          ),
          title: Text(assignment.title),
          subtitle: Text(
              '${DateFormat.yMMMMd('fa').format(assignment.dueDate)} \n${assignment.time}'),
          onTap: onTap,
        ),
      ),
    );
  }
}

class AssignmentDetailsPage extends StatefulWidget {
  final Assignment assignment;

  const AssignmentDetailsPage({required this.assignment});

  @override
  _AssignmentDetailsPageState createState() => _AssignmentDetailsPageState();
}

class _AssignmentDetailsPageState extends State<AssignmentDetailsPage> {
  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: Text('جزئیات تمرین'),
      ),
      body: Padding(
        padding: EdgeInsets.all(16.0),
        child: Column(
          crossAxisAlignment: CrossAxisAlignment.start,
          children: [
            Text('عنوان: ${widget.assignment.title}', style: TextStyle(fontSize: 20)),
            SizedBox(height: 10),
            Text('ددلاین: ${DateFormat.yMMMMd('fa').format(widget.assignment.dueDate)}'),
            SizedBox(height: 10),
            Text('زمان تخمینی باقی‌مانده: 5 ساعت'),
            SizedBox(height: 10),
            Text('توضیحات: آشنایی با verilog و مدارهای آسنکرون'),
            SizedBox(height: 10),
            CheckboxListTile(
              title: Text('علامت‌گذاری به عنوان تکمیل شده'),
              value: widget.assignment.isCompleted,
              onChanged: (bool? value) {
                setState(() {
                  widget.assignment.isCompleted = value ?? false;
                });
              },
            ),
            TextField(
              decoration: InputDecoration(
                labelText: 'توضیحات تحویل',
                border: OutlineInputBorder(),
              ),
              maxLines: 3,
            ),
            SizedBox(height: 10),
            ElevatedButton(
              onPressed: () {},
              child: Text('ثبت'),
            ),
          ],
        ),
      ),
    );
  }
}