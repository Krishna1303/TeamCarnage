package com.example.teamcarnage;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {
    EditText e;
    Button b;
    SharedPreferences s ;
    FirebaseDatabase fd =FirebaseDatabase.getInstance();
    DatabaseReference dr;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        s = getSharedPreferences(getString(R.string.app_name), Context.MODE_PRIVATE);
        if (!s.getBoolean("status",false)){
            SharedPreferences.Editor editor = s.edit();
            editor.putBoolean("status",Boolean.TRUE);
            e = findViewById(R.id.phone_edit_text);
            b = findViewById(R.id.btn_submit);
            b.setOnClickListener(v -> {
                String num = e.getText().toString();
                if(num.length()==0)
                    e.setError("Please enter your phone nummber");
                else if(num.length() != 10)
                    e.setError("Please enter correct phone number");
                else {
                    editor.putString("PhoneNumber",num);
                    dr = fd.getReference();
                    dr.child("TeamCarnage Members").child(num).setValue("Account Created");
                    startActivity(new Intent(this,Chats.class));
                    finish();
                }
                editor.apply();
            });
        }
        else{
            finish();
            startActivity(new Intent(this,Chats.class));
        }
    }
}