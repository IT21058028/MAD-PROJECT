package com.example.movie_seat;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.FirebaseDatabase;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.ViewHolder;

import java.util.HashMap;
import java.util.Map;

public class Movie_adapter extends FirebaseRecyclerAdapter<Movie_model,Movie_adapter.myviewholder>
{


    public Movie_adapter(@NonNull FirebaseRecyclerOptions<Movie_model> options) {
        super(options);

    }

    @Override
    protected void onBindViewHolder(@NonNull myviewholder holder, @SuppressLint("RecyclerView") int position, @NonNull final Movie_model model) {
        holder.Movie_name.setText(model.getMovieName());
        holder.Movie_date.setText(model.getMovieDate());

        Glide.with(holder.Movie_img.getContext()).load(model.Image).into(holder.Movie_img);

        holder.update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                DialogPlus dialog = DialogPlus.newDialog(holder.Movie_img.getContext())
                        .setContentHolder(new ViewHolder(R.layout.content))
                        .setExpanded(true,1650)
                        .create();
                //dialog.show();

                View view1 = dialog.getHolderView();

                EditText movie_name = view1.findViewById(R.id.update_movie_name);
                EditText movie_type = view1.findViewById(R.id.update_movie_type);
                EditText movie_duration = view1.findViewById(R.id.update_movie_duration);
                EditText movie_release_date = view1.findViewById(R.id.update_release_date);
                EditText movie_describtion = view1.findViewById(R.id.update_movie_describtion);

                Button btnupdate = view1.findViewById(R.id.btnupdate);

                movie_name.setText(model.getMovieName());
                movie_type.setText(model.getMovieType());
                movie_duration.setText(model.getMovieDuration());
                movie_release_date.setText(model.getMovieDate());
                movie_describtion.setText(model.getMovieDescribtion());

                dialog.show();

                btnupdate.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Map<String,Object> map = new HashMap<>();
                        map.put("MovieName",movie_name.getText().toString());
                        map.put("MovieType",movie_type.getText().toString());
                        map.put("MovieDuration",movie_duration.getText().toString());
                        map.put("MovieDate",movie_release_date.getText().toString());
                        map.put("MovieDescribtion",movie_describtion.getText().toString());

                        FirebaseDatabase.getInstance().getReference().child("Movies").child(getRef(position).getKey()).updateChildren(map)
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void unused) {
                                        Toast.makeText(holder.Movie_name.getContext(), "Movie Updated Successfully", Toast.LENGTH_SHORT).show();
                                        dialog.dismiss();
                                    }
                                })

                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Toast.makeText(holder.Movie_name.getContext(), "Movie Updated Not Successfully", Toast.LENGTH_SHORT).show();
                                        dialog.dismiss();
                                    }
                                });
                    }
                });

            }
        });

        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(holder.Movie_name.getContext());
                builder.setTitle("Are You Sure?");
                builder.setMessage("Delete Movie Details");

                builder.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        FirebaseDatabase.getInstance().getReference().child("Movies")
                                .child(getRef(position).getKey()).removeValue();
                    }
                });

                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Toast.makeText(holder.Movie_name.getContext(), "Cancelled", Toast.LENGTH_SHORT).show();
                    }
                });
                builder.show();
            }
        });

        holder.Movie_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AppCompatActivity activity=(AppCompatActivity)view.getContext();
                activity.getSupportFragmentManager().beginTransaction().replace(R.id.wrapper1,new Descfragment_Movies(model.getMovieName(),model.getMovieDate(),model.getMovieDescribtion(),model.getImage())).addToBackStack(null).commit();
            }
        });
    }

    @NonNull
    @Override
    public myviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.singlerowdesign,parent,false);
        return new myviewholder(view);
    }

    public class myviewholder extends RecyclerView.ViewHolder
    {
        ImageView Movie_img,update,delete;
        TextView Movie_name,Movie_date,desctext;

        public myviewholder(@NonNull View itemView) {
            super(itemView);

            Movie_img=itemView.findViewById(R.id.movie_img);
            Movie_name=itemView.findViewById(R.id.movie_name);
            Movie_date=itemView.findViewById(R.id.movie_date);
            update = itemView.findViewById(R.id.edit_movie);
            delete = itemView.findViewById(R.id.delete_movie);
        }
    }

}
