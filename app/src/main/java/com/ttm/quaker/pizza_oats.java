package com.ttm.quaker;

import android.os.AsyncTask;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class pizza_oats extends AppCompatActivity {

    TextView Recipe_Title;
    TextView Recipe_Ingridients;
    TextView Recipe_Steps;
    int recipe_tester;
    String baseUrlForImage= "http://10.0.2.2:8888/Quaker_Users/android_connect/Images/";
    private ImageView picture;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pizza_oats);
        Recipe_Title= (TextView) findViewById(R.id.contest_title);
        Recipe_Ingridients= (TextView) findViewById(R.id.Ingridients_List_Contest);
        Recipe_Steps= (TextView) findViewById(R.id.Steps_List_Contest);
        picture= (ImageView) findViewById(R.id.download_image);
        //getData();

        recipe_tester= getIntent().getIntExtra("CustomerRecipe", -1);

        if (this.recipe_tester > -1)
        {
            // we have customer ID passed correctly.
            new GetActualRecipes().execute(new ApiConnector());
        }
        else
            Recipe_Title.setText("Failed");


    }

    private class GetActualRecipes extends AsyncTask<ApiConnector,Long,JSONArray>
    {
        @Override
        protected JSONArray doInBackground(ApiConnector... params) {

            // it is executed on Background thread

            return params[0].GetProductDetails(recipe_tester);
        }

        @Override
        protected void onPostExecute(JSONArray jsonArray) {

            try
            {
                JSONObject customer = jsonArray.getJSONObject(0);

                Recipe_Title.setText(customer.getString("Recipe_Title"));
                Recipe_Ingridients.setText(customer.getString("Recipe_Ingridients"));
                Recipe_Steps.setText(customer.getString("Recipe_Steps"));

                String urlForImage = baseUrlForImage + customer.getString("Image_Name");
                new DownloadImageTask(picture).execute(urlForImage);
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }



        }
    }
}
