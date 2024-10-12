package com.example.anyastudyguide;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.Objects;

public class QuestionActivity extends AppCompatActivity {

    //You'll need these for populate views
    ImageView questionImage;
    TextView questionType, questionText;
    EditText answerText;

    String answer; // HINT: You might need this
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_question);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        this.populateViews();
    }

    /* TODO: Mini Challenge #1
        - Replace the imageView with the proper question's image, the question name's textview with the correct question's name, and the
        question textview with the corresponding question randomly obtained from the main activity
     */
    private void populateViews()
    {
        questionImage = findViewById(R.id.questionImage);
        questionType = findViewById(R.id.questionType);
        questionText = findViewById(R.id.questionText);

        Intent i = getIntent();

        String q = i.getStringExtra("question");
        String name = i.getStringExtra("name");


        questionText.setText(q);
        questionType.setText(name);

        switch (name) {
            case "General Knowledge":
                questionImage.setImageResource(R.drawable.knowledge);
                break;
            case "Mathematics":
                questionImage.setImageResource(R.drawable.math);
                break;
            case "Tonitrus":
                questionImage.setImageResource(R.drawable.tonitrus);
                break;
        }
    }

    /* TODO: Mini Challenge #1
    - Fill up this one to allow Anya to enter her answer. Make sure that the answer is not case sensitive
    */
    public void submitAnswer(View v) {
        answerText = findViewById(R.id.answerText);
        Intent j = getIntent();
        answer = Objects.requireNonNull(j.getStringExtra("answer")).toLowerCase();

        Intent result_intent = new Intent();

        if(answer.equalsIgnoreCase(answerText.getText().toString().trim())){
            result_intent.putExtra("isCorrect", true);
            result_intent.putExtra("type", j.getStringExtra("type"));
        }
        else {
            result_intent.putExtra("isCorrect", false);
            result_intent.putExtra("type", j.getStringExtra("type"));
        }

        setResult(Activity.RESULT_OK, result_intent);
        finish();
    }
}