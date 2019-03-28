package com.city.autocomplete.application.helpers;

import static com.city.autocomplete.application.Constants.*;

public class CoordsCalculator {

    /*
    This method uses Haversine distance algorithm to measure the distance between 2 geocodes and
    return a specific score based on the distance.
     */
    public static double calculateHaversineDistanceScore(double startLatitude, double startLongitude, double endLatitude, double endLongitude) {
        double latitudeDifferenceInRad = degreeToRadians(endLatitude - startLatitude);
        double longitudeDifferenceInRad = degreeToRadians(endLongitude - startLongitude);

        double startLatitudeInRad = degreeToRadians(startLatitude);
        double endLatitudeInRadInRad = degreeToRadians(endLatitude);

         double a = Math.sin(latitudeDifferenceInRad/2) * Math.sin(latitudeDifferenceInRad/2) +
                Math.sin(longitudeDifferenceInRad/2) * Math.sin(longitudeDifferenceInRad/2) * Math.cos(startLatitudeInRad) * Math.cos(endLatitudeInRadInRad);
         double distance = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a)) * EARTH_RADIUS_IN_KM;

         if(distance <= WITHIN_25_KM) return WITHIN_25_KM_SCORE;
         if(distance <= WITHIN_50_KM) return WITHIN_50_KM_SCORE;
         return WITHIN_ANY_RANGE_SCORE;
    }

    private static double degreeToRadians(double numInDegree) {
        return Math.PI *(numInDegree)/180;
    }
}

