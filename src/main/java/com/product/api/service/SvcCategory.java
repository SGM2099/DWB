package com.product.api.service;

import com.product.api.entity.Category;

import java.util.List;

public interface SvcCategory {

    List<Category> getCategories();
    Category getCategory(Integer category_id);
    String createRegion(Category category);
    String updateRegion(Integer category_id, Category category);
    String deleteCategory(Integer category_id);

}
