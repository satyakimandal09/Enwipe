package com.example.satyaki.enactus33;

/**
 * Created by Satyaki on 13-03-2018.
 */

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;


public class MyAdapter extends ArrayAdapter<InfoDisplay.StoreInfo>
{

    public MyAdapter(Context context, ArrayList<InfoDisplay.StoreInfo>records){

        super(context,0,records);
    }
    @Override
    public View getView(final int position, View convertView, ViewGroup parent){

        Holder main=new Holder();
        if(convertView==null){
            convertView= LayoutInflater.from(getContext()).inflate(R.layout.listview_custom,parent,false);
            Holder h=new Holder();
            h.tv=(TextView)convertView.findViewById(R.id.Details);
            h.i=(Button)convertView.findViewById(R.id.Info);
            h.l=(Button)convertView.findViewById(R.id.Location);
            h.p=(Button)convertView.findViewById(R.id.Paid);
            h.i.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                }
            });
            h.p.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                }
            });
            h.l.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String str="Location of"+" "+position+" "+"is"+" "+getItem(position).getAddrees();
                }
            });

        }

        return convertView;
    }
    public class Holder{

        TextView tv;
        Button l;
        Button p;
        Button i;
    }
}
