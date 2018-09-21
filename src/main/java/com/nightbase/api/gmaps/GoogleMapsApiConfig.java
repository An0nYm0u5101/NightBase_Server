package com.nightbase.api.gmaps;

import com.google.maps.GeoApiContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GoogleMapsApiConfig {
    @Bean
    public GeoApiContext geoApiContext() {

        return new GeoApiContext.Builder()
                .apiKey(**REDACTED**)
                .build();
    }
}
