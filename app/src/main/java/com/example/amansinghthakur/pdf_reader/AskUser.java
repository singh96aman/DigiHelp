package com.example.amansinghthakur.pdf_reader;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

public class AskUser extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ask_user);
        //Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);


        if (getString(R.string.subscription_key).startsWith("Please")) {
            new AlertDialog.Builder(this)
                    .setTitle(getString(R.string.add_subscription_key_tip_title))
                    .setMessage(getString(R.string.add_subscription_key_tip))
                    .setCancelable(false)
                    .show();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    public void activityAnalyze(View v) {
        Intent intent = new Intent(this, AnalyzeActivity.class);
        startActivity(intent);
    }

    public void activityAnalyzeInDomain(View v) {
        Intent intent = new Intent(this, AnalyzeInDomainActivity.class);
        startActivity(intent);
    }

    public void activityDescribe(View v) {
        Intent intent = new Intent(this, DescribeActivity.class);
        startActivity(intent);
    }

    public void activityHandwriting(View v)
    {
        Intent intent =new Intent(this, HandwritingRecognizeActivity.class);
        startActivity(intent);
    }

    public void activityRecognize(View v) {
        Intent intent = new Intent(this, RecognizeActivity.class);
        startActivity(intent);
    }

    public void activityThumbnail(View v) {
        Intent intent = new Intent(this, ThumbnailActivity.class);
        startActivity(intent);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
