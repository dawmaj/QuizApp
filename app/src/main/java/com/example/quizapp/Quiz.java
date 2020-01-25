package com.example.quizapp;

import android.provider.BaseColumns;

public class Quiz implements BaseColumns {
    public static final String QUIZ_TABLE_NAME = "quiz_questions";
    public static final String COLUMN_TYPE = "typeq";
    public static final String COLUMN_QUESTION = "question";
    public static final String COLUMN_OPTION1 = "option1";
    public static final String COLUMN_OPTION2 = "option2";
    public static final String COLUMN_OPTION3 = "option3";
    public static final String COLUMN_OPTION4 = "option4";
    public static final String COLUMN_ANSWER = "answer";
    public static final String COLUMN_IMAGE = "image";
    public static final String COLUMN_MUSIC = "music";

    public static final String WATERMARKS_TABLE = "watermarks";
    public static final String COLUMN_WATERMARK = "water";

    public static final String QUIZ_ANSWERS = "quiz_ans";
}