package com.example.quiz;

import static android.icu.lang.UCharacter.GraphemeClusterBreak.V;

import static java.sql.DriverManager.println;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

import android.view.View;
import android.util.Log;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private TextView questionTextView;
    private EditText answerEditText;
    private TextView hintTextView;
    private ArrayList<Quiz> quizzes = new ArrayList<>();

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        questionTextView = findViewById(R.id.textView4);
        answerEditText = findViewById(R.id.editTxtAnswer);
        hintTextView = findViewById(R.id.textView2);

        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(getResources().openRawResource(R.raw.quizs)));
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(", ");
                if (parts.length >= 3) {
                    Quiz quiz = new Quiz(parts[0], parts[1], parts[2]);
                    quizzes.add(quiz);
                }
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Random random = new Random();
        int randomIndex = random.nextInt(quizzes.size());
        Quiz randomQuiz = quizzes.get(randomIndex);

        Button btnCheck = findViewById(R.id.btnCheck);
        btnCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userAnswer = answerEditText.getText().toString();
                if (userAnswer.equals(randomQuiz.getAnswer())) {
                    Toast.makeText(MainActivity.this, "정답입니다!", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(MainActivity.this, "오답입니다.", Toast.LENGTH_LONG).show();
                }
            }
        });

        questionTextView.setText(randomQuiz.getQuestion());
        hintTextView.setText(randomQuiz.getHint());

        findViewById(R.id.view).setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int action = event.getAction();
                if(action == MotionEvent.ACTION_DOWN) {
                    hintTextView.setText(randomQuiz.getHint());
                } else if(action == MotionEvent.ACTION_UP) {
                    hintTextView.setText("힌트 보기");
                }
                return true;
            }
        });
    }

    public class Quiz {
        private String question;
        private String answer;
        private String hint;

        public Quiz(String question, String answer, String hint) {
            this.question = question;
            this.answer = answer;
            this.hint = hint;
        }

        public String getQuestion() {
            return question;
        }

        public String getAnswer() {
            return answer;
        }

        public String getHint() {
            return hint;
        }
    }
}