//creating a package called homecaresquad
package com.example.homecaresquad;

//importing required functions
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;

import com.example.homecaresquad.databinding.ActivityMainBinding;

import java.util.ArrayList;
import java.util.Set;

//creating MainActivity
public class MainActivity extends AppCompatActivity {

    //declaring required variables
    private ActivityMainBinding binding;

    //oncreate method to initailize tasks when the activity is created
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        //setting OnClickListener for loginButton
        binding.loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Open LoginActivity when loginButton is clicked
                openLoginPage();
            }
        });

        //declaring OnClickListener for loginButton
        binding.signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Open LoginActivity when loginButton is clicked
                openSignUpPage();
            }
        });
    }

    //method to open login page
    private void openLoginPage() {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }

    //method to open sign up page
    private void openSignUpPage() {
        Intent intent = new Intent(this, SignUpActivity.class);
        startActivity(intent);
    }

}