package com.example.movie_seat;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
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

public class Add_theatre_admin extends AppCompatActivity {

    ImageButton imageButton;
    EditText Theatre_name,Theatre_type, Open_hours, Theatre_describtion;
    Button Add_Theatre;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    StorageReference storageReference;
    private static final int Gallery_code=1;
    private Uri imageUri1=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_theatre_admin);

        imageButton = findViewById(R.id.imageButton_add);
        Theatre_name = findViewById(R.id.login_email);
        Theatre_type = findViewById(R.id.theatre_type);
        Open_hours = findViewById(R.id.login_password);
        Theatre_describtion = findViewById(R.id.Theatre_describtion);

        Add_Theatre = findViewById(R.id.add_movie);

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("Theatres");
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
            imageUri1 = data.getData();
            imageButton.setImageURI(imageUri1);
        }
        Add_Theatre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name= Theatre_name.getText().toString().trim();
                String type= Theatre_type.getText().toString().trim();
                String open_hours= Open_hours.getText().toString().trim();
                String describtion= Theatre_describtion.getText().toString().trim();

                if(!name.isEmpty() && !type.isEmpty() && !open_hours.isEmpty() && !describtion.isEmpty() && imageUri1!=null){

                    StorageReference filepath = storageReference.child("Post_images").child(imageUri1.getLastPathSegment());
                    filepath.putFile(imageUri1).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                            Task<Uri> downloadurl = taskSnapshot.getStorage().getDownloadUrl().addOnCompleteListener(new OnCompleteListener<Uri>() {
                                @Override
                                public void onComplete(@NonNull Task<Uri> task) {
                                    String t = task.getResult().toString();
                                    DatabaseReference newpost = databaseReference.push();
                                    newpost.child("TheatreName").setValue(name);
                                    newpost.child("TheatreType").setValue(type);
                                    newpost.child("OpenHours").setValue(open_hours);
                                    newpost.child("MovieDescribtion").setValue(describtion);
                                    newpost.child("Image").setValue(task.getResult().toString());
                                    Toast.makeText(Add_theatre_admin.this, "Theatre Added Successful", Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                    });
                }

            }
        });

    }
}