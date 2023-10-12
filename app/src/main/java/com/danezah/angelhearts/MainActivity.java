package com.danezah.angelhearts;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;

import androidx.core.view.WindowCompat;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.danezah.angelhearts.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {


    private boolean isLoggedIn = false; // Assuming this variable determines if the user is logged in or not

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Check if the user is logged in
        if (isLoggedIn) {
            // User is logged in, redirect to MySpaceActivity
            Intent intent = new Intent(MainActivity.this, MySpaceActivity.class);
            startActivity(intent);
            finish(); // Optional: Finish the current activity so the user can't go back to it
        } else {
            // User is not logged in, redirect to LoginActivity
            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(intent);
            finish(); // Optional: Finish the current activity so the user can't go back to it
        }
    }
}