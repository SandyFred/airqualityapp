package com.team01.favouriteservice.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class OpenWeather {
    List<OpenData> list;

    public List<OpenData> getList() {
        return list;
    }

    public void setList(List<OpenData> list) {
        this.list = list;
    }
}
