package com.danezah.angelhearts;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MySpaceActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_space);

        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();

        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
            // User is logged in, redirect to ForumActivity
            Intent intent = new Intent(MySpaceActivity.this, ForumActivity.class);
            startActivity(intent);
            finish();
        } else {
            // User is not logged in, redirect to LoginActivity
            Intent intent = new Intent(MySpaceActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
        }
        if (currentUser != null) {
            String userId = currentUser.getUid();
            DatabaseReference userRef = mDatabase.child("users").child(userId);

            userRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()) {
                        String userType = dataSnapshot.child("userType").getValue(String.class);
                        if (userType != null) {
                            if (userType.equals("Angel")) {
                                // User is registered as "Angel", redirect to AngelPanelActivity
                                Intent intent = new Intent(MySpaceActivity.this, AngelPanelActivity.class);
                                startActivity(intent);
                                finish();
                            } else if (userType.equals("HelpSeeker")) {
                                // User is registered as "HelpSeeker", redirect to HelpSeekerActivity
                                Intent intent = new Intent(MySpaceActivity.this, HelpseekerActivity.class);
                                startActivity(intent);
                                finish();
                            } else {
                                // User is not registered as "Angel" or "HelpSeeker", redirect to ForumActivity
                                Intent intent = new Intent(MySpaceActivity.this, ForumActivity.class);
                                startActivity(intent);
                                finish();
                            }
                        }
                    } else {
                        // User data does not exist, redirect to ForumActivity
                        Intent intent = new Intent(MySpaceActivity.this, ForumActivity.class);
                        startActivity(intent);
                        finish();
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    // Handle database error if needed
                }
            });
        } else {
            // User is not logged in, redirect to LoginActivity
            Intent intent = new Intent(MySpaceActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
        }
    }
}
