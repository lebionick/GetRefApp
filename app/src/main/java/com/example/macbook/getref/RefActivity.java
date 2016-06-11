package com.example.macbook.getref;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class RefActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ref);
        TextView ref = (TextView)findViewById(R.id.refView);
        String stRef = getIntent().getExtras().getString("refs");
        if(stRef!=null)
          ref.setText(stRef);
    }
}
