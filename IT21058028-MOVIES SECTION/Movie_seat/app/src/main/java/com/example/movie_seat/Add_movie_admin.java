package com.example.movie_seat;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.Calendar;

public class Add_movie_admin extends AppCompatActivity {

    ImageButton imageButton;
    EditText Movie_name,Movie_type,Movie_duration,Movie_date,Movie_describtion;
    DatePickerDialog.OnDateSetListener setListener;
    Button Add_movie;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    StorageReference storageReference;
    private static final int Gallery_code=1;
    private Uri imageUri=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_movie_admin);

        imageButton = findViewById(R.id.imageButton_add);
        Movie_name = findViewById(R.id.login_email);
        Movie_type = findViewById(R.id.theatre_type);
        Movie_duration = findViewById(R.id.login_password);

        Movie_date = findViewById(R.id.movie_date);

        Calendar calendar = Calendar.getInstance();
        final int year = calendar.get(Calendar.YEAR);
        final int month = calendar.get(Calendar.MONTH);
        final int day = calendar.get(Calendar.DAY_OF_MONTH);

        Movie_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        Add_movie_admin.this, android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                        setListener,year,month,day);
                datePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                datePickerDialog.show();
            }
        });

setListener = new DatePickerDialog.OnDateSetListener() {
    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

        month = month+1;
        String date = day+"/"+month+"/"+year;
        Movie_date.setText(date);
    }
};



        Movie_describtion = findViewById(R.id.Theatre_describtion);

        Add_movie = findViewById(R.id.add_movie);

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("Movies");
        storageReference = FirebaseStorage.getInstance().getReference();

        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                startActivityForResult(intent,Gallery_code);


            }
        });


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode== Gallery_code && resultCode == RESULT_OK)
        {
            imageUri = data.getData();
            imageButton.setImageURI(imageUri);
    }
        Add_movie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name= Movie_name.getText().toString().trim();
                String type= Movie_type.getText().toString().trim();
                String duration= Movie_duration.getText().toString().trim();
                String date= Movie_date.getText().toString().trim();
                String describtion= Movie_describtion.getText().toString().trim();

                if(name.isEmpty()){
                    Movie_name.setError("Movie Name Required");
                    Movie_name.requestFocus();
                    return;
                }
                   if(type.isEmpty()){
                    Movie_type.setError("Movie Type Required");
                    Movie_type.requestFocus();
                    return;
                }
                      if(duration.isEmpty()){
                    Movie_duration.setError("Movie Duration Required");
                    Movie_duration.requestFocus();
                    return;
                }
                         if(date.isEmpty()){
                    Movie_date.setError("Movie Release Date Required");
                    Movie_date.requestFocus();
                    return;
                }

                            if(describtion.isEmpty()){
                    Movie_describtion.setError("Movie Description Required");
                    Movie_describtion.requestFocus();
                    return;
                }

                if(!name.isEmpty() && !type.isEmpty() && !duration.isEmpty() && !date.isEmpty() && !describtion.isEmpty() && imageUri!=null){
Toast.makeText(Add_movie_admin.this,"not sucess",Toast.LENGTH_SHORT).show();
                    StorageReference filepath = storageReference.child("Post_image").child(imageUri.getLastPathSegment());
                    filepath.putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                            Task<Uri> downloadurl = taskSnapshot.getStorage().getDownloadUrl().addOnCompleteListener(new OnCompleteListener<Uri>() {
                                @Override
                                public void onComplete(@NonNull Task<Uri> task) {
                                    String t = task.getResult().toString();
                                    DatabaseReference newpost = databaseReference.push();
                                    newpost.child("MovieName").setValue(name);
                                    newpost.child("MovieType").setValue(type);
                                    newpost.child("MovieDuration").setValue(duration);
                                    newpost.child("MovieDate").setValue(date);
                                    newpost.child("MovieDescribtion").setValue(describtion);
                                    newpost.child("Image").setValue(task.getResult().toString());

                                    if (task.isSuccessful()){
                                    Toast.makeText(Add_movie_admin.this, "Movie Added Successful", Toast.LENGTH_SHORT).show();
                                }
                                else{
                                    Toast.makeText(Add_movie_admin.this, "Movie Added Not Sucessfull",Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                        }
                    });
                }

            }
        });

}
}