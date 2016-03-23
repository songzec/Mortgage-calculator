package com.example.chens.mini2assignment2;

/**
 * Created by chens on 2016/3/21.
 */
import android.database.Cursor;
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
                        propertyTaxTextView, propertyInsuranceTextView, PMITextView, ZIPCodeTextView;
    private DataBase db;
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
        db = new DataBase(MainActivity.this);

        purchasePriceTextView = (TextView) findViewById(R.id.purchasePrice);
        downPaymentPriceTextView = (TextView) findViewById(R.id.downPaymentNumber);
        downPaymentPercentageTextView = (TextView) findViewById(R.id.downPaymentPercentage);
        mortgageTermTextView = (TextView) findViewById(R.id.mortgageTerm);
        interestRateTextView = (TextView) findViewById(R.id.interestRate);
        propertyTaxTextView = (TextView) findViewById(R.id.propertyTax);
        propertyInsuranceTextView = (TextView) findViewById(R.id.propertyInsurance);
        PMITextView = (TextView) findViewById(R.id.PMI);
        ZIPCodeTextView = (TextView) findViewById(R.id.ZIPCode);
        monthSpinner = (Spinner) findViewById(R.id.monthspinner);
        yearSpinner = (Spinner) findViewById(R.id.yearspinner);
        resultTextView = (TextView) findViewById(R.id.resultTextView);

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

        /* setting defualt value */
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

                    int purchasedPrice = Integer.parseInt(purchasePriceTextView.getText().toString());
                    double downPaymentPercentage = Double.parseDouble(downPaymentPercentageTextView.getText().toString());
                    int termInYears = Integer.parseInt(mortgageTermTextView.getText().toString());
                    double interestRate = Double.parseDouble(interestRateTextView.getText().toString());
                    double propertyTax = Double.parseDouble(propertyTaxTextView.getText().toString());
                    double propertyInsurance = Double.parseDouble(propertyInsuranceTextView.getText().toString());
                    double PMI = Double.parseDouble(PMITextView.getText().toString());
                    int ZIPCode = Integer.parseInt(ZIPCodeTextView.getText().toString());
                    String month = monthSpinner.getSelectedItem().toString();
                    int year = Integer.parseInt(yearSpinner.getSelectedItem().toString());

                    Mortgage mortgage = new Mortgage();
                    mortgage.setPurchasedPrice(purchasedPrice);
                    mortgage.setDownPaymentPercentage(downPaymentPercentage);
                    mortgage.setTermInYears(termInYears);
                    mortgage.setInterestRate(interestRate);
                    mortgage.setPropertyTax(propertyTax);
                    mortgage.setPropertyInsurance(propertyInsurance);
                    mortgage.setPMI(PMI);
                    mortgage.setZIPCode(ZIPCode);
                    mortgage.setMonth(month);
                    mortgage.setYear(year);
                    mortgage.calculateMonthlyPayment();

                    db.insertMortgage(mortgage);
                    resultTextView.setText(mortgage.monthlyPaymentToString());
                    db.open();
                    Cursor c = db.getAllMortgage();
                    int count = c.getCount();
                    for (int i = 0; i < count; i++) {
                        c.moveToNext();
                        System.out.println(c.getString(2));

                    }
                    db.close();
                } catch (NumberFormatException e) {
                    e.printStackTrace();
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
}
