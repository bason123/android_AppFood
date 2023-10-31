package com.example.pnlibrary.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.TextView;

import com.example.pnlibrary.R;

public class SplashActivity extends AppCompatActivity {

    TextView btnStart;
    boolean flag = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        btnStart = findViewById(R.id.buttonStart);
        Intent intent = new Intent(SplashActivity.this,Login.class);
        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                flag = true;
                finish();
                startActivity(intent);
            }
        });
        new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if(!flag){
                            finish();
                            startActivity(intent);
                        }
                    }
                },5000);
    }
}