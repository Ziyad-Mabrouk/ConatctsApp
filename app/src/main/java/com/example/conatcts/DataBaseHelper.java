package com.example.conatcts;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class DataBaseHelper extends SQLiteOpenHelper {

    public static final String Contact_Table = "Contact_Table";
    public static final String Column_Contact_ID = "id";
    public static final String Column_Contact_FirstName = "firstName";
    public static final String Column_Contact_LastName = "lastName";
    public static final String Column_Contact_PhoneNumber = "phoneNumber";
    public static final String Column_Contact_EmailAdress = "emailAdress";

    public DataBaseHelper(@Nullable Context context) {
        super(context, "contact.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE " + Contact_Table + " (" + Column_Contact_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + Column_Contact_FirstName + " TEXT, " + Column_Contact_LastName + " TEXT, " + Column_Contact_PhoneNumber + " TEXT, " + Column_Contact_EmailAdress + " TEXT)";
        db.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public boolean addContact(Contact contact) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(Column_Contact_FirstName, contact.getFirstName());
        cv.put(Column_Contact_LastName, contact.getLastName());
        cv.put(Column_Contact_PhoneNumber, contact.getPhoneNumber());
        cv.put(Column_Contact_EmailAdress, contact.getEmailAdress());

        long insert = db.insert(Contact_Table, null, cv);
        return insert != -1;
    }

    public List<Contact> getAllContacts() {
        List<Contact> returnList = new ArrayList<>();

        String selectAll = "SELECT * FROM " + Contact_Table;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectAll, null);

        if(cursor.moveToFirst()){
            do{
                int contactID = cursor.getInt(0);
                String contactFirstName = cursor.getString(1);
                String contactLastName = cursor.getString(2);
                String contactPhoneNumber = cursor.getString(3);
                String contactEmailAdress = cursor.getString(4);

                Contact newContact = new Contact(contactID, contactFirstName, contactLastName, contactPhoneNumber, contactEmailAdress);
                returnList.add(newContact);

            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();

        return returnList;
    }

    public boolean deleteContact(Contact contact) {
        SQLiteDatabase db = this.getWritableDatabase();

        String delete = "DELETE FROM " + Contact_Table + " WHERE " + Column_Contact_ID + " = " + contact.getId();
        Cursor cursor = db.rawQuery(delete, null);

        if (cursor.moveToFirst()) {
            return true;
        } else {
            return false;
        }
    }

    public boolean modifyContact(Contact contact) {
        SQLiteDatabase db = this.getWritableDatabase();

        String update = "UPDATE " + Contact_Table + " SET " + Column_Contact_FirstName + " = '" + contact.getFirstName() + "', " + Column_Contact_LastName + " = '" + contact.getLastName() + "', " + Column_Contact_PhoneNumber + " = '" + contact.getPhoneNumber() + "', " + Column_Contact_EmailAdress + " = '" + contact.getEmailAdress() + "' WHERE " + Column_Contact_ID + " = " + contact.getId();
        Cursor cursor = db.rawQuery(update, null);

        if (cursor.moveToFirst()) {
            return true;
        } else {
            return false;
        }

    }

    public List<Contact> searchContact(String name) {
        List<Contact> returnList = new ArrayList<>();

        String match = "SELECT * FROM " + Contact_Table + " WHERE " + Column_Contact_FirstName + " LIKE '%" + name + "%' OR " + Column_Contact_LastName + " LIKE '%" + name + "%'";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(match, null);

        if(cursor.moveToFirst()){
            do{
                int contactID = cursor.getInt(0);
                String contactFirstName = cursor.getString(1);
                String contactLastName = cursor.getString(2);
                String contactPhoneNumber = cursor.getString(3);
                String contactEmailAdress = cursor.getString(4);

                Contact newContact = new Contact(contactID, contactFirstName, contactLastName, contactPhoneNumber, contactEmailAdress);
                returnList.add(newContact);

            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();

        return returnList;
    }

    public List<Contact> getSorted(int direction) {
        List<Contact> returnList = new ArrayList<>();

        String sortDirection = (direction == 0) ? " ASC": " DESC";
        String match = "SELECT * FROM " + Contact_Table + " ORDER BY LOWER(" + Column_Contact_FirstName + ") " + sortDirection;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(match, null);

        if(cursor.moveToFirst()){
            do{
                int contactID = cursor.getInt(0);
                String contactFirstName = cursor.getString(1);
                String contactLastName = cursor.getString(2);
                String contactPhoneNumber = cursor.getString(3);
                String contactEmailAdress = cursor.getString(4);

                Contact newContact = new Contact(contactID, contactFirstName, contactLastName, contactPhoneNumber, contactEmailAdress);
                returnList.add(newContact);

            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();

        return returnList;
    }
}
