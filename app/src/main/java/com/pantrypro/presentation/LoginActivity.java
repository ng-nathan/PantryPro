package com.pantrypro.presentation;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.pantrypro.logic.LoginHandler;
import com.pantrypro.R;
import com.pantrypro.objects.ErrorMessage;

public class LoginActivity extends AppCompatActivity {

    private EditText loginUsername, loginPassword;
    private Button loginButton;
    private TextView signupRedirectText, loginAsGuest;
    private LoginHandler loginHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        loginButton = findViewById(R.id.loginButton);
        signupRedirectText = findViewById(R.id.signupRedirectText);
        loginAsGuest = findViewById(R.id.loginAsGuest);
        loginUsername = findViewById(R.id.loginUsername);
        loginPassword = findViewById(R.id.loginPassword);

        signupRedirectText.setOnClickListener(v -> {
            Intent intent = new Intent(LoginActivity.this, SignupActivity.class);
            startActivity(intent);
            finish();
        });

        loginAsGuest.setOnClickListener(v -> {
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            loginHandler = new LoginHandler("Guest","None");
            loginHandler.guestUser(this);
        });

        loginButton.setOnClickListener(v -> {

            loginHandler = new LoginHandler(loginUsername.getText().toString(),loginPassword.getText().toString());

            boolean validUsername = loginHandler.validationUsername();
            boolean validPassword = loginHandler.validationPassword();
            if(!validUsername | !validPassword){
                if(!validUsername){
                    loginUsername.setError(ErrorMessage.USERNAME_EMPTY);
                }
                if(!validPassword){
                    loginPassword.setError(ErrorMessage.PASSWORD_EMPTY);
                }
            } else {
                loginHandler.checkUser(this);
            }
        });
    }

    public void setErrorUsername(String errorMessage){
        loginUsername.setError(errorMessage);
    }

    public void setErrorPassword(String errorMessage){
        loginPassword.setError(errorMessage);
    }

    public void requestFocusUsername(){
        loginUsername.requestFocus();
    }

    public void requestFocusPassword(){
        loginPassword.requestFocus();
    }

}
