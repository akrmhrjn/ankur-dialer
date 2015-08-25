package com.example.akrmhrjn.ankurdailer;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.util.Log;

import java.util.ArrayList;

/**
 * Created by akrmhrjn on 7/15/15.
 */
public class DatabaseHelper extends android.database.sqlite.SQLiteOpenHelper {

    private static DatabaseHelper helperInstance = null;

    public DatabaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory,
                          int version) {
        super(context, name, factory, version);
    }

    private DatabaseHelper(Context context) {
        super(context, "ankurDailer", null, 1);
    }

    /**
     * This method sends a singleton instance of database so the database does not have multiple
     * instances running at the same time
     *
     * @param context The context of the activity calling the method
     * @return A handle iinstance of the database
     */
    public static DatabaseHelper getInstance(Context context) {
        if (helperInstance == null) {
            helperInstance = new DatabaseHelper(context.getApplicationContext());
        }
        return helperInstance;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //table creation script
        String queryContacts = "CREATE TABLE tbl_numbers(_id integer primary key autoincrement, name text, phone text);";
        db.execSQL(queryContacts);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }

    //add Contacts
    public boolean addContacts(ArrayList<ContactClass> contactList) {
        String name, phone;
        long response = 0;
        System.out.println("ArrayList Size:" + contactList.size());
        for(ContactClass contact : contactList){
            ContentValues cv = new ContentValues();
            cv.put("name", contact.name);
            cv.put("phone", contact.number);
            System.out.println("Contacts>> Name: " + contact.name+" Phone:"+contact.number);
            response = this.getWritableDatabase().insert("tbl_numbers", null, cv);
        }
        return response != -1;
    }

    //get Contacts
    public ArrayList<ContactClass> getContacts() {
        SQLiteDatabase db = this.getWritableDatabase();
        ArrayList<ContactClass> list = new ArrayList<ContactClass>();

        Cursor cur = db.rawQuery("select * from tbl_numbers;", null);
        if (cur.moveToFirst()) {
            do {
                ContactClass contacts = new ContactClass();
                contacts.id = cur.getInt(0);
                contacts.name = cur.getString(1);
                contacts.number = cur.getString(2);
                list.add(contacts);
            } while (cur.moveToNext());
        }
        cur.close();
        return list;
    }

    //delete
    public void removeAndAddContacts(ArrayList<ContactClass> contactsList){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("delete from tbl_numbers");
        System.out.println("Deleted");
        addContacts(contactsList);
    }

    public ArrayList<ContactClass> searchContacts(String newText) {
        ArrayList<ContactClass> contactList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from tbl_numbers where name LIKE '%"+newText+"%'", null);
        Log.d("Cursor", "" + cursor.getCount());

        if (cursor.moveToFirst()) {
            do {
                ContactClass contact = new ContactClass();
                contact.id = cursor.getInt(0);
                contact.name = cursor.getString(1);
                contact.number = cursor.getString(2);

                contactList.add(contact);
            } while (cursor.moveToNext());
        }

        return contactList;
    }

}

