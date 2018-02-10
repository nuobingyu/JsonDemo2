package com.example.jsondemo.Json;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 96274 on 2018/1/24.
 */

public class JSubject {

    public Rating rating;

    public List<String> genres = new ArrayList<>();

    public String title;

    public List<Cast> casts = new ArrayList<>();

    public String id;

    public static class Rating{

        public double average;
    }
}
