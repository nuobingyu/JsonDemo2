package com.example.jsondemo.Json;

/**
 * Created by 96274 on 2018/2/5.
 */

public class Photo{

    public String thumb;

    public String icon;

    public Author author;

    public String cover;

    class Author {
        public String alt;
        public String avatar;
        public String name;
    }
}
