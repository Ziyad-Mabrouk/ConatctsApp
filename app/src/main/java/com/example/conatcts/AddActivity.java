package com.example.conatcts;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class AddActivity extends AppCompatActivity {

    EditText firstName, lastName, phoneNumber, emailAdress;
    Button addNewButton, cancelButton;

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        firstName = findViewById(R.id.firstName);
        lastName = findViewById(R.id.lastName);
        phoneNumber = findViewById(R.id.phoneNumber);
        emailAdress = findViewById(R.id.emailAdress);

        addNewButton = findViewById(R.id.saveButton);
        addNewButton.setOnClickListener(view -> {
            try {
                Contact newContact = new Contact(-1, firstName.getText().toString(), lastName.getText().toString(), phoneNumber.getText().toString(), emailAdress.getText().toString());
                DataBaseHelper dataBaseHelper = new DataBaseHelper(AddActivity.this);

                boolean isAdded = dataBaseHelper.addContact(newContact);
                if (isAdded) {
                    Toast.makeText(AddActivity.this, "New Contact Added Successfully", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(AddActivity.this, MainActivity.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(AddActivity.this, "Error", Toast.LENGTH_SHORT).show();
                }

            } catch (Exception e) {
                Toast.makeText(AddActivity.this, "Error", Toast.LENGTH_SHORT).show();
            }

        });
        cancelButton = findViewById(R.id.cancelAddButton);
        cancelButton.setOnClickListener(view -> {
            Intent intent = new Intent(AddActivity.this, MainActivity.class);
            startActivity(intent);
        });

    }
}