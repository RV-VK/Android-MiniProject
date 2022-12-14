package com.example.smartportal;

import static com.example.smartportal.R.id.editTextTextPersonName;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {

    EditText e1,e2;
    Button b;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        e1=findViewById(editTextTextPersonName);
        e2=findViewById(R.id.editTextTextPassword);
        b=findViewById(R.id.button);
        FirebaseAuth auth=FirebaseAuth.getInstance();
        b.setOnClickListener(v-> {
            String uname = e1.getText().toString();
            String pwd = e2.getText().toString();
            if (uname.isEmpty() || pwd.isEmpty())
            {
                Toast.makeText(getApplicationContext(),"Missing Credentials",Toast.LENGTH_SHORT).show();
            }
            else{
                auth.signInWithEmailAndPassword(uname,pwd).addOnCompleteListener(this,task -> {
                    if(task.isSuccessful()){
                        String temp[]=uname.split("\\_");
                        if(temp[1].contains("staff"))
                        {
                            Toast.makeText(this, "Staff Login!", Toast.LENGTH_SHORT).show();
                            Intent i=new Intent(getApplicationContext(),StaffActivity.class);
                            startActivity(i);
                            finish();
                        }
                        else if(temp[1].contains("student"))
                        {
                            Toast.makeText(this,"Student Login!",Toast.LENGTH_SHORT).show();
                            Intent i=new Intent(getApplicationContext(),StudentActivity.class);
                            i.putExtra("usr",uname);
                            startActivity(i);
                            finish();
                        }
                    }
                    else{
                        Toast.makeText(this, "Login Failed! Invalid Credentials", Toast.LENGTH_SHORT).show();
                    }

                });
            }
        });


    }
}