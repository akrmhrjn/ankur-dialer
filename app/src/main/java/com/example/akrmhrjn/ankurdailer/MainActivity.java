package com.example.akrmhrjn.ankurdailer;

import android.app.WallpaperManager;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.provider.CallLog;
import android.provider.ContactsContract;
import android.support.v7.app.ActionBarActivity;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;


public class MainActivity extends ActionBarActivity {

    EditText number;
    Button btn1, btn2, btn3, btn4, btn5, btn6, btn7, btn8, btn9, btn0, btnHas, btnAst;
    ImageButton btnDial, btnDel, btnContacts;
    ImageView wall;

    String sum = "";
    ArrayList contactList, nameList;
    ArrayList<ContactClass> contactsList;
    ArrayList<LogClass> logList;
    ListView listView;
    LogAdapter adapter;
    Adapter conAdapter;

    SessionManager session;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialer_activity);

        contactList = new ArrayList();
        logList = new ArrayList();


        WallpaperManager wallpaperManager = WallpaperManager.getInstance(this);
        Drawable wallpaperDrawable = wallpaperManager.getDrawable();


        // Session class instance
       // session = new SessionManager(this);


        // get data from session
/*        HashMap<String, Set> contacts = session.getContacts();

        try {
            Set<String> number = contacts.get(SessionManager.CONTACTS);


            for (String contact : number) {
                contactList.add(contact);
            }
        } catch (NullPointerException err) {}*/




        number = (EditText) findViewById(R.id.etNum);
        btn1 = (Button) findViewById(R.id.btn1);
        btn2 = (Button) findViewById(R.id.btn2);
        btn4 = (Button) findViewById(R.id.btn4);
        btn5 = (Button) findViewById(R.id.btn5);
        btn6 = (Button) findViewById(R.id.btn6);
        btn7 = (Button) findViewById(R.id.btn7);
        btn8 = (Button) findViewById(R.id.btn8);
        btn9 = (Button) findViewById(R.id.btn9);
        btn0 = (Button) findViewById(R.id.btn0);
        btn3 = (Button) findViewById(R.id.btn3);
        btnHas = (Button) findViewById(R.id.btnHas);
        btnAst = (Button) findViewById(R.id.btnAst);
        btnDial = (ImageButton) findViewById(R.id.btnDial);
        btnDel = (ImageButton) findViewById(R.id.btnDel);
        btnContacts = (ImageButton) findViewById(R.id.btnContacts);
        wall = (ImageView) findViewById(R.id.wall);
        listView = (ListView) findViewById(R.id.listView);


        getCallLogs();
        adapter = new LogAdapter(this, logList);
        listView.setAdapter(adapter);



        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                LogClass contact = (LogClass) listView.getAdapter().getItem(position);
                Intent call = new Intent(Intent.ACTION_CALL);
                call.setData(Uri.parse("tel:" + Uri.encode(contact.number)));
                startActivityForResult(call, 1);
            }
        });

        wall.setImageDrawable(wallpaperDrawable);

        number.setInputType(InputType.TYPE_NULL);

        number.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String num = number.getText().toString();
                adapter.getFilter().filter(num);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (sum.equals(""))
                    sum = "1";
                else
                    sum = sum + "1";
                number.setText(sum);
            }
        });

        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (sum.equals(""))
                    sum = "2";
                else
                    sum = sum + "2";
                number.setText(sum);
            }
        });

        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (sum.equals(""))
                    sum = "3";
                else
                    sum = sum + "3";
                number.setText(sum);
            }
        });

        btn4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (sum.equals(""))
                    sum = "4";
                else
                    sum = sum + "4";
                number.setText(sum);
            }
        });
        btn5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (sum.equals(""))
                    sum = "5";
                else
                    sum = sum + "5";
                number.setText(sum);
            }
        });
        btn6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (sum.equals(""))
                    sum = "6";
                else
                    sum = sum + "6";
                number.setText(sum);
            }
        });
        btn7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (sum.equals(""))
                    sum = "7";
                else
                    sum = sum + "7";
                number.setText(sum);
            }
        });
        btn8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (sum.equals(""))
                    sum = "8";
                else
                    sum = sum + "8";
                number.setText(sum);
            }
        });
        btn9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (sum.equals(""))
                    sum = "9";
                else
                    sum = sum + "9";
                number.setText(sum);
            }
        });

        btn0.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (sum.equals(""))
                    sum = "0";
                else
                    sum = sum + "0";
                number.setText(sum);
            }
        });

        btnHas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (sum.equals(""))
                    sum = "#";
                else
                    sum = sum + "#";
                number.setText(sum);
            }
        });
        btnAst.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (sum.equals(""))
                    sum = "*";
                else
                    sum = sum + "*";
                number.setText(sum);
            }
        });

        btnDial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(number.getText().toString()!=null && !number.getText().toString().isEmpty()) {

                    Intent call = new Intent(Intent.ACTION_CALL);
                    call.setData(Uri.parse("tel:" + Uri.encode(number.getText().toString())));
                    startActivityForResult(call, 1);


                }
            }
        });

        btnDel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int length = number.getText().length();
                if (length > 0) {
                    number.getText().delete(length - 1, length);
                    sum = number.getText().toString();
                }

            }
        });

        btnContacts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent next = new Intent(MainActivity.this,ContactsActivity.class);
                startActivityForResult(next,1);
            }
        });

    }

    public String del(String str) {
        if (str.length() > 0 && str.charAt(str.length() - 1) == 'x') {
            str = str.substring(0, str.length() - 1);
        }
        return str;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            new saveContacts().execute();
            return true;
        }

        return super.onOptionsItemSelected(item);
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
                DatabaseHelper.getInstance(MainActivity.this).removeAndAddContacts(contactsList);
                //DatabaseHelper.getInstance(MainActivity.this).addContacts(contactsList);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(MainActivity.this,"Contacts updated.",Toast.LENGTH_LONG).show();
                    }
                });
                //session.clear();
                //session.saveContacts(contactList);
            }
            return null;
        }
    }

    public ArrayList<LogClass> getCallLogs(){

        Uri allCalls = Uri.parse("content://call_log/calls");
        Cursor c = managedQuery(allCalls, null, null, null, null);

        if (c.moveToFirst()) {
            do {
                LogClass log = new LogClass();
                log.number= c.getString(c.getColumnIndex(CallLog.Calls.NUMBER));// for  number
                log.name= c.getString(c.getColumnIndex(CallLog.Calls.CACHED_NAME));// for name
                log.duration = c.getString(c.getColumnIndex(CallLog.Calls.DURATION));// for duration
                log.type = Integer.parseInt(c.getString(c.getColumnIndex(CallLog.Calls.TYPE)));// for call type, Incoming or out going*/
                log.date = c.getString(c.getColumnIndex(CallLog.Calls.DATE));

                logList.add(log);
            } while (c.moveToNext());
        }
        Collections.reverse(logList);
        return logList;

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==1){
            getCallLogs();
            adapter.notifyDataSetChanged();
        }
    }
}



