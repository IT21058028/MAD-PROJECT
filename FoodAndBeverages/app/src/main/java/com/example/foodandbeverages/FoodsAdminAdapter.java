package com.example.foodandbeverages;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class FoodsAdminAdapter extends RecyclerView.Adapter<FoodsAdminAdapter.MyViewHolder> {

    Context context;
    ArrayList<FoodModel> foods;


    FirebaseFirestore db;

    public FoodsAdminAdapter(Context context, ArrayList<FoodModel> ordersArrayList) {
        this.context = context;
        this.foods = ordersArrayList;
    }

    @NonNull
    @Override
    public FoodsAdminAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(context).inflate(R.layout.admin_item_card, parent, false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull FoodsAdminAdapter.MyViewHolder holder, int position) {
        FoodModel foodModel = foods.get(position);
        db = FirebaseFirestore.getInstance();
        holder.foodName.setText(foodModel.name);
        holder.foodPrice.setText(String.valueOf(foodModel.price));
        if(foodModel.inStock){
            holder.stockButton.setText("Out of Stock");
        }else{
            holder.stockButton.setText("In Stock");
        }

        Picasso.get().load(foodModel.imageUrl).into(holder.itemImage);

        holder.stockButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //  Intent intent = new Intent(context,EditOrderActivity.class);
                Map data = new HashMap();
                data.put("inStock" , !foodModel.inStock);

                db.collection("foods").document(foodModel.id).update(data).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(context, "Food status updated successfully", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(context, AllFoodsActivity.class);
                            context.startActivity(intent);
                        } else {
                            Toast.makeText(context, "Food status updating failed", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });

        holder.deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteItem(foodModel.id);
                Intent intent = new Intent(context, AllFoodsActivity.class);
                context.startActivity(intent);
            }
        });

        holder.editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, AddEditFoodItem.class);
                intent.putExtra("NAME",foodModel.name);
                intent.putExtra("PRICE",foodModel.price);
                intent.putExtra("IMAGE",foodModel.imageUrl);
                intent.putExtra("ID",foodModel.id);
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return foods.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView foodName, foodPrice;
        Button deleteButton, editButton,stockButton;
        ImageView itemImage;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            foodName = itemView.findViewById(R.id.tvName);
            foodPrice = itemView.findViewById(R.id.tvPrice);
            deleteButton = itemView.findViewById(R.id.deleteButton);
            editButton = itemView.findViewById(R.id.editButton);
            stockButton = itemView.findViewById(R.id.markStockButton);
            itemImage = itemView.findViewById(R.id.itemImage);

        }
    }

    private void deleteItem(String id) {
        db = FirebaseFirestore.getInstance();
        db.collection("foods").document(id).delete();

    }

}
