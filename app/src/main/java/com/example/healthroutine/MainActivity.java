package com.example.healthroutine;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

//Home page- add users
public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener , View.OnClickListener {

    EditText userName;
    EditText phone;
    ImageView homeBtn;
    ArrayList<users> users;
    ArrayList<String> keys;
    FirebaseDatabase myDB;
    DatabaseReference myDBRef;
    ArrayAdapter<users> adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        userName = (EditText) findViewById(R.id.InputName);
        phone = (EditText) findViewById(R.id.inputPhone);
        homeBtn = (ImageView) findViewById(R.id.start);
        users = new ArrayList();
        keys = new ArrayList();
        homeBtn.setOnClickListener(this);
        myDB = FirebaseDatabase.getInstance();//create tree of users
        myDBRef = myDB.getReference("users");
        adapter = new ArrayAdapter<users>(this,
                android.R.layout.simple_list_item_1,users);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.start:
                addUser();//add useres deatails to firebase by button
                break;
        }
    }





    public void addUser() {
        Log.d("shelly","add");
        String Name = userName.getText().toString();
        String Phone = phone.getText().toString();


        if (userName.equals("") || phone.equals("")) {
            Log.d("shelly", "missing name or phone");
            return;
        }

        users Users = new users(Name,Phone,0,0);//ctor
        Log.d("shelly","name="+Name+" phone="+Phone);
        String key = myDBRef.push().getKey();//create key
        myDBRef.child(key).setValue(Users);//add users deatails to key: name,phone,steps,water..


        Intent intent = new Intent(this, MenuScreen.class);//move to menu screen
        intent.putExtra("name",Name);//transfer user name to other screen
        startActivity(intent);
        finish();
    }







    private void addChildListener() {
        Log.d("shelly","add");
        ChildEventListener childListener = new ChildEventListener() {

            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Log.d("shelly_artium","onChildAdded");

                //create titles of firebase: key,name,phone..
                String key = dataSnapshot.getKey();
                String name = (String)dataSnapshot.child("name").getValue();
                String phone = (String)dataSnapshot.child("phone").getValue();
                long steps = (long)dataSnapshot.child("steps").getValue();
                long countCups = (long)dataSnapshot.child("countCups").getValue();

                Log.d("shelly_artium","name="+name);
                users user = new users(name,phone,steps,countCups);
                Log.d("shelly_artium",users.toString());
                users.add(user);
                keys.add(key);
                adapter.notifyDataSetChanged();//add data to firebase
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {
            }

            //option to remove user from firebase
            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                String key = dataSnapshot.getKey();
                int index = keys.indexOf(key);

                if (index != -1) {
                    users.remove(index);
                    keys.remove(index);
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        };
        Log.d("shelly_artium","add child listener");
        myDBRef.addChildEventListener(childListener);//after all- we add the child to the "users" in firebase.
    }



    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }
}