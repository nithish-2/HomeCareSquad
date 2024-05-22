//creating a package called homecaresquad
package com.example.homecaresquad;

//importing required functions
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

//creating an adapter for service listing
public class ServiceAdapter extends RecyclerView.Adapter<ServiceAdapter.ViewHolder> {

    //declaring required variables
    private List<Service> services;
    private Context context;
    private OnItemClickListener listener;

    //constructor
    public ServiceAdapter(Context context, List<Service> services) {
        this.context = context;
        this.services = services;
    }

    //interface for item click listener
    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    //setter method for the listener
    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    //view holder to hold the item_service view
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_service, parent, false);
        return new ViewHolder(view);
    }

    //holder to bind the services
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Service service = services.get(position);
        holder.bind(service);

        //setting onclick listener for the item view
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onItemClick(position);
                    Log.d("ServiceDetailsAdapter", "Clicked service: " + service.getName());

                }
            }
        });
    }

    //method to get the item count
    @Override
    public int getItemCount() {
        if (services != null) {
            return services.size();
        } else {
            return 0;
        }
    }

    //static method to hold the recycler view
    public static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView textView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.service_name);
        }
        public void bind(Service service) {
            textView.setText(service.getName());
        }
    }

}
