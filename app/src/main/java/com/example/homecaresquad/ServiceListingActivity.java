//creating a package called homecaresquad
package com.example.homecaresquad;

//importing required functions
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

//creating ServiceListingActivity
public class ServiceListingActivity extends AppCompatActivity {
    //declaring required variables
    private RecyclerView recyclerView;
    private ServiceAdapter adapter;
    private List<Service> services;

    //oncreate method to initailize tasks when the activity is created
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_listing);

        recyclerView = findViewById(R.id.recycler_view);
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
        adapter = new ServiceAdapter(this, services);

        //setting adapter to RecyclerView
        recyclerView.setAdapter(adapter);

        //setting item click listener for the adapter
        adapter.setOnItemClickListener(new ServiceAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                //getting the clicked service
                Service service = services.get(position);

                //navigating to ServiceDetailsActivity with service details
                Intent intent = new Intent(ServiceListingActivity.this, ServiceDetailsActivity.class);
                intent.putExtra("serviceName", service.getName());
                intent.putExtra("basicCost", service.getBasicCost());
                intent.putExtra("standardCost", service.getStandardCost());
                intent.putExtra("premiumCost", service.getPremiumCost());
                startActivity(intent);
            }
        });

    }
}
