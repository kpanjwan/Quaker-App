package com.ttm.quaker;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Contest extends AppCompatActivity {

    Button AddRecipe;
    Button ViewRecipe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contest);

        AddRecipe= (Button) findViewById(R.id.AddRecipe);
        ViewRecipe= (Button) findViewById(R.id.ViewRecipe);

        View.OnClickListener View_Recipe= new View.OnClickListener(){
            public void onClick (View v)
            {
                Intent intent= new Intent(Contest.this, View_Recipes.class);
                startActivity(intent);
            }
        };
        ViewRecipe.setOnClickListener(View_Recipe);

        View.OnClickListener Add_Recipe= new View.OnClickListener(){
            public void onClick (View v)
            {
                Intent intent= new Intent(Contest.this, UploadRecipe.class);
                startActivity(intent);
            }
        };
        AddRecipe.setOnClickListener(Add_Recipe);
    }
}
