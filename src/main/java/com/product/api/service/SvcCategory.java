package com.product.api.service;

import com.product.api.dto.ApiResponse;
import com.product.api.entity.Category;

import java.util.List;

public interface SvcCategory {

    List<Category> getCategories();
    Category getCategory(Integer category_id);
    ApiResponse createRegion(Category category);
    ApiResponse updateRegion(Integer category_id, Category category);
    ApiResponse deleteCategory(Integer category_id);

}
