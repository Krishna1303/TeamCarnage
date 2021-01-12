package com.example.teamcarnage;

import android.content.Intent;
import android.os.Bundle;
import android.widget.RelativeLayout;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.FirebaseDatabase;

public class Chats extends AppCompatActivity {
    FirebaseDatabase fd = FirebaseDatabase.getInstance();
    RelativeLayout t;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chats);
        getSupportActionBar().hide();
        t = findViewById(R.id.id_rl);
        t.setOnClickListener(v -> {
            startActivity(new Intent(this,ChatScreen.class));
        });
    }
}