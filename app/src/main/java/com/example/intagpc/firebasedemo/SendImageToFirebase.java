package com.example.intagpc.firebasedemo;

import android.*;
import android.Manifest;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;

public class SendImageToFirebase extends AppCompatActivity {

    private Button btnSendImage;
    private StorageReference mStorageRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_image_to_firebase);
        mStorageRef = FirebaseStorage.getInstance().getReference();

        initWidgets();
        buttonCallListener();
    }

    public void initWidgets() {

        btnSendImage = (Button) findViewById(R.id.btnSendImage);
    }

    public void buttonCallListener() {

        btnSendImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ContextCompat.checkSelfPermission(SendImageToFirebase.this
                        ,
                        Manifest.permission.READ_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED) {

                }
                // Permission is not granted
                // Should we show an explanation?
                else {
                    uploadImageToFirebase();

                }
            }
        });
    }

    public void uploadImageToFirebase() {


        // Here, thisActivity is the current activity

        String path = "storage/emulated/0/DCIM/Camera/IMG_20180223_113037.jpg";
        Uri file = Uri.fromFile(new File(path.toString()));
        StorageReference riversRef = mStorageRef.child("Camera/IMG_20180223_113037.jpg");

        riversRef.putFile(file)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        // Get a URL to the uploaded content
                        Uri downloadUrl = taskSnapshot.getDownloadUrl();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception exception) {
                        // Handle unsuccessful uploads
                        // ...
                        Log.e("exception", exception.toString());
                    }
                });
    }
}
