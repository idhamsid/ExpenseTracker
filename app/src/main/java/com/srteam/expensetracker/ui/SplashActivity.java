package com.srteam.expensetracker.ui;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import com.srteam.expensetracker.R;
import com.srteam.expensetracker.isConfig.isAdsConfig;

import static java.lang.Thread.sleep;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        isAdsConfig.initAds(this);
        Thread myThread = new Thread(new Runnable() {
            public void run() {

                try {
                    sleep(3000);
                    Intent splashIntent = new Intent(getApplicationContext(),MainActivity.class);
                    startActivity(splashIntent);
                    finish();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });



        myThread.start();


    }
}
