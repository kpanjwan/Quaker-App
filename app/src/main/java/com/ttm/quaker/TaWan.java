package com.ttm.quaker;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class TaWan extends AppCompatActivity {

    TextView address;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ta_wan);
        address= (TextView) findViewById(R.id.address);
        View.OnClickListener address_finder= new View.OnClickListener() {
            public void onClick (View v)
            {
                Uri gmmIntentUri = Uri.parse("geo:0,0?q=FX Lifestyle X'nter Lt. F2, Jl. Jendral Sudirman Pintu Satu Senayan, Jl. Jenderal Sudirman, Kota Jakarta Pusat, Daerah Khusus Ibukota Jakarta 10270, Indonesia");
                Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                mapIntent.setPackage("com.google.android.apps.maps");
                startActivity(mapIntent);
            }
        };
        address.setOnClickListener(address_finder);

    }
}
