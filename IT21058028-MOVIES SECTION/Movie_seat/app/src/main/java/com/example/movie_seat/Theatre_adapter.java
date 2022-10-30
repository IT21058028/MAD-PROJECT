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

public class Theatre_adapter extends FirebaseRecyclerAdapter<Theatre_model,Theatre_adapter.myviewholder>
{

    public Theatre_adapter(@NonNull FirebaseRecyclerOptions<Theatre_model> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull myviewholder holder, int position, @NonNull final Theatre_model model) {
        holder.Theatre_name.setText(model.getTheatreName());
        holder.Theatre_type.setText(model.getTheatreType());

        Glide.with(holder.Theatre_img.getContext()).load(model.Image).into(holder.Theatre_img);

        holder.Theatre_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AppCompatActivity activity=(AppCompatActivity)view.getContext();
                activity.getSupportFragmentManager().beginTransaction().replace(R.id.wrapper1,new Descfragment_Movies(model.getTheatreName(),model.getTheatreType(),model.getTheatreDescribtion(),model.getImage())).addToBackStack(null).commit();
            }
        });
    }

    @NonNull
    @Override
    public myviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.singlerowdesign2,parent,false);
        return new myviewholder(view);
    }

    public class myviewholder extends RecyclerView.ViewHolder
    {
        ImageView Theatre_img;
        TextView Theatre_name,Theatre_type,desctext;

        public myviewholder(@NonNull View itemView) {
            super(itemView);

            Theatre_img=itemView.findViewById(R.id.theatre_img);
            Theatre_name=itemView.findViewById(R.id.login_email);
            Theatre_type=itemView.findViewById(R.id.theatre_type);

        }
    }

}
