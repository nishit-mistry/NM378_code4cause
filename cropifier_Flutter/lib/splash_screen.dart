import 'dart:async';
import 'package:flutter/material.dart';
import 'main.dart';

class splash_screen extends StatefulWidget {
  @override
  State<StatefulWidget> createState() {
    return _splash_screenState();
  }
}

class _splash_screenState extends State<splash_screen> {
  @override
  void initState() {
    super.initState();
    Timer(
        Duration(seconds: 2),
            () => Navigator.pushReplacement(
            context, MaterialPageRoute(builder: (context) => Home())));
  }

  @override
  Widget build(BuildContext context) {
    return Container(
      child: Image.asset(
        'assets/splash_screen.png',
        height: MediaQuery.of(context).size.height,
        width: MediaQuery.of(context).size.width,
      ),
    );
  }
}
