package com.example.chens.mini2assignment2;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

public class MainActivity extends AppCompatActivity {
    private static final String[] months = {"January","February","March","April","May","June",
                                            "July","August","September","October","November","December"};
    private static String[] years;
    private Spinner monthSpinner, yearSpinner;
    private void createYear() {
        years = new String[31];
        for (int i = 2001; i <= 2031; i++) {
            years[i - 2001] = Integer.toString(i);
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        createYear();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        monthSpinner = (Spinner) findViewById(R.id.monthspinner);
        yearSpinner = (Spinner) findViewById(R.id.yearspinner);

        ArrayAdapter<String> monthAdapter = new ArrayAdapter<String>(
                MainActivity.this,
                android.R.layout.test_list_item, months);
        monthSpinner.setAdapter(monthAdapter);

        ArrayAdapter<String> yearAdapter = new ArrayAdapter<String>(
                MainActivity.this,
                android.R.layout.test_list_item, years);
        yearSpinner.setAdapter(yearAdapter);


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

       FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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
