package com.product.api.controller;

import com.product.api.entity.Category;
import com.product.api.service.SvcCategory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/category")
public class CtrlCategory {

    @Autowired
    SvcCategory svcCategory;

    @GetMapping
    public ResponseEntity<List<Category>> getCategories() {
        return new ResponseEntity<>(svcCategory.getCategories(), HttpStatus.OK);
    }

    @GetMapping("/{category_id}")
    public ResponseEntity<Category> getCategory(@PathVariable Integer category_id) {
        return new ResponseEntity<>(svcCategory.getCategory(category_id),HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<String> createCategory(@Valid @RequestBody Category category, BindingResult bindingResult) {
        return new ResponseEntity<>(svcCategory.createRegion(category), HttpStatus.CREATED);
    }

    @PutMapping("/{category_id}")
    public ResponseEntity<String> updateCategory(@PathVariable Integer category_id, @RequestBody Category category, BindingResult bindingResult) {
        return new ResponseEntity<>(svcCategory.updateRegion(category_id,category),HttpStatus.OK);
    }

    @DeleteMapping("/{category_id}")
    public ResponseEntity<String> deleteCategory(@PathVariable Integer category_id) {
        return new ResponseEntity<>(svcCategory.deleteCategory(category_id),HttpStatus.OK);
    }

}
