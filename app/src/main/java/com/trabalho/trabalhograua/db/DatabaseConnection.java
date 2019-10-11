package com.trabalho.trabalhograua.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseConnection extends SQLiteOpenHelper {

    private static final String DB_NAME = "TrabalhoGrauA.db";
    private static final String KEY_ID = "_id";
    /**
     * Table CUSTOMER
     */
    private static final String TABLE_CUSTOMER = "customer";
    private static final String NAME_COL = "name";
    private static final String AGE_COL = "age";
    private static final String ADDRESS_COL = "address";
    private static final String SEX_COL = "sex";
    private static final String EMAIL_COL = "email";
    private static final String PHONE_COL = "phone";

    private static final String CREATE_TABLE_CUSTOMERS = "CREATE TABLE "
            + TABLE_CUSTOMER + " ( " + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + NAME_COL + " TEXT,"
            + AGE_COL + " INTEGER,"
            + ADDRESS_COL + " TEXT,"
            + SEX_COL + " TEXT,"
            + EMAIL_COL + " TEXT,"
            + PHONE_COL + " INTEGER);";

    /**
     * Table SCHEDULE
     */
    private static final String TABLE_SCHEDULE = "schedule";
    private static final String ID_CUSTOMER_COL = "idCustomer";
    private static final String SCHEDULE_DATE_COL = "scheduleDate";
    private static final String SCHEDULE_TIME_COL = "scheduleTime";
    private static final String DESCRIPTION_COL = "description";

    private static final String CREATE_TABLE_SCHEDULES = "CREATE TABLE "
            + TABLE_SCHEDULE + " ( " + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + ID_CUSTOMER_COL + " INTEGER,"
            + SCHEDULE_DATE_COL + " TEXT,"
            + SCHEDULE_TIME_COL + " TEXT,"
            + DESCRIPTION_COL + " TEXT);";

    /**
     * CUSTOM QUERYS
     */
    private static final String QUERY_ALL_SCHEDULES_JOIN_CUSTOMER = "SELECT s._id, c.name,"
            + " s.scheduleDate, s.scheduleTime"
            + " FROM " + TABLE_SCHEDULE + " s"
            + " INNER JOIN " + TABLE_CUSTOMER + " c ON c._id=s.idCustomer";

    private static final String QUERY_ALL_SCHEDULES_JOIN_CUSTOMER_WITH_WHERE = "SELECT *"
            + " FROM " + TABLE_SCHEDULE + " s"
            + " INNER JOIN " + TABLE_CUSTOMER + " c ON c._id=s.idCustomer"
            + " WHERE s._id = ?";

    SQLiteDatabase database;

    public DatabaseConnection(Context context) {
        super(context, DB_NAME, null, 1);
        database = this.getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_CUSTOMERS);
        db.execSQL(CREATE_TABLE_SCHEDULES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS '" + TABLE_CUSTOMER + "'");
        db.execSQL("DROP TABLE IF EXISTS '" + TABLE_SCHEDULE + "'");
        onCreate(db);
    }

    public static String getDbName() {
        return DB_NAME;
    }

    public static String getKeyId() {
        return KEY_ID;
    }

    /**
     * Customer
     */

    public static String getTableCustomer() {
        return TABLE_CUSTOMER;
    }

    public static String getNameCol() {
        return NAME_COL;
    }

    public static String getEmailCol() {
        return EMAIL_COL;
    }

    public static String getAgeCol() {
        return AGE_COL;
    }

    public static String getAddressCol() {
        return ADDRESS_COL;
    }

    public static String getSexCol() {
        return SEX_COL;
    }

    public static String getPhoneCol() {
        return PHONE_COL;
    }

    public static String getCreateTableCustomers() {
        return CREATE_TABLE_CUSTOMERS;
    }

    /**
     * Schedule
     */
    public static String getTableSchedule() {
        return TABLE_SCHEDULE;
    }

    public static String getIdCustomerCol() {
        return ID_CUSTOMER_COL;
    }

    public static String getScheduleDateCol() {
        return SCHEDULE_DATE_COL;
    }

    public static String getScheduleTimeCol() {
        return SCHEDULE_TIME_COL;
    }

    public static String getDescriptionCol() {
        return DESCRIPTION_COL;
    }

    public static String getCreateTableSchedules() {
        return CREATE_TABLE_SCHEDULES;
    }

    /**
     * Custom Querys
     */
    public static String getQueryAllSchedulesJoinCustomer() {
        return QUERY_ALL_SCHEDULES_JOIN_CUSTOMER;
    }

    public static String getQueryAllSchedulesJoinCustomerWithWhere() {
        return QUERY_ALL_SCHEDULES_JOIN_CUSTOMER_WITH_WHERE;
    }
}
