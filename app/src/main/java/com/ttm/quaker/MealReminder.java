package com.ttm.quaker;

import android.app.AlarmManager;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.Calendar;

public class MealReminder extends AppCompatActivity {

    TextView breakfast;
    TextView brunch_time;
    TextView lunch_time;
    TextView AF_time;
    TextView Dinner_time;
    int spinnerPos;
    int brunchPos;
    int LunchPos;
    int AFPos;
    int DinnerPos;
    Spinner Dinner;
    Spinner spinner;
    Spinner brunch;
    Spinner lunch;
    Spinner AFmeal;
    Switch bswitch;
    Switch brswitch;
    Switch lswitch;
    Switch AFswitch;
    Switch dswitch;
    Intent intent;
    private PendingIntent alarmIntent;
    public String meals_main="";
    SharedPreferences spinnersaving1;
    SharedPreferences spinnersaving2;
    SharedPreferences spinnersaving3;
    SharedPreferences spinnersaving4;
    SharedPreferences spinnersaving5;
    SharedPreferences timesaving1;
    SharedPreferences timesaving2;
    SharedPreferences timesaving3;
    SharedPreferences timesaving4;
    SharedPreferences timesaving5;
    SharedPreferences switchsaving1;
    SharedPreferences switchsaving2;
    SharedPreferences switchsaving3;
    SharedPreferences switchsaving4;
    SharedPreferences switchsaving5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meal_reminder);
        intent= new Intent(getBaseContext(), AlarmReceiver.class);


        spinnersaving1= getPreferences(Context.MODE_PRIVATE);
        spinnersaving2= getPreferences(Context.MODE_PRIVATE);
        spinnersaving3= getPreferences(Context.MODE_PRIVATE);
        spinnersaving4= getPreferences(Context.MODE_PRIVATE);
        spinnersaving5= getPreferences(Context.MODE_PRIVATE);
        timesaving1= getPreferences(Context.MODE_PRIVATE);
        timesaving2= getPreferences(Context.MODE_PRIVATE);
        timesaving3= getPreferences(Context.MODE_PRIVATE);
        timesaving4= getPreferences(Context.MODE_PRIVATE);
        timesaving5= getPreferences(Context.MODE_PRIVATE);
        switchsaving1= getPreferences(Context.MODE_PRIVATE);
        switchsaving2= getPreferences(Context.MODE_PRIVATE);
        switchsaving3= getPreferences(Context.MODE_PRIVATE);
        switchsaving4= getPreferences(Context.MODE_PRIVATE);
        switchsaving5= getPreferences(Context.MODE_PRIVATE);

        TextView why= (TextView) findViewById(R.id.why);
        View.OnClickListener why_should = new View.OnClickListener() {
            public void onClick(View v) {
               Intent intent= new Intent(MealReminder.this, why_should.class);
                startActivity(intent);
            }
        };
        why.setOnClickListener(why_should);
