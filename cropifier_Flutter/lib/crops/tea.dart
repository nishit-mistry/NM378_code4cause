import 'package:flutter/material.dart';
import 'package:esys_flutter_share/esys_flutter_share.dart';
import 'package:flutter/services.dart';
import 'package:cropifier_start/my_flutter_app_icons.dart';

class tea extends StatelessWidget {
  final String name;
  tea(this.name);

  @override
  Widget build(BuildContext context) {
    Widget tea1 = new Center(
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
                        Text('Tea', textAlign: TextAlign.center, style: TextStyle(fontSize: 18),),
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
                        Text('Tea plantation in India has been contributing significantly towards the socio economic development of the people of the tea growing regions of the country.', textAlign: TextAlign.center, style: TextStyle(fontSize: 18),),
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
                        Text('4.5 – 5.6', textAlign: TextAlign.center, style: TextStyle(fontSize: 18),),
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
                        Text('Full or Dappled sunlight.', textAlign: TextAlign.center, style: TextStyle(fontSize: 18),),
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
                        Text('Well drained soil.', textAlign: TextAlign.center, style: TextStyle(fontSize: 18),),
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
                        Text('April to June and October to December.', textAlign: TextAlign.center, style: TextStyle(fontSize: 18),),
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
                        Text('21°C to 29°C is ideal.', textAlign: TextAlign.center, style: TextStyle(fontSize: 18),),
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
                        Text('Two or three times per week during the summer, using rainwater whenever possible. Allow the soil to dry slightly between waterings.', textAlign: TextAlign.center, style: TextStyle(fontSize: 18),),
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
                        Text('Darjeeling, Tamil Nadu, Munnar, Assam, Himachal Pradesh, Karnataka and West Bengal.', textAlign: TextAlign.center, style: TextStyle(fontSize: 18),),
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
                        Text('Camellia sinensis.', textAlign: TextAlign.center, style: TextStyle(fontSize: 18),),
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
                final ByteData bytes = await rootBundle.load('assets/documents/Tea.txt');
                await Share.file('Tea', 'Tea.txt', bytes.buffer.asUint8List(), 'text/plain');
              },
            ),
            body: new ListView(
              children: <Widget>[
                tea1
              ],
            )
        )
    );
  }
}