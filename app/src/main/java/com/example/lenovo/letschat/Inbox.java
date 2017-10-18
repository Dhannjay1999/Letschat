package com.example.lenovo.letschat;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import static com.example.lenovo.letschat.R.id.mylist;

public class Inbox extends AppCompatActivity {
private static final int Default_Msg_Limit=1000;
    FirebaseAuth mfirebaseauth;
    FirebaseDatabase f;
    String frndname;
    String name2;
    ListView mylistView;
    DatabaseReference d;
    ArrayList<Message> myMessageList;
    DatabaseReference df;
    ImageButton msgbtn;
    ImageButton gallerybtn;
    EditText mytext;
    String msg;
    AdapterMessage adapterMessage;
    ValueEventListener a;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inbox);
        Intent i=getIntent();
        frndname=i.getStringExtra("UserNames");
         name2=i.getStringExtra("FireAuth");
       f=FirebaseDatabase.getInstance();
        d=f.getReference(frndname+name2);
        df=f.getReference(name2+frndname);
        mylistView=(ListView)findViewById(R.id.privatemessage);
        myMessageList=new ArrayList<>();
         adapterMessage=new AdapterMessage(this,myMessageList);
        mylistView.setAdapter(adapterMessage);
        msgbtn=(ImageButton)findViewById(R.id.msgbtn);
        gallerybtn=(ImageButton)findViewById(R.id.gallerybtn);
        mytext=(EditText)findViewById(R.id.mytext);
                    mytext.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    if (charSequence.toString().trim().length() > 0) {
                        msgbtn.setEnabled(true);
                    } else {
                        msgbtn.setEnabled(false);
                    }
                }

                @Override
                public void afterTextChanged(Editable editable) {
                }
            });
            mytext.setFilters(new InputFilter[]{new InputFilter.LengthFilter(Default_Msg_Limit)});


            msgbtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    myMessageList.clear();
                    String messages = mytext.getText().toString().trim();

                    String id = d.push().getKey();
                    String id2=df.push().getKey();

                    Message message = new Message(id,messages,name2,"Anonymus");
                    d.child(id).setValue(message);
                    df.child(id2).setValue(message);
                    mytext.setText("");
                }
    });

    }

    @Override
    protected void onStart() {
        super.onStart();
        if(a==null){
            ValueEventListener a= d.addValueEventListener(new ValueEventListener() {
                @Override

                public void onDataChange(DataSnapshot dataSnapshot) {
                    for (DataSnapshot mysnapshot : dataSnapshot.getChildren()) {

                        Message message = mysnapshot.getValue(Message.class);

                        myMessageList.add(message);
                    }
                    adapterMessage.notifyDataSetChanged();
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        }
    }
}
