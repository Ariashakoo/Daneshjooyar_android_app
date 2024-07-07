import 'package:flutter/material.dart';
import 'package:intl/intl.dart'; // Only import this for the date formatting

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
  final List<Assignment> assignments = [
    Assignment('AP Mini Project Assignment', DateTime.now().add(Duration(days: 2)), '4:00 PM'),
    Assignment('Digital Logic Circuits 1 Assignment', DateTime.now().add(Duration(days: 4)), '6:00 PM'),
    Assignment('Math 2 Assignment', DateTime.now().add(Duration(days: 5)), '12:00 PM'),
    Assignment('Differential Equations 2 Assignment', DateTime.now().add(Duration(days: 6)), '9:00 AM'),
    Assignment('Computer Architecture Assignment', DateTime.now().add(Duration(days: 7)), '9:00 AM'),
  ];

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: Text('Assignments'),
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
              '${DateFormat.yMMMMd().format(assignment.dueDate)} \n${assignment.time}'),
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
        title: Text('Assignment Details'),
      ),
      body: Padding(
        padding: EdgeInsets.all(16.0),
        child: Column(
          crossAxisAlignment: CrossAxisAlignment.start,
          children: [
            Text('Title: ${widget.assignment.title}', style: TextStyle(fontSize: 20)),
            SizedBox(height: 10),
            Text('Deadline: ${DateFormat.yMMMMd().format(widget.assignment.dueDate)}'),
            SizedBox(height: 10),
            Text('Estimated Remaining Time: 5 hours'),
            SizedBox(height: 10),
            Text('Description: Familiarize yourself with Verilog and asynchronous circuits'),
            SizedBox(height: 10),
            CheckboxListTile(
              title: Text('Mark as completed'),
              value: widget.assignment.isCompleted,
              onChanged: (bool? value) {
                setState(() {
                  widget.assignment.isCompleted = value ?? false;
                });
              },
            ),
            TextField(
              decoration: InputDecoration(
                labelText: 'Submission Notes',
                border: OutlineInputBorder(),
              ),
              maxLines: 3,
            ),
            SizedBox(height: 10),
            ElevatedButton(
              onPressed: () {},
              child: Text('Submit'),
            ),
          ],
        ),
      ),
    );
  }
}