import 'package:flutter/material.dart';
import 'package:esys_flutter_share/esys_flutter_share.dart';
import 'package:flutter/services.dart';
import 'package:cropifier_start/my_flutter_app_icons.dart';

class methi extends StatelessWidget {
  final String name;
  methi(this.name);

  @override
  Widget build(BuildContext context) {
    Widget methi1 = new Center(
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
                        Text('Fenugreek', textAlign: TextAlign.center, style: TextStyle(fontSize: 18),),
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
                        Text('Fenugreek is popularly known as “methi” in India and used as condiments and as a flavoring agent for food preparations.The young pods and leaves are used as vegetable consumed in daily cooking.', textAlign: TextAlign.center, style: TextStyle(fontSize: 18),),
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
                        Text('6.1 to 7', textAlign: TextAlign.center, style: TextStyle(fontSize: 18),),
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
                        Text('Full sun to Partial shade.', textAlign: TextAlign.center, style: TextStyle(fontSize: 18),),
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
                        Text('Loamy or sandy loam soils with good drainage.', textAlign: TextAlign.center, style: TextStyle(fontSize: 18),),
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
                        Text('First fortnight of October, second fortnight of June to July-end.', textAlign: TextAlign.center, style: TextStyle(fontSize: 18),),
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
                        Text('Moderately cool, frost-free and clear sky.', textAlign: TextAlign.center, style: TextStyle(fontSize: 18),),
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
                        Text('Water the plant with about 1 inch of water per week.', textAlign: TextAlign.center, style: TextStyle(fontSize: 18),),
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
                        Text('Rajasthan, Tamil Nadu, Gujarat, Madhya Pradesh, Punjab and Uttar Pradesh.', textAlign: TextAlign.center, style: TextStyle(fontSize: 18),),
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
                        Text('Trigonella foenum-graecum.', textAlign: TextAlign.center, style: TextStyle(fontSize: 18),),
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
                final ByteData bytes = await rootBundle.load('assets/documents/Fenugreek.txt');
                await Share.file('Fenugreek', 'Fenugreek.txt', bytes.buffer.asUint8List(), 'text/plain');
              },
            ),
            body: new ListView(
              children: <Widget>[
                methi1
              ],
            )
        )
    );
  }
}