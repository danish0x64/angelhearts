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

        // Fetch Helpseekers from Firestore
        fetchHelpseekers();
    }

    private void fetchHelpseekers() {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("users")
                .whereEqualTo("userType", "HelpSeeker") // Change "HelpSeeker" to the user type you want to fetch
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            List<String> helpseekers = new ArrayList<>();
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                helpseekers.add(document.getString("username")); // Change "username" to the field you want to display
                            }

                            if (helpseekers.isEmpty()) {
                                showNoHelpseekersMessage();
                            } else {
                                showHelpseekers(helpseekers);
                            }
                        } else {
                            Log.e("AngelPanelActivity", "Error getting Helpseekers", task.getException());
                        }
                    }
                });
    }

    private void showHelpseekers(List<String> helpseekers) {
        angelAdapter.setAngels(helpseekers);
        recyclerView.setVisibility(View.VISIBLE);
        noAngelsText.setVisibility(View.GONE);
    }

    private void showNoHelpseekersMessage() {
        recyclerView.setVisibility(View.GONE);
        noAngelsText.setVisibility(View.VISIBLE);
    }

    @Override
    public void onAngelClick(String helpseekerName) {
        // Handle the click event, for example, start a chat activity
        Intent chatIntent = new Intent(this, ChatActivity.class);
        chatIntent.putExtra("helpseekerName", helpseekerName);
        startActivity(chatIntent);
        startChatWithHelpseeker(helpseekerName);
    }

    private void startChatWithHelpseeker(String helpseekerName) {
        // Implement your logic to start a chat activity with the selected helpseeker
        // You can pass the helpseekerName to the chat activity using Intent
        Toast.makeText(this, "Starting chat with " + helpseekerName, Toast.LENGTH_SHORT).show();
        // Example: Intent chatIntent = new Intent(this, ChatActivity.class);
        // chatIntent.putExtra("helpseekerName", helpseekerName);
        // startActivity(chatIntent);
    }
}
