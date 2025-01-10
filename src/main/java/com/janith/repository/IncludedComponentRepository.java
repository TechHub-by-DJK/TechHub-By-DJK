package com.janith.repository;

import com.janith.model.IncludedComponents;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IncludedComponentRepository extends JpaRepository<IncludedComponents, Long> {

    List<IncludedComponents> findByShopId(Long shopId);
}
