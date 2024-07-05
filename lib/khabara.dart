import 'package:flutter/material.dart';
// import 'package:webview_flutter/webview_flutter.dart';  // I don't know why it gives an error

class NewsPage extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return Scaffold(
      body: Column(
        children: [
          Container(
            margin: EdgeInsets.all(8.0),
            child: Row(
              mainAxisAlignment: MainAxisAlignment.spaceAround,
              children: [
                Chip(label: Text('اخبار')),
                Chip(label: Text('رویدادها')),
                Chip(label: Text('یادآوری‌ها')),
                Chip(label: Text('تولدهای امروز')),
                Chip(label: Text('تمدیدها')),
              ],
            ),
          ),
          Container(
            margin: EdgeInsets.all(8.0),
            child: Text(
              'امروز (۱۶ اردیبهشت)',
              style: TextStyle(fontSize: 16, fontWeight: FontWeight.bold),
            ),
          ),
          Expanded(
            child: ListView(
              children: [
                NewsCard(
                  title: 'اطلاعیه آموزشی',
                  description: 'قابل توجه دانشجویان دکترا ورودی ۹۸ امکان حذف یک نیمسال بدون احتساب در سنوات...',
                  imageUrl: 'https://example.com/image1.jpg',
                ),
                NewsCard(
                  title: 'اطلاعیه آموزشی',
                  description: 'قابل توجه دانشجویان دکترا ورودی ۹۸ امکان حذف یک نیمسال بدون احتساب در سنوات...',
                  imageUrl: 'https://example.com/image2.jpg',
                ),
              ],
            ),
          ),
        ],
      ),
    );
  }
}

class NewsCard extends StatelessWidget {
  final String title;
  final String description;
  final String imageUrl;

  NewsCard({required this.title, required this.description, required this.imageUrl});

  @override
  Widget build(BuildContext context) {
    return Card(
      margin: EdgeInsets.all(8.0),
      child: Column(
        children: [
          Image.network(imageUrl),
          Padding(
            padding: const EdgeInsets.all(8.0),
            child: Text(
              title,
              style: TextStyle(fontSize: 18, fontWeight: FontWeight.bold),
            ),
          ),
          Padding(
            padding: const EdgeInsets.all(8.0),
            child: Text(description),
          ),
          Padding(
            padding: const EdgeInsets.all(8.0),
            child: TextButton(
              onPressed: () {
                // Navigate to detailed news page
              },
              child: Text('مطالعه بیشتر...'),
            ),
          ),
        ],
      ),
    );
  }
}