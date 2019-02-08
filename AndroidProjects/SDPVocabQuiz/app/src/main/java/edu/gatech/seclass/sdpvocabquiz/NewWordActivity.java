package edu.gatech.seclass.sdpvocabquiz;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class NewWordActivity extends AppCompatActivity
{

    String mUsername;
    Database mDatabase;

    EditText word;
    EditText definition;

    EditText incorrect1;
    EditText incorrect2;
    EditText incorrect3;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_word);

        mUsername = getIntent().getStringExtra("USERNAME");

        mDatabase = new Database(this);

        word = findViewById(R.id.theWord);
        definition = findViewById(R.id.newWordCorrect);

        incorrect1 = findViewById(R.id.newWordIncorrect1);
        incorrect2 = findViewById(R.id.newWordIncorrect2);
        incorrect3 = findViewById(R.id.newWordIncorrect3);

        //Save Word
        Button saveWord = findViewById(R.id.newWordSave);
        saveWord.setOnClickListener(new View.OnClickListener(){
            //Execute the following code when the register button is clicked
            @Override
            public void onClick(View view){
                if(checkAllFields())
                {
                    Intent intent = new Intent();
                    intent.putExtra("Word", word.getText().toString());
                    intent.putExtra("Definition", definition.getText().toString());
                    intent.putExtra("Incorrect1", incorrect1.getText().toString());
                    intent.putExtra("Incorrect2", incorrect2.getText().toString());
                    intent.putExtra("Incorrect3", incorrect3.getText().toString());
                    setResult(RESULT_OK, intent);
                    finish();
                }
                else
                {
                    Toast.makeText(NewWordActivity.this, "All fields must be filled in", Toast.LENGTH_LONG).show();
                }
            }
        });

        //Discard word

        Button discardWord = findViewById(R.id.newWordDiscard);

        discardWord.setOnClickListener(new View.OnClickListener(){
            //Execute the following code when the register button is clicked
            @Override
            public void onClick(View view){

                finish();

            }
        });



    }

    private boolean checkAllFields()
    {
        boolean emptyField;

        emptyField = word.getText().toString().isEmpty();
        emptyField |= definition.getText().toString().isEmpty();
        emptyField |= incorrect1.getText().toString().isEmpty();
        emptyField |= incorrect2.getText().toString().isEmpty();
        emptyField |= incorrect3.getText().toString().isEmpty();

        return !emptyField;
    }

}
