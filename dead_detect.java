package com.example.myapplication;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;

class Process
{
    String name;
    int at;
    int bt;

    Process next;


    public Process() {
        this.name = null;
        this.at= -1;
        this.bt=-1;
        this.next=null;
    }
    public String getName()
    {
        return this.name;
    }
    public int getat()
    {
        return this.at;
    }
    public int getbt()
    {
        return this.bt;
    }
    public void setName(String str)
    {
        this.name=str;

    }
    public void setAt(int arrt)
    {
        this.at=arrt;

    }
    public void setBt(int burt)
    {
        this.bt=burt;

    }
    public boolean checkFill()
    {
        if(this.name==null|| this.at<0 || this.bt<0)
            return false;
        return true;
    }


}

public class dead_detect extends AppCompatActivity{
    //boolean isFilled=true;
    Process head=null;
    int procNum=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dead_detect);
        //int procNum=0;

        Button createProcessBt =  findViewById(R.id.addProc);
        Button submitBt= findViewById(R.id.submitProcSched);
        createProcessBt.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view)
            {
                Process tail=getTail();
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
                    Toast.makeText(dead_detect.this, "You did not enter values", Toast.LENGTH_SHORT).show();
                }
            }
        });



        submitBt.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view)
            {
                takeSubmission(procNum);
            }
        });
    }
    private void addProcessInput(int procNum)
    {
        LinearLayout parent1= findViewById(R.id.procNameRow);
        LinearLayout parent2= findViewById(R.id.atRow);
        LinearLayout parent3= findViewById(R.id.btRow);


        TextInputLayout procName= new TextInputLayout(this);
        procName.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT));
        LinearLayout.LayoutParams editTextParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        EditText editText= new EditText(this);
        editText.setLayoutParams(editTextParams);
        editText.setHint("Process " + procNum + " name");

        TextInputLayout at= new TextInputLayout(this);
        at.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT));
        EditText editText2= new EditText(this);
        editText2.setLayoutParams(editTextParams);
        editText2.setHint("Arrival Time");
        editText2.setInputType(InputType.TYPE_CLASS_NUMBER);

        TextInputLayout bt= new TextInputLayout(this);
        bt.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT));
        EditText editText3= new EditText(this);
        editText3.setLayoutParams(editTextParams);
        editText3.setHint("Burst Time");
        editText3.setInputType(InputType.TYPE_CLASS_NUMBER);

        parent1.addView(procName);
        parent2.addView(at);
        parent3.addView(bt);
        procName.addView(editText, editTextParams);
        at.addView(editText2, editTextParams);
        bt.addView(editText3, editTextParams);

        Process newProc;
       if(head==null)
       {
           head=new Process();
           newProc=head;
       }
       else
       {
           Process temp=head;
           while(temp.next!=null)
           {
               temp=temp.next;
           }
           newProc=new Process();
           temp.next=newProc;
       }


        /*if (input2.matches("")) {
            Toast.makeText(this, "You did not enter Arrival time", Toast.LENGTH_SHORT).show();
            isFilled=false;
        }*/


        /*if (input3.matches("")) {
            Toast.makeText(this, "You did not enter Burst time", Toast.LENGTH_SHORT).show();
            isFilled=false;
        }*/

        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }
            public void afterTextChanged(Editable s) {
                String input = editText.getText().toString();
                //newProc.setName(input);
                Boolean filled;
                if (input.equals("")) {
                    filled = false;
                    newProc.setName(null);
                }
                else
                    filled=true;
                if (filled)
                    newProc.setName(input);
            }
        });
        editText2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }
            public void afterTextChanged(Editable s) {
                String input2 = editText2.getText().toString();
                //newProc.setAt(Integer.parseInt(input2));
                Boolean filled;
                if (input2.equals("")) {
                    filled = false;
                    newProc.setAt(-1);
                }
                else
                    filled=true;
                if (filled)
                    newProc.setAt(Integer.parseInt(input2));
            }
        });
        editText3.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }
            public void afterTextChanged(Editable s) {
                String input3 = editText3.getText().toString();
                Boolean filled;
                if (input3.equals("")) {
                    filled = false;
                    newProc.setBt(-1);
                }
                else
                    filled=true;
                if (filled)
                    newProc.setBt(Integer.parseInt(input3));
            }
        });

    }

    public Process getTail()
    {
        if (head==null)
            return null;
        Process temp=head;
        while(temp.next!=null)
        {
            temp=temp.next;
        }
        return temp;
    }


        private String getProcName(int num)
        {
            Process temp=head;
            int count=1;
            while(count!=num)
            {
                temp=temp.next;
                count+=1;
            }
            return temp.getName();
        }

    private int getProcat(int num)
    {
        Process temp=head;
        int count=1;
        while(count!=num)
        {
            temp=temp.next;
            count+=1;
        }
        return temp.getat();
    }

    private int getProcbt(int num)
    {
        Process temp=head;
        int count=1;
        while(count!=num)
        {
            temp=temp.next;
            count+=1;
        }
        return temp.getbt();
    }

        private void takeSubmission(int n) {
            int[] at=new int[n];
            int[] bt=new int[n];
            String[] name=new String[n];

            for(int i=0;i<n;i++)
            {
                at[i]=getProcat(i);
                bt[i]=getProcbt(i);
                name[i]=getProcName(i);
            }

            int[] ct = new int[n];
            int[] tat = new int[n];
            int[] wt = new int[n];
            int temp;
            float awt = 0, atat = 0;

            for (int i = 0; i < n; i++) {
                for (int j = 0; j < (n - i); j++) {
                    if (at[j] > at[j + 1]) {
                        String tempName = name[j + 1];
                        name[j + 1] = name[j];
                        name[j] = tempName;
                        temp = at[j + 1];
                        at[j + 1] = at[j];
                        at[j] = temp;
                        temp = bt[j + 1];
                        bt[j + 1] = bt[j];
                        bt[j] = temp;
                    }
                }
            }

            ct[0] = at[0] + bt[0];

            for (int i = 1; i < n; i++) {
                temp = 0;
                if (ct[i - 1] < at[i]) {
                    temp = at[i] - ct[i - 1];
                }
                ct[i] = ct[i - 1] + bt[i] + temp;
            }

            System.out.println("\np\t A.T\t B.T\t C.T\t TAT\t WT");
            for (int i = 0; i < n; i++) {
                tat[i] = ct[i] - at[i];
                wt[i] = tat[i] - bt[i];
                atat += tat[i];
                awt += wt[i];
            }
            atat = atat / n;
            awt = awt / n;
            for (int i = 0; i < n; i++) {
                System.out.println("P" + name[i] + "\t " + at[i] + "\t " + bt[i] + "\t " + ct[i] + "\t " + tat[i] + "\t " + wt[i]);
            }
            System.out.println("average turnaround time is " + atat);
            System.out.println("average waiting time is " + awt);
        }





}
