package com.team01.favouriteservice.dto;

public class Response {
    private String country;
    private String state;
    private String city;
    private int aqi;
    private Components pollutants;

    public Response(String country, String state, String city, int aqi, Components pollutants) {
        this.country = country;
        this.state = state;
        this.city = city;
        this.aqi = aqi;
        this.pollutants = pollutants;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public int getAqi() {
        return aqi;
    }

    public void setAqi(int aqi) {
        this.aqi = aqi;
    }

    public Components getPollutants() {
        return pollutants;
    }

    public void setPollutants(Components pollutants) {
        this.pollutants = pollutants;
    }
}
