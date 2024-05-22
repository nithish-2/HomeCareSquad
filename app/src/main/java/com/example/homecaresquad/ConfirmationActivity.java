//creating a package called homecaresquad
package com.example.homecaresquad;

//importing required functions
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import java.util.Random;

//creating ConfirmationActivity
public class ConfirmationActivity extends AppCompatActivity {

    //oncreate method to initailize tasks when the activity is created
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirmation);

        //debug statement
        Log.d("ConfirmationActivity", "onCreate()");

        //retrieving appointment details from Intent extras
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            String selectedDate = extras.getString("selectedDate", "");
            String selectedTime = extras.getString("selectedTime", "");

            //debug statement
            Log.d("ConfirmationActivity", "Selected Date: " + selectedDate);
            Log.d("ConfirmationActivity", "Selected Time: " + selectedTime);

            //generating random technician name and mobile number
            String technicianName = generateRandomTechnicianName();
            String technicianMobile = generateRandomMobileNumber();

            //debug statement
            Log.d("ConfirmationActivity", "Technician Name: " + technicianName);
            Log.d("ConfirmationActivity", "Technician Mobile: " + technicianMobile);

            //TextView technicianTextView = findViewById(R.id.technicianTextView);

            //setting technician name and mobile number
            String technicianDetails = "Technician: " + technicianName + " - " + technicianMobile;
            //technicianTextView.setText(technicianDetails);
        }

        //declaring logout button
        Button logoutButton = findViewById(R.id.logoutButton);

        //setting OnClickListener for the logout button
        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //creating an Intent to navigate to MainActivity
                Intent intent = new Intent(ConfirmationActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }


    //method to generate a random technician name
    private String generateRandomTechnicianName() {
        String[] technicianNames = {"Peter Parker", "John Wick", "Edward Stark", "Jake Thompson"};
        Random random = new Random();
        int index = random.nextInt(technicianNames.length);
        return technicianNames[index];
    }

    //method to generate a random mobile number
    private String generateRandomMobileNumber() {
        Random random = new Random();
        int firstPart = random.nextInt(900) + 100;
        int secondPart = random.nextInt(900) + 100;
        int thirdPart = random.nextInt(9000) + 1000;
        return String.format("%d-%d-%04d", firstPart, secondPart, thirdPart);
    }
}
