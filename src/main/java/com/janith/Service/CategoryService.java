package com.janith.Service;

import com.janith.model.Category;

import java.util.List;

public interface CategoryService {

    public Category createCategory(String name, Long userId) throws Exception;

    public List<Category> findCategoryByShopId(Long shopId) throws Exception;

    public Category findCategoryById(Long id) throws Exception;
}
