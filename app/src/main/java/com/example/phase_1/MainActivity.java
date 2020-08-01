package com.example.phase_1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import com.bumptech.glide.Glide;
import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Typeface;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.theartofdev.edmodo.cropper.CropImage;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;

    AlertDialog alertDialog1;

    private Uri imageUri;


    // string to send to next activity that describes the chosen classifier
    private String chosen;

    //boolean value dictating if chosen model is quantized version or not.
    private boolean quant;
    // for permission requests
    public static final int REQUEST_PERMISSION = 300;

    // request code for permission requests to the os for image
    public static final int REQUEST_IMAGE = 100;
    private static final int REQUEST_CODE = 101;
    Location currentlocation;
    FusedLocationProviderClient fusedLocationProviderClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView tv = findViewById(R.id.tagline);
        Typeface face = Typeface.createFromAsset(getAssets(),
                "fonts/Lato-BoldItalic.ttf");
        tv.setTypeface(face);

        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null){
            actionBar.setBackgroundDrawable(getResources().getDrawable(R.drawable.actionbar_gradient));
        }

        //app name on title bar
        getSupportActionBar().setTitle(Html.fromHtml("<font color='#000000'>Cropifier</font>"));

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        fetchLastLocation();
        statusCheck();

        //camera button
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                     filename in assets
                chosen = "output_final_float.lite";
//                    // model in not quantized
                quant = false;
//                    // open camera
                openCameraIntent();
            }
        });

        CustomBottomNavigationView1 customBottomNavigationView1 = findViewById(R.id.customBottomNavBar);
        customBottomNavigationView1.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
//                if (item.getItemId() == R.id.home) {
////                    getSupportActionBar().setTitle("Home");
//                }
//                else if (item.getItemId() == R.id.camera) {
//                    // filename in assets
//                    chosen = "outputv13.lite";
//                    // model in not quantized
//                    quant = false;
//                    // open camera
//                    openCameraIntent();
//                }else
                 if (item.getItemId() == R.id.explore) {
                    CreateAlertDialogWithRadioButtonGroup();
                } else if (item.getItemId() == R.id.livefeed) {
                    openlivefeed();
                }
                return false;
            }

        });

    }

    private void statusCheck() {

        final LocationManager manager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        if (!manager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            buildAlertMessageNoGps();

        }
    }

    private void buildAlertMessageNoGps() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("To continue, turn on your device location,which uses Google's location service")
                .setCancelable(false)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialog, final int id) {
                        startActivity(new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                    }
                })
                .setNegativeButton("NO THANKS", new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialog, final int id) {
                        dialog.cancel();
                    }
                });

        final AlertDialog alert = builder.create();
        alert.show();
    }

    private void fetchLastLocation() {

        if(ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this,Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED){
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

                    Toast.makeText(getApplicationContext(),Label1,Toast.LENGTH_SHORT).show();


                }
            }
        });


    }

    private void openlivefeed() {
        Intent mySuperIntent = new Intent(MainActivity.this, DetectorActivity.class);
        startActivity(mySuperIntent);
    }

    private void CreateAlertDialogWithRadioButtonGroup() {
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);

        builder.setTitle(getString(R.string.role));

        builder.setSingleChoiceItems(R.array.roles, -1, new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int item) {

                switch (item) {
                    case 0:
                        Intent i = new Intent(MainActivity.this, MapsActivity.class);
                        startActivity(i);
                        break;
                    case 1:
                        Intent j = new Intent(MainActivity.this, AnalystMap.class);
                        startActivity(j);
                        break;
                }
                alertDialog1.dismiss();
            }
        });
        alertDialog1 = builder.create();
        alertDialog1.show();

    }


    private void openCameraIntent() {

        CropImage.activity().start(MainActivity.this);
    }

    // dictates what to do after the user takes an image, selects and image, or crops an image
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // if the camera activity is finished, obtained the uri, crop it to make it square, and send it to 'Classify' activity


        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {

            CropImage.ActivityResult result = CropImage.getActivityResult(data);

            if (resultCode == RESULT_OK) {
                imageUri = result.getUri();
                Intent i = new Intent(MainActivity.this, Classifier.class);
                // put image data in extras to send
                i.putExtra("resID_uri", imageUri);
                // put filename in extras
                i.putExtra("chosen", chosen);
                // put model type in extras
                i.putExtra("quant", quant);
                // send other required data
                startActivity(i);
            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
                Exception e = result.getError();
                Toast.makeText(this, "Possible error is ", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void identification(View view) {

//        clickFab();
        androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(MainActivity.this);
        LayoutInflater inflater = LayoutInflater.from(MainActivity.this);
        View dialogLayout = inflater.inflate(R.layout.alertbox_with_image, null);
        TextView tv = dialogLayout.findViewById(R.id.tv_01);
        ImageView iv = dialogLayout.findViewById(R.id.image);
        Glide.with(MainActivity.this)
                .load(R.drawable.camera_gif)
                .into(iv);
//                iv.setImageResource(R.drawable.coffee_farm);
        tv.setText("Click on the camera button and identify field photo of crops ");
        builder.setPositiveButton("Okay , Got it", null);
        builder.setView(dialogLayout);
        builder.show();
    }

    public void map(View view) {
        CreateAlertDialogWithRadioButtonGroup();
    }

    public void live(View view) {
        openlivefeed();
    }

    public void export(View view) {

        androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(MainActivity.this);
        LayoutInflater inflater = LayoutInflater.from(MainActivity.this);
        View dialogLayout = inflater.inflate(R.layout.alertbox_with_image, null);
        TextView tv = dialogLayout.findViewById(R.id.tv_01);
        ImageView iv = dialogLayout.findViewById(R.id.image);
        Glide.with(MainActivity.this)
                .load(R.drawable.send)
                .into(iv);
//                iv.setImageResource(R.drawable.coffee_farm);
        tv.setText("Share and save the information for future use");
        builder.setPositiveButton("Okay , Got it", null);
        builder.setView(dialogLayout);
        builder.show();
    }
}
