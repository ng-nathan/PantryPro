package com.pantrypro.logic;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.pantrypro.presentation.LoginActivity;
import com.pantrypro.presentation.MainActivity;
import com.pantrypro.objects.ErrorMessage;
import com.pantrypro.presentation.ProfileFragment;


public class LoginHandler {

    private String loginUsername, loginPassword;

    public LoginHandler(String loginUsername, String loginPassword){
        this.loginUsername = loginUsername;
        this.loginPassword = loginPassword;
    }

    //Get user credentials for testing
    public String getUsername(){return this.loginUsername;}
    public String getPassword(){ return this.loginPassword;}

    //validate username and password when users log in
    public Boolean validationUsername(){
        return loginUsername != null && !loginUsername.isEmpty();
    }
    public Boolean validationPassword(){
        return loginPassword != null && !loginUsername.isEmpty();
    }

    //check if the user exists on the database
    public void checkUser(LoginActivity loginActivity){
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("users");
        //check user profile based on username (username = primary key)
        Query checkUserOnDB = reference.orderByChild("username").equalTo(loginUsername);

        checkUserOnDB.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Log.d("DataSnapshot", snapshot.toString());
                //if the user exists
                if(snapshot.exists()){
                    //loginUsername.setError(null);
                    loginActivity.setErrorUsername(null);
                    String passwordFromDB = snapshot.child(loginUsername).child("password").getValue(String.class);
                    // check if the password matches
                    if(passwordFromDB != null && passwordFromDB.equals(loginPassword)){
                        //redirect user to the app if they successfully log in
                        loginActivity.setErrorPassword(null);

                        //pass profile info
                        String nameFirebase = snapshot.child(loginUsername).child("name").getValue(String.class);
                        String userNameFirebase = snapshot.child(loginUsername).child("username").getValue(String.class);
                        String passwordFirebase = snapshot.child(loginUsername).child("password").getValue(String.class);
                        String emailFirebase = snapshot.child(loginUsername).child("email").getValue(String.class);

                        Intent intent = new Intent(loginActivity, MainActivity.class);
                        intent.putExtra("name", nameFirebase);
                        intent.putExtra("username", userNameFirebase);
                        intent.putExtra("password", passwordFirebase);
                        intent.putExtra("email", emailFirebase);

                        loginActivity.startActivity(intent);

                    } else {
                        loginActivity.setErrorPassword(ErrorMessage.INCORRECT_PASSWORD);
                        loginActivity.requestFocusPassword();
                    }
                } else {
                    loginActivity.setErrorUsername(ErrorMessage.USER_NOT_EXIST);
                    loginActivity.requestFocusUsername();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("UserDatabase", "Failed to read value.", error.toException());
            }
        });
    }

    public void guestUser(LoginActivity loginActivity){
        if (loginUsername.equals("Guest") && loginPassword.equals("None")){

            Intent intent = new Intent(loginActivity, MainActivity.class);
            intent.putExtra("name", "Guest");
            intent.putExtra("username", "Guest");
            intent.putExtra("password", "N/A");
            intent.putExtra("email", "N/A");

            loginActivity.startActivity(intent);
        }
    }
}
