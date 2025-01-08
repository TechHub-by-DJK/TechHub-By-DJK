package com.janith.repository;

import com.janith.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Category, Long> {

    public List<Category> findByShopId(Long id); //Find all  the categories within the shop
}
