package edu.gatech.seclass.sdpvocabquiz;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class DashboardActivity extends AppCompatActivity implements View.OnClickListener
{

    String mUsername;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dashboard);

        mUsername = getIntent().getStringExtra("USERNAME");

        /**Create Quiz**/
        //Find the create quiz button
        Button createQuiz = findViewById(R.id.create_quiz_button);
        //Set a click listener on the register button
        createQuiz.setOnClickListener(this);

        /**View Stats Category**/
        //Find the view stats button
        Button viewStats = findViewById(R.id.view_stats_button);
        //Set a click listener on the register button
        viewStats.setOnClickListener(this);

        /**Take Quiz Category**/
        Button takeQuiz = findViewById(R.id.take_quiz_button);
        takeQuiz.setOnClickListener(this);

        /**Remove Quiz Category**/
        Button removeQuiz = findViewById(R.id.remove_quiz_button);
        removeQuiz.setOnClickListener(this);

    }


    @Override
    public void onClick(View view)
    {
        Intent intent = null;
        switch(view.getId())
        {
            case R.id.view_stats_button:
                intent = new Intent(this, ViewQuizNames.class);
                intent.putExtra("USERNAME", mUsername);
                intent.putExtra("MODE", "ViewStats");
                break;
            case R.id.create_quiz_button:
                intent = new Intent(this, NewQuizActivity.class);
                intent.putExtra("USERNAME", mUsername);
                break;
            case R.id.remove_quiz_button:
                intent = new Intent(this, ViewQuizNames.class);
                intent.putExtra("USERNAME", mUsername);
                intent.putExtra("MODE", "RemoveQuiz");
                break;
            case R.id.take_quiz_button:
                intent = new Intent(this, ViewQuizNames.class);
                intent.putExtra("USERNAME", mUsername);
                intent.putExtra("MODE", "TakeQuiz");
                break;
        }

        if(intent != null)
        {
            startActivity(intent);
        }
    }
}