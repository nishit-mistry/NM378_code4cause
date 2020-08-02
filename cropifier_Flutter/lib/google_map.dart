import 'package:flutter/material.dart';
import 'package:flutter/cupertino.dart';

class MyMap extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return new Scaffold(
      appBar: AppBar(
        title: Text(
          "Google Map Page",
          style: TextStyle(color: Colors.black, fontWeight: FontWeight.bold),
        ),
        flexibleSpace: Container(
          decoration: BoxDecoration(
              gradient: LinearGradient(
                  begin: Alignment.topLeft,
                  end: Alignment.bottomRight,
                  colors: <Color>[Color(0xffeebeba), Color(0xfffad669)])),
        ),
      ),
      body: new Text("This is the Google Map Page"),
    );
  }
}
