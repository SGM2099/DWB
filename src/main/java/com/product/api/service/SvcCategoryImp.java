package com.product.api.service;

import com.product.api.entity.Category;
import com.product.api.repository.RepoCategory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SvcCategoryImp implements SvcCategory {

    @Autowired
    RepoCategory repo;

    @Override
    public List<Category> getCategories() {
        return repo.findByStatus(1);
    }

    @Override
    public Category getCategory(Integer category_id) {
        return repo.findByCategoryId(category_id);
    }

    @Override
    public String createRegion(Category category) {

        Category categorySaved = repo.findByCategory(category.getCategory());

        if (categorySaved != null){
            if (categorySaved.getStatus() == 0)
                repo.activateCategory(categorySaved.getCategory_id());
            else
                return "category already exist";
        }

        repo.createCategory(category.getCategory(), category.getAcronym());
        return "category created";

    }

    @Override
    public String updateRegion(Integer category_id, Category category) {

        Category categorySaved = repo.findByCategoryId(category_id);

        if (categorySaved != null){

            if (categorySaved.getStatus() == 0) {
                return "category is not activated";
            } else {

                categorySaved = (Category) repo.findByCategory(category.getCategory());

                if (categorySaved != null) {
                    return "category already exist";
                } else {

                    repo.updateCategory(category_id, category.getCategory());
                    return "category updated";

                }

            }

        } else {
            return "category does not exist";
        }

    }

    @Override
    public String deleteCategory(Integer category_id) {

        Category categorySaved = repo.findByCategoryId(category_id);

        if (categorySaved != null){

            repo.deleteById(category_id);
            return "category removed";

        } else {
            return "category does not exist";
        }

    }

}
