package com.example.movie_seat;

import android.content.Context;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;


import com.google.firebase.database.FirebaseDatabase;


public class Refragment_Movies extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private EditText glucose_level;
    private String mParam1;
    private String mParam2;
    RecyclerView recview1;
    Movie_adapter adapter1;
    public Refragment_Movies() {

    }

    public static Refragment_Movies newInstance(String param1, String param2) {
        Refragment_Movies fragment = new Refragment_Movies();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view=inflater.inflate(R.layout.activity_refragment_movies, container, false);

        recview1=(RecyclerView)view.findViewById(R.id.recview1);
        recview1.setLayoutManager(new LinearLayoutManager(getContext()));

        FirebaseRecyclerOptions<Movie_model> options =
                new FirebaseRecyclerOptions.Builder<Movie_model>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("Movies"), Movie_model.class)
                        .build();

        adapter1=new Movie_adapter(options);
        recview1.setAdapter(adapter1);


        return view;
    }


    @Override
    public void onStart() {
        super.onStart();
        adapter1.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        adapter1.stopListening();
    }
}