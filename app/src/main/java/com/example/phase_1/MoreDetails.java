package com.example.phase_1;

import android.Manifest;
import android.animation.Animator;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.graphics.pdf.PdfDocument;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;

import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.jgabrielfreitas.core.BlurImageView;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Locale;

import static com.example.phase_1.MainActivity.REQUEST_PERMISSION;

public class MoreDetails extends AppCompatActivity {
    FloatingActionButton fab, fabShare, fabSave, fabTranslate;
    LinearLayout fabLayout1, fabLayout2, fabLayout3;
    View fabBGLayout;
    boolean isFABOpen = false;
    ImageView cropimage;
    String cropName;
    TextView label_cropname, label_des, label_ph, label_sunlight, label_soiltype, label_cultitime, label_climate, label_water, label_cultiAt, label_sciname, label_location;
    TextView crop_name, crop_ph, crop_sun, crop_soil, crop_ctime, crop_climate, crop_water, crop_cultiAt, crop_des, crop_sciName, user_location;
    Drawable background1;
    private BlurImageView background;
    private LinearLayout llScroll, llloc;
    private Bitmap bitmap;


    private static final String AUTHORITY =
            "com.example.phase_1";
    private static final Uri PROVIDER =
            Uri.parse("content://" + AUTHORITY);

    @Override
    protected void onCreate(Bundle fabSavedInstanceState) {
        super.onCreate(fabSavedInstanceState);
        setContentView(R.layout.activity_more_details);



        fabLayout1 = (LinearLayout) findViewById(R.id.fabLayout1);
        fabLayout2 = (LinearLayout) findViewById(R.id.fabLayout2);
        fabLayout3 = (LinearLayout) findViewById(R.id.fabLayout3);
        fab = (FloatingActionButton) findViewById(R.id.fab);
        fabShare = (FloatingActionButton) findViewById(R.id.fabShare);
        fabSave = (FloatingActionButton) findViewById(R.id.fabSave);
        fabTranslate = (FloatingActionButton) findViewById(R.id.fabTranslate);
        fabBGLayout = findViewById(R.id.fabBGLayout);

        llScroll = findViewById(R.id.llScroll);
        user_location = findViewById(R.id.user_location);
        label_location = findViewById(R.id.label_location);
        llloc = findViewById(R.id.llloc);


        cropimage = findViewById(R.id.selected_image);
        cropimage.setImageBitmap(BitmapHelper.getInstance().getBitmap());
        background = findViewById(R.id.back_imageview);
        background.setImageBitmap(BitmapHelper.getInstance().getBitmap());
//        background.setBlur(7);


        label_cropname = findViewById(R.id.label_cropname);
        label_ph = findViewById(R.id.label_ph);
        label_sunlight = findViewById(R.id.label_sunlight);
        label_soiltype = findViewById(R.id.label_soiltype);
        label_cultitime = findViewById(R.id.label_cultitime);
        label_climate = findViewById(R.id.label_climate);
        label_water = findViewById(R.id.label_water);
        label_cultiAt = findViewById(R.id.label_cultiAt);
        label_des = findViewById(R.id.label_des);
        label_sciname = findViewById(R.id.label_sciname);

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

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!isFABOpen) {
                    showFABMenu();
                } else {
                    closeFABMenu();
                }
            }
        });




