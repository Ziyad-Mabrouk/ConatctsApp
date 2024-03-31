package com.example.conatcts;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class ModifyActivity extends AppCompatActivity {

    EditText firstName, lastName, phoneNumber, emailAdress;
    Button saveButton, cancelButton;

    DataBaseHelper dataBaseHelper = new DataBaseHelper(ModifyActivity.this);
    List<Contact> contactList = new ArrayList<>();

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify);

        firstName = findViewById(R.id.firstName);
        lastName = findViewById(R.id.lastName);
        phoneNumber = findViewById(R.id.phoneNumber);
        emailAdress = findViewById(R.id.emailAdress);

        Intent intent = getIntent();
        Intent intentBack = new Intent(ModifyActivity.this, MainActivity.class);

        int id = intent.getIntExtra("id", -1);
        contactList = dataBaseHelper.getAllContacts();
        Contact contact = new Contact();

        if (id >= 0){
            for (Contact c : contactList) {
                if (id == c.getId()) {
                    contact = c;
                    break;
                }
            }
            firstName.setText(contact.getFirstName());
            lastName.setText(contact.getLastName());
            phoneNumber.setText(contact.getPhoneNumber());
            emailAdress.setText(contact.getEmailAdress());
        } else {
            startActivity(intentBack);
        }

        cancelButton = findViewById(R.id.cancelModifyButton);
        cancelButton.setOnClickListener(view -> startActivity(intentBack));

        saveButton = findViewById(R.id.saveButton);
        saveButton.setOnClickListener(view -> {
            Contact modifiedContact = new Contact(id, firstName.getText().toString(), lastName.getText().toString(), phoneNumber.getText().toString(), emailAdress.getText().toString());
            boolean isNotModified = dataBaseHelper.modifyContact(modifiedContact);

            if (isNotModified) {
                Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Contact Modified Successfully", Toast.LENGTH_SHORT).show();
            }

            startActivity(intentBack);
        });

    }
}