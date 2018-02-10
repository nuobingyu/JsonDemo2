package com.example.jsondemo;

import android.app.ProgressDialog;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import com.example.jsondemo.Json.Movie;
import com.example.jsondemo.util.HttpUtil;
import com.google.gson.Gson;
import java.io.IOException;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;
/**
 * Created by 96274 on 2018/1/23.
 */

public class Fragment1 extends Fragment {

    private boolean isLoad = true;

    private RecyclerView recyclerView;

    private ImageView img;

    private ProgressDialog progressDialog;

    private MyRecyclerViewAdapter adapter ;

    private SwipeRefreshLayout refreshLayout;

    private static final String URL_main = "https://api.douban.com/v2/movie/in_theaters";

    public Fragment1() {}

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view1 = inflater.inflate(R.layout.item1,container,false   );
        img =(ImageView) view1.findViewById(R.id.movie_pic);
        View view = inflater.inflate(R.layout.fragment1,container ,false );
        refreshLayout =(SwipeRefreshLayout)view.findViewById(R.id.swipe_refresh);
        LinearLayoutManager manager = new LinearLayoutManager(getActivity());
        recyclerView = (RecyclerView)view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(manager);
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener( ) {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if(newState == 0 || newState == 2){
                    isLoad = false ;
                }else if(newState == 1){
                    isLoad = true;
                }
            }
        });
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener( ) {
            @Override
            public void onRefresh() {
                refreshLayout.setRefreshing(true);
                adapter.notifyDataSetChanged();
                refreshLayout.setRefreshing(false);
            }
        });
        if(isLoad) {
            showPressDialog();
            sendRequestWithOkHttp( );
        }
        return view;
    }

    private void sendRequestWithOkHttp(){
        HttpUtil.sendOkHttpRequest(URL_main, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String responseText = response.body().string();
                final Movie movie= new Gson().fromJson(responseText , Movie.class);
                getActivity().runOnUiThread(new Runnable( ) {
                    @Override
                    public void run() {
                        adapter = new MyRecyclerViewAdapter(movie.subjects ,getContext());
                        recyclerView.setAdapter(adapter);
                        refreshLayout.setRefreshing(false);
                        closeProgressDialog();
                    }
                });
            }
        });
    }

    private void showPressDialog(){
        if(progressDialog == null){
            progressDialog = new ProgressDialog(getActivity());
            progressDialog.setMessage("正在加载...");
            progressDialog.setCanceledOnTouchOutside(false);
        }
        progressDialog.show();
    }

    private void closeProgressDialog(){
        if(progressDialog != null){
            progressDialog.dismiss();
        }
    }
}
