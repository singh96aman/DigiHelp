package com.example.amansinghthakur.pdf_reader;

import android.content.Intent;
import android.graphics.pdf.PdfRenderer;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.github.barteksc.pdfviewer.PDFView;
import com.github.barteksc.pdfviewer.listener.OnLoadCompleteListener;
import com.github.barteksc.pdfviewer.scroll.DefaultScrollHandle;
import com.github.barteksc.pdfviewer.scroll.ScrollHandle;

import java.io.File;




public class pdf extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Intent i = getIntent();
        String path = i.getExtras().getString("PATH");
        String name = i.getExtras().getString("NAME");
        Log.e("sid",name);
        setTitle(name);


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pdf);
        PDFView pdfView = (PDFView) findViewById(R.id.pdfView);
        //DefaultScrollHandle scrollBar = (DefaultScrollHandle) findViewById(R.id.scrollbar);


        File file = new File(path);
        if(file.canRead()){
            pdfView.fromFile(file).defaultPage(1).onLoad(new OnLoadCompleteListener() {
                @Override
                public void loadComplete(int nbPages) {
                    Toast.makeText(pdf.this, ""+nbPages, Toast.LENGTH_SHORT).show();
                }
            }).load();
        }
    }

    /**
     * Go to the given page.
     *
     * @param page Page index.
     */
    /*public void jumpTo(int page, boolean withAnimation) {
        if (file == null) {
            return;
        }

        page = pdfFile.determineValidPageNumberFrom(page);
        float offset = -pdfFile.getPageOffset(page, zoom);
        if (swipeVertical) {
            if (withAnimation) {
                animationManager.startYAnimation(currentYOffset, offset);
            } else {
                moveTo(currentXOffset, offset);
            }
        } else {
            if (withAnimation) {
                animationManager.startXAnimation(currentXOffset, offset);
            } else {
                moveTo(offset, currentYOffset);
            }
        }
        showPage(page);
    }

    public void jumpTo(int page) {
        jumpTo(page, false);
    }

    void showPage(int pageNb) {
        if (recycled) {
            return;
        }

        // Check the page number and makes the
        // difference between UserPages and DocumentPages
        pageNb = pdfFile.determineValidPageNumberFrom(pageNb);
        currentPage = pageNb;

        loadPages();

        if (scrollHandle != null && !documentFitsView()) {
            scrollHandle.setPageNum(currentPage + 1);
        }

        callbacks.callOnPageChange(currentPage, pdfFile.getPagesCount());
    }

*/
}
