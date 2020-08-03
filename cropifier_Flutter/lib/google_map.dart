import 'dart:async';
import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';
import 'package:fluttertoast/fluttertoast.dart';
import 'package:geolocator/geolocator.dart';
import 'package:google_maps_flutter/google_maps_flutter.dart';
import 'package:permission_handler/permission_handler.dart';
import 'models/pin_pill_info.dart';
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

class MyMap extends StatefulWidget {
  @override
  _MyMapState createState() => _MyMapState();
}

class _MyMapState extends State<MyMap> {
  // ignore: unused_field
  GoogleMapController _controller;

  PinInformation currentlySelectedPin = PinInformation(
      pinPath: '',
      avatarPath: '',
      location: LatLng(0, 0),
      locationName: '',
      labelColor: Colors.grey);
  BitmapDescriptor bittergourd_img;
  BitmapDescriptor chillies_img;
  BitmapDescriptor coffee_img;
  BitmapDescriptor cotton_img;
  BitmapDescriptor lentils_img;
  BitmapDescriptor maize_img;
  BitmapDescriptor methi_img;
  BitmapDescriptor mustard_img;
  BitmapDescriptor peas_img;
  BitmapDescriptor pumpkin_img;
  BitmapDescriptor rice_img;
  BitmapDescriptor sesame_img;
  BitmapDescriptor sorghum_img;
  BitmapDescriptor soyabean_img;
  BitmapDescriptor sugarcane_img;
  BitmapDescriptor tea_img;
  BitmapDescriptor tobacco_img;
  BitmapDescriptor turmeric_img;
  BitmapDescriptor wheat_img;

  PinInformation sourceIconInfo;

  Position position;
  Widget _child;

  Future<void> getLocation() async {
    PermissionStatus permission = await PermissionHandler()
        .checkPermissionStatus(PermissionGroup.location);

    if (permission == PermissionStatus.denied) {
      await PermissionHandler()
          .requestPermissions([PermissionGroup.locationAlways]);
    }

    var geolocator = Geolocator();

    GeolocationStatus geolocationStatus =
    await geolocator.checkGeolocationPermissionStatus();

    switch (geolocationStatus) {
      case GeolocationStatus.denied:
        showToast('denied');
        break;
      case GeolocationStatus.disabled:
        showToast('disabled');
        break;
      case GeolocationStatus.restricted:
        showToast('restricted');
        break;
      case GeolocationStatus.unknown:
        showToast('unknown');
        break;
      case GeolocationStatus.granted:
        showToast('Access granted');
        _getCurrentLocation();
    }
  }

  void _setStyle(GoogleMapController controller) async {
    String value = await DefaultAssetBundle.of(context)
        .loadString('assets/map_style.json');
    controller.setMapStyle(value);
  }

