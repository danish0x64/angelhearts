package com.danezah.angelhearts;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.danezah.angelhearts.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ForumActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;

    private Button angelButton;
    private Button helpseekerButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forum);

        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();

        angelButton = findViewById(R.id.angel_button);
        helpseekerButton = findViewById(R.id.helpseeker_button);

        angelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateUserCategory("Angel");
                redirectToAngelPanel();
            }
        });

        helpseekerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateUserCategory("Helpseeker");
                redirectToHelpseekerActivity();
            }
        });
    }

    private void updateUserCategory(String category) {
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
            String userId = currentUser.getUid();
            DatabaseReference userRef = mDatabase.child("users").child(userId).child("userType");
            userRef.setValue(category);
        }
    }

    private void redirectToAngelPanel() {
        // Add code here to redirect to AngelPanelActivity
    }

    private void redirectToHelpseekerActivity() {
        // Add code here to redirect to HelpseekerActivity
    }
}
