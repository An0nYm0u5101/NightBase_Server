package com.nightbase.api.artist.repository;

import com.nightbase.api.artist.model.Artist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ArtistRepository extends JpaRepository<Artist, Long> {

    Artist findArtistByName(@Param("name")String name);

    @Query("select count(e)>0 from Artist e where LOWER(e.name) = LOWER(:name)")
    boolean existsByName(@Param("name")String name);
}
