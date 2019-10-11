package com.trabalho.trabalhograua.controllers;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.trabalho.trabalhograua.db.DatabaseConnection;
import com.trabalho.trabalhograua.models.CustomerModel;

public class CustomerController {

    private DatabaseConnection conn;

    public CustomerController(Context context) {
        conn = new DatabaseConnection(context);
    }

    public Boolean add(CustomerModel customer) {
        SQLiteDatabase db = conn.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(DatabaseConnection.getNameCol(), customer.getName());
        values.put(DatabaseConnection.getAgeCol(), customer.getAge());
        values.put(DatabaseConnection.getAddressCol(), customer.getAddress());
        values.put(DatabaseConnection.getSexCol(), customer.getSex());
        values.put(DatabaseConnection.getEmailCol(), customer.getEmail());
        values.put(DatabaseConnection.getPhoneCol(), customer.getPhone());

        long result = db.insert(DatabaseConnection.getTableCustomer(), null, values);

        return result != -1;
    }

    public Cursor getAllCustomers() {
        SQLiteDatabase db = conn.getReadableDatabase();

        return db.query(DatabaseConnection.getTableCustomer(), null, null,
                null, null, null, null);
    }

    public Cursor findById(int id) {
        SQLiteDatabase db = conn.getReadableDatabase();

        String where = DatabaseConnection.getKeyId() + "=" + id;

        Cursor cursor = db.query(DatabaseConnection.getTableCustomer(), null, where,
                null, null, null, null, null);

        if (cursor != null) {
            cursor.moveToFirst();
        }
        db.close();
        return cursor;
    }

    public void updateCustomer(int id, CustomerModel customer) {
        ContentValues values;
        String where;

        SQLiteDatabase db = conn.getWritableDatabase();

        where = DatabaseConnection.getKeyId() + "=" + id;

        values = new ContentValues();

        values.put(DatabaseConnection.getNameCol(), customer.getName());
        values.put(DatabaseConnection.getAgeCol(), customer.getAge());
        values.put(DatabaseConnection.getAddressCol(), customer.getAddress());
        values.put(DatabaseConnection.getPhoneCol(), customer.getPhone());
        values.put(DatabaseConnection.getEmailCol(), customer.getEmail());
        values.put(DatabaseConnection.getSexCol(), customer.getSex());

        db.update(DatabaseConnection.getTableCustomer(), values, where, null);
        db.close();
    }
}
