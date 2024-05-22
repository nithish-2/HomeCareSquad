//creating a package called homecaresquad
package com.example.homecaresquad;

//importing required variables
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import java.util.regex.Pattern;

//creating SignUpActivity
public class SignUpActivity extends AppCompatActivity {

    //declaring required variables
    private EditText emailField;
    private EditText nameField;
    private EditText passwordField;
    private EditText confirmPasswordField;
    private static final String PREFS_NAME = "UserPrefs";

    //oncreate method to initailize tasks when the activity is created
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        //initializing fields
        emailField = findViewById(R.id.emailField);
        nameField = findViewById(R.id.nameField);
        passwordField = findViewById(R.id.passwordField);
        confirmPasswordField = findViewById(R.id.confirmPasswordField);

        //submit button functionality
        Button submitBtn = findViewById(R.id.submitButton2);
        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validateFields()) {
                    saveCredentials();
                    Toast.makeText(SignUpActivity.this, "Account created successfully", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(SignUpActivity.this, ServiceListingActivity.class);
                    startActivity(intent);
                }
            }
        });

        //loginHereTextView functionality
        TextView loginHereTextView = findViewById(R.id.loginHereTextView);
        loginHereTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignUpActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });
    }

    //method for validating all fields
    private boolean validateFields() {
        String email = emailField.getText().toString().trim();
        String name = nameField.getText().toString().trim();
        String password = passwordField.getText().toString().trim();
        String confirmPassword = confirmPasswordField.getText().toString().trim();

        //email validation
        if (email.isEmpty()) {
            emailField.setError("Email is required");
            return false;
        }

        if (!isValidEmail(email)) {
            emailField.setError("Invalid email format");
            return false;
        }

        //name validation
        if (name.isEmpty()) {
            nameField.setError("Name is required");
            return false;
        }

        //password and confirm password validation
        if (password.isEmpty()) {
            passwordField.setError("Password is required");
            return false;
        }

        if (!password.equals(confirmPassword)) {
            confirmPasswordField.setError("Passwords do not match");
            return false;
        }

        return true;
    }

    //method to save the credentials
    private void saveCredentials() {
        String email = emailField.getText().toString().trim();
        String password = passwordField.getText().toString().trim();

        SharedPreferences.Editor editor = getSharedPreferences(PREFS_NAME, MODE_PRIVATE).edit();
        editor.putString("email", email);
        editor.putString("password", password);
        editor.apply();
    }

    //method to check the pattern of the email
    private boolean isValidEmail(String email) {
        Pattern pattern = Patterns.EMAIL_ADDRESS;
        return pattern.matcher(email).matches();
    }
}
