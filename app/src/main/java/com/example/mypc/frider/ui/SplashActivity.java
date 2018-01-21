package com.example.mypc.frider.ui;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

import com.example.mypc.frider.R;
import com.example.mypc.frider.ui.login.LoginActivity;

public class SplashActivity extends AppCompatActivity {

    public static final int TIME_OUT=3000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Handler h = new Handler();

        h.postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(getApplicationContext(),LoginActivity.class));
            }
        },TIME_OUT);

    }
}
