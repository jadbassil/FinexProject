package com.example.jb.project;

/**
 * Created by JB on 1/11/2018.
 */

public class ObjectivesListModel {

    private String name = "";
    private String endDate = "";
    private String amount = "";

    //Setters
    public void setName(String name){
        this.name = name;
    }

    public void setEndDate(String endDate){
        this.endDate = endDate;
    }

    public void setAmount(String amount){
        this.amount = amount;
    }

    //Getters
    public String getName(){
        return this.name;
    }

    public String getEndDate(){
        return this.endDate;
    }

    public String getAmount(){
        return this.amount;
    }

}
