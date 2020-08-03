import 'dart:io';
import 'package:flutter/material.dart';
import 'package:cropifier_start/face_detection_camera.dart';
import 'package:tflite/tflite.dart';
import 'package:image_picker/image_picker.dart';

class camera_page extends StatefulWidget {
  @override
  State<StatefulWidget> createState() {
    return _camera_pageState();
  }
}

class _camera_pageState extends State<camera_page> {
  List _outputs;
  File _image;
  int count = 0;
  bool _loading;

  void initState() {
    super.initState();
    _loading = true;
    loadModel().then((value) {
      setState(() {
        _loading = false;
      });
    });
  }

  loadModel() async {
    await Tflite.loadModel(
      model: "assets/model_unquant.tflite",
      labels: "assets/labels.txt",
    );
  }

  clickImage() async {
    count++;
    print(count);
    // ignore: deprecated_member_use
    var image = await ImagePicker.pickImage(source: ImageSource.camera);
    if (image == null) return null;
    setState(() {
      _loading = true;
      //Declare File _image in the class which is used to display the image on the screen.
      _image = image;
    });
    classifyImage(image);
  }

  pickImage() async {
    count++;
    print(count);
    // ignore: deprecated_member_use
    var image = await ImagePicker.pickImage(source: ImageSource.gallery);
    if (image == null) return null;
    setState(() {
      _loading = true;
      //Declare File _image in the class which is used to display the image on the screen.
      _image = image;
    });
    classifyImage(image);
  }

  classifyImage(File image) async {
    var output = await Tflite.runModelOnImage(
      path: image.path,
      numResults: 1,
      threshold: 0.5,
      imageMean: 127.5,
      imageStd: 127.5,
    );
    setState(() {
      _loading = false;
      //Declare List _outputs in the class which will be used to show the classified classs name and confidence
      _outputs = output;
    });
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
        backgroundColor: Colors.grey[900],
        appBar: AppBar(
          title: Text(
            "Crop Identification Page",
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
        floatingActionButton: Column(
          mainAxisAlignment: MainAxisAlignment.end,
          children: <Widget>[
            FloatingActionButton(
              heroTag: null,
              onPressed: () => clickImage(),
              child: Icon(Icons.camera),
            ),
            FloatingActionButton(
              heroTag: null,
              onPressed: () => pickImage(),
              child: Icon(Icons.image),
            ),
//            SizedBox(width: 10),
            FloatingActionButton(
              heroTag: null,
              onPressed: () {
                Navigator.push(context,
                    MaterialPageRoute(builder: (BuildContext context) {
                      return FaceDetectionFromLiveCamera();
                    }));
              },
              child: Icon(Icons.videocam),
            ),
          ],
        ),
        body: _loading
            ? Container(
          alignment: Alignment.center,
          child: CircularProgressIndicator(),
        )
            : Container(
          width: MediaQuery.of(context).size.width,
          child: Column(
            crossAxisAlignment: CrossAxisAlignment.center,
            mainAxisAlignment: MainAxisAlignment.center,
            children: [
              _image == null
                  ? Container()
                  : Image.file(_image,
                  fit: BoxFit.contain,
                  height: MediaQuery.of(context).size.height * 0.6),
              SizedBox(
                height: 10,
              ),
              _outputs != null
                  ? Column(
                children: <Widget>[
                  Text(
                    "${_outputs[0]["label"]}",
                    style: TextStyle(
                      color: Colors.green,
                      fontSize: 25.0,
                    ),
                  ),
                  Text(
                    "${(_outputs[0]["confidence"] * 100).toStringAsFixed(0)}%",
                    style: TextStyle(
                        color: Colors.purpleAccent, fontSize: 20),
                  )
                ],
              )
                  : Expanded(
                child: Column(
                  mainAxisAlignment: MainAxisAlignment.start,
                  children: <Widget>[
                    Padding(
                      padding: const EdgeInsets.all(16.0),
                      child: Text(
                        "Click a Photo or Choose one from Gallery or use the Live Camera Feed to Identify the Crop",
                        style: TextStyle(
                            fontSize: 20,
                            fontWeight: FontWeight.w500,
                            color: Colors.white),
                        textAlign: TextAlign.center,
                      ),
                    )
                  ],
                ),
              )
            ],
          ),
        ));
  }
}