// request permission to write data (aka images) to the user's external storage of their phone

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    REQUEST_PERMISSION);
        }

        // request permission to read data (aka images) from the user's external storage of their phone

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                    REQUEST_PERMISSION);
        }




        SharedPreferences sharedPreferences = getSharedPreferences("myKey", MODE_PRIVATE);
        String value = sharedPreferences.getString("value", "");
        user_location.setText(value);

        // create the get Intent object
        Intent intent = getIntent();
        // receive the value by getStringExtra() method and key must be same which is send by main activity
        cropName = intent.getStringExtra("retrieve");

        switch (cropName) {
            case "Bitter Gourd":
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
                break;
            case "Chilly":
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
                break;
            case "Coffee":
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
                break;
            case "Cotton":
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
                break;
            case "Lentil":
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
                break;
            case "Maize":
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
                break;
            case "Fenugreek":
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
                break;
            case "Indian Mustard":
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
                break;
            case "Peas":
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
                break;
            case "Pumpkin":
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
                break;
            case "Rice":
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
                break;
            case "Sesame":
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
                break;
            case "Sorghum":
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
                break;
            case "Soybean":
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
                break;
            case "Sugarcane":
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
                break;
            case "Tea":
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
                break;
            case "Turmeric":
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
                break;
            case "Tobacco":
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
                break;
            case "Wheat":
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
                break;
        }


        fabSave.setOnClickListener(v -> {

            llloc.setVisibility(View.VISIBLE);
            llScroll.setBackgroundColor(Color.parseColor("#ffffff"));
            Log.d("size"," "+llScroll.getWidth() +"  "+llScroll.getWidth());
            bitmap = loadBitmapFromView(llScroll, llScroll.getWidth(), llScroll.getHeight());
            createPdf();
        });

        fabTranslate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showChangeLanguageDialog();
            }
        });

        fabShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                llloc.setVisibility(View.VISIBLE);
                llScroll.setBackgroundColor(Color.parseColor("#ffffff"));
                Log.d("size"," "+llScroll.getWidth() +"  "+llScroll.getWidth());
                bitmap = loadBitmapFromView(llScroll, llScroll.getWidth(), llScroll.getHeight());
                shareGeneratedPdf();
            }
        });


    }



    public static Bitmap loadBitmapFromView(View v, int width, int height) {
        Bitmap b = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas c = new Canvas(b);
        v.draw(c);

        return b;
    }
    private void createPdf(){
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
        String fileName = filename + ".pdf";

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
        Toast.makeText(this, "PDF Saved: " +fileName, Toast.LENGTH_SHORT).show();
        llScroll.setBackground(background1);
        llloc.setVisibility(View.INVISIBLE);




    }

    private void shareGeneratedPdf() {
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
        String fileName = filename + ".pdf";

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
        llScroll.setBackground(background1);
        llloc.setVisibility(View.INVISIBLE);

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

    private void showChangeLanguageDialog() {
        final String[] Listitems = {"English", "ગુજરાતી", "हिन्दी", "मराठी"};
        AlertDialog.Builder mBuilder = new AlertDialog.Builder(MoreDetails.this);
        mBuilder.setTitle("Choose Language:");
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

    private void showFABMenu() {
        isFABOpen = true;
        fabLayout1.setVisibility(View.VISIBLE);
        fabLayout2.setVisibility(View.VISIBLE);
        fabLayout3.setVisibility(View.VISIBLE);
        fabBGLayout.setVisibility(View.VISIBLE);
        fab.animate().rotationBy(180);
        fabLayout1.animate().translationY(-getResources().getDimension(R.dimen.standard_55));
        fabLayout2.animate().translationY(-getResources().getDimension(R.dimen.standard_100));
        fabLayout3.animate().translationY(-getResources().getDimension(R.dimen.standard_145));
    }

    private void closeFABMenu() {
        isFABOpen = false;
        fabBGLayout.setVisibility(View.GONE);
        fab.animate().rotation(0);
        fabLayout1.animate().translationY(0);
        fabLayout2.animate().translationY(0);
        fabLayout3.animate().translationY(0);
        fabLayout3.animate().translationY(0).setListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {

            }

            @Override
            public void onAnimationEnd(Animator animator) {
                if (!isFABOpen) {
                    fabLayout1.setVisibility(View.GONE);
                    fabLayout2.setVisibility(View.GONE);
                    fabLayout3.setVisibility(View.GONE);
                }
            }

            @Override
            public void onAnimationCancel(Animator animator) {

            }

            @Override
            public void onAnimationRepeat(Animator animator) {

            }
        });
    }
}

