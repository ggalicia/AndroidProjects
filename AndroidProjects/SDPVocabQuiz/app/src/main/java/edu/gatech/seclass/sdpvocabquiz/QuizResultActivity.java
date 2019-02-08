package edu.gatech.seclass.sdpvocabquiz;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Date;

/**
 * Created by Dylan Barrett on 10/12/2018.
 */

public class QuizResultActivity extends AppCompatActivity {

    Database database;

    TextView TV_quizName, TV_quizScore, TV_quizDate;
    Button btn_ToDashboard;

    float score;
    String userName;
    String quizName;
    Date date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.quiz_result);

        database = new Database(this);

        TV_quizName = findViewById(R.id.quizResultName);
        TV_quizScore = findViewById(R.id.quizResultScore);
        TV_quizDate = findViewById(R.id.quizResultDate);

        btn_ToDashboard = findViewById(R.id.backToDashboard);

        btn_ToDashboard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BackToDashboard();
            }
        });

        Bundle b = getIntent().getExtras();

        score = b.getFloat("Score");
        userName = b.getString("USERNAME");
        quizName = b.getString("QUIZ_NAME");
        Long dateInLong = b.getLong("DATE");
        date = new Date(dateInLong);


        CreateStatistic(quizName,userName,score,date);

        score *= 100;

        DisplayScore();

    }

    public void DisplayScore() {
        TV_quizName.setText(quizName);
        TV_quizScore.setText(String.format("%.2f", score));
        TV_quizDate.setText(date.toString());
    }

    public void CreateStatistic(String quizName, String studentName, float score, Date dateCompleted) {
        QuizScoreStatistic stat = new QuizScoreStatistic(quizName, studentName, score, dateCompleted);

        database.addQuizScore(stat);
    }

    public void BackToDashboard() {
        Intent intent = new Intent(QuizResultActivity.this, DashboardActivity.class);
        Bundle b = new Bundle();

        //Pass username along
        b.putString("USERNAME", userName);

        intent.putExtras(b);
        startActivity(intent);
        finish();
    }


}
