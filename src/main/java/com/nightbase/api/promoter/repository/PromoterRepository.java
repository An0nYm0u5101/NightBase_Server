package com.nightbase.api.promoter.repository;

import com.nightbase.api.promoter.model.Promoter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PromoterRepository extends JpaRepository<Promoter, Long> {
    Promoter findPromoterByName(@Param("name")String name);

    @Query("select count(e)>0 from Promoter e where LOWER(e.name) = LOWER(:name)")
    boolean existsByName(@Param("name")String name);
}
