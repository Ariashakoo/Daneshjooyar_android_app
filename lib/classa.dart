import 'package:flutter/material.dart';
import 'dart:io';
import 'dart:convert';

class ClassModel {
  final String title;
  final String professor;
  final int unitCount;
  final int remainingAssignments;
  bool isDone;

  ClassModel({
    required this.title,
    required this.professor,
    required this.unitCount,
    required this.remainingAssignments,
    this.isDone = false,
  });
}

class ClassesPage extends StatefulWidget {
  @override
  _ClassesPageState createState() => _ClassesPageState();
}

class _ClassesPageState extends State<ClassesPage> {
  final List<ClassModel> _classes = [];

  @override
  void initState() {
    super.initState();
    _fetchClassesFromServer();
  }

  Future<void> _fetchClassesFromServer() async {
    try {
      final socket = await Socket.connect('192.168.8.100', 12345);
      socket.write('course\n'); // Send the command with a newline character

      socket.listen((data) {
        final decodedData = utf8.decode(data);
        final classLines = decodedData.split('\n');

        setState(() {
          _classes.clear();
          for (var line in classLines) {
            if (line.trim().isNotEmpty) {
              final parts = line.split('~');
              if (parts.length == 4) {
                final classModel = ClassModel(
                  title: parts[0],
                  professor: parts[2],
                  unitCount: int.parse(parts[1]),
                  remainingAssignments: int.parse(parts[3]),
                );
                _classes.add(classModel);
              }
            }
          }
        });

        socket.destroy();
      });
    } catch (e) {
      print("Error: $e");
    }
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: Text('کلاس‌ها'),
        actions: [
          Padding(
            padding: const EdgeInsets.only(right: 16.0),
            child: Center(child: Text('ترم بهار ۱۴۰۳')),
          ),
        ],
      ),
      body: Container(
        decoration: BoxDecoration(
          image: DecorationImage(
            image: AssetImage('assets/images/ae.jpg'),
            fit: BoxFit.cover,
          ),
        ),
        child: Padding(
          padding: const EdgeInsets.all(8.0),
          child: ListView(
            children: _classes.map((classModel) {
              return ClassCard(
                title: classModel.title,
                teacher: classModel.professor,
                unitCount: classModel.unitCount,
                remainingAssignments: classModel.remainingAssignments,
                topStudent: 'Parsa Hamzei', // Placeholder for top student
                color: Colors.purple, // Placeholder color
              );
            }).toList(),
          ),
        ),
      ),
    );
  }
}

class ClassCard extends StatelessWidget {
  final String title;
  final String teacher;
  final int unitCount;
  final int remainingAssignments;
  final String topStudent;
  final Color color;

  ClassCard({
    required this.title,
    required this.teacher,
    required this.unitCount,
    required this.remainingAssignments,
    required this.topStudent,
    required this.color,
  });

  @override
  Widget build(BuildContext context) {
    return Card(
      color: color,
      child: Padding(
        padding: const EdgeInsets.all(16.0),
        child: Column(
          crossAxisAlignment: CrossAxisAlignment.start,
          children: [
            Text(
              'استاد: $teacher',
              style: TextStyle(color: Colors.white, fontSize: 16),
            ),
            SizedBox(height: 8),
            Text(
              title,
              style: TextStyle(color: Colors.white, fontSize: 20, fontWeight: FontWeight.bold),
            ),
            SizedBox(height: 8),
            Row(
              mainAxisAlignment: MainAxisAlignment.spaceBetween,
              children: [
                Text(
                  'تعداد واحد: $unitCount',
                  style: TextStyle(color: Colors.white),
                ),
                Text(
                  'تکالیف باقی‌مانده: $remainingAssignments',
                  style: TextStyle(color: Colors.white),
                ),
                Text(
                  'دانشجوی ممتاز: $topStudent',
                  style: TextStyle(color: Colors.white),
                ),
              ],
            ),
          ],
        ),
      ),
    );
  }
}

void main() {
  runApp(MyApp());
}

class MyApp extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      home: ClassesPage(),
    );
  }
}
