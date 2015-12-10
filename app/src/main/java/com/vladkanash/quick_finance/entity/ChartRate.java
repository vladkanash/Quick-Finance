package com.vladkanash.quick_finance.entity;

import java.util.Date;

/**
 * Created by vladkanash on 12/10/15.
 */
public class ChartRate {

    private double rate;
    private Date date;

    public ChartRate(double rate, Date date) {
        this.rate = rate;
        this.date = date;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public double getRate() {
        return rate;
    }

    public void setRate(double rate) {
        this.rate = rate;
    }
}
