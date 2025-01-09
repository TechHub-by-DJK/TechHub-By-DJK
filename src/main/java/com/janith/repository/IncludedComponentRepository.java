package com.janith.repository;

import com.janith.model.IncludedComponents;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IncludedComponentRepository extends JpaRepository<IncludedComponents, Long> {
}
