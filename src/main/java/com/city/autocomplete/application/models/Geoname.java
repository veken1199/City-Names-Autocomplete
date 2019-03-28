package com.city.autocomplete.application.models;

import lombok.Getter;

import java.util.Arrays;

import static com.city.autocomplete.application.Constants.*;

@Getter
public class Geoname {
    private int id;
    private String name;
    private String ascii;
    private String alt_name;
    private double latitude;
    private double longitude;
    private String country;
    private String stateCode;

    public static Geoname createGeonameFromArray(String[] geonameData) {
        Geoname geoname = new Geoname();
        geoname.id = Integer.parseInt(geonameData[DATA_ID_INDEX]);
        geoname.name = geonameData[DATA_NAME_INDEX];
        geoname.ascii = geonameData[DATA_ASCII_INDEX];
        geoname.alt_name = geonameData[DATA_ALT_NAME_INDEX];
        geoname.latitude = Double.parseDouble(geonameData[DATA_LATITUDE_INDEX]);
        geoname.longitude = Double.parseDouble(geonameData[DATA_LONGITUDE_INDEX]);
        geoname.country = geonameData[DATA_COUNTRY_INDEX];
        geoname.stateCode = geonameData[DATA_ADMINCODE_INDEX];

        return geoname;
    }

    public String toString() {
        return this.id + " " + this.ascii + " " + Arrays.asList(this.alt_name.split(",")).toString() +
                this.country + " " + this.stateCode + " \n";
    }
}
