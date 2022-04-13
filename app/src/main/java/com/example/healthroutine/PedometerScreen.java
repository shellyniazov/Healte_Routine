package com.example.healthroutine;

import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

//Pedometer screen
public class PedometerScreen extends AppCompatActivity implements SensorEventListener {

    private TextView mStepsSinceReboot;//show the steps with string
    private SensorManager mSensorManager;//Sensor activation
    private boolean isSensorPresent = false;//Sensor boot without power on
    private long steps = 0;

    FirebaseDatabase myDB;
    DatabaseReference myDBRef;
    //long lastTime;
    String name = "";
    TextView n;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pedometer_screen);


        myDB = FirebaseDatabase.getInstance();
        myDBRef = myDB.getReference("users");

        mStepsSinceReboot = (TextView) findViewById(R.id.countSteps);
        mSensorManager = (SensorManager) this.getSystemService(Context.SENSOR_SERVICE);//Activating the step sensor


        //lastTime = System.currentTimeMillis();

        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        if (extras != null) {
            name = extras.getString("name");
        }
    }



    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }


    @Override
    protected void onResume() {
        super.onResume();
        isSensorPresent = true;//The step sensor is turned on
        Sensor countSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_STEP_DETECTOR);//type of sensor
        Log.d("Shelly", "counter sensor =" + countSensor);
        if(countSensor != null){//if sensor exist do his actions.
            mSensorManager.registerListener(this, countSensor,mSensorManager.SENSOR_DELAY_UI);
        } else {
            Toast.makeText(this, "Sensor Not Found!", Toast.LENGTH_SHORT).show();
        }
    }

    //Stops the step sensor
    @Override
    protected void onPause() {
        super.onPause();
        isSensorPresent = false;
    }


    @Override
    public void onSensorChanged(SensorEvent event) {
        //Toast.makeText(this, "counter sensor=" + steps, Toast.LENGTH_SHORT).show();
        Log.d("shelly15","on sensor change:");
        if(isSensorPresent)
        {
            steps++;
              //long currTime = System.currentTimeMillis();
              //if (currTime - lastTime > 60000){
                 updateSteps(name);
              //   lastTime = currTime;
           //}

            mStepsSinceReboot.setText(steps+"");//show the steps on screen
        }
        Log.d("Shelly11", "counter sensor =" + steps);
    }






    //update steps to user if he exist
    private void updateSteps(String Username) {
        Log.d("shelly15","update steps:"+Username);
        myDBRef.orderByChild("username").equalTo(Username).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.hasChildren()) {
                    for (DataSnapshot data : dataSnapshot.getChildren()) {
                        String key = data.getKey();
                        String userName = (String) data.child("username").getValue();

                        myDBRef.child(key).child("steps").setValue(steps);
                        Log.d("shelly","user exists:"+Username);

                    }
                } else {
                    Log.d("shelly17","user does not exists:"+Username);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}
