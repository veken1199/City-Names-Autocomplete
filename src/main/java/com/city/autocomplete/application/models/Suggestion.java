package com.city.autocomplete.application.models;

import com.city.autocomplete.application.helpers.CityNameBuilder;
import lombok.Getter;

@Getter
public class Suggestion implements Comparable<Suggestion> {
    public String name;
    public double latitude;
    public double longitude;
    public double score;

    public static Suggestion build(Geoname geoname) {
        Suggestion suggestion = new Suggestion();
        suggestion.name = CityNameBuilder.exec(geoname);
        suggestion.latitude = geoname.getLatitude();
        suggestion.longitude = geoname.getLongitude();
        return suggestion;
    }

    @Override
    public int compareTo(Suggestion o) {
        return (int)(((this.score * 1000) - (this.score * 1000)));
    }


}
