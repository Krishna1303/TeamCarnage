package com.example.teamcarnage;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.net.Uri;
import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

import java.util.Random;

public class MsgAdapter extends FirebaseRecyclerAdapter<Messages,MsgAdapter.MsgViewHolder> {
    Random r =  new Random();
    GradientDrawable p = new GradientDrawable();
    Listeners mListeners;
    public interface Listeners {
        void onItemClick(int position);
    }
    public void setOnListeners(Listeners listeners){
        mListeners = listeners;
    }
    public MsgAdapter(@NonNull FirebaseRecyclerOptions<Messages> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull MsgViewHolder holder, int position, @NonNull Messages model) {
        holder.msgBy.setText(nameFinder(model.getPhoneNo(),holder.msg.getContext()));
        holder.msg.setText(model.getMsg());
        holder.msgTime.setText(model.getTime());
    }

    @NonNull
    @Override
    public MsgViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_view_msgs,parent,false);
        p.setCornerRadius(10f);
        p.setColor(Color.rgb(255,200,231));
        view.setBackground(p);
        return new MsgAdapter.MsgViewHolder(view,mListeners);
    }

    public class MsgViewHolder extends RecyclerView.ViewHolder{
        TextView msgBy,msg,msgTime;
        public MsgViewHolder(@NonNull View itemView,Listeners listeners) {
            super(itemView);
            msgBy = itemView.findViewById(R.id.msg_by);
            msg = itemView.findViewById(R.id.msg);
            msgTime = itemView.findViewById(R.id.msg_time);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    listeners.onItemClick(position);
                }
            });
        }
    }
    public String nameFinder(String s, Context context){
        Uri uri=Uri.withAppendedPath(ContactsContract.PhoneLookup.CONTENT_FILTER_URI,Uri.encode(s));
        String contactName="";
        Cursor cursor= context.getContentResolver().query(uri,null,null,null,null);
        if (cursor != null) {
            if(cursor.moveToFirst()) {
                contactName=cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
            }
            cursor.close();
        }
        if(contactName=="") return "Unknown Name";
        else return contactName;
    }
}
