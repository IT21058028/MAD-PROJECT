package com.example.payment;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;

public class paymentMethod extends AppCompatActivity {


    TicketModel ticketModel;
    Button cancelButton,payButton;
    EditText cardNumber,expDate,cvc;
    ConstantValues constantValues;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_method);

        cancelButton = findViewById(R.id.payViewBackButton);
        payButton = findViewById(R.id.payViewPayButton);

        cardNumber = findViewById(R.id.cardNameInputField);
        expDate = findViewById(R.id.cardNumberInputField);
        cvc = findViewById(R.id.cvcInputField);


        constantValues = new ConstantValues();


        System.out.println(getIntent().getLongExtra(constantValues.numOfTicketsIntent,0));





        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        payButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ticketModel = new TicketModel(
                        "",
                        getIntent().getStringExtra(constantValues.movieName) ,
                        getIntent().getStringExtra(constantValues.ticketType),
                        getIntent().getStringExtra(constantValues.numOfTicketsIntent),
                        getIntent().getStringExtra(constantValues.seatNumbersIntent),
                        getIntent().getDoubleExtra(constantValues.totalAmountIntent,0),
                        getIntent().getStringExtra(constantValues.fullName),
                        getIntent().getStringExtra(constantValues.address),
                        getIntent().getStringExtra(constantValues.email),false
                );
                System.out.println(ticketModel.fullName + " " + ticketModel.address + " " +ticketModel.email);
                FirebaseFirestore db = FirebaseFirestore.getInstance();
                db.collection("tickets").document().set(ticketModel).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(paymentMethod.this, "Your ticket placed successfully", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(paymentMethod.this,AllTicketsActivity.class);
                            startActivity(intent);
                        }else{
                            Toast.makeText(paymentMethod.this, "Your ticket placing failed,Try again", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
    }
}