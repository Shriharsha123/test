package com.example.databaseapplication53;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
public class MainActivity extends AppCompatActivity {
    Button submitbtn,callButton;
    EditText name,usn,phone,branch,sem,usnCall;
    DatabaseConnection db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        submitbtn=findViewById(R.id.submitBtn);
        name=findViewById(R.id.name);
        usn=findViewById(R.id.usn);
        branch=findViewById(R.id.branch);
        phone=findViewById(R.id.phone);
        sem=findViewById(R.id.sem);
        callButton=findViewById(R.id.CALLbTN);
        usnCall=findViewById(R.id.usnToCall);
        submitbtn.setOnClickListener(v->{
            String usnStr=usn.getText().toString();
            String nameStr=name.getText().toString();
            String branchStr=branch.getText().toString();
            String phonenum=phone.getText().toString();
            int semester=Integer.parseInt(sem.getText().toString());
            db=new DatabaseConnection(this);
            Boolean myResult=db.insertValues(nameStr,usnStr,branchStr,phonenum,semester);
            if(myResult)
                Toast.makeText(MainActivity.this,"Data inserted successfully",Toast.LENGTH_LONG).show();
            else
                Toast.makeText(MainActivity.this,"Data is not inserted",Toast.LENGTH_LONG).show();
        });
        callButton.setOnClickListener(v-> {
            String myUSN=usnCall.getText().toString();
            db=new DatabaseConnection(this);
            Cursor myCursor= db.fetchPhoneNo(myUSN);
            myCursor.moveToFirst();
            String myPhoneNumber=String.valueOf(myCursor.getInt(0));
            Intent i=new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + myPhoneNumber));
            startActivity(i);
        });
    }
}