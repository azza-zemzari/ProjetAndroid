package com.example.phone;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class Resultat extends AppCompatActivity {
    private TextView resultat ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resultat);
        resultat=(TextView) findViewById(R.id.resulta);
        String normal = getIntent().getExtras().getString("normal");
        String grave = getIntent().getExtras().getString("grave");
        String message = result(Integer.valueOf(normal) ,Integer.valueOf(grave));
        resultat.setText(message);


        Button button = (Button) findViewById(R.id.consult);
        if (Integer.valueOf(normal)+Integer.valueOf(grave)<3) {
            button.setEnabled(false);
        }
        button.setOnClickListener(new View.OnClickListener() {
                                      public void onClick(View v) {
                                          String med = MedecinSelect(Integer.valueOf(normal) ,Integer.valueOf(grave));
                                          Intent intent = new Intent( Resultat.this, Listemedecin.class );
                                          intent.putExtra("param", med);
                                          startActivityForResult(intent, 0);

                                      }
                                  }
        );
    }
    private String result (int normal , int grave){
        String s;
        if ((normal <3)&& (grave==0)) {
            s ="Vous n'avez pas de COVID";

        }
        else if (normal >2) {
            s = "Vous présentez des symptômes légers de COVID";
        }
        else  {
            s= "Vous présentez des symptômes grave de COVID , vous devez consulter immediatement un spécialiste";
        }

        return  s;
    }
    private String MedecinSelect ( int normal , int grave){
        String x;
        if (grave >0){
            x = "spécialiste";
        } else if ( normal>2){
            x="généraliste";
        } else { x="";}
        return x;
    }

}