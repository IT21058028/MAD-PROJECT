package com.example.movie_seat;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

public class Descfragment_Movies extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;
    String name, date, desc, img;
    public Descfragment_Movies() {

    }

    public Descfragment_Movies(String name, String date, String desc, String img) {
        this.name=name;
        this.date=date;
        this.desc=desc;
        this.img=img;
    }

    public static Descfragment_Movies newInstance(String param1, String param2) {
        Descfragment_Movies fragment1 = new Descfragment_Movies();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment1.setArguments(args);
        return fragment1;
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

        View view=inflater.inflate(R.layout.activity_descfragment_movies, container, false);

        ImageView imageholder=view.findViewById(R.id.imagegholder);
        TextView nameholder=view.findViewById(R.id.nameholder);
        TextView dateholder=view.findViewById(R.id.dateholder);
        TextView descholder=view.findViewById(R.id.descholder);

        nameholder.setText(name);
        dateholder.setText(date);
        descholder.setText(desc);
        Glide.with(getContext()).load(img).into(imageholder);


        return  view;
    }

    public void onBackPressed()
    {
        AppCompatActivity activity=(AppCompatActivity)getContext();
        activity.getSupportFragmentManager().beginTransaction().replace(R.id.wrapper1,new Refragment_Movies()).addToBackStack(null).commit();

    }
}