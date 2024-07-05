import 'package:flutter/material.dart';
import 'package:intl/intl.dart';

class ClassModel {
  final String id;
  final String title;
  final String professor;
  final DateTime classTime;
  bool isDone;

  ClassModel({required this.id, required this.title, required this.professor, required this.classTime, this.isDone = false});
}

class ClassListScreen extends StatefulWidget {
  @override
  _ClassListScreenState createState() => _ClassListScreenState();
}

class _ClassListScreenState extends State<ClassListScreen> {
  final List<ClassModel> _classes = [];
  final TextEditingController _classTextEditingController = TextEditingController();
  final TextEditingController _professorTextEditingController = TextEditingController();
  int _currentIndex = 0;
  DateTime _selectedDate = DateTime.now();
  TimeOfDay _selectedTime = TimeOfDay(hour: 0, minute: 0);

  void _createClass(ClassModel classModel) {
    setState(() {
      _classes.add(classModel);
    });
  }

  void _removeClass(String classId) {
    setState(() {
      _classes.removeWhere((classModel) => classModel.id == classId);
    });
  }

  void onTabTapped(int index) {
    setState(() {
      _currentIndex = index;
    });
  }

  void _toggleClassStatus(ClassModel classModel) {
    setState(() {
      classModel.isDone =!classModel.isDone;
    });
  }

  Future<void> _selectDate(BuildContext context) async {
    final DateTime? picked = await showDatePicker(
      context: context,
      initialDate: _selectedDate,
      firstDate: DateTime(2015),
      lastDate: DateTime(2101),
    );
    if (picked!= null) {
      setState(() {
        _selectedDate = picked;
      });
    }
  }

  Future<void> _selectTime(BuildContext context) async {
    final TimeOfDay? picked = await showTimePicker(
      context: context,
      initialTime: _selectedTime,
    );
    if (picked!= null) {
      setState(() {
        _selectedTime = picked;
      });
    }
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: Text('My Classes'),
        centerTitle: true,
      ),
      body: Container(
        decoration: BoxDecoration(
          image: DecorationImage(
            image: AssetImage('assets/images/back.jpg'), // replace with your image
            fit: BoxFit.cover,
          ),
        ),
        child: ListView.builder(
          itemCount: _classes.length,
          itemBuilder: (context, index) {
            return ListTile(
              title: Text(
                _classes[index].title,
                style: TextStyle(
                  color: _classes[index].isDone? Colors.green : Colors.red,
                ),
              ),
              subtitle: Text(
                'Professor: ${_classes[index].professor}\nTime: ${DateFormat.yMMMd().add_Hms().format(_classes[index].classTime)}',
              ),
              trailing: IconButton(
                icon: Icon(Icons.delete),
                onPressed: () {
                  _removeClass(_classes[index].id);
                },
              ),
              onTap: () {
                _toggleClassStatus(_classes[index]);
              },
            );
          },
        ),
      ),
      floatingActionButton: FloatingActionButton(
        child: Icon(Icons.add),
        onPressed: () {
          showDialog(
            context: context,
            builder: (context) {
              return AlertDialog(
                title: Text('New Class'),
                content: Column(
                  mainAxisSize: MainAxisSize.min,
                  children: [
                    TextField(
                      controller: _classTextEditingController,
                      decoration: InputDecoration(
                        labelText: 'Class Name',
                      ),
                      maxLines: 2,
                    ),
                    SizedBox(height: 10),
                    TextField(
                      controller: _professorTextEditingController,
                      decoration: InputDecoration(
                        labelText: 'Professor Name',
                      ),
                      maxLines: 2,
                    ),
                    SizedBox(height: 10),
                    ElevatedButton(
                      onPressed: () => _selectDate(context),
                      child: Text('Select Class Date'),
                    ),
                    Text(DateFormat.yMMMd().format(_selectedDate)),
                    SizedBox(height: 10),
                    ElevatedButton(
                      onPressed: () => _selectTime(context),
                      child: Text('Select Class Time'),
                    ),
                    Text(_selectedTime.format(context)),
                  ],
                ),
                actionsAlignment: MainAxisAlignment.spaceBetween,
                actions: [
                  TextButton(
                    onPressed: () {
                      Navigator.of(context).pop();
                    },
                    child: Text('Cancel'),
                  ),
                  TextButton(
                    onPressed: () {
                      if (_classTextEditingController.text.isNotEmpty && _professorTextEditingController.text.isNotEmpty) {
                        final ClassModel newClass = ClassModel(
                          id: DateTime.now().toString(),
                          title: _classTextEditingController.text,
                          professor: _professorTextEditingController.text,
                          classTime: DateTime(
                            _selectedDate.year,
                            _selectedDate.month,
                            _selectedDate.day,
                            _selectedTime.hour,
                            _selectedTime.minute,
                          ),
                        );
                        _createClass(newClass);
                        _classTextEditingController.clear();
                        _professorTextEditingController.clear();
                        Navigator.of(context).pop();
                      }
                    },
                    child: Text('Save'),
                  ),
                ],
              );
            },
          );
        },
      ),
    );
  }
}

class ClassesPage extends StatelessWidget {
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
            image: AssetImage('assets/background.jpg'), // replace with your image
            fit: BoxFit.cover,
          ),
        ),
        child: Padding(
          padding: const EdgeInsets.all(8.0),
          child: ListView(
            children: [
              ClassCard(
                title: 'برنامه‌سازی پیشرفته',
                teacher: 'دکتر وحیدی',
                unitCount: 3,
                remainingAssignments: 4,
                topStudent: 'علی علوی',
                color: Colors.purple,
              ),
              ClassCard(
                title: 'معماری کامپیوتر',
                teacher: 'دکتر مهدیانی',
                unitCount: 3,
                remainingAssignments: 4,
                topStudent: 'علی علوی',
                color: Colors.red,
              ),
              ClassCard(
                title: 'ساختمان داده',
                teacher: 'دکتر علیدوست',
                unitCount: 3,
                remainingAssignments: 4,
                topStudent: 'علی علوی',
                color: Colors.green,
              ),
            ],
          ),
        ),
      ),
      floatingActionButton: FloatingActionButton(
        onPressed: () {
          _navigateToClassListScreen(context); // Navigate to ClassListScreen
        },
        child: Icon(Icons.add),
        tooltip: 'افزودن کلاس',
      ),
    );
  }

  void _navigateToClassListScreen(BuildContext context) {
    Navigator.push(
      context,
      MaterialPageRoute(builder: (context) => ClassListScreen()),
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
  runApp(MaterialApp(
    home: ClassesPage(),
  ));
}