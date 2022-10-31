package com.example.payment;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class TicketsAdapter extends RecyclerView.Adapter<TicketsAdapter.MyViewHolder> {

    Context context;
    ArrayList<TicketModel> tickets;


    FirebaseFirestore db;

    public TicketsAdapter(Context context, ArrayList<TicketModel> ordersArrayList) {
        this.context = context;
        this.tickets = ordersArrayList;
    }

    @NonNull
    @Override
    public TicketsAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(context).inflate(R.layout.ticket_card, parent, false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull TicketsAdapter.MyViewHolder holder, int position) {
        TicketModel ticketModel = tickets.get(position);
        holder.ticketsType.setText(ticketModel.ticketType);
        holder.numberOfTickets.setText(ticketModel.numberOfSeats);
        holder.seatNumber.setText(ticketModel.seatNumbers);
        holder.totalAmount.setText("Rs " + String.valueOf(ticketModel.totalAmount));
        holder.movieName.setText(ticketModel.fullName);
        db = FirebaseFirestore.getInstance();
        holder.editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

               Intent intent = new Intent(
                       context,PaymentDetails.class
               );
               intent.putExtra("NAME",ticketModel.fullName);
                intent.putExtra("ADDRESS",ticketModel.address);
                intent.putExtra("EMAIL",ticketModel.email);
                intent.putExtra("ID",ticketModel.id);
                intent.putExtra("TOTAL_BILL",String.valueOf(ticketModel.totalAmount));
               context.startActivity(intent);
            }
        });
        holder.deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteItem(ticketModel.id);
                Intent intent = new Intent(context, AllTicketsActivity.class);
                context.startActivity(intent);
            }
        });
        if(ticketModel.status){
            holder.editButton.setVisibility(View.INVISIBLE);
        }

    }

    @Override
    public int getItemCount() {
        return tickets.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView movieName, seatNumber, totalAmount,numberOfTickets,ticketsType;
        Button deleteButton, editButton;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            movieName = itemView.findViewById(R.id.tvMovieName);
            seatNumber = itemView.findViewById(R.id.tvSeatNumber);
            numberOfTickets = itemView.findViewById(R.id.tvNumberOfTickets);
            totalAmount = itemView.findViewById(R.id.tvTotalAmount);
            ticketsType = itemView.findViewById(R.id.tvTicketType);

            editButton = itemView.findViewById(R.id.verifyTicketButton);
            deleteButton = itemView.findViewById(R.id.deleteTicketButton);
        }
    }

    private void deleteItem(String id) {
        db = FirebaseFirestore.getInstance();
        db.collection("tickets").document(id).delete();

    }

}
