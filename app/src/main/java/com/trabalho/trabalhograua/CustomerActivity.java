package com.trabalho.trabalhograua;


import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.trabalho.trabalhograua.controllers.CustomerController;
import com.trabalho.trabalhograua.models.CustomerModel;

public class CustomerActivity extends AppCompatActivity {

    private CustomerModel customer = new CustomerModel();
    RadioGroup radioGroup;
    RadioButton radioButton;
    Button btnAdd;

    //Fields
    EditText name, age, address, phone, email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.customer_activity);
        setTitle("Cadastro de Cliente");

        radioGroup = findViewById(R.id.sexRadioGroup);

        name = findViewById(R.id.name);
        age = findViewById(R.id.age);
        address = findViewById((R.id.address));
        phone = findViewById(R.id.phone);
        email = findViewById(R.id.email);
        btnAdd = findViewById(R.id.btn_add);

        this.addData();
    }

    public void addData() {
        btnAdd.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
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

                    Boolean result = customerController.add(customer);

                    if (result) {
                        Toast.makeText(getApplicationContext(), "Data inserted", Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(getApplicationContext(), "Data not inserted", Toast.LENGTH_LONG).show();
                    }
                    Intent intent = new Intent(v.getContext(), MenuActivity.class);
                    startActivity(intent);
                } else {
                    alertDialog();
                }
            }
        });
    }

    public void goBackMenu(View view) {
        Intent intent = new Intent(view.getContext(), MenuActivity.class);
        startActivity(intent);
    }

    public void onRadioButtonClicked(View view) {
        int radioId = radioGroup.getCheckedRadioButtonId();

        radioButton = findViewById(radioId);

        Toast.makeText(this, "Selected radio button: " + radioButton.getText(), Toast.LENGTH_SHORT).show();
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

        if (radioButton.getText().toString().equalsIgnoreCase("")) {
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
}
