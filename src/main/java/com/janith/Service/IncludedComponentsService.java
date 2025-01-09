package com.janith.Service;

import com.janith.model.ComponentCategory;

import java.util.List;

public interface IncludedComponentsService {

    public ComponentCategory createComponentCategory(String name, Long shopId)throws Exception;

    public ComponentCategory findComponentCategoryById(Long id)throws Exception;

    public List<ComponentCategory> findComponentCategoryByShopId(Long shopId)throws Exception;
}
