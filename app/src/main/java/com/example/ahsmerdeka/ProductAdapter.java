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

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ViewHolder> {

    Context mContext;
    int mResouce;
    ArrayList<Product> mProducts;
    OnProductChangeListener mOnProductChangeListener;

    public ProductAdapter(Context context, int resouce, ArrayList<Product> products, OnProductChangeListener onProductChangeListener) {
        mContext = context;
        mResouce = resouce;
        mProducts = products;
        mOnProductChangeListener = onProductChangeListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(mContext);
        View view = layoutInflater.inflate(mResouce, parent, false);

        ViewHolder viewHolder = new ViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {

        Product product = mProducts.get(position);

        holder.tvProductName.setText(product.getProductname());
        holder.tvProductPrice.setText("Rp. " + product.getPrice());

        holder.buttonProductEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mOnProductChangeListener.onProductEdit(holder.getAdapterPosition());
            }
        });

        holder.buttonProductDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mOnProductChangeListener.onProductDelete(holder.getAdapterPosition());
            }
        });

    }

    @Override
    public int getItemCount() {
        return mProducts.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvProductName;
        TextView tvProductPrice;
        MaterialButton buttonProductEdit;
        MaterialButton buttonProductDelete;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tvProductName = itemView.findViewById(R.id.tvProductName);
            tvProductPrice = itemView.findViewById(R.id.tvPrice);
            buttonProductEdit = itemView.findViewById(R.id.buttonProductEdit);
            buttonProductDelete = itemView.findViewById(R.id.buttonProductDelete);

        }
    }

    public interface OnProductChangeListener {
        void onProductEdit (int position);
        void onProductDelete (int position);
    }
}
