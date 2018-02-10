package com.example.jsondemo;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * Created by 96274 on 2018/1/23.
 */

public class MyFragmentAdapter extends FragmentPagerAdapter {

    private Context context;
    public static final int COUNT_PAGE = 2;
    private String  tabTitles[] = {"正在上映" , "我感兴趣的"};

    public MyFragmentAdapter(FragmentManager fm ,Context context ) {
        super(fm);
        this.context = context;
    }

    @Override
    public Fragment getItem(int position) {
        switch(position){
            case 0: return new Fragment1();
            case 1: return new Fragment2();
            case 2: return null;
            default : return null;
        }
    }

    @Override
    public int getCount() {
        return COUNT_PAGE ;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return tabTitles[position];
    }
}
