package com.example.busbookingapp;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.content.res.Resources;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.view.View;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "BusBookingApp.db";
    private static final int DATABASE_VERSION = 2; // Increment the version

    // Table Names
    private static final String TABLE_USERS = "Users";
    private static final String TABLE_BUSES = "Buses";
    private static final String TABLE_TRANSACTIONS = "Transactions";

    // Users Table Columns
    private static final String COLUMN_USER_ID = "id";
    private static final String COLUMN_USERNAME = "username";
    private static final String COLUMN_PASSWORD = "password";
    private static final String COLUMN_EMAIL = "email";
    private static final String COLUMN_PHONE = "phone_number";
    private static final String COLUMN_FULLNAME = "full_name";
    private static final String COLUMN_THEME = "theme";

    // Buses Table Columns
    private static final String COLUMN_BUS_ID = "bus_id";
    private static final String COLUMN_BUS_NAME = "bus_name";
    private static final String COLUMN_BUS_TYPE = "bus_type";
    private static final String COLUMN_DEPARTURE_TIME = "departure_time";
    private static final String COLUMN_ARRIVAL_TIME = "arrival_time";
    private static final String COLUMN_SOURCE = "source";
    private static final String COLUMN_DESTINATION = "destination";

    // Transactions Table Columns
    private static final String COLUMN_TRANSACTION_ID = "id";
    private static final String COLUMN_TRANSACTION_BUS_ID = "bus_id";
    private static final String COLUMN_TRANSACTION_SEATS = "seats";
    private static final String COLUMN_TRANSACTION_ID_NUMBER = "id_number";
    private static final String COLUMN_TRANSACTION_PAYMENT_METHOD = "payment_method";
    private static final String COLUMN_TRANSACTION_PAYMENT_ID = "payment_id";
    private static final String COLUMN_TRANSACTION_DATE = "transaction_date";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Create Users table
        String CREATE_USERS_TABLE = "CREATE TABLE " + TABLE_USERS + "("
                + COLUMN_USER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COLUMN_USERNAME + " TEXT,"
                + COLUMN_PASSWORD + " TEXT,"
                + COLUMN_EMAIL + " TEXT,"
                + COLUMN_PHONE + " TEXT,"
                + COLUMN_FULLNAME + " TEXT,"
                + COLUMN_THEME + " TEXT" + ")";
        db.execSQL(CREATE_USERS_TABLE);

        // Create Buses table
        String CREATE_BUSES_TABLE = "CREATE TABLE " + TABLE_BUSES + "("
                + COLUMN_BUS_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COLUMN_BUS_NAME + " TEXT,"
                + COLUMN_BUS_TYPE + " TEXT,"
                + COLUMN_DEPARTURE_TIME + " TEXT,"
                + COLUMN_ARRIVAL_TIME + " TEXT,"
                + COLUMN_SOURCE + " TEXT,"
                + COLUMN_DESTINATION + " TEXT" + ")";
        db.execSQL(CREATE_BUSES_TABLE);

        // Create Transactions table
        String CREATE_TRANSACTIONS_TABLE = "CREATE TABLE " + TABLE_TRANSACTIONS + "("
                + COLUMN_TRANSACTION_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COLUMN_TRANSACTION_BUS_ID + " TEXT,"
                + COLUMN_TRANSACTION_SEATS + " TEXT,"
                + COLUMN_TRANSACTION_ID_NUMBER + " TEXT,"
                + COLUMN_TRANSACTION_PAYMENT_METHOD + " TEXT,"
                + COLUMN_TRANSACTION_PAYMENT_ID + " TEXT,"
                + COLUMN_TRANSACTION_DATE + " INTEGER" + ")";
        db.execSQL(CREATE_TRANSACTIONS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion < 2) {
            // Create Transactions table if it doesn't exist
            String CREATE_TRANSACTIONS_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_TRANSACTIONS + "("
                    + COLUMN_TRANSACTION_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + COLUMN_TRANSACTION_BUS_ID + " TEXT,"
                    + COLUMN_TRANSACTION_SEATS + " TEXT,"
                    + COLUMN_TRANSACTION_ID_NUMBER + " TEXT,"
                    + COLUMN_TRANSACTION_PAYMENT_METHOD + " TEXT,"
                    + COLUMN_TRANSACTION_PAYMENT_ID + " TEXT,"
                    + COLUMN_TRANSACTION_DATE + " INTEGER" + ")";
            db.execSQL(CREATE_TRANSACTIONS_TABLE);
        }
    }

    // Add user method
    public boolean addUser(String username, String password, String email, String phone, String fullName) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_USERNAME, username);
        values.put(COLUMN_PASSWORD, password);
        values.put(COLUMN_EMAIL, email);
        values.put(COLUMN_PHONE, phone);
        values.put(COLUMN_FULLNAME, fullName);

        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_USERS + " WHERE " +
                COLUMN_USERNAME + "=? OR " + COLUMN_EMAIL + "=?", new String[]{username, email});
        if (cursor.getCount() > 0) {
            cursor.close();
            return false; // User already exists
        }

        long result = db.insert(TABLE_USERS, null, values);
        cursor.close();
        db.close();
        return result != -1; // True if the user was added successfully
    }

    // Check user credentials
    public boolean checkUser(String username, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_USERS + " WHERE " +
                COLUMN_USERNAME + "=? AND " + COLUMN_PASSWORD + "=?", new String[]{username, password});
        boolean result = cursor.getCount() > 0;
        cursor.close();
        return result;
    }

    // Fetch available buses based on source and destination
    public List<Bus> getAvailableBuses(String source, String destination) {
        List<Bus> busList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_BUSES + " WHERE " +
                COLUMN_SOURCE + "=? AND " + COLUMN_DESTINATION + "=?", new String[]{source, destination});

        if (cursor.moveToFirst()) {
            do {
                @SuppressLint("Range") Bus bus = new Bus(
                        cursor.getString(cursor.getColumnIndex(COLUMN_BUS_ID)), // Pass busId
                        cursor.getString(cursor.getColumnIndex(COLUMN_BUS_NAME)),
                        cursor.getString(cursor.getColumnIndex(COLUMN_BUS_TYPE)),
                        cursor.getString(cursor.getColumnIndex(COLUMN_DEPARTURE_TIME)),
                        cursor.getString(cursor.getColumnIndex(COLUMN_ARRIVAL_TIME)),
                        cursor.getString(cursor.getColumnIndex(COLUMN_SOURCE)),
                        cursor.getString(cursor.getColumnIndex(COLUMN_DESTINATION))
                );
                busList.add(bus);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return busList;
    }

    // Fetch bus by ID
    public Bus getBusById(String busId) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_BUSES + " WHERE " + COLUMN_BUS_ID + " = ?", new String[]{busId});

        if (cursor.moveToFirst()) {
            @SuppressLint("Range") Bus bus = new Bus(
                    cursor.getString(cursor.getColumnIndex(COLUMN_BUS_ID)), // Pass busId
                    cursor.getString(cursor.getColumnIndex(COLUMN_BUS_NAME)),
                    cursor.getString(cursor.getColumnIndex(COLUMN_BUS_TYPE)),
                    cursor.getString(cursor.getColumnIndex(COLUMN_DEPARTURE_TIME)),
                    cursor.getString(cursor.getColumnIndex(COLUMN_ARRIVAL_TIME)),
                    cursor.getString(cursor.getColumnIndex(COLUMN_SOURCE)),
                    cursor.getString(cursor.getColumnIndex(COLUMN_DESTINATION))
            );
            cursor.close();
            return bus; // Return the found bus
        } else {
            cursor.close();
            return null; // Bus not found
        }
    }

    // Fetch all buses from the database
    @SuppressLint("Range")
    public List<Bus> getAllBuses() {
        List<Bus> busList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_BUSES, null);

        if (cursor.moveToFirst()) {
            do {
                Bus bus = new Bus(
                        cursor.getString(cursor.getColumnIndex(COLUMN_BUS_ID)), // Include busId here
                        cursor.getString(cursor.getColumnIndex(COLUMN_BUS_NAME)),
                        cursor.getString(cursor.getColumnIndex(COLUMN_BUS_TYPE)),
                        cursor.getString(cursor.getColumnIndex(COLUMN_DEPARTURE_TIME)),
                        cursor.getString(cursor.getColumnIndex(COLUMN_ARRIVAL_TIME)),
                        cursor.getString(cursor.getColumnIndex(COLUMN_SOURCE)),
                        cursor.getString(cursor.getColumnIndex(COLUMN_DESTINATION))
                );
                busList.add(bus);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return busList;
    }

    // Delete bus
    public boolean deleteBus(String busId) {
        SQLiteDatabase db = this.getWritableDatabase();
        int result = db.delete(TABLE_BUSES, COLUMN_BUS_ID + "=?", new String[]{busId});
        db.close();
        return result > 0; // Return true if the bus was deleted successfully
    }

    // Add bus
    public boolean addBus(String busId, String busName, String busType, String source, String destination, String departureTime, String arrivalTime) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_BUS_ID, busId); // Using busId to allow specifying an ID
        values.put(COLUMN_BUS_NAME, busName);
        values.put(COLUMN_BUS_TYPE, busType);
        values.put(COLUMN_SOURCE, source);
        values.put(COLUMN_DESTINATION, destination);
        values.put(COLUMN_DEPARTURE_TIME, departureTime);
        values.put(COLUMN_ARRIVAL_TIME, arrivalTime);

        long result = db.insert(TABLE_BUSES, null, values);
        db.close();
        return result != -1; // True if the bus was added successfully
    }

    // Fetch user details by username
    public User getUserByUsername(String username) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_USERS + " WHERE " + COLUMN_USERNAME + " = ?", new String[]{username});

        if (cursor.moveToFirst()) {
            @SuppressLint("Range") String fullName = cursor.getString(cursor.getColumnIndex(COLUMN_FULLNAME));
            @SuppressLint("Range") String phoneNumber = cursor.getString(cursor.getColumnIndex(COLUMN_PHONE));
            @SuppressLint("Range") String password = cursor.getString(cursor.getColumnIndex(COLUMN_PASSWORD));
            cursor.close();
            return new User(fullName, phoneNumber, password); // Create and return a User object
        } else {
            cursor.close();
            return null; // User not found
        }
    }

    // Update user details
    public boolean updateUser(String username, String newPassword, String newPhone, String newFullName) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_PASSWORD, newPassword);
        values.put(COLUMN_PHONE, newPhone);
        values.put(COLUMN_FULLNAME, newFullName);

        int result = db.update(TABLE_USERS, values, COLUMN_USERNAME + " = ?", new String[]{username});
        return result > 0; // Return true if the update was successful
    }

    // Update user theme
    public void updateUserTheme(String username, String newTheme) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_THEME, newTheme); // Save the selected theme for the user

        int result = db.update(TABLE_USERS, values, COLUMN_USERNAME + " = ?", new String[]{username});
    }

    // Fetch all locations from resources (strings.xml) for spinners in BusSearchActivity
    public List<String> getAllLocationsFromResources() {
        List<String> locations = new ArrayList<>();
        try {
            View context = null;
            Resources res = context.getResources();
            String[] cities = res.getStringArray(R.array.city_names); // Ensure you have this array in strings.xml
            Collections.addAll(locations, cities);
        } catch (Exception e) {
            Log.e("DatabaseHelper", "Error fetching locations from resources", e);
        }
        return locations;
    }

    // Add this method to your existing DatabaseHelper class

    public void addTransaction(String busId, String seats, String idNumber, String paymentMethod, String paymentId) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_TRANSACTION_BUS_ID, busId);
        values.put(COLUMN_TRANSACTION_SEATS, seats);
        values.put(COLUMN_TRANSACTION_ID_NUMBER, idNumber);
        values.put(COLUMN_TRANSACTION_PAYMENT_METHOD, paymentMethod);
        values.put(COLUMN_TRANSACTION_PAYMENT_ID, paymentId);
        values.put(COLUMN_TRANSACTION_DATE, System.currentTimeMillis());

        db.insert(TABLE_TRANSACTIONS, null, values);
        db.close();
    }

    @SuppressLint("Range")
    public List<Transaction> getAllTransactions() {
        List<Transaction> transactions = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_TRANSACTIONS, null, null, null, null, null, COLUMN_TRANSACTION_DATE + " DESC");

        if (cursor.moveToFirst()) {
            do {
                Transaction transaction = new Transaction(
                    cursor.getLong(cursor.getColumnIndex(COLUMN_TRANSACTION_ID)),
                    cursor.getString(cursor.getColumnIndex(COLUMN_TRANSACTION_BUS_ID)),
                    cursor.getString(cursor.getColumnIndex(COLUMN_TRANSACTION_SEATS)),
                    cursor.getString(cursor.getColumnIndex(COLUMN_TRANSACTION_ID_NUMBER)),
                    cursor.getString(cursor.getColumnIndex(COLUMN_TRANSACTION_PAYMENT_METHOD)),
                    cursor.getString(cursor.getColumnIndex(COLUMN_TRANSACTION_PAYMENT_ID)),
                    cursor.getLong(cursor.getColumnIndex(COLUMN_TRANSACTION_DATE))
                );
                transactions.add(transaction);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return transactions;
    }

    // Add this method to your DatabaseHelper class
    public void cancelTransaction(long id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_TRANSACTIONS, COLUMN_TRANSACTION_ID + " = ?", new String[]{String.valueOf(id)});
    }

    // Add this method to your DatabaseHelper class

    @SuppressLint("Range")
    public Transaction getTransactionById(long transactionId) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_TRANSACTIONS, null, COLUMN_TRANSACTION_ID + " = ?",
                new String[]{String.valueOf(transactionId)}, null, null, null);

        Transaction transaction = null;
        if (cursor.moveToFirst()) {
            transaction = new Transaction(
                    cursor.getLong(cursor.getColumnIndex(COLUMN_TRANSACTION_ID)),
                    cursor.getString(cursor.getColumnIndex(COLUMN_TRANSACTION_BUS_ID)),
                    cursor.getString(cursor.getColumnIndex(COLUMN_TRANSACTION_SEATS)),
                    cursor.getString(cursor.getColumnIndex(COLUMN_TRANSACTION_ID_NUMBER)),
                    cursor.getString(cursor.getColumnIndex(COLUMN_TRANSACTION_PAYMENT_METHOD)),
                    cursor.getString(cursor.getColumnIndex(COLUMN_TRANSACTION_PAYMENT_ID)),
                    cursor.getLong(cursor.getColumnIndex(COLUMN_TRANSACTION_DATE))
            );
        }
        cursor.close();
        db.close();
        return transaction;
    }
}
