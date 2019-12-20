package com.example.crimereporter;

public class Post {

    String title, description, image, condition, latitude, longitude, type;

    public Post(){

    }

    public Post(String title, String description, String image, String condition, String latitude, String longitude, String type) {
        this.title = title;
        this.description = description;
        this.image = image;
        this.condition = condition;
        this.latitude = latitude;
        this.longitude = longitude;
        this.type = type;
    }
    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }


    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }



    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String image) {
        this.longitude = longitude;
    }



    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String desc) {
        this.description = desc;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
