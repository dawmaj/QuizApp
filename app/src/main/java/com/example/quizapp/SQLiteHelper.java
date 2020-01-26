package com.example.quizapp;

import android.content.ContentValues;
import android.content.Context;
import net.sqlcipher.database.SQLiteDatabase;
import net.sqlcipher.database.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class SQLiteHelper extends SQLiteOpenHelper {

    Context context;
    private static SQLiteHelper instance;
    private static String DB_NAME = "quiz.db";
    private static int DB_VER = 1;
    private SQLiteDatabase db;
    public SQLiteHelper(Context context)
    {
        super(context,DB_NAME,null,DB_VER);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        this.db = db;

        final String SQL_CREATE_QUESTIONS_TABLE = "CREATE TABLE " +
                Quiz.QUIZ_TABLE_NAME + " ( " +
                Quiz._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                Quiz.COLUMN_QUESTION + " TEXT, " +
                Quiz.COLUMN_OPTION1 + " TEXT, " +
                Quiz.COLUMN_OPTION2 + " TEXT, " +
                Quiz.COLUMN_OPTION3 + " TEXT, " +
                Quiz.COLUMN_OPTION4 + " TEXT, " +
                Quiz.COLUMN_TYPE + " TEXT, " +
                Quiz.COLUMN_IMAGE + " INTEGER," +
                Quiz.COLUMN_MUSIC + " INTEGER" +
                ")";

        final String SQL_ANSWERS = "CREATE TABLE " +
                Quiz.QUIZ_ANSWERS + " ( " +
                Quiz._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "+
                Quiz.COLUMN_ANSWER + " TEXT" +
                ")";

        final String SQL_CREATE_WATERMARKS = "CREATE TABLE " +
                Quiz.WATERMARKS_TABLE+ " ( " +
                Quiz.COLUMN_WATERMARK + " INTEGER" +
                ")";

        db.execSQL(SQL_CREATE_QUESTIONS_TABLE);
        db.execSQL(SQL_CREATE_WATERMARKS);
        db.execSQL(SQL_ANSWERS);
        fillQuestionsTable();
        fillWatermarks();
    }

    private void fillQuestionsTable() {
        Question q1 = new Question("Who draw this painting?", "Jan Matejko", "Pablo Picasso", "Andy Warholl" ,
                "Hans Memling","p",R.drawable.image1, 0);
        Question a1 = new Question("", "Hans Memling");
        addAns(a1, "p");
        addQuestion(q1);
        Question q2 = new Question("Who draw this painting?", "Hans Memling", "Pablo Picasso", "Andy Warholl" ,
                "Jan Matejko","p",R.drawable.image2, 0);
        Question a2 = new Question("", "Jan Matejko");
        addAns(a2, "p");
        addQuestion(q2);
        Question q3 = new Question("What nationality have this former ski jumper?", "Finland", "Poland", "Slovenia" ,
                "Norway","p",R.drawable.image3, 0);
        Question a3 = new Question("", "Norway");
        addAns(a3, "p");
        addQuestion(q3);
        Question q4 = new Question("Who draw this painting?", "Jan Matejko", "Wyspianski", "Andy Warholl" ,
                "Chełmoński","p",R.drawable.image4, 0);
        Question a4 = new Question("", "Chełmoński");
        addAns(a4, "p");
        addQuestion(q4);
        Question q5 = new Question("Whats country is this flag?", "USA", "Cuba", "Sudan" ,
                "Liberia","p",R.drawable.image5, 0);
        Question a5 = new Question("", "Liberia");
        addAns(a5, "p");
        addQuestion(q5);
        Question q6 = new Question("From what musical this song belongs?", "Backgammon", "Console", "Boxing" ,
                "Chess","m",0, R.raw.music1);
        Question a6 = new Question("", "Chess");
        addAns(a6, "m");
        addQuestion(q6);
        Question q7 = new Question("Whats the name of band?", "Playa", "Oooh", "A-ha" ,
                "Righeira","m",0, R.raw.music2);
        Question a7 = new Question("", "Righeira");
        addAns(a7, "m");
        addQuestion(q7);
        Question q8 = new Question("Who sang this?", "Kukulska", "Santor", "Rinn" ,
                "Jantar","m",0, R.raw.music3);
        Question a8 = new Question("", "Jantar");
        addAns(a8, "m");
        addQuestion(q8);
        Question q9 = new Question("Name that tune", "Mniej niż zero", "Kryzysowa narzeczona", "Mamona" ,
                "Minus zero","m",0, R.raw.music4);
        Question a9 = new Question("", "Minus zero");
        addAns(a9, "m");
        addQuestion(q9);
        Question q10 = new Question("Who sang this?", "Just 5", "Roy Orbison", "Mr. President" ,
                "Modern Talking","m",0, R.raw.music5);
        Question a10 = new Question("", "Modern Talking");
        addAns(a10, "m");
        addQuestion(q10);
    }

    private void fillWatermarks()
    {
        addWater(R.drawable.frame);
        addWater(R.drawable.frame2);
        addWater(R.drawable.frame3);
        addWater(R.drawable.frame4);
        addWater(R.drawable.frame5);
    }

    static public synchronized SQLiteHelper getInstance(Context context) {
        if (instance == null) {
            instance = new SQLiteHelper(context);
        }
        return instance;
    }

    private void addQuestion(Question question) {
        ContentValues cv = new ContentValues();
        cv.put(Quiz.COLUMN_QUESTION, question.getQUESTION());
        cv.put(Quiz.COLUMN_OPTION1, question.getOPTA());
        cv.put(Quiz.COLUMN_OPTION2, question.getOPTB());
        cv.put(Quiz.COLUMN_OPTION3, question.getOPTC());
        cv.put(Quiz.COLUMN_OPTION4, question.getANSWER());
        cv.put(Quiz.COLUMN_TYPE, question.getTYPE());
        cv.put(Quiz.COLUMN_IMAGE, question.getIMAGE());
        cv.put(Quiz.COLUMN_MUSIC, question.getMUSIC());
        db.insert(Quiz.QUIZ_TABLE_NAME, null, cv);
    }

    private void addAns(Question question, String type)
    {
        ContentValues cv = new ContentValues();
        cv.put(Quiz.COLUMN_ANSWER , question.getANSWER());
        cv.put(Quiz.COLUMN_TYPE, type);
        db.insert(Quiz.QUIZ_ANSWERS, null, cv);
    }

    private void addWater(int id)
    {
        ContentValues cv = new ContentValues();
        cv.put(Quiz.COLUMN_WATERMARK, id);
        db.insert(Quiz.WATERMARKS_TABLE, null, cv);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + Quiz.QUIZ_TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + Quiz.QUIZ_ANSWERS);
        db.execSQL("DROP TABLE IF EXISTS " + Quiz.WATERMARKS_TABLE);
        onCreate(db);
    }
}
