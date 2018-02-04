package com.example.ym5_subbook;

/**
 * Created by a123456 on 2018-02-02.
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
    public String getName(){
        return this.name;

    }
    public String getDate(){
        return this.date;
    }
    public float getCharge(){
        return this.charge;
    }
    public String getComments(){
        return this.comments;
    }

    public void setName(String newName){
        this.name = newName;

    }
    public void setDate(String newDate){
        this.date = newDate;
    }
    public void setCharge(String newCharge){

        this.charge = Float.parseFloat(newCharge);
    }
    public void setComments(String newComments){
        this.comments = newComments;
    }

    public String toString(){
        return name+"|"+date+"|"+charge+"|"+comments;
    }

}
