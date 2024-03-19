package com.example.myapplication;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
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


    public Process(String str, int num1, int num2) {
        this.name = str;
        this.at= num1;
        this.bt=num2;
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


}

public class dead_detect extends AppCompatActivity{
    boolean isFilled=true;
    Process head=null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dead_detect);
        int procNum=0;

        Button createProcessBt =  findViewById(R.id.addProc);
        Button submitBt= findViewById(R.id.submitProcSched);
        createProcessBt.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view)
            {
                if(isFilled) {
                    procNum = procNum + 1;
                    addProcessInput(procNum);
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
        LinearLayout parent= findViewById(R.id.parent);

        LinearLayout newChild= new LinearLayout(this);
        LinearLayout.LayoutParams newChildParams= new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT);
        newChild.setLayoutParams(newChildParams);
        newChild.setOrientation(LinearLayout.HORIZONTAL);

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

        TextInputLayout bt= new TextInputLayout(this);
        bt.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT));
        EditText editText3= new EditText(this);
        editText3.setLayoutParams(editTextParams);
        editText3.setHint("Burst Time");

        parent.addView(newChild);
        newChild.addView(procName);
        newChild.addView(at);
        newChild.addView(bt);
        procName.addView(editText, editTextParams);
        at.addView(editText2, editTextParams);
        bt.addView(editText3, editTextParams);

        String input = editText.getText().toString();
        if (input.matches("")) {
            Toast.makeText(this, "You did not enter a username", Toast.LENGTH_SHORT).show();
            isFilled=false;
        }

        String input2 = editText2.getText().toString();
        if (input2.matches("")) {
            Toast.makeText(this, "You did not enter a username", Toast.LENGTH_SHORT).show();
            isFilled=false;
        }

        String input3 = editText3.getText().toString();
        if (input3.matches("")) {
            Toast.makeText(this, "You did not enter a username", Toast.LENGTH_SHORT).show();
            isFilled=false;
        }
        if(head==null)
            head= new Process(input,Integer.parseInt(input2),Integer.parseInt(input3));
        else
        {
            Process temp=head;
            while(temp.next!=null)
            {
                temp=temp.next;
            }
            Process newProc= new Process(input,Integer.parseInt(input2),Integer.parseInt(input3));
            temp.next=newProc;
        }
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
