package com.nightbase.api.artist.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.nightbase.api.event.model.Event;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name="artists")
public class Artist implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="id_artist")
    private Long idArtist;

    @NotBlank
    @Column(name="name")
    private String name;
    @Column(name="resident_advisor")
    private String residentadvisor;
    @Column(name="soundcloud")
    private String soundcloud;
    @Column(name="facebook")
    private String facebook;
    @Column(name="twitter")
    private String twitter;
    @Column(name="instagram")
    private String instagram;
    @Column(name="country")
    private String country;
    @Column(name="label")
    private String label;

    @ManyToMany(fetch = FetchType.LAZY,
            cascade = {
                    CascadeType.PERSIST,
                    CascadeType.MERGE
            },
            mappedBy = "artists")
    private Set<Event> events = new HashSet<>();

    public Artist(){}

    public Artist(String name, String residentadvisor, String soundcloud, String facebook, String twitter, String
            instagram, String country) {
        this.name = name;
        this.residentadvisor = residentadvisor;
        this.soundcloud = soundcloud;
        this.facebook = facebook;
        this.twitter = twitter;
        this.instagram = instagram;
        this.country = country;
    }

    public Long getIdArtist() {
        return idArtist;
    }

    public void setIdArtist(Long idArtist) {
        this.idArtist = idArtist;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getResidentadvisor() {
        return residentadvisor;
    }

    public void setResidentadvisor(String residentadvisor) {
        this.residentadvisor = residentadvisor;
    }

    public String getSoundcloud() {
        return soundcloud;
    }

    public void setSoundcloud(String soundcloud) {
        this.soundcloud = soundcloud;
    }

    public String getFacebook() {
        return facebook;
    }

    public void setFacebook(String facebook) {
        this.facebook = facebook;
    }

    public String getTwitter() {
        return twitter;
    }

    public void setTwitter(String twitter) {
        this.twitter = twitter;
    }

    public String getInstagram() {
        return instagram;
    }

    public void setInstagram(String instagram) {
        this.instagram = instagram;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    @JsonIgnore
    public Set<Event> getEvents() {
        return events;
    }

    public void setEvents(Set<Event> events) {
        this.events = events;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    @Override
    public String toString() {
        return "Artist{" + "idArtist=" + idArtist + ", name='" + name + '\'' + ", residentadvisor='" +
                residentadvisor + '\'' + ", soundcloud='" + soundcloud + '\'' + ", facebook='" + facebook + '\'' + "," +
                " twitter='" + twitter + '\'' + ", instagram='" + instagram + '\'' + ", country='" + country + '\'' + '}';
    }
}
