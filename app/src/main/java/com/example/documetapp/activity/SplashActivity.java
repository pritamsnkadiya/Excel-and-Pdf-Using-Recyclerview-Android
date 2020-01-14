package com.example.documetapp.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.example.documetapp.R;
import com.example.documetapp.utils.AppConstants;
import com.example.documetapp.utils.Method;

import static com.example.documetapp.init.ApplicationAppContext.getAppContext;

public class SplashActivity extends AppCompatActivity {

    public Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        handler = new Handler();

        AppConstants.CONTEXT = SplashActivity.this;
        AppConstants.TOKEN = Method.getPreferences(getAppContext(), "Authorization");

        handler.postDelayed(() -> {
            startActivity(new Intent(SplashActivity.this, MainActivity.class));
            finish();
        }, 2000);
    }
}
