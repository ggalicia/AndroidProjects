package edu.gatech.seclass.sdpvocabquiz;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class ViewQuizNames extends AppCompatActivity implements AdapterView.OnItemClickListener
{
    Database mDatabase;
    String mMode;
    String mUsername;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.viewstats);

        TextView title = findViewById(R.id.view_stats_tv);

        mUsername = getIntent().getStringExtra("USERNAME");
        mMode = getIntent().getStringExtra("MODE");
        mDatabase = new Database(this);

        ArrayList<String> quizNames;

        if(mMode.equals("ViewStats"))
        {
            quizNames = new ArrayList<>(mDatabase.getQuizNamesByDateTaken(mUsername));
            title.setText("View Quiz Stats");
        }
        else if(mMode.equals("RemoveQuiz"))
        {
            quizNames = new ArrayList<>(mDatabase.getQuizzesCreated(mUsername));
            title.setText("Remove Quiz");
        }
        else if(mMode.equals("TakeQuiz"))
        {
            quizNames = new ArrayList<>(mDatabase.getQuizzesCreatedByOthers(mUsername));
            title.setText("Take Quiz");
        }
        else
        {
            //TODO handle error condition better
            onBackPressed();
            quizNames = new ArrayList<>();
        }

        ArrayAdapter<String> quizNameAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, quizNames);
        ListView quizList = findViewById(R.id.quiz_list);
        quizList.setAdapter(quizNameAdapter);
        quizList.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l)
    {
        String quizName = (String)adapterView.getItemAtPosition(i);

        if(mMode.equals("ViewStats"))
        {
            Intent intent = new Intent(this, ViewQuizStatsActivity.class);
            intent.putExtra("USERNAME", mUsername);
            intent.putExtra("QUIZ_NAME", quizName);
            startActivity(intent);
        }
        else if(mMode.equals("RemoveQuiz"))
        {
            Database.ErrorCode error = mDatabase.removeQuiz(quizName);
            if(error != Database.ErrorCode.None)
            {
                Toast.makeText(this, error.name(), Toast.LENGTH_LONG).show();
            }
            else
            {
                Toast.makeText(this, "Quiz successfully deleted", Toast.LENGTH_LONG).show();
                onBackPressed();
            }
        }
        else if(mMode.equals("TakeQuiz"))
        {
            Intent intent = new Intent(this, QuestionActivity.class);
            intent.putExtra("QUIZ_NAME", quizName);
            intent.putExtra("USERNAME", mUsername);
            startActivity(intent);
        }
    }
}