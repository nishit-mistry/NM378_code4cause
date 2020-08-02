package com.example.phase_1.adapters;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.phase_1.R;
import com.squareup.picasso.Picasso;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.MyViewHolder> {
    private Context mContext;
    private Integer[] mImage;
    private String[] mTitle;
    private String[] msubTitle;
    private String[] mtemp;

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView title;
        TextView subtitle;
        ImageView imgView;
        TextView temp;

        public MyViewHolder(View itemView) {
            super(itemView);

            this.title = (TextView) itemView.findViewById(R.id.title);
            this.subtitle = (TextView) itemView.findViewById(R.id.subtitle);
            this.imgView = (ImageView) itemView.findViewById(R.id.imgcar);
            this.temp = (TextView)itemView.findViewById(R.id.temp);
        }
    }

    public CustomAdapter(Context mContext, Integer[] image, String[] title, String[] subTitle, String temp[]) {
        this.mContext = mContext;
        this.mImage = image;
        this.mTitle = title;
        this.msubTitle = subTitle;
        this.mtemp = temp;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent,
                                           int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardlayout, parent, false);

        MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int i) {
//i starts with 0
        //bitter gourd
        if( i == 0){
            holder.itemView.setBackgroundColor(Color.parseColor("#e6ffe6"));
        }
        //chilli
        if( i == 1){
            holder.itemView.setBackgroundColor(Color.parseColor("#ffe6e6"));
        }
        //coffee
        if( i == 2){
            holder.itemView.setBackgroundColor(Color.parseColor("#ecd9c6"));
        }
        //cotton
        if( i == 3){
            holder.itemView.setBackgroundColor(Color.parseColor("#ffffff"));
        }
        //maize
        if( i == 4){
            holder.itemView.setBackgroundColor(Color.parseColor("#ffffcc"));
        }
        //fenugreek
        if( i == 5){
            holder.itemView.setBackgroundColor(Color.parseColor("#e6ffe6"));
        }
        //indian mustard
        if( i == 6){
            holder.itemView.setBackgroundColor(Color.parseColor("#ffffcc"));
        }
        //peas
        if( i == 7){
            holder.itemView.setBackgroundColor(Color.parseColor("#e6ffe6"));
        }//pumpkin
        if( i == 8){
            holder.itemView.setBackgroundColor(Color.parseColor("#ffe6cc"));
        }//rice
        if( i == 9){
            holder.itemView.setBackgroundColor(Color.parseColor("#ffffff"));
        }//soyabean
        if( i == 10){
            holder.itemView.setBackgroundColor(Color.parseColor("#fff0b3"));
        }//sugarcane
        if( i == 11){
            holder.itemView.setBackgroundColor(Color.parseColor("#e6ffe6"));
        }//turmeric
        if( i == 12){
            holder.itemView.setBackgroundColor(Color.parseColor("#fff0b3"));
        }//wheat
        if( i == 13){
            holder.itemView.setBackgroundColor(Color.parseColor("#ffe6cc"));
        }

        holder.title.setText(mTitle[i]);
        holder.subtitle.setText(msubTitle[i]);
        holder.temp.setText(mtemp[i]);
        Picasso.get().load(mImage[i]).into(holder.imgView);




    }


    @Override
    public int getItemCount() {
        return mTitle.length;
    }
}
