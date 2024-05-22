//creating a package called homecaresquad
package com.example.homecaresquad;

//importing required functions
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

//creating Service
public class ServiceDetailsActivity extends AppCompatActivity {

    //declaring required variables
    private RecyclerView recyclerView;
    private ServiceDetailsAdapter adapter;
    private List<Service> services;

    //oncreate method to initailize tasks when the activity is created
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_details);

        recyclerView = findViewById(R.id.service_details_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        //initializing services
        services = new ArrayList<>();
        services.add(new Service("Plumbing",  29.99, 39.99, 49.99));
        services.add(new Service("Cleaning",  24.99, 34.99, 44.99));
        services.add(new Service("Repairs",  19.99, 29.99, 39.99));
        services.add(new Service("Gardening",  129.99, 139.99, 149.99));
        services.add(new Service("Home Spa",  49.99, 59.99, 69.99));
        services.add(new Service("Electrical",  39.99, 59.99, 79.99));

        //initializing adapter with context and services
        adapter = new ServiceDetailsAdapter(this, services);

        //setting adapter to RecyclerView
        recyclerView.setAdapter(adapter);
    }
}
