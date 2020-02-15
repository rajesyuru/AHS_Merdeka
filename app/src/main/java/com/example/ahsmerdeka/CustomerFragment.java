package com.example.ahsmerdeka;

import android.os.Bundle;
import android.util.Log;
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

public class CustomerFragment extends Fragment {
    private static final String TAG = "###";

    private RecyclerView recyclerView;
    private CustomerAdapter customerAdapter;
    private ArrayList<Customer> customerArrayList;
    private TextView empty;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        getActivity().setTitle("Customers");

        View view = inflater.inflate(R.layout.customer_fragment, container, false);

        recyclerView = view.findViewById(R.id.recyclerView);
        empty = view.findViewById(R.id.customersEmpty);

        final AHSDBHelper ahsdbHelper = new AHSDBHelper(getActivity());
        customerArrayList = ahsdbHelper.getCustomers();

        customerAdapter = new CustomerAdapter(getActivity(), R.layout.item_customer, customerArrayList, new CustomerAdapter.onButtonItemListener() {
            @Override
            public void onEdit(int position) {
                Customer customer = customerArrayList.get(position);

                AddCustomerFragment addCustomerFragment = AddCustomerFragment.newInstance(customer.getId());



                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, addCustomerFragment).commit();

            }

            @Override
            public void onDelete(final int position) {
                Customer customer = customerArrayList.get(position);

                AHSDBHelper ahsdbHelper1 = new AHSDBHelper(getActivity());

                ahsdbHelper1.deleteCustomer(customer.getId());

                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        customerArrayList.remove(position);
                        customerAdapter.notifyDataSetChanged();
                    }
                });

            }
        });
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(customerAdapter);

        if (customerArrayList.size() == 0) {
            recyclerView.setVisibility(View.INVISIBLE);
            empty.setVisibility(View.VISIBLE);
        } else {
            recyclerView.setVisibility(View.VISIBLE);
            empty.setVisibility(View.INVISIBLE);
        }

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.menu_customer_add, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, AddCustomerFragment.newInstance(0)).commit();
        return super.onOptionsItemSelected(item);
    }
}
