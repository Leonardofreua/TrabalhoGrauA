package com.trabalho.trabalhograua;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.trabalho.trabalhograua.controllers.CustomerController;
import com.trabalho.trabalhograua.db.DatabaseConnection;

public class CustomersListActivity extends AppCompatActivity {

    ListView customersList;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.customers_list);
        setTitle("Lista de Cliente");

        this.getAllCustomers();
    }

    public void getAllCustomers() {
        CustomerController customerController = new CustomerController(getApplicationContext());

        Cursor customers = customerController.getAllCustomers();

        String[] fieldNames = new String[]{DatabaseConnection.getKeyId(), DatabaseConnection.getNameCol()};

        int[] idViews = new int[]{R.id.id, R.id.customerName};

        SimpleCursorAdapter adapter = new SimpleCursorAdapter(getBaseContext(),
                R.layout.customers_row, customers, fieldNames, idViews, 0);
        customersList = findViewById(R.id.customers_list);
        customersList.setAdapter(adapter);

        this.setItemClick(customers);
    }

    public void setItemClick(final Cursor cursor) {
        customersList.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String _id;

                cursor.moveToPosition(position);
                _id = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseConnection.getKeyId()));

                Intent intent = new Intent(view.getContext(), UpdateCustomerActivity.class);
                intent.putExtra("_id", _id);
                startActivity(intent);
                finish();
            }
        });
    }

    public void goBackMenu(View view) {
        Intent intent = new Intent(view.getContext(), MenuActivity.class);
        startActivity(intent);
    }
}
