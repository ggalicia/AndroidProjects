package com.example.android.computerliteracyapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity
{

    int score = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     *this method checks how many questions the user got correct and displays the result in a toast message
     */
    public void submitQuiz (View view)
    {
        checkAnswers();

        if(score == 7)
        {
            String submit = "Wow! You got a perfect score! " + score + "/7 correct!";
            Toast.makeText(this, submit, Toast.LENGTH_LONG).show();
        }
        else
        {
            String submit = "You got " + score + "/7 correct!";
            Toast.makeText(this, submit, Toast.LENGTH_LONG).show();
        }
        score = 0;
    }

    /**
     * This method resets the quiz
     */
    public void resetQuestions(View view){
        Intent i = getBaseContext().getPackageManager().getLaunchIntentForPackage(getBaseContext().getPackageName());
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(i);
    }

    /**
     *this method checks the quiz answers
     */
    private void checkAnswers()
    {
        questionOne();
        questionTwo();
        questionThree();
        questionFour();
        questionFive();
        questionSix();
        questionSeven();
    }

    /**
     * This method checks if question 1 is correct
     */
    public void questionOne() {
        RadioButton isOneCorrect = findViewById(R.id.question_1_answer_1_rb);

        if (isOneCorrect.isChecked())
        {
            score++;
        }
    }

    /**
     * This method checks if question 2 is correct
     */
    public void questionTwo() {
        RadioButton isTwoCorrect = findViewById(R.id.question_2_answer_2_rb);

        if (isTwoCorrect.isChecked()) {
            score++;
        }
    }

    /**
     * This method checks if question 3 is correct
     */
    public void questionThree() {
        RadioButton isThreeCorrect = findViewById(R.id.question_3_answer_true_rb);

        if (isThreeCorrect.isChecked()) {
            score++;
        }
    }

    /**
     * This method checks if question 4 is correct
     */
    public void questionFour()
    {
        CheckBox questionFourOption1Cb = (CheckBox) findViewById(R.id.question_4_cb_1);
        CheckBox questionFourOption3Cb = (CheckBox) findViewById(R.id.question_4_cb_3);
        CheckBox questionFiveOption5Cb = (CheckBox) findViewById(R.id.question_4_cb_5);
        CheckBox questionFourAnswer2Cb = (CheckBox) findViewById(R.id.question_4_answer_2_cb);
        CheckBox questionFourAnswer4Cb = (CheckBox) findViewById(R.id.question_4_answer_4_cb);

        if (questionFourAnswer2Cb.isChecked() && questionFourAnswer4Cb.isChecked() && !questionFourOption1Cb.isChecked() && !questionFourOption3Cb.isChecked() && !questionFiveOption5Cb.isChecked())
        {
            score++;
        }
    }

    /**
     * This method checks if question 5 is correct
     * This method checks if user input is equal to the word "desktop"
     * it ignores the case
     */
    public void questionFive()
    {
        EditText isQuestionFiveAMatch = findViewById(R.id.computerTerm_answer_et);
        String userInput = isQuestionFiveAMatch.getText().toString();

        if (userInput.equalsIgnoreCase("Desktop"))
        {
            score++;
        }
    }

    /**
     * This method checks if question 6 is correct
     */
    public void questionSix()
    {
        RadioButton isSixCorrect = findViewById(R.id.question_6_answer_3_rb);

        if (isSixCorrect.isChecked())
        {
            score++;
        }
    }

    /**
     * This method checks if question 7 is correct
     */
    public void questionSeven() {
        RadioButton isSevenCorrect = findViewById(R.id.question_7_answer_1_rb);

        if (isSevenCorrect.isChecked()) {
            score++;
        }
    }
}
