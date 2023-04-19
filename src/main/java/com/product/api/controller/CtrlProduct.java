package com.product.api.controller;

import javax.validation.Valid;

import com.product.api.entity.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import com.product.api.dto.ApiResponse;
import com.product.api.entity.Product;
import com.product.api.service.SvcProduct;
import com.product.exception.ApiException;

import java.util.List;

@RestController
@RequestMapping("/product")
public class CtrlProduct {

	@Autowired
	SvcProduct svcProduct;

    @GetMapping
    public ResponseEntity<List<Product>> getProducts() {
        return new ResponseEntity<>(svcProduct.getProducts(), HttpStatus.OK);
    }

    @GetMapping("/{gtin}")
    public ResponseEntity<Product> getProduct(@PathVariable String gtin) {
        return new ResponseEntity<>(svcProduct.getProduct(gtin),HttpStatus.OK);
    }
	
	@PostMapping
	public ResponseEntity<ApiResponse> createProduct(@Valid @RequestBody Product in, BindingResult bindingResult){
		if(bindingResult.hasErrors())
			throw new ApiException(HttpStatus.BAD_REQUEST, bindingResult.getAllErrors().get(0).getDefaultMessage());
		return new ResponseEntity<>(svcProduct.createProduct(in),HttpStatus.OK);
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<ApiResponse> updateProduct(@PathVariable("id") Integer id, @Valid @RequestBody Product in, BindingResult bindingResult){
		if(bindingResult.hasErrors())
			throw new ApiException(HttpStatus.BAD_REQUEST, bindingResult.getAllErrors().get(0).getDefaultMessage());
		return new ResponseEntity<>(svcProduct.updateProduct(in, id),HttpStatus.OK);
	}
	
	// 2. Implementar método updateProductStock

    @PutMapping("/{gtin}/stock")
    public ResponseEntity<ApiResponse> updateProductStock(@PathVariable("gtin") String gtin, @RequestBody Product newStock, BindingResult bindingResult){
        if(bindingResult.hasErrors())
            throw new ApiException(HttpStatus.BAD_REQUEST, bindingResult.getAllErrors().get(0).getDefaultMessage());
        return new ResponseEntity<>(svcProduct.updateProductStock(gtin, newStock.getStock()),HttpStatus.OK);
    }

    @PutMapping("/{gtin}/category")
    public ResponseEntity<ApiResponse> updateProductCategory(@PathVariable("gtin") String gtin, @RequestBody Product newCategory, BindingResult bindingResult){
        if(bindingResult.hasErrors())
            throw new ApiException(HttpStatus.BAD_REQUEST, bindingResult.getAllErrors().get(0).getDefaultMessage());
        return new ResponseEntity<>(svcProduct.updateProductCategory(gtin, newCategory.getCategory_id()),HttpStatus.OK);
    }
	
	@DeleteMapping("/{id}")
	public ResponseEntity<ApiResponse> deleteProduct(@PathVariable("id") Integer id){
		return new ResponseEntity<>(svcProduct.deleteProduct(id), HttpStatus.OK);
	}
}
