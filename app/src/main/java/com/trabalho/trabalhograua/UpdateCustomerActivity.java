package com.trabalho.trabalhograua;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.trabalho.trabalhograua.controllers.CustomerController;
import com.trabalho.trabalhograua.db.DatabaseConnection;
import com.trabalho.trabalhograua.models.CustomerModel;

public class UpdateCustomerActivity extends AppCompatActivity {

    private CustomerModel customer = new CustomerModel();

    //Fields
    EditText name, age, address, phone, email;
    String _id;
    RadioGroup radioGroup;
    RadioButton radioButton;
    RadioButton radioOne;
    RadioButton radioTwo;
    Button btnUpdate;

    Cursor cursor;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.update_customer_activity);
        setTitle("Alteração de Cliente");

        btnUpdate = findViewById(R.id.btn_update);

        this.loadValues();

        this.updateCustomer();
    }

    public void loadValues() {
        CustomerController customerController = new CustomerController(getApplicationContext());

        this._id = this.getIntent().getStringExtra("_id");

        radioGroup = findViewById(R.id.sexRadioGroup);
        radioOne = findViewById(R.id.radio_one);
        radioTwo = findViewById(R.id.radio_two);

        name = findViewById(R.id.name);
        age = findViewById(R.id.age);
        address = findViewById(R.id.address);
        phone = findViewById(R.id.phone);
        email = findViewById(R.id.email);

        cursor = customerController.findById(Integer.parseInt(this._id));

        name.setText(cursor.getString(cursor.getColumnIndexOrThrow(
                DatabaseConnection.getNameCol())));

        age.setText(cursor.getString(cursor.getColumnIndexOrThrow(
                DatabaseConnection.getAgeCol())));

        address.setText(cursor.getString(cursor.getColumnIndexOrThrow(
                DatabaseConnection.getAddressCol())));

        phone.setText(cursor.getString(cursor.getColumnIndexOrThrow(
                DatabaseConnection.getPhoneCol())));

        email.setText(cursor.getString(cursor.getColumnIndexOrThrow(
                DatabaseConnection.getEmailCol())));

        String radioValue = cursor.getString(cursor.getColumnIndexOrThrow(
                DatabaseConnection.getSexCol()));

        if (radioValue.equals("Masculino")) {
            radioOne.setChecked(true);
        } else {
            radioTwo.setChecked(true);
        }
    }

    public void updateCustomer() {
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (validationSuccess()) {
                    CustomerController customerController = new CustomerController(getApplicationContext());

                    int radioId = radioGroup.getCheckedRadioButtonId();

                    radioButton = findViewById(radioId);

                    customer.setName(name.getText().toString());
                    customer.setAge(Integer.parseInt(age.getText().toString()));
                    customer.setAddress(address.getText().toString());
                    customer.setSex(radioButton.getText().toString());
                    customer.setEmail(email.getText().toString());
                    customer.setPhone(Integer.parseInt(phone.getText().toString()));

                    customerController.updateCustomer(Integer.parseInt(_id), customer);
                    Intent intent = new Intent(view.getContext(), CustomersListActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    alertDialog();
                }
            }
        });
    }

    private Boolean validationSuccess() {
        if (name.getText().toString().equalsIgnoreCase("")) {
            return false;
        }

        if (age.getText().toString().equalsIgnoreCase("")) {
            return false;
        }

        if (address.getText().toString().equalsIgnoreCase("")) {
            return false;
        }

        if (phone.getText().toString().equalsIgnoreCase("")) {
            return false;
        }

        if (email.getText().toString().equalsIgnoreCase("")) {
            return false;
        }

        return true;
    }

    private void alertDialog() {
        new AlertDialog.Builder(this)
                .setTitle("Campos obrigatórios")
                .setMessage("Verifique se preencheu todos os campos")

                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                    }
                })

                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();

    }

    public void onRadioButtonClicked(View view) {
        int radioId = radioGroup.getCheckedRadioButtonId();

        radioButton = findViewById(radioId);

        Toast.makeText(this, "Selected radio button: " + radioButton.getText(), Toast.LENGTH_SHORT).show();
    }

    public void goBackMenu(View view) {
        Intent intent = new Intent(view.getContext(), CustomersListActivity.class);
        startActivity(intent);
    }
}
