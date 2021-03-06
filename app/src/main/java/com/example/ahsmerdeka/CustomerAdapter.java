package com.example.ahsmerdeka;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.button.MaterialButton;

import java.util.ArrayList;

public class CustomerAdapter extends RecyclerView.Adapter<CustomerAdapter.ViewHolder> {

    private Context mContext;
    private int mResource;
    private ArrayList<Customer> mCustomers;
    private onButtonItemListener MonButtonItemListener;

    public CustomerAdapter(Context context, int resource, ArrayList<Customer> customers, onButtonItemListener onButtonItemListener) {

        mContext = context;
        mResource = resource;
        mCustomers = customers;
        MonButtonItemListener = onButtonItemListener;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(mContext);
        View view = layoutInflater.inflate(mResource, parent, false);

        ViewHolder viewHolder = new ViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        Customer customer = mCustomers.get(position);

        holder.tvName.setText(customer.getName());
        holder.tvAddress.setText(customer.getAddress());
        holder.tvPhone.setText(customer.getPhone());

        holder.buttonEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MonButtonItemListener.onEdit(holder.getAdapterPosition());
            }
        });
        holder.buttonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MonButtonItemListener.onDelete(holder.getAdapterPosition());
            }
        });
    }

    @Override
    public int getItemCount() {
        return mCustomers.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView tvName;
        TextView tvAddress;
        TextView tvPhone;
        MaterialButton buttonEdit;
        MaterialButton buttonDelete;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tvName = itemView.findViewById(R.id.tvName);
            tvAddress = itemView.findViewById(R.id.tvAddress);
            tvPhone = itemView.findViewById(R.id.tvPhone);
            buttonEdit = itemView.findViewById(R.id.buttonEdit);
            buttonDelete = itemView.findViewById(R.id.buttonDelete);

        }
    }

    public interface onButtonItemListener {
        void onEdit (int position);
        void onDelete (int position);
    }
}
