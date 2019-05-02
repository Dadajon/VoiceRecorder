package com.example.shero.voicerecorder.Adapter;


import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.shero.voicerecorder.R;
import com.example.shero.voicerecorder.Record;

import java.io.IOException;
import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.ViewHolder> {

    private static final String TAG = "RecyclerViewAdapter";

    private ArrayList<String> mImages;
    private ArrayList<String> bot_textArray;
    private ArrayList<String> user_textArray;
    private ArrayList<String> audioArray;
    private Context context;
    private MediaPlayer player = new MediaPlayer();
    Record record = new Record();


    public MessageAdapter(ArrayList<String> mImages, ArrayList<String> bot_textArray, ArrayList<String> user_textArray, ArrayList<String> audioArray, Context context) {
        this.mImages = mImages;
        this.bot_textArray = bot_textArray;
        this.user_textArray = user_textArray;
        this.audioArray = audioArray;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view_bot = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.chat_item_left,viewGroup,false);
        ViewHolder  holder = new ViewHolder(view_bot);

        return holder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, final int i) {
        Log.d(TAG, "onBindViewHolder: called.");

        Glide.with(context)
                .asBitmap()
                .load(mImages.get(i))
                .into(viewHolder.image);

        viewHolder.bot_text.setText(bot_textArray.get(i));
        viewHolder.user_text.setText(user_textArray.get(i));

        try {
            player.setAudioStreamType(AudioManager.STREAM_MUSIC);
            player.setDataSource(audioArray.get(i));
            player.prepare();
            player.start();
        } catch (Exception e) {
            Log.d(TAG, "onBindViewHolder: failed to stream audio");
        }

        /// stream completion -> user text.setVisible
        player.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                viewHolder.user_layout.setVisibility(View.VISIBLE);

            }
        });

        //Replay Button for bot_chat /////////////////////////
        viewHolder.replay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              if(player != null){
                  player.stop();
                  try{
                      player.prepare();
                      player.start();
                  } catch (IOException e) {

                  }
              }
            }
        });


        //can be clickable here
        viewHolder.bot_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: " + mImages.get(i));
                Toast.makeText(context,bot_textArray.get(i), Toast.LENGTH_SHORT).show();
            }
        });


    }

    @Override
    public int getItemCount() {
        return bot_textArray.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        CircleImageView image;
        TextView bot_text, user_text;
        RelativeLayout  bot_layout, user_layout;
        Button replay, rec,play;

        public ViewHolder(View view_bot){
            super(view_bot);

            replay = itemView.findViewById(R.id.repeat_audio);
            rec = itemView.findViewById(R.id.recordBtn);
            play = itemView.findViewById(R.id.playButton)
;           image = itemView.findViewById(R.id.profile_image);
            bot_text = itemView.findViewById(R.id.bot_text);
            user_text = itemView.findViewById(R.id.user_text);
            bot_layout = itemView.findViewById(R.id.bot_layout);
            user_layout = itemView.findViewById(R.id.user_layout);
        }

    }
}
