package edu.gatech.seclass.sdpvocabquiz;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class NewQuizActivity extends AppCompatActivity
{

    String mUsername;
    Database database;

    EditText quizNameEdit;
    EditText descriptionEdit;

    String quizName;
    String description;
    ArrayList<VocabWord> vocabWords;
    ArrayList<String> incorrectDefs;
    String quizCreator;

    ArrayList<String> words;
    ArrayAdapter<String> wordAdapter;

    int quizLength = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_quiz);

        database = new Database(this);

        mUsername = getIntent().getStringExtra("USERNAME");

        quizNameEdit = findViewById(R.id.newQuizName);

        quizNameEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                quizName = quizNameEdit.getText().toString();

                if(database.quizExists(quizName))
                {
                    quizNameEdit.setError(quizName + " already exists.");
                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        descriptionEdit = findViewById(R.id.newDescription);
        description = descriptionEdit.getText().toString();

        quizCreator = mUsername;

        vocabWords = new ArrayList<>();

        incorrectDefs = new ArrayList<>();

        words = new ArrayList<String>();
        wordAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, words);

        ListView wordList = findViewById(R.id.current_words_list);
        wordList.setAdapter(wordAdapter);

        Button saveQuiz = findViewById(R.id.newQuizSaveButton);
        saveQuiz.setOnClickListener(new View.OnClickListener(){
            //Execute the following code when the register button is clicked
            @Override
            public void onClick(View view){

                quizName = quizNameEdit.getText().toString();

                description = descriptionEdit.getText().toString();

                quizLength = vocabWords.size();
                if(quizLength < 1)
                {
                    Toast.makeText(NewQuizActivity.this, "Please add a word before trying to save the quiz.", Toast.LENGTH_LONG).show();
                    return;
                }

                Quiz q = new Quiz(quizName, description, quizLength, vocabWords, incorrectDefs, quizCreator);
                Database.ErrorCode error = database.addQuiz(q);
                if (error == Database.ErrorCode.None) {
                    onBackPressed();
                } else {
                    Toast.makeText(NewQuizActivity.this, error.name(), Toast.LENGTH_LONG).show();
                }

            }
        });

        // Add word
        Button addWord = findViewById(R.id.addWordButton);
        addWord.setOnClickListener(new View.OnClickListener(){
            //Execute the following code when the register button is clicked
            @Override
            public void onClick(View view){


                quizLength = vocabWords.size();
                if(quizLength < 10)
                {
                    Intent newWordIntent = new Intent(NewQuizActivity.this, NewWordActivity.class);
                    newWordIntent.putExtra("USERNAME", mUsername);
                    startActivityForResult(newWordIntent, 5273);
                }
                else {

                    Toast.makeText(NewQuizActivity.this, "Quiz length limit reached.", Toast.LENGTH_LONG).show();
                }

            }
        });

        //Discard Quiz
        Button discardQuiz = findViewById(R.id.newQuizDiscardButton);
        discardQuiz.setOnClickListener(new View.OnClickListener(){
            //Execute the following code when the register button is clicked
            @Override
            public void onClick(View view){
                finish();

            }
        });

        updateWordCount();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode == RESULT_OK) {
            if(data != null){
                String word = data.getStringExtra("Word");
                String definition = data.getStringExtra("Definition");
                String incorrect1 = data.getStringExtra("Incorrect1");
                String incorrect2 = data.getStringExtra("Incorrect2");
                String incorrect3 = data.getStringExtra("Incorrect3");
                VocabWord vWord = new VocabWord(word, definition);

                vocabWords.add(vWord);
                incorrectDefs.add(incorrect1);
                incorrectDefs.add(incorrect2);
                incorrectDefs.add(incorrect3);

                updateWordCount();

                words.add(vWord.GetWord());
                wordAdapter.notifyDataSetChanged();
            }
        }
    }

    private void updateWordCount()
    {
        TextView text = findViewById(R.id.current_words_text);
        text.setText(String.format("%s%d", getString(R.string.new_quiz_word_count),
                vocabWords.size()));
    }
}
