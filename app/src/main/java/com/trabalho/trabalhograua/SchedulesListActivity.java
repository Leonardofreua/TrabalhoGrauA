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

import com.trabalho.trabalhograua.controllers.ScheduleController;
import com.trabalho.trabalhograua.db.DatabaseConnection;

public class SchedulesListActivity extends AppCompatActivity {

    ListView schedulesList;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.schedules_list);
        setTitle("Lista de Consultas");

        this.getAllSchedules();
    }

    public void getAllSchedules() {
        ScheduleController scheduleController = new ScheduleController(getApplicationContext());

        Cursor schedules = scheduleController.getAllSchedules();

        String[] fieldNames = new String[]{DatabaseConnection.getKeyId(),
                DatabaseConnection.getNameCol(),
                DatabaseConnection.getScheduleDateCol(),
                DatabaseConnection.getScheduleTimeCol()};

        int[] idViews = new int[]{R.id.id,
                R.id.customerName,
                R.id.scheduleDate,
                R.id.scheduleTime};

        SimpleCursorAdapter adapter = new SimpleCursorAdapter(getBaseContext(),
                R.layout.schedules_row, schedules, fieldNames, idViews, 0);
        schedulesList = findViewById(R.id.schedules_list);
        schedulesList.setAdapter(adapter);

        this.setItemClick(schedules);
    }

    public void setItemClick(final Cursor cursor) {
        schedulesList.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String _id;

                cursor.moveToPosition(position);
                _id = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseConnection.getKeyId()));

                Intent intent = new Intent(view.getContext(), UpdateScheduleActivity.class);
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
