package com.ttm.quaker;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.ParcelFileDescriptor;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.FileDescriptor;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class UploadRecipe extends AppCompatActivity {

    Button ChooseImage;
    private static final int SELECT_PICTURE = 1;
    ImageView picture;
    String URL= "http://10.0.2.2:8888/Quaker_Users/android_connect/uploadImage.php";
    EditText ingridients;
    EditText steps;
    EditText title;
    Button upload;
    String ingridents_upload;
    String steps_upload;
    String title_upload;
    String imageData;

    InputStream is = null;
    ArrayList<Integer> tester;

    int idofRecipe;
     List<NameValuePair> params = new ArrayList<NameValuePair>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StrictMode.enableDefaults();
        setContentView(R.layout.activity_upload_recipe);
        picture= (ImageView) findViewById(R.id.NewRecipe);
        ingridients= (EditText) findViewById(R.id.ingridients_input);
        steps= (EditText) findViewById(R.id.step_input);
        title= (EditText) findViewById(R.id.recipe_title);
        upload= (Button) findViewById(R.id.upload);

        getData();
        ChooseImage= (Button) findViewById(R.id.ChooseImage);
        ChooseImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), SELECT_PICTURE);
            }
        });

        View.OnClickListener UploadButton= new View.OnClickListener() {
            public void onClick (View v)
            {
                title_upload= title.getText().toString();
                ingridents_upload = ingridients.getText().toString();
                steps_upload= steps.getText().toString();

                Intent intent= new Intent(UploadRecipe.this, Contest.class);
                startActivity(intent);
                // registerUser();
                if(ingridents_upload.equals("") || steps_upload.equals("")){
                    String msg = "One or more fields are empty";
                    ingridients.setText("");
                    steps.setText("");
                }
                else{
                    String testing= "4";

                    params.add(new BasicNameValuePair("pid", Integer.toString(idofRecipe)));
                    params.add(new BasicNameValuePair("Recipe", title_upload));
                    params.add(new BasicNameValuePair("Recipe_Title", title_upload));
                    params.add(new BasicNameValuePair("Recipe_Ingridients", ingridents_upload));
                    params.add(new BasicNameValuePair("Recipe_Steps", steps_upload));
                    params.add(new BasicNameValuePair("Image_Name", imageData));

                    try{
                        StrictMode.enableDefaults();
                        HttpClient httpClient = new DefaultHttpClient();
                        HttpPost httpPost = new HttpPost(URL);
                        httpPost.setEntity(new UrlEncodedFormEntity(params));
                        HttpResponse httpResponse = httpClient.execute(httpPost);
                        HttpEntity httpEntity = httpResponse.getEntity();
                        is = httpEntity.getContent();
                        is.close();
                    }catch(IOException e){

                    }
                }

            }
        };
        upload.setOnClickListener(UploadButton);

    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            if (requestCode == SELECT_PICTURE) {
                Uri selectedImageUri = data.getData();
                if (Build.VERSION.SDK_INT < 19) {
                    String selectedImagePath = getPath(selectedImageUri);
                    Bitmap bitmap = BitmapFactory.decodeFile(selectedImagePath);
                    SetImage(bitmap);
                } else {
                    ParcelFileDescriptor parcelFileDescriptor;
                    try {
                        parcelFileDescriptor = getContentResolver().openFileDescriptor(selectedImageUri, "r");
                        FileDescriptor fileDescriptor = parcelFileDescriptor.getFileDescriptor();
                        Bitmap image = BitmapFactory.decodeFileDescriptor(fileDescriptor);
                        parcelFileDescriptor.close();
                        SetImage(image);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    private void SetImage(Bitmap image) {
        this.picture.setImageBitmap(image);

        // upload
        imageData = encodeTobase64(image);


       /* new AsyncTask<ApiConnector,Long, Boolean >() {
            @Override
            protected Boolean doInBackground(ApiConnector... apiConnectors) {
                return apiConnectors[0].uploadImageToserver(params);
            }
        }.execute(new ApiConnector()); */

    }

    public static String encodeTobase64(Bitmap image) {
        System.gc();

        if (image == null)return null;

        Bitmap immagex = image;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        immagex.compress(Bitmap.CompressFormat.JPEG, 100, baos);

        byte[] b = baos.toByteArray();

        String imageEncoded = Base64.encodeToString(b, Base64.DEFAULT); // min minSdkVersion 8

        return imageEncoded;
    }


    public String getPath(Uri uri) {
        if( uri == null ) {
            return null;
        }
        String[] projection = { MediaStore.Images.Media.DATA };
        Cursor cursor = getContentResolver().query(uri, projection, null, null, null);
        if( cursor != null ){
            int column_index = cursor
                    .getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            return cursor.getString(column_index);
        }
        return uri.getPath();
    }

   /* private class GetCustomerDetails extends AsyncTask<ApiConnector,Long,JSONArray>
    {
        @Override
        protected JSONArray doInBackground(ApiConnector... params) {

            // it is executed on Background thread

            return params[0].GetProductDetails(idofRecipe);
        }

        @Override
        protected void onPostExecute(JSONArray jsonArray) {

            try
            {
                JSONObject customer = jsonArray.getJSONObject(0);

                RecipeID = customer.getString("pid");
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }

        }
    } */

    public void getData(){
        String result = "";
        InputStream isr = null;
        try{
            HttpClient httpclient = new DefaultHttpClient();
            HttpPost httppost = new HttpPost("http://10.0.2.2:8888/Quaker_Users/android_connect/get_all_recipes.php"); //YOUR PHP SCRIPT ADDRESS
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
            tester= new ArrayList<Integer>();
            JSONArray jArray = new JSONArray(result);
            for (int i=0; i<jArray.length(); i++)
            {
                tester.add(jArray.getJSONObject(i).getInt("pid"));
            }
            idofRecipe= tester.size() + 1;


        } catch (Exception e) {
            // TODO: handle exception
            Log.e("log_tag", "Error Parsing Data "+e.toString());
        }

    }


}
