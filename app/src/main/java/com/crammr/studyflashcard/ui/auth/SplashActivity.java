package com.crammr.studyflashcard.ui.auth;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;

import androidx.appcompat.app.AppCompatActivity;

import com.crammr.studyflashcard.R;
import com.crammr.studyflashcard.data.repository.UserRepository;
import com.crammr.studyflashcard.ui.main.MainActivity;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        UserRepository userRepository = new UserRepository(getApplication());

        new Handler(Looper.getMainLooper()).postDelayed(() -> {
            if (userRepository.isLoggedIn()) {
                startActivity(new Intent(SplashActivity.this, MainActivity.class));
            } else {
                startActivity(new Intent(SplashActivity.this, WelcomeActivity.class));
            }
            finish();
        }, 1200);
    }
}
