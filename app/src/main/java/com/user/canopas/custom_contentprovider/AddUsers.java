package com.user.canopas.custom_contentprovider;

import android.content.ContentValues;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class AddUsers extends AppCompatActivity {
    EditText edt_name, edt_contact_no;
    Button add;
    String name, contact_no;
    UserDatabase userDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_users);

        edt_name = (EditText) findViewById(R.id.user_name);
        edt_contact_no = (EditText) findViewById(R.id.contact_no);
        add = (Button) findViewById(R.id.add);
        userDatabase=new UserDatabase(this);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                name = edt_name.getText().toString();
                contact_no = edt_contact_no.getText().toString();
                if (name.isEmpty() && contact_no.isEmpty())
                    Toast.makeText(AddUsers.this, "plz fillup all", Toast.LENGTH_SHORT).show();
                ContentValues values = new ContentValues();
                values.put("USERNAME", name);
                values.put("CONTACT_NO", contact_no);
                userDatabase.addNewUser(values);
                startActivity(new Intent(AddUsers.this,MainActivity.class));
                finish();
            }
        });

    }
}
