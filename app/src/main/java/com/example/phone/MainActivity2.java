package com.example.phone;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckedTextView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class MainActivity2 extends AppCompatActivity {
    private ListView mListView;
    private int i =0;
    private int nrl =0;
    private int grv =0;
    private FirebaseDatabase mDatabase = FirebaseDatabase.getInstance();

    private DatabaseReference mDatabaseReference = mDatabase.getReference();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        mListView = (ListView) findViewById(R.id.listView);
        final List<String> list = new ArrayList<>();
        final ArrayList<Symptome> dataModels = new ArrayList<Symptome>();

        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(MainActivity2.this, android.R.layout.simple_list_item_multiple_choice, list);


        mListView.setAdapter(arrayAdapter);
        mDatabaseReference = mDatabase.getReference();
        DatabaseReference scoreRef = mDatabaseReference.child("symptomes");
       // Log.d("Message tag-------", scoreRef.toString());
       scoreRef.addValueEventListener(new ValueEventListener() {


            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {


                for (DataSnapshot data : dataSnapshot.getChildren()) {

                    Symptome r=new Symptome();
                    String name;
                    name=data.child("name").getValue().toString();
                    r.setName((data.child("name").getValue().toString()));
                    r.setNiveau((data.child("niveau").getValue().toString()));
                    dataModels.add(r);
                    list.add(name);

                }
               arrayAdapter.notifyDataSetChanged();

                Log.d("Message tag-------", list.toString());
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w("Failed to read value.", error.toException());
            }
        });


        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // change the checkbox state
                CheckedTextView checkedTextView = ((CheckedTextView)view);
                checkedTextView.setChecked(!checkedTextView.isChecked());
                Log.d("le symptome est =",list.get(position));
                String niveau = dataModels.get(position).getNiveau();
                Log.d("le niveau est =",niveau);

                if(checkedTextView.isChecked())
                {


                       if (niveau.equals("normal"))
                       { nrl= nrl+1;
                       } else {
                           grv = grv+1;
                       }

                }
                else
                {

                    if (niveau.equals("normal"))
                    { nrl= nrl-1;
                    } else {
                        grv = grv-1;
                    }
                }

            }
        });




  Button button = (Button) findViewById(R.id.analyse);
        button.setOnClickListener(new View.OnClickListener() {
                                      public void onClick(View v) {
                                          Intent intent = new Intent( MainActivity2.this, Resultat.class );
                                          intent.putExtra("normal",  String.valueOf(nrl));
                                          intent.putExtra("grave",  String.valueOf(grv));
                                          startActivityForResult(intent, 0);

                                      }
                                  }
        );


    }



}