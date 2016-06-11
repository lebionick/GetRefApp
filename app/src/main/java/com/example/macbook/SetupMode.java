package com.example.macbook.getref;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ExpandableListView;

import java.util.ArrayList;

public class SetupMode extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setup_mode);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SetupMode.this, Settings.class);
                startActivity(intent);
            }
        });
        Button mCinema = (Button)findViewById(R.id.cinemaButton);
        mCinema.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SetupMode.this, ScrollingMovies.class);
                startActivity(intent);
            }
        });

        ExpandableListView mHomeList = (ExpandableListView)findViewById(R.id.expHome);
        ArrayList<ArrayList<String>> mHomeButtonGroups = new ArrayList<ArrayList<String>>();
        do{
            ArrayList<String> mHomeGroups = new ArrayList<String>();
            mHomeGroups.add("Beginner");
            mHomeGroups.add("Medium");
            mHomeGroups.add("Advanced");
            mHomeGroups.add("Demo");
            mHomeButtonGroups.add(mHomeGroups);
        }
        while (false);

        ExpListAdapter adapter = new ExpListAdapter(getApplicationContext(),mHomeButtonGroups);
        mHomeList.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_setup_mode, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