  Set<Marker> _createMarker() {
    return <Marker>[
      Marker(
          markerId: MarkerId('home'),
          position: LatLng(position.latitude, position.longitude),
          icon: BitmapDescriptor.defaultMarker,
          infoWindow: InfoWindow(title: 'Current Location')),
      Marker(
        markerId: MarkerId('bittergourd1'),
        position: LatLng(22.1358, 82.1495),
        icon: bittergourd_img,
        infoWindow: InfoWindow(
          title: 'Bitter Gourd',
          snippet: "Bilaspur, Chattisgarh",
          onTap: () {
            Navigator.of(context).push(
              MaterialPageRoute(
                builder: (context) => bitter("Bitter Gourd"),
              ),
            );
          },
        ),
      ),
      Marker(
        markerId: MarkerId('bittergourd2'),
        position: LatLng(14.7013731, 79.623258),
        icon: bittergourd_img,
        infoWindow: InfoWindow(
          title: 'Bitter Gourd',
          snippet: "Nellore, Andhra Pradesh",
          onTap: () {
            Navigator.of(context).push(
              MaterialPageRoute(
                builder: (context) => bitter("Bitter Gourd"),
              ),
            );
          },
        ),
      ),
      Marker(
        markerId: MarkerId('bittergourd3'),
        position: LatLng(16.6959687, 74.2099502),
        icon: bittergourd_img,
        infoWindow: InfoWindow(
          title: 'Bitter Gourd',
          snippet: "Kolhapur,Maharashtra",
          onTap: () {
            Navigator.of(context).push(
              MaterialPageRoute(
                builder: (context) => bitter("Bitter Gourd"),
              ),
            );
          },
        ),
      ),
      Marker(
        markerId: MarkerId('bittergourd4'),
        position: LatLng(21.5143576, 70.4342862),
        icon: bittergourd_img,
        infoWindow: InfoWindow(
          title: 'Bitter Gourd',
          snippet: "Junagadh,Gujarat",
          onTap: () {
            Navigator.of(context).push(
              MaterialPageRoute(
                builder: (context) => bitter("Bitter Gourd"),
              ),
            );
          },
        ),
      ),
      Marker(
        markerId: MarkerId('bittergourd5'),
        position: LatLng(26.938663, 75.900492),
        icon: bittergourd_img,
        infoWindow: InfoWindow(
          title: 'Bitter Gourd',
          snippet: "Jaipur,Rajasthan",
          onTap: () {
            Navigator.of(context).push(
              MaterialPageRoute(
                builder: (context) => bitter("Bitter Gourd"),
              ),
            );
          },
        ),
      ),
      Marker(
        markerId: MarkerId('chillies1'),
        position: LatLng(16.5721152, 80.8147065),
        icon: chillies_img,
        infoWindow: InfoWindow(
          title: 'Chillies',
          snippet: "Krishna, Andhra Pradesh",
          onTap: () {
            Navigator.of(context).push(
              MaterialPageRoute(
                builder: (context) => chilly("Chilly"),
              ),
            );
          },
        ),
      ),
      Marker(
        markerId: MarkerId('chillies2'),
        position: LatLng(17.743230, 76.358023),
        icon: chillies_img,
        infoWindow: InfoWindow(
          title: 'Chillies',
          snippet: "Solapur, Maharashtra",
          onTap: () {
            Navigator.of(context).push(
              MaterialPageRoute(
                builder: (context) => chilly("Chilly"),
              ),
            );
          },
        ),
      ),
      Marker(
        markerId: MarkerId('chillies3'),
        position: LatLng(21.8529856, 72.186655),
        icon: chillies_img,
        infoWindow: InfoWindow(
          title: 'Chillies3',
          snippet: "Bhavnagar, Gujarat",
          onTap: () {
            Navigator.of(context).push(
              MaterialPageRoute(
                builder: (context) => chilly("Chilly"),
              ),
            );
          },
        ),
      ),
      Marker(
        markerId: MarkerId('chillies4'),
        position: LatLng(14.9158796, 79.9470888),
        icon: chillies_img,
        infoWindow: InfoWindow(
          title: 'Chillies',
          snippet: "Nellore, Andhra Pradesh",
          onTap: () {
            Navigator.of(context).push(
              MaterialPageRoute(
                builder: (context) => chilly("Chilly"),
              ),
            );
          },
        ),
      ),
      Marker(
        markerId: MarkerId('chillies5'),
        position: LatLng(26.0000704, 85.3798459),
        icon: chillies_img,
        infoWindow: InfoWindow(
          title: 'Chillies',
          snippet: "Muzaffarpur, Bihar",
          onTap: () {
            Navigator.of(context).push(
              MaterialPageRoute(
                builder: (context) => chilly("Chilly"),
              ),
            );
          },
        ),
      ),
      Marker(
        markerId: MarkerId('coffee1'),
        position: LatLng(15.33671, 76.1714945),
        icon: coffee_img,
        infoWindow: InfoWindow(
          title: 'Coffee',
          snippet: "Koppal, Karnataka",
          onTap: () {
            Navigator.of(context).push(
              MaterialPageRoute(
                builder: (context) => coffee("Coffee"),
              ),
            );
          },
        ),
      ),
      Marker(
        markerId: MarkerId('coffee2'),
        position: LatLng(9.143953, 76.880594),
        icon: coffee_img,
        infoWindow: InfoWindow(
          title: 'Coffee',
          snippet: "Pathanamthitta, Kerala",
          onTap: () {
            Navigator.of(context).push(
              MaterialPageRoute(
                builder: (context) => coffee("Coffee"),
              ),
            );
          },
        ),
      ),
      Marker(
        markerId: MarkerId('coffee3'),
        position: LatLng(17.745857, 83.3146099),
        icon: coffee_img,
        infoWindow: InfoWindow(
          title: 'Coffee',
          snippet: "Visakhapatnam, Andhra Pradesh",
          onTap: () {
            Navigator.of(context).push(
              MaterialPageRoute(
                builder: (context) => coffee("Coffee"),
              ),
            );
          },
        ),
      ),
      Marker(
        markerId: MarkerId('coffee4'),
        position: LatLng(11.9896583, 76.6221921),
        icon: coffee_img,
        infoWindow: InfoWindow(
          title: 'Coffee',
          snippet: "Mysore, Karnataka",
          onTap: () {
            Navigator.of(context).push(
              MaterialPageRoute(
                builder: (context) => coffee("Coffee"),
              ),
            );
          },
        ),
      ),
      Marker(
        markerId: MarkerId('coffee5'),
        position: LatLng(9.5901707, 76.5065346),
        icon: coffee_img,
        infoWindow: InfoWindow(
          title: 'Coffee',
          snippet: "Kottayam, Kerala",
          onTap: () {
            Navigator.of(context).push(
              MaterialPageRoute(
                builder: (context) => coffee("Coffee"),
              ),
            );
          },
        ),
      ),
      Marker(
        markerId: MarkerId('cotton1'),
        position: LatLng(30.936965, 75.906644),
        icon: cotton_img,
        infoWindow: InfoWindow(
          title: 'Cotton',
          snippet: "Ludhiana, Punjab",
          onTap: () {
            Navigator.of(context).push(
              MaterialPageRoute(
                builder: (context) => cotton("Cotton"),
              ),
            );
          },
        ),
      ),
      Marker(
        markerId: MarkerId('cotton2'),
        position: LatLng(21.703803, 73.011676),
        icon: cotton_img,
        infoWindow: InfoWindow(
          title: 'Cotton',
          snippet: "Bharuch, Gujarat",
          onTap: () {
            Navigator.of(context).push(
              MaterialPageRoute(
                builder: (context) => cotton("Cotton"),
              ),
            );
          },
        ),
      ),
      Marker(
        markerId: MarkerId('cotton3'),
        position: LatLng(20.9642836, 77.9191293),
        icon: cotton_img,
        infoWindow: InfoWindow(
          title: 'Cotton',
          snippet: "Amravati, Maharashtra",
          onTap: () {
            Navigator.of(context).push(
              MaterialPageRoute(
                builder: (context) => cotton("Cotton"),
              ),
            );
          },
        ),
      ),
      Marker(
        markerId: MarkerId('cotton4'),
        position: LatLng(20.0988268, 77.1208336),
        icon: cotton_img,
        infoWindow: InfoWindow(
          title: 'Cotton',
          snippet: "Washim, Maharashtra",
          onTap: () {
            Navigator.of(context).push(
              MaterialPageRoute(
                builder: (context) => cotton("Cotton"),
              ),
            );
          },
        ),
      ),
      Marker(
        markerId: MarkerId('cotton5'),
        position: LatLng(22.992514, 72.5238699),
        icon: cotton_img,
        infoWindow: InfoWindow(
          title: 'Cotton',
          snippet: "Ahmedabad, Gujarat",
          onTap: () {
            Navigator.of(context).push(
              MaterialPageRoute(
                builder: (context) => cotton("Cotton"),
              ),
            );
          },
        ),
      ),
      Marker(
        markerId: MarkerId('lentils1'),
        position: LatLng(23.835158, 84.282460),
        icon: lentils_img,
        infoWindow: InfoWindow(
          title: 'Lentils',
          snippet: "Bokaro, Jharkhand",
          onTap: () {
            Navigator.of(context).push(
              MaterialPageRoute(
                builder: (context) => lentil("Lentil"),
              ),
            );
          },
        ),
      ),
      Marker(
        markerId: MarkerId('lentils2'),
        position: LatLng(24.420004, 81.982265),
        icon: lentils_img,
        infoWindow: InfoWindow(
          title: 'Lentils',
          snippet: "Sidhi, Madhya Pradesh",
          onTap: () {
            Navigator.of(context).push(
              MaterialPageRoute(
                builder: (context) => lentil("Lentil"),
              ),
            );
          },
        ),
      ),
      Marker(
        markerId: MarkerId('lentils3'),
        position: LatLng(17.6079693, 81.3368949),
        icon: lentils_img,
        infoWindow: InfoWindow(
          title: 'Lentils',
          snippet: "East Godavari, Andhra Pradesh",
          onTap: () {
            Navigator.of(context).push(
              MaterialPageRoute(
                builder: (context) => lentil("Lentil"),
              ),
            );
          },
        ),
      ),
      Marker(
        markerId: MarkerId('lentils4'),
        position: LatLng(17.0015691, 81.4784293),
        icon: lentils_img,
        infoWindow: InfoWindow(
          title: 'Lentils',
          snippet: "West Godavari, Andhra Pradesh",
          onTap: () {
            Navigator.of(context).push(
              MaterialPageRoute(
                builder: (context) => lentil("Lentil"),
              ),
            );
          },
        ),
      ),
      Marker(
        markerId: MarkerId('lentils5'),
        position: LatLng(15.3842851, 79.9568439),
        icon: lentils_img,
        infoWindow: InfoWindow(
          title: 'Lentils',
          snippet: "Prakasam, Andhra Pradesh",
          onTap: () {
            Navigator.of(context).push(
              MaterialPageRoute(
                builder: (context) => lentil("Lentil"),
              ),
            );
          },
        ),
      ),
      Marker(
        markerId: MarkerId('maize1'),
        position: LatLng(25.217379, 74.430118),
        icon: maize_img,
        infoWindow: InfoWindow(
          title: 'Maize',
          snippet: "Bhilwara, Rajasthan",
          onTap: () {
            Navigator.of(context).push(
              MaterialPageRoute(
                builder: (context) => maize("Maize"),
              ),
            );
          },
        ),
      ),
      Marker(
        markerId: MarkerId('maize2'),
        position: LatLng(12.962304, 74.968404),
        icon: maize_img,
        infoWindow: InfoWindow(
          title: 'Maize',
          snippet: "Dakshina Kannada, Karnataka",
          onTap: () {
            Navigator.of(context).push(
              MaterialPageRoute(
                builder: (context) => maize("Maize"),
              ),
            );
          },
        ),
      ),
      Marker(
        markerId: MarkerId('maize3'),
        position: LatLng(24.0088483, 72.945629),
        icon: maize_img,
        infoWindow: InfoWindow(
          title: 'Maize',
          snippet: "SabarKantha, Gujarat",
          onTap: () {
            Navigator.of(context).push(
              MaterialPageRoute(
                builder: (context) => maize("Maize"),
              ),
            );
          },
        ),
      ),
      Marker(
        markerId: MarkerId('maize4'),
        position: LatLng(23.2670993, 73.1222821),
        icon: maize_img,
        infoWindow: InfoWindow(
          title: 'Maize',
          snippet: "Udepur, Gujarat",
          onTap: () {
            Navigator.of(context).push(
              MaterialPageRoute(
                builder: (context) => maize("Maize"),
              ),
            );
          },
        ),
      ),
      Marker(
        markerId: MarkerId('maize5'),
        position: LatLng(23.5478252, 74.4055896),
        icon: maize_img,
        infoWindow: InfoWindow(
          title: 'Maize',
          snippet: "Banswara, Rajasthan",
          onTap: () {
            Navigator.of(context).push(
              MaterialPageRoute(
                builder: (context) => maize("Maize"),
              ),
            );
          },
        ),
      ),
      Marker(
        markerId: MarkerId('methi1'),
        position: LatLng(24.960222, 73.747137),
        icon: methi_img,
        infoWindow: InfoWindow(
          title: 'Fenugreek',
          snippet: "Rajsamand, Rajasthan",
          onTap: () {
            Navigator.of(context).push(
              MaterialPageRoute(
                builder: (context) => methi("Fenugreek"),
              ),
            );
          },
        ),
      ),
      Marker(
        markerId: MarkerId('methi2'),
        position: LatLng(11.465733, 78.917556),
        icon: methi_img,
        infoWindow: InfoWindow(
          title: 'Fenugreek',
          snippet: "Cuddalore, Tamil Nadu",
          onTap: () {
            Navigator.of(context).push(
              MaterialPageRoute(
                builder: (context) => methi("Fenugreek"),
              ),
            );
          },
        ),
      ),
      Marker(
        markerId: MarkerId('methi3'),
        position: LatLng(30.6949027, 77.9681044),
        icon: methi_img,
        infoWindow: InfoWindow(
          title: 'Fenugreek',
          snippet: "Dehradun, Uttrakhand",
          onTap: () {
            Navigator.of(context).push(
              MaterialPageRoute(
                builder: (context) => methi("Fenugreek"),
              ),
            );
          },
        ),
      ),
      Marker(
        markerId: MarkerId('methi4'),
        position: LatLng(28.393527, 77.833948),
        icon: methi_img,
        infoWindow: InfoWindow(
          title: 'Fenugreek',
          snippet: "Bulandshahr, Uttar Pradesh",
          onTap: () {
            Navigator.of(context).push(
              MaterialPageRoute(
                builder: (context) => methi("Fenugreek"),
              ),
            );
          },
        ),
      ),
      Marker(
        markerId: MarkerId('methi5'),
        position: LatLng(27.1905718, 75.785764),
        icon: methi_img,
        infoWindow: InfoWindow(
          title: 'Fenugreek',
          snippet: "Jaipur, Rajasthan",
          onTap: () {
            Navigator.of(context).push(
              MaterialPageRoute(
                builder: (context) => methi("Fenugreek"),
              ),
            );
          },
        ),
      ),
      Marker(
        markerId: MarkerId('mustard1'),
        position: LatLng(25.6109918, 75.8963763),
        icon: mustard_img,
        infoWindow: InfoWindow(
          title: 'Indian Mustard',
          snippet: "Kota,Rajasthan",
          onTap: () {
            Navigator.of(context).push(
              MaterialPageRoute(
                builder: (context) => mustard("Indian Mustard"),
              ),
            );
          },
        ),
      ),
      Marker(
        markerId: MarkerId('mustard2'),
        position: LatLng(29.110322, 75.735147),
        icon: mustard_img,
        infoWindow: InfoWindow(
          title: 'Indian Mustard',
          snippet: "Hisar, Haryana",
          onTap: () {
            Navigator.of(context).push(
              MaterialPageRoute(
                builder: (context) => mustard("Indian Mustard"),
              ),
            );
          },
        ),
      ),
      Marker(
        markerId: MarkerId('mustard3'),
        position: LatLng(26.9980286, 94.6389229),
        icon: mustard_img,
        infoWindow: InfoWindow(
          title: 'Indian Mustard',
          snippet: "Sibsagar, Assam",
          onTap: () {
            Navigator.of(context).push(
              MaterialPageRoute(
                builder: (context) => mustard("Indian Mustard"),
              ),
            );
          },
        ),
      ),
      Marker(
        markerId: MarkerId('mustard4'),
        position: LatLng(27.3892139, 94.3675155),
        icon: mustard_img,
        infoWindow: InfoWindow(
          title: 'Indian Mustard',
          snippet: "Dhemaji, Assam",
          onTap: () {
            Navigator.of(context).push(
              MaterialPageRoute(
                builder: (context) => mustard("Indian Mustard"),
              ),
            );
          },
        ),
      ),
      Marker(
        markerId: MarkerId('mustard5'),
        position: LatLng(26.7941747, 84.9943328),
        icon: mustard_img,
        infoWindow: InfoWindow(
          title: 'Indian Mustard',
          snippet: "East Champaran, Bihar",
          onTap: () {
            Navigator.of(context).push(
              MaterialPageRoute(
                builder: (context) => mustard("Indian Mustard"),
              ),
            );
          },
        ),
      ),
      Marker(
        markerId: MarkerId('peas1'),
        position: LatLng(29.362139, 77.743669),
        icon: peas_img,
        infoWindow: InfoWindow(
          title: 'Peas',
          snippet: "Muzaffarnagar, Uttar Pradesh",
          onTap: () {
            Navigator.of(context).push(
              MaterialPageRoute(
                builder: (context) => pea("Peas"),
              ),
            );
          },
        ),
      ),
      Marker(
        markerId: MarkerId('peas2'),
        position: LatLng(26.117770, 78.264709),
        icon: peas_img,
        infoWindow: InfoWindow(
          title: 'Peas',
          snippet: "Gwalior, Madhya Pradesh",
          onTap: () {
            Navigator.of(context).push(
              MaterialPageRoute(
                builder: (context) => pea("Peas"),
              ),
            );
          },
        ),
      ),
      Marker(
        markerId: MarkerId('peas3'),
        position: LatLng(30.6026069, 77.4426994),
        icon: peas_img,
        infoWindow: InfoWindow(
          title: 'Peas',
          snippet: "Sirmaur, Himachal Pradesh",
          onTap: () {
            Navigator.of(context).push(
              MaterialPageRoute(
                builder: (context) => pea("Peas"),
              ),
            );
          },
        ),
      ),
      Marker(
        markerId: MarkerId('peas4'),
        position: LatLng(23.716425, 77.339118),
        icon: peas_img,
        infoWindow: InfoWindow(
          title: 'Peas',
          snippet: "Bhopal, Madhya Pradesh",
          onTap: () {
            Navigator.of(context).push(
              MaterialPageRoute(
                builder: (context) => pea("Peas"),
              ),
            );
          },
        ),
      ),
      Marker(
        markerId: MarkerId('peas5'),
        position: LatLng(26.686999, 77.848496),
        icon: peas_img,
        infoWindow: InfoWindow(
          title: 'Peas',
          snippet: "Dholpur, Rajasthan",
          onTap: () {
            Navigator.of(context).push(
              MaterialPageRoute(
                builder: (context) => pea("Peas"),
              ),
            );
          },
        ),
      ),
      Marker(
        markerId: MarkerId('pumpkin1'),
        position: LatLng(20.8693735, 83.1555593),
        icon: pumpkin_img,
        infoWindow: InfoWindow(
          title: 'Pumpkin',
          snippet: "Balangir, Odisha",
          onTap: () {
            Navigator.of(context).push(
              MaterialPageRoute(
                builder: (context) => pumpkin("Pumpkin"),
              ),
            );
          },
        ),
      ),
      Marker(
        markerId: MarkerId('pumpkin2'),
        position: LatLng(24.7354209, 83.2725943),
        icon: pumpkin_img,
        infoWindow: InfoWindow(
          title: 'Pumpkin',
          snippet: "Chandauli, Uttar Pradesh",
          onTap: () {
            Navigator.of(context).push(
              MaterialPageRoute(
                builder: (context) => pumpkin("Pumpkin"),
              ),
            );
          },
        ),
      ),
      Marker(
        markerId: MarkerId('pumpkin3'),
        position: LatLng(24.5233069, 83.0276753),
        icon: pumpkin_img,
        infoWindow: InfoWindow(
          title: 'Pumpkin',
          snippet: "Sonbhadra, Uttar Pradesh",
          onTap: () {
            Navigator.of(context).push(
              MaterialPageRoute(
                builder: (context) => pumpkin("Pumpkin"),
              ),
            );
          },
        ),
      ),
      Marker(
        markerId: MarkerId('pumpkin4'),
        position: LatLng(22.763635, 75.623447),
        icon: pumpkin_img,
        infoWindow: InfoWindow(
          title: 'Pumpkin',
          snippet: "Indore, Madhya Pradesh",
          onTap: () {
            Navigator.of(context).push(
              MaterialPageRoute(
                builder: (context) => pumpkin("Pumpkin"),
              ),
            );
          },
        ),
      ),
      Marker(
        markerId: MarkerId('pumpkin5'),
        position: LatLng(21.250534, 81.447467),
        icon: pumpkin_img,
        infoWindow: InfoWindow(
          title: 'Pumpkin',
          snippet: "Durg, Chattisgarh",
          onTap: () {
            Navigator.of(context).push(
              MaterialPageRoute(
                builder: (context) => pumpkin("Pumpkin"),
              ),
            );
          },
        ),
      ),
      Marker(
        markerId: MarkerId('rice1'),
        position: LatLng(26.750911, 88.403998),
        icon: rice_img,
        infoWindow: InfoWindow(
          title: 'Rice',
          snippet: "Darjeeling, West Bengal",
          onTap: () {
            Navigator.of(context).push(
              MaterialPageRoute(
                builder: (context) => rice("Rice"),
              ),
            );
          },
        ),
      ),
      Marker(
        markerId: MarkerId('rice2'),
        position: LatLng(26.959140, 83.526210),
        icon: rice_img,
        infoWindow: InfoWindow(
          title: 'Rice',
          snippet: "Maharajganj, Uttar Pradesh",
          onTap: () {
            Navigator.of(context).push(
              MaterialPageRoute(
                builder: (context) => rice("Rice"),
              ),
            );
          },
        ),
      ),
      Marker(
        markerId: MarkerId('rice3'),
        position: LatLng(20.3098764, 73.7769867),
        icon: rice_img,
        infoWindow: InfoWindow(
          title: 'Rice',
          snippet: "Nashik, Maharashtra",
          onTap: () {
            Navigator.of(context).push(
              MaterialPageRoute(
                builder: (context) => rice("Rice"),
              ),
            );
          },
        ),
      ),
      Marker(
        markerId: MarkerId('rice4'),
        position: LatLng(20.9304523, 74.8927555),
        icon: rice_img,
        infoWindow: InfoWindow(
          title: 'Rice',
          snippet: "Dhule, Maharashtra",
          onTap: () {
            Navigator.of(context).push(
              MaterialPageRoute(
                builder: (context) => rice("Rice"),
              ),
            );
          },
        ),
      ),
      Marker(
        markerId: MarkerId('rice5'),
        position: LatLng(18.8401849, 73.9071292),
        icon: rice_img,
        infoWindow: InfoWindow(
          title: 'Rice',
          snippet: "Pune, Maharashtra",
          onTap: () {
            Navigator.of(context).push(
              MaterialPageRoute(
                builder: (context) => rice("Rice"),
              ),
            );
          },
        ),
      ),
      Marker(
        markerId: MarkerId('sesame1'),
        position: LatLng(21.626913, 70.720903),
        icon: sesame_img,
        infoWindow: InfoWindow(
          title: 'Sesame',
          snippet: "Rajkot, Gujarat",
          onTap: () {
            Navigator.of(context).push(
              MaterialPageRoute(
                builder: (context) => sesame("Sesame"),
              ),
            );
          },
        ),
      ),
      Marker(
        markerId: MarkerId('sesame2'),
        position: LatLng(22.938650, 87.304805),
        icon: sesame_img,
        infoWindow: InfoWindow(
          title: 'Sesame',
          snippet: "Bankura, West Bengal",
          onTap: () {
            Navigator.of(context).push(
              MaterialPageRoute(
                builder: (context) => sesame("Sesame"),
              ),
            );
          },
        ),
      ),
      Marker(
        markerId: MarkerId('sesame3'),
        position: LatLng(23.5547822, 72.6811814),
        icon: sesame_img,
        infoWindow: InfoWindow(
          title: 'Sesame',
          snippet: "Ahmedabad, Gujarat",
          onTap: () {
            Navigator.of(context).push(
              MaterialPageRoute(
                builder: (context) => sesame("Sesame"),
              ),
            );
          },
        ),
      ),
      Marker(
        markerId: MarkerId('sesame4'),
        position: LatLng(26.1363461, 93.1133449),
        icon: sesame_img,
        infoWindow: InfoWindow(
          title: 'Sesame',
          snippet: "Karbi Anglong, Assam",
          onTap: () {
            Navigator.of(context).push(
              MaterialPageRoute(
                builder: (context) => sesame("Sesame"),
              ),
            );
          },
        ),
      ),
      Marker(
        markerId: MarkerId('sesame5'),
        position: LatLng(25.2940865, 93.1449919),
        icon: sesame_img,
        infoWindow: InfoWindow(
          title: 'Sesame',
          snippet: "North Cachar hills, Assam",
          onTap: () {
            Navigator.of(context).push(
              MaterialPageRoute(
                builder: (context) => sesame("Sesame"),
              ),
            );
          },
        ),
      ),
      Marker(
        markerId: MarkerId('sorghum1'),
        position: LatLng(18.986876, 75.770952),
        icon: sorghum_img,
        infoWindow: InfoWindow(
          title: 'Sorghum',
          snippet: "Beed, Maharastra",
          onTap: () {
            Navigator.of(context).push(
              MaterialPageRoute(
                builder: (context) => sorghum("Sorghum"),
              ),
            );
          },
        ),
      ),
      Marker(
        markerId: MarkerId('sorghum2'),
        position: LatLng(14.715736, 75.725490),
        icon: sorghum_img,
        infoWindow: InfoWindow(
          title: 'Sorghum',
          snippet: "Haveri, Karnataka",
          onTap: () {
            Navigator.of(context).push(
              MaterialPageRoute(
                builder: (context) => sorghum("Sorghum"),
              ),
            );
          },
        ),
      ),
      Marker(
        markerId: MarkerId('sorghum3'),
        position: LatLng(21.2669565, 74.7808977),
        icon: sorghum_img,
        infoWindow: InfoWindow(
          title: 'Sorghum',
          snippet: "Dhule, Maharashtra",
          onTap: () {
            Navigator.of(context).push(
              MaterialPageRoute(
                builder: (context) => sorghum("Sorghum"),
              ),
            );
          },
        ),
      ),
      Marker(
        markerId: MarkerId('sorghum4'),
        position: LatLng(21.3932543, 74.2375777),
        icon: sorghum_img,
        infoWindow: InfoWindow(
          title: 'Sorghum',
          snippet: "Nandurbar, Maharashtra",
          onTap: () {
            Navigator.of(context).push(
              MaterialPageRoute(
                builder: (context) => sorghum("Sorghum"),
              ),
            );
          },
        ),
      ),
      Marker(
        markerId: MarkerId('sorghum5'),
        position: LatLng(18.4475147, 73.8434084),
        icon: sorghum_img,
        infoWindow: InfoWindow(
          title: 'Sorghum',
          snippet: "Pune, Maharashtra",
          onTap: () {
            Navigator.of(context).push(
              MaterialPageRoute(
                builder: (context) => sorghum("Sorghum"),
              ),
            );
          },
        ),
      ),
      Marker(
        markerId: MarkerId('soyabean1'),
        position: LatLng(23.151412, 77.453936),
        icon: soyabean_img,
        infoWindow: InfoWindow(
          title: 'Soyabean',
          snippet: "Bhopal, Madhya Pradesh",
          onTap: () {
            Navigator.of(context).push(
              MaterialPageRoute(
                builder: (context) => soyabean("Soyabean"),
              ),
            );
          },
        ),
      ),
      Marker(
        markerId: MarkerId('soyabean2'),
        position: LatLng(21.252773, 79.184813),
        icon: soyabean_img,
        infoWindow: InfoWindow(
          title: 'Soyabean',
          snippet: "Nagpur, Maharashtra",
          onTap: () {
            Navigator.of(context).push(
              MaterialPageRoute(
                builder: (context) => soyabean("Soyabean"),
              ),
            );
          },
        ),
      ),
      Marker(
        markerId: MarkerId('soyabean3'),
        position: LatLng(26.513293, 77.030616),
        icon: soyabean_img,
        infoWindow: InfoWindow(
          title: 'Soyabean',
          snippet: "Karauli, Rajasthan",
          onTap: () {
            Navigator.of(context).push(
              MaterialPageRoute(
                builder: (context) => soyabean("Soyabean"),
              ),
            );
          },
        ),
      ),
      Marker(
        markerId: MarkerId('soyabean4'),
        position: LatLng(27.201986, 77.454777),
        icon: soyabean_img,
        infoWindow: InfoWindow(
          title: 'Soyabean',
          snippet: "Bharatpur, Rajasthan",
          onTap: () {
            Navigator.of(context).push(
              MaterialPageRoute(
                builder: (context) => soyabean("Soyabean"),
              ),
            );
          },
        ),
      ),
      Marker(
        markerId: MarkerId('soyabean5'),
        position: LatLng(23.913736, 91.358568),
        icon: soyabean_img,
        infoWindow: InfoWindow(
          title: 'Soyabean',
          snippet: "West Tripura, Tripura",
          onTap: () {
            Navigator.of(context).push(
              MaterialPageRoute(
                builder: (context) => soyabean("Soyabean"),
              ),
            );
          },
        ),
      ),
      Marker(
        markerId: MarkerId('sugarcane1'),
        position: LatLng(19.086550, 74.549908),
        icon: sugarcane_img,
        infoWindow: InfoWindow(
          title: 'Sugarcane',
          snippet: "Ahmednagar, Maharashtra",
          onTap: () {
            Navigator.of(context).push(
              MaterialPageRoute(
                builder: (context) => sugarcane("Sugarcane"),
              ),
            );
          },
        ),
      ),
      Marker(
        markerId: MarkerId('sugarcane2'),
        position: LatLng(21.0072979, 77.7238642),
        icon: sugarcane_img,
        infoWindow: InfoWindow(
          title: 'Sugarcane',
          snippet: "Amravati, Maharashtra",
          onTap: () {
            Navigator.of(context).push(
              MaterialPageRoute(
                builder: (context) => sugarcane("Sugarcane"),
              ),
            );
          },
        ),
      ),
      Marker(
        markerId: MarkerId('sugarcane3'),
        position: LatLng(18.3009064, 83.8757434),
        icon: sugarcane_img,
        infoWindow: InfoWindow(
          title: 'Sugarcane',
          snippet: "Srikakulam, Andhra Pradesh",
          onTap: () {
            Navigator.of(context).push(
              MaterialPageRoute(
                builder: (context) => sugarcane("Sugarcane"),
              ),
            );
          },
        ),
      ),
      Marker(
        markerId: MarkerId('sugarcane4'),
        position: LatLng(18.0778517, 83.443264),
        icon: sugarcane_img,
        infoWindow: InfoWindow(
          title: 'Sugarcane',
          snippet: "Vizianagaram, Andhra Pradesh",
          onTap: () {
            Navigator.of(context).push(
              MaterialPageRoute(
                builder: (context) => sugarcane("Sugarcane"),
              ),
            );
          },
        ),
      ),
      Marker(
        markerId: MarkerId('sugarcane5'),
        position: LatLng(16.3963465, 81.3749315),
        icon: sugarcane_img,
        infoWindow: InfoWindow(
          title: 'Sugarcane',
          snippet: "West Godavari, Andhra Pradesh",
          onTap: () {
            Navigator.of(context).push(
              MaterialPageRoute(
                builder: (context) => sugarcane("Sugarcane"),
              ),
            );
          },
        ),
      ),
      Marker(
        markerId: MarkerId('tea1'),
        position: LatLng(26.384473, 92.684993),
        icon: tea_img,
        infoWindow: InfoWindow(
          title: 'Tea',
          snippet: "Nagaon, Assam",
          onTap: () {
            Navigator.of(context).push(
              MaterialPageRoute(
                builder: (context) => tea("Tea"),
              ),
            );
          },
        ),
      ),
      Marker(
        markerId: MarkerId('tea2'),
        position: LatLng(22.397046, 88.855864),
        icon: tea_img,
        infoWindow: InfoWindow(
          title: 'Tea',
          snippet: "North 24 Parganas, West Bengal",
          onTap: () {
            Navigator.of(context).push(
              MaterialPageRoute(
                builder: (context) => tea("Tea"),
              ),
            );
          },
        ),
      ),
      Marker(
        markerId: MarkerId('tea3'),
        position: LatLng(26.7167719, 94.2265322),
        icon: tea_img,
        infoWindow: InfoWindow(
          title: 'Tea',
          snippet: "Jorhat, Assam",
          onTap: () {
            Navigator.of(context).push(
              MaterialPageRoute(
                builder: (context) => tea("Tea"),
              ),
            );
          },
        ),
      ),
      Marker(
        markerId: MarkerId('tea4'),
        position: LatLng(24.6305068, 92.8566039),
        icon: tea_img,
        infoWindow: InfoWindow(
          title: 'Tea',
          snippet: "Cachar, Assam",
          onTap: () {
            Navigator.of(context).push(
              MaterialPageRoute(
                builder: (context) => tea("Tea"),
              ),
            );
          },
        ),
      ),
      Marker(
        markerId: MarkerId('tea5'),
        position: LatLng(32.105633, 76.2690707),
        icon: tea_img,
        infoWindow: InfoWindow(
          title: 'Tea',
          snippet: "Kangra, Himachal Pradesh",
          onTap: () {
            Navigator.of(context).push(
              MaterialPageRoute(
                builder: (context) => tea("Tea"),
              ),
            );
          },
        ),
      ),
      Marker(
        markerId: MarkerId('tobacco1'),
        position: LatLng(17.2664424, 80.1176356),
        icon: tobacco_img,
        infoWindow: InfoWindow(
          title: 'Tobacco',
          snippet: "Khammam, Telangana",
          onTap: () {
            Navigator.of(context).push(
              MaterialPageRoute(
                builder: (context) => tobacco("Tobacco"),
              ),
            );
          },
        ),
      ),
      Marker(
        markerId: MarkerId('tobacco2'),
        position: LatLng(14.4214929, 79.986702),
        icon: tobacco_img,
        infoWindow: InfoWindow(
          title: 'Tobacco',
          snippet: "Nellore, Andhra Pradesh",
          onTap: () {
            Navigator.of(context).push(
              MaterialPageRoute(
                builder: (context) => tobacco("Tobacco"),
              ),
            );
          },
        ),
      ),
      Marker(
        markerId: MarkerId('tobacco3'),
        position: LatLng(16.3488806, 80.4641881),
        icon: tobacco_img,
        infoWindow: InfoWindow(
          title: 'Tobacco',
          snippet: "Guntur, Andhra Pradesh",
          onTap: () {
            Navigator.of(context).push(
              MaterialPageRoute(
                builder: (context) => tobacco("Tobacco"),
              ),
            );
          },
        ),
      ),
      Marker(
        markerId: MarkerId('tobacco4'),
        position: LatLng(25.750797, 87.4748715),
        icon: tobacco_img,
        infoWindow: InfoWindow(
          title: 'Tobacco',
          snippet: "Purnia, Bihar",
          onTap: () {
            Navigator.of(context).push(
              MaterialPageRoute(
                builder: (context) => tobacco("Tobacco"),
              ),
            );
          },
        ),
      ),
      Marker(
        markerId: MarkerId('tobacco5'),
        position: LatLng(25.3709824, 87.5746358),
        icon: tobacco_img,
        infoWindow: InfoWindow(
          title: 'Tobacco',
          snippet: "Katihar, Bihar",
          onTap: () {
            Navigator.of(context).push(
              MaterialPageRoute(
                builder: (context) => tobacco("Tobacco"),
              ),
            );
          },
        ),
      ),
      Marker(
        markerId: MarkerId('turmeric1'),
        position: LatLng(13.768064, 78.155620),
        icon: turmeric_img,
        infoWindow: InfoWindow(
          title: 'Turmeric',
          snippet: "Chittoor, Andhra Pradesh",
          onTap: () {
            Navigator.of(context).push(
              MaterialPageRoute(
                builder: (context) => turmeric("Turmeric"),
              ),
            );
          },
        ),
      ),
      Marker(
        markerId: MarkerId('turmeric2'),
        position: LatLng(20.931400, 83.658740),
        icon: turmeric_img,
        infoWindow: InfoWindow(
          title: 'Turmeric',
          snippet: "Bargarh, Odisha",
          onTap: () {
            Navigator.of(context).push(
              MaterialPageRoute(
                builder: (context) => turmeric("Turmeric"),
              ),
            );
          },
        ),
      ),
      Marker(
        markerId: MarkerId('turmeric3'),
        position: LatLng(14.0572115, 79.8056534),
        icon: turmeric_img,
        infoWindow: InfoWindow(
          title: 'Turmeric',
          snippet: "Nellore, Andhra Pradesh",
          onTap: () {
            Navigator.of(context).push(
              MaterialPageRoute(
                builder: (context) => turmeric("Turmeric"),
              ),
            );
          },
        ),
      ),
      Marker(
        markerId: MarkerId('turmeric4'),
        position: LatLng(11.343701, 77.677660),
        icon: turmeric_img,
        infoWindow: InfoWindow(
          title: 'Turmeric',
          snippet: "Erode, Tamil Nadu",
          onTap: () {
            Navigator.of(context).push(
              MaterialPageRoute(
                builder: (context) => turmeric("Turmeric"),
              ),
            );
          },
        ),
      ),
      Marker(
        markerId: MarkerId('turmeric5'),
        position: LatLng(20.068709, 85.806950),
        icon: turmeric_img,
        infoWindow: InfoWindow(
          title: 'Turmeric',
          snippet: "Jajpur, Odisha",
          onTap: () {
            Navigator.of(context).push(
              MaterialPageRoute(
                builder: (context) => turmeric("Turmeric"),
              ),
            );
          },
        ),
      ),
      Marker(
        markerId: MarkerId('wheat1'),
        position: LatLng(29.512206, 75.061473),
        icon: wheat_img,
        infoWindow: InfoWindow(
          title: 'Wheat',
          snippet: "Sirsa, Haryana",
          onTap: () {
            Navigator.of(context).push(
              MaterialPageRoute(
                builder: (context) => wheat("Wheat"),
              ),
            );
          },
        ),
      ),
      Marker(
        markerId: MarkerId('wheat2'),
        position: LatLng(22.179727, 76.139752),
        icon: wheat_img,
        infoWindow: InfoWindow(
          title: 'Wheat',
          snippet: "Khargone, Madhya pradesh",
          onTap: () {
            Navigator.of(context).push(
              MaterialPageRoute(
                builder: (context) => wheat("Wheat"),
              ),
            );
          },
        ),
      ),
      Marker(
        markerId: MarkerId('wheat3'),
        position: LatLng(20.057331, 73.8136037),
        icon: wheat_img,
        infoWindow: InfoWindow(
          title: 'Wheat',
          snippet: "Nashik, Maharashtra",
          onTap: () {
            Navigator.of(context).push(
              MaterialPageRoute(
                builder: (context) => wheat("Wheat"),
              ),
            );
          },
        ),
      ),
      Marker(
        markerId: MarkerId('wheat4'),
        position: LatLng(21.2009694, 71.3720026),
        icon: wheat_img,
        infoWindow: InfoWindow(
          title: 'Wheat',
          snippet: "Amreli, Gujarat",
          onTap: () {
            Navigator.of(context).push(
              MaterialPageRoute(
                builder: (context) => wheat("Wheat"),
              ),
            );
          },
        ),
      ),
      Marker(
        markerId: MarkerId('wheat5'),
        position: LatLng(17.1141952, 81.1397531),
        icon: wheat_img,
        infoWindow: InfoWindow(
          title: 'Wheat',
          snippet: "West Godavari, Andhra Pradesh",
          onTap: () {
            Navigator.of(context).push(
              MaterialPageRoute(
                builder: (context) => wheat("Wheat"),
              ),
            );
          },
        ),
      ),
    ].toSet();
  }

