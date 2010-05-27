package com.flowfact.mongodbsample;

import com.mongodb.ReflectionDBObject;

/**
{accountNumber : "9876543210", bankCode : "30020011", accountHolder : "Otto Normal"} }
 */
public class BankData extends ReflectionDBObject {
    private String accountNumber;
    private String bankCode;
    private String accountHolder;

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getBankCode() {
        return bankCode;
    }

    public void setBankCode(String bankCode) {
        this.bankCode = bankCode;
    }

    public String getAccountHolder() {
        return accountHolder;
    }

    public void setAccountHolder(String accountHolder) {
        this.accountHolder = accountHolder;
    }
}
