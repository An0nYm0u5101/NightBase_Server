package com.nightbase.api.promoter.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.nightbase.api.event.model.Event;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name="promoters")
public class Promoter {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="id_promoter")
    private Long idPromoter;

    @NotBlank
    @Column(name="name")
    private String name;
    @Column(name="resident_advisor")
    private String residentadvisor;
    @Column(name="region")
    private String region;

    @ManyToMany(fetch = FetchType.LAZY,
            cascade = {
                    CascadeType.PERSIST,
                    CascadeType.MERGE
            },
            mappedBy = "promoters")
    private Set<Event> events = new HashSet<>();

    public Promoter() {
    }

    public Promoter(String name, String residentadvisor, String region) {
        this.name = name;
        this.residentadvisor = residentadvisor;
        this.region = region;
    }

    public Long getIdPromoter() {
        return idPromoter;
    }

    public void setIdPromoter(Long idPromoter) {
        this.idPromoter = idPromoter;
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

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    @JsonIgnore
    public Set<Event> getEvents() {
        return events;
    }

    public void setEvents(Set<Event> events) {
        this.events = events;
    }
}
