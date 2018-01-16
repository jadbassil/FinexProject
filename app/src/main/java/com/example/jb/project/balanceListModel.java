package com.example.jb.project;

/**
 * Created by JB on 1/16/2018.
 */

public class balanceListModel {
    String Month;
    String income;
    String expenses;

    public void setExpenses(String expenses) {
        this.expenses = expenses;
    }

    public void setIncome(String income) {
        this.income = income;
    }

    public void setMonth(String month) {
        Month = month;
    }

    public String getExpenses() {
        return expenses;
    }

    public String getIncome() {
        return income;
    }

    public String getMonth() {
        return Month;
    }
}
