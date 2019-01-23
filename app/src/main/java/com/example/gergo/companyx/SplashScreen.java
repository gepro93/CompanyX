package com.example.gergo.companyx;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

public class SplashScreen extends AppCompatActivity {

    private static int SPLASH_TIME_OUT = 3000;
    ImageView iwLogo;
    TextView twLogo, twPrecentage;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        init();

        iwLogo.animate().alpha(1f).setDuration(1500);
        twLogo.animate().alpha(1f).setDuration(1500);
        twPrecentage.animate().alpha(1f).setDuration(1500);
        progressBar.animate().alpha(1f).setDuration(1500);

        progressBar.setMax(100);

        progressBarAnimation();

    }

    public void init(){
        iwLogo = (ImageView) findViewById(R.id.iwLogo);
        twLogo = (TextView) findViewById(R.id.twLogo);
        twPrecentage = (TextView) findViewById(R.id.twPrecentage);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
    }

    public void progressBarAnimation(){
        ProgressBarAnimation anim = new ProgressBarAnimation(this,progressBar,twPrecentage,0f,100f);
        anim.setDuration(6000);
        progressBar.setAnimation(anim);
    }

}
