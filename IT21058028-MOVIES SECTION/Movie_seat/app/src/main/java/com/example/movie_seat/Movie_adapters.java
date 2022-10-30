package com.example.movie_seat;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

public class Movie_adapters extends FirebaseRecyclerAdapter<Movie_model,Movie_adapters.myviewholder>
{

    public Movie_adapters(@NonNull FirebaseRecyclerOptions<Movie_model> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull myviewholder holder, int position, @NonNull final Movie_model model) {
        holder.Movie_name.setText(model.getMovieName());
        holder.Movie_date.setText(model.getMovieDate());

        Glide.with(holder.Movie_img.getContext()).load(model.Image).into(holder.Movie_img);

        holder.Movie_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AppCompatActivity activity=(AppCompatActivity)view.getContext();
                activity.getSupportFragmentManager().beginTransaction().replace(R.id.wrapper2,new Descfragment_Movies(model.getMovieName(),model.getMovieDate(),model.getMovieDescribtion(),model.getImage())).addToBackStack(null).commit();
            }
        });
    }

    @NonNull
    @Override
    public myviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.singlerowdesign3,parent,false);
        return new myviewholder(view);
    }

    public class myviewholder extends RecyclerView.ViewHolder
    {
        ImageView Movie_img;
        TextView Movie_name,Movie_date,desctext;

        public myviewholder(@NonNull View itemView) {
            super(itemView);

            Movie_img=itemView.findViewById(R.id.movie_img);
            Movie_name=itemView.findViewById(R.id.movie_name);
            Movie_date=itemView.findViewById(R.id.movie_date);

        }
    }

}
