package com.example.chens.mini2assignment2;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private static final String[] months = {"Jan","Feb","Mar","Apr","May","Jun",
                                            "Jul","Aug","Sep","Oct","Nov","Dec"};
    private static String[] years;
    private Spinner monthSpinner, yearSpinner;
    private Button calculatorButton;
    private TextView resultTextView, purchasePriceTextView, downPaymentPriceTextView, downPaymentPercentageTextView;
    private TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            try  {
                int downPaymentPrice = Integer.parseInt(purchasePriceTextView.getText().toString())
                        * Integer.parseInt(downPaymentPercentageTextView.getText().toString()) / 100;
                String text = "%($" + downPaymentPrice + ")";
                downPaymentPriceTextView.setText(text);
            } catch (NumberFormatException e){
                String text = "%($NaN)";
            }
        }
        @Override
        public void afterTextChanged(Editable s) {
        }
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }
    };
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
        resultTextView = (TextView) findViewById(R.id.resultTextView);
        purchasePriceTextView = (TextView) findViewById(R.id.purchasePrice);
        downPaymentPriceTextView = (TextView) findViewById(R.id.downPaymentNumber);
        downPaymentPercentageTextView = (TextView) findViewById(R.id.downPaymentPercentage);
        purchasePriceTextView.addTextChangedListener(textWatcher);
        downPaymentPercentageTextView.addTextChangedListener(textWatcher);

        ArrayAdapter<String> monthAdapter = new ArrayAdapter<String>(
                MainActivity.this,
                android.R.layout.test_list_item,
                months);
        monthSpinner.setAdapter(monthAdapter);

        ArrayAdapter<String> yearAdapter = new ArrayAdapter<String>(
                MainActivity.this,
                android.R.layout.test_list_item,
                years);
        yearSpinner.setAdapter(yearAdapter);


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        calculatorButton = (Button)findViewById(R.id.button);
        calculatorButton.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                resultTextView.setText(purchasePriceTextView.getText());
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
