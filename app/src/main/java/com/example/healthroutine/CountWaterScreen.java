package com.example.healthroutine;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

//Count water screen
public class CountWaterScreen extends AppCompatActivity implements View.OnClickListener {

    private ImageView img1, img2, img3, img4, img5, img6, img7, img8;
    private View lastview = null;
    private long counterCups = 0;
    private Button countWater;
    FirebaseDatabase myDB;
    DatabaseReference myDBRef;
    String name = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.count_water_screen);

        myDB = FirebaseDatabase.getInstance();
        myDBRef = myDB.getReference("users");

        countWater = (Button) findViewById(R.id.countWaterBtn);
        countWater.setOnClickListener(this);

        img1 = (ImageView) findViewById(R.id.img1);
        img1.setOnClickListener(this);
        img2 = (ImageView) findViewById(R.id.img2);
        img2.setOnClickListener(this);
        img3 = (ImageView) findViewById(R.id.img3);
        img3.setOnClickListener(this);
        img4 = (ImageView) findViewById(R.id.img4);
        img4.setOnClickListener(this);
        img5 = (ImageView) findViewById(R.id.img5);
        img5.setOnClickListener(this);
        img6 = (ImageView) findViewById(R.id.img6);
        img6.setOnClickListener(this);
        img7 = (ImageView) findViewById(R.id.img7);
        img7.setOnClickListener(this);
        img8 = (ImageView) findViewById(R.id.img8);
        img8.setOnClickListener(this);


        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        if (extras != null) {
            name = extras.getString("name");
        }
    }


    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.countWaterBtn:
                Log.d("shelly","cups:" + counterCups);
                updateCups(name);//when the user click on the button, the count of water cups is Updated by user name
                break;

            case R.id.img1:
                ImageView tv = (ImageView) img1;
                tv.setImageResource(R.drawable.empty);//when we click on cup image it means the cup is empty now.
                lastview = img1;
                counterCups++;
                break;


            case R.id.img2:
                ImageView tv1 = (ImageView) img2;
                tv1.setImageResource(R.drawable.empty);
                lastview = img1;
                counterCups++;
                break;


            case R.id.img3:
                ImageView tv2 = (ImageView) img3;
                tv2.setImageResource(R.drawable.empty);
                lastview = img3;
                counterCups++;
                break;

            case R.id.img4:
                ImageView tv3 = (ImageView) img4;
                tv3.setImageResource(R.drawable.empty);
                lastview = img4;
                counterCups++;
                break;

            case R.id.img5:
                ImageView tv4 = (ImageView) img5;
                tv4.setImageResource(R.drawable.empty);
                lastview = img5;
                counterCups++;
                break;

            case R.id.img6:
                ImageView tv5 = (ImageView) img6;
                tv5.setImageResource(R.drawable.empty);
                lastview = img6;
                counterCups++;
                break;

            case R.id.img7:
                ImageView tv6 = (ImageView) img7;
                tv6.setImageResource(R.drawable.empty);
                lastview = img7;
                counterCups++;
                break;

            case R.id.img8:
                ImageView tv7 = (ImageView) img8;
                tv7.setImageResource(R.drawable.empty);
                lastview = img8;
                counterCups++;
                break;
        }
    }




        //update count of cups if user exist.
        private void updateCups(String Username) {

            myDBRef.orderByChild("username").equalTo(Username).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if (dataSnapshot.hasChildren()) {
                        for (DataSnapshot data : dataSnapshot.getChildren()) {
                            String key = data.getKey();
                            String userName = (String) data.child("username").getValue();

                            myDBRef.child(key).child("countCups").setValue(counterCups);
                            Log.d("shelly","user exists:"+Username+"cups:"+counterCups);

                        }
                    } else {
                        Log.d("shelly","user does not exists:"+Username);
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }
    }


