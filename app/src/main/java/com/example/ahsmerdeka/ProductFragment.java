package com.example.ahsmerdeka;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ProductFragment extends Fragment {

    private RecyclerView recyclerView;
    private ArrayList<Product> productArrayList;
    private ProductAdapter productAdapter;
    private TextView empty;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        getActivity().setTitle(getResources().getString(R.string.product));

        View view = inflater.inflate(R.layout.product_fragment, container, false);

        recyclerView = view.findViewById(R.id.productRecyclerView);
        empty = view.findViewById(R.id.tvProductEmpty);

        final AHSDBHelper ahsdbHelper = new AHSDBHelper(getActivity());

        productArrayList = ahsdbHelper.getProducts();

        productAdapter = new ProductAdapter(getActivity(), R.layout.item_product, productArrayList, new ProductAdapter.OnProductChangeListener() {
            @Override
            public void onProductEdit(int position) {
                Product product = productArrayList.get(position);

                AddProductFragment addProductFragment = AddProductFragment.newInstance(product.getProduct_id());

                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, addProductFragment).commit();
            }

            @Override
            public void onProductDelete(final int position) {
                Product product = productArrayList.get(position);

                AHSDBHelper ahsdbHelper1 = new AHSDBHelper(getActivity());

                ahsdbHelper1.deleteProduct(product.getProduct_id());

                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        productArrayList.remove(position);
                        productAdapter.notifyDataSetChanged();
                        itemEmpty();
                    }
                });
            }
        });

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(productAdapter);

        itemEmpty();

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.menu_product_add, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }



    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, AddProductFragment.newInstance(0)).commit();
        return super.onOptionsItemSelected(item);
    }

    private void itemEmpty() {
        if (productArrayList.size() > 0) {
            recyclerView.setVisibility(View.VISIBLE);
            empty.setVisibility(View.INVISIBLE);
        } else {
            recyclerView.setVisibility(View.INVISIBLE);
            empty.setVisibility(View.VISIBLE);
        }
    }
}
