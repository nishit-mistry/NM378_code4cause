import 'package:flutter/material.dart';
import 'package:esys_flutter_share/esys_flutter_share.dart';
import 'package:flutter/services.dart';
import 'package:cropifier_start/my_flutter_app_icons.dart';

class bitter extends StatelessWidget {
  final String name;
  bitter(this.name);

  @override
  Widget build(BuildContext context) {
    Widget bitter1 = new Center(
      child: Column(
        children: <Widget>[
          Container(
            margin: EdgeInsets.all(10),
            child: Table(
              defaultVerticalAlignment: TableCellVerticalAlignment.middle,
              border: TableBorder(
                  horizontalInside: BorderSide(width: 1, color: Colors.black),
                  verticalInside: BorderSide(width: 1, color: Colors.black)),
              children: [
                TableRow(
                  decoration: BoxDecoration(color: Colors.grey[200]),
                  children: [
                    Column(
                      children: <Widget>[
                        Icon(MyFlutterApp.crop),
                        Text(
                          'Crop Name:',
                          textAlign: TextAlign.center,
                          style: TextStyle(
                              fontWeight: FontWeight.bold, fontSize: 20),
                        ),
                      ],
                    ),
                    Column(
                      children: <Widget>[
                        Text(
                          'Bitter Gourd',
                          textAlign: TextAlign.center,
                          style: TextStyle(fontSize: 18),
                        ),
                      ],
                    ),
                  ],
                ),
                TableRow(
                  children: [
                    Column(
                      children: <Widget>[
                        Icon(Icons.info),
                        Text(
                          'Description:',
                          textAlign: TextAlign.center,
                          style: TextStyle(
                              fontWeight: FontWeight.bold, fontSize: 20),
                        ),
                      ],
                    ),
                    Column(
                      children: <Widget>[
                        Text(
                          'Bitter gourd (Momordica charantia) is an important vegetable crop and is grown for its immature tuberculate fruits which have a unique bitter taste.',
                          textAlign: TextAlign.center,
                          style: TextStyle(fontSize: 18),
                        ),
                      ],
                    ),
                  ],
                ),
                TableRow(
                  decoration: BoxDecoration(color: Colors.grey[200]),
                  children: [
                    Column(
                      children: <Widget>[
                        Icon(MyFlutterApp.ph),
                        Text(
                          'pH of Soil:',
                          textAlign: TextAlign.center,
                          style: TextStyle(
                              fontWeight: FontWeight.bold, fontSize: 20),
                        ),
                      ],
                    ),
                    Column(
                      children: <Widget>[
                        Text(
                          '6.0 – 6.7',
                          textAlign: TextAlign.center,
                          style: TextStyle(fontSize: 18),
                        ),
                      ],
                    ),
                  ],
                ),
                TableRow(
                  children: [
                    Column(
                      children: <Widget>[
                        Icon(Icons.wb_sunny),
                        Text(
                          'Sunlight Requirements:',
                          textAlign: TextAlign.center,
                          style: TextStyle(
                              fontWeight: FontWeight.bold, fontSize: 20),
                        ),
                      ],
                    ),
                    Column(
                      children: <Widget>[
                        Text(
                          '5 to 6 hours of sunlight.',
                          textAlign: TextAlign.center,
                          style: TextStyle(fontSize: 18),
                        ),
                      ],
                    ),
                  ],
                ),
                TableRow(
                  decoration: BoxDecoration(color: Colors.grey[200]),
                  children: [
                    Column(
                      children: <Widget>[
                        Icon(MyFlutterApp.soil),
                        Text(
                          'Type of Soil:',
                          textAlign: TextAlign.center,
                          style: TextStyle(
                              fontWeight: FontWeight.bold, fontSize: 20),
                        ),
                      ],
                    ),
                    Column(
                      children: <Widget>[
                        Text(
                          'Well drained sandy to sandy loam; Medium black soils.',
                          textAlign: TextAlign.center,
                          style: TextStyle(fontSize: 18),
                        ),
                      ],
                    ),
                  ],
                ),
                TableRow(
                  children: [
                    Column(
                      children: <Widget>[
                        Icon(Icons.timer),
                        Text(
                          'Cultivation Time:',
                          textAlign: TextAlign.center,
                          style: TextStyle(
                              fontWeight: FontWeight.bold, fontSize: 20),
                        ),
                      ],
                    ),
                    Column(
                      children: <Widget>[
                        Text(
                          'January to March, June-July.',
                          textAlign: TextAlign.center,
                          style: TextStyle(fontSize: 18),
                        ),
                      ],
                    ),
                  ],
                ),
                TableRow(
                  decoration: BoxDecoration(color: Colors.grey[200]),
                  children: [
                    Column(
                      children: <Widget>[
                        Icon(MyFlutterApp.climate),
                        Text(
                          'Climate:',
                          textAlign: TextAlign.center,
                          style: TextStyle(
                              fontWeight: FontWeight.bold, fontSize: 20),
                        ),
                      ],
                    ),
                    Column(
                      children: <Widget>[
                        Text(
                          'Minimum temperature of 18°C during early growth, but optimal temperatures are in the range of 24° to 27° C.',
                          textAlign: TextAlign.center,
                          style: TextStyle(fontSize: 18),
                        ),
                      ],
                    ),
                  ],
                ),
                TableRow(
                  children: [
                    Column(
                      children: <Widget>[
                        Icon(MyFlutterApp.water),
                        Text(
                          'Water Requirements:',
                          textAlign: TextAlign.center,
                          style: TextStyle(
                              fontWeight: FontWeight.bold, fontSize: 20),
                        ),
                      ],
                    ),
                    Column(
                      children: <Widget>[
                        Text(
                          'Bitter gourds need continuous watering all through their life cycle. Water is vital for their growth and survival. Inadequate moisture can delay flowering and result in stunted gourds.',
                          textAlign: TextAlign.center,
                          style: TextStyle(fontSize: 18),
                        ),
                      ],
                    ),
                  ],
                ),
                TableRow(
                  decoration: BoxDecoration(color: Colors.grey[200]),
                  children: [
                    Column(
                      children: <Widget>[
                        Icon(Icons.location_on),
                        Text(
                          'Cultivated At:',
                          textAlign: TextAlign.center,
                          style: TextStyle(
                              fontWeight: FontWeight.bold, fontSize: 20),
                        ),
                      ],
                    ),
                    Column(
                      children: <Widget>[
                        Text(
                          'Bangalore, New Delhi, Kerala, Tamil Nadu, Dapoli, Punjab, Kanpur, Rahuri.',
                          textAlign: TextAlign.center,
                          style: TextStyle(fontSize: 18),
                        ),
                      ],
                    ),
                  ],
                ),
                TableRow(
                  children: [
                    Column(
                      children: <Widget>[
                        Icon(MyFlutterApp.sciname),
                        Text(
                          'Scientific Name:',
                          textAlign: TextAlign.center,
                          style: TextStyle(
                              fontWeight: FontWeight.bold, fontSize: 20),
                        ),
                      ],
                    ),
                    Column(
                      children: <Widget>[
                        Text(
                          'Momordica charantia.',
                          textAlign: TextAlign.center,
                          style: TextStyle(fontSize: 18),
                        ),
                      ],
                    ),
                  ],
                ),
              ],
            ),
          ),
        ],
      ),
    );

    return new MaterialApp(
        debugShowCheckedModeBanner: false,
        title: "",
        home: new Scaffold(
            appBar: new AppBar(
              backgroundColor: Colors.green,
              title: new Text(name),
              automaticallyImplyLeading: true,
              leading: IconButton(
                icon: Icon(Icons.arrow_back),
                onPressed: () => Navigator.pop(context, false),
              ),
            ),
            floatingActionButton: FloatingActionButton(
              child: Icon(Icons.share),
              backgroundColor: Colors.green,
              onPressed: () async {
                final ByteData bytes =
                    await rootBundle.load('assets/documents/Bitter_Gourd.txt');
                await Share.file('Bitter Gourd', 'Bitter_Gourd.txt',
                    bytes.buffer.asUint8List(), 'text/plain');
              },
            ),
            body: new ListView(
              children: <Widget>[bitter1],
            )));
  }
}
