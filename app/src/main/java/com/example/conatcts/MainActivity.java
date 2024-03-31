package com.example.conatcts;


import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.SearchView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    FloatingActionButton addButton;
    SearchView serachView;
    RecyclerView recyclerView;
    RecyclerView.Adapter adapter;
    RecyclerView.LayoutManager layoutManager;

    DataBaseHelper dataBaseHelper = new DataBaseHelper(MainActivity.this);
    List<Contact> contactList = new ArrayList<>();
    List<Contact> searchList = new ArrayList<>();
    List<Contact> sortedList = new ArrayList<>();

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
        if(!serachView.isIconified()){
            serachView.setIconified(true);
        } else {
            Toast.makeText(MainActivity.this, "Closed", Toast.LENGTH_SHORT).show();
            finishAffinity();
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getOnBackPressedDispatcher().addCallback(this, new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {

            }
        });

        addButton = findViewById(R.id.addButton);
        addButton.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, AddActivity.class);
            startActivity(intent);
        });

        contactList = dataBaseHelper.getAllContacts();

        recyclerView = findViewById(R.id.recyclerView);
        layoutManager = new LinearLayoutManager(MainActivity.this);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new RecyclerViewAdapter(contactList, MainActivity.this);
        recyclerView.setAdapter(adapter);

        serachView = findViewById(R.id.searchView);
        serachView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                searchList = dataBaseHelper.searchContact(s);

                recyclerView = findViewById(R.id.recyclerView);
                layoutManager = new LinearLayoutManager(MainActivity.this);
                recyclerView.setLayoutManager(layoutManager);
                adapter = new RecyclerViewAdapter(searchList, MainActivity.this);
                recyclerView.setAdapter(adapter);

                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                searchList = dataBaseHelper.searchContact(s);

                recyclerView = findViewById(R.id.recyclerView);
                layoutManager = new LinearLayoutManager(MainActivity.this);
                recyclerView.setLayoutManager(layoutManager);
                adapter = new RecyclerViewAdapter(searchList, MainActivity.this);
                recyclerView.setAdapter(adapter);

                return false;
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int direction = item.getItemId() == R.id.sortAscending ? 0 : 1;

        sortedList = dataBaseHelper.getSorted(direction);
        recyclerView = findViewById(R.id.recyclerView);
        layoutManager = new LinearLayoutManager(MainActivity.this);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new RecyclerViewAdapter(sortedList, MainActivity.this);
        recyclerView.setAdapter(adapter);

        return true;
    }


}