package com.city.autocomplete.application.helpers;

import com.city.autocomplete.application.Constants;

import java.util.Optional;

public class BaseHelpers {
    /*
    This method is used to format a double number into 3 decimal places
     */
    public static double formatDecimals(double number) {
        return formatDecimals(number, Constants.DEFAULT_DECIMAL_PLACES);
    }

    public static double formatDecimals(double number, int decimalPoints){
        double base = Math.pow(10, decimalPoints);
        double formatted = Math.round(base * number)/base;
        // if the number was negative MAX_DOUBLE, then we will have NaN
        return new Double(formatted).isNaN()? 0 : formatted;
    }

    /*
   Helper function to find a minimum integer out of an array of an array
    */
    public static int findMinOfNumber(int...numbers) {
        int min = Integer.MAX_VALUE;
        for(int number: numbers) {
            if(min > number) min = number;
        }
        return min;
    }

    /*
    Helper method to print 2d array
     */
    public static void print2DArray(int[][] a) {
        for(int[] aa: a  ){
            for(int aaa: aa) {
                System.out.print(aaa + " ");
            }
            System.out.println("");
        }
    }
}
