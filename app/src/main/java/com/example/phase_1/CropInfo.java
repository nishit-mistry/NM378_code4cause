package com.example.phase_1;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.pdf.PdfDocument;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.text.Html;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;


public class CropInfo extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    String cropName;
    //Label
    TextView cropname_label,description_label,ph_label,sunlight_label,soiltype_label,cultitime_label,climate_label,water_label,cultiAt_label,sciname_label;

    //EditText
    TextView crop_name,crop_ph,crop_sun,crop_soil,crop_ctime,crop_climate,crop_water,crop_cultiAt,crop_des,crop_sciName;

    //LinearLayout
    LinearLayout llScroll_cropInfo;

    private Bitmap bitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crop_info);
        ActionBar actionbar = getSupportActionBar();
        getSupportActionBar().setTitle(Html.fromHtml("<font color='#000000'>Cropifier</font>"));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null){
            actionBar.setBackgroundDrawable(getResources().getDrawable(R.drawable.actionbar_gradient));
        }
//        getSupportActionBar().setHomeAsUpIndicator(Html.fromHtml("<)R.drawable.ic_baseline_arrow_back_24);

        llScroll_cropInfo = findViewById(R.id.llScroll_cropInfo);

        crop_name = findViewById(R.id.crop_name);
        crop_ph = findViewById(R.id.crop_ph);
        crop_sun = findViewById(R.id.crop_sun);
        crop_soil = findViewById(R.id.crop_soil);
        crop_ctime = findViewById(R.id.crop_ctime);
        crop_climate = findViewById(R.id.crop_climate);
        crop_water = findViewById(R.id.crop_water);
        crop_cultiAt = findViewById(R.id.crop_cultiAt);
        crop_des = findViewById(R.id.crop_des);
        crop_sciName = findViewById(R.id.crop_sciName);


        cropname_label = findViewById(R.id.cropname_label);
        description_label = findViewById(R.id.description_label);
        ph_label = findViewById(R.id.ph_label);
        sunlight_label = findViewById(R.id.sunlight_label);
        soiltype_label = findViewById(R.id.soiltype_label);
        cultitime_label = findViewById(R.id.cultitime_label);
        climate_label = findViewById(R.id.climate_label);
        water_label = findViewById(R.id.water_label);
        cultiAt_label = findViewById(R.id.cultiAt_label);
        sciname_label = findViewById(R.id.sciname_label);



        FloatingActionButton share = findViewById(R.id.share);
        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                llScroll_cropInfo.setBackgroundColor(Color.parseColor("#ffffff"));
                Log.d("size"," "+llScroll_cropInfo.getWidth() +"  "+llScroll_cropInfo.getWidth());
                bitmap = loadBitmapFromView(llScroll_cropInfo, llScroll_cropInfo.getWidth(), llScroll_cropInfo.getHeight());
                sharedPdf();

            }
        });

        // create the get Intent object
        Intent intent = getIntent();
        // receive the value by getStringExtra() method and key must be same which is send by main activity
        cropName = intent.getStringExtra("cropName_key");

        if(cropName.equals(getString(R.string.bg_name))) 
        {
            crop_name.setText(R.string.bg_name);
            crop_ph.setText(R.string.bg_ph);
            crop_sun.setText(R.string.bg_sun);
            crop_soil.setText(R.string.bg_soil);
            crop_ctime.setText(R.string.bg_ctime);
            crop_climate.setText(R.string.bg_climate);
            crop_water.setText(R.string.bg_water);
            crop_cultiAt.setText(R.string.bg_culti);
            crop_des.setText(R.string.bg_des);
            crop_sciName.setText(R.string.bg_sciname);
        }
        else if(cropName.equals( getString(R.string.chilly_name))) {
            crop_name.setText(R.string.chilly_name);
            crop_ph.setText(R.string.chilly_ph);
            crop_sun.setText(R.string.chilly_sun);
            crop_soil.setText(R.string.chilly_soil);
            crop_ctime.setText(R.string.chilly_ctime);
            crop_climate.setText(R.string.chilly_climate);
            crop_water.setText(R.string.chilly_water);
            crop_cultiAt.setText(R.string.chilly_culti);
            crop_des.setText(R.string.chilly_des);
            crop_sciName.setText(R.string.chilly_sciname);
        }

        else if(cropName.equals( getString(R.string.coffee_name))) {
            crop_name.setText(R.string.coffee_name);
            crop_ph.setText(R.string.coffee_ph);
            crop_sun.setText(R.string.coffee_sun);
            crop_soil.setText(R.string.coffee_soil);
            crop_ctime.setText(R.string.coffee_ctime);
            crop_climate.setText(R.string.coffee_climate);
            crop_water.setText(R.string.coffee_water);
            crop_cultiAt.setText(R.string.coffee_culti);
            crop_des.setText(R.string.coffee_des);
            crop_sciName.setText(R.string.coffee_sciname);
        }
            else if(cropName.equals( getString(R.string.cotton_name))) {
            crop_name.setText(R.string.cotton_name);
            crop_ph.setText(R.string.cotton_ph);
            crop_sun.setText(R.string.cotton_sun);
            crop_soil.setText(R.string.cotton_soil);
            crop_ctime.setText(R.string.cotton_ctime);
            crop_climate.setText(R.string.cotton_climate);
            crop_water.setText(R.string.cotton_water);
            crop_cultiAt.setText(R.string.cotton_culti);
            crop_des.setText(R.string.cotton_des);
            crop_sciName.setText(R.string.cotton_sciname);
        }

            else if(cropName.equals( getString(R.string.lentil_name))) {
            crop_name.setText(R.string.lentil_name);
            crop_ph.setText(R.string.lentil_ph);
            crop_sun.setText(R.string.lentil_sun);
            crop_soil.setText(R.string.lentil_soil);
            crop_ctime.setText(R.string.lentil_ctime);
            crop_climate.setText(R.string.lentil_climate);
            crop_water.setText(R.string.lentil_water);
            crop_cultiAt.setText(R.string.lentil_culti);
            crop_des.setText(R.string.lentil_des);
            crop_sciName.setText(R.string.lentil_sciname);
        }

            else if(cropName.equals( getString(R.string.maize_name))){
                crop_name.setText(R.string.maize_name);
                crop_ph.setText(R.string.maize_ph);
                crop_sun.setText(R.string.maize_sun);
                crop_soil.setText(R.string.maize_soil);
                crop_ctime.setText(R.string.maize_ctime);
                crop_climate.setText(R.string.maize_climate);
                crop_water.setText(R.string.maize_water);
                crop_cultiAt.setText(R.string.maize_culti);
                crop_des.setText(R.string.maize_des);
                crop_sciName.setText(R.string.maize_sciname);
                }

            else if(cropName.equals( getString(R.string.methi_name))){
                crop_name.setText(R.string.methi_name);
                crop_ph.setText(R.string.methi_ph);
                crop_sun.setText(R.string.methi_sun);
                crop_soil.setText(R.string.methi_soil);
                crop_ctime.setText(R.string.methi_ctime);
                crop_climate.setText(R.string.methi_climate);
                crop_water.setText(R.string.methi_water);
                crop_cultiAt.setText(R.string.methi_culti);
                crop_des.setText(R.string.methi_des);
                crop_sciName.setText(R.string.methi_sciname);
                }

            else if(cropName.equals( getString(R.string.mustard_name))){
                crop_name.setText(R.string.mustard_name);
                crop_ph.setText(R.string.mustard_ph);
                crop_sun.setText(R.string.mustard_sun);
                crop_soil.setText(R.string.mustard_soil);
                crop_ctime.setText(R.string.mustard_ctime);
                crop_climate.setText(R.string.mustard_climate);
                crop_water.setText(R.string.mustard_water);
                crop_cultiAt.setText(R.string.mustard_culti);
                crop_des.setText(R.string.mustard_des);
                crop_sciName.setText(R.string.mustard_sciname);
                }

            else if(cropName.equals(getString(R.string.peas_name))){
                crop_name.setText(R.string.peas_name);
                crop_ph.setText(R.string.peas_ph);
                crop_sun.setText(R.string.peas_sun);
                crop_soil.setText(R.string.peas_soil);
                crop_ctime.setText(R.string.peas_ctime);
                crop_climate.setText(R.string.peas_climate);
                crop_water.setText(R.string.peas_water);
                crop_cultiAt.setText(R.string.peas_culti);
                crop_des.setText(R.string.peas_des);
                crop_sciName.setText(R.string.peas_sciname);
                }

            else if(cropName.equals( getString(R.string.pumpkin_name))){
                crop_name.setText(R.string.pumpkin_name);
                crop_ph.setText(R.string.pumpkin_ph);
                crop_sun.setText(R.string.pumpkin_sun);
                crop_soil.setText(R.string.pumpkin_soil);
                crop_ctime.setText(R.string.pumpkin_ctime);
                crop_climate.setText(R.string.pumpkin_climate);
                crop_water.setText(R.string.pumpkin_water);
                crop_cultiAt.setText(R.string.pumpkin_culti);
                crop_des.setText(R.string.pumpkin_des);
                crop_sciName.setText(R.string.pumpkin_sciname);
                }

            else if(cropName.equals(getString(R.string.rice_name))){
                crop_name.setText(R.string.rice_name);
                crop_ph.setText(R.string.rice_ph);
                crop_sun.setText(R.string.rice_sun);
                crop_soil.setText(R.string.rice_soil);
                crop_ctime.setText(R.string.rice_ctime);
                crop_climate.setText(R.string.rice_climate);
                crop_water.setText(R.string.rice_water);
                crop_cultiAt.setText(R.string.rice_culti);
                crop_des.setText(R.string.rice_des);
                crop_sciName.setText(R.string.rice_sciname);
                }

            else if(cropName.equals(getString(R.string.sesame_name))){
                crop_name.setText(R.string.sesame_name);
                crop_ph.setText(R.string.sesame_ph);
                crop_sun.setText(R.string.sesame_sun);
                crop_soil.setText(R.string.sesame_soil);
                crop_ctime.setText(R.string.sesame_ctime);
                crop_climate.setText(R.string.sesame_climate);
                crop_water.setText(R.string.sesame_water);
                crop_cultiAt.setText(R.string.sesame_culti);
                crop_des.setText(R.string.sesame_des);
                crop_sciName.setText(R.string.sesame_sciname);
                }

            else if(cropName.equals( getString(R.string.sorghum_name))){
                crop_name.setText(R.string.sorghum_name);
                crop_ph.setText(R.string.sorghum_ph);
                crop_sun.setText(R.string.sorghum_sun);
                crop_soil.setText(R.string.sorghum_soil);
                crop_ctime.setText(R.string.sorghum_ctime);
                crop_climate.setText(R.string.sorghum_climate);
                crop_water.setText(R.string.sorghum_water);
                crop_cultiAt.setText(R.string.sorghum_culti);
                crop_des.setText(R.string.sorghum_des);
                crop_sciName.setText(R.string.sorghum_sciname);
                }

            else if(cropName.equals(getString(R.string.soybean_name))){
                crop_name.setText(R.string.soybean_name);
                crop_ph.setText(R.string.soybean_ph);
                crop_sun.setText(R.string.soybean_sun);
                crop_soil.setText(R.string.soybean_soil);
                crop_ctime.setText(R.string.soybean_ctime);
                crop_climate.setText(R.string.soybean_climate);
                crop_water.setText(R.string.soybean_water);
                crop_cultiAt.setText(R.string.soybean_culti);
                crop_des.setText(R.string.soybean_des);
                crop_sciName.setText(R.string.soybean_sciname);
                }

            else if(cropName.equals( getString(R.string.sugarcane_name))){
                crop_name.setText(R.string.sugarcane_name);
                crop_ph.setText(R.string.sugarcane_ph);
                crop_sun.setText(R.string.sugarcane_sun);
                crop_soil.setText(R.string.sugarcane_soil);
                crop_ctime.setText(R.string.sugarcane_ctime);
                crop_climate.setText(R.string.sugarcane_climate);
                crop_water.setText(R.string.sugarcane_water);
                crop_cultiAt.setText(R.string.sugarcane_culti);
                crop_des.setText(R.string.sugarcane_des);
                crop_sciName.setText(R.string.sugarcane_sciname);
                }

            else if(cropName.equals(getString(R.string.tea_name))){
                crop_name.setText(R.string.tea_name);
                crop_ph.setText(R.string.tea_ph);
                crop_sun.setText(R.string.tea_sun);
                crop_soil.setText(R.string.tea_soil);
                crop_ctime.setText(R.string.tea_ctime);
                crop_climate.setText(R.string.tea_climate);
                crop_water.setText(R.string.tea_water);
                crop_cultiAt.setText(R.string.tea_culti);
                crop_des.setText(R.string.tea_des);
                crop_sciName.setText(R.string.tea_sciname);
                }

            else if(cropName.equals( getString(R.string.turmeric_name))){
                crop_name.setText(R.string.turmeric_name);
                crop_ph.setText(R.string.turmeric_ph);
                crop_sun.setText(R.string.turmeric_sun);
                crop_soil.setText(R.string.turmeric_soil);
                crop_ctime.setText(R.string.turmeric_ctime);
                crop_climate.setText(R.string.turmeric_climate);
                crop_water.setText(R.string.turmeric_water);
                crop_cultiAt.setText(R.string.turmeric_culti);
                crop_des.setText(R.string.turmeric_des);
                crop_sciName.setText(R.string.turmeric_sciname);
                }

            else if(cropName.equals( getString(R.string.tobacco_name))){
                crop_name.setText(R.string.tobacco_name);
                crop_ph.setText(R.string.tobacco_ph);
                crop_sun.setText(R.string.tobacco_sun);
                crop_soil.setText(R.string.tobacco_soil);
                crop_ctime.setText(R.string.tobacco_ctime);
                crop_climate.setText(R.string.tobacco_climate);
                crop_water.setText(R.string.tobacco_water);
                crop_cultiAt.setText(R.string.tobacco_culti);
                crop_des.setText(R.string.tobacco_des);
                crop_sciName.setText(R.string.tobacco_sciname);
                }

            else if(cropName.equals( getString(R.string.wheat_name))){
                crop_name.setText(R.string.wheat_name);
                crop_ph.setText(R.string.wheat_ph);
                crop_sun.setText(R.string.wheat_sun);
                crop_soil.setText(R.string.wheat_soil);
                crop_ctime.setText(R.string.wheat_ctime);
                crop_climate.setText(R.string.wheat_climate);
                crop_water.setText(R.string.wheat_water);
                crop_cultiAt.setText(R.string.wheat_culti);
                crop_des.setText(R.string.wheat_des);
                crop_sciName.setText(R.string.wheat_sciname);
                }
        }
    public static Bitmap loadBitmapFromView(View v, int width, int height) {
        Bitmap b = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas c = new Canvas(b);
        v.draw(c);

        return b;
    }

    private void sharedPdf() {

        WindowManager wm = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
        //  Display display = wm.getDefaultDisplay();
        DisplayMetrics displaymetrics = new DisplayMetrics();

        this.getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        float hight = displaymetrics.heightPixels ;
        float width = displaymetrics.widthPixels ;

        int convertHighet = (int) hight, convertWidth = (int) width;


        PdfDocument document = new PdfDocument();
        PdfDocument.PageInfo pageInfo = new PdfDocument.PageInfo.Builder(convertWidth, convertHighet, 1).create();
        PdfDocument.Page page = document.startPage(pageInfo);

        Canvas canvas = page.getCanvas();

        Paint paint = new Paint();
        canvas.drawPaint(paint);

                bitmap = Bitmap.createScaledBitmap(bitmap, convertWidth, convertHighet, true);

        paint.setColor(Color.BLUE);
                canvas.drawBitmap(bitmap, 0, 0 , null);
        document.finishPage(page);

        String filename = crop_name.getText().toString();
        // write the document content
        String fileName = filename +"_offline" +".pdf";

        //create file
        File targetPdf = new File(Environment.getExternalStorageDirectory().getAbsolutePath(), fileName);

        File filePath;
        filePath = new File(String.valueOf(targetPdf));
        try {
            document.writeTo(new FileOutputStream(filePath));

        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(this, "Something wrong: " + e.toString(), Toast.LENGTH_LONG).show();
        }

        // close the document
        document.close();
//        llScroll_cropInfo.setBackground(background1);


        Intent email = new Intent(Intent.ACTION_SEND);
//        email.putExtra(Intent.EXTRA_EMAIL, "receiver_email_address");
//        email.putExtra(Intent.EXTRA_SUBJECT, "subject");
//        email.putExtra(Intent.EXTRA_TEXT, "email body");
        Context context = this;
        Uri uri = FileProvider.getUriForFile(context, context.getApplicationContext().getPackageName() + ".provider", new File(Environment.getExternalStorageDirectory().getAbsolutePath(),  fileName));
        email.putExtra(Intent.EXTRA_STREAM, uri);
        email.setType("application/pdf");
        email.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        email.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        startActivity(email);
    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}


