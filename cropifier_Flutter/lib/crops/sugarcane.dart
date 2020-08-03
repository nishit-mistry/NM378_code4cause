import 'package:flutter/material.dart';
import 'package:esys_flutter_share/esys_flutter_share.dart';
import 'package:flutter/services.dart';
import 'package:cropifier_start/my_flutter_app_icons.dart';

class sugarcane extends StatelessWidget {
  final String name;
  sugarcane(this.name);

  @override
  Widget build(BuildContext context) {
    Widget sugarcane1 = new Center(
      child: Column(
        children: <Widget>[
          Container(
            margin: EdgeInsets.all(10),
            child: Table(
              defaultVerticalAlignment: TableCellVerticalAlignment.middle,
              border: TableBorder(horizontalInside: BorderSide(width: 1, color: Colors.black), verticalInside: BorderSide(width: 1, color: Colors.black)),
              children: [
                TableRow(
                  decoration: BoxDecoration(color: Colors.grey[200]),
                  children: [
                    Column(
                      children: <Widget>[
                        Icon(MyFlutterApp.crop),
                        Text('Crop Name:',textAlign: TextAlign.center, style: TextStyle(fontWeight: FontWeight.bold, fontSize: 20),),
                      ],
                    ),
                    Column(
                      children: <Widget>[
                        Text('Sugarcane', textAlign: TextAlign.center, style: TextStyle(fontSize: 18),),
                      ],
                    ),
                  ],
                ),
                TableRow(
                  children: [
                    Column(
                      children: <Widget>[
                        Icon(Icons.info),
                        Text('Description:',textAlign: TextAlign.center, style: TextStyle(fontWeight: FontWeight.bold, fontSize: 20),),
                      ],
                    ),
                    Column(
                      children: <Widget>[
                        Text('Sugarcane is grown as a Kharif Crop. It needs hot and humid climate with an average temperature of 21°C to 27°C.Sugarcane can grow in any soil which can retain moisture.', textAlign: TextAlign.center, style: TextStyle(fontSize: 18),),
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
                        Text('pH of Soil:', textAlign: TextAlign.center, style: TextStyle(fontWeight: FontWeight.bold, fontSize: 20),),
                      ],
                    ),
                    Column(
                      children: <Widget>[
                        Text('5 to 8.5', textAlign: TextAlign.center, style: TextStyle(fontSize: 18),),
                      ],
                    ),
                  ],
                ),
                TableRow(
                  children: [
                    Column(
                      children: <Widget>[
                        Icon(Icons.wb_sunny),
                        Text('Sunlight Requirements:', textAlign: TextAlign.center, style: TextStyle(fontWeight: FontWeight.bold, fontSize: 20),),
                      ],
                    ),
                    Column(
                      children: <Widget>[
                        Text('Full Sun.', textAlign: TextAlign.center, style: TextStyle(fontSize: 18),),
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
                        Text('Type of Soil:',textAlign: TextAlign.center, style: TextStyle(fontWeight: FontWeight.bold, fontSize: 20),),
                      ],
                    ),
                    Column(
                      children: <Widget>[
                        Text('Loamy soil, Black soil.', textAlign: TextAlign.center, style: TextStyle(fontSize: 18),),
                      ],
                    ),
                  ],
                ),
                TableRow(
                  children: [
                    Column(
                      children: <Widget>[
                        Icon(Icons.timer),
                        Text('Cultivation Time:', textAlign: TextAlign.center, style: TextStyle(fontWeight: FontWeight.bold, fontSize: 20),),
                      ],
                    ),
                    Column(
                      children: <Widget>[
                        Text('October, February-march, July.', textAlign: TextAlign.center, style: TextStyle(fontSize: 18),),
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
                        Text('Climate:', textAlign: TextAlign.center, style: TextStyle(fontWeight: FontWeight.bold, fontSize: 20),),
                      ],
                    ),
                    Column(
                      children: <Widget>[
                        Text('Tropical.', textAlign: TextAlign.center, style: TextStyle(fontSize: 18),),
                      ],
                    ),
                  ],
                ),
                TableRow(
                  children: [
                    Column(
                      children: <Widget>[
                        Icon(MyFlutterApp.water),
                        Text('Water Requirements:',textAlign: TextAlign.center, style: TextStyle(fontWeight: FontWeight.bold, fontSize: 20),),
                      ],
                    ),
                    Column(
                      children: <Widget>[
                        Text('1200 to 3500 mm.', textAlign: TextAlign.center, style: TextStyle(fontSize: 18),),
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
                        Text('Cultivated At:',textAlign: TextAlign.center, style: TextStyle(fontWeight: FontWeight.bold, fontSize: 20),),
                      ],
                    ),
                    Column(
                      children: <Widget>[
                        Text('Uttar Pradesh, Maharashtra and Karnataka.', textAlign: TextAlign.center, style: TextStyle(fontSize: 18),),
                      ],
                    ),
                  ],
                ),
                TableRow(
                  children: [
                    Column(
                      children: <Widget>[
                        Icon(MyFlutterApp.sciname),
                        Text('Scientific Name:',textAlign: TextAlign.center, style: TextStyle(fontWeight: FontWeight.bold, fontSize: 20),),
                      ],
                    ),
                    Column(
                      children: <Widget>[
                        Text('Saccharum officinarum.', textAlign: TextAlign.center, style: TextStyle(fontSize: 18),),
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
        title:"",
        home: new Scaffold(
            appBar: new AppBar(
              backgroundColor: Colors.green,
              title: new Text(name),
              automaticallyImplyLeading: true,
              leading: IconButton(icon: Icon(Icons.arrow_back),
                onPressed: () => Navigator.pop(context, false),
              ),
            ),
            floatingActionButton: FloatingActionButton(
              child: Icon(Icons.share),
              backgroundColor: Colors.green,
              onPressed: () async {
                final ByteData bytes = await rootBundle.load('assets/documents/Sugarcane.txt');
                await Share.file('Sugarcane', 'Sugarcane.txt', bytes.buffer.asUint8List(), 'text/plain');
              },
            ),
            body: new ListView(
              children: <Widget>[
                sugarcane1
              ],
            )
        )
    );
  }
}