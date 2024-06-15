import 'package:flutter/material.dart';

class SaraPage extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: Text('Sara Page'),
      ),
      body: Center(
        child: Text(
          'Welcome to Sara Page!',
          style: TextStyle(fontSize: 24),
        ),
      ),
    );
  }
}