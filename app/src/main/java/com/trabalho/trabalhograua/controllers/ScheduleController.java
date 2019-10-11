package com.trabalho.trabalhograua.controllers;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.trabalho.trabalhograua.db.DatabaseConnection;
import com.trabalho.trabalhograua.models.ScheduleModel;

public class ScheduleController {

    private DatabaseConnection conn;

    public ScheduleController(Context context) {
        conn = new DatabaseConnection(context);
    }

    public Boolean add(ScheduleModel schedule) {
        SQLiteDatabase db = conn.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(DatabaseConnection.getIdCustomerCol(), schedule.getIdCustomer());
        values.put(DatabaseConnection.getScheduleDateCol(), schedule.getScheduleDate());
        values.put(DatabaseConnection.getScheduleTimeCol(), schedule.getScheduleTime());
        values.put(DatabaseConnection.getDescriptionCol(), schedule.getDescription());

        long result = db.insert(DatabaseConnection.getTableSchedule(), null, values);

        return result != -1;
    }

    public Cursor getAllSchedules() {
        SQLiteDatabase db = conn.getReadableDatabase();

        Cursor cursor = db.rawQuery(DatabaseConnection.getQueryAllSchedulesJoinCustomer(), null);

        if (cursor != null) {
            cursor.moveToFirst();
        }
        db.close();
        return cursor;
    }

    public Cursor findById(int id) {
        SQLiteDatabase db = conn.getReadableDatabase();

        Cursor cursor = db.rawQuery(DatabaseConnection.getQueryAllSchedulesJoinCustomerWithWhere(),
                new String[]{String.valueOf(id)});

        if (cursor != null) {
            cursor.moveToFirst();
        }
        db.close();
        return cursor;
    }

    public Cursor getAllCustomers() {
        SQLiteDatabase db = conn.getReadableDatabase();

        String[] columns = {DatabaseConnection.getKeyId(), DatabaseConnection.getNameCol()};

        return db.query(DatabaseConnection.getTableCustomer(), columns, null,
                null, null, null, null);
    }

    public void updateSchedule(int id, ScheduleModel schedule) {
        ContentValues values;
        String where;

        SQLiteDatabase db = conn.getWritableDatabase();

        where = DatabaseConnection.getKeyId() + "=" + id;

        values = new ContentValues();

        values.put(DatabaseConnection.getIdCustomerCol(), schedule.getIdCustomer());
        values.put(DatabaseConnection.getScheduleDateCol(), schedule.getScheduleDate());
        values.put(DatabaseConnection.getScheduleTimeCol(), schedule.getScheduleTime());
        values.put(DatabaseConnection.getDescriptionCol(), schedule.getDescription());

        db.update(DatabaseConnection.getTableSchedule(), values, where, null);
        db.close();
    }
}
