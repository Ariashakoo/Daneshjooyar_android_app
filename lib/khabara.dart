import 'package:flutter/material.dart';
import 'package:url_launcher/url_launcher.dart';

class NewsPage extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return Scaffold(
      body: Container(
        decoration: BoxDecoration(
          image: DecorationImage(
            image: AssetImage('assets/images/back.jpg'), // replace with your background image
            fit: BoxFit.cover,
          ),
        ),
        child: Column(
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
                    description: 'اخبار دانشگاه بهشتی',
                    imageUrl: 'assets/images/uni.jpg', // Valid URL #1
                    url: 'https://news.sbu.ac.ir',  // Add URL for the first news
                  ),
                  NewsCard(
                    title: 'اطلاعیه آموزشی',
                    description: 'اخبار جدید دانشجویی',
                    imageUrl: 'assets/images/uni2.jpg', // Valid URL #2
                    url: 'https://www.mehrnews.com',  // Add URL for the second news
                  ),
                ],
              ),
            ),
          ],
        ),
      ),
    );
  }
}

class NewsCard extends StatelessWidget {
  final String title;
  final String description;
  final String imageUrl;
  final String url;

  NewsCard({required this.title, required this.description, required this.imageUrl, required this.url});

  Future<void> _launchURL(String url) async {
    if (await canLaunch(url)) {
      await launch(url);
    } else {
      throw 'Could not launch $url';
    }
  }

  @override
  Widget build(BuildContext context) {
    return Card(
      margin: EdgeInsets.all(8.0),
      child: Column(
        children: [
          Image.asset(imageUrl), // Use Image.asset for local images
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
              onPressed: () => _launchURL(url), // Open the URL when pressed
              child: Text('مطالعه بیشتر...'),
            ),
          ),
        ],
      ),
    );
  }
}