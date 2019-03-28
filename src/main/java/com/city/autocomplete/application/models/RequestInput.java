package com.city.autocomplete.application.models;

import com.city.autocomplete.application.Constants;
import com.city.autocomplete.application.helpers.StringMatcher;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import java.util.function.BiFunction;

public class RequestInput {

    @NotNull(message = Constants.QUERY_DEFAULT_MESSAGE)
    @Getter
    @Setter
    private String q;

    @Getter
    @Setter
    @DecimalMin(value = "-180.0", message = Constants.LONGITUDE_DEFAULT_ERROR_MESSAGE)
    @DecimalMax(value = "180.0", message = Constants.LONGITUDE_DEFAULT_ERROR_MESSAGE)
    private double longitude;

    @Setter
    @Getter
    @DecimalMin(value = "-90.0", message = Constants.LATITUDE_DEFAULT_ERROR_MESSAGE)
    @DecimalMax(value = "90.0", message = Constants.LATITUDE_DEFAULT_ERROR_MESSAGE)
    private double latitude;

    @Setter
    @Getter
    @DecimalMax(value = "1.0", message = Constants.MIN_SCORE_DEFAULT_ERROR_MESSAGE)
    @DecimalMin(value = "0", message = Constants.MIN_SCORE_DEFAULT_ERROR_MESSAGE)
    private double minScore = 0.01;

    @Setter
    @Getter
    @Range(min = 1, max = 2000, message = Constants.LIMIT_DEFAULT_ERROR_MESSAGE)
    private int limit = 2000;

    @Getter
    private BiFunction<String, String, Double> similarityAlgo = StringMatcher.getDefaultAlgo();

    /*
    This is a custom setter to provide the logic of transforming
    user's input as string to one of our string matcherFunctions
     */
    public void setSimilarityAlgo(String algo) {
        this.similarityAlgo = StringMatcher.factory(algo);
    }

    public String toString() {
        return String.format("\nq: %s \n" +
                "longitude: %s\n" +
                "latitude: %s\n" +
                "minScore: %s\n" +
                "limit: %s", this.q, this.longitude, this.latitude, this.limit, this.minScore);
    }
}
