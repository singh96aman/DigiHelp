package com.example.amansinghthakur.pdf_reader;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;


public class CustomAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<PDFDoc> arrayList;
    String name=null;

    public CustomAdapter(Context context, ArrayList<PDFDoc> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
    }

    @Override
    public int getCount() {
        return arrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return arrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.model, parent, false);
        }
        final PDFDoc pdfDoc = (PDFDoc) getItem(position);
        TextView textView = (TextView)convertView.findViewById(R.id.textViewPDF);
        ImageView imageView = (ImageView)convertView.findViewById(R.id.imageViewPDF);

        textView.setText(pdfDoc.getName());

        imageView.setImageResource(R.drawable.pdfimage);

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openPDf(pdfDoc.getPath(),pdfDoc.getName());
            }
        });
        return convertView;
    }

    private void openPDf(String Path, String name){
        Intent i = new Intent(context,pdf.class);
        i.putExtra("PATH",Path);
        i.putExtra("NAME",name);
        context.startActivity(i);
    }
}
