package edu.gatech.seclass.sdpvocabquiz;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Patterns;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.Set;

import static edu.gatech.seclass.sdpvocabquiz.R.array.seniority_array;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener
{

    EditText mUsernameText;
    EditText mMajorText;
    EditText mEmailText;

    Spinner spinner;

    Database database;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);

        database = new Database(this);

        /**Check if username already exists in database**/

        mUsernameText = findViewById(R.id.username_et_2);


        mUsernameText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                String usernameInput = mUsernameText.getText().toString();

                if(database.studentExists(usernameInput))
                {
                    mUsernameText.setError(usernameInput + " already exists.");
                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        mMajorText = findViewById(R.id.major_et);

        //mSeniorityText = findViewById(R.id.seniority_et);

        /**Check if e-mail format is valid**/
        mEmailText = findViewById(R.id.email_et);

        mEmailText.addTextChangedListener(new TextWatcher() {
                                              @Override
                                              public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                                              }

                                              @Override
                                              public void onTextChanged(CharSequence s, int start, int before, int count) {

                                                  String userEmailInput = mEmailText.getText().toString().trim();

                                                  if (!Patterns.EMAIL_ADDRESS.matcher(userEmailInput).matches())
                                                  {
                                                      mEmailText.setError("Please enter a valid email address.");
                                                  }

                                              }

                                              @Override
                                              public void afterTextChanged(Editable s) {

                                              }
                                          });



        /**Set up spinner and populate with values**/
        spinner = findViewById(R.id.spinner);
        spinner.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, Seniority.values()));


        /**Set up second register button**/
        //Find the register button 2
        Button registerTwo = findViewById(R.id.register_button_2);
        //Set a click listener on the register button
        registerTwo.setOnClickListener(this);

    }

    @Override
    public void onClick(View view)
    {
        switch (view.getId())
        {
            case R.id.register_button_2:

                Student newStudent = new Student(mUsernameText.getText().toString(),
                        mMajorText.getText().toString(), (Seniority) spinner.getSelectedItem(), mEmailText.getText()
                        .toString());

                Database.ErrorCode error = database.addStudent(newStudent);

                if (error == Database.ErrorCode.None) {
                    Toast.makeText(this, "Registration successful!", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(this, DashboardActivity.class);
                    intent.putExtra("USERNAME", newStudent.username);
                    startActivity(intent);
                } else {
                    Toast.makeText(this, error.name(), Toast.LENGTH_LONG).show();
                }
            }
        }


}
