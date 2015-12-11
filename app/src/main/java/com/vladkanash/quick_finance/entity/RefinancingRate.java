package com.vladkanash.quick_finance.entity;

/**
 * Created by vladkanash on 12/11/15.
 */
public class RefinancingRate {

    private int rate;
    private String date;

    public RefinancingRate(String date, int rate) {
        this.date = date.replaceAll("/", ".");
        this.rate = rate;
    }

    public int getRate() {
        return rate;
    }

    public void setRate(int rate) {
        this.rate = rate;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
