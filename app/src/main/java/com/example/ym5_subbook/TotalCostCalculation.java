/*
 * Copyright (c)2018 Yue Ma - All Rights Reserved. Email Address: ym5@ualberta.ca
 *
 */

package com.example.ym5_subbook;
/**
 * Represents monthly subscription
 * @author: Yue Ma
 * @version:1.0
 *
 */
import java.math.BigDecimal;
import java.util.ArrayList;

public class TotalCostCalculation {
    /**
     * calculate the summation of monthly charge
     * @param allSubs
     * @return
     */
    public String totalMonthlyCharge(ArrayList<subscription> allSubs){
        float totalCost = 0;
        for(int i = 0;i<allSubs.size();i++){
            totalCost+=allSubs.get(i).getCharge();
        }
        totalCost = round(totalCost,2);

        return Float.toString(totalCost);

    }

    /**
     * round float to some decimal length
     * @param d floatnumber
     * @param decimalPlace number of decimal bits
     * @return
     */
    public static float round(float d, int decimalPlace) {
        return BigDecimal.valueOf(d).setScale(decimalPlace,BigDecimal.ROUND_HALF_UP).floatValue();
    }
}
