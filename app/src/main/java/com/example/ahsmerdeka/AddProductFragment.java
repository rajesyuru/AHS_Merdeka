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
import com.google.android.material.textfield.TextInputLayout;

public class AddProductFragment extends Fragment {

    private static String KEY_PRODUCT_ID = "idProduct";

    private int id;

    TextInputEditText etProductName;
    TextInputEditText etPrice;
    MaterialButton buttonProductSave;
    TextInputLayout productNameError;
    TextInputLayout priceError;

    public static AddProductFragment newInstance(int id) {
        AddProductFragment fragment = new AddProductFragment();

        Bundle bundle = new Bundle();
        bundle.putInt(KEY_PRODUCT_ID, id);

        fragment.setArguments(bundle);

        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        getActivity().setTitle(getResources().getString(R.string.add_product));

        View view = inflater.inflate(R.layout.add_product_fragment, container, false);

        etProductName = view.findViewById(R.id.etProductName);
        etPrice = view.findViewById(R.id.etPrice);
        buttonProductSave = view.findViewById(R.id.buttonSave);
        productNameError = view.findViewById(R.id.productNameError);
        priceError = view.findViewById(R.id.priceError);

        buttonProductSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (etProductName.getText().length() > 0 && etPrice.getText().length() > 0) {
                    AHSDBHelper ahsdbHelper = new AHSDBHelper(getActivity());

                    Product product = new Product();
                    product.setProduct_id(id);
                    product.setProductname(etProductName.getText().toString());
                    product.setPrice(etPrice.getText().toString());

                    if (id > 0) {
                        ahsdbHelper.updateProduct(product);
                    } else {
                        ahsdbHelper.addProduct(product);
                    }

                    getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, new ProductFragment()).commit();
                } else if (etProductName.getText().length() == 0) {
                    productNameError.setErrorEnabled(true);
                    productNameError.setError(getResources().getString(R.string.not_filled_error));
                } else if (etPrice.getText().length() == 0) {
                    priceError.setErrorEnabled(true);
                    priceError.setError(getResources().getString(R.string.not_filled_error));
                } else {
                    productNameError.setErrorEnabled(true);
                    productNameError.setError(getResources().getString(R.string.not_filled_error));

                    priceError.setErrorEnabled(true);
                    priceError.setError(getResources().getString(R.string.not_filled_error));
                }

            }
        });


        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        Bundle bundle = getArguments();

        if (bundle != null) {
            id = bundle.getInt(KEY_PRODUCT_ID);

            if (id > 0) {
                AHSDBHelper ahsdbHelper = new AHSDBHelper(getActivity());

                Product product = ahsdbHelper.getProduct(id);

                etProductName.setText(product.getProductname());
                etPrice.setText(product.getPrice());

                getActivity().setTitle(getResources().getString(R.string.edit_product));
            }
        }

        super.onViewCreated(view, savedInstanceState);
    }
}
