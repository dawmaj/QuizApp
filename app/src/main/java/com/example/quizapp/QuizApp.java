package com.example.quizapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.apache.commons.text.StringEscapeUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class QuizApp extends AppCompatActivity {
    int score = 0;
    int qid=0;
    int qcount=0;
    private TextView questionField,scores;
    Button opt1,opt2,opt3,opt4, musicq, pictureq;
    private RequestQueue myRequest;
    Question currentQ, cansQ;
    private ArrayList<Question> questions = new ArrayList<>();
    private ArrayList<Question> correctans = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE);
        setContentView(R.layout.activity_main);
        questionField = findViewById(R.id.Question);
        scores = findViewById(R.id.Score);
        opt1 =  findViewById(R.id.A_ans);
        opt2 =  findViewById(R.id.B_ans);
        opt3 =  findViewById(R.id.C_ans);
        opt4 =  findViewById(R.id.D_ans);
        musicq = findViewById(R.id.Music_q);
        pictureq = findViewById(R.id.Picture_q);
        myRequest = Volley.newRequestQueue(this);
        jsonParse();
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


    void openMusicQuiz(){
        Intent intent = new Intent(this, MusicQuestions.class);
        startActivity(intent);
    }

    void openPictureQuiz() {
        Intent intent = new Intent(this, PictureQuestions.class);
        startActivity(intent);
    }
    //get all questions
    void jsonParse() {
        String URL = "https://opentdb.com/api.php?amount=100&type=multiple&encoding=base64";

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, URL, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray jsonArray = response.getJSONArray("results");
                            qcount=0;
                            qid=0;
                            score = 0;
                            questions.clear();
                            correctans.clear();
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject listquestions = jsonArray.getJSONObject(i);

                                String myQuestion = StringEscapeUtils.unescapeHtml4(listquestions.getString("question"));
                                String correctAnswer = StringEscapeUtils.unescapeHtml4(listquestions.getString("correct_answer"));
                                String incorrectAnswers = listquestions.getString("incorrect_answers");
                                JSONArray ans = new JSONArray(incorrectAnswers);
                                String incorrectans1 = StringEscapeUtils.unescapeHtml4(ans.getString(0));
                                String incorrectans2 = StringEscapeUtils.unescapeHtml4(ans.getString(1));
                                String incorrectans3 = StringEscapeUtils.unescapeHtml4(ans.getString(2));
                                Random rand = new Random();

                                int  n = rand.nextInt(4) + 1;

                                Question question =null;

                                switch(n)
                                {
                                    case 1 : question = new Question(myQuestion,incorrectans1,incorrectans2,incorrectans3,correctAnswer);
                                        break;
                                    case 2 :  question = new Question(myQuestion,incorrectans3,correctAnswer,incorrectans1,incorrectans2);
                                        break;
                                    case 3 :  question = new Question(myQuestion,incorrectans3,correctAnswer,incorrectans2,incorrectans1);
                                        break;
                                    case 4 :  question = new Question(myQuestion,correctAnswer,incorrectans2,incorrectans1,incorrectans3);
                                        break;

                                }

                                Question cq = new Question(myQuestion,correctAnswer);
                                questions.add(question);
                                correctans.add(cq);
                            }
                            setQuestionList();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String>  params = new HashMap<String, String>();
                params.put("Content-Type", "application-json;charset=UTF-8");
                return params;
            }
        };
        myRequest.add(request);
    }
    //get questions list
    private void setQuestionList() {
        currentQ = questions.get(qid);
        cansQ = correctans.get(qid);

        questionField.setText(currentQ.getQUESTION());
        opt1.setText(currentQ.getOPTA());
        opt2.setText(currentQ.getOPTB());
        opt3.setText(currentQ.getOPTC());
        opt4.setText(currentQ.getANSWER());
        qcount++;
        qid++;
    }

    //prevent to exit
    boolean doubleBackToExitPressedOnce = false;
    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }
        System.out.println(questions.get(5).getOPTA());
        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce=false;
            }
        }, 2000);
    }

    //quiz logic
    void handleButtonClick(int b) {
        if(b==1)
        {
            if (opt1.getText().toString().equals(correctans.get(qid - 1).getANSWER())) {
                score++;
                scores.setText("SCORE: " + score);
                setQuestionList();
            }
            if (opt1.getText().toString().equals(correctans.get(qid - 1).getANSWER()) && (qid >= 48) ) {
                score++;
                scores.setText("SCORE: " + score);
                jsonParse();
            }
            else if (qid >= 48) {
                jsonParse();
            }
            else
                setQuestionList();
        }

        if(b==2)
        {
            if (opt2.getText().toString().equals(correctans.get(qid - 1).getANSWER())) {
                score++;
                scores.setText("SCORE: " + score);
                setQuestionList();
            }
            if (opt2.getText().toString().equals(correctans.get(qid - 1).getANSWER()) && (qid >= 48) ) {
                score++;
                scores.setText("SCORE: " + score);
                jsonParse();
            }
            else if (qid >= 48) {
                jsonParse();
            }
            else
                setQuestionList();
        }
        if(b==3)
        {
            if (opt3.getText().toString().equals(correctans.get(qid - 1).getANSWER())) {
                score++;
                scores.setText("SCORE: " + score);
                setQuestionList();
            }
            if (opt3.getText().toString().equals(correctans.get(qid - 1).getANSWER()) && (qid >= 48) ) {
                score++;
                scores.setText("SCORE: " + score);
                jsonParse();
            }
            else if (qid >= 48) {
                jsonParse();
            }
            else
                setQuestionList();
        }
        if(b==4) {
            if (opt4.getText().toString().equals(correctans.get(qid - 1).getANSWER())) {
                score++;
                scores.setText("SCORE: " + score);
                setQuestionList();
            }
            if (opt4.getText().toString().equals(correctans.get(qid - 1).getANSWER()) && (qid >= 48) ) {
                score++;
                scores.setText("SCORE: " + score);
                jsonParse();
            }
            else if (qid >= 48) {
                jsonParse();
            }
            else
                setQuestionList();
        }
    }

}
