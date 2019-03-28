package com.city.autocomplete.application.helpers;

import com.city.autocomplete.application.models.Geoname;
import org.springframework.stereotype.Component;

import java.util.AbstractMap;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
public class CityNameBuilder {

    // We find those for wiki and the following site:
    // http://download.geonames.org/export/dump/admin1CodesASCII.txt
    private static Map<String, String> provinces = Stream.of(
            new AbstractMap.SimpleImmutableEntry<>("01", "AB"),
            new AbstractMap.SimpleImmutableEntry<>("02", "BC"),
            new AbstractMap.SimpleImmutableEntry<>("03", "MB"),
            new AbstractMap.SimpleImmutableEntry<>("04", "NB"),
            new AbstractMap.SimpleImmutableEntry<>("05", "NL"),
            new AbstractMap.SimpleImmutableEntry<>("07", "NS"),
            new AbstractMap.SimpleImmutableEntry<>("08", "ON"),
            new AbstractMap.SimpleImmutableEntry<>("09", "PE"),
            new AbstractMap.SimpleImmutableEntry<>("10", "QC"),
            new AbstractMap.SimpleImmutableEntry<>("11", "SK"),
            new AbstractMap.SimpleImmutableEntry<>("12", "YT"),
            new AbstractMap.SimpleImmutableEntry<>("13", "NT"),
            new AbstractMap.SimpleImmutableEntry<>("14", "NU"))
            .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));

    private static Map<String, String> countryNames = Stream.of(
            new AbstractMap.SimpleImmutableEntry<>("CA", "Canada"),
            new AbstractMap.SimpleImmutableEntry<>("US", "USA"))
            .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));

    public static String exec(Geoname geoname) {
        String countryName = Optional.ofNullable(countryNames.get(geoname.getCountry())).orElse("Unknown");
        String provinceName = covertToProvinceName(geoname.getStateCode());
        return geoname.getName() + ", " + provinceName + ", " + countryName;
    }

    /*
    In our data, Canada provinces code names are presented as numbers
    such as 01 for AB. However. US states are mentioned correctly in the data.
    This function converts integers to readable strings, otherwise it returns
    the input as is if it is not part of Canadian provinces map instance
     */
    private static String covertToProvinceName(String provinceCode) {
        return Optional.ofNullable(provinces.get(provinceCode)).orElse(provinceCode);
    }
}
