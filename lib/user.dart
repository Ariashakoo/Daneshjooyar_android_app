import 'package:flutter/material.dart';
import 'sara.dart'; // Add this line to import the SaraPage class

class UserPage extends StatelessWidget {
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
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: Text('User Page'),
      ),
      body: Center(
        child: Column(
          mainAxisAlignment: MainAxisAlignment.center,
          children: [
            Text(
              'Name:',
              style: TextStyle(fontSize: 24),
            ),
            SizedBox(height: 8),
            Text(
              '${firstNameController.text} ${lastNameController.text}',
              style: TextStyle(fontSize: 24),
            ),
            SizedBox(height: 16),
            Text(
              'ID:',
              style: TextStyle(fontSize: 24),
            ),
            SizedBox(height: 8),
            Text(
              idController.text,
              style: TextStyle(fontSize: 24),
            ),
            SizedBox(height: 16),
            Text(
              'Email:',
              style: TextStyle(fontSize: 24),
            ),
            SizedBox(height: 8),
            Text(
              emailController.text,
              style: TextStyle(fontSize: 24),
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