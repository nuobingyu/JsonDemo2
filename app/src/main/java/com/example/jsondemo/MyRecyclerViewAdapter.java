package com.example.jsondemo;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.preference.PreferenceManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.jsondemo.Json.JSubject;
import com.example.jsondemo.Json.Pic;
import com.example.jsondemo.util.HttpUtil;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;


public class MyRecyclerViewAdapter extends RecyclerView.Adapter<MyRecyclerViewAdapter.ViewHolder> {

    private List<JSubject> subjects= new ArrayList<>();

    private Context context;

    private Map<ImageView,String > imgMap = new HashMap<>();

    private Map<String , String> urlMap = new HashMap<>();


    public MyRecyclerViewAdapter(List<JSubject> mList ,Context context ){
        this.subjects = mList;
        this.context = context;
    }

    static class ViewHolder extends RecyclerView.ViewHolder{
        private ImageView img;
        private TextView TitleText;
        private TextView fenText;

        public ViewHolder(View view) {
            super(view);
            //System.out.println("进入ViewHolder的构造器了...");
            img = (ImageView) view.findViewById(R.id.movie_pic);
            TitleText = (TextView) view.findViewById(R.id.title_txt);
            fenText = (TextView) view.findViewById(R.id.pingfeng_txt);
        }
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
      //  System.out.println("进入OnCreateViewHolder的方法了...");
        View view = LayoutInflater.from(parent.getContext()).
                inflate(R.layout.item1 , parent ,false);
        ViewHolder holder = new ViewHolder(view);
        view.setTag(holder);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        //System.out.println("进入OnBindViewHolder的方法了...");
        JSubject subject = subjects.get(position);

        //设置标题
        if(subject.title.length() > 7){
            holder.TitleText.setText(subject.title.substring(0,7)+"...");
        }else{
            holder.TitleText.setText(subject.title);
        }
        //设置评分
        if(subject.rating.average == 0.0){
            holder.fenText.setText("尚未有评分");
        }else {
            holder.fenText.setText("" + subject.rating.average + "分");
        }
        //设置图片
        String url_photo = "https://api.douban.com/v2/movie/subject/"
                + subject.id + "/photos?apikey=0b2bdeda43b5688921839c8ecb20399b&start=0&count=100&client=&udid=";

      //  if(imgMap.get(holder.img) == null)
            imgMap.put(holder.img ,url_photo);
        if(urlMap.get(url_photo) == null)
            LoadThread(url_photo);
        if(imgMap.get(holder.img) != null && urlMap.get(imgMap.get(holder.img))!= null){
            Glide.with(context)
                    .load(urlMap.get(imgMap.get(holder.img)))
                    .placeholder(R.mipmap.ic_launcher)
                    .into(holder.img );
        }
    }

    public void LoadThread(final String url){

        new Thread(new Runnable() {
            @Override
            public void run() {
                RequestPhotoURL(url);
            }
        }).start();
    }

    public void RequestPhotoURL(final String url_ph){
        HttpUtil.sendOkHttpRequest(url_ph, new Callback( ) {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String photoText = response.body().string();
                final Pic pic = new Gson().fromJson(photoText , Pic.class);
                urlMap.put(url_ph ,pic.photos.get(0).cover);
            }
        });
    }



    @Override
    public int getItemCount() {
        return subjects.size();
    }
}
