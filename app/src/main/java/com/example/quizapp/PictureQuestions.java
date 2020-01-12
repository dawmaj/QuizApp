package com.example.quizapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.PorterDuff;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;

import java.util.Random;

public class PictureQuestions extends AppCompatActivity {
    Button opt1,opt2,opt3,opt4, textq, musicq;
    ImageView img;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE);
        setContentView(R.layout.activity_picture_questions);
        textq = findViewById(R.id.Text_q);
        musicq = findViewById(R.id.Music_q);
        ImageView img = findViewById(R.id.image);
        Random rand = new Random();
        int  n = rand.nextInt(5) + 1;
        switch(n)
        {
            case 1:  Bitmap bmap = addWaterMark(BitmapFactory.decodeResource(getResources(), R.drawable.image1));
                img.setImageBitmap(bmap);
                break;
            case 2: Bitmap bm = addWaterMark(BitmapFactory.decodeResource(getResources(), R.drawable.image2));
                img.setImageBitmap(bm);
                break;
            case 3: Bitmap BM = addWaterMark(BitmapFactory.decodeResource(getResources(), R.drawable.image3));
                img.setImageBitmap(BM);
                break;
            case 4: Bitmap bitmap = addWaterMark(BitmapFactory.decodeResource(getResources(), R.drawable.image4));
                img.setImageBitmap(bitmap);
                break;
            case 5: Bitmap bitm = addWaterMark(BitmapFactory.decodeResource(getResources(), R.drawable.image5));
                img.setImageBitmap(bitm);
                break;
        }
        //Bitmap watermarkInImage = addWaterMark(bmap);
        //img.setImageBitmap(bmap);
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

    private Bitmap addWaterMark(Bitmap src) {
        int w = src.getWidth();
        int h = src.getHeight();
        Bitmap result = Bitmap.createBitmap(w, h, src.getConfig());
        Canvas canvas = new Canvas(result);
        canvas.drawBitmap(src, 0, 0, null);
        Bitmap waterMark = adjustOpacity(BitmapFactory.decodeResource(getResources(), R.drawable.frame),10);
        canvas.drawBitmap(waterMark, 0, 0, null);
        return result;
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
}
