package com.example.memoryallocation;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;


import com.google.android.material.textfield.TextInputLayout;


class Process
{
    String name;
    int pro_size;

    Process next;


    public Process() {
        this.name = null;
        this.pro_size= -1;
        this.next=null;
    }
    public String getName()
    {
        return this.name;
    }
    public int getPSize(){
        return this.pro_size;
    }
    public void setName(String str)
    {
        this.name=str;
    }
    public void setProSize(int pro_size){
        this.pro_size = pro_size;
    }
    public boolean checkFill()
    {
        return this.name != null && this.pro_size >= 0;
    }
}

class MemoryBlock{
    int block_size;

    MemoryBlock next;
    public MemoryBlock() {
        this.block_size= -1;
        this.next=null;
    }
    public int getBSize(){
        return this.block_size;
    }
    public void setBlocksize(int block_size){
        this.block_size=block_size;
    }
    public boolean checkFill()
    {
        return this.block_size >= 0;
    }
}
public class memory_main extends AppCompatActivity{
    int procNum=0;
    int algorithm = 0;
    int resNum=0;
    Process prohead;
    MemoryBlock memhead;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.memoryalloc);
        prohead=null;
        memhead=null;

        Button createProcessBt =  findViewById(R.id.create_process);
        Button createResourceBt = findViewById(R.id.create_block);
        Button submitbtn = findViewById(R.id.submit);
        RadioButton firstbtn = findViewById(R.id.firstfitbtn);
        RadioButton bestbtn = findViewById(R.id.bestfitbtn);
        RadioButton worstbtn = findViewById(R.id.worstfitbtn);
        RadioGroup radioGroup = (RadioGroup)findViewById(R.id.selectAlgorithm);
        createProcessBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                Process tail=getProTail();
                if(tail==null)
                {
                    procNum = procNum + 1;
                    addProcessInput(procNum);
                }
                //int procNum=0;
                else if(tail.checkFill()) {
                    procNum = procNum + 1;
                    addProcessInput(procNum);
                }
                else
                {
                    Toast.makeText(memory_main.this, "You did not enter values", Toast.LENGTH_SHORT).show();
                }
                procNum = procNum + 1;
                addProcessInput(procNum);
            }
        });

        createResourceBt.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view)
            {
                MemoryBlock tail=getBlockTail();
                if(tail==null)
                {
                    resNum = resNum + 1;
                    addResourceInput(resNum);
                }
                //int procNum=0;
                else if(tail.checkFill()) {
                    resNum = resNum + 1;
                    addResourceInput(resNum);
                }
                else
                {
                    Toast.makeText(memory_main.this, "You did not enter values", Toast.LENGTH_SHORT).show();
                }
            }
        });

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // find which radio button is selected
                if(checkedId == R.id.firstfitbtn) {
                    algorithm = 1;
                } else if(checkedId == R.id.bestfitbtn) {
                    algorithm = 2;
                } else if(checkedId == R.id.worstfitbtn) {
                    algorithm = 3;
                }

            }

        });

        submitbtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view)
            {
                Animation fadeInAnimation = AnimationUtils.loadAnimation(memory_main.this, R.anim.sample);

                // Apply the animation to the root view of the activity layout
                View rootView = findViewById(android.R.id.content);
                rootView.startAnimation(fadeInAnimation);
                takeSubmission(algorithm,procNum,resNum);
            }
        });

    }

    private void addProcessInput(int procNum)
    {
        LinearLayout layout= findViewById(R.id.processCreateCol);

        TextInputLayout newInput= new TextInputLayout(this);

        newInput.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT));
        LinearLayout.LayoutParams editTextParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);

        EditText editText= new EditText(this);
        editText.setLayoutParams(editTextParams);
        editText.setHint("Process " + procNum + "size");
        editText.setInputType(EditorInfo.TYPE_CLASS_NUMBER);

        layout.addView(newInput);
        newInput.addView(editText, editTextParams);

        Process newProc;
        if(prohead==null)
        {
            prohead=new Process();
            newProc=prohead;
        }
        else
        {
            Process temp=getProTail();
            newProc=new Process();
            temp.next=newProc;
        }

        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
            public void afterTextChanged(Editable s) {
                String input = editText.getText().toString();
                boolean filled;
                if (input.isEmpty()) {
                    filled = false;
                    newProc.setName(null);
                    newProc.setProSize(0);
                }
                else
                    filled=true;
                if (filled){
                    newProc.setName("P" + procNum);
                    newProc.setProSize(Integer.parseInt(input));
                }

            }
        });
    }
    private void addResourceInput(int resNum) {

        LinearLayout layout = findViewById(R.id.blockCreateCol);

        TextInputLayout newInput = new TextInputLayout(this);

        newInput.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT));

        LinearLayout.LayoutParams editTextParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);

        EditText editText = new EditText(this);
        editText.setLayoutParams(editTextParams);
        editText.setHint("Block " + resNum + "size");
        editText.setInputType(EditorInfo.TYPE_CLASS_NUMBER);

        layout.addView(newInput);
        newInput.addView(editText, editTextParams);

        MemoryBlock newBlock;
        if(memhead==null)
        {
            memhead=new MemoryBlock();
            newBlock=memhead;
        }
        else
        {
            MemoryBlock temp=getBlockTail();
            newBlock=new MemoryBlock();
            temp.next=newBlock;
        }


        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
            public void afterTextChanged(Editable s) {
                String input = editText.getText().toString();
                boolean filled;
                if (input.isEmpty()) {
                    filled = false;
                }
                else
                    filled=true;
                if (filled)
                    newBlock.setBlocksize(Integer.parseInt(input));
            }
        });
    }

    private Process getProTail() {
        Process result = null;
        if (prohead != null) {
            Process temp = prohead;
            while (temp.next != null) {
                temp = temp.next;
            }
            result = temp;
        }
        return result;
    }
    private MemoryBlock getBlockTail() {
        MemoryBlock result = null;
        if (memhead != null) {
            MemoryBlock temp = memhead;
            while (temp.next != null) {
                temp = temp.next;
            }
            result = temp;
        }
        return result;
    }

    private String getProcName(int num)
    {
        Process temp=prohead;
        int count=0;
        while(count<num)
        {
            temp=temp.next;
            count+=1;
        }
        return temp.getName();
    }

    private int getProSize(int num)
    {
        Process temp=prohead;
        int count=0;
        while(count<num)
        {
            temp=temp.next;
            count+=1;
        }
        return temp.getPSize();
    }

    private int getBlockSize(int num)
    {
        MemoryBlock temp=memhead;
        int count=0;
        while(count<num)
        {
            temp=temp.next;
            count+=1;
        }
        return temp.getBSize();
    }
    private void takeSubmission(int algorithm,int n,int m){

        if(algorithm == 1){

            int allocation[] = new int[n];
            int blockSize[] = new int[m];
            int processSize[]=new int[n];

            for(int i=0;i<n;i++){
                processSize[i] = getProSize(i);
            }
            for(int i=0;i<m;i++){
                blockSize[i] = getBlockSize(i);
            }
            // Initially no block is assigned to any process

            for (int i = 0; i < allocation.length; i++)
                allocation[i] = -1;
            // pick each process and find suitable blocks
            // according to its size ad assign to it
            for (int i = 0; i < n; i++)
            {
                for (int j = 0; j < m; j++)
                {
                    if (blockSize[j] >= processSize[i])
                    {
                        // allocate block j to p[i] process
                        allocation[i] = j;
                        // Reduce available memory in this block.
                        blockSize[j] -= processSize[i];
                        break;
                    }
                }
            }
            System.out.println("\nProcess No.\tProcess Size\tBlock no.");
            for (int i = 0; i < n; i++)
            {
                System.out.print(" " + (i+1) + "\t\t" +
                        processSize[i] + "\t\t");
                if (allocation[i] != -1)
                    System.out.print(allocation[i] + 1);
                else
                    System.out.print("Not Allocated");
                System.out.println();
            }
        }
        else if(algorithm==2){
            int allocation[] = new int[n];
            int blockSize[] = new int[m];
            int processSize[]=new int[n];

            for(int i=0;i<n;i++){
                processSize[i] = getProSize(i);
            }
            for(int i=0;i<m;i++){
                blockSize[i] = getBlockSize(i);
            }
            // Initially no block is assigned to any process
            for (int i = 0; i < allocation.length; i++)
                allocation[i] = -1;
            // pick each process and find suitable blocks
            // according to its size ad assign to it
            for (int i=0; i<n; i++)
            {
                // Find the best fit block for current process
                int bestIdx = -1;
                for (int j=0; j<m; j++)
                {
                    if (blockSize[j] >= processSize[i])
                    {
                        if (bestIdx == -1)
                            bestIdx = j;
                        else if (blockSize[bestIdx] > blockSize[j])
                            bestIdx = j;
                    }
                }
                // If we could find a block for current process
                if (bestIdx != -1)
                {
                    // allocate block j to p[i] process
                    allocation[i] = bestIdx;
                    // Reduce available memory in this block.
                    blockSize[bestIdx] -= processSize[i];
                }
            }
            System.out.println("\nProcess No.\tProcess Size\tBlock no.");
            for (int i = 0; i < n; i++)
            {
                System.out.print("   " + (i+1) + "\t\t" + processSize[i] + "\t\t");
                if (allocation[i] != -1)
                    System.out.print(allocation[i] + 1);
                else
                    System.out.print("Not Allocated");
                System.out.println();
            }
        }
        else{
            int allocation[] = new int[n];
            int blockSize[] = new int[m];
            int processSize[]=new int[n];

            for(int i=0;i<n;i++){
                processSize[i] = getProSize(i);
            }
            for(int i=0;i<m;i++){
                blockSize[i] = getBlockSize(i);
            }

            for (int i = 0; i < allocation.length; i++)
                allocation[i] = -1;
            // Initially no block is assigned to any process

            // pick each process and find suitable blocks
            // according to its size ad assign to it
            for (int i=0; i<n; i++)
            {
                // Find the best fit block for current process
                int wstIdx = -1;
                for (int j=0; j<m; j++)
                {
                    if (blockSize[j] >= processSize[i])
                    {
                        if (wstIdx == -1)
                            wstIdx = j;
                        else if (blockSize[wstIdx] < blockSize[j])
                            wstIdx = j;
                    }
                }
                // If we could find a block for current process
                if (wstIdx != -1)
                {
                    // allocate block j to p[i] process
                    allocation[i] = wstIdx;
                    // Reduce available memory in this block.
                    blockSize[wstIdx] -= processSize[i];
                }
            }
            System.out.println("\nProcess No.\tProcess Size\tBlock no.");
            for (int i = 0; i < n; i++)
            {
                System.out.print("   " + (i+1) + "\t\t" + processSize[i] + "\t\t");
                if (allocation[i] != -1)
                    System.out.println(allocation[i] + 1);
                else
                    System.out.println("Not Allocated");
                System.out.println();
            }
        }
    }

}