import 'package:flutter/material.dart';
import 'package:flutter/services.dart';
import 'user.dart';
import 'dart:io';
import 'dart:convert';

class LoginPage extends StatefulWidget {
  @override
  _LoginPageState createState() => _LoginPageState();
}

class _LoginPageState extends State<LoginPage> {
  final _formKey = GlobalKey<FormState>();
  TextEditingController firstNameController = TextEditingController();
  TextEditingController lastNameController = TextEditingController();
  TextEditingController idController = TextEditingController();
  TextEditingController emailController = TextEditingController();
  TextEditingController passwordController = TextEditingController();

  bool _isLoggedIn = false;

  @override
  Widget build(BuildContext context) {
    return Container(
      decoration: BoxDecoration(
        image: DecorationImage(
          image: AssetImage('assets/images/backl.jpg'), // replace with your image
          fit: BoxFit.cover,
        ),
      ),
      child: Scaffold(
        appBar: AppBar(
          title: const Text('Login'),
          backgroundColor: Colors.transparent, // make the app bar transparent
        ),
        backgroundColor: Colors.transparent, // make the scaffold background transparent
        body: _isLoggedIn
            ? UserPage(
          firstNameController: firstNameController,
          lastNameController: lastNameController,
          idController: idController,
          emailController: emailController,
        )
            : SingleChildScrollView(
          padding: const EdgeInsets.symmetric(horizontal: 16, vertical: 32),
          child: Form(
            key: _formKey,
            child: Column(
              crossAxisAlignment: CrossAxisAlignment.stretch,
              children: [
                TextFormField(
                  controller: firstNameController,
                  decoration: InputDecoration(
                    border: OutlineInputBorder(),
                    labelText: 'First Name',
                    hintText: 'Enter your first name',
                    filled: true,
                    fillColor: Colors.white,
                  ),
                  validator: (value) {
                    if (value == null || value.isEmpty) {
                      return 'Please enter your first name';
                    }
                    return null;
                  },
                ),
                const SizedBox(height: 16),
                TextFormField(
                  controller: lastNameController,
                  decoration: InputDecoration(
                    border: OutlineInputBorder(),
                    labelText: 'Last Name',
                    hintText: 'Enter your last name',
                    filled: true,
                    fillColor: Colors.white,
                  ),
                  validator: (value) {
                    if (value == null || value.isEmpty) {
                      return 'Please enter your last name';
                    }
                    return null;
                  },
                ),
                const SizedBox(height: 16),
                TextFormField(
                  controller: idController,
                  inputFormatters: [
                    FilteringTextInputFormatter.digitsOnly,
                    LengthLimitingTextInputFormatter(10),
                  ],
                  decoration: InputDecoration(
                    border: OutlineInputBorder(),
                    labelText: 'ID',
                    hintText: 'Enter your ID',
                    filled: true,
                    fillColor: Colors.white,
                  ),
                  validator: (value) {
                    if (value == null || value.isEmpty) {
                      return 'Please enter your ID';
                    }
                    if (value.length != 10) {
                      return 'ID must be exactly 10 characters long';
                    }
                    return null;
                  },
                ),
                const SizedBox(height: 16),
                TextFormField(
                  controller: emailController,
                  decoration: InputDecoration(
                    border: OutlineInputBorder(),
                    labelText: 'Email',
                    hintText: 'Enter valid mail id as abc@gmail.com',
                    filled: true,
                    fillColor: Colors.white,
                  ),
                  validator: (value) {
                    if (value == null || value.isEmpty) {
                      return 'Please enter your email';
                    }
                    final emailRegex = RegExp(r'^[\w-]+(\.[\w-]+)*@([\w-]+\.)+[a-zA-Z]{2,7}$');
                    if (!emailRegex.hasMatch(value)) {
                      return 'Please enter a valid email address';
                    }
                    return null;
                  },
                ),
                const SizedBox(height: 16),
                TextFormField(
                  controller: passwordController,
                  obscureText: true,
                  decoration: InputDecoration(
                    border: OutlineInputBorder(),
                    labelText: 'Password',
                    hintText: 'Enter your secure password',
                    filled: true,
                    fillColor: Colors.white,
                  ),
                  validator: (value) {
                    if (value == null || value.isEmpty) {
                      return 'Please enter your password';
                    }
                    return null;
                  },
                ),
                const SizedBox(height: 16),
                ElevatedButton(
                  onPressed: () async {
                    if (_formKey.currentState!.validate()) {
                      try {
                        // Establish a socket connection
                        Socket socket = await Socket.connect('192.168.1.100', 8088);

                        // Create a map to store the data
                        Map<String, String> data = {
                          'firstName': firstNameController.text,
                          'lastName': lastNameController.text,
                          'id': idController.text,
                          'email': emailController.text,
                          'password': passwordController.text,
                        };

                        // Convert the map to JSON
                        String jsonData = json.encode(data);

                        // Send the JSON data to the server
                        socket.write(jsonData);

                        // Wait for the response
                        String response = await utf8.decoder.bind(socket).first;

                        // Close the socket
                        await socket.close();

                        // Parse the response
                        Map<String, dynamic> responseData = json.decode(response);

                        // Check if the response data matches the form data
                        if (responseData['firstName'] == firstNameController.text &&
                            responseData['lastName'] == lastNameController.text &&
                            responseData['id'] == idController.text &&
                            responseData['email'] == emailController.text &&
                            responseData['password'] == passwordController.text) {
                          // Handle form submission
                          setState(() {
                            _isLoggedIn = true;
                          });
                          Navigator.pushReplacement(
                            context,
                            MaterialPageRoute(builder: (context) => UserPage(
                              firstNameController: firstNameController,
                              lastNameController: lastNameController,
                              idController: idController,
                              emailController: emailController,
                            )),
                          );
                        } else {
                          // Show an error
                          ScaffoldMessenger.of(context).showSnackBar(
                            const SnackBar(
                              content: Text('Error: Invalid login credentials'),
                              duration: Duration(seconds: 2),
                            ),
                          );
                        }
                      } catch (e) {
                        // Handle socket errors
                        ScaffoldMessenger.of(context).showSnackBar(
                          SnackBar(
                            content: Text('Error: $e'),
                            duration: Duration(seconds: 2),
                          ),
                        );
                      }
                    }
                  },
                  child: const Text('Login'),
                ),
              ],
            ),
          ),
        ),
      ),
    );
  }
}