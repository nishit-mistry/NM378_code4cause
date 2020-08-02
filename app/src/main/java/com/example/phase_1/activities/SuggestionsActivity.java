package com.example.phase_1.activities;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.example.phase_1.MainActivity;
import com.example.phase_1.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;


public class SuggestionsActivity extends AppCompatActivity {
    AlertDialog alertDialog1;
    CardView bitter_cv,chilli_cv,coffee_cv,cotton_cv,indianM_cv,lentil_cv,maize_cv,fenugreek_cv,peas_cv,
            pumpkin_cv,rice_cv,sesame_cv,sorghum_cv,soyabean_cv,sugarcane_cv,tea_cv,tobacco_cv,turmeric_cv,wheat_cv;
    TextView tv_todayTemp,tv_todayHumid;
    String unit,todayTemp,todayHumid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_suggestions);

        getSupportActionBar().setTitle(Html.fromHtml("<font color='#000000'>Cropifier</font>"));
        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null){
            actionBar.setBackgroundDrawable(getResources().getDrawable(R.drawable.actionbar_gradient));
        }

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(SuggestionsActivity.this, MainActivity.class);
                startActivity(i);
            }
        });

        bitter_cv = findViewById(R.id.bitter_cv);
        chilli_cv = findViewById(R.id.chilli_cv);
        coffee_cv = findViewById(R.id.coffee_cv);
        cotton_cv = findViewById(R.id.cotton_cv);
        fenugreek_cv = findViewById(R.id.methi_cv);
        indianM_cv = findViewById(R.id.rai_cv);
        lentil_cv = findViewById(R.id.lentil_cv);
        maize_cv = findViewById(R.id.maize_cv);
        peas_cv = findViewById(R.id.peas_cv);
        pumpkin_cv = findViewById(R.id.pum_cv);
        rice_cv = findViewById(R.id.rice_cv);
        sesame_cv = findViewById(R.id.sesame_cv);
        sorghum_cv = findViewById(R.id.sor_cv);
        soyabean_cv = findViewById(R.id.soy_cv);
        sugarcane_cv = findViewById(R.id.sugar_cv);
        tea_cv = findViewById(R.id.tea_cv);
        tobacco_cv = findViewById(R.id.tob_cv);
        turmeric_cv = findViewById(R.id.tur_cv);
        wheat_cv = findViewById(R.id.wheat_cv);

//        bitter_cv.setBackgroundColor(Color.parseColor("#e6ffe6"));
//        chilli_cv.setBackgroundColor(Color.parseColor("#ffe6e6"));
//        coffee_cv.setBackgroundColor(Color.parseColor("#ecd9c6"));
//        cotton_cv.setBackgroundColor(Color.parseColor("#ffffff"));
//        fenugreek_cv.setBackgroundColor(Color.parseColor("#e6ffe6"));
//        indianM_cv.setBackgroundColor(Color.parseColor("#ffffcc"));
//        lentil_cv.setBackgroundColor(Color.parseColor("#e6ffe6"));
//        maize_cv.setBackgroundColor(Color.parseColor("#ffffcc"));
//        peas_cv.setBackgroundColor(Color.parseColor("#e6ffe6"));
//        pumpkin_cv.setBackgroundColor(Color.parseColor("#ffe6cc"));
//        rice_cv.setBackgroundColor(Color.parseColor("#ffffff"));
//        sesame_cv.setBackgroundColor(Color.parseColor("#ffffcc"));
//        sorghum_cv.setBackgroundColor(Color.parseColor("#ffe6cc"));
//        soyabean_cv.setBackgroundColor(Color.parseColor("#fff0b3"));
//        sugarcane_cv.setBackgroundColor(Color.parseColor("#e6ffe6"));
//        tea_cv.setBackgroundColor(Color.parseColor("#ffe6cc"));
//        tobacco_cv.setBackgroundColor(Color.parseColor("#e6ffe6"));
//        turmeric_cv.setBackgroundColor(Color.parseColor("#fff0b3"));
//        wheat_cv.setBackgroundColor(Color.parseColor("#ffe6cc"));


        // create the get Intent object
        Intent intent = getIntent();
        // receive the value by getStringExtra() method and key must be same which is send by main activity
        todayTemp = intent.getStringExtra("todayTemp_key");
        todayHumid = intent.getStringExtra("todayHumid_key");

        //To get the temperature unit
        unit = (todayTemp.replaceAll("[^a-zA-z]",""));
        // to eliminate units like °C and %
        double temp = Double.parseDouble(todayTemp.replaceAll("[^0-9.]", ""));

        int humidity = Integer.parseInt(todayHumid.replaceAll("[^0-9]", ""));


        AlertDialog.Builder builder = new AlertDialog.Builder(SuggestionsActivity.this);
        builder.setTitle(R.string.weatherinfo_suggestion).setIcon(R.drawable.ic_action_des);
        builder.setMessage(getString(R.string.today_temperature) + todayTemp + "\n" + getString(R.string.today_humidity)+ todayHumid );
        builder.setPositiveButton(R.string.ok_got_it,new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface,int i) {

                alertDialog1.dismiss();
            }
        });
        alertDialog1 = builder.create();
        alertDialog1.show();
