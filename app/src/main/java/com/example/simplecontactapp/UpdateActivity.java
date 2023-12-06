package com.example.simplecontactapp;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.drawable.IconCompat;

import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class UpdateActivity extends AppCompatActivity {

    EditText name_input, phone_input, birthday_input;

    Button update_button, delete_button;

    String id, name, phone, birthday;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);

        name_input = findViewById(R.id.name_input2);
        phone_input = findViewById(R.id.phone_input2);
        birthday_input = findViewById(R.id.birthday_input2);
        update_button = findViewById(R.id.update_button);
        delete_button = findViewById(R.id.delete_button);

        getAndSetIntentData();

        ActionBar ab = getSupportActionBar();

        if (ab != null) {
            ab.setTitle(name);;
        }
        update_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //And only then we call this
                MyDatabaseHelper myDB = new MyDatabaseHelper(UpdateActivity.this);
                name = name_input.getText().toString().trim();
                phone = phone_input.getText().toString().trim();
                birthday = birthday_input.getText().toString().trim();
                myDB.updateData(id, name, phone, birthday);
                finish();
            }
        });

        delete_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                confirmDialog();
            }
        });


    }


    void getAndSetIntentData(){
        if(getIntent().hasExtra("id") && getIntent().hasExtra("name") &&
                getIntent().hasExtra("phone") && getIntent().hasExtra("birthday")){
            //Getting Data from Intent
            id = getIntent().getStringExtra("id");
            name = getIntent().getStringExtra("name");
            phone = getIntent().getStringExtra("phone");
            birthday = getIntent().getStringExtra("birthday");

            //Setting Intent Data
            name_input.setText(name);
            phone_input.setText(phone);
            birthday_input.setText(birthday);
        }else{
            Toast.makeText(this, "Nenhum contato cadastrado.", Toast.LENGTH_SHORT).show();
        }
    }


    void confirmDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(UpdateActivity.this);
        builder.setTitle("Excluir " + name + "?");
        builder.setMessage("Tem certeza que deseja excluir " + name + "?");
        builder.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                MyDatabaseHelper myDB = new MyDatabaseHelper(UpdateActivity.this);
                myDB.deleteOneRow(id);
                finish();
            }
        });
        builder.setNegativeButton("Não", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        builder.create().show();
    }
}