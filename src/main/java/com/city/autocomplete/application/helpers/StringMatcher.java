package com.city.autocomplete.application.helpers;

import com.city.autocomplete.application.Constants;

import java.util.Objects;
import java.util.Optional;
import java.util.function.BiFunction;

public class StringMatcher {

    /*
    Simple factory method that returns JaroWinklers function if the input was
    Jaro, otherwise it will return levenshtein as default function
     */
    public static BiFunction<String, String, Double> factory(String algoName) {
        if(algoName.equals("Jaro")) return StringMatcher::JaroWinklerDistance;
        else return getDefaultAlgo();
    }

    public static BiFunction<String, String, Double> getDefaultAlgo() {
        return StringMatcher::levenshteinNormalizedDistanceScore;
    }

    public static double levenshteinNormalizedDistanceScore(String query, String target) {
        return baseMatching(query, target)
                .orElseGet(() -> { double distance = levenshteinDistance(query, target);
                        if(distance == 0) {
                            return Constants.MIN_STRING_SIMILARITY_SCORE;
                        } else {
                            return BaseHelpers.formatDecimals(1 - (distance / Math.max(query.length(), target.length())));
                        }
                    });
    }

    public static double levenshteinDistance(String query, String target) {
        // not case sensitive and no spaces before or after the inputs
        query = query.toLowerCase().trim();
        target = target.toLowerCase().trim();

        // Create distance matrix
        int[][] md = new int[target.length()+1][query.length()+1];

        // initialize the distance matrix with indices
        for (int row = 0; row < md[0].length; row++) md[0][row] = row;
        for (int column = 0; column < md.length; column++) md[column][0] = column;

        // iterate over the query and the target to fill the rest of distance matrix
        for (int targetCharIndex = 0; targetCharIndex < target.length(); targetCharIndex++) {
            char currentTargetChar = target.charAt(targetCharIndex);
            for (int queryCharIndex = 0; queryCharIndex < query.length(); queryCharIndex++) {
                if(query.charAt(queryCharIndex) == currentTargetChar) {
                    md[targetCharIndex + 1][queryCharIndex + 1] = md[targetCharIndex][queryCharIndex];
                } else {
                    md[targetCharIndex + 1][queryCharIndex + 1] = BaseHelpers.findMinOfNumber(md[targetCharIndex][queryCharIndex] + 1,
                            md[targetCharIndex][queryCharIndex + 1] + 1,
                            md[targetCharIndex + 1][queryCharIndex] + 1);
                }
            }
        }

        // return the number edits normalized
        return md[target.length()][query.length()];
    }

    public static double JaroWinklerDistance(String query, String target) {
        Optional<Double> baseResult = baseMatching(query, target);
        if(baseResult.isPresent())return baseResult.get();

        // not case sensitive and no spaces before or after the inputs
        query = query.toLowerCase().trim();
        target = target.toLowerCase().trim();

        // arrays to store which chars have already matched
        boolean[] queryMatches = new boolean[query.length()];
        boolean[] targetMatches = new boolean[target.length()];

        // character Two characters from query and target respectively, are considered matching only
        // if they are the same and not farther than max(query.len, target.len)/2 -1
        int matchingDistance = Math.max(query.length(), target.length())/2 -1;

        // find all matching characters
        int matches = 0;

        // transpositions to be calculated
        int transpositions = 0;

        for(int queryCharIndex = 0; queryCharIndex < query.length(); queryCharIndex++) {
            int front = Math.max(0, queryCharIndex - matchingDistance);
            int end = Math.min(queryCharIndex + matchingDistance + 1, target.length());

            for (; front < end; front++) {
                if (targetMatches[front]) continue;
                if (target.charAt(front) == query.charAt(queryCharIndex)) {
                    targetMatches[front] = true;
                    queryMatches[queryCharIndex] = true;
                    matches++;
                    break; // we found a match, let breaks and check new queryCharIndex
                }
            }
        }
        if(matches == 0 ) return Constants.MIN_STRING_SIMILARITY_SCORE;

        // the number of matching (but different sequence order) characters
        // divided by 2 defines the number of transpositions.
        int targetCharIndex = 0;
        for (int queryCharIndex = 0; queryCharIndex < query.length(); queryCharIndex++ ) {
            if (!queryMatches[queryCharIndex]) continue;
            while (!targetMatches[targetCharIndex]) targetCharIndex++;
            if (query.charAt(queryCharIndex) != target.charAt(targetCharIndex)) transpositions++;
            targetCharIndex++;
        }

        // apply Jaro distance formula
        double distance = ((1/3.0)*(((double) matches/query.length()) +
                ((double)matches/target.length()) +
                (((double)matches - transpositions/2.0))/matches));

        return BaseHelpers.formatDecimals(distance);
    }

    /*
    This method accepts a string of concatenated city alternative names
    that are separated by `,` and then checks if the query is an exact match of
    one of the alternative names
     */
    public static boolean isAlternativeName(String altNames, String query) {
        String[] altNameArray = altNames.toLowerCase().split(Constants.ALT_NAMES_SEPARATOR);
        for(String altName: altNameArray ) {
            if (altName.equals(query.toLowerCase())) return true;
        }
        return false;
    }

    /*
    Helper function to check strings passed into similarity algo methods if they
    were identical or nulls before processing them
     */
    private static Optional<Double> baseMatching(String query, String target) {
        // check for nulls
        if(Objects.isNull(query) || Objects.isNull(target)) return Optional.of(Constants.MIN_STRING_SIMILARITY_SCORE);

        // not case sensitive
        query = query.toLowerCase();
        target = target.toLowerCase();

        if(query.equals(target)) return Optional.of(Constants.MAX_STRING_SIMILARITY_SCORE);
        return Optional.ofNullable(null);
    }

}
