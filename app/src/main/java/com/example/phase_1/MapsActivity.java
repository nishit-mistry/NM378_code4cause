package com.example.phase_1;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.maps.android.SphericalUtil;

import java.util.HashMap;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    HashMap<String,String> markerMap = new HashMap<String,String>();
    AlertDialog alertDialog1;
    Location currentlocation;
    FusedLocationProviderClient fusedLocationProviderClient;
    private static final int REQUEST_CODE = 101;
    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        fetchLastLocation();
        statusCheck();
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
    }

    public void statusCheck() {
        final LocationManager manager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        if (!manager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            buildAlertMessageNoGps();

        }
    }

    private void buildAlertMessageNoGps() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Your GPS seems to be disabled, do you want to enable it?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialog, final int id) {
                        startActivity(new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialog, final int id) {
                        dialog.cancel();
                    }
                });
        final AlertDialog alert = builder.create();
        alert.show();
    }

    private void fetchLastLocation() {
        if(ActivityCompat.checkSelfPermission(this,Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this,Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this,new String[]
                    {Manifest.permission.ACCESS_FINE_LOCATION},REQUEST_CODE);
            return;

        }
        Task<Location> task = fusedLocationProviderClient.getLastLocation();
        task.addOnSuccessListener(new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                if(location != null){
                    currentlocation = location;

                    double latitude = currentlocation.getLatitude();
                    double longitude = currentlocation.getLongitude();
                    String Label1 = latitude +","+longitude;
                    SharedPreferences sharedPref = getSharedPreferences("myKey", MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPref.edit();
                    editor.putString("value", Label1);
                    editor.apply();

                    Toast.makeText(getApplicationContext(),currentlocation.getLatitude()
                            + ""+currentlocation.getLongitude(),Toast.LENGTH_SHORT).show();



                    SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
                    mapFragment.getMapAsync(MapsActivity.this);
                    final Snackbar snackBar = Snackbar.make(mapFragment.getView(), "Click the marker and it's info snippet for more details", Snackbar.LENGTH_INDEFINITE);
                    snackBar.setAction("Dismiss", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            // Call your action method here
                            snackBar.dismiss();
                        }
                    });
                    snackBar.setActionTextColor(Color.parseColor("#FF0000"));
                    snackBar.show();

                }
            }
        });

    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines,add listeners or move the camera. In this case,
     * we just add a marker near Sydney,Australia.
     * If Google Play services is not installed on the device,the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);

        // Add a marker in Sydney and move the camera
        LatLng latLng = new LatLng(currentlocation.getLatitude(),currentlocation.getLongitude());
//        LatLng latLng = new LatLng(23.1059275,72.5915411); //gujarat technological university

