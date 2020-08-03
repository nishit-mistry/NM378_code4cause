import 'package:flutter/material.dart';
import 'package:esys_flutter_share/esys_flutter_share.dart';
import 'package:flutter/services.dart';
import 'package:cropifier_start/my_flutter_app_icons.dart';

class wheat extends StatelessWidget {
  final String name;
  wheat(this.name);

  @override
  Widget build(BuildContext context) {
    Widget wheat1 = new Center(
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
                        Text('Wheat', textAlign: TextAlign.center, style: TextStyle(fontSize: 18),),
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
                        Text('Wheat (Triticum spp.) occupies the prime position among the food crops in the world. In India, it is the second important food crop being next to rice and contributes to the total food grain production of the country to the extent of about 25%.', textAlign: TextAlign.center, style: TextStyle(fontSize: 18),),
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
                        Text('6.0 and 7.0', textAlign: TextAlign.center, style: TextStyle(fontSize: 18),),
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
                        Text('Full sun.', textAlign: TextAlign.center, style: TextStyle(fontSize: 18),),
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
                        Text('Loamy soil.', textAlign: TextAlign.center, style: TextStyle(fontSize: 18),),
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
                        Text('October- December.', textAlign: TextAlign.center, style: TextStyle(fontSize: 18),),
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
                        Text('Low humidity.', textAlign: TextAlign.center, style: TextStyle(fontSize: 18),),
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
                        Text('450 to 650mm.', textAlign: TextAlign.center, style: TextStyle(fontSize: 18),),
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
                        Text('Punjab, Uttar Pradesh, Haryana, Madhya Pradesh, Rajashthan and Bihar.', textAlign: TextAlign.center, style: TextStyle(fontSize: 18),),
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
                        Text('Triticum.', textAlign: TextAlign.center, style: TextStyle(fontSize: 18),),
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
                final ByteData bytes = await rootBundle.load('assets/documents/Wheat.txt');
                await Share.file('Wheat', 'Wheat.txt', bytes.buffer.asUint8List(), 'text/plain');
              },
            ),
            body: new ListView(
              children: <Widget>[
                wheat1
              ],
            )
        )
    );
  }
}