package com.example.akrmhrjn.ankurdailer;

/**
 * Created by akrmhrjn on 1/5/15.
 */

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class SessionManager {
    // Shared Preferences
    SharedPreferences pref;

    // Editor for Shared preferences
    Editor editor;

    // Context
    Context _context;

    // Shared pref mode
    int PRIVATE_MODE = 0;

    // Sharedpref file name
    private static final String PREF_NAME = "AnkurDialer";

    // All Shared Preferences Keys

    private static final String HAS_CONTACTS = "hasContacts";



    public static final String CONTACTS = "contacts";

    // Constructor
    public SessionManager(Context context){
        this._context = context;
        pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }

    /**
     * save contacts
     * */
    public void saveContacts(ArrayList contacts){
        // Storing crop value as TRUE
        editor.putBoolean(HAS_CONTACTS, true);

        //Set the values
        Set<String> set = new HashSet<String>();
        set.addAll(contacts);
        editor.putStringSet(CONTACTS, set);
        editor.commit();

        // commit changes
        editor.commit();
    }



    public Boolean check(){
        // Check status
        if(!this.hasContacts()){

            return false;
        }
        else{
            return true;
        }
    }

    /**
     * Get stored session data
     * */
    public HashMap<String, Set> getContacts(){
        HashMap<String, Set> con = new HashMap<String, Set>();
        con.put(CONTACTS, pref.getStringSet(CONTACTS, null));
        return con;
    }


    public void clear() {
        // Clearing all data from Shared Preferences
        editor.clear();
        editor.commit();
    }


    public boolean hasContacts(){
        return pref.getBoolean(HAS_CONTACTS, false);
    }
}