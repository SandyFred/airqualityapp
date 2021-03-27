package com.team01.favouriteservice.dto;

import java.util.ArrayList;
import java.util.List;

public class Location {
    private ArrayList<Double> coordinates;

    public ArrayList<Double> getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(ArrayList<Double> coordinates) {
        this.coordinates = coordinates;
    }
}
