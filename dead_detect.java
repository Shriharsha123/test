package com.example.myapplication;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.google.android.material.textfield.TextInputLayout;

public class dead_detect extends AppCompatActivity{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dead_detect);
        Button createProcessBt =  findViewById(R.id.addProc);
        createProcessBt.setOnClickListener(new View.OnClickListener() {
            int procNum=0;
            @Override
            public void onClick(View view)
            {
                procNum=procNum+1;
                addProcessInput(procNum);
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
        editText2.setHint("Process " + procNum + " name");

        TextInputLayout bt= new TextInputLayout(this);
        bt.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT));
        EditText editText3= new EditText(this);
        editText3.setLayoutParams(editTextParams);
        editText3.setHint("Process " + procNum + " name");

        parent.addView(newChild);
        newChild.addView(procName);
        newChild.addView(at);
        newChild.addView(bt);
        procName.addView(editText, editTextParams);
        at.addView(editText2, editTextParams);
        bt.addView(editText3, editTextParams);
    }
}
