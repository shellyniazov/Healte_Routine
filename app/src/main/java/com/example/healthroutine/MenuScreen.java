package com.example.healthroutine;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

//Menu screen - choose option
public class MenuScreen extends AppCompatActivity implements View.OnClickListener {

    String name = "";
    TextView menuT1;
    ImageView pedometer;
    ImageView water;
    ImageView info;
    ImageView diet;
    ImageView bmi;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu_screen);

        pedometer = (ImageView) findViewById(R.id.Pedometer);
        water = (ImageView) findViewById(R.id.Water);
        info = (ImageView) findViewById(R.id.Info);
        diet = (ImageView) findViewById(R.id.Diet);
        bmi = (ImageView) findViewById(R.id.BMI);
        menuT1 = (TextView) findViewById(R.id.menutitle);
        menuT1.setText(getIntent().getExtras().getString("name"));//get the intent from home screen and show the name on screen (string)

        pedometer.setOnClickListener(this);
        water.setOnClickListener(this);
        info.setOnClickListener(this);
        diet.setOnClickListener(this);
        bmi.setOnClickListener(this);

        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        if(extras != null){
            name = extras.getString(  "name");//Keep the name we got inside a variable
        }
        else
            name = "UnKnown";
            menuT1.setText(name);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.Pedometer:
                Intent intent = new Intent(this, PedometerScreen.class);
                intent.putExtra("name",name);
                startActivity(intent);
                break;
            case R.id.Water:
                Intent intent1 = new Intent(this, CountWaterScreen.class);
                intent1.putExtra("name",name);
                startActivity(intent1);
                break;
            case R.id.Info:
                Intent intent2 = new Intent(this, InfoScreen.class);
                intent2.putExtra("name",name);
                startActivity(intent2);
                break;
            case R.id.Diet:
                Intent intent3 = new Intent(this, DietMenuScreen.class);
                intent3.putExtra("name",name);
                startActivity(intent3);
                break;
            case R.id.BMI:
                Intent intent4 = new Intent(this, BMIScreen.class);
                intent4.putExtra("name",name);
                startActivity(intent4);
                break;
        }
    }
}