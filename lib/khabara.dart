import 'package:flutter/material.dart';
import 'package:url_launcher/url_launcher.dart';
import 'package:intl/intl.dart';
import 'birthday.dart';  // Import the BirthdayPage

class NewsPage extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    // Get today's date
    String today = DateFormat('yyyy-MM-dd').format(DateTime.now());

    return Scaffold(
      body: Container(
        decoration: BoxDecoration(
          image: DecorationImage(
            image: AssetImage('assets/images/ae.jpg'), // replace with your background image
            fit: BoxFit.cover,
          ),
        ),
        child: Column(
          children: [
            Row(
              mainAxisAlignment: MainAxisAlignment.end,
              children: [
                IconButton(
                  icon: Icon(Icons.cake),
                  onPressed: () {
                    Navigator.push(
                      context,
                      MaterialPageRoute(builder: (context) => BirthdayHomePage()),
                    );
                  },
                  tooltip: 'Birthday Page',
                ),
              ],
            ),
            Container(
              margin: EdgeInsets.all(8.0),
              child: Text(
                'امروز ($today)',
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