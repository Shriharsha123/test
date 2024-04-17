package com.example.myapplication;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import java.util.Arrays;
public class memoryAllocation extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mem_alloc);
        getValues();

    }
    public void firstFit(int[] blockSize, int m, int[] processSize, int n) {
        int[] allocation = new int[n];
        Arrays.fill(allocation, -1);
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                if (blockSize[j] >= processSize[i]) {
                    allocation[i] = j;
                    blockSize[j] -= processSize[i];
                    break;
                }
            }
        }
        System.out.println("\nProcess No.\tProcess Size\tBlock no.");
        for (int i = 0; i < n; i++) {
            System.out.print(" " + (i+1) + "\t\t" +
                    processSize[i] + "\t\t");
            if (allocation[i] != -1)
                System.out.print(allocation[i] + 1);
            else
                System.out.print("Not Allocated");
            System.out.println();
        }
    }
    static void bestFit(int[] blockSize, int m, int[] processSize, int n) {
        int allocation[] = new int[n];
        Arrays.fill(allocation, -1);
        for (int i=0; i<n; i++) {
            int bestIdx = -1;
            for (int j=0; j<m; j++) {
                if (blockSize[j] >= processSize[i]) {
                    if (bestIdx == -1)
                        bestIdx = j;
                    else if (blockSize[bestIdx] > blockSize[j])
                        bestIdx = j;
                }
            }
            if (bestIdx != -1) {
                allocation[i] = bestIdx;
                blockSize[bestIdx] -= processSize[i];
            }
        }
        System.out.println("\nProcess No.\tProcess Size\tBlock no.");
        for (int i = 0; i < n; i++) {
            System.out.print("   " + (i+1) + "\t\t" + processSize[i] + "\t\t");
            if (allocation[i] != -1)
                System.out.print(allocation[i] + 1);
            else
                System.out.print("Not Allocated");
            System.out.println();
        }
    }
    void worstFit(int[] blockSize, int m, int[] processSize, int n) {
        int[] allocation=new int[n];
        Arrays.fill(allocation, -1);
        for (int i=0; i<n; i++) {
            int wstIdx = -1;
            for (int j=0; j<m; j++) {
                if (blockSize[j] >= processSize[i]) {
                    if (wstIdx == -1)
                        wstIdx = j;
                    else if (blockSize[wstIdx] < blockSize[j])
                        wstIdx = j;
                }
            }
            if (wstIdx != -1) {
                allocation[i] = wstIdx;
                blockSize[wstIdx] -= processSize[i];
            }
        }
        System.out.println("\nProcess No.\tProcess Size\tBlock no.");
        for (int i = 0; i < n; i++) {
            System.out.println("   " + (i+1) + "\t\t" + processSize[i] + "\t\t");
            if (allocation[i] != -1)
                System.out.print(allocation[i] + 1);
            else
                System.out.print("Not Allocated");
            System.out.println();
        }
    }
    public void getValues() {
        int[] blockSize = {100, 500, 200, 300, 600};
        int[] processSize = {212, 417, 112, 426};
        int m = blockSize.length;
        int n = processSize.length;
        firstFit(blockSize, m, processSize, n);
        bestFit(blockSize, m, processSize, n);
    }
}