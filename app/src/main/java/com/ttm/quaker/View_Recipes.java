package com.ttm.quaker;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class View_Recipes extends AppCompatActivity {

    ListView recipes;
    ArrayList<String> All_Recipes;
    ArrayAdapter<String> adapter;
    private JSONArray jsonArray;
    public static int Num_Recipes=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view__recipes);

        recipes= (ListView) findViewById(R.id.recipes);
        new GetAllRecipesTask().execute(new ApiConnector());
        StrictMode.enableDefaults();


        this.recipes.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                try
                {
                    // GEt the customer which was clicked
                    JSONObject customerClicked = jsonArray.getJSONObject(position);


                    Intent intent1 = new Intent(View_Recipes.this, pizza_oats.class);
                    intent1.putExtra("CustomerRecipe", customerClicked.getInt("pid"));
                    startActivity(intent1);


                }
                catch (JSONException e)
                {
                    e.printStackTrace();
                }
            }
        });



    }
    protected void onResume() {
        super.onResume();

        new GetAllRecipesTask().execute(new ApiConnector());
    }

    public  void setListAdapter(JSONArray jsonArray)
    {
        this.jsonArray = jsonArray;
        this.recipes.setAdapter(new GetAllRecipesListView(jsonArray,this));
    }


    private class GetAllRecipesTask extends AsyncTask<ApiConnector,Long,JSONArray>
    {
        @Override
        protected JSONArray doInBackground(ApiConnector... params) {

            // it is executed on Background thread

            return params[0].GetAllRecipes();
        }

        @Override
        protected void onPostExecute(JSONArray jsonArray) {

            setListAdapter(jsonArray);


        }
    }
}
