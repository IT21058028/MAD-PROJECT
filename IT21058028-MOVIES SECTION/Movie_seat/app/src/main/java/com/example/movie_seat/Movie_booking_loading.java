package com.example.movie_seat;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.Transformation;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class Movie_booking_loading extends AppCompatActivity {

    ProgressBar progressBar;
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_booking_loading);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);


        progressBar = (ProgressBar)findViewById(R.id.progressBar);
        textView = (TextView)findViewById(R.id.textView);

        progressBar.setMax(100);
        textView.setScaleY(3f);

        progressBarAnimations();

    }

    public void progressBarAnimations() {

        ProgressBarAnimation anim = new ProgressBarAnimation(this,progressBar,textView, 0f,100f);
        anim.setDuration(1000);
        progressBar.setAnimation(anim);
    }

    public class ProgressBarAnimation extends Animation {
        private Context context;
        private ProgressBar progressBar;
        private TextView textView;
        private float from;
        private float to;

        public ProgressBarAnimation(Context context, ProgressBar progressBar, TextView textView, float from, float to) {
            this.context = context;
            this.progressBar = progressBar;
            this.textView = textView;
            this.from = from;
            this.to = to;
        }
        @Override

        protected void applyTransformation(float interploatedTime, Transformation t){
            super.applyTransformation(interploatedTime,t);
            float value = from + (to-from) * interploatedTime;
            progressBar.setProgress((int) value);
            textView.setText((int) value+"%");

            if(value == to){
                context.startActivity(new Intent(Movie_booking_loading.this, UserSelection.class));
            }

        }
    }
}