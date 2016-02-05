package com.ttm.quaker;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

public class MainMenu extends AppCompatActivity {

    final String[] MainMenu= {"Daily Demand Calories", "Community", "Contest", "Meal Reminder",
            "Surprising Uses", "Oatmeal Resto", "New Products", "Daily Recipe"};
    ListView mainmenu;
    ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        mainmenu= (ListView) findViewById(R.id.main_menu);

        adapter = new ArrayAdapter<String>(
                this, R.layout.list_activity_mainmenu, MainMenu);
        mainmenu.setAdapter(adapter);

        mainmenu.setOnItemClickListener(new AdapterView.OnItemClickListener() {


            @Override
            public void onItemClick(AdapterView<?> adapter, View v, int position, long id) {

                // ItemClicked item = adapter.getItem(position);
                Intent intent1 = new Intent(MainMenu.this, DDM.class);
                Intent intent2= new Intent(MainMenu.this, Community.class);
                Intent intent3 = new Intent(MainMenu.this, Contest.class);
                Intent intent4 = new Intent(MainMenu.this, MealReminder.class);
                Intent intent5 = new Intent(MainMenu.this, SurprisingUses.class);
                Intent intent6 = new Intent(MainMenu.this, Restaurant.class);
                Intent intent7 = new Intent(MainMenu.this, NewProduct.class);
                Intent intent8= new Intent(MainMenu.this, DailyRecipe.class);
                if (position == 0)
                    startActivity(intent1);
                if (position == 1)
                    startActivity(intent2);
                if (position == 2)
                    startActivity(intent3);
                if (position == 3)
                    startActivity(intent4);
                if (position == 4)
                    startActivity(intent5);
                if (position == 5)
                    startActivity(intent6);
                if (position == 6)
                    startActivity(intent7);
                if(position ==7)
                    startActivity(intent8);

            }

        });
    }
}
