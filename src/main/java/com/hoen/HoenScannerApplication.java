package com.hoen;

import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class HoenScannerApplication extends Application<io.dropwizard.Configuration> {

    public static void main(String[] args) throws Exception {
        new HoenScannerApplication().run(args);
    }

    @Override
    public void run(io.dropwizard.Configuration configuration, Environment environment) throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        List<SearchResult> searchResults = new ArrayList<>();

        InputStream rentalStream = getClass().getClassLoader().getResourceAsStream("rental_cars.json");
        InputStream hotelStream = getClass().getClassLoader().getResourceAsStream("hotels.json");

        if (rentalStream != null) {
            searchResults.addAll(mapper.readValue(rentalStream, new TypeReference<List<SearchResult>>() {}));
        }
        if (hotelStream != null) {
            searchResults.addAll(mapper.readValue(hotelStream, new TypeReference<List<SearchResult>>() {}));
        }

        environment.jersey().register(new SearchResource(searchResults));
        System.out.println("Welcome to Hoen Scanner!");
    }
}