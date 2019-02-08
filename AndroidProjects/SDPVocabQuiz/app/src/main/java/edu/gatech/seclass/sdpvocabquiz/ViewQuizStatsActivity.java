package edu.gatech.seclass.sdpvocabquiz;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class ViewQuizStatsActivity extends AppCompatActivity
{
    Database database;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_quiz_stats);

        database = new Database(this);

        String username = getIntent().getStringExtra("USERNAME");
        String quizName = getIntent().getStringExtra("QUIZ_NAME");

        TextView quizNameView = findViewById(R.id.quiz_name);
        quizNameView.setText(String.format("%s Stats", quizName));

        TextView firstScore = findViewById(R.id.first_score);
        TextView firstDate = findViewById(R.id.first_date);

        TextView highestScore = findViewById(R.id.highest_score);
        TextView highestDate = findViewById(R.id.highest_date);

        ListView topScorers = findViewById(R.id.top_scorers_list);

        QuizStatisticDisplayInfo statistics = database.getQuizStatisticDisplayInfo(username,
                quizName);

        if (statistics.firstScoreDate != null)
        {
            firstScore.setText(String.format("Score: %.2f%%", statistics.firstScore * 100));
            firstDate.setText(statistics.firstScoreDate.toString());

            highestScore.setText(String.format("Score: %.2f%%", statistics.highestScore * 100));
            highestDate.setText(statistics.highestScoreDate.toString());
        }
        else
        {
            firstScore.setText("Score: N/A");
            firstDate.setText("N/A");

            highestScore.setText("Score: N/A");
            highestDate.setText("N/A");
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout
                .simple_list_item_1, statistics.perfectScorers);
        topScorers.setAdapter(adapter);
    }
}
