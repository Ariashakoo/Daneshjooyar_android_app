
import 'package:flutter/material.dart';
import 'package:untitled4/login.dart';
import 'package:untitled4/signup.dart';

void main() {
  runApp(const MyApp());
}

class MyApp extends StatelessWidget {
  const MyApp({super.key});

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      debugShowCheckedModeBanner: false ,
      title: 'Daneshjooyar ',
      theme: ThemeData(
        primarySwatch: Colors.blue,
      ),
      home: const MyHomePage(),
    );
  }
}

class MyHomePage extends StatelessWidget {
  const MyHomePage({super.key});

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: Text('Home page',
          style : TextStyle(  fontSize: 30,),),
      ),
      body: Container( // Add a Container for the background
        decoration: BoxDecoration( // Add a BoxDecoration
          image: DecorationImage( // Add a DecorationImage
            image: AssetImage('assets/images/Back4.jpeg'), // Replace with your background image
            fit: BoxFit.cover,
          ),
        ),
        child: Center(
          child: Column(
            mainAxisAlignment: MainAxisAlignment.center,
            children: [
              Text('Welcome to Daneshjooyar ',
                style : TextStyle(  fontSize: 42,color: Colors.white,),),
              Container( // Wrap the ElevatedButton in a Container
                height: 50, // Specify the height
                width: 200, // Specify the width
                child: ElevatedButton(
                  style: ElevatedButton.styleFrom( // Use the styleFrom method
                    backgroundColor: Colors.green, // Button background color
                    foregroundColor: Colors.white, // Button text color
                    padding:  EdgeInsets.all(20), // Increase the padding
                    textStyle:  TextStyle(fontSize: 20),
                  ),
                  onPressed: () {
                    Navigator.push(
                      context,
                      MaterialPageRoute(builder: (context) =>  LoginPage()),
                    );
                  },
                  child: const Text('Login'),
                ),
              ),
              Container( // Wrap the ElevatedButton in a Container
                height: 50, // Specify the height
                width: 200, // Specify the width
                child: ElevatedButton(
                  style: ElevatedButton.styleFrom( // Use the styleFrom method
                    backgroundColor: Colors.green, // Button background color
                    foregroundColor: Colors.white, // Button text color
                    padding:  EdgeInsets.all(20), // Increase the padding
                    textStyle:  TextStyle(fontSize: 20),
                  ),
                  onPressed: () {
                    Navigator.push(
                      context,
                      MaterialPageRoute(builder: (context) => SignUpPage()),
                    );
                  },
                  child: const Text('Sign Up'),
                ),
              ),
            ],
          ),
        ),
      ),
    );
  }
}
