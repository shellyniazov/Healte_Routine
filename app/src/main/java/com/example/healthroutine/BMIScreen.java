package com.example.healthroutine;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

//Bmi screen
public class BMIScreen extends AppCompatActivity {

    private EditText weightKgEditText, heightCmEditText;
    private Button calculateButton;
    private TextView bmiTextView, categoryTextView;
    private CardView bmiResultCardView;

    private boolean inMetricUnits;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tmp);

        weightKgEditText = findViewById(R.id.activity_main_weightkgs);
        heightCmEditText = findViewById(R.id.activity_main_heightcm);

        calculateButton = findViewById(R.id.activity_main_calculate);

        bmiTextView = findViewById(R.id.activity_main_bmi);//title of result
        categoryTextView = findViewById(R.id.activity_main_category);
        bmiResultCardView = findViewById(R.id.activity_main_resultcard);//results of bmi calc(string)

        inMetricUnits = true;
        bmiResultCardView.setVisibility(View.GONE);//The text of the bmi results is initialized as hidden

        calculateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (inMetricUnits) {
                    if (weightKgEditText.length() == 0 || heightCmEditText.length() == 0) {
                        Toast.makeText(BMIScreen.this, "Populate Weight and Height to Calculate BMI", Toast.LENGTH_SHORT).show();
                    } else {
                        double heightInCms = Double.parseDouble(heightCmEditText.getText().toString());
                        double weightInKgs = Double.parseDouble(weightKgEditText.getText().toString());
                        double bmi = BMICalc.getInstance().calculateBMIMetric(heightInCms, weightInKgs);//Sending height and weight to the function in the bmi calculation department and returning the bmi result.
                        displayBMI(bmi);
                        categoryTextView.setText(BMICalc.getInstance().classifyBMI(bmi));//View bmi description
                    }
                }
            }
        });
    }



    //private void updateInputsVisibility() {
    //    if (inMetricUnits) {
    //        heightCmEditText.setVisibility(View.VISIBLE);
    //        weightKgEditText.setVisibility(View.VISIBLE);
    //    }
    //}

    private void displayBMI(double bmi) {
        bmiResultCardView.setVisibility(View.VISIBLE);//The Bmi results are now visible
        bmiTextView.setText(String.format("%.2f", bmi));
        }
    }




