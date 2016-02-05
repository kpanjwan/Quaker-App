package com.ttm.quaker;

import android.content.Intent;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

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

public class LoginScreen extends AppCompatActivity {

    EditText email;
    EditText password;
    ArrayList<String> tester;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_screen);
        email= (EditText) findViewById(R.id.Email);
        password= (EditText) findViewById(R.id.password);
        StrictMode.enableDefaults();
        getData();

        Button login = (Button) findViewById(R.id.login);
        View.OnClickListener LoginButton= new View.OnClickListener() {
            public void onClick (View v)
            {
                Intent intent1= new Intent(LoginScreen.this, MainMenu.class);
                Intent intent= new Intent(LoginScreen.this, SignUp.class);
                if(tester.contains(email.getText().toString()) && tester.contains(password.getText().toString()))
                    startActivity(intent1);
                else
                    startActivity(intent);
            }
        };
        login.setOnClickListener(LoginButton);

    }

    public void getData(){
        String result = "";
        InputStream isr = null;
        try{
            HttpClient httpclient = new DefaultHttpClient();
            HttpPost httppost = new HttpPost("http://10.0.2.2:8888/Quaker_Users/android_connect/get_all_products.php"); //YOUR PHP SCRIPT ADDRESS
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
            String s= "";
            tester= new ArrayList<String>();
            JSONArray jArray = new JSONArray(result);
            for (int i=0; i<jArray.length(); i++)
            {
                tester.add(jArray.getJSONObject(i).getString("Password"));
                tester.add(jArray.getJSONObject(i+1).getString("Email"));
            }


        } catch (Exception e) {
            // TODO: handle exception
            Log.e("log_tag", "Error Parsing Data "+e.toString());
        }

    }

}
