package com.example.quizapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

public class PictureQuestions extends AppCompatActivity {
    Button opt1,opt2,opt3,opt4, textq, musicq;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE);
        setContentView(R.layout.activity_picture_questions);
        textq = findViewById(R.id.Text_q);
        musicq = findViewById(R.id.Music_q);

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

        musicq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try{
                    openMusicQuiz();
                }
                catch (Exception err)
                {
                }
            }
        });
    }

    void openTextQuiz(){
        Intent intent = new Intent(this, QuizApp.class);
        startActivity(intent);
    }

    void openMusicQuiz() {
        Intent intent = new Intent(this, MusicQuestions.class);
        startActivity(intent);
    }
}