//Breakfast
        bswitch= (Switch) findViewById(R.id.switch1);
        spinner = (Spinner) findViewById(R.id.breakoptions);
        spinner.setEnabled(false);
        breakfast = (TextView) findViewById(R.id.picktime);
        breakfast.setEnabled(false);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.Breakfast, R.layout.spinner_layout);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinner.setAdapter(adapter);
        spinner.setSelection(spinnersaving1.getInt("SpinnerState", 0));
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            public void onItemSelected(AdapterView<?> arg0, View arg1,
                                       int arg2, long arg3) {

                spinnerPos = arg2;
            }

            public void onNothingSelected(AdapterView<?> arg0) {

                // do nothing
            }
        });




        breakfast.setText(timesaving1.getString("textvalue", "00:00"));
        bswitch.setChecked(switchsaving1.getBoolean("Switch1", false));
        if(bswitch.isChecked()){
            spinner.setEnabled(true);
            breakfast.setEnabled(true);
        }

        bswitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                //commit prefs on change
               if(bswitch.isChecked())
               {
                   spinner.setEnabled(true);
                   breakfast.setEnabled(true);
               }
            }
        });

        class TimePickerFragment extends DialogFragment
                implements TimePickerDialog.OnTimeSetListener {

            @Override
            public Dialog onCreateDialog(Bundle savedInstanceState) {
                // Use the current time as the default values for the picker
                final Calendar c = Calendar.getInstance();
                int hour = c.get(Calendar.HOUR_OF_DAY);
                int minute = c.get(Calendar.MINUTE);

                // Create a new instance of TimePickerDialog and return it
                return new TimePickerDialog(getActivity(), this, hour, minute,
                        DateFormat.is24HourFormat(getActivity()));
            }

            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                // Do something with the time chosen by the user
                breakfast.setText(new StringBuilder().append(pad(hourOfDay))
                        .append(":").append(pad(minute)));

                AlarmManager am0 = (AlarmManager) getBaseContext().getSystemService(Context.ALARM_SERVICE);
                meals_main = spinner.getSelectedItem().toString();
                Calendar cal = Calendar.getInstance();
                cal.setTimeInMillis(System.currentTimeMillis());
                cal.set(Calendar.HOUR_OF_DAY, hourOfDay);
                cal.set(Calendar.MINUTE, minute);

                if (bswitch.isChecked()) {
                    setAlarm(cal, 0, am0);
                }


            }

            private String pad(int c) {
                if (c >= 10)
                    return String.valueOf(c);
                else
                    return "0" + String.valueOf(c);
            }

        }
        View.OnClickListener picker = new View.OnClickListener() {
            public void onClick(View v) {
                DialogFragment newFragment = new TimePickerFragment();
                newFragment.show(getFragmentManager(), "timePicker");
            }
        };
        breakfast.setOnClickListener(picker);



        //Brunch
        brswitch= (Switch) findViewById(R.id.switch2);
        brunch = (Spinner) findViewById(R.id.brunchoptions);
        brunch.setEnabled(false);
        brunch_time = (TextView) findViewById(R.id.pickbrunchtime);
        brunch_time.setEnabled(false);

        ArrayAdapter<CharSequence> brunch_adapter = ArrayAdapter.createFromResource(this,
                R.array.Brunch, R.layout.spinner_layout);
        // Specify the layout to use when the list of choices appears
        brunch_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        brunch.setAdapter(brunch_adapter);
        brunch.setSelection(spinnersaving2.getInt("SpinnerState2", 0));
        brunch.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            public void onItemSelected(AdapterView<?> arg0, View arg1,
                                       int arg2, long arg3) {

                brunchPos = arg2;
            }

            public void onNothingSelected(AdapterView<?> arg0) {

                // do nothing
            }
        });

        brunch_time.setText(timesaving2.getString("textvalue2", "00:00"));
        brswitch.setChecked(switchsaving2.getBoolean("Switch2", false));
        if(brswitch.isChecked()){
            brunch.setEnabled(true);
            brunch_time.setEnabled(true);
        }

        brswitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                //commit prefs on change
                if (brswitch.isChecked()) {
                    brunch.setEnabled(true);
                    brunch_time.setEnabled(true);
                }
            }
        });

        class TimePickerFragment1 extends DialogFragment
                implements TimePickerDialog.OnTimeSetListener {

            @Override
            public Dialog onCreateDialog(Bundle savedInstanceState) {
                // Use the current time as the default values for the picker
                final Calendar c = Calendar.getInstance();
                int hour = c.get(Calendar.HOUR_OF_DAY);
                int minute = c.get(Calendar.MINUTE);

                // Create a new instance of TimePickerDialog and return it
                return new TimePickerDialog(getActivity(), this, hour, minute,
                        DateFormat.is24HourFormat(getActivity()));
            }

            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                // Do something with the time chosen by the user
                brunch_time.setText(new StringBuilder().append(pad(hourOfDay))
                        .append(":").append(pad(minute)));

                AlarmManager am1= (AlarmManager)getBaseContext().getSystemService(Context.ALARM_SERVICE);
                meals_main= brunch.getSelectedItem().toString();
                Calendar cal = Calendar.getInstance();
                cal.setTimeInMillis(System.currentTimeMillis());
                cal.set(Calendar.HOUR_OF_DAY, hourOfDay);
                cal.set(Calendar.MINUTE, minute);


                if(brswitch.isChecked())
                {
                    setAlarm(cal, 1, am1);
                }
            }

            private String pad(int c) {
                if (c >= 10)
                    return String.valueOf(c);
                else
                    return "0" + String.valueOf(c);
            }

        }
        View.OnClickListener brunch_picker = new View.OnClickListener() {
            public void onClick(View v) {
                DialogFragment newFragment = new TimePickerFragment1();
                newFragment.show(getFragmentManager(), "timePicker1");
            }
        };
        brunch_time.setOnClickListener(brunch_picker);

        //lunch
        lswitch = (Switch) findViewById(R.id.switch3);
        lunch = (Spinner) findViewById(R.id.lunchoptions);
        lunch.setEnabled(false);
        lunch_time = (TextView) findViewById(R.id.picklunchtime);
        lunch_time.setEnabled(false);

        ArrayAdapter<CharSequence> lunch_adapter = ArrayAdapter.createFromResource(this,
                R.array.Lunch, R.layout.spinner_layout);
        // Specify the layout to use when the list of choices appears
        lunch_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        lunch.setAdapter(lunch_adapter);
        lunch.setSelection(spinnersaving3.getInt("SpinnerState3", 0));
        lunch.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            public void onItemSelected(AdapterView<?> arg0, View arg1,
                                       int arg2, long arg3) {

                LunchPos = arg2;
            }

            public void onNothingSelected(AdapterView<?> arg0) {

                // do nothing
            }
        });

        lunch_time.setText(timesaving3.getString("textvalue3", "00:00"));
        lswitch.setChecked(switchsaving3.getBoolean("Switch3", false));
        if (lswitch.isChecked()) {
            lunch_time.setEnabled(true);
            lunch.setEnabled(true);
        }

        lswitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                //commit prefs on change
                if (lswitch.isChecked()) {
                    lunch_time.setEnabled(true);
                    lunch.setEnabled(true);
                }
            }
        });


        class TimePickerFragment2 extends DialogFragment
                implements TimePickerDialog.OnTimeSetListener {

            @Override
            public Dialog onCreateDialog(Bundle savedInstanceState) {
                // Use the current time as the default values for the picker
                final Calendar c = Calendar.getInstance();
                int hour = c.get(Calendar.HOUR_OF_DAY);
                int minute = c.get(Calendar.MINUTE);

                // Create a new instance of TimePickerDialog and return it
                return new TimePickerDialog(getActivity(), this, hour, minute,
                        DateFormat.is24HourFormat(getActivity()));
            }

            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                // Do something with the time chosen by the user
                lunch_time.setText(new StringBuilder().append(pad(hourOfDay))
                        .append(":").append(pad(minute)));

                AlarmManager am2= (AlarmManager)getBaseContext().getSystemService(Context.ALARM_SERVICE);
                meals_main= lunch.getSelectedItem().toString();
                Calendar cal = Calendar.getInstance();
                cal.setTimeInMillis(System.currentTimeMillis());
                cal.set(Calendar.HOUR_OF_DAY, hourOfDay);
                cal.set(Calendar.MINUTE, minute);


                if(lswitch.isChecked())
                {
                    setAlarm(cal, 2, am2);
                }
            }

            private String pad(int c) {
                if (c >= 10)
                    return String.valueOf(c);
                else
                    return "0" + String.valueOf(c);
            }

        }
        View.OnClickListener lunch_picker = new View.OnClickListener() {
            public void onClick(View v) {
                DialogFragment newFragment = new TimePickerFragment2();
                newFragment.show(getFragmentManager(), "timePicker2");
            }
        };
        lunch_time.setOnClickListener(lunch_picker);

        //Afternoon Meal
        AFswitch= (Switch) findViewById(R.id.switch4);
        AFmeal = (Spinner) findViewById(R.id.AFoptions);
        AFmeal.setEnabled(false);
        AF_time = (TextView) findViewById(R.id.pickAFtime);
        AF_time.setEnabled(false);

        ArrayAdapter<CharSequence> AFmeal_adapter = ArrayAdapter.createFromResource(this,
                R.array.AFMeal, R.layout.spinner_layout);
        // Specify the layout to use when the list of choices appears
        AFmeal_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        AFmeal.setAdapter(AFmeal_adapter);
        AFmeal.setSelection(spinnersaving4.getInt("SpinnerState4", 0));
        AFmeal.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            public void onItemSelected(AdapterView<?> arg0, View arg1,
                                       int arg2, long arg3) {

                AFPos = arg2;
            }

            public void onNothingSelected(AdapterView<?> arg0) {

                // do nothing
            }
        });

        AF_time.setText(timesaving4.getString("textvalue4", "00:00"));
        AFswitch.setChecked(switchsaving4.getBoolean("Switch4", false));
        if (AFswitch.isChecked()) {
            AFmeal.setEnabled(true);
            AF_time.setEnabled(true);
        }

        AFswitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                //commit prefs on change
                if (AFswitch.isChecked()) {
                    AFmeal.setEnabled(true);
                    AF_time.setEnabled(true);
                }
            }
        });

        class TimePickerFragment3 extends DialogFragment
                implements TimePickerDialog.OnTimeSetListener {

            @Override
            public Dialog onCreateDialog(Bundle savedInstanceState) {
                // Use the current time as the default values for the picker
                final Calendar c = Calendar.getInstance();
                int hour = c.get(Calendar.HOUR_OF_DAY);
                int minute = c.get(Calendar.MINUTE);

                // Create a new instance of TimePickerDialog and return it
                return new TimePickerDialog(getActivity(), this, hour, minute,
                        DateFormat.is24HourFormat(getActivity()));
            }

            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                // Do something with the time chosen by the user
                AF_time.setText(new StringBuilder().append(pad(hourOfDay))
                        .append(":").append(pad(minute)));

                AlarmManager am3= (AlarmManager)getBaseContext().getSystemService(Context.ALARM_SERVICE);
                meals_main= AFmeal.getSelectedItem().toString();
                Calendar cal = Calendar.getInstance();
                cal.setTimeInMillis(System.currentTimeMillis());
                cal.set(Calendar.HOUR_OF_DAY, hourOfDay);
                cal.set(Calendar.MINUTE, minute);


                if(AFswitch.isChecked())
                {
                    setAlarm(cal, 3, am3);
                }
            }

            private String pad(int c) {
                if (c >= 10)
                    return String.valueOf(c);
                else
                    return "0" + String.valueOf(c);
            }

        }
        View.OnClickListener AF_picker = new View.OnClickListener() {
            public void onClick(View v) {
                DialogFragment newFragment = new TimePickerFragment3();
                newFragment.show(getFragmentManager(), "timePicker3");
            }
        };
        AF_time.setOnClickListener(AF_picker);

        //Dinner
        dswitch= (Switch) findViewById(R.id.switch5);
        Dinner= (Spinner) findViewById(R.id.Dinneroptions);
        Dinner.setEnabled(false);
        Dinner_time = (TextView) findViewById(R.id.pickDinnertime);
        Dinner_time.setEnabled(false);
        ArrayAdapter<CharSequence> dinner_adapter = ArrayAdapter.createFromResource(this,
                R.array.Dinner, R.layout.spinner_layout);
        // Specify the layout to use when the list of choices appears
        dinner_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        Dinner.setAdapter(dinner_adapter);
        Dinner.setSelection(spinnersaving5.getInt("SpinnerState5", 0));

        Dinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            public void onItemSelected(AdapterView<?> arg0, View arg1,
                                       int arg2, long arg3) {

                DinnerPos = arg2;

            }


            public void onNothingSelected(AdapterView<?> arg0) {

                // do nothing
            }
        });

        Dinner_time.setText(timesaving5.getString("textvalue5", "00:00"));
        dswitch.setChecked(switchsaving5.getBoolean("Switch5", false));
        if (dswitch.isChecked()) {
            Dinner.setEnabled(true);
            Dinner_time.setEnabled(true);
        }

        dswitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                //commit prefs on change
                if (dswitch.isChecked()) {
                    Dinner.setEnabled(true);
                    Dinner_time.setEnabled(true);
                }
            }
        });

        class TimePickerFragment4 extends DialogFragment
                implements TimePickerDialog.OnTimeSetListener {

            @Override
            public Dialog onCreateDialog(Bundle savedInstanceState) {
                // Use the current time as the default values for the picker
                final Calendar c = Calendar.getInstance();
                int hour = c.get(Calendar.HOUR_OF_DAY);
                int minute = c.get(Calendar.MINUTE);

                // Create a new instance of TimePickerDialog and return it
                return new TimePickerDialog(getActivity(), this, hour, minute,
                        DateFormat.is24HourFormat(getActivity()));
            }

            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                // Do something with the time chosen by the user
                Dinner_time.setText(new StringBuilder().append(pad(hourOfDay))
                        .append(":").append(pad(minute)));

                AlarmManager am4= (AlarmManager)getBaseContext().getSystemService(Context.ALARM_SERVICE);
                meals_main= Dinner.getSelectedItem().toString();
                Calendar cal = Calendar.getInstance();
                cal.setTimeInMillis(System.currentTimeMillis());
                cal.set(Calendar.HOUR_OF_DAY, hourOfDay);
                cal.set(Calendar.MINUTE, minute);


                if(dswitch.isChecked())
                {
                    setAlarm(cal, 4, am4);
                }
            }

            private String pad(int c) {
                if (c >= 10)
                    return String.valueOf(c);
                else
                    return "0" + String.valueOf(c);
            }

        }
        View.OnClickListener Dinner_picker = new View.OnClickListener() {
            public void onClick(View v) {
                DialogFragment newFragment = new TimePickerFragment4();
                newFragment.show(getFragmentManager(), "timePicker4");
            }
        };
        Dinner_time.setOnClickListener(Dinner_picker);
    }

    @Override
    protected void onResume() {
        super.onResume();
        meals_main = "";
    }

    @Override
    protected void onPause() {
        super.onPause();
        meals_main= "";
    }

    protected void onStop() {
        super.onStop();
        meals_main= "";

        SharedPreferences.Editor editor= spinnersaving1.edit();
        editor.putInt("SpinnerState", spinnerPos);
        editor.apply();

        SharedPreferences.Editor editor1 = spinnersaving2.edit();
        editor1.putInt("SpinnerState2", brunchPos);
        editor1.apply();

        SharedPreferences.Editor editor2 = spinnersaving3.edit();
        editor2.putInt("SpinnerState3", LunchPos);
        editor2.apply();

        SharedPreferences.Editor editor3 = spinnersaving4.edit();
        editor3.putInt("SpinnerState4", AFPos);
        editor3.apply();

        SharedPreferences.Editor editor4 = spinnersaving5.edit();
        editor4.putInt("SpinnerState5", DinnerPos);
        editor4.apply();

        SharedPreferences.Editor sedt = timesaving1.edit();
        sedt.putString("textvalue", breakfast.getText().toString());
        sedt.apply();

        SharedPreferences.Editor sedt2 = timesaving2.edit();
        sedt2.putString("textvalue2", brunch_time.getText().toString());
        sedt2.apply();

        SharedPreferences.Editor sedt3 = timesaving3.edit();
        sedt3.putString("textvalue3", lunch_time.getText().toString());
        sedt3.apply();

        SharedPreferences.Editor sedt4 = timesaving4.edit();
        sedt4.putString("textvalue4", AF_time.getText().toString());
        sedt4.apply();

        SharedPreferences.Editor sedt5 = timesaving5.edit();
        sedt5.putString("textvalue5", Dinner_time.getText().toString());
        sedt5.apply();

        SharedPreferences.Editor switchsave1= switchsaving1.edit();
        switchsave1.putBoolean("Switch1", bswitch.isChecked());
        switchsave1.apply();

        SharedPreferences.Editor switchsave2= switchsaving2.edit();
        switchsave2.putBoolean("Switch2", brswitch.isChecked());
        switchsave2.apply();

        SharedPreferences.Editor switchsave3 = switchsaving3.edit();
        switchsave3.putBoolean("Switch3", lswitch.isChecked());
        switchsave3.apply();

        SharedPreferences.Editor switchsave4 = switchsaving4.edit();
        switchsave4.putBoolean("Switch4", AFswitch.isChecked());
        switchsave4.apply();

        SharedPreferences.Editor switchsave5 = switchsaving5.edit();
        switchsave5.putBoolean("Switch5", dswitch.isChecked());
        switchsave5.apply();
    }

    private void setAlarm(Calendar targetCal, int i, AlarmManager alarmMgr) {
        alarmMgr = (AlarmManager)getBaseContext().getSystemService(Context.ALARM_SERVICE);
        intent.putExtra("KEY", meals_main);
        alarmIntent = PendingIntent.getBroadcast(getBaseContext(), i, intent, PendingIntent.FLAG_ONE_SHOT);
        alarmMgr.set(AlarmManager.RTC_WAKEUP, targetCal.getTimeInMillis(), alarmIntent);
    }


}
