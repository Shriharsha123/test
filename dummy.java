package com.example.myapplication;

import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.os.Handler;
import androidx.appcompat.app.AppCompatActivity;

public class dummy extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dummy);
        TextView textView=findViewById(R.id.random);
        TextView textView2=findViewById(R.id.random2);
        TextView textView3=findViewById(R.id.random3);
        ObjectAnimator animation = ObjectAnimator.ofFloat(textView, "translationX", 200f);
        ObjectAnimator animation2 = ObjectAnimator.ofFloat(textView, "translationY", 400f);
        ObjectAnimator animation3 = ObjectAnimator.ofFloat(textView2, "translationX", 200f);
        ObjectAnimator animation4 = ObjectAnimator.ofFloat(textView2, "translationY", 400f);
        ObjectAnimator animation5 = ObjectAnimator.ofFloat(textView3, "translationX", 200f);
        ObjectAnimator animation6 = ObjectAnimator.ofFloat(textView3, "translationY", 400f);
        Button startBtn=findViewById(R.id.start);
        startBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        animation.setDuration(1000);
                        animation.start();
                    }
                }, 0);

                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        animation2.setDuration(1000);
                        animation2.start();
                    }
                }, 1000);

                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        animation3.setDuration(1000);
                        animation3.start();
                    }
                }, 0);
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        animation4.setDuration(1000);
                        animation4.start();
                    }
                }, 1000);
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        animation5.setDuration(1000);
                        animation5.start();
                    }
                }, 0);
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        animation6.setDuration(1000);
                        animation6.start();
                    }
                }, 1000);


            }
        });

    }
}
