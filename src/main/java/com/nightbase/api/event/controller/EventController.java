package com.nightbase.api.event.controller;

import com.google.maps.GeoApiContext;
import com.google.maps.GeocodingApi;
import com.google.maps.errors.ApiException;
import com.google.maps.model.GeocodingResult;
import com.nightbase.api.artist.model.Artist;
import com.nightbase.api.artist.repository.ArtistRepository;
import com.nightbase.api.event.model.Event;
import com.nightbase.api.event.repository.EventRepository;
import com.nightbase.api.exception.ResourceNotFoundException;
import com.nightbase.api.promoter.model.Promoter;
import com.nightbase.api.promoter.repository.PromoterRepository;
import com.nightbase.api.venue.model.Venue;
import com.nightbase.api.venue.repository.VenueRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.imageio.ImageIO;
import javax.validation.Valid;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

@RestController
@RequestMapping("event")
public class EventController {
    @Autowired
    EventRepository eventRepository;

    @Autowired
    VenueRepository venueRepository;

    @Autowired
    ArtistRepository artistRepository;

    @Autowired
    PromoterRepository promoterRepository;

    @Autowired
    GeoApiContext geoApiContext;

    @GetMapping("/id/{id}")
    public Event getEventById(@PathVariable(value = "id") Long EventId) {
        return eventRepository.findById(EventId).orElseThrow(() -> new ResourceNotFoundException("Event", "id",
                EventId));
    }

    @GetMapping("name/{name}")
    public ResponseEntity<Event> findEventByName(@PathVariable("name") String name) {
        Event event;
        if(eventRepository.existsByName(name)){
            event = eventRepository.findEventByName(name);
        } else {
            throw new ResourceNotFoundException("Event", "name", name);
        }
        return new ResponseEntity<>(event, HttpStatus.OK);
    }

    @GetMapping("allevents")
    public List<Event> getAllEvents() {
        return eventRepository.findAll();
    }

    @PostMapping("add")
    public Event createEvent(@Valid @RequestBody Event event) {

        if(eventRepository.findEventByName(event.getName())!= null){
            throw new ResourceNotFoundException("Event", "name", event.getName());
        }

        for(Promoter promoter : event.getPromotersTemp()){
            try {
                if(promoterRepository.existsByName(promoter.getName())){
                    promoter = promoterRepository.findPromoterByName(promoter.getName());
                } else {
                    throw new ResourceNotFoundException("Promoter", "name", promoter.getName());
                }
            } catch (ResourceNotFoundException e){
                promoter = promoterRepository.save(promoter);
            }
            event.getPromoters().add(promoter);
        }

        for(Artist artist : event.getArtistsTemp()){
            try {
                if(artistRepository.existsByName(artist.getName())){
                    artist = artistRepository.findArtistByName(artist.getName());
                } else {
                    throw new ResourceNotFoundException("Artist", "name", artist.getName());
                }
            } catch (ResourceNotFoundException e){
                artist.setCountry("Mexico");
                artist = artistRepository.save(artist);
            }
            event.getArtists().add(artist);
        }

        Venue venue = event.getVenueTemp();
        try {
            if(venueRepository.existsByName(venue.getName())){
                venue = venueRepository.findVenueByName(venue.getName());
            } else {
                throw new ResourceNotFoundException("Venue", "name", venue.getName());
            }
        } catch (ResourceNotFoundException e){
            venue.setCountry("");
            venue.setLogoLink("");
            processVenueAddress(venue);
            venue = venueRepository.save(venue);
        }
        event.setVenue(venue);

        return eventRepository.save(event);
    }

    @PutMapping("/update/{id}")
    public Event updateEvent(@PathVariable(value = "id") Long EventId, @Valid @RequestBody Event eventDetails) {

        Event event = eventRepository.findById(EventId).orElseThrow(() -> new ResourceNotFoundException("Event",
                "id", EventId));

        eventDetails.setName(event.getName());
        eventDetails.setDescription(event.getDescription());
        eventDetails.setDate(event.getDate());
        eventDetails.setPrice(event.getPrice());
        eventDetails.setResidentAdvisorLink(event.getResidentAdvisorLink());
        eventDetails.setMinimumAge(event.getMinimumAge());
        eventDetails.setVenue(event.getVenue());

        return eventRepository.save(event);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteEvent(@PathVariable(value = "id") Long EventId) {
        Event Event = eventRepository.findById(EventId).orElseThrow(() -> new ResourceNotFoundException("Event",
                "id", EventId));

        eventRepository.delete(Event);

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

    private void processEventImage(String imageURL) throws IOException {
        URL url = new URL(imageURL);
        BufferedImage img = ImageIO.read(url);
        File file = new File("D:\\image\\downloaded.jpg");
        ImageIO.write(img, "jpg", file);
    }
}
