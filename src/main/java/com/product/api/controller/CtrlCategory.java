package com.product.api.controller;

import com.product.api.dto.ApiResponse;
import com.product.api.entity.Category;
import com.product.api.service.SvcCategory;
import com.product.exception.ApiException;
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
    public ResponseEntity<ApiResponse> createCategory(@Valid @RequestBody Category category, BindingResult bindingResult) {

        if (bindingResult.hasErrors()){
            throw new ApiException(HttpStatus.BAD_REQUEST, bindingResult.getAllErrors().get(0).getDefaultMessage());
        }

        return new ResponseEntity<>(svcCategory.createCategory(category), HttpStatus.CREATED);
    }

    @PutMapping("/{category_id}")
    public ResponseEntity<ApiResponse> updateCategory(@PathVariable Integer category_id, @RequestBody Category category, BindingResult bindingResult) {
        return new ResponseEntity<>(svcCategory.updateCategory(category_id,category),HttpStatus.OK);
    }

    @DeleteMapping("/{category_id}")
    public ResponseEntity<ApiResponse> deleteCategory(@PathVariable Integer category_id) {
        return new ResponseEntity<>(svcCategory.deleteCategory(category_id),HttpStatus.OK);
    }

}
