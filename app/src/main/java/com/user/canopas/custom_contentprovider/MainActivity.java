package com.user.canopas.custom_contentprovider;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    ArrayList<User> arrayList;
    ListView list;
    UserDatabase userDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        arrayList = new ArrayList<>();
        userDatabase = new UserDatabase(this);
        list = (ListView) findViewById(R.id.list);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, AddUsers.class));

            }
        });

        getUser();


    }

    private void getUser() {
        Cursor cursor = userDatabase.getUsers(null, null, null, null, null);

        while (cursor.moveToNext()) {
            String name = cursor.getString(cursor.getColumnIndex(userDatabase.USERNAME));
            String contact_no = cursor.getString(cursor.getColumnIndex(userDatabase.CONTACT_NO));
            arrayList.add(new User(name, contact_no));

        }
        if (arrayList != null) {
            Cust_adptr adptr = new Cust_adptr(this, arrayList);
            list.setAdapter(adptr);
        }
    }




    private class Cust_adptr extends BaseAdapter {
        Context c;
        ArrayList<User> arrayList;

        public Cust_adptr(Context c, ArrayList<User> arrayList) {
            this.c = c;
            this.arrayList = arrayList;
        }

        @Override
        public int getCount() {
            return arrayList.size();
        }

        @Override
        public Object getItem(int i) {
            return arrayList.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {

            view = LayoutInflater.from(c).inflate(R.layout.rowdesign, viewGroup, false);
            TextView name = view.findViewById(R.id.txt_name);
            TextView contact_no = view.findViewById(R.id.txt_contact_no);
            name.setText(arrayList.get(i).getName());
            contact_no.setText(arrayList.get(i).getContact_no());

            return view;
        }
    }
}
