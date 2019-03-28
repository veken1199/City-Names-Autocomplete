package com.city.autocomplete.application;

import org.springframework.stereotype.Component;

@Component
public class Constants {
    public static final int DATA_ID_INDEX = 0;
    public static final int DATA_NAME_INDEX = 1;
    public static final int DATA_ASCII_INDEX = 2;
    public static final int DATA_ALT_NAME_INDEX = 3;
    public static final int DATA_LATITUDE_INDEX = 4;
    public static final int DATA_LONGITUDE_INDEX = 5;
    public static final int DATA_COUNTRY_INDEX = 8;
    public static final int DATA_ADMINCODE_INDEX = 10;

    public static final String RESPONSE_SUCCESS_MESSAGE = "Operation succeeded";
    public static final String RESPONSE_ERROR_MESSAGE = "Operation failed";

    public static final int MAX_STRING_SIMILARITY_SCORE = 1;
    public static final int MIN_STRING_SIMILARITY_SCORE = 0;

    public static final int DEFAULT_DECIMAL_PLACES = 3;

    public static final String QUERY_DEFAULT_MESSAGE = "Search query should not be empty";
    public static final String MIN_SCORE_DEFAULT_ERROR_MESSAGE = "minScore should be between 0 and 1";
    public static final String LATITUDE_DEFAULT_ERROR_MESSAGE = "Latitude value must be between -90 to 90.";
    public static final String LONGITUDE_DEFAULT_ERROR_MESSAGE = "Longitude value must be between -180 to 180.";
    public static final String LIMIT_DEFAULT_ERROR_MESSAGE = "Limit should be between 1 and 2000";
    public static final String SIMILARITY_ALGO_DEFAULT_MESSAGE = "By default, the api uses Levenshtein algo. " +
            "The only accepted values is 'Jaro' for Jaro winkler algo";

    public final static double EARTH_RADIUS_IN_KM = 6371;

    public final static double  WITHIN_25_KM = 25;
    public final static double  WITHIN_50_KM = 50;

    public final static double  WITHIN_25_KM_SCORE = 0.3;
    public final static double  WITHIN_50_KM_SCORE = 0.15;
    public final static double  WITHIN_ANY_RANGE_SCORE = 0;

    public final static String ALT_NAMES_SEPARATOR = ",";

}
