package com.example.amansinghthakur.pdf_reader;


import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Base64;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.amansinghthakur.pdf_reader.helper.APIURLS;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.microsoft.projectoxford.vision.VisionServiceClient;
import com.microsoft.projectoxford.vision.VisionServiceRestClient;
import com.microsoft.projectoxford.vision.contract.AnalysisInDomainResult;
import com.microsoft.projectoxford.vision.contract.AnalysisResult;
import com.microsoft.projectoxford.vision.contract.Category;
import com.microsoft.projectoxford.vision.contract.Face;
import com.microsoft.projectoxford.vision.rest.VisionServiceException;
import com.example.amansinghthakur.pdf_reader.helper.ImageHelper;

import org.json.JSONObject;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;

public class AnalyzeInDomainActivity extends ActionBarActivity {

    // Flag to indicate which task is to be performed.
    private static final int REQUEST_SELECT_IMAGE = 0;

    // The button to select an image
    private Button mButtonSelectImage, b2;

    // The URI of the image selected to detect.
    private Uri mImageUri;

    // The image selected to detect.
    private Bitmap mBitmap;

    // The edit to show status and result.
    private EditText mEditText;

    private VisionServiceClient client;

    public String BitMapToString(Bitmap bitmap) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
        byte[] b = baos.toByteArray();
        String temp = Base64.encodeToString(b, Base64.DEFAULT);
        return temp;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_analyze_in_domain);
        APIURLS.arr = new ArrayList<String>();
        APIURLS.im = new ArrayList<String>();

        if (client == null) {
            client = new VisionServiceRestClient(getString(R.string.subscription_key), getString(R.string.subscription_apiroot));
        }

        mButtonSelectImage = (Button)findViewById(R.id.buttonSelectImage);
        mEditText = (EditText)findViewById(R.id.editTextResult);
        b2 = (Button) findViewById(R.id.button2);

        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), Results.class);
                startActivity(i);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_analyze, menu);
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

    public void doAnalyze() {
        mButtonSelectImage.setEnabled(false);
        mEditText.setText("Analyzing...");

        try {
            new doRequest().execute();
        } catch (Exception e) {
            mEditText.setText("Error encountered. Exception is: " + e.toString());
        }
    }

    // Called when the "Select Image" button is clicked.
    public void selectImage(View view) {
        mEditText.setText("");

        Intent intent;
        intent = new Intent(AnalyzeInDomainActivity.this, com.example.amansinghthakur.pdf_reader.helper.SelectImageActivity.class);
        startActivityForResult(intent, REQUEST_SELECT_IMAGE);
    }

    // Called when image selection is done.
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.d("AnalyzeInDomainActivity", "onActivityResult");
        switch (requestCode) {
            case REQUEST_SELECT_IMAGE:
                if(resultCode == RESULT_OK) {
                    // If image is selected successfully, set the image URI and bitmap.
                    mImageUri = data.getData();

                    mBitmap = ImageHelper.loadSizeLimitedBitmapFromUri(
                            mImageUri, getContentResolver());
                    if (mBitmap != null) {
                        // Show the image on screen.
                        ImageView imageView = (ImageView) findViewById(R.id.selectedImage);
                        imageView.setImageBitmap(mBitmap);
                        APIURLS.im.add(BitMapToString(mBitmap));

                        // Add detection log.
                        Log.d("AnalyzeInDomainActivity", "Image: " + mImageUri + " resized to " + mBitmap.getWidth()
                                + "x" + mBitmap.getHeight());

                        doAnalyze();
                    }
                }
                break;
            default:
                break;
        }
    }


    private String process() throws VisionServiceException, IOException {
        Gson gson = new Gson();

        String model = "celebrities";

        // Put the image into an input stream for detection.
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        mBitmap.compress(Bitmap.CompressFormat.JPEG, 100, output);
        ByteArrayInputStream inputStream = new ByteArrayInputStream(output.toByteArray());

        AnalysisInDomainResult v = this.client.analyzeImageInDomain(inputStream, model);
        String result = gson.toJson(v);
        Log.d("result", result);

        return result;
    }

    private class doRequest extends AsyncTask<String, String, String> {
        // Store error message
        private Exception e = null;

        public doRequest() {
        }

        @Override
        protected String doInBackground(String... args) {
            try {
                return process();
            } catch (Exception e) {
                this.e = e;    // Store error
            }

            return null;
        }

        @Override
        protected void onPostExecute(String data) {
            super.onPostExecute(data);
            // Display based on error existence

            mEditText.setText("");
            if (e != null) {
                mEditText.setText("Error: " + e.getMessage());
                this.e = null;
            } else {
                Gson gson = new Gson();
                AnalysisInDomainResult result = gson.fromJson(data, AnalysisInDomainResult.class);

                mEditText.append("Image format: " + result.metadata.format + "\n");
                mEditText.append("Image width: " + result.metadata.width + ", height:" + result.metadata.height + "\n");
                mEditText.append("\n");

                // Decode the returned result
                // NOTE: this is different for each domain model
                JsonArray detectedCelebs = result.result.get("celebrities").getAsJsonArray();
                mEditText.append("Celebrities detected: "+detectedCelebs.size()+"\n");

                for (JsonElement celebElement: detectedCelebs) {
                    JsonObject celeb = celebElement.getAsJsonObject();
                    mEditText.append("Name: " + celeb.get("name").getAsString() + ", score: " + celeb.get("confidence").getAsString() + "\n");
                }


                mEditText.append("\n--- Raw Data ---\n\n");
                APIURLS.arr.add((mEditText.toString()));
                mEditText.append(data);
                mEditText.setSelection(0);
            }

            mButtonSelectImage.setEnabled(true);
        }
    }
}
