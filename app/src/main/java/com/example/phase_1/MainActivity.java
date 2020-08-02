package com.example.phase_1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Html;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.phase_1.activities.AnalystMap;
import com.example.phase_1.activities.MapsActivity;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.soundcloud.android.crop.Crop;
import com.theartofdev.edmodo.cropper.CropImage;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Locale;



public class MainActivity extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;

    AlertDialog alertDialog1;

    //    CharSequence[] values = {"Farmer","General"};
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
    public final int crop_id = 0;

    Location currentlocation;
    FusedLocationProviderClient fusedLocationProviderClient;

    // ArrayList for person names
//    ArrayList<String> personNames = new ArrayList<>(Arrays.asList(getResources().getStringArray(R.array.home_cardview)));
//    ArrayList<String> personNames = new ArrayList<>(Arrays.asList(R.string.home_card1, "Explore on Map", "Crop Info can be Exported", "Real Time Crop Identification", "Multiple Lingual Support ", "Suggestions using Weather Forecast", "Offline Access to Information","Cross Platform Compatibility"));
//    ArrayList<Integer> personImages = new ArrayList<>(Arrays.asList(R.drawable.image1, R.drawable.image2, R.drawable.image3, R.drawable.image4, R.drawable.image5, R.drawable.image6, R.drawable.image7,R.drawable.image8));
//    ArrayList<Integer> colors = new ArrayList<>(Arrays.asList(R.color.bg1,R.color.bg2,R.color.bg3,R.color.bg4,R.color.bg5,R.color.bg6,R.color.bg7,R.color.bg8));
    ArrayList<Integer> personImages = new ArrayList<>(Arrays.asList(R.drawable.image1, R.drawable.image2, R.drawable.image4, R.drawable.image5, R.drawable.image6, R.drawable.image7));
    ArrayList<Integer> colors = new ArrayList<>(Arrays.asList(R.color.bg1,R.color.bg2,R.color.bg4,R.color.bg5,R.color.bg6,R.color.bg7));

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loadLanguage();
        setContentView(R.layout.activity_main);

        TextView tv = findViewById(R.id.tagline);
        Typeface face = Typeface.createFromAsset(getAssets(),
                "fonts/Lato-BoldItalic.ttf");
        tv.setTypeface(face);

        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null){
            actionBar.setBackgroundDrawable(getResources().getDrawable(R.drawable.actionbar_gradient));
        }
        ArrayList<String> personNames = new ArrayList<>(Arrays.asList(getResources().getStringArray(R.array.home_cardview)));
        // get the reference of RecyclerView
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        // set a GridLayoutManager with 2 number of columns , horizontal gravity and false value for reverseLayout to show the items from start to end
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getApplicationContext(),2, LinearLayoutManager.HORIZONTAL,false);
        recyclerView.setLayoutManager(gridLayoutManager); // set LayoutManager to RecyclerView
        //  call the constructor of CustomAdapter to send the reference and data to Adapter
        CustomAdapter customAdapter = new CustomAdapter(MainActivity.this, personNames,personImages,colors);
        recyclerView.setAdapter(customAdapter); // set the Adapter to RecyclerView

        //to check if gps is enabled or not
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        fetchLastLocation();
        statusCheck();

        //app name on title bar
        getSupportActionBar().setTitle(Html.fromHtml("<font color='#000000'>Cropifier</font>"));


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

        //bottom nav menu bar
        CustomBottomNavigationView1 customBottomNavigationView1 = findViewById(R.id.customBottomNavBar);
        customBottomNavigationView1.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if(item.getItemId() == R.id.home){

                }
                else if(item.getItemId() == R.id.explore){
                    CreateAlertDialogWithRadioButtonGroup() ;
                }else if(item.getItemId() == R.id.livefeed){
                    openlivefeed();
                }else if(item.getItemId() == R.id.knowmore){
                    Intent intent = new Intent(MainActivity.this,KnowMore_Offline.class);
                    startActivity(intent);
                }
                return  false;
            }

        });
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.lang_menu, menu);
        MenuItem item = menu.findItem(R.id.translate);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.translate:
                showChangeLanguageDialog();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    // creating separate strings.xml for each language
    private void showChangeLanguageDialog() {
        final String[] Listitems = {"English", "ગુજરાતી", "हिन्दी", "मराठी"};
        androidx.appcompat.app.AlertDialog.Builder mBuilder = new androidx.appcompat.app.AlertDialog.Builder(MainActivity.this);
        mBuilder.setTitle(getString(R.string.choose_lang));
        mBuilder.setSingleChoiceItems(Listitems, -1, (dialogInterface, which) -> {
            if( which == 0){
                setLocale("en");
                recreate();
            }
            else if( which == 1){
                setLocale("gu");
                recreate();
            }
            else if( which == 2){
                setLocale("hi");
                recreate();
            }
            else if( which == 3){
                setLocale("mr");
                recreate();
            }
            //dismiss the dialog box
            dialogInterface.dismiss();

        });
        androidx.appcompat.app.AlertDialog mDialog = mBuilder.create();
        mDialog.show();
    }
    private void setLocale(String lang) {
        Locale locale = new Locale(lang);
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        getBaseContext().getResources().updateConfiguration(config, getBaseContext().getResources().getDisplayMetrics());
        SharedPreferences.Editor editor = getSharedPreferences("Settings", MODE_PRIVATE).edit();
        editor.putString("My_Lang", lang);
        editor.apply();



    }

    public void loadLocale(){
        SharedPreferences prefs = getSharedPreferences("Settings", Activity.MODE_PRIVATE);
        String language = prefs.getString("My_Lang", "" );
        setLocale(language);

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

                switch(item)
                {
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

    private void statusCheck() {

        final LocationManager manager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        if (!manager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            buildAlertMessageNoGps();

        }
    }

    private void buildAlertMessageNoGps() {
        final Dialog customDialog = new Dialog(MainActivity.this);
        customDialog.setContentView(R.layout.custom_dailog_layout);
        final LinearLayout ll_hidden;
        final Button know_more,OK,no_thanks;

        // using window set the hight and width of custom dialog
        Window window = customDialog.getWindow();
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        window.setGravity(Gravity.CENTER);
        customDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        // bind all controls with custom dialog
        know_more = (Button) customDialog.findViewById(R.id.know_more);
        OK = (Button) customDialog.findViewById(R.id.OK);
        no_thanks = (Button) customDialog.findViewById(R.id.no_thanks);
        OK.setOnClickListener(v -> startActivity(new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS)));
        no_thanks.setOnClickListener(v -> customDialog.cancel());
        ll_hidden = (LinearLayout) customDialog.findViewById(R.id.ll_hidden);
        ll_hidden.setVisibility(View.GONE);
        know_more.setOnClickListener(v -> {
//
            ll_hidden.setVisibility(View.VISIBLE);

        });
        customDialog.show();
    }

    private void fetchLastLocation() {

        if(ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this,Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this,new String[]
                    {Manifest.permission.ACCESS_FINE_LOCATION},REQUEST_CODE);
            return;

        }
        Task<Location> task = fusedLocationProviderClient.getLastLocation();
        task.addOnSuccessListener(location -> {
            if(location != null){
                currentlocation = location;

                double latitude = currentlocation.getLatitude();
                double longitude = currentlocation.getLongitude();
                String Label1 = latitude +","+longitude;
                String loc = currentlocation.getLatitude() +","+ currentlocation.getLatitude();
                SharedPreferences sharedPref = getSharedPreferences("myKey", MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPref.edit();
                editor.putString("value", loc);
                editor.apply();

                    Toast.makeText(getApplicationContext(),Label1,Toast.LENGTH_SHORT).show();


            }
        });


    }
    private void loadLanguage() {
// we can use this method to load language,
// this method should be called before setContentView() method of the onCreate method

        Locale locale = new Locale(getLangCode());
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        getBaseContext().getResources().updateConfiguration(config, getBaseContext().getResources().getDisplayMetrics());
    }
    private String getLangCode() {
        SharedPreferences preferences = getSharedPreferences("Settings", MODE_PRIVATE);
        String langCode = preferences.getString("My_Lang", "");
// save english ‘en’ as the default language
        return langCode;
    }


    private void openCameraIntent() {

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    REQUEST_PERMISSION);
        }

        // request permission to read data (aka images) from the user's external storage of their phone

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                    REQUEST_PERMISSION);
        }

        CropImage.activity().start(MainActivity.this);
    }

    // dictates what to do after the user takes an image, selects and image, or crops an image
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        // if the camera activity is finished, obtained the uri, crop it to make it square, and send it to 'Classify' activity


        if(requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE ) {

            CropImage.ActivityResult result = CropImage.getActivityResult(data);

            if (resultCode == RESULT_OK){
                imageUri = result.getUri();
                Intent i = new Intent(MainActivity.this,Classifier.class);
                // put image data in extras to send
                i.putExtra("resID_uri", imageUri);
                // put filename in extras
                i.putExtra("chosen", chosen);
                // put model type in extras
                i.putExtra("quant", quant);
                // send other required data
                startActivity(i);
            }else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE){
                Exception e = result.getError();
                Toast.makeText(this, "Possible error is ", Toast.LENGTH_SHORT).show();
            }
        }
    }

}
