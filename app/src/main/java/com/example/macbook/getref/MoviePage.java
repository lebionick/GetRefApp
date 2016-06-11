package com.example.macbook.getref;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by macbook on 13.04.16.
 */
public class MoviePage extends Activity {
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.movie_page);

        long MovieID;

        TextView mTitle = (TextView)findViewById(R.id.movieTitle);
        TextView mDiscription = (TextView)findViewById(R.id.movieDescription);
        TextView mActors = (TextView)findViewById(R.id.actorsView);
        Button mGetref = (Button)findViewById(R.id.getRefsButton);

        mTitle.setText(getIntent().getExtras().getString("title"));
        mDiscription.setText(getIntent().getExtras().getString("description"));
        mActors.setText(getIntent().getExtras().getString("actors"));
        MovieID= Long.parseLong(getIntent().getExtras().getString("id"));
        mGetref.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MoviePage.this, RefActivity.class);
                intent.putExtra("refs", getIntent().getExtras().getString("picture"));
                startActivity(intent);
            }
        });
    }
}
