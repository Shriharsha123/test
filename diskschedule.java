package com.example.myapplication;
import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    EditText inputEditText;
    Button scheduleButton;
    LineChart lineChart;
    TextView result;
    View diskHeadView; // View representing the disk head

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        inputEditText = findViewById(R.id.inputEditText);
        scheduleButton = findViewById(R.id.scheduleButton);
        lineChart = findViewById(R.id.lineChart);
        result = findViewById(R.id.resultTextView);
        diskHeadView = findViewById(R.id.diskHeadView); // Reference to the disk head view

        setupChart();

        scheduleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String input = inputEditText.getText().toString();
                String[] inputArray = input.split(",");

                // Convert input string array to integer array
                int[] requests = new int[inputArray.length];
                for (int i = 0; i < inputArray.length; i++) {
                    requests[i] = Integer.parseInt(inputArray[i].trim());
                }

                //  initial head position
                int initialHeadPosition = 50;

                // Calculate total head movement using FCFS algorithm
                int totalHeadMovement = calculateTotalHeadMovement(requests, initialHeadPosition);

                // Display the result
                result.setText("Total head movement using FCFS: " + totalHeadMovement);

                // Animate the disk head along with the line chart
                animateDiskHead(requests);
                updateLineChart(requests);
            }
        });
    }

    // FCFS disk scheduling algorithm implementation
    private int calculateTotalHeadMovement(int[] requests, int initialHeadPosition) {
        int totalHeadMovement = 0;
        int currentHeadPosition = initialHeadPosition;

        // Calculate total head movement
        for (int i = 0; i < requests.length; i++) {
            totalHeadMovement += Math.abs(requests[i] - currentHeadPosition);
            currentHeadPosition = requests[i];
        }

        return totalHeadMovement;
    }

    // Set up line chart
    private void setupChart() {
        lineChart.getDescription().setEnabled(false); // Disable chart description

        // Customize the X-axis
        XAxis xAxis = lineChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM); // Set X-axis position to bottom
        xAxis.setDrawGridLines(false); // Disable grid lines on X-axis

        // Customize the Y-axis
        YAxis yAxisLeft = lineChart.getAxisLeft();
        yAxisLeft.setDrawGridLines(false); // Disable grid lines on Y-axis

        // Hide the right Y-axis
        lineChart.getAxisRight().setEnabled(false);
    }

    // Animate the disk head movement to a specific position
    private void animateDiskHeadToPosition(int position) {
        // Calculate the translationX value to move the disk head to the specified position
        final float targetX = position;

        // Create an ObjectAnimator to animate the translationX property of the disk head view
        final ObjectAnimator animator = ObjectAnimator.ofFloat(diskHeadView, "translationX", targetX);
        animator.setDuration(1000); // Animation duration in milliseconds

        // Start the animation
        animator.start();
    }

    // Animate the disk head movement along with the disk requests
    private void animateDiskHead(final int[] requests) {
        final Handler handler = new Handler();
        for (int i = 0; i < requests.length; i++) {
            final int request = requests[i];
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    animateDiskHeadToPosition(request);
                }
            }, i * 1000); // Delay each animation by i seconds (adjust as needed)
        }
    }

    // Update line chart with disk requests
    private void updateLineChart(int[] requests) {
        List<Entry> entries = new ArrayList<>();
        for (int i = 0; i < requests.length; i++) {
            entries.add(new Entry(i, requests[i]));
        }

        LineDataSet dataSet = new LineDataSet(entries, "Requests");
        dataSet.setDrawCircles(false); // Disable drawing circles at data points
        dataSet.setDrawValues(false); // Disable drawing values on data points

        LineData lineData = new LineData(dataSet);
        lineChart.setData(lineData);
        lineChart.animateY(1500); // Animate the chart
    }
}
