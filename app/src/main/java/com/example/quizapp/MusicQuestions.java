package com.example.quizapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import net.sqlcipher.database.SQLiteDatabase;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Random;

public class MusicQuestions extends AppCompatActivity {
    MediaPlayer player;
    float pitch = 0.95f;
    private TextView questionField,scores;
    Button opt1,opt2,opt3,opt4, textq, pictureq;
    SQLiteHelper connect;
    int score = 0;
    int qid=0;
    int qcount=0;
    Question currentQ, cansQ;
    private ArrayList<Question> questions = new ArrayList<>();
    private ArrayList<Question> correctans = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE);
        setContentView(R.layout.activity_music_questions);
        questionField = findViewById(R.id.Question);
        scores = findViewById(R.id.Score);
        opt1 =  findViewById(R.id.A_ans);
        opt2 =  findViewById(R.id.B_ans);
        opt3 =  findViewById(R.id.C_ans);
        opt4 =  findViewById(R.id.D_ans);
        textq = findViewById(R.id.Text_q);
        pictureq = findViewById(R.id.Picture_q);
        getQuestions();
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

        opt1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try{
                    handleButtonClick(1);
                }
                catch (Exception err)
                {
                }
            }
        });

        opt2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try{
                    handleButtonClick(2);
                }
                catch (Exception err)
                {
                }
            }
        });

        opt3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try{
                    handleButtonClick(3);
                }
                catch (Exception err)
                {
                }
            }
        });

        opt4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try{
                    handleButtonClick(4);
                }
                catch (Exception err)
                {
                }
            }
        });
    }

    void getQuestions() {
        connect = new SQLiteHelper(this);
        SQLiteDatabase.loadLibs(this);
        qcount=0;
        qid=0;
        score = 0;
        questions.clear();
        correctans.clear();
        Random rand = new SecureRandom();
        byte bytes[] = new byte[512];
        rand.nextBytes(bytes);
        String hash = bytes.toString();
        String secret = hash;
        SQLiteDatabase db = connect.getInstance(this).getReadableDatabase(secret);
        String selectQuery = "SELECT * FROM " + Quiz.QUIZ_TABLE_NAME + " WHERE " + Quiz.COLUMN_TYPE + "='m'";
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                String q = cursor.getString(cursor.getColumnIndex(Quiz.COLUMN_QUESTION));
                String ans =  cursor.getString(cursor.getColumnIndex(Quiz.COLUMN_OPTION4));
                String ans1 = cursor.getString(cursor.getColumnIndex(Quiz.COLUMN_OPTION1));
                String ans2 = cursor.getString(cursor.getColumnIndex(Quiz.COLUMN_OPTION2));
                String ans3 = cursor.getString(cursor.getColumnIndex(Quiz.COLUMN_OPTION3));
                Integer id = cursor.getInt(cursor.getColumnIndex(Quiz.COLUMN_MUSIC));
                int  n = rand.nextInt(4);

                Question question =null;

                switch(n)
                {
                    case 1 : question = new Question(q,ans2,ans1,ans3,ans,id,"");
                        break;
                    case 2 :  question = new Question(q,ans1,ans,ans3,ans2,id,"");
                        break;
                    case 3 :  question = new Question(q,ans3,ans,ans1,ans2,id,"");
                        break;
                    case 4 :  question = new Question(q,ans,ans2,ans3,ans1,id,"");
                        break;
                }

                Question cq = new Question(q,ans);
                questions.add(question);
                correctans.add(cq);
            } while (cursor.moveToNext());
        }
        setList();
    }

    void setList() {
        stopSound();
        currentQ = questions.get(qid);
        cansQ = correctans.get(qid);
        playSound(currentQ.getMUSIC());
        questionField.setText(currentQ.getQUESTION());
        opt1.setText(currentQ.getOPTA());
        opt2.setText(currentQ.getOPTB());
        opt3.setText(currentQ.getOPTC());
        opt4.setText(currentQ.getANSWER());
        qcount++;
        qid++;
    }

    void playSound(int id) {
        if (player == null) {
            player = MediaPlayer.create(this, id);
            player.setPlaybackParams(player.getPlaybackParams().setPitch(pitch));
            player.start();
        }
        else
        {
            player = null;
            player = MediaPlayer.create(this, id);
            player.setPlaybackParams(player.getPlaybackParams().setPitch(pitch));
            player.start();
        }
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

    void handleButtonClick(int b) {
        if(b==1)
        {
            if (opt1.getText().toString().equals(correctans.get(qid - 1).getANSWER())) {
                score++;
                scores.setText("SCORE: " + score);
                setList();
            }
            if (opt1.getText().toString().equals(correctans.get(qid - 1).getANSWER()) && (qid >= questions.size()) ) {
                score++;
                scores.setText("SCORE: " + score);
                getQuestions();
            }
            else if (qid >= questions.size()) {
                getQuestions();
            }
            else
                setList();
        }

        if(b==2)
        {
            if (opt2.getText().toString().equals(correctans.get(qid - 1).getANSWER())) {
                score++;
                scores.setText("SCORE: " + score);
                setList();
            }
            if (opt2.getText().toString().equals(correctans.get(qid - 1).getANSWER()) && (qid >= questions.size()) ) {
                score++;
                scores.setText("SCORE: " + score);
                getQuestions();
            }
            else if (qid >= questions.size()) {
                getQuestions();
            }
            else
                setList();
        }
        if(b==3)
        {
            if (opt3.getText().toString().equals(correctans.get(qid - 1).getANSWER())) {
                score++;
                scores.setText("SCORE: " + score);
                setList();
            }
            if (opt3.getText().toString().equals(correctans.get(qid - 1).getANSWER()) && (qid >= questions.size()) ) {
                score++;
                scores.setText("SCORE: " + score);
                getQuestions();
            }
            else if (qid >= questions.size()) {
                getQuestions();
            }
            else
                setList();
        }
        if(b==4) {
            if (opt4.getText().toString().equals(correctans.get(qid - 1).getANSWER())) {
                score++;
                scores.setText("SCORE: " + score);
                setList();
            }
            if (opt4.getText().toString().equals(correctans.get(qid - 1).getANSWER()) && (qid >= questions.size()) ) {
                score++;
                scores.setText("SCORE: " + score);
                getQuestions();
            }
            else if (qid >= questions.size()) {
                getQuestions();
            }
            else
                setList();
        }
    }
}
