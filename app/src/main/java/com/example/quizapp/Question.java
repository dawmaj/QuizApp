package com.example.quizapp;

import android.graphics.Bitmap;

public class Question {
    private int ID;
    private String QUESTION;
    private String OPTA;
    private String OPTB;
    private String OPTC;
    private String ANSWER;
    private String TYPE;
    private int IMG;
    private int MUSIC;
    private Bitmap WATERMARK;

    public Question()
    {
        ID=0;
        QUESTION="";
        OPTA="";
        OPTB="";
        OPTC="";
        ANSWER="";
    }
    public Question(String qUESTION, String oPTA, String oPTB, String oPTC,
                    String aNSWER) {

        QUESTION = qUESTION;
        OPTA = oPTA;
        OPTB = oPTB;
        OPTC = oPTC;
        ANSWER = aNSWER;
    }

    public Question(String qUESTION, String oPTA, String oPTB, String oPTC,
                    String aNSWER, int img) {
        QUESTION = qUESTION;
        OPTA = oPTA;
        OPTB = oPTB;
        OPTC = oPTC;
        ANSWER = aNSWER;
        IMG = img;
    }

    public Question(String qUESTION, String oPTA, String oPTB, String oPTC,
                    String aNSWER, int music, String check) {

        QUESTION = qUESTION;
        OPTA = oPTA;
        OPTB = oPTB;
        OPTC = oPTC;
        ANSWER = aNSWER;
        TYPE = check;
        MUSIC = music;
    }
    public Question(String qd, String cans) {
        QUESTION = qd;
        ANSWER = cans;

    }


    //only to db
    public Question(String qUESTION, String oPTA, String oPTB, String oPTC,
                    String aNSWER, String type, int img, int music) {

        QUESTION = qUESTION;
        OPTA = oPTA;
        OPTB = oPTB;
        OPTC = oPTC;
        ANSWER = aNSWER;
        TYPE = type;
        IMG = img;
        MUSIC = music;
    }

    public int getID()
    {
        return ID;
    }

    public String getQUESTION() {
        return QUESTION;
    }
    public String getOPTA() {
        return OPTA;
    }
    public String getOPTB() {
        return OPTB;
    }
    public String getOPTC() {
        return OPTC;
    }
    public String getANSWER() {
        return ANSWER;
    }
    public String getTYPE() {
        return TYPE;
    }
    public int getIMAGE() { return IMG; }
    public int getMUSIC() { return MUSIC; }
    public Bitmap getWaterMark() {return WATERMARK;}
    public void setID(int id)
    {
        ID=id;
    }
    public void setQUESTION(String qUESTION) {
        QUESTION = qUESTION;
    }
    public void setOPTA(String oPTA) {
        OPTA = oPTA;
    }
    public void setOPTB(String oPTB) {
        OPTB = oPTB;
    }
    public void setOPTC(String oPTC) {
        OPTC = oPTC;
    }
    public void setANSWER(String aNSWER) {
        ANSWER = aNSWER;
    }

}