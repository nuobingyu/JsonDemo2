package com.example.jsondemo.Json;

import android.widget.ListView;

import com.google.gson.annotations.SerializedName;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by 96274 on 2018/1/24.
 */

public class Movie {

    public int count;

    public int start;

    public int total;

    public List<JSubject> subjects = new ArrayList<>();
}
