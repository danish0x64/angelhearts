package com.danezah.angelhearts;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class HelpseekerActivity extends AppCompatActivity implements AngelAdapter.OnAngelClickListener {

    private RecyclerView recyclerView;
    private AngelAdapter angelAdapter;
    private TextView noAngelsText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help_seeker);

        recyclerView = findViewById(R.id.recyclerView);
        noAngelsText = findViewById(R.id.noAngelsText);

        // Set up RecyclerView
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        angelAdapter = new AngelAdapter(this);
        recyclerView.setAdapter(angelAdapter);

        // Fetch Angels from Firestore
        fetchAngels();
    }

    private void fetchAngels() {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("users")
                .whereEqualTo("userType", "Angel")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            List<String> angels = new ArrayList<>();
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                angels.add(document.getString("username")); // Change "username" to the field you want to display
                            }

                            if (angels.isEmpty()) {
                                showNoAngelsMessage();
                            } else {
                                showAngels(angels);
                            }
                        } else {
                            Log.e("HelpseekerActivity", "Error getting Angels", task.getException());
                        }
                    }
                });
    }

    private void showAngels(List<String> angels) {
        angelAdapter.setAngels(angels);
        recyclerView.setVisibility(View.VISIBLE);
        noAngelsText.setVisibility(View.GONE);
    }

    private void showNoAngelsMessage() {
        recyclerView.setVisibility(View.GONE);
        noAngelsText.setVisibility(View.VISIBLE);
    }

    @Override
    public void onAngelClick(String angelName) {
        // Handle the click event, for example, start a chat activity
        Intent chatIntent = new Intent(this, ChatActivity.class);
        chatIntent.putExtra("angelName", angelName);
        startActivity(chatIntent);
        startChatWithAngel(angelName);
    }

    private void startChatWithAngel(String angelName) {
        // Implement your logic to start a chat activity with the selected angel
        // You can pass the angelName to the chat activity using Intent
        Toast.makeText(this, "Starting chat with " + angelName, Toast.LENGTH_SHORT).show();
        // Example: Intent chatIntent = new Intent(this, ChatActivity.class);
        // chatIntent.putExtra("angelName", angelName);
        // startActivity(chatIntent);
    }
}