//        LatLng latLng = new LatLng(28.2180, 94.7278);//arunachal pradesh
//        LatLng latLng = new LatLng(26.2006, 92.9376);//assam
//        LatLng latLng = new LatLng(25.0961, 85.3131);//bihar
//        LatLng latLng = new LatLng(21.2787, 81.8661); //chattisgarh
//        LatLng latLng = new LatLng(22.2587, 71.1924); //gujarat
//        LatLng latLng = new LatLng(31.1048, 77.1734);//hp
//        LatLng latLng = new LatLng(33.7782, 76.5762);//j&k
//        LatLng latLng = new LatLng(23.6102, 85.2799); //jharkhand
//        LatLng latLng = new LatLng(15.3173, 75.7139);//karnataka
//        LatLng latLng = new LatLng(10.8505, 76.2711);//kerala
//        LatLng latLng = new LatLng(22.9734, 78.6569); //mp
//        LatLng latLng = new LatLng(19.7515, 75.7139);//maharashtra
//        LatLng latLng = new LatLng(25.4670, 91.3662);//meghalaya
//        LatLng latLng = new LatLng(20.9517, 85.0985); //odisha
//        LatLng latLng = new LatLng(31.1471, 75.3412);//punjab
//        LatLng latLng = new LatLng(27.0238, 74.2179);//rajasthan
//        LatLng latLng = new LatLng(11.1271, 78.6569);//tn
//        LatLng latLng = new LatLng(23.9408, 91.9882); //tripura
//        LatLng latLng = new LatLng(26.8467, 80.9462); //up
//        LatLng latLng = new LatLng(30.0668, 79.0193);//uttrakhand
//        LatLng latLng = new LatLng(22.9868, 87.8550); //WestBengal
//        LatLng latLng = new LatLng(18.1124, 79.0193);//Telangana
//        LatLng latLng = new LatLng(29.0588, 76.0856);//Haryana
//        LatLng latLng = new LatLng(15.2993, 74.1240); //goa
//        LatLng latLng = new LatLng(24.6637, 93.9063);//manipur
//        LatLng latLng = new LatLng(27.5330, 88.5122); //sikkim
//        LatLng latLng = new LatLng(26.1584, 94.5624);//nagaland
//        LatLng latLng = new LatLng(23.1645, 92.9376);//mizoram


        Marker ownLoc = mMap.addMarker(new MarkerOptions()
                .position(latLng)
                .title("You are here")
                .alpha(0.7f));
        mMap.animateCamera(CameraUpdateFactory.newLatLng(latLng));
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng,7));

        mMap.addCircle(new CircleOptions()
                .center(latLng)
                .radius(200000)
                .strokeColor(Color.LTGRAY)
                .strokeWidth(4)
                .fillColor(0x250000ff));

        String id339 = ownLoc.getId();
        markerMap.put(id339,"action_own");


        //bitter gourd
        Marker bitter1= mMap.addMarker(new MarkerOptions()
                .position(new LatLng(22.1358,82.1495))
                .title("Bitter Gourd")
                .snippet("Bilaspur,Chattisgarh")
                .rotation((float) 0.5)
                .icon(bitmapDescriptorFromVector(getApplicationContext(),R.drawable.bitter))
                .visible(false));
        if (SphericalUtil.computeDistanceBetween(latLng,bitter1.getPosition()) < 200000) {
            bitter1.setVisible(true);
        }
        String id1 = bitter1.getId();
        markerMap.put(id1,"action_bitter");

        Marker bitter2 = mMap.addMarker(new MarkerOptions()
                .position(new LatLng(14.7013731,79.623258))
                .title("Bitter Gourd")
                .snippet("Nellore,Andhra Pradesh")
                .rotation((float) 0.5)
                .icon(bitmapDescriptorFromVector(getApplicationContext(),R.drawable.bitter))
                .visible(false));
        if (SphericalUtil.computeDistanceBetween(latLng,bitter2.getPosition()) < 200000) {
            bitter2.setVisible(true);
        }
        String id2 = bitter2.getId();
        markerMap.put(id2,"action_bitter");

        Marker bitter3 = mMap.addMarker(new MarkerOptions()
                .position(new LatLng(16.6959687,74.2099502))
                .title("Bitter Gourd")
                .snippet("Kolhapur,Maharashtra")
                .rotation((float) 0.5)
                .icon(bitmapDescriptorFromVector(getApplicationContext(),R.drawable.bitter))
                .visible(false));
        if (SphericalUtil.computeDistanceBetween(latLng,bitter3.getPosition()) < 200000) {
            bitter3.setVisible(true);
        }
        String id3 = bitter3.getId();
        markerMap.put(id3,"action_bitter");

        Marker bitter4 = mMap.addMarker(new MarkerOptions()
                .position(new LatLng(21.5143576,70.4342862))
                .title("Bitter Gourd")
                .snippet("Junagadh,Gujarat")
                .rotation((float) 0.5)
                .icon(bitmapDescriptorFromVector(getApplicationContext(),R.drawable.bitter))
                .visible(false));
        if (SphericalUtil.computeDistanceBetween(latLng,bitter4.getPosition()) < 200000) {
            bitter4.setVisible(true);
        }
        String id4 = bitter4.getId();
        markerMap.put(id4,"action_bitter");

        Marker bitter5 = mMap.addMarker(new MarkerOptions()
                .position(new LatLng(26.938663, 75.900492))
                .title("Bitter Gourd")
                .snippet("Jaipur,Rajasthan")
                .rotation((float) 0.5)
                .icon(bitmapDescriptorFromVector(getApplicationContext(),R.drawable.bitter))
                .visible(false));
        if (SphericalUtil.computeDistanceBetween(latLng,bitter5.getPosition()) < 200000) {
            bitter5.setVisible(true);
        }
        String id5 = bitter5.getId();
        markerMap.put(id5,"action_bitter");

        Marker bitter6 = mMap.addMarker(new MarkerOptions()
                .position(new LatLng(31.376087, 75.531180))
                .title("Bitter Gourd")
                .snippet("Jalandar,Punjab")
                .rotation((float) 0.5)
                .icon(bitmapDescriptorFromVector(getApplicationContext(),R.drawable.bitter))
                .visible(false));
        if (SphericalUtil.computeDistanceBetween(latLng,bitter6.getPosition()) < 200000) {
            bitter6.setVisible(true);
        }
        String id6 = bitter6.getId();
        markerMap.put(id6,"action_bitter");

        Marker bitter7 = mMap.addMarker(new MarkerOptions()
                .position(new LatLng(11.758306,79.6703561))
                .title("Bitter Gourd")
                .snippet("Cuddalore,Tamil Nadu")
                .rotation((float) 0.5)
                .icon(bitmapDescriptorFromVector(getApplicationContext(),R.drawable.bitter))
                .visible(false));
        if (SphericalUtil.computeDistanceBetween(latLng,bitter7.getPosition()) < 200000) {
            bitter7.setVisible(true);
        }
        String id7 = bitter7.getId();
        markerMap.put(id7,"action_bitter");

        Marker bitter8 = mMap.addMarker(new MarkerOptions()
                .position(new LatLng(8.5450713,76.9122998))
                .title("Bitter Gourd")
                .snippet("Thiruvanthapuram,Kerala")
                .rotation((float) 0.5)
                .icon(bitmapDescriptorFromVector(getApplicationContext(),R.drawable.bitter))
                .visible(false));
        if (SphericalUtil.computeDistanceBetween(latLng,bitter8.getPosition()) < 200000) {
            bitter8.setVisible(true);
        }
        String id8 = bitter8.getId();
        markerMap.put(id8,"action_bitter");

        Marker bitter9 = mMap.addMarker(new MarkerOptions()
                .position(new LatLng(16.1776452,75.61501))
                .title("Bitter Gourd")
                .snippet("Bagalkot,Karnataka")
                .rotation((float) 0.5)
                .icon(bitmapDescriptorFromVector(getApplicationContext(),R.drawable.bitter))
                .visible(false));
        if (SphericalUtil.computeDistanceBetween(latLng,bitter9.getPosition()) < 200000) {
            bitter9.setVisible(true);
        }
        String id9 = bitter9.getId();
        markerMap.put(id9,"action_bitter");

        Marker bitter10 = mMap.addMarker(new MarkerOptions()
                .position(new LatLng(22.622005, 87.586422))
                .title("Bitter Gourd")
                .snippet("Paschim Medinipur,West Bengal")
                .rotation((float) 0.5)
                .icon(bitmapDescriptorFromVector(getApplicationContext(),R.drawable.bitter))
                .visible(false));
        if (SphericalUtil.computeDistanceBetween(latLng,bitter10.getPosition()) < 200000) {
            bitter10.setVisible(true);
        }
        String id10 = bitter10.getId();
        markerMap.put(id10,"action_bitter");

        Marker bitter11 = mMap.addMarker(new MarkerOptions()
                .position(new LatLng(20.121101, 85.093024))
                .title("Bitter Gourd")
                .snippet("Nayagarh,Odisha")
                .rotation((float) 0.5)
                .icon(bitmapDescriptorFromVector(getApplicationContext(),R.drawable.bitter))
                .visible(false));
        if (SphericalUtil.computeDistanceBetween(latLng,bitter11.getPosition()) < 200000) {
            bitter11.setVisible(true);
        }
        String id11 = bitter11.getId();
        markerMap.put(id11,"action_bitter");

        Marker bitter12 = mMap.addMarker(new MarkerOptions()
                .position(new LatLng(26.5027069,92.1928121))
                .title("Bitter Gourd")
                .snippet("Darrang,Assam")
                .rotation((float) 0.5)
                .icon(bitmapDescriptorFromVector(getApplicationContext(),R.drawable.bitter))
                .visible(false));
        if (SphericalUtil.computeDistanceBetween(latLng,bitter12.getPosition()) < 200000) {
            bitter12.setVisible(true);
        }
        String id12 = bitter12.getId();
        markerMap.put(id12,"action_bitter");

        Marker bitter13 = mMap.addMarker(new MarkerOptions()
                .position(new LatLng(26.880645, 80.870557))
                .title("Bitter Gourd")
                .snippet("Lucknow,Uttar Pradesh")
                .rotation((float) 0.5)
                .icon(bitmapDescriptorFromVector(getApplicationContext(),R.drawable.bitter))
                .visible(false));
        if (SphericalUtil.computeDistanceBetween(latLng,bitter13.getPosition()) < 200000) {
            bitter13.setVisible(true);
        }
        String id13 = bitter13.getId();
        markerMap.put(id13,"action_bitter");

        Marker bitter14 = mMap.addMarker(new MarkerOptions()
                .position(new LatLng(26.1388739,85.5993063))
                .title("Bitter Gourd")
                .snippet("Muzaffarpur,Bihar")
                .rotation((float) 0.5)
                .icon(bitmapDescriptorFromVector(getApplicationContext(),R.drawable.bitter))
                .visible(false));
        if (SphericalUtil.computeDistanceBetween(latLng,bitter14.getPosition()) < 200000) {
            bitter14.setVisible(true);
        }
        String id14 = bitter14.getId();
        markerMap.put(id14,"action_bitter");

        //chillies
        Marker chilli1 = mMap.addMarker(new MarkerOptions()
                .position(new LatLng( 16.5721152,80.8147065))
                .title("Chillies")
                .snippet("Krishna,Andhra Pradesh")
                .rotation((float) 0.5)
                .icon(bitmapDescriptorFromVector(getApplicationContext(),R.drawable.chilly))
                .visible(false));
        if (SphericalUtil.computeDistanceBetween(latLng,chilli1.getPosition()) < 200000) {
            chilli1.setVisible(true);
        }
        String id15 = chilli1.getId();
        markerMap.put(id15,"action_chilli");

        Marker chilli2 = mMap.addMarker(new MarkerOptions()
                .position(new LatLng( 17.743230,76.358023))
                .title("Chillies")
                .snippet("Solapur,Maharashtra")
                .rotation((float) 0.5)
                .icon(bitmapDescriptorFromVector(getApplicationContext(),R.drawable.chilly))
                .visible(false));
        if (SphericalUtil.computeDistanceBetween(latLng,chilli2.getPosition()) < 200000) {
            chilli2.setVisible(true);
        }
        String id16 = chilli2.getId();
        markerMap.put(id16,"action_chilli");

        Marker chilli3 = mMap.addMarker(new MarkerOptions()
                .position(new LatLng( 21.8529856,72.186655))
                .title("Chillies")
                .snippet("Bhavnagar,Gujarat")
                .rotation((float) 0.5)
                .icon(bitmapDescriptorFromVector(getApplicationContext(),R.drawable.chilly))
                .visible(false));
        if (SphericalUtil.computeDistanceBetween(latLng,chilli3.getPosition()) < 200000) {
            chilli3.setVisible(true);
        }
        String id17 = chilli3.getId();
        markerMap.put(id17,"action_chilli");

        Marker chilli4 = mMap.addMarker(new MarkerOptions()
                .position(new LatLng( 14.9158796,79.9470888))
                .title("Chillies")
                .snippet("Nellore,Andhra Pradesh")
                .rotation((float) 0.5)
                .icon(bitmapDescriptorFromVector(getApplicationContext(),R.drawable.chilly))
                .visible(false));
        if (SphericalUtil.computeDistanceBetween(latLng,chilli4.getPosition()) < 200000) {
            chilli4.setVisible(true);
        }
        String id18 = chilli4.getId();
        markerMap.put(id18,"action_chilli");

        Marker chilli5 = mMap.addMarker(new MarkerOptions()
                .position(new LatLng( 26.0000704,85.3798459))
                .title("Chillies")
                .snippet("Muzaffarpur,Bihar")
                .rotation((float) 0.5)
                .icon(bitmapDescriptorFromVector(getApplicationContext(),R.drawable.chilly))
                .visible(false));
        if (SphericalUtil.computeDistanceBetween(latLng,chilli5.getPosition()) < 200000) {
            chilli5.setVisible(true);
        }
        String id19 = chilli5.getId();
        markerMap.put(id19,"action_chilli");

        Marker chilli6 = mMap.addMarker(new MarkerOptions()
                .position(new LatLng( 25.728502,85.3146136))
                .title("Chillies")
                .snippet("Vaishali,Bihar")
                .rotation((float) 0.5)
                .icon(bitmapDescriptorFromVector(getApplicationContext(),R.drawable.chilly))
                .visible(false));
        if (SphericalUtil.computeDistanceBetween(latLng,chilli6.getPosition()) < 200000) {
            chilli6.setVisible(true);
        }
        String id20 = chilli6.getId();
        markerMap.put(id20,"action_chilli");

        Marker chilli7 = mMap.addMarker(new MarkerOptions()
                .position(new LatLng( 25.5917546,85.1287018))
                .title("Chillies")
                .snippet("Patna,Bihar")
                .rotation((float) 0.5)
                .icon(bitmapDescriptorFromVector(getApplicationContext(),R.drawable.chilly))
                .visible(false));
        if (SphericalUtil.computeDistanceBetween(latLng,chilli7.getPosition()) < 200000) {
            chilli7.setVisible(true);
        }
        String id21 = chilli7.getId();
        markerMap.put(id21,"action_chilli");

        Marker chilli8 = mMap.addMarker(new MarkerOptions()
                .position(new LatLng( 25.5917546,85.1287018))
                .title("Chillies")
                .snippet("Patna,Bihar")
                .rotation((float) 0.5)
                .icon(bitmapDescriptorFromVector(getApplicationContext(),R.drawable.chilly))
                .visible(false));
        if (SphericalUtil.computeDistanceBetween(latLng,chilli8.getPosition()) < 200000) {
            chilli8.setVisible(true);
        }
        String id22 = chilli8.getId();
        markerMap.put(id22,"action_chilli");

        Marker chilli9 = mMap.addMarker(new MarkerOptions()
                .position(new LatLng( 23.835437,85.9400771))
                .title("Chillies")
                .snippet("Palamu,Jharkhand")
                .rotation((float) 0.5)
                .icon(bitmapDescriptorFromVector(getApplicationContext(),R.drawable.chilly))
                .visible(false));
        if (SphericalUtil.computeDistanceBetween(latLng,chilli9.getPosition()) < 200000) {
            chilli9.setVisible(true);
        }
        String id23 = chilli9.getId();
        markerMap.put(id23,"action_chilli");

        Marker chilli10 = mMap.addMarker(new MarkerOptions()
                .position(new LatLng( 15.8231769,74.4692489))
                .title("Chillies")
                .snippet("Belgaum,Karnataka")
                .rotation((float) 0.5)
                .icon(bitmapDescriptorFromVector(getApplicationContext(),R.drawable.chilly))
                .visible(false));
        if (SphericalUtil.computeDistanceBetween(latLng,chilli10.getPosition()) < 200000) {
            chilli10.setVisible(true);
        }
        String id24 = chilli10.getId();
        markerMap.put(id24,"action_chilli");

        Marker chilli11 = mMap.addMarker(new MarkerOptions()
                .position(new LatLng( 14.7908863,75.4137576))
                .title("Chillies")
                .snippet("Haveri,Karnataka")
                .rotation((float) 0.5)
                .icon(bitmapDescriptorFromVector(getApplicationContext(),R.drawable.chilly))
                .visible(false));
        if (SphericalUtil.computeDistanceBetween(latLng,chilli11.getPosition()) < 200000) {
            chilli11.setVisible(true);
        }
        String id25 = chilli11.getId();
        markerMap.put(id25,"action_chilli");

        Marker chilli12 = mMap.addMarker(new MarkerOptions()
                .position(new LatLng( 13.9285953,76.3238332))
                .title("Chillies")
                .snippet("Chitradurga,Karnataka")
                .rotation((float) 0.5)
                .icon(bitmapDescriptorFromVector(getApplicationContext(),R.drawable.chilly))
                .visible(false));
        if (SphericalUtil.computeDistanceBetween(latLng,chilli12.getPosition()) < 200000) {
            chilli12.setVisible(true);
        }
        String id26 = chilli12.getId();
        markerMap.put(id26,"action_chilli");

        Marker chilli13 = mMap.addMarker(new MarkerOptions()
                .position(new LatLng( 11.7059314,76.0892638))
                .title("Chillies")
                .snippet("Wayanad,Kerala")
                .rotation((float) 0.5)
                .icon(bitmapDescriptorFromVector(getApplicationContext(),R.drawable.chilly))
                .visible(false));
        if (SphericalUtil.computeDistanceBetween(latLng,chilli13.getPosition()) < 200000) {
            chilli13.setVisible(true);
        }
        String id27 = chilli13.getId();
        markerMap.put(id27,"action_chilli");

        Marker chilli14 = mMap.addMarker(new MarkerOptions()
                .position(new LatLng( 9.9301089,76.329705))
                .title("Chillies")
                .snippet("Ernakulam,Kerala")
                .rotation((float) 0.5)
                .icon(bitmapDescriptorFromVector(getApplicationContext(),R.drawable.chilly))
                .visible(false));
        if (SphericalUtil.computeDistanceBetween(latLng,chilli14.getPosition()) < 200000) {
            chilli14.setVisible(true);
        }
        String id28 = chilli14.getId();
        markerMap.put(id28,"action_chilli");

        Marker chilli15 = mMap.addMarker(new MarkerOptions()
                .position(new LatLng( 10.7997999,76.6184431))
                .title("Chillies")
                .snippet("Palakkad,Kerala")
                .rotation((float) 0.5)
                .icon(bitmapDescriptorFromVector(getApplicationContext(),R.drawable.chilly))
                .visible(false));
        if (SphericalUtil.computeDistanceBetween(latLng,chilli15.getPosition()) < 200000) {
            chilli15.setVisible(true);
        }
        String id29 = chilli15.getId();
        markerMap.put(id29,"action_chilli");

        Marker chilli16 = mMap.addMarker(new MarkerOptions()
                .position(new LatLng( 30.660071,76.393922))
                .title("Chillies")
                .snippet("Fatehgarh Sahib,Punjab")
                .rotation((float) 0.5)
                .icon(bitmapDescriptorFromVector(getApplicationContext(),R.drawable.chilly))
                .visible(false));
        if (SphericalUtil.computeDistanceBetween(latLng,chilli16.getPosition()) < 200000) {
            chilli16.setVisible(true);
        }
        String id30 = chilli16.getId();
        markerMap.put(id30,"action_chilli");

        Marker chilli17 = mMap.addMarker(new MarkerOptions()
                .position(new LatLng( 30.299452,76.343685))
                .title("Chillies")
                .snippet("Patiala,Punjab")
                .rotation((float) 0.5)
                .icon(bitmapDescriptorFromVector(getApplicationContext(),R.drawable.chilly))
                .visible(false));
        if (SphericalUtil.computeDistanceBetween(latLng,chilli17.getPosition()) < 200000) {
            chilli17.setVisible(true);
        }
        String id31 = chilli17.getId();
        markerMap.put(id31,"action_chilli");

        Marker chilli18 = mMap.addMarker(new MarkerOptions()
                .position(new LatLng(27.212677,73.749994))
                .title("Chillies")
                .snippet("Nagaur,Rajasthan")
                .rotation((float) 0.5)
                .icon(bitmapDescriptorFromVector(getApplicationContext(),R.drawable.chilly))
                .visible(false));
        if (SphericalUtil.computeDistanceBetween(latLng,chilli18.getPosition()) < 200000) {
            chilli18.setVisible(true);
        }
        String id32 = chilli18.getId();
        markerMap.put(id32,"action_chilli");

        Marker chilli19 = mMap.addMarker(new MarkerOptions()
                .position(new LatLng(25.760550,73.344324))
                .title("Chillies")
                .snippet("Pali,Rajasthan")
                .rotation((float) 0.5)
                .icon(bitmapDescriptorFromVector(getApplicationContext(),R.drawable.chilly))
                .visible(false));
        if (SphericalUtil.computeDistanceBetween(latLng,chilli19.getPosition()) < 200000) {
            chilli19.setVisible(true);
        }
        String id33 = chilli19.getId();
        markerMap.put(id33,"action_chilli");

        Marker chilli20 = mMap.addMarker(new MarkerOptions()
                .position(new LatLng(8.750034,77.766482))
                .title("Chillies")
                .snippet("Tirunelveli,Tamil Nadu")
                .rotation((float) 0.5)
                .icon(bitmapDescriptorFromVector(getApplicationContext(),R.drawable.chilly))
                .visible(false));
        if (SphericalUtil.computeDistanceBetween(latLng,chilli20.getPosition()) < 200000) {
            chilli20.setVisible(true);
        }
        String id34 = chilli20.getId();
        markerMap.put(id34,"action_chilli");

        Marker chilli21 = mMap.addMarker(new MarkerOptions()
                .position(new LatLng(9.562338,77.971782))
                .title("Chillies")
                .snippet("Virudhunagar,Tamil Nadu")
                .rotation((float) 0.5)
                .icon(bitmapDescriptorFromVector(getApplicationContext(),R.drawable.chilly))
                .visible(false));
        if (SphericalUtil.computeDistanceBetween(latLng,chilli21.getPosition()) < 200000) {
            chilli21.setVisible(true);
        }
        String id35 = chilli21.getId();
        markerMap.put(id35,"action_chilli");

        Marker chilli22 = mMap.addMarker(new MarkerOptions()
                .position(new LatLng(10.750193,79.608814))
                .title("Chillies")
                .snippet("Nagapattinam,Tamil Nadu")
                .rotation((float) 0.5)
                .icon(bitmapDescriptorFromVector(getApplicationContext(),R.drawable.chilly))
                .visible(false));
        if (SphericalUtil.computeDistanceBetween(latLng,chilli22.getPosition()) < 200000) {
            chilli22.setVisible(true);
        }
        String id36 = chilli22.getId();
        markerMap.put(id36,"action_chilli");

        Marker chilli23 = mMap.addMarker(new MarkerOptions()
                .position(new LatLng(26.542259,88.657778))
                .title("Chillies")
                .snippet("Jalpaiguri,West Bengal")
                .rotation((float) 0.5)
                .icon(bitmapDescriptorFromVector(getApplicationContext(),R.drawable.chilly))
                .visible(false));
        if (SphericalUtil.computeDistanceBetween(latLng,chilli23.getPosition()) < 200000) {
            chilli23.setVisible(true);
        }
        String id37 = chilli23.getId();
        markerMap.put(id37,"action_chilli");

        Marker chilli24 = mMap.addMarker(new MarkerOptions()
                .position(new LatLng(27.327495,82.845870))
                .title("Chillies")
                .snippet("Siddhart nagar,Uttar Pradesh")
                .rotation((float) 0.5)
                .icon(bitmapDescriptorFromVector(getApplicationContext(),R.drawable.chilly))
                .visible(false));
        if (SphericalUtil.computeDistanceBetween(latLng,chilli24.getPosition()) < 200000) {
            chilli24.setVisible(true);
        }
        String id38 = chilli24.getId();
        markerMap.put(id38,"action_chilli");

        Marker chilli25 = mMap.addMarker(new MarkerOptions()
                .position(new LatLng(24.6287944,77.3137289))
                .title("Chillies")
                .snippet("Guna,Madhya Pradesh")
                .rotation((float) 0.5)
                .icon(bitmapDescriptorFromVector(getApplicationContext(),R.drawable.chilly))
                .visible(false));
        if (SphericalUtil.computeDistanceBetween(latLng,chilli25.getPosition()) < 200000) {
            chilli25.setVisible(true);
        }
        String id39 = chilli25.getId();
        markerMap.put(id39,"action_chilli");

        //coffee
        Marker coffee1 = mMap.addMarker(new MarkerOptions()
                .position(new LatLng(15.33671,76.1714945 ))
                .title("Coffee")
                .snippet("Koppal,Karnataka")
                .rotation((float) 0.5)
                .icon(bitmapDescriptorFromVector(getApplicationContext(),R.drawable.coffee))
                .visible(false));
        if (SphericalUtil.computeDistanceBetween(latLng,coffee1.getPosition()) < 200000) {
            coffee1.setVisible(true);
        }
        String id40 = coffee1.getId();
        markerMap.put(id40,"action_coffee");

        Marker coffee2 = mMap.addMarker(new MarkerOptions()
                .position(new LatLng(9.143953,76.880594))
                .title("Coffee")
                .snippet("Pathanamthitta,Kerala")
                .rotation((float) 0.5)
                .icon(bitmapDescriptorFromVector(getApplicationContext(),R.drawable.coffee))
                .visible(false));
        if (SphericalUtil.computeDistanceBetween(latLng,coffee2.getPosition()) < 200000) {
            coffee2.setVisible(true);
        }
        String id41 = coffee2.getId();
        markerMap.put(id41,"action_coffee");

        Marker coffee3 = mMap.addMarker(new MarkerOptions()
                .position(new LatLng(17.745857,83.3146099))
                .title("Coffee")
                .snippet("Visakhapatnam,Andhra Pradesh")
                .rotation((float) 0.5)
                .icon(bitmapDescriptorFromVector(getApplicationContext(),R.drawable.coffee))
                .visible(false));
        if (SphericalUtil.computeDistanceBetween(latLng,coffee3.getPosition()) < 200000) {
            coffee3.setVisible(true);
        }
        String id42 = coffee3.getId();
        markerMap.put(id42,"action_coffee");

        Marker coffee4 = mMap.addMarker(new MarkerOptions()
                .position(new LatLng(11.9896583,76.6221921))
                .title("Coffee")
                .snippet("Mysore,Karnataka")
                .rotation((float) 0.5)
                .icon(bitmapDescriptorFromVector(getApplicationContext(),R.drawable.coffee))
                .visible(false));
        if (SphericalUtil.computeDistanceBetween(latLng,coffee4.getPosition()) < 200000) {
            coffee4.setVisible(true);
        }
        String id43 = coffee4.getId();
        markerMap.put(id43,"action_coffee");

        Marker coffee5 = mMap.addMarker(new MarkerOptions()
                .position(new LatLng(9.5901707,76.5065346))
                .title("Coffee")
                .snippet("Kottayam,Kerala")
                .rotation((float) 0.5)
                .icon(bitmapDescriptorFromVector(getApplicationContext(),R.drawable.coffee))
                .visible(false));
        if (SphericalUtil.computeDistanceBetween(latLng,coffee5.getPosition()) < 200000) {
            coffee5.setVisible(true);
        }
        String id44 = coffee5.getId();
        markerMap.put(id44,"action_coffee");

        Marker coffee6 = mMap.addMarker(new MarkerOptions()
                .position(new LatLng(11.813559,79.030833))
                .title("Coffee")
                .snippet("Thiruvannamalai,Tamil nadu")
                .rotation((float) 0.5)
                .icon(bitmapDescriptorFromVector(getApplicationContext(),R.drawable.coffee))
                .visible(false));
        if (SphericalUtil.computeDistanceBetween(latLng,coffee6.getPosition()) < 200000) {
            coffee6.setVisible(true);
        }
        String id45 = coffee6.getId();
        markerMap.put(id45,"action_coffee");

        Marker coffee7 = mMap.addMarker(new MarkerOptions()
                .position(new LatLng(18.110086,78.403759))
                .title("Coffee")
                .snippet("Medak,Telangana")
                .rotation((float) 0.5)
                .icon(bitmapDescriptorFromVector(getApplicationContext(),R.drawable.coffee))
                .visible(false));
        if (SphericalUtil.computeDistanceBetween(latLng,coffee7.getPosition()) < 200000) {
            coffee7.setVisible(true);
        }
        String id46 = coffee7.getId();
        markerMap.put(id46,"action_coffee");

        Marker coffee8 = mMap.addMarker(new MarkerOptions()
                .position(new LatLng(21.342638, 83.646271))
                .title("Coffee")
                .snippet("Bargarh,Odisha")
                .rotation((float) 0.5)
                .icon(bitmapDescriptorFromVector(getApplicationContext(),R.drawable.coffee))
                .visible(false));
        if (SphericalUtil.computeDistanceBetween(latLng,coffee8.getPosition()) < 200000) {
            coffee8.setVisible(true);
        }
        String id47 = coffee8.getId();
        markerMap.put(id47,"action_coffee");

        Marker coffee9 = mMap.addMarker(new MarkerOptions()
                .position(new LatLng(26.4432382,91.6276435))
                .title("Coffee")
                .snippet("Kamrup,Assam")
                .rotation((float) 0.5)
                .icon(bitmapDescriptorFromVector(getApplicationContext(),R.drawable.coffee))
                .visible(false));
        if (SphericalUtil.computeDistanceBetween(latLng,coffee9.getPosition()) < 200000) {
            coffee9.setVisible(true);
        }
        String id48 = coffee9.getId();
        markerMap.put(id48,"action_coffee");

        Marker coffee10 = mMap.addMarker(new MarkerOptions()
                .position(new LatLng(25.7019388,93.848571))
                .title("Coffee")
                .snippet("Dimapur,Nagaland")
                .rotation((float) 0.5)
                .icon(bitmapDescriptorFromVector(getApplicationContext(),R.drawable.coffee))
                .visible(false));
        if (SphericalUtil.computeDistanceBetween(latLng,coffee10.getPosition()) < 200000) {
            coffee10.setVisible(true);
        }
        String id49 = coffee10.getId();
        markerMap.put(id49,"action_coffee");

        Marker coffee11 = mMap.addMarker(new MarkerOptions()
                .position(new LatLng(24.8035857,93.8991926))
                .title("Coffee")
                .snippet("Imphal,Manipur")
                .rotation((float) 0.5)
                .icon(bitmapDescriptorFromVector(getApplicationContext(),R.drawable.coffee))
                .visible(false));
        if (SphericalUtil.computeDistanceBetween(latLng,coffee11.getPosition()) < 200000) {
            coffee11.setVisible(true);
        }
        String id50 = coffee11.getId();
        markerMap.put(id50,"action_coffee");

        Marker coffee12 = mMap.addMarker(new MarkerOptions()
                .position(new LatLng(27.9826308,94.6813279))
                .title("Coffee")
                .snippet("Lepa-Rada,Arunachal Pradesh")
                .rotation((float) 0.5)
                .icon(bitmapDescriptorFromVector(getApplicationContext(),R.drawable.coffee))
                .visible(false));
        if (SphericalUtil.computeDistanceBetween(latLng,coffee12.getPosition()) < 200000) {
            coffee12.setVisible(true);
        }
        String id51 = coffee12.getId();
        markerMap.put(id51,"action_coffee");

        Marker coffee13 = mMap.addMarker(new MarkerOptions()
                .position(new LatLng(23.2769632,92.7712328))
                .title("Coffee")
                .snippet("Serchhip,Mizoram")
                .rotation((float) 0.5)
                .icon(bitmapDescriptorFromVector(getApplicationContext(),R.drawable.coffee))
                .visible(false));
        if (SphericalUtil.computeDistanceBetween(latLng,coffee13.getPosition()) < 200000) {
            coffee13.setVisible(true);
        }
        String id52 = coffee13.getId();
        markerMap.put(id52,"action_coffee");

        //Cotton
        Marker cotton1 = mMap.addMarker(new MarkerOptions()
                .position(new LatLng(30.936965,75.906644))
                .title("Cotton")
                .snippet("Ludhiana,Punjab")
                .rotation((float)0.5)
                .icon(bitmapDescriptorFromVector(getApplicationContext(),R.drawable.cotton1))
                .visible(false));
        if (SphericalUtil.computeDistanceBetween(latLng,cotton1.getPosition()) < 200000) {
            cotton1.setVisible(true);
        }
        String id53 = cotton1.getId();
        markerMap.put(id53,"action_cotton");

        Marker cotton2 = mMap.addMarker(new MarkerOptions()
                .position(new LatLng(21.703803,73.011676))
                .title("Cotton")
                .snippet("Bharuch,Gujarat")
                .rotation((float) 0.5)
                .icon(bitmapDescriptorFromVector(getApplicationContext(),R.drawable.cotton1))
                .visible(false));
        if (SphericalUtil.computeDistanceBetween(latLng,cotton2.getPosition()) < 200000) {
            cotton2.setVisible(true);
        }
        String id54 = cotton2.getId();
        markerMap.put(id54,"action_cotton");

        Marker cotton3 = mMap.addMarker(new MarkerOptions()
                .position(new LatLng(20.9642836,77.9191293))
                .title("Cotton")
                .snippet("Amravati,Maharashtra")
                .rotation((float) 0.5)
                .icon(bitmapDescriptorFromVector(getApplicationContext(),R.drawable.cotton1))
                .visible(false));
        if (SphericalUtil.computeDistanceBetween(latLng,cotton3.getPosition()) < 200000) {
            cotton3.setVisible(true);
        }
        String id55 = cotton3.getId();
        markerMap.put(id55,"action_cotton");

        Marker cotton4 = mMap.addMarker(new MarkerOptions()
                .position(new LatLng(20.0988268,77.1208336))
                .title("Cotton")
                .snippet("Washim,Maharashtra")
                .rotation((float) 0.5)
                .icon(bitmapDescriptorFromVector(getApplicationContext(),R.drawable.cotton1))
                .visible(false));
        if (SphericalUtil.computeDistanceBetween(latLng,cotton4.getPosition()) < 200000) {
            cotton4.setVisible(true);
        }
        String id56 = cotton4.getId();
        markerMap.put(id56,"action_cotton");

        Marker cotton5 = mMap.addMarker(new MarkerOptions()
                .position(new LatLng(22.992514,72.5238699))
                .title("Cotton")
                .snippet("Ahmedabad,Gujarat")
                .rotation((float) 0.5)
                .icon(bitmapDescriptorFromVector(getApplicationContext(),R.drawable.cotton1))
                .visible(false));
        if (SphericalUtil.computeDistanceBetween(latLng,cotton5.getPosition()) < 200000) {
            cotton5.setVisible(true);
        }
        String id57 = cotton5.getId();
        markerMap.put(id57,"action_cotton");

        Marker cotton6 = mMap.addMarker(new MarkerOptions()
                .position(new LatLng(21.6618867,69.6137261))
                .title("Cotton")
                .snippet("Porbandar,Gujarat")
                .rotation((float) 0.5)
                .icon(bitmapDescriptorFromVector(getApplicationContext(),R.drawable.cotton1))
                .visible(false));
        if (SphericalUtil.computeDistanceBetween(latLng,cotton6.getPosition()) < 200000) {
            cotton6.setVisible(true);
        }
        String id58 = cotton6.getId();
        markerMap.put(id58,"action_cotton");

        Marker cotton7 = mMap.addMarker(new MarkerOptions()
                .position(new LatLng(22.7192407,71.6191697))
                .title("Cotton")
                .snippet("Surendranagar,Gujarat")
                .rotation((float) 0.5)
                .icon(bitmapDescriptorFromVector(getApplicationContext(),R.drawable.cotton1))
                .visible(false));
        if (SphericalUtil.computeDistanceBetween(latLng,cotton7.getPosition()) < 200000) {
            cotton7.setVisible(true);
        }
        String id59 = cotton7.getId();
        markerMap.put(id59,"action_cotton");

        Marker cotton8 = mMap.addMarker(new MarkerOptions()
                .position(new LatLng(21.6037204,71.2267004))
                .title("Cotton")
                .snippet("Amreli,Gujarat")
                .rotation((float) 0.5)
                .icon(bitmapDescriptorFromVector(getApplicationContext(),R.drawable.cotton1))
                .visible(false));
        if (SphericalUtil.computeDistanceBetween(latLng,cotton8.getPosition()) < 200000) {
            cotton8.setVisible(true);
        }
        String id60 = cotton8.getId();
        markerMap.put(id60,"action_cotton");

        Marker cotton9 = mMap.addMarker(new MarkerOptions()
                .position(new LatLng(21.7822891,72.0007483))
                .title("Cotton")
                .snippet("Bhavnagar,Gujarat")
                .rotation((float) 0.5)
                .icon(bitmapDescriptorFromVector(getApplicationContext(),R.drawable.cotton1))
                .visible(false));
        if (SphericalUtil.computeDistanceBetween(latLng,cotton9.getPosition()) < 200000) {
            cotton9.setVisible(true);
        }
        String id61 = cotton9.getId();
        markerMap.put(id61,"action_cotton");

        Marker cotton10 = mMap.addMarker(new MarkerOptions()
                .position(new LatLng(16.6201881,80.392938))
                .title("Cotton")
                .snippet("Guntur,Andhra Pradesh")
                .rotation((float) 0.5)
                .icon(bitmapDescriptorFromVector(getApplicationContext(),R.drawable.cotton1))
                .visible(false));
        if (SphericalUtil.computeDistanceBetween(latLng,cotton10.getPosition()) < 200000) {
            cotton10.setVisible(true);
        }
        String id62 = cotton10.getId();
        markerMap.put(id62,"action_cotton");

        Marker cotton11 = mMap.addMarker(new MarkerOptions()
                .position(new LatLng(14.5188676,77.6209084))
                .title("Cotton")
                .snippet("Anantapur,Andhra Pradesh")
                .rotation((float) 0.5)
                .icon(bitmapDescriptorFromVector(getApplicationContext(),R.drawable.cotton1))
                .visible(false));
        if (SphericalUtil.computeDistanceBetween(latLng,cotton11.getPosition()) < 200000) {
            cotton11.setVisible(true);
        }
        String id63 = cotton11.getId();
        markerMap.put(id63,"action_cotton");

        Marker cotton12 = mMap.addMarker(new MarkerOptions()
                .position(new LatLng(14.3681261,78.3869848))
                .title("Cotton")
                .snippet("YSR Kadapa,Andhra Pradesh")
                .rotation((float) 0.5)
                .icon(bitmapDescriptorFromVector(getApplicationContext(),R.drawable.cotton1))
                .visible(false));
        if (SphericalUtil.computeDistanceBetween(latLng,cotton12.getPosition()) < 200000) {
            cotton12.setVisible(true);
        }
        String id64 = cotton12.getId();
        markerMap.put(id64,"action_cotton");

        Marker cotton13 = mMap.addMarker(new MarkerOptions()
                .position(new LatLng(25.9029465,87.4968142))
                .title("Cotton")
                .snippet("Purnia,Bihar")
                .rotation((float) 0.5)
                .icon(bitmapDescriptorFromVector(getApplicationContext(),R.drawable.cotton1))
                .visible(false));
        if (SphericalUtil.computeDistanceBetween(latLng,cotton13.getPosition()) < 200000) {
            cotton13.setVisible(true);
        }
        String id65 = cotton13.getId();
        markerMap.put(id65,"action_cotton");

        Marker cotton14 = mMap.addMarker(new MarkerOptions()
                .position(new LatLng(15.4131182,75.6058384))
                .title("Cotton")
                .snippet("Gadag,Karnataka")
                .rotation((float) 0.5)
                .icon(bitmapDescriptorFromVector(getApplicationContext(),R.drawable.cotton1))
                .visible(false));
        if (SphericalUtil.computeDistanceBetween(latLng,cotton14.getPosition()) < 200000) {
            cotton14.setVisible(true);
        }
        String id66 = cotton14.getId();
        markerMap.put(id66,"action_cotton");

        Marker cotton15 = mMap.addMarker(new MarkerOptions()
                .position(new LatLng(16.2345654,77.3364226))
                .title("Cotton")
                .snippet("Raichur,Karnataka")
                .rotation((float) 0.5)
                .icon(bitmapDescriptorFromVector(getApplicationContext(),R.drawable.cotton1))
                .visible(false));
        if (SphericalUtil.computeDistanceBetween(latLng,cotton15.getPosition()) < 200000) {
            cotton15.setVisible(true);
        }
        String id67 = cotton15.getId();
        markerMap.put(id67,"action_cotton");

        Marker cotton16 = mMap.addMarker(new MarkerOptions()
                .position(new LatLng(17.3645509,76.8161156))
                .title("Cotton")
                .snippet("Gulbarga,Karnataka")
                .rotation((float) 0.5)
                .icon(bitmapDescriptorFromVector(getApplicationContext(),R.drawable.cotton1))
                .visible(false));
        if (SphericalUtil.computeDistanceBetween(latLng,cotton16.getPosition()) < 200000) {
            cotton16.setVisible(true);
        }
        String id68 = cotton16.getId();
        markerMap.put(id68,"action_cotton");

        Marker cotton17 = mMap.addMarker(new MarkerOptions()
                .position(new LatLng(30.473139,74.488159))
                .title("Cotton")
                .snippet("Sri Muktsar Sahib,Punjab")
                .rotation((float) 0.5)
                .icon(bitmapDescriptorFromVector(getApplicationContext(),R.drawable.cotton1))
                .visible(false));
        if (SphericalUtil.computeDistanceBetween(latLng,cotton17.getPosition()) < 200000) {
            cotton17.setVisible(true);
        }
        String id69 = cotton17.getId();
        markerMap.put(id69,"action_cotton");

        Marker cotton18 = mMap.addMarker(new MarkerOptions()
                .position(new LatLng(30.904472,74.605943))
                .title("Cotton")
                .snippet("Firozpur,Punjab")
                .rotation((float) 0.5)
                .icon(bitmapDescriptorFromVector(getApplicationContext(),R.drawable.cotton1))
                .visible(false));
        if (SphericalUtil.computeDistanceBetween(latLng,cotton18.getPosition()) < 200000) {
            cotton18.setVisible(true);
        }
        String id70 = cotton18.getId();
        markerMap.put(id70,"action_cotton");

        Marker cotton19 = mMap.addMarker(new MarkerOptions()
                .position(new LatLng(26.938663,75.700492))
                .title("Cotton")
                .snippet("Jaipur,Rajasthan")
                .rotation((float) 0.5)
                .icon(bitmapDescriptorFromVector(getApplicationContext(),R.drawable.cotton1))
                .visible(false));
        if (SphericalUtil.computeDistanceBetween(latLng,cotton19.getPosition()) < 200000) {
            cotton19.setVisible(true);
        }
        String id71 = cotton19.getId();
        markerMap.put(id71,"action_cotton");

        Marker cotton20 = mMap.addMarker(new MarkerOptions()
                .position(new LatLng(23.838828,74.452142))
                .title("Cotton")
                .snippet("Banswara,Rajasthan")
                .rotation((float) 0.5)
                .icon(bitmapDescriptorFromVector(getApplicationContext(),R.drawable.cotton1))
                .visible(false));
        if (SphericalUtil.computeDistanceBetween(latLng,cotton20.getPosition()) < 200000) {
            cotton20.setVisible(true);
        }
        String id72 = cotton20.getId();
        markerMap.put(id72,"action_cotton");

        Marker cotton21 = mMap.addMarker(new MarkerOptions()
                .position(new LatLng(24.878113,74.642495))
                .title("Cotton")
                .snippet("Chittorgarh,Rajasthan")
                .rotation((float) 0.5)
                .icon(bitmapDescriptorFromVector(getApplicationContext(),R.drawable.cotton1))
                .visible(false));
        if (SphericalUtil.computeDistanceBetween(latLng,cotton21.getPosition()) < 200000) {
            cotton21.setVisible(true);
        }
        String id73 = cotton21.getId();
        markerMap.put(id73,"action_cotton");

        Marker cotton22 = mMap.addMarker(new MarkerOptions()
                .position(new LatLng(11.137253,77.3909908))
                .title("Cotton")
                .snippet("Tiruppur,Tamil Nadu")
                .rotation((float) 0.5)
                .icon(bitmapDescriptorFromVector(getApplicationContext(),R.drawable.cotton1))
                .visible(false));
        if (SphericalUtil.computeDistanceBetween(latLng,cotton22.getPosition()) < 200000) {
            cotton22.setVisible(true);
        }
        String id74 = cotton22.getId();
        markerMap.put(id74,"action_cotton");

        Marker cotton23 = mMap.addMarker(new MarkerOptions()
                .position(new LatLng(10.358579,77.956068))
                .title("Cotton")
                .snippet("Dindigul,Tamil Nadu")
                .rotation((float) 0.5)
                .icon(bitmapDescriptorFromVector(getApplicationContext(),R.drawable.cotton1))
                .visible(false));
        if (SphericalUtil.computeDistanceBetween(latLng,cotton23.getPosition()) < 200000) {
            cotton23.setVisible(true);
        }
        String id75 = cotton23.getId();
        markerMap.put(id75,"action_cotton");

        Marker cotton24 = mMap.addMarker(new MarkerOptions()
                .position(new LatLng(8.085850,77.534731))
                .title("Cotton")
                .snippet("Kanyakumari,Tamil Nadu")
                .rotation((float) 0.5)
                .icon(bitmapDescriptorFromVector(getApplicationContext(),R.drawable.cotton1))
                .visible(false));
        if (SphericalUtil.computeDistanceBetween(latLng,cotton24.getPosition()) < 200000) {
            cotton24.setVisible(true);
        }
        String id76 = cotton24.getId();
        markerMap.put(id76,"action_cotton");

        Marker cotton25 = mMap.addMarker(new MarkerOptions()
                .position(new LatLng(23.9372909,92.2055373))
                .title("Cotton")
                .snippet("Khowai,Tripura")
                .rotation((float) 0.5)
                .icon(bitmapDescriptorFromVector(getApplicationContext(),R.drawable.cotton1))
                .visible(false));
        if (SphericalUtil.computeDistanceBetween(latLng,cotton25.getPosition()) < 200000) {
            cotton25.setVisible(true);
        }
        String id77 = cotton25.getId();
        markerMap.put(id77,"action_cotton");

        Marker cotton26 = mMap.addMarker(new MarkerOptions()
                .position(new LatLng(28.916407,77.681379))
                .title("Cotton")
                .snippet("Meerut,Uttar Pradesh")
                .rotation((float) 0.5)
                .icon(bitmapDescriptorFromVector(getApplicationContext(),R.drawable.cotton1))
                .visible(false));
        if (SphericalUtil.computeDistanceBetween(latLng,cotton26.getPosition()) < 200000) {
            cotton26.setVisible(true);
        }
        String id78 = cotton26.getId();
        markerMap.put(id78,"action_cotton");

        Marker cotton27 = mMap.addMarker(new MarkerOptions()
                .position(new LatLng(27.942964,78.143680))
                .title("Cotton")
                .snippet("Aligarh,Uttar Pradesh")
                .rotation((float) 0.5)
                .icon(bitmapDescriptorFromVector(getApplicationContext(),R.drawable.cotton1))
                .visible(false));
        if (SphericalUtil.computeDistanceBetween(latLng,cotton27.getPosition()) < 200000) {
            cotton27.setVisible(true);
        }
        String id79 = cotton27.getId();
        markerMap.put(id79,"action_cotton");

        Marker cotton28 = mMap.addMarker(new MarkerOptions()
                .position(new LatLng(23.445347,75.575178))
                .title("Cotton")
                .snippet("Ujjain,Madhya Pradesh")
                .rotation((float) 0.5)
                .icon(bitmapDescriptorFromVector(getApplicationContext(),R.drawable.cotton1))
                .visible(false));
        if (SphericalUtil.computeDistanceBetween(latLng,cotton28.getPosition()) < 200000) {
            cotton28.setVisible(true);
        }
        String id80 = cotton28.getId();
        markerMap.put(id80,"action_cotton");

        Marker cotton29 = mMap.addMarker(new MarkerOptions()
                .position(new LatLng(22.145669,77.233997))
                .title("Cotton")
                .snippet("Harda,Madhya Pradesh")
                .rotation((float) 0.5)
                .icon(bitmapDescriptorFromVector(getApplicationContext(),R.drawable.cotton1))
                .visible(false));
        if (SphericalUtil.computeDistanceBetween(latLng,cotton29.getPosition()) < 200000) {
            cotton29.setVisible(true);
        }
        String id81 = cotton29.getId();
        markerMap.put(id81,"action_cotton");

        Marker cotton30 = mMap.addMarker(new MarkerOptions()
                .position(new LatLng(25.790411,76.770744))
                .title("Cotton")
                .snippet("Sheopur,Madhya Pradesh")
                .rotation((float) 0.5)
                .icon(bitmapDescriptorFromVector(getApplicationContext(),R.drawable.cotton1))
                .visible(false));
        if (SphericalUtil.computeDistanceBetween(latLng,cotton30.getPosition()) < 200000) {
            cotton30.setVisible(true);
        }
        String id82 = cotton30.getId();
        markerMap.put(id82,"action_cotton");


        //methi
        Marker methi1 = mMap.addMarker(new MarkerOptions()
                .position(new LatLng( 24.960222,73.747137))
                .title("Fenugreek")
                .snippet("Rajsamand,Rajasthan")
                .rotation((float) 0.5)
                .icon(bitmapDescriptorFromVector(getApplicationContext(),R.drawable.methi))
                .visible(false));
        if (SphericalUtil.computeDistanceBetween(latLng,methi1.getPosition()) < 200000) {
            methi1.setVisible(true);
        }
        String id83 = methi1.getId();
        markerMap.put(id83,"action_methi");

        Marker methi2 = mMap.addMarker(new MarkerOptions()
                .position(new LatLng(11.465733,78.917556))
                .title("Fenugreek")
                .snippet("Cuddalore,Tamil Nadu")
                .rotation((float) 0.5)
                .icon(bitmapDescriptorFromVector(getApplicationContext(),R.drawable.methi))
                .visible(false));
        if (SphericalUtil.computeDistanceBetween(latLng,methi2.getPosition()) < 200000) {
            methi2.setVisible(true);
        }
        String id84 = methi2.getId();
        markerMap.put(id84,"action_methi");

        Marker methi3 = mMap.addMarker(new MarkerOptions()
                .position(new LatLng( 30.6949027,77.9681044))
                .title("Fenugreek")
                .snippet("Dehradun,Uttrakhand")
                .rotation((float) 0.5)
                .icon(bitmapDescriptorFromVector(getApplicationContext(),R.drawable.methi))
                .visible(false));
        if (SphericalUtil.computeDistanceBetween(latLng,methi3.getPosition()) < 200000) {
            methi3.setVisible(true);
        }
        String id85 = methi3.getId();
        markerMap.put(id85,"action_methi");

        Marker methi4 = mMap.addMarker(new MarkerOptions()
                .position(new LatLng(28.393527,77.833948))
                .title("Fenugreek")
                .snippet("Bulandshahr,Uttar Pradesh")
                .rotation((float) 0.5)
                .icon(bitmapDescriptorFromVector(getApplicationContext(),R.drawable.methi))
                .visible(false));
        if (SphericalUtil.computeDistanceBetween(latLng,methi4.getPosition()) < 200000) {
            methi4.setVisible(true);
        }
        String id86 = methi4.getId();
        markerMap.put(id86,"action_methi");

        Marker methi5 = mMap.addMarker(new MarkerOptions()
                .position(new LatLng( 27.1905718,75.785764))
                .title("Fenugreek")
                .snippet("Jaipur,Rajasthan")
                .rotation((float) 0.5)
                .icon(bitmapDescriptorFromVector(getApplicationContext(),R.drawable.methi))
                .visible(false));
        if (SphericalUtil.computeDistanceBetween(latLng,methi5.getPosition()) < 200000) {
            methi5.setVisible(true);
        }
        String id87 = methi5.getId();
        markerMap.put(id87,"action_methi");

        Marker methi6 = mMap.addMarker(new MarkerOptions()
                .position(new LatLng( 22.978655,88.716146))
                .title("Fenugreek")
                .snippet("Murshidabad,West Bengal")
                .rotation((float) 0.5)
                .icon(bitmapDescriptorFromVector(getApplicationContext(),R.drawable.methi))
                .visible(false));
        if (SphericalUtil.computeDistanceBetween(latLng,methi6.getPosition()) < 200000) {
            methi6.setVisible(true);
        }
        String id88 = methi6.getId();
        markerMap.put(id88,"action_methi");

        Marker methi7 = mMap.addMarker(new MarkerOptions()
                .position(new LatLng(22.080513,72.8127040))
                .title("Fenugreek")
                .snippet("Bharuch,Gujarat")
                .rotation((float) 0.5)
                .icon(bitmapDescriptorFromVector(getApplicationContext(),R.drawable.methi))
                .visible(false));
        if (SphericalUtil.computeDistanceBetween(latLng,methi7.getPosition()) < 200000) {
            methi7.setVisible(true);
        }
        String id89 = methi7.getId();
        markerMap.put(id89,"action_methi");

        Marker methi8 = mMap.addMarker(new MarkerOptions()
                .position(new LatLng(23.697114, 80.448223))
                .title("Fenugreek")
                .snippet("Katni,Madhya Pradesh")
                .rotation((float) 0.5)
                .icon(bitmapDescriptorFromVector(getApplicationContext(),R.drawable.methi))
                .visible(false));
        if (SphericalUtil.computeDistanceBetween(latLng,methi8.getPosition()) < 200000) {
            methi8.setVisible(true);
        }
        String id90 = methi8.getId();
        markerMap.put(id90,"action_methi");

        Marker methi9= mMap.addMarker(new MarkerOptions()
                .position(new LatLng(19.2067796,81.9303989))
                .title("Fenugreek")
                .snippet("Bastar,Chattisgarh")
                .rotation((float) 0.5)
                .icon(bitmapDescriptorFromVector(getApplicationContext(),R.drawable.methi))
                .visible(false));
        if (SphericalUtil.computeDistanceBetween(latLng,methi9.getPosition()) < 200000) {
            methi9.setVisible(true);
        }
        String id91 = methi9.getId();
        markerMap.put(id91,"action_methi");

        //Indian mustard
        Marker mustard1 = mMap.addMarker(new MarkerOptions()
                .position(new LatLng( 25.6109918,75.8963763))
                .title("Indian mustard")
                .snippet("Kota,Rajasthan")
                .rotation((float) 0.5)
                .icon(bitmapDescriptorFromVector(getApplicationContext(),R.drawable.mustard))
                .visible(false));
        if (SphericalUtil.computeDistanceBetween(latLng,mustard1.getPosition()) < 200000) {
            mustard1.setVisible(true);
        }
        String id92 = mustard1.getId();
        markerMap.put(id92,"action_rai");

        Marker mustard2 = mMap.addMarker(new MarkerOptions()
                .position(new LatLng(29.110322,75.735147 ))
                .title("Indian mustard")
                .snippet("Hisar,Haryana")
                .rotation((float) 0.5)
                .icon(bitmapDescriptorFromVector(getApplicationContext(),R.drawable.mustard))
                .visible(false));
        if (SphericalUtil.computeDistanceBetween(latLng,mustard2.getPosition()) < 200000) {
            mustard2.setVisible(true);
        }
        String id93 = mustard2.getId();
        markerMap.put(id93,"action_rai");

        Marker mustard3 = mMap.addMarker(new MarkerOptions()
                .position(new LatLng(26.9980286,94.6389229 ))
                .title("Indian mustard")
                .snippet("Sibsagar,Assam")
                .rotation((float) 0.5)
                .icon(bitmapDescriptorFromVector(getApplicationContext(),R.drawable.mustard))
                .visible(false));
        if (SphericalUtil.computeDistanceBetween(latLng,mustard3.getPosition()) < 200000) {
            mustard3.setVisible(true);
        }
        String id94 = mustard3.getId();
        markerMap.put(id94,"action_rai");

        Marker mustard4 = mMap.addMarker(new MarkerOptions()
                .position(new LatLng(27.3892139,94.3675155 ))
                .title("Indian mustard")
                .snippet("Dhemaji,Assam")
                .rotation((float) 0.5)
                .icon(bitmapDescriptorFromVector(getApplicationContext(),R.drawable.mustard))
                .visible(false));
        if (SphericalUtil.computeDistanceBetween(latLng,mustard4.getPosition()) < 200000) {
            mustard4.setVisible(true);
        }
        String id95 = mustard4.getId();
        markerMap.put(id95,"action_rai");

        Marker mustard5 = mMap.addMarker(new MarkerOptions()
                .position(new LatLng(26.7941747,84.9943328 ))
                .title("Indian mustard")
                .snippet("East Champaran,Bihar")
                .rotation((float) 0.5)
                .icon(bitmapDescriptorFromVector(getApplicationContext(),R.drawable.mustard))
                .visible(false));
        if (SphericalUtil.computeDistanceBetween(latLng,mustard5.getPosition()) < 200000) {
            mustard5.setVisible(true);
        }
        String id96 = mustard5.getId();
        markerMap.put(id96,"action_rai");

        Marker mustard6 = mMap.addMarker(new MarkerOptions()
                .position(new LatLng(33.806501,74.2608064))
                .title("Indian mustard")
                .snippet("Poonch,Jammu Kashmir")
                .rotation((float) 0.5)
                .icon(bitmapDescriptorFromVector(getApplicationContext(),R.drawable.mustard))
                .visible(false));
        if (SphericalUtil.computeDistanceBetween(latLng,mustard6.getPosition()) < 200000) {
            mustard6.setVisible(true);
        }
        String id97 = mustard6.getId();
        markerMap.put(id97,"action_rai");

        Marker mustard7 = mMap.addMarker(new MarkerOptions()
                .position(new LatLng(33.7238001,75.1427025))
                .title("Indian mustard")
                .snippet("AnantNag,Jammu Kashmir")
                .rotation((float) 0.5)
                .icon(bitmapDescriptorFromVector(getApplicationContext(),R.drawable.mustard))
                .visible(false));
        if (SphericalUtil.computeDistanceBetween(latLng,mustard7.getPosition()) < 200000) {
            mustard7.setVisible(true);
        }
        String id98 = mustard7.getId();
        markerMap.put(id98,"action_rai");

        Marker mustard8 = mMap.addMarker(new MarkerOptions()
                .position(new LatLng(25.5953263,90.2420074))
                .title("Indian mustard")
                .snippet("West Garo Hills,Meghalaya")
                .rotation((float) 0.5)
                .icon(bitmapDescriptorFromVector(getApplicationContext(),R.drawable.mustard))
                .visible(false));
        if (SphericalUtil.computeDistanceBetween(latLng,mustard8.getPosition()) < 200000) {
            mustard8.setVisible(true);
        }
        String id99 = mustard8.getId();
        markerMap.put(id99,"action_rai");

        Marker mustard9 = mMap.addMarker(new MarkerOptions()
                .position(new LatLng(12.120346,78.141446))
                .title("Indian mustard")
                .snippet("Dharampuri,Tamil Nadu")
                .rotation((float) 0.5)
                .icon(bitmapDescriptorFromVector(getApplicationContext(),R.drawable.mustard))
                .visible(false));
        if (SphericalUtil.computeDistanceBetween(latLng,mustard9.getPosition()) < 200000) {
            mustard9.setVisible(true);
        }
        String id100 = mustard9.getId();
        markerMap.put(id100,"action_rai");

        Marker mustard10 = mMap.addMarker(new MarkerOptions()
                .position(new LatLng(25.510867,78.759478))
                .title("Indian mustard")
                .snippet("Jhansi,Uttar Pradesh")
                .rotation((float) 0.5)
                .icon(bitmapDescriptorFromVector(getApplicationContext(),R.drawable.mustard))
                .visible(false));
        if (SphericalUtil.computeDistanceBetween(latLng,mustard10.getPosition()) < 200000) {
            mustard10.setVisible(true);
        }
        String id101 = mustard10.getId();
        markerMap.put(id101,"action_rai");

        Marker mustard11 = mMap.addMarker(new MarkerOptions()
                .position(new LatLng(25.218192,82.926492))
                .title("Indian mustard")
                .snippet("Betwar,Uttar Pradesh")
                .rotation((float) 0.5)
                .icon(bitmapDescriptorFromVector(getApplicationContext(),R.drawable.mustard))
                .visible(false));
        if (SphericalUtil.computeDistanceBetween(latLng,mustard11.getPosition()) < 200000) {
            mustard11.setVisible(true);
        }
        String id102 = mustard11.getId();
        markerMap.put(id102,"action_rai");

        Marker mustard12 = mMap.addMarker(new MarkerOptions()
                .position(new LatLng(27.559122,80.706447))
                .title("Indian mustard")
                .snippet("Sitapur,Uttar Pradesh")
                .rotation((float) 0.5)
                .icon(bitmapDescriptorFromVector(getApplicationContext(),R.drawable.mustard))
                .visible(false));
        if (SphericalUtil.computeDistanceBetween(latLng,mustard12.getPosition()) < 200000) {
            mustard12.setVisible(true);
        }
        String id103 = mustard12.getId();
        markerMap.put(id103,"action_rai");

        Marker mustard13 = mMap.addMarker(new MarkerOptions()
                .position(new LatLng(26.447289,78.725410))
                .title("Indian mustard")
                .snippet("Bhind,Madhya Pradesh")
                .rotation((float) 0.5)
                .icon(bitmapDescriptorFromVector(getApplicationContext(),R.drawable.mustard))
                .visible(false));
        if (SphericalUtil.computeDistanceBetween(latLng,mustard13.getPosition()) < 200000) {
            mustard13.setVisible(true);
        }
        String id104 = mustard13.getId();
        markerMap.put(id104,"action_rai");

