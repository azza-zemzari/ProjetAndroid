package com.example.phone;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Authentification extends AppCompatActivity {

    private Button Login;
    private FirebaseAuth mAuth;
    private EditText editTextEmailAddress;
    private EditText editTextPassword;
    private ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_authentification);
        Button login = (Button) findViewById(R.id.loginbutton);
        editTextEmailAddress = (EditText)findViewById(R.id.editTextEmailAddress);
        editTextPassword = (EditText)findViewById(R.id.editTextPassword);
        mAuth = FirebaseAuth.getInstance();
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                login();

            }
        });
        progressDialog = new ProgressDialog(this);
    }
    private void login() {
        Context context = getApplicationContext();
        //getting email and password from edit texts
        String email = editTextEmailAddress.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();
        if (TextUtils.isEmpty(email)) {
            Toast.makeText(this, "Please enter email", Toast.LENGTH_LONG).show();
            return;
        }

        if (TextUtils.isEmpty(password)) {
            Toast.makeText(this, "Please enter password", Toast.LENGTH_LONG).show();
            return;
        }


        progressDialog.setMessage("veuillez patienter...");
        progressDialog.show();
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        progressDialog.dismiss();


                        if (!task.isSuccessful()) {
                            progressDialog.dismiss();


                            Toast.makeText(context, "echec", Toast.LENGTH_SHORT).show();
                            //Toast.makeText(getActivity(), "signInWithEmail:failed", Toast.LENGTH_SHORT).show();
                        }
                        else{

                            Toast.makeText(context, "reussir", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(Authentification.this, Int1.class));;
                        }


                    }
                });


    }


}