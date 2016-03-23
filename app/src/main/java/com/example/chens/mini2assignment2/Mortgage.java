package com.example.chens.mini2assignment2;

/**
 * Created by chens on 2016/3/22.
 */
public class Mortgage {
    private int purchasedPrice;
    private double downPaymentPercentage;
    private int termInYears;
    private double interestRate;
    private double propertyTax;
    private double propertyInsurance;
    private double PMI;
    private int ZIPCode;
    private String month;
    private int year;
    private double monthlyPayment;

    public double calculateMonthlyPayment() {

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

        monthlyPayment =
                (purchasedPrice *(1 - downPaymentPercentage/100)*monthlyRate) /
                        (1-Math.pow(1+monthlyRate, -termInMonths));

        monthlyPayment = monthlyPayment + (propertyInsurance + propertyTax) / 12;
        return monthlyPayment;
    }
    public String monthlyPaymentToString() {
        return String.valueOf(monthlyPayment);
    }

    /* getters */
    public int getPurchasedPrice() {
        return purchasedPrice;
    }

    public double getDownPaymentPercentage() {
        return downPaymentPercentage;
    }

    public int getTermInYears() {
        return termInYears;
    }

    public double getInterestRate() {
        return interestRate;
    }

    public double getPropertyTax() {
        return propertyTax;
    }

    public double getPropertyInsurance() {
        return propertyInsurance;
    }

    public double getPMI() {
        return PMI;
    }

    public int getZIPCode() {
        return ZIPCode;
    }

    public String getMonth() {
        return month;
    }

    public int getYear() {
        return year;
    }

    public double getMonthlyPayment() {
        return monthlyPayment;
    }


    /* setters */
    public void setDownPaymentPercentage(double downPaymentPercentage) {
        this.downPaymentPercentage = downPaymentPercentage;
    }

    public void setPMI(double PMI) {
        this.PMI = PMI;
    }

    public void setZIPCode(int ZIPCode) {
        this.ZIPCode = ZIPCode;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public void setPurchasedPrice(int purchasedPrice) {
        this.purchasedPrice = purchasedPrice;
    }

    public void setTermInYears(int termInYears) {
        this.termInYears = termInYears;
    }

    public void setInterestRate(double interestRate) {
        this.interestRate = interestRate;
    }

    public void setPropertyTax(double propertyTax) {
        this.propertyTax = propertyTax;
    }

    public void setPropertyInsurance(double propertyInsurance) {
        this.propertyInsurance = propertyInsurance;
    }
}
