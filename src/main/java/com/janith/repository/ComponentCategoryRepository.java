package com.janith.repository;

import com.janith.model.ComponentCategory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ComponentCategoryRepository extends JpaRepository<ComponentCategory, Long>{

    List<ComponentCategory> findByShopId(Long id);
}
