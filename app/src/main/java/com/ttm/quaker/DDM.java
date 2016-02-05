package com.ttm.quaker;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class DDM extends AppCompatActivity {

    ImageView first;
    ImageView second;
    ImageView third;
    ImageView fourth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ddm);

        first= (ImageView) findViewById(R.id.first);
        second= (ImageView) findViewById(R.id.second);
        third= (ImageView) findViewById(R.id.third);
        fourth= (ImageView) findViewById(R.id.fourth);

        View.OnClickListener first_item= new View.OnClickListener() {
            public void onClick (View v)
            {
                Intent intent= new Intent(DDM.this, thousand_kcal.class);
                startActivity(intent);
            }
        };
        first.setOnClickListener(first_item);

        View.OnClickListener second_item= new View.OnClickListener() {
            public void onClick (View v)
            {
                Intent intent= new Intent(DDM.this, twelve_kcal.class);
                startActivity(intent);
            }
        };
        second.setOnClickListener(second_item);

        View.OnClickListener third_item= new View.OnClickListener() {
            public void onClick (View v)
            {
                Intent intent= new Intent(DDM.this, fifteen_kcal.class);
                startActivity(intent);
            }
        };
        third.setOnClickListener(third_item);

        View.OnClickListener fourth_item= new View.OnClickListener() {
            public void onClick (View v)
            {
                Intent intent= new Intent(DDM.this, twenty_kcal.class);
                startActivity(intent);
            }
        };
        fourth.setOnClickListener(fourth_item);
    }
}
