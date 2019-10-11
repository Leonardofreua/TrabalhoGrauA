package com.trabalho.trabalhograua;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import com.trabalho.trabalhograua.controllers.ScheduleController;
import com.trabalho.trabalhograua.models.ScheduleModel;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class ScheduleActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener,
        TimePickerDialog.OnTimeSetListener {

    private ScheduleModel schedule = new ScheduleModel();

    Spinner sp;
    Button btnAdd;
    ArrayList<String> customersNames = new ArrayList<>();
    ArrayList<Integer> idCustomers = new ArrayList<>();
    ArrayAdapter<String> adapter;
    EditText scheduleDate, scheduleTime, description;
    Integer idCustomer;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.schedule_activity);
        setTitle("Cadastrar Consultas");

        btnAdd = findViewById(R.id.btn_add);
        sp = findViewById(R.id.idCustomer);

        adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, customersNames);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Spinner population
        this.populateSpinner();

        this.getSelectedCustomer();
        scheduleDate = findViewById(R.id.scheduleDate);
        scheduleTime = findViewById(R.id.scheduleTime);
        description = findViewById(R.id.description);

        this.addData();
    }

    public void addData() {
        btnAdd.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                if (validationSuccess()) {
                    ScheduleController scheduleController = new ScheduleController(getApplicationContext());

                    schedule.setIdCustomer(idCustomer);
                    schedule.setScheduleDate(scheduleDate.getText().toString());
                    schedule.setScheduleTime(scheduleTime.getText().toString());
                    schedule.setDescription(description.getText().toString());

                    Boolean result = scheduleController.add(schedule);

                    if (result) {
                        Toast.makeText(getApplicationContext(), "Data inserted", Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(getApplicationContext(), "Data not inserted", Toast.LENGTH_LONG).show();
                    }
                    Intent intent = new Intent(view.getContext(), MenuActivity.class);
                    startActivity(intent);
                } else {
                    alertDialog();
                }
            }
        });
    }

    public void getSelectedCustomer() {
        sp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1,
                                       int arg2, long arg3) {
                idCustomer = idCustomers.get(sp.getSelectedItemPosition());
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    public void populateSpinner() {
        ScheduleController scheduleController = new ScheduleController(getApplicationContext());

        Cursor customers = scheduleController.getAllCustomers();
        Integer ids;
        String names;
        while (customers.moveToNext()) {
            ids = customers.getInt(0);
            names = customers.getString(1);
            this.idCustomers.add(ids);
            this.customersNames.add(names);
        }
        sp.setAdapter(adapter);
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, year);
        c.set(Calendar.MONTH, month);
        c.set(Calendar.DAY_OF_MONTH, dayOfMonth);

        DateFormat df = new SimpleDateFormat("dd/MM/yyyy");

        String currentDateString = df.format(c.getTime());
        EditText scheduleDate = findViewById(R.id.scheduleDate);
        scheduleDate.setText(currentDateString);
    }

    @Override
    public void onTimeSet(TimePicker view, int hour, int minute) {
        String timeFormat = hour + ":" + minute;
        EditText scheduleTime = findViewById(R.id.scheduleTime);
        scheduleTime.setText(timeFormat);
    }

    public void setScheduleDatePicker(View v) {
        DialogFragment datePicker = new DatePickerFragment();
        datePicker.show(getSupportFragmentManager(), "date picker");
    }

    public void setScheduleTimePicker(View v) {
        DialogFragment timePicker = new TimePickerFragment();
        timePicker.show(getSupportFragmentManager(), "time picker");
    }

    public void goBackMenu(View view) {
        Intent intent = new Intent(view.getContext(), MenuActivity.class);
        startActivity(intent);
    }

    private Boolean validationSuccess() {
        if (scheduleDate.getText().toString().equalsIgnoreCase("")) {
            return false;
        }

        if (scheduleTime.getText().toString().equalsIgnoreCase("")) {
            return false;
        }

        if (description.getText().toString().equalsIgnoreCase("")) {
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
