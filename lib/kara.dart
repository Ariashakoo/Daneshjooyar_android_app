import 'package:flutter/material.dart';
import 'package:intl/intl.dart';

class TaskModel {
  final String id;
  final String title;
  final DateTime deadline;
  DateTime? reminder;
  bool isDone;

  TaskModel({required this.id, required this.title, required this.deadline, this.reminder, this.isDone = false});
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
  DateTime? _selectedReminder;

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

  void _toggleTaskStatus(TaskModel task) {
    setState(() {
      task.isDone = !task.isDone;
    });
  }

  Future<void> _selectDate(BuildContext context, {bool isDeadline = true}) async {
    final DateTime? picked = await showDatePicker(
      context: context,
      initialDate: isDeadline ? _selectedDate : DateTime.now(),
      firstDate: DateTime(2015),
      lastDate: DateTime(2101),
    );
    if (picked != null) {
      setState(() {
        if (isDeadline) {
          _selectedDate = picked;
        } else {
          _selectedReminder = picked;
        }
      });
    }
  }

  Future<void> _selectTime(BuildContext context, {bool isDeadline = true}) async {
    final TimeOfDay? picked = await showTimePicker(
      context: context,
      initialTime: isDeadline ? _selectedTime : TimeOfDay(hour: 12, minute: 0),
    );
    if (picked != null) {
      setState(() {
        if (isDeadline) {
          _selectedTime = picked;
        } else {
          _selectedReminder = DateTime(
            _selectedReminder!.year,
            _selectedReminder!.month,
            _selectedReminder!.day,
            picked.hour,
            picked.minute,
          );
        }
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
      body: Container(
        decoration: BoxDecoration(
          image: DecorationImage(
            image: AssetImage('assets/images/back.jpg'), // replace with your image
            fit: BoxFit.cover,
          ),
        ),
        child: ListView.builder(
          itemCount: _tasks.length,
          itemBuilder: (context, index) {
            return ListTile(
              title: Text(
                _tasks[index].title,
                style: TextStyle(
                  color: _tasks[index].isDone ? Colors.green : Colors.red,
                ),
              ),
              subtitle: Text(
                'Deadline: ${DateFormat.yMMMd().add_Hms().format(_tasks[index].deadline)}'
                    '${_tasks[index].reminder != null ? '\nReminder: ${DateFormat.yMMMd().add_Hms().format(_tasks[index].reminder!)}' : ''}',
              ),
              trailing: IconButton(
                icon: Icon(Icons.delete),
                onPressed: () {
                  _removeTask(_tasks[index].id);
                },
              ),
              onTap: () {
                _toggleTaskStatus(_tasks[index]);
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
                      child: Text('Select Deadline Date'),
                    ),
                    Text(DateFormat.yMMMd().format(_selectedDate)),
                    SizedBox(height: 10),
                    ElevatedButton(
                      onPressed: () => _selectTime(context),
                      child: Text('Select Deadline Time'),
                    ),
                    Text(_selectedTime.format(context)),
                    SizedBox(height: 10),
                    ElevatedButton(
                      onPressed: () => _selectDate(context, isDeadline: false),
                      child: Text('Select Reminder Date'),
                    ),
                    if (_selectedReminder != null) Text(DateFormat.yMMMd().format(_selectedReminder!)),
                    SizedBox(height: 10),
                    ElevatedButton(
                      onPressed: () => _selectTime(context, isDeadline: false),
                      child: Text('Select Reminder Time'),
                    ),
                    if (_selectedReminder != null) Text(DateFormat.yMMMd().add_Hms().format(_selectedReminder!)),
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
                          reminder: _selectedReminder,
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
    );
  }
}