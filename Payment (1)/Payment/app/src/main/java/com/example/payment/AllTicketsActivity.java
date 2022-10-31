package com.example.payment;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class AllTicketsActivity extends AppCompatActivity {

    RecyclerView allTicketsListView;
    TicketsAdapter ticketsAdapter;
    ArrayList<TicketModel> tickets;
    FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_tickets);

        allTicketsListView = findViewById(R.id.allTicketsRecyclerView);
        tickets = new ArrayList<TicketModel>();
        db = FirebaseFirestore.getInstance();
        ticketsAdapter = new TicketsAdapter(this,tickets);
        allTicketsListView.setAdapter(ticketsAdapter);

        db.collection("tickets").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                if(error == null){
                    for(DocumentSnapshot snapshot:value){
                        TicketModel ticketModel = new TicketModel(
                                snapshot.getId(),
                                snapshot.getString("movieName"),
                                snapshot.getString("ticketType"),
                                snapshot.getString("numberOfSeats"),
                                snapshot.getString("seatNumbers"),
                                snapshot.getDouble("totalAmount"),
                                snapshot.getString("fullName"),
                                snapshot.getString("address"),
                                snapshot.getString("email"),
                                snapshot.getBoolean("status")
                                );
                        tickets.add(ticketModel);
                        ticketsAdapter.notifyDataSetChanged();
                    }
                }
            }
        });

    }
}