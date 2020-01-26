package com.example.quizapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import net.sqlcipher.database.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Random;

public class PictureQuestions extends AppCompatActivity {
    Button opt1,opt2,opt3,opt4, textq, musicq;
    private TextView questionField,scores;
    private ImageView img;
    int score = 0;
    int qid=0;
    int qcount=0;
    SQLiteHelper connect;
    Question currentQ, cansQ;
    private ArrayList<Question> questions = new ArrayList<>();
    private ArrayList<Question> correctans = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE);
        setContentView(R.layout.activity_picture_questions);
        img = findViewById(R.id.image);
        questionField = findViewById(R.id.Question);
        scores = findViewById(R.id.Score);
        opt1 =  findViewById(R.id.A_ans);
        opt2 =  findViewById(R.id.B_ans);
        opt3 =  findViewById(R.id.C_ans);
        opt4 =  findViewById(R.id.D_ans);
        textq = findViewById(R.id.Text_q);
        musicq = findViewById(R.id.Music_q);
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
        String selectQuery = "SELECT * FROM " + Quiz.QUIZ_TABLE_NAME + " WHERE " + Quiz.COLUMN_TYPE + "='p'";
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                String q = cursor.getString(cursor.getColumnIndex(Quiz.COLUMN_QUESTION));
                String ans =  cursor.getString(cursor.getColumnIndex(Quiz.COLUMN_OPTION4));
                String ans1 = cursor.getString(cursor.getColumnIndex(Quiz.COLUMN_OPTION1));
                String ans2 = cursor.getString(cursor.getColumnIndex(Quiz.COLUMN_OPTION2));
                String ans3 = cursor.getString(cursor.getColumnIndex(Quiz.COLUMN_OPTION3));
                Integer id = cursor.getInt(cursor.getColumnIndex(Quiz.COLUMN_IMAGE));
                int  n = rand.nextInt(4);

                Question question =null;

                switch(n)
                {
                    case 1 : question = new Question(q,ans2,ans1,ans3,ans,id);
                        break;
                    case 2 :  question = new Question(q,ans1,ans,ans3,ans2,id);
                        break;
                    case 3 :  question = new Question(q,ans3,ans,ans1,ans2,id);
                        break;
                    case 4 :  question = new Question(q,ans,ans2,ans3,ans1,id);
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
        currentQ = questions.get(qid);
        cansQ = correctans.get(qid);
        questionField.setText(currentQ.getQUESTION());
        opt1.setText(currentQ.getOPTA());
        opt2.setText(currentQ.getOPTB());
        opt3.setText(currentQ.getOPTC());
        opt4.setText(currentQ.getANSWER());
        qcount++;
        qid++;
        Bitmap bmap = addWaterMark(BitmapFactory.decodeResource(getResources(), currentQ.getIMAGE()));
        img.setImageBitmap(bmap);
    }
    void openTextQuiz(){
        Intent intent = new Intent(this, QuizApp.class);
        startActivity(intent);
    }

    private Bitmap addWaterMark(Bitmap src) {
        SQLiteDatabase db = connect.getInstance(this).getReadableDatabase("test");
        int w = src.getWidth();
        int h = src.getHeight();
        int i = 0;
        Integer water [] = new Integer[6];
        Bitmap result = Bitmap.createBitmap(w, h, src.getConfig());
        Canvas canvas = new Canvas(result);
        canvas.drawBitmap(src, 0, 0, null);
        String selectQuery = "SELECT * FROM " + Quiz.WATERMARKS_TABLE;
        Cursor cursor = db.rawQuery(selectQuery, null);
        int  n = new SecureRandom().nextInt(5);
        if (cursor.moveToFirst()) {
            do {
                water[i] = cursor.getInt(cursor.getColumnIndex(Quiz.COLUMN_WATERMARK));
                i++;
            } while (cursor.moveToNext());
        }
        switch(n)
        {
            case 0 :
                canvas.drawBitmap(adjustOpacity(BitmapFactory.decodeResource(getResources(), water[0]),10), 0, 0, null);
                return result;
            case 1 :
                canvas.drawBitmap(adjustOpacity(BitmapFactory.decodeResource(getResources(), water[1]),10), 0, 0, null);
                return result;
            case 2 :
                canvas.drawBitmap(adjustOpacity(BitmapFactory.decodeResource(getResources(), water[2]),10), 0, 0, null);
                return result;
            case 3 :
                canvas.drawBitmap(adjustOpacity(BitmapFactory.decodeResource(getResources(), water[3]),10), 0, 0, null);
                return result;
            case 4 :
                canvas.drawBitmap(adjustOpacity(BitmapFactory.decodeResource(getResources(), water[4]),10), 0, 0, null);
                return result;
            default :
                canvas.drawBitmap(adjustOpacity(BitmapFactory.decodeResource(getResources(), R.drawable.frame),10), 0, 0, null);
                return result;
        }
    }

    private Bitmap adjustOpacity(Bitmap bitmap, int opacity)
    {
        Bitmap mutableBitmap = bitmap.isMutable()
                ? bitmap
                : bitmap.copy(Bitmap.Config.ARGB_8888, true);
        Canvas canvas = new Canvas(mutableBitmap);
        int colour = (opacity & 0xFF) << 24;
        canvas.drawColor(colour, PorterDuff.Mode.DST_IN);
        return mutableBitmap;
    }

    void openMusicQuiz() {
        Intent intent = new Intent(this, MusicQuestions.class);
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
