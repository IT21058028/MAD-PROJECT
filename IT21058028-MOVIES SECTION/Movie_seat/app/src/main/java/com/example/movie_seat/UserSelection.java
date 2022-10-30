package com.example.movie_seat;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class UserSelection extends AppCompatActivity implements View.OnClickListener {

    private Button admin_btn,user_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_selection);

        admin_btn = findViewById(R.id.Admin);
        admin_btn.setOnClickListener(this);

        user_btn = findViewById(R.id.User);
        user_btn.setOnClickListener(this);
    }



    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.User:
                startActivity(new Intent(this,Now_showing.class));
                break;

            case R.id.Admin:
                startActivity(new Intent(this,Admin_login.class));
                break;

        }
    }
}