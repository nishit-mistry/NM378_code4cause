package com.example.phase_1;

public class uploadinfo {

    public String imageName;
    public String locationCoords;
    public uploadinfo(){}

    public uploadinfo(String name, String coords) {
        this.imageName = name;
        this.locationCoords = coords;
    }

    public String getImageName() {
        return imageName;
    }


    public String getLocationCoords() {
        return locationCoords;
    }
}
