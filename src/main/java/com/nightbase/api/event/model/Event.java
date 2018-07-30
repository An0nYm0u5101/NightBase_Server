package com.nightbase.api.event.model;

import com.nightbase.api.artist.model.Artist;
import com.nightbase.api.promoter.model.Promoter;
import com.nightbase.api.venue.model.Venue;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name="events")
public class Event implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="id_event")
    private Long idEvent;

    @ManyToOne
    @JoinColumn(name="id_venue")
    private Venue venue;

    @NotBlank
    @Column(name="name")
    private String name;
    @Column(name="description")
    private String description;
    @Column(name="date")
    private String date;
    @Column(name="price")
    private String price;
    @Column(name="resident_advisor_link")
    private String residentAdvisorLink;
    @Column(name="resident_advisor_members")
    private String residentAdvisorMembers;
    @Column(name="minimum_age")
    private String minimumAge;
    @Column(name="flyer_url")
    private String flyerURL;
    @Column(name="flyer_image")
    private String flyerImage;

    @ManyToMany(fetch = FetchType.LAZY,
            cascade = {
                    CascadeType.PERSIST,
                    CascadeType.MERGE
            })
    @JoinTable(name = "event_artists",
            joinColumns = { @JoinColumn(name = "id_event") },
            inverseJoinColumns = { @JoinColumn(name = "id_artist") })
    private Set<Artist> artists = new HashSet<>();

    @ManyToMany(fetch = FetchType.LAZY,
            cascade = {
                    CascadeType.PERSIST,
                    CascadeType.MERGE
            })
    @JoinTable(name = "event_promoters",
            joinColumns = { @JoinColumn(name = "id_event") },
            inverseJoinColumns = { @JoinColumn(name = "id_promoter") })
    private Set<Promoter> promoters = new HashSet<>();

    @Transient
    private Venue venueTemp;
    @Transient
    private List<Artist> artistsTemp;
    @Transient
    private List<Promoter> promotersTemp;

    public Event() {
    }

    public Event(Venue venue, String name, String description, String date, String price, String residentAdvisorLink,
                 String minimumAge) {
        this.venue = venue;
        this.name = name;
        this.description = description;
        this.date = date;
        this.price = price;
        this.residentAdvisorLink = residentAdvisorLink;
        this.minimumAge = minimumAge;
    }

    public String getFlyerImage() {
        return flyerImage;
    }

    public void setFlyerImage(String flyerImage) {
        this.flyerImage = flyerImage;
    }

    public Long getIdEvent() {
        return idEvent;
    }

    public void setIdEvent(Long idEvent) {
        this.idEvent = idEvent;
    }

    public Venue getVenue() {
        return venue;
    }

    public void setVenue(Venue venue) {
        this.venue = venue;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getResidentAdvisorLink() {
        return residentAdvisorLink;
    }

    public void setResidentAdvisorLink(String residentAdvisorLink) {
        this.residentAdvisorLink = residentAdvisorLink;
    }

    public String getMinimumAge() {
        return minimumAge;
    }

    public void setMinimumAge(String minimumAge) {
        this.minimumAge = minimumAge;
    }

    public Venue getVenueTemp() {
        return venueTemp;
    }

    public void setVenueTemp(Venue venueTemp) {
        this.venueTemp = venueTemp;
    }

    public List<Artist> getArtistsTemp() {
        return artistsTemp;
    }

    public void setArtistsTemp(List<Artist> artistsTemp) {
        this.artistsTemp = artistsTemp;
    }

    public Set<Artist> getArtists() {
        return artists;
    }

    public void setArtists(Set<Artist> artists) {
        this.artists = artists;
    }

    public List<Promoter> getPromotersTemp() {
        return promotersTemp;
    }

    public void setPromotersTemp(List<Promoter> promotersTemp) {
        this.promotersTemp = promotersTemp;
    }

    public String getResidentAdvisorMembers() {
        return residentAdvisorMembers;
    }

    public void setResidentAdvisorMembers(String residentAdvisorMembers) {
        this.residentAdvisorMembers = residentAdvisorMembers;
    }

    public Set<Promoter> getPromoters() {
        return promoters;
    }

    public void setPromoters(Set<Promoter> promoters) {
        this.promoters = promoters;
    }

    public String getFlyerURL() {
        return flyerURL;
    }

    public void setFlyerURL(String flyerURL) {
        this.flyerURL = flyerURL;
    }
}

