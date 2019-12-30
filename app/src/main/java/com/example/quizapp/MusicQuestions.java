package com.example.quizapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

public class MusicQuestions extends AppCompatActivity {
    Button opt1,opt2,opt3,opt4, textq, pictureq;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE);
        setContentView(R.layout.activity_music_questions);
        textq = findViewById(R.id.Text_q);
        pictureq = findViewById(R.id.Picture_q);

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

    void openTextQuiz(){
        Intent intent = new Intent(this, QuizApp.class);
        startActivity(intent);
    }

    void openPictureQuiz() {
        Intent intent = new Intent(this, PictureQuestions.class);
        startActivity(intent);
    }
}
