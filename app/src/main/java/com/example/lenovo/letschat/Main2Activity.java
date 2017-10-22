package com.example.lenovo.letschat;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Main2Activity extends AppCompatActivity {
    FirebaseDatabase f;
    EditText status;
    DatabaseReference dfc;
    Button savestatus;
    String names;
    String af;
    String uid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        savestatus=(Button)findViewById(R.id.savestatus);
        status=(EditText)findViewById(R.id.status);
        Intent i=getIntent();
         names=i.getStringExtra("user");
        uid=i.getStringExtra("ids");
        f=FirebaseDatabase.getInstance();

        dfc=f.getReference("Status");
        savestatus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                af=status.getText().toString();
                String id=dfc.child(names).push().getKey();
                dfc.child(uid).child("stts").setValue(af);
            }
        });

    }
}
