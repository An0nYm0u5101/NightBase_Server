package com.nightbase.api.venue.repository;

import com.nightbase.api.venue.model.Venue;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface VenueRepository extends JpaRepository<Venue, Long> {
    @Query("SELECT vnue FROM Venue vnue WHERE LOWER(vnue.name) = LOWER(:name)")
    Venue findVenueByName(@Param("name")String name);

    @Query("select case when (count(e)>0) then true else false end from Venue e where LOWER(e.name) = LOWER(:name)")
    boolean existsByName(@Param("name")String name);
}
