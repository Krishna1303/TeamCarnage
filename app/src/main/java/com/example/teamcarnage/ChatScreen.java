package com.example.teamcarnage;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class ChatScreen extends AppCompatActivity {
    LinearLayout l;
    FirebaseRecyclerOptions<Messages> values;
    RecyclerView r;
    MsgAdapter adapter;
    DatabaseReference d;
    EditText e;
    FloatingActionButton b;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_screen);
        getSupportActionBar().hide();
        SharedPreferences s = getSharedPreferences(getString(R.string.app_name),MODE_PRIVATE);
        d = FirebaseDatabase.getInstance().getReference("Team Carnage Chat");
        r = findViewById(R.id.msg_recycler_view);
        l = findViewById(R.id.id_back);
        e = findViewById(R.id.msg_from_mem);
        b = findViewById(R.id.send_msg);
        l.setOnClickListener(v -> {
            finish();
        });
        b.setOnClickListener(v1 ->{
            String msg = e.getText().toString().trim();
            Messages m = new Messages(msg,dateAndTime(),s.getString("PhoneNumber",""));
            e.setText("");
            d.child(msgNumFinder()).setValue(m);
        });
        LinearLayoutManager linear = new LinearLayoutManager(this);
        linear.setStackFromEnd(true);
        r.setLayoutManager(linear);
        values = new FirebaseRecyclerOptions.Builder<Messages>().setQuery(d,Messages.class).build();
        adapter = new MsgAdapter(values);
        r.setAdapter(adapter);
        r.smoothScrollToPosition(Integer.parseInt(msgNumFinder())-1);
        adapter.setOnListeners(new MsgAdapter.Listeners() {
            @Override
            public void onItemClick(int position) {
                Toast.makeText(ChatScreen.this, "CLICKED ITEM NO-"+String.valueOf(position+1), Toast.LENGTH_LONG).show();
            }
        });
    }
    @Override protected void onStart()
    {
        super.onStart();
        adapter.startListening();
    }
    @Override protected void onStop()
    {
        super.onStop();
        adapter.stopListening();
    }
    public String dateAndTime(){
        Calendar d = Calendar.getInstance();
        SimpleDateFormat s = new SimpleDateFormat("HH : MM ");
        return s.format(d.getTime());
    }
    public String msgNumFinder(){
        return String.valueOf(values.getSnapshots().size()+1);
    }
}