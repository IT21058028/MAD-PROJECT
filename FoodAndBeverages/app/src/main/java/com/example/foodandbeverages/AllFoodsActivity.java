package com.example.foodandbeverages;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Toast;

import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class AllFoodsActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    FirebaseFirestore db;
    ArrayList<FoodModel> foods;
    FoodsAdminAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_foods);

        db = FirebaseFirestore.getInstance();
        foods  = new ArrayList<FoodModel>();
        adapter = new FoodsAdminAdapter(this,foods);
        recyclerView = findViewById(R.id.allFoodsAdminListView);
        recyclerView.setAdapter(adapter);

        db.collection("foods").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                if(error == null){
                    for(DocumentSnapshot snapshot:value){
                        FoodModel foodModel = new FoodModel(
                                snapshot.getId(),
                                snapshot.getString("name"),
                                snapshot.getString("imageUrl"),
                                snapshot.getDouble("price"),
                                snapshot.getBoolean("inStock")
                        );
                        foods.add(foodModel);
                        adapter.notifyDataSetChanged();
                    }
                }else{
                    Toast.makeText(AllFoodsActivity.this, "Fetch data from db failed " + error, Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}