package com.pantrypro.presentation;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.pantrypro.R;
import com.pantrypro.logic.SignupHandler;

public class SignupActivity extends AppCompatActivity {

    public EditText signupName, signupEmail, signupUsername, signupPassword, signupConfirmPassword;
    private TextView loginRedirectText;
    private Button signupButton;
    private FirebaseDatabase database;
    private DatabaseReference reference;
    private SignupHandler signupHandler;
    private Spinner spinnerSignUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        signupButton = findViewById(R.id.signupButton);
        loginRedirectText = findViewById(R.id.loginRedirectText);

        //get user input
        signupName = findViewById(R.id.signupName);
        signupEmail = findViewById(R.id.signupEmail);
        signupUsername = findViewById(R.id.signupUsername);
        signupPassword = findViewById(R.id.signupPassword);
        signupConfirmPassword = findViewById(R.id.signupConfirmPassword);

        //redirect to login activity if users already have an account
        loginRedirectText.setOnClickListener(v -> {
            Intent intent = new Intent(SignupActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
        });


        signupButton.setOnClickListener(v -> {
            //get database from firebase
            database = FirebaseDatabase.getInstance();
            reference = database.getReference("users");


            signupHandler = new SignupHandler(signupName.getText().toString(),signupEmail.getText().toString(),signupUsername.getText().toString(),signupPassword.getText().toString(),signupConfirmPassword.getText().toString(), reference);
            signupHandler.checkIfUserExist(this);
        });
    }


    public void setError(EditText text, String errorMessage) {
        text.setError(errorMessage);
    }
}
