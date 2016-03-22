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
    private TextView resultTextView, purchasePriceTextView, downPaymentPriceTextView,
                        downPaymentPercentageTextView, mortgageTermTextView, interestRateTextView,
                        propertyTaxTextView, propertyInsuranceTextView, ZIPCodeTextView;
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
        mortgageTermTextView = (TextView) findViewById(R.id.mortgageTerm);
        interestRateTextView = (TextView) findViewById(R.id.interestRate);
        propertyTaxTextView = (TextView) findViewById(R.id.propertyTax);
        propertyInsuranceTextView = (TextView) findViewById(R.id.propertyInsurance);
        ZIPCodeTextView = (TextView) findViewById(R.id.ZIPCode);
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

        if (savedInstanceState == null) {
            purchasePriceTextView.setText("300000");
            downPaymentPercentageTextView.setText("20");
            mortgageTermTextView.setText("30");
            interestRateTextView.setText("4.5");
            propertyTaxTextView.setText("3000");
            propertyInsuranceTextView.setText("1500");
            ZIPCodeTextView.setText("15213");
            monthSpinner.setSelection(2);
            yearSpinner.setSelection(15);
        }
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        calculatorButton = (Button)findViewById(R.id.button);
        calculatorButton.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                try {
                    int loanAmount = (int) (Integer.parseInt(purchasePriceTextView.getText().toString()) * (1 - Double.parseDouble(downPaymentPercentageTextView.getText().toString()) / 100));
                    int termInYears = Integer.parseInt(mortgageTermTextView.getText().toString());
                    double interestRate = Double.parseDouble(interestRateTextView.getText().toString());
                    double propertyTax = Double.parseDouble(propertyTaxTextView.getText().toString());
                    double propertyInsurance = Double.parseDouble(propertyInsuranceTextView.getText().toString());
                    Double monthlyPayment = calculateMonthlyPayment(loanAmount, termInYears, interestRate);
                    monthlyPayment = monthlyPayment + (propertyTax + propertyInsurance) / 12;
                    resultTextView.setText(monthlyPayment.toString());
                } catch (NumberFormatException e) {
                    resultTextView.setText("Wrong Number Format");
                }
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

    private double calculateMonthlyPayment(
            int loanAmount, int termInYears, double interestRate) {

        // Convert interest rate into a decimal
        // eg. 6.5% = 0.065

        interestRate /= 100.0;

        // Monthly interest rate
        // is the yearly rate divided by 12

        double monthlyRate = interestRate / 12.0;

        // The length of the term in months
        // is the number of years times 12

        int termInMonths = termInYears * 12;

        // Calculate the monthly payment
        // Typically this formula is provided so
        // we won't go into the details

        // The Math.pow() method is used calculate values raised to a power

        double monthlyPayment =
                (loanAmount*monthlyRate) /
                        (1-Math.pow(1+monthlyRate, -termInMonths));

        return monthlyPayment;
    }
}
