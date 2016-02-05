package com.ttm.quaker;

import android.app.ListActivity;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
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

public class Community extends AppCompatActivity {

    Button goblog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_community);

        goblog= (Button) findViewById(R.id.blogbutton);
        View.OnClickListener blogbutton= new View.OnClickListener(){
            public void onClick (View v)
            {
                Intent intent= new Intent(Community.this, community_blog.class);
                startActivity(intent);
            }
        };
        goblog.setOnClickListener(blogbutton);

    }

}

