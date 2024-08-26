package com.pantrypro.presentation;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;

import androidx.appcompat.app.AppCompatActivity;

import com.pantrypro.R;

/**
 * A splash screen activity displayed when the app starts.
 */
public class SplashScreen extends AppCompatActivity {

    // Duration of splash screen display in milliseconds
    public static int SPLASH_TIMER = 1800;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        // Using a Handler to delay the start of the LoginActivity after SPLASH_TIMER milliseconds
        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
                // Start LoginActivity and finish SplashScreen
                Intent intent = new Intent(SplashScreen.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        }, SPLASH_TIMER);
    }
}