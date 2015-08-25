package com.example.akrmhrjn.ankurdailer;

import android.content.ContentResolver;
import android.database.Cursor;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 * Created by akrmhrjn on 7/16/15.
 */
public class ContactsActivity extends AppCompatActivity implements SearchView.OnQueryTextListener, SearchView.OnCloseListener,SwipeRefreshLayout.OnRefreshListener {

    private Toolbar toolbar;
    private SearchView searchView;
    private RecyclerView recyclerView;
    private SwipeRefreshLayout swipeRefreshLayout;
    private ArrayList<ContactClass> contactList;
    ArrayList<ContactClass> contactsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.contacts_activity);
        setToolbar();

        searchView = (SearchView) findViewById(R.id.searchView);
        searchView.onActionViewExpanded();
        searchView.setOnQueryTextListener(this);
        searchView.setOnCloseListener(this);
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh_layout);
        //swipeRefreshLayout.setColorSchemeColors(android.R.color.holo_blue_bright, android.R.color.holo_green_light, android.R.color.holo_orange_light, android.R.color.holo_red_light);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        contactList =  new ArrayList<>();
        contactList = DatabaseHelper.getInstance(this).getContacts();


        updateViews();

        swipeRefreshLayout.setOnRefreshListener(this);

        /**
         * Showing Swipe Refresh animation on activity create
         * As animation won't start on onCreate, post runnable is used
         */
        /*swipeRefreshLayout.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        swipeRefreshLayout.setRefreshing(true);

                                        new saveContacts().execute();
                                    }
                                }
        );*/


    }

    /**
     * This method is called when swipe refresh is pulled down
     */
    @Override
    public void onRefresh() {
        new saveContacts().execute();
    }

    private void setToolbar() {
        toolbar = (Toolbar) findViewById(R.id.search_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_search, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if(id == android.R.id.home){
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        contactList = DatabaseHelper.getInstance(this).searchContacts(query);
        updateViews();
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        contactList = DatabaseHelper.getInstance(this).searchContacts(newText);
        updateViews();
        return false;
    }

    private void updateViews() {
        Collections.sort(contactList, new Comparator<ContactClass>() {
            @Override
            public int compare(ContactClass c1, ContactClass c2) {
                return c1.name.compareToIgnoreCase(c2.name);
            }

        });
        SearchAdapter adapter = new SearchAdapter(this, contactList);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public boolean onClose() {
        return false;
    }

    public class saveContacts extends AsyncTask {

        @Override
        protected Void doInBackground(Object[] params) {

            contactsList = new ArrayList<ContactClass>();
            ContentResolver cr = getContentResolver();
            Cursor cur = cr.query(ContactsContract.Contacts.CONTENT_URI, null,
                    null, null, null);
            String phone = null;
            String a = null, temp = null;
            String concat = null;

            if (cur.getCount() > 0) {
                while (cur.moveToNext()) {
                    String id = cur.getString(cur
                            .getColumnIndex(ContactsContract.Contacts._ID));
                    String name = cur
                            .getString(cur
                                    .getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
                    if (Integer
                            .parseInt(cur.getString(cur
                                    .getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER))) > 0) {
                        System.out.println("name : " + name + ", ID : " + id);

                        Cursor pCur = cr.query(
                                ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                                null,
                                ContactsContract.CommonDataKinds.Phone.CONTACT_ID
                                        + " = ?", new String[]{id}, null);
                        while (pCur.moveToNext()) {
                            phone = pCur
                                    .getString(pCur
                                            .getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));

                            temp = phone.replace("-", "");
                            a = temp.replace("+977", "");
                            a = a.replace("1 ","01");

                            /*System.out.println("phone" + a);
                            concat = a + ",(" + name + ")";
                            System.out.println("saba" + concat);
                            contactList.add(concat);*/

                            ContactClass contacts = new ContactClass();
                            contacts.name = name;
                            contacts.number = a;
                            contactsList.add(contacts);
                        }


                        pCur.close();
                    }
                }
                DatabaseHelper.getInstance(ContactsActivity.this).removeAndAddContacts(contactsList);
                //DatabaseHelper.getInstance(ContactsActivity.this).addContacts(contactsList);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if(swipeRefreshLayout.isRefreshing())
                            swipeRefreshLayout.setRefreshing(false);
                        Toast.makeText(ContactsActivity.this, "Contacts updated.", Toast.LENGTH_LONG).show();
                    }
                });

            }
            return null;
        }
    }
}
