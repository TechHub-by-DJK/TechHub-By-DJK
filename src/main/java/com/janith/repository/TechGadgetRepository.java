package com.janith.repository;

import com.janith.model.TechGadget;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface TechGadgetRepository extends JpaRepository<TechGadget, Long> {
    List<TechGadget> findByShopId(Long shopId);
    List<TechGadget> findByCompatibilityType(TechGadget.CompatibilityType compatibilityType);
}