  void setSourceAndDestinationIcons() async {
    bittergourd_img = await BitmapDescriptor.fromAssetImage(
        ImageConfiguration(devicePixelRatio: 2.5),
        'assets/crop_photos/bittergourd.png');

    chillies_img = await BitmapDescriptor.fromAssetImage(
        ImageConfiguration(devicePixelRatio: 2.5),
        'assets/crop_photos/chillies.png');

    coffee_img = await BitmapDescriptor.fromAssetImage(
        ImageConfiguration(devicePixelRatio: 2.5),
        'assets/crop_photos/coffee.png');

    cotton_img = await BitmapDescriptor.fromAssetImage(
        ImageConfiguration(devicePixelRatio: 2.5),
        'assets/crop_photos/cotton.png');

    lentils_img = await BitmapDescriptor.fromAssetImage(
        ImageConfiguration(devicePixelRatio: 2.5),
        'assets/crop_photos/lentils.png');

    maize_img = await BitmapDescriptor.fromAssetImage(
        ImageConfiguration(devicePixelRatio: 2.5),
        'assets/crop_photos/maize.png');

    methi_img = await BitmapDescriptor.fromAssetImage(
        ImageConfiguration(devicePixelRatio: 2.5),
        'assets/crop_photos/methi.png');

    mustard_img = await BitmapDescriptor.fromAssetImage(
        ImageConfiguration(devicePixelRatio: 2.5),
        'assets/crop_photos/mustard.png');

    peas_img = await BitmapDescriptor.fromAssetImage(
        ImageConfiguration(devicePixelRatio: 2.5),
        'assets/crop_photos/peas.png');

    pumpkin_img = await BitmapDescriptor.fromAssetImage(
        ImageConfiguration(devicePixelRatio: 2.5),
        'assets/crop_photos/pumpkin.png');

    rice_img = await BitmapDescriptor.fromAssetImage(
        ImageConfiguration(devicePixelRatio: 2.5),
        'assets/crop_photos/rice.png');

    sesame_img = await BitmapDescriptor.fromAssetImage(
        ImageConfiguration(devicePixelRatio: 2.5),
        'assets/crop_photos/sesame.png');

    sorghum_img = await BitmapDescriptor.fromAssetImage(
        ImageConfiguration(devicePixelRatio: 2.5),
        'assets/crop_photos/sorghum.png');

    soyabean_img = await BitmapDescriptor.fromAssetImage(
        ImageConfiguration(devicePixelRatio: 2.5),
        'assets/crop_photos/soyabean.png');

    sugarcane_img = await BitmapDescriptor.fromAssetImage(
        ImageConfiguration(devicePixelRatio: 2.5),
        'assets/crop_photos/sugarcane.png');

    tea_img = await BitmapDescriptor.fromAssetImage(
        ImageConfiguration(devicePixelRatio: 2.5),
        'assets/crop_photos/tea.png');

    tobacco_img = await BitmapDescriptor.fromAssetImage(
        ImageConfiguration(devicePixelRatio: 2.5),
        'assets/crop_photos/tobacco.png');

    turmeric_img = await BitmapDescriptor.fromAssetImage(
        ImageConfiguration(devicePixelRatio: 2.5),
        'assets/crop_photos/turmeric.png');

    wheat_img = await BitmapDescriptor.fromAssetImage(
        ImageConfiguration(devicePixelRatio: 2.5),
        'assets/crop_photos/wheat.png');
  }

  void showToast(message) {
    Fluttertoast.showToast(
        msg: message,
        toastLength: Toast.LENGTH_SHORT,
        gravity: ToastGravity.BOTTOM,
        timeInSecForIos: 1,
        backgroundColor: Colors.white,
        textColor: Colors.black,
        fontSize: 16.0);
  }

  @override
  void initState() {
    getLocation();
    setSourceAndDestinationIcons();
    super.initState();
  }

  void _getCurrentLocation() async {
    Position res = await Geolocator().getCurrentPosition();
    setState(() {
      position = res;
      _child = _mapWidget();
    });
  }

  Widget _mapWidget() {
    return GoogleMap(
      mapType: MapType.hybrid,
      markers: _createMarker(),
      initialCameraPosition: CameraPosition(
        target: LatLng(position.latitude, position.longitude),
        zoom: 4.0,
      ),
      onMapCreated: (GoogleMapController controller) {
        _controller = controller;
        _setStyle(controller);
      },
    );
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
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
        body: _child);
  }
}
