package com.example.paging1;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.animation.ObjectAnimator;
import android.view.animation.DecelerateInterpolator;
import android.os.Handler;

import java.util.concurrent.atomic.AtomicInteger;

public class MainActivity extends AppCompatActivity {

    private EditText numFrames, numOfPages, pageNum;
    private LinearLayout result;
    private final Handler handler = new Handler(); // Handler for managing animation delays

    @Override
    protected void onCreate(Bundle saved_instance) {
        super.onCreate(saved_instance);
        setContentView(R.layout.activity_main);

        numFrames = findViewById(R.id.numFrames);
        numOfPages = findViewById(R.id.numOfPages);
        pageNum = findViewById(R.id.pageNum);
        Button btn = findViewById(R.id.calculateButton);
        result = findViewById(R.id.result);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                result.removeAllViews(); // Clear previous output
                animateFIFO(); // Animate the FIFO process
            }
        });
    }

    private void smoothShakeAnimation(View view) {

        ObjectAnimator shake = ObjectAnimator.ofFloat(view, "translationX", 0f, 25f, -25f, 25f, -25f, 0f);
        shake.setDuration(600); // Slightly longer duration for a smoother effect
        shake.setInterpolator(new DecelerateInterpolator());
        shake.start(); // Start the shake animation
    }

    private void smoothFadeInAnimation(View view, int duration) {
        // Create a smooth fade-in animation
        ObjectAnimator fadeIn = ObjectAnimator.ofFloat(view, "alpha", 2f, 1f);
        fadeIn.setDuration(duration); // Longer duration for smoother effect
        fadeIn.start(); // Start the fade-in animation
    }

    private void smoothFadeOutAnimation(View view, int duration) {
        // Create a smooth fade-out animation
        ObjectAnimator fadeOut = ObjectAnimator.ofFloat(view, "alpha", 1f, 0f);
        fadeOut.setDuration(duration); // Longer duration for smoother effect
        fadeOut.start(); // Start the fade-out animation
    }
    private void animateFIFO() {
        try {
            int frames = Integer.parseInt(numFrames.getText().toString());
            int page_len = Integer.parseInt(numOfPages.getText().toString());
            String[] refInput = pageNum.getText().toString().split("\\s*,\\s*"); // Split by comma
            int[] reference = new int[page_len];

            for (int i = 0; i < page_len; i++) {
                reference[i] = Integer.parseInt(refInput[i]); // Convert to integer
            }

            int[] buffer = new int[frames];
            TextView[] frameViews = new TextView[frames]; // Keep track of frame views
            final int[] pointer = {0};

           
            for (int j = 0; j < frames; j++) {
                buffer[j] = -1; // Placeholder for empty frames
                TextView frameText = new TextView(this);
                frameText.setTypeface(Typeface.MONOSPACE); // Consistent spacing
                //frameText.setText("-");
                //frameText.setPadding(16, 16, 16, 16); // Padding for spacing
                result.addView(frameText); // Add to the output container
                frameViews[j] = frameText; // Keep reference to frame views
                smoothFadeInAnimation(frameText, 800); // Smooth fade-in for initialization
            }

            final AtomicInteger[] hit = {new AtomicInteger()};
            final AtomicInteger[] fault = {new AtomicInteger()};

            // Perform the FIFO algorithm with delays for smooth animation
            for (int i = 0; i < page_len; i++) {
                int finalI = i;
                handler.postDelayed(() -> {
                    boolean pageHit = false;

                    // Check for page hit
                    for (int j = 0; j < frames; j++) {
                        if (buffer[j] == reference[finalI]) {
                            pageHit = true;
                            hit[0].getAndIncrement(); // Increment the hit count
                            smoothShakeAnimation(frameViews[j]); // Apply smooth shake for page hits
                            break;
                        }
                    }

                    if (!pageHit) { // If it's a page miss, handle replacement
                        fault[0].getAndIncrement(); // Increment the fault count

                        // Smooth fade-out for old frame
                        smoothFadeOutAnimation(frameViews[pointer[0]], 800);

                        // Smooth fade-in for new frame after replacement
                        frameViews[pointer[0]].setText(String.valueOf(reference[finalI])); // Replace with the new page
                        smoothFadeInAnimation(frameViews[pointer[0]], 800); // Smooth fade-in
                        buffer[pointer[0]] = reference[finalI]; // Update the buffer
                        pointer[0] = (pointer[0] + 1) % frames; // Update the pointer for FIFO
                    }
                }, 900L * i); // Delay to ensure animations are smooth and complete before the next step
            }
            // After all steps are processed, show the hit and fault count
            handler.postDelayed(() -> {
                TextView hitText = new TextView(this);
                hitText.setText("Number of Hits: " + hit[0]);
                result.addView(hitText); // Add hit count to result

                TextView faultText = new TextView(this);
                faultText.setText("Number of Faults: " + fault[0]);
                result.addView(faultText); // Add fault count to result
            }, 900L * page_len + 800); // Final delay for summary

        } catch (Exception e) {
            TextView errorText = new TextView(this);
            errorText.setText("Error: Please check your input.");
            result.addView(errorText); // Display error message if there's an exception
        }
    }
}
