import 'package:flip_card/flip_card.dart';
import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';

class home_page extends StatefulWidget {
  @override
  State<StatefulWidget> createState() {
    return _home_pageState();
  }
}

class _home_pageState extends State<home_page> {
  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: Text(
          "Cropifier",
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
      body: Container(
        margin: EdgeInsets.symmetric(vertical: 20.0),
        height: 600.0,
        alignment: Alignment.center,
        child: ListView(
          scrollDirection: Axis.horizontal,
          children: <Widget>[
            FlipCard(
              direction: FlipDirection.VERTICAL,
              front: Container(
                width: 310.0,
                child: Image.asset('assets/images/image1.png'),
              ),
              back: Container(
                alignment: Alignment.center,
                width: 300.0,
                padding: EdgeInsets.only(top: 25.0),
                child: Text(
                  "Click on the Camera Button from Navigation Bar, Click the Photo OR Select from Gallery, Press on Classify Image & Get Your Crop Identified.",
                  textAlign: TextAlign.center,
                  style: TextStyle(
                    fontSize: 18,
                    color: Colors.black,
                    fontWeight: FontWeight.bold,
                  ),
                ),
              ),
            ),
            FlipCard(
              direction: FlipDirection.VERTICAL,
              front: Container(
                width: 300.0,
                child: Image.asset('assets/images/image2.png'),
              ),
              back: Container(
                alignment: Alignment.center,
                width: 300.0,
                padding: EdgeInsets.only(top: 25.0),
                child: Text(
                  "Click on Map Button from Navigation Bar & Get the Country Crop View of multiple crops. Click on a particular crop to view the Location & Click on it again to view the Info.",
                  textAlign: TextAlign.center,
                  style: TextStyle(
                    fontSize: 18,
                    color: Colors.black,
                    fontWeight: FontWeight.bold,
                  ),
                ),
              ),
            ),
            FlipCard(
              direction: FlipDirection.VERTICAL,
              front: Container(
                width: 300.0,
                child: Image.asset('assets/images/image3.png'),
              ),
              back: Container(
                alignment: Alignment.center,
                width: 300.0,
                padding: EdgeInsets.only(top: 25.0),
                child: Text(
                  "Click on Crop Info Button from Navigation Bar & Select Any Card of a Particular Crop, and Share the Info on any Medium to Anyone.",
                  textAlign: TextAlign.center,
                  style: TextStyle(
                    fontSize: 18,
                    color: Colors.black,
                    fontWeight: FontWeight.bold,
                  ),
                ),
              ),
            ),
            FlipCard(
              direction: FlipDirection.VERTICAL,
              front: Container(
                width: 300.0,
                child: Image.asset('assets/images/image4.png'),
              ),
              back: Container(
                alignment: Alignment.center,
                width: 300.0,
                padding: EdgeInsets.only(top: 25.0),
                child: Text(
                  "Get all the Crop Information without wasting a pinch of Mobile Data from the Crop Info Button on Navigation Bar.",
                  textAlign: TextAlign.center,
                  style: TextStyle(
                    fontSize: 18,
                    color: Colors.black,
                    fontWeight: FontWeight.bold,
                  ),
                ),
              ),
            ),
            FlipCard(
              direction: FlipDirection.VERTICAL,
              front: Container(
                width: 300.0,
                child: Image.asset('assets/images/image5.png'),
              ),
              back: Container(
                alignment: Alignment.center,
                width: 300.0,
                padding: EdgeInsets.only(top: 25.0),
                child: Text(
                  "Get an immersive experience of our app on ANDROID Platform as well.",
                  textAlign: TextAlign.center,
                  style: TextStyle(
                    fontSize: 18,
                    color: Colors.black,
                    fontWeight: FontWeight.bold,
                  ),
                ),
              ),
            ),
          ],
        ),
      ),
    );
  }
}
