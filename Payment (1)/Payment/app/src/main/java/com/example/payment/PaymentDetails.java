package com.example.payment;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class PaymentDetails extends AppCompatActivity {

    EditText fullName,address,email;
    TextView title,totalBill;
    Button nextButton,cancelButton;
    ConstantValues constantValues;


    String ticketType,movieName;
    String seatNumbers;
    String numOfSeats;
    double totalAmount;

    String nameValue,emailValue,addressValue,totalBillValue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_details);

        constantValues = new ConstantValues();
        ticketType =getIntent().getStringExtra(constantValues.ticketType);
        movieName =getIntent().getStringExtra(constantValues.movieName);
        numOfSeats =  getIntent().getStringExtra(constantValues.numOfTicketsIntent);
        totalAmount = getIntent().getDoubleExtra(constantValues.totalAmountIntent,0);
        seatNumbers = getIntent().getStringExtra(constantValues.seatNumbersIntent);

        fullName = (EditText) findViewById(R.id.personNameInputField);
        address = (EditText) findViewById(R.id.personAddressInputFireld);
        email = (EditText) findViewById(R.id.personEmailInputField);
        title = (TextView) findViewById(R.id.paymentDetailsTextView);
        totalBill = (TextView) findViewById(R.id.totalBillTextView);

        nextButton = (Button) findViewById(R.id.paymentDetailsNextButton);

        if(getIntent().getStringExtra("NAME")!=null){
            nameValue = getIntent().getStringExtra("NAME");
            emailValue = getIntent().getStringExtra("EMAIL");
            addressValue = getIntent().getStringExtra("ADDRESS");
            totalBillValue = String.valueOf(getIntent().getLongExtra("TOTAL_BILL",0));
            fullName.setText(nameValue);
            address.setText(emailValue);
            email.setText(addressValue);
            totalBill.setText("Total Value : "+totalBillValue);
            nextButton.setText("EDIT");

        }else{
            fullName.setText("");
            address.setText("");
            email.setText("");
        }



        cancelButton = (Button) findViewById(R.id.paymetnDetailsCancelButton);

        title.setText("Payment Details");
        totalBill.setText("Total Bill : "+ String.valueOf(getIntent().getDoubleExtra(constantValues.totalAmountIntent,0.0)));

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(getIntent().getStringExtra("NAME")!=null)
                {     String fullNameValue = String.valueOf(fullName.getText());
                    String addressValue = String.valueOf(address.getText());
                    String emailValue = String.valueOf(email.getText());
                    boolean validator = validator(fullNameValue,"Full Name") && validator(addressValue,"Address") && validator(emailValue,"Email");
                    if(validator){

                        Map data = new HashMap();
                        data.put("fullName",fullNameValue);
                        data.put("address",addressValue);
                        data.put("email",emailValue);
                        FirebaseFirestore.getInstance().collection("tickets").document(getIntent().getStringExtra("ID")).update(data).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if(task.isSuccessful()){
                                    Toast.makeText(PaymentDetails.this, "Ticket updated successfully", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(PaymentDetails.this,AllTicketsActivity.class);
                                    startActivity(intent);
                                }else{
                                    Toast.makeText(PaymentDetails.this, "Ticket updating failed", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    }
                }else{
                    submit();
                }


            }
        });
    }

    void submit(){
        String fullNameValue = String.valueOf(fullName.getText());
        String addressValue = String.valueOf(address.getText());
        String emailValue = String.valueOf(email.getText());
        boolean validator = validator(fullNameValue,"Full Name") && validator(addressValue,"Address") && validator(emailValue,"Email");
        if(validator){
            Intent intent = new Intent(PaymentDetails.this,paymentMethod.class);
            intent.putExtra("MOVIE_NAME",movieName);
            intent.putExtra("TICKET_TYPE",ticketType);
            intent.putExtra("NUM_OF_SEATS",numOfSeats);
            intent.putExtra("TOTAL_AMOUNT",totalAmount);
            intent.putExtra("SEAT_NUMBERS",seatNumbers);
            intent.putExtra(constantValues.fullName,fullNameValue);
            intent.putExtra(constantValues.address,addressValue);
            intent.putExtra(constantValues.email,emailValue);
            startActivity(intent);
        }
    }

    boolean validator (String value,String message){
        System.out.println(message +value.length());
        if(value.isEmpty() || value == ""){
            Toast.makeText(this, "Please enter a " +message, Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }
}