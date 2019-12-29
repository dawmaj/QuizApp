package com.example.quizapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
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
    private TextView questionField;
    Button opt1,opt2,opt3,opt4;
    private RequestQueue myRequest;
    private ArrayList<Question> questions = new ArrayList<>();
    private ArrayList<Question> correctans = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE);
        setContentView(R.layout.activity_main);
        questionField = findViewById(R.id.Question);
        opt1 =  findViewById(R.id.A_ans);
        opt2 =  findViewById(R.id.B_ans);
        opt3 =  findViewById(R.id.C_ans);
        opt4 =  findViewById(R.id.D_ans);
        myRequest = Volley.newRequestQueue(this);
        jsonParse();
    }


    //get all questions
    private void jsonParse() {
        String URL = "https://opentdb.com/api.php?amount=100&type=multiple&encoding=base64";

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, URL, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray jsonArray = response.getJSONArray("results");

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
}
