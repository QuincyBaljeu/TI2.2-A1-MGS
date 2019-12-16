package com.example.ti22_a1_mgs.Boundaries;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.ti22_a1_mgs.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.Locale;

public class HelpActivity extends AppCompatActivity {

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);

        Toolbar toolbar = findViewById(R.id.custom_action_bar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setTitle(R.string.name_help_activity);

        TextView helpText = findViewById(R.id.text_helpMenu);

        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.nav_view_main);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {

            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {

                    case R.id.navigation_home:
                        finish();
                        break;

                }
                return true;
            }

        });

        if (Locale.getDefault().getLanguage().equals("nl")) {

            helpText.setText("Selecteer het route menu \n" +
                    "Selecteer een route uit de lijst \n" +
                    "Open het kaart menu \n" +
                    "Volg de route");

        } else if (Locale.getDefault().getLanguage().equals("en")) {

            helpText.setText("Select the routes menu \n" +
                    "Select a route from the list \n" +
                    "Open the map menu \n" +
                    "Follow the given route!");

        } else {
            helpText.setText("Select the routes menu \n" +
                    "Select a route from the list \n" +
                    "Open the map menu \n" +
                    "Follow the given route!");
        }
    }
}
