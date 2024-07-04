import 'package:flutter/material.dart';
import 'package:intl/intl.dart';

class TaskModel {
  final String id;
  final String title;
  final DateTime deadline;

  TaskModel({required this.id, required this.title, required this.deadline});
}

class Kara extends StatefulWidget {
  @override
  _KaraState createState() => _KaraState();
}

class _KaraState extends State<Kara> {
  final List<TaskModel> _tasks = [];
  final TextEditingController _taskTextEditingController = TextEditingController();
  int _currentIndex = 0;
  DateTime _selectedDate = DateTime.now();
  TimeOfDay _selectedTime = TimeOfDay(hour: 0, minute: 0);

  void _createTask(TaskModel task) {
    setState(() {
      _tasks.add(task);
    });
  }

  void _removeTask(String taskId) {
    setState(() {
      _tasks.removeWhere((task) => task.id == taskId);
    });
  }

  void onTabTapped(int index) {
    setState(() {
      _currentIndex = index;
    });
  }

  Future<void> _selectDate(BuildContext context) async {
    final DateTime? picked = await showDatePicker(
      context: context,
      initialDate: _selectedDate,
      firstDate: DateTime(2015),
      lastDate: DateTime(2101),
    );
    if (picked!= null && picked!= _selectedDate) {
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
    if (picked!= null && picked!= _selectedTime) {
      setState(() {
        _selectedTime = picked;
      });
    }
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: Text('My To-Do List'),
        centerTitle: true,
      ),
      body: Column(
        children: [
          // Widget for the task list
          ListView.builder(
            shrinkWrap: true,
            itemCount: _tasks.length,
            itemBuilder: (context, index) {
              return ListTile(
                title: Text(_tasks[index].title),
                subtitle: Text(DateFormat.yMMMd().add_Hms().format(_tasks[index].deadline)),
                trailing: IconButton(
                  icon: Icon(Icons.delete),
                  onPressed: () {
                    _removeTask(_tasks[index].id);
                  },
                ),
              );
            },
          ),
        ],
      ),
      floatingActionButton: FloatingActionButton(
        child: Icon(Icons.add),
        onPressed: () {
          showDialog(
            context: context,
            builder: (context) {
              return AlertDialog(
                title: Text('New Task'),
                content: Column(
                  mainAxisSize: MainAxisSize.min,
                  children: [
                    TextField(
                      controller: _taskTextEditingController,
                      decoration: InputDecoration(
                        labelText: 'Describe your task',
                      ),
                      maxLines: 2,
                    ),
                    SizedBox(height: 10),
                    ElevatedButton(
                      onPressed: () => _selectDate(context),
                      child: Text('Select Date'),
                    ),
                    Text(DateFormat.yMMMd().format(_selectedDate)),
                    SizedBox(height: 10),
                    ElevatedButton(
                      onPressed: () => _selectTime(context),
                      child: Text('Select Time'),
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
                      if (_taskTextEditingController.text.isNotEmpty) {
                        final TaskModel newTask = TaskModel(
                          id: DateTime.now().toString(),
                          title: _taskTextEditingController.text,
                          deadline: DateTime(
                            _selectedDate.year,
                            _selectedDate.month,
                            _selectedDate.day,
                            _selectedTime.hour,
                            _selectedTime.minute,
                          ),
                        );
                        _createTask(newTask);
                        _taskTextEditingController.clear();
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
      bottomNavigationBar: BottomNavigationBar(
        onTap: onTabTapped,
        currentIndex: _currentIndex,
        items: [
          BottomNavigationBarItem(
            icon: Icon(Icons.home),
            label: 'Home',
          ),
          BottomNavigationBarItem(
            icon: Icon(Icons.settings),
            label: 'Settings',
          ),
        ],
      ),
    );
  }
}