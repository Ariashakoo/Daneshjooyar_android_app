import 'package:flutter/material.dart';
import 'package:image_picker/image_picker.dart';
import 'dart:io';
import 'dart:convert';
import 'dart:async';
import 'dart:typed_data';
import 'sara.dart'; // Add this line to import the SaraPage class

class UserPage extends StatefulWidget {
  final TextEditingController firstNameController;
  final TextEditingController lastNameController;
  final TextEditingController idController;
  final TextEditingController emailController;

  const UserPage({
    required this.firstNameController,
    required this.lastNameController,
    required this.idController,
    required this.emailController,
  });

  @override
  _UserPageState createState() => _UserPageState();
}

class _UserPageState extends State<UserPage> {
  File? _image;
  String _grade = "Loading...";
  final ImagePicker _picker = ImagePicker();

  @override
  void initState() {
    super.initState();
    _retrieveGrade(); // Retrieve the grade from the server when the Widget initializes
  }

  Future<void> _retrieveGrade() async {
    const String serverAddress = '127.0.0.1'; // Replace with your server's IP address
    const int serverPort = 12345; // Replace with your server's port
    Socket? socket;

    try {
      socket = await Socket.connect(serverAddress, serverPort);
      print('Connected to: ${socket.remoteAddress.address}:${socket.remotePort}');

      socket.write('user\n');

      // Listen for the response from the server
      socket.listen((Uint8List data) {
        final String serverResponse = String.fromCharCodes(data);
        setState(() {
          _grade = serverResponse;
        });
        socket?.destroy();
      });
    } catch (e) {
      print('Error: $e');
      socket?.destroy();
    }
  }

  void _showAdminMessage() {
    showDialog(
      context: context,
      builder: (context) {
        return AlertDialog(
          content: const Text("Only admin has the right!"),
          actions: [
            TextButton(
              onPressed: () => Navigator.of(context).pop(),
              child: const Text("OK"),
            ),
          ],
        );
      },
    );
  }

  Future<void> _changePassword(String newPassword) async {
    const String serverAddress = '172.20.110.1'; // Replace with your server's IP address
    const int serverPort = 12345; // Replace with your server's port
    Socket? socket;

    try {
      socket = await Socket.connect(serverAddress, serverPort);
      print('Connected to: ${socket.remoteAddress.address}:${socket.remotePort}');

      // Send the change password command along with the username and new password
      String command = 'change_password~user_1~$newPassword\n';
      socket.write(command);

      // Listen for the response from the server
      socket.listen((Uint8List data) {
        final String serverResponse = String.fromCharCodes(data);
        print('Server response: $serverResponse');
        socket?.destroy();
      });
    } catch (e) {
      print('Error: $e');
      socket?.destroy();
    }
  }

  void _showPasswordPanel() {
    TextEditingController passwordController = TextEditingController();
    showDialog(
      context: context,
      builder: (context) {
        return AlertDialog(
          title: const Text('Change Password'),
          content: TextField(
            controller: passwordController,
            obscureText: true,
            decoration: const InputDecoration(hintText: "Enter new password"),
          ),
          actions: [
            TextButton(
              onPressed: () {
                // Send new password to the server
                _changePassword(passwordController.text);
                Navigator.of(context).pop();
              },
              child: const Text("Submit"),
            ),
            TextButton(
              onPressed: () => Navigator.of(context).pop(),
              child: const Text("Cancel"),
            ),
          ],
        );
      },
    );
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: const Text('User Page'),
      ),
      body: Container(
        decoration: const BoxDecoration(
          image: DecorationImage(
            image: AssetImage('assets/images/back.jpg'), // Make sure to add your background image asset in your project
            fit: BoxFit.cover,
          ),
        ),
        child: Center(
          child: Column(
            mainAxisAlignment: MainAxisAlignment.center,
            children: [
              Container(
                width: 300,
                padding: const EdgeInsets.all(16),
                margin: const EdgeInsets.only(bottom: 32),
                decoration: BoxDecoration(
                  color: Colors.white,
                  border: Border.all(width: 1, color: Colors.grey),
                  borderRadius: const BorderRadius.all(Radius.circular(10)),
                  boxShadow: [
                    BoxShadow(
                      color: Colors.grey.withOpacity(0.5),
                      spreadRadius: 2,
                      blurRadius: 7,
                      offset: const Offset(0, 3),
                    ),
                  ],
                ),
                child: Column(
                  children: [
                    Stack(
                      children: [
                        Container(
                          width: 100,
                          height: 100,
                          decoration: BoxDecoration(
                            shape: BoxShape.circle,
                            border: Border.all(width: 2, color: Colors.grey),
                          ),
                          child: _image != null
                              ? CircleAvatar(
                            radius: 50,
                            backgroundImage: Image.file(_image!).image,
                          )
                              : const Icon(Icons.person, size: 50),
                        ),
                        Positioned(
                          top: 0,
                          right: 0,
                          child: CircleAvatar(
                            radius: 15,
                            backgroundColor: Colors.blue,
                            child: IconButton(
                              icon: const Icon(Icons.edit, size: 15, color: Colors.white),
                              onPressed: () async {
                                final XFile? image = await _picker.pickImage(source: ImageSource.gallery);
                                setState(() {
                                  if (image != null) {
                                    _image = File(image.path);
                                  } else {
                                    _image = null;
                                  }
                                });
                              },
                            ),
                          ),
                        ),
                      ],
                    ),
                    const SizedBox(height: 16),
                    Text(
                      '${widget.firstNameController.text} ${widget.lastNameController.text}',
                      style: const TextStyle(fontSize: 18, fontWeight: FontWeight.bold),
                    ),
                  ],
                ),
              ),
              Container(
                width: 300,
                padding: const EdgeInsets.all(16),
                decoration: BoxDecoration(
                  color: Colors.white,
                  border: Border.all(width: 1, color: Colors.grey),
                  borderRadius: const BorderRadius.all(Radius.circular(10)),
                  boxShadow: [
                    BoxShadow(
                      color: Colors.grey.withOpacity(0.5),
                      spreadRadius: 2,
                      blurRadius: 7,
                      offset: const Offset(0, 3),
                    ),
                  ],
                ),
                child: Column(
                  children: [
                    const SizedBox(height: 8),
                    Text(
                      'ID: ${widget.idController.text}',
                      style: const TextStyle(fontSize: 18),
                    ),
                    const SizedBox(height: 8),
                    const Text(
                      'ترم بهار 1402 1403',
                      style: TextStyle(fontSize: 18),
                    ),
                    const SizedBox(height: 8),
                    Text(
                      _grade,
                      style: const TextStyle(fontSize: 18),
                    ),
                  ],
                ),
              ),
              const SizedBox(height: 32),
              ElevatedButton(
                onPressed: () {
                  Navigator.push(
                    context,
                    MaterialPageRoute(builder: (context) => SaraPage()), // Make sure `SaraPage` is defined in `sara.dart`
                  );
                },
                child: const Text('Go to Sara Page'),
              ),
              const SizedBox(height: 16),
              ElevatedButton(
                onPressed: _showAdminMessage,
                child: const Text('Change Info'),
              ),
              const SizedBox(height: 16),
              ElevatedButton(
                onPressed: _showPasswordPanel,
                child: const Text('Change Password'),
              ),
            ],
          ),
        ),
      ),
    );
  }
}