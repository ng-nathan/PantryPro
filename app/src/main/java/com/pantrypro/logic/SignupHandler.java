package com.pantrypro.logic;

import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.pantrypro.objects.UserProfile;
import com.pantrypro.objects.ErrorMessage;
import com.pantrypro.presentation.LoginActivity;
import com.pantrypro.presentation.SignupActivity;

public class SignupHandler {
    private String signupName, signupEmail, signupUsername, signupPassword, signupConfirmPassword;
    private DatabaseReference reference;
    private boolean foundMatchingEmail = false;
    public SignupHandler(String signupName, String signupEmail, String signupUsername, String signupPassword, String signupConfirmPassword){
        this.signupName = signupName;
        this.signupEmail = signupEmail;
        this.signupUsername = signupUsername;
        this.signupPassword = signupPassword;
        this.signupConfirmPassword = signupConfirmPassword;
    }


    public SignupHandler(String signupName, String signupEmail, String signupUsername, String signupPassword, String signupConfirmPassword, DatabaseReference reference){
        this.signupName = signupName;
        this.signupEmail = signupEmail;
        this.signupUsername = signupUsername;
        this.signupPassword = signupPassword;
        this.signupConfirmPassword = signupConfirmPassword;
        this.reference = reference;
    }

    public Boolean validateFields(SignupActivity signupActivity){
        Boolean allValid = true;
        if(signupName.isEmpty()){
            signupActivity.setError(signupActivity.signupName, ErrorMessage.NAME_EMPTY);
            allValid = false;
        }
        if(!validationEmail(signupEmail)){
            signupActivity.setError(signupActivity.signupEmail, ErrorMessage.EMAIL_ERROR);
            allValid = false;
        }
        if(!validationUsername(signupUsername)){
            signupActivity.setError(signupActivity.signupUsername, ErrorMessage.USERNAME_ERROR);
            allValid = false;
        }
        if(!validationPassword(signupPassword)){
            signupActivity.setError(signupActivity.signupPassword, ErrorMessage.PASSWORD_ERROR);
            allValid = false;
        }
        if(signupConfirmPassword.isEmpty()){
            signupActivity.setError(signupActivity.signupConfirmPassword, ErrorMessage.RETYPE_PASSWORD);
            allValid = false;
        }
        if(!signupConfirmPassword.equals(signupPassword)){
            signupActivity.setError(signupActivity.signupConfirmPassword, ErrorMessage.PASSWORD_NO_MATCH);
            allValid = false;
        }
        return allValid;
    }

    public boolean validationEmail(String email) {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        return !email.isEmpty() && email.matches(emailRegex);
    }
    public boolean validationUsername(String username) {
        String usernameRegex = "^[A-Za-z][A-Za-z0-9_]{7,29}$";
        return !username.isEmpty() && username.matches(usernameRegex);
    }

    public boolean validationPassword(String password) {
        String passwordRegex = "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[@$!%*#?&])[A-Za-z\\d@$!%*#?&]{8,}$";
        return !password.isEmpty() && password.matches(passwordRegex);
    }

    private void signupButtonPressed( SignupActivity signupActivity ){
        if(validateFields(signupActivity)){

            if(signupPassword.equals(signupConfirmPassword)){
                UserProfile userProfile = new UserProfile(signupName, signupEmail, signupUsername, signupPassword);
                reference.child(signupUsername).setValue(userProfile);

                Toast.makeText(signupActivity, "Successfully signed up!", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(signupActivity, LoginActivity.class);
                signupActivity.startActivity(intent);
                signupActivity.finish();
            }
        }
    }

    public void checkIfUserExist( SignupActivity signupActivity ){
        //check user profile based on username (username = primary key)
        Query checkUserOnDB = reference.orderByChild("username").equalTo(signupUsername);
        checkUserOnDB.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Log.d("DataSnapshot", snapshot.toString());
                if(snapshot.exists()){
                    signupActivity.setError(signupActivity.signupUsername, ErrorMessage.USERNAME_DUP);
                } else {
                    checkIfEmailExist(signupActivity);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("UserDatabase", "Failed to read value.", error.toException());
            }
        });
    }

    private void checkIfEmailExist(SignupActivity signupActivity){
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                // search through all accounts and see if the same email is already used
                for(DataSnapshot childSnapshot : snapshot.getChildren()){
                    String email = childSnapshot.child("email").getValue(String.class);

                    if(signupEmail.equals(email)){
                        foundMatchingEmail = true;
                        signupActivity.setError(signupActivity.signupEmail, ErrorMessage.EMAIL_DUP);
                        break;
                    }
                }
                // if no matching email, create account
                if(!foundMatchingEmail){
                    signupButtonPressed(signupActivity);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}
