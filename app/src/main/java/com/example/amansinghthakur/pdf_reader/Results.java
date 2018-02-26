package com.example.amansinghthakur.pdf_reader;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.example.amansinghthakur.pdf_reader.helper.APIURLS;

import java.util.ArrayList;

public class Results extends AppCompatActivity {

    TextView t1;
    ListView lv;
    Context context;

    ArrayList prgmName;
    public static int [] prgmImages={R.drawable.logo,R.drawable.logo,R.drawable.logo,R.drawable.logo,R.drawable.logo,R.drawable.logo,R.drawable.logo,R.drawable.logo,R.drawable.logo};
    //public static String [] prgmNameList={"Let Us C","c++","JAVA","Jsp","Microsoft .Net","Android","PHP","Jquery","JavaScript"};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);
        //t1 = (TextView) findViewById(R.id.textView4);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        context=this;

        lv=(ListView) findViewById(R.id.listView);
        lv.setAdapter(new CustomAdapterResults(this,APIURLS.arr.toArray(new String[APIURLS.arr.size()]),APIURLS.im.toArray(new String[APIURLS.im.size()])));


       /*String temp = "";
        for(String s : APIURLS.arr)
        {
            temp += s+"\n"+"\n";
        }
        t1.setText(temp);*/
    }

}
