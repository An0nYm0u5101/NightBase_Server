package com.nightbase.api.venue.controller;

import com.google.maps.GeoApiContext;
import com.google.maps.GeocodingApi;
import com.google.maps.errors.ApiException;
import com.google.maps.model.GeocodingResult;
import com.nightbase.api.exception.ResourceNotFoundException;
import com.nightbase.api.venue.model.Venue;
import com.nightbase.api.venue.repository.VenueRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("venue")
public class VenueController {
    @Autowired
    VenueRepository venueRepository;
    @Autowired
    GeoApiContext geoApiContext;

    @GetMapping("/id/{id}")
    public Venue findVenueById(@PathVariable(value = "id") Long VenueId) {
        return venueRepository.findById(VenueId).orElseThrow(() -> new ResourceNotFoundException("Venue", "id",
                VenueId));
    }

    @GetMapping("name/{name}")
    public ResponseEntity<Venue> getVenueByName(@PathVariable("name") String name) {
        Venue venue;
        if (venueRepository.existsByName(name)) {
            venue = venueRepository.findVenueByName(name);
        } else {
            throw new ResourceNotFoundException("Venue", "name", name);
        }
        return new ResponseEntity<>(venue, HttpStatus.OK);
    }

    @GetMapping("allvenues")
    public List<Venue> getAllVenues() {
        return venueRepository.findAll();
    }

    @PostMapping("add")
    public Venue createVenue(@Valid @RequestBody Venue venue) {
        processVenueAddress(venue);
        return venueRepository.save(venue);
    }

    @PutMapping("/update/{id}")
    public Venue updateVenue(@PathVariable(value = "id") Long VenueId, @Valid @RequestBody Venue venueDetails) {

        Venue venue = venueRepository.findById(VenueId).orElseThrow(() -> new ResourceNotFoundException("Venue",
                "id", VenueId));

        venueDetails.setName(venue.getName());
        venueDetails.setAddress(venue.getAddress());
        venueDetails.setCountry(venue.getCountry());
        venueDetails.setLogoLink(venue.getLogoLink());

        processVenueAddress(venue);

        return venueRepository.save(venue);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteVenue(@PathVariable(value = "id") Long VenueId) {
        Venue Venue = venueRepository.findById(VenueId).orElseThrow(() -> new ResourceNotFoundException("Venue",
                "id", VenueId));

        venueRepository.delete(Venue);

        return ResponseEntity.ok().build();
    }

    private void processVenueAddress(Venue venue) {
        try {
            GeocodingResult[] results = GeocodingApi.geocode(geoApiContext, venue.getAddress()).await();
            venue.setLatitude(results[0].geometry.location.lat);
            venue.setLongitude(results[0].geometry.location.lng);
        } catch (ApiException | InterruptedException | IOException e) {
            e.printStackTrace();
        }
    }

}
