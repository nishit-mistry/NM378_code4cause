package com.example.phase_1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.vectordrawable.graphics.drawable.AnimatedVectorDrawableCompat;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.graphics.drawable.AnimatedVectorDrawable;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.os.Bundle;
import android.os.Handler;
import android.util.TypedValue;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.phase_1.activities.MapsActivity;

import java.util.Locale;

public class LanguageSelectorActivity extends AppCompatActivity {
//    int SPLASH_TIME = 3000; //This is 3 seconds
    CardView english_cv,gujarati_cv,hindi_cv,marathi_cv;
    ImageView done1,done2,done3,done4;
    Button getStarted;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (restorePrefData()) {

            Intent intent = new Intent(getApplicationContext(),SplashActivity.class );
            startActivity(intent);
            finish();

        }
        setContentView(R.layout.activity_language_selector);
        


        done1 = findViewById(R.id.done1);
        done2 = findViewById(R.id.done2);
        done3 = findViewById(R.id.done3);
        done4 = findViewById(R.id.done4);
        getStarted = findViewById(R.id.finish);

        

        english_cv = findViewById(R.id.english_cv);
        gujarati_cv = findViewById(R.id.gujarati_cv);
        hindi_cv = findViewById(R.id.hindi_cv);
        marathi_cv = findViewById(R.id.marathi_cv);

        english_cv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                done1.setVisibility(View.VISIBLE);
                setLocale("en");
                recreate();
            }
        });

        gujarati_cv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                done2.setVisibility(View.VISIBLE);
                setLocale("gu");
                recreate();
            }
        });

        hindi_cv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                done3.setVisibility(View.VISIBLE);
                setLocale("hi");
                recreate();
            }
        });
        marathi_cv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                done4.setVisibility(View.VISIBLE);
                setLocale("mr");
                recreate();
            }
        });

        if (getLangCode().contentEquals("en")) {

            getStarted.setVisibility(View.VISIBLE);
            done1.setVisibility(View.VISIBLE);
        }else if (getLangCode().contentEquals("gu")) {
            getStarted.setTextSize(TypedValue.COMPLEX_UNIT_SP,25);
            getStarted.setVisibility(View.VISIBLE);
            done2.setVisibility(View.VISIBLE);
        }
        else if (getLangCode().contentEquals("hi")) {
            getStarted.setTextSize(TypedValue.COMPLEX_UNIT_SP,25);
            getStarted.setVisibility(View.VISIBLE);
            done3.setVisibility(View.VISIBLE);
        }
        else if (getLangCode().contentEquals("mr")) {
            getStarted.setTextSize(TypedValue.COMPLEX_UNIT_SP,25);
            getStarted.setVisibility(View.VISIBLE);
            done4.setVisibility(View.VISIBLE);
        }

        getStarted.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                //open main activity

                Intent intent = new Intent(getApplicationContext(),SplashActivity.class);
                startActivity(intent);
                // also we need to save a boolean value to storage so next time when the user run the app
                // we could know that he is already checked the intro screen activity
                // i'm going to use shared preferences to that process
                savePrefsData();
                finish();



            }
        });
//        Finish.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(getApplicationContext(),SplashActivity.class);
//                startActivity(intent);
//                // also we need to save a boolean value to storage so next time when the user run the app
//                // we could know that he is already checked the intro screen activity
//                // i'm going to use shared preferences to that process
//
//                finish();
//            }
//        });

//        SharedPreferences pref = getSharedPreferences("ActivityPREF", Context.MODE_PRIVATE);
//        if(pref.getBoolean("activity_executed", false)){
//            Intent intent = new Intent(LanguageSelectorActivity.this, SplashActivity.class);
//            startActivity(intent);
//            finish();
//        } else {
//            SharedPreferences.Editor ed = pref.edit();
//            ed.putBoolean("activity_executed", true);
//            ed.commit();
//        }

    }

private boolean restorePrefData() {


    SharedPreferences pref = getApplicationContext().getSharedPreferences("langPref",MODE_PRIVATE);
    Boolean isIntroActivityOpnendBefore = pref.getBoolean("isIntroOpnend",false);
    return  isIntroActivityOpnendBefore;



}

    private void savePrefsData() {

        SharedPreferences pref = getApplicationContext().getSharedPreferences("langPref",MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putBoolean("isIntroOpnend",true);
        editor.commit();


    }


//    private void savePrefsData() {
//        SharedPreferences pref = getSharedPreferences("ActivityPREF", Context.MODE_PRIVATE);
//        if(pref.getBoolean("activity_executed", false)){
//            Intent intent = new Intent(LanguageSelectorActivity.this, SplashActivity.class);
//            startActivity(intent);
//            finish();
//        } else {
//            SharedPreferences.Editor ed = pref.edit();
//            ed.putBoolean("activity_executed", true);
//            ed.commit();
//        }
//    }

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

        private String getLangCode() {
            SharedPreferences preferences = getSharedPreferences("Settings", MODE_PRIVATE);
            String langCode = preferences.getString("My_Lang", "");
// save english ‘en’ as the default language
            return langCode;
        }
}


