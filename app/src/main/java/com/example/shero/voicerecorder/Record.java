package com.example.shero.voicerecorder;

import android.app.Activity;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.IOException;

public class Record extends AppCompatActivity  {
    private static final String TAG = "RecordActivity";

    public Button mRecordBtn;
    private MediaRecorder recorder;

    public Button mPlayBtn;
    private MediaPlayer player;
    private String fileName = null;
    private static final String LOG_TAG = "Record log";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chat_item_left);
        Log.d(TAG, "onCreate: Record Activity started");
        Toast.makeText(this, "Record activity started", Toast.LENGTH_SHORT).show();

        mRecordBtn = (Button) findViewById(R.id.recordBtn);
        mPlayBtn = (Button) findViewById(R.id.playButton);

        fileName = Environment.getExternalStorageDirectory().getAbsolutePath();
        fileName += "/recorded_audio.3gp";

        mRecordBtn.setOnClickListener(new View.OnClickListener() {
            boolean mStartRecording = true;
            @Override
            public void onClick(View v) {
                onRecord(mStartRecording);
                if(mStartRecording) mRecordBtn.setText("Stop Recording");
                else mRecordBtn.setText("Start Recording");
                mStartRecording = !mStartRecording;
            }
        });

        mPlayBtn.setOnClickListener(new View.OnClickListener() {
            boolean mStartPlaying = true;
            @Override
            public void onClick(View v) {
                onPlay(mStartPlaying);

                if(mStartPlaying) mPlayBtn.setText("Stop Playing");
                else mPlayBtn.setText("Start Playing");

                mStartPlaying = !mStartPlaying;
                Toast.makeText(Record.this, "Play button clicked", Toast.LENGTH_SHORT).show();
            }
        });


        super.onStop();
        if (recorder != null) {
            recorder.release();
            recorder = null;
        }

        if (player != null) {
            player.release();
            player = null;
        }


    }


    public void checkRecord(){
        Log.d(TAG, "checkRecord: is working");
    }

    @Override
    protected void onStart() {
        super.onStart();

    }
    //functions for recording voice
    private void startRecording() {
        recorder = new MediaRecorder();
        recorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        recorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        recorder.setOutputFile(fileName);
        recorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);

        try {
            recorder.prepare();
        } catch (IOException e) {
            Log.e(LOG_TAG, "prepare() failed");
        }

        recorder.start();
    }

    private void stopRecording() {
        recorder.stop();
        recorder.release();
        recorder = null;
    }

    //functions to play it
    private void startPlaying() {
        player = new MediaPlayer();
        try {
            player.setDataSource(fileName);
            player.prepare();
            player.start();
        } catch (IOException e) {
            Log.e(LOG_TAG, "prepare() failed");
        }
    }

    private void stopPlaying() {
        player.release();
        player = null;
    }


    //used for changing text when button pressed(boolean)
    private void onRecord(boolean start) {
        if (start) {
            startRecording();
        } else {
            stopRecording();
        }
    }

    private void onPlay(boolean start) {
        if (start) {
            startPlaying();
        } else {
            stopPlaying();
        }
    }
}
