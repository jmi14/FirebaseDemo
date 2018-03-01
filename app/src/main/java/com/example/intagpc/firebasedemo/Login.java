package com.example.intagpc.firebasedemo;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;


public class Login extends AppCompatActivity {

    private Button btnLogin;
    private EditText etEmail;
    private EditText etPassword;

    private FirebaseAuth firebaseAuth;
    private boolean status;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        firebaseAuth = FirebaseAuth.getInstance();
        initWidgets();
        buttonCallListener();

    }

    public void initWidgets() {

        btnLogin = (Button) findViewById(R.id.button);
        etPassword = (EditText) findViewById(R.id.etpass);
        etEmail = (EditText) findViewById(R.id.etemail);
    }


    public void buttonCallListener() {

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = etEmail.getText().toString();
                String password = etPassword.getText().toString();

               if( signInFirebase(email, password))
               {

                   Intent intent = new Intent(Login.this,SendImageToFirebase.class);
                   startActivity(intent);
               }
               else{
                   Toast.makeText(Login.this,"error",Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    public boolean signInFirebase(String email, String password) {

        this.firebaseAuth.signInWithEmailAndPassword(email, password).
                addOnCompleteListener(Login.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if (task.isSuccessful()) {
                            status = true;
                        } else {
                            status = false;
                        }

                    }
                });

        return status;
    }

}
