package com.example.jsondemo.util;

import com.example.jsondemo.Json.Movie;
import com.example.jsondemo.Json.Pic;
import com.google.gson.Gson;
/**
 * Created by 96274 on 2017/11/23.
 */

public class Utility {

    public static Movie handleMovieResponse(String response){
        try{
            System.out.println(response);
            return new Gson().fromJson(response , Movie.class);
        }catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }

    public static Pic handlePicResponse(String response){
        try{
            System.out.println(response);
            return new Gson().fromJson(response , Pic.class);
        }catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }

}
