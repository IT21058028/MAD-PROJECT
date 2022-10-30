package com.example.movie_seat;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class Admin_dashboard extends AppCompatActivity implements View.OnClickListener {

    private ImageView add,edit,profile,theatre;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_dashboard);

        add = findViewById(R.id.imageView_add);
        add.setOnClickListener(this);

        edit = findViewById(R.id.imageView2_edit);
        edit.setOnClickListener(this);

        profile = findViewById(R.id.imageView3_profile);
        profile.setOnClickListener(this);

        theatre = findViewById(R.id.imageView5_theatre);
        theatre.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.imageView_add:
                startActivity(new Intent(this,Add_movie_admin.class));
                break;

            case R.id.imageView2_edit:
                startActivity(new Intent(this,Edit_movie_admin.class));
                break;

            case R.id.imageView3_profile:
                startActivity(new Intent(this, Add_theatre_admin.class));
                break;

            case R.id.imageView5_theatre:
                startActivity(new Intent(this,List_theatre_admin.class));
                break;
        }

    }
}