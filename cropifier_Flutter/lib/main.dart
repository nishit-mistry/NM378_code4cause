import 'package:curved_navigation_bar/curved_navigation_bar.dart';
import 'package:flutter/material.dart';
import 'package:flutter/services.dart';
import 'package:cropifier_start/camera_page.dart';
import 'package:cropifier_start/crops.dart';
import 'package:cropifier_start/home_page.dart';
import 'package:cropifier_start/google_map.dart';
import 'package:cropifier_start/splash_screen.dart';

void main() => runApp(MyApp());

class MyApp extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    SystemChrome.setPreferredOrientations([
      DeviceOrientation.portraitUp,
      DeviceOrientation.portraitDown,
    ]);
    return MaterialApp(
      debugShowCheckedModeBanner: false,
      title: 'Cropifier Flutter',
      theme: ThemeData(
        primarySwatch: Colors.green,
        visualDensity: VisualDensity.adaptivePlatformDensity,
      ),
      home: splash_screen(),
    );
  }
}

class Home extends StatefulWidget {
  @override
  State<StatefulWidget> createState() {
    return _HomeState();
  }
}

class _HomeState extends State<Home> {
  int _currentIndex = 0;
  final List<Widget> _children = [home_page(), MyMap(), camera_page(), crops()];

  void onTabTapped(int index) {
    setState(() {
      _currentIndex = index;
    });
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      body: _children[_currentIndex],
      bottomNavigationBar: CurvedNavigationBar(
        height: 60,
        color: Colors.green,
        backgroundColor: Colors.white,
        buttonBackgroundColor: Colors.white,
        onTap: onTabTapped,
        index: _currentIndex,
        items: <Widget>[
          Icon(Icons.home, size: 25, color: Colors.black),
          Icon(Icons.explore, size: 25, color: Colors.black),
          Icon(Icons.camera, size: 25, color: Colors.black),
          Icon(Icons.info, size: 25, color: Colors.black)
        ],
        animationDuration: Duration(milliseconds: 300),
      ),
    );
  }
}
