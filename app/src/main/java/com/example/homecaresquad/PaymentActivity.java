//creating a package called homecaresquad
package com.example.homecaresquad;

//importing required functions
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Calendar;

//creating PaymentActivity
public class PaymentActivity extends AppCompatActivity {

    //declaring required variables
    private TextView confirmationMessageTextView;
    private Button confirmAppointmentButton;
    private ProgressBar circularProgressView;
    private EditText cardNumberField;
    private EditText monthField;
    private EditText yearField;
    private EditText cvvField;
    private EditText cardHolderNameField;
    private boolean isAppointmentConfirmed = false;
    private SharedPreferences sharedPreferences;
    private static final String PREFS_NAME = "MyPrefs";
    private static final String KEY_SELECTED_DATE = "selectedDate";
    private static final String KEY_SELECTED_TIME = "selectedTime";
    private String selectedDate;
    private String selectedTime;

    //oncreate method to initailize tasks when the activity is created
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);

        //initializing editText fields
        confirmationMessageTextView = findViewById(R.id.confirmationMessageTextView);
        confirmAppointmentButton = findViewById(R.id.confirmAppointmentButton);
        circularProgressView = findViewById(R.id.circularProgressView);

        //initializing editText fields
        cardNumberField = findViewById(R.id.cardNumberField);
        monthField = findViewById(R.id.monthField);
        yearField = findViewById(R.id.yearField);
        cvvField = findViewById(R.id.cvvField);
        cardHolderNameField = findViewById(R.id.cardHolderNameField);

        //initializing SharedPreferences
        sharedPreferences = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);

        //retrieving selectedDate and selectedTime from SharedPreferences
        selectedDate = sharedPreferences.getString(KEY_SELECTED_DATE, "");
        selectedTime = sharedPreferences.getString(KEY_SELECTED_TIME, "");

        //setting Onclick listener for confirm appointment button
        confirmAppointmentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //form validations
                if (validateForm()) {
                    //circular progress view
                    showCircularProgress(true);

                    //appointment booking process
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            //confirmation message and proceed to next activity
                            String message = "Your appointment has been booked.\nDate: " + selectedDate + "\nTime: " + selectedTime;
                            showConfirmationMessage(message);
                            Toast.makeText(PaymentActivity.this, message, Toast.LENGTH_LONG).show();
                            startActivity(new Intent(PaymentActivity.this, ConfirmationActivity.class));
                        }
                    }, 1000);
                }
            }
        });
    }

    //method for form validation
    private boolean validateForm() {
        //retrieving input values from EditText fields
        String cardNumber = cardNumberField.getText().toString().trim();
        String month = monthField.getText().toString().trim();
        String year = yearField.getText().toString().trim();
        String cvv = cvvField.getText().toString().trim();
        String cardHolderName = cardHolderNameField.getText().toString().trim();

        //validating card number format: XXXXXXXXXXXXXXXX
        if (!cardNumber.matches("\\d{16}")) {
            cardNumberField.setError("Invalid card number format");
            return false;
        }

        //validating month format: XX (it should be 1 to 12)
        int inputMonth = Integer.parseInt(month);
        if (inputMonth < 1 || inputMonth > 12) {
            monthField.setError("Invalid month format");
            return false;
        }

        //validating year format: XX (should be greater than 23)
        int inputYear = Integer.parseInt(year);
        if (inputYear <= 23) {
            yearField.setError("Invalid year format");
            return false;
        }

        //validating expiry date
        if ((inputMonth == 4 && inputYear == 24) || (inputMonth == 3 && inputYear == 24) || (inputMonth == 2 && inputYear == 24)|| (inputMonth == 1 && inputYear == 24)) {
            monthField.setError("Invalid month-year combination");
            yearField.setError("Invalid month-year combination");
            return false;
        }

        //current date and time
        Calendar currentDate = Calendar.getInstance();
        currentDate.add(Calendar.DAY_OF_MONTH, 1);

        int currentMonth = currentDate.get(Calendar.MONTH) + 1;
        int currentYear = currentDate.get(Calendar.YEAR) % 100;

        //validating expiry date: should not be earlier than tomorrow
        if (inputYear < currentYear || (inputYear == currentYear && inputMonth < currentMonth)) {
            Toast.makeText(PaymentActivity.this, "Expiry date cannot be earlier than tomorrow", Toast.LENGTH_SHORT).show();
            return false;
        }

        //validating CVV format: XXX
        if (!cvv.matches("\\d{3}")) {
            cvvField.setError("Invalid CVV format");
            return false;
        }

        //validating cardholder name: normal text
        if (cardHolderName.isEmpty()) {
            cardHolderNameField.setError("Cardholder name is required");
            return false;
        }
        return true;
    }


    //method to show/hide circular progress view
    private void showCircularProgress(boolean show) {
        if (show) {
            circularProgressView.setVisibility(View.VISIBLE);
        } else {
            circularProgressView.setVisibility(View.GONE);
        }
    }

    //method to show confirmation message with date and time
    private void showConfirmationMessage(String message) {
        confirmationMessageTextView.setText(message);
        isAppointmentConfirmed = true;
        showCircularProgress(false);
    }

    //method to save instance state
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean("isAppointmentConfirmed", isAppointmentConfirmed);
    }

    //method to restore instance state
    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        isAppointmentConfirmed = savedInstanceState.getBoolean("isAppointmentConfirmed");
        if (isAppointmentConfirmed) {
            //confirmation message if appointment is already confirmed
            String message = "Your appointment has been booked.\nDate: " + selectedDate + "\nTime: " + selectedTime;
            showConfirmationMessage(message);
        }
    }
}
