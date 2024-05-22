//creating a package called homecaresquad
package com.example.homecaresquad;

//importing required functions
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

//creating ServiceDetailsActivity
public class ServiceDetailsAdapter extends RecyclerView.Adapter<ServiceDetailsAdapter.ViewHolder> {
    //declaring required variables
    private List<Service> services;
    private Context context;
    private Service service;

    //constructor
    public ServiceDetailsAdapter(Context context, List<Service> services) {
        this.context = context;
        this.services = services;
    }

    //method to hold the item_service_detail view
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_service_detail, parent, false);
        return new ViewHolder(view);
    }

    //method to bind the service view
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        Service service = services.get(position);
        holder.bind(service);

        //debug statement
        Log.d("ServiceDetailsAdapter", "Bound service: " + service.getName() + " at position: " + position + " with holder: " + holder);

        //setting on click listener for the item
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //getting the clicked service
                Service clickedService = services.get(holder.getAdapterPosition());

                //debug statement
                Log.d("ServiceDetailsAdapter", "Clicked service: " + clickedService.getName() + ", Basic Cost: $" + clickedService.getBasicCost() + " at position: " + holder.getAdapterPosition() + " with holder: " + holder);

                //creating an intent to start the AppointmentActivity
                Intent intent = new Intent(context, AppointmentActivity.class);

                //passing service details to the AppointmentActivity
                intent.putExtra("serviceName", clickedService.getName());
                intent.putExtra("basicCost", clickedService.getBasicCost());
                intent.putExtra("standardCost", clickedService.getStandardCost());
                intent.putExtra("premiumCost", clickedService.getPremiumCost());
                context.startActivity(intent);
            }
        });
}

    //method to get the item count
    @Override
    public int getItemCount() {
        return services.size();
    }

    //method to hold the recycler view
    public static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView serviceNameTextView, basicCostTextView, standardCostTextView, premiumCostTextView;

        //method for holding the view
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            serviceNameTextView = itemView.findViewById(R.id.service_name);
            basicCostTextView = itemView.findViewById(R.id.basic_cost);
            standardCostTextView = itemView.findViewById(R.id.standard_cost);
            premiumCostTextView = itemView.findViewById(R.id.premium_cost);
        }

        //method to bind the service
        public void bind(Service service) {
            serviceNameTextView.setText(service.getName());
            basicCostTextView.setText("Basic Cost: $" + service.getBasicCost());
            standardCostTextView.setText("Standard Cost: $" + service.getStandardCost());
            premiumCostTextView.setText("Premium Cost: $" + service.getPremiumCost());
        }

    }

}
