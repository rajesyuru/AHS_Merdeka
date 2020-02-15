package com.example.ahsmerdeka;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;

public class CustomerFragment extends Fragment {
    private static final String TAG = "###";
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        getActivity().setTitle("Customers");
        return inflater.inflate(R.layout.customer_fragment, container, false);
    }

    @Override
    public void onResume() {
        super.onResume();

        Log.d(TAG, "onResume: ");

        AHSDBHelper ahsdbHelper = new AHSDBHelper(getActivity());
        ArrayList<Customer> customers = ahsdbHelper.getCustomers();

        for (int i = 0; i < customers.size(); i++ ) {
            Log.d(TAG, customers.get(i).getName() + ", " +
                    customers.get(i).getAddress() + ", " +
                    customers.get(i).getPhone());

        }
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
        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, new AddCustomerFragment()).commit();
        return super.onOptionsItemSelected(item);
    }
}
