package com.example.payment;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;


public class PaymentActivity extends AppCompatActivity {
    Button movieDetailsNextButton,allTickets;

    String movieName = "Maze Runner";
    String ticketType = "Gold Class";
    String seatNumbers = "2,5";
    int numOfSeats = 2;
    double totalAmount = 1000.0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);
        movieDetailsNextButton = findViewById(R.id.movieDetailsNextButton);
        allTickets = findViewById(R.id.allTicketsButton);


        movieDetailsNextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =  new Intent(PaymentActivity.this,PaymentDetails.class);
                intent.putExtra("TICKET_TYPE",ticketType);
                intent.putExtra("NUM_OF_SEATS",String.valueOf(numOfSeats));
                intent.putExtra("TOTAL_AMOUNT",totalAmount);
                intent.putExtra("SEAT_NUMBERS",seatNumbers);
                intent.putExtra("MOVIE_NAME",movieName);
                startActivity(intent);
            }
        });

        allTickets.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =  new Intent(PaymentActivity.this,AllTicketsActivity.class);

                startActivity(intent);
            }
        });
    }
    }

