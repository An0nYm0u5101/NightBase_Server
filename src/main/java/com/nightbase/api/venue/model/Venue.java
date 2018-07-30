package com.nightbase.api.venue.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.nightbase.api.event.model.Event;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "venues")
public class Venue implements Serializable {
    private static final long serialVersionUID = 1L;

    @OneToMany(mappedBy = "venue", cascade = CascadeType.ALL)
    private List<Event> events;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id_venue")
    private Long idVenue;

    @NotBlank
    @Column(name = "name")
    private String name;
    @Column(name = "country")
    private String country;
    @Column(name = "address")
    private String address;
    @Column(name = "logo_link")
    private String logoLink;
    @Column(name = "latitude")
    private double latitude;
    @Column(name = "longitude")
    private double longitude;

    public Venue() {
    }

    /*public Venue(String name) {
        this.name = name;
    }*/

    public Venue(List<Event> events, @NotBlank String name, String country, String address, String logoLink) {
        this.events = events;
        this.name = name;
        this.country = country;
        this.address = address;
        this.logoLink = logoLink;
    }

    public Long getIdVenue() {
        return idVenue;
    }

    public void setIdVenue(Long idVenue) {
        this.idVenue = idVenue;
    }

    @JsonIgnore
    public List<Event> getEvents() {
        return events;
    }

    public void setEvents(List<Event> events) {
        this.events = events;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getLogoLink() {
        return logoLink;
    }

    public void setLogoLink(String logoLink) {
        this.logoLink = logoLink;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }
}
