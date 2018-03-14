package com.example.suh.parking;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by psh on 18. 3. 14.
 */

public class SplashActivity extends Activity{


    @Override
    protected void onCreate(Bundle savedInstanceState){
        setContentView(R.layout.splash_layout);
        super.onCreate(savedInstanceState);
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {

            @Override
            public void run() {
                finish();
            }
        }, 1000);// 3 초
    }


}
