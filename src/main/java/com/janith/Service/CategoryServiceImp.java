package com.janith.Service;

import com.janith.model.Category;
import com.janith.model.Shop;
import com.janith.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryServiceImp implements CategoryService {

    @Autowired
    private ShopService shopService;

    @Autowired
    private CategoryRepository CategoryRepository;
    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public Category createCategory(String name, Long userId) throws Exception {
        Shop shop = shopService.findShopById(userId);
        Category category = new Category();
        category.setName(name);
        category.setShop(shop);

        return categoryRepository.save(category);
    }

    @Override
    public List<Category> findCategoryByShopId(Long shopId) throws Exception {
        return categoryRepository.findByShopId(shopId);
    }

    @Override
    public Category findCategoryById(Long id) throws Exception {
        Optional<Category> optionalCategory = categoryRepository.findById(id);

        if (optionalCategory.isEmpty()) {
            throw new Exception("Category not found");
        }
        return optionalCategory.get();
    }
}
