package com.example.proc_scheduling;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import java.sql.Time;
import java.util.Random;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

class MultiThread extends Thread{
    Semaphore s;
    int i;
    public void run()
    {

        System.out.println("Thread " + String.valueOf(i) + " requesting access");
        try {
            s.acquire();
            System.out.println("Thread " + String.valueOf(i) + " acquired access");
            TimeUnit.SECONDS.sleep(5);
        } catch (InterruptedException e) {
            System.out.println("Thread " + String.valueOf(i) + " waiting");
        }

        s.release();
        System.out.println("Thread " + String.valueOf(i) + " released access");
    }
    public void setValues(Semaphore sem,int val)
    {
        this.s=sem;
        this.i=val;
    }
}
public class proc_synch1 extends AppCompatActivity {
    int semaphore;
    int threadnUM;
    Semaphore s;
    Button submitBtn;
    EditText semval,threadval;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.semaphore);
        submitBtn=findViewById(R.id.button);
        semval=findViewById(R.id.semValue);
        threadval=findViewById(R.id.threadNum);
        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    semaphore=Integer.parseInt(semval.getText().toString());
                    threadnUM=Integer.parseInt(threadval.getText().toString());
                    s=new Semaphore(semaphore);

                    for (int i = 0; i <threadnUM ; i++) {
                        MultiThread inst=new MultiThread();
                        inst.setValues(s,i);
                        inst.start();
                    }
                    /*Random ran=new Random();
                    int[] acqdelays=new int[threadnUM];
                    int[] reldelays=new int[threadnUM];
                    for (int i = 0; i <threadnUM ; i++) {
                        acqdelays[i]= ran.nextInt(10);
                        reldelays[i]= ran.nextInt(10);
                    }
                    Handler handle=new Handler();
                    for (int i = 0; i <threadnUM ; i++) {
                        int finalI1 = i;
                        handle.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                System.out.println("Thread " + String.valueOf(finalI1) + " requesting access");
                                try {
                                    s.acquire();
                                    System.out.println("Thread " + String.valueOf(finalI1) + " acquired access");
                                    Time t=new Time(0,0,5);
                                    t.wait();
                                    s.release();
                                } catch (InterruptedException e) {
                                    System.out.println("Thread " + String.valueOf(finalI1) + " waiting");
                                }
                            }
                            },acqdelays[i]*1000L);
                    }
                    for (int i = 0; i <threadnUM ; i++) {
                        int finalI1 = i;
                        handle.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                s.release();
                                System.out.println("Thread " + String.valueOf(finalI1) + " released access");
                            }
                        },acqdelays[i]+reldelays[i]*1000L);
                    }*/

                }
                catch (Exception e) {
                    Toast.makeText(proc_synch1.this,"Error:" + String.valueOf(e),Toast.LENGTH_LONG ).show();
                    System.out.println(String.valueOf(e));
                }
            }
        });
    }
}