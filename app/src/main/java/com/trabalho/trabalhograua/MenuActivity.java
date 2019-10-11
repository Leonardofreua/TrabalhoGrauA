package com.trabalho.trabalhograua;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class MenuActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu_activity);
        setTitle("Menu");
    }

    public void toAddCustumer(View view) {
        Intent intent = new Intent(view.getContext(), CustomerActivity.class);
        startActivity(intent);
    }

    public void toCustomersList(View view) {
        Intent intent = new Intent(view.getContext(), CustomersListActivity.class);
        startActivity(intent);
    }

    public void toAddSchedule(View view) {
        Intent intent = new Intent(view.getContext(), ScheduleActivity.class);
        startActivity(intent);
    }

    public void toSchedulesList(View view) {
        Intent intent = new Intent(view.getContext(), SchedulesListActivity.class);
        startActivity(intent);
    }
}
