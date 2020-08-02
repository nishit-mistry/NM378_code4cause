package com.example.phase_1;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.phase_1.activities.AnalystMap;
import com.example.phase_1.activities.MainActivity;
import com.example.phase_1.activities.MapsActivity;
import com.example.phase_1.activities.SuggestionsActivity;
import com.theartofdev.edmodo.cropper.CropImage;

import java.util.ArrayList;
import java.util.Locale;

import static android.app.Activity.RESULT_OK;
import static android.content.Context.MODE_PRIVATE;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.MyViewHolder> {

    ArrayList<String> personNames;
    ArrayList<Integer> personImages;
    ArrayList<Integer> colors;
    Context context;
    AlertDialog alertDialog1;


    public CustomAdapter(Context context, ArrayList<String> personNames, ArrayList<Integer> personImages, ArrayList<Integer> colors) {
        this.context = context;
        this.personNames = personNames;
        this.personImages = personImages;
        this.colors = colors;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // infalte the item Layout
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.rowlayout, parent, false);
        // set the view's size, margins, paddings and layout parameters
        MyViewHolder vh = new MyViewHolder(v); // pass the view to View Holder
        return vh;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, @SuppressLint("RecyclerView") final int position) {
        // set the data in items
        holder.name.setText(personNames.get(position));
        holder.image.setImageResource(personImages.get(position));
        if(position==0)
            holder.ll1.setBackgroundColor(Color.parseColor("#bfefbb"));
        else if(position==1)
            holder.ll1.setBackgroundColor(Color.parseColor("#BDEDFF"));
//        else if(position==2)
//            holder.ll1.setBackgroundColor(Color.parseColor("#ffd5ff"));
        else if(position==2)
            holder.ll1.setBackgroundColor(Color.parseColor("#D5F5E3"));
        else if(position==3)
            holder.ll1.setBackgroundColor(Color.parseColor("#b6cfdf"));
        else if(position==4)
            holder.ll1.setBackgroundColor(Color.parseColor("#FCF3CF"));
        else if(position==5)
            holder.ll1.setBackgroundColor(Color.parseColor("#EBDEF0"));
//        else if(position==7)
//            holder.ll1.setBackgroundColor(Color.parseColor("#AED6F1"));

        // implement setOnClickListener event on item view.
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // open another activity on item click
                if(position == 0){
                    //open camera
                    holder.ll1.setBackgroundColor(Color.parseColor("#FFFFFF"));
//                    CropImage.activity().start((Activity)context);
                    clickFab();
                }else if (position == 1){
                    //open map
                    holder.ll1.setBackgroundColor(Color.parseColor("#FFFFFF"));
                    CreateAlertDialogWithRadioButtonGroup();
                }else if(position == 2){
                    //open livefeed
                    holder.ll1.setBackgroundColor(Color.parseColor("#FFFFFF"));
                    openlivefeed();
                }else if(position == 3){
                    //change lang
                    holder.ll1.setBackgroundColor(Color.parseColor("#FFFFFF"));
                    showChangeLanguageDialog();
                }else if(position == 4){
                    //open suggestions
                    holder.ll1.setBackgroundColor(Color.parseColor("#FFFFFF"));
                    Intent i = new Intent(context, MainActivity.class);
                    context.startActivity(i);
                }else if(position == 5){
                    //open crop info
                    holder.ll1.setBackgroundColor(Color.parseColor("#FFFFFF"));
                    Intent i = new Intent(context, KnowMore_Offline.class);
                    context.startActivity(i);
                }
//                Intent intent = new Intent(context, SecondActivity.class);
//                intent.putExtra("image", personImages.get(position)); // put image data in Intent
//                context.startActivity(intent); // start Intent
            }

            private void clickFab() {
                androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(context);
                LayoutInflater inflater = LayoutInflater.from(context);
                View dialogLayout = inflater.inflate(R.layout.alertbox_with_image, null);
                TextView tv = dialogLayout.findViewById(R.id.tv_01);
                ImageView iv = dialogLayout.findViewById(R.id.image);
                Glide.with(context)
                        .load(R.drawable.camera_gif)
                        .into(iv);
//                iv.setImageResource(R.drawable.coffee_farm);
                tv.setText(R.string.open_camera);
                builder.setPositiveButton(R.string.ok_got_it, null);
                builder.setView(dialogLayout);
                builder.show();

//                AlertDialog.Builder builder = new AlertDialog.Builder(context);
//                builder.setTitle("Open Camera");
//                builder.setMessage("Click on camera button");
//                builder.setPositiveButton(R.string.ok_got_it,new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialogInterface,int i) {
//
//                        alertDialog1.dismiss();
//                    }
//                });
//                alertDialog1 = builder.create();
//                alertDialog1.show();
            }
        });

    }


    private void showChangeLanguageDialog() {
        final String[] Listitems = {"English", "ગુજરાતી", "हिन्दी", "मराठी"};
        androidx.appcompat.app.AlertDialog.Builder mBuilder = new androidx.appcompat.app.AlertDialog.Builder(context);
        mBuilder.setTitle(R.string.choose_lang);
        mBuilder.setSingleChoiceItems(Listitems, -1, (dialogInterface, which) -> {
            if( which == 0){
                setLocale("en");
                ((Activity)context).recreate();
            }
            else if( which == 1){
                setLocale("gu");
                ((Activity)context).recreate();
            }
            else if( which == 2){
                setLocale("hi");
                ((Activity)context).recreate();
            }
            else if( which == 3){
                setLocale("mr");
                ((Activity)context).recreate();
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
        context.getResources().updateConfiguration(config, context.getResources().getDisplayMetrics());
        SharedPreferences.Editor editor = context.getSharedPreferences("Settings", MODE_PRIVATE).edit();
        editor.putString("My_Lang", lang);
        editor.apply();



    }

    private void openlivefeed() {
        Intent i = new Intent(context, DetectorActivity.class);
        context.startActivity(i);
    }

    private void CreateAlertDialogWithRadioButtonGroup() {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);

        builder.setTitle(R.string.role);

        builder.setSingleChoiceItems(R.array.roles, -1, new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int item) {

                switch(item)
                {
                    case 0:
                        Intent i = new Intent(context, MapsActivity.class);
                        context.startActivity(i);
                        break;
                    case 1:
                        Intent j = new Intent(context, AnalystMap.class);
                        context.startActivity(j);
                        break;

                }

                alertDialog1.dismiss();
            }
        });
        alertDialog1 = builder.create();
        alertDialog1.show();

    }



    @Override
    public int getItemCount() {
        return personNames.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        // init the item view's
        TextView name;
        ImageView image;
        LinearLayout ll1;

        public MyViewHolder(View itemView) {
            super(itemView);

            // get the reference of item view's
            name = (TextView) itemView.findViewById(R.id.name);
            image = (ImageView) itemView.findViewById(R.id.image);
            ll1 = (LinearLayout) itemView.findViewById(R.id.ll1);

        }
    }
}
