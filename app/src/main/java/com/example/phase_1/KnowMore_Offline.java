package com.example.phase_1;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Html;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.GridLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import java.util.Locale;


public class KnowMore_Offline extends AppCompatActivity {

    GridLayout mainGrid;
    CardView bitter_cv1,chilli_cv1,coffee_cv1,cotton_cv1,indianM_cv1,lentil_cv1,maize_cv1,fenugreek_cv1,peas_cv1,
            pumpkin_cv1,rice_cv1,sesame_cv1,sorghum_cv1,soyabean_cv1,sugarcane_cv1,tea_cv1,tobacco_cv1,turmeric_cv1,wheat_cv1;
    TextView bitterG,chilly,coffee,cotton,lentils,maize,fenugreek,indianMustard,peas,pumpkin,rice,sesame,sorghum,soybean,sugarcane,tea,turmeric,tobacco,wheat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_know_more_offline);
        getSupportActionBar().setTitle(Html.fromHtml("<font color='#000000'>Cropifier</font>"));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null){
            actionBar.setBackgroundDrawable(getResources().getDrawable(R.drawable.actionbar_gradient));
        }
        bitterG =findViewById(R.id.tv1);
        chilly = findViewById(R.id.tv2);
        coffee = findViewById(R.id.tv3);
        cotton =findViewById(R.id.tv4);
        lentils = findViewById(R.id.tv5);
        maize = findViewById(R.id.tv6);
        fenugreek =findViewById(R.id.tv7);
        indianMustard = findViewById(R.id.tv8);
        peas = findViewById(R.id.tv9);
        pumpkin =findViewById(R.id.tv10);
        rice = findViewById(R.id.tv11);
        sesame = findViewById(R.id.tv12);
        sorghum = findViewById(R.id.tv13);
        soybean =findViewById(R.id.tv14);
        sugarcane = findViewById(R.id.tv15);
        tea = findViewById(R.id.tv16);
        tobacco = findViewById(R.id.tv17);
        turmeric =findViewById(R.id.tv18);
        wheat = findViewById(R.id.tv19);

        bitter_cv1 = findViewById(R.id.bitter_cv1);
        chilli_cv1 = findViewById(R.id.chilli_cv1);
        coffee_cv1 = findViewById(R.id.coffee_cv1);
        cotton_cv1 = findViewById(R.id.cotton_cv1);
        fenugreek_cv1 = findViewById(R.id.fenugreek_cv1);
        indianM_cv1 = findViewById(R.id.mustard_cv1);
        lentil_cv1 = findViewById(R.id.lentil_cv1);
        maize_cv1 = findViewById(R.id.maize_cv1);
        peas_cv1 = findViewById(R.id.peas_cv1);
        pumpkin_cv1 = findViewById(R.id.pum_cv1);
        rice_cv1 = findViewById(R.id.rice_cv1);
        sesame_cv1 = findViewById(R.id.sesame_cv1);
        sorghum_cv1 = findViewById(R.id.sorghum_cv1);
        soyabean_cv1 = findViewById(R.id.soybean_cv1);
        sugarcane_cv1 = findViewById(R.id.sugarcane_cv1);
        tea_cv1 = findViewById(R.id.tea_cv1);
        tobacco_cv1 = findViewById(R.id.tobacco_cv1);
        turmeric_cv1 = findViewById(R.id.turmeric_cv1);
        wheat_cv1 = findViewById(R.id.wheat_cv1);

