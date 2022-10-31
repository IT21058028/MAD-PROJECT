package com.example.foodandbeverages;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.firestore.FirebaseFirestore;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.Map;

public class AddEditFoodItem extends AppCompatActivity {

    TextInputEditText name,price,imageUrl;
    FirebaseFirestore db;
    Button addButton,cancelButton,allButton;
    ImageView imageView1,imageView2,imageView3;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit_food_item);

        db = FirebaseFirestore.getInstance();

        name = findViewById(R.id.foodNameField);
        price = findViewById(R.id.priceField);
        imageUrl = findViewById(R.id.phootUrl);

        addButton = findViewById(R.id.saveButton);
        cancelButton = findViewById(R.id.cancelButton);
        allButton = findViewById(R.id.allButton);
        imageView1 = findViewById(R.id.imageView2);
        imageView2 = findViewById(R.id.imageView3);
        imageView3 = findViewById(R.id.imageView4);

        Picasso.get().load("https://upload.wikimedia.org/wikipedia/commons/thumb/a/a3/Eq_it-na_pizza-margherita_sep2005_sml.jpg/800px-Eq_it-na_pizza-margherita_sep2005_sml.jpg").into(imageView1);
        Picasso.get().load("https://therecipecritic.com/wp-content/uploads/2019/07/easy_fried_rice-1.jpg").into(imageView2);
        Picasso.get().load("https://post.healthline.com/wp-content/uploads/2020/08/coffee-worlds-biggest-source-of-antioxidants-1296x728-feature_0-732x549.jpg").into(imageView3);
        if(getIntent().getStringExtra("NAME") !=null){
            name.setText(getIntent().getStringExtra("NAME") );
            price.setText(String.valueOf(getIntent().getDoubleExtra("PRICE",0.0)));
            imageUrl.setText(getIntent().getStringExtra("IMAGE"));

            addButton.setText("Edit");
        }

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean validator = validator(String.valueOf(name.getText()),"Name")
                        && validator(String.valueOf(price.getText()),"Price") && validator(String.valueOf(imageUrl.getText()),"Photo url")
                        ;
               if(validator){
                   if(getIntent().getStringExtra("NAME") !=null){
                       update();
                   }else{
                       add();
                   }
               }
            }
        });

        allButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AddEditFoodItem.this,AllFoodsActivity.class);
                startActivity(intent);
            }
        });

    }

    void update(){
        String id = getIntent().getStringExtra("ID");
        Map data = new HashMap();
        data.put("name",String.valueOf(name.getText()));
        data.put("price",Double.parseDouble(String.valueOf(price.getText())));
        data.put("name",String.valueOf(name.getText()));

        db.collection("foods").document(id).update(data).addOnCompleteListener(new OnCompleteListener() {
            @Override
            public void onComplete(@NonNull Task task) {
                if(task.isSuccessful()){
                    Toast.makeText(AddEditFoodItem.this, "Update food item success", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(AddEditFoodItem.this,AllFoodsActivity.class);
                    startActivity(intent);
                }else{
                    Toast.makeText(AddEditFoodItem.this, "update food item failed", Toast.LENGTH_SHORT).show();

                }
            }
        });
    }

    void add(){

        FoodModel foodModel = new FoodModel(
                "",
                String.valueOf(name.getText()),
                String.valueOf(imageUrl.getText()),
                 Double.parseDouble(String.valueOf(price.getText())),
                true
        );

        db.collection("foods").document().set(foodModel).addOnCompleteListener(new OnCompleteListener() {
            @Override
            public void onComplete(@NonNull Task task) {
                if(task.isSuccessful()){
                    Toast.makeText(AddEditFoodItem.this, "Add food item success", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(AddEditFoodItem.this,AllFoodsActivity.class);
                    startActivity(intent);
                }else{
                    Toast.makeText(AddEditFoodItem.this, "Add food item failed", Toast.LENGTH_SHORT).show();

                }
            }
        });
    }

    boolean validator(String value,String name){
        if(value.isEmpty()){
            Toast.makeText(this, "Please enter "+name, Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }
}