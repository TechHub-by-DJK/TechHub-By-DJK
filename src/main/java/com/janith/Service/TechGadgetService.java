package com.janith.Service;

import com.janith.model.TechGadget;
import com.janith.request.CreateTechGadgetRequest;
import java.util.List;

public interface TechGadgetService {
    TechGadget createTechGadget(CreateTechGadgetRequest request);
    TechGadget updateTechGadget(Long id, CreateTechGadgetRequest request);
    void deleteTechGadget(Long id);
    TechGadget getTechGadgetById(Long id);
    List<TechGadget> getAllTechGadgets();
    List<TechGadget> getTechGadgetsByShop(Long shopId);
    List<TechGadget> getTechGadgetsByCompatibility(String compatibilityType);
}