//        bitter_cv1.setBackgroundColor(Color.parseColor("#e6ffe6"));
//        chilli_cv1.setBackgroundColor(Color.parseColor("#ffe6e6"));
//        coffee_cv1.setBackgroundColor(Color.parseColor("#ecd9c6"));
//        cotton_cv1.setBackgroundColor(Color.parseColor("#ffffff"));
//        fenugreek_cv1.setBackgroundColor(Color.parseColor("#e6ffe6"));
//        indianM_cv1.setBackgroundColor(Color.parseColor("#ffffcc"));
//        lentil_cv1.setBackgroundColor(Color.parseColor("#e6ffe6"));
//        maize_cv1.setBackgroundColor(Color.parseColor("#ffffcc"));
//        peas_cv1.setBackgroundColor(Color.parseColor("#e6ffe6"));
//        pumpkin_cv1.setBackgroundColor(Color.parseColor("#ffe6cc"));
//        rice_cv1.setBackgroundColor(Color.parseColor("#ffffff"));
//        sesame_cv1.setBackgroundColor(Color.parseColor("#ffffcc"));
//        sorghum_cv1.setBackgroundColor(Color.parseColor("#ffe6cc"));
//        soyabean_cv1.setBackgroundColor(Color.parseColor("#fff0b3"));
//        sugarcane_cv1.setBackgroundColor(Color.parseColor("#e6ffe6"));
//        tea_cv1.setBackgroundColor(Color.parseColor("#ffe6cc"));
//        tobacco_cv1.setBackgroundColor(Color.parseColor("#e6ffe6"));
//        turmeric_cv1.setBackgroundColor(Color.parseColor("#fff0b3"));
//        wheat_cv1.setBackgroundColor(Color.parseColor("#ffe6cc"));


        mainGrid = (GridLayout) findViewById(R.id.mainGrid);
        setSingleEvent(mainGrid);




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
        AlertDialog.Builder mBuilder = new AlertDialog.Builder(KnowMore_Offline.this);
        mBuilder.setTitle("Choose Language...");
        mBuilder.setSingleChoiceItems(Listitems, -1, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int which) {
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

            }
        });
        AlertDialog mDialog = mBuilder.create();
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

   private void setSingleEvent(GridLayout mainGrid){
        for(int i =0;i<mainGrid.getChildCount(); i++)
        {
            CardView cardView = (CardView)mainGrid.getChildAt(i);
            final int final1 = i;
            cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (final1 == 0){

                        bitter_cv1.setCardBackgroundColor(getResources().getColor(R.color.action2));
                        chilli_cv1.setCardBackgroundColor(getResources().getColor(R.color.white));
                        coffee_cv1.setCardBackgroundColor(getResources().getColor(R.color.white));
                        cotton_cv1.setCardBackgroundColor(getResources().getColor(R.color.white));
                        lentil_cv1.setCardBackgroundColor(getResources().getColor(R.color.white));
                        maize_cv1.setCardBackgroundColor(getResources().getColor(R.color.white));
                        fenugreek_cv1.setCardBackgroundColor(getResources().getColor(R.color.white));
                        indianM_cv1.setCardBackgroundColor(getResources().getColor(R.color.white));
                        peas_cv1.setCardBackgroundColor(getResources().getColor(R.color.white));
                        pumpkin_cv1.setCardBackgroundColor(getResources().getColor(R.color.white));
                        rice_cv1.setCardBackgroundColor(getResources().getColor(R.color.white));
                        sesame_cv1.setCardBackgroundColor(getResources().getColor(R.color.white));
                        sorghum_cv1.setCardBackgroundColor(getResources().getColor(R.color.white));
                        soyabean_cv1.setCardBackgroundColor(getResources().getColor(R.color.white));
                        sugarcane_cv1.setCardBackgroundColor(getResources().getColor(R.color.white));
                        tea_cv1.setCardBackgroundColor(getResources().getColor(R.color.white));
                        tobacco_cv1.setCardBackgroundColor(getResources().getColor(R.color.white));
                        turmeric_cv1.setCardBackgroundColor(getResources().getColor(R.color.white));
                        wheat_cv1.setCardBackgroundColor(getResources().getColor(R.color.white));

                        String cropName = bitterG.getText().toString();
                        Intent intent = new Intent(KnowMore_Offline.this, CropInfo.class);
                        intent.putExtra("cropName_key", cropName);
                        startActivity(intent);
//                        bitter_cv1.setCardBackgroundColor(getResources().getColor(R.color.white));
//                        Toast.makeText(KnowMore_Offline.this, R.string.bg_name, Toast.LENGTH_LONG).show();
                    }
                    else if (final1 == 1){
                        bitter_cv1.setCardBackgroundColor(getResources().getColor(R.color.white));
                        chilli_cv1.setCardBackgroundColor(getResources().getColor(R.color.action2));
                        coffee_cv1.setCardBackgroundColor(getResources().getColor(R.color.white));
                        cotton_cv1.setCardBackgroundColor(getResources().getColor(R.color.white));
                        lentil_cv1.setCardBackgroundColor(getResources().getColor(R.color.white));
                        maize_cv1.setCardBackgroundColor(getResources().getColor(R.color.white));
                        fenugreek_cv1.setCardBackgroundColor(getResources().getColor(R.color.white));
                        indianM_cv1.setCardBackgroundColor(getResources().getColor(R.color.white));
                        peas_cv1.setCardBackgroundColor(getResources().getColor(R.color.white));
                        pumpkin_cv1.setCardBackgroundColor(getResources().getColor(R.color.white));
                        rice_cv1.setCardBackgroundColor(getResources().getColor(R.color.white));
                        sesame_cv1.setCardBackgroundColor(getResources().getColor(R.color.white));
                        sorghum_cv1.setCardBackgroundColor(getResources().getColor(R.color.white));
                        soyabean_cv1.setCardBackgroundColor(getResources().getColor(R.color.white));
                        sugarcane_cv1.setCardBackgroundColor(getResources().getColor(R.color.white));
                        tea_cv1.setCardBackgroundColor(getResources().getColor(R.color.white));
                        tobacco_cv1.setCardBackgroundColor(getResources().getColor(R.color.white));
                        turmeric_cv1.setCardBackgroundColor(getResources().getColor(R.color.white));
                        wheat_cv1.setCardBackgroundColor(getResources().getColor(R.color.white));
                        String cropName = chilly.getText().toString();
                        Intent intent = new Intent(KnowMore_Offline.this, CropInfo.class);
                        intent.putExtra("cropName_key", cropName);
                        startActivity(intent);
//                        Toast.makeText(KnowMore_Offline.this, R.string.chilly_name, Toast.LENGTH_SHORT).show();
                    }
                    else if (final1 == 2){
                        bitter_cv1.setCardBackgroundColor(getResources().getColor(R.color.white));
                        chilli_cv1.setCardBackgroundColor(getResources().getColor(R.color.white));
                        coffee_cv1.setCardBackgroundColor(getResources().getColor(R.color.action2));
                        cotton_cv1.setCardBackgroundColor(getResources().getColor(R.color.white));
                        lentil_cv1.setCardBackgroundColor(getResources().getColor(R.color.white));
                        maize_cv1.setCardBackgroundColor(getResources().getColor(R.color.white));
                        fenugreek_cv1.setCardBackgroundColor(getResources().getColor(R.color.white));
                        indianM_cv1.setCardBackgroundColor(getResources().getColor(R.color.white));
                        peas_cv1.setCardBackgroundColor(getResources().getColor(R.color.white));
                        pumpkin_cv1.setCardBackgroundColor(getResources().getColor(R.color.white));
                        rice_cv1.setCardBackgroundColor(getResources().getColor(R.color.white));
                        sesame_cv1.setCardBackgroundColor(getResources().getColor(R.color.white));
                        sorghum_cv1.setCardBackgroundColor(getResources().getColor(R.color.white));
                        soyabean_cv1.setCardBackgroundColor(getResources().getColor(R.color.white));
                        sugarcane_cv1.setCardBackgroundColor(getResources().getColor(R.color.white));
                        tea_cv1.setCardBackgroundColor(getResources().getColor(R.color.white));
                        tobacco_cv1.setCardBackgroundColor(getResources().getColor(R.color.white));
                        turmeric_cv1.setCardBackgroundColor(getResources().getColor(R.color.white));
                        wheat_cv1.setCardBackgroundColor(getResources().getColor(R.color.white));
                        String cropName = coffee.getText().toString();
                        Intent intent = new Intent(KnowMore_Offline.this, CropInfo.class);
                        intent.putExtra("cropName_key", cropName);
                        startActivity(intent);
//                        Toast.makeText(KnowMore_Offline.this, R.string.coffee_name, Toast.LENGTH_SHORT).show();
                    }
                    else if (final1 == 3){
                        bitter_cv1.setCardBackgroundColor(getResources().getColor(R.color.white));
                        chilli_cv1.setCardBackgroundColor(getResources().getColor(R.color.white));
                        coffee_cv1.setCardBackgroundColor(getResources().getColor(R.color.white));
                        cotton_cv1.setCardBackgroundColor(getResources().getColor(R.color.action2));
                        lentil_cv1.setCardBackgroundColor(getResources().getColor(R.color.white));
                        maize_cv1.setCardBackgroundColor(getResources().getColor(R.color.white));
                        fenugreek_cv1.setCardBackgroundColor(getResources().getColor(R.color.white));
                        indianM_cv1.setCardBackgroundColor(getResources().getColor(R.color.white));
                        peas_cv1.setCardBackgroundColor(getResources().getColor(R.color.white));
                        pumpkin_cv1.setCardBackgroundColor(getResources().getColor(R.color.white));
                        rice_cv1.setCardBackgroundColor(getResources().getColor(R.color.white));
                        sesame_cv1.setCardBackgroundColor(getResources().getColor(R.color.white));
                        sorghum_cv1.setCardBackgroundColor(getResources().getColor(R.color.white));
                        soyabean_cv1.setCardBackgroundColor(getResources().getColor(R.color.white));
                        sugarcane_cv1.setCardBackgroundColor(getResources().getColor(R.color.white));
                        tea_cv1.setCardBackgroundColor(getResources().getColor(R.color.white));
                        tobacco_cv1.setCardBackgroundColor(getResources().getColor(R.color.white));
                        turmeric_cv1.setCardBackgroundColor(getResources().getColor(R.color.white));
                        wheat_cv1.setCardBackgroundColor(getResources().getColor(R.color.white));
                        String cropName = cotton.getText().toString();
                        Intent intent = new Intent(KnowMore_Offline.this, CropInfo.class);
                        intent.putExtra("cropName_key", cropName);
                        startActivity(intent);
//                        Toast.makeText(KnowMore_Offline.this, R.string.cotton_name, Toast.LENGTH_SHORT).show();
                    }
                    else if (final1 == 4){
                        bitter_cv1.setCardBackgroundColor(getResources().getColor(R.color.white));
                        chilli_cv1.setCardBackgroundColor(getResources().getColor(R.color.white));
                        coffee_cv1.setCardBackgroundColor(getResources().getColor(R.color.white));
                        cotton_cv1.setCardBackgroundColor(getResources().getColor(R.color.white));
                        lentil_cv1.setCardBackgroundColor(getResources().getColor(R.color.action2));
                        maize_cv1.setCardBackgroundColor(getResources().getColor(R.color.white));
                        fenugreek_cv1.setCardBackgroundColor(getResources().getColor(R.color.white));
                        indianM_cv1.setCardBackgroundColor(getResources().getColor(R.color.white));
                        peas_cv1.setCardBackgroundColor(getResources().getColor(R.color.white));
                        pumpkin_cv1.setCardBackgroundColor(getResources().getColor(R.color.white));
                        rice_cv1.setCardBackgroundColor(getResources().getColor(R.color.white));
                        sesame_cv1.setCardBackgroundColor(getResources().getColor(R.color.white));
                        sorghum_cv1.setCardBackgroundColor(getResources().getColor(R.color.white));
                        soyabean_cv1.setCardBackgroundColor(getResources().getColor(R.color.white));
                        sugarcane_cv1.setCardBackgroundColor(getResources().getColor(R.color.white));
                        tea_cv1.setCardBackgroundColor(getResources().getColor(R.color.white));
                        tobacco_cv1.setCardBackgroundColor(getResources().getColor(R.color.white));
                        turmeric_cv1.setCardBackgroundColor(getResources().getColor(R.color.white));
                        wheat_cv1.setCardBackgroundColor(getResources().getColor(R.color.white));
                        String cropName = lentils.getText().toString();
                        Intent intent = new Intent(KnowMore_Offline.this, CropInfo.class);
                        intent.putExtra("cropName_key", cropName);
                        startActivity(intent);
//                        Toast.makeText(KnowMore_Offline.this, R.string.lentil_name, Toast.LENGTH_SHORT).show();
                    }
                    else if (final1 == 5){
                        bitter_cv1.setCardBackgroundColor(getResources().getColor(R.color.white));
                        chilli_cv1.setCardBackgroundColor(getResources().getColor(R.color.white));
                        coffee_cv1.setCardBackgroundColor(getResources().getColor(R.color.white));
                        cotton_cv1.setCardBackgroundColor(getResources().getColor(R.color.white));
                        lentil_cv1.setCardBackgroundColor(getResources().getColor(R.color.white));
                        maize_cv1.setCardBackgroundColor(getResources().getColor(R.color.action2));
                        fenugreek_cv1.setCardBackgroundColor(getResources().getColor(R.color.white));
                        indianM_cv1.setCardBackgroundColor(getResources().getColor(R.color.white));
                        peas_cv1.setCardBackgroundColor(getResources().getColor(R.color.white));
                        pumpkin_cv1.setCardBackgroundColor(getResources().getColor(R.color.white));
                        rice_cv1.setCardBackgroundColor(getResources().getColor(R.color.white));
                        sesame_cv1.setCardBackgroundColor(getResources().getColor(R.color.white));
                        sorghum_cv1.setCardBackgroundColor(getResources().getColor(R.color.white));
                        soyabean_cv1.setCardBackgroundColor(getResources().getColor(R.color.white));
                        sugarcane_cv1.setCardBackgroundColor(getResources().getColor(R.color.white));
                        tea_cv1.setCardBackgroundColor(getResources().getColor(R.color.white));
                        tobacco_cv1.setCardBackgroundColor(getResources().getColor(R.color.white));
                        turmeric_cv1.setCardBackgroundColor(getResources().getColor(R.color.white));
                        wheat_cv1.setCardBackgroundColor(getResources().getColor(R.color.white));
                        String cropName = maize.getText().toString();
                        Intent intent = new Intent(KnowMore_Offline.this, CropInfo.class);
                        intent.putExtra("cropName_key", cropName);
                        startActivity(intent);
//                        Toast.makeText(KnowMore_Offline.this, R.string.maize_name, Toast.LENGTH_SHORT).show();
                    }
                    else if (final1 == 6){
                        bitter_cv1.setCardBackgroundColor(getResources().getColor(R.color.white));
                        chilli_cv1.setCardBackgroundColor(getResources().getColor(R.color.white));
                        coffee_cv1.setCardBackgroundColor(getResources().getColor(R.color.white));
                        cotton_cv1.setCardBackgroundColor(getResources().getColor(R.color.white));
                        lentil_cv1.setCardBackgroundColor(getResources().getColor(R.color.white));
                        maize_cv1.setCardBackgroundColor(getResources().getColor(R.color.white));
                        fenugreek_cv1.setCardBackgroundColor(getResources().getColor(R.color.action2));
                        indianM_cv1.setCardBackgroundColor(getResources().getColor(R.color.white));
                        peas_cv1.setCardBackgroundColor(getResources().getColor(R.color.white));
                        pumpkin_cv1.setCardBackgroundColor(getResources().getColor(R.color.white));
                        rice_cv1.setCardBackgroundColor(getResources().getColor(R.color.white));
                        sesame_cv1.setCardBackgroundColor(getResources().getColor(R.color.white));
                        sorghum_cv1.setCardBackgroundColor(getResources().getColor(R.color.white));
                        soyabean_cv1.setCardBackgroundColor(getResources().getColor(R.color.white));
                        sugarcane_cv1.setCardBackgroundColor(getResources().getColor(R.color.white));
                        tea_cv1.setCardBackgroundColor(getResources().getColor(R.color.white));
                        tobacco_cv1.setCardBackgroundColor(getResources().getColor(R.color.white));
                        turmeric_cv1.setCardBackgroundColor(getResources().getColor(R.color.white));
                        wheat_cv1.setCardBackgroundColor(getResources().getColor(R.color.white));
                        String cropName = fenugreek.getText().toString();
                        Intent intent = new Intent(KnowMore_Offline.this, CropInfo.class);
                        intent.putExtra("cropName_key", cropName);
                        startActivity(intent);
//                        Toast.makeText(KnowMore_Offline.this, R.string.methi_name, Toast.LENGTH_SHORT).show();
                    }
                    else if (final1 == 7){
                        bitter_cv1.setCardBackgroundColor(getResources().getColor(R.color.white));
                        chilli_cv1.setCardBackgroundColor(getResources().getColor(R.color.white));
                        coffee_cv1.setCardBackgroundColor(getResources().getColor(R.color.white));
                        cotton_cv1.setCardBackgroundColor(getResources().getColor(R.color.white));
                        lentil_cv1.setCardBackgroundColor(getResources().getColor(R.color.white));
                        maize_cv1.setCardBackgroundColor(getResources().getColor(R.color.white));
                        fenugreek_cv1.setCardBackgroundColor(getResources().getColor(R.color.white));
                        indianM_cv1.setCardBackgroundColor(getResources().getColor(R.color.white));
                        peas_cv1.setCardBackgroundColor(getResources().getColor(R.color.white));
                        pumpkin_cv1.setCardBackgroundColor(getResources().getColor(R.color.white));
                        rice_cv1.setCardBackgroundColor(getResources().getColor(R.color.white));
                        sesame_cv1.setCardBackgroundColor(getResources().getColor(R.color.white));
                        sorghum_cv1.setCardBackgroundColor(getResources().getColor(R.color.white));
                        soyabean_cv1.setCardBackgroundColor(getResources().getColor(R.color.white));
                        sugarcane_cv1.setCardBackgroundColor(getResources().getColor(R.color.white));
                        tea_cv1.setCardBackgroundColor(getResources().getColor(R.color.white));
                        tobacco_cv1.setCardBackgroundColor(getResources().getColor(R.color.white));
                        turmeric_cv1.setCardBackgroundColor(getResources().getColor(R.color.white));
                        wheat_cv1.setCardBackgroundColor(getResources().getColor(R.color.action2));
                        String cropName = wheat.getText().toString();
                        Intent intent = new Intent(KnowMore_Offline.this, CropInfo.class);
                        intent.putExtra("cropName_key", cropName);
                        startActivity(intent);
//                        Toast.makeText(KnowMore_Offline.this, R.string.wheat_name, Toast.LENGTH_SHORT).show();
                    }
                    else if (final1 == 8){
                        bitter_cv1.setCardBackgroundColor(getResources().getColor(R.color.action2));
                        chilli_cv1.setCardBackgroundColor(getResources().getColor(R.color.white));
                        coffee_cv1.setCardBackgroundColor(getResources().getColor(R.color.white));
                        cotton_cv1.setCardBackgroundColor(getResources().getColor(R.color.white));
                        lentil_cv1.setCardBackgroundColor(getResources().getColor(R.color.white));
                        maize_cv1.setCardBackgroundColor(getResources().getColor(R.color.white));
                        fenugreek_cv1.setCardBackgroundColor(getResources().getColor(R.color.white));
                        indianM_cv1.setCardBackgroundColor(getResources().getColor(R.color.white));
                        peas_cv1.setCardBackgroundColor(getResources().getColor(R.color.action2));
                        pumpkin_cv1.setCardBackgroundColor(getResources().getColor(R.color.white));
                        rice_cv1.setCardBackgroundColor(getResources().getColor(R.color.white));
                        sesame_cv1.setCardBackgroundColor(getResources().getColor(R.color.white));
                        sorghum_cv1.setCardBackgroundColor(getResources().getColor(R.color.white));
                        soyabean_cv1.setCardBackgroundColor(getResources().getColor(R.color.white));
                        sugarcane_cv1.setCardBackgroundColor(getResources().getColor(R.color.white));
                        tea_cv1.setCardBackgroundColor(getResources().getColor(R.color.white));
                        tobacco_cv1.setCardBackgroundColor(getResources().getColor(R.color.white));
                        turmeric_cv1.setCardBackgroundColor(getResources().getColor(R.color.white));
                        wheat_cv1.setCardBackgroundColor(getResources().getColor(R.color.white));
                        String cropName = peas.getText().toString();
                        Intent intent = new Intent(KnowMore_Offline.this, CropInfo.class);
                        intent.putExtra("cropName_key", cropName);
                        startActivity(intent);
//                        Toast.makeText(KnowMore_Offline.this, R.string.peas_name, Toast.LENGTH_SHORT).show();
                    }
                    else if (final1 == 9){
                        bitter_cv1.setCardBackgroundColor(getResources().getColor(R.color.white));
                        chilli_cv1.setCardBackgroundColor(getResources().getColor(R.color.white));
                        coffee_cv1.setCardBackgroundColor(getResources().getColor(R.color.white));
                        cotton_cv1.setCardBackgroundColor(getResources().getColor(R.color.white));
                        lentil_cv1.setCardBackgroundColor(getResources().getColor(R.color.white));
                        maize_cv1.setCardBackgroundColor(getResources().getColor(R.color.white));
                        fenugreek_cv1.setCardBackgroundColor(getResources().getColor(R.color.white));
                        indianM_cv1.setCardBackgroundColor(getResources().getColor(R.color.white));
                        peas_cv1.setCardBackgroundColor(getResources().getColor(R.color.white));
                        pumpkin_cv1.setCardBackgroundColor(getResources().getColor(R.color.action2));
                        rice_cv1.setCardBackgroundColor(getResources().getColor(R.color.white));
                        sesame_cv1.setCardBackgroundColor(getResources().getColor(R.color.white));
                        sorghum_cv1.setCardBackgroundColor(getResources().getColor(R.color.white));
                        soyabean_cv1.setCardBackgroundColor(getResources().getColor(R.color.white));
                        sugarcane_cv1.setCardBackgroundColor(getResources().getColor(R.color.white));
                        tea_cv1.setCardBackgroundColor(getResources().getColor(R.color.white));
                        tobacco_cv1.setCardBackgroundColor(getResources().getColor(R.color.white));
                        turmeric_cv1.setCardBackgroundColor(getResources().getColor(R.color.white));
                        wheat_cv1.setCardBackgroundColor(getResources().getColor(R.color.white));
                        String cropName = pumpkin.getText().toString();
                        Intent intent = new Intent(KnowMore_Offline.this, CropInfo.class);
                        intent.putExtra("cropName_key", cropName);
                        startActivity(intent);
//                        Toast.makeText(KnowMore_Offline.this, R.string.pumpkin_name, Toast.LENGTH_SHORT).show();
                    }
                    else if (final1 == 10){
                        bitter_cv1.setCardBackgroundColor(getResources().getColor(R.color.white));
                        chilli_cv1.setCardBackgroundColor(getResources().getColor(R.color.white));
                        coffee_cv1.setCardBackgroundColor(getResources().getColor(R.color.white));
                        cotton_cv1.setCardBackgroundColor(getResources().getColor(R.color.white));
                        lentil_cv1.setCardBackgroundColor(getResources().getColor(R.color.white));
                        maize_cv1.setCardBackgroundColor(getResources().getColor(R.color.white));
                        fenugreek_cv1.setCardBackgroundColor(getResources().getColor(R.color.white));
                        indianM_cv1.setCardBackgroundColor(getResources().getColor(R.color.white));
                        peas_cv1.setCardBackgroundColor(getResources().getColor(R.color.white));
                        pumpkin_cv1.setCardBackgroundColor(getResources().getColor(R.color.white));
                        rice_cv1.setCardBackgroundColor(getResources().getColor(R.color.action2));
                        sesame_cv1.setCardBackgroundColor(getResources().getColor(R.color.white));
                        sorghum_cv1.setCardBackgroundColor(getResources().getColor(R.color.white));
                        soyabean_cv1.setCardBackgroundColor(getResources().getColor(R.color.white));
                        sugarcane_cv1.setCardBackgroundColor(getResources().getColor(R.color.white));
                        tea_cv1.setCardBackgroundColor(getResources().getColor(R.color.white));
                        tobacco_cv1.setCardBackgroundColor(getResources().getColor(R.color.white));
                        turmeric_cv1.setCardBackgroundColor(getResources().getColor(R.color.white));
                        wheat_cv1.setCardBackgroundColor(getResources().getColor(R.color.white));
                        String cropName = rice.getText().toString();
                        Intent intent = new Intent(KnowMore_Offline.this, CropInfo.class);
                        intent.putExtra("cropName_key", cropName);
                        startActivity(intent);
//                        Toast.makeText(KnowMore_Offline.this, R.string.rice_name, Toast.LENGTH_SHORT).show();
                    }
                    else if (final1 == 11){
                        bitter_cv1.setCardBackgroundColor(getResources().getColor(R.color.white));
                        chilli_cv1.setCardBackgroundColor(getResources().getColor(R.color.white));
                        coffee_cv1.setCardBackgroundColor(getResources().getColor(R.color.white));
                        cotton_cv1.setCardBackgroundColor(getResources().getColor(R.color.white));
                        lentil_cv1.setCardBackgroundColor(getResources().getColor(R.color.white));
                        maize_cv1.setCardBackgroundColor(getResources().getColor(R.color.white));
                        fenugreek_cv1.setCardBackgroundColor(getResources().getColor(R.color.white));
                        indianM_cv1.setCardBackgroundColor(getResources().getColor(R.color.white));
                        peas_cv1.setCardBackgroundColor(getResources().getColor(R.color.white));
                        pumpkin_cv1.setCardBackgroundColor(getResources().getColor(R.color.white));
                        rice_cv1.setCardBackgroundColor(getResources().getColor(R.color.white));
                        sesame_cv1.setCardBackgroundColor(getResources().getColor(R.color.action2));
                        sorghum_cv1.setCardBackgroundColor(getResources().getColor(R.color.white));
                        soyabean_cv1.setCardBackgroundColor(getResources().getColor(R.color.white));
                        sugarcane_cv1.setCardBackgroundColor(getResources().getColor(R.color.white));
                        tea_cv1.setCardBackgroundColor(getResources().getColor(R.color.white));
                        tobacco_cv1.setCardBackgroundColor(getResources().getColor(R.color.white));
                        turmeric_cv1.setCardBackgroundColor(getResources().getColor(R.color.white));
                        wheat_cv1.setCardBackgroundColor(getResources().getColor(R.color.white));
                        String cropName = sesame.getText().toString();
                        Intent intent = new Intent(KnowMore_Offline.this, CropInfo.class);
                        intent.putExtra("cropName_key", cropName);
                        startActivity(intent);
//                        Toast.makeText(KnowMore_Offline.this, R.string.sesame_name, Toast.LENGTH_SHORT).show();
                    }
                    else if (final1 == 12){
                        bitter_cv1.setCardBackgroundColor(getResources().getColor(R.color.white));
                        chilli_cv1.setCardBackgroundColor(getResources().getColor(R.color.white));
                        coffee_cv1.setCardBackgroundColor(getResources().getColor(R.color.white));
                        cotton_cv1.setCardBackgroundColor(getResources().getColor(R.color.white));
                        lentil_cv1.setCardBackgroundColor(getResources().getColor(R.color.white));
                        maize_cv1.setCardBackgroundColor(getResources().getColor(R.color.white));
                        fenugreek_cv1.setCardBackgroundColor(getResources().getColor(R.color.white));
                        indianM_cv1.setCardBackgroundColor(getResources().getColor(R.color.white));
                        peas_cv1.setCardBackgroundColor(getResources().getColor(R.color.white));
                        pumpkin_cv1.setCardBackgroundColor(getResources().getColor(R.color.white));
                        rice_cv1.setCardBackgroundColor(getResources().getColor(R.color.white));
                        sesame_cv1.setCardBackgroundColor(getResources().getColor(R.color.white));
                        sorghum_cv1.setCardBackgroundColor(getResources().getColor(R.color.action2));
                        soyabean_cv1.setCardBackgroundColor(getResources().getColor(R.color.white));
                        sugarcane_cv1.setCardBackgroundColor(getResources().getColor(R.color.white));
                        tea_cv1.setCardBackgroundColor(getResources().getColor(R.color.white));
                        tobacco_cv1.setCardBackgroundColor(getResources().getColor(R.color.white));
                        turmeric_cv1.setCardBackgroundColor(getResources().getColor(R.color.white));
                        wheat_cv1.setCardBackgroundColor(getResources().getColor(R.color.white));
                        String cropName = sorghum.getText().toString();
                        Intent intent = new Intent(KnowMore_Offline.this, CropInfo.class);
                        intent.putExtra("cropName_key", cropName);
                        startActivity(intent);
//                        Toast.makeText(KnowMore_Offline.this, R.string.sorghum_name, Toast.LENGTH_SHORT).show();
                    }
                    else if (final1 == 13){
                        bitter_cv1.setCardBackgroundColor(getResources().getColor(R.color.white));
                        chilli_cv1.setCardBackgroundColor(getResources().getColor(R.color.white));
                        coffee_cv1.setCardBackgroundColor(getResources().getColor(R.color.white));
                        cotton_cv1.setCardBackgroundColor(getResources().getColor(R.color.white));
                        lentil_cv1.setCardBackgroundColor(getResources().getColor(R.color.white));
                        maize_cv1.setCardBackgroundColor(getResources().getColor(R.color.white));
                        fenugreek_cv1.setCardBackgroundColor(getResources().getColor(R.color.white));
                        indianM_cv1.setCardBackgroundColor(getResources().getColor(R.color.white));
                        peas_cv1.setCardBackgroundColor(getResources().getColor(R.color.white));
                        pumpkin_cv1.setCardBackgroundColor(getResources().getColor(R.color.white));
                        rice_cv1.setCardBackgroundColor(getResources().getColor(R.color.white));
                        sesame_cv1.setCardBackgroundColor(getResources().getColor(R.color.white));
                        sorghum_cv1.setCardBackgroundColor(getResources().getColor(R.color.white));
                        soyabean_cv1.setCardBackgroundColor(getResources().getColor(R.color.action2));
                        sugarcane_cv1.setCardBackgroundColor(getResources().getColor(R.color.white));
                        tea_cv1.setCardBackgroundColor(getResources().getColor(R.color.white));
                        tobacco_cv1.setCardBackgroundColor(getResources().getColor(R.color.white));
                        turmeric_cv1.setCardBackgroundColor(getResources().getColor(R.color.white));
                        wheat_cv1.setCardBackgroundColor(getResources().getColor(R.color.white));
                        String cropName = soybean.getText().toString();
                        Intent intent = new Intent(KnowMore_Offline.this, CropInfo.class);
                        intent.putExtra("cropName_key", cropName);
                        startActivity(intent);
//                        Toast.makeText(KnowMore_Offline.this, R.string.soybean_name, Toast.LENGTH_SHORT).show();
                    }
                    else if (final1 == 14){
                        bitter_cv1.setCardBackgroundColor(getResources().getColor(R.color.white));
                        chilli_cv1.setCardBackgroundColor(getResources().getColor(R.color.white));
                        coffee_cv1.setCardBackgroundColor(getResources().getColor(R.color.white));
                        cotton_cv1.setCardBackgroundColor(getResources().getColor(R.color.white));
                        lentil_cv1.setCardBackgroundColor(getResources().getColor(R.color.white));
                        maize_cv1.setCardBackgroundColor(getResources().getColor(R.color.white));
                        fenugreek_cv1.setCardBackgroundColor(getResources().getColor(R.color.white));
                        indianM_cv1.setCardBackgroundColor(getResources().getColor(R.color.white));
                        peas_cv1.setCardBackgroundColor(getResources().getColor(R.color.white));
                        pumpkin_cv1.setCardBackgroundColor(getResources().getColor(R.color.white));
                        rice_cv1.setCardBackgroundColor(getResources().getColor(R.color.white));
                        sesame_cv1.setCardBackgroundColor(getResources().getColor(R.color.white));
                        sorghum_cv1.setCardBackgroundColor(getResources().getColor(R.color.white));
                        soyabean_cv1.setCardBackgroundColor(getResources().getColor(R.color.white));
                        sugarcane_cv1.setCardBackgroundColor(getResources().getColor(R.color.action2));
                        tea_cv1.setCardBackgroundColor(getResources().getColor(R.color.white));
                        tobacco_cv1.setCardBackgroundColor(getResources().getColor(R.color.white));
                        turmeric_cv1.setCardBackgroundColor(getResources().getColor(R.color.white));
                        wheat_cv1.setCardBackgroundColor(getResources().getColor(R.color.white));
                        String cropName = sugarcane.getText().toString();
                        Intent intent = new Intent(KnowMore_Offline.this, CropInfo.class);
                        intent.putExtra("cropName_key", cropName);
                        startActivity(intent);
//                        Toast.makeText(KnowMore_Offline.this, R.string.sugarcane_name, Toast.LENGTH_SHORT).show();
                    }
                    else if (final1 == 15){
                        bitter_cv1.setCardBackgroundColor(getResources().getColor(R.color.white));
                        chilli_cv1.setCardBackgroundColor(getResources().getColor(R.color.white));
                        coffee_cv1.setCardBackgroundColor(getResources().getColor(R.color.white));
                        cotton_cv1.setCardBackgroundColor(getResources().getColor(R.color.white));
                        lentil_cv1.setCardBackgroundColor(getResources().getColor(R.color.white));
                        maize_cv1.setCardBackgroundColor(getResources().getColor(R.color.white));
                        fenugreek_cv1.setCardBackgroundColor(getResources().getColor(R.color.white));
                        indianM_cv1.setCardBackgroundColor(getResources().getColor(R.color.white));
                        peas_cv1.setCardBackgroundColor(getResources().getColor(R.color.white));
                        pumpkin_cv1.setCardBackgroundColor(getResources().getColor(R.color.white));
                        rice_cv1.setCardBackgroundColor(getResources().getColor(R.color.white));
                        sesame_cv1.setCardBackgroundColor(getResources().getColor(R.color.white));
                        sorghum_cv1.setCardBackgroundColor(getResources().getColor(R.color.white));
                        soyabean_cv1.setCardBackgroundColor(getResources().getColor(R.color.white));
                        sugarcane_cv1.setCardBackgroundColor(getResources().getColor(R.color.white));
                        tea_cv1.setCardBackgroundColor(getResources().getColor(R.color.action2));
                        tobacco_cv1.setCardBackgroundColor(getResources().getColor(R.color.white));
                        turmeric_cv1.setCardBackgroundColor(getResources().getColor(R.color.white));
                        wheat_cv1.setCardBackgroundColor(getResources().getColor(R.color.white));
                        String cropName = tea.getText().toString();
                        Intent intent = new Intent(KnowMore_Offline.this, CropInfo.class);
                        intent.putExtra("cropName_key", cropName);
                        startActivity(intent);
//                        Toast.makeText(KnowMore_Offline.this, R.string.tea_name, Toast.LENGTH_SHORT).show();
                    }
                    else if (final1 == 16){
                        bitter_cv1.setCardBackgroundColor(getResources().getColor(R.color.white));
                        chilli_cv1.setCardBackgroundColor(getResources().getColor(R.color.white));
                        coffee_cv1.setCardBackgroundColor(getResources().getColor(R.color.white));
                        cotton_cv1.setCardBackgroundColor(getResources().getColor(R.color.white));
                        lentil_cv1.setCardBackgroundColor(getResources().getColor(R.color.white));
                        maize_cv1.setCardBackgroundColor(getResources().getColor(R.color.white));
                        fenugreek_cv1.setCardBackgroundColor(getResources().getColor(R.color.white));
                        indianM_cv1.setCardBackgroundColor(getResources().getColor(R.color.white));
                        peas_cv1.setCardBackgroundColor(getResources().getColor(R.color.white));
                        pumpkin_cv1.setCardBackgroundColor(getResources().getColor(R.color.white));
                        rice_cv1.setCardBackgroundColor(getResources().getColor(R.color.white));
                        sesame_cv1.setCardBackgroundColor(getResources().getColor(R.color.white));
                        sorghum_cv1.setCardBackgroundColor(getResources().getColor(R.color.white));
                        soyabean_cv1.setCardBackgroundColor(getResources().getColor(R.color.white));
                        sugarcane_cv1.setCardBackgroundColor(getResources().getColor(R.color.white));
                        tea_cv1.setCardBackgroundColor(getResources().getColor(R.color.white));
                        tobacco_cv1.setCardBackgroundColor(getResources().getColor(R.color.action2));
                        turmeric_cv1.setCardBackgroundColor(getResources().getColor(R.color.white));
                        wheat_cv1.setCardBackgroundColor(getResources().getColor(R.color.white));
                        String cropName = tobacco.getText().toString();
                        Intent intent = new Intent(KnowMore_Offline.this, CropInfo.class);
                        intent.putExtra("cropName_key", cropName);
                        startActivity(intent);
//                        Toast.makeText(KnowMore_Offline.this, R.string.turmeric_name, Toast.LENGTH_SHORT).show();
                    }
                    else if (final1 == 17){
                        bitter_cv1.setCardBackgroundColor(getResources().getColor(R.color.white));
                        chilli_cv1.setCardBackgroundColor(getResources().getColor(R.color.white));
                        coffee_cv1.setCardBackgroundColor(getResources().getColor(R.color.white));
                        cotton_cv1.setCardBackgroundColor(getResources().getColor(R.color.white));
                        lentil_cv1.setCardBackgroundColor(getResources().getColor(R.color.white));
                        maize_cv1.setCardBackgroundColor(getResources().getColor(R.color.white));
                        fenugreek_cv1.setCardBackgroundColor(getResources().getColor(R.color.white));
                        indianM_cv1.setCardBackgroundColor(getResources().getColor(R.color.white));
                        peas_cv1.setCardBackgroundColor(getResources().getColor(R.color.white));
                        pumpkin_cv1.setCardBackgroundColor(getResources().getColor(R.color.white));
                        rice_cv1.setCardBackgroundColor(getResources().getColor(R.color.white));
                        sesame_cv1.setCardBackgroundColor(getResources().getColor(R.color.white));
                        sorghum_cv1.setCardBackgroundColor(getResources().getColor(R.color.white));
                        soyabean_cv1.setCardBackgroundColor(getResources().getColor(R.color.white));
                        sugarcane_cv1.setCardBackgroundColor(getResources().getColor(R.color.white));
                        tea_cv1.setCardBackgroundColor(getResources().getColor(R.color.white));
                        tobacco_cv1.setCardBackgroundColor(getResources().getColor(R.color.white));
                        turmeric_cv1.setCardBackgroundColor(getResources().getColor(R.color.action2));
                        wheat_cv1.setCardBackgroundColor(getResources().getColor(R.color.white));
                        String cropName = turmeric.getText().toString();
                        Intent intent = new Intent(KnowMore_Offline.this, CropInfo.class);
                        intent.putExtra("cropName_key", cropName);
                        startActivity(intent);
//                        Toast.makeText(KnowMore_Offline.this, R.string.tobacco_name, Toast.LENGTH_SHORT).show();
                    }
                    else if (final1 == 18){
                        bitter_cv1.setCardBackgroundColor(getResources().getColor(R.color.white));
                        chilli_cv1.setCardBackgroundColor(getResources().getColor(R.color.white));
                        coffee_cv1.setCardBackgroundColor(getResources().getColor(R.color.white));
                        cotton_cv1.setCardBackgroundColor(getResources().getColor(R.color.white));
                        lentil_cv1.setCardBackgroundColor(getResources().getColor(R.color.white));
                        maize_cv1.setCardBackgroundColor(getResources().getColor(R.color.white));
                        fenugreek_cv1.setCardBackgroundColor(getResources().getColor(R.color.white));
                        indianM_cv1.setCardBackgroundColor(getResources().getColor(R.color.action2));
                        peas_cv1.setCardBackgroundColor(getResources().getColor(R.color.white));
                        pumpkin_cv1.setCardBackgroundColor(getResources().getColor(R.color.white));
                        rice_cv1.setCardBackgroundColor(getResources().getColor(R.color.white));
                        sesame_cv1.setCardBackgroundColor(getResources().getColor(R.color.white));
                        sorghum_cv1.setCardBackgroundColor(getResources().getColor(R.color.white));
                        soyabean_cv1.setCardBackgroundColor(getResources().getColor(R.color.white));
                        sugarcane_cv1.setCardBackgroundColor(getResources().getColor(R.color.white));
                        tea_cv1.setCardBackgroundColor(getResources().getColor(R.color.white));
                        tobacco_cv1.setCardBackgroundColor(getResources().getColor(R.color.white));
                        turmeric_cv1.setCardBackgroundColor(getResources().getColor(R.color.white));
                        wheat_cv1.setCardBackgroundColor(getResources().getColor(R.color.white));
                        String cropName = indianMustard.getText().toString();
                        Intent intent = new Intent(KnowMore_Offline.this, CropInfo.class);
                        intent.putExtra("cropName_key", cropName);
                        startActivity(intent);
//                        Toast.makeText(KnowMore_Offline.this, R.string.mustard_name, Toast.LENGTH_SHORT).show();
                    }


                }
            });
        }
   }

}
