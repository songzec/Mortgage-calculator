package com.example.chens.mini2assignment2.util;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase.CursorFactory;

import com.example.chens.mini2assignment2.model.Mortgage;

/**
 * Created by chens on 2016/3/22.
 */
public class DataBase {
    private DatabaseOpenHelper databaseOpenHelper;
    private final String tableName = "MortgageTable";
    private final String ID = "ID";
    private final String PurchasedPrice = "PurchasedPrice";
    private final String DownPaymentPercentage = "DownPaymentPercentage";
    private final String MortgageTerm = "MortgageTerm";
    private final String InterestRate = "InterestRate";
    private final String PropertyTax = "PropertyTax";
    private final String PropertyInsurance = "PropertyInsurance";
    private final String ZIPCode = "ZIPCode";
    private final String month = "Month";
    private final String year = "Year";
    private SQLiteDatabase database;
    public DataBase(Context context) {
        databaseOpenHelper =
                new DatabaseOpenHelper(context, "MortgageDatabase", null, 1);
    }
    public void insertMortgage(Mortgage mortgage) {
        ContentValues newMortgage = new ContentValues();
        newMortgage.put(PurchasedPrice, mortgage.getPurchasedPrice());
        newMortgage.put(DownPaymentPercentage, mortgage.getDownPaymentPercentage());
        newMortgage.put(MortgageTerm, mortgage.getTermInYears());
        newMortgage.put(InterestRate, mortgage.getInterestRate());
        newMortgage.put(PropertyTax, mortgage.getPropertyTax());
        newMortgage.put(PropertyInsurance, mortgage.getPropertyInsurance());
        newMortgage.put(ZIPCode, mortgage.getZIPCode());
        newMortgage.put(month, mortgage.getMonth());
        newMortgage.put(year, mortgage.getYear());
        open();
        database.insert(tableName, null, newMortgage);
        close();
    }

    public void open() throws SQLException {
        // create or open a database for reading/writing
        database = databaseOpenHelper.getWritableDatabase();
    }

    public void close() {
        if (database != null)
            database.close(); // close the database connection
    }

    public Cursor getAllMortgage() {
        return database.query(tableName, null, null, null, null, null, ID);
    }
    private class DatabaseOpenHelper extends SQLiteOpenHelper {
        public DatabaseOpenHelper(Context context, String name, CursorFactory factory, int version) {
            super(context, name, factory, version);
        }
        @Override
        public void onCreate(SQLiteDatabase db) {
            String createQuery = "CREATE TABLE " + tableName + " ("
                    + ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + PurchasedPrice + " TEXT,"
                    + DownPaymentPercentage + " TEXT,"
                    + MortgageTerm + " TEXT,"
                    + InterestRate + " TEXT,"
                    + PropertyTax + " TEXT,"
                    + PropertyInsurance + " TEXT,"
                    + ZIPCode + " TEXT,"
                    + month + " TEXT,"
                    + year +  " TEXT)";
            db.execSQL(createQuery);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        }
    }
}
