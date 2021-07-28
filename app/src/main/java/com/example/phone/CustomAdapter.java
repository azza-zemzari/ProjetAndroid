
package com.example.phone;

import android.content.Context;
import android.content.Intent;
import android.provider.CalendarContract;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class CustomAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<Medecin> arrayList;
    private TextView name;
    private TextView  specialite;
    private TextView  adresse;
    private Button rendez;
    private ImageView image;


    public CustomAdapter(Context context, ArrayList<Medecin> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
    }
    @Override
    public int getCount() {
        return arrayList.size();
    }
    @Override
    public Object getItem(int position) {
        return position;
    }
    @Override
    public long getItemId(int position) {
        return position;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = LayoutInflater.from(context).inflate(R.layout.list_item, parent, false);
        name = convertView.findViewById(R.id.name);
        specialite = convertView.findViewById(R.id.specialite);
        adresse = convertView.findViewById(R.id.adresse);
        rendez = convertView.findViewById(R.id.rendez);
        Log.d("name?????????", arrayList.get(position).getName());
        name.setText(arrayList.get(position).getName());
        specialite.setText(arrayList.get(position).getSpecialite());
        adresse.setText(arrayList.get(position).getAdresse());
        rendez.setOnClickListener(new View.OnClickListener() {
                                      public void onClick(View v) {
                                          Intent intent =  new Intent(v.getContext() , Rendezvous.class);
                                          intent.putExtra("medecinId", arrayList.get(position).getId());
                                          intent.putExtra("medecinName", arrayList.get(position).getName());
                                          v.getContext().startActivity(intent);

                                      }// Do something in response to button click
                                  }
        );


        return convertView;
    }

}