//max and min in degree celcius
//max and min Humidity in percentage

        int bitterG_Humid = 60, chilli_Humid_min = 50, chilli_Humid_max = 70,coffee_Humid_min = 70,coffee_Humid_max = 80,cotton_Humid_min = 55,
                cotton_Humid_max = 60,methi_Humid = 70,mustard_Humid_min = 60,mustard_Humid_max = 80,lentil_Humid = 65,maize_Humid = 55,
                peas_Humid = 65,pum_Humid_min = 50,pum_Humid_max = 75, rice_Humid_min = 60,rice_Humid_max = 80,sesame_Humid = 50,sor_Humid = 90,
                soy_Humid = 85,sugarC_Humid_min = 80,sugarC_Humid_max = 85,tea_Humid_min = 60, tea_Humid_max = 80,tob_Humid_min = 60,
                tob_Humid_max = 68,turmeric_Humid_min = 70,turmeric_Humid_max = 80,wheat_Humid_min = 50,wheat_Humid_max = 60;


        double bitterG_Temp_minC = 24,bitterG_Temp_maxC = 35,chilli_Temp_minC = 20,chilli_Temp_maxC = 25,coffee_Temp_minC = 15,coffee_Temp_maxC = 30,
                cotton_Temp_minC = 21,cotton_Temp_maxC = 37,methi_Temp_minC = 10,methi_Temp_maxC = 35,mustard_Temp_minC = 10,mustard_Temp_maxC = 25,
                lentil_Temp_minC = 18,lentil_Temp_maxC = 30,maize_Temp_minC = 10,maize_Temp_maxC = 25,peas_Temp_minC = 15,peas_Temp_maxC = 25,
                pum_Temp_minC = 24,pum_Temp_maxC = 27,rice_Temp_minC = 21,rice_Temp_maxC = 37,sesame_Temp_minC = 24,sesame_Temp_maxC = 32,
                sor_Temp_minC = 15,sor_Temp_maxC = 40,soy_Temp_minC = 10,soy_Temp_maxC=33, sugarC_Temp_minC = 20, sugarC_Temp_maxC = 50,
                tea_Temp_minC = 16,tea_Temp_maxC = 32,tob_Temp_minC = 15,tob_Temp_maxC = 20,turmeric_Temp_minC = 20,turmeric_Temp_maxC = 30,
                wheat_Temp_minC = 20,wheat_Temp_maxC = 25;


        if(temp >=  (bitterG_Temp_minC) && temp <=  (bitterG_Temp_maxC) && humidity >= bitterG_Humid){
            bitter_cv.setVisibility(View.VISIBLE);
        }

        if(temp >=  (chilli_Temp_minC) && temp <= (chilli_Temp_maxC) &&
                humidity >= chilli_Humid_min && humidity <= chilli_Humid_max){
            chilli_cv.setVisibility(View.VISIBLE);
        }

        if(temp >= (coffee_Temp_minC) && temp <= (coffee_Temp_maxC) &&
                humidity >= coffee_Humid_min && humidity <= coffee_Humid_max){
            coffee_cv.setVisibility(View.VISIBLE);
        }

        if(temp >= (cotton_Temp_minC) && temp <= (cotton_Temp_maxC) &&
                humidity >= cotton_Humid_min && humidity <= cotton_Humid_max) {
            cotton_cv.setVisibility(View.VISIBLE);
        }

        if(temp >= (methi_Temp_minC) && temp <= (methi_Temp_maxC) && humidity >= methi_Humid) {
            fenugreek_cv.setVisibility(View.VISIBLE);
        }

        if(temp >= (mustard_Temp_minC) && temp <= (mustard_Temp_maxC) &&
                humidity >= mustard_Humid_min && humidity <= mustard_Humid_max) {
            indianM_cv.setVisibility(View.VISIBLE);
        }

        if(temp >= (lentil_Temp_minC) && temp <= (lentil_Temp_maxC) && humidity == lentil_Humid) {
            lentil_cv.setVisibility(View.VISIBLE);
        }

        if(temp >= (maize_Temp_minC) && temp <= (maize_Temp_maxC) && humidity == maize_Humid) {
            maize_cv.setVisibility(View.VISIBLE);
        }

        if(temp >= (peas_Temp_minC) && temp <= (peas_Temp_maxC) && humidity == peas_Humid) {
            peas_cv.setVisibility(View.VISIBLE);
        }

        if(temp >= (pum_Temp_minC) && temp <= (pum_Temp_maxC) &&
                humidity >= pum_Humid_min && humidity <= pum_Humid_max){
            pumpkin_cv.setVisibility(View.VISIBLE);
        }

        if(temp >= (rice_Temp_minC) && temp <= (rice_Temp_maxC) &&
                humidity >= rice_Humid_min && humidity <= rice_Humid_max){
            rice_cv.setVisibility(View.VISIBLE);
        }

        if(temp >= (sesame_Temp_minC) && temp <= (sesame_Temp_maxC) && humidity == sesame_Humid ){
            sesame_cv.setVisibility(View.VISIBLE);
        }

        if(temp >= (sor_Temp_minC) && temp <= (sor_Temp_maxC) && humidity >= sor_Humid){
            sorghum_cv.setVisibility(View.VISIBLE);
        }

        if(temp >= (soy_Temp_minC) && temp <= (soy_Temp_maxC) && humidity >= soy_Humid){
            soyabean_cv.setVisibility(View.VISIBLE);
        }
        if(temp >= (sugarC_Temp_minC) && temp <= (sugarC_Temp_maxC) &&
                humidity >= sugarC_Humid_min && humidity <= sugarC_Humid_max){
            sugarcane_cv.setVisibility(View.VISIBLE);
        }

        if(temp >= (tea_Temp_minC) && temp <= (tea_Temp_maxC) &&
                humidity >= tea_Humid_min && humidity <= tea_Humid_max){
            tea_cv.setVisibility(View.VISIBLE);
        }

        if(temp >= (tob_Temp_minC) && temp <= (tob_Temp_maxC) &&
                humidity >= tob_Humid_min && humidity <= tob_Humid_max){
            tobacco_cv.setVisibility(View.VISIBLE);
        }

        if(temp >= (turmeric_Temp_minC) && temp <= (turmeric_Temp_maxC) &&
                humidity >= turmeric_Humid_min && humidity <= turmeric_Humid_max){
            turmeric_cv.setVisibility(View.VISIBLE);
        }

        if(temp >= (wheat_Temp_minC) && temp <= (wheat_Temp_maxC) &&
                humidity >= wheat_Humid_min && humidity <= wheat_Humid_max){
            wheat_cv.setVisibility(View.VISIBLE);
        }

    }
//    public double (double temperature) {
//        if (unit.equals("F")) {
//            return (((9 * temperature) / 5) + 32);
//
//        } else if (unit.equals("K")) {
//            return (temperature + 273.15f);
//
//        } else {
//            return temperature;
//        }
//    }



}

