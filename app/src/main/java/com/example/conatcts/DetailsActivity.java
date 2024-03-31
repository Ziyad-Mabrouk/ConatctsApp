package com.example.conatcts;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class DetailsActivity extends AppCompatActivity {

    TextView nameText, numberText, emailText;
    Button modifyButton, removeButton;

    List<Contact> contactList = new ArrayList<>();
    DataBaseHelper dataBaseHelper = new DataBaseHelper(DetailsActivity.this);
    Contact contact = new Contact();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        nameText = findViewById(R.id.nameText);
        numberText = findViewById(R.id.numberText);
        emailText = findViewById(R.id.emailText);

        Intent intent = getIntent();
        Intent intentBack = new Intent(DetailsActivity.this, MainActivity.class);

        int id = intent.getIntExtra("id", -1);

        contactList = dataBaseHelper.getAllContacts();

        if (id >= 0) {
            for (Contact c : contactList) {
                if (c.getId() == id) {
                    contact = c;
                    break;
                }
            }
            nameText.setText(contact.getName());
            numberText.setText(contact.getPhoneNumber());
            emailText.setText(contact.getEmailAdress());
        } else {
            // go back
            startActivity(intentBack);
        }

        removeButton = findViewById(R.id.removeButton);
        removeButton.setOnClickListener(view -> {
            boolean isNotDeleted = dataBaseHelper.deleteContact(contact);
            if (isNotDeleted) {
                Toast.makeText(DetailsActivity.this, "Error", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(DetailsActivity.this, "Contact Deleted Successfully", Toast.LENGTH_SHORT).show();
            }
            startActivity(intentBack);
        });

        modifyButton = findViewById(R.id.modifyButton);
        modifyButton.setOnClickListener(view -> {
            Intent intentNext = new Intent(DetailsActivity.this, ModifyActivity.class);
            intentNext.putExtra("id", contact.getId());
            startActivity(intentNext);
        });
    }
}