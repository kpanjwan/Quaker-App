package com.ttm.quaker;

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

public class DailyRecipe extends AppCompatActivity {
    TextView Title_Recipe;
    TextView Recipe_Ingridients;
    TextView Recipe_Steps;
    String imageurl= "http://10.0.2.2:8888/Quaker_Users/android_connect/Images/Daily.png";
    ImageView Daily_image;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daily_recipe);
        Title_Recipe= (TextView) findViewById(R.id.dtitle);
        Recipe_Ingridients= (TextView) findViewById(R.id.Ingridients_List);
        Recipe_Steps= (TextView) findViewById(R.id.Steps_List);
        Daily_image= (ImageView) findViewById(R.id.pancakes);
        StrictMode.enableDefaults();
        getData();
    }

    public void getData(){
        StrictMode.enableDefaults();
        String result = "";
        InputStream isr = null;
        try{
            HttpClient httpclient = new DefaultHttpClient();
            HttpPost httppost = new HttpPost("http://10.0.2.2:8888/Quaker_Users/android_connect/Get_Daily.php"); //YOUR PHP SCRIPT ADDRESS
            HttpResponse response = httpclient.execute(httppost);
            HttpEntity entity = response.getEntity();
            isr = entity.getContent();
        }
        catch(Exception e){
            Log.e("log_tag", "Error in http connection " + e.toString());
        }
        //convert response to string
        try{
            BufferedReader reader = new BufferedReader(new InputStreamReader(isr,"iso-8859-1"),8);
            StringBuilder sb = new StringBuilder();
            String line = null;
            while ((line = reader.readLine()) != null) {
                sb.append(line + "\n");
            }
            isr.close();

            result=sb.toString();
        }
        catch(Exception e){
            Log.e("log_tag", "Error  converting result "+e.toString());
        }

        //parse json data
        try {
            String t= "";
            String i= "";
            String s= "";
            JSONArray jArray = new JSONArray(result);

            for (int j=0; j<jArray.length(); j++)
            {
                t= t+ jArray.getJSONObject(j).getString("Recipe_Title");
                i= i+ jArray.getJSONObject(j).getString("Recipe_Ingridients");
                s= s+ jArray.getJSONObject(j).getString("Recipe_Method");
            }


            Title_Recipe.setText(t);
            Recipe_Ingridients.setText(i);
            Recipe_Steps.setText(s);
            new DownloadImageTask(Daily_image).execute(imageurl);
        }
        catch (Exception e) {
            // TODO: handle exception
            Log.e("log_tag", "Error Parsing Data "+e.toString());
        }

    }

}
