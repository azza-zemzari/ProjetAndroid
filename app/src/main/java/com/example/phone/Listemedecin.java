package com.example.phone;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class Listemedecin extends AppCompatActivity {

    private ListView mListView;
    private int i =0;
    private FirebaseDatabase mDatabase = FirebaseDatabase.getInstance();
    private DatabaseReference mDatabaseReference = mDatabase.getReference();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listemedecin);
        mListView = (ListView) findViewById(R.id.ListMed);
        String parametre = getIntent().getExtras().getString("param");
        final ArrayList<Medecin> MedecinGeneral = new ArrayList<Medecin>();
        final ArrayList<Medecin> MedecinSpecialiste = new ArrayList<Medecin>();
        final CustomAdapter arrayAdapter = new CustomAdapter(this, filtreMedecins(parametre,MedecinGeneral,MedecinSpecialiste));

        mListView.setAdapter(arrayAdapter);
        mDatabaseReference = mDatabase.getReference();
        DatabaseReference scoreRef = mDatabaseReference.child("medecins");
        scoreRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot data : dataSnapshot.getChildren()) {
                    Log.d("--data----", data.toString());
                    Medecin r=new Medecin();
                    r.setId((data.getKey()));
                    r.setName((data.child("name").getValue().toString()));
                    r.setSpecialite((data.child("specialite").getValue().toString()));
                    r.setAdresse((data.child("adresse").getValue().toString()));
                if (r.getSpecialite().equals("Médecin généraliste"))
                { MedecinGeneral.add(r);}
                else
                { MedecinSpecialiste.add(r);}

                }
                arrayAdapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.d("Failed to read value.",error.toString());
            }
        });


        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // change the checkbox state
                Log.d("2222222222 tag-------", String.valueOf(i));
            }
        });




    }

    private ArrayList<Medecin> filtreMedecins (  String speciality,ArrayList<Medecin> listG, ArrayList<Medecin>listS) {
        if (speciality.equals("généraliste")) {
return listG;           }
            else{
return  listS;
        }

    }
}