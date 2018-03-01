package com.example.intagpc.firebasedemo;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class MainActivity extends AppCompatActivity {
    private Button btnRegister;
    private EditText etName, etPassword, etEmail;
    private String emaitText, passwordText, nameText;
    private TextView tvLogin;

    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        firebaseAuth = FirebaseAuth.getInstance();

        initWidgets();
        buttonCallListener();
        textViewCallListener();

    }

    public void initWidgets()

    {
        etEmail = (EditText) findViewById(R.id.editmail);
        etPassword = (EditText) findViewById(R.id.editpassword);
        etName = (EditText) findViewById(R.id.editname);
        btnRegister = (Button) findViewById(R.id.btnsignup);
        tvLogin = (TextView) findViewById(R.id.tvLogin);
    }


    public void buttonCallListener() {

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                emaitText = etEmail.getText().toString();
                passwordText = etPassword.getText().toString();
                nameText = etName.getText().toString();

                if (checkFields(emaitText, passwordText, nameText)) {


                    if (isEmailValid(emaitText)) {
                        signUp(emaitText, passwordText);

                    } else {
                        Toast.makeText(MainActivity.this, "please type valid email address", Toast.LENGTH_LONG).show();
                    }

                } else {
                    Toast.makeText(MainActivity.this, "some fields are empty", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    public void textViewCallListener() {

        tvLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(MainActivity.this, Login.class);
                startActivity(intent);
            }
        });
    }

    public boolean checkFields(String email, String password, String name) {

        if (TextUtils.isEmpty(email) || TextUtils.isEmpty(password) || TextUtils.isEmpty(name)) {

//            etName.setError("Name should not be empty");

            return false;
        } else {


            return true;
        }
    }

    public boolean isEmailValid(String email) {

        if (Patterns.EMAIL_ADDRESS.matcher(email).matches()) {

            return true;

        } else {


            return false;
        }
    }

    public void signUp(String email, String password) {

        this.firebaseAuth.createUserWithEmailAndPassword(email, password).
                addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if (task.isSuccessful()) {

                            Toast.makeText(MainActivity.this, "Data is inserted", Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(MainActivity.this, "error", Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }

}
