package com.example.movie_seat;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;


public class Admin_register extends AppCompatActivity implements View.OnClickListener {

    private TextView Banner,Login;
    private EditText Name, Email, Password;
    private Button Register;
    private FirebaseAuth mAuth;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_register);



        Register = (Button) findViewById(R.id.register);
        Register.setOnClickListener(this);

        Login = (TextView) findViewById(R.id.text_login);
        Login.setOnClickListener(this);



        Name = (EditText) findViewById(R.id.register_name);
        Email = (EditText) findViewById(R.id.register_email);
        Password = (EditText) findViewById(R.id.register_password);

        mAuth = FirebaseAuth.getInstance();

    }

    @Override
    public void onClick(View view) {

        switch (view.getId()){
            case R.id.text_register:
                startActivity(new Intent(this,Admin_register.class));
                break;

            case R.id.text_login:
                startActivity(new Intent(this,Admin_login.class));
                break;

            case R.id.register:
                UserRegister();
                break;
        }

    }

    private void UserRegister() {

        String name = Name.getText().toString().trim();
        String email = Email.getText().toString().trim();
        String password = Password.getText().toString().trim();

        if(name.isEmpty()){
            Name.setError("Name is required");
            Name.requestFocus();
            return;
        }

        if(email.isEmpty()){
            Email.setError("Email is required");
            Email.requestFocus();
            return;

        }

        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            Email.setError("Provide proper Email Address");
            Email.requestFocus();
            return;

        }

        if(password.isEmpty()){
            Password.setError("Password is required");
            Password.requestFocus();
            return;

        }
        if(password.length()<6){
            Password.setError("Minimum Password Length is 6");
            Password.requestFocus();
            return;
        }


        mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    Admin user = new Admin(name,email);
                    FirebaseDatabase.getInstance().getReference("Admins")
                            .child(FirebaseAuth.getInstance().getUid())
                            .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {

                                    if(task.isSuccessful()){
                                        Toast.makeText(Admin_register.this, "User registered successfully", Toast.LENGTH_SHORT).show();
                                        startActivity(new Intent(Admin_register.this, Admin_dashboard.class));

                                    }else{
                                        Toast.makeText(Admin_register.this, "Registration Failed"+task.getException().getMessage(), Toast.LENGTH_SHORT).show();

                                    }

                                }
                            });
                }else{
                    Toast.makeText(Admin_register.this,"Failed to Register",Toast.LENGTH_LONG).show();

                }
            }
        });
    }
}