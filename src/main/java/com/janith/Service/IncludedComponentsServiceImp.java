package com.janith.Service;

import com.janith.model.ComponentCategory;
import com.janith.model.IncludedComponents;
import com.janith.model.Shop;
import com.janith.repository.ComponentCategoryRepository;
import com.janith.repository.IncludedComponentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class IncludedComponentsServiceImp implements IncludedComponentsService{

    @Autowired
    private IncludedComponentRepository includedComponentRepository;

    @Autowired
    private ComponentCategoryRepository componentCategoryRepository;

    @Autowired
    private ShopService shopService;


    @Override
    public ComponentCategory createComponentCategory(String name, Long shopId) throws Exception {

        Shop shop = shopService.findShopById(shopId);

        ComponentCategory category = new ComponentCategory();
        category.setName(name);
        category.setShop(shop);

        return componentCategoryRepository.save(category);
    }

    @Override
    public ComponentCategory findComponentCategoryById(Long id) throws Exception {
        Optional<ComponentCategory> opt = componentCategoryRepository.findById(id);

        if (opt.isEmpty()) {
            throw new Exception("Component category not found");
        }
        return opt.get();
    }

    @Override
    public List<ComponentCategory> findComponentCategoryByShopId(Long shopId) throws Exception {
        return List.of();
    }

    @Override
    public IncludedComponents createComponent(Long shopId, String componentName, long categoryId) throws Exception {
        return null;
    }

    @Override
    public List<IncludedComponents> findShopIncludedComponents(Long shopId) throws Exception {
        return List.of();
    }

    @Override
    public IncludedComponents updateStock(Long id) throws Exception {
        return null;
    }
}
