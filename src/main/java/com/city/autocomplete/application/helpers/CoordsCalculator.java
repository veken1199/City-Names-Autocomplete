package com.city.autocomplete.application.helpers;

import static com.city.autocomplete.application.Constants.*;

public class CoordsCalculator {

    /*
       This method uses Haversine distance algorithm to measure the distance between 2 geocodes and
       return a specific score based on the distance.
    */
    public static double calculateHaversineDistanceScore(double startLatitude, double startLongitude, double endLatitude, double endLongitude) {
        double distance = calculateHaversineDistance(startLatitude, startLongitude, endLatitude, endLongitude);
        if(distance <= WITHIN_30_KM) return WITHIN_30_KM_SCORE;
        if(distance <= WITHIN_60_KM) return WITHIN_60_KM_SCORE;
        return WITHIN_ANY_RANGE_SCORE;
    }

    /*
       This method uses Haversine distance algorithm to measure the distance between 2 geo locations
    */
    public static double calculateHaversineDistance(double startLatitude, double startLongitude, double endLatitude, double endLongitude) {
        double latitudeDifferenceInRad = degreeToRadians(endLatitude - startLatitude);
        double longitudeDifferenceInRad = degreeToRadians(endLongitude - startLongitude);


        double startLatitudeInRad = degreeToRadians(startLatitude);
        double endLatitudeInRadInRad = degreeToRadians(endLatitude);

         double a = Math.pow(Math.sin(latitudeDifferenceInRad/2), 2) + Math.pow(Math.sin(longitudeDifferenceInRad/2), 2) *
                 Math.cos(startLatitudeInRad) * Math.cos(endLatitudeInRadInRad);

         return  2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a)) * EARTH_RADIUS_IN_KM;
    }

    private static double degreeToRadians(double numInDegree) {
        return Math.PI *(numInDegree)/180;
    }
}

