package com.example.phone;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.mbms.StreamingServiceInfo;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.applandeo.materialcalendarview.CalendarView;
import com.applandeo.materialcalendarview.EventDay;
import com.applandeo.materialcalendarview.listeners.OnDayClickListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class Rendezvous extends AppCompatActivity {
   private CalendarView calendrier ;

    private FirebaseAuth mAuth;
    private FirebaseDatabase mDatabase = FirebaseDatabase.getInstance();
    private DatabaseReference mDatabaseReference = mDatabase.getReference();

    private String patient;
    private String date;

    private String nameMed;
    private String Email;
    private String key;


    private  List<Calendar> calendars = new ArrayList<>();
    List<Calendar> calendarsSelected = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rendezvous);
        Button confirmer = (Button) findViewById(R.id.confirmer);
        Button supprimer = (Button) findViewById(R.id.supprimer);
      /*  AlertDialog.Builder builder =new AlertDialog.Builder(Rendezvous.this);
        builder.setTitle("Suppresion");
        builder.setMessage("vous avez supprimer le rendez-vous");
        builder.setPositiveButton("ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        }); */



        String medid = getIntent().getExtras().getString("medecinId");
        nameMed = getIntent().getExtras().getString("medecinName");
        confirmer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Rdv rdv= new Rdv(date,patient);
                Log.d("redv-------",rdv.toString());
                DatabaseReference rdvRef = mDatabaseReference.child("medecins").child(medid).child("rendezVous").push();
                 key = rdvRef.getKey();
                rdvRef.setValue(rdv);
                supprimer.setEnabled(true);
                sendMail();

            }
        });
        supprimer.setEnabled(false);
        supprimer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Rdv rdv= new Rdv(date,patient);
                Log.d("redv-------",rdv.toString());
                DatabaseReference rdvRef = mDatabaseReference.child("medecins").child(medid).child("rendezVous").child(key);
              rdvRef.removeValue();

                AlertDialog alertDialog = new AlertDialog.Builder(Rendezvous.this).create();
                alertDialog.setTitle("Alert");
                alertDialog.setMessage("Vous avez supprimer votre rendezvous");
                alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                alertDialog.show();


            }
        });

        calendrier = findViewById(R.id.calendarView);
        Calendar min = Calendar.getInstance();
        calendrier.setMinimumDate(min);
        disableDatePrise(calendrier,medid);

        calendrier.setOnDayClickListener(new OnDayClickListener() {
            @Override
            public void onDayClick(EventDay eventDay) {
                List<EventDay> events = new ArrayList<>();
                Calendar randomCalendar = eventDay.getCalendar();
                calendarsSelected.add(randomCalendar);
                events.add(new EventDay(randomCalendar, R.drawable.sample_circle));
                calendrier.setEvents(events);


                SimpleDateFormat format = new SimpleDateFormat("dd/MM/yy");
                date =format.format(randomCalendar.getTime());
                Log.d("dateeeeeeSelect",date);
            }
        });
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser() ;
        if (user != null){
            Log.d( "user est ------", String.valueOf(user.getUid()));
        }else {
            Log.d( "erreur ------","user not found");
        }
      user.sendEmailVerification();
        String id = user.getUid() ;
        mDatabaseReference = mDatabase.getReference();
        DatabaseReference firebaseRef = mDatabaseReference.child("users").child(id);
        firebaseRef.addValueEventListener(new ValueEventListener() {
                                              @Override
                                              public void onDataChange(@NonNull DataSnapshot snapshot) {
                                                  Log.d("name user = ------",snapshot.child("name").getValue().toString());
                                                  patient= snapshot.child("name").getValue().toString();
                                                  Email=snapshot.child("email").getValue().toString();
                                              }

                                              @Override
                                              public void onCancelled(@NonNull DatabaseError error) {

                                              }
                                          }
        );





    }
    private void  disableDatePrise( CalendarView calendarView, String id)
    {
        List<EventDay> events = new ArrayList<>();
        mDatabaseReference = mDatabase.getReference();
        DatabaseReference firebaseRef = mDatabaseReference.child("medecins").child(id).child("rendezVous");
        firebaseRef.addValueEventListener(new ValueEventListener() {
                                              @Override
                                              public void onDataChange(@NonNull DataSnapshot snapshot) {
                                                  for (DataSnapshot data : snapshot.getChildren()) {
                                                      Log.d("date =========", data.child("date").getValue().toString());
                                                      //calendrier.setDate(Long.valueOf(data.child("data").getValue().toString()) );
                                                      String ss= data.child("date").getValue().toString();
                                                      SimpleDateFormat format = new SimpleDateFormat("dd/MM/yy");
                                                      Date date = null;
                                                      try {
                                                          date = format.parse(ss);
                                                      } catch (ParseException e) {
                                                          e.printStackTrace();
                                                      }
                                                      Calendar calendar = Calendar.getInstance();
                                                      calendar.setTime(date);
                                                      calendars.add(calendar);
                                                      calendarView.setDisabledDays(calendars);
                                                      calendarView.setEvents(events);
                                                  } //

                                              }

                                              @Override
                                              public void onCancelled(@NonNull DatabaseError error) {

                                              }
                                          }
        );
    }

    private  void sendMail() {
        Intent i = new Intent(Intent.ACTION_SEND);
        i.setType("message/rfc822");
        i.putExtra(Intent.EXTRA_EMAIL, new String[]{Email});
        i.putExtra(Intent.EXTRA_SUBJECT, "Rendez-vous Toubib");
        i.putExtra(Intent.EXTRA_TEXT, "Vous avec un rendez vous avec " + nameMed + " la date est " + date);

        try {
            startActivity(i);
        } catch (ActivityNotFoundException e) {

        }
    }
}