package com.example.movie_seat;

public class Theatre_model {
    String TheatreName, TheatreType, OpenHours, TheatreDescribtion, Image;

    public Theatre_model() {

    }

    public Theatre_model(String theatreName, String theatreType, String openHours, String theatreDescribtion, String image) {
        this.TheatreName = theatreName;
        this.TheatreType = theatreType;
        this.OpenHours = openHours;
        this.TheatreDescribtion = theatreDescribtion;
        this.Image = image;
    }

    public String getTheatreName() {
        return TheatreName;
    }

    public void setTheatreName(String theatreName) {
        TheatreName = theatreName;
    }

    public String getTheatreType() {
        return TheatreType;
    }

    public void setTheatreType(String theatreType) {
        TheatreType = theatreType;
    }

    public String getOpenHours() {
        return OpenHours;
    }

    public void setOpenHours(String openHours) {
        OpenHours = openHours;
    }

    public String getTheatreDescribtion() {
        return TheatreDescribtion;
    }

    public void setTheatreDescribtion(String theatreDescribtion) {
        TheatreDescribtion = theatreDescribtion;
    }

    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        Image = image;
    }
}