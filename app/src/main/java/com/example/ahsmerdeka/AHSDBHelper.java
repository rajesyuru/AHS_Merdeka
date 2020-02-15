package com.example.ahsmerdeka;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class AHSDBHelper extends SQLiteOpenHelper {
    private static String DB_NAME = "ahs.db";
    private static int DB_VERSION = 1;

    public AHSDBHelper(@Nullable Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String CREATE_TABLE_CUSTOMERS = "CREATE TABLE " + CustomerContract.CustomerTable.TABLE_NAME +
                " (" +
                CustomerContract.CustomerTable.COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                CustomerContract.CustomerTable.COLUMN_NAME + " TEXT, " +
                CustomerContract.CustomerTable.COLUMN_ADDRESS + " TEXT, " +
                CustomerContract.CustomerTable.COLUMN_PHONE + " TEXT" +
                ")";

        String CREATE_TABLE_PRODUCTS = "CREATE TABLE " + ProductContract.ProductTable.TABLE_NAME +
                " (" +
                ProductContract.ProductTable.COLUMN_PRODUCT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                ProductContract.ProductTable.COLUMN_PRODUCT_NAME + " TEXT, " +
                ProductContract.ProductTable.COLUMN_PRICE + " TEXT" +
                ")";

        db.execSQL(CREATE_TABLE_CUSTOMERS);
        db.execSQL(CREATE_TABLE_PRODUCTS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }


    public void addProduct(Product product) {
        SQLiteDatabase db = getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(ProductContract.ProductTable.COLUMN_PRODUCT_NAME, product.getProductname());
        contentValues.put(ProductContract.ProductTable.COLUMN_PRICE, product.getPrice());

        db.insert(ProductContract.ProductTable.TABLE_NAME, null, contentValues);
    }

    public ArrayList<Product> getProducts() {
        ArrayList<Product> products = new ArrayList<>();

        SQLiteDatabase db = getReadableDatabase();
        String[] columns = {
                ProductContract.ProductTable.COLUMN_PRODUCT_ID,
                ProductContract.ProductTable.COLUMN_PRODUCT_NAME,
                ProductContract.ProductTable.COLUMN_PRICE
        };

        String selection = "";

        Cursor cursor = db.query(
                ProductContract.ProductTable.TABLE_NAME,
                columns, selection, null, null, null, null
        );

        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(cursor.getColumnIndex(ProductContract.ProductTable.COLUMN_PRODUCT_ID));
                String productName = cursor.getString(cursor.getColumnIndex(ProductContract.ProductTable.COLUMN_PRODUCT_NAME));
                String price = cursor.getString(cursor.getColumnIndex(ProductContract.ProductTable.COLUMN_PRICE));

                Product product = new Product();
                product.setProduct_id(id);
                product.setProductname(productName);
                product.setPrice(price);

                products.add(product);

            } while (cursor.moveToNext());
        }
        return products;
    }

    public Product getProduct(int id) {

        Product product = null;

        SQLiteDatabase db = getReadableDatabase();

        String[] columns = {
                ProductContract.ProductTable.COLUMN_PRODUCT_ID,
                ProductContract.ProductTable.COLUMN_PRODUCT_NAME,
                ProductContract.ProductTable.COLUMN_PRICE
        };

        String selection = ProductContract.ProductTable.COLUMN_PRODUCT_ID + " = ?";
        String[] selectionArgs = { String.valueOf(id)};

        Cursor cursor = db.query(
                ProductContract.ProductTable.TABLE_NAME,
                columns, selection, selectionArgs, null, null, null
        );

        if (cursor.moveToFirst()) {
            int _id = cursor.getInt(cursor.getColumnIndex(ProductContract.ProductTable.COLUMN_PRODUCT_ID));
            String productName = cursor.getString(cursor.getColumnIndex(ProductContract.ProductTable.COLUMN_PRODUCT_NAME));
            String price = cursor.getString(cursor.getColumnIndex(ProductContract.ProductTable.COLUMN_PRICE));

            product = new Product();
            product.setProduct_id(id);
            product.setProductname(productName);
            product.setPrice(price);
        }

        return product;

    }

    public void updateProduct(Product product) {
        SQLiteDatabase db = getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(ProductContract.ProductTable.COLUMN_PRODUCT_NAME, product.getProductname());
        contentValues.put(ProductContract.ProductTable.COLUMN_PRICE, product.getPrice());

        String[] whereArgs = { String.valueOf(product.getProduct_id())};

        db.update(ProductContract.ProductTable.TABLE_NAME, contentValues, ProductContract.ProductTable.COLUMN_PRODUCT_ID + " = ?", whereArgs);

    }

    public void deleteProduct(int id) {
        SQLiteDatabase db = getWritableDatabase();

        String[] whereArgs = { String.valueOf(id)};

        db.delete(ProductContract.ProductTable.TABLE_NAME, ProductContract.ProductTable.COLUMN_PRODUCT_ID + " = ?", whereArgs);
    }


    public void updateCustomer(Customer customer) {
        SQLiteDatabase db = getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(CustomerContract.CustomerTable.COLUMN_NAME, customer.getName());
        contentValues.put(CustomerContract.CustomerTable.COLUMN_ADDRESS, customer.getAddress());
        contentValues.put(CustomerContract.CustomerTable.COLUMN_PHONE, customer.getPhone());

        String[] whereArgs = { String.valueOf(customer.getId())};

        db.update(CustomerContract.CustomerTable.TABLE_NAME, contentValues, CustomerContract.CustomerTable.COLUMN_ID + " = ?", whereArgs);
    }

    public void deleteCustomer(int id) {
        SQLiteDatabase db = getWritableDatabase();

        String[] whereArgs = { String.valueOf(id)};

        db.delete(CustomerContract.CustomerTable.TABLE_NAME, CustomerContract.CustomerTable.COLUMN_ID + " = ?", whereArgs);
    }

    public Customer getCustomer(int id) {

        Customer customer = null;

        SQLiteDatabase db = getReadableDatabase();

        String[] columns = {
                CustomerContract.CustomerTable.COLUMN_ID,
                CustomerContract.CustomerTable.COLUMN_NAME,
                CustomerContract.CustomerTable.COLUMN_ADDRESS,
                CustomerContract.CustomerTable.COLUMN_PHONE
        };

        String selection = CustomerContract.CustomerTable.COLUMN_ID + " = ?";
        String[] selectionArgs = { String.valueOf(id)};

        Cursor cursor = db.query(
                CustomerContract.CustomerTable.TABLE_NAME,
                columns, selection, selectionArgs, null, null, null
        );

        if (cursor.moveToFirst()) {

            int _id = cursor.getInt(cursor.getColumnIndex(CustomerContract.CustomerTable.COLUMN_ID));
            String name = cursor.getString(cursor.getColumnIndex(CustomerContract.CustomerTable.COLUMN_NAME));
            String address = cursor.getString(cursor.getColumnIndex(CustomerContract.CustomerTable.COLUMN_ADDRESS));
            String phone = cursor.getString(cursor.getColumnIndex(CustomerContract.CustomerTable.COLUMN_PHONE));

            customer = new Customer();
            customer.setId(_id);
            customer.setName(name);
            customer.setAddress(address);
            customer.setPhone(phone);

        }

        return customer;

    }


    public void addCustomer(Customer customer) {
        SQLiteDatabase db = getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(CustomerContract.CustomerTable.COLUMN_NAME, customer.getName());
        contentValues.put(CustomerContract.CustomerTable.COLUMN_ADDRESS, customer.getAddress());
        contentValues.put(CustomerContract.CustomerTable.COLUMN_PHONE, customer.getPhone());

        db.insert(CustomerContract.CustomerTable.TABLE_NAME, null, contentValues);
    }



    public ArrayList<Customer> getCustomers() {
        ArrayList<Customer> customers = new ArrayList<>();

        SQLiteDatabase db = getReadableDatabase();
        String[] columns = {
                CustomerContract.CustomerTable.COLUMN_ID,
                CustomerContract.CustomerTable.COLUMN_NAME,
                CustomerContract.CustomerTable.COLUMN_ADDRESS,
                CustomerContract.CustomerTable.COLUMN_PHONE
        };

        String selection = "";

        Cursor cursor = db.query(
                CustomerContract.CustomerTable.TABLE_NAME,
                columns, selection, null, null, null, null
        );

        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(cursor.getColumnIndex(CustomerContract.CustomerTable.COLUMN_ID));
                String name = cursor.getString(cursor.getColumnIndex(CustomerContract.CustomerTable.COLUMN_NAME));
                String address = cursor.getString(cursor.getColumnIndex(CustomerContract.CustomerTable.COLUMN_ADDRESS));
                String phone = cursor.getString(cursor.getColumnIndex(CustomerContract.CustomerTable.COLUMN_PHONE));

                Customer customer = new Customer();
                customer.setId(id);
                customer.setName(name);
                customer.setAddress(address);
                customer.setPhone(phone);

                customers.add(customer);

            } while (cursor.moveToNext());
        }
        return customers;
    }
}
