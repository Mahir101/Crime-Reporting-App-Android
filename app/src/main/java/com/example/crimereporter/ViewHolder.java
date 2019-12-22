package com.example.crimereporter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

public class ViewHolder extends RecyclerView.ViewHolder {

    View mView;

    public ViewHolder(View itemView) {
        super(itemView);

        mView = itemView;

        //item click
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mClickListener.onItemClick(view, getAdapterPosition());
            }
        });
        //item long click
        itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                mClickListener.onItemLongClick(view, getAdapterPosition());
                return true;
            }
        });

    }

    //set details to recycler view row
    public void setDetails(Context ctx,String condition, String title, String description, String image, String type){
        //Views
        TextView titlere = mView.findViewById(R.id.reTitle);
        TextView detailre = mView.findViewById(R.id.reDesc);
        ImageView imagere = mView.findViewById(R.id.reImage);
        TextView ret = mView.findViewById(R.id.reText);
        TextView typere = mView.findViewById(R.id.reType);
        //set data to views
        ret.setText(condition);
        titlere.setText(title);
        detailre.setText(description);
        typere.setText(type);
        Picasso.get().load(image).into(imagere);



    }

    public void setDetails2(Context ctx,String name, String age, String dresscolor, String image, String city, String gender, String description, String address){
        //Views
        TextView namex = mView.findViewById(R.id.namem);
        TextView agex = mView.findViewById(R.id.agem);

        ImageView imagex = mView.findViewById(R.id.imagem);

        TextView cityx = mView.findViewById(R.id.citym);
        TextView genderx = mView.findViewById(R.id.genderm);
        TextView dcx = mView.findViewById(R.id.dresscolorm);
        TextView descx = mView.findViewById(R.id.descm);
        TextView addx = mView.findViewById(R.id.addressm);
        //set data to views
        namex.setText("NAME: "+name);
        agex.setText("AGE: "+age);
        genderx.setText("GENDER: "+gender);
        cityx.setText("CITY: "+city);
        dcx.setText("DRESSCOLOR: "+dresscolor);
        addx.setText("ADDRESS: "+address);
        descx.setText("DESCRIPTION: "+description);
        Picasso.get().load(image).into(imagex);
    }

    private ViewHolder.ClickListener mClickListener;

    //interface to send callbacks
    public interface ClickListener{
        void onItemClick(View view, int position);
        void onItemLongClick(View  view, int position);
    }

    public void setOnClickListener(ViewHolder.ClickListener clickListener){
        mClickListener = clickListener;
    }
}