//Lentil
        Marker lentil1 = mMap.addMarker(new MarkerOptions()
                .position(new LatLng( 23.835158,84.282460))
                .title("Lentils")
                .snippet("Bokaro,Jharkhand")
                .rotation((float) 0.5)
                .icon(bitmapDescriptorFromVector(getApplicationContext(),R.drawable.lentils))
                .visible(false));
        if (SphericalUtil.computeDistanceBetween(latLng,lentil1.getPosition()) < 200000) {
            lentil1.setVisible(true);
        }
        String id105 = lentil1.getId();
        markerMap.put(id105,"action_lentil");

        Marker lentil2 = mMap.addMarker(new MarkerOptions()
                .position(new LatLng( 24.420004,81.982265))
                .title("Lentils")
                .snippet("Sidhi,Madhya Pradesh")
                .rotation((float) 0.5)
                .icon(bitmapDescriptorFromVector(getApplicationContext(),R.drawable.lentils))
                .visible(false));
        if (SphericalUtil.computeDistanceBetween(latLng,lentil2.getPosition()) < 200000) {
            lentil2.setVisible(true);
        }
        String id106 = lentil2.getId();
        markerMap.put(id106,"action_lentil");

        Marker lentil3 = mMap.addMarker(new MarkerOptions()
                .position(new LatLng( 17.6079693,81.3368949))
                .title("Lentils")
                .snippet("East Godavari,Andhra Pradesh")
                .rotation((float) 0.5)
                .icon(bitmapDescriptorFromVector(getApplicationContext(),R.drawable.lentils))
                .visible(false));
        if (SphericalUtil.computeDistanceBetween(latLng,lentil3.getPosition()) < 200000) {
            lentil3.setVisible(true);
        }
        String id107 = lentil3.getId();
        markerMap.put(id107,"action_lentil");

        Marker lentil4 = mMap.addMarker(new MarkerOptions()
                .position(new LatLng( 17.0015691,81.4784293))
                .title("Lentils")
                .snippet("West Godavari,Andhra Pradesh")
                .rotation((float) 0.5)
                .icon(bitmapDescriptorFromVector(getApplicationContext(),R.drawable.lentils))
                .visible(false));
        if (SphericalUtil.computeDistanceBetween(latLng,lentil4.getPosition()) < 200000) {
            lentil4.setVisible(true);
        }
        String id108 = lentil4.getId();
        markerMap.put(id108,"action_lentil");

        Marker lentil5 = mMap.addMarker(new MarkerOptions()
                .position(new LatLng( 15.3842851,79.9568439))
                .title("Lentils")
                .snippet("Prakasam,Andhra Pradesh")
                .rotation((float) 0.5)
                .icon(bitmapDescriptorFromVector(getApplicationContext(),R.drawable.lentils))
                .visible(false));
        if (SphericalUtil.computeDistanceBetween(latLng,lentil5.getPosition()) < 200000) {
            lentil5.setVisible(true);
        }
        String id109 = lentil5.getId();
        markerMap.put(id109,"action_lentil");

        Marker lentil6 = mMap.addMarker(new MarkerOptions()
                .position(new LatLng( 25.118556,83.4784832))
                .title("Lentils")
                .snippet("Kaimur,Bihar")
                .rotation((float) 0.5)
                .icon(bitmapDescriptorFromVector(getApplicationContext(),R.drawable.lentils))
                .visible(false));
        if (SphericalUtil.computeDistanceBetween(latLng,lentil6.getPosition()) < 200000) {
            lentil6.setVisible(true);
        }
        String id110 = lentil6.getId();
        markerMap.put(id110,"action_lentil");

        Marker lentil7 = mMap.addMarker(new MarkerOptions()
                .position(new LatLng( 30.412697,74.038519))
                .title("Lentils")
                .snippet("Fazilka,Punjab")
                .rotation((float) 0.5)
                .icon(bitmapDescriptorFromVector(getApplicationContext(),R.drawable.lentils))
                .visible(false));
        if (SphericalUtil.computeDistanceBetween(latLng,lentil7.getPosition()) < 200000) {
            lentil7.setVisible(true);
        }
        String id111 = lentil7.getId();
        markerMap.put(id111,"action_lentil");

        Marker lentil8 = mMap.addMarker(new MarkerOptions()
                .position(new LatLng( 30.172508,74.982386))
                .title("Lentils")
                .snippet("Bathinda,Punjab")
                .rotation((float) 0.5)
                .icon(bitmapDescriptorFromVector(getApplicationContext(),R.drawable.lentils))
                .visible(false));
        if (SphericalUtil.computeDistanceBetween(latLng,lentil8.getPosition()) < 200000) {
            lentil8.setVisible(true);
        }
        String id112 = lentil8.getId();
        markerMap.put(id112,"action_lentil");

        Marker lentil9 = mMap.addMarker(new MarkerOptions()
                .position(new LatLng( 30.223110,75.832173))
                .title("Lentils")
                .snippet("Sangrur,Punjab")
                .rotation((float) 0.5)
                .icon(bitmapDescriptorFromVector(getApplicationContext(),R.drawable.lentils))
                .visible(false));
        if (SphericalUtil.computeDistanceBetween(latLng,lentil9.getPosition()) < 200000) {
            lentil9.setVisible(true);
        }
        String id113 = lentil9.getId();
        markerMap.put(id113,"action_lentil");

        Marker lentil10 = mMap.addMarker(new MarkerOptions()
                .position(new LatLng( 28.317362,74.960309))
                .title("Lentils")
                .snippet("Churu,Rajasthan")
                .rotation((float) 0.5)
                .icon(bitmapDescriptorFromVector(getApplicationContext(),R.drawable.lentils))
                .visible(false));
        if (SphericalUtil.computeDistanceBetween(latLng,lentil10.getPosition()) < 200000) {
            lentil10.setVisible(true);
        }
        String id114 = lentil10.getId();
        markerMap.put(id114,"action_lentil");

        Marker lentil11 = mMap.addMarker(new MarkerOptions()
                .position(new LatLng( 28.036759,73.271327))
                .title("Lentils")
                .snippet("Bikaner,Rajasthan")
                .rotation((float) 0.5)
                .icon(bitmapDescriptorFromVector(getApplicationContext(),R.drawable.lentils))
                .visible(false));
        if (SphericalUtil.computeDistanceBetween(latLng,lentil11.getPosition()) < 200000) {
            lentil11.setVisible(true);
        }
        String id115 = lentil11.getId();
        markerMap.put(id115,"action_lentil");

        Marker lentil12 = mMap.addMarker(new MarkerOptions()
                .position(new LatLng( 28.127644,75.386839))
                .title("Lentils")
                .snippet("Jhunjhunun,Rajasthan")
                .rotation((float) 0.5)
                .icon(bitmapDescriptorFromVector(getApplicationContext(),R.drawable.lentils))
                .visible(false));
        if (SphericalUtil.computeDistanceBetween(latLng,lentil12.getPosition()) < 200000) {
            lentil12.setVisible(true);
        }
        String id116 = lentil12.getId();
        markerMap.put(id116,"action_lentil");

        Marker lentil13 = mMap.addMarker(new MarkerOptions()
                .position(new LatLng( 26.429398,74.596271))
                .title("Lentils")
                .snippet("Ajmer,Rajasthan")
                .rotation((float) 0.5)
                .icon(bitmapDescriptorFromVector(getApplicationContext(),R.drawable.lentils))
                .visible(false));
        if (SphericalUtil.computeDistanceBetween(latLng,lentil13.getPosition()) < 200000) {
            lentil13.setVisible(true);
        }
        String id117 = lentil13.getId();
        markerMap.put(id117,"action_lentil");

        Marker lentil14 = mMap.addMarker(new MarkerOptions()
                .position(new LatLng( 8.7673883,78.0999913))
                .title("Lentils")
                .snippet("Thoothukudi,Tamil Nadu")
                .rotation((float) 0.5)
                .icon(bitmapDescriptorFromVector(getApplicationContext(),R.drawable.lentils))
                .visible(false));
        if (SphericalUtil.computeDistanceBetween(latLng,lentil14.getPosition()) < 200000) {
            lentil14.setVisible(true);
        }
        String id118 = lentil14.getId();
        markerMap.put(id118,"action_lentil");

        Marker lentil15 = mMap.addMarker(new MarkerOptions()
                .position(new LatLng( 9.352319,78.865012))
                .title("Lentils")
                .snippet("Ramanathapuram,Tamil Nadu")
                .rotation((float) 0.5)
                .icon(bitmapDescriptorFromVector(getApplicationContext(),R.drawable.lentils))
                .visible(false));
        if (SphericalUtil.computeDistanceBetween(latLng,lentil15.getPosition()) < 200000) {
            lentil15.setVisible(true);
        }
        String id119 = lentil15.getId();
        markerMap.put(id119,"action_lentil");

        Marker lentil16 = mMap.addMarker(new MarkerOptions()
                .position(new LatLng( 9.842558,78.489942))
                .title("Lentils")
                .snippet("Sivagangai,Tamil Nadu")
                .rotation((float) 0.5)
                .icon(bitmapDescriptorFromVector(getApplicationContext(),R.drawable.lentils))
                .visible(false));
        if (SphericalUtil.computeDistanceBetween(latLng,lentil16.getPosition()) < 200000) {
            lentil16.setVisible(true);
        }
        String id120 = lentil16.getId();
        markerMap.put(id120,"action_lentil");

        Marker lentil17 = mMap.addMarker(new MarkerOptions()
                .position(new LatLng( 25.022883,88.124463))
                .title("Lentils")
                .snippet("Malda,West Bengal")
                .rotation((float) 0.5)
                .icon(bitmapDescriptorFromVector(getApplicationContext(),R.drawable.lentils))
                .visible(false));
        if (SphericalUtil.computeDistanceBetween(latLng,lentil17.getPosition()) < 200000) {
            lentil17.setVisible(true);
        }
        String id121 = lentil17.getId();
        markerMap.put(id121,"action_lentil");

        //maize
        Marker maize1 = mMap.addMarker(new MarkerOptions()
                .position(new LatLng( 25.217379,74.430118))
                .title("Maize")
                .snippet("Bhilwara,Rajasthan")
                .rotation((float) 0.5)
                .icon(bitmapDescriptorFromVector(getApplicationContext(),R.drawable.maize))
                .visible(false));
        if (SphericalUtil.computeDistanceBetween(latLng,maize1.getPosition()) < 200000) {
            maize1.setVisible(true);
        }
        String id122 = maize1.getId();
        markerMap.put(id122,"action_maize");

        Marker maize2 = mMap.addMarker(new MarkerOptions()
                .position(new LatLng(12.962304,74.968404))
                .title("Maize")
                .snippet("Dakshina Kannada,Karnataka")
                .rotation((float) 0.5)
                .icon(bitmapDescriptorFromVector(getApplicationContext(),R.drawable.maize))
                .visible(false));
        if (SphericalUtil.computeDistanceBetween(latLng,maize2.getPosition()) < 200000) {
            maize2.setVisible(true);
        }
        String id123 = maize2.getId();
        markerMap.put(id123,"action_maize");

        Marker maize3 = mMap.addMarker(new MarkerOptions()
                .position(new LatLng(24.0088483,72.945629))
                .title("Maize")
                .snippet("SabarKantha,Gujarat")
                .rotation((float) 0.5)
                .icon(bitmapDescriptorFromVector(getApplicationContext(),R.drawable.maize))
                .visible(false));
        if (SphericalUtil.computeDistanceBetween(latLng,maize3.getPosition()) < 200000) {
            maize3.setVisible(true);
        }
        String id124 = maize3.getId();
        markerMap.put(id124,"action_maize");

        Marker maize4 = mMap.addMarker(new MarkerOptions()
                .position(new LatLng(23.2670993,73.1222821))
                .title("Maize")
                .snippet("Udepur,Gujarat")
                .rotation((float) 0.5)
                .icon(bitmapDescriptorFromVector(getApplicationContext(),R.drawable.maize))
                .visible(false));
        if (SphericalUtil.computeDistanceBetween(latLng,maize4.getPosition()) < 200000) {
            maize4.setVisible(true);
        }
        String id125 = maize4.getId();
        markerMap.put(id125,"action_maize");

        Marker maize5 = mMap.addMarker(new MarkerOptions()
                .position(new LatLng(23.5478252,74.4055896))
                .title("Maize")
                .snippet("Banswara,Rajasthan")
                .rotation((float) 0.5)
                .icon(bitmapDescriptorFromVector(getApplicationContext(),R.drawable.maize))
                .visible(false));
        if (SphericalUtil.computeDistanceBetween(latLng,maize5.getPosition()) < 200000) {
            maize5.setVisible(true);
        }
        String id126 = maize5.getId();
        markerMap.put(id126,"action_maize");

        Marker maize6 = mMap.addMarker(new MarkerOptions()
                .position(new LatLng(17.0538002,82.078628))
                .title("Maize")
                .snippet("East Godavari,Andhra Pradesh")
                .rotation((float) 0.5)
                .icon(bitmapDescriptorFromVector(getApplicationContext(),R.drawable.maize))
                .visible(false));
        if (SphericalUtil.computeDistanceBetween(latLng,maize6.getPosition()) < 200000) {
            maize6.setVisible(true);
        }
        String id127 = maize6.getId();
        markerMap.put(id127,"action_maize");

        Marker maize7 = mMap.addMarker(new MarkerOptions()
                .position(new LatLng(26.5133553,93.7497394))
                .title("Maize")
                .snippet("Golaghat,Assam")
                .rotation((float) 0.5)
                .icon(bitmapDescriptorFromVector(getApplicationContext(),R.drawable.maize))
                .visible(false));
        if (SphericalUtil.computeDistanceBetween(latLng,maize7.getPosition()) < 200000) {
            maize7.setVisible(true);
        }
        String id128 = maize7.getId();
        markerMap.put(id128,"action_maize");

        Marker maize8 = mMap.addMarker(new MarkerOptions()
                .position(new LatLng(25.5702799,87.5604638))
                .title("Maize")
                .snippet("Katihar,Bihar")
                .rotation((float) 0.5)
                .icon(bitmapDescriptorFromVector(getApplicationContext(),R.drawable.maize))
                .visible(false));
        if (SphericalUtil.computeDistanceBetween(latLng,maize8.getPosition()) < 200000) {
            maize8.setVisible(true);
        }
        String id129 = maize8.getId();
        markerMap.put(id129,"action_maize");

        Marker maize9 = mMap.addMarker(new MarkerOptions()
                .position(new LatLng(25.8565545,85.784318))
                .title("Maize")
                .snippet("Samastipur,Bihar")
                .rotation((float) 0.5)
                .icon(bitmapDescriptorFromVector(getApplicationContext(),R.drawable.maize))
                .visible(false));
        if (SphericalUtil.computeDistanceBetween(latLng,maize9.getPosition()) < 200000) {
            maize9.setVisible(true);
        }
        String id130 = maize9.getId();
        markerMap.put(id130,"action_maize");

        Marker maize10 = mMap.addMarker(new MarkerOptions()
                .position(new LatLng(26.1506333,85.3434666))
                .title("Maize")
                .snippet("Muzaffarpur,Bihar")
                .rotation((float) 0.5)
                .icon(bitmapDescriptorFromVector(getApplicationContext(),R.drawable.maize))
                .visible(false));
        if (SphericalUtil.computeDistanceBetween(latLng,maize10.getPosition()) < 200000) {
            maize10.setVisible(true);
        }
        String id131 = maize10.getId();
        markerMap.put(id131,"action_maize");

        Marker maize11 = mMap.addMarker(new MarkerOptions()
                .position(new LatLng(23.2188581,82.8636573))
                .title("Maize")
                .snippet("Surajpur,Chattisgarh")
                .rotation((float) 0.5)
                .icon(bitmapDescriptorFromVector(getApplicationContext(),R.drawable.maize))
                .visible(false));
        if (SphericalUtil.computeDistanceBetween(latLng,maize11.getPosition()) < 200000) {
            maize11.setVisible(true);
        }
        String id132 = maize11.getId();
        markerMap.put(id132,"action_maize");

        Marker maize12 = mMap.addMarker(new MarkerOptions()
                .position(new LatLng(22.2358,82.2495))
                .title("Maize")
                .snippet("Bilaspur,Chattisgarh")
                .rotation((float) 0.5)
                .icon(bitmapDescriptorFromVector(getApplicationContext(),R.drawable.maize))
                .visible(false));
        if (SphericalUtil.computeDistanceBetween(latLng,maize12.getPosition()) < 200000) {
            maize12.setVisible(true);
        }
        String id133 = maize12.getId();
        markerMap.put(id133,"action_maize");

        Marker maize13 = mMap.addMarker(new MarkerOptions()
                .position(new LatLng(34.2839597,74.614112))
                .title("Maize")
                .snippet("Bandipora,Jammu Kashmir")
                .rotation((float) 0.5)
                .icon(bitmapDescriptorFromVector(getApplicationContext(),R.drawable.maize))
                .visible(false));
        if (SphericalUtil.computeDistanceBetween(latLng,maize13.getPosition()) < 200000) {
            maize13.setVisible(true);
        }
        String id134 = maize13.getId();
        markerMap.put(id134,"action_maize");

        Marker maize14 = mMap.addMarker(new MarkerOptions()
                .position(new LatLng(32.3886303,75.5558307))
                .title("Maize")
                .snippet("Kathua,Jammu Kashmir")
                .rotation((float) 0.5)
                .icon(bitmapDescriptorFromVector(getApplicationContext(),R.drawable.maize))
                .visible(false));
        if (SphericalUtil.computeDistanceBetween(latLng,maize14.getPosition()) < 200000) {
            maize14.setVisible(true);
        }
        String id135 = maize14.getId();
        markerMap.put(id135,"action_maize");

        Marker maize15 = mMap.addMarker(new MarkerOptions()
                .position(new LatLng(11.9896583,76.4221921))
                .title("Maize")
                .snippet("Mysore,Karnataka")
                .rotation((float) 0.5)
                .icon(bitmapDescriptorFromVector(getApplicationContext(),R.drawable.maize))
                .visible(false));
        if (SphericalUtil.computeDistanceBetween(latLng,maize15.getPosition()) < 200000) {
            maize15.setVisible(true);
        }
        String id136 = maize15.getId();
        markerMap.put(id136,"action_maize");

        Marker maize16 = mMap.addMarker(new MarkerOptions()
                .position(new LatLng(25.7010954,90.8044352))
                .title("Maize")
                .snippet("East Garo Hills,Meghalaya")
                .rotation((float) 0.5)
                .icon(bitmapDescriptorFromVector(getApplicationContext(),R.drawable.maize))
                .visible(false));
        if (SphericalUtil.computeDistanceBetween(latLng,maize16.getPosition()) < 200000) {
            maize16.setVisible(true);
        }
        String id137 = maize16.getId();
        markerMap.put(id137,"action_maize");

        Marker maize17 = mMap.addMarker(new MarkerOptions()
                .position(new LatLng(19.165465,83.425974))
                .title("Maize")
                .snippet("Rayagada,Odisha")
                .rotation((float) 0.5)
                .icon(bitmapDescriptorFromVector(getApplicationContext(),R.drawable.maize))
                .visible(false));
        if (SphericalUtil.computeDistanceBetween(latLng,maize17.getPosition()) < 200000) {
            maize17.setVisible(true);
        }
        String id138 = maize17.getId();
        markerMap.put(id138,"action_maize");

        Marker maize18 = mMap.addMarker(new MarkerOptions()
                .position(new LatLng(20.813687,84.315090))
                .title("Maize")
                .snippet("Baudh,Odisha")
                .rotation((float) 0.5)
                .icon(bitmapDescriptorFromVector(getApplicationContext(),R.drawable.maize))
                .visible(false));
        if (SphericalUtil.computeDistanceBetween(latLng,maize18.getPosition()) < 200000) {
            maize18.setVisible(true);
        }
        String id139 = maize18.getId();
        markerMap.put(id139,"action_maize");

        Marker maize19 = mMap.addMarker(new MarkerOptions()
                .position(new LatLng(31.271512,74.605614))
                .title("Maize")
                .snippet("Tarn Taran,Punjab")
                .rotation((float) 0.5)
                .icon(bitmapDescriptorFromVector(getApplicationContext(),R.drawable.maize))
                .visible(false));
        if (SphericalUtil.computeDistanceBetween(latLng,maize19.getPosition()) < 200000) {
            maize19.setVisible(true);
        }
        String id140 = maize19.getId();
        markerMap.put(id140,"action_maize");

        Marker maize20 = mMap.addMarker(new MarkerOptions()
                .position(new LatLng(30.671371,76.680613))
                .title("Maize")
                .snippet("Sas Nagar,Punjab")
                .rotation((float) 0.5)
                .icon(bitmapDescriptorFromVector(getApplicationContext(),R.drawable.maize))
                .visible(false));
        if (SphericalUtil.computeDistanceBetween(latLng,maize20.getPosition()) < 200000) {
            maize20.setVisible(true);
        }
        String id141 = maize20.getId();
        markerMap.put(id141,"action_maize");

        Marker maize21 = mMap.addMarker(new MarkerOptions()
                .position(new LatLng(25.104157,76.453138))
                .title("Maize")
                .snippet("Baran,Rajasthan")
                .rotation((float) 0.5)
                .icon(bitmapDescriptorFromVector(getApplicationContext(),R.drawable.maize))
                .visible(false));
        if (SphericalUtil.computeDistanceBetween(latLng,maize21.getPosition()) < 200000) {
            maize21.setVisible(true);
        }
        String id142 = maize21.getId();
        markerMap.put(id142,"action_maize");

        Marker maize22 = mMap.addMarker(new MarkerOptions()
                .position(new LatLng(23.845555,73.732519))
                .title("Maize")
                .snippet("Dungarpur,Rajasthan")
                .rotation((float) 0.5)
                .icon(bitmapDescriptorFromVector(getApplicationContext(),R.drawable.maize))
                .visible(false));
        if (SphericalUtil.computeDistanceBetween(latLng,maize22.getPosition()) < 200000) {
            maize22.setVisible(true);
        }
        String id143 = maize22.getId();
        markerMap.put(id143,"action_maize");

        Marker maize23 = mMap.addMarker(new MarkerOptions()
                .position(new LatLng(10.777648,78.709473))
                .title("Maize")
                .snippet("Tiruchirappalli,Tamil Nadu")
                .rotation((float) 0.5)
                .icon(bitmapDescriptorFromVector(getApplicationContext(),R.drawable.maize))
                .visible(false));
        if (SphericalUtil.computeDistanceBetween(latLng,maize23.getPosition()) < 200000) {
            maize23.setVisible(true);
        }
        String id144 = maize23.getId();
        markerMap.put(id144,"action_maize");

        Marker maize24 = mMap.addMarker(new MarkerOptions()
                .position(new LatLng(29.486628,77.717797))
                .title("Maize")
                .snippet("Muzaffarnagar,Uttar Pradesh")
                .rotation((float) 0.5)
                .icon(bitmapDescriptorFromVector(getApplicationContext(),R.drawable.maize))
                .visible(false));
        if (SphericalUtil.computeDistanceBetween(latLng,maize24.getPosition()) < 200000) {
            maize24.setVisible(true);
        }
        String id145 = maize24.getId();
        markerMap.put(id145,"action_maize");

        Marker maize25 = mMap.addMarker(new MarkerOptions()
                .position(new LatLng(24.489440,74.868504))
                .title("Maize")
                .snippet("Neemuch,Madhya Pradesh")
                .rotation((float) 0.5)
                .icon(bitmapDescriptorFromVector(getApplicationContext(),R.drawable.maize))
                .visible(false));
        if (SphericalUtil.computeDistanceBetween(latLng,maize25.getPosition()) < 200000) {
            maize25.setVisible(true);
        }
        String id146 = maize25.getId();
        markerMap.put(id146,"action_maize");

        Marker maize26 = mMap.addMarker(new MarkerOptions()
                .position(new LatLng(21.772594,74.760215))
                .title("Maize")
                .snippet("Barwani,Madhya Pradesh")
                .rotation((float) 0.5)
                .icon(bitmapDescriptorFromVector(getApplicationContext(),R.drawable.maize))
                .visible(false));
        if (SphericalUtil.computeDistanceBetween(latLng,maize26.getPosition()) < 200000) {
            maize26.setVisible(true);
        }
        String id147 = maize26.getId();
        markerMap.put(id147,"action_maize");


        //peas
        Marker peas1 = mMap.addMarker(new MarkerOptions()
                .position(new LatLng( 29.362139,77.743669))
                .title("Peas")
                .snippet("Muzaffarnagar,Uttar Pradesh")
                .rotation((float) 0.5)
                .icon(bitmapDescriptorFromVector(getApplicationContext(),R.drawable.peas))
                .visible(false));
        if (SphericalUtil.computeDistanceBetween(latLng,peas1.getPosition()) < 200000) {
            peas1.setVisible(true);
        }
        String id148 = peas1.getId();
        markerMap.put(id148,"action_peas");

        Marker peas2 = mMap.addMarker(new MarkerOptions()
                .position(new LatLng(26.117770,78.264709 ))
                .title("Peas")
                .snippet("Gwalior,Madhya Pradesh")
                .rotation((float) 0.5)
                .icon(bitmapDescriptorFromVector(getApplicationContext(),R.drawable.peas))
                .visible(false));
        if (SphericalUtil.computeDistanceBetween(latLng,peas2.getPosition()) < 200000) {
            peas2.setVisible(true);
        }
        String id149 = peas2.getId();
        markerMap.put(id149,"action_peas");

        Marker peas3 = mMap.addMarker(new MarkerOptions()
                .position(new LatLng(30.6026069,77.4426994))
                .title("Peas")
                .snippet("Sirmaur,Himachal Pradesh")
                .rotation((float) 0.5)
                .icon(bitmapDescriptorFromVector(getApplicationContext(),R.drawable.peas))
                .visible(false));
        if (SphericalUtil.computeDistanceBetween(latLng,peas3.getPosition()) < 200000) {
            peas3.setVisible(true);
        }
        String id150 = peas3.getId();
        markerMap.put(id150,"action_peas");

        Marker peas4 = mMap.addMarker(new MarkerOptions()
                .position(new LatLng(23.716425, 77.339118))
                .title("Peas")
                .snippet("Bhopal,Madhya Pradesh")
                .rotation((float) 0.5)
                .icon(bitmapDescriptorFromVector(getApplicationContext(),R.drawable.peas))
                .visible(false));
        if (SphericalUtil.computeDistanceBetween(latLng,peas4.getPosition()) < 200000) {
            peas4.setVisible(true);
        }
        String id151 = peas4.getId();
        markerMap.put(id151,"action_peas");

        Marker peas5 = mMap.addMarker(new MarkerOptions()
                .position(new LatLng( 26.686999, 77.848496))
                .title("Peas")
                .snippet("Dholpur,Rajasthan")
                .rotation((float) 0.5)
                .icon(bitmapDescriptorFromVector(getApplicationContext(),R.drawable.peas))
                .visible(false));
        if (SphericalUtil.computeDistanceBetween(latLng,peas5.getPosition()) < 200000) {
            peas5.setVisible(true);
        }
        String id152 = peas5.getId();
        markerMap.put(id152,"action_peas");

        Marker peas6 = mMap.addMarker(new MarkerOptions()
                .position(new LatLng( 20.994239, 75.601993))
                .title("Peas")
                .snippet("Jalgoan,Maharashtra")
                .rotation((float) 0.5)
                .icon(bitmapDescriptorFromVector(getApplicationContext(),R.drawable.peas))
                .visible(false));
        if (SphericalUtil.computeDistanceBetween(latLng,peas6.getPosition()) < 200000) {
            peas6.setVisible(true);
        }
        String id153 = peas6.getId();
        markerMap.put(id153,"action_peas");

        Marker peas7 = mMap.addMarker(new MarkerOptions()
                .position(new LatLng( 21.171878, 79.668042))
                .title("Peas")
                .snippet("Bhandara,Maharashtra")
                .rotation((float) 0.5)
                .icon(bitmapDescriptorFromVector(getApplicationContext(),R.drawable.peas))
                .visible(false));
        if (SphericalUtil.computeDistanceBetween(latLng,peas7.getPosition()) < 200000) {
            peas7.setVisible(true);
        }
        String id154 = peas7.getId();
        markerMap.put(id154,"action_peas");

        Marker peas8 = mMap.addMarker(new MarkerOptions()
                .position(new LatLng( 30.840660, 75.193077))
                .title("Peas")
                .snippet("Moga,Punjab")
                .rotation((float) 0.5)
                .icon(bitmapDescriptorFromVector(getApplicationContext(),R.drawable.peas))
                .visible(false));
        if (SphericalUtil.computeDistanceBetween(latLng,peas8.getPosition()) < 200000) {
            peas8.setVisible(true);
        }
        String id155 = peas8.getId();
        markerMap.put(id155,"action_peas");

        Marker peas9 = mMap.addMarker(new MarkerOptions()
                .position(new LatLng( 32.0132881,75.4249447))
                .title("Peas")
                .snippet("Gurdaspur,Punjab")
                .rotation((float) 0.5)
                .icon(bitmapDescriptorFromVector(getApplicationContext(),R.drawable.peas))
                .visible(false));
        if (SphericalUtil.computeDistanceBetween(latLng,peas9.getPosition()) < 200000) {
            peas9.setVisible(true);
        }
        String id156 = peas9.getId();
        markerMap.put(id156,"action_peas");

        Marker peas10 = mMap.addMarker(new MarkerOptions()
                .position(new LatLng( 29.2867567,76.2940763))
                .title("Peas")
                .snippet("Jind,Haryana")
                .rotation((float) 0.5)
                .icon(bitmapDescriptorFromVector(getApplicationContext(),R.drawable.peas))
                .visible(false));
        if (SphericalUtil.computeDistanceBetween(latLng,peas10.getPosition()) < 200000) {
            peas10.setVisible(true);
        }
        String id157 = peas10.getId();
        markerMap.put(id157,"action_peas");

        Marker peas11 = mMap.addMarker(new MarkerOptions()
                .position(new LatLng( 15.1697235,76.8875897))
                .title("Peas")
                .snippet("Ballari,Karnataka")
                .rotation((float) 0.5)
                .icon(bitmapDescriptorFromVector(getApplicationContext(),R.drawable.peas))
                .visible(false));
        if (SphericalUtil.computeDistanceBetween(latLng,peas11.getPosition()) < 200000) {
            peas11.setVisible(true);
        }
        String id158 = peas11.getId();
        markerMap.put(id158,"action_peas");

        Marker peas12 = mMap.addMarker(new MarkerOptions()
                .position(new LatLng( 24.8976559,85.5229268))
                .title("Peas")
                .snippet("Nawada,Bihar")
                .rotation((float) 0.5)
                .icon(bitmapDescriptorFromVector(getApplicationContext(),R.drawable.peas))
                .visible(false));
        if (SphericalUtil.computeDistanceBetween(latLng,peas12.getPosition()) < 200000) {
            peas12.setVisible(true);
        }
        String id159 = peas12.getId();
        markerMap.put(id159,"action_peas");


        //pum
        Marker pum1 = mMap.addMarker(new MarkerOptions()
                .position(new LatLng(20.8693735,83.1555593))
                .title("Pumpkin")
                .snippet("Balangir,Odisha")
                .rotation((float) 0.5)
                .icon(bitmapDescriptorFromVector(getApplicationContext(),R.drawable.pum))
                .visible(false));
        if (SphericalUtil.computeDistanceBetween(latLng,pum1.getPosition()) < 200000) {
            pum1.setVisible(true);
        }
        String id160 = pum1.getId();
        markerMap.put(id160,"action_pum");

        Marker pum2 = mMap.addMarker(new MarkerOptions()
                .position(new LatLng(24.7354209,83.2725943))
                .title("Pumpkin")
                .snippet("Chandauli,Uttar Pradesh")
                .rotation((float) 0.5)
                .icon(bitmapDescriptorFromVector(getApplicationContext(),R.drawable.pum))
                .visible(false));
        if (SphericalUtil.computeDistanceBetween(latLng,pum2.getPosition()) < 200000) {
            pum2.setVisible(true);
        }
        String id161 = pum2.getId();
        markerMap.put(id161,"action_pum");

        Marker pum3 = mMap.addMarker(new MarkerOptions()
                .position(new LatLng(24.5233069,83.0276753))
                .title("Pumpkin")
                .snippet("Sonbhadra,Uttar Pradesh")
                .rotation((float) 0.5)
                .icon(bitmapDescriptorFromVector(getApplicationContext(),R.drawable.pum))
                .visible(false));
        if (SphericalUtil.computeDistanceBetween(latLng,pum3.getPosition()) < 200000) {
            pum3.setVisible(true);
        }
        String id162 = pum3.getId();
        markerMap.put(id162,"action_pum");

        Marker pum4 = mMap.addMarker(new MarkerOptions()
                .position(new LatLng(22.763635, 75.623447))
                .title("Pumpkin")
                .snippet("Indore,Madhya Pradesh")
                .rotation((float) 0.5)
                .icon(bitmapDescriptorFromVector(getApplicationContext(),R.drawable.pum))
                .visible(false));
        if (SphericalUtil.computeDistanceBetween(latLng,pum4.getPosition()) < 200000) {
            pum4.setVisible(true);
        }
        String id163 = pum4.getId();
        markerMap.put(id163,"action_pum");

        Marker pum5 = mMap.addMarker(new MarkerOptions()
                .position(new LatLng(21.250534, 81.447467))
                .title("Pumpkin")
                .snippet("Durg,Chattisgarh")
                .rotation((float) 0.5)
                .icon(bitmapDescriptorFromVector(getApplicationContext(),R.drawable.pum))
                .visible(false));
        if (SphericalUtil.computeDistanceBetween(latLng,pum5.getPosition()) < 200000) {
            pum5.setVisible(true);
        }
        String id164 = pum5.getId();
        markerMap.put(id164,"action_pum");

        Marker pum6 = mMap.addMarker(new MarkerOptions()
                .position(new LatLng(13.961249, 75.570615))
                .title("Pumpkin")
                .snippet("Shivamogga,Karnataka")
                .rotation((float) 0.5)
                .icon(bitmapDescriptorFromVector(getApplicationContext(),R.drawable.pum))
                .visible(false));
        if (SphericalUtil.computeDistanceBetween(latLng,pum6.getPosition()) < 200000) {
            pum6.setVisible(true);
        }
        String id165 = pum6.getId();
        markerMap.put(id165,"action_pum");

        Marker pum7 = mMap.addMarker(new MarkerOptions()
                .position(new LatLng(29.064662, 76.319078))
                .title("Pumpkin")
                .snippet("Rohtak,Haryana")
                .rotation((float) 0.5)
                .icon(bitmapDescriptorFromVector(getApplicationContext(),R.drawable.pum))
                .visible(false));
        if (SphericalUtil.computeDistanceBetween(latLng,pum7.getPosition()) < 200000) {
            pum7.setVisible(true);
        }
        String id166 = pum7.getId();
        markerMap.put(id166,"action_pum");

        Marker pum8 = mMap.addMarker(new MarkerOptions()
                .position(new LatLng(25.5809964,91.901691))
                .title("Pumpkin")
                .snippet("East Khasi Hills,Meghalaya")
                .rotation((float) 0.5)
                .icon(bitmapDescriptorFromVector(getApplicationContext(),R.drawable.pum))
                .visible(false));
        if (SphericalUtil.computeDistanceBetween(latLng,pum8.getPosition()) < 200000) {
            pum8.setVisible(true);
        }
        String id167 = pum8.getId();
        markerMap.put(id167,"action_pum");

        Marker pum9 = mMap.addMarker(new MarkerOptions()
                .position(new LatLng(29.192370, 73.903310))
                .title("Pumpkin")
                .snippet("Ganganagar,Rajasthan")
                .rotation((float) 0.5)
                .icon(bitmapDescriptorFromVector(getApplicationContext(),R.drawable.pum))
                .visible(false));
        if (SphericalUtil.computeDistanceBetween(latLng,pum9.getPosition()) < 200000) {
            pum9.setVisible(true);
        }
        String id168 = pum9.getId();
        markerMap.put(id168,"action_pum");

        //rice
        Marker rice1 = mMap.addMarker(new MarkerOptions()
                .position(new LatLng( 26.750911,88.403998))
                .title("Rice")
                .snippet("Darjeeling,West Bengal")
                .rotation((float) 0.5)
                .icon(bitmapDescriptorFromVector(getApplicationContext(),R.drawable.rice))
                .visible(false));
        if (SphericalUtil.computeDistanceBetween(latLng,rice1.getPosition()) < 200000) {
            rice1.setVisible(true);
        }
        String id169 = rice1.getId();
        markerMap.put(id169,"action_rice");

        Marker rice2 = mMap.addMarker(new MarkerOptions()
                .position(new LatLng( 26.959140,83.526210))
                .title("Rice")
                .snippet("Maharajganj,Uttar Pradesh")
                .rotation((float) 0.5)
                .icon(bitmapDescriptorFromVector(getApplicationContext(),R.drawable.rice))
                .visible(false));
        if (SphericalUtil.computeDistanceBetween(latLng,rice2.getPosition()) < 200000) {
            rice2.setVisible(true);
        }
        String id170 = rice2.getId();
        markerMap.put(id170,"action_rice");

        Marker rice3 = mMap.addMarker(new MarkerOptions()
                .position(new LatLng( 20.3098764,73.7769867))
                .title("Rice")
                .snippet("Nashik,Maharashtra")
                .rotation((float) 0.5)
                .icon(bitmapDescriptorFromVector(getApplicationContext(),R.drawable.rice))
                .visible(false));
        if (SphericalUtil.computeDistanceBetween(latLng,rice3.getPosition()) < 200000) {
            rice3.setVisible(true);
        }
        String id171 = rice3.getId();
        markerMap.put(id171,"action_rice");

        Marker rice4 = mMap.addMarker(new MarkerOptions()
                .position(new LatLng( 20.9304523,74.8927555))
                .title("Rice")
                .snippet("Dhule,Maharashtra")
                .rotation((float) 0.5)
                .icon(bitmapDescriptorFromVector(getApplicationContext(),R.drawable.rice))
                .visible(false));
        if (SphericalUtil.computeDistanceBetween(latLng,rice4.getPosition()) < 200000) {
            rice4.setVisible(true);
        }
        String id172 = rice4.getId();
        markerMap.put(id172,"action_rice");

        Marker rice5 = mMap.addMarker(new MarkerOptions()
                .position(new LatLng( 18.8401849,73.9071292))
                .title("Rice")
                .snippet("Pune,Maharashtra")
                .rotation((float) 0.5)
                .icon(bitmapDescriptorFromVector(getApplicationContext(),R.drawable.rice))
                .visible(false));
        if (SphericalUtil.computeDistanceBetween(latLng,rice5.getPosition()) < 200000) {
            rice5.setVisible(true);
        }
        String id173 = rice5.getId();
        markerMap.put(id173,"action_rice");

        Marker rice6 = mMap.addMarker(new MarkerOptions()
                .position(new LatLng( 23.2571641,72.253918))
                .title("Rice")
                .snippet("Gandhinagar,Gujarat")
                .rotation((float) 0.5)
                .icon(bitmapDescriptorFromVector(getApplicationContext(),R.drawable.rice))
                .visible(false));
        if (SphericalUtil.computeDistanceBetween(latLng,rice6.getPosition()) < 200000) {
            rice6.setVisible(true);
        }
        String id174 = rice6.getId();
        markerMap.put(id174,"action_rice");

        Marker rice7 = mMap.addMarker(new MarkerOptions()
                .position(new LatLng( 22.260527,73.1675671))
                .title("Rice")
                .snippet("Vadodara,Gujarat")
                .rotation((float) 0.5)
                .icon(bitmapDescriptorFromVector(getApplicationContext(),R.drawable.rice))
                .visible(false));
        if (SphericalUtil.computeDistanceBetween(latLng,rice7.getPosition()) < 200000) {
            rice7.setVisible(true);
        }
        String id175 = rice7.getId();
        markerMap.put(id175,"action_rice");

        Marker rice8 = mMap.addMarker(new MarkerOptions()
                .position(new LatLng( 20.9680693,72.9150651))
                .title("Rice")
                .snippet("Navsari,Gujarat")
                .rotation((float) 0.5)
                .icon(bitmapDescriptorFromVector(getApplicationContext(),R.drawable.rice))
                .visible(false));
        if (SphericalUtil.computeDistanceBetween(latLng,rice8.getPosition()) < 200000) {
            rice8.setVisible(true);
        }
        String id176 = rice8.getId();
        markerMap.put(id176,"action_rice");

        Marker rice9 = mMap.addMarker(new MarkerOptions()
                .position(new LatLng( 18.0919945,83.6758909))
                .title("Rice")
                .snippet("Vizianagaram,Andhra Pradesh")
                .rotation((float) 0.5)
                .icon(bitmapDescriptorFromVector(getApplicationContext(),R.drawable.rice))
                .visible(false));
        if (SphericalUtil.computeDistanceBetween(latLng,rice9.getPosition()) < 200000) {
            rice9.setVisible(true);
        }
        String id177 = rice9.getId();
        markerMap.put(id177,"action_rice");

        Marker rice10 = mMap.addMarker(new MarkerOptions()
                .position(new LatLng( 17.1493272,81.3777578))
                .title("Rice")
                .snippet("West Godavari,Andhra Pradesh")
                .rotation((float) 0.5)
                .icon(bitmapDescriptorFromVector(getApplicationContext(),R.drawable.rice))
                .visible(false));
        if (SphericalUtil.computeDistanceBetween(latLng,rice10.getPosition()) < 200000) {
            rice10.setVisible(true);
        }
        String id178 = rice10.getId();
        markerMap.put(id178,"action_rice");

        Marker rice11 = mMap.addMarker(new MarkerOptions()
                .position(new LatLng( 16.713958,80.7395574))
                .title("Rice")
                .snippet("Krishna,Andhra Pradesh")
                .rotation((float) 0.5)
                .icon(bitmapDescriptorFromVector(getApplicationContext(),R.drawable.rice))
                .visible(false));
        if (SphericalUtil.computeDistanceBetween(latLng,rice11.getPosition()) < 200000) {
            rice11.setVisible(true);
        }
        String id179 = rice11.getId();
        markerMap.put(id179,"action_rice");

        Marker rice12 = mMap.addMarker(new MarkerOptions()
                .position(new LatLng( 16.2765955,80.2709792))
                .title("Rice")
                .snippet("Guntur,Andhra Pradesh")
                .rotation((float) 0.5)
                .icon(bitmapDescriptorFromVector(getApplicationContext(),R.drawable.rice))
                .visible(false));
        if (SphericalUtil.computeDistanceBetween(latLng,rice12.getPosition()) < 200000) {
            rice12.setVisible(true);
        }
        String id180 = rice12.getId();
        markerMap.put(id180,"action_rice");

        Marker rice13 = mMap.addMarker(new MarkerOptions()
                .position(new LatLng( 15.1407055,80.0514787))
                .title("Rice")
                .snippet("Prakasam,Andhra Pradesh")
                .rotation((float) 0.5)
                .icon(bitmapDescriptorFromVector(getApplicationContext(),R.drawable.rice))
                .visible(false));
        if (SphericalUtil.computeDistanceBetween(latLng,rice13.getPosition()) < 200000) {
            rice13.setVisible(true);
        }
        String id181 = rice13.getId();
        markerMap.put(id181,"action_rice");

        Marker rice14 = mMap.addMarker(new MarkerOptions()
                .position(new LatLng( 16.0106918,80.0374893))
                .title("Rice")
                .snippet("Prakasam,Andhra Pradesh")
                .rotation((float) 0.5)
                .icon(bitmapDescriptorFromVector(getApplicationContext(),R.drawable.rice))
                .visible(false));
        if (SphericalUtil.computeDistanceBetween(latLng,rice14.getPosition()) < 200000) {
            rice14.setVisible(true);
        }
        String id182 = rice14.getId();
        markerMap.put(id182,"action_rice");

        Marker rice15 = mMap.addMarker(new MarkerOptions()
                .position(new LatLng( 15.1967855,78.0369791))
                .title("Rice")
                .snippet("Kurnool,Andhra Pradesh")
                .rotation((float) 0.5)
                .icon(bitmapDescriptorFromVector(getApplicationContext(),R.drawable.rice))
                .visible(false));
        if (SphericalUtil.computeDistanceBetween(latLng,rice15.getPosition()) < 200000) {
            rice15.setVisible(true);
        }
        String id183 = rice15.getId();
        markerMap.put(id183,"action_rice");

        Marker rice16 = mMap.addMarker(new MarkerOptions()
                .position(new LatLng( 14.719659,77.5920217))
                .title("Rice")
                .snippet("Anantapur,Andhra Pradesh")
                .rotation((float) 0.5)
                .icon(bitmapDescriptorFromVector(getApplicationContext(),R.drawable.rice))
                .visible(false));
        if (SphericalUtil.computeDistanceBetween(latLng,rice16.getPosition()) < 200000) {
            rice16.setVisible(true);
        }
        String id184 = rice16.getId();
        markerMap.put(id184,"action_rice");

        Marker rice17 = mMap.addMarker(new MarkerOptions()
                .position(new LatLng( 15.0433221,78.3341844))
                .title("Rice")
                .snippet("YSR Kadapa,Andhra Pradesh")
                .rotation((float) 0.5)
                .icon(bitmapDescriptorFromVector(getApplicationContext(),R.drawable.rice))
                .visible(false));
        if (SphericalUtil.computeDistanceBetween(latLng,rice17.getPosition()) < 200000) {
            rice17.setVisible(true);
        }
        String id185 = rice17.getId();
        markerMap.put(id185,"action_rice");

        Marker rice18 = mMap.addMarker(new MarkerOptions()
                .position(new LatLng( 14.4937192,79.6553043))
                .title("Rice")
                .snippet("Nellore,Andhra Pradesh")
                .rotation((float) 0.5)
                .icon(bitmapDescriptorFromVector(getApplicationContext(),R.drawable.rice))
                .visible(false));
        if (SphericalUtil.computeDistanceBetween(latLng,rice18.getPosition()) < 200000) {
            rice18.setVisible(true);
        }
        String id186 = rice18.getId();
        markerMap.put(id186,"action_rice");

        Marker rice19 = mMap.addMarker(new MarkerOptions()
                .position(new LatLng( 26.2603336,92.3310749))
                .title("Rice")
                .snippet("Morigaon,Assam")
                .rotation((float) 0.5)
                .icon(bitmapDescriptorFromVector(getApplicationContext(),R.drawable.rice))
                .visible(false));
        if (SphericalUtil.computeDistanceBetween(latLng,rice19.getPosition()) < 200000) {
            rice19.setVisible(true);
        }
        String id187 = rice19.getId();
        markerMap.put(id187,"action_rice");

        Marker rice20 = mMap.addMarker(new MarkerOptions()
                .position(new LatLng( 25.8774375,92.8429856))
                .title("Rice")
                .snippet("Karbi Anglong,Assam")
                .rotation((float) 0.5)
                .icon(bitmapDescriptorFromVector(getApplicationContext(),R.drawable.rice))
                .visible(false));
        if (SphericalUtil.computeDistanceBetween(latLng,rice20.getPosition()) < 200000) {
            rice20.setVisible(true);
        }
        String id188 = rice20.getId();
        markerMap.put(id188,"action_rice");

        Marker rice21 = mMap.addMarker(new MarkerOptions()
                .position(new LatLng( 26.8569286,94.1756956))
                .title("Rice")
                .snippet("Jorhat,Assam")
                .rotation((float) 0.5)
                .icon(bitmapDescriptorFromVector(getApplicationContext(),R.drawable.rice))
                .visible(false));
        if (SphericalUtil.computeDistanceBetween(latLng,rice21.getPosition()) < 200000) {
            rice21.setVisible(true);
        }
        String id189 = rice21.getId();
        markerMap.put(id189,"action_rice");

        Marker rice22 = mMap.addMarker(new MarkerOptions()
                .position(new LatLng( 27.5103533,95.3465964))
                .title("Rice")
                .snippet("Tinsukai,Assam")
                .rotation((float) 0.5)
                .icon(bitmapDescriptorFromVector(getApplicationContext(),R.drawable.rice))
                .visible(false));
        if (SphericalUtil.computeDistanceBetween(latLng,rice22.getPosition()) < 200000) {
            rice22.setVisible(true);
        }
        String id190 = rice22.getId();
        markerMap.put(id190,"action_rice");

        Marker rice23 = mMap.addMarker(new MarkerOptions()
                .position(new LatLng( 26.1361328,87.4304357))
                .title("Rice")
                .snippet("Araria,Bihar")
                .rotation((float) 0.5)
                .icon(bitmapDescriptorFromVector(getApplicationContext(),R.drawable.rice))
                .visible(false));
        if (SphericalUtil.computeDistanceBetween(latLng,rice23.getPosition()) < 200000) {
            rice23.setVisible(true);
        }
        String id191 = rice23.getId();
        markerMap.put(id191,"action_rice");

        Marker rice24 = mMap.addMarker(new MarkerOptions()
                .position(new LatLng( 25.2148301,86.9523786))
                .title("Rice")
                .snippet("Bhagalpur,Bihar")
                .rotation((float) 0.5)
                .icon(bitmapDescriptorFromVector(getApplicationContext(),R.drawable.rice))
                .visible(false));
        if (SphericalUtil.computeDistanceBetween(latLng,rice24.getPosition()) < 200000) {
            rice24.setVisible(true);
        }
        String id192 = rice24.getId();
        markerMap.put(id192,"action_rice");

        Marker rice25 = mMap.addMarker(new MarkerOptions()
                .position(new LatLng( 26.1296856,86.5977723))
                .title("Rice")
                .snippet("Supaul,Bihar")
                .rotation((float) 0.5)
                .icon(bitmapDescriptorFromVector(getApplicationContext(),R.drawable.rice))
                .visible(false));
        if (SphericalUtil.computeDistanceBetween(latLng,rice25.getPosition()) < 200000) {
            rice25.setVisible(true);
        }
        String id193 = rice25.getId();
        markerMap.put(id193,"action_rice");

        Marker rice26 = mMap.addMarker(new MarkerOptions()
                .position(new LatLng( 26.3479631,86.0602104))
                .title("Rice")
                .snippet("Madhubani,Bihar")
                .rotation((float) 0.5)
                .icon(bitmapDescriptorFromVector(getApplicationContext(),R.drawable.rice))
                .visible(false));
        if (SphericalUtil.computeDistanceBetween(latLng,rice26.getPosition()) < 200000) {
            rice26.setVisible(true);
        }
        String id194 = rice26.getId();
        markerMap.put(id194,"action_rice");

        Marker rice27 = mMap.addMarker(new MarkerOptions()
                .position(new LatLng( 24.7650045,85.0086464))
                .title("Rice")
                .snippet("Gaya,Bihar")
                .rotation((float) 0.5)
                .icon(bitmapDescriptorFromVector(getApplicationContext(),R.drawable.rice))
                .visible(false));
        if (SphericalUtil.computeDistanceBetween(latLng,rice27.getPosition()) < 200000) {
            rice27.setVisible(true);
        }
        String id195 = rice27.getId();
        markerMap.put(id195,"action_rice");

        Marker rice28 = mMap.addMarker(new MarkerOptions()
                .position(new LatLng( 27.1589469,84.3157251))
                .title("Rice")
                .snippet("West Champaran,Bihar")
                .rotation((float) 0.5)
                .icon(bitmapDescriptorFromVector(getApplicationContext(),R.drawable.rice))
                .visible(false));
        if (SphericalUtil.computeDistanceBetween(latLng,rice28.getPosition()) < 200000) {
            rice28.setVisible(true);
        }
        String id196 = rice28.getId();
        markerMap.put(id196,"action_rice");

        Marker rice29 = mMap.addMarker(new MarkerOptions()
                .position(new LatLng( 31.0769271,77.1744928))
                .title("Rice")
                .snippet("Shimla,Himachal Pradesh")
                .rotation((float) 0.5)
                .icon(bitmapDescriptorFromVector(getApplicationContext(),R.drawable.rice))
                .visible(false));
        if (SphericalUtil.computeDistanceBetween(latLng,rice29.getPosition()) < 200000) {
            rice29.setVisible(true);
        }
        String id197 = rice29.getId();
        markerMap.put(id197,"action_rice");

        Marker rice30 = mMap.addMarker(new MarkerOptions()
                .position(new LatLng( 33.3139516,75.7564761))
                .title("Rice")
                .snippet("Kishtwar,Jammu Kashmir")
                .rotation((float) 0.5)
                .icon(bitmapDescriptorFromVector(getApplicationContext(),R.drawable.rice))
                .visible(false));
        if (SphericalUtil.computeDistanceBetween(latLng,rice30.getPosition()) < 200000) {
            rice30.setVisible(true);
        }
        String id198 = rice30.getId();
        markerMap.put(id198,"action_rice");

        Marker rice31 = mMap.addMarker(new MarkerOptions()
                .position(new LatLng( 32.4831134,75.3273046))
                .title("Rice")
                .snippet("Udhampur,Jammu Kashmir")
                .rotation((float) 0.5)
                .icon(bitmapDescriptorFromVector(getApplicationContext(),R.drawable.rice))
                .visible(false));
        if (SphericalUtil.computeDistanceBetween(latLng,rice31.getPosition()) < 200000) {
            rice31.setVisible(true);
        }
        String id199 = rice31.getId();
        markerMap.put(id199,"action_rice");

        Marker rice32 = mMap.addMarker(new MarkerOptions()
                .position(new LatLng( 32.632623,74.9159809))
                .title("Rice")
                .snippet("Jammu,Jammu Kashmir")
                .rotation((float) 0.5)
                .icon(bitmapDescriptorFromVector(getApplicationContext(),R.drawable.rice))
                .visible(false));
        if (SphericalUtil.computeDistanceBetween(latLng,rice32.getPosition()) < 200000) {
            rice32.setVisible(true);
        }
        String id200 = rice32.getId();
        markerMap.put(id200,"action_rice");

        Marker rice33 = mMap.addMarker(new MarkerOptions()
                .position(new LatLng( 15.4766302,75.9749233))
                .title("Rice")
                .snippet("Koppal,Karnataka")
                .rotation((float) 0.5)
                .icon(bitmapDescriptorFromVector(getApplicationContext(),R.drawable.rice))
                .visible(false));
        if (SphericalUtil.computeDistanceBetween(latLng,rice33.getPosition()) < 200000) {
            rice33.setVisible(true);
        }
        String id201 = rice33.getId();
        markerMap.put(id201,"action_rice");

        Marker rice34 = mMap.addMarker(new MarkerOptions()
                .position(new LatLng( 14.9200953,74.3455377))
                .title("Rice")
                .snippet("Uttara Kannada,Karnataka")
                .rotation((float) 0.5)
                .icon(bitmapDescriptorFromVector(getApplicationContext(),R.drawable.rice))
                .visible(false));
        if (SphericalUtil.computeDistanceBetween(latLng,rice34.getPosition()) < 200000) {
            rice34.setVisible(true);
        }
        String id202 = rice34.getId();
        markerMap.put(id202,"action_rice");

        Marker rice35 = mMap.addMarker(new MarkerOptions()
                .position(new LatLng( 13.2977661,74.9722905))
                .title("Rice")
                .snippet("Udupi,Karnataka")
                .rotation((float) 0.5)
                .icon(bitmapDescriptorFromVector(getApplicationContext(),R.drawable.rice))
                .visible(false));
        if (SphericalUtil.computeDistanceBetween(latLng,rice35.getPosition()) < 200000) {
            rice35.setVisible(true);
        }
        String id203 = rice35.getId();
        markerMap.put(id203,"action_rice");

        Marker rice36 = mMap.addMarker(new MarkerOptions()
                .position(new LatLng( 20.489870,85.821550))
                .title("Rice")
                .snippet("Cuttack,Odisha")
                .rotation((float) 0.5)
                .icon(bitmapDescriptorFromVector(getApplicationContext(),R.drawable.rice))
                .visible(false));
        if (SphericalUtil.computeDistanceBetween(latLng,rice36.getPosition()) < 200000) {
            rice36.setVisible(true);
        }
        String id204 = rice36.getId();
        markerMap.put(id204,"action_rice");

        Marker rice37 = mMap.addMarker(new MarkerOptions()
                .position(new LatLng( 30.985726,76.530645))
                .title("Rice")
                .snippet("Rupnagar,Punjab")
                .rotation((float) 0.5)
                .icon(bitmapDescriptorFromVector(getApplicationContext(),R.drawable.rice))
                .visible(false));
        if (SphericalUtil.computeDistanceBetween(latLng,rice37.getPosition()) < 200000) {
            rice37.setVisible(true);
        }
        String id205 = rice37.getId();
        markerMap.put(id205,"action_rice");

        Marker rice38 = mMap.addMarker(new MarkerOptions()
                .position(new LatLng( 31.590260,74.914905))
                .title("Rice")
                .snippet("Amritsar,Punjab")
                .rotation((float) 0.5)
                .icon(bitmapDescriptorFromVector(getApplicationContext(),R.drawable.rice))
                .visible(false));
        if (SphericalUtil.computeDistanceBetween(latLng,rice38.getPosition()) < 200000) {
            rice38.setVisible(true);
        }
        String id206 = rice38.getId();
        markerMap.put(id206,"action_rice");

        Marker rice39 = mMap.addMarker(new MarkerOptions()
                .position(new LatLng( 31.355793,75.381542))
                .title("Rice")
                .snippet("Kapurthala,Punjab")
                .rotation((float) 0.5)
                .icon(bitmapDescriptorFromVector(getApplicationContext(),R.drawable.rice))
                .visible(false));
        if (SphericalUtil.computeDistanceBetween(latLng,rice39.getPosition()) < 200000) {
            rice39.setVisible(true);
        }
        String id207 = rice39.getId();
        markerMap.put(id207,"action_rice");

        Marker rice40 = mMap.addMarker(new MarkerOptions()
                .position(new LatLng( 32.255119,75.636116))
                .title("Rice")
                .snippet("Pathankot,Punjab")
                .rotation((float) 0.5)
                .icon(bitmapDescriptorFromVector(getApplicationContext(),R.drawable.rice))
                .visible(false));
        if (SphericalUtil.computeDistanceBetween(latLng,rice40.getPosition()) < 200000) {
            rice40.setVisible(true);
        }
        String id208 = rice40.getId();
        markerMap.put(id208,"action_rice");

        Marker rice41 = mMap.addMarker(new MarkerOptions()
                .position(new LatLng( 26.059709,76.355857))
                .title("Rice")
                .snippet("Sawai madhpur,Rajasthan")
                .rotation((float) 0.5)
                .icon(bitmapDescriptorFromVector(getApplicationContext(),R.drawable.rice))
                .visible(false));
        if (SphericalUtil.computeDistanceBetween(latLng,rice41.getPosition()) < 200000) {
            rice41.setVisible(true);
        }
        String id209 = rice41.getId();
        markerMap.put(id209,"action_rice");

        Marker rice42 = mMap.addMarker(new MarkerOptions()
                .position(new LatLng( 29.9642896,77.5749563))
                .title("Rice")
                .snippet("Saharanpur,Uttar Pradesh")
                .rotation((float) 0.5)
                .icon(bitmapDescriptorFromVector(getApplicationContext(),R.drawable.rice))
                .visible(false));
        if (SphericalUtil.computeDistanceBetween(latLng,rice42.getPosition()) < 200000) {
            rice42.setVisible(true);
        }
        String id210 = rice42.getId();
        markerMap.put(id210,"action_rice");

        Marker rice43 = mMap.addMarker(new MarkerOptions()
                .position(new LatLng( 28.700466,77.498934))
                .title("Rice")
                .snippet("Ghaziabad,Uttar Pradesh")
                .rotation((float) 0.5)
                .icon(bitmapDescriptorFromVector(getApplicationContext(),R.drawable.rice))
                .visible(false));
        if (SphericalUtil.computeDistanceBetween(latLng,rice43.getPosition()) < 200000) {
            rice43.setVisible(true);
        }
        String id211 = rice43.getId();
        markerMap.put(id211,"action_rice");

        Marker rice44 = mMap.addMarker(new MarkerOptions()
                .position(new LatLng( 27.211849,77.902565))
                .title("Rice")
                .snippet("Agra,Uttar Pradesh")
                .rotation((float) 0.5)
                .icon(bitmapDescriptorFromVector(getApplicationContext(),R.drawable.rice))
                .visible(false));
        if (SphericalUtil.computeDistanceBetween(latLng,rice44.getPosition()) < 200000) {
            rice44.setVisible(true);
        }
        String id212 = rice44.getId();
        markerMap.put(id212,"action_rice");

        Marker rice45 = mMap.addMarker(new MarkerOptions()
                .position(new LatLng( 27.677519,78.142137))
                .title("Rice")
                .snippet("Kheria,Uttar Pradesh")
                .rotation((float) 0.5)
                .icon(bitmapDescriptorFromVector(getApplicationContext(),R.drawable.rice))
                .visible(false));
        if (SphericalUtil.computeDistanceBetween(latLng,rice45.getPosition()) < 200000) {
            rice45.setVisible(true);
        }
        String id213 = rice45.getId();
        markerMap.put(id213,"action_rice");

        Marker rice46 = mMap.addMarker(new MarkerOptions()
                .position(new LatLng( 27.579854,81.615557))
                .title("Rice")
                .snippet("Bahraich,Uttar Pradesh")
                .rotation((float) 0.5)
                .icon(bitmapDescriptorFromVector(getApplicationContext(),R.drawable.rice))
                .visible(false));
        if (SphericalUtil.computeDistanceBetween(latLng,rice46.getPosition()) < 200000) {
            rice46.setVisible(true);
        }
        String id214 = rice46.getId();
        markerMap.put(id214,"action_rice");

