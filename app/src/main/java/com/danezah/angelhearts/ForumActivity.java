package com.danezah.angelhearts;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;

import java.util.HashMap;
import java.util.Map;

public class ForumActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private FirebaseFirestore db;

    private Button angelButton;
    private Button helpSeekerButton;
    private EditText usernameEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forum);

        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        angelButton = findViewById(R.id.angel_button);
        helpSeekerButton = findViewById(R.id.helpSeeker_button);
        usernameEditText = findViewById(R.id.usernameEditText);

        angelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateUserCategory("Angel");
            }
        });

        helpSeekerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateUserCategory("HelpSeeker");
            }
        });
    }

    private void updateUserCategory(String category) {
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
            String userId = currentUser.getUid();
            String username = usernameEditText.getText().toString().trim();

            if (username.isEmpty()) {
                Toast.makeText(ForumActivity.this, "Please enter a username", Toast.LENGTH_SHORT).show();
                return;
            }

            DocumentReference userRef = db.collection("users").document(userId);
            Map<String, Object> userData = new HashMap<>();
            userData.put("userType", category);
            userData.put("username", username);

            userRef.set(userData, SetOptions.merge())
                    .addOnSuccessListener(aVoid -> {
                        redirectToActivity(category);
                    })
                    .addOnFailureListener(e -> {
                        Toast.makeText(ForumActivity.this, "Failed to update user data", Toast.LENGTH_SHORT).show();
                    });
        }
    }

    private void redirectToActivity(String category) {
        if ("Angel".equals(category)) {
            Intent intent = new Intent(this, AngelPanelActivity.class);
            startActivity(intent);
        } else if ("HelpSeeker".equals(category)) {
            Intent intent = new Intent(this, HelpseekerActivity.class);
            startActivity(intent);
        }
    }
}
