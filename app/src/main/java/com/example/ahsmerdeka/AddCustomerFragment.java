package com.example.ahsmerdeka;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

public class AddCustomerFragment extends Fragment {
    TextInputEditText etName;
    TextInputEditText etAddress;
    TextInputEditText etPhone;
    MaterialButton buttonSave;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        getActivity().setTitle("Add Customer");

        View view = inflater.inflate(R.layout.add_customer_fragment, container, false);

        etName = view.findViewById(R.id.etName);
        etAddress = view.findViewById(R.id.etAddress);
        etPhone = view.findViewById(R.id.etPhone);
        buttonSave = view.findViewById(R.id.buttonSave);

        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AHSDBHelper ahsdbHelper = new AHSDBHelper(getActivity());

                Customer customer = new Customer();
                customer.setName(etName.getText().toString());
                customer.setAddress(etAddress.getText().toString());
                customer.setPhone(etPhone.getText().toString());

                ahsdbHelper.addCustomer(customer);

                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, new CustomerFragment()).commit();
            }
        });

        return view ;
    }
}
