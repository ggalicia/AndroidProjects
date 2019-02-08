package edu.gatech.seclass.sdpvocabquiz;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Date;
import java.util.Random;

/**
 * Created by Dylan Barrett on 10/11/2018.
 */

public class QuestionActivity extends AppCompatActivity {

    Database database;

    TextView question;
    Button btn_a, btn_b, btn_c, btn_d;

    ArrayList<VocabWord> words = new ArrayList<>();

    ArrayList<String> incorrectDefinitions = new ArrayList<>();

    ArrayList<String> wordDefinitions = new ArrayList<>();

    int wordLength, definitionLength;

    VocabWord currentWord;
    String[] answers = new String[4];
    int correctIndex;

    int numWords;
    int numCorrect = 0;

    String quizName;

    String username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.quiz_question);

        database = new Database(this);

        quizName = getIntent().getStringExtra("QUIZ_NAME");
        username = getIntent().getStringExtra("USERNAME");

        question = findViewById(R.id.quizQuestionWord);
        btn_a = findViewById(R.id.answerA);
        btn_b = findViewById(R.id.answerB);
        btn_c = findViewById(R.id.answerC);
        btn_d = findViewById(R.id.answerD);

        Quiz currentQuiz = database.getQuiz(quizName);

        words = currentQuiz.getVocabWords();
        incorrectDefinitions = currentQuiz.getIncorrectDefs();

        for(VocabWord word : words)
        {
            wordDefinitions.add(word.GetDefinition());
        }

        wordLength = words.size();
        definitionLength = incorrectDefinitions.size();

        //wordLength changes so we need this variable to get intial quiz length
        numWords = wordLength;

        btn_a.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CheckAnswer(0);
            }
        });

        btn_b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CheckAnswer(1);
            }
        });

        btn_c.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CheckAnswer(2);
            }
        });

        btn_d.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CheckAnswer(3);
            }
        });

        NextQuestion();
    }

//
    public void NextQuestion()
    {
        if(wordLength <= 0)
        {
            FinishQuiz();
            return;
        }

        answers[0] = "";
        answers[1] = "";
        answers[2] = "";
        answers[3] = "";

        //get random vocab word and def
        int index = RandomIndexInRange(wordLength);
         currentWord = words.get(index);

        //remove word from list
        words.remove(index);
        wordLength--;

        //put correctDef in random spot, save correct index
        index = RandomIndexInRange(4);
        answers[index] = currentWord.GetDefinition();
        correctIndex = index;

        //get the random incorrect definitions and put in answer array
        ArrayList<String> incorrect = getIncorrectDefinitions(answers[correctIndex]);

        for(int i = 0; i < 3; i++)
        {
            index = RandomIndexInRange(incorrect.size());
            if(!answers[i].isEmpty())
            {
                answers[i+1] = incorrect.get(index);
            }else
            {
                answers[i] = incorrect.get(index);
            }

            incorrect.remove(index);
        }

        //Display Word and definitions
        DisplayQuestion();
    }

    public int RandomIndexInRange(int size)
    {
        return new Random().nextInt(size);
    }

    public void DisplayQuestion()
    {
        question.setText(currentWord.GetWord());
        btn_a.setText(answers[0]);
        btn_b.setText(answers[1]);
        btn_c.setText(answers[2]);
        btn_d.setText(answers[3]);
    }

    //button on click method for checking the user's answer
    //each button gets an int between 0 and 3
    public void CheckAnswer(int index)
    {
        if(index == correctIndex)
        {
            //correct
            numCorrect++;

            Toast.makeText(this, "Correct!", Toast.LENGTH_LONG).show();

        }else{

            //incorrect
            Toast.makeText(this, "Sorry, that was incorrect!", Toast.LENGTH_LONG).show();
        }

        NextQuestion();
    }

    //Calculate Score and Show the Quiz Score
    public void FinishQuiz() {
        //calculate score
        float score = (float)numCorrect / numWords;

        //get the date
        Date date = new Date();
        long dateInLong = date.getTime();

        //Pass the score to next activity and start it.
        Intent intent = new Intent(QuestionActivity.this, QuizResultActivity.class);
        Bundle b = new Bundle();
        b.putFloat("Score", score);

        //Pass username along
        b.putString("USERNAME", username);

        //Pass quizName
        b.putString("QUIZ_NAME", quizName);

        //Pass Date
        b.putLong("DATE", dateInLong);

        intent.putExtras(b);
        startActivity(intent);
        finish();
    }

    private ArrayList<String> getIncorrectDefinitions(String correctDefinition)
    {
        ArrayList<String> incorrect = new ArrayList<>(wordDefinitions);

        incorrect.remove(correctDefinition);

        incorrect.addAll(incorrectDefinitions);

        return incorrect;
    }
}