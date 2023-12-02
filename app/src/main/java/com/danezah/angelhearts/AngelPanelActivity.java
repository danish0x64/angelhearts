package com.danezah.angelhearts;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

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

public class AngelPanelActivity extends AppCompatActivity implements AngelAdapter.OnAngelClickListener {

    private RecyclerView recyclerView;
    private AngelAdapter angelAdapter;
    private TextView noAngelsText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_angel_panel);

        recyclerView = findViewById(R.id.recyclerView);
        noAngelsText = findViewById(R.id.noAngelsText);

        // Set up RecyclerView
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        angelAdapter = new AngelAdapter(this);

        recyclerView.setAdapter(angelAdapter);

        // Set the OnAngelClickListener
        angelAdapter.setOnAngelClickListener(this);

        // Fetch Angels from Firestore
        fetchAngels();
    }

    private void fetchAngels() {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("users")
                //  .whereEqualTo("userType", "Angel")
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
                            Log.e("AngelPanelActivity", "Error getting Angels", task.getException());
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
        startChatWithAngel(angelName);
    }

    private void startChatWithAngel(String angelName) {
        // Implement your logic to start a chat activity with the selected angel
        // You can pass the angelName to the chat activity using Intent
        // Example: Intent chatIntent = new Intent(this, ChatActivity.class);
        // chatIntent.putExtra("angelName", angelName);
        // startActivity(chatIntent);
    }
}
