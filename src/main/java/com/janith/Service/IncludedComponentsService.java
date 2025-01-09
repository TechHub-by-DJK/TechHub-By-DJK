package com.janith.Service;

import com.janith.model.ComponentCategory;
import com.janith.model.IncludedComponents;

import java.util.List;

public interface IncludedComponentsService {

    public ComponentCategory createComponentCategory(String name, Long shopId)throws Exception;

    public ComponentCategory findComponentCategoryById(Long id)throws Exception;

    public List<ComponentCategory> findComponentCategoryByShopId(Long shopId)throws Exception;

    public IncludedComponents createComponent(Long shopId, String componentName, long categoryId)throws Exception;

    public List<IncludedComponents> findShopIncludedComponents(Long shopId)throws Exception;

    public IncludedComponents updateStock(Long id) throws Exception;
}
