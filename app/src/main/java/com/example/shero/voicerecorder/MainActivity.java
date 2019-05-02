package com.example.shero.voicerecorder;

import android.Manifest;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Layout;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.shero.voicerecorder.Adapter.MessageAdapter;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.IOException;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    FirebaseDatabase mFirebaseDatabase;
    DatabaseReference mRef;

    //vars

    private ArrayList<String> bot_textArray = new ArrayList<>();
    private ArrayList<String> user_textArray = new ArrayList<>();
    private ArrayList<String> mImageUrls = new ArrayList<>();
    private ArrayList<String> audioList = new ArrayList<>();

    private static final int REQUEST_RECORD_AUDIO_PERMISSION = 200;




    // Requesting permission to RECORD_AUDIO
    private boolean permissionToRecordAccepted = false;
    private String [] permissions = {Manifest.permission.RECORD_AUDIO};

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode){
            case REQUEST_RECORD_AUDIO_PERMISSION:
                permissionToRecordAccepted  = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                break;
        }
        if (!permissionToRecordAccepted ) finish();

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d(TAG, "onCreate: started");
        ActivityCompat.requestPermissions(this, permissions, REQUEST_RECORD_AUDIO_PERMISSION);

        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mRef = mFirebaseDatabase.getReference("Data");

        initImageBitmaps();
    }

    //add functions for RecyclerView
    private void initImageBitmaps(){
        Log.d(TAG, "initImageBitmaps: preparing bitmaps");
        mImageUrls.add("https://firebasestorage.googleapis.com/v0/b/voicerecorder-99718.appspot.com/o/barista.jpg?alt=media&token=49ecccb9-bc2b-49b3-aae9-ea882b39f1be");
        bot_textArray.add("Hello! What can i get you?");
        user_textArray.add("An Espresso, please");
        audioList.add("https://firebasestorage.googleapis.com/v0/b/voicerecorder-99718.appspot.com/o/1.mp3?alt=media&token=0123c319-5fde-4aff-b280-b5f5a63660c8");

        initRecyclerView();
    }


    private void initRecyclerView(){
        Log.d(TAG, "initRecyclerView: intit recyclerView");
        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        MessageAdapter adapter = new MessageAdapter(mImageUrls,bot_textArray,user_textArray,audioList,this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

}
