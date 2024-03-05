package com.yi.truecaller.number.locator.name.finder.Constructor;

public class NumberSearch_Constructor {
    protected int iconvalue;
    protected String sg_latitude;
    protected String sg_longitude;
    private String sg_operator;
    private String sg_statename;

    public void setOperator(String str) {
        this.sg_operator = str;
    }

    public String getOperator() {
        return this.sg_operator;
    }

    public void stateName(String str) {
        this.sg_statename = str;
    }

    public void iconValue(int i) {
        this.iconvalue = i;
    }

    public void setLatitude(String str) {
        this.sg_latitude = str;
    }

    public void setLongitude(String str) {
        this.sg_longitude = str;
    }

    public String getStateName() {
        return this.sg_statename;
    }
}
