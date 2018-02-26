package com.example.amansinghthakur.pdf_reader;

import android.app.Activity;
import android.app.SearchManager;
import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.widget.Toast;

import java.io.Console;
import java.util.ArrayList;

import static android.R.attr.name;
import static android.icu.lang.UCharacter.GraphemeClusterBreak.T;

public class SearchResultsActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.searchable);
        handleIntent(getIntent());
    }

    @Override
    protected void onNewIntent(Intent intent) {

        handleIntent(intent);
    }

    private void handleIntent(Intent intent) {

        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);
            // doQuery(query);
        }
    }

    public void doQuery(String query)
    {
        Intent i = new Intent();
        ArrayList<String> arrayList = new ArrayList<>();
        arrayList =  i.getStringArrayListExtra("file_list");
        for(String name : arrayList)
        {
            Log.d("FILE_NAME" ,name);
            Toast.makeText(getApplicationContext() ,"FILE_NAME" + name.toString() ,Toast.LENGTH_SHORT ).show();
            if(name.toLowerCase().contains(query.toLowerCase()))
            {
                Toast.makeText(getApplicationContext(), "Found " + name + " ! " ,  Toast.LENGTH_SHORT).show();
            }
            else
            {
                Toast.makeText(getApplicationContext(),"Nahi aaya !" , Toast.LENGTH_SHORT).show();
            }

        }
    }

}