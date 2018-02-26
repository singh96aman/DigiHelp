package com.example.amansinghthakur.pdf_reader.careforu;


import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.amansinghthakur.pdf_reader.R;

public class MainActivity extends AppCompatActivity implements Home.OnFragmentInteractionListener {

    public ImageView iv;
    TextView tv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        Bitmap bitmap = BitmapFactory.decodeResource(getResources(),R.mipmap.ic_launcher);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle("DegiHelp");

        Intent intent = getIntent();
        if(intent.getExtras()!=null)
        {
            if(intent.getExtras().getInt("id")!=0)
            {
                int i = intent.getExtras().getInt("id");
                String s=intent.getExtras().getString("text");
                Home hm = new Home();
                getSupportFragmentManager().beginTransaction().replace(R.id.container, hm).commit();
            }
            else {
                FragmentReminder rm = new FragmentReminder();
                getSupportFragmentManager().beginTransaction().replace(R.id.container, rm).commit();
                SharedPreferences sp=getSharedPreferences("Shpr", Context.MODE_PRIVATE);
            }

        }
        else {
            Home hm = new Home();
            getSupportFragmentManager().beginTransaction().replace(R.id.container, hm).commit();

            SharedPreferences sp=getSharedPreferences("Shpr", Context.MODE_PRIVATE);
            Boolean loggedin=sp.getBoolean("logged",false);
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
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

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
