package com.example.amansinghthakur.pdf_reader;

import android.Manifest;
import android.app.Activity;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.GridView;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    int MY_PERMISSIONS_REQUEST_READ_CONTACTS = 1;
    boolean check = false;
    GridView gridView;
    SQLiteDatabase db;
    static ArrayList<String> stringArrayList;



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //db = openOrCreateDatabase("ZailetDB6",Context.MODE_PRIVATE,null);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               Intent i = new Intent(getApplicationContext(), AskUser.class);
                startActivity(i);
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//            if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
//                    != PackageManager.PERMISSION_GRANTED) {
//
//                requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
//                        MY_PERMISSIONS_REQUEST_READ_CONTACTS);
//                return;
//            }
//        }
        isStoragePermissionGranted();
//        if(true == Manifest.permission.READ_EXTERNAL_STORAGE){}
//        = isStoragePermissionGranted();
        check = false;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                check = true;
            }
        }
        Log.e("CheckStatus", "" + check);

        showInGrid(check, gridView);


//TODO ok wala method hai yeh//
//        this.setListAdapter(new ArrayAdapter(this,
//                android.R.layout.simple_list_item_1, GetFiles(String.valueOf(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)))));
    }


    private void showInGrid(boolean check, GridView gridV) {
        gridV = (GridView) findViewById(R.id.gridView);
        if (check) {
            gridV.setAdapter(new CustomAdapter(MainActivity.this,
                    GetFiles(String.valueOf(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)))));
        } //else {
            //Toast.makeText(this, "Storage Read Permission Denial", Toast.LENGTH_LONG).show();
       // }
    }

    public boolean isStoragePermissionGranted() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_GRANTED) {
                Log.v("Premission", "Permission is granted");
                return true;
            } else {

                Log.v("Permission", "Permission is revoked");
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
                return false;
            }
        } else {
            //permission is automatically granted on sdk<23 upon installation
            Log.v("Premission", "Permission is granted");
            return true;
        }
    }
@Override
public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults){
    super.onRequestPermissionsResult(requestCode,permissions,grantResults);
    showInGrid(true, gridView);
}
    public ArrayList<PDFDoc> GetFiles(String DirectoryPath) {
        ArrayList<PDFDoc> arrayList = new ArrayList<>();
        String file_name = null;
        stringArrayList = new ArrayList<>();
        File f = new File(DirectoryPath);
        //Log.d("aman",DirectoryPath);
        PDFDoc pdfDoc;
        File[] files = f.listFiles();
        if (files.length == 0) {
            Toast.makeText(this, "No File Exists", Toast.LENGTH_SHORT).show();
            return null;
        } else {
            for (int i = 0; i < files.length; i++)
                if (files[i].getName().endsWith(".pdf")) {
                    pdfDoc = new PDFDoc();
                    pdfDoc.setName(files[i].getName());
                    file_name = files[i].getName();
                    pdfDoc.setPath(files[i].getAbsolutePath());
                    arrayList.add(pdfDoc);
                    stringArrayList.add(file_name.toString());
                }

        }
//        Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
        Toast.makeText(this, "File Exists", Toast.LENGTH_SHORT).show();
        Log.e("akshay",arrayList.toString());
        return arrayList;
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
//        return super.onCreateOptionsMenu(menu);
            getMenuInflater().inflate(R.menu.main, menu);
            // Associate searchable configuration with the SearchView
            SearchManager searchManager =
                    (SearchManager) getSystemService(Context.SEARCH_SERVICE);
            SearchView searchView =
                    (SearchView) menu.findItem(R.id.search).getActionView();
            searchView.setSearchableInfo(
                    searchManager.getSearchableInfo(getComponentName()));
            searchView.setIconifiedByDefault(false);
            //Toast.makeText(getApplicationContext(),stringArrayList.toString(),Toast.LENGTH_LONG).show();
            return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id) {
            case R.id.refreshId:
                showInGrid(check, gridView);
                break;
            case R.id.AboutApp:
                Intent intent=new Intent(this,AboutUs.class);
                startActivity(intent);
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            Intent i = new Intent(getApplicationContext(),AskUser.class);
            startActivity(i);
            // Handle the camera action
        }
         else if (id == R.id.nav_share) {
            Intent intent=new Intent(Intent.ACTION_VIEW);
            intent.setData(Uri.parse("https://www.facebook.com"));
            startActivity(intent);

        } else if (id == R.id.nav_send) {
            Toast.makeText(this,"Coming soon..",Toast.LENGTH_LONG).show();

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
