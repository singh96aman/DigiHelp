package com.example.amansinghthakur.pdf_reader.careforu;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import com.example.amansinghthakur.pdf_reader.R;
/**
 * Created by Rishab on 1/3/2017.
 */

public class FirstAidContentAdapter extends FragmentStatePagerAdapter {

    int tabno;
    public FirstAidContentAdapter(FragmentManager fm, int t)
    {
        super(fm);
        tabno=t;
    }
    @Override
    public Fragment getItem(int position) {
        switch(position)
        {
            case 0: FirstAidTips firstAidTips= new FirstAidTips();
                return firstAidTips;
            case 1: FirstAidOffline firstAidOffline= new FirstAidOffline();
                return firstAidOffline;
            default: return null;
        }
    }

    @Override
    public int getCount() {
        return tabno;
    }
}
