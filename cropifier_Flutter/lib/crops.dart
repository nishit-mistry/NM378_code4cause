import 'crops/bitter.dart';
import 'crops/chilly.dart';
import 'crops/coffee.dart';
import 'crops/cotton.dart';
import 'crops/lentil.dart';
import 'crops/maize.dart';
import 'crops/methi.dart';
import 'crops/mustard.dart';
import 'crops/pea.dart';
import 'crops/pumpkin.dart';
import 'crops/rice.dart';
import 'crops/sesame.dart';
import 'crops/sorghum.dart';
import 'crops/soyabean.dart';
import 'crops/sugarcane.dart';
import 'crops/tea.dart';
import 'crops/tobacco.dart';
import 'crops/turmeric.dart';
import 'crops/wheat.dart';
import 'package:flutter/material.dart';
import 'package:flutter/cupertino.dart';

class crops extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return new Scaffold(
      appBar: AppBar(
        title: Text(
          "Crop Information Page",
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
        padding: EdgeInsets.all(10),
        child: GridView(
          gridDelegate:
          SliverGridDelegateWithFixedCrossAxisCount(crossAxisCount: 2),
          children: <Widget>[
            GestureDetector(
              child: Card(
                elevation: 10,
                child: Container(
                  alignment: Alignment.bottomCenter,
                  decoration: BoxDecoration(
                    image: DecorationImage(
                      image: AssetImage("assets/images/bittergourd_img.jpg"),
                      fit: BoxFit.cover,
                    ),
                  ),
                  child: Text(
                    "Bitter Gourd",
                    style: TextStyle(
                        fontSize: 20,
                        color: Colors.black,
                        fontWeight: FontWeight.bold),
                  ),
                ),
              ),
              onTap: () {
                Navigator.of(context).push(
                  MaterialPageRoute(
                    builder: (context) => bitter("Bitter Gourd"),
                  ),
                );
              },
            ),
            GestureDetector(
              child: Card(
                elevation: 10,
                child: Container(
                  alignment: Alignment.bottomCenter,
                  decoration: BoxDecoration(
                    image: DecorationImage(
                      image: AssetImage("assets/images/chilly_img.jpg"),
                      fit: BoxFit.cover,
                    ),
                  ),
                  child: Text(
                    "Chilly",
                    style: TextStyle(
                        fontSize: 20,
                        color: Colors.black,
                        fontWeight: FontWeight.bold),
                  ),
                ),
              ),
              onTap: () {
                Navigator.of(context).push(
                  MaterialPageRoute(
                    builder: (context) => chilly("Chilly"),
                  ),
                );
              },
            ),
            GestureDetector(
              child: Card(
                elevation: 10,
                child: Container(
                  alignment: Alignment.bottomCenter,
                  decoration: BoxDecoration(
                    image: DecorationImage(
                      image: AssetImage("assets/images/coffee_img.jpg"),
                      fit: BoxFit.cover,
                    ),
                  ),
                  child: Text(
                    "Coffee",
                    style: TextStyle(
                        fontSize: 20,
                        color: Colors.black,
                        fontWeight: FontWeight.bold),
                  ),
                ),
              ),
              onTap: () {
                Navigator.of(context).push(
                  MaterialPageRoute(
                    builder: (context) => coffee("Coffee"),
                  ),
                );
              },
            ),
            GestureDetector(
              child: Card(
                elevation: 10,
                child: Container(
                  alignment: Alignment.bottomCenter,
                  decoration: BoxDecoration(
                    image: DecorationImage(
                      image: AssetImage("assets/images/cotton_img.jpg"),
                      fit: BoxFit.cover,
                    ),
                  ),
                  child: Text(
                    "Cotton",
                    style: TextStyle(
                        fontSize: 20,
                        color: Colors.black,
                        fontWeight: FontWeight.bold),
                  ),
                ),
              ),
              onTap: () {
                Navigator.of(context).push(
                  MaterialPageRoute(
                    builder: (context) => cotton("Cotton"),
                  ),
                );
              },
            ),
            GestureDetector(
              child: Card(
                elevation: 10,
                child: Container(
                  alignment: Alignment.bottomCenter,
                  decoration: BoxDecoration(
                    image: DecorationImage(
                      image: AssetImage("assets/images/methi_img.jpg"),
                      fit: BoxFit.cover,
                    ),
                  ),
                  child: Text(
                    "Fenugreek",
                    style: TextStyle(
                        fontSize: 20,
                        color: Colors.black,
                        fontWeight: FontWeight.bold),
                  ),
                ),
              ),
              onTap: () {
                Navigator.of(context).push(
                  MaterialPageRoute(
                    builder: (context) => methi("Fenugreek"),
                  ),
                );
              },
            ),
            GestureDetector(
              child: Card(
                elevation: 10,
                child: Container(
                  alignment: Alignment.bottomCenter,
                  decoration: BoxDecoration(
                    image: DecorationImage(
                      image: AssetImage("assets/images/mustard_img.jpg"),
                      fit: BoxFit.cover,
                    ),
                  ),
                  child: Text(
                    "Indian Mustard",
                    style: TextStyle(
                        fontSize: 20,
                        color: Colors.black,
                        fontWeight: FontWeight.bold),
                  ),
                ),
              ),
              onTap: () {
                Navigator.of(context).push(
                  MaterialPageRoute(
                    builder: (context) => mustard("Indian Mustard"),
                  ),
                );
              },
            ),
            GestureDetector(
              child: Card(
                elevation: 10,
                child: Container(
                  alignment: Alignment.bottomCenter,
                  decoration: BoxDecoration(
                    image: DecorationImage(
                      image: AssetImage("assets/images/lentils_img.jpg"),
                      fit: BoxFit.cover,
                    ),
                  ),
                  child: Text(
                    "Lentil",
                    style: TextStyle(
                        fontSize: 20,
                        color: Colors.black,
                        fontWeight: FontWeight.bold),
                  ),
                ),
              ),
              onTap: () {
                Navigator.of(context).push(
                  MaterialPageRoute(
                    builder: (context) => lentil("Lentil"),
                  ),
                );
              },
            ),
            GestureDetector(
              child: Card(
                elevation: 10,
                child: Container(
                  alignment: Alignment.bottomCenter,
                  decoration: BoxDecoration(
                    image: DecorationImage(
                      image: AssetImage("assets/images/maize_img.jpg"),
                      fit: BoxFit.cover,
                    ),
                  ),
                  child: Text(
                    "Maize",
                    style: TextStyle(
                        fontSize: 20,
                        color: Colors.black,
                        fontWeight: FontWeight.bold),
                  ),
                ),
              ),
              onTap: () {
                Navigator.of(context).push(
                  MaterialPageRoute(
                    builder: (context) => maize("Maize"),
                  ),
                );
              },
            ),
            GestureDetector(
              child: Card(
                elevation: 10,
                child: Container(
                  alignment: Alignment.bottomCenter,
                  decoration: BoxDecoration(
                    image: DecorationImage(
                      image: AssetImage("assets/images/peas_img.jpg"),
                      fit: BoxFit.cover,
                    ),
                  ),
                  child: Text(
                    "Peas",
                    style: TextStyle(
                        fontSize: 20,
                        color: Colors.black,
                        fontWeight: FontWeight.bold),
                  ),
                ),
              ),
              onTap: () {
                Navigator.of(context).push(
                  MaterialPageRoute(
                    builder: (context) => pea("Peas"),
                  ),
                );
              },
            ),
            GestureDetector(
              child: Card(
                elevation: 10,
                child: Container(
                  alignment: Alignment.bottomCenter,
                  decoration: BoxDecoration(
                    image: DecorationImage(
                      image: AssetImage("assets/images/pum_img.jpg"),
                      fit: BoxFit.cover,
                    ),
                  ),
                  child: Text(
                    "Pumpkin",
                    style: TextStyle(
                        fontSize: 20,
                        color: Colors.black,
                        fontWeight: FontWeight.bold),
                  ),
                ),
              ),
              onTap: () {
                Navigator.of(context).push(
                  MaterialPageRoute(
                    builder: (context) => pumpkin("Pumpkin"),
                  ),
                );
              },
            ),
            GestureDetector(
              child: Card(
                elevation: 10,
                child: Container(
                  alignment: Alignment.bottomCenter,
                  decoration: BoxDecoration(
                    image: DecorationImage(
                      image: AssetImage("assets/images/rice_img.jpg"),
                      fit: BoxFit.cover,
                    ),
                  ),
                  child: Text(
                    "Rice",
                    style: TextStyle(
                        fontSize: 20,
                        color: Colors.black,
                        fontWeight: FontWeight.bold),
                  ),
                ),
              ),
              onTap: () {
                Navigator.of(context).push(
                  MaterialPageRoute(
                    builder: (context) => rice("Rice"),
                  ),
                );
              },
            ),
            GestureDetector(
              child: Card(
                elevation: 10,
                child: Container(
                  alignment: Alignment.bottomCenter,
                  decoration: BoxDecoration(
                    image: DecorationImage(
                      image: AssetImage("assets/images/sesame_img.jpg"),
                      fit: BoxFit.cover,
                    ),
                  ),
                  child: Text(
                    "Sesame",
                    style: TextStyle(
                        fontSize: 20,
                        color: Colors.black,
                        fontWeight: FontWeight.bold),
                  ),
                ),
              ),
              onTap: () {
                Navigator.of(context).push(
                  MaterialPageRoute(
                    builder: (context) => sesame("Sesame"),
                  ),
                );
              },
            ),
            GestureDetector(
              child: Card(
                elevation: 10,
                child: Container(
                  alignment: Alignment.bottomCenter,
                  decoration: BoxDecoration(
                    image: DecorationImage(
                      image: AssetImage("assets/images/sorghum_img.jpg"),
                      fit: BoxFit.cover,
                    ),
                  ),
                  child: Text(
                    "Sorghum",
                    style: TextStyle(
                        fontSize: 20,
                        color: Colors.black,
                        fontWeight: FontWeight.bold),
                  ),
                ),
              ),
              onTap: () {
                Navigator.of(context).push(
                  MaterialPageRoute(
                    builder: (context) => sorghum("Sorghum"),
                  ),
                );
              },
            ),
            GestureDetector(
              child: Card(
                elevation: 10,
                child: Container(
                  alignment: Alignment.bottomCenter,
                  decoration: BoxDecoration(
                    image: DecorationImage(
                      image: AssetImage("assets/images/soyabean_img.jpg"),
                      fit: BoxFit.cover,
                    ),
                  ),
                  child: Text(
                    "Soyabean",
                    style: TextStyle(
                        fontSize: 20,
                        color: Colors.black,
                        fontWeight: FontWeight.bold),
                  ),
                ),
              ),
              onTap: () {
                Navigator.of(context).push(
                  MaterialPageRoute(
                    builder: (context) => soyabean("Soyabean"),
                  ),
                );
              },
            ),
            GestureDetector(
              child: Card(
                elevation: 10,
                child: Container(
                  alignment: Alignment.bottomCenter,
                  decoration: BoxDecoration(
                    image: DecorationImage(
                      image: AssetImage("assets/images/sugarcane_img.jpg"),
                      fit: BoxFit.cover,
                    ),
                  ),
                  child: Text(
                    "Sugarcane",
                    style: TextStyle(
                        fontSize: 20,
                        color: Colors.black,
                        fontWeight: FontWeight.bold),
                  ),
                ),
              ),
              onTap: () {
                Navigator.of(context).push(
                  MaterialPageRoute(
                    builder: (context) => sugarcane("Sugarcane"),
                  ),
                );
              },
            ),
            GestureDetector(
              child: Card(
                elevation: 10,
                child: Container(
                  alignment: Alignment.bottomCenter,
                  decoration: BoxDecoration(
                    image: DecorationImage(
                      image: AssetImage("assets/images/tea_img.jpg"),
                      fit: BoxFit.cover,
                    ),
                  ),
                  child: Text(
                    "Tea",
                    style: TextStyle(
                        fontSize: 20,
                        color: Colors.black,
                        fontWeight: FontWeight.bold),
                  ),
                ),
              ),
              onTap: () {
                Navigator.of(context).push(
                  MaterialPageRoute(
                    builder: (context) => tea("Tea"),
                  ),
                );
              },
            ),
            GestureDetector(
              child: Card(
                elevation: 10,
                child: Container(
                  alignment: Alignment.bottomCenter,
                  decoration: BoxDecoration(
                    image: DecorationImage(
                      image: AssetImage("assets/images/tobacco_img.jpg"),
                      fit: BoxFit.cover,
                    ),
                  ),
                  child: Text(
                    "Tobacco",
                    style: TextStyle(
                        fontSize: 20,
                        color: Colors.black,
                        fontWeight: FontWeight.bold),
                  ),
                ),
              ),
              onTap: () {
                Navigator.of(context).push(
                  MaterialPageRoute(
                    builder: (context) => tobacco("Tobacco"),
                  ),
                );
              },
            ),
            GestureDetector(
              child: Card(
                elevation: 10,
                child: Container(
                  alignment: Alignment.bottomCenter,
                  decoration: BoxDecoration(
                    image: DecorationImage(
                      image: AssetImage("assets/images/turmeric_img.jpg"),
                      fit: BoxFit.cover,
                    ),
                  ),
                  child: Text(
                    "Turmeric",
                    style: TextStyle(
                        fontSize: 20,
                        color: Colors.black,
                        fontWeight: FontWeight.bold),
                  ),
                ),
              ),
              onTap: () {
                Navigator.of(context).push(
                  MaterialPageRoute(
                    builder: (context) => turmeric("Turmeric"),
                  ),
                );
              },
            ),
            GestureDetector(
              child: Card(
                elevation: 10,
                child: Container(
                  alignment: Alignment.bottomCenter,
                  decoration: BoxDecoration(
                    image: DecorationImage(
                      image: AssetImage("assets/images/wheat_img.jpg"),
                      fit: BoxFit.cover,
                    ),
                  ),
                  child: Text(
                    "Wheat",
                    style: TextStyle(
                        fontSize: 20,
                        color: Colors.black,
                        fontWeight: FontWeight.bold),
                  ),
                ),
              ),
              onTap: () {
                Navigator.of(context).push(
                  MaterialPageRoute(
                    builder: (context) => wheat("Wheat"),
                  ),
                );
              },
            ),
          ],
        ),
      ),
    );
  }
}