//sesame
        Marker sesame1=mMap.addMarker(new MarkerOptions()
                .position(new LatLng(21.626913,70.720903))
                .title("Sesame")
                .snippet("Rajkot,Gujarat")
                .rotation((float) 0.5)
                .icon(bitmapDescriptorFromVector(getApplicationContext(),R.drawable.sesame))
                .visible(false));
        if (SphericalUtil.computeDistanceBetween(latLng,sesame1.getPosition()) < 200000) {
            sesame1.setVisible(true);
        }
        String id215 = sesame1.getId();
        markerMap.put(id215,"action_sesame");

        Marker sesame2=mMap.addMarker(new MarkerOptions()
                .position(new LatLng( 22.938650,87.304805))
                .title("Sesame")
                .snippet("Bankura,West bengal")
                .rotation((float) 0.5)
                .icon(bitmapDescriptorFromVector(getApplicationContext(),R.drawable.sesame))
                .visible(false));
        if (SphericalUtil.computeDistanceBetween(latLng,sesame2.getPosition()) < 200000) {
            sesame2.setVisible(true);
        }
        String id216 = sesame2.getId();
        markerMap.put(id216,"action_sesame");

        Marker sesame3 =mMap.addMarker(new MarkerOptions()
                .position(new LatLng( 23.5547822,72.6811814))
                .title("Sesame")
                .snippet("Ahmedabad,Gujarat")
                .rotation((float) 0.5)
                .icon(bitmapDescriptorFromVector(getApplicationContext(),R.drawable.sesame))
                .visible(false));
        if (SphericalUtil.computeDistanceBetween(latLng,sesame3.getPosition()) < 200000) {
            sesame3.setVisible(true);
        }
        String id217 = sesame3.getId();
        markerMap.put(id217,"action_sesame");

        Marker sesame4 =mMap.addMarker(new MarkerOptions()
                .position(new LatLng( 26.1363461,93.1133449))
                .title("Sesame")
                .snippet("Karbi Anglong,Assam")
                .rotation((float) 0.5)
                .icon(bitmapDescriptorFromVector(getApplicationContext(),R.drawable.sesame))
                .visible(false));
        if (SphericalUtil.computeDistanceBetween(latLng,sesame4.getPosition()) < 200000) {
            sesame4.setVisible(true);
        }
        String id218 = sesame4.getId();
        markerMap.put(id218,"action_sesame");

        Marker sesame5 =mMap.addMarker(new MarkerOptions()
                .position(new LatLng( 25.2940865,93.1449919))
                .title("Sesame")
                .snippet("North Cachar hills,Assam")
                .rotation((float) 0.5)
                .icon(bitmapDescriptorFromVector(getApplicationContext(),R.drawable.sesame))
                .visible(false));
        if (SphericalUtil.computeDistanceBetween(latLng,sesame5.getPosition()) < 200000) {
            sesame5.setVisible(true);
        }
        String id219 = sesame5.getId();
        markerMap.put(id219,"action_sesame");

        Marker sesame6 =mMap.addMarker(new MarkerOptions()
                .position(new LatLng( 24.628284,83.9225635))
                .title("Sesame")
                .snippet("Rohtas,Bihar")
                .rotation((float) 0.5)
                .icon(bitmapDescriptorFromVector(getApplicationContext(),R.drawable.sesame))
                .visible(false));
        if (SphericalUtil.computeDistanceBetween(latLng,sesame6.getPosition()) < 200000) {
            sesame6.setVisible(true);
        }
        String id220 = sesame6.getId();
        markerMap.put(id220,"action_sesame");

        Marker sesame7 =mMap.addMarker(new MarkerOptions()
                .position(new LatLng( 12.9928119,76.116288))
                .title("Sesame")
                .snippet("Hassan,Karnataka")
                .rotation((float) 0.5)
                .icon(bitmapDescriptorFromVector(getApplicationContext(),R.drawable.sesame))
                .visible(false));
        if (SphericalUtil.computeDistanceBetween(latLng,sesame7.getPosition()) < 200000) {
            sesame7.setVisible(true);
        }
        String id221 = sesame7.getId();
        markerMap.put(id221,"action_sesame");

        Marker sesame8 =mMap.addMarker(new MarkerOptions()
                .position(new LatLng( 22.042923,87.746943))
                .title("Sesame")
                .snippet("Purva medinipur,West Bengal")
                .rotation((float) 0.5)
                .icon(bitmapDescriptorFromVector(getApplicationContext(),R.drawable.sesame))
                .visible(false));
        if (SphericalUtil.computeDistanceBetween(latLng,sesame8.getPosition()) < 200000) {
            sesame8.setVisible(true);
        }
        String id222 = sesame8.getId();
        markerMap.put(id222,"action_sesame");

        Marker sesame9 =mMap.addMarker(new MarkerOptions()
                .position(new LatLng( 25.260411,80.893047))
                .title("Sesame")
                .snippet("Barwara,Uttar Pradesh")
                .rotation((float) 0.5)
                .icon(bitmapDescriptorFromVector(getApplicationContext(),R.drawable.sesame))
                .visible(false));
        if (SphericalUtil.computeDistanceBetween(latLng,sesame9.getPosition()) < 200000) {
            sesame9.setVisible(true);
        }
        String id223 = sesame9.getId();
        markerMap.put(id223,"action_sesame");

        Marker sesame10 =mMap.addMarker(new MarkerOptions()
                .position(new LatLng( 26.512019,77.881133))
                .title("Sesame")
                .snippet("Morena,Uttar Pradesh")
                .rotation((float) 0.5)
                .icon(bitmapDescriptorFromVector(getApplicationContext(),R.drawable.sesame))
                .visible(false));
        if (SphericalUtil.computeDistanceBetween(latLng,sesame10.getPosition()) < 200000) {
            sesame10.setVisible(true);
        }
        String id224 = sesame10.getId();
        markerMap.put(id224,"action_sesame");

        //sorghum
        Marker sor1=mMap.addMarker(new MarkerOptions()
                .position(new LatLng(18.986876,75.770952))
                .title("Sorghum")
                .snippet("Beed,Maharastra")
                .rotation((float) 0.5)
                .icon(bitmapDescriptorFromVector(getApplicationContext(),R.drawable.sor))
                .visible(false));
        if (SphericalUtil.computeDistanceBetween(latLng,sor1.getPosition()) < 200000) {
            sor1.setVisible(true);
        }
        String id225 = sor1.getId();
        markerMap.put(id225,"action_sor");

        Marker sor2=mMap.addMarker(new MarkerOptions()
                .position(new LatLng( 14.715736,75.725490))
                .title("Sorghum")
                .snippet("Haveri,Karnataka")
                .rotation((float) 0.5)
                .icon(bitmapDescriptorFromVector(getApplicationContext(),R.drawable.sor))
                .visible(false));
        if (SphericalUtil.computeDistanceBetween(latLng,sor2.getPosition()) < 200000) {
            sor2.setVisible(true);
        }
        String id226 = sor2.getId();
        markerMap.put(id226,"action_sor");

        Marker sor3=mMap.addMarker(new MarkerOptions()
                .position(new LatLng( 21.2669565,74.7808977))
                .title("Sorghum")
                .snippet("Dhule,Maharashtra")
                .rotation((float) 0.5)
                .icon(bitmapDescriptorFromVector(getApplicationContext(),R.drawable.sor))
                .visible(false));
        if (SphericalUtil.computeDistanceBetween(latLng,sor3.getPosition()) < 200000) {
            sor3.setVisible(true);
        }
        String id227 = sor3.getId();
        markerMap.put(id227,"action_sor");

        Marker sor4=mMap.addMarker(new MarkerOptions()
                .position(new LatLng(21.3932543,74.2375777))
                .title("Sorghum")
                .snippet("Nandurbar,Maharashtra")
                .rotation((float) 0.5)
                .icon(bitmapDescriptorFromVector(getApplicationContext(),R.drawable.sor))
                .visible(false));
        if (SphericalUtil.computeDistanceBetween(latLng,sor4.getPosition()) < 200000) {
            sor4.setVisible(true);
        }
        String id228 = sor4.getId();
        markerMap.put(id228,"action_sor");

        Marker sor5 =mMap.addMarker(new MarkerOptions()
                .position(new LatLng(18.4475147,73.8434084))
                .title("Sorghum")
                .snippet("Pune,Maharashtra")
                .rotation((float) 0.5)
                .icon(bitmapDescriptorFromVector(getApplicationContext(),R.drawable.sor))
                .visible(false));
        if (SphericalUtil.computeDistanceBetween(latLng,sor5.getPosition()) < 200000) {
            sor5.setVisible(true);
        }
        String id229 = sor5.getId();
        markerMap.put(id229,"action_sor");

        Marker sor6 =mMap.addMarker(new MarkerOptions()
                .position(new LatLng(22.9832719,72.9809737))
                .title("Sorghum")
                .snippet("Ahmedabad,Gujarat")
                .rotation((float) 0.5)
                .icon(bitmapDescriptorFromVector(getApplicationContext(),R.drawable.sor))
                .visible(false));
        if (SphericalUtil.computeDistanceBetween(latLng,sor6.getPosition()) < 200000) {
            sor6.setVisible(true);
        }
        String id230 = sor6.getId();
        markerMap.put(id230,"action_sor");

        Marker sor7 =mMap.addMarker(new MarkerOptions()
                .position(new LatLng(22.5462744,73.1193225))
                .title("Sorghum")
                .snippet("Vadodara,Gujarat")
                .rotation((float) 0.5)
                .icon(bitmapDescriptorFromVector(getApplicationContext(),R.drawable.sor))
                .visible(false));
        if (SphericalUtil.computeDistanceBetween(latLng,sor7.getPosition()) < 200000) {
            sor7.setVisible(true);
        }
        String id231 = sor7.getId();
        markerMap.put(id231,"action_sor");

        Marker sor8 =mMap.addMarker(new MarkerOptions()
                .position(new LatLng(16.642075,80.5673684))
                .title("Sorghum")
                .snippet("Guntur,Andhra Pradesh")
                .rotation((float) 0.5)
                .icon(bitmapDescriptorFromVector(getApplicationContext(),R.drawable.sor))
                .visible(false));
        if (SphericalUtil.computeDistanceBetween(latLng,sor8.getPosition()) < 200000) {
            sor8.setVisible(true);
        }
        String id232 = sor8.getId();
        markerMap.put(id232,"action_sor");

        Marker sor9 =mMap.addMarker(new MarkerOptions()
                .position(new LatLng(15.8984676,74.814463))
                .title("Sorghum")
                .snippet("Belgaum,Karnataka")
                .rotation((float) 0.5)
                .icon(bitmapDescriptorFromVector(getApplicationContext(),R.drawable.sor))
                .visible(false));
        if (SphericalUtil.computeDistanceBetween(latLng,sor9.getPosition()) < 200000) {
            sor9.setVisible(true);
        }
        String id233 = sor9.getId();
        markerMap.put(id233,"action_sor");

        Marker sor10 =mMap.addMarker(new MarkerOptions()
                .position(new LatLng(16.8135701,75.7024928))
                .title("Sorghum")
                .snippet("Bijapur,Karnataka")
                .rotation((float) 0.5)
                .icon(bitmapDescriptorFromVector(getApplicationContext(),R.drawable.sor))
                .visible(false));
        if (SphericalUtil.computeDistanceBetween(latLng,sor10.getPosition()) < 200000) {
            sor10.setVisible(true);
        }
        String id234 = sor10.getId();
        markerMap.put(id234,"action_sor");

        Marker sor11 =mMap.addMarker(new MarkerOptions()
                .position(new LatLng(20.133158,85.983432))
                .title("Sorghum")
                .snippet("Kalahandi,Odisha")
                .rotation((float) 0.5)
                .icon(bitmapDescriptorFromVector(getApplicationContext(),R.drawable.sor))
                .visible(false));
        if (SphericalUtil.computeDistanceBetween(latLng,sor11.getPosition()) < 200000) {
            sor11.setVisible(true);
        }
        String id235 = sor11.getId();
        markerMap.put(id235,"action_sor");

        Marker sor12 =mMap.addMarker(new MarkerOptions()
                .position(new LatLng(18.809900,82.720923))
                .title("Sorghum")
                .snippet("Koraput,Odisha")
                .rotation((float) 0.5)
                .icon(bitmapDescriptorFromVector(getApplicationContext(),R.drawable.sor))
                .visible(false));
        if (SphericalUtil.computeDistanceBetween(latLng,sor12.getPosition()) < 200000) {
            sor12.setVisible(true);
        }
        String id236 = sor12.getId();
        markerMap.put(id236,"action_sor");

        Marker sor13 =mMap.addMarker(new MarkerOptions()
                .position(new LatLng(24.578791,76.164549))
                .title("Sorghum")
                .snippet("Jhalawar,Rajasthan")
                .rotation((float) 0.5)
                .icon(bitmapDescriptorFromVector(getApplicationContext(),R.drawable.sor))
                .visible(false));
        if (SphericalUtil.computeDistanceBetween(latLng,sor13.getPosition()) < 200000) {
            sor13.setVisible(true);
        }
        String id237 = sor13.getId();
        markerMap.put(id237,"action_sor");

        Marker sor14 =mMap.addMarker(new MarkerOptions()
                .position(new LatLng(25.2133838,75.8365463))
                .title("Sorghum")
                .snippet("Kota,Rajasthan")
                .rotation((float) 0.5)
                .icon(bitmapDescriptorFromVector(getApplicationContext(),R.drawable.sor))
                .visible(false));
        if (SphericalUtil.computeDistanceBetween(latLng,sor14.getPosition()) < 200000) {
            sor14.setVisible(true);
        }
        String id238 = sor14.getId();
        markerMap.put(id238,"action_sor");

        Marker sor15 =mMap.addMarker(new MarkerOptions()
                .position(new LatLng(24.092389,75.075389))
                .title("Sorghum")
                .snippet("Mandsaur,Madhya Pradesh")
                .rotation((float) 0.5)
                .icon(bitmapDescriptorFromVector(getApplicationContext(),R.drawable.sor))
                .visible(false));
        if (SphericalUtil.computeDistanceBetween(latLng,sor15.getPosition()) < 200000) {
            sor15.setVisible(true);
        }
        String id239 = sor15.getId();
        markerMap.put(id239,"action_sor");

        Marker sor16 =mMap.addMarker(new MarkerOptions()
                .position(new LatLng(23.039650,79.211833))
                .title("Sorghum")
                .snippet("Narsinghpur,Madhya Pradesh")
                .rotation((float) 0.5)
                .icon(bitmapDescriptorFromVector(getApplicationContext(),R.drawable.sor))
                .visible(false));
        if (SphericalUtil.computeDistanceBetween(latLng,sor16.getPosition()) < 200000) {
            sor16.setVisible(true);
        }
        String id240 = sor16.getId();
        markerMap.put(id240,"action_sor");



        //soyabean
        Marker soyabean1=mMap.addMarker(new MarkerOptions()
                .position(new LatLng(23.151412,77.453936))
                .title("Soyabean")
                .snippet("Bhopal,Madhya Pradesh")
                .rotation((float) 0.5)
                .icon(bitmapDescriptorFromVector(getApplicationContext(),R.drawable.soyabean))
                .visible(false));
        if (SphericalUtil.computeDistanceBetween(latLng,soyabean1.getPosition()) < 200000) {
            soyabean1.setVisible(true);
        }
        String id241 = soyabean1.getId();
        markerMap.put(id241,"action_soy");

        Marker soyabean2=mMap.addMarker(new MarkerOptions()
                .position(new LatLng( 21.252773,79.184813))
                .title("Soyabean")
                .snippet("Nagpur,Maharashtra")
                .rotation((float) 0.5)
                .icon(bitmapDescriptorFromVector(getApplicationContext(),R.drawable.soyabean))
                .visible(false));
        if (SphericalUtil.computeDistanceBetween(latLng,soyabean2.getPosition()) < 200000) {
            soyabean2.setVisible(true);
        }
        String id242 = soyabean2.getId();
        markerMap.put(id242,"action_soy");

        Marker soyabean3=mMap.addMarker(new MarkerOptions()
                .position(new LatLng( 26.513293,77.030616))
                .title("Soyabean")
                .snippet("Karauli,Rajasthan")
                .rotation((float) 0.5)
                .icon(bitmapDescriptorFromVector(getApplicationContext(),R.drawable.soyabean))
                .visible(false));
        if (SphericalUtil.computeDistanceBetween(latLng,soyabean3.getPosition()) < 200000) {
            soyabean3.setVisible(true);
        }
        String id243 = soyabean3.getId();
        markerMap.put(id243,"action_soy");

        Marker soyabean4=mMap.addMarker(new MarkerOptions()
                .position(new LatLng( 27.201986,77.454777))
                .title("Soyabean")
                .snippet("Bharatpur,Rajasthan")
                .rotation((float) 0.5)
                .icon(bitmapDescriptorFromVector(getApplicationContext(),R.drawable.soyabean))
                .visible(false));
        if (SphericalUtil.computeDistanceBetween(latLng,soyabean4.getPosition()) < 200000) {
            soyabean4.setVisible(true);
        }
        String id244 = soyabean4.getId();
        markerMap.put(id244,"action_soy");

        Marker soyabean5=mMap.addMarker(new MarkerOptions()
                .position(new LatLng( 23.913736,91.358568))
                .title("Soyabean")
                .snippet("West Tripura,Tripura")
                .rotation((float) 0.5)
                .icon(bitmapDescriptorFromVector(getApplicationContext(),R.drawable.soyabean))
                .visible(false));
        if (SphericalUtil.computeDistanceBetween(latLng,soyabean5.getPosition()) < 200000) {
            soyabean5.setVisible(true);
        }
        String id245 = soyabean5.getId();
        markerMap.put(id245,"action_soy");

        Marker soyabean6=mMap.addMarker(new MarkerOptions()
                .position(new LatLng( 23.431914,91.7468784))
                .title("Soyabean")
                .snippet("Gomati,Tripura")
                .rotation((float) 0.5)
                .icon(bitmapDescriptorFromVector(getApplicationContext(),R.drawable.soyabean))
                .visible(false));
        if (SphericalUtil.computeDistanceBetween(latLng,soyabean6.getPosition()) < 200000) {
            soyabean6.setVisible(true);
        }
        String id246 = soyabean6.getId();
        markerMap.put(id246,"action_soy");

        Marker soyabean7=mMap.addMarker(new MarkerOptions()
                .position(new LatLng( 25.560317,77.643937))
                .title("Soyabean")
                .snippet("Shivpuri,Madhya Pradesh")
                .rotation((float) 0.5)
                .icon(bitmapDescriptorFromVector(getApplicationContext(),R.drawable.soyabean))
                .visible(false));
        if (SphericalUtil.computeDistanceBetween(latLng,soyabean7.getPosition()) < 200000) {
            soyabean7.setVisible(true);
        }
        String id247 = soyabean7.getId();
        markerMap.put(id247,"action_soy");

        Marker soyabean8=mMap.addMarker(new MarkerOptions()
                .position(new LatLng( 22.303269,78.963753))
                .title("Soyabean")
                .snippet("Chhindwara,Madhya Pradesh")
                .rotation((float) 0.5)
                .icon(bitmapDescriptorFromVector(getApplicationContext(),R.drawable.soyabean))
                .visible(false));
        if (SphericalUtil.computeDistanceBetween(latLng,soyabean8.getPosition()) < 200000) {
            soyabean8.setVisible(true);
        }
        String id248 = soyabean8.getId();
        markerMap.put(id248,"action_soy");

        Marker soyabean9=mMap.addMarker(new MarkerOptions()
                .position(new LatLng( 24.013261,76.722729))
                .title("Soyabean")
                .snippet("Rajgarh,Madhya Pradesh")
                .rotation((float) 0.5)
                .icon(bitmapDescriptorFromVector(getApplicationContext(),R.drawable.soyabean))
                .visible(false));
        if (SphericalUtil.computeDistanceBetween(latLng,soyabean9.getPosition()) < 200000) {
            soyabean9.setVisible(true);
        }
        String id249 = soyabean9.getId();
        markerMap.put(id249,"action_soy");

        Marker soyabean10 = mMap.addMarker(new MarkerOptions()
                .position(new LatLng(22.6775847,73.9507212))
                .title("Soyabean")
                .snippet("Dohad,Gujarat")
                .rotation((float) 0.5)
                .icon(bitmapDescriptorFromVector(getApplicationContext(),R.drawable.soyabean))
                .visible(false));
        if (SphericalUtil.computeDistanceBetween(latLng,soyabean10.getPosition()) < 200000) {
            soyabean10.setVisible(true);
        }
        String id250 = soyabean10.getId();
        markerMap.put(id250,"action_soy");

        //sugarcane

        Marker sugarcane1=mMap.addMarker(new MarkerOptions()
                .position(new LatLng( 19.086550,74.549908))
                .title("Sugarcane")
                .snippet("Ahmednagar,Maharashtra")
                .rotation((float) 0.5)
                .icon(bitmapDescriptorFromVector(getApplicationContext(),R.drawable.sugarcane1))
                .visible(false));
        if (SphericalUtil.computeDistanceBetween(latLng,sugarcane1.getPosition()) < 200000) {
            sugarcane1.setVisible(true);
        }
        String id251 = sugarcane1.getId();
        markerMap.put(id251,"action_sugar");

        Marker sugarcane2 = mMap.addMarker(new MarkerOptions()
                .position(new LatLng( 21.0072979,77.7238642))
                .title("Sugarcane")
                .snippet("Amravati,Maharashtra")
                .rotation((float) 0.5)
                .icon(bitmapDescriptorFromVector(getApplicationContext(),R.drawable.sugarcane1))
                .visible(false));
        if (SphericalUtil.computeDistanceBetween(latLng,sugarcane2.getPosition()) < 200000) {
            sugarcane2.setVisible(true);
        }
        String id252 = sugarcane2.getId();
        markerMap.put(id252,"action_sugar");

        Marker sugarcane3 = mMap.addMarker(new MarkerOptions()
                .position(new LatLng( 18.3009064,83.8757434))
                .title("Sugarcane")
                .snippet("Srikakulam,Andhra Pradesh")
                .rotation((float) 0.5)
                .icon(bitmapDescriptorFromVector(getApplicationContext(),R.drawable.sugarcane1))
                .visible(false));
        if (SphericalUtil.computeDistanceBetween(latLng,sugarcane3.getPosition()) < 200000) {
            sugarcane3.setVisible(true);
        }
        String id253 = sugarcane3.getId();
        markerMap.put(id253,"action_sugar");

        Marker sugarcane4 = mMap.addMarker(new MarkerOptions()
                .position(new LatLng( 18.0778517,83.443264))
                .title("Sugarcane")
                .snippet("Vizianagaram,Andhra Pradesh")
                .rotation((float) 0.5)
                .icon(bitmapDescriptorFromVector(getApplicationContext(),R.drawable.sugarcane1))
                .visible(false));
        if (SphericalUtil.computeDistanceBetween(latLng,sugarcane4.getPosition()) < 200000) {
            sugarcane4.setVisible(true);
        }
        String id254 = sugarcane4.getId();
        markerMap.put(id254,"action_sugar");

        Marker sugarcane5 = mMap.addMarker(new MarkerOptions()
                .position(new LatLng( 17.6058541,83.1345935))
                .title("Sugarcane")
                .snippet("Visakhapatnam,Andhra Pradesh")
                .rotation((float) 0.5)
                .icon(bitmapDescriptorFromVector(getApplicationContext(),R.drawable.sugarcane1))
                .visible(false));
        if (SphericalUtil.computeDistanceBetween(latLng,sugarcane5.getPosition()) < 200000) {
            sugarcane5.setVisible(true);
        }
        String id255 = sugarcane5.getId();
        markerMap.put(id255,"action_sugar");

        Marker sugarcane6 = mMap.addMarker(new MarkerOptions()
                .position(new LatLng( 16.3963465,81.3749315))
                .title("Sugarcane")
                .snippet("West Godavari,Andhra Pradesh")
                .rotation((float) 0.5)
                .icon(bitmapDescriptorFromVector(getApplicationContext(),R.drawable.sugarcane1))
                .visible(false));
        if (SphericalUtil.computeDistanceBetween(latLng,sugarcane6.getPosition()) < 200000) {
            sugarcane6.setVisible(true);
        }
        String id256 = sugarcane6.getId();
        markerMap.put(id256,"action_sugar");

        Marker sugarcane7 = mMap.addMarker(new MarkerOptions()
                .position(new LatLng( 16.031978,81.0479664))
                .title("Sugarcane")
                .snippet("Krishna,Andhra Pradesh")
                .rotation((float) 0.5)
                .icon(bitmapDescriptorFromVector(getApplicationContext(),R.drawable.sugarcane1))
                .visible(false));
        if (SphericalUtil.computeDistanceBetween(latLng,sugarcane7.getPosition()) < 200000) {
            sugarcane7.setVisible(true);
        }
        String id257 = sugarcane7.getId();
        markerMap.put(id257,"action_sugar");

        Marker sugarcane8 = mMap.addMarker(new MarkerOptions()
                .position(new LatLng( 13.2237776,79.0789327))
                .title("Sugarcane")
                .snippet("Chittoor,Andhra Pradesh")
                .rotation((float) 0.5)
                .icon(bitmapDescriptorFromVector(getApplicationContext(),R.drawable.sugarcane1))
                .visible(false));
        if (SphericalUtil.computeDistanceBetween(latLng,sugarcane8.getPosition()) < 200000) {
            sugarcane8.setVisible(true);
        }
        String id258 = sugarcane8.getId();
        markerMap.put(id258,"action_sugar");

        Marker sugarcane9 = mMap.addMarker(new MarkerOptions()
                .position(new LatLng( 26.8103237,92.8351045))
                .title("Sugarcane")
                .snippet("Sonitpur,Assam")
                .rotation((float) 0.5)
                .icon(bitmapDescriptorFromVector(getApplicationContext(),R.drawable.sugarcane1))
                .visible(false));
        if (SphericalUtil.computeDistanceBetween(latLng,sugarcane9.getPosition()) < 200000) {
            sugarcane9.setVisible(true);
        }
        String id259 = sugarcane9.getId();
        markerMap.put(id259,"action_sugar");

        Marker sugarcane10 = mMap.addMarker(new MarkerOptions()
                .position(new LatLng( 24.8591778,92.3527871))
                .title("Sugarcane")
                .snippet("Karimganj,Assam")
                .rotation((float) 0.5)
                .icon(bitmapDescriptorFromVector(getApplicationContext(),R.drawable.sugarcane1))
                .visible(false));
        if (SphericalUtil.computeDistanceBetween(latLng,sugarcane10.getPosition()) < 200000) {
            sugarcane10.setVisible(true);
        }
        String id260 = sugarcane10.getId();
        markerMap.put(id260,"action_sugar");

        Marker sugarcane11 = mMap.addMarker(new MarkerOptions()
                .position(new LatLng( 27.0171538,93.7614001))
                .title("Sugarcane")
                .snippet("Lakhimpur,Assam")
                .rotation((float) 0.5)
                .icon(bitmapDescriptorFromVector(getApplicationContext(),R.drawable.sugarcane1))
                .visible(false));
        if (SphericalUtil.computeDistanceBetween(latLng,sugarcane11.getPosition()) < 200000) {
            sugarcane11.setVisible(true);
        }
        String id261 = sugarcane11.getId();
        markerMap.put(id261,"action_sugar");

        Marker sugarcane12 = mMap.addMarker(new MarkerOptions()
                .position(new LatLng( 26.177542,86.3724718))
                .title("Sugarcane")
                .snippet("Madhepur,Bihar")
                .rotation((float) 0.5)
                .icon(bitmapDescriptorFromVector(getApplicationContext(),R.drawable.sugarcane1))
                .visible(false));
        if (SphericalUtil.computeDistanceBetween(latLng,sugarcane12.getPosition()) < 200000) {
            sugarcane12.setVisible(true);
        }
        String id262 = sugarcane12.getId();
        markerMap.put(id262,"action_sugar");

        Marker sugarcane13 = mMap.addMarker(new MarkerOptions()
                .position(new LatLng( 31.4752376,76.2646685))
                .title("Sugarcane")
                .snippet("Una,Himachal Pradesh")
                .rotation((float) 0.5)
                .icon(bitmapDescriptorFromVector(getApplicationContext(),R.drawable.sugarcane1))
                .visible(false));
        if (SphericalUtil.computeDistanceBetween(latLng,sugarcane13.getPosition()) < 200000) {
            sugarcane13.setVisible(true);
        }
        String id263 = sugarcane13.getId();
        markerMap.put(id263,"action_sugar");

        Marker sugarcane14 = mMap.addMarker(new MarkerOptions()
                .position(new LatLng( 33.3191147,75.7493952))
                .title("Sugarcane")
                .snippet("Udhampur,Jammu Kashmir")
                .rotation((float) 0.5)
                .icon(bitmapDescriptorFromVector(getApplicationContext(),R.drawable.sugarcane1))
                .visible(false));
        if (SphericalUtil.computeDistanceBetween(latLng,sugarcane14.getPosition()) < 200000) {
            sugarcane14.setVisible(true);
        }
        String id264 = sugarcane14.getId();
        markerMap.put(id264,"action_sugar");

        Marker sugarcane15 = mMap.addMarker(new MarkerOptions()
                .position(new LatLng( 10.5148576,76.1602565))
                .title("Sugarcane")
                .snippet("Thrissur,Kerala")
                .rotation((float) 0.5)
                .icon(bitmapDescriptorFromVector(getApplicationContext(),R.drawable.sugarcane1))
                .visible(false));
        if (SphericalUtil.computeDistanceBetween(latLng,sugarcane15.getPosition()) < 200000) {
            sugarcane15.setVisible(true);
        }
        String id265 = sugarcane15.getId();
        markerMap.put(id265,"action_sugar");

        Marker sugarcane16 = mMap.addMarker(new MarkerOptions()
                .position(new LatLng( 20.494802,86.444659))
                .title("Sugarcane")
                .snippet("Kendrapara,Odisha")
                .rotation((float) 0.5)
                .icon(bitmapDescriptorFromVector(getApplicationContext(),R.drawable.sugarcane1))
                .visible(false));
        if (SphericalUtil.computeDistanceBetween(latLng,sugarcane16.getPosition()) < 200000) {
            sugarcane16.setVisible(true);
        }
        String id266 = sugarcane16.getId();
        markerMap.put(id266,"action_sugar");

        Marker sugarcane17 = mMap.addMarker(new MarkerOptions()
                .position(new LatLng( 22.9049027,88.3581044))
                .title("Sugarcane")
                .snippet("Hooghly,West Bengal")
                .rotation((float) 0.5)
                .icon(bitmapDescriptorFromVector(getApplicationContext(),R.drawable.sugarcane1))
                .visible(false));
        if (SphericalUtil.computeDistanceBetween(latLng,sugarcane17.getPosition()) < 200000) {
            sugarcane17.setVisible(true);
        }
        String id267 = sugarcane17.getId();
        markerMap.put(id267,"action_sugar");

        //tea
        Marker tea1 = mMap.addMarker(new MarkerOptions()
                .position(new LatLng(26.384473,92.684993))
                .title("Tea")
                .snippet("Nagaon,Assam")
                .rotation((float) 0.5)
                .icon(bitmapDescriptorFromVector(getApplicationContext(),R.drawable.tea))
                .visible(false));
        if (SphericalUtil.computeDistanceBetween(latLng,tea1.getPosition()) < 200000) {
            tea1.setVisible(true);
        }
        String id268 = tea1.getId();
        markerMap.put(id268,"action_tea");

        Marker tea2 = mMap.addMarker(new MarkerOptions()
                .position(new LatLng(22.397046,88.855864))
                .title("Tea")
                .snippet("North 24 Parganas,West Bengal")
                .rotation((float) 0.5)
                .icon(bitmapDescriptorFromVector(getApplicationContext(),R.drawable.tea))
                .visible(false));
        if (SphericalUtil.computeDistanceBetween(latLng,tea2.getPosition()) < 200000) {
            tea2.setVisible(true);
        }
        String id269 = tea2.getId();
        markerMap.put(id269,"action_tea");

        Marker tea3 = mMap.addMarker(new MarkerOptions()
                .position(new LatLng(26.7167719,94.2265322))
                .title("Tea")
                .snippet("Jorhat ,Assam")
                .rotation((float) 0.5)
                .icon(bitmapDescriptorFromVector(getApplicationContext(),R.drawable.tea))
                .visible(false));
        if (SphericalUtil.computeDistanceBetween(latLng,tea3.getPosition()) < 200000) {
            tea3.setVisible(true);
        }
        String id270 = tea3.getId();
        markerMap.put(id270,"action_tea");

        Marker tea4 = mMap.addMarker(new MarkerOptions()
                .position(new LatLng(24.6305068,92.8566039))
                .title("Tea")
                .snippet("Cachar ,Assam")
                .rotation((float) 0.5)
                .icon(bitmapDescriptorFromVector(getApplicationContext(),R.drawable.tea))
                .visible(false));
        if (SphericalUtil.computeDistanceBetween(latLng,tea4.getPosition()) < 200000) {
            tea4.setVisible(true);
        }
        String id271 = tea4.getId();
        markerMap.put(id271,"action_tea");

        Marker tea5 = mMap.addMarker(new MarkerOptions()
                .position(new LatLng(32.105633,76.2690707))
                .title("Tea")
                .snippet("Kangra,Himachal Pradesh")
                .rotation((float) 0.5)
                .icon(bitmapDescriptorFromVector(getApplicationContext(),R.drawable.tea))
                .visible(false));
        if (SphericalUtil.computeDistanceBetween(latLng,tea5.getPosition()) < 200000) {
            tea5.setVisible(true);
        }
        String id272 = tea5.getId();
        markerMap.put(id272,"action_tea");

        Marker tea6 = mMap.addMarker(new MarkerOptions()
                .position(new LatLng(9.7880412,76.8900514))
                .title("Tea")
                .snippet("Idukki,Kerala")
                .rotation((float) 0.5)
                .icon(bitmapDescriptorFromVector(getApplicationContext(),R.drawable.tea))
                .visible(false));
        if (SphericalUtil.computeDistanceBetween(latLng,tea6.getPosition()) < 200000) {
            tea6.setVisible(true);
        }
        String id273 = tea6.getId();
        markerMap.put(id273,"action_tea");

        Marker tea7 = mMap.addMarker(new MarkerOptions()
                .position(new LatLng(23.599552,91.8179982))
                .title("Tea")
                .snippet("Dhalai,Tripura")
                .rotation((float) 0.5)
                .icon(bitmapDescriptorFromVector(getApplicationContext(),R.drawable.tea))
                .visible(false));
        if (SphericalUtil.computeDistanceBetween(latLng,tea7.getPosition()) < 200000) {
            tea7.setVisible(true);
        }
        String id274 = tea7.getId();
        markerMap.put(id274,"action_tea");

        Marker tea8 = mMap.addMarker(new MarkerOptions()
                .position(new LatLng(24.0631626,91.6092164))
                .title("Tea")
                .snippet("Khowai,Tripura")
                .rotation((float) 0.5)
                .icon(bitmapDescriptorFromVector(getApplicationContext(),R.drawable.tea))
                .visible(false));
        if (SphericalUtil.computeDistanceBetween(latLng,tea8.getPosition()) < 200000) {
            tea8.setVisible(true);
        }
        String id275 = tea8.getId();
        markerMap.put(id275,"action_tea");

        Marker tea9 = mMap.addMarker(new MarkerOptions()
                .position(new LatLng(23.0754303,91.6477198))
                .title("Tea")
                .snippet("South tripura,Tripura")
                .rotation((float) 0.5)
                .icon(bitmapDescriptorFromVector(getApplicationContext(),R.drawable.tea))
                .visible(false));
        if (SphericalUtil.computeDistanceBetween(latLng,tea9.getPosition()) < 200000) {
            tea9.setVisible(true);
        }
        String id276 = tea9.getId();
        markerMap.put(id276,"action_tea");

        Marker tea10 = mMap.addMarker(new MarkerOptions()
                .position(new LatLng(23.7396573,91.1843175))
                .title("Tea")
                .snippet("Sipahijala,Tripura")
                .rotation((float) 0.5)
                .icon(bitmapDescriptorFromVector(getApplicationContext(),R.drawable.tea))
                .visible(false));
        if (SphericalUtil.computeDistanceBetween(latLng,tea10.getPosition()) < 200000) {
            tea10.setVisible(true);
        }
        String id277 = tea10.getId();
        markerMap.put(id277,"action_tea");

        Marker tea11 = mMap.addMarker(new MarkerOptions()
                .position(new LatLng(26.358796,89.421641))
                .title("Tea")
                .snippet("Darjeeling,West Bengal")
                .rotation((float) 0.5)
                .icon(bitmapDescriptorFromVector(getApplicationContext(),R.drawable.tea))
                .visible(false));
        if (SphericalUtil.computeDistanceBetween(latLng,tea11.getPosition()) < 200000) {
            tea11.setVisible(true);
        }
        String id278 = tea11.getId();
        markerMap.put(id278,"action_tea");

        //tobacco

        Marker tob1 = mMap.addMarker(new MarkerOptions()
                .position(new LatLng(17.2664424,80.1176356))
                .title("Tobacco ")
                .snippet("Khammam,Telangana")
                .rotation((float) 0.5)
                .icon(bitmapDescriptorFromVector(getApplicationContext(),R.drawable.tobacco))
                .visible(false));
        if (SphericalUtil.computeDistanceBetween(latLng,tob1.getPosition()) < 200000) {
            tob1.setVisible(true);
        }
        String id279 = tob1.getId();
        markerMap.put(id279,"action_tob");

        Marker tob2 = mMap.addMarker(new MarkerOptions()
                .position(new LatLng(14.4214929,79.986702 ))
                .title("Tobacco")
                .snippet("Nellore,Andhra Pradesh")
                .rotation((float) 0.5)
                .icon(bitmapDescriptorFromVector(getApplicationContext(),R.drawable.tobacco))
                .visible(false));
        if (SphericalUtil.computeDistanceBetween(latLng,tob2.getPosition()) < 200000) {
            tob2.setVisible(true);
        }
        String id280 = tob2.getId();
        markerMap.put(id280,"action_tob");

        Marker tob3 = mMap.addMarker(new MarkerOptions()
                .position(new LatLng(16.3488806,80.4641881 ))
                .title("Tobacco")
                .snippet("Guntur,Andhra Pradesh")
                .rotation((float) 0.5)
                .icon(bitmapDescriptorFromVector(getApplicationContext(),R.drawable.tobacco))
                .visible(false));
        if (SphericalUtil.computeDistanceBetween(latLng,tob3.getPosition()) < 200000) {
            tob3.setVisible(true);
        }
        String id281 = tob3.getId();
        markerMap.put(id281,"action_tob");

        Marker tob4 = mMap.addMarker(new MarkerOptions()
                .position(new LatLng(25.750797,87.4748715 ))
                .title("Tobacco")
                .snippet("Purnia,Bihar")
                .rotation((float) 0.5)
                .icon(bitmapDescriptorFromVector(getApplicationContext(),R.drawable.tobacco))
                .visible(false));
        if (SphericalUtil.computeDistanceBetween(latLng,tob4.getPosition()) < 200000) {
            tob4.setVisible(true);
        }
        String id282 = tob4.getId();
        markerMap.put(id282,"action_tob");

        Marker tob5 = mMap.addMarker(new MarkerOptions()
                .position(new LatLng(25.3709824,87.5746358))
                .title("Tobacco")
                .snippet("Katihar,Bihar")
                .rotation((float) 0.5)
                .icon(bitmapDescriptorFromVector(getApplicationContext(),R.drawable.tobacco))
                .visible(false));
        if (SphericalUtil.computeDistanceBetween(latLng,tob5.getPosition()) < 200000) {
            tob5.setVisible(true);
        }
        String id283 = tob5.getId();
        markerMap.put(id283,"action_tob");

        Marker tob6 = mMap.addMarker(new MarkerOptions()
                .position(new LatLng(22.5274929,72.9661525))
                .title("Tobacco")
                .snippet("Anand,Gujarat")
                .rotation((float) 0.5)
                .icon(bitmapDescriptorFromVector(getApplicationContext(),R.drawable.tobacco))
                .visible(false));
        if (SphericalUtil.computeDistanceBetween(latLng,tob6.getPosition()) < 200000) {
            tob6.setVisible(true);
        }
        String id284 = tob6.getId();
        markerMap.put(id284,"action_tob");

        Marker tob7 = mMap.addMarker(new MarkerOptions()
                .position(new LatLng(16.3949744,74.3870556))
                .title("Tobacco")
                .snippet("Nipani,Karnataka")
                .rotation((float) 0.5)
                .icon(bitmapDescriptorFromVector(getApplicationContext(),R.drawable.tobacco))
                .visible(false));
        if (SphericalUtil.computeDistanceBetween(latLng,tob7.getPosition()) < 200000) {
            tob7.setVisible(true);
        }
        String id285 = tob7.getId();
        markerMap.put(id285,"action_tob");

        Marker tob8 = mMap.addMarker(new MarkerOptions()
                .position(new LatLng(11.0535813,76.9539661))
                .title("Tobacco")
                .snippet("Coimbatore,Tamil Nadu")
                .rotation((float) 0.5)
                .icon(bitmapDescriptorFromVector(getApplicationContext(),R.drawable.tobacco))
                .visible(false));
        if (SphericalUtil.computeDistanceBetween(latLng,tob8.getPosition()) < 200000) {
            tob8.setVisible(true);
        }
        String id286 = tob8.getId();
        markerMap.put(id286,"action_tob");

        Marker tob9 = mMap.addMarker(new MarkerOptions()
                .position(new LatLng(17.6900288,81.9570628))
                .title("Tobacco")
                .snippet("East Godavari,Andhra Pradesh")
                .rotation((float) 0.5)
                .icon(bitmapDescriptorFromVector(getApplicationContext(),R.drawable.tobacco))
                .visible(false));
        if (SphericalUtil.computeDistanceBetween(latLng,tob9.getPosition()) < 200000) {
            tob9.setVisible(true);
        }
        String id287 = tob9.getId();
        markerMap.put(id287,"action_tob");

        Marker tob10 = mMap.addMarker(new MarkerOptions()
                .position(new LatLng(16.5368008,81.6705261))
                .title("Tobacco")
                .snippet("West Godavari,Andhra Pradesh")
                .rotation((float) 0.5)
                .icon(bitmapDescriptorFromVector(getApplicationContext(),R.drawable.tobacco))
                .visible(false));
        if (SphericalUtil.computeDistanceBetween(latLng,tob10.getPosition()) < 200000) {
            tob10.setVisible(true);
        }
        String id288 = tob10.getId();
        markerMap.put(id288,"action_tob");

        Marker tob11 = mMap.addMarker(new MarkerOptions()
                .position(new LatLng(12.0328785,75.3751598))
                .title("Tobacco")
                .snippet("Kannur,Kerala")
                .rotation((float) 0.5)
                .icon(bitmapDescriptorFromVector(getApplicationContext(),R.drawable.tobacco))
                .visible(false));
        if (SphericalUtil.computeDistanceBetween(latLng,tob11.getPosition()) < 200000) {
            tob11.setVisible(true);
        }
        String id289 = tob11.getId();
        markerMap.put(id289,"action_tob");

        Marker tob12 = mMap.addMarker(new MarkerOptions()
                .position(new LatLng(31.556300,75.913226))
                .title("Tobacco")
                .snippet("Hushiarpur,Punjab")
                .rotation((float) 0.5)
                .icon(bitmapDescriptorFromVector(getApplicationContext(),R.drawable.tobacco))
                .visible(false));
        if (SphericalUtil.computeDistanceBetween(latLng,tob12.getPosition()) < 200000) {
            tob12.setVisible(true);
        }
        String id290 = tob12.getId();
        markerMap.put(id290,"action_tob");

        Marker tob13 = mMap.addMarker(new MarkerOptions()
                .position(new LatLng(23.5482456,91.4876243))
                .title("Tobacco")
                .snippet("Gomati,Tripura")
                .rotation((float) 0.5)
                .icon(bitmapDescriptorFromVector(getApplicationContext(),R.drawable.tobacco))
                .visible(false));
        if (SphericalUtil.computeDistanceBetween(latLng,tob13.getPosition()) < 200000) {
            tob13.setVisible(true);
        }
        String id291 = tob13.getId();
        markerMap.put(id291,"action_tob");

        Marker tob14 = mMap.addMarker(new MarkerOptions()
                .position(new LatLng(25.4746818,78.5318343))
                .title("Tobacco")
                .snippet("Jhansi,Uttar Pradesh")
                .rotation((float) 0.5)
                .icon(bitmapDescriptorFromVector(getApplicationContext(),R.drawable.tobacco))
                .visible(false));
        if (SphericalUtil.computeDistanceBetween(latLng,tob14.getPosition()) < 200000) {
            tob14.setVisible(true);
        }
        String id292 = tob14.getId();
        markerMap.put(id292,"action_tob");

        Marker tob15 = mMap.addMarker(new MarkerOptions()
                .position(new LatLng(24.701304,78.429723))
                .title("Tobacco")
                .snippet("Lalitpur,Uttar Pradesh")
                .rotation((float) 0.5)
                .icon(bitmapDescriptorFromVector(getApplicationContext(),R.drawable.tobacco))
                .visible(false));
        if (SphericalUtil.computeDistanceBetween(latLng,tob15.getPosition()) < 200000) {
            tob15.setVisible(true);
        }
        String id293 = tob15.getId();
        markerMap.put(id293,"action_tob");

        Marker tob16 = mMap.addMarker(new MarkerOptions()
                .position(new LatLng(25.3439832,80.3370608))
                .title("Tobacco")
                .snippet("Manipur,Uttar Pradesh")
                .rotation((float) 0.5)
                .icon(bitmapDescriptorFromVector(getApplicationContext(),R.drawable.tobacco))
                .visible(false));
        if (SphericalUtil.computeDistanceBetween(latLng,tob16.getPosition()) < 200000) {
            tob16.setVisible(true);
        }
        String id294 = tob16.getId();
        markerMap.put(id294,"action_tob");

        Marker tob17 = mMap.addMarker(new MarkerOptions()
                .position(new LatLng(23.969256,78.756131))
                .title("Tobacco")
                .snippet("Sagar,Madhya Pradesh")
                .rotation((float) 0.5)
                .icon(bitmapDescriptorFromVector(getApplicationContext(),R.drawable.tobacco))
                .visible(false));
        if (SphericalUtil.computeDistanceBetween(latLng,tob17.getPosition()) < 200000) {
            tob17.setVisible(true);
        }
        String id295 = tob17.getId();
        markerMap.put(id295,"action_tob");

        Marker tob18 = mMap.addMarker(new MarkerOptions()
                .position(new LatLng(24.651054,80.671495))
                .title("Tobacco")
                .snippet("Satna,Madhya Pradesh")
                .rotation((float) 0.5)
                .icon(bitmapDescriptorFromVector(getApplicationContext(),R.drawable.tobacco))
                .visible(false));
        if (SphericalUtil.computeDistanceBetween(latLng,tob18.getPosition()) < 200000) {
            tob18.setVisible(true);
        }
        String id296 = tob18.getId();
        markerMap.put(id296,"action_tob");

        //turmeric
        Marker tur1=mMap.addMarker(new MarkerOptions()
                .position(new LatLng(13.768064,78.155620))
                .title("Turmeric")
                .snippet("Chittoor,Andhra Pradesh")
                .rotation((float) 0.5)
                .icon(bitmapDescriptorFromVector(getApplicationContext(),R.drawable.turmeric))
                .visible(false));
        if (SphericalUtil.computeDistanceBetween(latLng,tur1.getPosition()) < 200000) {
            tur1.setVisible(true);
        }
        String id297 = tur1.getId();
        markerMap.put(id297,"action_turmeric");

        Marker tur2=mMap.addMarker(new MarkerOptions()
                .position(new LatLng(20.931400,83.658740))
                .title("Turmeric")
                .snippet("Bargarh,Odisha")
                .rotation((float) 0.5)
                .icon(bitmapDescriptorFromVector(getApplicationContext(),R.drawable.turmeric))
                .visible(false));
        if (SphericalUtil.computeDistanceBetween(latLng,tur2.getPosition()) < 200000) {
            tur2.setVisible(true);
        }
        String id298 = tur2.getId();
        markerMap.put(id298,"action_turmeric");

        Marker tur3=mMap.addMarker(new MarkerOptions()
                .position(new LatLng(14.0572115,79.8056534))
                .title("Turmeric")
                .snippet("Nellore,Andhra Pradesh")
                .rotation((float) 0.5)
                .icon(bitmapDescriptorFromVector(getApplicationContext(),R.drawable.turmeric))
                .visible(false));
        if (SphericalUtil.computeDistanceBetween(latLng,tur3.getPosition()) < 200000) {
            tur3.setVisible(true);
        }
        String id299 = tur3.getId();
        markerMap.put(id299,"action_turmeric");

        Marker tur4=mMap.addMarker(new MarkerOptions()
                .position(new LatLng(11.343701, 77.677660))
                .title("Turmeric")
                .snippet("Erode,Tamil Nadu")
                .rotation((float) 0.5)
                .icon(bitmapDescriptorFromVector(getApplicationContext(),R.drawable.turmeric))
                .visible(false));
        if (SphericalUtil.computeDistanceBetween(latLng,tur4.getPosition()) < 200000) {
            tur4.setVisible(true);
        }
        String id300 = tur4.getId();
        markerMap.put(id300,"action_turmeric");

        Marker tur5=mMap.addMarker(new MarkerOptions()
                .position(new LatLng(20.068709, 85.806950))
                .title("Turmeric")
                .snippet("Jajpur,Odisha")
                .rotation((float) 0.5)
                .icon(bitmapDescriptorFromVector(getApplicationContext(),R.drawable.turmeric))
                .visible(false));
        if (SphericalUtil.computeDistanceBetween(latLng,tur5.getPosition()) < 200000) {
            tur5.setVisible(true);
        }
        String id301 = tur5.getId();
        markerMap.put(id301,"action_turmeric");

        Marker tur6=mMap.addMarker(new MarkerOptions()
                .position(new LatLng(20.966207, 84.853171))
                .title("Turmeric")
                .snippet("Naupada,Odisha")
                .rotation((float) 0.5)
                .icon(bitmapDescriptorFromVector(getApplicationContext(),R.drawable.turmeric))
                .visible(false));
        if (SphericalUtil.computeDistanceBetween(latLng,tur6.getPosition()) < 200000) {
            tur6.setVisible(true);
        }
        String id302 = tur6.getId();
        markerMap.put(id302,"action_turmeric");

        Marker tur7=mMap.addMarker(new MarkerOptions()
                .position(new LatLng(16.775973,77.1394177))
                .title("Turmeric")
                .snippet("Yadgir,Karnataka")
                .rotation((float) 0.5)
                .icon(bitmapDescriptorFromVector(getApplicationContext(),R.drawable.turmeric))
                .visible(false));
        if (SphericalUtil.computeDistanceBetween(latLng,tur7.getPosition()) < 200000) {
            tur7.setVisible(true);
        }
        String id303 = tur7.getId();
        markerMap.put(id303,"action_turmeric");

        Marker tur8=mMap.addMarker(new MarkerOptions()
                .position(new LatLng(24.360351, 87.841698))
                .title("Turmeric")
                .snippet("Birbhum,West Bengal")
                .rotation((float) 0.5)
                .icon(bitmapDescriptorFromVector(getApplicationContext(),R.drawable.turmeric))
                .visible(false));
        if (SphericalUtil.computeDistanceBetween(latLng,tur8.getPosition()) < 200000) {
            tur8.setVisible(true);
        }
        String id304 = tur8.getId();
        markerMap.put(id304,"action_turmeric");

        Marker tur9=mMap.addMarker(new MarkerOptions()
                .position(new LatLng(23.220243, 73.777021))
                .title("Turmeric")
                .snippet("Mahisagar,Gujarat")
                .rotation((float) 0.5)
                .icon(bitmapDescriptorFromVector(getApplicationContext(),R.drawable.turmeric))
                .visible(false));
        if (SphericalUtil.computeDistanceBetween(latLng,tur9.getPosition()) < 200000) {
            tur9.setVisible(true);
        }
        String id305 = tur9.getId();
        markerMap.put(id305,"action_turmeric");

        Marker tur10=mMap.addMarker(new MarkerOptions()
                .position(new LatLng(22.749544, 72.681173))
                .title("Turmeric")
                .snippet("Kheda,Gujarat")
                .rotation((float) 0.5)
                .icon(bitmapDescriptorFromVector(getApplicationContext(),R.drawable.turmeric))
                .visible(false));
        if (SphericalUtil.computeDistanceBetween(latLng,tur10.getPosition()) < 200000) {
            tur10.setVisible(true);
        }
        String id306 = tur10.getId();
        markerMap.put(id306,"action_turmeric");

        Marker tur11=mMap.addMarker(new MarkerOptions()
                .position(new LatLng(25.421914, 91.294206))
                .title("Turmeric")
                .snippet("South West Khasi Hills,Meghalaya")
                .rotation((float) 0.5)
                .icon(bitmapDescriptorFromVector(getApplicationContext(),R.drawable.turmeric))
                .visible(false));
        if (SphericalUtil.computeDistanceBetween(latLng,tur11.getPosition()) < 200000) {
            tur11.setVisible(true);
        }
        String id307 = tur11.getId();
        markerMap.put(id307,"action_turmeric");

        Marker tur12=mMap.addMarker(new MarkerOptions()
                .position(new LatLng(21.421914, 80.294206))
                .title("Turmeric")
                .snippet("Gondia,Maharashtra")
                .rotation((float) 0.5)
                .icon(bitmapDescriptorFromVector(getApplicationContext(),R.drawable.turmeric))
                .visible(false));
        if (SphericalUtil.computeDistanceBetween(latLng,tur12.getPosition()) < 200000) {
            tur12.setVisible(true);
        }
        String id308 = tur12.getId();
        markerMap.put(id308,"action_turmeric");

        Marker tur13=mMap.addMarker(new MarkerOptions()
                .position(new LatLng(26.0201502,89.9645397))
                .title("Turmeric")
                .snippet("Dhubri,Assam")
                .rotation((float) 0.5)
                .icon(bitmapDescriptorFromVector(getApplicationContext(),R.drawable.turmeric))
                .visible(false));
        if (SphericalUtil.computeDistanceBetween(latLng,tur13.getPosition()) < 200000) {
            tur13.setVisible(true);
        }
        String id309 = tur13.getId();
        markerMap.put(id309,"action_turmeric");

        Marker tur14=mMap.addMarker(new MarkerOptions()
                .position(new LatLng(26.3947561,90.2588234))
                .title("Turmeric")
                .snippet("Kokrajhar,Assam")
                .rotation((float) 0.5)
                .icon(bitmapDescriptorFromVector(getApplicationContext(),R.drawable.turmeric))
                .visible(false));
        if (SphericalUtil.computeDistanceBetween(latLng,tur14.getPosition()) < 200000) {
            tur14.setVisible(true);
        }
        String id310 = tur14.getId();
        markerMap.put(id310,"action_turmeric");

        //Wheat
        Marker wheat1=mMap.addMarker(new MarkerOptions()
                .position(new LatLng(29.512206,75.061473))
                .title("Wheat")
                .snippet("Sirsa,Haryana")
                .rotation((float) 0.5)
                .icon(bitmapDescriptorFromVector(getApplicationContext(),R.drawable.wheat))
                .visible(false));
        if (SphericalUtil.computeDistanceBetween(latLng,wheat1.getPosition()) < 200000) {
            wheat1.setVisible(true);
        }
        String id311 = wheat1.getId();
        markerMap.put(id311,"action_wheat");

        Marker wheat2=mMap.addMarker(new MarkerOptions()
                .position(new LatLng( 22.179727,76.139752))
                .title("Wheat")
                .snippet("Khargone,Madhya pradesh")
                .rotation((float) 0.5)
                .icon(bitmapDescriptorFromVector(getApplicationContext(),R.drawable.wheat))
                .visible(false));
        if (SphericalUtil.computeDistanceBetween(latLng,wheat2.getPosition()) < 200000) {
            wheat2.setVisible(true);
        }
        String id312 = wheat2.getId();
        markerMap.put(id312,"action_wheat");

        Marker wheat3 = mMap.addMarker(new MarkerOptions()
                .position(new LatLng( 20.057331,73.8136037))
                .title("Wheat")
                .snippet("Nashik,Maharashtra")
                .rotation((float) 0.5)
                .icon(bitmapDescriptorFromVector(getApplicationContext(),R.drawable.wheat))
                .visible(false));
        if (SphericalUtil.computeDistanceBetween(latLng,wheat3.getPosition()) < 200000) {
            wheat3.setVisible(true);
        }
        String id313 = wheat3.getId();
        markerMap.put(id313,"action_wheat");

        Marker wheat4 = mMap.addMarker(new MarkerOptions()
                .position(new LatLng( 21.2009694,71.3720026))
                .title("Wheat")
                .snippet("Amreli,Gujarat")
                .rotation((float) 0.5)
                .icon(bitmapDescriptorFromVector(getApplicationContext(),R.drawable.wheat))
                .visible(false));
        if (SphericalUtil.computeDistanceBetween(latLng,wheat4.getPosition()) < 200000) {
            wheat4.setVisible(true);
        }
        String id314 = wheat4.getId();
        markerMap.put(id314,"action_wheat");

        Marker wheat5 = mMap.addMarker(new MarkerOptions()
                .position(new LatLng( 17.1141952,81.1397531))
                .title("Wheat")
                .snippet("West Godavari,Andhra Pradesh")
                .rotation((float) 0.5)
                .icon(bitmapDescriptorFromVector(getApplicationContext(),R.drawable.wheat))
                .visible(false));
        if (SphericalUtil.computeDistanceBetween(latLng,wheat5.getPosition()) < 200000) {
            wheat5.setVisible(true);
        }
        String id315 = wheat5.getId();
        markerMap.put(id315,"action_wheat");

        Marker wheat6 = mMap.addMarker(new MarkerOptions()
                .position(new LatLng( 26.5157526,93.9849306))
                .title("Wheat")
                .snippet("Golaghat,Assam")
                .rotation((float) 0.5)
                .icon(bitmapDescriptorFromVector(getApplicationContext(),R.drawable.wheat))
                .visible(false));
        if (SphericalUtil.computeDistanceBetween(latLng,wheat6.getPosition()) < 200000) {
            wheat6.setVisible(true);
        }
        String id316 = wheat6.getId();
        markerMap.put(id316,"action_wheat");

        Marker wheat7 = mMap.addMarker(new MarkerOptions()
                .position(new LatLng( 26.310496,90.9832458))
                .title("Wheat")
                .snippet("Barpeta,Assam")
                .rotation((float) 0.5)
                .icon(bitmapDescriptorFromVector(getApplicationContext(),R.drawable.wheat))
                .visible(false));
        if (SphericalUtil.computeDistanceBetween(latLng,wheat7.getPosition()) < 200000) {
            wheat7.setVisible(true);
        }
        String id317 = wheat7.getId();
        markerMap.put(id317,"action_wheat");

        Marker wheat8 = mMap.addMarker(new MarkerOptions()
                .position(new LatLng( 25.5645538,83.9732883))
                .title("Wheat")
                .snippet("Buxar,Bihar")
                .rotation((float) 0.5)
                .icon(bitmapDescriptorFromVector(getApplicationContext(),R.drawable.wheat))
                .visible(false));
        if (SphericalUtil.computeDistanceBetween(latLng,wheat8.getPosition()) < 200000) {
            wheat8.setVisible(true);
        }
        String id318 = wheat8.getId();
        markerMap.put(id318,"action_wheat");

        Marker wheat9 = mMap.addMarker(new MarkerOptions()
                .position(new LatLng( 25.5113709,84.4733858))
                .title("Wheat")
                .snippet("Bhojpur,Bihar")
                .rotation((float) 0.5)
                .icon(bitmapDescriptorFromVector(getApplicationContext(),R.drawable.wheat))
                .visible(false));
        if (SphericalUtil.computeDistanceBetween(latLng,wheat9.getPosition()) < 200000) {
            wheat9.setVisible(true);
        }
        String id319 = wheat9.getId();
        markerMap.put(id319,"action_wheat");

        Marker wheat10 = mMap.addMarker(new MarkerOptions()
                .position(new LatLng( 30.6742095,77.704602))
                .title("Wheat")
                .snippet("Sirmaur,Himachal Pradesh")
                .rotation((float) 0.5)
                .icon(bitmapDescriptorFromVector(getApplicationContext(),R.drawable.wheat))
                .visible(false));
        if (SphericalUtil.computeDistanceBetween(latLng,wheat10.getPosition()) < 200000) {
            wheat10.setVisible(true);
        }
        String id320 = wheat10.getId();
        markerMap.put(id320,"action_wheat");

        Marker wheat11 = mMap.addMarker(new MarkerOptions()
                .position(new LatLng( 32.9158079,75.1396585))
                .title("Wheat")
                .snippet("Udhampur,Jammu Kashmir")
                .rotation((float) 0.5)
                .icon(bitmapDescriptorFromVector(getApplicationContext(),R.drawable.wheat))
                .visible(false));
        if (SphericalUtil.computeDistanceBetween(latLng,wheat11.getPosition()) < 200000) {
            wheat11.setVisible(true);
        }
        String id321 = wheat11.getId();
        markerMap.put(id321,"action_wheat");

        Marker wheat12 = mMap.addMarker(new MarkerOptions()
                .position(new LatLng( 32.7681981,74.7813627))
                .title("Wheat")
                .snippet("Jammu,Jammu Kashmir")
                .rotation((float) 0.5)
                .icon(bitmapDescriptorFromVector(getApplicationContext(),R.drawable.wheat))
                .visible(false));
        if (SphericalUtil.computeDistanceBetween(latLng,wheat12.getPosition()) < 200000) {
            wheat12.setVisible(true);
        }
        String id322 = wheat12.getId();
        markerMap.put(id322,"action_wheat");

        Marker wheat13 = mMap.addMarker(new MarkerOptions()
                .position(new LatLng( 15.5031961,74.9837177))
                .title("Wheat")
                .snippet("Dharwad,Karnataka")
                .rotation((float) 0.5)
                .icon(bitmapDescriptorFromVector(getApplicationContext(),R.drawable.wheat))
                .visible(false));
        if (SphericalUtil.computeDistanceBetween(latLng,wheat13.getPosition()) < 200000) {
            wheat13.setVisible(true);
        }
        String id323 = wheat13.getId();
        markerMap.put(id323,"action_wheat");

        Marker wheat14 = mMap.addMarker(new MarkerOptions()
                .position(new LatLng( 21.932114,85.967359))
                .title("Wheat")
                .snippet("Mayurbhanj,Odisha")
                .rotation((float) 0.5)
                .icon(bitmapDescriptorFromVector(getApplicationContext(),R.drawable.wheat))
                .visible(false));
        if (SphericalUtil.computeDistanceBetween(latLng,wheat14.getPosition()) < 200000) {
            wheat14.setVisible(true);
        }
        String id324 = wheat14.getId();
        markerMap.put(id324,"action_wheat");

        Marker wheat15 = mMap.addMarker(new MarkerOptions()
                .position(new LatLng( 22.131704,84.041835))
                .title("Wheat")
                .snippet("Sundargarh,Odisha")
                .rotation((float) 0.5)
                .icon(bitmapDescriptorFromVector(getApplicationContext(),R.drawable.wheat))
                .visible(false));
        if (SphericalUtil.computeDistanceBetween(latLng,wheat15.getPosition()) < 200000) {
            wheat15.setVisible(true);
        }
        String id325 = wheat15.getId();
        markerMap.put(id325,"action_wheat");

        Marker wheat16 = mMap.addMarker(new MarkerOptions()
                .position(new LatLng( 21.529911,84.728439))
                .title("Wheat")
                .snippet("Debagarh,Odisha")
                .rotation((float) 0.5)
                .icon(bitmapDescriptorFromVector(getApplicationContext(),R.drawable.wheat))
                .visible(false));
        if (SphericalUtil.computeDistanceBetween(latLng,wheat16.getPosition()) < 200000) {
            wheat16.setVisible(true);
        }
        String id326 = wheat16.getId();
        markerMap.put(id326,"action_wheat");

        Marker wheat17 = mMap.addMarker(new MarkerOptions()
                .position(new LatLng(18.808716,84.206042))
                .title("Wheat")
                .snippet("Gajapati,Odisha")
                .rotation((float) 0.5)
                .icon(bitmapDescriptorFromVector(getApplicationContext(),R.drawable.wheat))
                .visible(false));
        if (SphericalUtil.computeDistanceBetween(latLng,wheat17.getPosition()) < 200000) {
            wheat17.setVisible(true);
        }
        String id327 = wheat17.getId();
        markerMap.put(id327,"action_wheat");

        Marker wheat18 = mMap.addMarker(new MarkerOptions()
                .position(new LatLng(18.808716,84.206042))
                .title("Wheat")
                .snippet("Gajapati,Odisha")
                .rotation((float) 0.5)
                .icon(bitmapDescriptorFromVector(getApplicationContext(),R.drawable.wheat))
                .visible(false));
        if (SphericalUtil.computeDistanceBetween(latLng,wheat18.getPosition()) < 200000) {
            wheat18.setVisible(true);
        }
        String id328 = wheat18.getId();
        markerMap.put(id328,"action_wheat");

        Marker wheat19 = mMap.addMarker(new MarkerOptions()
                .position(new LatLng(25.306894,74.639822))
                .title("Wheat")
                .snippet("Bhilwara,Rajasthan")
                .rotation((float) 0.5)
                .icon(bitmapDescriptorFromVector(getApplicationContext(),R.drawable.wheat))
                .visible(false));
        if (SphericalUtil.computeDistanceBetween(latLng,wheat19.getPosition()) < 200000) {
            wheat19.setVisible(true);
        }
        String id329 = wheat19.getId();
        markerMap.put(id329,"action_wheat");

        Marker wheat20 = mMap.addMarker(new MarkerOptions()
                .position(new LatLng(27.538524,76.583693))
                .title("Wheat")
                .snippet("Alwar,Rajasthan")
                .rotation((float) 0.5)
                .icon(bitmapDescriptorFromVector(getApplicationContext(),R.drawable.wheat))
                .visible(false));
        if (SphericalUtil.computeDistanceBetween(latLng,wheat20.getPosition()) < 200000) {
            wheat20.setVisible(true);
        }
        String id330 = wheat20.getId();
        markerMap.put(id330,"action_wheat");

        Marker wheat21 = mMap.addMarker(new MarkerOptions()
                .position(new LatLng(29.902579,73.853205))
                .title("Wheat")
                .snippet("Ganganagar,Rajasthan")
                .rotation((float) 0.5)
                .icon(bitmapDescriptorFromVector(getApplicationContext(),R.drawable.wheat))
                .visible(false));
        if (SphericalUtil.computeDistanceBetween(latLng,wheat21.getPosition()) < 200000) {
            wheat21.setVisible(true);
        }
        String id331 = wheat21.getId();
        markerMap.put(id331,"action_wheat");

        Marker wheat22 = mMap.addMarker(new MarkerOptions()
                .position(new LatLng(29.594180,74.335214))
                .title("Wheat")
                .snippet("Hanumangarh,Rajasthan")
                .rotation((float) 0.5)
                .icon(bitmapDescriptorFromVector(getApplicationContext(),R.drawable.wheat))
                .visible(false));
        if (SphericalUtil.computeDistanceBetween(latLng,wheat22.getPosition()) < 200000) {
            wheat22.setVisible(true);
        }
        String id332 = wheat22.getId();
        markerMap.put(id332,"action_wheat");

        Marker wheat23 = mMap.addMarker(new MarkerOptions()
                .position(new LatLng(29.850118,78.1852029))
                .title("Wheat")
                .snippet("Pauri Garhwal,Uttrakhand")
                .rotation((float) 0.5)
                .icon(bitmapDescriptorFromVector(getApplicationContext(),R.drawable.wheat))
                .visible(false));
        if (SphericalUtil.computeDistanceBetween(latLng,wheat23.getPosition()) < 200000) {
            wheat23.setVisible(true);
        }
        String id333 = wheat23.getId();
        markerMap.put(id333,"action_wheat");

        Marker wheat24 = mMap.addMarker(new MarkerOptions()
                .position(new LatLng(24.2020521,88.2733951))
                .title("Wheat")
                .snippet("Murshidabad,West Bengal")
                .rotation((float) 0.5)
                .icon(bitmapDescriptorFromVector(getApplicationContext(),R.drawable.wheat))
                .visible(false));
        if (SphericalUtil.computeDistanceBetween(latLng,wheat24.getPosition()) < 200000) {
            wheat24.setVisible(true);
        }
        String id334 = wheat24.getId();
        markerMap.put(id334,"action_wheat");

        Marker wheat25 = mMap.addMarker(new MarkerOptions()
                .position(new LatLng(22.710770,88.759543))
                .title("Wheat")
                .snippet("Nadia,West Bengal")
                .rotation((float) 0.5)
                .icon(bitmapDescriptorFromVector(getApplicationContext(),R.drawable.wheat))
                .visible(false));
        if (SphericalUtil.computeDistanceBetween(latLng,wheat25.getPosition()) < 200000) {
            wheat25.setVisible(true);
        }
        String id335 = wheat25.getId();
        markerMap.put(id335,"action_wheat");

        Marker wheat26 = mMap.addMarker(new MarkerOptions()
                .position(new LatLng(26.746461,83.885325))
                .title("Wheat")
                .snippet("Kushinagar,Uttar Pradesh")
                .rotation((float) 0.5)
                .icon(bitmapDescriptorFromVector(getApplicationContext(),R.drawable.wheat))
                .visible(false));
        if (SphericalUtil.computeDistanceBetween(latLng,wheat26.getPosition()) < 200000) {
            wheat26.setVisible(true);
        }
        String id336 = wheat26.getId();
        markerMap.put(id336,"action_wheat");

        Marker wheat27 = mMap.addMarker(new MarkerOptions()
                .position(new LatLng(27.861592,79.943965))
                .title("Wheat")
                .snippet("Shahjahanpur,Uttar Pradesh")
                .rotation((float) 0.5)
                .icon(bitmapDescriptorFromVector(getApplicationContext(),R.drawable.wheat))
                .visible(false));
        if (SphericalUtil.computeDistanceBetween(latLng,wheat27.getPosition()) < 200000) {
            wheat27.setVisible(true);
        }
        String id337 = wheat27.getId();
        markerMap.put(id337,"action_wheat");

        Marker wheat28 = mMap.addMarker(new MarkerOptions()
                .position(new LatLng(28.413389,79.396606))
                .title("Wheat")
                .snippet("Bareilly,Uttar Pradesh")
                .rotation((float) 0.5)
                .icon(bitmapDescriptorFromVector(getApplicationContext(),R.drawable.wheat))
                .visible(false));
        if (SphericalUtil.computeDistanceBetween(latLng,wheat28.getPosition()) < 200000) {
            wheat28.setVisible(true);
        }
        String id338 = wheat28.getId();
        markerMap.put(id338,"action_wheat");

        mMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
            @Override
            public void onInfoWindowClick(Marker marker) {
                String actionId = markerMap.get(marker.getId());

                switch (actionId) {
                    case "action_bitter": {
                        androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(MapsActivity.this);
                        LayoutInflater inflater = getLayoutInflater();
                        View dialogLayout = inflater.inflate(R.layout.alertbox_with_image, null);
                        TextView tv = dialogLayout.findViewById(R.id.tv_01);
                        ImageView iv = dialogLayout.findViewById(R.id.image);
                        iv.setImageResource(R.drawable.bittergourd_farm);
                        tv.setText("Bitter gourd (Momordica charantia) is an important vegetable crop and is grown for its immature tuberculate fruits which have a unique bitter taste.");
                        builder.setPositiveButton("Okay , Got it", null);
                        builder.setView(dialogLayout);
                        builder.show();
                        break;
                    }
                    case "action_sugar": {
                        androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(MapsActivity.this);
                        LayoutInflater inflater = getLayoutInflater();
                        View dialogLayout = inflater.inflate(R.layout.alertbox_with_image, null);
                        TextView tv = dialogLayout.findViewById(R.id.tv_01);
                        ImageView iv = dialogLayout.findViewById(R.id.image);
                        iv.setImageResource(R.drawable.sugarcane_farm);
                        tv.setText("Sugarcane is grown as a Kharif Crop. It needs hot and humid climate with an average temperature of 21°C to 27°C.Sugarcane can grow in any soil which can retain moisture.");
                        builder.setPositiveButton("Okay , Got it", null);
                        builder.setView(dialogLayout);
                        builder.show();
                        break;
                    }
                    case "action_sesame": {
                        androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(MapsActivity.this);
                        LayoutInflater inflater = getLayoutInflater();
                        View dialogLayout = inflater.inflate(R.layout.alertbox_with_image, null);
                        TextView tv = dialogLayout.findViewById(R.id.tv_01);
                        ImageView iv = dialogLayout.findViewById(R.id.image);
                        iv.setImageResource(R.drawable.sesame_farm);
                        tv.setText("Sesame is one of the oldest oilseed crops and popularly known as Til or Gingelly. Worldwide, it is used for its nutritional, medicinal, and industrial purposes.In India, the sesame crop can be cultivated as Kharif, summer, and also as semi rabi crop.");
                        builder.setPositiveButton("Okay , Got it", null);
                        builder.setView(dialogLayout);
                        builder.show();
                        break;
                    }
                    case "action_sor": {
                        androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(MapsActivity.this);
                        LayoutInflater inflater = getLayoutInflater();
                        View dialogLayout = inflater.inflate(R.layout.alertbox_with_image, null);
                        TextView tv = dialogLayout.findViewById(R.id.tv_01);
                        ImageView iv = dialogLayout.findViewById(R.id.image);
                        iv.setImageResource(R.drawable.sorghum_farm);
                        tv.setText("Sorghum (Sorghum vulgare Pers.), popularly known as jowar, is one of the most important cereal crop in the world. It is a staple crop for human and other animals for food, fodder, fiber, and fuel. The annual area under it ranges between 17 and 18 million hectares and the annual production between 8 and 10 million tonnes.");
                        builder.setPositiveButton("Okay , Got it", null);
                        builder.setView(dialogLayout);
                        builder.show();
                        break;
                    }
                    case "action_soy": {
                        androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(MapsActivity.this);
                        LayoutInflater inflater = getLayoutInflater();
                        View dialogLayout = inflater.inflate(R.layout.alertbox_with_image, null);
                        TextView tv = dialogLayout.findViewById(R.id.tv_01);
                        ImageView iv = dialogLayout.findViewById(R.id.image);
                        iv.setImageResource(R.drawable.soyabean_farm);
                        tv.setText("Soybean is one of the most grown and used oilseeds. In India, soybean is predominantly grown as a rainfed crop in all the states of Madhya Pradesh.");
                        builder.setPositiveButton("Okay , Got it", null);
                        builder.setView(dialogLayout);
                        builder.show();
                        break;
                    }
                    case "action_wheat": {
                        androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(MapsActivity.this);
                        LayoutInflater inflater = getLayoutInflater();
                        View dialogLayout = inflater.inflate(R.layout.alertbox_with_image, null);
                        TextView tv = dialogLayout.findViewById(R.id.tv_01);
                        ImageView iv = dialogLayout.findViewById(R.id.image);
                        iv.setImageResource(R.drawable.wheat_farm);
                        tv.setText("Wheat (Triticum spp.) occupies the prime position among the food crops in the world. In India, it is the second important food crop being next to rice and contributes to the total food grain production of the country to the extent of about 25%.");
                        builder.setPositiveButton("Okay , Got it", null);
                        builder.setView(dialogLayout);
                        builder.show();
                        break;
                    }
                    case "action_rice": {
                        androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(MapsActivity.this);
                        LayoutInflater inflater = getLayoutInflater();
                        View dialogLayout = inflater.inflate(R.layout.alertbox_with_image, null);
                        TextView tv = dialogLayout.findViewById(R.id.tv_01);
                        ImageView iv = dialogLayout.findViewById(R.id.image);
                        iv.setImageResource(R.drawable.rice_farm);
                        tv.setText("Rice is grown almost throughout the year in hot and humid regions of eastern and southern parts of India that receive heavy annual rainfall. Rice is a tropical plant that requires sufficient water to grow well. That is why it is fundamentally a kharif crop in India.");
                        builder.setPositiveButton("Okay , Got it", null);
                        builder.setView(dialogLayout);
                        builder.show();
                        break;
                    }
                    case "action_methi": {
                        androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(MapsActivity.this);
                        LayoutInflater inflater = getLayoutInflater();
                        View dialogLayout = inflater.inflate(R.layout.alertbox_with_image, null);
                        TextView tv = dialogLayout.findViewById(R.id.tv_01);
                        ImageView iv = dialogLayout.findViewById(R.id.image);
                        iv.setImageResource(R.drawable.fenugreek_farm);
                        tv.setText("Fenugreek is popularly known as “methi” in India and used as condiments and as a flavoring agent for food preparations.The young pods and leaves are used as vegetable consumed in daily cooking.");
                        builder.setPositiveButton("Okay , Got it", null);
                        builder.setView(dialogLayout);
                        builder.show();
                        break;
                    }
                    case "action_mustard": {
                        androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(MapsActivity.this);
                        LayoutInflater inflater = getLayoutInflater();
                        View dialogLayout = inflater.inflate(R.layout.alertbox_with_image, null);
                        TextView tv = dialogLayout.findViewById(R.id.tv_01);
                        ImageView iv = dialogLayout.findViewById(R.id.image);
                        iv.setImageResource(R.drawable.mustard_farm);
                        tv.setText("Mustard has been a traditionally important oilseed crop in the India. It is a major Rabi crop.");
                        builder.setPositiveButton("Okay , Got it", null);
                        builder.setView(dialogLayout);
                        builder.show();
                        break;
                    }
                    case "action_peas": {
                        androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(MapsActivity.this);
                        LayoutInflater inflater = getLayoutInflater();
                        View dialogLayout = inflater.inflate(R.layout.alertbox_with_image, null);
                        TextView tv = dialogLayout.findViewById(R.id.tv_01);
                        ImageView iv = dialogLayout.findViewById(R.id.image);
                        iv.setImageResource(R.drawable.peas_farm);
                        tv.setText("Green Peas, also popularly known as “garden peas” is one of the vegetable crops in India and basically this crop is cultivated for its green pods. Green peas belong to Leguminaceae family. Green Peas are used in vegetable cooking\\'s, in soups and frozen canned food as well.");
                        builder.setPositiveButton("Okay , Got it", null);
                        builder.setView(dialogLayout);
                        builder.show();
                        break;
                    }
                    case "action_lentil": {
                        androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(MapsActivity.this);
                        LayoutInflater inflater = getLayoutInflater();
                        View dialogLayout = inflater.inflate(R.layout.alertbox_with_image, null);
                        TextView tv = dialogLayout.findViewById(R.id.tv_01);
                        ImageView iv = dialogLayout.findViewById(R.id.image);
                        iv.setImageResource(R.drawable.lentil_farm);
                        tv.setText("The lentil(Lens culinaris or Lens esculenta) is an edible legume. It is an annual plant known for its lens-shaped seeds. It is about 40 cm (16 inch) tall, and the seeds grow in pods, usually with two seeds in each. As a food crop, the majority of world production comes from Canada and India, producing 58% combined of the world total.");
                        builder.setPositiveButton("Okay , Got it", null);
                        builder.setView(dialogLayout);
                        builder.show();
                        break;
                    }
                    case "action_pum": {
                        androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(MapsActivity.this);
                        LayoutInflater inflater = getLayoutInflater();
                        View dialogLayout = inflater.inflate(R.layout.alertbox_with_image, null);
                        TextView tv = dialogLayout.findViewById(R.id.tv_01);
                        ImageView iv = dialogLayout.findViewById(R.id.image);
                        iv.setImageResource(R.drawable.pumpkin_farm);
                        tv.setText("Pumpkin is a popular vegetable rainy season crop in India. The pumpkin belongs to “Cucurbitaceae” family and is grown extensively during Kharif season (monsoon) and summer season throughout India, for immature and tender fruits.");
                        builder.setPositiveButton("Okay , Got it", null);
                        builder.setView(dialogLayout);
                        builder.show();
                        break;
                    }
                    case "action_turmeric": {
                        androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(MapsActivity.this);
                        LayoutInflater inflater = getLayoutInflater();
                        View dialogLayout = inflater.inflate(R.layout.alertbox_with_image, null);
                        TextView tv = dialogLayout.findViewById(R.id.tv_01);
                        ImageView iv = dialogLayout.findViewById(R.id.image);
                        iv.setImageResource(R.drawable.turmeric_farm);
                        tv.setText("Turmeric (Curcuma longa L), the ancient and sacred spice of India known as ‘Indian saffron’ is an important commercial spice crop grown in India.");
                        builder.setPositiveButton("Okay , Got it", null);
                        builder.setView(dialogLayout);
                        builder.show();
                        break;
                    }
                    case "action_cotton": {
                        androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(MapsActivity.this);
                        LayoutInflater inflater = getLayoutInflater();
                        View dialogLayout = inflater.inflate(R.layout.alertbox_with_image, null);
                        TextView tv = dialogLayout.findViewById(R.id.tv_01);
                        ImageView iv = dialogLayout.findViewById(R.id.image);
                        iv.setImageResource(R.drawable.cotton_farm);
                        tv.setText("Cotton is a soft, fluffy staple fiber that grows in a boll, or protective case, around the seeds of the cotton plants.Cotton is an immensely important crop for the sustainable economy of India and livelihood of the Indian cotton farming community. It is cultivated in about 312 lakh hectares across the world and in around 117 lakh hectares in the country.");
                        builder.setPositiveButton("Okay , Got it", null);
                        builder.setView(dialogLayout);
                        builder.show();
                        break;
                    }
                    case "action_chilli": {
                        androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(MapsActivity.this);
                        LayoutInflater inflater = getLayoutInflater();
                        View dialogLayout = inflater.inflate(R.layout.alertbox_with_image, null);
                        TextView tv = dialogLayout.findViewById(R.id.tv_01);
                        ImageView iv = dialogLayout.findViewById(R.id.image);
                        iv.setImageResource(R.drawable.chilli_farm);
                        tv.setText(" Chilli belongs to family Solanaceae. It is a small, yearly shrub with a straight and branched shoot.Chilli has simple leaves and its flowers are white in color. In India, chilli is also called mirchi,lanka etc.");
                        builder.setPositiveButton("Okay , Got it", null);
                        builder.setView(dialogLayout);
                        builder.show();
                        break;
                    }
                    case "action_maize": {
                        androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(MapsActivity.this);
                        LayoutInflater inflater = getLayoutInflater();
                        View dialogLayout = inflater.inflate(R.layout.alertbox_with_image, null);
                        TextView tv = dialogLayout.findViewById(R.id.tv_01);
                        ImageView iv = dialogLayout.findViewById(R.id.image);
                        iv.setImageResource(R.drawable.maize_farm);
                        tv.setText("Maize, also known as corn in the US, Canada and Australia is the most widely produced crop all over the world. Maize have carbohydrate(70%) ,protein(10%) and oil(4%).");
                        builder.setPositiveButton("Okay , Got it", null);
                        builder.setView(dialogLayout);
                        builder.show();
                        break;
                    }
                    case "action_tea": {
                        androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(MapsActivity.this);
                        LayoutInflater inflater = getLayoutInflater();
                        View dialogLayout = inflater.inflate(R.layout.alertbox_with_image, null);
                        TextView tv = dialogLayout.findViewById(R.id.tv_01);
                        ImageView iv = dialogLayout.findViewById(R.id.image);
                        iv.setImageResource(R.drawable.tea_farm);
                        tv.setText("Tea plantation in India has been contributing significantly towards the socio economic development of the people of the tea growing regions of the country.");
                        builder.setPositiveButton("Okay , Got it", null);
                        builder.setView(dialogLayout);
                        builder.show();
                        break;
                    }
                    case "action_tob": {
                        androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(MapsActivity.this);
                        LayoutInflater inflater = getLayoutInflater();
                        View dialogLayout = inflater.inflate(R.layout.alertbox_with_image, null);
                        TextView tv = dialogLayout.findViewById(R.id.tv_01);
                        ImageView iv = dialogLayout.findViewById(R.id.image);
                        iv.setImageResource(R.drawable.tobacco_farm);
                        tv.setText("Today, tobacco is one of the major commercial crops grown in India. Various types of tobaccos are cultivated in India for use in tobacco products such as Cigarette, Bidi, Cigar, Cheroot, Hookah, Chewing and Snuff.");
                        builder.setPositiveButton("Okay , Got it", null);
                        builder.setView(dialogLayout);
                        builder.show();
                        break;
                    }
                    case "action_coffee": {
                        androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(MapsActivity.this);
                        LayoutInflater inflater = getLayoutInflater();
                        View dialogLayout = inflater.inflate(R.layout.alertbox_with_image, null);
                        TextView tv = dialogLayout.findViewById(R.id.tv_01);
                        ImageView iv = dialogLayout.findViewById(R.id.image);
                        iv.setImageResource(R.drawable.coffee_farm);
                        tv.setText("Indian coffee is the most extraordinary of beverages,offering intriguing subtlety and stimulating intensity. India is the only country that grows all of its coffee under shade. Typically mild and not too acidic,these coffees possess an exotic full-bodied taste and a fine aroma.");
                        builder.setPositiveButton("Okay , Got it", null);
                        builder.setView(dialogLayout);
                        builder.show();
                        break;
                    }
                    

                }
                
//                if(actionId.equals("action_bitter")){
//                    AlertDialog.Builder builder = new AlertDialog.Builder(MapsActivity.this);
//                    builder.setTitle("Bitter Gourd").setIcon(R.drawable.bitter);
//                    builder.setMessage("Bitter gourd (Momordica charantia) is an important vegetable crop and is grown for its immature " +
//                            "tuberculate fruits which have a unique bitter taste.");
//                    alertDialog1 = builder.create();
//                    alertDialog1.show();
//                }
//                else if(actionId.equals("action_sugar")){
//                    AlertDialog.Builder builder = new AlertDialog.Builder(MapsActivity.this);
//                    builder.setTitle("Sugarcane").setIcon(R.drawable.sugarcane1);
//                    builder.setMessage("Sugarcane is grown as a Kharif Crop. It needs hot and humid climate with an average temperature of " +
//                            "21°C to 27°C. Sugarcane can grow in any soil which can retain moisture.");
//                    alertDialog1 = builder.create();
//                    alertDialog1.show();
//                }
//                else if(actionId.equals("action_sesame")){
//                    AlertDialog.Builder builder = new AlertDialog.Builder(MapsActivity.this);
//                    builder.setTitle("Sesame").setIcon(R.drawable.sesame);
//                    builder.setMessage("Sesame(Til or Gingelly) is one of the oldest oilseed crops  with oil content of 40 to 50 % . " +
//                            "In India,the sesame crop can be cultivated as Kharif,Summer,and also as Semi-Rabi crop.");
//                    alertDialog1 = builder.create();
//                    alertDialog1.show();
//                }
//                else if(actionId.equals("action_sor")){
//                    AlertDialog.Builder builder = new AlertDialog.Builder(MapsActivity.this);
//                    builder.setTitle("Sorghum").setIcon(R.drawable.sor);
//                    builder.setMessage("Sorghum(Sorghum vulgare Pers.),popularly known as jowar,is the most important food and fodder" +
//                            " crop of dryland agriculture. The annual area under it ranges between 17-18 million hectares and " +
//                            "the annual production between 8-10 million tonnes.");
//                    alertDialog1 = builder.create();
//                    alertDialog1.show();
//                }
//                else if(actionId.equals("action_soy")){
//                    AlertDialog.Builder builder = new AlertDialog.Builder(MapsActivity.this);
//                    builder.setTitle("Soyabean").setIcon(R.drawable.soyabean);
//                    builder.setMessage("Soybean,(Glycine max),also called soja bean or soya bean,annual legume of the pea family (Fabaceae)" +
//                            " and its edible seed.In India,soybean is predominantly grown as a rainfed crop covering the states of Madhya Pradesh.");
//                    alertDialog1 = builder.create();
//                    alertDialog1.show();
//                }
//                else if(actionId.equals("action_wheat")){
//                    AlertDialog.Builder builder = new AlertDialog.Builder(MapsActivity.this);
//                    builder.setTitle("Wheat").setIcon(R.drawable.wheat);
//                    builder.setMessage("Wheat(Triticum spp.) occupies the prime position among the food crops in the world. In India," +
//                            "it is the second important food crop being next to rice and contributes to the 25% of country's total foodgrain production " );
//                    alertDialog1 = builder.create();
//                    alertDialog1.show();
//                }
//                else if(actionId.equals("action_rice")){
//                    AlertDialog.Builder builder = new AlertDialog.Builder(MapsActivity.this);
//                    builder.setTitle("Rice").setIcon(R.drawable.rice);
//                    builder.setMessage("Rice is the basic food crop and being a tropical plant,it flourishes comfortably in hot and" +
//                            " humid climate.It is mainly grown in rain fed areas that receive heavy annual rainfall. " +
//                            "That is why it is fundamentally a kharif crop in India.");
//                    alertDialog1 = builder.create();
//                    alertDialog1.show();
//                }
//                else if(actionId.equals("action_methi")){
//                    AlertDialog.Builder builder = new AlertDialog.Builder(MapsActivity.this);
//                    builder.setTitle("Fenugreek").setIcon(R.drawable.methi);
//                    builder.setMessage("Fenugreek is popularly known as “methi” in India and used as condiments and as a flavoring agent " +
//                            "for food preparations. The young pods and leaves are used as vegetable consumed in daily cooking.");
//                    alertDialog1 = builder.create();
//                    alertDialog1.show();
//                }
//                else if(actionId.equals("action_rai")){
//                    AlertDialog.Builder builder = new AlertDialog.Builder(MapsActivity.this);
//                    builder.setTitle("Mustard").setIcon(R.drawable.mustard);
//                    builder.setMessage("Mustard is the second most important and most prominent winter oilseed crop of India." +
//                            " It is grown mainly in the northern plains of India with some cultivated area in the eastern " +
//                            "geography as well. It is a major Rabi crop.");
//                    alertDialog1 = builder.create();
//                    alertDialog1.show();
//                }
//                else if(actionId.equals("action_peas")){
//                    AlertDialog.Builder builder = new AlertDialog.Builder(MapsActivity.this);
//                    builder.setTitle("Peas").setIcon(R.drawable.peas);
//                    builder.setMessage("Green Peas,also popularly known as “garden peas” is one of the vegetable crops in India and " +
//                            "basically this crop is cultivated for its green pods. Green peas belong to Leguminaceae family. " +
//                            "Green Peas are used in vegetable cooking's,in soups & frozen canned food as well.");
//                    alertDialog1 = builder.create();
//                    alertDialog1.show();
//                }
//                else if(actionId.equals("action_lentil")){
//                    AlertDialog.Builder builder = new AlertDialog.Builder(MapsActivity.this);
//                    builder.setTitle("Lentils").setIcon(R.drawable.lentils);
//                    builder.setMessage("In India,it is predominantly grown in the North,particularly in Uttar Pradesh,Madhya Pradesh," +
//                            "Bihar and West Bengal. In West Bengal the crop covers an area of about 0.7 lakh hectares or about 18% of " +
//                            "the total pulse area in the State.");
//                    alertDialog1 = builder.create();
//                    alertDialog1.show();
//                }
//                else if(actionId.equals("action_pum")){
//                    AlertDialog.Builder builder = new AlertDialog.Builder(MapsActivity.this);
//                    builder.setTitle("Pumpkin").setIcon(R.drawable.pum);
//                    builder.setMessage("Pumpkin is a popular vegetable rainy season crop in India. The pumpkin belongs to “Cucurbitaceae” " +
//                            "family and is grown extensively during Kharif season (monsoon) and summer season throughout India," +
//                            "for immature and tender fruits");
//                    alertDialog1 = builder.create();
//                    alertDialog1.show();
//                }
//                else if(actionId.equals("action_turmeric")){
//                    AlertDialog.Builder builder = new AlertDialog.Builder(MapsActivity.this);
//                    builder.setTitle("Turmeric").setIcon(R.drawable.turmeric);
//                    builder.setMessage("Turmeric (Curcuma longa L),the ancient and sacred spice of India known as ‘Indian saffron’ is an " +
//                            "important commercial spice crop grown in India.");
//                    alertDialog1 = builder.create();
//                    alertDialog1.show();
//                }
//                else if(actionId.equals("action_cotton")){
//                    AlertDialog.Builder builder = new AlertDialog.Builder(MapsActivity.this);
//                    builder.setTitle("Cotton").setIcon(R.drawable.cotton1);
//                    builder.setMessage("Cotton is an immensely important crop for the sustainable economy of India and livelihood of the" +
//                            " Indian cotton farming community. It is cultivated in about 312 lakh hectares across the world and in around " +
//                            "117 lakh hectares in the country.");
//                    alertDialog1 = builder.create();
//                    alertDialog1.show();
//                }
//                else if(actionId.equals("action_chilli")){
//                    AlertDialog.Builder builder = new AlertDialog.Builder(MapsActivity.this);
//                    builder.setTitle("Chilly").setIcon(R.drawable.chilly);
//                    builder.setMessage("Chilli belongs to family Solanaceae. It is a small,yearly shrub with a straight,branched shoot. " +
//                            "It also has a tap root system with very simple leaves. The flowers of chilli are white in color. In India " +
//                            "chilli is also called mirchi,lanka etc.");
//                    alertDialog1 = builder.create();
//                    alertDialog1.show();
//                }
//                else if(actionId.equals("action_maize")){
//                    AlertDialog.Builder builder = new AlertDialog.Builder(MapsActivity.this);
//                    builder.setTitle("Maize").setIcon(R.drawable.maize);
//                    builder.setMessage("Maize,also known as corn in the US,Canada & Australia is the most widely produced crop all over " +
//                            "the world. Maize have carbohydrate(70%) ,protein(10%) and oil(4%)");
//                    alertDialog1 = builder.create();
//                    alertDialog1.show();
//                }
//                else if(actionId.equals("action_tea")){
//                    AlertDialog.Builder builder = new AlertDialog.Builder(MapsActivity.this);
//                    builder.setTitle("Tea").setIcon(R.drawable.tea);
//                    builder.setMessage("Tea plantation in India has been contributing significantly towards the socio economic development" +
//                            " of the people of the tea growing regions of the country.");
//                    alertDialog1 = builder.create();
//                    alertDialog1.show();
//                }
//                else if(actionId.equals("action_tob")){
//                    AlertDialog.Builder builder = new AlertDialog.Builder(MapsActivity.this);
//                    builder.setTitle("Tobacco").setIcon(R.drawable.tobacco);
//                    builder.setMessage("Tobacco is one of the major commercial crops grown in India. Various types of tobaccos are cultivated in " +
//                            "India for use in tobacco products such as Cigarette,Bidi,Cigar,Cheroot,Hookah,Chewing and Snuff etc");
//                    alertDialog1 = builder.create();
//                    alertDialog1.show();
//                }
//                else if(actionId.equals("action_coffee")){
//                    AlertDialog.Builder builder = new AlertDialog.Builder(MapsActivity.this);
//                    builder.setTitle("Coffee").setIcon(R.drawable.coffee);
//                    builder.setMessage("Indian coffee is the most extraordinary of beverages,offering intriguing subtlety and stimulating" +
//                            " intensity. India is the only country that grows all of its coffee under shade. Typically mild and not too" +
//                            " acidic,these coffees possess an exotic full-bodied taste and a fine aroma.");
//                    alertDialog1 = builder.create();
//                    alertDialog1.show();
//                }
//                else if(actionId.equals("action_own")){
//
//                    AlertDialog.Builder builder = new AlertDialog.Builder(MapsActivity.this);
//                    builder.setTitle("Weather and Suggestions").setIcon(R.drawable.ic_help);
//                    builder.setMessage("Do you want to know the weather forecast and get crops suggestions according to your location?");
//
//                    builder.setNegativeButton("NO",new DialogInterface.OnClickListener() {
//                        @Override
//                        public void onClick(DialogInterface dialogInterface,int i) {
//
//                            alertDialog1.cancel();
//                        }
//                    });
//                    builder.setPositiveButton("YES",new DialogInterface.OnClickListener() {
//                        @Override
//                        public void onClick(DialogInterface dialogInterface,int i) {
//
//                            Intent intent = new Intent(getApplicationContext(),MainActivity.class);
//                            startActivity(intent);
//                        }
//                    });
//
//                    alertDialog1 = builder.create();
//                    alertDialog1.show();
//
//                    // Get the alert dialog buttons reference
//                    Button positiveButton = alertDialog1.getButton(AlertDialog.BUTTON_POSITIVE);
//                    Button negativeButton = alertDialog1.getButton(AlertDialog.BUTTON_NEGATIVE);
//
//                    // Change the alert dialog buttons text and background color
//                    positiveButton.setTextColor(Color.parseColor("#FF0B8B42"));
//                    negativeButton.setTextColor(Color.parseColor("#FFFF0400"));
//                }
            }


        });


    }
    @Override
    public void onRequestPermissionsResult(int requestCode,@NonNull String[] permissions,@NonNull int[] grantResults ){
        switch (requestCode){
            case REQUEST_CODE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    fetchLastLocation();
                }
                break;
        }
    }
    private BitmapDescriptor bitmapDescriptorFromVector(Context context, int vectorResId) {
        int height = 80;
        int width = 80;
        Drawable vectorDrawable = ContextCompat.getDrawable(context,vectorResId);
        vectorDrawable.setBounds(0,0,width,height);
        Bitmap bitmap = Bitmap.createBitmap(width,height,Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        vectorDrawable.draw(canvas);
        return BitmapDescriptorFactory.fromBitmap(bitmap);
    }
}

