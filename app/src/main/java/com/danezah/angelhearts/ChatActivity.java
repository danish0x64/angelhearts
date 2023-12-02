package com.danezah.angelhearts;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class ChatActivity extends AppCompatActivity {

    private String userId;

    // Firebase initialization and chat functionality here

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        userId = getIntent().getStringExtra("userId");

        // Set up chat UI and functionality here
    }
}
