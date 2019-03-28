package com.city.autocomplete.application.helpers;

import com.city.autocomplete.application.models.Geoname;
import org.apache.tomcat.util.http.fileupload.util.Streams;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;


@Component
public class DataLoader {

    static String DATA_FILE_NAME;
    Logger logger = LoggerFactory.getLogger(DataLoader.class);

    // Spring does not inject values into static class variable.
    // The work around is to make this class @component
    // and then provide a setter function called by spring boot with
    // the default value
    @Value("${app.DATA_FILE_NAME}")
    private void setDataFileName(String filename) {
        this.DATA_FILE_NAME = filename;
    }

    public List<Geoname> loadData() {
        logger.info("Data loading started ... ");

        List<Geoname> geonames = Collections.EMPTY_LIST;
        try {
            InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream(DATA_FILE_NAME);
            Stream<String> lines = Arrays.asList(Streams.asString(inputStream, "utf-8")
                    .split("\n"))
                    .stream();
            geonames = lines.map(line -> line.split("\t"))
                    .skip(1) // we skip the first row because it contains column names
                    .map(Geoname::createGeonameFromArray)
                    .collect(Collectors.toList());

            geonames.forEach(geoname -> logger.trace(geoname.toString()));
        } catch (IOException e) {
            e.printStackTrace();
            logger.error("Something wrong happened when we tried to load the data");
        }

        logger.info(String.format("Data loading completed: Loaded %d rows of data", geonames.size()));
        return geonames;

    }
}



