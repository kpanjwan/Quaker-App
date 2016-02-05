package com.ttm.quaker;

import android.app.ActionBar;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.StrictMode;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.Spinner;
import android.app.Fragment;
import android.app.FragmentManager;
import android.widget.Toast;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

public class SignUp extends AppCompatActivity {

    private EditText FirstName;
    private EditText LastName;
    private EditText Email;
    private EditText Password;
    private Spinner  Gender;
    private CheckBox terms;
    private NumberPicker Day;
    private NumberPicker Month;
    private NumberPicker Year;
    private Button SignUp;
    String First_Name_test= "";
    String Last_Name_test= "";
    String Email_test= "";
    String Password_test= "";
    String Gender_test= "";
    String DOB_test= "";

    InputStream is = null;
    String exceptionMessage = "There seems to be some problem connecting to database. " +
            "Please check your Internet Connection and try again.";
    String successMessage = "Data has been entered successfully";

    private static final String REGISTER_URL = "http://10.0.2.2:8888/Quaker_Users/android_connect/create_product.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StrictMode.enableDefaults();
        setContentView(R.layout.activity_sign_up);
        FirstName= (EditText) findViewById(R.id.FirstName);
        LastName= (EditText) findViewById(R.id.LastName);
        Email= (EditText) findViewById(R.id.EmailAddress);
        Password= (EditText) findViewById(R.id.NewPassword);
        terms= (CheckBox) findViewById(R.id.Terms);

        Gender= (Spinner) findViewById(R.id.genderspin);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.Genders, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        Gender.setAdapter(adapter);

        Day= (NumberPicker) findViewById(R.id.Day);
        Day.setMaxValue(31);
        Day.setMinValue(1);
        Day.setValue(1);

        Month= (NumberPicker) findViewById(R.id.Month);
        Month.setMaxValue(12);
        Month.setMinValue(1);
        Month.setValue(1);

        Year= (NumberPicker) findViewById(R.id.Year);
        Calendar calendar= Calendar.getInstance();
        final int year = calendar.get(Calendar.YEAR);
        Year.setMaxValue(year);
        Year.setMinValue(1940);
        Year.setValue(year);

        SignUp = (Button) findViewById(R.id.SignUp);
        View.OnClickListener SignUpButton= new View.OnClickListener() {
            public void onClick (View v)
            {
                First_Name_test = FirstName.getText().toString();
                Last_Name_test= LastName.getText().toString();
                Password_test = Password.getText().toString();
                Email_test = Email.getText().toString();
                Gender_test= Gender.getSelectedItem().toString();
                int day_birth= Day.getValue();
                int month_birth= Month.getValue();
                int year_birth= Year.getValue();
                DOB_test= day_birth + "/" + month_birth + "/"+ year_birth;

                if(!First_Name_test.equals("") && !Last_Name_test.equals("") && !Email_test.equals("") && !Password_test.equals("") && year_birth < year-18 && terms.isChecked())
                {
                    Intent intent= new Intent(SignUp.this, MainMenu.class);
                    startActivity(intent);

                    // registerUser();
                    if(First_Name_test.equals("") || Last_Name_test.equals("") || Email_test.equals("")){
                        String msg = "One or more fields are empty";
                        FirstName.setText("");
                        LastName.setText("");
                        Email.setText("");
                    }
                    else{

                        List<NameValuePair> nameValuePairList = new ArrayList<NameValuePair>();
                        nameValuePairList.add(new BasicNameValuePair("First_Name", First_Name_test));
                        nameValuePairList.add(new BasicNameValuePair("Last_Name", Last_Name_test));
                        nameValuePairList.add(new BasicNameValuePair("Email", Email_test));
                        nameValuePairList.add(new BasicNameValuePair("Password", Password_test));
                        nameValuePairList.add(new BasicNameValuePair("Gender", Gender_test));
                        nameValuePairList.add(new BasicNameValuePair("DOB", DOB_test));

                        try{
                            HttpClient httpClient = new DefaultHttpClient();
                            HttpPost httpPost = new HttpPost(REGISTER_URL);
                            httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairList));
                            HttpResponse httpResponse = httpClient.execute(httpPost);
                            HttpEntity httpEntity = httpResponse.getEntity();
                            is = httpEntity.getContent();
                            //FirstName.setText("");
                            //LastName.setText("");
                            //Email.setText("");
                            // Toast.makeText(getApplicationContext(), successMessage, Toast.LENGTH_SHORT).show();
                            is.close();
                        }catch(IOException e){
                            // Toast.makeText(getApplicationContext(), exceptionMessage, Toast.LENGTH_SHORT).show();
                        }
                    }
                }
                else
                {
                    Context context= getApplicationContext();
                    if(First_Name_test.equals("") || Last_Name_test.equals("") || Email_test.equals("") || Password_test.equals(""))
                        Toast.makeText(context, "One or more of the input fields is empty", Toast.LENGTH_LONG).show();
                    if(year_birth > year-18)
                        Toast.makeText(context, "You are not old enough to sign up for this app", Toast.LENGTH_LONG).show();
                    if(!terms.isChecked())
                        Toast.makeText(context, "Please agree to terms and conditions before signing up", Toast.LENGTH_LONG).show();
                }

            }
        };
        SignUp.setOnClickListener(SignUpButton);
    }



}
