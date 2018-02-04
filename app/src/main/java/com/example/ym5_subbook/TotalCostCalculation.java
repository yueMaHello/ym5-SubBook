package com.example.ym5_subbook;

import java.math.BigDecimal;
import java.util.ArrayList;

/**
 * Created by a123456 on 2018-02-04.
 */

public class TotalCostCalculation {
    public String totalMonthlyCharge(ArrayList<subscription> allSubs){
        float totalCost = 0;
        for(int i = 0;i<allSubs.size();i++){
            totalCost+=allSubs.get(i).getCharge();
        }
        totalCost = round(totalCost,2);

        return Float.toString(totalCost);

    }
    public static float round(float d, int decimalPlace) {
        return BigDecimal.valueOf(d).setScale(decimalPlace,BigDecimal.ROUND_HALF_UP).floatValue();
    }
}
