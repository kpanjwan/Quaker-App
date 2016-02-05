package com.ttm.quaker;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by Kuldeeppanjwani on 15-11-19.
 */
public class GetAllRecipesListView extends BaseAdapter {
    private JSONArray dataArray;
    private Activity activity;

    private static LayoutInflater inflater = null;

    public GetAllRecipesListView(JSONArray jsonArray, Activity a)
    {
        this.dataArray = jsonArray;
        this.activity = a;


        inflater = (LayoutInflater) this.activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }


    @Override
    public int getCount() {
        return this.dataArray.length();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        // set up convert view if it is null
        final ListCell cell;
        if (convertView == null)
        {
            convertView = inflater.inflate(R.layout.recipe_listview, null);
            cell = new ListCell();

            cell.RecipeName = (TextView) convertView.findViewById(R.id.recipe_name);

            cell.mobile = (ImageView) convertView.findViewById(R.id.recipe_image);

            convertView.setTag(cell);
        }
        else
        {
            cell = (ListCell) convertView.getTag();
        }

        // change the data of cell

        try
        {
            JSONObject jsonObject = this.dataArray.getJSONObject(position);
            cell.RecipeName.setText(jsonObject.getString("Recipe"));

           // String mobile = jsonObject.getString("Mobile");
           // cell.mobile.setImageResource(R.drawable.ic_launcher);
        /*    if (mobile.equals("iPhone"))
            {
                cell.mobile.setImageResource(R.drawable.iphone);
            }
            else if (mobile.equals("Android"))
            {
                cell.mobile.setImageResource(R.drawable.ic_launcher);
            }
            else if (mobile.equals("Nokia"))
            {
                cell.mobile.setImageResource(R.drawable.nokia);
            }
            else
            {
            } */

           // String name_image= jsonObject.getString("Image_Name");
            String UserID= jsonObject.getString("pid");
            String UrlImageServer= "http://10.0.2.2:8888/Quaker_Users/android_connect/Images/"+ UserID+ ".jpg";

            new AsyncTask<String, Void, Bitmap>()
            {
                @Override
                protected Bitmap doInBackground(String... Params) {
                    String url= Params[0];
                    Bitmap icon= null;

                    try {
                        InputStream in= new java.net.URL(url).openStream();
                        icon= BitmapFactory.decodeStream(in);

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    return icon;
                }

                @Override
                protected void onPostExecute(Bitmap bitmap) {
                    cell.mobile.setImageBitmap(bitmap);
                }
            }.execute(UrlImageServer);


        }
        catch (JSONException e)
        {
            e.printStackTrace();
        }


        return convertView;
    }



    private  class  ListCell
    {
        private TextView RecipeName;
        private ImageView mobile;

    }

}
