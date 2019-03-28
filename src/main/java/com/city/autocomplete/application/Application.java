package com.city.autocomplete.application;

import com.city.autocomplete.application.containers.GeonameContainer;
import com.city.autocomplete.application.helpers.DataLoader;
import com.city.autocomplete.application.models.Geoname;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.List;

@SpringBootApplication
public class Application {

    @Autowired
    GeonameContainer geonameContainer;

    @Autowired
    DataLoader dataLoader;

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Bean
    public CommandLineRunner SetupApplication() {
        return (args) -> {
            List<Geoname> geonameList = dataLoader.loadData();
            geonameContainer.setGeonames(geonameList);
        };
    }
}
