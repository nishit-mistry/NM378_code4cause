package com.example.phase_1.activities;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
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

import com.example.phase_1.R;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;

import java.util.HashMap;

public class AnalystMap extends FragmentActivity implements OnMapReadyCallback {

    HashMap<String, String> markerMap = new HashMap<String, String>();
    AlertDialog alertDialog1;
    Location currentlocation;
    FusedLocationProviderClient fusedLocationProviderClient;
    private static final int REQUEST_CODE = 101;

    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_analyst_map);
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        fetchLastLocation();
        statusCheck();
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
//
    }

    private void statusCheck() {
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
        if(ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED){
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
                    Toast.makeText(getApplicationContext(), currentlocation.getLatitude()
                            + ""+currentlocation.getLongitude(),Toast.LENGTH_SHORT).show();

                    SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.analyst_map);
                    mapFragment.getMapAsync(AnalystMap.this);
                    final Snackbar snackBar = Snackbar.make(mapFragment.getView(), R.string.snackbar, Snackbar.LENGTH_INDEFINITE);
                    snackBar.setAction(R.string.okay, new View.OnClickListener() {
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
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);

        // Add a marker in India and move the camera
        LatLng latLng = new LatLng(currentlocation.getLatitude(),currentlocation.getLongitude());
        Marker ownLoc = mMap.addMarker(new MarkerOptions()
                .position(latLng)
                .title(getString(R.string.own_loc))
                .alpha(0.7f));
        LatLng India = new LatLng(20.5937, 78.9629);
        mMap.animateCamera(CameraUpdateFactory.newLatLng(India));
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(India, (float) 4.5));

        String id339 = ownLoc.getId();
        markerMap.put(id339,"action_own");

        //bitter gourd
        Marker bitter1= mMap.addMarker(new MarkerOptions()
                .position(new LatLng(22.1358,82.1495))
                .title(getString(R.string.bg_name))
                .snippet(getString(R.string.bitter1))
                .rotation((float) 0.5)
                .icon(bitmapDescriptorFromVector(getApplicationContext(),R.drawable.bitter)));
        String id1 = bitter1.getId();
        markerMap.put(id1,"action_bitter");

        Marker bitter2 = mMap.addMarker(new MarkerOptions()
                .position(new LatLng(14.7013731,79.623258))
                .title(getString(R.string.bg_name))
                .snippet(getString(R.string.bitter2))
                .rotation((float) 0.5)
                .icon(bitmapDescriptorFromVector(getApplicationContext(),R.drawable.bitter)));
        String id2 = bitter2.getId();
        markerMap.put(id2,"action_bitter");

        Marker bitter3 = mMap.addMarker(new MarkerOptions()
                .position(new LatLng(16.6959687,74.2099502))
                .title(getString(R.string.bg_name))
                .snippet(getString(R.string.bitter3))
                .rotation((float) 0.5)
                .icon(bitmapDescriptorFromVector(getApplicationContext(),R.drawable.bitter)));

        String id3 = bitter3.getId();
        markerMap.put(id3,"action_bitter");

        Marker bitter4 = mMap.addMarker(new MarkerOptions()
                .position(new LatLng(21.5143576,70.4342862))
                .title(getString(R.string.bg_name))
                .snippet(getString(R.string.bitter4))
                .rotation((float) 0.5)
                .icon(bitmapDescriptorFromVector(getApplicationContext(),R.drawable.bitter)));

        String id4 = bitter4.getId();
        markerMap.put(id4,"action_bitter");

        Marker bitter5 = mMap.addMarker(new MarkerOptions()
                .position(new LatLng(26.938663, 75.900492))
                .title(getString(R.string.bg_name))
                .snippet(getString(R.string.bitter5))
                .rotation((float) 0.5)
                .icon(bitmapDescriptorFromVector(getApplicationContext(),R.drawable.bitter)));

        String id5 = bitter5.getId();
        markerMap.put(id5,"action_bitter");

        Marker bitter6 = mMap.addMarker(new MarkerOptions()
                .position(new LatLng(31.376087, 75.531180))
                .title(getString(R.string.bg_name))
                .snippet(getString(R.string.bitter6))
                .rotation((float) 0.5)
                .icon(bitmapDescriptorFromVector(getApplicationContext(),R.drawable.bitter)));

        String id6 = bitter6.getId();
        markerMap.put(id6,"action_bitter");

        Marker bitter7 = mMap.addMarker(new MarkerOptions()
                .position(new LatLng(11.758306,79.6703561))
                .title(getString(R.string.bg_name))
                .snippet(getString(R.string.bitter7))
                .rotation((float) 0.5)
                .icon(bitmapDescriptorFromVector(getApplicationContext(),R.drawable.bitter)));

        String id7 = bitter7.getId();
        markerMap.put(id7,"action_bitter");

        Marker bitter8 = mMap.addMarker(new MarkerOptions()
                .position(new LatLng(8.5450713,76.9122998))
                .title(getString(R.string.bg_name))
                .snippet(getString(R.string.bitter8))
                .rotation((float) 0.5)
                .icon(bitmapDescriptorFromVector(getApplicationContext(),R.drawable.bitter)));

        String id8 = bitter8.getId();
        markerMap.put(id8,"action_bitter");

        Marker bitter9 = mMap.addMarker(new MarkerOptions()
                .position(new LatLng(16.1776452,75.61501))
                .title(getString(R.string.bg_name))
                .snippet(getString(R.string.bitter9))
                .rotation((float) 0.5)
                .icon(bitmapDescriptorFromVector(getApplicationContext(),R.drawable.bitter)));

        String id9 = bitter9.getId();
        markerMap.put(id9,"action_bitter");

        Marker bitter10 = mMap.addMarker(new MarkerOptions()
                .position(new LatLng(22.622005, 87.586422))
                .title(getString(R.string.bg_name))
                .snippet(getString(R.string.bitter10))
                .rotation((float) 0.5)
                .icon(bitmapDescriptorFromVector(getApplicationContext(),R.drawable.bitter)));

        String id10 = bitter10.getId();
        markerMap.put(id10,"action_bitter");

        Marker bitter11 = mMap.addMarker(new MarkerOptions()
                .position(new LatLng(20.121101, 85.093024))
                .title(getString(R.string.bg_name))
                .snippet(getString(R.string.bitter11))
                .rotation((float) 0.5)
                .icon(bitmapDescriptorFromVector(getApplicationContext(),R.drawable.bitter)));

        String id11 = bitter11.getId();
        markerMap.put(id11,"action_bitter");

        Marker bitter12 = mMap.addMarker(new MarkerOptions()
                .position(new LatLng(26.5027069,92.1928121))
                .title(getString(R.string.bg_name))
                .snippet(getString(R.string.bitter12))
                .rotation((float) 0.5)
                .icon(bitmapDescriptorFromVector(getApplicationContext(),R.drawable.bitter)));

        String id12 = bitter12.getId();
        markerMap.put(id12,"action_bitter");

        Marker bitter13 = mMap.addMarker(new MarkerOptions()
                .position(new LatLng(26.880645, 80.870557))
                .title(getString(R.string.bg_name))
                .snippet(getString(R.string.bitter13))
                .rotation((float) 0.5)
                .icon(bitmapDescriptorFromVector(getApplicationContext(),R.drawable.bitter)));

        String id13 = bitter13.getId();
        markerMap.put(id13,"action_bitter");

        Marker bitter14 = mMap.addMarker(new MarkerOptions()
                .position(new LatLng(26.1388739,85.5993063))
                .title(getString(R.string.bg_name))
                .snippet(getString(R.string.bitter14))
                .rotation((float) 0.5)
                .icon(bitmapDescriptorFromVector(getApplicationContext(),R.drawable.bitter)));

        String id14 = bitter14.getId();
        markerMap.put(id14,"action_bitter");

        //chillies
        Marker chilli1 = mMap.addMarker(new MarkerOptions()
                .position(new LatLng( 16.5721152,80.8147065))
                .title(getString(R.string.chilly_name))
                .snippet(getString(R.string.chilli1))
                .rotation((float) 0.5)
                .icon(bitmapDescriptorFromVector(getApplicationContext(),R.drawable.chilly)));

        String id15 = chilli1.getId();
        markerMap.put(id15,"action_chilli");

        Marker chilli2 = mMap.addMarker(new MarkerOptions()
                .position(new LatLng( 17.743230,76.358023))
                .title(getString(R.string.chilly_name))
                .snippet(getString(R.string.chilli2))
                .rotation((float) 0.5)
                .icon(bitmapDescriptorFromVector(getApplicationContext(),R.drawable.chilly)));

        String id16 = chilli2.getId();
        markerMap.put(id16,"action_chilli");

        Marker chilli3 = mMap.addMarker(new MarkerOptions()
                .position(new LatLng( 21.8529856,72.186655))
                .title(getString(R.string.chilly_name))
                .snippet(getString(R.string.chilli3))
                .rotation((float) 0.5)
                .icon(bitmapDescriptorFromVector(getApplicationContext(),R.drawable.chilly)));

        String id17 = chilli3.getId();
        markerMap.put(id17,"action_chilli");

        Marker chilli4 = mMap.addMarker(new MarkerOptions()
                .position(new LatLng( 14.9158796,79.9470888))
                .title(getString(R.string.chilly_name))
                .snippet(getString(R.string.chilli4))
                .rotation((float) 0.5)
                .icon(bitmapDescriptorFromVector(getApplicationContext(),R.drawable.chilly)));

        String id18 = chilli4.getId();
        markerMap.put(id18,"action_chilli");

        Marker chilli5 = mMap.addMarker(new MarkerOptions()
                .position(new LatLng( 26.0000704,85.3798459))
                .title(getString(R.string.chilly_name))
                .snippet(getString(R.string.chilli5))
                .rotation((float) 0.5)
                .icon(bitmapDescriptorFromVector(getApplicationContext(),R.drawable.chilly)));

        String id19 = chilli5.getId();
        markerMap.put(id19,"action_chilli");

        Marker chilli6 = mMap.addMarker(new MarkerOptions()
                .position(new LatLng( 25.728502,85.3146136))
                .title(getString(R.string.chilly_name))
                .snippet(getString(R.string.chilli6))
                .rotation((float) 0.5)
                .icon(bitmapDescriptorFromVector(getApplicationContext(),R.drawable.chilly)));

        String id20 = chilli6.getId();
        markerMap.put(id20,"action_chilli");

        Marker chilli8 = mMap.addMarker(new MarkerOptions()
                .position(new LatLng( 25.5917546,85.1287018))
                .title(getString(R.string.chilly_name))
                .snippet(getString(R.string.chilli8))
                .rotation((float) 0.5)
                .icon(bitmapDescriptorFromVector(getApplicationContext(),R.drawable.chilly)));

        String id22 = chilli8.getId();
        markerMap.put(id22,"action_chilli");

        Marker chilli9 = mMap.addMarker(new MarkerOptions()
                .position(new LatLng( 23.835437,85.9400771))
                .title(getString(R.string.chilly_name))
                .snippet(getString(R.string.chilli9))
                .rotation((float) 0.5)
                .icon(bitmapDescriptorFromVector(getApplicationContext(),R.drawable.chilly)));

        String id23 = chilli9.getId();
        markerMap.put(id23,"action_chilli");

        Marker chilli10 = mMap.addMarker(new MarkerOptions()
                .position(new LatLng( 15.8231769,74.4692489))
                .title(getString(R.string.chilly_name))
                .snippet(getString(R.string.chilli10))
                .rotation((float) 0.5)
                .icon(bitmapDescriptorFromVector(getApplicationContext(),R.drawable.chilly)));

        String id24 = chilli10.getId();
        markerMap.put(id24,"action_chilli");

        Marker chilli11 = mMap.addMarker(new MarkerOptions()
                .position(new LatLng( 14.7908863,75.4137576))
                .title(getString(R.string.chilly_name))
                .snippet(getString(R.string.chilli11))
                .rotation((float) 0.5)
                .icon(bitmapDescriptorFromVector(getApplicationContext(),R.drawable.chilly)));

        String id25 = chilli11.getId();
        markerMap.put(id25,"action_chilli");

        Marker chilli12 = mMap.addMarker(new MarkerOptions()
                .position(new LatLng( 13.9285953,76.3238332))
                .title(getString(R.string.chilly_name))
                .snippet(getString(R.string.chilli12))
                .rotation((float) 0.5)
                .icon(bitmapDescriptorFromVector(getApplicationContext(),R.drawable.chilly)));

        String id26 = chilli12.getId();
        markerMap.put(id26,"action_chilli");

        Marker chilli13 = mMap.addMarker(new MarkerOptions()
                .position(new LatLng( 11.7059314,76.0892638))
                .title(getString(R.string.chilly_name))
                .snippet(getString(R.string.chilli13))
                .rotation((float) 0.5)
                .icon(bitmapDescriptorFromVector(getApplicationContext(),R.drawable.chilly)));

        String id27 = chilli13.getId();
        markerMap.put(id27,"action_chilli");

        Marker chilli14 = mMap.addMarker(new MarkerOptions()
                .position(new LatLng( 9.9301089,76.329705))
                .title(getString(R.string.chilly_name))
                .snippet(getString(R.string.chilli14))
                .rotation((float) 0.5)
                .icon(bitmapDescriptorFromVector(getApplicationContext(),R.drawable.chilly)));

        String id28 = chilli14.getId();
        markerMap.put(id28,"action_chilli");

        Marker chilli15 = mMap.addMarker(new MarkerOptions()
                .position(new LatLng( 10.7997999,76.6184431))
                .title(getString(R.string.chilly_name))
                .snippet(getString(R.string.chilli15))
                .rotation((float) 0.5)
                .icon(bitmapDescriptorFromVector(getApplicationContext(),R.drawable.chilly)));

        String id29 = chilli15.getId();
        markerMap.put(id29,"action_chilli");

        Marker chilli16 = mMap.addMarker(new MarkerOptions()
                .position(new LatLng( 30.660071,76.393922))
                .title(getString(R.string.chilly_name))
                .snippet(getString(R.string.chilli16))
                .rotation((float) 0.5)
                .icon(bitmapDescriptorFromVector(getApplicationContext(),R.drawable.chilly)));

        String id30 = chilli16.getId();
        markerMap.put(id30,"action_chilli");

        Marker chilli17 = mMap.addMarker(new MarkerOptions()
                .position(new LatLng( 30.299452,76.343685))
                .title(getString(R.string.chilly_name))
                .snippet(getString(R.string.chilli17))
                .rotation((float) 0.5)
                .icon(bitmapDescriptorFromVector(getApplicationContext(),R.drawable.chilly)));

        String id31 = chilli17.getId();
        markerMap.put(id31,"action_chilli");

        Marker chilli18 = mMap.addMarker(new MarkerOptions()
                .position(new LatLng(27.212677,73.749994))
                .title(getString(R.string.chilly_name))
                .snippet(getString(R.string.chilli18))
                .rotation((float) 0.5)
                .icon(bitmapDescriptorFromVector(getApplicationContext(),R.drawable.chilly)));

        String id32 = chilli18.getId();
        markerMap.put(id32,"action_chilli");

        Marker chilli19 = mMap.addMarker(new MarkerOptions()
                .position(new LatLng(25.760550,73.344324))
                .title(getString(R.string.chilly_name))
                .snippet(getString(R.string.chilli19))
                .rotation((float) 0.5)
                .icon(bitmapDescriptorFromVector(getApplicationContext(),R.drawable.chilly)));

        String id33 = chilli19.getId();
        markerMap.put(id33,"action_chilli");

        Marker chilli20 = mMap.addMarker(new MarkerOptions()
                .position(new LatLng(8.750034,77.766482))
                .title(getString(R.string.chilly_name))
                .snippet(getString(R.string.chilli20))
                .rotation((float) 0.5)
                .icon(bitmapDescriptorFromVector(getApplicationContext(),R.drawable.chilly)));

        String id34 = chilli20.getId();
        markerMap.put(id34,"action_chilli");

        Marker chilli21 = mMap.addMarker(new MarkerOptions()
                .position(new LatLng(9.562338,77.971782))
                .title(getString(R.string.chilly_name))
                .snippet(getString(R.string.chilli21))
                .rotation((float) 0.5)
                .icon(bitmapDescriptorFromVector(getApplicationContext(),R.drawable.chilly)));

        String id35 = chilli21.getId();
        markerMap.put(id35,"action_chilli");

        Marker chilli22 = mMap.addMarker(new MarkerOptions()
                .position(new LatLng(10.750193,79.608814))
                .title(getString(R.string.chilly_name))
                .snippet(getString(R.string.chilli22))
                .rotation((float) 0.5)
                .icon(bitmapDescriptorFromVector(getApplicationContext(),R.drawable.chilly)));

        String id36 = chilli22.getId();
        markerMap.put(id36,"action_chilli");

        Marker chilli23 = mMap.addMarker(new MarkerOptions()
                .position(new LatLng(26.542259,88.657778))
                .title(getString(R.string.chilly_name))
                .snippet(getString(R.string.chilli23))
                .rotation((float) 0.5)
                .icon(bitmapDescriptorFromVector(getApplicationContext(),R.drawable.chilly)));

        String id37 = chilli23.getId();
        markerMap.put(id37,"action_chilli");

        Marker chilli24 = mMap.addMarker(new MarkerOptions()
                .position(new LatLng(27.327495,82.845870))
                .title(getString(R.string.chilly_name))
                .snippet(getString(R.string.chilli24))
                .rotation((float) 0.5)
                .icon(bitmapDescriptorFromVector(getApplicationContext(),R.drawable.chilly)));

        String id38 = chilli24.getId();
        markerMap.put(id38,"action_chilli");

        Marker chilli25 = mMap.addMarker(new MarkerOptions()
                .position(new LatLng(24.6287944,77.3137289))
                .title(getString(R.string.chilly_name))
                .snippet(getString(R.string.chilli25))
                .rotation((float) 0.5)
                .icon(bitmapDescriptorFromVector(getApplicationContext(),R.drawable.chilly)));

        String id39 = chilli25.getId();
        markerMap.put(id39,"action_chilli");

        //coffee
        Marker coffee1 = mMap.addMarker(new MarkerOptions()
                .position(new LatLng(15.33671,76.1714945 ))
                .title(getString(R.string.coffee_name))
                .snippet(getString(R.string.coffee1))
                .rotation((float) 0.5)
                .icon(bitmapDescriptorFromVector(getApplicationContext(),R.drawable.coffee)));

        String id40 = coffee1.getId();
        markerMap.put(id40,"action_coffee");

        Marker coffee2 = mMap.addMarker(new MarkerOptions()
                .position(new LatLng(9.143953,76.880594))
                .title(getString(R.string.coffee_name))
                .snippet(getString(R.string.coffee2))
                .rotation((float) 0.5)
                .icon(bitmapDescriptorFromVector(getApplicationContext(),R.drawable.coffee)));

        String id41 = coffee2.getId();
        markerMap.put(id41,"action_coffee");

        Marker coffee3 = mMap.addMarker(new MarkerOptions()
                .position(new LatLng(17.745857,83.3146099))
                .title(getString(R.string.coffee_name))
                .snippet(getString(R.string.coffee3))
                .rotation((float) 0.5)
                .icon(bitmapDescriptorFromVector(getApplicationContext(),R.drawable.coffee)));

        String id42 = coffee3.getId();
        markerMap.put(id42,"action_coffee");

        Marker coffee4 = mMap.addMarker(new MarkerOptions()
                .position(new LatLng(11.9896583,76.6221921))
                .title(getString(R.string.coffee_name))
                .snippet(getString(R.string.coffee4))
                .rotation((float) 0.5)
                .icon(bitmapDescriptorFromVector(getApplicationContext(),R.drawable.coffee)));

        String id43 = coffee4.getId();
        markerMap.put(id43,"action_coffee");

        Marker coffee5 = mMap.addMarker(new MarkerOptions()
                .position(new LatLng(9.5901707,76.5065346))
                .title(getString(R.string.coffee_name))
                .snippet(getString(R.string.coffee5))
                .rotation((float) 0.5)
                .icon(bitmapDescriptorFromVector(getApplicationContext(),R.drawable.coffee)));

        String id44 = coffee5.getId();
        markerMap.put(id44,"action_coffee");

        Marker coffee6 = mMap.addMarker(new MarkerOptions()
                .position(new LatLng(11.813559,79.030833))
                .title(getString(R.string.coffee_name))
                .snippet(getString(R.string.coffee6))
                .rotation((float) 0.5)
                .icon(bitmapDescriptorFromVector(getApplicationContext(),R.drawable.coffee)));

        String id45 = coffee6.getId();
        markerMap.put(id45,"action_coffee");

        Marker coffee7 = mMap.addMarker(new MarkerOptions()
                .position(new LatLng(18.110086,78.403759))
                .title(getString(R.string.coffee_name))
                .snippet(getString(R.string.coffee7))
                .rotation((float) 0.5)
                .icon(bitmapDescriptorFromVector(getApplicationContext(),R.drawable.coffee)));

        String id46 = coffee7.getId();
        markerMap.put(id46,"action_coffee");

        Marker coffee8 = mMap.addMarker(new MarkerOptions()
                .position(new LatLng(21.342638, 83.646271))
                .title(getString(R.string.coffee_name))
                .snippet(getString(R.string.coffee8))
                .rotation((float) 0.5)
                .icon(bitmapDescriptorFromVector(getApplicationContext(),R.drawable.coffee)));

        String id47 = coffee8.getId();
        markerMap.put(id47,"action_coffee");

        Marker coffee9 = mMap.addMarker(new MarkerOptions()
                .position(new LatLng(26.4432382,91.6276435))
                .title(getString(R.string.coffee_name))
                .snippet(getString(R.string.coffee9))
                .rotation((float) 0.5)
                .icon(bitmapDescriptorFromVector(getApplicationContext(),R.drawable.coffee)));

        String id48 = coffee9.getId();
        markerMap.put(id48,"action_coffee");

        Marker coffee10 = mMap.addMarker(new MarkerOptions()
                .position(new LatLng(25.7019388,93.848571))
                .title(getString(R.string.coffee_name))
                .snippet(getString(R.string.coffee10))
                .rotation((float) 0.5)
                .icon(bitmapDescriptorFromVector(getApplicationContext(),R.drawable.coffee)));

        String id49 = coffee10.getId();
        markerMap.put(id49,"action_coffee");

        Marker coffee11 = mMap.addMarker(new MarkerOptions()
                .position(new LatLng(24.8035857,93.8991926))
                .title(getString(R.string.coffee_name))
                .snippet(getString(R.string.coffee11))
                .rotation((float) 0.5)
                .icon(bitmapDescriptorFromVector(getApplicationContext(),R.drawable.coffee)));

        String id50 = coffee11.getId();
        markerMap.put(id50,"action_coffee");

        Marker coffee12 = mMap.addMarker(new MarkerOptions()
                .position(new LatLng(27.9826308,94.6813279))
                .title(getString(R.string.coffee_name))
                .snippet(getString(R.string.coffee12))
                .rotation((float) 0.5)
                .icon(bitmapDescriptorFromVector(getApplicationContext(),R.drawable.coffee)));

        String id51 = coffee12.getId();
        markerMap.put(id51,"action_coffee");

        Marker coffee13 = mMap.addMarker(new MarkerOptions()
                .position(new LatLng(23.2769632,92.7712328))
                .title(getString(R.string.coffee_name))
                .snippet(getString(R.string.coffee13))
                .rotation((float) 0.5)
                .icon(bitmapDescriptorFromVector(getApplicationContext(),R.drawable.coffee)));

        String id52 = coffee13.getId();
        markerMap.put(id52,"action_coffee");

        //Cotton
        Marker cotton1 = mMap.addMarker(new MarkerOptions()
                .position(new LatLng(30.936965,75.906644))
                .title(getString(R.string.cotton_name))
                .snippet(getString(R.string.cotton1))
                .rotation((float)0.5)
                .icon(bitmapDescriptorFromVector(getApplicationContext(),R.drawable.cotton1)));

        String id53 = cotton1.getId();
        markerMap.put(id53,"action_cotton");

        Marker cotton2 = mMap.addMarker(new MarkerOptions()
                .position(new LatLng(21.703803,73.011676))
                .title(getString(R.string.cotton_name))
                .snippet(getString(R.string.cotton2))
                .rotation((float) 0.5)
                .icon(bitmapDescriptorFromVector(getApplicationContext(),R.drawable.cotton1)));

        String id54 = cotton2.getId();
        markerMap.put(id54,"action_cotton");

        Marker cotton3 = mMap.addMarker(new MarkerOptions()
                .position(new LatLng(20.9642836,77.9191293))
                .title(getString(R.string.cotton_name))
                .snippet(getString(R.string.cotton3))
                .rotation((float) 0.5)
                .icon(bitmapDescriptorFromVector(getApplicationContext(),R.drawable.cotton1)));

        String id55 = cotton3.getId();
        markerMap.put(id55,"action_cotton");

        Marker cotton4 = mMap.addMarker(new MarkerOptions()
                .position(new LatLng(20.0988268,77.1208336))
                .title(getString(R.string.cotton_name))
                .snippet(getString(R.string.cotton4))
                .rotation((float) 0.5)
                .icon(bitmapDescriptorFromVector(getApplicationContext(),R.drawable.cotton1)));

        String id56 = cotton4.getId();
        markerMap.put(id56,"action_cotton");

        Marker cotton5 = mMap.addMarker(new MarkerOptions()
                .position(new LatLng(22.992514,72.5238699))
                .title(getString(R.string.cotton_name))
                .snippet(getString(R.string.cotton5))
                .rotation((float) 0.5)
                .icon(bitmapDescriptorFromVector(getApplicationContext(),R.drawable.cotton1)));

        String id57 = cotton5.getId();
        markerMap.put(id57,"action_cotton");

        Marker cotton6 = mMap.addMarker(new MarkerOptions()
                .position(new LatLng(21.6618867,69.6137261))
                .title(getString(R.string.cotton_name))
                .snippet(getString(R.string.cotton6))
                .rotation((float) 0.5)
                .icon(bitmapDescriptorFromVector(getApplicationContext(),R.drawable.cotton1)));

        String id58 = cotton6.getId();
        markerMap.put(id58,"action_cotton");

        Marker cotton7 = mMap.addMarker(new MarkerOptions()
                .position(new LatLng(22.7192407,71.6191697))
                .title(getString(R.string.cotton_name))
                .snippet(getString(R.string.cotton7))
                .rotation((float) 0.5)
                .icon(bitmapDescriptorFromVector(getApplicationContext(),R.drawable.cotton1)));

        String id59 = cotton7.getId();
        markerMap.put(id59,"action_cotton");

        Marker cotton8 = mMap.addMarker(new MarkerOptions()
                .position(new LatLng(21.6037204,71.2267004))
                .title(getString(R.string.cotton_name))
                .snippet(getString(R.string.cotton8))
                .rotation((float) 0.5)
                .icon(bitmapDescriptorFromVector(getApplicationContext(),R.drawable.cotton1)));

        String id60 = cotton8.getId();
        markerMap.put(id60,"action_cotton");

        Marker cotton9 = mMap.addMarker(new MarkerOptions()
                .position(new LatLng(21.7822891,72.0007483))
                .title(getString(R.string.cotton_name))
                .snippet(getString(R.string.cotton9))
                .rotation((float) 0.5)
                .icon(bitmapDescriptorFromVector(getApplicationContext(),R.drawable.cotton1)));

        String id61 = cotton9.getId();
        markerMap.put(id61,"action_cotton");

        Marker cotton10 = mMap.addMarker(new MarkerOptions()
                .position(new LatLng(16.6201881,80.392938))
                .title(getString(R.string.cotton_name))
                .snippet(getString(R.string.cotton10))
                .rotation((float) 0.5)
                .icon(bitmapDescriptorFromVector(getApplicationContext(),R.drawable.cotton1)));

        String id62 = cotton10.getId();
        markerMap.put(id62,"action_cotton");

        Marker cotton11 = mMap.addMarker(new MarkerOptions()
                .position(new LatLng(14.5188676,77.6209084))
                .title(getString(R.string.cotton_name))
                .snippet(getString(R.string.cotton11))
                .rotation((float) 0.5)
                .icon(bitmapDescriptorFromVector(getApplicationContext(),R.drawable.cotton1)));

        String id63 = cotton11.getId();
        markerMap.put(id63,"action_cotton");

        Marker cotton12 = mMap.addMarker(new MarkerOptions()
                .position(new LatLng(14.3681261,78.3869848))
                .title(getString(R.string.cotton_name))
                .snippet(getString(R.string.cotton12))
                .rotation((float) 0.5)
                .icon(bitmapDescriptorFromVector(getApplicationContext(),R.drawable.cotton1)));

        String id64 = cotton12.getId();
        markerMap.put(id64,"action_cotton");

        Marker cotton13 = mMap.addMarker(new MarkerOptions()
                .position(new LatLng(25.9029465,87.4968142))
                .title(getString(R.string.cotton_name))
                .snippet(getString(R.string.cotton13))
                .rotation((float) 0.5)
                .icon(bitmapDescriptorFromVector(getApplicationContext(),R.drawable.cotton1)));

        String id65 = cotton13.getId();
        markerMap.put(id65,"action_cotton");

        Marker cotton14 = mMap.addMarker(new MarkerOptions()
                .position(new LatLng(15.4131182,75.6058384))
                .title(getString(R.string.cotton_name))
                .snippet(getString(R.string.cotton14))
                .rotation((float) 0.5)
                .icon(bitmapDescriptorFromVector(getApplicationContext(),R.drawable.cotton1)));

        String id66 = cotton14.getId();
        markerMap.put(id66,"action_cotton");

        Marker cotton15 = mMap.addMarker(new MarkerOptions()
                .position(new LatLng(16.2345654,77.3364226))
                .title(getString(R.string.cotton_name))
                .snippet(getString(R.string.cotton15))
                .rotation((float) 0.5)
                .icon(bitmapDescriptorFromVector(getApplicationContext(),R.drawable.cotton1)));

        String id67 = cotton15.getId();
        markerMap.put(id67,"action_cotton");

        Marker cotton16 = mMap.addMarker(new MarkerOptions()
                .position(new LatLng(17.3645509,76.8161156))
                .title(getString(R.string.cotton_name))
                .snippet(getString(R.string.cotton16))
                .rotation((float) 0.5)
                .icon(bitmapDescriptorFromVector(getApplicationContext(),R.drawable.cotton1)));

        String id68 = cotton16.getId();
        markerMap.put(id68,"action_cotton");

        Marker cotton17 = mMap.addMarker(new MarkerOptions()
                .position(new LatLng(30.473139,74.488159))
                .title(getString(R.string.cotton_name))
                .snippet(getString(R.string.cotton17))
                .rotation((float) 0.5)
                .icon(bitmapDescriptorFromVector(getApplicationContext(),R.drawable.cotton1)));

        String id69 = cotton17.getId();
        markerMap.put(id69,"action_cotton");

        Marker cotton18 = mMap.addMarker(new MarkerOptions()
                .position(new LatLng(30.904472,74.605943))
                .title(getString(R.string.cotton_name))
                .snippet(getString(R.string.cotton18))
                .rotation((float) 0.5)
                .icon(bitmapDescriptorFromVector(getApplicationContext(),R.drawable.cotton1)));

        String id70 = cotton18.getId();
        markerMap.put(id70,"action_cotton");

        Marker cotton19 = mMap.addMarker(new MarkerOptions()
                .position(new LatLng(26.938663,75.700492))
                .title(getString(R.string.cotton_name))
                .snippet(getString(R.string.cotton19))
                .rotation((float) 0.5)
                .icon(bitmapDescriptorFromVector(getApplicationContext(),R.drawable.cotton1)));

        String id71 = cotton19.getId();
        markerMap.put(id71,"action_cotton");

        Marker cotton20 = mMap.addMarker(new MarkerOptions()
                .position(new LatLng(23.838828,74.452142))
                .title(getString(R.string.cotton_name))
                .snippet(getString(R.string.cotton20))
                .rotation((float) 0.5)
                .icon(bitmapDescriptorFromVector(getApplicationContext(),R.drawable.cotton1)));

        String id72 = cotton20.getId();
        markerMap.put(id72,"action_cotton");

        Marker cotton21 = mMap.addMarker(new MarkerOptions()
                .position(new LatLng(24.878113,74.642495))
                .title(getString(R.string.cotton_name))
                .snippet(getString(R.string.cotton21))
                .rotation((float) 0.5)
                .icon(bitmapDescriptorFromVector(getApplicationContext(),R.drawable.cotton1)));

        String id73 = cotton21.getId();
        markerMap.put(id73,"action_cotton");

        Marker cotton22 = mMap.addMarker(new MarkerOptions()
                .position(new LatLng(11.137253,77.3909908))
                .title(getString(R.string.cotton_name))
                .snippet(getString(R.string.cotton22))
                .rotation((float) 0.5)
                .icon(bitmapDescriptorFromVector(getApplicationContext(),R.drawable.cotton1)));

        String id74 = cotton22.getId();
        markerMap.put(id74,"action_cotton");

        Marker cotton23 = mMap.addMarker(new MarkerOptions()
                .position(new LatLng(10.358579,77.956068))
                .title(getString(R.string.cotton_name))
                .snippet(getString(R.string.cotton23))
                .rotation((float) 0.5)
                .icon(bitmapDescriptorFromVector(getApplicationContext(),R.drawable.cotton1)));

        String id75 = cotton23.getId();
        markerMap.put(id75,"action_cotton");

        Marker cotton24 = mMap.addMarker(new MarkerOptions()
                .position(new LatLng(8.085850,77.534731))
                .title(getString(R.string.cotton_name))
                .snippet(getString(R.string.cotton24))
                .rotation((float) 0.5)
                .icon(bitmapDescriptorFromVector(getApplicationContext(),R.drawable.cotton1)));

        String id76 = cotton24.getId();
        markerMap.put(id76,"action_cotton");

        Marker cotton25 = mMap.addMarker(new MarkerOptions()
                .position(new LatLng(23.9372909,92.2055373))
                .title(getString(R.string.cotton_name))
                .snippet(getString(R.string.cotton25))
                .rotation((float) 0.5)
                .icon(bitmapDescriptorFromVector(getApplicationContext(),R.drawable.cotton1)));

        String id77 = cotton25.getId();
        markerMap.put(id77,"action_cotton");

        Marker cotton26 = mMap.addMarker(new MarkerOptions()
                .position(new LatLng(28.916407,77.681379))
                .title(getString(R.string.cotton_name))
                .snippet(getString(R.string.cotton26))
                .rotation((float) 0.5)
                .icon(bitmapDescriptorFromVector(getApplicationContext(),R.drawable.cotton1)));

        String id78 = cotton26.getId();
        markerMap.put(id78,"action_cotton");

        Marker cotton27 = mMap.addMarker(new MarkerOptions()
                .position(new LatLng(27.942964,78.143680))
                .title(getString(R.string.cotton_name))
                .snippet(getString(R.string.cotton27))
                .rotation((float) 0.5)
                .icon(bitmapDescriptorFromVector(getApplicationContext(),R.drawable.cotton1)));

        String id79 = cotton27.getId();
        markerMap.put(id79,"action_cotton");

        Marker cotton28 = mMap.addMarker(new MarkerOptions()
                .position(new LatLng(23.445347,75.575178))
                .title(getString(R.string.cotton_name))
                .snippet(getString(R.string.cotton28))
                .rotation((float) 0.5)
                .icon(bitmapDescriptorFromVector(getApplicationContext(),R.drawable.cotton1)));

        String id80 = cotton28.getId();
        markerMap.put(id80,"action_cotton");

        Marker cotton29 = mMap.addMarker(new MarkerOptions()
                .position(new LatLng(22.145669,77.233997))
                .title(getString(R.string.cotton_name))
                .snippet(getString(R.string.cotton29))
                .rotation((float) 0.5)
                .icon(bitmapDescriptorFromVector(getApplicationContext(),R.drawable.cotton1)));

        String id81 = cotton29.getId();
        markerMap.put(id81,"action_cotton");

        Marker cotton30 = mMap.addMarker(new MarkerOptions()
                .position(new LatLng(25.790411,76.770744))
                .title(getString(R.string.cotton_name))
                .snippet(getString(R.string.cotton30))
                .rotation((float) 0.5)
                .icon(bitmapDescriptorFromVector(getApplicationContext(),R.drawable.cotton1)));

        String id82 = cotton30.getId();
        markerMap.put(id82,"action_cotton");


        //methi
        Marker  methi1 = mMap.addMarker(new MarkerOptions()
                .position(new LatLng( 24.960222,73.747137))
                .title(getString(R.string.methi_name))
                .snippet(getString(R.string.methi1))
                .rotation((float) 0.5)
                .icon(bitmapDescriptorFromVector(getApplicationContext(),R.drawable.methi)));

        String id83 = methi1.getId();
        markerMap.put(id83,"action_methi");

        Marker methi2 = mMap.addMarker(new MarkerOptions()
                .position(new LatLng(11.465733,78.917556))
                .title(getString(R.string.methi_name))
                .snippet(getString(R.string.methi2))
                .rotation((float) 0.5)
                .icon(bitmapDescriptorFromVector(getApplicationContext(),R.drawable.methi)));

        String id84 = methi2.getId();
        markerMap.put(id84,"action_methi");

        Marker methi3 = mMap.addMarker(new MarkerOptions()
                .position(new LatLng( 30.6949027,77.9681044))
                .title(getString(R.string.methi_name))
                .snippet(getString(R.string.methi3))
                .rotation((float) 0.5)
                .icon(bitmapDescriptorFromVector(getApplicationContext(),R.drawable.methi)));

        String id85 = methi3.getId();
        markerMap.put(id85,"action_methi");

        Marker methi4 = mMap.addMarker(new MarkerOptions()
                .position(new LatLng(28.393527,77.833948))
                .title(getString(R.string.methi_name))
                .snippet(getString(R.string.methi4))
                .rotation((float) 0.5)
                .icon(bitmapDescriptorFromVector(getApplicationContext(),R.drawable.methi)));

        String id86 = methi4.getId();
        markerMap.put(id86,"action_methi");

        Marker methi5 = mMap.addMarker(new MarkerOptions()
                .position(new LatLng( 27.1905718,75.785764))
                .title(getString(R.string.methi_name))
                .snippet(getString(R.string.methi5))
                .rotation((float) 0.5)
                .icon(bitmapDescriptorFromVector(getApplicationContext(),R.drawable.methi)));

        String id87 = methi5.getId();
        markerMap.put(id87,"action_methi");

        Marker methi6 = mMap.addMarker(new MarkerOptions()
                .position(new LatLng( 22.978655,88.716146))
                .title(getString(R.string.methi_name))
                .snippet(getString(R.string.methi6))
                .rotation((float) 0.5)
                .icon(bitmapDescriptorFromVector(getApplicationContext(),R.drawable.methi)));

        String id88 = methi6.getId();
        markerMap.put(id88,"action_methi");

        Marker methi7 = mMap.addMarker(new MarkerOptions()
                .position(new LatLng(22.080513,72.8127040))
                .title(getString(R.string.methi_name))
                .snippet(getString(R.string.methi7))
                .rotation((float) 0.5)
                .icon(bitmapDescriptorFromVector(getApplicationContext(),R.drawable.methi)));

        String id89 = methi7.getId();
        markerMap.put(id89,"action_methi");

        Marker methi8 = mMap.addMarker(new MarkerOptions()
                .position(new LatLng(23.697114, 80.448223))
                .title(getString(R.string.methi_name))
                .snippet(getString(R.string.methi8))
                .rotation((float) 0.5)
                .icon(bitmapDescriptorFromVector(getApplicationContext(),R.drawable.methi)));

        String id90 = methi8.getId();
        markerMap.put(id90,"action_methi");

        Marker methi9= mMap.addMarker(new MarkerOptions()
                .position(new LatLng(19.2067796,81.9303989))
                .title(getString(R.string.methi_name))
                .snippet(getString(R.string.methi9))
                .rotation((float) 0.5)
                .icon(bitmapDescriptorFromVector(getApplicationContext(),R.drawable.methi)));

        String id91 = methi9.getId();
        markerMap.put(id91,"action_methi");

        //Indian mustard
        Marker mustard1 = mMap.addMarker(new MarkerOptions()
                .position(new LatLng( 25.6109918,75.8963763))
                .title(getString(R.string.mustard_name))
                .snippet(getString(R.string.mustard1))
                .rotation((float) 0.5)
                .icon(bitmapDescriptorFromVector(getApplicationContext(),R.drawable.mustard)));

        String id92 = mustard1.getId();
        markerMap.put(id92,"action_mustard");

        Marker mustard2 = mMap.addMarker(new MarkerOptions()
                .position(new LatLng(29.110322,75.735147 ))
                .title(getString(R.string.mustard_name))
                .snippet(getString(R.string.mustard2))
                .rotation((float) 0.5)
                .icon(bitmapDescriptorFromVector(getApplicationContext(),R.drawable.mustard)));

        String id93 = mustard2.getId();
        markerMap.put(id93,"action_mustard");

        Marker mustard3 = mMap.addMarker(new MarkerOptions()
                .position(new LatLng(26.9980286,94.6389229 ))
                .title(getString(R.string.mustard_name))
                .snippet(getString(R.string.mustard3))
                .rotation((float) 0.5)
                .icon(bitmapDescriptorFromVector(getApplicationContext(),R.drawable.mustard)));

        String id94 = mustard3.getId();
        markerMap.put(id94,"action_mustard");

        Marker mustard4 = mMap.addMarker(new MarkerOptions()
                .position(new LatLng(27.3892139,94.3675155 ))
                .title(getString(R.string.mustard_name))
                .snippet(getString(R.string.mustard4))
                .rotation((float) 0.5)
                .icon(bitmapDescriptorFromVector(getApplicationContext(),R.drawable.mustard)));

        String id95 = mustard4.getId();
        markerMap.put(id95,"action_mustard");

        Marker mustard5 = mMap.addMarker(new MarkerOptions()
                .position(new LatLng(26.7941747,84.9943328 ))
                .title(getString(R.string.mustard_name))
                .snippet(getString(R.string.mustard5))
                .rotation((float) 0.5)
                .icon(bitmapDescriptorFromVector(getApplicationContext(),R.drawable.mustard)));

        String id96 = mustard5.getId();
        markerMap.put(id96,"action_mustard");

        Marker mustard6 = mMap.addMarker(new MarkerOptions()
                .position(new LatLng(33.806501,74.2608064))
                .title(getString(R.string.mustard_name))
                .snippet(getString(R.string.mustard6))
                .rotation((float) 0.5)
                .icon(bitmapDescriptorFromVector(getApplicationContext(),R.drawable.mustard)));

        String id97 = mustard6.getId();
        markerMap.put(id97,"action_mustard");

        Marker mustard7 = mMap.addMarker(new MarkerOptions()
                .position(new LatLng(33.7238001,75.1427025))
                .title(getString(R.string.mustard_name))
                .snippet(getString(R.string.mustard7))
                .rotation((float) 0.5)
                .icon(bitmapDescriptorFromVector(getApplicationContext(),R.drawable.mustard)));

        String id98 = mustard7.getId();
        markerMap.put(id98,"action_mustard");

        Marker mustard8 = mMap.addMarker(new MarkerOptions()
                .position(new LatLng(25.5953263,90.2420074))
                .title(getString(R.string.mustard_name))
                .snippet(getString(R.string.mustard8))
                .rotation((float) 0.5)
                .icon(bitmapDescriptorFromVector(getApplicationContext(),R.drawable.mustard)));

        String id99 = mustard8.getId();
        markerMap.put(id99,"action_mustard");

        Marker mustard9 = mMap.addMarker(new MarkerOptions()
                .position(new LatLng(12.120346,78.141446))
                .title(getString(R.string.mustard_name))
                .snippet(getString(R.string.mustard9))
                .rotation((float) 0.5)
                .icon(bitmapDescriptorFromVector(getApplicationContext(),R.drawable.mustard)));

        String id100 = mustard9.getId();
        markerMap.put(id100,"action_mustard");

        Marker mustard10 = mMap.addMarker(new MarkerOptions()
                .position(new LatLng(25.510867,78.759478))
                .title(getString(R.string.mustard_name))
                .snippet(getString(R.string.mustard10))
                .rotation((float) 0.5)
                .icon(bitmapDescriptorFromVector(getApplicationContext(),R.drawable.mustard)));

        String id101 = mustard10.getId();
        markerMap.put(id101,"action_mustard");

        Marker mustard11 = mMap.addMarker(new MarkerOptions()
                .position(new LatLng(25.218192,82.926492))
                .title(getString(R.string.mustard_name))
                .snippet(getString(R.string.mustard11))
                .rotation((float) 0.5)
                .icon(bitmapDescriptorFromVector(getApplicationContext(),R.drawable.mustard)));

        String id102 = mustard11.getId();
        markerMap.put(id102,"action_mustard");

        Marker mustard12 = mMap.addMarker(new MarkerOptions()
                .position(new LatLng(27.559122,80.706447))
                .title(getString(R.string.mustard_name))
                .snippet(getString(R.string.mustard12))
                .rotation((float) 0.5)
                .icon(bitmapDescriptorFromVector(getApplicationContext(),R.drawable.mustard)));

        String id103 = mustard12.getId();
        markerMap.put(id103,"action_mustard");

        Marker mustard13 = mMap.addMarker(new MarkerOptions()
                .position(new LatLng(26.447289,78.725410))
                .title(getString(R.string.mustard_name))
                .snippet(getString(R.string.mustard13))
                .rotation((float) 0.5)
                .icon(bitmapDescriptorFromVector(getApplicationContext(),R.drawable.mustard)));

        String id104 = mustard13.getId();
        markerMap.put(id104,"action_mustard");

//Lentil
        Marker lentil1 = mMap.addMarker(new MarkerOptions()
                .position(new LatLng( 23.835158,84.282460))
                .title(getString(R.string.lentil_name))
                .snippet(getString(R.string.lentil1))
                .rotation((float) 0.5)
                .icon(bitmapDescriptorFromVector(getApplicationContext(),R.drawable.lentils)));

        String id105 = lentil1.getId();
        markerMap.put(id105,"action_lentil");

        Marker lentil2 = mMap.addMarker(new MarkerOptions()
                .position(new LatLng( 24.420004,81.982265))
                .title(getString(R.string.lentil_name))
                .snippet(getString(R.string.lentil2))
                .rotation((float) 0.5)
                .icon(bitmapDescriptorFromVector(getApplicationContext(),R.drawable.lentils)));

        String id106 = lentil2.getId();
        markerMap.put(id106,"action_lentil");

        Marker lentil3 = mMap.addMarker(new MarkerOptions()
                .position(new LatLng( 17.6079693,81.3368949))
                .title(getString(R.string.lentil_name))
                .snippet(getString(R.string.lentil3))
                .rotation((float) 0.5)
                .icon(bitmapDescriptorFromVector(getApplicationContext(),R.drawable.lentils)));

        String id107 = lentil3.getId();
        markerMap.put(id107,"action_lentil");

        Marker lentil4 = mMap.addMarker(new MarkerOptions()
                .position(new LatLng( 17.0015691,81.4784293))
                .title(getString(R.string.lentil_name))
                .snippet(getString(R.string.lentil4))
                .rotation((float) 0.5)
                .icon(bitmapDescriptorFromVector(getApplicationContext(),R.drawable.lentils)));

        String id108 = lentil4.getId();
        markerMap.put(id108,"action_lentil");

        Marker lentil5 = mMap.addMarker(new MarkerOptions()
                .position(new LatLng( 15.3842851,79.9568439))
                .title(getString(R.string.lentil_name))
                .snippet(getString(R.string.lentil5))
                .rotation((float) 0.5)
                .icon(bitmapDescriptorFromVector(getApplicationContext(),R.drawable.lentils)));

        String id109 = lentil5.getId();
        markerMap.put(id109,"action_lentil");

        Marker lentil6 = mMap.addMarker(new MarkerOptions()
                .position(new LatLng( 25.118556,83.4784832))
                .title(getString(R.string.lentil_name))
                .snippet(getString(R.string.lentil6))
                .rotation((float) 0.5)
                .icon(bitmapDescriptorFromVector(getApplicationContext(),R.drawable.lentils)));

        String id110 = lentil6.getId();
        markerMap.put(id110,"action_lentil");

        Marker lentil7 = mMap.addMarker(new MarkerOptions()
                .position(new LatLng( 30.412697,74.038519))
                .title(getString(R.string.lentil_name))
                .snippet(getString(R.string.lentil7))
                .rotation((float) 0.5)
                .icon(bitmapDescriptorFromVector(getApplicationContext(),R.drawable.lentils)));

        String id111 = lentil7.getId();
        markerMap.put(id111,"action_lentil");

        Marker lentil8 = mMap.addMarker(new MarkerOptions()
                .position(new LatLng( 30.172508,74.982386))
                .title(getString(R.string.lentil_name))
                .snippet(getString(R.string.lentil8))
                .rotation((float) 0.5)
                .icon(bitmapDescriptorFromVector(getApplicationContext(),R.drawable.lentils)));

        String id112 = lentil8.getId();
        markerMap.put(id112,"action_lentil");

        Marker lentil9 = mMap.addMarker(new MarkerOptions()
                .position(new LatLng( 30.223110,75.832173))
                .title(getString(R.string.lentil_name))
                .snippet(getString(R.string.lentil9))
                .rotation((float) 0.5)
                .icon(bitmapDescriptorFromVector(getApplicationContext(),R.drawable.lentils)));

        String id113 = lentil9.getId();
        markerMap.put(id113,"action_lentil");

        Marker lentil10 = mMap.addMarker(new MarkerOptions()
                .position(new LatLng( 28.317362,74.960309))
                .title(getString(R.string.lentil_name))
                .snippet(getString(R.string.lentil10))
                .rotation((float) 0.5)
                .icon(bitmapDescriptorFromVector(getApplicationContext(),R.drawable.lentils)));

        String id114 = lentil10.getId();
        markerMap.put(id114,"action_lentil");

        Marker lentil11 = mMap.addMarker(new MarkerOptions()
                .position(new LatLng( 28.036759,73.271327))
                .title(getString(R.string.lentil_name))
                .snippet(getString(R.string.lentil11))
                .rotation((float) 0.5)
                .icon(bitmapDescriptorFromVector(getApplicationContext(),R.drawable.lentils)));

        String id115 = lentil11.getId();
        markerMap.put(id115,"action_lentil");

        Marker lentil12 = mMap.addMarker(new MarkerOptions()
                .position(new LatLng( 28.127644,75.386839))
                .title(getString(R.string.lentil_name))
                .snippet(getString(R.string.lentil12))
                .rotation((float) 0.5)
                .icon(bitmapDescriptorFromVector(getApplicationContext(),R.drawable.lentils)));

        String id116 = lentil12.getId();
        markerMap.put(id116,"action_lentil");

        Marker lentil13 = mMap.addMarker(new MarkerOptions()
                .position(new LatLng( 26.429398,74.596271))
                .title(getString(R.string.lentil_name))
                .snippet(getString(R.string.lentil13))
                .rotation((float) 0.5)
                .icon(bitmapDescriptorFromVector(getApplicationContext(),R.drawable.lentils)));

        String id117 = lentil13.getId();
        markerMap.put(id117,"action_lentil");

        Marker lentil14 = mMap.addMarker(new MarkerOptions()
                .position(new LatLng( 8.7673883,78.0999913))
                .title(getString(R.string.lentil_name))
                .snippet(getString(R.string.lentil14))
                .rotation((float) 0.5)
                .icon(bitmapDescriptorFromVector(getApplicationContext(),R.drawable.lentils)));

        String id118 = lentil14.getId();
        markerMap.put(id118,"action_lentil");

        Marker lentil15 = mMap.addMarker(new MarkerOptions()
                .position(new LatLng( 9.352319,78.865012))
                .title(getString(R.string.lentil_name))
                .snippet(getString(R.string.lentil15))
                .rotation((float) 0.5)
                .icon(bitmapDescriptorFromVector(getApplicationContext(),R.drawable.lentils)));

        String id119 = lentil15.getId();
        markerMap.put(id119,"action_lentil");

        Marker lentil16 = mMap.addMarker(new MarkerOptions()
                .position(new LatLng( 9.842558,78.489942))
                .title(getString(R.string.lentil_name))
                .snippet(getString(R.string.lentil16))
                .rotation((float) 0.5)
                .icon(bitmapDescriptorFromVector(getApplicationContext(),R.drawable.lentils)));

        String id120 = lentil16.getId();
        markerMap.put(id120,"action_lentil");

        Marker lentil17 = mMap.addMarker(new MarkerOptions()
                .position(new LatLng( 25.022883,88.124463))
                .title(getString(R.string.lentil_name))
                .snippet(getString(R.string.lentil17))
                .rotation((float) 0.5)
                .icon(bitmapDescriptorFromVector(getApplicationContext(),R.drawable.lentils)));

        String id121 = lentil17.getId();
        markerMap.put(id121,"action_lentil");

        //maize
        Marker maize1 = mMap.addMarker(new MarkerOptions()
                .position(new LatLng( 25.217379,74.430118))
                .title(getString(R.string.maize_name))
                .snippet(getString(R.string.maize1))
                .rotation((float) 0.5)
                .icon(bitmapDescriptorFromVector(getApplicationContext(),R.drawable.maize)));

        String id122 = maize1.getId();
        markerMap.put(id122,"action_maize");

        Marker maize2 = mMap.addMarker(new MarkerOptions()
                .position(new LatLng(12.962304,74.968404))
                .title(getString(R.string.maize_name))
                .snippet(getString(R.string.maize2))
                .rotation((float) 0.5)
                .icon(bitmapDescriptorFromVector(getApplicationContext(),R.drawable.maize)));

        String id123 = maize2.getId();
        markerMap.put(id123,"action_maize");

        Marker maize3 = mMap.addMarker(new MarkerOptions()
                .position(new LatLng(24.0088483,72.945629))
                .title(getString(R.string.maize_name))
                .snippet(getString(R.string.maize3))
                .rotation((float) 0.5)
                .icon(bitmapDescriptorFromVector(getApplicationContext(),R.drawable.maize)));

        String id124 = maize3.getId();
        markerMap.put(id124,"action_maize");

        Marker maize4 = mMap.addMarker(new MarkerOptions()
                .position(new LatLng(23.2670993,73.1222821))
                .title(getString(R.string.maize_name))
                .snippet(getString(R.string.maize4))
                .rotation((float) 0.5)
                .icon(bitmapDescriptorFromVector(getApplicationContext(),R.drawable.maize)));

        String id125 = maize4.getId();
        markerMap.put(id125,"action_maize");

        Marker maize5 = mMap.addMarker(new MarkerOptions()
                .position(new LatLng(23.5478252,74.4055896))
                .title(getString(R.string.maize_name))
                .snippet(getString(R.string.maize5))
                .rotation((float) 0.5)
                .icon(bitmapDescriptorFromVector(getApplicationContext(),R.drawable.maize)));

        String id126 = maize5.getId();
        markerMap.put(id126,"action_maize");

        Marker maize6 = mMap.addMarker(new MarkerOptions()
                .position(new LatLng(17.0538002,82.078628))
                .title(getString(R.string.maize_name))
                .snippet(getString(R.string.maize6))
                .rotation((float) 0.5)
                .icon(bitmapDescriptorFromVector(getApplicationContext(),R.drawable.maize)));

        String id127 = maize6.getId();
        markerMap.put(id127,"action_maize");

        Marker maize7 = mMap.addMarker(new MarkerOptions()
                .position(new LatLng(26.5133553,93.7497394))
                .title(getString(R.string.maize_name))
                .snippet(getString(R.string.maize7))
                .rotation((float) 0.5)
                .icon(bitmapDescriptorFromVector(getApplicationContext(),R.drawable.maize)));

        String id128 = maize7.getId();
        markerMap.put(id128,"action_maize");

        Marker maize8 = mMap.addMarker(new MarkerOptions()
                .position(new LatLng(25.5702799,87.5604638))
                .title(getString(R.string.maize_name))
                .snippet(getString(R.string.maize8))
                .rotation((float) 0.5)
                .icon(bitmapDescriptorFromVector(getApplicationContext(),R.drawable.maize)));

        String id129 = maize8.getId();
        markerMap.put(id129,"action_maize");

        Marker maize9 = mMap.addMarker(new MarkerOptions()
                .position(new LatLng(25.8565545,85.784318))
                .title(getString(R.string.maize_name))
                .snippet(getString(R.string.maize9))
                .rotation((float) 0.5)
                .icon(bitmapDescriptorFromVector(getApplicationContext(),R.drawable.maize)));

        String id130 = maize9.getId();
        markerMap.put(id130,"action_maize");

        Marker maize10 = mMap.addMarker(new MarkerOptions()
                .position(new LatLng(26.1506333,85.3434666))
                .title(getString(R.string.maize_name))
                .snippet(getString(R.string.maize10))
                .rotation((float) 0.5)
                .icon(bitmapDescriptorFromVector(getApplicationContext(),R.drawable.maize)));

        String id131 = maize10.getId();
        markerMap.put(id131,"action_maize");

        Marker maize11 = mMap.addMarker(new MarkerOptions()
                .position(new LatLng(23.2188581,82.8636573))
                .title(getString(R.string.maize_name))
                .snippet(getString(R.string.maize11))
                .rotation((float) 0.5)
                .icon(bitmapDescriptorFromVector(getApplicationContext(),R.drawable.maize)));

        String id132 = maize11.getId();
        markerMap.put(id132,"action_maize");

        Marker maize12 = mMap.addMarker(new MarkerOptions()
                .position(new LatLng(22.2358,82.2495))
                .title(getString(R.string.maize_name))
                .snippet(getString(R.string.maize12))
                .rotation((float) 0.5)
                .icon(bitmapDescriptorFromVector(getApplicationContext(),R.drawable.maize)));

        String id133 = maize12.getId();
        markerMap.put(id133,"action_maize");

        Marker maize13 = mMap.addMarker(new MarkerOptions()
                .position(new LatLng(34.2839597,74.614112))
                .title(getString(R.string.maize_name))
                .snippet(getString(R.string.maize13))
                .rotation((float) 0.5)
                .icon(bitmapDescriptorFromVector(getApplicationContext(),R.drawable.maize)));

        String id134 = maize13.getId();
        markerMap.put(id134,"action_maize");

        Marker maize14 = mMap.addMarker(new MarkerOptions()
                .position(new LatLng(32.3886303,75.5558307))
                .title(getString(R.string.maize_name))
                .snippet(getString(R.string.maize14))
                .rotation((float) 0.5)
                .icon(bitmapDescriptorFromVector(getApplicationContext(),R.drawable.maize)));

        String id135 = maize14.getId();
        markerMap.put(id135,"action_maize");

        Marker maize15 = mMap.addMarker(new MarkerOptions()
                .position(new LatLng(11.9896583,76.4221921))
                .title(getString(R.string.maize_name))
                .snippet(getString(R.string.maize15))
                .rotation((float) 0.5)
                .icon(bitmapDescriptorFromVector(getApplicationContext(),R.drawable.maize)));

        String id136 = maize15.getId();
        markerMap.put(id136,"action_maize");

        Marker maize16 = mMap.addMarker(new MarkerOptions()
                .position(new LatLng(25.7010954,90.8044352))
                .title(getString(R.string.maize_name))
                .snippet(getString(R.string.maize16))
                .rotation((float) 0.5)
                .icon(bitmapDescriptorFromVector(getApplicationContext(),R.drawable.maize)));

        String id137 = maize16.getId();
        markerMap.put(id137,"action_maize");

        Marker maize17 = mMap.addMarker(new MarkerOptions()
                .position(new LatLng(19.165465,83.425974))
                .title(getString(R.string.maize_name))
                .snippet(getString(R.string.maize17))
                .rotation((float) 0.5)
                .icon(bitmapDescriptorFromVector(getApplicationContext(),R.drawable.maize)));

        String id138 = maize17.getId();
        markerMap.put(id138,"action_maize");

        Marker maize18 = mMap.addMarker(new MarkerOptions()
                .position(new LatLng(20.813687,84.315090))
                .title(getString(R.string.maize_name))
                .snippet(getString(R.string.maize18))
                .rotation((float) 0.5)
                .icon(bitmapDescriptorFromVector(getApplicationContext(),R.drawable.maize)));

        String id139 = maize18.getId();
        markerMap.put(id139,"action_maize");

        Marker maize19 = mMap.addMarker(new MarkerOptions()
                .position(new LatLng(31.271512,74.605614))
                .title(getString(R.string.maize_name))
                .snippet(getString(R.string.maize19))
                .rotation((float) 0.5)
                .icon(bitmapDescriptorFromVector(getApplicationContext(),R.drawable.maize)));

        String id140 = maize19.getId();
        markerMap.put(id140,"action_maize");

        Marker maize20 = mMap.addMarker(new MarkerOptions()
                .position(new LatLng(30.671371,76.680613))
                .title(getString(R.string.maize_name))
                .snippet(getString(R.string.maize20))
                .rotation((float) 0.5)
                .icon(bitmapDescriptorFromVector(getApplicationContext(),R.drawable.maize)));

        String id141 = maize20.getId();
        markerMap.put(id141,"action_maize");

        Marker maize21 = mMap.addMarker(new MarkerOptions()
                .position(new LatLng(25.104157,76.453138))
                .title(getString(R.string.maize_name))
                .snippet(getString(R.string.maize21))
                .rotation((float) 0.5)
                .icon(bitmapDescriptorFromVector(getApplicationContext(),R.drawable.maize)));

        String id142 = maize21.getId();
        markerMap.put(id142,"action_maize");

        Marker maize22 = mMap.addMarker(new MarkerOptions()
                .position(new LatLng(23.845555,73.732519))
                .title(getString(R.string.maize_name))
                .snippet(getString(R.string.maize22))
                .rotation((float) 0.5)
                .icon(bitmapDescriptorFromVector(getApplicationContext(),R.drawable.maize)));

        String id143 = maize22.getId();
        markerMap.put(id143,"action_maize");

        Marker maize23 = mMap.addMarker(new MarkerOptions()
                .position(new LatLng(10.777648,78.709473))
                .title(getString(R.string.maize_name))
                .snippet(getString(R.string.maize23))
                .rotation((float) 0.5)
                .icon(bitmapDescriptorFromVector(getApplicationContext(),R.drawable.maize)));

        String id144 = maize23.getId();
        markerMap.put(id144,"action_maize");

        Marker maize24 = mMap.addMarker(new MarkerOptions()
                .position(new LatLng(29.486628,77.717797))
                .title(getString(R.string.maize_name))
                .snippet(getString(R.string.maize24))
                .rotation((float) 0.5)
                .icon(bitmapDescriptorFromVector(getApplicationContext(),R.drawable.maize)));

        String id145 = maize24.getId();
        markerMap.put(id145,"action_maize");

        Marker maize25 = mMap.addMarker(new MarkerOptions()
                .position(new LatLng(24.489440,74.868504))
                .title(getString(R.string.maize_name))
                .snippet(getString(R.string.maize25))
                .rotation((float) 0.5)
                .icon(bitmapDescriptorFromVector(getApplicationContext(),R.drawable.maize)));

        String id146 = maize25.getId();
        markerMap.put(id146,"action_maize");

        Marker maize26 = mMap.addMarker(new MarkerOptions()
                .position(new LatLng(21.772594,74.760215))
                .title(getString(R.string.maize_name))
                .snippet(getString(R.string.maize26))
                .rotation((float) 0.5)
                .icon(bitmapDescriptorFromVector(getApplicationContext(),R.drawable.maize)));

        String id147 = maize26.getId();
        markerMap.put(id147,"action_maize");


        //peas
        Marker peas1 = mMap.addMarker(new MarkerOptions()
                .position(new LatLng( 29.362139,77.743669))
                .title(getString(R.string.peas_name))
                .snippet(getString(R.string.peas1))
                .rotation((float) 0.5)
                .icon(bitmapDescriptorFromVector(getApplicationContext(),R.drawable.peas)));

        String id148 = peas1.getId();
        markerMap.put(id148,"action_peas");

        Marker peas2 = mMap.addMarker(new MarkerOptions()
                .position(new LatLng(26.117770,78.264709 ))
                .title(getString(R.string.peas_name))
                .snippet(getString(R.string.peas2))
                .rotation((float) 0.5)
                .icon(bitmapDescriptorFromVector(getApplicationContext(),R.drawable.peas)));

        String id149 = peas2.getId();
        markerMap.put(id149,"action_peas");

        Marker peas3 = mMap.addMarker(new MarkerOptions()
                .position(new LatLng(30.6026069,77.4426994))
                .title(getString(R.string.peas_name))
                .snippet(getString(R.string.peas3))
                .rotation((float) 0.5)
                .icon(bitmapDescriptorFromVector(getApplicationContext(),R.drawable.peas)));

        String id150 = peas3.getId();
        markerMap.put(id150,"action_peas");

        Marker peas4 = mMap.addMarker(new MarkerOptions()
                .position(new LatLng(23.716425, 77.339118))
                .title(getString(R.string.peas_name))
                .snippet(getString(R.string.peas4))
                .rotation((float) 0.5)
                .icon(bitmapDescriptorFromVector(getApplicationContext(),R.drawable.peas)));

        String id151 = peas4.getId();
        markerMap.put(id151,"action_peas");

        Marker peas5 = mMap.addMarker(new MarkerOptions()
                .position(new LatLng( 26.686999, 77.848496))
                .title(getString(R.string.peas_name))
                .snippet(getString(R.string.peas5))
                .rotation((float) 0.5)
                .icon(bitmapDescriptorFromVector(getApplicationContext(),R.drawable.peas)));

        String id152 = peas5.getId();
        markerMap.put(id152,"action_peas");

        Marker peas6 = mMap.addMarker(new MarkerOptions()
                .position(new LatLng( 20.994239, 75.601993))
                .title(getString(R.string.peas_name))
                .snippet(getString(R.string.peas6))
                .rotation((float) 0.5)
                .icon(bitmapDescriptorFromVector(getApplicationContext(),R.drawable.peas)));

        String id153 = peas6.getId();
        markerMap.put(id153,"action_peas");

        Marker peas7 = mMap.addMarker(new MarkerOptions()
                .position(new LatLng( 21.171878, 79.668042))
                .title(getString(R.string.peas_name))
                .snippet(getString(R.string.peas7))
                .rotation((float) 0.5)
                .icon(bitmapDescriptorFromVector(getApplicationContext(),R.drawable.peas)));

        String id154 = peas7.getId();
        markerMap.put(id154,"action_peas");

        Marker peas8 = mMap.addMarker(new MarkerOptions()
                .position(new LatLng( 30.840660, 75.193077))
                .title(getString(R.string.peas_name))
                .snippet(getString(R.string.peas8))
                .rotation((float) 0.5)
                .icon(bitmapDescriptorFromVector(getApplicationContext(),R.drawable.peas)));

        String id155 = peas8.getId();
        markerMap.put(id155,"action_peas");

        Marker peas9 = mMap.addMarker(new MarkerOptions()
                .position(new LatLng( 32.0132881,75.4249447))
                .title(getString(R.string.peas_name))
                .snippet(getString(R.string.peas9))
                .rotation((float) 0.5)
                .icon(bitmapDescriptorFromVector(getApplicationContext(),R.drawable.peas)));

        String id156 = peas9.getId();
        markerMap.put(id156,"action_peas");

        Marker peas10 = mMap.addMarker(new MarkerOptions()
                .position(new LatLng( 29.2867567,76.2940763))
                .title(getString(R.string.peas_name))
                .snippet(getString(R.string.peas10))
                .rotation((float) 0.5)
                .icon(bitmapDescriptorFromVector(getApplicationContext(),R.drawable.peas)));

        String id157 = peas10.getId();
        markerMap.put(id157,"action_peas");

        Marker peas11 = mMap.addMarker(new MarkerOptions()
                .position(new LatLng( 15.1697235,76.8875897))
                .title(getString(R.string.peas_name))
                .snippet(getString(R.string.peas11))
                .rotation((float) 0.5)
                .icon(bitmapDescriptorFromVector(getApplicationContext(),R.drawable.peas)));

        String id158 = peas11.getId();
        markerMap.put(id158,"action_peas");

        Marker peas12 = mMap.addMarker(new MarkerOptions()
                .position(new LatLng( 24.8976559,85.5229268))
                .title(getString(R.string.peas_name))
                .snippet(getString(R.string.peas12))
                .rotation((float) 0.5)
                .icon(bitmapDescriptorFromVector(getApplicationContext(),R.drawable.peas)));

        String id159 = peas12.getId();
        markerMap.put(id159,"action_peas");


        //pum
        Marker pum1 = mMap.addMarker(new MarkerOptions()
                .position(new LatLng(20.8693735,83.1555593))
                .title(getString(R.string.pumpkin_name))
                .snippet(getString(R.string.pumpkin1))
                .rotation((float) 0.5)
                .icon(bitmapDescriptorFromVector(getApplicationContext(),R.drawable.pum)));

        String id160 = pum1.getId();
        markerMap.put(id160,"action_pum");

        Marker pum2 = mMap.addMarker(new MarkerOptions()
                .position(new LatLng(24.7354209,83.2725943))
                .title(getString(R.string.pumpkin_name))
                .snippet(getString(R.string.pumpkin2))
                .rotation((float) 0.5)
                .icon(bitmapDescriptorFromVector(getApplicationContext(),R.drawable.pum)));

        String id161 = pum2.getId();
        markerMap.put(id161,"action_pum");

        Marker pum3 = mMap.addMarker(new MarkerOptions()
                .position(new LatLng(24.5233069,83.0276753))
                .title(getString(R.string.pumpkin_name))
                .snippet(getString(R.string.pumpkin3))
                .rotation((float) 0.5)
                .icon(bitmapDescriptorFromVector(getApplicationContext(),R.drawable.pum)));

        String id162 = pum3.getId();
        markerMap.put(id162,"action_pum");

        Marker pum4 = mMap.addMarker(new MarkerOptions()
                .position(new LatLng(22.763635, 75.623447))
                .title(getString(R.string.pumpkin_name))
                .snippet(getString(R.string.pumpkin4))
                .rotation((float) 0.5)
                .icon(bitmapDescriptorFromVector(getApplicationContext(),R.drawable.pum)));

        String id163 = pum4.getId();
        markerMap.put(id163,"action_pum");

        Marker pum5 = mMap.addMarker(new MarkerOptions()
                .position(new LatLng(21.250534, 81.447467))
                .title(getString(R.string.pumpkin_name))
                .snippet(getString(R.string.pumpkin5))
                .rotation((float) 0.5)
                .icon(bitmapDescriptorFromVector(getApplicationContext(),R.drawable.pum)));

        String id164 = pum5.getId();
        markerMap.put(id164,"action_pum");

        Marker pum6 = mMap.addMarker(new MarkerOptions()
                .position(new LatLng(13.961249, 75.570615))
                .title(getString(R.string.pumpkin_name))
                .snippet(getString(R.string.pumpkin6))
                .rotation((float) 0.5)
                .icon(bitmapDescriptorFromVector(getApplicationContext(),R.drawable.pum)));

        String id165 = pum6.getId();
        markerMap.put(id165,"action_pum");

        Marker pum7 = mMap.addMarker(new MarkerOptions()
                .position(new LatLng(29.064662, 76.319078))
                .title(getString(R.string.pumpkin_name))
                .snippet(getString(R.string.pumpkin7))
                .rotation((float) 0.5)
                .icon(bitmapDescriptorFromVector(getApplicationContext(),R.drawable.pum)));

        String id166 = pum7.getId();
        markerMap.put(id166,"action_pum");

        Marker pum8 = mMap.addMarker(new MarkerOptions()
                .position(new LatLng(25.5809964,91.901691))
                .title(getString(R.string.pumpkin_name))
                .snippet(getString(R.string.pumpkin8))
                .rotation((float) 0.5)
                .icon(bitmapDescriptorFromVector(getApplicationContext(),R.drawable.pum)));

        String id167 = pum8.getId();
        markerMap.put(id167,"action_pum");

        Marker pum9 = mMap.addMarker(new MarkerOptions()
                .position(new LatLng(29.192370, 73.903310))
                .title(getString(R.string.pumpkin_name))
                .snippet(getString(R.string.pumpkin9))
                .rotation((float) 0.5)
                .icon(bitmapDescriptorFromVector(getApplicationContext(),R.drawable.pum)));

        String id168 = pum9.getId();
        markerMap.put(id168,"action_pum");

        //rice
        Marker rice1 = mMap.addMarker(new MarkerOptions()
                .position(new LatLng( 26.750911,88.403998))
                .title(getString(R.string.rice_name))
                .snippet(getString(R.string.rice1))
                .rotation((float) 0.5)
                .icon(bitmapDescriptorFromVector(getApplicationContext(),R.drawable.rice)));

        String id169 = rice1.getId();
        markerMap.put(id169,"action_rice");

        Marker rice2 = mMap.addMarker(new MarkerOptions()
                .position(new LatLng( 26.959140,83.526210))
                .title(getString(R.string.rice_name))
                .snippet(getString(R.string.rice2))
                .rotation((float) 0.5)
                .icon(bitmapDescriptorFromVector(getApplicationContext(),R.drawable.rice)));

        String id170 = rice2.getId();
        markerMap.put(id170,"action_rice");

        Marker rice3 = mMap.addMarker(new MarkerOptions()
                .position(new LatLng( 20.3098764,73.7769867))
                .title(getString(R.string.rice_name))
                .snippet(getString(R.string.rice3))
                .rotation((float) 0.5)
                .icon(bitmapDescriptorFromVector(getApplicationContext(),R.drawable.rice)));

        String id171 = rice3.getId();
        markerMap.put(id171,"action_rice");

        Marker rice4 = mMap.addMarker(new MarkerOptions()
                .position(new LatLng( 20.9304523,74.8927555))
                .title(getString(R.string.rice_name))
                .snippet(getString(R.string.rice4))
                .rotation((float) 0.5)
                .icon(bitmapDescriptorFromVector(getApplicationContext(),R.drawable.rice)));

        String id172 = rice4.getId();
        markerMap.put(id172,"action_rice");

        Marker rice5 = mMap.addMarker(new MarkerOptions()
                .position(new LatLng( 18.8401849,73.9071292))
                .title(getString(R.string.rice_name))
                .snippet(getString(R.string.rice5))
                .rotation((float) 0.5)
                .icon(bitmapDescriptorFromVector(getApplicationContext(),R.drawable.rice)));

        String id173 = rice5.getId();
        markerMap.put(id173,"action_rice");

        Marker rice6 = mMap.addMarker(new MarkerOptions()
                .position(new LatLng( 23.2571641,72.253918))
                .title(getString(R.string.rice_name))
                .snippet(getString(R.string.rice6))
                .rotation((float) 0.5)
                .icon(bitmapDescriptorFromVector(getApplicationContext(),R.drawable.rice)));

        String id174 = rice6.getId();
        markerMap.put(id174,"action_rice");

        Marker rice7 = mMap.addMarker(new MarkerOptions()
                .position(new LatLng( 22.260527,73.1675671))
                .title(getString(R.string.rice_name))
                .snippet(getString(R.string.rice7))
                .rotation((float) 0.5)
                .icon(bitmapDescriptorFromVector(getApplicationContext(),R.drawable.rice)));

        String id175 = rice7.getId();
        markerMap.put(id175,"action_rice");

        Marker rice8 = mMap.addMarker(new MarkerOptions()
                .position(new LatLng( 20.9680693,72.9150651))
                .title(getString(R.string.rice_name))
                .snippet(getString(R.string.rice8))
                .rotation((float) 0.5)
                .icon(bitmapDescriptorFromVector(getApplicationContext(),R.drawable.rice)));

        String id176 = rice8.getId();
        markerMap.put(id176,"action_rice");

        Marker rice9 = mMap.addMarker(new MarkerOptions()
                .position(new LatLng( 18.0919945,83.6758909))
                .title(getString(R.string.rice_name))
                .snippet(getString(R.string.rice9))
                .rotation((float) 0.5)
                .icon(bitmapDescriptorFromVector(getApplicationContext(),R.drawable.rice)));

        String id177 = rice9.getId();
        markerMap.put(id177,"action_rice");

        Marker rice10 = mMap.addMarker(new MarkerOptions()
                .position(new LatLng( 17.1493272,81.3777578))
                .title(getString(R.string.rice_name))
                .snippet(getString(R.string.rice10))
                .rotation((float) 0.5)
                .icon(bitmapDescriptorFromVector(getApplicationContext(),R.drawable.rice)));

        String id178 = rice10.getId();
        markerMap.put(id178,"action_rice");

        Marker rice11 = mMap.addMarker(new MarkerOptions()
                .position(new LatLng( 16.713958,80.7395574))
                .title(getString(R.string.rice_name))
                .snippet(getString(R.string.rice11))
                .rotation((float) 0.5)
                .icon(bitmapDescriptorFromVector(getApplicationContext(),R.drawable.rice)));

        String id179 = rice11.getId();
        markerMap.put(id179,"action_rice");

        Marker rice12 = mMap.addMarker(new MarkerOptions()
                .position(new LatLng( 16.2765955,80.2709792))
                .title(getString(R.string.rice_name))
                .snippet(getString(R.string.rice12))
                .rotation((float) 0.5)
                .icon(bitmapDescriptorFromVector(getApplicationContext(),R.drawable.rice)));

        String id180 = rice12.getId();
        markerMap.put(id180,"action_rice");

        Marker rice13 = mMap.addMarker(new MarkerOptions()
                .position(new LatLng( 15.1407055,80.0514787))
                .title(getString(R.string.rice_name))
                .snippet(getString(R.string.rice13))
                .rotation((float) 0.5)
                .icon(bitmapDescriptorFromVector(getApplicationContext(),R.drawable.rice)));

        String id181 = rice13.getId();
        markerMap.put(id181,"action_rice");

        Marker rice14 = mMap.addMarker(new MarkerOptions()
                .position(new LatLng( 16.0106918,80.0374893))
                .title(getString(R.string.rice_name))
                .snippet(getString(R.string.rice14))
                .rotation((float) 0.5)
                .icon(bitmapDescriptorFromVector(getApplicationContext(),R.drawable.rice)));

        String id182 = rice14.getId();
        markerMap.put(id182,"action_rice");

        Marker rice15 = mMap.addMarker(new MarkerOptions()
                .position(new LatLng( 15.1967855,78.0369791))
                .title(getString(R.string.rice_name))
                .snippet(getString(R.string.rice15))
                .rotation((float) 0.5)
                .icon(bitmapDescriptorFromVector(getApplicationContext(),R.drawable.rice)));

        String id183 = rice15.getId();
        markerMap.put(id183,"action_rice");

        Marker rice16 = mMap.addMarker(new MarkerOptions()
                .position(new LatLng( 14.719659,77.5920217))
                .title(getString(R.string.rice_name))
                .snippet(getString(R.string.rice16))
                .rotation((float) 0.5)
                .icon(bitmapDescriptorFromVector(getApplicationContext(),R.drawable.rice)));

        String id184 = rice16.getId();
        markerMap.put(id184,"action_rice");

        Marker rice17 = mMap.addMarker(new MarkerOptions()
                .position(new LatLng( 15.0433221,78.3341844))
                .title(getString(R.string.rice_name))
                .snippet(getString(R.string.rice17))
                .rotation((float) 0.5)
                .icon(bitmapDescriptorFromVector(getApplicationContext(),R.drawable.rice)));

        String id185 = rice17.getId();
        markerMap.put(id185,"action_rice");

        Marker rice18 = mMap.addMarker(new MarkerOptions()
                .position(new LatLng( 14.4937192,79.6553043))
                .title(getString(R.string.rice_name))
                .snippet(getString(R.string.rice18))
                .rotation((float) 0.5)
                .icon(bitmapDescriptorFromVector(getApplicationContext(),R.drawable.rice)));

        String id186 = rice18.getId();
        markerMap.put(id186,"action_rice");

        Marker rice19 = mMap.addMarker(new MarkerOptions()
                .position(new LatLng( 26.2603336,92.3310749))
                .title(getString(R.string.rice_name))
                .snippet(getString(R.string.rice19))
                .rotation((float) 0.5)
                .icon(bitmapDescriptorFromVector(getApplicationContext(),R.drawable.rice)));

        String id187 = rice19.getId();
        markerMap.put(id187,"action_rice");

        Marker rice20 = mMap.addMarker(new MarkerOptions()
                .position(new LatLng( 25.8774375,92.8429856))
                .title(getString(R.string.rice_name))
                .snippet(getString(R.string.rice20))
                .rotation((float) 0.5)
                .icon(bitmapDescriptorFromVector(getApplicationContext(),R.drawable.rice)));

        String id188 = rice20.getId();
        markerMap.put(id188,"action_rice");

        Marker rice21 = mMap.addMarker(new MarkerOptions()
                .position(new LatLng( 26.8569286,94.1756956))
                .title(getString(R.string.rice_name))
                .snippet(getString(R.string.rice21))
                .rotation((float) 0.5)
                .icon(bitmapDescriptorFromVector(getApplicationContext(),R.drawable.rice)));

        String id189 = rice21.getId();
        markerMap.put(id189,"action_rice");

        Marker rice22 = mMap.addMarker(new MarkerOptions()
                .position(new LatLng( 27.5103533,95.3465964))
                .title(getString(R.string.rice_name))
                .snippet(getString(R.string.rice22))
                .rotation((float) 0.5)
                .icon(bitmapDescriptorFromVector(getApplicationContext(),R.drawable.rice)));

        String id190 = rice22.getId();
        markerMap.put(id190,"action_rice");

        Marker rice23 = mMap.addMarker(new MarkerOptions()
                .position(new LatLng( 26.1361328,87.4304357))
                .title(getString(R.string.rice_name))
                .snippet(getString(R.string.rice23))
                .rotation((float) 0.5)
                .icon(bitmapDescriptorFromVector(getApplicationContext(),R.drawable.rice)));

        String id191 = rice23.getId();
        markerMap.put(id191,"action_rice");

        Marker rice24 = mMap.addMarker(new MarkerOptions()
                .position(new LatLng( 25.2148301,86.9523786))
                .title(getString(R.string.rice_name))
                .snippet(getString(R.string.rice24))
                .rotation((float) 0.5)
                .icon(bitmapDescriptorFromVector(getApplicationContext(),R.drawable.rice)));

        String id192 = rice24.getId();
        markerMap.put(id192,"action_rice");

        Marker rice25 = mMap.addMarker(new MarkerOptions()
                .position(new LatLng( 26.1296856,86.5977723))
                .title(getString(R.string.rice_name))
                .snippet(getString(R.string.rice25))
                .rotation((float) 0.5)
                .icon(bitmapDescriptorFromVector(getApplicationContext(),R.drawable.rice)));

        String id193 = rice25.getId();
        markerMap.put(id193,"action_rice");

        Marker rice26 = mMap.addMarker(new MarkerOptions()
                .position(new LatLng( 26.3479631,86.0602104))
                .title(getString(R.string.rice_name))
                .snippet(getString(R.string.rice26))
                .rotation((float) 0.5)
                .icon(bitmapDescriptorFromVector(getApplicationContext(),R.drawable.rice)));

        String id194 = rice26.getId();
        markerMap.put(id194,"action_rice");

        Marker rice27 = mMap.addMarker(new MarkerOptions()
                .position(new LatLng( 24.7650045,85.0086464))
                .title(getString(R.string.rice_name))
                .snippet(getString(R.string.rice27))
                .rotation((float) 0.5)
                .icon(bitmapDescriptorFromVector(getApplicationContext(),R.drawable.rice)));

        String id195 = rice27.getId();
        markerMap.put(id195,"action_rice");

        Marker rice28 = mMap.addMarker(new MarkerOptions()
                .position(new LatLng( 27.1589469,84.3157251))
                .title(getString(R.string.rice_name))
                .snippet(getString(R.string.rice28))
                .rotation((float) 0.5)
                .icon(bitmapDescriptorFromVector(getApplicationContext(),R.drawable.rice)));

        String id196 = rice28.getId();
        markerMap.put(id196,"action_rice");

        Marker rice29 = mMap.addMarker(new MarkerOptions()
                .position(new LatLng( 31.0769271,77.1744928))
                .title(getString(R.string.rice_name))
                .snippet(getString(R.string.rice29))
                .rotation((float) 0.5)
                .icon(bitmapDescriptorFromVector(getApplicationContext(),R.drawable.rice)));

        String id197 = rice29.getId();
        markerMap.put(id197,"action_rice");

        Marker rice30 = mMap.addMarker(new MarkerOptions()
                .position(new LatLng( 33.3139516,75.7564761))
                .title(getString(R.string.rice_name))
                .snippet(getString(R.string.rice30))
                .rotation((float) 0.5)
                .icon(bitmapDescriptorFromVector(getApplicationContext(),R.drawable.rice)));

        String id198 = rice30.getId();
        markerMap.put(id198,"action_rice");

        Marker rice31 = mMap.addMarker(new MarkerOptions()
                .position(new LatLng( 32.4831134,75.3273046))
                .title(getString(R.string.rice_name))
                .snippet(getString(R.string.rice31))
                .rotation((float) 0.5)
                .icon(bitmapDescriptorFromVector(getApplicationContext(),R.drawable.rice)));

        String id199 = rice31.getId();
        markerMap.put(id199,"action_rice");

        Marker rice32 = mMap.addMarker(new MarkerOptions()
                .position(new LatLng( 32.632623,74.9159809))
                .title(getString(R.string.rice_name))
                .snippet(getString(R.string.rice32))
                .rotation((float) 0.5)
                .icon(bitmapDescriptorFromVector(getApplicationContext(),R.drawable.rice)));

        String id200 = rice32.getId();
        markerMap.put(id200,"action_rice");

        Marker rice33 = mMap.addMarker(new MarkerOptions()
                .position(new LatLng( 15.4766302,75.9749233))
                .title(getString(R.string.rice_name))
                .snippet(getString(R.string.rice33))
                .rotation((float) 0.5)
                .icon(bitmapDescriptorFromVector(getApplicationContext(),R.drawable.rice)));

        String id201 = rice33.getId();
        markerMap.put(id201,"action_rice");

        Marker rice34 = mMap.addMarker(new MarkerOptions()
                .position(new LatLng( 14.9200953,74.3455377))
                .title(getString(R.string.rice_name))
                .snippet(getString(R.string.rice34))
                .rotation((float) 0.5)
                .icon(bitmapDescriptorFromVector(getApplicationContext(),R.drawable.rice)));

        String id202 = rice34.getId();
        markerMap.put(id202,"action_rice");

        Marker rice35 = mMap.addMarker(new MarkerOptions()
                .position(new LatLng( 13.2977661,74.9722905))
                .title(getString(R.string.rice_name))
                .snippet(getString(R.string.rice35))
                .rotation((float) 0.5)
                .icon(bitmapDescriptorFromVector(getApplicationContext(),R.drawable.rice)));

        String id203 = rice35.getId();
        markerMap.put(id203,"action_rice");

        Marker rice36 = mMap.addMarker(new MarkerOptions()
                .position(new LatLng( 20.489870,85.821550))
                .title(getString(R.string.rice_name))
                .snippet(getString(R.string.rice36))
                .rotation((float) 0.5)
                .icon(bitmapDescriptorFromVector(getApplicationContext(),R.drawable.rice)));

        String id204 = rice36.getId();
        markerMap.put(id204,"action_rice");

        Marker rice37 = mMap.addMarker(new MarkerOptions()
                .position(new LatLng( 30.985726,76.530645))
                .title(getString(R.string.rice_name))
                .snippet(getString(R.string.rice37))
                .rotation((float) 0.5)
                .icon(bitmapDescriptorFromVector(getApplicationContext(),R.drawable.rice)));

        String id205 = rice37.getId();
        markerMap.put(id205,"action_rice");

        Marker rice38 = mMap.addMarker(new MarkerOptions()
                .position(new LatLng( 31.590260,74.914905))
                .title(getString(R.string.rice_name))
                .snippet(getString(R.string.rice38))
                .rotation((float) 0.5)
                .icon(bitmapDescriptorFromVector(getApplicationContext(),R.drawable.rice)));

        String id206 = rice38.getId();
        markerMap.put(id206,"action_rice");

        Marker rice39 = mMap.addMarker(new MarkerOptions()
                .position(new LatLng( 31.355793,75.381542))
                .title(getString(R.string.rice_name))
                .snippet(getString(R.string.rice39))
                .rotation((float) 0.5)
                .icon(bitmapDescriptorFromVector(getApplicationContext(),R.drawable.rice)));

        String id207 = rice39.getId();
        markerMap.put(id207,"action_rice");

        Marker rice40 = mMap.addMarker(new MarkerOptions()
                .position(new LatLng( 32.255119,75.636116))
                .title(getString(R.string.rice_name))
                .snippet(getString(R.string.rice40))
                .rotation((float) 0.5)
                .icon(bitmapDescriptorFromVector(getApplicationContext(),R.drawable.rice)));

        String id208 = rice40.getId();
        markerMap.put(id208,"action_rice");

        Marker rice41 = mMap.addMarker(new MarkerOptions()
                .position(new LatLng( 26.059709,76.355857))
                .title(getString(R.string.rice_name))
                .snippet(getString(R.string.rice41))
                .rotation((float) 0.5)
                .icon(bitmapDescriptorFromVector(getApplicationContext(),R.drawable.rice)));

        String id209 = rice41.getId();
        markerMap.put(id209,"action_rice");

        Marker rice42 = mMap.addMarker(new MarkerOptions()
                .position(new LatLng( 29.9642896,77.5749563))
                .title(getString(R.string.rice_name))
                .snippet(getString(R.string.rice42))
                .rotation((float) 0.5)
                .icon(bitmapDescriptorFromVector(getApplicationContext(),R.drawable.rice)));

        String id210 = rice42.getId();
        markerMap.put(id210,"action_rice");

        Marker rice43 = mMap.addMarker(new MarkerOptions()
                .position(new LatLng( 28.700466,77.498934))
                .title(getString(R.string.rice_name))
                .snippet(getString(R.string.rice43))
                .rotation((float) 0.5)
                .icon(bitmapDescriptorFromVector(getApplicationContext(),R.drawable.rice)));

        String id211 = rice43.getId();
        markerMap.put(id211,"action_rice");

        Marker rice44 = mMap.addMarker(new MarkerOptions()
                .position(new LatLng( 27.211849,77.902565))
                .title(getString(R.string.rice_name))
                .snippet(getString(R.string.rice44))
                .rotation((float) 0.5)
                .icon(bitmapDescriptorFromVector(getApplicationContext(),R.drawable.rice)));

        String id212 = rice44.getId();
        markerMap.put(id212,"action_rice");

        Marker rice45 = mMap.addMarker(new MarkerOptions()
                .position(new LatLng( 27.677519,78.142137))
                .title(getString(R.string.rice_name))
                .snippet(getString(R.string.rice45))
                .rotation((float) 0.5)
                .icon(bitmapDescriptorFromVector(getApplicationContext(),R.drawable.rice)));

        String id213 = rice45.getId();
        markerMap.put(id213,"action_rice");

        Marker rice46 = mMap.addMarker(new MarkerOptions()
                .position(new LatLng( 27.579854,81.615557))
                .title(getString(R.string.rice_name))
                .snippet(getString(R.string.rice46))
                .rotation((float) 0.5)
                .icon(bitmapDescriptorFromVector(getApplicationContext(),R.drawable.rice)));

        String id214 = rice46.getId();
        markerMap.put(id214,"action_rice");

//sesame
        Marker sesame1=mMap.addMarker(new MarkerOptions()
                .position(new LatLng(21.626913,70.720903))
                .title(getString(R.string.sesame_name))
                .snippet(getString(R.string.sesame1))
                .rotation((float) 0.5)
                .icon(bitmapDescriptorFromVector(getApplicationContext(),R.drawable.sesame)));

        String id215 = sesame1.getId();
        markerMap.put(id215,"action_sesame");

        Marker sesame2=mMap.addMarker(new MarkerOptions()
                .position(new LatLng( 22.938650,87.304805))
                .title(getString(R.string.sesame_name))
                .snippet(getString(R.string.sesame2))
                .rotation((float) 0.5)
                .icon(bitmapDescriptorFromVector(getApplicationContext(),R.drawable.sesame)));

        String id216 = sesame2.getId();
        markerMap.put(id216,"action_sesame");

        Marker sesame3 =mMap.addMarker(new MarkerOptions()
                .position(new LatLng( 23.5547822,72.6811814))
                .title(getString(R.string.sesame_name))
                .snippet(getString(R.string.sesame3))
                .rotation((float) 0.5)
                .icon(bitmapDescriptorFromVector(getApplicationContext(),R.drawable.sesame)));

        String id217 = sesame3.getId();
        markerMap.put(id217,"action_sesame");

        Marker sesame4 =mMap.addMarker(new MarkerOptions()
                .position(new LatLng( 26.1363461,93.1133449))
                .title(getString(R.string.sesame_name))
                .snippet(getString(R.string.sesame4))
                .rotation((float) 0.5)
                .icon(bitmapDescriptorFromVector(getApplicationContext(),R.drawable.sesame)));

        String id218 = sesame4.getId();
        markerMap.put(id218,"action_sesame");

        Marker sesame5 =mMap.addMarker(new MarkerOptions()
                .position(new LatLng( 25.2940865,93.1449919))
                .title(getString(R.string.sesame_name))
                .snippet(getString(R.string.sesame5))
                .rotation((float) 0.5)
                .icon(bitmapDescriptorFromVector(getApplicationContext(),R.drawable.sesame)));

        String id219 = sesame5.getId();
        markerMap.put(id219,"action_sesame");

        Marker sesame6 =mMap.addMarker(new MarkerOptions()
                .position(new LatLng( 24.628284,83.9225635))
                .title(getString(R.string.sesame_name))
                .snippet(getString(R.string.sesame6))
                .rotation((float) 0.5)
                .icon(bitmapDescriptorFromVector(getApplicationContext(),R.drawable.sesame)));

        String id220 = sesame6.getId();
        markerMap.put(id220,"action_sesame");

        Marker sesame7 =mMap.addMarker(new MarkerOptions()
                .position(new LatLng( 12.9928119,76.116288))
                .title(getString(R.string.sesame_name))
                .snippet(getString(R.string.sesame7))
                .rotation((float) 0.5)
                .icon(bitmapDescriptorFromVector(getApplicationContext(),R.drawable.sesame)));

        String id221 = sesame7.getId();
        markerMap.put(id221,"action_sesame");

        Marker sesame8 =mMap.addMarker(new MarkerOptions()
                .position(new LatLng( 22.042923,87.746943))
                .title(getString(R.string.sesame_name))
                .snippet(getString(R.string.sesame8))
                .rotation((float) 0.5)
                .icon(bitmapDescriptorFromVector(getApplicationContext(),R.drawable.sesame)));

        String id222 = sesame8.getId();
        markerMap.put(id222,"action_sesame");

        Marker sesame9 =mMap.addMarker(new MarkerOptions()
                .position(new LatLng( 25.260411,80.893047))
                .title(getString(R.string.sesame_name))
                .snippet(getString(R.string.sesame9))
                .rotation((float) 0.5)
                .icon(bitmapDescriptorFromVector(getApplicationContext(),R.drawable.sesame)));

        String id223 = sesame9.getId();
        markerMap.put(id223,"action_sesame");

        Marker sesame10 =mMap.addMarker(new MarkerOptions()
                .position(new LatLng( 26.512019,77.881133))
                .title(getString(R.string.sesame_name))
                .snippet(getString(R.string.sesame10))
                .rotation((float) 0.5)
                .icon(bitmapDescriptorFromVector(getApplicationContext(),R.drawable.sesame)));

        String id224 = sesame10.getId();
        markerMap.put(id224,"action_sesame");

        //sorghum
        Marker sor1=mMap.addMarker(new MarkerOptions()
                .position(new LatLng(18.986876,75.770952))
                .title(getString(R.string.sorghum_name))
                .snippet(getString(R.string.sorghum1))
                .rotation((float) 0.5)
                .icon(bitmapDescriptorFromVector(getApplicationContext(),R.drawable.sor)));

        String id225 = sor1.getId();
        markerMap.put(id225,"action_sor");

        Marker sor2=mMap.addMarker(new MarkerOptions()
                .position(new LatLng( 14.715736,75.725490))
                .title(getString(R.string.sorghum_name))
                .snippet(getString(R.string.sorghum2))
                .rotation((float) 0.5)
                .icon(bitmapDescriptorFromVector(getApplicationContext(),R.drawable.sor)));

        String id226 = sor2.getId();
        markerMap.put(id226,"action_sor");

        Marker sor3=mMap.addMarker(new MarkerOptions()
                .position(new LatLng( 21.2669565,74.7808977))
                .title(getString(R.string.sorghum_name))
                .snippet(getString(R.string.sorghum3))
                .rotation((float) 0.5)
                .icon(bitmapDescriptorFromVector(getApplicationContext(),R.drawable.sor)));

        String id227 = sor3.getId();
        markerMap.put(id227,"action_sor");

        Marker sor4=mMap.addMarker(new MarkerOptions()
                .position(new LatLng(21.3932543,74.2375777))
                .title(getString(R.string.sorghum_name))
                .snippet(getString(R.string.sorghum4))
                .rotation((float) 0.5)
                .icon(bitmapDescriptorFromVector(getApplicationContext(),R.drawable.sor)));

        String id228 = sor4.getId();
        markerMap.put(id228,"action_sor");

        Marker sor5 =mMap.addMarker(new MarkerOptions()
                .position(new LatLng(18.4475147,73.8434084))
                .title(getString(R.string.sorghum_name))
                .snippet(getString(R.string.sorghum5))
                .rotation((float) 0.5)
                .icon(bitmapDescriptorFromVector(getApplicationContext(),R.drawable.sor)));

        String id229 = sor5.getId();
        markerMap.put(id229,"action_sor");

        Marker sor6 =mMap.addMarker(new MarkerOptions()
                .position(new LatLng(22.9832719,72.9809737))
                .title(getString(R.string.sorghum_name))
                .snippet(getString(R.string.sorghum6))
                .rotation((float) 0.5)
                .icon(bitmapDescriptorFromVector(getApplicationContext(),R.drawable.sor)));

        String id230 = sor6.getId();
        markerMap.put(id230,"action_sor");

        Marker sor7 =mMap.addMarker(new MarkerOptions()
                .position(new LatLng(22.5462744,73.1193225))
                .title(getString(R.string.sorghum_name))
                .snippet(getString(R.string.sorghum7))
                .rotation((float) 0.5)
                .icon(bitmapDescriptorFromVector(getApplicationContext(),R.drawable.sor)));

        String id231 = sor7.getId();
        markerMap.put(id231,"action_sor");

        Marker sor8 =mMap.addMarker(new MarkerOptions()
                .position(new LatLng(16.642075,80.5673684))
                .title(getString(R.string.sorghum_name))
                .snippet(getString(R.string.sorghum8))
                .rotation((float) 0.5)
                .icon(bitmapDescriptorFromVector(getApplicationContext(),R.drawable.sor)));

        String id232 = sor8.getId();
        markerMap.put(id232,"action_sor");

        Marker sor9 =mMap.addMarker(new MarkerOptions()
                .position(new LatLng(15.8984676,74.814463))
                .title(getString(R.string.sorghum_name))
                .snippet(getString(R.string.sorghum9))
                .rotation((float) 0.5)
                .icon(bitmapDescriptorFromVector(getApplicationContext(),R.drawable.sor)));

        String id233 = sor9.getId();
        markerMap.put(id233,"action_sor");

        Marker sor10 =mMap.addMarker(new MarkerOptions()
                .position(new LatLng(16.8135701,75.7024928))
                .title(getString(R.string.sorghum_name))
                .snippet(getString(R.string.sorghum10))
                .rotation((float) 0.5)
                .icon(bitmapDescriptorFromVector(getApplicationContext(),R.drawable.sor)));

        String id234 = sor10.getId();
        markerMap.put(id234,"action_sor");

        Marker sor11 =mMap.addMarker(new MarkerOptions()
                .position(new LatLng(20.133158,85.983432))
                .title(getString(R.string.sorghum_name))
                .snippet(getString(R.string.sorghum11))
                .rotation((float) 0.5)
                .icon(bitmapDescriptorFromVector(getApplicationContext(),R.drawable.sor)));

        String id235 = sor11.getId();
        markerMap.put(id235,"action_sor");

        Marker sor12 =mMap.addMarker(new MarkerOptions()
                .position(new LatLng(18.809900,82.720923))
                .title(getString(R.string.sorghum_name))
                .snippet(getString(R.string.sorghum12))
                .rotation((float) 0.5)
                .icon(bitmapDescriptorFromVector(getApplicationContext(),R.drawable.sor)));

        String id236 = sor12.getId();
        markerMap.put(id236,"action_sor");

        Marker sor13 =mMap.addMarker(new MarkerOptions()
                .position(new LatLng(24.578791,76.164549))
                .title(getString(R.string.sorghum_name))
                .snippet(getString(R.string.sorghum13))
                .rotation((float) 0.5)
                .icon(bitmapDescriptorFromVector(getApplicationContext(),R.drawable.sor)));

        String id237 = sor13.getId();
        markerMap.put(id237,"action_sor");

        Marker sor14 =mMap.addMarker(new MarkerOptions()
                .position(new LatLng(25.2133838,75.8365463))
                .title(getString(R.string.sorghum_name))
                .snippet(getString(R.string.sorghum14))
                .rotation((float) 0.5)
                .icon(bitmapDescriptorFromVector(getApplicationContext(),R.drawable.sor)));

        String id238 = sor14.getId();
        markerMap.put(id238,"action_sor");

        Marker sor15 =mMap.addMarker(new MarkerOptions()
                .position(new LatLng(24.092389,75.075389))
                .title(getString(R.string.sorghum_name))
                .snippet(getString(R.string.sorghum15))
                .rotation((float) 0.5)
                .icon(bitmapDescriptorFromVector(getApplicationContext(),R.drawable.sor)));

        String id239 = sor15.getId();
        markerMap.put(id239,"action_sor");

        Marker sor16 =mMap.addMarker(new MarkerOptions()
                .position(new LatLng(23.039650,79.211833))
                .title(getString(R.string.sorghum_name))
                .snippet(getString(R.string.sorghum16))
                .rotation((float) 0.5)
                .icon(bitmapDescriptorFromVector(getApplicationContext(),R.drawable.sor)));

        String id240 = sor16.getId();
        markerMap.put(id240,"action_sor");



        //soybean
        Marker soybean1=mMap.addMarker(new MarkerOptions()
                .position(new LatLng(23.151412,77.453936))
                .title(getString(R.string.soybean_name))
                .snippet(getString(R.string.soybean1))
                .rotation((float) 0.5)
                .icon(bitmapDescriptorFromVector(getApplicationContext(),R.drawable.soyabean)));

        String id241 = soybean1.getId();
        markerMap.put(id241,"action_soy");

        Marker soybean2=mMap.addMarker(new MarkerOptions()
                .position(new LatLng( 21.252773,79.184813))
                .title(getString(R.string.soybean_name))
                .snippet(getString(R.string.soybean2))
                .rotation((float) 0.5)
                .icon(bitmapDescriptorFromVector(getApplicationContext(),R.drawable.soyabean)));

        String id242 = soybean2.getId();
        markerMap.put(id242,"action_soy");

        Marker soybean3=mMap.addMarker(new MarkerOptions()
                .position(new LatLng( 26.513293,77.030616))
                .title(getString(R.string.soybean_name))
                .snippet(getString(R.string.soybean3))
                .rotation((float) 0.5)
                .icon(bitmapDescriptorFromVector(getApplicationContext(),R.drawable.soyabean)));

        String id243 = soybean3.getId();
        markerMap.put(id243,"action_soy");

        Marker soybean4=mMap.addMarker(new MarkerOptions()
                .position(new LatLng( 27.201986,77.454777))
                .title(getString(R.string.soybean_name))
                .snippet(getString(R.string.soybean4))
                .rotation((float) 0.5)
                .icon(bitmapDescriptorFromVector(getApplicationContext(),R.drawable.soyabean)));

        String id244 = soybean4.getId();
        markerMap.put(id244,"action_soy");

        Marker soybean5=mMap.addMarker(new MarkerOptions()
                .position(new LatLng( 23.913736,91.358568))
                .title(getString(R.string.soybean_name))
                .snippet(getString(R.string.soybean5))
                .rotation((float) 0.5)
                .icon(bitmapDescriptorFromVector(getApplicationContext(),R.drawable.soyabean)));

        String id245 = soybean5.getId();
        markerMap.put(id245,"action_soy");

        Marker soybean6=mMap.addMarker(new MarkerOptions()
                .position(new LatLng( 23.431914,91.7468784))
                .title(getString(R.string.soybean_name))
                .snippet(getString(R.string.soybean6))
                .rotation((float) 0.5)
                .icon(bitmapDescriptorFromVector(getApplicationContext(),R.drawable.soyabean)));

        String id246 = soybean6.getId();
        markerMap.put(id246,"action_soy");

        Marker soybean7=mMap.addMarker(new MarkerOptions()
                .position(new LatLng( 25.560317,77.643937))
                .title(getString(R.string.soybean_name))
                .snippet(getString(R.string.soybean7))
                .rotation((float) 0.5)
                .icon(bitmapDescriptorFromVector(getApplicationContext(),R.drawable.soyabean)));

        String id247 = soybean7.getId();
        markerMap.put(id247,"action_soy");

        Marker soybean8=mMap.addMarker(new MarkerOptions()
                .position(new LatLng( 22.303269,78.963753))
                .title(getString(R.string.soybean_name))
                .snippet(getString(R.string.soybean8))
                .rotation((float) 0.5)
                .icon(bitmapDescriptorFromVector(getApplicationContext(),R.drawable.soyabean)));

        String id248 = soybean8.getId();
        markerMap.put(id248,"action_soy");

        Marker soybean9=mMap.addMarker(new MarkerOptions()
                .position(new LatLng( 24.013261,76.722729))
                .title(getString(R.string.soybean_name))
                .snippet(getString(R.string.soybean9))
                .rotation((float) 0.5)
                .icon(bitmapDescriptorFromVector(getApplicationContext(),R.drawable.soyabean)));

        String id249 = soybean9.getId();
        markerMap.put(id249,"action_soy");

        Marker soybean10 = mMap.addMarker(new MarkerOptions()
                .position(new LatLng(22.6775847,73.9507212))
                .title(getString(R.string.soybean_name))
                .snippet(getString(R.string.soybean10))
                .rotation((float) 0.5)
                .icon(bitmapDescriptorFromVector(getApplicationContext(),R.drawable.soyabean)));

        String id250 = soybean10.getId();
        markerMap.put(id250,"action_soy");

        //sugarcane

        Marker sugarcane1=mMap.addMarker(new MarkerOptions()
                .position(new LatLng( 19.086550,74.549908))
                .title(getString(R.string.sugarcane_name))
                .snippet(getString(R.string.sugarcane1))
                .rotation((float) 0.5)
                .icon(bitmapDescriptorFromVector(getApplicationContext(),R.drawable.sugarcane1)));

        String id251 = sugarcane1.getId();
        markerMap.put(id251,"action_sugar");

        Marker sugarcane2 = mMap.addMarker(new MarkerOptions()
                .position(new LatLng( 21.0072979,77.7238642))
                .title(getString(R.string.sugarcane_name))
                .snippet(getString(R.string.sugarcane2))
                .rotation((float) 0.5)
                .icon(bitmapDescriptorFromVector(getApplicationContext(),R.drawable.sugarcane1)));

        String id252 = sugarcane2.getId();
        markerMap.put(id252,"action_sugar");

        Marker sugarcane3 = mMap.addMarker(new MarkerOptions()
                .position(new LatLng( 18.3009064,83.8757434))
                .title(getString(R.string.sugarcane_name))
                .snippet(getString(R.string.sugarcane3))
                .rotation((float) 0.5)
                .icon(bitmapDescriptorFromVector(getApplicationContext(),R.drawable.sugarcane1)));

        String id253 = sugarcane3.getId();
        markerMap.put(id253,"action_sugar");

        Marker sugarcane4 = mMap.addMarker(new MarkerOptions()
                .position(new LatLng( 18.0778517,83.443264))
                .title(getString(R.string.sugarcane_name))
                .snippet(getString(R.string.sugarcane4))
                .rotation((float) 0.5)
                .icon(bitmapDescriptorFromVector(getApplicationContext(),R.drawable.sugarcane1)));

        String id254 = sugarcane4.getId();
        markerMap.put(id254,"action_sugar");

        Marker sugarcane5 = mMap.addMarker(new MarkerOptions()
                .position(new LatLng( 17.6058541,83.1345935))
                .title(getString(R.string.sugarcane_name))
                .snippet(getString(R.string.sugarcane5))
                .rotation((float) 0.5)
                .icon(bitmapDescriptorFromVector(getApplicationContext(),R.drawable.sugarcane1)));

        String id255 = sugarcane5.getId();
        markerMap.put(id255,"action_sugar");

        Marker sugarcane6 = mMap.addMarker(new MarkerOptions()
                .position(new LatLng( 16.3963465,81.3749315))
                .title(getString(R.string.sugarcane_name))
                .snippet(getString(R.string.sugarcane6))
                .rotation((float) 0.5)
                .icon(bitmapDescriptorFromVector(getApplicationContext(),R.drawable.sugarcane1)));

        String id256 = sugarcane6.getId();
        markerMap.put(id256,"action_sugar");

        Marker sugarcane7 = mMap.addMarker(new MarkerOptions()
                .position(new LatLng( 16.031978,81.0479664))
                .title(getString(R.string.sugarcane_name))
                .snippet(getString(R.string.sugarcane7))
                .rotation((float) 0.5)
                .icon(bitmapDescriptorFromVector(getApplicationContext(),R.drawable.sugarcane1)));

        String id257 = sugarcane7.getId();
        markerMap.put(id257,"action_sugar");

        Marker sugarcane8 = mMap.addMarker(new MarkerOptions()
                .position(new LatLng( 13.2237776,79.0789327))
                .title(getString(R.string.sugarcane_name))
                .snippet(getString(R.string.sugarcane8))
                .rotation((float) 0.5)
                .icon(bitmapDescriptorFromVector(getApplicationContext(),R.drawable.sugarcane1)));

        String id258 = sugarcane8.getId();
        markerMap.put(id258,"action_sugar");

        Marker sugarcane9 = mMap.addMarker(new MarkerOptions()
                .position(new LatLng( 26.8103237,92.8351045))
                .title(getString(R.string.sugarcane_name))
                .snippet(getString(R.string.sugarcane9))
                .rotation((float) 0.5)
                .icon(bitmapDescriptorFromVector(getApplicationContext(),R.drawable.sugarcane1)));

        String id259 = sugarcane9.getId();
        markerMap.put(id259,"action_sugar");

        Marker sugarcane10 = mMap.addMarker(new MarkerOptions()
                .position(new LatLng( 24.8591778,92.3527871))
                .title(getString(R.string.sugarcane_name))
                .snippet(getString(R.string.sugarcane10))
                .rotation((float) 0.5)
                .icon(bitmapDescriptorFromVector(getApplicationContext(),R.drawable.sugarcane1)));

        String id260 = sugarcane10.getId();
        markerMap.put(id260,"action_sugar");

        Marker sugarcane11 = mMap.addMarker(new MarkerOptions()
                .position(new LatLng( 27.0171538,93.7614001))
                .title(getString(R.string.sugarcane_name))
                .snippet(getString(R.string.sugarcane11))
                .rotation((float) 0.5)
                .icon(bitmapDescriptorFromVector(getApplicationContext(),R.drawable.sugarcane1)));

        String id261 = sugarcane11.getId();
        markerMap.put(id261,"action_sugar");

        Marker sugarcane12 = mMap.addMarker(new MarkerOptions()
                .position(new LatLng( 26.177542,86.3724718))
                .title(getString(R.string.sugarcane_name))
                .snippet(getString(R.string.sugarcane12))
                .rotation((float) 0.5)
                .icon(bitmapDescriptorFromVector(getApplicationContext(),R.drawable.sugarcane1)));

        String id262 = sugarcane12.getId();
        markerMap.put(id262,"action_sugar");

        Marker sugarcane13 = mMap.addMarker(new MarkerOptions()
                .position(new LatLng( 31.4752376,76.2646685))
                .title(getString(R.string.sugarcane_name))
                .snippet(getString(R.string.sugarcane13))
                .rotation((float) 0.5)
                .icon(bitmapDescriptorFromVector(getApplicationContext(),R.drawable.sugarcane1)));

        String id263 = sugarcane13.getId();
        markerMap.put(id263,"action_sugar");

        Marker sugarcane14 = mMap.addMarker(new MarkerOptions()
                .position(new LatLng( 33.3191147,75.7493952))
                .title(getString(R.string.sugarcane_name))
                .snippet(getString(R.string.sugarcane14))
                .rotation((float) 0.5)
                .icon(bitmapDescriptorFromVector(getApplicationContext(),R.drawable.sugarcane1)));

        String id264 = sugarcane14.getId();
        markerMap.put(id264,"action_sugar");

        Marker sugarcane15 = mMap.addMarker(new MarkerOptions()
                .position(new LatLng( 10.5148576,76.1602565))
                .title(getString(R.string.sugarcane_name))
                .snippet(getString(R.string.sugarcane15))
                .rotation((float) 0.5)
                .icon(bitmapDescriptorFromVector(getApplicationContext(),R.drawable.sugarcane1)));

        String id265 = sugarcane15.getId();
        markerMap.put(id265,"action_sugar");

        Marker sugarcane16 = mMap.addMarker(new MarkerOptions()
                .position(new LatLng( 20.494802,86.444659))
                .title(getString(R.string.sugarcane_name))
                .snippet(getString(R.string.sugarcane16))
                .rotation((float) 0.5)
                .icon(bitmapDescriptorFromVector(getApplicationContext(),R.drawable.sugarcane1)));

        String id266 = sugarcane16.getId();
        markerMap.put(id266,"action_sugar");

        Marker sugarcane17 = mMap.addMarker(new MarkerOptions()
                .position(new LatLng( 22.9049027,88.3581044))
                .title(getString(R.string.sugarcane_name))
                .snippet(getString(R.string.sugarcane17))
                .rotation((float) 0.5)
                .icon(bitmapDescriptorFromVector(getApplicationContext(),R.drawable.sugarcane1)));

        String id267 = sugarcane17.getId();
        markerMap.put(id267,"action_sugar");

        //tea
        Marker tea1 = mMap.addMarker(new MarkerOptions()
                .position(new LatLng(26.384473,92.684993))
                .title(getString(R.string.tea_name))
                .snippet(getString(R.string.tea1))
                .rotation((float) 0.5)
                .icon(bitmapDescriptorFromVector(getApplicationContext(),R.drawable.tea)));

        String id268 = tea1.getId();
        markerMap.put(id268,"action_tea");

        Marker tea2 = mMap.addMarker(new MarkerOptions()
                .position(new LatLng(22.397046,88.855864))
                .title(getString(R.string.tea_name))
                .snippet(getString(R.string.tea2))
                .rotation((float) 0.5)
                .icon(bitmapDescriptorFromVector(getApplicationContext(),R.drawable.tea)));

        String id269 = tea2.getId();
        markerMap.put(id269,"action_tea");

        Marker tea3 = mMap.addMarker(new MarkerOptions()
                .position(new LatLng(26.7167719,94.2265322))
                .title(getString(R.string.tea_name))
                .snippet(getString(R.string.tea3))
                .rotation((float) 0.5)
                .icon(bitmapDescriptorFromVector(getApplicationContext(),R.drawable.tea)));

        String id270 = tea3.getId();
        markerMap.put(id270,"action_tea");

        Marker tea4 = mMap.addMarker(new MarkerOptions()
                .position(new LatLng(24.6305068,92.8566039))
                .title(getString(R.string.tea_name))
                .snippet(getString(R.string.tea4))
                .rotation((float) 0.5)
                .icon(bitmapDescriptorFromVector(getApplicationContext(),R.drawable.tea)));

        String id271 = tea4.getId();
        markerMap.put(id271,"action_tea");

        Marker tea5 = mMap.addMarker(new MarkerOptions()
                .position(new LatLng(32.105633,76.2690707))
                .title(getString(R.string.tea_name))
                .snippet(getString(R.string.tea5))
                .rotation((float) 0.5)
                .icon(bitmapDescriptorFromVector(getApplicationContext(),R.drawable.tea)));

        String id272 = tea5.getId();
        markerMap.put(id272,"action_tea");

        Marker tea6 = mMap.addMarker(new MarkerOptions()
                .position(new LatLng(9.7880412,76.8900514))
                .title(getString(R.string.tea_name))
                .snippet(getString(R.string.tea6))
                .rotation((float) 0.5)
                .icon(bitmapDescriptorFromVector(getApplicationContext(),R.drawable.tea)));

        String id273 = tea6.getId();
        markerMap.put(id273,"action_tea");

        Marker tea7 = mMap.addMarker(new MarkerOptions()
                .position(new LatLng(23.599552,91.8179982))
                .title(getString(R.string.tea_name))
                .snippet(getString(R.string.tea7))
                .rotation((float) 0.5)
                .icon(bitmapDescriptorFromVector(getApplicationContext(),R.drawable.tea)));

        String id274 = tea7.getId();
        markerMap.put(id274,"action_tea");

        Marker tea8 = mMap.addMarker(new MarkerOptions()
                .position(new LatLng(24.0631626,91.6092164))
                .title(getString(R.string.tea_name))
                .snippet(getString(R.string.tea8))
                .rotation((float) 0.5)
                .icon(bitmapDescriptorFromVector(getApplicationContext(),R.drawable.tea)));

        String id275 = tea8.getId();
        markerMap.put(id275,"action_tea");

        Marker tea9 = mMap.addMarker(new MarkerOptions()
                .position(new LatLng(23.0754303,91.6477198))
                .title(getString(R.string.tea_name))
                .snippet(getString(R.string.tea9))
                .rotation((float) 0.5)
                .icon(bitmapDescriptorFromVector(getApplicationContext(),R.drawable.tea)));

        String id276 = tea9.getId();
        markerMap.put(id276,"action_tea");

        Marker tea10 = mMap.addMarker(new MarkerOptions()
                .position(new LatLng(23.7396573,91.1843175))
                .title(getString(R.string.tea_name))
                .snippet(getString(R.string.tea10))
                .rotation((float) 0.5)
                .icon(bitmapDescriptorFromVector(getApplicationContext(),R.drawable.tea)));

        String id277 = tea10.getId();
        markerMap.put(id277,"action_tea");

        Marker tea11 = mMap.addMarker(new MarkerOptions()
                .position(new LatLng(26.358796,89.421641))
                .title(getString(R.string.tea_name))
                .snippet(getString(R.string.tea11))
                .rotation((float) 0.5)
                .icon(bitmapDescriptorFromVector(getApplicationContext(),R.drawable.tea)));

        String id278 = tea11.getId();
        markerMap.put(id278,"action_tea");

        //tobacco

        Marker tob1 = mMap.addMarker(new MarkerOptions()
                .position(new LatLng(17.2664424,80.1176356))
                .title(getString(R.string.tobacco_name))
                .snippet(getString(R.string.tobacco1))
                .rotation((float) 0.5)
                .icon(bitmapDescriptorFromVector(getApplicationContext(),R.drawable.tobacco)));

        String id279 = tob1.getId();
        markerMap.put(id279,"action_tob");

        Marker tob2 = mMap.addMarker(new MarkerOptions()
                .position(new LatLng(14.4214929,79.986702 ))
                .title(getString(R.string.tobacco_name))
                .snippet(getString(R.string.tobacco2))
                .rotation((float) 0.5)
                .icon(bitmapDescriptorFromVector(getApplicationContext(),R.drawable.tobacco)));

        String id280 = tob2.getId();
        markerMap.put(id280,"action_tob");

        Marker tob3 = mMap.addMarker(new MarkerOptions()
                .position(new LatLng(16.3488806,80.4641881 ))
                .title(getString(R.string.tobacco_name))
                .snippet(getString(R.string.tobacco3))
                .rotation((float) 0.5)
                .icon(bitmapDescriptorFromVector(getApplicationContext(),R.drawable.tobacco)));

        String id281 = tob3.getId();
        markerMap.put(id281,"action_tob");

        Marker tob4 = mMap.addMarker(new MarkerOptions()
                .position(new LatLng(25.750797,87.4748715 ))
                .title(getString(R.string.tobacco_name))
                .snippet(getString(R.string.tobacco4))
                .rotation((float) 0.5)
                .icon(bitmapDescriptorFromVector(getApplicationContext(),R.drawable.tobacco)));

        String id282 = tob4.getId();
        markerMap.put(id282,"action_tob");

        Marker tob5 = mMap.addMarker(new MarkerOptions()
                .position(new LatLng(25.3709824,87.5746358))
                .title(getString(R.string.tobacco_name))
                .snippet(getString(R.string.tobacco5))
                .rotation((float) 0.5)
                .icon(bitmapDescriptorFromVector(getApplicationContext(),R.drawable.tobacco)));

        String id283 = tob5.getId();
        markerMap.put(id283,"action_tob");

        Marker tob6 = mMap.addMarker(new MarkerOptions()
                .position(new LatLng(22.5274929,72.9661525))
                .title(getString(R.string.tobacco_name))
                .snippet(getString(R.string.tobacco6))
                .rotation((float) 0.5)
                .icon(bitmapDescriptorFromVector(getApplicationContext(),R.drawable.tobacco)));

        String id284 = tob6.getId();
        markerMap.put(id284,"action_tob");

        Marker tob7 = mMap.addMarker(new MarkerOptions()
                .position(new LatLng(16.3949744,74.3870556))
                .title(getString(R.string.tobacco_name))
                .snippet(getString(R.string.tobacco7))
                .rotation((float) 0.5)
                .icon(bitmapDescriptorFromVector(getApplicationContext(),R.drawable.tobacco)));

        String id285 = tob7.getId();
        markerMap.put(id285,"action_tob");

        Marker tob8 = mMap.addMarker(new MarkerOptions()
                .position(new LatLng(11.0535813,76.9539661))
                .title(getString(R.string.tobacco_name))
                .snippet(getString(R.string.tobacco8))
                .rotation((float) 0.5)
                .icon(bitmapDescriptorFromVector(getApplicationContext(),R.drawable.tobacco)));

        String id286 = tob8.getId();
        markerMap.put(id286,"action_tob");

        Marker tob9 = mMap.addMarker(new MarkerOptions()
                .position(new LatLng(17.6900288,81.9570628))
                .title(getString(R.string.tobacco_name))
                .snippet(getString(R.string.tobacco9))
                .rotation((float) 0.5)
                .icon(bitmapDescriptorFromVector(getApplicationContext(),R.drawable.tobacco)));

        String id287 = tob9.getId();
        markerMap.put(id287,"action_tob");

        Marker tob10 = mMap.addMarker(new MarkerOptions()
                .position(new LatLng(16.5368008,81.6705261))
                .title(getString(R.string.tobacco_name))
                .snippet(getString(R.string.tobacco10))
                .rotation((float) 0.5)
                .icon(bitmapDescriptorFromVector(getApplicationContext(),R.drawable.tobacco)));

        String id288 = tob10.getId();
        markerMap.put(id288,"action_tob");

        Marker tob11 = mMap.addMarker(new MarkerOptions()
                .position(new LatLng(12.0328785,75.3751598))
                .title(getString(R.string.tobacco_name))
                .snippet(getString(R.string.tobacco11))
                .rotation((float) 0.5)
                .icon(bitmapDescriptorFromVector(getApplicationContext(),R.drawable.tobacco)));

        String id289 = tob11.getId();
        markerMap.put(id289,"action_tob");

        Marker tob12 = mMap.addMarker(new MarkerOptions()
                .position(new LatLng(31.556300,75.913226))
                .title(getString(R.string.tobacco_name))
                .snippet(getString(R.string.tobacco12))
                .rotation((float) 0.5)
                .icon(bitmapDescriptorFromVector(getApplicationContext(),R.drawable.tobacco)));

        String id290 = tob12.getId();
        markerMap.put(id290,"action_tob");

        Marker tob13 = mMap.addMarker(new MarkerOptions()
                .position(new LatLng(23.5482456,91.4876243))
                .title(getString(R.string.tobacco_name))
                .snippet(getString(R.string.tobacco13))
                .rotation((float) 0.5)
                .icon(bitmapDescriptorFromVector(getApplicationContext(),R.drawable.tobacco)));

        String id291 = tob13.getId();
        markerMap.put(id291,"action_tob");

        Marker tob14 = mMap.addMarker(new MarkerOptions()
                .position(new LatLng(25.4746818,78.5318343))
                .title(getString(R.string.tobacco_name))
                .snippet(getString(R.string.tobacco14))
                .rotation((float) 0.5)
                .icon(bitmapDescriptorFromVector(getApplicationContext(),R.drawable.tobacco)));

        String id292 = tob14.getId();
        markerMap.put(id292,"action_tob");

        Marker tob15 = mMap.addMarker(new MarkerOptions()
                .position(new LatLng(24.701304,78.429723))
                .title(getString(R.string.tobacco_name))
                .snippet(getString(R.string.tobacco15))
                .rotation((float) 0.5)
                .icon(bitmapDescriptorFromVector(getApplicationContext(),R.drawable.tobacco)));

        String id293 = tob15.getId();
        markerMap.put(id293,"action_tob");

        Marker tob16 = mMap.addMarker(new MarkerOptions()
                .position(new LatLng(25.3439832,80.3370608))
                .title(getString(R.string.tobacco_name))
                .snippet(getString(R.string.tobacco16))
                .rotation((float) 0.5)
                .icon(bitmapDescriptorFromVector(getApplicationContext(),R.drawable.tobacco)));

        String id294 = tob16.getId();
        markerMap.put(id294,"action_tob");

        Marker tob17 = mMap.addMarker(new MarkerOptions()
                .position(new LatLng(23.969256,78.756131))
                .title(getString(R.string.tobacco_name))
                .snippet(getString(R.string.tobacco17))
                .rotation((float) 0.5)
                .icon(bitmapDescriptorFromVector(getApplicationContext(),R.drawable.tobacco)));

        String id295 = tob17.getId();
        markerMap.put(id295,"action_tob");

        Marker tob18 = mMap.addMarker(new MarkerOptions()
                .position(new LatLng(24.651054,80.671495))
                .title(getString(R.string.tobacco_name))
                .snippet(getString(R.string.tobacco18))
                .rotation((float) 0.5)
                .icon(bitmapDescriptorFromVector(getApplicationContext(),R.drawable.tobacco)));

        String id296 = tob18.getId();
        markerMap.put(id296,"action_tob");

        //turmeric
        Marker tur1=mMap.addMarker(new MarkerOptions()
                .position(new LatLng(13.768064,78.155620))
                .title(getString(R.string.turmeric_name))
                .snippet(getString(R.string.turmeric1))
                .rotation((float) 0.5)
                .icon(bitmapDescriptorFromVector(getApplicationContext(),R.drawable.turmeric)));
        String id297 = tur1.getId();
        markerMap.put(id297,"action_turmeric");

        Marker tur2=mMap.addMarker(new MarkerOptions()
                .position(new LatLng(20.931400,83.658740))
                .title(getString(R.string.turmeric_name))
                .snippet(getString(R.string.turmeric2))
                .rotation((float) 0.5)
                .icon(bitmapDescriptorFromVector(getApplicationContext(),R.drawable.turmeric)));

        String id298 = tur2.getId();
        markerMap.put(id298,"action_turmeric");

        Marker tur3=mMap.addMarker(new MarkerOptions()
                .position(new LatLng(14.0572115,79.8056534))
                .title(getString(R.string.turmeric_name))
                .snippet(getString(R.string.turmeric3))
                .rotation((float) 0.5)
                .icon(bitmapDescriptorFromVector(getApplicationContext(),R.drawable.turmeric)));

        String id299 = tur3.getId();
        markerMap.put(id299,"action_turmeric");

        Marker tur4=mMap.addMarker(new MarkerOptions()
                .position(new LatLng(11.343701, 77.677660))
                .title(getString(R.string.turmeric_name))
                .snippet(getString(R.string.turmeric4))
                .rotation((float) 0.5)
                .icon(bitmapDescriptorFromVector(getApplicationContext(),R.drawable.turmeric)));

        String id300 = tur4.getId();
        markerMap.put(id300,"action_turmeric");

        Marker tur5=mMap.addMarker(new MarkerOptions()
                .position(new LatLng(20.068709, 85.806950))
                .title(getString(R.string.turmeric_name))
                .snippet(getString(R.string.turmeric5))
                .rotation((float) 0.5)
                .icon(bitmapDescriptorFromVector(getApplicationContext(),R.drawable.turmeric)));

        String id301 = tur5.getId();
        markerMap.put(id301,"action_turmeric");

        Marker tur6=mMap.addMarker(new MarkerOptions()
                .position(new LatLng(20.966207, 84.853171))
                .title(getString(R.string.turmeric_name))
                .snippet(getString(R.string.turmeric6))
                .rotation((float) 0.5)
                .icon(bitmapDescriptorFromVector(getApplicationContext(),R.drawable.turmeric)));

        String id302 = tur6.getId();
        markerMap.put(id302,"action_turmeric");

        Marker tur7=mMap.addMarker(new MarkerOptions()
                .position(new LatLng(16.775973,77.1394177))
                .title(getString(R.string.turmeric_name))
                .snippet(getString(R.string.turmeric7))
                .rotation((float) 0.5)
                .icon(bitmapDescriptorFromVector(getApplicationContext(),R.drawable.turmeric)));

        String id303 = tur7.getId();
        markerMap.put(id303,"action_turmeric");

        Marker tur8=mMap.addMarker(new MarkerOptions()
                .position(new LatLng(24.360351, 87.841698))
                .title(getString(R.string.turmeric_name))
                .snippet(getString(R.string.turmeric8))
                .rotation((float) 0.5)
                .icon(bitmapDescriptorFromVector(getApplicationContext(),R.drawable.turmeric)));

        String id304 = tur8.getId();
        markerMap.put(id304,"action_turmeric");

        Marker tur9=mMap.addMarker(new MarkerOptions()
                .position(new LatLng(23.220243, 73.777021))
                .title(getString(R.string.turmeric_name))
                .snippet(getString(R.string.turmeric9))
                .rotation((float) 0.5)
                .icon(bitmapDescriptorFromVector(getApplicationContext(),R.drawable.turmeric)));

        String id305 = tur9.getId();
        markerMap.put(id305,"action_turmeric");

        Marker tur10=mMap.addMarker(new MarkerOptions()
                .position(new LatLng(22.749544, 72.681173))
                .title(getString(R.string.turmeric_name))
                .snippet(getString(R.string.turmeric10))
                .rotation((float) 0.5)
                .icon(bitmapDescriptorFromVector(getApplicationContext(),R.drawable.turmeric)));

        String id306 = tur10.getId();
        markerMap.put(id306,"action_turmeric");

        Marker tur11=mMap.addMarker(new MarkerOptions()
                .position(new LatLng(25.421914, 91.294206))
                .title(getString(R.string.turmeric_name))
                .snippet(getString(R.string.turmeric11))
                .rotation((float) 0.5)
                .icon(bitmapDescriptorFromVector(getApplicationContext(),R.drawable.turmeric)));

        String id307 = tur11.getId();
        markerMap.put(id307,"action_turmeric");

        Marker tur12=mMap.addMarker(new MarkerOptions()
                .position(new LatLng(21.421914, 80.294206))
                .title(getString(R.string.turmeric_name))
                .snippet(getString(R.string.turmeric12))
                .rotation((float) 0.5)
                .icon(bitmapDescriptorFromVector(getApplicationContext(),R.drawable.turmeric)));

        String id308 = tur12.getId();
        markerMap.put(id308,"action_turmeric");

        Marker tur13=mMap.addMarker(new MarkerOptions()
                .position(new LatLng(26.0201502,89.9645397))
                .title(getString(R.string.turmeric_name))
                .snippet(getString(R.string.turmeric13))
                .rotation((float) 0.5)
                .icon(bitmapDescriptorFromVector(getApplicationContext(),R.drawable.turmeric)));

        String id309 = tur13.getId();
        markerMap.put(id309,"action_turmeric");

        Marker tur14=mMap.addMarker(new MarkerOptions()
                .position(new LatLng(26.3947561,90.2588234))
                .title(getString(R.string.turmeric_name))
                .snippet(getString(R.string.turmeric14))
                .rotation((float) 0.5)
                .icon(bitmapDescriptorFromVector(getApplicationContext(),R.drawable.turmeric)));

        String id310 = tur14.getId();
        markerMap.put(id310,"action_turmeric");

//Wheat
        Marker wheat1=mMap.addMarker(new MarkerOptions()
                .position(new LatLng(29.512206,75.061473))
                .title(getString(R.string.wheat_name))
                .snippet(getString(R.string.wheat1))
                .rotation((float) 0.5)
                .icon(bitmapDescriptorFromVector(getApplicationContext(),R.drawable.wheat)));

        String id311 = wheat1.getId();
        markerMap.put(id311,"action_wheat");

        Marker wheat2=mMap.addMarker(new MarkerOptions()
                .position(new LatLng( 22.179727,76.139752))
                .title(getString(R.string.wheat_name))
                .snippet(getString(R.string.wheat2))
                .rotation((float) 0.5)
                .icon(bitmapDescriptorFromVector(getApplicationContext(),R.drawable.wheat)));

        String id312 = wheat2.getId();
        markerMap.put(id312,"action_wheat");

        Marker wheat3 = mMap.addMarker(new MarkerOptions()
                .position(new LatLng( 20.057331,73.8136037))
                .title(getString(R.string.wheat_name))
                .snippet(getString(R.string.wheat3))
                .rotation((float) 0.5)
                .icon(bitmapDescriptorFromVector(getApplicationContext(),R.drawable.wheat)));

        String id313 = wheat3.getId();
        markerMap.put(id313,"action_wheat");

        Marker wheat4 = mMap.addMarker(new MarkerOptions()
                .position(new LatLng( 21.2009694,71.3720026))
                .title(getString(R.string.wheat_name))
                .snippet(getString(R.string.wheat4))
                .rotation((float) 0.5)
                .icon(bitmapDescriptorFromVector(getApplicationContext(),R.drawable.wheat)));

        String id314 = wheat4.getId();
        markerMap.put(id314,"action_wheat");

        Marker wheat5 = mMap.addMarker(new MarkerOptions()
                .position(new LatLng( 17.1141952,81.1397531))
                .title(getString(R.string.wheat_name))
                .snippet(getString(R.string.wheat5))
                .rotation((float) 0.5)
                .icon(bitmapDescriptorFromVector(getApplicationContext(),R.drawable.wheat)));

        String id315 = wheat5.getId();
        markerMap.put(id315,"action_wheat");

        Marker wheat6 = mMap.addMarker(new MarkerOptions()
                .position(new LatLng( 26.5157526,93.9849306))
                .title(getString(R.string.wheat_name))
                .snippet(getString(R.string.wheat6))
                .rotation((float) 0.5)
                .icon(bitmapDescriptorFromVector(getApplicationContext(),R.drawable.wheat)));

        String id316 = wheat6.getId();
        markerMap.put(id316,"action_wheat");

        Marker wheat7 = mMap.addMarker(new MarkerOptions()
                .position(new LatLng( 26.310496,90.9832458))
                .title(getString(R.string.wheat_name))
                .snippet(getString(R.string.wheat7))
                .rotation((float) 0.5)
                .icon(bitmapDescriptorFromVector(getApplicationContext(),R.drawable.wheat)));

        String id317 = wheat7.getId();
        markerMap.put(id317,"action_wheat");

        Marker wheat8 = mMap.addMarker(new MarkerOptions()
                .position(new LatLng( 25.5645538,83.9732883))
                .title(getString(R.string.wheat_name))
                .snippet(getString(R.string.wheat8))
                .rotation((float) 0.5)
                .icon(bitmapDescriptorFromVector(getApplicationContext(),R.drawable.wheat)));

        String id318 = wheat8.getId();
        markerMap.put(id318,"action_wheat");

        Marker wheat9 = mMap.addMarker(new MarkerOptions()
                .position(new LatLng( 25.5113709,84.4733858))
                .title(getString(R.string.wheat_name))
                .snippet(getString(R.string.wheat9))
                .rotation((float) 0.5)
                .icon(bitmapDescriptorFromVector(getApplicationContext(),R.drawable.wheat)));

        String id319 = wheat9.getId();
        markerMap.put(id319,"action_wheat");

        Marker wheat10 = mMap.addMarker(new MarkerOptions()
                .position(new LatLng( 30.6742095,77.704602))
                .title(getString(R.string.wheat_name))
                .snippet(getString(R.string.wheat10))
                .rotation((float) 0.5)
                .icon(bitmapDescriptorFromVector(getApplicationContext(),R.drawable.wheat)));

        String id320 = wheat10.getId();
        markerMap.put(id320,"action_wheat");

        Marker wheat11 = mMap.addMarker(new MarkerOptions()
                .position(new LatLng( 32.9158079,75.1396585))
                .title(getString(R.string.wheat_name))
                .snippet(getString(R.string.wheat11))
                .rotation((float) 0.5)
                .icon(bitmapDescriptorFromVector(getApplicationContext(),R.drawable.wheat)));

        String id321 = wheat11.getId();
        markerMap.put(id321,"action_wheat");

        Marker wheat12 = mMap.addMarker(new MarkerOptions()
                .position(new LatLng( 32.7681981,74.7813627))
                .title(getString(R.string.wheat_name))
                .snippet(getString(R.string.wheat12))
                .rotation((float) 0.5)
                .icon(bitmapDescriptorFromVector(getApplicationContext(),R.drawable.wheat)));

        String id322 = wheat12.getId();
        markerMap.put(id322,"action_wheat");

        Marker wheat13 = mMap.addMarker(new MarkerOptions()
                .position(new LatLng( 15.5031961,74.9837177))
                .title(getString(R.string.wheat_name))
                .snippet(getString(R.string.wheat13))
                .rotation((float) 0.5)
                .icon(bitmapDescriptorFromVector(getApplicationContext(),R.drawable.wheat)));

        String id323 = wheat13.getId();
        markerMap.put(id323,"action_wheat");

        Marker wheat14 = mMap.addMarker(new MarkerOptions()
                .position(new LatLng( 21.932114,85.967359))
                .title(getString(R.string.wheat_name))
                .snippet(getString(R.string.wheat14))
                .rotation((float) 0.5)
                .icon(bitmapDescriptorFromVector(getApplicationContext(),R.drawable.wheat)));

        String id324 = wheat14.getId();
        markerMap.put(id324,"action_wheat");

        Marker wheat15 = mMap.addMarker(new MarkerOptions()
                .position(new LatLng( 22.131704,84.041835))
                .title(getString(R.string.wheat_name))
                .snippet(getString(R.string.wheat15))
                .rotation((float) 0.5)
                .icon(bitmapDescriptorFromVector(getApplicationContext(),R.drawable.wheat)));

        String id325 = wheat15.getId();
        markerMap.put(id325,"action_wheat");

        Marker wheat16 = mMap.addMarker(new MarkerOptions()
                .position(new LatLng( 21.529911,84.728439))
                .title(getString(R.string.wheat_name))
                .snippet(getString(R.string.wheat16))
                .rotation((float) 0.5)
                .icon(bitmapDescriptorFromVector(getApplicationContext(),R.drawable.wheat)));

        String id326 = wheat16.getId();
        markerMap.put(id326,"action_wheat");

        Marker wheat18 = mMap.addMarker(new MarkerOptions()
                .position(new LatLng(18.808716,84.206042))
                .title(getString(R.string.wheat_name))
                .snippet(getString(R.string.wheat18))
                .rotation((float) 0.5)
                .icon(bitmapDescriptorFromVector(getApplicationContext(),R.drawable.wheat)));

        String id328 = wheat18.getId();
        markerMap.put(id328,"action_wheat");

        Marker wheat19 = mMap.addMarker(new MarkerOptions()
                .position(new LatLng(25.306894,74.639822))
                .title(getString(R.string.wheat_name))
                .snippet(getString(R.string.wheat19))
                .rotation((float) 0.5)
                .icon(bitmapDescriptorFromVector(getApplicationContext(),R.drawable.wheat)));

        String id329 = wheat19.getId();
        markerMap.put(id329,"action_wheat");

        Marker wheat20 = mMap.addMarker(new MarkerOptions()
                .position(new LatLng(27.538524,76.583693))
                .title(getString(R.string.wheat_name))
                .snippet(getString(R.string.wheat20))
                .rotation((float) 0.5)
                .icon(bitmapDescriptorFromVector(getApplicationContext(),R.drawable.wheat)));

        String id330 = wheat20.getId();
        markerMap.put(id330,"action_wheat");

        Marker wheat21 = mMap.addMarker(new MarkerOptions()
                .position(new LatLng(29.902579,73.853205))
                .title(getString(R.string.wheat_name))
                .snippet(getString(R.string.wheat21))
                .rotation((float) 0.5)
                .icon(bitmapDescriptorFromVector(getApplicationContext(),R.drawable.wheat)));

        String id331 = wheat21.getId();
        markerMap.put(id331,"action_wheat");

        Marker wheat22 = mMap.addMarker(new MarkerOptions()
                .position(new LatLng(29.594180,74.335214))
                .title(getString(R.string.wheat_name))
                .snippet(getString(R.string.wheat22))
                .rotation((float) 0.5)
                .icon(bitmapDescriptorFromVector(getApplicationContext(),R.drawable.wheat)));

        String id332 = wheat22.getId();
        markerMap.put(id332,"action_wheat");

        Marker wheat23 = mMap.addMarker(new MarkerOptions()
                .position(new LatLng(29.850118,78.1852029))
                .title(getString(R.string.wheat_name))
                .snippet(getString(R.string.wheat23))
                .rotation((float) 0.5)
                .icon(bitmapDescriptorFromVector(getApplicationContext(),R.drawable.wheat)));

        String id333 = wheat23.getId();
        markerMap.put(id333,"action_wheat");

        Marker wheat24 = mMap.addMarker(new MarkerOptions()
                .position(new LatLng(24.2020521,88.2733951))
                .title(getString(R.string.wheat_name))
                .snippet(getString(R.string.wheat24))
                .rotation((float) 0.5)
                .icon(bitmapDescriptorFromVector(getApplicationContext(),R.drawable.wheat)));

        String id334 = wheat24.getId();
        markerMap.put(id334,"action_wheat");

        Marker wheat25 = mMap.addMarker(new MarkerOptions()
                .position(new LatLng(22.710770,88.759543))
                .title(getString(R.string.wheat_name))
                .snippet(getString(R.string.wheat25))
                .rotation((float) 0.5)
                .icon(bitmapDescriptorFromVector(getApplicationContext(),R.drawable.wheat)));

        String id335 = wheat25.getId();
        markerMap.put(id335,"action_wheat");

        Marker wheat26 = mMap.addMarker(new MarkerOptions()
                .position(new LatLng(26.746461,83.885325))
                .title(getString(R.string.wheat_name))
                .snippet(getString(R.string.wheat26))
                .rotation((float) 0.5)
                .icon(bitmapDescriptorFromVector(getApplicationContext(),R.drawable.wheat)));

        String id336 = wheat26.getId();
        markerMap.put(id336,"action_wheat");

        Marker wheat27 = mMap.addMarker(new MarkerOptions()
                .position(new LatLng(27.861592,79.943965))
                .title(getString(R.string.wheat_name))
                .snippet(getString(R.string.wheat27))
                .rotation((float) 0.5)
                .icon(bitmapDescriptorFromVector(getApplicationContext(),R.drawable.wheat)));

        String id337 = wheat27.getId();
        markerMap.put(id337,"action_wheat");

        Marker wheat28 = mMap.addMarker(new MarkerOptions()
                .position(new LatLng(28.413389,79.396606))
                .title(getString(R.string.wheat_name))
                .snippet(getString(R.string.wheat28))
                .rotation((float) 0.5)
                .icon(bitmapDescriptorFromVector(getApplicationContext(),R.drawable.wheat)));

        String id338 = wheat28.getId();
        markerMap.put(id338,"action_wheat");



        mMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
            @Override
            public void onInfoWindowClick(Marker marker) {
                String actionId = markerMap.get(marker.getId());
                switch (actionId) {
                    case "action_bitter": {
                        androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(AnalystMap.this);
                        LayoutInflater inflater = getLayoutInflater();
                        View dialogLayout = inflater.inflate(R.layout.alertbox_with_image, null);
                        TextView tv = dialogLayout.findViewById(R.id.tv_01);
                        ImageView iv = dialogLayout.findViewById(R.id.image);
                        iv.setImageResource(R.drawable.bittergourd_farm);
                        tv.setText(R.string.bg_des);
                        builder.setPositiveButton(R.string.ok_got_it, null);
                        builder.setView(dialogLayout);
                        builder.show();
                        break;
                    }
                    case "action_sugar": {
                        androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(AnalystMap.this);
                        LayoutInflater inflater = getLayoutInflater();
                        View dialogLayout = inflater.inflate(R.layout.alertbox_with_image, null);
                        TextView tv = dialogLayout.findViewById(R.id.tv_01);
                        ImageView iv = dialogLayout.findViewById(R.id.image);
                        iv.setImageResource(R.drawable.sugarcane_farm);
                        tv.setText(R.string.sugarcane_des);
                        builder.setPositiveButton(R.string.ok_got_it, null);
                        builder.setView(dialogLayout);
                        builder.show();
                        break;
                    }
                    case "action_sesame": {
                        androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(AnalystMap.this);
                        LayoutInflater inflater = getLayoutInflater();
                        View dialogLayout = inflater.inflate(R.layout.alertbox_with_image, null);
                        TextView tv = dialogLayout.findViewById(R.id.tv_01);
                        ImageView iv = dialogLayout.findViewById(R.id.image);
                        iv.setImageResource(R.drawable.sesame_farm);
                        tv.setText(R.string.sesame_des);
                        builder.setPositiveButton(R.string.ok_got_it, null);
                        builder.setView(dialogLayout);
                        builder.show();
                        break;
                    }
                    case "action_sor": {
                        androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(AnalystMap.this);
                        LayoutInflater inflater = getLayoutInflater();
                        View dialogLayout = inflater.inflate(R.layout.alertbox_with_image, null);
                        TextView tv = dialogLayout.findViewById(R.id.tv_01);
                        ImageView iv = dialogLayout.findViewById(R.id.image);
                        iv.setImageResource(R.drawable.sorghum_farm);
                        tv.setText(R.string.sorghum_des);
                        builder.setPositiveButton(R.string.ok_got_it, null);
                        builder.setView(dialogLayout);
                        builder.show();
                        break;
                    }
                    case "action_soy": {
                        androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(AnalystMap.this);
                        LayoutInflater inflater = getLayoutInflater();
                        View dialogLayout = inflater.inflate(R.layout.alertbox_with_image, null);
                        TextView tv = dialogLayout.findViewById(R.id.tv_01);
                        ImageView iv = dialogLayout.findViewById(R.id.image);
                        iv.setImageResource(R.drawable.soyabean_farm);
                        tv.setText(R.string.soybean_des);
                        builder.setPositiveButton(R.string.ok_got_it, null);
                        builder.setView(dialogLayout);
                        builder.show();
                        break;
                    }
                    case "action_wheat": {
                        androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(AnalystMap.this);
                        LayoutInflater inflater = getLayoutInflater();
                        View dialogLayout = inflater.inflate(R.layout.alertbox_with_image, null);
                        TextView tv = dialogLayout.findViewById(R.id.tv_01);
                        ImageView iv = dialogLayout.findViewById(R.id.image);
                        iv.setImageResource(R.drawable.wheat_farm);
                        tv.setText(R.string.wheat_des);
                        builder.setPositiveButton(R.string.ok_got_it, null);
                        builder.setView(dialogLayout);
                        builder.show();
                        break;
                    }
                    case "action_rice": {
                        androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(AnalystMap.this);
                        LayoutInflater inflater = getLayoutInflater();
                        View dialogLayout = inflater.inflate(R.layout.alertbox_with_image, null);
                        TextView tv = dialogLayout.findViewById(R.id.tv_01);
                        ImageView iv = dialogLayout.findViewById(R.id.image);
                        iv.setImageResource(R.drawable.rice_farm);
                        tv.setText(R.string.rice_des);
                        builder.setPositiveButton(R.string.ok_got_it, null);
                        builder.setView(dialogLayout);
                        builder.show();
                        break;
                    }
                    case "action_methi": {
                        androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(AnalystMap.this);
                        LayoutInflater inflater = getLayoutInflater();
                        View dialogLayout = inflater.inflate(R.layout.alertbox_with_image, null);
                        TextView tv = dialogLayout.findViewById(R.id.tv_01);
                        ImageView iv = dialogLayout.findViewById(R.id.image);
                        iv.setImageResource(R.drawable.fenugreek_farm);
                        tv.setText(R.string.methi_des);
                        builder.setPositiveButton(R.string.ok_got_it, null);
                        builder.setView(dialogLayout);
                        builder.show();
                        break;
                    }
                    case "action_mustard": {
                        androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(AnalystMap.this);
                        LayoutInflater inflater = getLayoutInflater();
                        View dialogLayout = inflater.inflate(R.layout.alertbox_with_image, null);
                        TextView tv = dialogLayout.findViewById(R.id.tv_01);
                        ImageView iv = dialogLayout.findViewById(R.id.image);
                        iv.setImageResource(R.drawable.mustard_farm);
                        tv.setText(R.string.mustard_des);
                        builder.setPositiveButton(R.string.ok_got_it, null);
                        builder.setView(dialogLayout);
                        builder.show();
                        break;
                    }
                    case "action_peas": {
                        androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(AnalystMap.this);
                        LayoutInflater inflater = getLayoutInflater();
                        View dialogLayout = inflater.inflate(R.layout.alertbox_with_image, null);
                        TextView tv = dialogLayout.findViewById(R.id.tv_01);
                        ImageView iv = dialogLayout.findViewById(R.id.image);
                        iv.setImageResource(R.drawable.peas_farm);
                        tv.setText(R.string.peas_des);
                        builder.setPositiveButton(R.string.ok_got_it, null);
                        builder.setView(dialogLayout);
                        builder.show();
                        break;
                    }
                    case "action_lentil": {
                        androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(AnalystMap.this);
                        LayoutInflater inflater = getLayoutInflater();
                        View dialogLayout = inflater.inflate(R.layout.alertbox_with_image, null);
                        TextView tv = dialogLayout.findViewById(R.id.tv_01);
                        ImageView iv = dialogLayout.findViewById(R.id.image);
                        iv.setImageResource(R.drawable.lentil_farm);
                        tv.setText(R.string.lentil_des);
                        builder.setPositiveButton(R.string.ok_got_it, null);
                        builder.setView(dialogLayout);
                        builder.show();
                        break;
                    }
                    case "action_pum": {
                        androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(AnalystMap.this);
                        LayoutInflater inflater = getLayoutInflater();
                        View dialogLayout = inflater.inflate(R.layout.alertbox_with_image, null);
                        TextView tv = dialogLayout.findViewById(R.id.tv_01);
                        ImageView iv = dialogLayout.findViewById(R.id.image);
                        iv.setImageResource(R.drawable.pumpkin_farm);
                        tv.setText(R.string.pumpkin_des);
                        builder.setPositiveButton(R.string.ok_got_it, null);
                        builder.setView(dialogLayout);
                        builder.show();
                        break;
                    }
                    case "action_turmeric": {
                        androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(AnalystMap.this);
                        LayoutInflater inflater = getLayoutInflater();
                        View dialogLayout = inflater.inflate(R.layout.alertbox_with_image, null);
                        TextView tv = dialogLayout.findViewById(R.id.tv_01);
                        ImageView iv = dialogLayout.findViewById(R.id.image);
                        iv.setImageResource(R.drawable.turmeric_farm);
                        tv.setText(R.string.turmeric_des);
                        builder.setPositiveButton(R.string.ok_got_it, null);
                        builder.setView(dialogLayout);
                        builder.show();
                        break;
                    }
                    case "action_cotton": {
                        androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(AnalystMap.this);
                        LayoutInflater inflater = getLayoutInflater();
                        View dialogLayout = inflater.inflate(R.layout.alertbox_with_image, null);
                        TextView tv = dialogLayout.findViewById(R.id.tv_01);
                        ImageView iv = dialogLayout.findViewById(R.id.image);
                        iv.setImageResource(R.drawable.cotton_farm);
                        tv.setText(R.string.cotton_des);
                        builder.setPositiveButton(R.string.ok_got_it, null);
                        builder.setView(dialogLayout);
                        builder.show();
                        break;
                    }
                    case "action_chilli": {
                        androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(AnalystMap.this);
                        LayoutInflater inflater = getLayoutInflater();
                        View dialogLayout = inflater.inflate(R.layout.alertbox_with_image, null);
                        TextView tv = dialogLayout.findViewById(R.id.tv_01);
                        ImageView iv = dialogLayout.findViewById(R.id.image);
                        iv.setImageResource(R.drawable.chilli_farm);
                        tv.setText(R.string.chilly_des);
                        builder.setPositiveButton(R.string.ok_got_it, null);
                        builder.setView(dialogLayout);
                        builder.show();
                        break;
                    }
                    case "action_maize": {
                        androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(AnalystMap.this);
                        LayoutInflater inflater = getLayoutInflater();
                        View dialogLayout = inflater.inflate(R.layout.alertbox_with_image, null);
                        TextView tv = dialogLayout.findViewById(R.id.tv_01);
                        ImageView iv = dialogLayout.findViewById(R.id.image);
                        iv.setImageResource(R.drawable.maize_farm);
                        tv.setText(R.string.maize_des);
                        builder.setPositiveButton(R.string.ok_got_it, null);
                        builder.setView(dialogLayout);
                        builder.show();
                        break;
                    }
                    case "action_tea": {
                        androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(AnalystMap.this);
                        LayoutInflater inflater = getLayoutInflater();
                        View dialogLayout = inflater.inflate(R.layout.alertbox_with_image, null);
                        TextView tv = dialogLayout.findViewById(R.id.tv_01);
                        ImageView iv = dialogLayout.findViewById(R.id.image);
                        iv.setImageResource(R.drawable.tea_farm);
                        tv.setText(R.string.tea_des);
                        builder.setPositiveButton(R.string.ok_got_it, null);
                        builder.setView(dialogLayout);
                        builder.show();
                        break;
                    }
                    case "action_tob": {
                        androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(AnalystMap.this);
                        LayoutInflater inflater = getLayoutInflater();
                        View dialogLayout = inflater.inflate(R.layout.alertbox_with_image, null);
                        TextView tv = dialogLayout.findViewById(R.id.tv_01);
                        ImageView iv = dialogLayout.findViewById(R.id.image);
                        iv.setImageResource(R.drawable.tobacco_farm);
                        tv.setText(R.string.tobacco_des);
                        builder.setPositiveButton(R.string.ok_got_it, null);
                        builder.setView(dialogLayout);
                        builder.show();
                        break;
                    }
                    case "action_coffee": {
                        androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(AnalystMap.this);
                        LayoutInflater inflater = getLayoutInflater();
                        View dialogLayout = inflater.inflate(R.layout.alertbox_with_image, null);
                        TextView tv = dialogLayout.findViewById(R.id.tv_01);
                        ImageView iv = dialogLayout.findViewById(R.id.image);
                        iv.setImageResource(R.drawable.coffee_farm);
                        tv.setText(R.string.coffee_des);
                        builder.setPositiveButton(R.string.ok_got_it, null);
                        builder.setView(dialogLayout);
                        builder.show();
                        break;
                    }
                    case "action_own":
                        AlertDialog.Builder builder = new AlertDialog.Builder(AnalystMap.this);
                        builder.setTitle(R.string.if_weather).setIcon(R.drawable.ic_help);
                        builder.setMessage(R.string.if_weather_msg);

                        builder.setNegativeButton(R.string.no,new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface,int i) {

                                alertDialog1.cancel();
                            }
                        });
                        builder.setPositiveButton(R.string.yes,new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface,int i) {

                                Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                                startActivity(intent);
                            }
                        });

                        alertDialog1 = builder.create();
                        alertDialog1.show();

                        // Get the alert dialog buttons reference
                        Button positiveButton = alertDialog1.getButton(AlertDialog.BUTTON_POSITIVE);
                        Button negativeButton = alertDialog1.getButton(AlertDialog.BUTTON_NEGATIVE);

                        // Change the alert dialog buttons text and background color
                        positiveButton.setTextColor(Color.parseColor("#FF0B8B42"));
                        negativeButton.setTextColor(Color.parseColor("#FFFF0400"));
                }
            }


        });



    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults ){
        switch (requestCode){
            case REQUEST_CODE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    fetchLastLocation();
                }
                break;
        }
    }

    private BitmapDescriptor bitmapDescriptorFromVector(Context context, int vectorResId) {

        int height = 50;
        int width = 50;
        Drawable vectorDrawable = ContextCompat.getDrawable(context, vectorResId);
        vectorDrawable.setBounds(0, 0, width, height);
        Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        vectorDrawable.draw(canvas);
        return BitmapDescriptorFactory.fromBitmap(bitmap);
    }

}


