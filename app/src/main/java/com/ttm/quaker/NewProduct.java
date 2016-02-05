package com.ttm.quaker;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

public class NewProduct extends AppCompatActivity {

    LinearLayout TrueDelights;
    LinearLayout QuakerGrits;
    LinearLayout RealMedleys;
    LinearLayout InstantOatmeal;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_product);

        TrueDelights= (LinearLayout) findViewById(R.id.true_delights_layout);
        QuakerGrits= (LinearLayout) findViewById(R.id.quaker_grits_layout);
        RealMedleys= (LinearLayout) findViewById(R.id.real_medleys_layout);
        InstantOatmeal= (LinearLayout) findViewById(R.id.instant_oatmeal_layout);

        View.OnClickListener true_delights= new View.OnClickListener() {
            public void onClick (View v)
            {
                Intent intent= new Intent(NewProduct.this, true_delights.class);
                startActivity(intent);
            }
        };
        TrueDelights.setOnClickListener(true_delights);

        View.OnClickListener quaker_grits= new View.OnClickListener() {
            public void onClick (View v)
            {
                Intent intent= new Intent(NewProduct.this, quaker_grits.class);
                startActivity(intent);
            }
        };
        QuakerGrits.setOnClickListener(quaker_grits);

        View.OnClickListener real_medleys= new View.OnClickListener() {
            public void onClick (View v)
            {
                Intent intent= new Intent(NewProduct.this, real_medleys.class);
                startActivity(intent);
            }
        };
        RealMedleys.setOnClickListener(real_medleys);

        View.OnClickListener instant_oatmeal= new View.OnClickListener() {
            public void onClick (View v)
            {
                Intent intent= new Intent(NewProduct.this, instant_oatmeal.class);
                startActivity(intent);
            }
        };
        InstantOatmeal.setOnClickListener(instant_oatmeal);
    }
}
