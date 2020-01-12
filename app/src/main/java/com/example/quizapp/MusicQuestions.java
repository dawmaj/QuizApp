package com.example.quizapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

import java.util.Random;

public class MusicQuestions extends AppCompatActivity {
    MediaPlayer player;
    float pitch = 0.95f;
    Button opt1,opt2,opt3,opt4, textq, pictureq;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE);
        setContentView(R.layout.activity_music_questions);
        textq = findViewById(R.id.Text_q);
        pictureq = findViewById(R.id.Picture_q);
        playSound();
        textq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try{
                    openTextQuiz();
                }
                catch (Exception err)
                {
                }
            }
        });

        pictureq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try{
                    openPictureQuiz();
                }
                catch (Exception err)
                {
                }
            }
        });
    }

    void playSound() {
        Random rand = new Random();
        int  n = rand.nextInt(5) + 1;
        if (player == null) {
            switch(n)
            {
                case 1:  player = MediaPlayer.create(this, R.raw.music1);
                    player.setPlaybackParams(player.getPlaybackParams().setPitch(pitch));
                    break;
                case 2: player = MediaPlayer.create(this, R.raw.music2);
                    player.setPlaybackParams(player.getPlaybackParams().setPitch(pitch));
                    break;
                case 3: player = MediaPlayer.create(this, R.raw.music3);
                    player.setPlaybackParams(player.getPlaybackParams().setPitch(pitch));
                    break;
                case 4: player = MediaPlayer.create(this, R.raw.music4);
                    player.setPlaybackParams(player.getPlaybackParams().setPitch(pitch));
                    break;
                case 5: player = MediaPlayer.create(this, R.raw.music5);
                    player.setPlaybackParams(player.getPlaybackParams().setPitch(pitch));
                    break;
            }
        }
        else {
            player = null;switch(n)
            {
                case 1:  player = MediaPlayer.create(this, R.raw.music1);
                    player.setPlaybackParams(player.getPlaybackParams().setPitch(pitch));
                    break;
                case 2: player = MediaPlayer.create(this, R.raw.music2);
                    player.setPlaybackParams(player.getPlaybackParams().setPitch(pitch));
                    break;
                case 3: player = MediaPlayer.create(this, R.raw.music3);
                    player.setPlaybackParams(player.getPlaybackParams().setPitch(pitch));
                    break;
                case 4: player = MediaPlayer.create(this, R.raw.music4);
                    player.setPlaybackParams(player.getPlaybackParams().setPitch(pitch));
                    break;
                case 5: player = MediaPlayer.create(this, R.raw.music5);
                    player.setPlaybackParams(player.getPlaybackParams().setPitch(pitch));
                    break;
            }
        }
        player.start();
    }

    void stopSound() {
        if (player != null) {
            player.release();
            player = null;
        }
    }

    void openTextQuiz(){
        stopSound();
        Intent intent = new Intent(this, QuizApp.class);
        startActivity(intent);
    }

    void openPictureQuiz() {
        stopSound();
        Intent intent = new Intent(this, PictureQuestions.class);
        startActivity(intent);
    }
}
