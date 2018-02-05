/*
 * Copyright (c)2018 Yue Ma - All Rights Reserved. Email Address: ym5@ualberta.ca
 *
 */

package com.example.ym5_subbook;

/**
 * build a subscription
 * @author: Yue Ma
 * @version:1.0
 *
 */
public class subscription {
    private String name;
    private String date;
    private float charge;
    private String comments;
    subscription(String name, String date, String charge, String comments){
        this.name = name;
        this.date = date;
        this.charge = Float.parseFloat(charge);;
        this.comments = comments;
    }

    /**
     * get name
     * @return name
     */
    public String getName(){return this.name;}

    /**
     * get date
     * @return date
     */
    public String getDate(){return this.date;}

    /**
     * get charge
     * @return charge
     */
    public float getCharge(){return this.charge;}

    /**
     *get comments
     * @return comments
     */
    public String getComments(){return this.comments;}

    /**
     * set NAME
     * @param newName
     */
    public void setName(String newName){
        this.name = newName;
    }

    /**
     * set date
     * @param newDate
     */
    public void setDate(String newDate){
        this.date = newDate;
    }

    /**
     * set charge
     * @param newCharge
     */
    public void setCharge(String newCharge){

        this.charge = Float.parseFloat(newCharge);
    }

    /**
     * set comments
     * @param newComments
     */
    public void setComments(String newComments){
        this.comments = newComments;
    }

}
