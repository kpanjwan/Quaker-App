package com.ttm.quaker;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;

import java.util.ArrayList;

public class Restaurant extends AppCompatActivity {

    ListView restonames;
    EditText searcher;
    ArrayList<String> resto_names= new ArrayList<String>();
    ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant);
        searcher= (EditText) findViewById(R.id.resto_search);

        resto_names.add("Ta Wan- Sudirman, Jakarta");
        resto_names.add("TRF Homemade- Senayan, Jakarta");
        resto_names.add("Lai Ching- Four Seasons Hotel, Jakarta");
        resto_names.add("Healthy Choice- Kebon Jeruk, Jakarta");
        resto_names.add("Healthy Vege199- Penjaringan, Jakarta");
        resto_names.add("Healthy Bread- Cibodas");
        resto_names.add("Sinar Medan- Pecenongan, Jakarta");
        restonames= (ListView) findViewById(R.id.restonames);

        adapter = new ArrayAdapter<String>(
                this, R.layout.list_activity,resto_names);
        restonames.setAdapter(adapter);

        searcher.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence cs, int arg1, int arg2, int arg3) {
                // When user changed the Text
                Restaurant.this.adapter.getFilter().filter(cs);
            }

            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
                                          int arg3) {
                // TODO Auto-generated method stub

            }

            @Override
            public void afterTextChanged(Editable arg0) {
                // TODO Auto-generated method stub
            }
        });

        restonames.setOnItemClickListener(new AdapterView.OnItemClickListener() {


            @Override
            public void onItemClick(AdapterView<?> adapter, View v, int position, long id) {

               // ItemClicked item = adapter.getItem(position);
                Intent intent1= new Intent(Restaurant.this, TaWan.class);
                Intent intent2= new Intent(Restaurant.this, TRF.class);
                Intent intent3= new Intent(Restaurant.this, Lai_Ching.class);
                Intent intent4= new Intent(Restaurant.this, Healthy_Choice.class);
                Intent intent5= new Intent(Restaurant.this, Healthy_Vege.class);
                Intent intent6= new Intent(Restaurant.this, Healthy_Bread.class);
                Intent intent7= new Intent(Restaurant.this, Sinar_Medan.class);
                if(position == 0)
                    startActivity(intent1);
                if(position == 1)
                    startActivity(intent2);
                if(position == 2)
                    startActivity(intent3);
                if(position == 3)
                    startActivity(intent4);
                if(position == 4)
                    startActivity(intent5);
                if(position == 5)
                    startActivity(intent6);
                if(position == 6)
                    startActivity(intent7);

            }

        });

     /*   public ItemClicked getItem(int position)
        {

            return items.get(position);
        } */
    }


}