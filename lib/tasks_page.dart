import 'package:flutter/material.dart';
import 'dart:io';
import 'dart:async';
import 'package:file_picker/file_picker.dart';

class AssignmentsPage extends StatefulWidget {
  @override
  _AssignmentsPageState createState() => _AssignmentsPageState();
}

class _AssignmentsPageState extends State<AssignmentsPage> {
  final List<Assignment> assignments = [];
  bool isLoading = true;

  @override
  void initState() {
    super.initState();
    fetchAssignments();
  }

  void fetchAssignments() async {
    try {
      final Socket socket = await Socket.connect('192.168.8.100', 12345);
      print('Connected to the server');
      socket.writeln('tamrin');

      List<String> serverResponse = [];
      Completer<void> completer = Completer<void>();

      socket.listen(
            (List<int> event) {
          final responseString = String.fromCharCodes(event).trim();
          print('Received: $responseString'); // Debug statement
          if (responseString == "END_ASSIGNMENTS") {
            socket.close();
            completer.complete();
          } else {
            serverResponse.add(responseString);
          }
        },
        onError: (error) {
          print('Error: $error');
          setState(() {
            isLoading = false;
          });
          completer.completeError(error);
          socket.close();
        },
        onDone: () {
          print('Connection closed by the server');
          socket.close();
        },
      );

      await completer.future;
      parseAssignments(serverResponse);
    } catch (e) {
      setState(() {
        isLoading = false;
      });
      print("Failed to connect to the server: $e");
    }
  }

  void parseAssignments(List<String> response) {
    setState(() {
      for (var line in response) {
        if (line.isNotEmpty) {
          final parts = line.split('~');
          if (parts.length == 4) {
            bool isCompleted = parts[1].trim().toLowerCase() == 'true';
            assignments.add(
              Assignment(
                parts[2], // Title
                DateTime.now(), // Using current date, modify as needed
                'Time: ${parts[0]}', // Time remaining formatted as needed
                isCompleted: isCompleted,
                description: parts[3], // Description
              ),
            );
          }
        }
      }
      isLoading = false;
    });
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: Text('Assignments'),
      ),
      body: isLoading
          ? Center(child: CircularProgressIndicator())
          : ListView.builder(
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
            onCheckboxChanged: (bool? isChecked) {
              setState(() {
                assignments[index].isCompleted = isChecked ?? false;
              });
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
  final String description;
  bool isCompleted;
  File? file;

  Assignment(this.title, this.dueDate, this.time,
      {this.isCompleted = false, this.file, required this.description});
}

class AssignmentCard extends StatelessWidget {
  final Assignment assignment;
  final VoidCallback onTap;
  final ValueChanged<bool?> onCheckboxChanged;

  const AssignmentCard(
      {required this.assignment, required this.onTap, required this.onCheckboxChanged});

  @override
  Widget build(BuildContext context) {
    return Opacity(
      opacity: assignment.isCompleted ? 0.5 : 1.0,
      child: Card(
        margin: EdgeInsets.symmetric(vertical: 8, horizontal: 16),
        child: ListTile(
          leading: Checkbox(
            value: assignment.isCompleted,
            onChanged: onCheckboxChanged,
          ),
          title: Text(assignment.title),
          subtitle: Text('${assignment.time}\n${assignment.description}'),
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
  void _pickFile() async {
    FilePickerResult? result = await FilePicker.platform.pickFiles(
      type: FileType.custom,
      allowedExtensions: ['pdf'],
    );
    if (result != null) {
      setState(() {
        widget.assignment.file = File(result.files.single.path!);
      });
    }
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: Text('Assignment Details'),
      ),
      body: Padding(
        padding: EdgeInsets.all(16.0),
        child: SingleChildScrollView(
          child: Column(
            crossAxisAlignment: CrossAxisAlignment.start,
            children: [
              Text('Title: ${widget.assignment.title}', style: TextStyle(fontSize: 20)),
              SizedBox(height: 10),
              Text('Time Remaining: ${widget.assignment.time}'),
              SizedBox(height: 10),
              Text('Description: ${widget.assignment.description}'),
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
                onPressed: _pickFile,
                child: Text('Upload PDF'),
              ),
              if (widget.assignment.file != null)
                Padding(
                  padding: const EdgeInsets.only(top: 8.0),
                  child: Text('File: ${widget.assignment.file!.path.split('/').last}'),
                ),
              SizedBox(height: 10),
              ElevatedButton(
                onPressed: () {},
                child: Text('Submit'),
              ),
            ],
          ),
        ),
      ),
    );
  }
}
