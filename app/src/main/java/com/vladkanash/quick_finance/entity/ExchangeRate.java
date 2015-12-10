package com.vladkanash.quick_finance.entity;

/**
 * Created by vladkanash on 12/9/15.
 */
public class ExchangeRate {

    private int id;
    private int numCode;
    private int scale;
    private String charCode;
    private String name;
    private double rate;

    public ExchangeRate(int id, int numCode, int scale, String charCode, String name, double rate) {
        this.numCode = numCode;
        this.scale = scale;
        this.charCode = charCode;
        this.name = name;
        this.rate = rate;
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getNumCode() {
        return numCode;
    }

    public void setNumCode(int numCode) {
        this.numCode = numCode;
    }

    public String getCharCode() {
        return charCode;
    }

    public void setCharCode(String charCode) {
        this.charCode = charCode;
    }

    public int getScale() {
        return scale;
    }

    public void setScale(int scale) {
        this.scale = scale;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getRate() {
        return rate;
    }

    public void setRate(double rate) {
        this.rate = rate;
    }
}
