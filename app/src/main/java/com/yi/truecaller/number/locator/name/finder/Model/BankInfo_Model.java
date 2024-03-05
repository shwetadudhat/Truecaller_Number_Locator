package com.yi.truecaller.number.locator.name.finder.Model;

import java.io.Serializable;

public class BankInfo_Model implements Serializable {
    String bank_care = null;
    String bank_id = null;
    String bank_img = null;
    String bank_inquiry = null;
    String bank_name = null;

    public BankInfo_Model(String str, String str2, String str3, String str4, String str5) {
        this.bank_id = str;
        this.bank_name = str2;
        this.bank_inquiry = str3;
        this.bank_care = str4;
        this.bank_img = str5;
    }

    public String getBank_name() {
        return this.bank_name;
    }

    public String getBank_inquiry() {
        return this.bank_inquiry;
    }

    public String getBank_care() {
        return this.bank_care;
    }

    public String getBank_img() {
        return this.bank_img;
    }
}
