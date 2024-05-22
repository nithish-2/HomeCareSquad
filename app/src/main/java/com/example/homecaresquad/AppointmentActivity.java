//creating a package called homecaresquad
package com.example.homecaresquad;

//importing required functions
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;

//creating AppointmentActivity
public class AppointmentActivity extends AppCompatActivity {

    //declaring required variables
    private TextView serviceNameTextView;
    private TextView selectedDateTextView;
    private TextView selectedTimeTextView;
    private Button dateButton;
    private Button timeButton;
    private Button submitButton;
    private List<Service> selectedServices = new ArrayList<>();

    private DatePickerDialog.OnDateSetListener dateSetListener;
    private TimePickerDialog.OnTimeSetListener timeSetListener;
    private Calendar calendar;

    //oncreate method to initailize tasks when the activity is created
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appointment);

        //required variables
        serviceNameTextView = findViewById(R.id.serviceNameTextView);
        selectedDateTextView = findViewById(R.id.selectedDateTextView);
        selectedTimeTextView = findViewById(R.id.selectedTimeTextView);
        dateButton = findViewById(R.id.dateButton);
        timeButton = findViewById(R.id.timeButton);
        submitButton = findViewById(R.id.submitAppointmentButton);

        //retrieving selected services from Intent
        String serviceName = getIntent().getStringExtra("serviceName");
        double cost = getIntent().getDoubleExtra("cost", 0.0);
        selectedServices.add(new Service(serviceName, cost, cost, cost));

        //displaying the selected service name
        serviceNameTextView.setText(serviceName);
        calendar = Calendar.getInstance();

        //declaring date picker dialog
        dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                calendar.set(Calendar.YEAR, year);
                calendar.set(Calendar.MONTH, month);
                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateDateLabel();
            }
        };

        //declaring time picker dialog
        timeSetListener = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
                calendar.set(Calendar.MINUTE, minute);
                updateTimeLabel();
            }
        };

        //date button functionality
        dateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //getting current calendar instance
                Calendar currentDate = Calendar.getInstance();

                //setting minimum date to tomorrow (current date + 1)
                currentDate.add(Calendar.DAY_OF_MONTH, 1);

                //checking if current date is within the allowed range
                if (calendar.getTimeInMillis() < currentDate.getTimeInMillis()) {
                    //creating DatePickerDialog with minimum date set
                    DatePickerDialog datePickerDialog = new DatePickerDialog(AppointmentActivity.this, dateSetListener,
                            currentDate.get(Calendar.YEAR),
                            currentDate.get(Calendar.MONTH),
                            currentDate.get(Calendar.DAY_OF_MONTH));

                    //showing the DatePickerDialog
                    datePickerDialog.show();
                } else {
                    //showing error message on selectedDateTextView
                    selectedDateTextView.setError("Please select a date from tomorrow onwards.");
                }
            }
        });

        //time button functionality
        timeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //getting current calendar instance
                Calendar currentTime = Calendar.getInstance();

                //setting the minimum time to 9:00 AM
                currentTime.set(Calendar.HOUR_OF_DAY, 9);
                currentTime.set(Calendar.MINUTE, 0);

                //setting the maximum time to 6:00 PM
                Calendar maxTime = Calendar.getInstance();
                maxTime.set(Calendar.HOUR_OF_DAY, 18);
                maxTime.set(Calendar.MINUTE, 0);

                //checking if current time is within the allowed range
                if (currentTime.getTimeInMillis() < maxTime.getTimeInMillis()) {
                    //creating TimePickerDialog with current time and restricted time range
                    TimePickerDialog timePickerDialog = new TimePickerDialog(AppointmentActivity.this, timeSetListener,
                            currentTime.get(Calendar.HOUR_OF_DAY),
                            currentTime.get(Calendar.MINUTE),
                            false);

                    //setting the range of selectable times
                    timePickerDialog.updateTime(currentTime.get(Calendar.HOUR_OF_DAY), currentTime.get(Calendar.MINUTE));

                    //showing the TimePickerDialog
                    timePickerDialog.show();
                } else {
                    //showing error message on selectedTimeTextView
                    selectedTimeTextView.setError("Sorry, appointment times are available only from 9am until 6pm");
                }
            }
        });

        //submit button functionality
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //getting the selected date and time
                String selectedDate = selectedDateTextView.getText().toString();
                String selectedTime = selectedTimeTextView.getText().toString();

                //saving appointment details in SharedPreferences
                saveAppointment(selectedDate, selectedTime);

                //proceeding to payment
                startActivity(new Intent(AppointmentActivity.this, PaymentActivity.class));
            }
        });


    }

    //method to update date label
    private void updateDateLabel() {
        String dateFormat = "MM/dd/yyyy";
        SimpleDateFormat sdf = new SimpleDateFormat(dateFormat, Locale.US);
        selectedDateTextView.setText(sdf.format(calendar.getTime()));
    }

    //method to update time label
    private void updateTimeLabel() {
        String timeFormat = "hh:mm a";
        SimpleDateFormat sdf = new SimpleDateFormat(timeFormat, Locale.US);
        selectedTimeTextView.setText(sdf.format(calendar.getTime()));
    }

    //method to save appointment
    private void saveAppointment(String selectedDate, String selectedTime) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Set<String> serviceSet = new HashSet<>();
        for (Service service : selectedServices) {
            serviceSet.add(service.getName() + "," + service.getBasicCost() + "," + service.getStandardCost() + "," + service.getPremiumCost());
        }
        editor.putStringSet("selectedServices", serviceSet);
        editor.putString("selectedDate", selectedDate);
        editor.putString("selectedTime", selectedTime);
        editor.apply();
    }
}
