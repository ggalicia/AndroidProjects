package edu.gatech.seclass.sdpvocabquiz;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    Database database;

    Student currentStudent;
    Quiz currentQuiz;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        database = new Database(this);

        /**Log In Category**/
        //Find the log in button
        Button logIn = findViewById(R.id.login_button);

        //Set a click listener on the log in button
        logIn.setOnClickListener(new View.OnClickListener(){
            //Execute the following code when the log in button is clicked
            @Override
            public void onClick(View view){
                //if username is found, display the dashboard
                EditText usernameEditText = findViewById(R.id.username_et);
                String usernameInput = usernameEditText.getText().toString();

                //can you help implement username search in the if statement?
                if(database.studentExists(usernameInput))
                {
                    Intent logInIntent = new Intent(MainActivity.this, DashboardActivity.class);
                    logInIntent.putExtra("USERNAME", usernameInput);
                    startActivity(logInIntent);
                }
                else
                //else display a toast saying that username was not found
                {
                    String usernameNotFound = usernameInput + " was not found. Please register to continue.";
                    Toast.makeText(MainActivity.this, usernameNotFound, Toast.LENGTH_LONG).show();
                }

            }
        });

        /**Register 1 Category**/
        //Find the register button
        Button register = findViewById(R.id.register_button);

        //Set a click listener on the register button
        register.setOnClickListener(new View.OnClickListener(){
            //Execute the following code when the register button is clicked
            @Override
            public void onClick(View view){

                EditText usernameEditText = findViewById(R.id.username_et);
                String usernameInput = usernameEditText.getText().toString();

                if(database.studentExists(usernameInput))
                {
                    String usernameFound = usernameInput + " already exists.";
                    Toast.makeText(MainActivity.this, usernameFound, Toast.LENGTH_LONG).show();
                }
                else
                {
                    Intent registerIntent = new Intent(MainActivity.this, RegisterActivity.class);
                    startActivity(registerIntent);
                }
            }
        });

    }
}
