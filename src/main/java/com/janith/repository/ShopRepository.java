package com.janith.repository;

import com.janith.model.Shop;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ShopRepository extends JpaRepository<Shop, Long> {

    @Query("SELECT r FROM Shop r WHERE lower(r.name) LIKE  lower(concat('%',:query, '%')) " +
            "OR lower(r.buildingtype) LIKE lower(concat('%', :query, '%') ) ")
    List<Shop> findBySearchQuery(String query);
    Shop findByOwnerId(Long Userid);
}
