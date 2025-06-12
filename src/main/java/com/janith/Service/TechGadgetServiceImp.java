package com.janith.Service;

import com.janith.model.ComponentCategory;
import com.janith.model.Shop;
import com.janith.model.TechGadget;
import com.janith.repository.ComponentCategoryRepository;
import com.janith.repository.ShopRepository;
import com.janith.repository.TechGadgetRepository;
import com.janith.request.CreateTechGadgetRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TechGadgetServiceImp implements TechGadgetService {
    @Autowired
    private TechGadgetRepository techGadgetRepository;
    @Autowired
    private ShopRepository shopRepository;
    @Autowired
    private ComponentCategoryRepository componentCategoryRepository;

    @Override
    public TechGadget createTechGadget(CreateTechGadgetRequest request) {
        TechGadget gadget = new TechGadget();
        gadget.setName(request.getName());
        gadget.setDescription(request.getDescription());
        gadget.setPrice(request.getPrice());
        gadget.setImages(request.getImages());
        gadget.setBrand(request.getBrand());
        gadget.setSpecs(request.getSpecs());
        gadget.setAvailable(request.isAvailable());
        if (request.getCategoryId() != null) {
            Optional<ComponentCategory> category = componentCategoryRepository.findById(request.getCategoryId());
            category.ifPresent(gadget::setCategory);
        }
        if (request.getShopId() != null) {
            Optional<Shop> shop = shopRepository.findById(request.getShopId());
            shop.ifPresent(gadget::setShop);
        }
        if (request.getCompatibilityType() != null) {
            try {
                gadget.setCompatibilityType(TechGadget.CompatibilityType.valueOf(request.getCompatibilityType().toUpperCase()));
            } catch (IllegalArgumentException e) {
                gadget.setCompatibilityType(TechGadget.CompatibilityType.BOTH);
            }
        } else {
            gadget.setCompatibilityType(TechGadget.CompatibilityType.BOTH);
        }
        return techGadgetRepository.save(gadget);
    }

    @Override
    public TechGadget updateTechGadget(Long id, CreateTechGadgetRequest request) {
        Optional<TechGadget> optionalGadget = techGadgetRepository.findById(id);
        if (optionalGadget.isEmpty()) return null;
        TechGadget gadget = optionalGadget.get();
        gadget.setName(request.getName());
        gadget.setDescription(request.getDescription());
        gadget.setPrice(request.getPrice());
        gadget.setImages(request.getImages());
        gadget.setBrand(request.getBrand());
        gadget.setSpecs(request.getSpecs());
        gadget.setAvailable(request.isAvailable());
        if (request.getCategoryId() != null) {
            Optional<ComponentCategory> category = componentCategoryRepository.findById(request.getCategoryId());
            category.ifPresent(gadget::setCategory);
        }
        if (request.getShopId() != null) {
            Optional<Shop> shop = shopRepository.findById(request.getShopId());
            shop.ifPresent(gadget::setShop);
        }
        if (request.getCompatibilityType() != null) {
            try {
                gadget.setCompatibilityType(TechGadget.CompatibilityType.valueOf(request.getCompatibilityType().toUpperCase()));
            } catch (IllegalArgumentException e) {
                gadget.setCompatibilityType(TechGadget.CompatibilityType.BOTH);
            }
        } else {
            gadget.setCompatibilityType(TechGadget.CompatibilityType.BOTH);
        }
        return techGadgetRepository.save(gadget);
    }

    @Override
    public void deleteTechGadget(Long id) {
        techGadgetRepository.deleteById(id);
    }

    @Override
    public TechGadget getTechGadgetById(Long id) {
        return techGadgetRepository.findById(id).orElse(null);
    }

    @Override
    public List<TechGadget> getAllTechGadgets() {
        return techGadgetRepository.findAll();
    }

    @Override
    public List<TechGadget> getTechGadgetsByShop(Long shopId) {
        return techGadgetRepository.findByShopId(shopId);
    }

    @Override
    public List<TechGadget> getTechGadgetsByCompatibility(String compatibilityType) {
        try {
            TechGadget.CompatibilityType type = TechGadget.CompatibilityType.valueOf(compatibilityType.toUpperCase());
            return techGadgetRepository.findByCompatibilityType(type);
        } catch (IllegalArgumentException e) {
            return techGadgetRepository.findAll();
        }
    }
}

