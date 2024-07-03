import 'package:flutter/material.dart';
import 'package:image_picker/image_picker.dart';
import 'dart:io';
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

  final ImagePicker _picker = ImagePicker();

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: Text('User Page'),
      ),
      body: Center(
        child: Column(
          mainAxisAlignment: MainAxisAlignment.center,
          children: [
            Stack(
              children: [
                Container(
                  width: 200,
                  height: 200,
                  decoration: BoxDecoration(
                    shape: BoxShape.circle,
                    border: Border.all(width: 2, color: Colors.grey),
                  ),
                  child: _image!= null
                      ? CircleAvatar(
                    radius: 100,
                    backgroundImage: Image.file(_image!).image,
                  )
                      : Icon(Icons.person, size: 100),
                ),
                Positioned(
                  top: 0,
                  right: 0,
                  child: CircleAvatar(
                    radius: 15,
                    backgroundColor: Colors.blue,
                    child: IconButton(
                      icon: Icon(Icons.edit, size: 15, color: Colors.white),
                      onPressed: () async {
                        final XFile? image = await _picker.pickImage(source: ImageSource.gallery);
                        setState(() {
                          if (image!= null) {
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
            SizedBox(height: 16),
            Container(
              width: 300,
              padding: EdgeInsets.all(16),
              decoration: BoxDecoration(
                color: Colors.white, // Add this line
                border: Border.all(width: 1, color: Colors.grey),
                borderRadius: BorderRadius.all(Radius.circular(10)),
                boxShadow: [ // Add this line
                  BoxShadow(
                    color: Colors.grey.withOpacity(0.5),
                    spreadRadius: 2,
                    blurRadius: 7,
                    offset: Offset(0, 3), // changes position of shadow
                  ),
                ],
              ),
              child: Column(
                children: [
                  Text(
                    'Name: ${widget.firstNameController.text} ${widget.lastNameController.text}',
                    style: TextStyle(fontSize: 18),
                  ),
                  SizedBox(height: 8),
                  Text(
                    'ID: ${widget.idController.text}',
                    style: TextStyle(fontSize: 18),
                  ),
                  SizedBox(height: 8),
                  Text(
                    'Email: ${widget.emailController.text}',
                    style: TextStyle(fontSize: 18),
                  ),
                ],
              ),
            ),
            SizedBox(height: 32),
            ElevatedButton(
              onPressed: () {
                Navigator.push(
                  context,
                  MaterialPageRoute(builder: (context) => SaraPage()), // Use the correct class name
                );
              },
              child: Text('Go to Sara Page'),
            ),
          ],
        ),
      ),
    );
  }
}