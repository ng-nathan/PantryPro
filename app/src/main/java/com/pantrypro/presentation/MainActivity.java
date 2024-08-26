package com.pantrypro.presentation;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.pantrypro.R;
import com.pantrypro.databinding.ActivityMainBinding;

import com.pantrypro.logic.AccessRecipes;
import com.pantrypro.objects.UserProfile;
import com.pantrypro.persistence.utils.DBHelper;

/**
 * The main activity of the application.
 */
public class MainActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private AccessRecipes handleRecipesDB;
    private UserProfile currUser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        com.pantrypro.databinding.ActivityMainBinding binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // load database
        DBHelper.copyDatabaseToDevice(this);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        handleRecipesDB = new AccessRecipes(true);

        Intent intent = getIntent();
        currUser = new UserProfile(intent.getStringExtra("name"), intent.getStringExtra("email"), intent.getStringExtra("username"), intent.getStringExtra("password"));

        replaceFragment(new HomeFragment());
        binding.bottomNavigationView.setOnItemSelectedListener(item -> {
            int itemId = item.getItemId();
            if(itemId == R.id.bottom_home){
                replaceFragment(new HomeFragment());
                return true;
            } else if (itemId == R.id.bottom_addRecipe) {
                replaceFragment(new AddRecipeFragment());
                return true;
            } else if (itemId == R.id.bottom_profile) {
                replaceFragment(new ProfileFragment());
                toolbar.setTitle("Your profile");
                return true;
            }
            return false;
        });

        getOnBackPressedDispatcher().addCallback(this, new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                Toast.makeText(getApplicationContext(), "Back button is disable on this app", Toast.LENGTH_LONG).show();
            }
        });
    }

    public UserProfile currentUser(){
        return currUser;
    }

    /**
     * Replace the current fragment with the specified fragment.
     * @param fragment The fragment to replace with.
     */
    public void replaceFragment(Fragment fragment){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.setCustomAnimations(
                R.anim.fade_in,  // enter
                R.anim.fade_out,  // exit
                R.anim.fade_in,   // popEnter
                R.anim.fade_out  // popExit
        );
        fragmentTransaction.replace(R.id.frame_layout, fragment);
        fragmentTransaction.commit();
    }

    public Toolbar getToolbar(){
        return toolbar;
    }

    public AccessRecipes getHandleRecipesDB() {
        return handleRecipesDB;
    }
}