package com.example.movie_seat;

public class Movie_model {
    String MovieName, MovieType, MovieDuration, MovieDate, MovieDescribtion, Image;

    public Movie_model() {

    }

    public Movie_model(String movieName, String movieType, String movieDuration, String movieDate, String movieDescribtion, String image) {
        this.MovieName = movieName;
        this.MovieType = movieType;
        this.MovieDuration = movieDuration;
        this.MovieDate = movieDate;
        this.MovieDescribtion = movieDescribtion;
        this.Image = image;
    }

    public String getMovieName() {
        return MovieName;
    }

    public void setMovieName(String movieName) {
        MovieName = movieName;
    }

    public String getMovieType() {
        return MovieType;
    }

    public void setMovieType(String movieType) {
        MovieType = movieType;
    }

    public String getMovieDuration() {
        return MovieDuration;
    }

    public void setMovieDuration(String movieDuration) {
        MovieDuration = movieDuration;
    }

    public String getMovieDate() {
        return MovieDate;
    }

    public void setMovieDate(String movieDate) {
        MovieDate = movieDate;
    }

    public String getMovieDescribtion() {
        return MovieDescribtion;
    }

    public void setMovieDescribtion(String movieDescribtion) {
        MovieDescribtion = movieDescribtion;
    }

    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        Image = image;
    }
}