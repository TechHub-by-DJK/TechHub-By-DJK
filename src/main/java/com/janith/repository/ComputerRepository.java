package com.janith.repository;

import com.janith.model.Computer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ComputerRepository extends JpaRepository<Computer, Long> {

    List<Computer> findByShopId(Long shopId);

    @Query("SELECT f FROM Computer f WHERE f.name LIKE %:keyword% OR f.computerCategory.name LIKE %:keyword%")
    List<Computer> searchComputer(@Param("keyword") String keyword);
}
