package com.nightbase.api.event.repository;

import com.nightbase.api.event.model.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface EventRepository extends JpaRepository<Event, Long> {
    @Query("SELECT evnt FROM Event evnt WHERE LOWER(evnt.name) = LOWER(:name)")
    Event findEventByName(@Param("name")String name);

    @Query("select count(e)>0 from Event e where LOWER(e.name) = LOWER(:name)")
    boolean existsByName(@Param("name")String name);
}
