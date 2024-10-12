package com.example.anyastudyguide;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ArrayList<Question> questionList;

    ImageView generalImage,mathImage,tonitrusImage;
    TextView generalName,mathName,tonitrusName;
    TextView generalCorrect,mathCorrect,tonitrusCorrect;
    TextView generalWrong,mathWrong,tonitrusWrong;

    int generalCorrectCtr = 0, mathCorrectCtr = 0, tonitrusCorrectCtr = 0;
    int generalWrongCtr = 0, mathWrongCtr = 0, tonitrusWrongCtr = 0;

    private ActivityResultLauncher<Intent> myActivityResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>()
            {
                /* TODO: Mini Challenge #1:
                    Modify the function to enable the app to properly update correct / incorrect answers entered
                    depending on the question type
                 */
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == RESULT_OK) {
                        assert result.getData() != null;
                        boolean isCorrect = result.getData().getBooleanExtra("isCorrect", false);

                        switch(result.getData().getStringExtra("type")) {
                            case "gen":
                                if(isCorrect)
                                    generalCorrectCtr++;
                                else
                                    generalWrongCtr++;
                                break;
                            case "math":
                                if(isCorrect)
                                    mathCorrectCtr++;
                                else
                                    mathWrongCtr++;
                                break;
                            case "tonitrus":
                                if(isCorrect)
                                    tonitrusCorrectCtr++;
                                else
                                    tonitrusWrongCtr++;
                                break;
                        }
                        updatePoints();
                    }
                }
            }
    );
    private void initQuestions()
    {
        questionList = new ArrayList<Question>();

        questionList.add(new Question("What is the capital of Japan?","Tokyo","gen"));
        questionList.add(new Question("What type of animal is Bond?","Dog","gen"));
        questionList.add(new Question("What is the name of the school you go to?","Eden Academy","gen"));
        questionList.add(new Question("What is the name of a building where you can view fishes and marine life?","Aquarium","gen"));
        questionList.add(new Question("What month does Halloween take place?","October","gen"));
        questionList.add(new Question("7 x 52","364","math"));
        questionList.add(new Question("9 / 27 = 1 / x. x = ?","3","math"));
        questionList.add(new Question("Round off 289 to the nearest hundreds value.","300","math"));
        questionList.add(new Question("1, 5, 10, 16, 23, x, 40, 50. x = ?","31","math"));
        questionList.add(new Question("What is the sum of all numbers from 1 to 1000?","500500","math"));
        questionList.add(new Question("What year was Rome sacked?","375AD","tonitrus"));
        questionList.add(new Question("What is an area under investigation where possible tropical cyclones may form?","invest","tonitrus"));
        questionList.add(new Question("What year did the French Revolution begin?","1789","tonitrus"));
        questionList.add(new Question("The chemical compound used to turn fireworks green?","Barium","tonitrus"));
        questionList.add(new Question("What country was the first roller coaster conceptualized in?","Russia","tonitrus"));
    }

    private Question getRandomQuestion(String type)
    {
        ArrayList<Question> tq = new ArrayList<Question>();
        int len = 0;
        for (Question x: questionList)
        {
            if (type.equals(x.getType())) {
                tq.add(x);
                len++;
            }
        }
        int rand = (int)(Math.random() * len);
        Question q = tq.get(rand);
        tq.clear();

        return q;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Use these at your disposal
        generalImage = findViewById(R.id.generalImage);
        mathImage = findViewById(R.id.mathImage);
        tonitrusImage = findViewById(R.id.tonitrusImage);

        generalName = findViewById(R.id.generalName);
        mathName = findViewById(R.id.mathName);
        tonitrusName = findViewById(R.id.tonitrusName);

        generalCorrect = findViewById(R.id.generalCorrect);
        mathCorrect = findViewById(R.id.mathCorrect);
        tonitrusCorrect = findViewById(R.id.tonitrusCorrect);

        generalWrong = findViewById(R.id.generalWrong);
        mathWrong = findViewById(R.id.mathWrong);
        tonitrusWrong = findViewById(R.id.tonitrusWrong);

        initQuestions();

        CardView genCard = findViewById(R.id.genCard);
        CardView mathCard = findViewById(R.id.mathCard);
        CardView tonitrusCard = findViewById(R.id.tonCard);

        genCard.setOnClickListener(this::generalKnowledgeQuestion);
        mathCard.setOnClickListener(this::mathematicsQuestion);
        tonitrusCard.setOnClickListener(this::tonitrusQuestion);
    }

    /* TODO: Mini Challenge #1
        - Randomly obtain a General Knowledge Question and pass it to the sub activity
        - Properly associate this method via the onClick correctly in your res file or via your code
     */
    public void generalKnowledgeQuestion(View v)
    {
        Question q = getRandomQuestion("gen");
        Intent i = new Intent(MainActivity.this, QuestionActivity.class);
        i.putExtra("question", q.getQuestion());
        i.putExtra("answer", q.getAnswer());
        i.putExtra("name", generalName.getText());
        i.putExtra("type", q.getType());
        myActivityResultLauncher.launch(i);
    }

    /* TODO: Mini Challenge #1
    - Randomly obtain a Mathematics Question and pass it to the sub activity
    - Properly associate this method via the onClick correctly in your res file or via your code
    */

    public void mathematicsQuestion(View v)
    {
        Question q = getRandomQuestion("math");
        Intent i = new Intent(MainActivity.this, QuestionActivity.class);
        i.putExtra("question", q.getQuestion());
        i.putExtra("answer", q.getAnswer());
        i.putExtra("name", mathName.getText());
        i.putExtra("type", q.getType());
        myActivityResultLauncher.launch(i);
    }

    /* TODO: Mini Challenge #1
    - Randomly obtain a Difficult Question and pass it to the sub activity
    - Properly associate this method via the onClick correctly in your res file or via your code
    */

    public void tonitrusQuestion(View v)
    {
        Question q = getRandomQuestion("tonitrus");
        Intent i = new Intent(MainActivity.this, QuestionActivity.class);
        i.putExtra("question", q.getQuestion());
        i.putExtra("answer", q.getAnswer());
        i.putExtra("name", tonitrusName.getText());
        i.putExtra("type", q.getType());
        myActivityResultLauncher.launch(i);
    }

    /* TODO: Mini Challenge #1
    - Set all the Wrong Answers and Correct Answers back to 0 with this method
    */
    public void clearHistory(View v)
    {
        generalCorrectCtr = 0;
        generalWrongCtr = 0;
        mathCorrectCtr = 0;
        mathWrongCtr = 0;
        tonitrusCorrectCtr = 0;
        tonitrusWrongCtr = 0;
        updatePoints();
    }

    public void updatePoints() {
        generalCorrect.setText("Correct Answers: " + generalCorrectCtr);
        generalWrong.setText("Wrong Answers: " + generalWrongCtr);
        mathCorrect.setText("Correct Answers: " + mathCorrectCtr);
        mathWrong.setText("Wrong Answers: " + mathWrongCtr);
        tonitrusCorrect.setText("Correct Answers: " + tonitrusCorrectCtr);
        tonitrusWrong.setText("Wrong Answers: " + tonitrusWrongCtr);
    }
}