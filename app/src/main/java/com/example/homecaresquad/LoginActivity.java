//creating a package called homecaresquad
package com.example.homecaresquad;

//importing required functions
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

//creating LoginActivity
public class LoginActivity extends AppCompatActivity {

    //declaring required variables
    private EditText emailField;
    private EditText passwordField;

    //oncreate method to initailize tasks when the activity is created
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        emailField = findViewById(R.id.emailField);
        passwordField = findViewById(R.id.passwordField);

        //submit button functionality
        Button submitBtn = findViewById(R.id.submitButton);
        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validateFields()) {
                        Intent intent = new Intent(LoginActivity.this, ServiceListingActivity.class);
                        startActivity(intent);
                }
                else {
                        Toast.makeText(LoginActivity.this, "Invalid email or password", Toast.LENGTH_SHORT).show();
                    }
            }
        });

        //signUpTextView functionality
        TextView signUpTextView = findViewById(R.id.signUpTextView);
        signUpTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, SignUpActivity.class);
                startActivity(intent);
            }
        });
    }

    //method to validate all fields
    private boolean validateFields() {
        String email = emailField.getText().toString().trim();
        String password = passwordField.getText().toString().trim();

        //email validation
        if (email.isEmpty()) {
            emailField.setError("Email is required");
            return false;
        }

        //password validation
        if (password.isEmpty()) {
            passwordField.setError("Password is required");
            return false;
        }

        //default credentials validation
        if (!email.equals("ADMIN") || !password.equals("PASSWORD")) {
            Toast.makeText(LoginActivity.this, "Invalid email or password", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }

}
