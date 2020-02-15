package com.example.ahsmerdeka;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class AddCustomerFragment extends Fragment {
    private static String KEY_ID = "id";

    private int id;

    TextInputEditText etName;
    TextInputEditText etAddress;
    TextInputEditText etPhone;
    MaterialButton buttonSave;
    TextInputLayout noName;
    TextInputLayout noAddress;
    TextInputLayout noPhone;

    public static AddCustomerFragment newInstance(int id) {
        AddCustomerFragment fragment = new AddCustomerFragment();

        Bundle bundle = new Bundle();
        bundle.putInt(KEY_ID, id);

        fragment.setArguments(bundle);

        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        getActivity().setTitle(getResources().getString(R.string.add_customer));

        View view = inflater.inflate(R.layout.add_customer_fragment, container, false);

        etName = view.findViewById(R.id.etName);
        etAddress = view.findViewById(R.id.etAddress);
        etPhone = view.findViewById(R.id.etPhone);
        buttonSave = view.findViewById(R.id.buttonSave);
        noName = view.findViewById(R.id.noName);
        noAddress = view.findViewById(R.id.noAddress);
        noPhone = view.findViewById(R.id.noPhone);

        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (etName.getText().length() > 0 && etAddress.getText().length() > 0 && etPhone.getText().length() > 0) {
                    AHSDBHelper ahsdbHelper = new AHSDBHelper(getActivity());

                    Customer customer = new Customer();
                    customer.setId(id);
                    customer.setName(etName.getText().toString());
                    customer.setAddress(etAddress.getText().toString());
                    customer.setPhone(etPhone.getText().toString());

                    if (id > 0) {
                        ahsdbHelper.updateCustomer(customer);
                    } else {
                        ahsdbHelper.addCustomer(customer);
                    }

                    getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, new CustomerFragment()).commit();

                } else if (etName.getText().length() == 0) {

                    noName.setErrorEnabled(true);
                    noName.setError(getResources().getString(R.string.not_filled_error));

                } else if (etAddress.getText().length() == 0) {

                    noAddress.setErrorEnabled(true);
                    noAddress.setError(getResources().getString(R.string.not_filled_error));

                } else if (etPhone.getText().length() == 0) {

                    noPhone.setErrorEnabled(true);
                    noPhone.setError(getResources().getString(R.string.not_filled_error));

                } else {

                    noName.setErrorEnabled(true);
                    noName.setError(getResources().getString(R.string.not_filled_error));

                    noAddress.setErrorEnabled(true);
                    noAddress.setError(getResources().getString(R.string.not_filled_error));

                    noPhone.setErrorEnabled(true);
                    noPhone.setError(getResources().getString(R.string.not_filled_error));
                }
            }
        });

        return view ;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        Bundle bundle = getArguments();

        if (bundle != null) {
            id = bundle.getInt(KEY_ID);

            if (id > 0) {
                AHSDBHelper ahsdbHelper = new AHSDBHelper(getActivity());

                Customer customer = ahsdbHelper.getCustomer(id);

                etName.setText(customer.getName());
                etAddress.setText(customer.getAddress());
                etPhone.setText(customer.getPhone());

                getActivity().setTitle(getResources().getString(R.string.edit_customer));
            }
        }

        super.onViewCreated(view, savedInstanceState);
    }
}
